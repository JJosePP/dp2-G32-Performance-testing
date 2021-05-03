package acme.entities.workplan;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import acme.entities.tasks.Task;
import acme.framework.entities.DomainEntity;
import acme.framework.entities.UserAccount;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Workplan extends DomainEntity {
	
	// Serial version identifier ----------------------------------
	
	protected static final long serialVersionUID = 1L;
	
	// Attributes -------------------------------------------------
	
	@NotBlank
	@Length(min=1, max=80)
	protected String title;
	
	@NotNull
	protected Boolean isPrivate;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date startExecution;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date endExecution;
	
	@Digits(integer = 8, fraction = 2)
	@DecimalMin(value="0.0", inclusive=true)
	protected BigDecimal workload;
	
	
	@ManyToMany(mappedBy = "workplans", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Collection<Task> tasks;

	@NotNull
	@ManyToOne
	@JoinColumn(name="user")
	protected UserAccount userAccount;
	
	// Derived attributes -----------------------------------------
	
	public void setWorkload() {
		BigDecimal sum = BigDecimal.ZERO;
		
		for(final Task t : this.tasks) {
			sum = sum.add(t.getWorkload());
		}
		
		this.workload = sum;
	}

	
}
