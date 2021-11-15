package com.WorkoutPlanner;

import static org.assertj.core.api.Assertions.assertThat;

import com.WorkoutPlanner.web.AppController;
import com.WorkoutPlanner.web.ExerciseController;
import com.WorkoutPlanner.web.UserController;
import com.WorkoutPlanner.web.WorkoutController;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class WorkoutPlannerApplicationTests {

	@Autowired
	AppController appController;

	@Autowired
	ExerciseController exerciseController;

	@Autowired
	UserController userController;

	@Autowired
	WorkoutController workoutController;

	@Test
	void contextLoads() throws Exception {
		assertThat(appController).isNotNull();
		assertThat(exerciseController).isNotNull();
		assertThat(userController).isNotNull();
		assertThat(workoutController).isNotNull();
	}
}
