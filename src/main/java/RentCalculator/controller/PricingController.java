package RentCalculator.controller;

import RentCalculator.dto.PaymentMasterDTO;
import RentCalculator.dto.PaymentPriceDTO;
import RentCalculator.dto.ProductDTO;
import RentCalculator.model.PaymentMaster;
import RentCalculator.model.PaymentPrice;
import RentCalculator.model.Product;
import RentCalculator.service.PricingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.Inet4Address;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PricingController {

    private final PricingService pricingService;

    @GetMapping("/product")
    public ResponseEntity<?> getAllProducts() {
        List<Product> productList = pricingService.getAllProducts();

        return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable Integer productId) {
        Product product = pricingService.getProductById(productId);

        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }

    @GetMapping("/payment-master/{paymentMasterId}")
    public ResponseEntity<?> getPaymentMasterById(@PathVariable Integer paymentMasterId) {
        PaymentMaster paymentMaster = pricingService.getPaymentMasterById(paymentMasterId);

        return new ResponseEntity<PaymentMaster>(paymentMaster, HttpStatus.OK);
    }

    @GetMapping("/payment-master/{paymentMasterId}/pricing")
    public ResponseEntity<?> getPaymentPrices(@PathVariable Integer paymentMasterId) {
        List<PaymentPrice> paymentPriceList = pricingService.getPaymentPrices(paymentMasterId);
        return new ResponseEntity<List<PaymentPrice>>(paymentPriceList, HttpStatus.OK);
    }
    @PostMapping("/payment-master")
    public ResponseEntity<?> createPaymentMaster(@RequestParam String paymentName) {
        PaymentMaster paymentMaster = pricingService.createPaymentMaster(paymentName);
        return new ResponseEntity<PaymentMaster>(paymentMaster, HttpStatus.OK);
    }

    @PostMapping("/payment-master/{paymentMasterId}/pricing")
    public ResponseEntity<?> priceProduct(@RequestBody List<PaymentPriceDTO> paymentPrice, @PathVariable Integer paymentMasterId){
        List<PaymentPrice> paymentPriceList = pricingService.priceProduct(paymentPrice, paymentMasterId);
        pricingService.updateTotalPriceInPaymentMaster(paymentMasterId);
        return new ResponseEntity<List<PaymentPrice>>( paymentPriceList, HttpStatus.OK);
    }

//    @PutMapping("/user")
//    public void updateUser(@RequestParam Long id,
//                           @RequestParam String firstName,
//                           @RequestParam String secondName){
//
//        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
//        User user = userService.getUserById(id);
//        userService.updateUser(mapperFactory.getMapperFacade().map(user, User.class),firstName,secondName);
//    }

//    @PostMapping("/user")
//    public void setUser(@RequestBody UserDto user){
//        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
//        userService.saveUser(mapperFactory.getMapperFacade().map(user, User.class));
//    }
}
