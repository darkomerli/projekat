package darko.merli.Controller;

import darko.merli.Model.ChannelDTOS.Channel;
import darko.merli.Model.UserDTOS.Users;
import darko.merli.Model.VideoDTOS.Video;
import darko.merli.Repository.ChannelRepository;
import darko.merli.Repository.UserRepository;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class PageController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChannelRepository channelRepository;


    @GetMapping ("/index.html")
    public String index(Model model) {
        model.addAttribute("videos", videoRepository.findAll());
        String news = userService.newUsers();
        List<String> newUsers = new ArrayList<>();
        newUsers.add(news);
        model.addAttribute("newUsers", newUsers);
        return "index";
    }

    @GetMapping("/")
    public String index2(Model model) {
        model.addAttribute("videos", videoRepository.findAll());
        String news = userService.newUsers();
        List<String> newUsers = new ArrayList<>();
        newUsers.add(news);
        model.addAttribute("newUsers", newUsers);
        List<Users> listUsers = new ArrayList<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Users> user = userRepository.findByUsername(auth.getName());
        if(user.isPresent()) {
            listUsers.add(user.get());
        }
        if(listUsers.size()>0){
            model.addAttribute("listUsers", listUsers);
        }
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
        return "redirect:/";
    }

    @GetMapping("/successfullCreation.html")
    public String confirmedRegistration(){
        return "successfullCreation";
    }

    @GetMapping("/search")
    @Transactional(readOnly = true)
    public String search(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            String query = keyword.trim();
            List<Video> videos = videoService.searchVideosByKeyword(query);
            List<Channel> channels = channelRepository.searchByKeyword(query);

            model.addAttribute("videos", videos);
            model.addAttribute("channels", channels);
            model.addAttribute("keyword", query);
        } else {
            model.addAttribute("videos", new ArrayList<Video>());
            model.addAttribute("channels", new ArrayList<Channel>());
            model.addAttribute("keyword", "");
        }
        return "search-results";
    }
}
