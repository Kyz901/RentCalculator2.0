package rentCalculator.common.exceptions;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {

    public JwtAuthenticationException(final String msg, final Throwable t) {
        super(msg, t);
    }

    public JwtAuthenticationException(final String msg) {
        super(msg);
    }
}
