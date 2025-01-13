package darko.merli.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping ("/index.html")
    public String index() {
        return "index";
    }

    @GetMapping("/register.html")
    public String register(){
        return "register";
    }

    @GetMapping("/login.html")
    public String login(){
        return "login";
    }

}
