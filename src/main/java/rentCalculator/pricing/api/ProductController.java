package rentCalculator.pricing.api;

import com.kuzmin.logger.MultiLogger;
import rentCalculator.pricing.model.Product;
import rentCalculator.pricing.model.UpdateProduct;
import rentCalculator.pricing.service.ProductService;
import rentCalculator.security.model.AuthenticatedAccount;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.joining;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    private final MultiLogger log;

    public ProductController(
        final ProductService productService,
        final MultiLogger log
    ) {
        this.productService = productService;
        this.log = log;
    }

    @GetMapping("/all")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{productId}")
    public Product getProductById(
        @PathVariable final Long productId
    ) {
        return productService.getProductById(productId);
    }

    @PutMapping("/update-price")
    public Product updateProductSinglePrice(
        @RequestBody final UpdateProduct updateProduct,
        @AuthenticationPrincipal final AuthenticatedAccount principal
    ) {
        log.info(() -> String.format("IN [PUT updateProductSinglePrice]: product name: %s - initiated user: %s",
            updateProduct.getProductName(),
            principal.getUsername()
        ));

        return productService.updateProductSinglePrice(updateProduct);
    }

    @PutMapping("/update-price-bulk")
    public List<Product> updateProductsSinglePrice(
        @RequestBody final List<UpdateProduct> updateProducts,
        @AuthenticationPrincipal final AuthenticatedAccount principal
    ) {
        log.info(() -> String.format("IN [PUT updateProductsSinglePrice]: product names: %s - initiated user: %s",
            updateProducts
                .stream()
                .map(UpdateProduct::getProductName)
                .collect(joining(",")),
            principal.getUsername()
        ));

        return productService.updateProductsSinglePrice(updateProducts);
    }

    @PostMapping("/create")
    public Product createNewProduct(
        @RequestBody final Product product,
        @AuthenticationPrincipal final AuthenticatedAccount principal
    ) {
        log.info(() -> String.format("IN [POST createNewProduct]: product name: %s - initiated user: %s",
            product.getProductName(),
            principal.getUsername()
        ));

        return productService.createProduct(product);
    }

    @DeleteMapping("/delete/{productId}")
    public void deleteProduct(
        @PathVariable final Integer productId,
        @AuthenticationPrincipal final AuthenticatedAccount principal
        ) {
        log.info(() -> String.format("IN [DELETE deleteProduct]: productId: %d - initiated user: %s",
            productId,
            principal.getUsername()
        ));

        productService.deleteProduct(productId);
    }

}
