package darko.merli.Model.UserDTOS;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserExport {
    private String userName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
