package snowcoach.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import snowcoach.Model.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    Privilege findByName(String name);
}