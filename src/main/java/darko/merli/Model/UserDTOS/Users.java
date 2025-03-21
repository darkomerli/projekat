package darko.merli.Model.UserDTOS;

import darko.merli.Model.ChannelDTOS.Channel;
import darko.merli.Model.CommentDTOS.Comment;
import darko.merli.Model.VideoDTOS.Video;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "User")
//user class which represents the user of the site
public class Users {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String password;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    @OneToMany(mappedBy = "user")
    private List<Channel> channelList;

    @ManyToMany(mappedBy = "users")
    private List<Channel> subscribedChannelsList;

    @ManyToMany(mappedBy = "users")
    private List<Video> likedVideoList;

    @OneToMany(mappedBy= "userComm")
    private List<Comment> commentList;
}
