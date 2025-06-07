package snowcoach.Service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @PostConstruct
    public void init() {
        Privilege readPrivilege = new Privilege();
        readPrivilege.setName("READ");
        Privilege writePrivilege = new Privilege();
        writePrivilege.setName("WRITE");
        Privilege claimPrivilege = new Privilege();
        claimPrivilege.setName("CLAIM");
        Privilege adminPrivilege = new Privilege();
        adminPrivilege.setName("ADMIN");
        Privilege teach1Privilege = new Privilege();
        teach1Privilege.setName("TEACH1");
        Privilege teach2Privilege = new Privilege();
        teach2Privilege.setName("TEACH2");
        Privilege teach3Privilege = new Privilege();
        teach3Privilege.setName("TEACH3");


        privilegeRepository.saveAll(Arrays.asList(readPrivilege, writePrivilege, claimPrivilege, adminPrivilege, teach1Privilege, teach2Privilege, teach3Privilege));

        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        adminRole.setPrivileges(Arrays.asList(readPrivilege, writePrivilege, adminPrivilege, teach1Privilege, teach2Privilege, teach3Privilege, claimPrivilege));
        roleRepository.save(adminRole);

        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        userRole.setPrivileges(Arrays.asList(readPrivilege, writePrivilege));
        roleRepository.save(userRole);

        Role coach1Role = new Role();
        coach1Role.setName("ROLE_COACH1");
        coach1Role.setPrivileges(Arrays.asList(readPrivilege, writePrivilege, claimPrivilege, teach1Privilege));
        roleRepository.save(coach1Role);

        Role coach2Role = new Role();
        coach2Role.setName("ROLE_COACH2");
        coach2Role.setPrivileges(Arrays.asList(readPrivilege, writePrivilege, claimPrivilege, teach1Privilege, teach2Privilege));
        roleRepository.save(coach2Role);

        Role coach3Role = new Role();
        coach3Role.setName("ROLE_COACH3");
        coach3Role.setPrivileges(Arrays.asList(readPrivilege, writePrivilege, claimPrivilege, teach1Privilege, teach2Privilege, teach3Privilege));
        roleRepository.save(coach3Role);


        User coach2 = new User();
        coach2.setUsername("coach2");
        coach2.setPassword(encoder.encode("pass123"));
        coach2.setRoles(Arrays.asList(coach2Role));
        userRepository.save(coach2);

        User coach3 = new User();
        coach3.setUsername("coach3");
        coach3.setPassword(encoder.encode("pass123"));
        coach3.setRoles(Arrays.asList(coach3Role));
        userRepository.save(coach3);

        User athlete = new User();
        athlete.setUsername("athlete");
        athlete.setPassword(encoder.encode("pass123"));
        athlete.setRoles(Arrays.asList(userRole));
        userRepository.save(athlete);


        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(encoder.encode("admin123"));
        admin.setRoles(Arrays.asList(adminRole));
        userRepository.save(admin);
    }
}
