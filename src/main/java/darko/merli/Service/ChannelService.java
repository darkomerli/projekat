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

    public String deleteChannel(String name);

    public ChannelSearch updateChannel(String name, ChannelUpdate channel);

    public String subscribeChannel(String name, long userId) throws IllegalAccessException;

    public String unsubscribeChannel(String name, long userId) throws IllegalAccessException;
}
