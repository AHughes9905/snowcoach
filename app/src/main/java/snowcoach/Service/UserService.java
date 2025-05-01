package snowcoach.Service;

import snowcoach.DTO.UserDTO;
import snowcoach.DTO.UserLoginDTO;
import snowcoach.Model.User;
import snowcoach.Mapper.UserMapper;
import snowcoach.Repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDTO(user);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }

    public UserDTO updateUser(Long id, UserLoginDTO userLoginDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(userLoginDTO.getUsername());
        user.setPassword(userLoginDTO.getPassword()); 
        User updatedUser = userRepository.save(user);
        return userMapper.toDTO(updatedUser);
    }
}
