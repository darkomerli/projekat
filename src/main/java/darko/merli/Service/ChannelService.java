package darko.merli.Service;

import darko.merli.Model.Channel.Channel;
import darko.merli.Model.Channel.ChannelCreation;
import darko.merli.Model.Channel.ChannelSearch;
import darko.merli.Model.Channel.ChannelUpdate;
import org.springframework.stereotype.Service;

@Service
public interface ChannelService{


    public Channel createChannel(ChannelCreation channel);

    public ChannelSearch searchChannel(String name);

    public String deleteChannel(String name);

    public ChannelSearch updateChannel(String name, ChannelUpdate channel);

    public String subscribeChannel(String name, long userId) throws IllegalAccessException;
}
