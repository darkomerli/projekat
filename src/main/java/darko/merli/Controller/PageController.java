package darko.merli.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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

    @GetMapping("/login")
    public String loginPage(){
        return "wrong";
    }

    @GetMapping("/testX.html")
    public String test(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("logovan: " + auth.getName());
        return "testX";
    }

    @GetMapping("/login?error")
    public String loginError(){
        return "error";
    }

    @GetMapping("/loggedOut.html")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "loggedOut";
    }

}
