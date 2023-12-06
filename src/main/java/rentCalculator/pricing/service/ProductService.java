package rentCalculator.pricing.service;

import com.kuzmin.logger.MultiLogger;
import rentCalculator.pricing.model.Product;
import rentCalculator.pricing.model.UpdateProduct;
import rentCalculator.pricing.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final MultiLogger log;

    public ProductService(
        final ProductRepository productRepository,
        final MultiLogger log
    ) {
        this.productRepository = productRepository;
        this.log = log;
    }

    public List<Product> getAllProducts() {
        return productRepository.fetchAllProducts();
    }

    public Product getProductById(final Long productId) {
        return productRepository.fetchProductById(productId);
    }

    public List<Product> fetchProductsByIds(final List<Long> productIds) {
        return productRepository.fetchProductsByIds(productIds);
    }

    public Product createProduct(final Product product) {
        final long productId = productRepository.createProduct(
            product.getProductName(),
            product.getSinglePrice()
        );
        log.info(() -> String.format("IN [POST createNewProduct]: successfully created product: %d",
            productId
        ));

        return productRepository.fetchProductById(productId);
    }

    public Product updateProductSinglePrice(final UpdateProduct updateProduct) {
        final String updateName = productRepository.updateProductSinglePrice(updateProduct);

        log.debug(() -> String.format("IN [PUT updateProductSinglePrice]: successfully updated product: %s - new price: %f",
            updateProduct.getProductName(),
            updateProduct.getSinglePrice()
        ));

        return productRepository.fetchProductByName(updateName);
    }

    public List<Product> updateProductsSinglePrice(final List<UpdateProduct> updateProducts) {
        final List<String> productNames = updateProducts
            .stream()
            .map(productRepository::updateProductSinglePrice)
            .collect(toList());

        log.info(() -> String.format("IN [PUT updateProductsSinglePrice]: successfully updated products in bulk: %s",
            String.join(",", productNames)
        ));

        return productRepository.fetchProductByNames(productNames);
    }

    public void deleteProduct(Integer productId) {
        log.info(() -> String.format("IN [DELETE deleteProduct]: successfully deleted product id: %d",
            productId
        ));

        productRepository.deleteProduct(productId);
    }
}
