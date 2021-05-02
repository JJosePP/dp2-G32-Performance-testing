package acme.features.administrator.dashboardWorkplans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.workPlan.WorkPlan;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorDashboardWorkplanRepository extends AbstractRepository {
	
	@Query("select count(wkp) from WorkPlan wkp where wkp.isPrivate = false")
	Integer totalPublicWorkplans();

	@Query("select count(wkp) from WorkPlan wkp where wkp.isPrivate = true")
	Integer totalPrivateWorkplans();

	@Query("select count(wkp) from WorkPlan wkp where wkp.endExecution < ?1")
	Integer totalFinishedWorkplans(Date now);

	@Query("select count(wkp) from WorkPlan wkp where wkp.endExecution > ?1")
	Integer totalNonFinishedWorkplans(Date now);

	@Query("select wkp from WorkPlan wkp")
	Collection<WorkPlan> allWorkplans();

	@Query("select workload from WorkPlan wkp")
	Collection<BigDecimal> allWorkloads();

	@Query("select min(wkp.workload) from WorkPlan wkp")
	Double minimunWorkplanWorkload();

	@Query("select max(wkp.workload) from WorkPlan wkp")
	Double maximumWorkplanWorkload();

}
