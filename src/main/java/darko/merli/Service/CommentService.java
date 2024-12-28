package darko.merli.Service;

import darko.merli.Model.CommentDTOS.CommentCreate;
import darko.merli.Model.CommentDTOS.CommentReturn;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    public CommentReturn createComment(long id, CommentCreate comment);
}
