package snowcoach.DTO;

import java.util.List;

public class UserDTO {

    private String username;
    private List<String> roles;
    private Long id;

    public UserDTO() {}

    public UserDTO(String username, List<String> role, Long id) {
        this.id = id;
        this.username = username;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
