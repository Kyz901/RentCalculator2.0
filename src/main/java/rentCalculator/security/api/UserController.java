package rentCalculator.security.api;

import com.kuzmin.logger.MultiLogger;
import rentCalculator.security.model.AuthenticatedAccount;
import rentCalculator.security.model.User;
import rentCalculator.security.service.UserService;
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
public class UserController {

    private final UserService userService;

    private final MultiLogger log;

    public UserController(
        final UserService userService,
        final MultiLogger log
    ) {
        this.userService = userService;
        this.log = log;
    }

    @GetMapping("/all-users")
    public List<User> getAllUsers(
        @AuthenticationPrincipal final AuthenticatedAccount principal
    ) {
        log.info(() -> String.format("IN [GET getAllUsers]: - initiated user: %s",
            principal.getUsername()
        ));

        return userService.getAllUsers();
    }

    @PutMapping("/create-user/update-info")
    public User updateUserInfo(
        @RequestBody final User user,
        @AuthenticationPrincipal final AuthenticatedAccount principal
    ) {
        log.info(() -> String.format("IN [PUT updateUserInfo]: - initiated user: %s",
            principal.getUsername()
        ));

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
        log.info(() -> String.format("IN [PUT updateUserPrivileges]: user id to change privileges: %d - initiated user: %s",
            userId,
            principal.getUsername()
        ));

        return userService.updateUserPrivileges(userId, hasPrivileges);
    }

    @DeleteMapping("/delete-user/{userId}")
    public void deleteUser(
        @AuthenticationPrincipal final AuthenticatedAccount principal,
        @PathVariable final Long userId
    ) {
        log.info(() -> String.format("IN [DELETE deleteUser]: user id to delete: %d - initiated user: %s",
            userId,
            principal.getUsername()
        ));

        userService.deleteUser(userId);
    }
}
