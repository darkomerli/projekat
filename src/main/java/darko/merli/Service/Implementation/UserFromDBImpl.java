package darko.merli.Service.Implementation;

import darko.merli.Repository.UserRepository;
import darko.merli.Service.UsersFromDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import darko.merli.Model.UserDTOS.Users;

@Service
public class UserFromDBImpl implements UsersFromDB {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users userd = userRepository.findByUsername(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return User.builder().username(userd.getUsername()).password(userd.getPassword()).build();
    }

    public Users getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(auth.getName()).get();
    }
}
