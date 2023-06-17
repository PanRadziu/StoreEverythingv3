package pl.storeeverything.store.controler;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.storeeverything.store.model.RoleDetails;
import pl.storeeverything.store.model.UserDetails;
import pl.storeeverything.store.repo.UserRepo;
import pl.storeeverything.store.service.UserService;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    private RoleDetails roleDetails;
    private PasswordEncoder passwordEncoder;
    @GetMapping("/login")
    public String login(){
        return "login";
    }

//    @GetMapping("/register")
//    public String register(@ModelAttribute UserDetails userDetails){
//        System.out.println("XD");
//        System.out.println("KEKW" + userDetails);
//        return "register";
//    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new UserDetails());
        return "register";
    }

    @PostMapping("/register")
        public String register(@Valid @ModelAttribute("user") UserDetails userDetails, BindingResult result){
        if (result.hasErrors()) {
            return "register";
        }
        System.out.println("To drugie");
//        userDetails.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        userService.saveUser(userDetails);
//        userService.addUser(userDetails);

        System.out.println("po sejviku");
        return "redirect:/notes";
    }

}
