package rentCalculator.security.config;

import org.springframework.context.annotation.Configuration;
import rentCalculator.config.JwtAccessDeniedHandler;
import rentCalculator.config.JwtAuthenticationEntryPoint;
import rentCalculator.security.jwt.JwtConfigurer;
import rentCalculator.security.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfiguration(
        final JwtTokenProvider tokenProvider,
        final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
        final JwtAccessDeniedHandler jwtAccessDeniedHandler
    ) {
        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .httpBasic().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(STATELESS)
            .and()
            .formLogin().disable()
            .authorizeRequests()
                .antMatchers(DELETE, "/api/v1/user/delete-user/{userId}").hasRole("ADMIN")
                .antMatchers(GET, "/api/v1/user/all-users").hasRole("ADMIN")
                .antMatchers(PUT, "/api/v1/user/update-privileges").hasRole("ADMIN")
                .antMatchers(GET, "/api/v1/product/**").hasRole("ADMIN")
            .anyRequest().authenticated()
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .accessDeniedHandler(jwtAccessDeniedHandler)
            .and()
            .apply(new JwtConfigurer(tokenProvider));
    }
}
