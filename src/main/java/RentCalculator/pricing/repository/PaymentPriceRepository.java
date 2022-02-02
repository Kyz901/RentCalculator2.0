package RentCalculator.pricing.repository;


import RentCalculator.pricing.model.PaymentPrice;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class PaymentPriceRepository {

    private final NamedParameterJdbcOperations operations;

    public PaymentPriceRepository(
        final NamedParameterJdbcOperations operations
    ) {
        this.operations = operations;
    }

    public List<PaymentPrice> fetchAllPricesByPaymentMasterId(final Integer paymentMasterId) {
        String sql = "SELECT pp.id, pp.payment_master_id, pp.old_meter_readings, pp.new_meter_readings, pp.product_id, pp.price, pp.is_deleted"
            + " FROM rentcalculator.payment_price pp"
            + " WHERE pp.is_deleted = FALSE"
            + " AND pp.payment_master_id = :paymentMasterId";
        final MapSqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("paymentMasterId", paymentMasterId);

        return operations.query(sql, parameters, (rs, rowNum) -> new PaymentPrice()
            .setId(rs.getInt("id"))
            .setPaymentMasterId(rs.getInt("payment_master_id"))
            .setOldMeterReadings(rs.getInt("old_meter_readings"))
            .setNewMeterReadings(rs.getInt("new_meter_readings"))
            .setProductId(rs.getInt("product_id"))
            .setPrice(rs.getDouble("price"))
            .setDeleted(rs.getBoolean("is_deleted"))
        );
    }

    public boolean insertPriceIntoPaymentPrice(
        final Integer paymentMasterId,
        final PaymentPrice paymentPrice,
        final Double price
    ) {
        String sql = "INSERT INTO rentcalculator.payment_price"
            + " (id, payment_master_id, old_meter_readings, new_meter_readings,"
            + " product_id, price, is_deleted)"
            + " VALUES ( NULL, :paymentMasterId, :oldMeterReadings, :newMeterReadings,"
            + " :productId, :price, 0)";

        final MapSqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("paymentMasterId", paymentMasterId)
            .addValue("oldMeterReadings", paymentPrice.getOldMeterReadings())
            .addValue("newMeterReadings", paymentPrice.getNewMeterReadings())
            .addValue("productId", paymentPrice.getProductId())
            .addValue("price", price);

        operations.update(sql, parameters);
        return true;
    }
}
