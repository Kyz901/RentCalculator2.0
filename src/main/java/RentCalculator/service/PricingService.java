package RentCalculator.service;

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
}
