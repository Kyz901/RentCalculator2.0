package RentCalculator.repository;

import RentCalculator.model.PaymentPrice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PaymentPriceRepository extends JpaRepository<PaymentPrice,Integer> {

    @Query(value = "SELECT pp.id, pp.payment_master_id, pp.old_meter_readings, pp.new_meter_readings, pp.product_id, pp.price, pp.is_deleted " +
                   "FROM rentcalculator.payment_price pp " +
                   "WHERE pp.is_deleted = FALSE " +
                   "AND pp.payment_master_id = :paymentMasterId ",
                   nativeQuery = true)
    List<PaymentPrice> fetchAllPricesByPaymentMasterId(@Param("paymentMasterId") Integer paymentMasterId);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO rentcalculator.payment_price (id, payment_master_id, old_meter_readings, new_meter_readings, product_id, price, is_deleted) " +
                   "VALUES ( NULL, :paymentMasterId, :oldMeterReadings, :newMeterReadings, :productId, :price, 0)",
            nativeQuery = true)
    void insertPriceIntoPaymentPrice(@Param("paymentMasterId") Integer paymentMasterId,
                                     @Param("oldMeterReadings") double oldMeterReadings,
                                     @Param("newMeterReadings") double newMeterReadings,
                                     @Param("productId") Integer productId,
                                     @Param("price") double price);
}
