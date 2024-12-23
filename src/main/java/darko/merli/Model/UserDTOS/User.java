package darko.merli.Model.UserDTOS;

import darko.merli.Model.ChannelDTOS.Channel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "User")
//user class which represents the user of the site
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Channel> channelList;

    @ManyToMany(mappedBy = "users")
    private List<Channel> subscribedChannelsList;
}
