package acme.forms;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardWorkplan implements Serializable {
	
	
	protected static final long	serialVersionUID	= 1L;

	
	private Integer totalPublic;
	private Integer totalPrivate;
	private Integer totalFinished;
	private Integer totalNonFinished;
	private Double averageExecutionPeriod;
	private Double deviationExecutionPeriod;
	private Double minimunExecutionPeriod;
	private Double maximunExecutionPeriod;
	private BigDecimal averageWorkloads;
	private BigDecimal deviationWorkload;
	private Double minimunWorkload;
	private Double maximumWorkload;

}