package snowcoach.Filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import snowcoach.Model.Role;
import snowcoach.Service.MyUserDetailsService;
import snowcoach.Util.JwtUtil;
import snowcoach.Model.User;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    @Autowired
    ApplicationContext context;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {

        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String jwt = jwtUtil.getTokenFromCookies(request.getCookies());
        if (jwt != null) {
            System.out.println("JWT extracted: " + jwt);
            try {
                String username = jwtUtil.extractUsername(jwt);
                System.out.println("Extracted username: " + username);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(username);
                    if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                        System.out.println("Token validated successfully for: " + username);
                        
                        System.out.println("Authorities: " + userDetails.getAuthorities()); // Log the authorities collection

                        for (GrantedAuthority authority : userDetails.getAuthorities()) {
                            // Log the role name or authority value
                            System.out.println("Role: " + authority.getAuthority());
                        }

                        UsernamePasswordAuthenticationToken authToken = 
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken); // Set the context
                    } else {
                        System.out.println("JWT validation failed or user not found.");
                    }
                }
            } catch (Exception e) {
                System.out.println("Error during token validation or user lookup: " + e.getMessage());
            }
        } else {
            System.out.println("No JWT found in cookies.");
        }
        chain.doFilter(request, response);
    }


}