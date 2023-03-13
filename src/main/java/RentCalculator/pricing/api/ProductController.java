package RentCalculator.pricing.api;

import RentCalculator.pricing.model.Product;
import RentCalculator.pricing.model.UpdateProduct;
import RentCalculator.pricing.service.ProductService;
import RentCalculator.security.model.AuthenticatedAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.joining;

@RestController
@Slf4j
@RequestMapping("/api/v1/product")
public class ProductController {

    final ProductService productService;

    public ProductController(
        final ProductService productService
    ) {
        this.productService = productService;
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
        log.info("IN [PUT updateProductSinglePrice]: product name: {} - initiated user: {}",
            updateProduct.getProductName(),
            principal.getUsername()
        );

        return productService.updateProductSinglePrice(updateProduct);
    }

    @PutMapping("/update-price-bulk")
    public List<Product> updateProductsSinglePrice(
        @RequestBody final List<UpdateProduct> updateProducts,
        @AuthenticationPrincipal final AuthenticatedAccount principal
    ) {
        log.info("IN [PUT updateProductsSinglePrice]: product names: {} - initiated user: {}",
            updateProducts
                .stream()
                .map(UpdateProduct::getProductName)
                .collect(joining(",")),
            principal.getUsername()
        );

        return productService.updateProductsSinglePrice(updateProducts);
    }

    @PostMapping("/create")
    public Product createNewProduct(
        @RequestBody final Product product,
        @AuthenticationPrincipal final AuthenticatedAccount principal
    ) {
        log.info("IN [POST createNewProduct]: product name: {} - initiated user: {}",
            product.getProductName(),
            principal.getUsername()
        );

        return productService.createProduct(product);
    }

    @DeleteMapping("/delete/{productId}")
    public void deleteProduct(
        @PathVariable final Integer productId,
        @AuthenticationPrincipal final AuthenticatedAccount principal
        ) {
        log.info("IN [DELETE deleteProduct]: productId: {} - initiated user: {}",
            productId,
            principal.getUsername()
        );

        productService.deleteProduct(productId);
    }

}
