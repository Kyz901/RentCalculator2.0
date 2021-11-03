package RentCalculator.controller;

import RentCalculator.dto.ProductDTO;
import RentCalculator.dto.UpdateProductDTO;
import RentCalculator.model.Product;
import RentCalculator.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/product")
public class ProductController {

    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/all")
    public ResponseEntity<?> getAllProducts() {
        List<Product> productList = productService.getAllProducts();

        return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable Integer productId) {
        Product product = productService.getProductById(productId);

        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }

    @PutMapping("/product/update-price")
    public ResponseEntity<?> updateProductSinglePrice(@RequestBody UpdateProductDTO updateProductDTO){
        Product product = productService.updateProductSinglePrice(updateProductDTO);
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }

    @PostMapping("/product/create")
    public ResponseEntity<?> createNewProduct(@RequestBody ProductDTO productDTO) {
        Product product = productService.createProduct(productDTO);
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }



}
