package RentCalculator.controller;

import RentCalculator.dto.PaymentPriceDTO;

import RentCalculator.model.PaymentMaster;
import RentCalculator.model.PaymentPrice;

import RentCalculator.service.PricingService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class PricingController {

    private final PricingService pricingService;

    public PricingController(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    @GetMapping("/payment-master/{paymentMasterId}")
    public ResponseEntity<?> getPaymentMasterById(@PathVariable Integer paymentMasterId) {
        PaymentMaster paymentMaster = pricingService.getPaymentMasterById(paymentMasterId);

        return new ResponseEntity<PaymentMaster>(paymentMaster, HttpStatus.OK);
    }

    @GetMapping("/payment-master")
    public ResponseEntity<?> getPaymentsMasterForCurrentUser() {
        List<PaymentMaster> paymentMasterList = pricingService.getAllPaymentMasterForCurrentUser();
        return new ResponseEntity<List<PaymentMaster>>(paymentMasterList, HttpStatus.OK);
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
    public ResponseEntity<?> priceProduct(@RequestBody List<PaymentPriceDTO> paymentPrices, @PathVariable Integer paymentMasterId){
        List<PaymentPrice> paymentPriceList = pricingService.priceProduct(paymentPrices, paymentMasterId);
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
