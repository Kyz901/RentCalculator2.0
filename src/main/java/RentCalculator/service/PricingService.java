package RentCalculator.service;

import RentCalculator.dto.PaymentMasterDTO;
import RentCalculator.model.PaymentMaster;
import RentCalculator.model.PaymentPrice;
import RentCalculator.model.Product;

import java.util.List;

public interface PricingService {

    List<Product> getAllProducts();
    Product getProductById(Integer productId);
    void createPaymentMaster(PaymentMaster paymentMaster);
    PaymentMaster getPaymentMasterById(Integer paymentMasterId);
    List<PaymentMaster> getAllPaymentMaster();
    List<PaymentPrice> priceProduct(List<PaymentPrice> paymentPriceList, Integer paymentMasterId);
    void updateTotalPriceInPaymentMaster(Integer paymentMasterId);
    List<PaymentPrice> getPaymentPrices(Integer paymentMasterId);
}
