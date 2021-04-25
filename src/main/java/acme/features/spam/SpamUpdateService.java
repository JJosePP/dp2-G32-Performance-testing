package acme.features.spam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.spam.Spam;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractUpdateService;

@Service
public class SpamUpdateService implements AbstractUpdateService<Administrator, Spam>{

	@Autowired
	protected SpamRepository repository;
	
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
//		assert request != null;
//		assert entity != null;
//		assert model != null;
//
//		StringBuilder buffer;
//		Collection<UserRole> roles;
//
//		request.unbind(entity, model, "username", "identity.name", "identity.surname", "identity.email");
//
//		roles = entity.getRoles();
//		buffer = new StringBuilder();
//		for (final UserRole role : roles) {
//			buffer.append(role.getAuthorityName());
//			buffer.append(" ");
//		}
//
//		model.setAttribute("roleList", buffer.toString());
//
//		if (entity.isEnabled()) {
//			model.setAttribute("status", UserAccountStatus.ENABLED);
//		} else {
//			model.setAttribute("status", UserAccountStatus.DISABLED);
//		}
		
	}

	@Override
	public Spam findOne(final Request<Spam> request) {
		assert request != null;
		
		Spam result;
		
		result = this.repository.findSpamById();
		
		return result;
	}

	@Override
	public void validate(final Request<Spam> request, final Spam entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
	}

	@Override
	public void update(final Request<Spam> request, final Spam entity) {
		assert request != null;
		assert entity != null;
		
		if (request.getModel().getString("updateType").equals("add")) {
			entity.getWords().concat(", " + request.getModel().getString("newWord"));
		} else if (request.getModel().getString("updateType").equals("remove")) {
			entity.getWords().replace(", " + request.getModel().getString("oldWord"), "");
		} else if (request.getModel().getString("updateType").equals("changeThreshold")) {
			entity.setThreshold(Double.valueOf(request.getModel().getString("newThresHold")));
		}
		
		this.repository.save(entity);
	}

}
