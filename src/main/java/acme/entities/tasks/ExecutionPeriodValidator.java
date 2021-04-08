package acme.entities.tasks;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExecutionPeriodValidator implements ConstraintValidator<ExecutionPeriodConstraint, Task>{

	@Override
	public boolean isValid(Task task, ConstraintValidatorContext context) {
		return task.getStartExecution().before(task.getEndExecution());
	}

}
