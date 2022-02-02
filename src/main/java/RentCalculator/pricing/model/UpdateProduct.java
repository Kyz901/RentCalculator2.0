package RentCalculator.pricing.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UpdateProduct {

    String productName;
    Double singlePrice;

}
