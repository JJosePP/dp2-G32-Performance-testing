package acme.features.administrator.dashboard;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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
	
//	@Autowired
//	Dashboard dashboard;

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
			"averageTaskExecutionPeriod", "deviationTaskExecutionPeriod", "minimunTaskExecutionPeriod","maximunTaskExecutionPeriod",
			"averageTaskWorkloads");
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
		final Double minimunTaskExecutionPeriod;
		final Double maximunTaskExecutionPeriod;
		final BigDecimal averageTaskWorkloads;
		
		result = new Dashboard();
		totalPublicTasks = this.repository.totalPublicTasks();
		totalPrivateTasks = this.repository.totalPrivateTasks();
		totalFinishedTasks = this.repository.totalFinishedTasks(new Date());
		totalNonFinishedTasks = this.repository.totalNonFinishedTasks(new Date());
		final Collection<Long> periods = this.calculatePeriods();
		averageTaskExecutionPeriod = this.avgPeriodTask(result, periods);
		deviationTaskExecutionPeriod = this.deviationPeriodTask(result,periods);
		minimunTaskExecutionPeriod = this.minimunTaskExecutionPeriod(periods);
		maximunTaskExecutionPeriod = this.maximunTaskExecutionPeriod(periods);
		averageTaskWorkloads = this.avgWorkloadTask(result);
				
		
		result.setTotalPublicTasks(totalPublicTasks);
		result.setTotalPrivateTasks(totalPrivateTasks);
		result.setTotalFinishedTasks(totalFinishedTasks);
		result.setTotalNonFinishedTasks(totalNonFinishedTasks);
		result.setAverageTaskExecutionPeriod(averageTaskExecutionPeriod);
		result.setDeviationTaskExecutionPeriod(deviationTaskExecutionPeriod);
		result.setMinimunTaskExecutionPeriod(minimunTaskExecutionPeriod);
		result.setMaximunTaskExecutionPeriod(maximunTaskExecutionPeriod);
		
				
		return result;
	}
	
	/** Aux **/
	
	private Collection<Long> calculatePeriods(){
		final Collection<Task> allTasks = this.repository.allTasks();
		final List<Long> periodsList = new ArrayList<>();
		
		for(final Task t : allTasks) {
			final long startDate = t.getStartExecution().getTime();
			final long endDate = t.getEndExecution().getTime();
			final long period = endDate - startDate;
			periodsList.add(period);
		}
		
		final Collection<Long> periods = periodsList;
		return periods;
	}
	private Double avgPeriodTask(final Dashboard d, final Collection<Long> periods){
				long sum = 0L;
		
		for(final Long p:periods) {
			sum = sum + p;
		}
		
		final Double average = (double) (sum / periods.size());
		
		final Double avgDays = average /(8.64e7); //(1000 * 60 * 60 * 24)

		d.setAverageTaskExecutionPeriod(average);
	
		return avgDays;
	}
	
	private Double deviationPeriodTask(final Dashboard d,final Collection<Long> periods) {
		
		final double avg = d.getAverageTaskExecutionPeriod();

		Double variance = 0.;
		
		for(final Long p : periods) {
			Double range;
			range = Math.pow(p - avg, 2f);
			variance = variance + range;
		}
		
		variance = variance / periods.size();
		
		final Double deviation = Math.sqrt(variance);
		
		final Double devDays = deviation / (8.64e7);
		
		return devDays;
	}

	Double minimunTaskExecutionPeriod(final Collection<Long> periods) {
		final Long min = Collections.min(periods);
		
		return min/8.64e7;
	}
	
	Double maximunTaskExecutionPeriod(final Collection<Long> periods) {
		final Long max = Collections.max(periods);
		
		return max/8.64e7;
	}
	
	BigDecimal avgWorkloadTask(final Dashboard d) {
		final Collection<BigDecimal> workloads = this.repository.allWorkloads();
		BigDecimal sum = BigDecimal.ZERO;
		BigDecimal parteEntera = new BigDecimal(0.00);
		System.out.println("Inicializo parteEntera: " + parteEntera);
		BigDecimal parteDecimal = new BigDecimal(0.00);
		System.out.println("Inicializo parteDecimal: " + parteDecimal);
		for(final BigDecimal w : workloads) {
			parteEntera = parteEntera.add(w.setScale(0, RoundingMode.FLOOR));
			final BigDecimal aux = w.setScale(0,RoundingMode.FLOOR);
			System.out.println(aux);
			parteDecimal = parteDecimal.add(w.subtract(aux));

		}
		System.out.println("parteEntera despues de bucle: " + parteEntera);
		System.out.println("parteDecimal despues de bucle: " + parteDecimal);
		//parte decimal = 0.92
		parteDecimal = parteDecimal.multiply(new BigDecimal(100)); //92
		System.out.println("parteDecimal*100 = " + parteDecimal);
		final BigDecimal aux = parteDecimal.divide(new BigDecimal(60));//aux = 1.53333
		System.out.println("aux = " + parteDecimal + " /60 = " + aux);
		BigDecimal horas = aux.setScale(2, RoundingMode.FLOOR); // horas = 1 //parteDecimal.divide(new BigDecimal(60).setScale(2, RoundingMode.FLOOR));
		System.out.println("horas = " + horas);
		final BigDecimal minutos = aux.subtract(horas).multiply(new BigDecimal(60)).divide(new BigDecimal(100).setScale(2, RoundingMode.CEILING));
		System.out.println("minutos = " + minutos);
		horas = horas.add(parteEntera).add(minutos);
		System.out.println("horas = " + horas);
		sum = horas.add(minutos);
		final BigDecimal average = sum.divide(new BigDecimal(workloads.size()));
		
		d.setAverageTaskWorkloads(average);
		
		return average;
	}
	
}
