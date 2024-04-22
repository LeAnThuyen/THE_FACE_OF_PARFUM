package tana_edp_perume.example.tana_edp_perfume.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
@RestController
public class RestRootController {
    @RequestMapping("/ClearSession")
    public String ClearSession() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession(true);
        var check=  session.getAttribute("User");
        if (check!=null){
            session.removeAttribute("User");
            session.removeAttribute("UserId");
        }
        return "Sucess";
    }
}
