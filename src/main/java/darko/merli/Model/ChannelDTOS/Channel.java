package darko.merli.Model.ChannelDTOS;

import darko.merli.Model.UserDTOS.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
//used for getters and setters
@Data
@Table(name="Channel")
@AllArgsConstructor
@NoArgsConstructor
//channel class which represents the channel which a user owns
public class Channel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false,name="channel_Name", unique = true)
    private String channelName;
    @Column(nullable = false)
    private long subscribers;
    @Column(name = "description")
    private String description;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "subscriptions",
            joinColumns = @JoinColumn(name = "channel_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;
}
