package acme.features.administrator.task;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.spam.Spam;
import acme.entities.tasks.Task;
import acme.features.spam.AnySpamRepository;
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
	
	@Autowired
	protected AnySpamRepository spamRepository;

	@Override
	public boolean authorise(final Request<Task> request) {
		// TODO Auto-generated method stub
		assert request != null;
		return true;
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
		request.getModel().setAttribute("idPrincipal", request.getPrincipal().getAccountId());
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
		
		final boolean notSpamTitle = this.esSpam(entity.getTitle());
		final boolean notSpamDescription = this.esSpam(entity.getDescription());

		errors.state(request, !notSpamTitle, "title", "anonymous.shout.error.text");
		errors.state(request, !notSpamDescription, "description", "anonymous.shout.error.text");
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
	
	
	public boolean esSpam(final String text) {
		
		final Spam spamObject = this.spamRepository.findSpam();
		
		final List<String> spamWords = Arrays.asList(spamObject.getWords().split(", "));
		
		final String[] taskWords = text.toLowerCase().split(" ");
		
		Double numberSpamWords = 0.;
		Double spamPercentaje;
		final Double length = (double) taskWords.length;
		
		for(final String word:taskWords) {
			final String cleanWord = word.replaceAll("(?![À-ÿ\\u00f1\\u00d1a-zA-Z0-9]).", "");
			if(spamWords.contains(cleanWord)) {
				numberSpamWords++;
			}
		}
		
		spamPercentaje = ((numberSpamWords/length)*100);
		if(spamPercentaje>spamObject.getThreshold()) {
			return true;
		}else {
			return false;
		}
		
	}
}
