package darko.merli.Service.Implementation;

import darko.merli.Model.ChannelDTOS.Channel;
import darko.merli.Model.ChannelDTOS.ChannelSearch;
import darko.merli.Model.UserDTOS.UserCreation;
import darko.merli.Model.UserDTOS.UserSearch;
import darko.merli.Model.UserDTOS.UserUpdate;
import darko.merli.Model.UserDTOS.Users;
import darko.merli.Repository.UserRepository;
import darko.merli.Service.ChannelService;
import darko.merli.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChannelService channelService;

    public Users createUser(UserCreation users) {
        Users user = new Users();
        user.setUsername(users.getUsername());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(users.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Override
    public UserSearch updateUser(UserUpdate user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users userCurrent = userRepository.findByUsername(auth.getName()).get();
        boolean update = false;
        if(user.getUsername() != null && !user.getUsername().equals("")){
            userCurrent.setUsername(user.getUsername());
            System.out.println("Updated username.");
            update = true;
        }
        if(user.getPassword() != null && !user.getPassword().equals("")){
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            userCurrent.setPassword(encoder.encode(user.getPassword()));
            System.out.println("Updated password.");
            update = true;
        }
        if(update){
            userRepository.save(userCurrent);
            return userToUserSearch(userCurrent);
        } else {
            throw new IllegalArgumentException("Please provide a valid username and password.");
        }
    }

    public String deleteUser() throws IllegalAccessException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users userCurrent = userRepository.findByUsername(auth.getName()).get();
        List<Channel> channelList = userCurrent.getChannelList();
        for(Channel channel : channelList){
            channelService.deleteChannel(channel.getChannelName());
        }
        userRepository.delete(userCurrent);
        return "You have successfully deleted your account!";
    }

    public UserSearch searchUser(String name){
        Optional<Users> user = userRepository.findByUsername(name);
        if(user.isPresent()){
            return userToUserSearch(user.get());
        }
        else {
            throw new IllegalArgumentException("Couldn't find user with name: " + name);
        }
    }

    public UserSearch userToUserSearch(Users user){
        UserSearch userSearch = new UserSearch();
        userSearch.setUsername(user.getUsername());
        List<Channel> channelListMain = user.getChannelList();
        System.out.println(channelListMain.size());
        List<ChannelSearch> channelListSearch = new ArrayList<>();
        if(channelListMain == null){
            userSearch.setChannelList(channelListSearch);
            return userSearch;
        } else {
            for(Channel channel : channelListMain){
                ChannelSearch channels = channelService.channelToChannelSearch(channel);
                channelListSearch.add(channels);
                System.out.println(channelListSearch.size());
            }
            userSearch.setChannelList(channelListSearch);
            return userSearch;
        }

    }
}
