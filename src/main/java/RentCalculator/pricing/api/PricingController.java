package RentCalculator.pricing.api;

import RentCalculator.pricing.model.PaymentMaster;
import RentCalculator.pricing.model.PaymentPrice;

import RentCalculator.pricing.service.PricingService;
import RentCalculator.security.model.AuthenticatedAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/pricing")
public class PricingController {

    private final PricingService pricingService;

    public PricingController(
        final PricingService pricingService
    ) {
        this.pricingService = pricingService;
    }

    @GetMapping("/payment-master/{paymentMasterId}")
    public PaymentMaster getPaymentMasterById(
        @PathVariable final Long paymentMasterId
    ) {
        return pricingService.getPaymentMasterById(paymentMasterId);
    }

    @GetMapping("/payment-master")
    public List<PaymentMaster> getPaymentsMaster(
        @AuthenticationPrincipal final AuthenticatedAccount principal
    ) {
        return pricingService.getAllPaymentMasterForUser(principal);
    }

    @GetMapping("/payment-master/{paymentMasterId}/pricing")
    public List<PaymentPrice> getPaymentPrices(
        @PathVariable final Long paymentMasterId
    ) {
        return pricingService.getPaymentPrices(paymentMasterId);
    }

    @PostMapping("/payment-master")
    public PaymentMaster createPaymentMaster(
        @RequestParam final String paymentName,
        @AuthenticationPrincipal final AuthenticatedAccount principal
    ) {
        log.info("IN [POST createPaymentMaster]: <payment creating> - payment name: {} - initiated user: {}",
            paymentName,
            principal.getUsername()
        );

        return pricingService.createPaymentMaster(paymentName, principal);
    }

    @PostMapping("/payment-master/{paymentMasterId}/pricing")
    public List<PaymentPrice> priceProduct(
        @RequestBody final List<PaymentPrice> paymentPrices,
        @PathVariable final Long paymentMasterId,
        @AuthenticationPrincipal final AuthenticatedAccount principal
    ) {
        log.info("IN [POST priceProduct]: <start pricing> - paymentId: {} - initiated user: {}",
            paymentMasterId,
            principal.getUsername()
        );

        final List<PaymentPrice> paymentPriceList = pricingService.priceProduct(paymentPrices, paymentMasterId, principal);
        pricingService.updateTotalPriceInPaymentMaster(paymentMasterId);

        log.info(
            "IN [POST priceProduct]: <end pricing> - paymentId: {} - initiated user: {} - total prices inserted: {}",
            paymentMasterId,
            principal.getUsername(),
            paymentPriceList.size()
        );

        return paymentPriceList;
    }
}
