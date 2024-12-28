package darko.merli.Controller;

import darko.merli.Model.VideoDTOS.VideoSearch;
import darko.merli.Model.VideoDTOS.VideoUpdate;
import darko.merli.Model.VideoDTOS.VideoUpload;
import darko.merli.Service.VideoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@Tag(name="4. Videos")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @PostMapping("{name}/videos/post")
    public String postVideo(@PathVariable String name, @RequestBody VideoUpload video) throws IllegalAccessException {
        return videoService.postVideo(name, video);
    }

    @GetMapping("/videos/{id}")
    @SecurityRequirements
    public VideoSearch getVideo(@PathVariable long id){
        return videoService.searchVideo(id);
    }

    @DeleteMapping("/videos/{id}/delete")
    public String deleteVideo(@PathVariable long id) throws IllegalAccessException {
        return videoService.deleteVideo(id);
    }

    @PutMapping("/videos/{id}")
    public VideoSearch updateVideo(@PathVariable long id, @RequestBody VideoUpdate video) throws IllegalAccessException {
        return videoService.updateVideo(id, video);
    }

    @PutMapping("/videos/{id}/like")
    public String likeVideo(@PathVariable long id) throws IllegalAccessException {
        return videoService.likeVideo(id);
    }

    @PutMapping("/videos/{id}/unlike")
    public String unlikeVideo(@PathVariable long id) throws IllegalAccessException {
        return videoService.unlikeVideo(id);
    }
}
