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
    //need to implement

    //POST /api/auth/login

    //POST /api/auth/register
    
    //POST /api/auth/logout
}