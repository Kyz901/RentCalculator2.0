package rentCalculator.email.model;

import lombok.Data;
import lombok.experimental.Accessors;
import rentCalculator.security.model.AuthenticatedAccount;

@Data
@Accessors(chain = true)
public class UserInfo {

    private String firstName;
    private String secondName;
    private String email;
    private boolean hasPrivileges;

    public static UserInfo fromAuthenticatedAccount(final AuthenticatedAccount principal) {
        return new UserInfo()
            .setEmail(principal.getEmail())
            .setFirstName(principal.getFirstName())
            .setSecondName(principal.getLastName())
            .setHasPrivileges(principal.isHasPrivileges());
    }

}
