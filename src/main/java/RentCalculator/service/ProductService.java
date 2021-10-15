package RentCalculator.service;

import RentCalculator.dto.ProductDTO;
import RentCalculator.dto.UpdateProductDTO;
import RentCalculator.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();
    Product getProductById(Integer productId);
    Product createProduct(ProductDTO productDTO);
    Product updateProductSinglePrice(UpdateProductDTO updateProductDTO);
    void deleteProduct(Integer productId);
}
