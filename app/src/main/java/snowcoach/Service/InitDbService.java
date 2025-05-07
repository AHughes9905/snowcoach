package snowcoach.Service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import snowcoach.Model.Privilege;
import snowcoach.Model.Role;
import snowcoach.Model.User;
import snowcoach.Repository.PrivilegeRepository;
import snowcoach.Repository.RoleRepository;
import snowcoach.Repository.UserRepository;


import java.util.Arrays;

@Service
public class InitDbService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @PostConstruct
    public void init() {
        Privilege readPrivilege = new Privilege();
        readPrivilege.setName("READ_PRIVILEGE");
        Privilege writePrivilege = new Privilege();
        writePrivilege.setName("WRITE_PRIVILEGE");
        privilegeRepository.saveAll(Arrays.asList(readPrivilege, writePrivilege));

        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        adminRole.setPrivileges(Arrays.asList(readPrivilege, writePrivilege));
        roleRepository.save(adminRole);

        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        userRole.setPrivileges(Arrays.asList(readPrivilege));
        roleRepository.save(userRole);

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("admin123");
        admin.setRoles(Arrays.asList(adminRole));
        userRepository.save(admin);
    }
}
