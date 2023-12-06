package rentCalculator.email.model;

import lombok.Data;
import lombok.experimental.Accessors;
import rentCalculator.pricing.model.PaymentMaster;

@Data
@Accessors(chain = true)
public class PaymentMasterInfo {

    private Long id;
    private Double totalPrice;
    private UserInfo user;
    private String paymentName;

    public static PaymentMasterInfo fromPaymentMasterInfo(final PaymentMaster paymentMaster, final UserInfo userInfo) {
        return new PaymentMasterInfo()
            .setId(paymentMaster.getId())
            .setTotalPrice(paymentMaster.getTotalPrice())
            .setUser(userInfo)
            .setPaymentName(paymentMaster.getPaymentName());
    }

}
