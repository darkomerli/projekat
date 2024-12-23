package darko.merli.Service.Implementation;

import darko.merli.Model.UserDTOS.UserCreation;
import darko.merli.Model.UserDTOS.Users;
import darko.merli.Repository.UserRepository;
import darko.merli.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    public Users createUser(UserCreation users){
        Users user = new Users();
        user.setUsername(users.getUsername());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(users.getPassword()));
        userRepository.save(user);
        return user;
    }
}
