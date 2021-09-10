package RentCalculator.repository;

import RentCalculator.model.PaymentMaster;
import RentCalculator.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMasterRepository extends JpaRepository<PaymentMaster,Integer> {
}
