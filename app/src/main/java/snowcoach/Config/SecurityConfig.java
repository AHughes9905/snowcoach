package snowcoach.Config;

import snowcoach.Service.UserService;
import snowcoach.Util.JwtUtil;
import snowcoach.Filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    public SecurityConfig(JwtUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers("/api/auth/**").permitAll() // Allow authentication endpoints
            .anyRequest().authenticated() // Secure all other endpoints
            .and()
            .addFilterBefore(new JwtAuthenticationFilter(jwtUtil, userService), UsernamePasswordAuthenticationFilter.class); // Add JWT filter
        return http.build();
    }

    /// add something like the following to restrict url access
    //@Override
    //protected void configure(HttpSecurity http) throws Exception {
    //    http.authorizeRequests()
    //            .antMatchers("/admin/**").hasRole("ADMIN")
    //            .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
    //            .anyRequest().authenticated();
    //}

    ///
}
