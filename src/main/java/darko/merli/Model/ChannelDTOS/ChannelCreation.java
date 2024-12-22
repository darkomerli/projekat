package darko.merli.Model.ChannelDTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChannelCreation {
    private String channelName;
    private String description;
    private long user_id;
}