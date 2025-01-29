package darko.merli.Controller;

import darko.merli.Model.CommentDTOS.Comment;
import darko.merli.Model.CommentDTOS.CommentCreate;
import darko.merli.Model.UserDTOS.Users;
import darko.merli.Model.VideoDTOS.Video;
import darko.merli.Model.VideoDTOS.VideoSearch;
import darko.merli.Model.VideoDTOS.VideoUpdate;
import darko.merli.Model.VideoDTOS.VideoUpload;
import darko.merli.Repository.UserRepository;
import darko.merli.Repository.VideoRepository;
import darko.merli.Service.UserService;
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

import java.util.List;

@Controller
@Validated
@Tag(name="4. Videos")
//controller class which holds the apis for videos
public class VideoController {

    @Autowired
    private VideoService videoService;
    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Operation(summary = "Post a video to channel", description = "Post a video to the channel with selected name")
    @PostMapping("{name}/videos/post")
    public String postVideo(@PathVariable String name, @RequestBody VideoUpload video) throws IllegalAccessException {
        return videoService.postVideo(name, video);
    }

    @Operation(summary = "Find a video with id", description = "Find a video that has selected video id")
    @GetMapping("/videos/{id}")
    @SecurityRequirements
    public String getVideo(@PathVariable long id, Model model){
        boolean commentsCheck = false;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(!auth.getName().equals("anonymousUser")){
            Users user = userRepository.findByUsername(auth.getName()).get();
            long idOfUser = user.getUser_id();
            System.out.println(idOfUser);
            model.addAttribute("idOfUser", idOfUser);
        }
        List<Video> listOfVideos = videoRepository.findAll();
        model.addAttribute("listOfVideos", listOfVideos);
        model.addAttribute("MainId", id);
        Video video = videoRepository.findById(id).get();
        model.addAttribute("video", video);
        if(!video.getComments().isEmpty()){
            commentsCheck = true;
        }
        model.addAttribute("commentsCheck", commentsCheck);
        if(commentsCheck){
            List<Comment> commentList = video.getComments();
            model.addAttribute("comments", commentList);
            CommentCreate commentCreate = new CommentCreate();
            model.addAttribute("commentCreate", commentCreate);
        }
        videoService.searchVideo(id);
        return "video";
    }

    @Operation(summary = "Delete the video with id", description = "Delete the video with selected id")
    @DeleteMapping("/videos/{id}/delete")
    public String deleteVideo(@PathVariable long id) throws IllegalAccessException {
        return videoService.deleteVideo(id);
    }

    @Operation(summary = "Update the video with id", description = "Update the video with selected id")
    @PutMapping("/videos/{id}")
    public VideoSearch updateVideo(@PathVariable long id, @RequestBody VideoUpdate video) throws IllegalAccessException {
        return videoService.updateVideo(id, video);
    }

    @Operation(summary = "Like the video", description = "Like the video with the selected id")
    @PutMapping("/videos/{id}/like")
    public String likeVideo(@PathVariable long id) throws IllegalAccessException {
        return videoService.likeVideo(id);
    }

//    @Operation(summary = "Remove a like from video", description = "Remove a like from video with selected id")
//    @PutMapping("/videos/{id}/unlike")
//    public String unlikeVideo(@PathVariable long id) throws IllegalAccessException {
//        videoService.unlikeVideo(id);
//        return "redirect:/videos/" + id;
//    }

    @Operation(summary = "Remove a like from video", description = "Remove a like from video with selected id")
    @GetMapping("/videos/{id}/unlike")
    public String unlikeVideo(@PathVariable long id, Model model) throws IllegalAccessException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users user = userRepository.findByUsername(auth.getName()).get();
        long idUser = user.getUser_id();
        videoService.unlikeVideo(id);
        return "redirect:/users/"+idUser;
    }
}
