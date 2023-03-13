package RentCalculator.pricing.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Product {

    private Long id;
    private String productName;
    private Double singlePrice;
    private boolean isDeleted;

}
