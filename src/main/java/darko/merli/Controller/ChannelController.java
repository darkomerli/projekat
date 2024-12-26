package darko.merli.Controller;

import darko.merli.Model.ChannelDTOS.ChannelCreation;
import darko.merli.Model.ChannelDTOS.ChannelSearch;
import darko.merli.Model.ChannelDTOS.ChannelUpdate;
import darko.merli.Service.ChannelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@Tag(name = "1. Channels")
//controller class that hold the apis regarding the channels
public class ChannelController {

    @Autowired
    ChannelService channelService;

    //searching the channel by channel name
    @Operation(summary = "Search the channel", description = "Search the channel by its name")
    @GetMapping("/channel/@{name}")
    public ChannelSearch searchChannel(@PathVariable String name) {
        return channelService.searchChannel(name);
    }

    @Operation(summary = "Create a new channel", description = "Create the new channel by selecting its name and description.")
    @PostMapping("/channel/createChannel")
    public String createChannel(@RequestBody ChannelCreation channel){
        channelService.createChannel(channel);
        return "Channel created successfully.";
    }

    //deletion of the channel, for now, all users can delete every channel
    @Operation(summary = "Delete the channel", description = "Delete the channel by typing in channel name.")
    @DeleteMapping("/channel/deleteChannel/{name}")
    public String deleteChannel(@PathVariable String name) throws IllegalAccessException {
        return channelService.deleteChannel(name);
    }

    //updating the channel data such as name and description
    @Operation(summary = "Update the channel", description = "Update channel name or description")
    @PutMapping("/channel/updateChannel/{name}")
    public ChannelSearch updateChannel(@PathVariable String name, @RequestBody ChannelUpdate channel) throws IllegalAccessException {
        return channelService.updateChannel(name, channel);
    }

    //subscribing to a channel
    @Operation(summary = "Subscribe to the channel", description = "Subscribe to the channel with channel name")
    @PutMapping("/channel/@{name}/subscribe")
    public String subscribeToChannel(@PathVariable String name) throws IllegalAccessException {
        return channelService.subscribeChannel(name);
    }

    //unsubscribing from a channel
    @Operation(summary = "Unsubscribe from the channel", description = "Unsubscribe from the channel by using channel name")
    @PutMapping("/channel/@{name}/unsubscribe")
    public String unsubscribeFromChannel(@PathVariable String name) throws IllegalAccessException {
        return channelService.unsubscribeChannel(name);
    }
}
