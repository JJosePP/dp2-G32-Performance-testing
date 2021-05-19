package acme.testing.administrator.task;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class AdministratorTaskShowTests extends AcmePlannerTest{

	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/task/show-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void showNegative(final String id) {
		super.signIn("administrator2", "administrator2");
		this.driver.get("http://localhost:8050/Acme-Planner/administrator/task/show?language=en&debug=true&id=" + id);
		super.checkErrorsExist();
	}
	
	// El test positivo está implementado en los tests de listado.
}
