package RentCalculator.service.implementation;

import RentCalculator.model.CurrentUser;
import RentCalculator.model.PaymentMaster;
import RentCalculator.model.PaymentPrice;
import RentCalculator.model.Product;

import RentCalculator.dto.PaymentPriceDTO;

import RentCalculator.repository.PaymentMasterRepository;
import RentCalculator.repository.PaymentPriceRepository;
import RentCalculator.repository.ProductRepository;

import RentCalculator.service.PricingService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PricingServiceImpl implements PricingService {

    private final ProductRepository productRepository;
    private final PaymentMasterRepository paymentMasterRepository;
    private final PaymentPriceRepository paymentPriceRepository;

    public PricingServiceImpl(ProductRepository productRepository, PaymentMasterRepository paymentMasterRepository, PaymentPriceRepository paymentPriceRepository) {
        this.productRepository = productRepository;
        this.paymentMasterRepository = paymentMasterRepository;
        this.paymentPriceRepository = paymentPriceRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAllProducts();
    }

    @Override
    public Product getProductById(Integer productId) {
        return productRepository.findProductById(productId);
    }

    @Override
    public PaymentMaster getPaymentMasterById(Integer paymentMasterId) {
       return paymentMasterRepository.findPaymentMasterById(paymentMasterId);
    }

    @Override
    public List<PaymentMaster> getAllPaymentMasterForCurrentUser() {
        return paymentMasterRepository.findPaymentMasterForCurrentUser(CurrentUser.get().getId());
    }

    @Override
    public PaymentMaster createPaymentMaster(String paymentName) {
        paymentMasterRepository.createPaymentMaster(CurrentUser.get().getId(), paymentName);
        return paymentMasterRepository.findPaymentMasterByName(paymentName);
    }

    /** Pricing by formula -> price = (newMeterReadings - oldMeterReadings) * product.singlePrice(m^3)
     *
     * @param paymentPriceList
     * @param paymentMasterId
     * @return List of PaymentPrices
     */
    @Override
    public List<PaymentPrice> priceProduct(List<PaymentPriceDTO> paymentPriceList, Integer paymentMasterId) {
        for (PaymentPriceDTO paymentPrice : paymentPriceList) {
            Product product = productRepository.findProductById(paymentPrice.getProductId());
            Integer newMeterReadings = paymentPrice.getNewMeterReadings();
            Integer oldMeterReadings = paymentPrice.getOldMeterReadings();
            double price = (newMeterReadings - oldMeterReadings) * product.getSinglePrice();
            paymentPriceRepository.insertPriceIntoPaymentPrice(
                    paymentMasterId,
                    oldMeterReadings,
                    newMeterReadings,
                    paymentPrice.getProductId(),
                    price
            );
        }
        return paymentPriceRepository.findAllPricesByPaymentMasterId(paymentMasterId);
    }

    @Override
    public void updateTotalPriceInPaymentMaster(Integer paymentMasterId) {
        Double totalPrice = 0.00;
        List<PaymentPrice> paymentPriceList = paymentPriceRepository.findAllPricesByPaymentMasterId(paymentMasterId);
        for (PaymentPrice paymentPrice : paymentPriceList) {
            totalPrice += paymentPrice.getPrice();
        }
        paymentMasterRepository.updateTotalPrice(paymentMasterId, totalPrice);
    }

    @Override
    public List<PaymentPrice> getPaymentPrices(Integer paymentMasterId){
        return paymentPriceRepository.findAll().stream()
                .filter(pp -> !pp.isDeleted()
                        && pp.getPaymentMaster().getId().equals(paymentMasterId))
                .collect(Collectors.toList());
    }
}
