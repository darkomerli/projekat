package darko.merli.Service.Implementation;

import darko.merli.Model.ChannelDTOS.Channel;
import darko.merli.Model.VideoDTOS.Video;
import darko.merli.Model.VideoDTOS.VideoSearch;
import darko.merli.Model.VideoDTOS.VideoUpload;
import darko.merli.Repository.ChannelRepository;
import darko.merli.Repository.UserRepository;
import darko.merli.Repository.VideoRepository;
import darko.merli.Service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private UserRepository userRepository;

    public String postVideo(String name, VideoUpload video) throws IllegalAccessException {
        Channel channel = channelRepository.findByName(name);
        if(channel == null){
            throw new IllegalArgumentException("Channel not found");
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(channel.getUser().getUser_id() != userRepository.findByUsername(auth.getName()).get().getUser_id()){
            throw new IllegalAccessException("You cannot upload to this channel.");
        }
        Video videoReal = uploadToVideo(video);
        videoReal.setPostedChannel(channel);
        videoRepository.save(videoReal);
        return "Video posted";
    }

    @Override
    public VideoSearch searchVideo(long id) {
        Optional<Video> video = videoRepository.findById(id);
        if(video.isPresent()){
            return videoToSearch(video.get());
        }
        else {
            throw new IllegalArgumentException("Video with id: "+ id +" not found");
        }
    }

    public VideoSearch videoToSearch(Video video){
        VideoSearch videoSearch = new VideoSearch();
        videoSearch.setTitle(video.getTitle());
        videoSearch.setDescription(video.getDescription());
        videoSearch.setVideoUrl(video.getVideoUrl());
        videoSearch.setLikes(video.getLikes());
        videoSearch.setDislikes(video.getDislikes());
        if(video.getComments() == null){
            videoSearch.setComments(null);
        } else {
            videoSearch.setComments(video.getComments());
        }
        videoSearch.setNoOfComments(video.getNoOfComments());
        return videoSearch;
    }

    public Video uploadToVideo(VideoUpload video) {
        Video videoReal = new Video();
        videoReal.setTitle(video.getTitle());
        videoReal.setDescription(video.getDescription());
        videoReal.setComments(null);
        videoReal.setNoOfComments(0);
        videoReal.setVideoUrl(video.getVideoUrl());
        videoReal.setLikes(0);
        videoReal.setDislikes(0);
        return videoReal;
    }
}
