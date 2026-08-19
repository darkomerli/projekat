package darko.merli.Model.VideoDTOS;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VideoUpload {
    private String title;
    private String description;
    private String videoUrl;
}
