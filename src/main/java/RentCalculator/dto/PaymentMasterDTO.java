package RentCalculator.dto;

import RentCalculator.model.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PaymentMasterDTO {
    private Integer id;
    private Double totalPrice;
    private User user;
    private String paymentName;
    private boolean isDeleted;
}
