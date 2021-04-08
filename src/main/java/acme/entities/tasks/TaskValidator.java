package acme.entities.tasks;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TaskValidator implements ConstraintValidator<TaskConstraint, Task>{
		
	@Override
	public void initialize(final TaskConstraint constraint) {
	}

	@Override
	public boolean isValid(final Task task, final ConstraintValidatorContext context) {
		
		Boolean res=true;
		
		final Date start = task.getStartExecution();
		final Date end = task.getEndExecution();
		
		final Long startHours = start.getTime();
		final Long endHours = end.getTime();
		
		final Long diff=TimeUnit.HOURS.convert(endHours-startHours, TimeUnit.MILLISECONDS);
		final Long hours = task.getWorkload().longValue(); 
		
		final BigDecimal minutes = task.getWorkload().remainder(BigDecimal.ONE).setScale(2, BigDecimal.ROUND_HALF_EVEN);
		final BigDecimal limit = new BigDecimal("0.60");
		
		if(start.after(end)) {
			
			res= false;	
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("Start must be after end of execution period").addConstraintViolation();
			
		} else if(hours >= diff) {
			
			res= false;	
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("Workload must be within execution period's hours").addConstraintViolation();
			
		} else if(minutes.compareTo(limit)>=0){
			
			res= false;	
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("Minutes must be under 60").addConstraintViolation();
			
		}
		
		return res;
	}

}