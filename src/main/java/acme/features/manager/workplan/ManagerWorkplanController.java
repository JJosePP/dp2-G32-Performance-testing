package acme.features.manager.workplan;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.workplan.Workplan;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Manager;

@Controller
@RequestMapping("/manager/workplan/")
public class ManagerWorkplanController extends AbstractController<Manager, Workplan>{

	@Autowired
	protected ManagerWorkplanCreateService createService;
	
	@Autowired
	protected ManagerWorkplanListService listService;
	
	@Autowired
	protected ManagerWorkplanShowService showService;
	@PostConstruct
	protected void initialise() {
		super.addBasicCommand(BasicCommand.LIST, this.listService);
		super.addBasicCommand(BasicCommand.CREATE, this.createService);
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
	}
	
}
