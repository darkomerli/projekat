package darko.merli.Service;

import darko.merli.Model.UserDTOS.Users;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UsersFromDB extends UserDetailsService {
    Users getCurrentUser();
}
