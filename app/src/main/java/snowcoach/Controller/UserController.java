package snowcoach.Controller;
import snowcoach.DTO.UserDTO;
import snowcoach.DTO.UserAuthDTO;
import snowcoach.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //need to change response from UserDTO to different type of DTO
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        UserDTO user = userService.getUserDTOById(id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    //add put /api/users/{id}/update
    @PutMapping("/{id}/update")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserAuthDTO userAuthDTO) {
        UserDTO updatedUser = userService.updateUser(id, userAuthDTO);
        return ResponseEntity.ok(updatedUser);
    }

}