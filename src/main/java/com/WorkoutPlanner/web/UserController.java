package com.WorkoutPlanner.web;

import javax.validation.Valid;

import com.WorkoutPlanner.domain.SignupForm;
import com.WorkoutPlanner.domain.User;
import com.WorkoutPlanner.domain.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
    
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/saveuser")
    public String save(@Valid @ModelAttribute("signupform") SignupForm signupForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Authentication auth) {
        if (!bindingResult.hasErrors()) {
            if (signupForm.getPassword().equals(signupForm.getPasswordCheck())) {
                String password = signupForm.getPassword();
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                String hashPassword = bCryptPasswordEncoder.encode(password);

                String username = signupForm.getUsername();
                String email = signupForm.getEmail();

                User newUser = new User(username, hashPassword, email, "USER");

                if (userRepository.findByUsername(username) == null) {
                    if (userRepository.findByEmail(email) == null) {
                        userRepository.save(newUser);
                    } else {
                        bindingResult.rejectValue("email", "err.email", "Email already in use");
                        return "signup";
                    }
                } else {
                    bindingResult.rejectValue("username", "err.username", "Username already exists");
                    return "signup";
                }
            } else {
                bindingResult.rejectValue("passwordCheck", "err.passCheck", "Passwords do not match");
                return "signup";
            }
        } else {
            return "signup";
        }
        redirectAttributes.addFlashAttribute("successMessage", "You have successfully signed up!");
        return "redirect:/login";
    }
}