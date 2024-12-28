package darko.merli.Service.Implementation;

import darko.merli.Model.ChannelDTOS.Channel;
import darko.merli.Model.ChannelDTOS.ChannelCreation;
import darko.merli.Model.ChannelDTOS.ChannelSearch;
import darko.merli.Model.ChannelDTOS.ChannelUpdate;
import darko.merli.Model.UserDTOS.Users;
import darko.merli.Repository.ChannelRepository;
import darko.merli.Repository.UserRepository;
import darko.merli.Service.ChannelService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChannelServiceImpl implements ChannelService {

    @Autowired
    ChannelRepository channelRepository;
    @Autowired
    UserRepository userRepository;

//    Authentication auth = SecurityContextHolder.getContext().getAuthentication();


    public Channel createChannel(ChannelCreation creation){
        Channel channel = channelCreationToChannel(creation);
        if(creation.getChannelName().equals("") || creation.getDescription().equals("")){
            throw new IllegalArgumentException("Please provide a valid name and description");
        }
        channelRepository.save(channel);
        return channel;
    }

    public ChannelSearch searchChannel(String name) {
        Channel channel = channelRepository.findByName(name);
        if(channel == null){
            throw new EntityNotFoundException("Channel not found");
        } else {
            return channelToChannelSearch(channel);
        }
    }

    public String deleteChannel(String name) throws IllegalAccessException {
        Channel channel = channelRepository.findByName(name);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(channel == null){
            throw new EntityNotFoundException("Channel not found");
        } else {
            if(channel.getUser().getUser_id() == userRepository.findByUsername(auth.getName()).get().getUser_id()){
                channelRepository.deleteFromVideos(channelRepository.findByName(name).getId());
                channelRepository.delete(channelRepository.findByName(name));
                return "Channel "+channel.getChannelName()+" deleted";
            } else {
                throw new IllegalAccessException("You are not the owner of this channel, therefore, you cannot delete it.");
            }
        }
    }

    public ChannelSearch updateChannel(String name, ChannelUpdate channelU) throws IllegalAccessException {
        Optional<Channel> channel = channelRepository.findById(channelRepository.findByName(name).getId());
        boolean update = false;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(channel.isPresent()){
            if(channel.get().getUser().getUser_id() != userRepository.findByUsername(auth.getName()).get().getUser_id()){
                throw new IllegalAccessException("You are not the owner of this channel, therefore, you cannot update it.");
            } else {
                Channel channelUpdated = channel.get();
                if(channelU.getChannelName() != null && !channelU.getChannelName().equals("")){
                    update = true;
                    channelUpdated.setChannelName(channelU.getChannelName());
                }
                if(channelU.getDescription() != null && !channelU.getDescription().equals("")){
                    update = true;
                    channelUpdated.setDescription(channelU.getDescription());
                }
                if(update){
                    channelRepository.save(channelUpdated);
                    return channelToChannelSearch(channelUpdated);
                } else {
                    throw new IllegalArgumentException("Please provide new channel name or description");
                }
            }
        } else {
            throw new EntityNotFoundException("Channel not found");
        }
    }

    @Override
    public String subscribeChannel(String name) throws IllegalAccessException {
        Channel channel = channelRepository.findByName(name);
        if(channel == null){
            throw new EntityNotFoundException("Channel: "+ name +" not found");
        }
        List<Users> users = channel.getUsers();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users user = userRepository.findById(userRepository.findByUsername(auth.getName()).get().getUser_id()).get();
        if(channel.getUser().getUser_id() == user.getUser_id()){
            throw new IllegalAccessException("You can't subscribe to your own channel");
        } else {
            if (users.contains(user)) {
                throw new IllegalAccessException("You are already subscribed to this channel");
            } else {
                users.add(user);
                channel.setUsers(users);
                List<Channel> listofChannels = user.getSubscribedChannelsList();
                listofChannels.add(channel);
                user.setSubscribedChannelsList(listofChannels);
                channel.setSubscribers(channel.getSubscribers() + 1);
                userRepository.save(user);
                channelRepository.save(channel);
                return "Successfully subscribed to channel " + name;
            }
        }
    }

    @Override
    public String unsubscribeChannel(String name) throws IllegalAccessException {
        Channel channel = channelRepository.findByName(name);
        if(channel == null){
            throw new EntityNotFoundException("Channel: "+ name +" not found");
        }
        List<Users> users = channel.getUsers();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users user = userRepository.findById(userRepository.findByUsername(auth.getName()).get().getUser_id()).get();
        if(channel.getUser().getUser_id() == user.getUser_id()){
            throw new IllegalAccessException("You can't unsubscribe from your own channel because you are not a subscriber");
        } else {
            if(users.contains(user)){
                users.remove(user);
                channel.setUsers(users);
                List<Channel> listofChannels = user.getSubscribedChannelsList();
                listofChannels.remove(channel);
                user.setSubscribedChannelsList(listofChannels);
                channel.setSubscribers(channel.getSubscribers() - 1);
                userRepository.save(user);
                channelRepository.save(channel);
                return "You have successfully unsubscribed from channel " + name;
            } else {
                throw new IllegalAccessException("You are not subscribed to this channel");
            }
        }
    }

    //channel to searched channel
    public ChannelSearch channelToChannelSearch(Channel channel) {
        ChannelSearch searchedChannel = new ChannelSearch();
        searchedChannel.setChannelName(channel.getChannelName());
        searchedChannel.setSubscribers(channel.getSubscribers());
        searchedChannel.setUser_id(channel.getUser().getUser_id());
        searchedChannel.setDescription(channel.getDescription());
        return searchedChannel;
    }

    //created channel class to channel class
    public Channel channelCreationToChannel(ChannelCreation creation){
        Channel channel = new Channel();
        Channel chn = channelRepository.findByName(creation.getChannelName());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(chn != null){
            throw new EntityExistsException("Channel " + creation.getChannelName() + " already exists");
        }
        channel.setChannelName(creation.getChannelName());
        channel.setSubscribers(0);
        channel.setDescription(creation.getDescription());
        Optional<Users> user = userRepository.findById(userRepository.findByUsername(auth.getName()).get().getUser_id());
        channel.setUser(user.get());
        return channel;
    }
}
