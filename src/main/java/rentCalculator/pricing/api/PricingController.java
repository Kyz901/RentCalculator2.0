package rentCalculator.pricing.api;

import com.kuzmin.logger.MultiLogger;
import rentCalculator.pricing.model.PaymentMaster;
import rentCalculator.pricing.model.PaymentPrice;

import rentCalculator.pricing.service.PricingService;
import rentCalculator.security.model.AuthenticatedAccount;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pricing")
public class PricingController {

    private final PricingService pricingService;

    private final MultiLogger log;

    public PricingController(
        final PricingService pricingService,
        final MultiLogger multiLogger
    ) {
        this.pricingService = pricingService;
        this.log = multiLogger;
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
        log.info(() -> String.format("IN [POST createPaymentMaster]: <payment creating> - payment name: %s - initiated user: %s",
            paymentName,
            principal.getUsername()
        ));

        return pricingService.createPaymentMaster(paymentName, principal);
    }

    @PostMapping("/payment-master/{paymentMasterId}/pricing")
    public List<PaymentPrice> priceProduct(
        @RequestBody final List<PaymentPrice> paymentPrices,
        @PathVariable final Long paymentMasterId,
        @AuthenticationPrincipal final AuthenticatedAccount principal
    ) {
        log.info(() -> String.format("IN [POST priceProduct]: <start pricing> - paymentId: %d - initiated user: %s",
            paymentMasterId,
            principal.getUsername()
        ));
        final List<PaymentPrice> paymentPriceList = pricingService.priceProduct(paymentPrices, paymentMasterId, principal);

        log.info(() -> String.format(
            "IN [POST priceProduct]: <end pricing> - paymentId: %d - initiated user: %s - total prices inserted: %d",
            paymentMasterId,
            principal.getUsername(),
            paymentPriceList.size()
        ));

        return paymentPriceList;
    }
}
