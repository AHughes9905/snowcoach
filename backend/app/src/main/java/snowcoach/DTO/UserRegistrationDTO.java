package snowcoach.DTO;

import snowcoach.Model.Role;

import java.util.Collection;
import java.util.List;

public class UserRegistrationDTO {

    private String username;
    private String password;
    private Collection<Role> roles;

    public UserRegistrationDTO() {}

    public UserRegistrationDTO(String username, String password, Collection<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
