package darko.merli.Model.VideoDTOS;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
// we use object of this class to update a video
public class VideoUpdate {
    private String title;
    private String description;
}
