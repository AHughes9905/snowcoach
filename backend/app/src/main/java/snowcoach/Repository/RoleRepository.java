package snowcoach.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import snowcoach.Model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
