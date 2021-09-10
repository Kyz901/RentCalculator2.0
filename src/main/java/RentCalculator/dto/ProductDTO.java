package RentCalculator.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductDTO {

    private Integer id;
    private String productName;
    private Double singlePrice;
    private boolean isDeleted;

}
