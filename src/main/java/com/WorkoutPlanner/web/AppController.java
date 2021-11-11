package com.WorkoutPlanner.web;

import com.WorkoutPlanner.domain.SignupForm;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String addUser(Model model) {
        model.addAttribute("signupform", new SignupForm());
        return "signup";
    }
}
