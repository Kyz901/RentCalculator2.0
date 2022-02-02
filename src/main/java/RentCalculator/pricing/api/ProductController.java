package RentCalculator.pricing.api;

import RentCalculator.pricing.model.Product;
import RentCalculator.pricing.model.UpdateProduct;
import RentCalculator.pricing.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Product getProductById(@PathVariable Integer productId) {

        return productService.getProductById(productId);
    }

    @PutMapping("/update-price")
    public Product updateProductSinglePrice(@RequestBody UpdateProduct updateProduct) {
        return productService.updateProductSinglePrice(updateProduct);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createNewProduct(@RequestBody Product product) {
        productService.createProduct(product);
        return ResponseEntity.status(200).body("Product successfully created");
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.status(200).body("Product successfully deleted");
    }

}
