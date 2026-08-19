package darko.merli.Service;

import darko.merli.Model.UserDTOS.Users;
import darko.merli.Model.VideoDTOS.Video;
import darko.merli.Model.VideoDTOS.VideoSearch;
import darko.merli.Model.VideoDTOS.VideoUpdate;
import darko.merli.Model.VideoDTOS.VideoUpload;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VideoService {
    String postVideo(String name, VideoUpload video, MultipartFile file, MultipartFile thumbnail) throws IllegalAccessException, IOException;

    VideoSearch searchVideo(long id);

    String deleteVideo(long id) throws IllegalAccessException;

    VideoSearch updateVideo(long id, VideoUpdate video) throws IllegalAccessException;

    String likeVideo(long id) throws IllegalAccessException;

    String unlikeVideo(long id) throws IllegalAccessException;

    void unlikeVideos(Users userCurrent);

    List<Video> searchVideosByKeyword(String keyword);

    Video getVideoById(long id);
}