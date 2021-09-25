package RentCalculator.repository;

import RentCalculator.model.PaymentMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PaymentMasterRepository extends JpaRepository<PaymentMaster,Integer> {

    @Query(value = "SELECT pm.id, pm.total_price, pm.user_id, pm.payment_name, pm.is_deleted " +
                   "FROM rentcalculator.payment_master pm " +
                   "WHERE pm.is_deleted = FALSE " +
                   "AND pm.user_id = :userId ",
            nativeQuery = true)
    List<PaymentMaster> findPaymentMasterForCurrentUser(@Param("userId") Integer userId);

    @Query(value = "SELECT pm.id, pm.total_price, pm.user_id, pm.payment_name, pm.is_deleted " +
                   "FROM rentcalculator.payment_master pm " +
                   "WHERE pm.is_deleted = FALSE " +
                   "AND pm.payment_name = :paymentName ",
            nativeQuery = true)
    PaymentMaster findPaymentMasterByName(@Param("paymentName") String paymentName);

    @Query(value = "SELECT pm.id, pm.total_price, pm.user_id, pm.payment_name, pm.is_deleted " +
                   "FROM rentcalculator.payment_master pm " +
                   "WHERE pm.is_deleted = FALSE " +
                   "AND pm.id = :paymentMasterId " +
                   "LIMIT 1",
            nativeQuery = true)
    PaymentMaster findPaymentMasterById(@Param("paymentMasterId") Integer paymentMasterId);

    @Transactional
    @Query(value = "INSERT INTO rentcalculator.payment_master VALUES ( NULL, 0.0, :userId, :paymentName, 0)",
            nativeQuery = true)
    void createPaymentMaster(@Param("userId") Integer userId,
                             @Param("paymentName") String paymentName);

    @Transactional
    @Query(value = "UPDATE PaymentMaster pm " +
                   "SET pm.totalPrice = :totalPrice " +
                   "WHERE pm.id = :paymentMasterId")
    Integer updateTotalPrice(@Param("paymentMasterId") Integer paymentMasterId,
                             @Param("totalPrice") Double totalPrice);
}
