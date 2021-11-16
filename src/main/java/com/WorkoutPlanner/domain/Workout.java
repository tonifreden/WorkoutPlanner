package com.WorkoutPlanner.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long workoutId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    
    private String routine; // i.e. legs, chest, back, biceps... essentially the "name" of the workout, could be e.g. "Legs & Shoulders" or "Abs & Core"

    private String location; // gym/place where workout was or is to be done, mostly for personal reference. not mandatory. example: "Kamppi Fressi" or "Herttoniemi Elixia"

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workout", orphanRemoval = true)
    private List<Exercise> exercises;

    @ManyToOne
    @JoinColumn(name = "id")
    private User user;

    public Workout() {

    }

    public Workout(LocalDate date, String routine, String location) {
        super();
        this.date = date;
        this.routine = routine;
        this.location = location;
    }

    public Workout(LocalDate date, String routine, String location, List<Exercise> exercises) {
        super();
        this.date = date;
        this.routine = routine;
        this.location = location;
        this.exercises = exercises;
    }

    public Workout(LocalDate date, String routine, String location, List<Exercise> exercises, User user) {
        super();
        this.date = date;
        this.routine = routine;
        this.location = location;
        this.exercises = exercises;
        this.user = user;
    }

    public Long getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(Long workoutId) {
        this.workoutId = workoutId;
    }

    public LocalDate getDate() {
        return date;
    }

    // formats LocalDate object to Finnish locale
    public String getDateFormatted() {
        DateTimeFormatter finnishFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return finnishFormat.format(date);
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getRoutine() {
        return routine;
    }

    public void setRoutine(String routine) {
        this.routine = routine;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public void addExercise(Exercise exercise) {
        getExercises().add(exercise);
        exercise.setWorkout(this);
    }

    public void removeExercise(Exercise exercise) {
        getExercises().remove(exercise);
        exercise.setWorkout(null);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Workout: " + getDateFormatted() + ", Routine: " + routine + "; Location: " + location;
    }
}
