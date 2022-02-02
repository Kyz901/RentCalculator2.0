package RentCalculator.authorization.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class User {

    private Integer id;
    private String firstName;
    private String secondName;
    private String email;
    private String login;
    private String password;
    private boolean isDeleted;

}
