package darko.merli.Service;

import darko.merli.Model.UserDTOS.Users;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface UsersFromDB extends UserDetailsService {
    public Users getCurrentUser();
}
