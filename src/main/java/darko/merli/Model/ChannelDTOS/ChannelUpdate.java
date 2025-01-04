package darko.merli.Model.ChannelDTOS;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
//we send object of this type to update the channel
public class ChannelUpdate {
    private String channelName;
    private String description;
}
