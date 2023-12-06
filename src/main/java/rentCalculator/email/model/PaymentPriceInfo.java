package rentCalculator.email.model;

import lombok.Data;
import lombok.experimental.Accessors;
import rentCalculator.pricing.model.PaymentPrice;

@Data
@Accessors(chain = true)
public class PaymentPriceInfo {

    private Integer oldMeterReadings;
    private Integer newMeterReadings;
    private String productName;
    private Double price;

    public static PaymentPriceInfo fromPrice(final PaymentPrice paymentPrice, final String productName) {
        return new PaymentPriceInfo()
            .setPrice(paymentPrice.getPrice())
            .setNewMeterReadings(paymentPrice.getNewMeterReadings())
            .setOldMeterReadings(paymentPrice.getOldMeterReadings())
            .setProductName(productName);
    }

}
