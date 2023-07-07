package RentCalculator.pricing.service;

import RentCalculator.pricing.model.PaymentMaster;
import RentCalculator.pricing.model.PaymentPrice;
import RentCalculator.pricing.model.Product;
import RentCalculator.pricing.repository.PaymentMasterRepository;
import RentCalculator.pricing.repository.PaymentPriceRepository;

import RentCalculator.security.model.AuthenticatedAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PricingService {

    public final static Integer DEFAULT_MODIFIER = 1;
    public final static Integer PRIVILEGES_MODIFIER = 2;

    private final ProductService productService;
    private final PaymentMasterRepository paymentMasterRepository;
    private final PaymentPriceRepository paymentPriceRepository;

    public PricingService(
        final ProductService productService,
        final PaymentMasterRepository paymentMasterRepository,
        final PaymentPriceRepository paymentPriceRepository
    ) {
        this.productService = productService;
        this.paymentMasterRepository = paymentMasterRepository;
        this.paymentPriceRepository = paymentPriceRepository;
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

        log.info("IN [POST createPaymentMaster]: successfully created payment: {} - initiated user: {}",
            paymentMasterId,
            principal.getUsername()
        );

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
        final Integer privilegesModifier = hasPrivileges ? PRIVILEGES_MODIFIER : DEFAULT_MODIFIER;

        final List<Long> productIds = paymentPrices.stream()
            .map(PaymentPrice::getProductId)
            .collect(Collectors.toList());
        final Map<Long, Product> products = productService.fetchProductsByIds(productIds)
            .stream()
            .collect(Collectors.toMap(Product::getId, Function.identity()));

        paymentPrices.forEach(paymentPrice -> {
            final Product product = products.get(paymentPrice.getProductId());
            final Double price = paymentPrice.calculatePrice(product, privilegesModifier);

            paymentPriceRepository.insertPriceIntoPaymentPrice(
                paymentMasterId,
                paymentPrice,
                price,
                hasPrivileges
            );
        });

        return paymentPriceRepository.fetchAllPricesByPaymentMasterId(paymentMasterId);
    }

    public void updateTotalPriceInPaymentMaster(final Long paymentMasterId) {
        final List<PaymentPrice> paymentPrices = paymentPriceRepository.fetchAllPricesByPaymentMasterId(paymentMasterId);
        final Double totalPrice = calculateTotalPrice(paymentPrices);

        log.info(
            "IN [POST priceProduct]: payment id: {} - total payment cost: {}",
            paymentMasterId,
            totalPrice
        );

        paymentMasterRepository.updateTotalPrice(paymentMasterId, totalPrice);
    }

    private Double calculateTotalPrice(final List<PaymentPrice> paymentPrices) {
        return paymentPrices
            .stream()
            .map(PaymentPrice::getPrice)
            .mapToDouble(Double::doubleValue)
            .sum();
    }
}
