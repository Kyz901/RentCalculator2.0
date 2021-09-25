package RentCalculator.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "product_service")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    private Integer id;

    @Column(name="product_name")
    private String productName;

    @Column(name="single_price")
    private Double singlePrice;

    @Column(name="is_deleted")
    private boolean isDeleted;

    public Integer getId() {
        return id;
    }

    public Product setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public Product setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public Double getSinglePrice() {
        return singlePrice;
    }

    public Product setSinglePrice(Double singlePrice) {
        this.singlePrice = singlePrice;
        return this;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public Product setDeleted(boolean deleted) {
        isDeleted = deleted;
        return this;
    }
}
