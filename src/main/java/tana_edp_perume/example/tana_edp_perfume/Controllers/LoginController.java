package tana_edp_perume.example.tana_edp_perfume.Controllers;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tana_edp_perume.example.tana_edp_perfume.BuildingBlocks.PasswordManager;
import tana_edp_perume.example.tana_edp_perfume.Domain.Entities.UserSection.User;
import tana_edp_perume.example.tana_edp_perfume.Repositories.UserRepository;

@RestController

public class LoginController {
    @Autowired
    private UserRepository _userRepository;



    @PostMapping("/SignUp")
    public String createUser(@ModelAttribute("UserSignInAndSignUp") User UserSignInAndSignUp) {
        var User = _userRepository.FindUserByEmail(UserSignInAndSignUp.getEmail().toLowerCase());
        if (User!=null){
            return "Email has been signed up before!, Please try another email !";
        }
        PasswordManager passwordManager = new PasswordManager();
        UserSignInAndSignUp.setPassword(passwordManager.HashedPassword(UserSignInAndSignUp.getPassword()));
        var res = _userRepository.save(UserSignInAndSignUp);
        return "redirect:/Login";
    }

    @RequestMapping(value = "/CheckLogin/{email}/{password}")
    public String CheckLogin(@PathVariable String email,@PathVariable String password) {
        PasswordManager passwordManager = new PasswordManager();
        var User = _userRepository.FindUserByEmail(email.toLowerCase());
        if (User != null) {
            var checkpass= passwordManager.CheckPassword(password, User.getPassword());
            if (checkpass) {
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                HttpSession session = request.getSession(true);
                session.setAttribute("User",User);
                session.setAttribute("UserId",User.getId());
                return "Success";
            }
            else {
                return "Failed";
            }
        }
        return "Success";
    }


}