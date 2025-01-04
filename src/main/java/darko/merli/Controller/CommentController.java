package darko.merli.Controller;

import darko.merli.Model.CommentDTOS.CommentCreate;
import darko.merli.Model.CommentDTOS.CommentReturn;
import darko.merli.Service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@Tag(name = "3. Comments")
//controller class which holds the apis regarding comments
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Operation(description = "Delete the comment with given ID", summary = "Delete the comment")
    @PostMapping("/videos/{id}/comment")
    public CommentReturn createComment(@PathVariable int id, @RequestBody CommentCreate comment) {
        return commentService.createComment(id, comment);
    }
}
