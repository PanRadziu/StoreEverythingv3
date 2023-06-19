package pl.storeeverything.store.controler;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.storeeverything.store.model.NotesDetails;
import pl.storeeverything.store.model.RoleDetails;
import pl.storeeverything.store.model.UserDetails;
import pl.storeeverything.store.repo.UserRepo;
import pl.storeeverything.store.service.UserService;

import java.util.List;

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

    @GetMapping("/register/list")
    public String ListRegister(Model model) {
        List<UserDetails> userDetailsList = userService.getAllUsers();
        model.addAttribute("users", userDetailsList);
        return "user";
    }

    @PostMapping("/register")
        public String register(@Valid @ModelAttribute("user") UserDetails userDetails, BindingResult result){
        if (result.hasErrors()) {
            return "register";
        }
        userService.saveUser(userDetails);

        return "redirect:/notes";
    }

    @GetMapping("/register/edit/{id}")
    public String editUser(@PathVariable("id") Long id,Model model){
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("roles", userService.getRoleRepo().findAll());
        return "edit_user";
    }

    @PostMapping("/register/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") UserDetails userDetails){
        UserDetails existUser = userService.findUserById(id);
        existUser.setId(userDetails.getId());
        existUser.setFirstname(userDetails.getFirstname());
        existUser.setLastname(userDetails.getLastname());
        existUser.setUsername(userDetails.getUsername());
        existUser.setPassword(userDetails.getPassword());
        existUser.setAge(userDetails.getAge());
        existUser.setId_roles(userDetails.getId_roles());
        userService.saveUser(existUser);
        return "redirect:/notes";
    }

}
