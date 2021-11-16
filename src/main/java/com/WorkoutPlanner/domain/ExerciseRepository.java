package com.WorkoutPlanner.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ExerciseRepository extends CrudRepository<Exercise, Long> {
    // Find all exercises based on its/their name
    List<Exercise> findByName(String name);

    // Find all exercises based on target weight
    List<Exercise> findByTargetWeights(String targetWeights);

    // Find all exercises based on the number of target sets
    List<Exercise> findByTargetSets(Integer targetSets);

    // Find all exercises based on the number of target reps (per set)
    List<Exercise> findByTargetReps(Integer targetReps);
}
