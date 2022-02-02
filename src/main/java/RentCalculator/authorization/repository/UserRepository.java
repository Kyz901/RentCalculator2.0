package RentCalculator.authorization.repository;

import RentCalculator.authorization.model.User;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    private final NamedParameterJdbcOperations operations;

    public UserRepository(
        final NamedParameterJdbcOperations operations
    ) {
        this.operations = operations;
    }

    public List<User> fetchAllUsers() {
        String sql = "SELECT u.id, u.first_name, u.second_name, u.email, u.login, u.password, u.is_deleted"
            + " FROM rentcalculator.users u"
            + " WHERE u.is_deleted = FALSE";
        final MapSqlParameterSource parameters = new MapSqlParameterSource();
        return operations.query(sql, parameters, (rs, rowNum) -> new User()
            .setId(rs.getInt("id"))
            .setFirstName(rs.getString("first_name"))
            .setSecondName(rs.getString("second_name"))
            .setEmail(rs.getString("email"))
            .setLogin(rs.getString("login"))
            .setPassword(rs.getString("password"))
            .setDeleted(rs.getBoolean("is_deleted"))
        );
    }

    public User fetchUserById(final Integer userId) {
        String sql = "SELECT u.id, u.first_name, u.second_name, u.email, u.login, u.password, u.is_deleted"
            + " FROM rentcalculator.users u"
            + " WHERE u.is_deleted = FALSE"
            + " AND u.id = :userId";
        final MapSqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("userId", userId);
        return operations.queryForObject(sql, parameters, (rs, rowNum) -> new User()
            .setId(rs.getInt("id"))
            .setFirstName(rs.getString("first_name"))
            .setSecondName(rs.getString("second_name"))
            .setEmail(rs.getString("email"))
            .setPassword(rs.getString("password"))
            .setDeleted(rs.getBoolean("is_deleted"))
        );
    }

    public boolean insertNewUser(final User user) {
        final String sql = "INSERT INTO"
            + " rentcalculator.users(login, password)"
            + " VALUES (:login, :pass)";
        final MapSqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("login", user.getLogin())
            .addValue("pass", user.getPassword());

        operations.update(sql, parameters);
        return true;
    }

    public void updateUserInformation(final User user) {
        String sql = "UPDATE rentcalculator.users u"
            + " SET u.first_name = :firstName,"
            + "     u.second_name = :secondName,"
            + "     u.email = :email"
            + " WHERE u.id = :userId";
        final MapSqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("userId", user.getId())
            .addValue("firstName", user.getFirstName())
            .addValue("secondName", user.getSecondName())
            .addValue("email", user.getEmail());

        operations.update(sql, parameters);
    }

    public void deleteUser(final Integer userId) {
        String sql = "UPDATE rentcalculator.users u"
            + " SET u.is_deleted = TRUE"
            + " WHERE u.id = :userId";
        final MapSqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("userId", userId);

        operations.update(sql, parameters);
    }

}
