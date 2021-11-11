package com.WorkoutPlanner.domain;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface WorkoutRepository extends CrudRepository<Workout, Long> {
    // Find all workouts based on the date they were or are to be done
    List<Workout> findByDate(LocalDate date);

    // Find all workouts based on its/their routine (name)
    List<Workout> findByRoutine(String routine);

    // Method for sorting all Workouts by date, default view for main page / workout list
    List<Workout> findAllByOrderByDate();
}
