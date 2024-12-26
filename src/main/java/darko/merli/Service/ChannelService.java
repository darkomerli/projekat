package darko.merli.Service;

import darko.merli.Model.ChannelDTOS.Channel;
import darko.merli.Model.ChannelDTOS.ChannelCreation;
import darko.merli.Model.ChannelDTOS.ChannelSearch;
import darko.merli.Model.ChannelDTOS.ChannelUpdate;
import org.springframework.stereotype.Service;

@Service
public interface ChannelService{


    public Channel createChannel(ChannelCreation channel);

    public ChannelSearch searchChannel(String name);

    public String deleteChannel(String name) throws IllegalAccessException;

    public ChannelSearch updateChannel(String name, ChannelUpdate channel) throws IllegalAccessException;

    public String subscribeChannel(String name) throws IllegalAccessException;

    public String unsubscribeChannel(String name) throws IllegalAccessException;

    public ChannelSearch channelToChannelSearch(Channel channel);

}
