package RentCalculator.controller;

import RentCalculator.dto.CurrentUser;
import RentCalculator.dto.ProductDTO;
import RentCalculator.dto.UserDTO;
import RentCalculator.model.PaymentMaster;
import RentCalculator.model.Product;
import RentCalculator.model.User;
import RentCalculator.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.http.HTTPException;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserConroller {

    private final UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<List<User>>( users, HttpStatus.OK);
    }

    @GetMapping("/check-user-exist")
    public boolean isUserExist(@RequestParam String login) {
        return userService.isUserExist(login);
    }

    @GetMapping("/check-validation")
    public boolean validateUser(@RequestParam String login,
                                @RequestParam String pass) {
        return userService.checkValidation(login, pass);
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestParam String login,
                                        @RequestParam String pass) {
        if(!userService.isUserExist(login)){
            userService.createUser(login,pass);
            userService.checkValidation(login,pass);
            return new ResponseEntity<User>(CurrentUser.get(), HttpStatus.OK);
        }
        return ResponseEntity.status(409).body("User already exist!");
    }

    @PutMapping("/create-user/update-info")
    public ResponseEntity<?> updateUserInfo(@RequestParam String fistName,
                                            @RequestParam String secondName,
                                            @RequestParam String email) {
        userService.updateUserInfo(fistName, secondName, email); // todo: DTO
        return new ResponseEntity<User>(CurrentUser.get(),HttpStatus.OK);
    }

    @PutMapping("/delete-user/{userId}")
    public ResponseEntity<?> deleteUser(@RequestParam Integer userId) {
        userService.deleteUser(userId);
        return ResponseEntity.status(200).body("User successfully deleted");
    }
}