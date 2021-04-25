  
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	
	<acme:form-textbox code="manager.task.form.label.title" path="title"/>
	<acme:form-textarea code="manager.task.form.label.description" path="description"/>
	<acme:form-moment code="manager.task.form.label.startExecution" path="startExecution"/>
	<acme:form-moment code="manager.task.form.label.endExecution" path="endExecution"/>
	<acme:form-url code="manager.task.form.label.info" path="info"/>
	<acme:form-textbox code="manager.task.form.label.workload" path="workload"/>
	<acme:form-textbox code="manager.task.form.label.isPrivate" path="isPrivate"/>
	<acme:form-submit test="${command == 'create'}" code="manager.task.form.button.submit" action="/manager/task/create"/>
	<acme:form-submit test="${command == 'show'}" code="manager.task.form.button.update" action="/manager/task/update"/>
	<acme:form-submit test="${command == 'update'}" code="manager.task.form.button.update" action="/manager/task/update"/>
	<acme:form-submit  test="${command == 'show'}" code="manager.task.form.button.delete" action="/manager/task/delete"/>
  	<acme:form-return code="manager.task.form.button.return"/>
</acme:form>