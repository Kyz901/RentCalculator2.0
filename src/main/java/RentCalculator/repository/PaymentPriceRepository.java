package RentCalculator.repository;

import RentCalculator.model.PaymentMaster;
import RentCalculator.model.PaymentPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentPriceRepository extends JpaRepository<PaymentPrice,Integer> {
}
