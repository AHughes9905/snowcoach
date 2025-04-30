package Controller;

import DTO.UserLoginDTO;
import DTO.LoginResponseDTO;
import Model.User;
import Service.UserDetailsServiceImpl;
import Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody UserLoginDTO userLogin) {
        try {
            // Authenticate user
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLogin.getUsername(), userLogin.getPassword())
            );

            // Load user details
            User user = userDetailsService.loadUserByUsername(userLogin.getUsername());

            // Generate JWT token
            String token = jwtUtil.generateToken(user);

            // Return response
            return ResponseEntity.ok(new UserDTO(token, user.getUsername(), user.getRole()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body(new UserDTO("Invalid username or password"));
        }
    }
}