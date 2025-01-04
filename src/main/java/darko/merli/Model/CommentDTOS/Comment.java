package darko.merli.Model.CommentDTOS;

import darko.merli.Model.UserDTOS.Users;
import darko.merli.Model.VideoDTOS.Video;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment")
//comment class represents the comments which users leave in videos
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users userComm;

    @ManyToOne
    @JoinColumn(name = "video_id")
    private Video videoComm;
}
