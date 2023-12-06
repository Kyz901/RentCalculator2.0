package rentCalculator.broker.model;

import lombok.Data;
import lombok.experimental.Accessors;
import rentCalculator.email.model.PaymentMasterInfo;
import rentCalculator.email.model.PaymentPriceInfo;

import java.util.List;

@Data
@Accessors(chain = true)
public class PricingEvent {

    private Long quoteId;
    private List<PaymentPriceInfo> prices;
    private PaymentMasterInfo paymentMaster;

    public static PricingEvent fromPricing(
        final PaymentMasterInfo paymentMaster, final List<PaymentPriceInfo> prices
    ) {
        return new PricingEvent()
            .setQuoteId(paymentMaster.getId())
            .setPaymentMaster(paymentMaster)
            .setPrices(prices);
    }
}
