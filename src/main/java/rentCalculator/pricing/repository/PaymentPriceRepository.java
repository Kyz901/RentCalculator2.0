package rentCalculator.pricing.repository;

import rentCalculator.pricing.model.PaymentPrice;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PaymentPriceRepository {

    private final NamedParameterJdbcOperations operations;

    public PaymentPriceRepository(
        final NamedParameterJdbcOperations operations
    ) {
        this.operations = operations;
    }

    public List<PaymentPrice> fetchAllPricesByPaymentMasterId(final Long paymentMasterId) {
        final String sql = "SELECT pp.id, pp.payment_master_id, pp.old_meter_readings, pp.new_meter_readings, pp.product_id, pp.price, pp.is_deleted"
            + " FROM rentcalculator.payment_price pp"
            + " WHERE pp.is_deleted = FALSE"
            + " AND pp.payment_master_id = :paymentMasterId";
        final MapSqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("paymentMasterId", paymentMasterId);

        return operations.query(sql, parameters, (rs, rowNum) -> new PaymentPrice()
            .setId(rs.getLong("id"))
            .setPaymentMasterId(rs.getLong("payment_master_id"))
            .setOldMeterReadings(rs.getInt("old_meter_readings"))
            .setNewMeterReadings(rs.getInt("new_meter_readings"))
            .setProductId(rs.getLong("product_id"))
            .setPrice(rs.getDouble("price"))
            .setDeleted(rs.getBoolean("is_deleted"))
        );
    }

    public void insertPriceIntoPaymentPrice(
        final PaymentPrice paymentPrice,
        final boolean privilegesApplied
    ) {
        final String sql = "INSERT INTO rentcalculator.payment_price"
            + " (id, payment_master_id, old_meter_readings, new_meter_readings,"
            + " product_id, price, is_privileges_applied, is_deleted)"
            + " VALUES ( NULL, :paymentMasterId, :oldMeterReadings, :newMeterReadings,"
            + " :productId, :price, :privilegesApplied, 0)";

        final MapSqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("paymentMasterId", paymentPrice.getPaymentMasterId())
            .addValue("oldMeterReadings", paymentPrice.getOldMeterReadings())
            .addValue("newMeterReadings", paymentPrice.getNewMeterReadings())
            .addValue("productId", paymentPrice.getProductId())
            .addValue("privilegesApplied", privilegesApplied)
            .addValue("price", paymentPrice.getPrice());

        operations.update(sql, parameters);
    }
}
