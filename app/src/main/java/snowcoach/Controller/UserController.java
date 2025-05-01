package snowcoach.Controller;
import snowcoach.DTO.UserDTO;
import snowcoach.DTO.UserLoginDTO;
import snowcoach.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    //add put /api/users/{id}/update
    @PutMapping("/{id}/update")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserLoginDTO userLoginDTO) {
        UserDTO updatedUser = userService.updateUser(id, userLoginDTO);
        return ResponseEntity.ok(updatedUser);
    }

}