package darko.merli.Model.VideoDTOS;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
//we use object of this class to upload a video
public class VideoUpload {
    private String title;
    private String description;
    private String videoUrl;
}
