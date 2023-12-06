package rentCalculator.security.service;

import rentCalculator.security.model.AuthenticatedAccount;
import rentCalculator.security.model.User;
import rentCalculator.security.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(
        final UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    public User findByLogin(final String login) {
        return userRepository.fetchUserByLogin(login);
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
