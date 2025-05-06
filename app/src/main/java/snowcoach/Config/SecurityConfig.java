package snowcoach.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    //@Autowired
    //private UserDetailsService userDetailsService;

    public SecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;

    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests(request -> request
                .requestMatchers("api/auth/login", "api/auth/register")
                .permitAll()
                .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults()).
                sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).build();

    }

    //@Bean
    //AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
    //    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    //    daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        //daoAuthenticationProvider.setUserDetailsPasswordService(userDetailsService);
    //    return daoAuthenticationProvider;
    //}

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();

    }
}
