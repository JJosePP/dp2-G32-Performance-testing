package acme.features.spam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.spam.Spam;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Anonymous;
import acme.framework.services.AbstractShowService;

@Service
public class SpamShowService implements AbstractShowService<Anonymous, Spam>{
	
	@Autowired
	protected SpamRepository repository;

	@Override
	public boolean authorise(final Request<Spam> request) {
		assert request != null;

		return true;
	}

	@Override
	public void unbind(final Request<Spam> request, final Spam entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "words", "threshold");
		
	}

	@Override
	public Spam findOne(final Request<Spam> request) {
		assert request != null;
		
		Spam result;
		
		result = this.repository.findSpamById();
		
		return result;
	}

}
