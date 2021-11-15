package com.WorkoutPlanner;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;

import com.WorkoutPlanner.domain.Exercise;
import com.WorkoutPlanner.domain.ExerciseRepository;
import com.WorkoutPlanner.domain.User;
import com.WorkoutPlanner.domain.UserRepository;
import com.WorkoutPlanner.domain.Workout;
import com.WorkoutPlanner.domain.WorkoutRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AppRepositoryTest {
    
    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkoutRepository workoutRepository;

    /**
     * Test userRepository by finding user "admin" by username. Assert that 1) user is not null, 2) user's email is correct, 3) user's role is correct
     */
    @Test
    public void findByUsernameShouldReturnUser() {
        User user = userRepository.findByUsername("admin");
        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo("admin@adminmail.com");
        assertThat(user.getRole()).isEqualTo("ADMIN");
    }

    /**
     * Test userRepository by creating new user. Assert that 1) user is not null, 2) user's username is correct, 3) user's workout list is null
     */
    @Test
    public void createUser() {
        User user = new User("testuser", "$2a$10$220vEwCYQVaQAlbjUSQXqOeOZcGZBKQBxzPqvULV1L4Su1EP9kIvm", "tester@testmail.co.uk", "USER");
        userRepository.save(user);
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("testuser");
        assertThat(user.getWorkouts()).isNull();
    }

    /**
     * Test workoutRepository by creating a new workout. Assert that 1) workout is not null, 2) workout's location is correct, 3) workout's formatted date is correct, 4) workout's exercise list is empty
     */
    @Test
    public void createWorkout() {
        Workout workout = new Workout(LocalDate.of(2017, 9, 19), "All-round", "Office", new ArrayList<>());
        workoutRepository.save(workout);
        assertThat(workout).isNotNull();
        assertThat(workout.getLocation()).isEqualTo("Office");
        assertThat(workout.getDateFormatted()).isEqualTo("19.09.2017");
        assertThat(workout.getExercises().size()).isEqualTo(0);
    }

    /**
     * Test workoutRepository by finding workout by id. Assert that 1) workout is not null, 2) workout's routine is correct, 3) user tied to workout is not null, 4) user's username is correct
     */
    @Test
    public void findByIdShouldReturnWorkout() {
        long id = 1;
        Workout workout = workoutRepository.findById(Long.valueOf(id)).get();
        assertThat(workout).isNotNull();
        assertThat(workout.getRoutine()).isEqualTo("Legs & Shoulders");
        assertThat(workout.getUser()).isNotNull();
        assertThat(workout.getUser().getUsername()).isEqualTo("Toni");
    }

    /**
     * Test exerciseRepository by creating a blank exercise. Assert that 1) exercise is not null, 2) a couple of exercise's attributes are null.
     */
    @Test
    public void createBlankExercise() {
        Exercise exercise = new Exercise();
        exerciseRepository.save(exercise);
        assertThat(exercise).isNotNull();
        assertThat(exercise.getName()).isNull();
        assertThat(exercise.getResultReps()).isNull();
    }
}
