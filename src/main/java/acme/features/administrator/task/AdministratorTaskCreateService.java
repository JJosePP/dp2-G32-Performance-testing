package acme.features.administrator.task;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tasks.Task;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.entities.Principal;
import acme.framework.entities.UserAccount;
import acme.framework.services.AbstractCreateService;

@Service
public class AdministratorTaskCreateService implements AbstractCreateService<Administrator, Task>{

	@Autowired
	protected AdministratorTaskRepository repository;

	@Override
	public boolean authorise(final Request<Task> request) {
		// TODO Auto-generated method stub
		assert request != null;
		if(request.getModel().hasAttribute("id")) {
			return request.getModel().getInteger("id").equals(request.getPrincipal().getAccountId());
		}else {
			return true;
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
	public Task instantiate(final Request<Task> request) {
		// TODO Auto-generated method stub
		assert request != null;
		final Date startExecution = new Date("2021/08/15 13:02");
		final Date endExecution = new Date("2021/08/23 11:02");
		final Task result;
		
		Principal principal;
		principal = request.getPrincipal();
		final UserAccount usuario = this.repository.findOneUserAccountById(principal.getAccountId());
		
		result = new Task();
		result.setTitle("Task 001: Imports XML");
		result.setDescription("Import XML");
		result.setStartExecution(startExecution);
		result.setEndExecution(endExecution);
		result.setInfo("http://www.example.org");
		result.setWorkload(BigDecimal.valueOf(20.12));
		result.setUserAccount(usuario);
		result.setIsPrivate(true);
		result.setIsFinished(false);
		return result;
	}

	@Override
	public void validate(final Request<Task> request, final Task entity, final Errors errors) {
		// TODO Auto-generated method stub
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void create(final Request<Task> request, final Task entity) {
		// TODO Auto-generated method stub
		assert request != null;
		assert entity != null;
		if(request.getModel().getString("newStatus").equals("True")) {
			entity.setIsPrivate(Boolean.TRUE);
		}else if(request.getModel().getString("newStatus").equals("False")){
			entity.setIsPrivate(Boolean.FALSE);
		}
		
		if(request.getModel().getString("newFinished").equals("True")) {
			entity.setIsFinished(Boolean.TRUE);
		}else if(request.getModel().getString("newFinished").equals("False")){
			entity.setIsFinished(Boolean.FALSE);
		}
		this.repository.save(entity);
	};
}
