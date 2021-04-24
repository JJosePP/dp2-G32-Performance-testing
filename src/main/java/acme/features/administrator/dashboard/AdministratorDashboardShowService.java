package acme.features.administrator.dashboard;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tasks.Task;
import acme.forms.Dashboard;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractShowService;

@Service
public class AdministratorDashboardShowService implements AbstractShowService<Administrator, Dashboard> {

	@Autowired
	AdministratorDashboardRepository repository;

	@Override
	public boolean authorise(final Request<Dashboard> request) {
		assert request != null;

		return true;
	}

	@Override
	public void unbind(final Request<Dashboard> request, final Dashboard entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "totalPublicTasks", "totalPrivateTasks","totalFinishedTasks", "totalNonFinishedTasks", 
			"averageTaskExecutionPeriod", "deviationTaskExecutionPeriod");
	}

	@Override
	public Dashboard findOne(final Request<Dashboard> request) {
		assert request != null;

		final Dashboard result;
		final Integer totalPublicTasks;
		final Integer totalPrivateTasks;
		final Integer totalFinishedTasks;
		final Integer totalNonFinishedTasks;
		final Double averageTaskExecutionPeriod;
		final Double deviationTaskExecutionPeriod;
		
		totalPublicTasks = this.repository.totalPublicTasks();
		totalPrivateTasks = this.repository.totalPrivateTasks();
		totalFinishedTasks = this.repository.totalFinishedTasks(new Date());
		totalNonFinishedTasks = this.repository.totalNonFinishedTasks(new Date());
	//	averageTaskExecutionPeriod = this.avgPeriodTask();
		averageTaskExecutionPeriod = this.repository.averageTaskExecutionPeriod();
		deviationTaskExecutionPeriod = this.deviationPeriodTask();
				
		result = new Dashboard();
		result.setTotalPublicTasks(totalPublicTasks);
		result.setTotalPrivateTasks(totalPrivateTasks);
		result.setTotalFinishedTasks(totalFinishedTasks);
		result.setTotalNonFinishedTasks(totalNonFinishedTasks);
		result.setAverageTaskExecutionPeriod(averageTaskExecutionPeriod);
		result.setDeviationTaskExecutionPeriod(deviationTaskExecutionPeriod);
				
		return result;
	}
	
	/** Aux **/
	private Double avgPeriodTask(){
		final Collection<Task> allTasks = this.repository.allTasks();
		
		long sum = 0L;
		
		for(final Task t : allTasks) {
			final long startDate = t.getStartExecution().getTime();
			final long endDate = t.getEndExecution().getTime();
			final long period = endDate - startDate;
			sum = sum + period;
		}
		
		final Double average = (double) (sum / allTasks.size());
		
		final Double avgDays = average / (1000 * 60 * 60 * 24);
		
		return avgDays;
	}
	
	private Double deviationPeriodTask() {
		final Collection<Task> allTasks = this.repository.allTasks();
		
		final double avg = this.avgPeriodTask();
		Double variance = 0.;
		
		for(final Task t : allTasks) {
			final long startDate = t.getStartExecution().getTime();
			final long endDate = t.getEndExecution().getTime();
			final long period = endDate - startDate;
			Double range;
			range = Math.pow(period - avg, 2f);
			variance = variance + range;
		}
		
		variance = variance / allTasks.size();
		
		final Double deviation = Math.sqrt(variance);
		
		final Double devDays = deviation / (1000 * 60 * 60 * 24);
		
		return devDays;
	}

	
}
