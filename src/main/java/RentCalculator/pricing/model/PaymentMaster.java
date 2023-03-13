package RentCalculator.pricing.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PaymentMaster {

    private Long id;
    private Double totalPrice;
    private Integer userId;
    private String paymentName;
    private boolean isDeleted;

}
