package RentCalculator.dto;

public class PaymentPriceDTO {

    private Integer id;
    private Integer paymentMasterId;
    private Integer oldMeterReadings;
    private Integer newMeterReadings;
    private Integer productId;
    private Double price;
    private boolean isDeleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPaymentMasterId() {
        return paymentMasterId;
    }

    public void setPaymentMasterId(Integer paymentMasterId) {
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

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "PaymentPriceDTO{" +
                "id=" + id +
                ", paymentMasterId=" + paymentMasterId +
                ", oldMeterReadings=" + oldMeterReadings +
                ", newMeterReadings=" + newMeterReadings +
                ", productId=" + productId +
                ", price=" + price +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
