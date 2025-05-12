package snowcoach.Mapper;

import snowcoach.DTO.UserDTO;
import snowcoach.Model.User;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class UserMapper {

    // Convert User entity to UserDTO
    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setRoleNames(user.getRoles());
        return userDTO;
    }

    // Convert UserDTO to User entity
    public User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        //user.setRoles(userDTO.getRoles());
        return user;
    }
}