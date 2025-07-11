package snowcoach.DTO;

import snowcoach.Model.Role;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDTO {

    private String username;
    private Collection<String> roleNames;
    private Long id;

    public UserDTO() {}

    public UserDTO(String username, Collection<String> roleNames, Long id) {
        this.id = id;
        this.username = username;
        this.roleNames = roleNames;
    }

    public static Collection<String> extractRoleNames(Collection<Role> roles) {
        return roles.stream()
                .map(Role::getName) // Extract the name of each role
                .map(String::toUpperCase) // Convert names to uppercase (optional, for consistency)
                .collect(Collectors.toList()); // Collect names into a collection
    }

        public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Collection<String> getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(Collection<Role> roles) {
        this.roleNames = extractRoleNames(roles);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public void setRoleNames(Collection<String> roleNames) {
//        this.roleNames = roleNames;
//    }
//
//    public List<String> getRoleNames() {
//        this.roleNames = extractRoleNames(roles);
//    }
}
