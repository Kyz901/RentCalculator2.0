package RentCalculator.service;

import RentCalculator.dto.PaymentMasterDTO;
import RentCalculator.dto.PaymentPriceDTO;
import RentCalculator.model.PaymentMaster;
import RentCalculator.model.PaymentPrice;
import RentCalculator.model.Product;

import java.util.List;

public interface PricingService {

    List<Product> getAllProducts();
    Product getProductById(Integer productId);
    PaymentMaster createPaymentMaster(String paymentName);
    PaymentMaster getPaymentMasterById(Integer paymentMasterId);
    List<PaymentMaster> getAllPaymentMasterForCurrentUser();
    List<PaymentPrice> priceProduct(List<PaymentPriceDTO> paymentPriceList, Integer paymentMasterId);
    void updateTotalPriceInPaymentMaster(Integer paymentMasterId);
    List<PaymentPrice> getPaymentPrices(Integer paymentMasterId);
}
