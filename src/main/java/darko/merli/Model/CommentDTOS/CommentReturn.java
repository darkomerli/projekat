package darko.merli.Model.CommentDTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//when we display a comment as a return value we use this class to show its content and userName only
public class CommentReturn {
    private String content;
    private String userName;
}
