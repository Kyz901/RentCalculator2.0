package RentCalculator.model;

import javax.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "payment_master")
public class PaymentMaster implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    private Integer id;

    @Column(name="total_price")
    private double totalPrice;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name="payment_name")
    private String paymentName;

    @Column(name="is_deleted")
    private boolean isDeleted;

    public Integer getId() {
        return id;
    }

    public PaymentMaster setId(Integer id) {
        this.id = id;
        return this;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public PaymentMaster setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public User getUser() {
        return user;
    }

    public PaymentMaster setUser(User user) {
        this.user = user;
        return this;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public PaymentMaster setPaymentName(String paymentName) {
        this.paymentName = paymentName;
        return this;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public PaymentMaster setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
        return this;
    }
}
