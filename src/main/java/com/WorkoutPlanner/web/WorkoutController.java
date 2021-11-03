package com.WorkoutPlanner.web;

import com.WorkoutPlanner.domain.Workout;
import com.WorkoutPlanner.domain.WorkoutRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WorkoutController {

    @Autowired
    WorkoutRepository workoutRepository;
    
    // Show all workouts in a list
    @GetMapping("/workoutlist")
    public String workoutList(Model model) {
        model.addAttribute("workouts", workoutRepository.findAll());
        return "workoutlist";
    }

    // Add new workout
    @GetMapping("/add")
    public String addWorkout(Model model) {
        model.addAttribute("workout", new Workout());
        // model.addAttribute("exercises", attributeValue); katsotaan tuleeko tähän mitään
        return "addworkout";
    }

    // Save new workout
    @PostMapping("/save")
    public String saveWorkout(Workout workout) {
        workoutRepository.save(workout);
        return "redirect:/workoutlist";
    }

    // Delete workout
    @GetMapping("/delete/{id}")
    public String deleteWorkout(@PathVariable("id") Long workoutId, Model model) {
        workoutRepository.deleteById(workoutId);
        return "redirect:../workoutlist";
    }

    // Edit workout
    @GetMapping("/edit/{id}")
    public String editWorkout(@PathVariable("id") Long workoutId, Model model) {
        model.addAttribute("workout", workoutRepository.findById(workoutId));
        return "editworkout";
    }
}
