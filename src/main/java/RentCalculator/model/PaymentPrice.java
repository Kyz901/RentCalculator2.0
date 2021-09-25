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
    private boolean isDeleted;

    public Integer getId() {
        return id;
    }

    public PaymentPrice setId(Integer id) {
        this.id = id;
        return this;
    }

    public PaymentMaster getPaymentMaster() {
        return paymentMaster;
    }

    public PaymentPrice setPaymentMaster(PaymentMaster paymentMaster) {
        this.paymentMaster = paymentMaster;
        return this;
    }

    public Integer getOldMeterReadings() {
        return oldMeterReadings;
    }

    public PaymentPrice setOldMeterReadings(Integer oldMeterReadings) {
        this.oldMeterReadings = oldMeterReadings;
        return this;
    }

    public Integer getNewMeterReadings() {
        return newMeterReadings;
    }

    public PaymentPrice setNewMeterReadings(Integer newMeterReadings) {
        this.newMeterReadings = newMeterReadings;
        return this;
    }

    public Product getProduct() {
        return product;
    }

    public PaymentPrice setProduct(Product product) {
        this.product = product;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public PaymentPrice setPrice(Double price) {
        this.price = price;
        return this;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public PaymentPrice setDeleted(boolean deleted) {
        isDeleted = deleted;
        return this;
    }
}
