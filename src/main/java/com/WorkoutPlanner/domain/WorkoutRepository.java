package com.WorkoutPlanner.domain;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface WorkoutRepository extends CrudRepository<Workout, Long> {
    List<Workout> findByDate(LocalDate date);

    List<Workout> findByRoutine(String routine);

    // Method for sorting all Workouts by date
    List<Workout> findAllByOrderByDate();
}
