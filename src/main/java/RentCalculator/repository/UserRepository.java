package RentCalculator.repository;

import RentCalculator.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    @Query(value = "SELECT u.id, u.first_name, u.second_name, u.email, u.login, u.password, u.is_deleted FROM rentcalculator.users u " +
            "WHERE u.is_deleted = FALSE", nativeQuery = true)
    List<User> findAllUsers();

}
