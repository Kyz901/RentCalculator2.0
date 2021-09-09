package RentCalculator.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "payment_price")
public class PaymentPrice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="payment_master_id", nullable = false)
    private PaymentMaster paymentMaster;

    @Column(name="old_meter_readings")
    private Integer oldMeterReadings;

    @Column(name="new_meter_readings")
    private Integer newMeterReadings;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column
    private Double price;

    @Column(name="is_deleted")
    private Boolean isDeleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PaymentMaster getPaymentMaster() {
        return paymentMaster;
    }

    public void setPaymentMaster(PaymentMaster paymentMaster) {
        this.paymentMaster = paymentMaster;
    }

    public Integer getOldMeterReadings() {
        return oldMeterReadings;
    }

    public void setOldMeterReadings(Integer oldMeterReadings) {
        this.oldMeterReadings = oldMeterReadings;
    }

    public Integer getNewMeterReadings() {
        return newMeterReadings;
    }

    public void setNewMeterReadings(Integer newMeterReadings) {
        this.newMeterReadings = newMeterReadings;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
