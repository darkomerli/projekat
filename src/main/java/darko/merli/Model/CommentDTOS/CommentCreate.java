package darko.merli.Model.CommentDTOS;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentCreate {
    private String content;
    private long video_id;
    private long user_id;
}
