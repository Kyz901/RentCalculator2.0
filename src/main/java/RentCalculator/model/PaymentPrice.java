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
    private PaymentMaster paymentMasterId;

    @Column(name="old_meter_readings")
    private Integer oldMeterReadings;

    @Column(name="new_meter_readings")
    private Integer newMeterReadings;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product productId;

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

    public PaymentMaster getPaymentMasterId() {
        return paymentMasterId;
    }

    public void setPaymentMasterId(PaymentMaster paymentMasterId) {
        this.paymentMasterId = paymentMasterId;
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

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
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