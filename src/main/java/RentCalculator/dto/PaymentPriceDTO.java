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
    private PaymentMaster paymentMaster;
    private Integer oldMeterReadings;
    private Integer newMeterReadings;
    private Product product;
    private Double price;
    private boolean isDeleted;
}
