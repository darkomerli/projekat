package darko.merli.Service.Implementation;

import darko.merli.Model.Channel.Channel;
import darko.merli.Model.Channel.ChannelCreation;
import darko.merli.Model.Channel.ChannelSearch;
import darko.merli.Repository.ChannelRepository;
import darko.merli.Service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChannelServiceImpl implements ChannelService {
    @Autowired
    ChannelRepository channelRepository;


    public Channel createChannel(ChannelCreation creation){
        Channel channel;
        channel = channelCreationToChannel(creation);
        channelRepository.save(channel);
        return channel;
    }

    public ChannelSearch searchChannel(String name) {
        return channelToChannelSearch(channelRepository.findByName(name));
    }

    public String deleteChannel(String name){


    }

    public ChannelSearch channelToChannelSearch(Channel channel) {
        ChannelSearch searchedChannel = new ChannelSearch();
        searchedChannel.setChannelName(channel.getChannelName());
        searchedChannel.setSubscribers(channel.getSubscribers());
        return searchedChannel;
    }

    public Channel channelCreationToChannel(ChannelCreation creation){
        Channel channel = new Channel();
        channel.setChannelName(creation.getChannelName());
        channel.setSubscribers(0);
        return channel;
    }
}
