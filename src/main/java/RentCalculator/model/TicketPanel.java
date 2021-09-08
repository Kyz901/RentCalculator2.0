package RentCalculator.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "ticket_panel")
public class TicketPanel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_price_id", nullable = false)
    private PaymentPrice paymentPriceId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PaymentPrice getPaymentPriceId() {
        return paymentPriceId;
    }

    public void setPaymentPriceId(PaymentPrice paymentPriceId) {
        this.paymentPriceId = paymentPriceId;
    }
}
