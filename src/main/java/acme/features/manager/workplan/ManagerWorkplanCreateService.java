package acme.features.manager.workplan;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tasks.Task;
import acme.entities.workplan.Workplan;
import acme.features.manager.task.ManagerTaskRepository;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Manager;
import acme.framework.entities.Principal;
import acme.framework.entities.UserAccount;
import acme.framework.services.AbstractCreateService;

@Service
public class ManagerWorkplanCreateService implements AbstractCreateService<Manager, Workplan>{

	@Autowired
	protected ManagerWorkplanRepository repository;
	
	@Autowired
	protected ManagerTaskRepository taskRepository;

	@Override
	public boolean authorise(final Request<Workplan> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<Workplan> request, final Workplan entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Workplan> request, final Workplan entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "title","isPrivate","startExecution","endExecution","workload");
	}

	@Override
	public Workplan instantiate(final Request<Workplan> request) {
		assert request != null;
		
		final Date startExecution = new Date("2021/06/12 12:00");
		final Date endExecution = new Date("2021/08/10 18:00");
		
		final Principal principal = request.getPrincipal();
		final UserAccount usuario = this.repository.findOneUserAccountById(principal.getAccountId());
		final Workplan result = new Workplan();
		result.setTitle("My Work plan");
		result.setStartExecution(startExecution);
		result.setEndExecution(endExecution);
//		result.setIsPrivate(true);
		result.setWorkload(BigDecimal.valueOf(0.0));
		result.setUserAccount(usuario);
		//result.setTasks(null);
		final Collection<Task> tasks = this.taskRepository.findAllTaskById(principal.getAccountId());
		final Model model = new Model();
		model.setAttribute("tasks", tasks);
		System.out.println(tasks);
		request.setModel(model);
		return result;
	}

	@Override
	public void validate(final Request<Workplan> request, final Workplan entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void create(final Request<Workplan> request, final Workplan entity) {
		assert request != null;
		assert entity != null;
		
		this.repository.save(entity);
	}
	
}
