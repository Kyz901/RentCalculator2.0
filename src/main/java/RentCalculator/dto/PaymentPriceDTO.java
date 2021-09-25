package RentCalculator.dto;

import RentCalculator.model.PaymentMaster;
import RentCalculator.model.Product;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PaymentPriceDTO {

    private Integer id;
    private Integer paymentMasterId;
    private Integer oldMeterReadings;
    private Integer newMeterReadings;
    private Integer productId;
    private Double price;
    private boolean isDeleted;
}
