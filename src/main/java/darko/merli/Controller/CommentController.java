package darko.merli.Controller;

import darko.merli.Model.CommentDTOS.CommentCreate;
import darko.merli.Model.CommentDTOS.CommentReturn;
import darko.merli.Service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@Validated
@Tag(name = "3. Comments")
//controller class which holds the apis regarding comments
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Operation(description = "Create a comment on selected video with ID", summary = "Create a comment")
    @PostMapping("/videos/{id}")
    public String createComment(@PathVariable int id, @ModelAttribute("commentCreate") CommentCreate commentCreate) {
        System.out.println(commentCreate.getContent());
        commentService.createComment(id, commentCreate);
        return "redirect:/videos/" + id;
    }

    @Operation(description = "Delete a comment with ID on selected video with ID", summary = "Delete the comment")
    @GetMapping("/videos/{idVid}/comment/{idComm}")
    public String deleteComment(@PathVariable long idVid, @PathVariable long idComm) throws IllegalAccessException {
        commentService.deleteComment(idVid,idComm);
        return "redirect:/videos/" + idVid;
    }
}
