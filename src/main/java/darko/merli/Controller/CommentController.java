package darko.merli.Controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@Tag(name = "3. Comments")
public class CommentController {
}
