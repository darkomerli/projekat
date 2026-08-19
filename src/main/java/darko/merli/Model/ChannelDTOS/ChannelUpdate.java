package darko.merli.Model.ChannelDTOS;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChannelUpdate {
    private String channelName;
    private String description;
}
