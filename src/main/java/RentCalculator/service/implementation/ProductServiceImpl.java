package RentCalculator.service.implementation;

import RentCalculator.dto.ProductDTO;
import RentCalculator.dto.UpdateProductDTO;
import RentCalculator.model.Product;
import RentCalculator.repository.ProductRepository;
import RentCalculator.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.fetchAllProducts();
    }

    @Override
    public Product getProductById(Integer productId) {
        return productRepository.fetchProductById(productId);
    }

    @Override
    public Product createProduct(ProductDTO productDTO) {
        return productRepository.createProduct(
            productDTO.getProductName(),
            productDTO.getSinglePrice()
        );
    }

    @Override
    public Product updateProductSinglePrice(UpdateProductDTO updateProductDTO) {
        productRepository.updateProductSinglePrice(
            updateProductDTO.getProductName(),
            updateProductDTO.getSinglePrice()
        );
        return productRepository.fetchProductByName(updateProductDTO.getProductName());
    }

    @Override
    public void deleteProduct(Integer productId) {
        productRepository.deleteProduct(productId);
    }
}
