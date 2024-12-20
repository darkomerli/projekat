package darko.merli.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("home")
    public void home(@RequestParam("name") String name, @RequestParam("age") String age){
        System.out.println(name + " " + age);
    }

}
