package RentCalculator.repository;

import RentCalculator.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    @Query(value = "SELECT u.id, u.first_name, u.second_name, u.email, u.login, u.password, u.is_deleted " +
                   "FROM rentcalculator.users u " +
                   "WHERE u.is_deleted = FALSE",
                    nativeQuery = true)
    List<User> fetchAllUsers();

    @Query(value = "SELECT u.id, u.first_name, u.second_name, u.email, u.login, u.password, u.is_deleted " +
                   "FROM rentcalculator.users u " +
                   "WHERE u.is_deleted = FALSE " +
                   "AND u.id = :userId",
                    nativeQuery = true)
    User getUserById(@Param("userId") Integer userId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User u " +
                   "SET u.isDeleted = TRUE " +
                   "WHERE u.id = :userId")
    Integer deleteUser(@Param("userId") Integer userId);

}
