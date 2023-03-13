package RentCalculator.pricing.repository;

import RentCalculator.pricing.model.PaymentMaster;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PaymentMasterRepository {

    private final NamedParameterJdbcOperations operations;

    public PaymentMasterRepository(
        final NamedParameterJdbcOperations operations
    ) {
        this.operations = operations;
    }

    public List<PaymentMaster> fetchPaymentMasterForCurrentUser(final Long userId) {
        final String sql = "SELECT pm.id, pm.total_price, pm.user_id, pm.payment_name, pm.is_deleted"
            + " FROM rentcalculator.payment_master pm"
            + " WHERE pm.is_deleted = FALSE"
            + " AND pm.user_id = :userId";
        final MapSqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("userId", userId);

        return operations.query(sql, parameters, (rs, rowNum) -> new PaymentMaster()
            .setId(rs.getLong("id"))
            .setTotalPrice(rs.getDouble("total_price"))
            .setUserId(rs.getInt("user_id"))
            .setPaymentName(rs.getString("payment_name"))
            .setDeleted(rs.getBoolean("is_deleted"))
        );
    }

    public PaymentMaster fetchPaymentMasterById(final Long paymentMasterId) {
        final String sql = "SELECT pm.id, pm.total_price, pm.user_id, pm.payment_name, pm.is_deleted"
            + " FROM rentcalculator.payment_master pm"
            + " WHERE pm.is_deleted = FALSE"
            + " AND pm.id = :paymentMasterId";
        final MapSqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("paymentMasterId", paymentMasterId);

        return operations.queryForObject(sql, parameters, (rs, rowNum) -> new PaymentMaster()
            .setId(rs.getLong("id"))
            .setTotalPrice(rs.getDouble("total_price"))
            .setUserId(rs.getInt("user_id"))
            .setPaymentName(rs.getString("payment_name"))
            .setDeleted(rs.getBoolean("is_deleted"))
        );
    }

    public Long createPaymentMaster(
        final Long userId,
        final String paymentName
    ) {
        String sql = "INSERT INTO rentcalculator.payment_master(id, total_price, user_id, payment_name, is_deleted)"
            + " VALUES (NULL, 0.0, :userId, :paymentName, 0)";
        final MapSqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("userId", userId)
            .addValue("paymentName", paymentName);

        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        operations.update(sql, parameters, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public void updateTotalPrice(
        final Long paymentMasterId,
        final Double totalPrice
    ) {
        String sql = "UPDATE rentcalculator.payment_master pm"
            + " SET pm.total_price = :totalPrice"
            + " WHERE pm.id = :paymentMasterId";
        final MapSqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("totalPrice", totalPrice)
            .addValue("paymentMasterId", paymentMasterId);

        operations.update(sql, parameters);
    }
}
