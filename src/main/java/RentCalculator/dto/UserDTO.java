package RentCalculator.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDTO {

    private Integer id;
    private String firstName;
    private String secondName;
    private String email;
    private String login;
    private String password;
    private boolean isDeleted;
}
