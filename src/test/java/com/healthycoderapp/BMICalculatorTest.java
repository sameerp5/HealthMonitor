package com.healthycoderapp;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assume.assumeTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class BMICalculatorTest {
	
	private String environment = "Prod";
	
	@Nested
	class IsDietRecommendedTests {
		
		@ParameterizedTest(name = "weight={0}")
		@ValueSource(doubles = {79.2, 78.0, 77.3})
		void return_false_When_DietNotRecommended(double coderWeight) {
			// Arrange
			double weight = coderWeight;
			double height = 1.8;
			
			// Act
			boolean isRecommended = BMICalculator.isDietRecommended(weight, height);
			
			// Assert
			assertFalse(isRecommended);
		}
		
		@ParameterizedTest(name = "weight={0}, height={1}")
		//@CsvSource({"89.0, 1.8","88.2, 1.7", "99.5, 1.84"})
		@CsvFileSource(resources = "/JunitTestCases.csv")
		void return_true_When_DietRecommended(double coderWeight, double coderheight) {
			// Arrange
			double weight = coderWeight;
			double height = coderheight;
			
			// Act
			boolean isRecommended = BMICalculator.isDietRecommended(weight, height);
			
			// Assert
			assertTrue(isRecommended);
		}
		
		//@RepeatedTest(value=10, name=RepeatedTest.LONG_DISPLAY_NAME)
		@Test
		void throwException_When_ProvideWithHeightAsZero() {
			assumeTrue(BMICalculatorTest.this.environment.equalsIgnoreCase("Prod"));
			// Arrange
			double weight = 80.0;
			double height = 0.0;
		
			
			// Act
			Executable executable = () -> BMICalculator.isDietRecommended(weight, height);
			
			// Assert
			assertThrows(ArithmeticException.class, executable);
		}
	
	}
	
	@Nested
	class FindCoderWithWorstBmiTests {
		
		@Test
		void return_CoderWithWorstBmi_When_ProvideWithListOfCoders() {
			// Arrange
			List<Coder> listOfCoders = new ArrayList<>();
			listOfCoders.add(new Coder(1.8, 80.0)); //24.69
			listOfCoders.add(new Coder(1.7, 83.0)); // 28.72
			listOfCoders.add(new Coder(1.9, 89.7)); // 24.85
			listOfCoders.add(new Coder(1.96, 103.5)); // 26.95
			
			// Act
			Coder coderWithWorstBmi = BMICalculator.findCoderWithWorstBMI(listOfCoders);
			
			// Assert
			assertAll(
			() -> assertEquals(1.7, coderWithWorstBmi.getHeight()),
			() -> assertEquals(83.0, coderWithWorstBmi.getWeight()));
		}
		
		@Test
		void return_Null_When_ProvideWithEmptyListOfCoders() {
			// Arrange
			List<Coder> listOfCoders = new ArrayList<>();
			
			// Act
			Coder coderWithWorstBmi = BMICalculator.findCoderWithWorstBMI(listOfCoders);
			
			// Assert
			assertNull(coderWithWorstBmi);
		}
		
	}
	
	@Nested
	class getBmiScoresTests {
		
		@Test
		void return_BmiScores_When_ProvideWithListOfCoders() {
			// Arrange
			List<Coder> listOfCoders = new ArrayList<>();
			listOfCoders.add(new Coder(1.8, 80.0)); //24.69
			listOfCoders.add(new Coder(1.7, 83.0)); // 28.72
			listOfCoders.add(new Coder(1.9, 89.7)); // 24.85
			listOfCoders.add(new Coder(1.9, 78.5)); // 26.95
			double[] expectedBmiScores = {24.69, 28.72, 24.85, 28.67};
			
			// Act
			double[] actualBmiScores = BMICalculator.getBMIScores(listOfCoders);
			
			// Assert
			assertArrayEquals(expectedBmiScores, actualBmiScores);
		}
		
		@Test
		void throw_ArithmetciException_When_ProvideWithListOfCodershavingZeroHeight() {
			// Arrange
			List<Coder> listOfCoders = new ArrayList<>();
			listOfCoders.add(new Coder(1.8, 80.0)); //24.69
			listOfCoders.add(new Coder(1.7, 83.0)); // 28.72
			listOfCoders.add(new Coder(1.9, 89.7)); // 24.85
			listOfCoders.add(new Coder(0.0, 103.5)); // 26.95
			
			
			// Act
			Executable executable = () -> BMICalculator.getBMIScores(listOfCoders);
			
			// Assert
			assertThrows(ArithmeticException.class, executable);
			
			
		}
	}	
}
