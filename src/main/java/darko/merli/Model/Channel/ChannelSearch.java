package darko.merli.Model.Channel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChannelSearch {
    private String channelName;
    private long subscribers;
}
