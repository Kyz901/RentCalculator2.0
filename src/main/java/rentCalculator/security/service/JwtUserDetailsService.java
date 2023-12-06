package rentCalculator.security.service;

import com.kuzmin.logger.MultiLogger;
import rentCalculator.security.model.AuthenticatedAccount;
import rentCalculator.security.model.User;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    private final MultiLogger log;

    public JwtUserDetailsService(final UserService userService, final MultiLogger log) {
        this.userService = userService;
        this.log = log;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        final User user = userService.findByLogin(login); //todo: load by token
        if(user == null) {
            log.error(() -> String.format("IN [loadUserByUsername]: - User '%s' not found", login));
            throw new UsernameNotFoundException("User with login: " + login + " not found");
        }

        return AuthenticatedAccount
            .builder()
            .userId(user.getId())
            .email(user.getEmail())
            .firstName(user.getFirstName())
            .lastName(user.getSecondName())
            .hasPrivileges(user.isHasPrivileges())
            .accountNonLocked(user.isActive())
            .credentialsNonExpired(true)
            .authorities(AuthorityUtils.createAuthorityList(user.getRole().getName()))
            .build();
    }
}
