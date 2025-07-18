package snowcoach.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import snowcoach.Model.User;
import snowcoach.Model.UserPrincipal;
import snowcoach.Repository.UserRepository;

// Needed for authentication with Spring Security
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException(username));
        return new UserPrincipal(user);
    }
}
