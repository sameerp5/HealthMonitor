package com.healthycoderapp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class DietPlannerTest {

	private DietPlanner dietplanner;

	@BeforeEach
	void beforeEach() {
		dietplanner = new DietPlanner(20, 30, 50);
	}

	@Test
	void return_DietPlan_When_providedFeMaleCoder() {
		// Arrange
		Coder coder = new Coder(1.72, 89.0, 56, Gender.FEMALE);
		DietPlan expectedDietplan = new DietPlan(1888, 94, 63, 236);

		// Act
		DietPlan actualDietPlan = dietplanner.calculateDiet(coder);
		
		// Assert
		assertAll(
		() -> assertEquals(expectedDietplan.getCalories(), actualDietPlan.getCalories()),
		() -> assertEquals(expectedDietplan.getCarbohydrate(), actualDietPlan.getCarbohydrate()),
		() -> assertEquals(expectedDietplan.getFat(), actualDietPlan.getFat()),
		() -> assertEquals(expectedDietplan.getProtein(), actualDietPlan.getProtein()));
	}
	
	@Test
	void return_DietPlan_When_providedMaleCoder() {
		// Arrange
		Coder coder = new Coder(1.72, 89.0, 56, Gender.MALE);
		DietPlan expectedDietplan = new DietPlan(2129, 106, 71, 266);

		// Act
		DietPlan actualDietPlan = dietplanner.calculateDiet(coder);
		
		// Assert
		assertAll(
		() -> assertEquals(expectedDietplan.getCalories(), actualDietPlan.getCalories()),
		() -> assertEquals(expectedDietplan.getCarbohydrate(), actualDietPlan.getCarbohydrate()),
		() -> assertEquals(expectedDietplan.getFat(), actualDietPlan.getFat()),
		() -> assertEquals(expectedDietplan.getProtein(), actualDietPlan.getProtein()));
	}
	
	@Test
	void throw_RunTimeExcetion_When_providedWithOtherThan100Percent() {
		// Arrange
		Coder coder = new Coder(1.72, 89.0, 56, Gender.MALE);

		// Act
		Executable executable = () -> new DietPlanner(20, 30, 55).calculateDiet(coder);
		
		// Assert
		assertThrows(RuntimeException.class, executable);
	}
}
