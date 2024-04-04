package com.example.sport_club.controller;

import com.example.sport_club.model.UserModel;
import com.example.sport_club.model.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class RegistrationController {

    @GetMapping("/access_denied")
    private String access()
    {
        return "access_denied";
    }
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private com.example.sport_club.repo.UserRepo userRepo;
    @GetMapping("/registration")
    private String RegView()
    {
        return "regis";
    }
    @PostMapping("/registration")
    private String Reg(UserModel user, Model model)
    {
        UserModel user_from_db = userRepo.findByEmail(user.getEmail());
        if (user_from_db != null)
        {
            model.addAttribute("message", "Пользователь с таким логином уже существует");
            return "regis";
        }
        String check = user.getEmail();
        if (!check.contains("@"))
        {
            model.addAttribute("messagee", "Введите существующую электронную почту");
            return "regis";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(RoleEnum.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setImage("https://buzookod.ru/media/2816616767_vubrbeJ.jpg");
        try {
            userRepo.save(user);
        }
        catch (NullPointerException e){
            return "redirect:/regis";
        }
        return "/login";
    }
}
