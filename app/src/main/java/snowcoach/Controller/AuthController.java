package snowcoach.Controller;

import snowcoach.DTO.AuthRespDTO;
import snowcoach.DTO.UserAuthDTO;
import snowcoach.DTO.UserDTO;
import snowcoach.DTO.UserRegistrationDTO;
import snowcoach.Model.User;
import snowcoach.Service.UserService;
import snowcoach.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public AuthController(AuthenticationManager authManager, JwtUtil jwtUtil, UserService userService) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        try {
            userService.createUser(userRegistrationDTO);
            return ResponseEntity.ok("User registered successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserAuthDTO userAuthDTO, HttpServletResponse response) {
        try {
            String jwt = userService.verifyUser(userAuthDTO);
            Cookie jwtCookie = jwtUtil.createJwtCookie(jwt);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setSecure(true);
            response.addCookie(jwtCookie);
            UserDTO userDTO = userService.getUserDTOByName(userAuthDTO.getUsername());

            return ResponseEntity.ok(userDTO);
        } catch (Exception e) {
            System.out.println(userAuthDTO.getUsername());
            System.out.println(e.getMessage());
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie logoutCookie = jwtUtil.createLogoutCookie();
        response.addCookie(logoutCookie);
        return ResponseEntity.ok("Logged out successfully");
    }

    //need to implement

    //POST /api/auth/login

    //POST /api/auth/register
    
    //POST /api/auth/logout
}