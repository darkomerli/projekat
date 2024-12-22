package darko.merli.Controller;

import darko.merli.Model.Channel.Channel;
import darko.merli.Model.Channel.ChannelCreation;
import darko.merli.Model.Channel.ChannelSearch;
import darko.merli.Model.Channel.ChannelUpdate;
import darko.merli.Service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
//controller class that hold the apis regarding the channels
public class ChannelController {

    @Autowired
    ChannelService channelService;

    //searching the channel by channel name
    @GetMapping("/@{name}")
    public ChannelSearch searchChannel(@PathVariable String name) {
        return channelService.searchChannel(name);
    }

    //creation of the channel, for now all users can create the channel
    @PostMapping("/createChannel")
    public String createChannel(@RequestBody ChannelCreation channel){
        channelService.createChannel(channel);
        return "Channel created successfully.";
    }

    //deletion of the channel, for now, all users can delete every channel
    @DeleteMapping("/deleteChannel/{name}")
    public String deleteChannel(@PathVariable String name){
        return channelService.deleteChannel(name);
    }

    //updating the channel data such as name and description
    @PutMapping("/updateChannel/{name}")
    public ChannelSearch updateChannel(@PathVariable String name, @RequestBody ChannelUpdate channel){
        return channelService.updateChannel(name, channel);
    }

    //subscribing to a channel
    @PutMapping("/@{name}/subscribe/{id}")
    public String subscribeToChannel(@PathVariable long id, @PathVariable String name) throws IllegalAccessException {
        return channelService.subscribeChannel(name, id);
    }
}
