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

@Service
@Slf4j
public class PricingService {

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
    public List<PaymentPrice> priceProduct(final List<PaymentPrice> paymentPrices, final Long paymentMasterId) {
        paymentPrices.forEach(paymentPrice -> {
            final Product product = productService.getProductById(paymentPrice.getProductId());
            final Double price = paymentPrice.calculatePrice(product);
            paymentPriceRepository.insertPriceIntoPaymentPrice(
                paymentMasterId,
                paymentPrice,
                price
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
