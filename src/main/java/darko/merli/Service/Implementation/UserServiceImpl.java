package darko.merli.Service.Implementation;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import darko.merli.Model.ChannelDTOS.Channel;
import darko.merli.Model.ChannelDTOS.ChannelSearch;
import darko.merli.Model.UserDTOS.*;
import darko.merli.Repository.UserRepository;
import darko.merli.Service.ChannelService;
import darko.merli.Service.UserService;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        if(users.getUsername().equals("") || users.getPassword().equals("")) {
            throw new IllegalArgumentException("Username and password cannot be empty");
        }
        user.setUsername(users.getUsername());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(users.getPassword()));
        user.setCreated_at(LocalDateTime.now());
        user.setUpdated_at(LocalDateTime.now());
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
            userCurrent.setUpdated_at(LocalDateTime.now());
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

    public UserExport userToUserExport(Users user){
        UserExport userExport = new UserExport();
        userExport.setUserName(user.getUsername());
        //doing this because I have null values in db for created_At
        if(user.getCreated_at() == null){
            userExport.setCreatedAt(LocalDateTime.now());
        } else {
            userExport.setCreatedAt(user.getCreated_at());
        }
        //doing this because I have null values in db for updated_At
        if(user.getUpdated_at() == null){
            userExport.setUpdatedAt(LocalDateTime.now());
        } else {
            userExport.setUpdatedAt(user.getUpdated_at());
        }
        return userExport;
    }

    @Autowired
    Environment env;

    @Scheduled(cron = "0 0 * * * *")
    public File smallCron() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        String formatedDateTime = now.format(formatter);
        String path = env.getProperty("path.folder");
        File file = new File(path+"\\Users "+formatedDateTime+".csv");
        Writer writer = new FileWriter(file);
        StatefulBeanToCsv<UserExport> users = new StatefulBeanToCsvBuilder<UserExport>(writer).build();
        List<Users> list = userRepository.findAll();
        List<UserExport> converted = new ArrayList<>();
        for(Users user : list){
            UserExport userX = userToUserExport(user);
            converted.add(userX);
        }
        System.out.println(converted.size() +" "+ formatedDateTime);
        users.write(converted);
        writer.close();
        return file;
    }

    @Scheduled(cron = "0 * * * * *")
    public String newUsers() {
        int num = 0;
        List<Users> list = userRepository.findAll();
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();
        for(Users user : list){
            //doing this because I have null values in db for created_At
            if(user.getCreated_at() == null){
                continue;
            }
            if(user.getCreated_at().getHour() == 0){
                if(user.getCreated_at().isBefore(now) && user.getCreated_at().isAfter(LocalDateTime.of(year,month,day-1,hour-1,minute,second))){
                    num++;
                }
            } else {
                if(user.getCreated_at().isBefore(now) && user.getCreated_at().isAfter(LocalDateTime.of(year,month,day,hour-1,minute,second))){
                    num++;
                }
            }
        }
        return "Users created in the last hour: "+num;
    }
}
