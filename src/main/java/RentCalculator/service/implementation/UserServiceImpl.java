package RentCalculator.service.implementation;

import RentCalculator.dto.CurrentUser;
import RentCalculator.model.User;
import RentCalculator.repository.PaymentMasterRepository;
import RentCalculator.repository.ProductRepository;
import RentCalculator.repository.UserRepository;
import RentCalculator.service.UserService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final PaymentMasterRepository paymentMasterRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll().stream()
                .filter(u -> !u.isDeleted())
                .collect(Collectors.toList());
    }

    @Override
    public User getUserById(Integer userId) {
        return userRepository.getOne(userId);
    }

    @Override
    public void createUser(String login, String pass) {
            User user = new User();
            user.setLogin(login);
            user.setPassword(pass);
            userRepository.save(user);
    }

    @Override
    public User deleteUser(Integer userId) {
        User user = userRepository.getOne(userId);
        user.setDeleted(true);
        userRepository.save(user);
        return user;
    }

    @Override
    public boolean checkValidation(String login, String pass) {
        List<User> users = userRepository.findAll().stream()
                .filter(u -> u.getLogin().toUpperCase().equals(login.toUpperCase())
                        && u.getPassword().equals(pass)
                        && !u.isDeleted())
                .collect(Collectors.toList());
        if(users.size() > 0){
         CurrentUser.set(users.get(0));
         return true;
        }
        return false;
    }

    @Override
    public boolean isUserExist(String login) {
        List<User> users = userRepository.findAll().stream()
                .filter(u -> u.getLogin().toUpperCase().equals(login.toUpperCase())
                        && !u.isDeleted())
                .collect(Collectors.toList());
        return users.size() > 0;
    }

    @Override
    public void updateUserInfo(String firstName, String secondName, String email) {
        User user = CurrentUser.get();
        user.setFirstName(firstName);
        user.setSecondName(secondName);
        user.setEmail(email);
        CurrentUser.set(user);
        userRepository.save(user);
    }
}
