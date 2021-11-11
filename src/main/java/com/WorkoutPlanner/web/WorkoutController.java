package com.WorkoutPlanner.web;

import java.util.List;
import java.util.Optional;

import com.WorkoutPlanner.domain.Workout;
import com.WorkoutPlanner.domain.WorkoutRepository;

import org.springframework.beans.factory.annotation.Autowired;
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
    @PostMapping("/workouts")
    public @ResponseBody Workout saveWorkoutRest(@RequestBody Workout workout) {
        return workoutRepository.save(workout);
    }

    /******************************************************/

    // Show all workouts in a list, sorted by date
    @GetMapping("/workoutlist")
    public String workoutList(Model model) {
        model.addAttribute("workouts", workoutRepository.findAllByOrderByDate());
        return "workoutlist";
    }

    // Add new workout
    @GetMapping("/add")
    public String addWorkout(Model model) {
        model.addAttribute("workout", new Workout());
        return "addworkout";
    }

    // Save new workout (redirects to newly created workout's exercise list)
    @PostMapping("/save")
    public String saveWorkout(Workout workout, RedirectAttributes redirectAttributes) {
        workoutRepository.save(workout);
        redirectAttributes.addAttribute("id", workout.getWorkoutId());
        return "redirect:/workoutlist/{id}";
    }

    // Delete workout (and all exercises in it)
    @GetMapping("/delete/{id}")
    public String deleteWorkout(@PathVariable("id") Long workoutId) {
        workoutRepository.deleteById(workoutId);
        return "redirect:../workoutlist";
    }

    // Edit workout (redirects to edited workout's exercise list)
    @GetMapping("/edit/{id}")
    public String editWorkout(@PathVariable("id") Long workoutId, Model model) {
        model.addAttribute("workout", workoutRepository.findById(workoutId));
        return "editworkout";
    }
}
