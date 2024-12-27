package darko.merli.Service;

import darko.merli.Model.VideoDTOS.VideoSearch;
import darko.merli.Model.VideoDTOS.VideoUpdate;
import darko.merli.Model.VideoDTOS.VideoUpload;
import org.springframework.stereotype.Service;

@Service
public interface VideoService {
    public String postVideo(String name, VideoUpload video) throws IllegalAccessException;

    public VideoSearch searchVideo(long id);

    public String deleteVideo(long id) throws IllegalAccessException;

    public VideoSearch updateVideo(long id, VideoUpdate video) throws IllegalAccessException;
}
