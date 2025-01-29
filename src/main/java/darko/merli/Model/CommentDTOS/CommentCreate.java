package darko.merli.Model.CommentDTOS;

import lombok.Data;

@Data
//we send object of this class to create a comment
public class CommentCreate {
    public String content;
}
