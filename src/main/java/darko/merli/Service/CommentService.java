package darko.merli.Service;

import darko.merli.Model.CommentDTOS.Comment;
import darko.merli.Model.CommentDTOS.CommentCreate;
import darko.merli.Model.CommentDTOS.CommentReturn;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    CommentReturn createComment(long id, CommentCreate comment);

    String deleteComment(long id, long ids) throws IllegalAccessException;

    void deleteOneComment(List<Comment> comment);
}
