package RentCalculator.pricing.api;

import RentCalculator.pricing.model.PaymentMaster;
import RentCalculator.pricing.model.PaymentPrice;

import RentCalculator.pricing.service.PricingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class PricingController {

    private final PricingService pricingService;

    public PricingController(
        final PricingService pricingService
    ) {
        this.pricingService = pricingService;
    }

    @GetMapping("/payment-master/{paymentMasterId}")
    public PaymentMaster getPaymentMasterById(@PathVariable Integer paymentMasterId) {
        return pricingService.getPaymentMasterById(paymentMasterId);
    }

    @GetMapping("/payment-master")
    public List<PaymentMaster> getPaymentsMasterForCurrentUser() {
        return pricingService.getAllPaymentMasterForCurrentUser();
    }

    @GetMapping("/payment-master/{paymentMasterId}/pricing")
    public List<PaymentPrice> getPaymentPrices(@PathVariable Integer paymentMasterId) {
        return pricingService.getPaymentPrices(paymentMasterId);
    }

    @PostMapping("/payment-master")
    public PaymentMaster createPaymentMaster(@RequestParam String paymentName) {
        return pricingService.createPaymentMaster(paymentName);
    }

    @PostMapping("/payment-master/{paymentMasterId}/pricing")
    public List<PaymentPrice> priceProduct(
        @RequestBody List<PaymentPrice> paymentPrices,
        @PathVariable Integer paymentMasterId
    ) {
        List<PaymentPrice> paymentPriceList = pricingService.priceProduct(paymentPrices, paymentMasterId);
        pricingService.updateTotalPriceInPaymentMaster(paymentMasterId);
        return paymentPriceList;
    }
}
