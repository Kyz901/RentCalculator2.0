package RentCalculator.pricing.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PaymentPrice {

    private Integer id;
    private Integer paymentMasterId;
    private Integer oldMeterReadings;
    private Integer newMeterReadings;
    private Integer productId;
    private Double price;
    private boolean isDeleted;

    public Double calculatePrice(final Product product) {
        return (this.newMeterReadings - this.oldMeterReadings) * product.getSinglePrice();
    }
}
