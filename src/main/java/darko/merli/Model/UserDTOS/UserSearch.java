package darko.merli.Model.UserDTOS;

import darko.merli.Model.ChannelDTOS.ChannelSearch;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserSearch {
    private String username;
    private List<ChannelSearch> channelList;
}
