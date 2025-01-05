package darko.merli.Service;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import darko.merli.Model.UserDTOS.UserCreation;
import darko.merli.Model.UserDTOS.UserSearch;
import darko.merli.Model.UserDTOS.UserUpdate;
import darko.merli.Model.UserDTOS.Users;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public interface UserService {

    Users createUser(UserCreation users) throws IllegalAccessException;

    UserSearch updateUser(UserUpdate user);

    String deleteUser() throws IllegalAccessException;

    UserSearch searchUser(String name);

    File smallCron() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;

    public String newUsers();
}
