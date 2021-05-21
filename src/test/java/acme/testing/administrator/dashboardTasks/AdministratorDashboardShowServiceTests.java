package acme.testing.administrator.dashboardTasks;

import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import acme.testing.AcmePlannerTest;

public class AdministratorDashboardShowServiceTests extends AcmePlannerTest {
	
	//Se comprueba que los valores que proporciona el dashboard son los esperados
	@ParameterizedTest
	@CsvFileSource(resources="/administrator/dashboardTasks/show-positive.csv", encoding= "utf-8", numLinesToSkip= 1)
	@Order(10)
	public void showDashboardPositive(final String dummy) {
		
		super.signIn("administrator", "administrator");
		super.clickOnMenu("Administrator", "Tasks Dashboard");
		final List<WebElement> elements = this.driver.findElements(By.xpath("//td"));
		
		for(int i=0; i<elements.size(); i++) {
			assert Double.parseDouble(elements.get(i).getText()) >= 0.;
		}
		super.signOut();
		
	}
	
	//Se comprueba que solo puede acceder al dashboard un administrador
	@ParameterizedTest
	@CsvFileSource(resources="/administrator/dashboardTasks/show-negative.csv", encoding= "utf-8", numLinesToSkip= 1)
	@Order(20)
	public void showDashboardNegative(final String credentialsUsername, final String credentialsPassword) {
		
		if(credentialsUsername.contains("anonymous")) {
			this.driver.get("http://localhost:8050/Acme-Planner/administrator/dashboard/show?language=en&debug=true");
			super.checkErrorsExist();
		}else {
			super.signIn(credentialsUsername, credentialsPassword);
			this.driver.get("http://localhost:8050/Acme-Planner/administrator/dashboard/show?language=en&debug=true");
			super.checkErrorsExist();
			super.signOut();
		}
	}
	
	
	


}
