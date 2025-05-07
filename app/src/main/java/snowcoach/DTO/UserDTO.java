package snowcoach.DTO;

import snowcoach.Model.Role;

import java.util.Collection;
import java.util.List;

public class UserDTO {

    private String username;
    private Collection<Role> roles;
    private Long id;

    public UserDTO() {}

    public UserDTO(String username, Collection<Role> roles, Long id) {
        this.id = id;
        this.username = username;
        this.roles = this.roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
