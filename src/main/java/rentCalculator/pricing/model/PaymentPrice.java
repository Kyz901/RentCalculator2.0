package rentCalculator.pricing.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PaymentPrice {

    private Long id;
    private Long paymentMasterId;
    private Integer oldMeterReadings;
    private Integer newMeterReadings;
    private Long productId;
    private Double price;
    private boolean isDeleted;

    public Double calculatePrice(final Product product, final Integer modifier) {
        this.price = (this.newMeterReadings - this.oldMeterReadings) * product.getSinglePrice() / modifier;
        return this.price;
    }
}
