package acme.features.administrator.task;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.spam.Spam;
import acme.entities.tasks.Task;
import acme.features.spam.SpamRepository;
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
	
	@Autowired
	protected SpamRepository spamRepository;

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
		
		final boolean notSpamTitle = this.esSpam(entity.getTitle());
		final boolean notSpamDescription = this.esSpam(entity.getDescription());

		errors.state(request, !notSpamTitle, "title", "anonymous.shout.error.text");
		errors.state(request, !notSpamDescription, "description", "anonymous.shout.error.text");
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
	
	public boolean esSpam(final String text) {
		
		final Spam spamObject = this.spamRepository.findSpamById();
		
		final List<String> spamWords = Arrays.asList(spamObject.getWords().split(", "));
		System.out.println(spamWords);
		
		final String[] taskWords = text.toLowerCase().split(" ");
		
		Double numberSpamWords = 0.;
		Double spamPercentaje;
		final Double length = (double) taskWords.length;
		
		for(final String word:taskWords) {
			final String cleanWord = word.replaceAll("(?![À-ÿ\\u00f1\\u00d1a-zA-Z0-9]).", "");
			System.out.println(cleanWord);
			if(spamWords.contains(cleanWord)) {
				numberSpamWords++;
			}
		}
		
		spamPercentaje = ((numberSpamWords/length)*100);
		System.out.println(spamPercentaje);
		if(spamPercentaje>spamObject.getThreshold()) {
			return true;
		}else {
			return false;
		}
		
	}
}
