package com.WorkoutPlanner.web;

import java.util.List;
import java.util.Optional;

import com.WorkoutPlanner.domain.Exercise;
import com.WorkoutPlanner.domain.ExerciseRepository;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@CrossOrigin
@Controller
public class ExerciseController {
    
    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    WorkoutRepository workoutRepository;

    @Autowired
    UserRepository userRepository;

    /****************** RESTFUL SERVICES ******************/

    // Get all exercises
    @GetMapping("/api/exercises")
    public @ResponseBody List<Exercise> exerciseListRest() {
        return (List<Exercise>) exerciseRepository.findAll();
    }

    // Get exercise by id
    @GetMapping("/api/exercises/{id}")
    public @ResponseBody Optional<Exercise> findExerciseRest(@PathVariable("id") Long exerciseId) {
        return exerciseRepository.findById(exerciseId);
    }

    // Get all exercises of a workout by workout id
    @GetMapping("/api/exercises/workout/{id}")
    public @ResponseBody List<Exercise> findExercisesByWorkoutId(@PathVariable("id") Long workoutId) {
        Workout workout = workoutRepository.findById(workoutId).get();
        return workout.getExercises();
    }

    // Save new exercise
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @PostMapping("/api/exercises")
    public @ResponseBody Exercise saveExerciseRest(@RequestBody Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    /******************************************************/

    // Show exercise list by workout id
    @PreAuthorize(value = "hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/workoutlist/{id}")
    public String exerciseList(@PathVariable("id") Long workoutId, Model model, Authentication auth, RedirectAttributes redirectAttributes) {
        User user = userRepository.findByUsername(auth.getName());
        if (user == workoutRepository.findById(workoutId).get().getUser()) {
            model.addAttribute("workout", workoutRepository.findById(workoutId).get());
            model.addAttribute("exercise", new Exercise());
            return "exerciselist";
        } else {
            redirectAttributes.addFlashAttribute("warningMessage", "You are not authorized to do that!");
            return "redirect:../workoutlist";
        }
    }

    // Save new exercise targets to exercise list
    @PreAuthorize(value = "hasAnyAuthority('USER', 'ADMIN')")
    @PostMapping("/workoutlist/{id}/save")
    public String addExercise(@PathVariable("id") Long workoutId, @ModelAttribute Exercise exercise) {
        exercise.setName(exercise.getName());
        exercise.setTargetWeight(exercise.getTargetWeight());
        exercise.setTargetSets(exercise.getTargetSets());
        exercise.setTargetReps(exercise.getTargetReps());
        exercise.setWorkout(workoutRepository.findById(workoutId).get());
        exerciseRepository.save(exercise);
        return "redirect:/workoutlist/{id}";
    }

    // Save exercise results to exercise (after submitting exercise targets)
    @PreAuthorize(value = "hasAnyAuthority('USER', 'ADMIN')")
    @PostMapping("/workoutlist/{id}/save/{exerciseid}/results")
    public String saveResults(@PathVariable("id") Long workoutId, @PathVariable("exerciseid") Long exerciseId, @RequestParam("resultReps") String newResultReps) {
        Exercise exercise = exerciseRepository.findById(exerciseId).get();
        exercise.setResultReps(newResultReps);
        exerciseRepository.save(exercise);
        return "redirect:/workoutlist/{id}";
    }

    // Save exercise comments to exercise (after submitting exercise targets)
    @PreAuthorize(value = "hasAnyAuthority('USER', 'ADMIN')")
    @PostMapping("/workoutlist/{id}/save/{exerciseid}/comments")
    public String saveComments(@PathVariable("id") Long workoutId, @PathVariable("exerciseid") Long exerciseId, @RequestParam("comments") String newComments) {
        Exercise exercise = exerciseRepository.findById(exerciseId).get();
        exercise.setComments(newComments);
        exerciseRepository.save(exercise);
        return "redirect:/workoutlist/{id}";
    }

    // Delete exercise
    @PreAuthorize(value = "hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/workoutlist/{id}/delete/{exerciseid}")
    public String removeExercise(@PathVariable("id") Long workoutId, @PathVariable("exerciseid") Long exerciseId, Authentication auth, RedirectAttributes redirectAttributes) {
        User user = userRepository.findByUsername(auth.getName());
        if (user == workoutRepository.findById(workoutId).get().getUser()) {
            exerciseRepository.delete(exerciseRepository.findById(exerciseId).get());
        } else {
            redirectAttributes.addFlashAttribute("warningMessage", "You are not authorized to do that!");
        }
        return "redirect:/workoutlist/{id}";
    }
}
