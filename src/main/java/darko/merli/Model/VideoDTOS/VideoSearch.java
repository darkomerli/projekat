package darko.merli.Model.VideoDTOS;

import darko.merli.Model.CommentDTOS.CommentReturn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
//we use object of this class to search for a video
public class VideoSearch {

    private String title;
    private String description;
    private String videoUrl;
    private long likes;
    private long noOfComments;
    private long views;
    private List<CommentReturn> comments;
}