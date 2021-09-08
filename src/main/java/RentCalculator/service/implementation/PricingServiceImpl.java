package RentCalculator.service.implementation;

import RentCalculator.model.Product;
import RentCalculator.repository.ProductRepository;
import RentCalculator.service.PricingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PricingServiceImpl implements PricingService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll().stream().filter(p -> !p.getDeleted()).collect(Collectors.toList());
    }

    @Override
    public Product getProductById(Integer id) {
        return productRepository.getOne((long) id);
    }
}
