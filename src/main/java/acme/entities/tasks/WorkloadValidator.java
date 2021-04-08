package acme.entities.tasks;

import java.util.concurrent.TimeUnit;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class WorkloadValidator implements ConstraintValidator<WorkloadConstraint, Task>{

	@Override
	public boolean isValid(Task task, ConstraintValidatorContext context) {
		
		Boolean res=true;
		Long start = task.getStartExecution().getTime();
		Long end= task.getEndExecution().getTime();
		
		Long diff=TimeUnit.HOURS.convert(end-start, TimeUnit.MILLISECONDS);
		Long hours = task.getWorkload().longValue(); 
		
		if(hours>=diff) {
			res=false;			
		}
		return res;
	}

}