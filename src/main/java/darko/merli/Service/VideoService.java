package darko.merli.Service;

import darko.merli.Model.VideoDTOS.VideoSearch;
import darko.merli.Model.VideoDTOS.VideoUpdate;
import darko.merli.Model.VideoDTOS.VideoUpload;
import org.springframework.stereotype.Service;

@Service
public interface VideoService {
    String postVideo(String name, VideoUpload video) throws IllegalAccessException;

    VideoSearch searchVideo(long id);

    String deleteVideo(long id) throws IllegalAccessException;

    VideoSearch updateVideo(long id, VideoUpdate video) throws IllegalAccessException;

    String likeVideo(long id) throws IllegalAccessException;

    String unlikeVideo(long id) throws IllegalAccessException;
}
