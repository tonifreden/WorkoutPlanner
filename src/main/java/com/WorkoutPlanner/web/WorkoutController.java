package com.WorkoutPlanner.web;

import java.util.List;
import java.util.Optional;

import com.WorkoutPlanner.domain.User;
import com.WorkoutPlanner.domain.UserRepository;
import com.WorkoutPlanner.domain.Workout;
import com.WorkoutPlanner.domain.WorkoutRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@CrossOrigin
@Controller
public class WorkoutController {

    @Autowired
    WorkoutRepository workoutRepository;

    @Autowired
    UserRepository userRepository;
    
    /****************** RESTFUL SERVICES ******************/

    // Get all workouts
    @GetMapping("/api/workouts")
    public @ResponseBody List<Workout> workoutListRest() {
        return (List<Workout>) workoutRepository.findAll();
    }

    // Get workout by id
    @GetMapping("/api/workouts/{id}")
    public @ResponseBody Optional<Workout> findWorkoutRest(@PathVariable("id") Long workoutId) {
        return workoutRepository.findById(workoutId);
    }

    // Save new workout
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @PostMapping("/api/workouts")
    public @ResponseBody Workout saveWorkoutRest(@RequestBody Workout workout) {
        return workoutRepository.save(workout);
    }

    /******************************************************/

    // Show current User's workouts in a list, sorted by date
    @PreAuthorize(value = "hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/workoutlist")
    public String workoutList(Model model, Authentication auth) {
        User user = userRepository.findByUsername(auth.getName());
        model.addAttribute("workouts", workoutRepository.findAllByUserOrderByDate(user));
        return "workoutlist";
    }

    // Add new workout
    @GetMapping("/add")
    public String addWorkout(Model model) {
        model.addAttribute("workout", new Workout());
        return "addworkout";
    }

    // Save new workout for current User (redirects to newly created workout's exercise list)
    @PreAuthorize(value = "hasAnyAuthority('USER', 'ADMIN')")
    @PostMapping("/save")
    public String saveWorkout(Workout workout, RedirectAttributes redirectAttributes, Authentication auth) {
        User user = userRepository.findByUsername(auth.getName());
        workout.setUser(user);
        workoutRepository.save(workout);
        redirectAttributes.addAttribute("id", workout.getWorkoutId());
        return "redirect:/workoutlist/{id}";
    }

    // Delete workout (and all exercises in it) from current User
    @PreAuthorize(value = "hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteWorkout(@PathVariable("id") Long workoutId, Authentication auth, RedirectAttributes redirectAttributes) {
        User user = userRepository.findByUsername(auth.getName());
        if (user == workoutRepository.findById(workoutId).get().getUser()) {
            workoutRepository.deleteById(workoutId);
        } else {
            redirectAttributes.addFlashAttribute("warningMessage", "You are not authorized to do that!");
        }
        return "redirect:../workoutlist";
    }

    // Edit workout from current User (redirects to edited workout's exercise list after saving)
    @PreAuthorize(value = "hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/edit/{id}")
    public String editWorkout(@PathVariable("id") Long workoutId, Model model, Authentication auth, RedirectAttributes redirectAttributes) {
        User user = userRepository.findByUsername(auth.getName());
        if (user == workoutRepository.findById(workoutId).get().getUser()) {
            model.addAttribute("workout", workoutRepository.findById(workoutId));
            return "editworkout";
        } else {
            redirectAttributes.addFlashAttribute("warningMessage", "You are not authorized to do that!");
            return "redirect:../workoutlist";
        }
    }
}
