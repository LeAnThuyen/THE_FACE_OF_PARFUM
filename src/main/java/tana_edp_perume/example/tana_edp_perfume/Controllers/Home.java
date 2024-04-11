package tana_edp_perume.example.tana_edp_perfume.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller

public class Home {
    @GetMapping("/")
    public String HomePage(){
        return  "Home";
    }
}