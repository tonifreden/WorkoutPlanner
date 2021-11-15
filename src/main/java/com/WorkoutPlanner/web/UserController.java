package com.WorkoutPlanner.web;

import javax.validation.Valid;

import com.WorkoutPlanner.domain.SignupForm;
import com.WorkoutPlanner.domain.User;
import com.WorkoutPlanner.domain.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
    
    @Autowired
    private UserRepository userRepository;

    // See all users - ADMIN only
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @GetMapping("/userlist")
    public String userList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "userlist";
    }

    // Delete a user - ADMIN only
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @GetMapping("/userlist/delete/{id}")
    public String deleteUser(@PathVariable("id") Long userId, RedirectAttributes redirectAttributes) {
        userRepository.deleteById(userId);
        redirectAttributes.addFlashAttribute("successMessage", "You have successfully deleted a user. Happy now?");
        return "redirect:../../userlist";
    }

    // Signup functionality
    @PostMapping("/saveuser")
    public String save(@Valid @ModelAttribute("signupform") SignupForm signupForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Authentication auth) {
        // Check that form contains no errors
        if (!bindingResult.hasErrors()) {

            // Check that passwords match
            if (signupForm.getPassword().equals(signupForm.getPasswordCheck())) {

                // Create User object and set its attributes
                String password = signupForm.getPassword();
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                String hashPassword = bCryptPasswordEncoder.encode(password);

                String username = signupForm.getUsername();
                String email = signupForm.getEmail();

                User newUser = new User(username, hashPassword, email, "USER");

                // Check that username does not exist already
                if (userRepository.findByUsername(username) == null) {

                    // Check that given email is not taken
                    if (userRepository.findByEmail(email) == null) {

                        // If all good, save User to UserRepository
                        userRepository.save(newUser);
                    } else {

                        // Otherwise reject email if already taken
                        bindingResult.rejectValue("email", "err.email", "Email already in use");
                        return "signup";
                    }

                // Otherwise reject username if already exists
                } else {
                    bindingResult.rejectValue("username", "err.username", "Username already exists");
                    return "signup";
                }

            // Otherwise reject unmatching passwords
            } else {
                bindingResult.rejectValue("passwordCheck", "err.passCheck", "Passwords do not match");
                return "signup";
            }

        // Otherwise reject signup if form contains errors
        } else {
            return "signup";
        }

        // If all goes as planned, redirect to login page
        redirectAttributes.addFlashAttribute("successMessage", "You have successfully signed up!");
        return "redirect:/login";
    }
}