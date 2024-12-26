package darko.merli.Service;

import darko.merli.Model.ChannelDTOS.Channel;
import darko.merli.Model.ChannelDTOS.ChannelSearch;
import darko.merli.Model.UserDTOS.UserCreation;
import darko.merli.Model.UserDTOS.UserSearch;
import darko.merli.Model.UserDTOS.UserUpdate;
import darko.merli.Model.UserDTOS.Users;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public Users createUser(UserCreation users) throws IllegalAccessException;

    public UserSearch updateUser(UserUpdate user);

    public String deleteUser() throws IllegalAccessException;

    public UserSearch searchUser(String name);
}
