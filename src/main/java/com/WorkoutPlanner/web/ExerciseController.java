package com.WorkoutPlanner.web;

import java.util.List;

import com.WorkoutPlanner.domain.Exercise;
import com.WorkoutPlanner.domain.ExerciseRepository;
import com.WorkoutPlanner.domain.WorkoutRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ExerciseController {
    
    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    WorkoutRepository workoutRepository;

    // Show exercise list by workout id
    @GetMapping("/workoutlist/{id}")
    public String exerciseList(@PathVariable("id") Long workoutId, Model model) {
        model.addAttribute("workout", workoutRepository.findById(workoutId).get());
        model.addAttribute("exercise", new Exercise());
        return "exerciselist";
    }

    // Save new exercise to exercise list
    @PostMapping("/workoutlist/{id}/save")
    public String addExercise(@PathVariable("id") Long workoutId, @ModelAttribute Exercise exercise) {
        exercise.setName(exercise.getName());
        exercise.setTargetWeight(exercise.getTargetWeight());
        exercise.setTargetSets(exercise.getTargetSets());
        exercise.setTargetReps(exercise.getTargetReps());
        exercise.setComments(exercise.getComments());
        exercise.setWorkout(workoutRepository.findById(workoutId).get());
        exerciseRepository.save(exercise);
        return "redirect:/workoutlist/{id}";
    }

    // Save exercise results to exercise (after submitting exercise targets)
    @PostMapping("/workoutlist/{id}/save/{exerciseid}/results")
    public String saveResults(@PathVariable("id") Long workoutId, @PathVariable("exerciseid") Long exerciseId, @RequestParam("resultReps") String newResultReps) {
        Exercise exercise = exerciseRepository.findById(exerciseId).get();
        exercise.setResultReps(newResultReps);
        exerciseRepository.save(exercise);
        return "redirect:/workoutlist/{id}";
    }

    // Save exercise comments to exercise (after submitting exercise targets)
    @PostMapping("/workoutlist/{id}/save/{exerciseid}/comments")
    public String saveComments(@PathVariable("id") Long workoutId, @PathVariable("exerciseid") Long exerciseId, @RequestParam("comments") String newComments) {
        Exercise exercise = exerciseRepository.findById(exerciseId).get();
        exercise.setComments(newComments);
        exerciseRepository.save(exercise);
        return "redirect:/workoutlist/{id}";
    }

    // Delete exercise
    @GetMapping("/workoutlist/{id}/delete/{exerciseid}")
    public String removeExercise(@PathVariable("id") Long workoutId, @PathVariable("exerciseid") Long exerciseId) {
        exerciseRepository.delete(exerciseRepository.findById(exerciseId).get());
        return "redirect:/workoutlist/{id}";
    }

    // Show all exercises
    @GetMapping("/workoutlist/exercises")
    public String allExercises(Model model) {
        List<Exercise> exercises = (List<Exercise>) exerciseRepository.findAll();
        model.addAttribute("exercises", exercises);
        return "exerciselist";
    }
}
