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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        UserDTO user = userService.getUserDTOById(id);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("update/username/{id}")
    public ResponseEntity<UserDTO> updateUsername(@PathVariable Long id, @RequestBody String username) {
        UserDTO updatedUser = userService.updateUsername(id, username);
        return ResponseEntity.ok(updatedUser);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("update/password/{id}")
    public ResponseEntity<UserDTO> updatePassword(@PathVariable Long id, @RequestBody String password) {
        UserDTO updatedUser = userService.updatePassword(id, password);
        return ResponseEntity.ok(updatedUser);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("update/role/{id}")
    public ResponseEntity<UserDTO> updateRole(@PathVariable Long id, @RequestBody String role) {
        UserDTO updatedUser = userService.updateRole(id, role);
        return ResponseEntity.ok(updatedUser);
    }

}