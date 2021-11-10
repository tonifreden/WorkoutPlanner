package com.WorkoutPlanner;

import java.time.LocalDate;
import java.util.ArrayList;

import com.WorkoutPlanner.domain.Exercise;
import com.WorkoutPlanner.domain.ExerciseRepository;
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
	public CommandLineRunner workoutDemo(WorkoutRepository workoutRepository, ExerciseRepository exerciseRepository) {
		return (args) -> {
			Log.info("save a couple of workouts");
			Workout workout1 = new Workout(LocalDate.of(2021, 7, 12), "Legs & Shoulders", "Pasila Fitness 24/7", new ArrayList<>());
			Workout workout2 = new Workout(LocalDate.of(2021, 7, 15), "Chest & Biceps", "", new ArrayList<>());
			Workout workout3 = new Workout(LocalDate.of(2021, 7, 18), "Back & Triceps", "Personal home gym", new ArrayList<>());
			workoutRepository.save(workout1);
			workoutRepository.save(workout2);
			workoutRepository.save(workout3);
			
			exerciseRepository.save(new Exercise("jotain", "60kg", 3, 8, "hyvin meni", workout1));
			exerciseRepository.save(new Exercise("sitä", "100kg", 5, 5, "", workout1));
			exerciseRepository.save(new Exercise("tätä", "20kg", 3, 6, "6, 6, 5", "aivan paska", workout1));

			Log.info("fetch all workouts");
			for (Workout workout : workoutRepository.findAll()) {
				Log.info(workout.toString());
			}
		};
	}
}
