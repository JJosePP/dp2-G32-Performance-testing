package acme.entities.spam;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Spam extends DomainEntity {
	
	// Serial version identifier ----------------------------------
	
	protected static final long serialVersionUID = 1L;
	
	// Attributes -------------------------------------------------
	
	@NotBlank
	protected String words;
	
	protected Double threshold;
}
