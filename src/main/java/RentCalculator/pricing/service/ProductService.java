package RentCalculator.pricing.service;

import RentCalculator.pricing.model.Product;
import RentCalculator.pricing.model.UpdateProduct;
import RentCalculator.pricing.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(
        ProductRepository productRepository
    ) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.fetchAllProducts();
    }

    public Product getProductById(Integer productId) {
        return productRepository.fetchProductById(productId);
    }

    public void createProduct(Product product) {
        productRepository.createProduct(
            product.getProductName(),
            product.getSinglePrice()
        );
    }

    public Product updateProductSinglePrice(UpdateProduct updateProduct) {
        productRepository.updateProductSinglePrice(
            updateProduct.getProductName(),
            updateProduct.getSinglePrice()
        );
        return productRepository.fetchProductByName(updateProduct.getProductName());
    }

    public void deleteProduct(Integer productId) {
        productRepository.deleteProduct(productId);
    }
}
