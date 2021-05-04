package acme.features.manager.workplan;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tasks.Task;
import acme.entities.workplan.Workplan;
import acme.features.manager.task.ManagerTaskRepository;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Manager;
import acme.framework.services.AbstractShowService;

@Service
public class ManagerWorkplanShowService implements AbstractShowService<Manager, Workplan> {

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
	public void unbind(final Request<Workplan> request, final Workplan entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		

		final Collection<Task> tasks = this.taskRepository.findAllTaskById(request.getPrincipal().getAccountId());
		final Collection<Task> workplanTasks = entity.getTasks();
		final Collection<Task> tasksUnassigned = new ArrayList<>();
		
		for(final Task t:tasks) {
			if(!workplanTasks.contains(t)) {
				tasksUnassigned.add(t);
			}
		}
		
		model.setAttribute("tasksUnassigned", tasksUnassigned);

		request.unbind(entity, model,"title","startExecution","endExecution","workload","isPrivate","tasks");
		
	}

	@Override
	public Workplan findOne(final Request<Workplan> request) {
		assert request != null;
		
		final Workplan w = this.repository.findOneById(request.getModel().getInteger("id"));

		return w;
	}
}