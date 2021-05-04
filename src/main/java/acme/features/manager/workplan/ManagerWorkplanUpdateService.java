package acme.features.manager.workplan;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tasks.Task;
import acme.entities.workplan.Workplan;
import acme.features.manager.task.ManagerTaskRepository;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Manager;
import acme.framework.services.AbstractUpdateService;

@Service
public class ManagerWorkplanUpdateService implements AbstractUpdateService<Manager, Workplan>{

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
		request.unbind(entity, model,"title","startExecution","endExecution","workload","isPrivate","tasks");
		
	}

	@Override
	public Workplan findOne(final Request<Workplan> request) {
		return this.repository.findOneById(request.getModel().getInteger("id"));
	}

	@Override
	public void validate(final Request<Workplan> request, final Workplan entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
	}

	@Override
	public void update(final Request<Workplan> request, final Workplan entity) {
		assert request != null;
		assert entity != null;
		
		final Integer newTaskId = request.getModel().getInteger("tasksUnassigned");
		
		//Obtenemos la task seleccionada
		final Task newTask = this.taskRepository.findOneTaskById(newTaskId);
		
		//Se añade el workplan que estamos editando a la lista de workplans de la task seleccionada
		final Collection<Workplan> workplanList = newTask.getWorkplans();
		workplanList.add(entity);
		newTask.setWorkplans(workplanList);
		
		//Obtenemos la lista de tasks del workload y añadimos la task seleccionada a la lista de tasks del nuevo workplan
		final Collection<Task> taskList = entity.getTasks();
		taskList.add(newTask);
		
		entity.setTasks(taskList);
		
		//Guardamos las entidades modificadas
		this.repository.save(entity);
		this.taskRepository.save(newTask);
		
	}

}
