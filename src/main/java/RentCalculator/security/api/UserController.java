package RentCalculator.security.api;

import RentCalculator.security.model.AuthenticatedAccount;
import RentCalculator.security.model.User;
import RentCalculator.security.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(
        final UserService userService
    ) {
        this.userService = userService;
    }

    @GetMapping("/all-users")
    public List<User> getAllUsers(
        @AuthenticationPrincipal final AuthenticatedAccount principal
    ) {
        log.info("IN [GET getAllUsers]: - initiated user: {}",
            principal.getUsername()
        );

        return userService.getAllUsers();
    }

    @PutMapping("/create-user/update-info")
    public User updateUserInfo(
        @RequestBody final User user,
        @AuthenticationPrincipal final AuthenticatedAccount principal
    ) {
        log.info("IN [PUT updateUserInfo]: - initiated user: {}",
            principal.getUsername()
        );

        return userService.updateUserInfo(
            user.getFirstName(),
            user.getSecondName(),
            user.getEmail(),
            principal
        );
    }

    @PutMapping("/update-privileges")
    public User updateUserPrivileges(
        @RequestParam final boolean hasPrivileges,
        @RequestParam final Long userId,
        @AuthenticationPrincipal final AuthenticatedAccount principal
    ) {
        log.info("IN [PUT updateUserPrivileges]: user id to change privileges: {} - initiated user: {}",
            userId,
            principal.getUsername()
        );

        return userService.updateUserPrivileges(userId, hasPrivileges);
    }

    @DeleteMapping("/delete-user/{userId}")
    public void deleteUser(
        @AuthenticationPrincipal final AuthenticatedAccount principal,
        @PathVariable final Long userId
    ) {
        log.info("IN [DELETE deleteUser]: user id to delete: {} - initiated user: {}",
            userId,
            principal.getUsername()
        );

        userService.deleteUser(userId);
    }
}
