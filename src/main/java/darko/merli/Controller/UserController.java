package darko.merli.Controller;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import darko.merli.Model.UserDTOS.UserCreation;
import darko.merli.Model.UserDTOS.UserSearch;
import darko.merli.Model.UserDTOS.UserUpdate;
import darko.merli.Model.UserDTOS.Users;
import darko.merli.Repository.UserRepository;
import darko.merli.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@Validated
@Tag(name = "2. Users")
//controller class which holds the apis for user
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Operation(summary = "Create a User", description = "Create the user with username and password")
    @PostMapping("/users/create")
    @SecurityRequirements
    //inside paramateres I have removed @RequestBody, which was causing an error when i send data from the browser in my custom register page
    public String createUser(UserCreation user) throws IllegalAccessException {
        userService.createUser(user);
        return "successfullCreation";
    }

    @GetMapping("/users/{id}")
    public String myProfile(Model model, @PathVariable long id) {
        Users user = userRepository.findById(id).get();
        List<Users> usersList = new ArrayList<>();
        usersList.add(user);
        model.addAttribute("usersList", usersList);
        return "myProfile";
    }

    @Operation(summary = "Update the user", description = "Update the current logged in user")
    @PutMapping("/users/update")
    public UserSearch updateUser(@RequestBody UserUpdate user){
        return userService.updateUser(user);
    }

    @Operation(summary = "Delete the user", description = "Delete the current logged in user")
    @DeleteMapping("users/delete")
    public String deleteUser() throws IllegalAccessException {
        return userService.deleteUser();
    }

    @Operation(summary = "Search the user", description = "Get the user with selected name")
    @GetMapping("users/search/{name}")
    @SecurityRequirements
    public UserSearch searchUser(@PathVariable String name){
        return userService.searchUser(name);
    }

    @Operation(summary = "Export CSV of users", description = "Get users")
    @GetMapping("users/export")
    @SecurityRequirements
    public byte[] exportCSV(HttpServletResponse response) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
        String formatedDate = currentTime.format(formatter);
        String fileName = "users "+formatedDate+".csv";
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        File file = userService.smallCron();
        return FileUtils.readFileToByteArray(file);
    }

    @GetMapping("users/try")
    @SecurityRequirements
    public String tryTo(){
        return userService.newUsers();
    }
}
