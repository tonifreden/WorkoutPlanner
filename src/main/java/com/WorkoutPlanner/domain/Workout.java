package com.WorkoutPlanner.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Workout {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long workoutId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    
    private String routine; // i.e. legs, chest, back, biceps... essentially the "name" of the session, could be e.g. "Legs & Shoulders"

    public Workout() {

    }

    public Workout(LocalDate date, String routine) {
        super();
        this.date = date;
        this.routine = routine;
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

    @Override
    public String toString() {
        return "Workout: " + getDateFormatted() + ", Routine: " + routine;
    }
}
