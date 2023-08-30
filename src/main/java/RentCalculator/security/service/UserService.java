package RentCalculator.security.service;

import RentCalculator.security.model.AuthenticatedAccount;
import RentCalculator.security.model.User;
import RentCalculator.security.model.UserRole;
import RentCalculator.security.repository.UserRepository;

import RentCalculator.security.repository.UserRoleRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static RentCalculator.common.RoleEnum.USER_ROLE;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(
        final UserRepository userRepository,
        final UserRoleRepository userRoleRepository,
        final BCryptPasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(final User user) {
        final UserRole userRole = userRoleRepository.fetchRoleByName(USER_ROLE);

        final long createdUserId = userRepository.createUser(user
            .setPassword(user.getPassword()) //todo: encoder
//            .setPassword(passwordEncoder.encode(user.getPassword()))
            .setRole(userRole)
        );

        return userRepository.fetchUserById(createdUserId);
    }

    public User findByLogin(final String login) {
        return userRepository.fetchUserByLogin(login);
    }

    public boolean isValidPassword(final User authUser, final User existedUser) {
        return existedUser.getPassword().equals(authUser.getPassword());
    }

    public User findById(final Long userId) {
        return userRepository.fetchUserById(userId);
    }

    public List<User> getAllUsers() {
        return userRepository.fetchAllUsers();
    }

    public void deleteUser(final Long userId) {
        userRepository.deleteUser(userId);
    }

    public User updateUserInfo(
        final String firstName,
        final String secondName,
        final String email,
        final AuthenticatedAccount principal
    ) {
        userRepository.updateUserInformation(
            firstName,
            secondName,
            email,
            principal.getUserId()
        );

        return userRepository.fetchUserById(principal.getUserId());
    }

    public User updateUserPrivileges(final Long userId, final boolean hasPrivileges) {
        userRepository.updateUserPrivileges(userId, hasPrivileges);

        return userRepository.fetchUserById(userId);
    }

}
