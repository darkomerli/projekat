package darko.merli.Model.UserDTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//DTO class for user creation process. needs only username and password.
public class UserCreation {
    private String username;
    private String password;
}
