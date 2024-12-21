package darko.merli.Service;

import darko.merli.Model.Channel.Channel;
import darko.merli.Model.Channel.ChannelCreation;
import darko.merli.Model.Channel.ChannelSearch;
import org.springframework.stereotype.Service;

@Service
public interface ChannelService{


    Channel createChannel(ChannelCreation channel);

    ChannelSearch searchChannel(String name);

    String deleteChannel(String name);
}
