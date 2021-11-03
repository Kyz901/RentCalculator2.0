package RentCalculator.service.implementation;

import RentCalculator.model.CurrentUser;
import RentCalculator.model.User;

import RentCalculator.repository.UserRepository;

import RentCalculator.service.UserService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.fetchAllUsers();
    }

    @Override
    public User getUserById(Integer userId) {
        return userRepository.getUserById(userId);
    }

    @Override
    public void createUser(String login, String pass) {
            User user = new User()
            .setLogin(login)
            .setPassword(pass);
            userRepository.save(user);
    }

    @Override
    public void deleteUser(Integer userId) {
        userRepository.deleteUser(userId);
    }

    @Override
    public boolean checkValidation(String login, String pass) {
        List<User> users = userRepository.fetchAllUsers().stream()
                .filter(u -> u.getLogin().toUpperCase().equals(login.toUpperCase())
                        && u.getPassword().equals(pass))
                .collect(Collectors.toList());
        if(users.size() > 0){
         CurrentUser.set(users.get(0));
         return true;
        }
        return false;
    }

    @Override
    public boolean isUserExist(String login) {
        List<User> users = userRepository.fetchAllUsers().stream()
                .filter(u -> u.getLogin().toUpperCase().equals(login.toUpperCase()))
                .collect(Collectors.toList());
        return users.size() > 0;
    }

    @Override
    public void updateUserInfo(String firstName, String secondName, String email) {
        User user = CurrentUser.get().setFirstName(firstName).setSecondName(secondName).setEmail(email);
        CurrentUser.set(user);
        userRepository.save(user);
    }
}
