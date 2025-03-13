package darko.merli.Controller;

import darko.merli.Model.ChannelDTOS.Channel;
import darko.merli.Model.ChannelDTOS.ChannelCreation;
import darko.merli.Model.ChannelDTOS.ChannelSearch;
import darko.merli.Model.ChannelDTOS.ChannelUpdate;
import darko.merli.Model.UserDTOS.Users;
import darko.merli.Model.VideoDTOS.Video;
import darko.merli.Model.VideoDTOS.VideoSearch;
import darko.merli.Repository.ChannelRepository;
import darko.merli.Repository.UserRepository;
import darko.merli.Repository.VideoRepository;
import darko.merli.Service.ChannelService;
import darko.merli.Service.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@Validated
@Tag(name = "1. Channels")
//controller class that hold the apis regarding the channels
public class ChannelController {

    @Autowired
    ChannelService channelService;
    @Autowired
    ChannelRepository channelRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    VideoRepository videoRepository;

    //searching the channel by channel name
    @Operation(summary = "Search the channel", description = "Search the channel by its name")
    @GetMapping("/channel/{name}")
    @SecurityRequirements
    public String searchChannel(@PathVariable String name, Model model) {
        boolean exists = false;
        Channel channelX = channelRepository.findByName(name);
        List<Channel> channels = new ArrayList<>();
        channels.add(channelX);
        model.addAttribute("channels", channels);
        List<Video> videos = channelX.getVideos();
        model.addAttribute("videos", videos);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username", auth.getName());
        if(!auth.getName().equals("anonymousUser")){
            List<Channel> listOfSubscribed = userRepository.findByUsername(auth.getName()).get().getSubscribedChannelsList();
            if(listOfSubscribed.contains(channelX)){
                exists = true;
            }
        }
        model.addAttribute("exists", exists);
        return "channel";
    }

    @Operation(summary = "Get to channel creation page", description = "Create the new channel by selecting its name and description.")
    @GetMapping("/channel/creation")
    public String createChannel(){
//        channelService.createChannel(channel);
        return "channelCreation";
    }

    @Operation(summary = "Create a new channel", description = "Create the new channel by selecting its name and description.")
    @PostMapping("/channel/create")
    public String createChannelData(@ModelAttribute("channelCreate") ChannelCreation channelCreate){
        channelService.createChannel(channelCreate);
        return "redirect:/channel/" + channelCreate.getChannelName();
    }

    //deletion of the channel
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
    @GetMapping("/channel/{name}/subscribe")
    public String subscribeToChannel(@PathVariable String name) throws IllegalAccessException {
        channelService.subscribeChannel(name);
        return "redirect:/channel/" + name;
    }

    //unsubscribing from a channel
    @Operation(summary = "Unsubscribe from the channel", description = "Unsubscribe from the channel by using channel name")
    @GetMapping("/channel/{name}/unsubscribe")
    public String unsubscribeFromChannel(@PathVariable String name) throws IllegalAccessException {
        channelService.unsubscribeChannel(name);
        return "redirect:/channel/"+name;
    }

    @Operation(summary = "Unsubscribe from the channel", description = "Unsubscribe from the channel by using channel name")
    @GetMapping("/channel/{name}/unsubscribeFrom")
    public String unsubscribedFromChannel(@PathVariable String name) throws IllegalAccessException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users user = userRepository.findByUsername(auth.getName()).get();
        long idUser = user.getUser_id();
        channelService.unsubscribeChannel(name);
        return "redirect:/users/"+idUser;
    }

    @Operation(summary = "Unsubscribe from the channel", description = "Unsubscribe from the channel by using channel name")
    @GetMapping("/channel/{name}/unsubscribeFromVideo/{videoId}")
    public String unsubscribedFromChannelInVideo(@PathVariable String name, @PathVariable long videoId) throws IllegalAccessException {
        channelService.unsubscribeChannel(name);
        Video video = videoRepository.findById(videoId).get();
        video.setViews(video.getViews() - 1);
        videoRepository.save(video);
        return "redirect:/videos/"+videoId;
    }

    @Operation(summary = "Unsubscribe from the channel", description = "Unsubscribe from the channel by using channel name")
    @GetMapping("/channel/{name}/subscribeFromVideo/{videoId}")
    public String subscribedFromChannelInVideo(@PathVariable String name, @PathVariable long videoId) throws IllegalAccessException {
        channelService.subscribeChannel(name);
        Video video = videoRepository.findById(videoId).get();
        video.setViews(video.getViews() - 1);
        videoRepository.save(video);
        return "redirect:/videos/"+videoId;
    }


}
