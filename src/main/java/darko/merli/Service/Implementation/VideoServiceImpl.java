package darko.merli.Service.Implementation;

import darko.merli.Model.ChannelDTOS.Channel;
import darko.merli.Model.CommentDTOS.Comment;
import darko.merli.Model.CommentDTOS.CommentReturn;
import darko.merli.Model.UserDTOS.Users;
import darko.merli.Model.VideoDTOS.Video;
import darko.merli.Model.VideoDTOS.VideoSearch;
import darko.merli.Model.VideoDTOS.VideoUpdate;
import darko.merli.Model.VideoDTOS.VideoUpload;
import darko.merli.Repository.ChannelRepository;
import darko.merli.Repository.UserRepository;
import darko.merli.Repository.VideoRepository;
import darko.merli.Service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentServiceImpl commentService;

    //upload a video to selected channel name
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

    //search the video with id
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

    //delete the video with id
    @Override
    public String deleteVideo(long id) throws IllegalAccessException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Video> video = videoRepository.findById(id);
        if(video.isPresent()){
            Video videoReal = video.get();
            long idOfUser = videoReal.getPostedChannel().getUser().getUser_id();
            if(idOfUser == userRepository.findByUsername(auth.getName()).get().getUser_id()){
                videoRepository.deleteFromDatabase(videoReal.getId());
                videoRepository.deleteFromComments(videoReal.getId());
                videoRepository.delete(videoReal);
                return "Video with id "+ id +" deleted";
            } else {
                throw new IllegalAccessException("You cannot delete this video. You are not the owner of this channel.");
            }
        } else {
            throw new IllegalArgumentException("Video with id: "+ id +" not found");
        }
    }

    //update the video with id
    @Override
    public VideoSearch updateVideo(long id, VideoUpdate video) throws IllegalAccessException {
        Optional<Video> videoReal = videoRepository.findById(id);
        if(videoReal.isPresent()){
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Video video1 = videoReal.get();
            long idOfUser = video1.getPostedChannel().getUser().getUser_id();
            if(idOfUser == userRepository.findByUsername(auth.getName()).get().getUser_id()){
                boolean update = false;
                if(video.getDescription() != null && !video.getDescription().equals("")){
                    update = true;
                    video1.setDescription(video.getDescription());
                }
                if(video.getTitle() != null && !video.getTitle().equals("")){
                    update = true;
                    video1.setTitle(video.getTitle());
                }
                if(update){
                    videoRepository.save(video1);
                    return videoToSearch(video1);
                } else {
                    throw new IllegalArgumentException("Please provide a new title or description.");
                }

            }
            else {
                throw new IllegalAccessException("You cannot update this video. You are not the owner of this channel.");
            }
        } else {
            throw new IllegalArgumentException("Video with id: "+ id +" not found");
        }
    }

    //like the video with id
    @Override
    public String likeVideo(long id) throws IllegalAccessException {
        Optional<Video> video = videoRepository.findById(id);
        if(video.isPresent()){
            Video videoReal = video.get();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Users user = userRepository.findByUsername(auth.getName()).get();
            List<Users> listOfUsers = videoReal.getUsers();
            if(listOfUsers.contains(user)){
                throw new IllegalAccessException("You have already liked this video.");
            } else {
                listOfUsers.add(user);
                videoReal.setUsers(listOfUsers);
                List<Video> listOfVideos = user.getLikedVideoList();
                listOfVideos.add(videoReal);
                videoReal.setLikes(listOfUsers.size());
                user.setLikedVideoList(listOfVideos);
                videoRepository.save(videoReal);
                userRepository.save(user);
                return "Video liked";
            }
        }
        else {
            throw new IllegalArgumentException("Video with id: "+ id +" not found");
        }
    }

    //remove the like from video with id
    @Override
    public String unlikeVideo(long id) throws IllegalAccessException {
        Optional<Video> video = videoRepository.findById(id);
        if(video.isPresent()){
            Video videoReal = video.get();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Users user = userRepository.findByUsername(auth.getName()).get();
            List<Users> listOfUsers = videoReal.getUsers();
            if(listOfUsers.contains(user)){
                listOfUsers.remove(user);
                videoReal.setUsers(listOfUsers);
                List<Video> listOfVideos = user.getLikedVideoList();
                listOfVideos.remove(videoReal);
                user.setLikedVideoList(listOfVideos);
                videoReal.setLikes(listOfUsers.size());
                videoRepository.save(videoReal);
                userRepository.save(user);
                return "Video unliked";
            } else {
                throw new IllegalAccessException("You have not liked this video.");
            }
        } else {
            throw new IllegalArgumentException("Video with id: "+ id +" not found");
        }
    }

    //object mapping, Video -> VideoSearch
    public VideoSearch videoToSearch(Video video){
        VideoSearch videoSearch = new VideoSearch();
        videoSearch.setTitle(video.getTitle());
        videoSearch.setDescription(video.getDescription());
        videoSearch.setVideoUrl(video.getVideoUrl());
        videoSearch.setLikes(video.getLikes());
        if(video.getComments() == null){
            videoSearch.setComments(null);
        } else {
            List<Comment> list = video.getComments();
            List<CommentReturn> listReturn = new ArrayList<>();
            for(Comment comment : list){
                listReturn.add(commentService.commentToCommentReturn(comment));
            }
            videoSearch.setComments(listReturn);
        }
        videoSearch.setNoOfComments(video.getNoOfComments());
        return videoSearch;
    }

    //object mapping: VideoUpload -> Video
    public Video uploadToVideo(VideoUpload video) {
        Video videoReal = new Video();
        videoReal.setTitle(video.getTitle());
        videoReal.setDescription(video.getDescription());
        videoReal.setComments(null);
        videoReal.setNoOfComments(0);
        videoReal.setVideoUrl(video.getVideoUrl());
        videoReal.setLikes(0);
        return videoReal;
    }
}
