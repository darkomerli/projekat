package darko.merli.Model.Channel;

import darko.merli.Model.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="Channel")
@AllArgsConstructor
@NoArgsConstructor
public class Channel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false,name="channel_Name", unique = true)
    private String channelName;
    @Column(nullable = false)
    private long subscribers;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
