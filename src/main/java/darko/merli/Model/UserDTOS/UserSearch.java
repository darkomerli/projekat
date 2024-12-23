package darko.merli.Model.UserDTOS;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
//DTO class for user search, returns only username
public class UserSearch {
    private String username;
}
