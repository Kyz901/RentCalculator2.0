package RentCalculator.pricing.service;

import RentCalculator.pricing.model.Product;
import RentCalculator.pricing.model.UpdateProduct;
import RentCalculator.pricing.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
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

    public Product getProductById(final Long productId) {
        return productRepository.fetchProductById(productId);
    }

    public Product createProduct(final Product product) {
        final long productId = productRepository.createProduct(
            product.getProductName(),
            product.getSinglePrice()
        );
        log.info("IN [POST createNewProduct]: successfully created product: {}",
            productId
        );

        return productRepository.fetchProductById(productId);
    }

    public Product updateProductSinglePrice(final UpdateProduct updateProduct) {
        final String updateName = productRepository.updateProductSinglePrice(updateProduct);

        log.info("IN [PUT updateProductSinglePrice]: successfully updated product: {} - new price: {}",
            updateProduct.getProductName(),
            updateProduct.getSinglePrice()
        );

        return productRepository.fetchProductByName(updateName);
    }

    public List<Product> updateProductsSinglePrice(final List<UpdateProduct> updateProducts) {
        final List<String> productNames = updateProducts
            .stream()
            .map(productRepository::updateProductSinglePrice)
            .collect(toList());

        log.info("IN [PUT updateProductsSinglePrice]: successfully updated products in bulk: {}",
            String.join(",", productNames)
        );

        return productRepository.fetchProductByNames(productNames);
    }

    public void deleteProduct(Integer productId) {
        log.info("IN [DELETE deleteProduct]: successfully deleted product id: {}",
            productId
        );

        productRepository.deleteProduct(productId);
    }
}
