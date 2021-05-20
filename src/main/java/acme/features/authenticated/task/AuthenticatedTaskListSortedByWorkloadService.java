package acme.features.authenticated.task;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tasks.Task;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.services.AbstractListService;

@Service
public class AuthenticatedTaskListSortedByWorkloadService implements AbstractListService<Authenticated, Task>{
	
	@Autowired
	protected AuthenticatedTaskRepository repository;

	@Override
	public boolean authorise(final Request<Task> request) {
		assert request != null;
		if(request.getModel().hasAttribute("id")) {
			return request.getModel().getInteger("id").equals(request.getPrincipal().getAccountId());
		}else {
			return true;
		}
	}

	@Override
	public void unbind(final Request<Task> request, final Task entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "title","description","startExecution","endExecution","workload", "isFinished");
	}

	@Override
	public Collection<Task> findMany(final Request<Task> request) {
		assert request != null;
		
		return this.repository.findTasksOrderByWorkload();
	}

}
