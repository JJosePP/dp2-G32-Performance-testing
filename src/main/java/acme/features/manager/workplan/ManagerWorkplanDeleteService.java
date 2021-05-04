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
import acme.framework.services.AbstractDeleteService;

@Service
public class ManagerWorkplanDeleteService implements AbstractDeleteService<Manager, Workplan>{

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
		assert request != null;
		return this.repository.findOneById(request.getModel().getInteger("id"));
	}

	@Override
	public void validate(final Request<Workplan> request, final Workplan entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
	}

	@Override
	public void delete(final Request<Workplan> request, final Workplan entity) {
		//Obtenemos la lista de tasks del workplan que vamos a borrar
		final Collection<Task> tasksList = entity.getTasks();
		
		//De cada una de las task eliminamos el workplan de su lista de workplans asignados y guardamos dicha task
		for(final Task t:tasksList) {
			t.getWorkplans().remove(entity);
			this.taskRepository.save(t);
		}
		
		//Eliminamos el workplan
		this.repository.delete(entity);
		
	}

}
