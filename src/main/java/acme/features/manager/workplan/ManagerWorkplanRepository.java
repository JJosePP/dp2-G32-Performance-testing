package acme.features.manager.workplan;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.tasks.Task;
import acme.entities.workplan.Workplan;
import acme.framework.entities.UserAccount;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface ManagerWorkplanRepository extends AbstractRepository {

	@Query("select ua from UserAccount ua where ua.id = ?1")
	UserAccount findOneUserAccountById(int id);
	
	@Query("select w from Workplan w where w.userAccount.id = ?1")
	Collection<Workplan> findAllWorkPlankById(int id);
	
	@Query("select w from Workplan w where w.id = ?1")
	Workplan findOneById(int id);
	
	@Query("select w from Task w join fetch w.workplans workplan where workplan.id = ?1")
	Collection<Task> findWorkplansByTask(int id);
}
