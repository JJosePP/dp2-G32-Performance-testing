package acme.features.manager.workplan;

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
		
		if(newTaskId.equals(-1)) {
			this.repository.save(entity);
		}else {
			//Obtenemos la task seleccionada
			//worplan con id = 1
			final Task newTask = this.taskRepository.findOneTaskById(newTaskId);
			
			
			entity.getTasks().add(newTask);
			
			newTask.getWorkplans().add(entity);
			
			
			//Guardamos las entidades modificadas
			this.repository.save(entity);
			this.taskRepository.save(newTask);
		}
		
		
	}

}
