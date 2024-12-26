package darko.merli.Controller;

import darko.merli.Model.UserDTOS.UserCreation;
import darko.merli.Model.UserDTOS.Users;
import darko.merli.Service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@Tag(name = "2. Users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users/create")
    @SecurityRequirements
    public Users createUser(@RequestBody UserCreation user){
        return userService.createUser(user);
    }
}
