package darko.merli.Service.Implementation;

import darko.merli.Model.CommentDTOS.Comment;
import darko.merli.Model.CommentDTOS.CommentCreate;
import darko.merli.Model.CommentDTOS.CommentReturn;
import darko.merli.Model.VideoDTOS.Video;
import darko.merli.Repository.CommentRepository;
import darko.merli.Repository.UserRepository;
import darko.merli.Repository.VideoRepository;
import darko.merli.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    VideoRepository videoRepository;

    @Autowired
    UserServiceImpl userServiceImpl;

    @Override
    public CommentReturn createComment(long id, CommentCreate comment) {
        Optional<Video> video = videoRepository.findById(id);
        if(video.isPresent()) {
            Video video1 = video.get();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Comment comment1 = new Comment();
            comment1.setContent(comment.getContent());
            comment1.setUserComm(userRepository.findByUsername(auth.getName()).get());
            comment1.setVideoComm(video1);
            commentRepository.save(comment1);
            List<Comment> listOfComments = video1.getComments();
            listOfComments.add(comment1);
            video1.setComments(listOfComments);
            video1.setNoOfComments(listOfComments.size());
            videoRepository.save(video1);
            return commentToCommentReturn(comment1);

        } else {
            throw new IllegalArgumentException("Video with id " + id + " not found");
        }
    }

    public CommentReturn commentToCommentReturn(Comment comment) {
        CommentReturn commentReturn = new CommentReturn();
        commentReturn.setContent(comment.getContent());
        commentReturn.setUserName(comment.getUserComm().getUsername());
        return commentReturn;
    }
}
