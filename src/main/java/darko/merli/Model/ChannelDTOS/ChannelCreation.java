package darko.merli.Model.ChannelDTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//we send object of this type to controller in order to create a channel
public class ChannelCreation {
    private String channelName;
    private String description;
}