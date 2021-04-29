package acme.features.administrator.task;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.tasks.Task;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Administrator;

@Controller
@RequestMapping("/administrator/task/")
public class AdministratorTaskController extends AbstractController<Administrator, Task>{

	@Autowired
	protected AdministratorTaskListService listService;
	
	@Autowired
	protected AdministratorTaskShowService showService;
	
	@Autowired
	protected AdministratorTaskUpdateService updateService;
	
	@Autowired 
	protected AdministratorTaskCreateService createService;
	
	@Autowired
	protected AdministratorTaskDeleteService deleteService;
	
	@PostConstruct
	protected void initialise() {
		super.addBasicCommand(BasicCommand.LIST, this.listService);
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
		super.addBasicCommand(BasicCommand.CREATE, this.createService);
		super.addBasicCommand(BasicCommand.UPDATE, this.updateService);
		super.addBasicCommand(BasicCommand.DELETE, this.deleteService);
	}
}
