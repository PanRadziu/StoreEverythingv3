package pl.storeeverything.store.security;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.storeeverything.store.repo.UserRepo;

import java.util.Collection;
import java.util.LinkedList;

@Service
public class UserSecurityService implements UserDetailsService {
    private UserRepo userRepo;
    public UserSecurityService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        pl.storeeverything.store.model.UserDetails user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        else{
            UserDetails userDetails = new User(user.getUsername(),
                    user.getPassword(),
                    user.getRoles());

            return userDetails;
        }

    }
}
