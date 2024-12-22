package darko.merli.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
//Class that has 3 attributes and which the app uses to throw and exception
public class ApiException {
    private final String message;

    private final HttpStatus httpStatus;

    private final LocalDateTime timestamp;
}
