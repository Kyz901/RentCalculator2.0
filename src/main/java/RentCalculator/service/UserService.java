package RentCalculator.service;

import RentCalculator.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Integer userId);
    void createUser(String login, String pass);
    void deleteUser(Integer userId);
    boolean checkValidation(String login, String pass);
    boolean isUserExist(String login);
    void updateUserInfo(String firstName, String secondName, String email);
}
