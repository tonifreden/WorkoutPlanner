package com.WorkoutPlanner;

import java.time.LocalDate;
import java.util.ArrayList;

import com.WorkoutPlanner.domain.Exercise;
import com.WorkoutPlanner.domain.ExerciseRepository;
import com.WorkoutPlanner.domain.User;
import com.WorkoutPlanner.domain.UserRepository;
import com.WorkoutPlanner.domain.Workout;
import com.WorkoutPlanner.domain.WorkoutRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WorkoutPlannerApplication {
	private static final Logger Log = LoggerFactory.getLogger(WorkoutPlannerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(WorkoutPlannerApplication.class, args);
	}

	@Bean
	public CommandLineRunner workoutDemo(WorkoutRepository workoutRepository, ExerciseRepository exerciseRepository, UserRepository userRepository) {
		return (args) -> {
			Log.info("save a couple of users, workouts and exercises");
			userRepository.deleteAll();
			User toni = new User("Toni", "$2a$10$uTrTctg4BYkPXvDw3I2OG.6R7.cEZFizDqfzoPk8.V4NKIPmLtXs6", "toni@awesomemail.org", "ADMIN");
			User user = new User("user", "$2a$10$uHgwYhqLKumssPxb1ooWDOfgCn3SgFkU5CVsK3KXp9BdSjs2Tf8cq", "user@usermail.com", "USER");
			User admin = new User("admin", "$2a$10$CuDAFP7yMSmO8qNUsUmLgOFcytLlPXgsN5Uk23Zo9K2i6asRgMu..", "admin@adminmail.com", "ADMIN");
			userRepository.save(toni);
			userRepository.save(user);
			userRepository.save(admin);

			Workout workout1 = new Workout(LocalDate.of(2021, 7, 12), "Legs & Shoulders", "Pasila Fitness 24/7", new ArrayList<>(), toni);
			Workout workout2 = new Workout(LocalDate.of(2021, 7, 15), "Chest & Biceps", "", new ArrayList<>(), toni);
			Workout workout3 = new Workout(LocalDate.of(2021, 7, 18), "Back & Triceps", "Personal home gym", new ArrayList<>(), user);
			
			workoutRepository.save(workout1);
			workoutRepository.save(workout2);
			workoutRepository.save(workout3);
			
			exerciseRepository.save(new Exercise("Squat", "60kg", 3, 8, "", workout1));
			exerciseRepository.save(new Exercise("Overhead press", "100kg", 5, 5, "Trying for personal best", workout1));
			exerciseRepository.save(new Exercise("Calves", "20kg", 3, 6, "6, 5, 4", "Way too heavy, reduce weights next time", workout1));


			Log.info("fetch all workouts");
			for (Workout workout : workoutRepository.findAll()) {
				Log.info(workout.toString());
			}
		};
	}
}
