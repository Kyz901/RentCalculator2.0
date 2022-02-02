package RentCalculator.pricing.service;

import RentCalculator.authorization.model.CurrentUser;

import RentCalculator.pricing.model.PaymentMaster;
import RentCalculator.pricing.model.PaymentPrice;
import RentCalculator.pricing.model.Product;
import RentCalculator.pricing.repository.PaymentMasterRepository;
import RentCalculator.pricing.repository.PaymentPriceRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

    public PaymentMaster getPaymentMasterById(final Integer paymentMasterId) {
        return paymentMasterRepository.fetchPaymentMasterById(paymentMasterId);
    }

    public List<PaymentMaster> getAllPaymentMasterForCurrentUser() {
        return paymentMasterRepository.fetchPaymentMasterForCurrentUser(CurrentUser.get().getId());
    }

    public PaymentMaster createPaymentMaster(final String paymentName) {
        paymentMasterRepository.createPaymentMaster(CurrentUser.get().getId(), paymentName);
        return paymentMasterRepository.fetchPaymentMasterByName(paymentName);
    }

    /**
     * Pricing by formula -> Price = (NewMeterReadings - OldMeterReadings) * ProductSinglePrice(m^3)
     *
     * @param paymentPriceList
     * @param paymentMasterId
     * @return List of PaymentPrices
     */
    public List<PaymentPrice> priceProduct(final List<PaymentPrice> paymentPriceList, Integer paymentMasterId) {
        for (PaymentPrice paymentPrice : paymentPriceList) {
            Integer productId = paymentPrice.getProductId();
            Product product = productService.getProductById(productId);
            Double price = calculatePrice(paymentPrice, product);
            paymentPriceRepository.insertPriceIntoPaymentPrice(
                paymentMasterId,
                paymentPrice,
                price
            );
        }

        return paymentPriceRepository.fetchAllPricesByPaymentMasterId(paymentMasterId);
    }

    private Double calculatePrice(final PaymentPrice paymentPrice, final Product product) {
        return (paymentPrice.getNewMeterReadings() - paymentPrice.getOldMeterReadings()) * product.getSinglePrice();
    }

    public void updateTotalPriceInPaymentMaster(final Integer paymentMasterId) {

        List<PaymentPrice> paymentPriceList = paymentPriceRepository.fetchAllPricesByPaymentMasterId(paymentMasterId);
        Double totalPrice = calculateTotalPrice(paymentPriceList);
        paymentMasterRepository.updateTotalPrice(paymentMasterId, totalPrice);
    }

    private Double calculateTotalPrice(final List<PaymentPrice> paymentPrices) {
        return paymentPrices
            .stream()
            .map(PaymentPrice::getPrice)
            .mapToDouble(Double::doubleValue)
            .sum();
    }

    public List<PaymentPrice> getPaymentPrices(final Integer paymentMasterId) {
        return paymentPriceRepository.fetchAllPricesByPaymentMasterId(paymentMasterId);
    }
}
