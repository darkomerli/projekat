package darko.merli.Service;

import darko.merli.Model.ChannelDTOS.Channel;
import darko.merli.Model.ChannelDTOS.ChannelCreation;
import darko.merli.Model.ChannelDTOS.ChannelSearch;
import darko.merli.Model.ChannelDTOS.ChannelUpdate;
import darko.merli.Model.UserDTOS.Users;
import org.springframework.stereotype.Service;

@Service
public interface ChannelService{


    Channel createChannel(ChannelCreation channel);

    ChannelSearch searchChannel(String name);

    String deleteChannel(String name) throws IllegalAccessException;

    ChannelSearch updateChannel(String name, ChannelUpdate channel) throws IllegalAccessException;

    String subscribeChannel(String name) throws IllegalAccessException;

    String unsubscribeChannel(String name) throws IllegalAccessException;

    ChannelSearch channelToChannelSearch(Channel channel);

    void unsubscribeChannels(Users user);

}
