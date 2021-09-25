package RentCalculator.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users", schema = "rentcalculator")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column
    private String email;

    @Column
    private String login;

    @Column
    private String password;

    @Column(name="is_deleted")
    private boolean isDeleted;

    public Integer getId() {
        return id;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getSecondName() {
        return secondName;
    }

    public User setSecondName(String secondName) {
        this.secondName = secondName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public User setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public User setDeleted(boolean deleted) {
        isDeleted = deleted;
        return this;
    }
}
