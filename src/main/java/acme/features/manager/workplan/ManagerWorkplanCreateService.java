package acme.features.manager.workplan;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.workPlan.WorkPlan;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Manager;
import acme.framework.entities.Principal;
import acme.framework.entities.UserAccount;
import acme.framework.services.AbstractCreateService;

@Service
public class ManagerWorkplanCreateService implements AbstractCreateService<Manager, WorkPlan>{

	@Autowired
	protected ManagerWorkplanRepository repository;

	@Override
	public boolean authorise(final Request<WorkPlan> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<WorkPlan> request, final WorkPlan entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<WorkPlan> request, final WorkPlan entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "title","isPrivate","startExecution","endExecution","workload");
	}

	@Override
	public WorkPlan instantiate(final Request<WorkPlan> request) {
		assert request != null;
		
		final Date startExecution = new Date("2021/06/12 12:00");
		final Date endExecution = new Date("2021/08/10 18:00");
		
		final Principal principal = request.getPrincipal();
		final UserAccount usuario = this.repository.findOneUserAccountById(principal.getAccountId());
		final WorkPlan result = new WorkPlan();
		result.setTitle("My Work plan");
		result.setStartExecution(startExecution);
		result.setEndExecution(endExecution);
		result.setIsPrivate(true);
		result.setWorkload(BigDecimal.valueOf(0.0));
		result.setUserAccount(usuario);
		//result.setTasks(null);
		return result;
	}

	@Override
	public void validate(final Request<WorkPlan> request, final WorkPlan entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void create(final Request<WorkPlan> request, final WorkPlan entity) {
		assert request != null;
		assert entity != null;
		
		this.repository.save(entity);
	}
	
}
