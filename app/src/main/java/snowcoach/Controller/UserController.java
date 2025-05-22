package snowcoach.Controller;
import org.springframework.security.access.prepost.PreAuthorize;
import snowcoach.DTO.UserDTO;
import snowcoach.DTO.UserAuthDTO;
import snowcoach.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        UserDTO user = userService.getUserDTOById(id);
        return ResponseEntity.ok(user);
    }

    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        System.out.println("Getting all users");
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserAuthDTO userAuthDTO) {
        UserDTO updatedUser = userService.updateUser(id, userAuthDTO);
        return ResponseEntity.ok(updatedUser);
    }

}