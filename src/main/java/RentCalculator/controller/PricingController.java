package RentCalculator.controller;

import RentCalculator.model.Product;
import RentCalculator.service.PricingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
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
    public List<Product> getAllProducts(){
        return pricingService.getAllProducts();
    }

    @GetMapping("/product/{id}")
    public Product getAllProducts(@PathVariable Integer id){
        return pricingService.getProductById(id);
    }

//    @PostMapping("/user")
//    public void setUser(@RequestBody UserDto user){
//        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
//        userService.saveUser(mapperFactory.getMapperFacade().map(user, User.class));
//    }
}
