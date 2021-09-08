package RentCalculator.service;

import RentCalculator.model.Product;

import java.util.List;

public interface PricingService {

    List<Product> getAllProducts();
    Product getProductById(Integer id);
}
