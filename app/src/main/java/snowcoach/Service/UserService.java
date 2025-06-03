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
import snowcoach.Repository.RoleRepository;
import snowcoach.Repository.UserRepository;
import org.springframework.stereotype.Service;
import snowcoach.Util.JwtUtil;

import java.util.Collections;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final JwtUtil jwtUtil;

    public BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    public UserService(UserRepository userRepository, UserMapper userMapper, AuthenticationManager authenticationManager, JwtUtil jwtUtil, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.roleRepository = roleRepository;
    }

    public boolean createUser(UserAuthDTO userAuthDTO) {
        if (userRepository.findByUsername(userAuthDTO.getUsername()).isPresent()) {
            return false;
        }
        User user = new User();
        user.setUsername(userAuthDTO.getUsername());
        user.setPassword(encoder.encode(userAuthDTO.getPassword()));

        user.setRoles(new HashSet<>(Set.of(roleRepository.findByName("USER"))));
        userRepository.save(user);
        return true;
    }

    public UserDTO getUserDTOById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDTO(user);
    }

    public UserDTO getUserDTOByName(String name) {
        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDTO(user);
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toDTO).toList();
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

    public UserDTO updateUsername(Long id, String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already taken");
        }
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(username);
        User updatedUser = userRepository.save(user);
        return userMapper.toDTO(updatedUser);
    }

    public UserDTO updatePassword(Long id, String password) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(encoder.encode(password));
        User updatedUser = userRepository.save(user);
        return userMapper.toDTO(updatedUser);
    }

    public UserDTO updateRole(Long id, String role) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRoles(new HashSet<>(Set.of(roleRepository.findByName(role))));
        User updatedUser = userRepository.save(user);
        return userMapper.toDTO(updatedUser);
    }

    public String verifyUser(UserAuthDTO userAuthDTO) {
        User user = new User();
        user.setUsername(userAuthDTO.getUsername());
        user.setPassword(userAuthDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        if (authentication.isAuthenticated()) {
            return jwtUtil.generateToken(userAuthDTO.getUsername());
        }
        return "Not verified";

    }

}