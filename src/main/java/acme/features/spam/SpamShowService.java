package acme.features.spam;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.shouts.Shout;
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
		
		result = this.repository.findSpamById(12);
		
		return result;
	}

	public boolean esSpam(final Shout entity) {
		
		final Spam spamObject = this.repository.findSpamById(12);
		System.out.println(spamObject);
		
		final List<String> spamWords = Arrays.asList(spamObject.getWords().split(", "));
		
		final String[] shoutWords = entity.getText().replaceAll("(?![À-ÿ\\u00f1\\u00d1a-zA-Z0-9]).", "").toLowerCase().split(" ");
		System.out.println(shoutWords[0]);
		
		Double numberSpamWords = 0.;
		Double spamPercentaje;
		final Double length = (double) shoutWords.length;
		
		for(final String word:shoutWords) {
			if(spamWords.contains(word)) {
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
