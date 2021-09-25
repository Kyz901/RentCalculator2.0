package RentCalculator.repository;

import RentCalculator.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    @Query(value = "SELECT ps.id, ps.product_name, ps.single_price, ps.is_deleted " +
                   "FROM rentcalculator.product_service ps " +
                   "WHERE ps.is_deleted = FALSE",
            nativeQuery = true)
    List<Product> findAllProducts();

    @Query(value = "SELECT ps.id, ps.product_name, ps.single_price, ps.is_deleted " +
                   "FROM rentcalculator.product_service ps " +
                   "WHERE ps.is_deleted = FALSE " +
                   "AND ps.id = :productId",
            nativeQuery = true)
    Product findProductById(@Param("productId") Integer productId);
}
