package darko.merli.Model.UserDTOS;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
//DTO class for updating the user
public class UserUpdate {
    private String username;
    private String password;
}
