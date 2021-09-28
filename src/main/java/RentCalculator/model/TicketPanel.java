package RentCalculator.model;

import javax.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "ticket_panel")
public class TicketPanel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_price_id", nullable = false)
    private PaymentPrice paymentPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PaymentPrice getPaymentPrice() {
        return paymentPrice;
    }

    public void setPaymentPrice(PaymentPrice paymentPrice) {
        this.paymentPrice = paymentPrice;
    }
}
