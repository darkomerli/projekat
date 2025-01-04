package darko.merli.Model.ChannelDTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//controller will return object of this type when we search for a channel
public class ChannelSearch {
    private String channelName;
    private long subscribers;
    private long user_id;
    private String description;
}
