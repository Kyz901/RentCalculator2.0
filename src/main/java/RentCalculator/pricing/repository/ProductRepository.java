package RentCalculator.pricing.repository;

import RentCalculator.pricing.model.Product;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {

    private final NamedParameterJdbcOperations operations;

    public ProductRepository(
        final NamedParameterJdbcOperations operations
    ) {
        this.operations = operations;
    }

    public List<Product> fetchAllProducts() {
        final String sql = "SELECT ps.id, ps.product_name, ps.single_price, ps.is_deleted"
            + " FROM rentcalculator.product_service ps"
            + " WHERE ps.is_deleted = FALSE";
        final MapSqlParameterSource parameters = new MapSqlParameterSource();

        return operations.query(sql, parameters, (rs, rowNum) -> new Product()
            .setId(rs.getInt("id"))
            .setProductName(rs.getString("product_name"))
            .setSinglePrice(rs.getDouble("single_price"))
            .setDeleted(rs.getBoolean("is_deleted"))
        );
    }

    public Product fetchProductById(final Integer productId) {
        final String sql = "SELECT ps.id, ps.product_name, ps.single_price, ps.is_deleted"
            + " FROM rentcalculator.product_service ps"
            + " WHERE ps.is_deleted = FALSE"
            + " AND ps.id = :productId";
        final MapSqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("productId", productId);

        return operations.queryForObject(sql, parameters, (rs, rowNum) -> new Product()
            .setId(rs.getInt("id"))
            .setProductName(rs.getString("product_name"))
            .setSinglePrice(rs.getDouble("single_price"))
            .setDeleted(rs.getBoolean("is_deleted"))
        );
    }

    public Product fetchProductByName(final String productName) {
        final String sql = "SELECT ps.id, ps.product_name, ps.single_price, ps.is_deleted"
            + " FROM rentcalculator.product_service ps"
            + " WHERE ps.is_deleted = FALSE"
            + " AND ps.product_name = :productName";
        final MapSqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("productName", productName);

        return operations.queryForObject(sql, parameters, (rs, rowNum) -> new Product()
            .setId(rs.getInt("id"))
            .setProductName(rs.getString("product_name"))
            .setSinglePrice(rs.getDouble("single_price"))
            .setDeleted(rs.getBoolean("is_deleted"))
        );
    }

    public boolean createProduct(
        final String productName,
        final Double singlePrice
    ) {
        final String sql = "INSERT INTO rentcalculator.product_service(id, product_name, single_price, is_deleted)"
            + " VALUES (NULL, :productName, :singlePrice, 0)";
        final MapSqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("productName", productName)
            .addValue("singlePrice", singlePrice);

        operations.update(sql, parameters);
        return true;
    }

    public void updateProductSinglePrice(
        final String productName,
        final Double newSinglePrice
    ) {
        final String sql = "UPDATE rentcalculator.product_service ps"
            + " SET ps.single_price = :newSinglePrice"
            + " WHERE ps.product_name = :productName";
        final MapSqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("productName", productName)
            .addValue("newSinglePrice", newSinglePrice);

        operations.update(sql, parameters);
    }

    public void deleteProduct(final Integer productId) {
        String sql = "UPDATE rentcalculator.product_service ps"
            + " SET ps.is_deleted = TRUE"
            + " WHERE ps.id = :productId";
        final MapSqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("productId", productId);

        operations.update(sql, parameters);
    }
}
