package RentCalculator.repository;

import RentCalculator.model.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    @Query(value = "SELECT ps.id, ps.product_name, ps.single_price, ps.is_deleted " +
                   "FROM rentcalculator.product_service ps " +
                   "WHERE ps.is_deleted = FALSE",
            nativeQuery = true)
    List<Product> fetchAllProducts();

    @Query(value = "SELECT ps.id, ps.product_name, ps.single_price, ps.is_deleted " +
                   "FROM rentcalculator.product_service ps " +
                   "WHERE ps.is_deleted = FALSE " +
                   "AND ps.id = :productId",
            nativeQuery = true)
    Product fetchProductById(@Param("productId") Integer productId);

    @Query(value = "SELECT ps.id, ps.product_name, ps.single_price, ps.is_deleted " +
        "FROM rentcalculator.product_service ps " +
        "WHERE ps.is_deleted = FALSE " +
        "AND ps.product_name = :productName",
        nativeQuery = true)
    Product fetchProductByName(@Param("productName") String productName);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO rentcalculator.product_service(id, product_name, single_price, is_deleted) " +
        "VALUES ( NULL, 0.0, :productName, :singlePrice, 0)",
        nativeQuery = true)
    Product createProduct(@Param("productName") String productName,
                          @Param("singlePrice") Double singlePrice);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Product p " +
        "SET p.singlePrice = :newSinglePrice " +
        "WHERE p.productName = :productName")
    Integer updateProductSinglePrice(@Param("productName") String productName,
                                     @Param("newSinglePrice") Double singlePrice);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Product p " +
        "SET p.isDeleted = TRUE " +
        "WHERE p.id = :productId")
    Integer deleteProduct(@Param("productId") Integer productId);
}
