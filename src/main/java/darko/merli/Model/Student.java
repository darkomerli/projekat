package darko.merli.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Student {
    @Id
    private int id;
    private String name;
}
