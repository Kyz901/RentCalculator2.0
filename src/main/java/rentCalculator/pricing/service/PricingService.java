package rentCalculator.pricing.service;

import com.kuzmin.logger.MultiLogger;
import rentCalculator.broker.model.PricingEvent;
import rentCalculator.broker.producer.PricingKafkaProducer;
import rentCalculator.email.model.PaymentMasterInfo;
import rentCalculator.email.model.UserInfo;
import rentCalculator.pricing.model.PaymentMaster;
import rentCalculator.pricing.model.PaymentPrice;
import rentCalculator.email.model.PaymentPriceInfo;
import rentCalculator.pricing.model.Product;
import rentCalculator.pricing.repository.PaymentMasterRepository;
import rentCalculator.pricing.repository.PaymentPriceRepository;
import rentCalculator.security.model.AuthenticatedAccount;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;

@Service
public class PricingService {

    public final static Integer DEFAULT_MODIFIER = 1;
    public final static Integer PRIVILEGES_MODIFIER = 2;

    private final ProductService productService;
    private final PaymentMasterRepository paymentMasterRepository;
    private final PaymentPriceRepository paymentPriceRepository;
    private final PricingKafkaProducer pricingKafkaProducer;

    private final MultiLogger log;

    public PricingService(
        final ProductService productService, final PaymentMasterRepository paymentMasterRepository,
        final PaymentPriceRepository paymentPriceRepository, final PricingKafkaProducer pricingKafkaProducer,
        final MultiLogger log
    ) {
        this.productService = productService;
        this.paymentMasterRepository = paymentMasterRepository;
        this.paymentPriceRepository = paymentPriceRepository;
        this.pricingKafkaProducer = pricingKafkaProducer;
        this.log = log;
    }

    public PaymentMaster getPaymentMasterById(final Long paymentMasterId) {
        return paymentMasterRepository.fetchPaymentMasterById(paymentMasterId);
    }

    public List<PaymentPrice> getPaymentPrices(final Long paymentMasterId) {
        return paymentPriceRepository.fetchAllPricesByPaymentMasterId(paymentMasterId);
    }

    public List<PaymentMaster> getAllPaymentMasterForUser(final AuthenticatedAccount principal) {
        return paymentMasterRepository.fetchPaymentMasterForCurrentUser(principal.getUserId());
    }

    public PaymentMaster createPaymentMaster(
        final String paymentName,
        final AuthenticatedAccount principal
    ) {
        final long paymentMasterId = paymentMasterRepository.createPaymentMaster(principal.getUserId(), paymentName);

        log.info(() -> String.format("IN [POST createPaymentMaster]: successfully created payment: %d - initiated user: %s",
            paymentMasterId,
            principal.getUsername()
        ));

        return paymentMasterRepository.fetchPaymentMasterById(paymentMasterId);
    }

    /**
     * Pricing by formula -> Price = (NewMeterReadings - OldMeterReadings) * ProductSinglePrice(m^3)
     *
     * @param paymentPrices
     * @param paymentMasterId
     * @return List of PaymentPrices
     */
    public List<PaymentPrice> priceProduct(
        final List<PaymentPrice> paymentPrices, final Long paymentMasterId,
        final AuthenticatedAccount principal
    ) {
        final boolean hasPrivileges = principal.isHasPrivileges();

        final List<Long> productIds = paymentPrices.stream()
            .map(PaymentPrice::getProductId)
            .collect(toList());
        final Map<Long, Product> products = getProductsMap(productIds);
        final List<PaymentPriceInfo> paymentPriceInfos = calculatePaymentPrices(paymentPrices, products, paymentMasterId, hasPrivileges);

        updateTotalPriceInPaymentMaster(paymentPrices);

        final PaymentMaster paymentMaster = getPaymentMasterById(paymentMasterId);
        final PricingEvent pricingEvent = PricingEvent.fromPricing(
            PaymentMasterInfo.fromPaymentMasterInfo(
                paymentMaster,
                UserInfo.fromAuthenticatedAccount(principal)),
            paymentPriceInfos
        );

        pricingKafkaProducer.sendCompletePricing(pricingEvent);

        return paymentPriceRepository.fetchAllPricesByPaymentMasterId(paymentMasterId);
    }

    public void updateTotalPriceInPaymentMaster(final List<PaymentPrice> paymentPrices) {
        final Double totalPrice = calculateTotalPrice(paymentPrices);
        final Long paymentMasterId = Optional.ofNullable(paymentPrices.get(0))
            .map(PaymentPrice::getPaymentMasterId)
            .orElse(0L);

        log.info(() -> String.format(
            "IN [POST priceProduct]: payment id: %d - total payment cost: %.2f",
            paymentMasterId,
            totalPrice
        ));

        paymentMasterRepository.updateTotalPrice(paymentMasterId, totalPrice);
    }

    private Double calculateTotalPrice(final List<PaymentPrice> paymentPrices) {
        return paymentPrices
            .stream()
            .map(PaymentPrice::getPrice)
            .mapToDouble(Double::doubleValue)
            .sum();
    }

    private Map<Long, Product> getProductsMap(final List<Long> productIds) {
        return productService.fetchProductsByIds(productIds)
            .stream()
            .collect(Collectors.toMap(Product::getId, identity()));
    }

    private List<PaymentPriceInfo> calculatePaymentPrices(
        final List<PaymentPrice> paymentPrices, final Map<Long, Product> products,
        final Long paymentMasterId, final boolean hasPrivileges
    ) {
        final Integer privilegesModifier = hasPrivileges ? PRIVILEGES_MODIFIER : DEFAULT_MODIFIER;

        return paymentPrices.stream()
            .map(paymentPrice -> {
                final Product product = products.get(paymentPrice.getProductId());
                paymentPrice.calculatePrice(product, privilegesModifier);
                paymentPrice.setPaymentMasterId(paymentMasterId);

                paymentPriceRepository.insertPriceIntoPaymentPrice(
                    paymentPrice,
                    hasPrivileges
                );
                return PaymentPriceInfo.fromPrice(paymentPrice, product.getProductName());
            })
            .collect(toList());
    }
}
