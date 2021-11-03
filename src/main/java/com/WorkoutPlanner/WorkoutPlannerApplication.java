package com.WorkoutPlanner;

import java.time.LocalDate;

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
	public CommandLineRunner workoutDemo(WorkoutRepository workoutRepository) {
		return (args) -> {
			Log.info("save a couple of workouts");
			workoutRepository.save(new Workout(LocalDate.of(2021, 7, 12), "Legs & Shoulders"));
			workoutRepository.save(new Workout(LocalDate.of(2021, 7, 15), "Chest & Biceps"));
			workoutRepository.save(new Workout(LocalDate.of(2021, 7, 18), "Back & Triceps"));

			Log.info("fetch all workouts");
			for (Workout workout : workoutRepository.findAll()) {
				Log.info(workout.toString());
			}
		};
	}
}
