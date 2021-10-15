package RentCalculator.dto;

public class PaymentMasterDTO {
    private Integer id;
    private Double totalPrice;
    private Integer userId;
    private String paymentName;
    private boolean isDeleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "PaymentMasterDTO{" +
                "id=" + id +
                ", totalPrice=" + totalPrice +
                ", userId=" + userId +
                ", paymentName='" + paymentName + '\'' +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
