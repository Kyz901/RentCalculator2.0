package RentCalculator.authorization.service;

import RentCalculator.authorization.model.User;
import RentCalculator.authorization.model.CurrentUser;

import RentCalculator.authorization.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(
        final UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.fetchAllUsers();
    }

    public User getUserById(final Integer userId) {
        return userRepository.fetchUserById(userId);
    }

    public void createUser(final String login, final String pass) {
        User user = new User()
            .setLogin(login)
            .setPassword(pass);
        userRepository.insertNewUser(user);
    }

    public void deleteUser(final Integer userId) {
        userRepository.deleteUser(userId);
    }

    public boolean checkValidation(final String login, final String pass) {
        List<User> users = userRepository.fetchAllUsers().stream()
            .filter(u -> u.getLogin().equalsIgnoreCase(login)
                && u.getPassword().equals(pass))
            .limit(1)
            .collect(Collectors.toList());
        if (users.size() > 0) {
            CurrentUser.set(users.get(0));
            return true;
        }
        return false;
    }

    public boolean isUserExist(final String login) {
        return userRepository.fetchAllUsers().stream()
            .anyMatch(u -> u.getLogin().equalsIgnoreCase(login));
    }

    public void updateUserInfo(final String firstName, final String secondName, final String email) {
        User user = CurrentUser.get().setFirstName(firstName).setSecondName(secondName).setEmail(email);
        CurrentUser.set(user);
        userRepository.updateUserInformation(user);
    }
}
