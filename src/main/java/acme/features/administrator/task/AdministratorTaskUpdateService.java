package acme.features.administrator.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tasks.Task;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractUpdateService;

@Service
public class AdministratorTaskUpdateService implements AbstractUpdateService<Administrator, Task>{

	@Autowired
	protected AdministratorTaskRepository repository;

	@Override
	public boolean authorise(final Request<Task> request) {
		// TODO Auto-generated method stub
		assert request != null;
		final Principal principal;
		final int idPrincipal = request.getPrincipal().getAccountId();
		
		final int idUserTask = this.repository.findOneTaskById(request.getModel().getInteger("id")).getUserAccount().getId();
		if(idPrincipal == idUserTask) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public void bind(final Request<Task> request, final Task entity, final Errors errors) {
		// TODO Auto-generated method stub
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Task> request, final Task entity, final Model model) {
		// TODO Auto-generated method stub
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model, "title", "description", "startExecution","endExecution","info","workload","isPrivate");
	}

	@Override
	public Task findOne(final Request<Task> request) {
		// TODO Auto-generated method stub
		return this.repository.findOneTaskById(request.getModel().getInteger("id"));
	}

	@Override
	public void validate(final Request<Task> request, final Task entity, final Errors errors) {
		// TODO Auto-generated method stub
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void update(final Request<Task> request, final Task entity) {
		// TODO Auto-generated method stub
		assert request != null;
		assert entity != null;
		
		if(request.getModel().getString("newFinished").equals("True")) {
			entity.setIsPrivate(Boolean.TRUE);
		}else if(request.getModel().getString("newFinished").equals("False")){
			entity.setIsPrivate(Boolean.FALSE);
		}
		
		if(request.getModel().getString("newFinished").equals("True")) {
			entity.setIsFinished(Boolean.TRUE);
		}else if(request.getModel().getString("newFinished").equals("False")){
			entity.setIsFinished(Boolean.FALSE);
		}
		this.repository.save(entity);
	}
}
