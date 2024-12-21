package darko.merli.Controller;

import darko.merli.Model.Channel.Channel;
import darko.merli.Model.Channel.ChannelCreation;
import darko.merli.Model.Channel.ChannelSearch;
import darko.merli.Service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
public class ChannelController {

    @Autowired
    ChannelService channelService;

    @GetMapping("/@{name}")
    public ChannelSearch getChannel(@PathVariable String name) {
        return channelService.searchChannel(name);
    }

    @PostMapping("/createChannel")
    public void createChannel(@RequestBody ChannelCreation channel){
        Channel channel1 = channelService.createChannel(channel);
    }

    @DeleteMapping("/deleteChannel/{name}")
    public String deleteChannel(@PathVariable String name){
        return channelService.deleteChannel(name);
    }

}
