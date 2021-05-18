package acme.testing.spam;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class SpamUpdateTest extends AcmePlannerTest {
	
	// Lifecycle management ---------------------------------------------------
	
	
	// Test cases -------------------------------------------------------------
	
	//Se comprueba que se pueden actualizar el umbral y las palabras marcadas como spam correctamente
	@ParameterizedTest
	@CsvFileSource(resources="/administrator/spam/update-positive.csv", encoding= "utf-8", numLinesToSkip= 1)
	@Order(10)
	public void updatePositive(final String threshold, final String words) {
		
		super.signIn("administrator", "administrator");
		super.clickOnMenu("Spam", "Spam list");
		
		super.fillInputBoxIn("threshold", threshold);
		super.fillInputBoxIn("words", words);
		
		super.clickOnSubmitButton("Update");
		
		super.clickOnMenu("Spam", "Spam list");
		
		super.signOut();
		
	}
	
	//Se comprueba que no se pueden actualizar con umbral negativo (límite inferior 0) y las palabras marcadas como spam vacías
	@ParameterizedTest
	@CsvFileSource(resources="/administrator/spam/update-negative.csv", encoding= "utf-8", numLinesToSkip= 1)
	@Order(20)
	public void updateNegative(final String threshold, final String words) {
		
		super.signIn("administrator", "administrator");
		
		super.clickOnMenu("Spam", "Spam list");
		
		super.fillInputBoxIn("threshold", threshold);
		super.fillInputBoxIn("words", words);
		super.clickOnSubmitButton("Update");
		super.checkErrorsExist();
		
		super.signOut();
				
	}
	
	//Se comprueba que no se pueden actualizar con 101 como umbral (límite superior 100) y las palabras marcadas como spam vacías
	@ParameterizedTest
	@CsvFileSource(resources="/administrator/spam/update-negative-two.csv", encoding= "utf-8", numLinesToSkip= 1)
	@Order(30)
	public void updateNegativeTwo(final String threshold, final String words) {
		
		super.signIn("administrator", "administrator");
		
		super.clickOnMenu("Spam", "Spam list");
				
		super.fillInputBoxIn("threshold", threshold);
		super.fillInputBoxIn("words", words);
		super.clickOnSubmitButton("Update");
		super.checkErrorsExist();
		
		super.signOut();
				
	}
}
