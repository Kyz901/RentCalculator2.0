package RentCalculator.controller;

import RentCalculator.model.CurrentUser;
import RentCalculator.model.User;

import RentCalculator.service.UserService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/user")
public class UserConroller {

    private final UserService userService;

    public UserConroller(UserService userService) {
        this.userService = userService;
    }

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

    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
        return ResponseEntity.status(200).body("User successfully deleted");
    }
}