package darko.merli.Model.VideoDTOS;

import darko.merli.Model.ChannelDTOS.Channel;
import darko.merli.Model.CommentDTOS.Comment;
import darko.merli.Model.UserDTOS.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "video")
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    private long likes;
    private long dislikes;
    private long noOfComments;
    private String videoUrl;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel postedChannel;

    @OneToMany(mappedBy = "videoComm")
    private List<Comment> comments;

    @ManyToMany
    @JoinTable(
            name = "liked_videos",
            joinColumns = @JoinColumn(name = "video_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<Users> users;

}
