package com.example.project.assertions;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CountDownLatch;

import static java.time.Duration.ofMinutes;
import static java.time.Duration.ofMillis;

import org.junit.jupiter.api.Test;

import example.domain.Person;
import example.util.Calculator;

class AssertionDemo {

	private final Calculator calculator = new Calculator();

	private final Person person = new Person("Jane", "Doe");

	@Test
	void standardAssertions() {
		assertEquals(2, calculator.add(1, 1));
		assertEquals(4, calculator.multiply(2, 2), "The optional failure message is now the last parameter");

		// Lazily evaluates generateFailureMessage('a','b').
		assertTrue('a' < 'b', () -> generateFailureMessage('a', 'b'));
	}

	@Test
	void groupedAssertions() {
		// In a grouped assertion all assertions are executed, and all
		// failures will be reported together.
		assertAll("person", () -> assertEquals("Jane", person.getFirstName()),
				() -> assertEquals("Doe", person.getLastName()));

	}

	@Test
	void dependentAssertions() {
		// Within a code block, if an assertion fails the
		// subsequent code in the same block will be skipped.
		assertAll("properties", () -> {
			String firstName = person.getFirstName();
			assertNotNull(firstName);

			// Executed only if the previous assertion is valid.
			assertAll("first name", () -> assertTrue(firstName.startsWith("J")),
					() -> assertTrue(firstName.endsWith("e")));
		}, () -> {
			// Grouped assertion, so processed independently
			// of results of first name assertions.
			String lastName = person.getLastName();
			assertNotNull(lastName);

			// Executed only if the previous assertion is valid.
			assertAll("lastt name", () -> assertTrue(lastName.startsWith("D")),
					() -> assertTrue(lastName.endsWith("e")));

		});

	}
	
	@Test
	void exceptionTesting() {
		Exception exception= assertThrows(ArithmeticException.class, () ->
			calculator.divide(1, 0));
		assertEquals("/ by zero", exception.getMessage());	
	}
	
	@Test
	void timeoutNotExceeded() {
		assertTimeout(ofMinutes(2), () -> {
			calculator.add(1000,2000);
		});
	}
	
	@Test
	void timeoutNotExceededWithResult() {
		String actualResult = assertTimeout(ofMinutes(2), () -> {
				return "a result";
		});
		assertEquals("a result", actualResult);
	}
	
	@Test
	void timeoutNotExceededWitMethod() {
		String actualGreeting = assertTimeout(ofMinutes(2), AssertionDemo::greeting);
		assertEquals("Hello, World!", actualGreeting);
	}
	
	@Test
	void timeoutExceeded() {
		// The following assertion fails with an error message similar to:
		// execution exceeded timeout of 10 ms by 91 ms
		assertTimeout(ofMillis(10), () -> {
			Thread.sleep(100);
		});
	}
	
	@Test
	void timeoutExceededWithPreemptiveTermination() {
		// The following assertion fails with an error message similar to:
		// execution timed out after 10 ms
		assertTimeoutPreemptively(ofMillis(10), () -> {
			// Simulate task that takes more than 10 ms.
			new CountDownLatch(1).await();
		});
		
	}
	
	private static String greeting() {
		return "Hello, World!";
	}
	


	private static String generateFailureMessage(char a, char b) {
		return "Assertion messages can be lazily evaluated -- "
				+ "to avoid constructing complex messages unnecessarily.";
	}

}
