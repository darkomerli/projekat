package darko.merli.Model.Channel;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChannelUpdate {
    private String channelName;
    private String description;
}
