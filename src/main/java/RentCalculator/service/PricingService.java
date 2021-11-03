package RentCalculator.service;

import RentCalculator.dto.PaymentPriceDTO;

import RentCalculator.model.PaymentMaster;
import RentCalculator.model.PaymentPrice;

import java.util.List;

public interface PricingService {

    PaymentMaster createPaymentMaster(String paymentName);
    PaymentMaster getPaymentMasterById(Integer paymentMasterId);
    List<PaymentMaster> getAllPaymentMasterForCurrentUser();
    List<PaymentPrice> priceProduct(List<PaymentPriceDTO> paymentPriceList, Integer paymentMasterId);
    void updateTotalPriceInPaymentMaster(Integer paymentMasterId);
    List<PaymentPrice> getPaymentPrices(Integer paymentMasterId);
}
