package RentCalculator.authorization.api;

import RentCalculator.authorization.model.User;
import RentCalculator.authorization.model.CurrentUser;
import RentCalculator.authorization.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(
        final UserService userService
    ) {
        this.userService = userService;
    }

    @GetMapping("/all-users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/check-user-exist")
    public boolean isUserExist(
        @RequestParam final String login
    ) {
        return userService.isUserExist(login);
    }

    @GetMapping("/check-validation")
    public boolean validateUser(
        @RequestParam final String login,
        @RequestParam final String pass
    ) {
        return userService.checkValidation(login, pass);
    }

    @PostMapping("/create-user")
    public User createUser(
        @RequestParam final String login,
        @RequestParam final String pass
    ) {
        if (!userService.isUserExist(login)) {
            userService.createUser(login, pass);
            userService.checkValidation(login, pass);
        }
        return CurrentUser.get();
    }

    @PutMapping("/create-user/update-info")
    public User updateUserInfo(
        @RequestParam final String fistName,
        @RequestParam final String secondName,
        @RequestParam final String email
    ) {
        userService.updateUserInfo(fistName, secondName, email);
        return CurrentUser.get();
    }

    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<?> deleteUser(
        @PathVariable final Integer userId
    ) {
        userService.deleteUser(userId);
        return ResponseEntity.status(200).body("User successfully deleted!");
    }
}