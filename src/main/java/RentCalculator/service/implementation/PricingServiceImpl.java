package RentCalculator.service.implementation;

import RentCalculator.dto.CurrentUser;
import RentCalculator.model.PaymentMaster;
import RentCalculator.model.PaymentPrice;
import RentCalculator.model.Product;
import RentCalculator.repository.PaymentMasterRepository;
import RentCalculator.repository.PaymentPriceRepository;
import RentCalculator.repository.ProductRepository;
import RentCalculator.repository.UserRepository;
import RentCalculator.service.PricingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PricingServiceImpl implements PricingService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final PaymentMasterRepository paymentMasterRepository;
    private final PaymentPriceRepository paymentPriceRepositor;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll().stream().filter(p -> !p.getDeleted()).collect(Collectors.toList());
    }

    @Override
    public Product getProductById(Integer productId) {
        return productRepository.getOne(productId);
    }

    @Override
    public PaymentMaster getPaymentMasterById(Integer paymentMasterId) {
       return paymentMasterRepository.getOne(paymentMasterId);
    }

    @Override
    public List<PaymentMaster> getAllPaymentMaster() {
        return paymentMasterRepository.findAll().stream().filter(pm -> !pm.getIsDeleted() && pm.getUser().getId().equals(CurrentUser.get().getId())).collect(Collectors.toList());
    }

    @Override
    public void createPaymentMaster(PaymentMaster paymentMaster) {
        paymentMaster.setUser(CurrentUser.get());
        paymentMasterRepository.save(paymentMaster);
    }

    @Override
    public List<PaymentPrice> priceProduct(List<PaymentPrice> paymentPriceList, Integer paymentMasterId) {
        for (PaymentPrice paymentPrice : paymentPriceList) {
            paymentPrice.setPrice(
                (paymentPrice.getNewMeterReadings() - paymentPrice.getOldMeterReadings()) * productRepository.getOne(paymentPrice.getProduct().getId()).getSinglePrice() // (new - old) * price for 1m^3
            );
            paymentPrice.setPaymentMaster(paymentMasterRepository.getOne(paymentMasterId));
            paymentPriceRepositor.save(paymentPrice);
        }
        return paymentPriceList;
    }
}
