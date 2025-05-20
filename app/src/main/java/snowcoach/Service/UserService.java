package snowcoach.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import snowcoach.DTO.UserAuthDTO;
import snowcoach.DTO.UserDTO;
import snowcoach.DTO.UserRegistrationDTO;
import snowcoach.Model.User;
import snowcoach.Mapper.UserMapper;
import snowcoach.Repository.UserRepository;
import org.springframework.stereotype.Service;
import snowcoach.Util.JwtUtil;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;;
    private JwtUtil jwtUtil;

    public BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    public UserService(UserRepository userRepository, UserMapper userMapper, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public void createUser(UserRegistrationDTO userRegistrationDTO) {
        User user = new User();
        user.setUsername(userRegistrationDTO.getUsername());
        user.setPassword(encoder.encode(userRegistrationDTO.getPassword()));
        user.setRoles(userRegistrationDTO.getRoles());
        userRepository.save(user);
        return;
    }

    public UserDTO getUserDTOById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        System.out.println("got user info");
        return userMapper.toDTO(user);
    }

    public UserDTO getUserDTOByName(String name) {
        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDTO(user);
    }

    public User getUserByName(String name) {
        return userRepository.findByUsername(name)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }

    public UserDTO updateUser(Long id, UserAuthDTO userAuthDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(userAuthDTO.getUsername());
        user.setPassword(encoder.encode(userAuthDTO.getPassword()));
        User updatedUser = userRepository.save(user);
        return userMapper.toDTO(updatedUser);
    }

    public String verifyUser(UserAuthDTO userAuthDTO) {
        User user = new User();
        user.setUsername(userAuthDTO.getUsername());
        user.setPassword(userAuthDTO.getPassword());
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        if (authentication.isAuthenticated()) {
            return jwtUtil.generateToken(userAuthDTO.getUsername());
        }
        return "Not verified";

    }

}
