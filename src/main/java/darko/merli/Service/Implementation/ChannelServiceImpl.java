package darko.merli.Service.Implementation;

import darko.merli.Model.ChannelDTOS.Channel;
import darko.merli.Model.ChannelDTOS.ChannelCreation;
import darko.merli.Model.ChannelDTOS.ChannelSearch;
import darko.merli.Model.ChannelDTOS.ChannelUpdate;
import darko.merli.Model.UserDTOS.User;
import darko.merli.Repository.ChannelRepository;
import darko.merli.Repository.UserRepository;
import darko.merli.Service.ChannelService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChannelServiceImpl implements ChannelService {

    @Autowired
    ChannelRepository channelRepository;
    @Autowired
    UserRepository userRepository;


    public Channel createChannel(ChannelCreation creation){
        Channel channel = channelCreationToChannel(creation);
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

    public String deleteChannel(String name){
        Channel channel = channelRepository.findByName(name);
        if(channel == null){
            throw new EntityNotFoundException("Channel not found");
        } else {
            channelRepository.delete(channelRepository.findByName(name));
            return "Channel deleted";
        }
    }

    public ChannelSearch updateChannel(String name, ChannelUpdate channelU){
        Optional<Channel> channel = channelRepository.findById(channelRepository.findByName(name).getId());
        boolean update = false;
        if(channel.isPresent()){
            Channel channelUpdated = channel.get();
            if(channelU.getChannelName() != null){
                update = true;
                channelUpdated.setChannelName(channelU.getChannelName());
            }
            if(channelU.getDescription() != null){
                update = true;
                channelUpdated.setDescription(channelU.getDescription());
            }
            if(update == true){
                channelRepository.save(channelUpdated);
                return channelToChannelSearch(channelUpdated);
            } else {
                throw new IllegalArgumentException("Please provide new channel name or description");
            }
        } else {
            throw new EntityNotFoundException("Channel not found");
        }
    }

    @Override
    public String subscribeChannel(String name, long userId) throws IllegalAccessException {
        Channel channel = channelRepository.findByName(name);
        List<User> users = channel.getUsers();
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            if(channel.getUser().getUser_id() == userId){
                throw new IllegalAccessException("You can't subscribe to your own channel");
            } else {
                if(users.contains(user.get())){
                    throw new IllegalAccessException("This user is already subscribed to this channel");
                } else {
                    users.add(user.get());
                    channel.setUsers(users);
                    List<Channel> listofChannels = user.get().getSubscribedChannelsList();
                    listofChannels.add(channel);
                    user.get().setSubscribedChannelsList(listofChannels);
                    channel.setSubscribers(channel.getSubscribers() + 1);
                    userRepository.save(user.get());
                    channelRepository.save(channel);
                    return "Successfully subscribed to channel " + name;
                }
            }
        } else {
            throw new EntityNotFoundException("User not found");
        }

    }

    @Override
    public String unsubscribeChannel(String name, long userId) throws IllegalAccessException {
        Channel channel = channelRepository.findByName(name);
        List<User> users = channel.getUsers();
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            if(channel.getUser().getUser_id() == userId){
                throw new IllegalAccessException("You can't unsubscribe from your own channel because you are not a subscriber");
            } else {
                if(users.contains(user.get())){
                    users.remove(user.get());
                    channel.setUsers(users);
                    List<Channel> listofChannels = user.get().getSubscribedChannelsList();
                    listofChannels.remove(channel);
                    user.get().setSubscribedChannelsList(listofChannels);
                    channel.setSubscribers(channel.getSubscribers() - 1);
                    userRepository.save(user.get());
                    channelRepository.save(channel);
                    return "User with ID: "+ user.get().getUser_id()+"Successfully unsubscribed from channel " + name;
                } else {
                    throw new IllegalAccessException("This user is not subscribed to this channel");
                }
            }
        } else {
            throw new EntityNotFoundException("User not found");
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
        if(chn != null){
            throw new EntityExistsException("Channel " + creation.getChannelName() + " already exists");
        }
        channel.setChannelName(creation.getChannelName());
        channel.setSubscribers(0);
        channel.setDescription(creation.getDescription());
        Optional<User> user = userRepository.findById(creation.getUser_id());
        if(user.isPresent()){
            channel.setUser(user.get());
        } else {
            throw new EntityNotFoundException("User with ID: " + creation.getUser_id() + " not found");
        }
        return channel;
    }
}
