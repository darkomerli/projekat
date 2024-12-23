package darko.merli.Service;

import darko.merli.Model.UserDTOS.UserCreation;
import darko.merli.Model.UserDTOS.Users;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public Users createUser(UserCreation users);
}
