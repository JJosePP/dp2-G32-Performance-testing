package acme.features.administrator.spam;

import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.spam.Spam;
import acme.features.spam.AnySpamRepository;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractCreateService;

public class AdministratorSpamCreateService implements AbstractCreateService<Administrator, Spam>{
	
	@Autowired
	protected AnySpamRepository repository;

	@Override
	public boolean authorise(final Request<Spam> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<Spam> request, final Spam entity, final Errors errors) {

		assert request != null;
		assert entity != null;
		assert errors != null;
		
		request.bind(entity, errors);
		
	}

	@Override
	public void unbind(final Request<Spam> request, final Spam entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "words", "threshold");
		
	}

	@Override
	public Spam instantiate(final Request<Spam> request) {
		assert request != null;
		final Spam result;
		
		result = new Spam();
		result.setThreshold(22.3);
		result.setWords("sexo");
		return result;
	}

	@Override
	public void validate(final Request<Spam> request, final Spam entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
	}

	@Override
	public void create(final Request<Spam> request, final Spam entity) {
		assert request != null;
		assert entity != null;
		
		this.repository.save(entity);
		
	}

}
