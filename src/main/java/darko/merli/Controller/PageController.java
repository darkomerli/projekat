package darko.merli.Controller;

import darko.merli.Model.VideoDTOS.Video;
import darko.merli.Repository.VideoRepository;
import darko.merli.Service.UserService;
import darko.merli.Service.VideoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PageController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private UserService userService;

    @GetMapping ("/index.html")
    public String index(Model model) {
        model.addAttribute("videos", videoRepository.findAll());
        String news = userService.newUsers();
        List<String> newUsers = new ArrayList<>();
        newUsers.add(news);
        model.addAttribute("newUsers", newUsers);
        return "index";
    }
    //test

    @GetMapping("/")
    public String index2(Model model) {
        model.addAttribute("videos", videoRepository.findAll());
        String news = userService.newUsers();
        List<String> newUsers = new ArrayList<>();
        newUsers.add(news);
        model.addAttribute("newUsers", newUsers);
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
