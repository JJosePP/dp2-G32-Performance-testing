  
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
	
	<jstl:if test="${command == 'show'}">
	<acme:form-textbox code="manager.task.form.label.isFinished" path="isFinished" readonly="true"/>
	</jstl:if>
	
	<acme:form-select code="manager.task.form.label.newFinished" path="newFinished">
		<jstl:if test="${isFinished}">
			<acme:form-option code="YES" value="True" selected="true"/>
			<acme:form-option code="NO" value="False"/>
		</jstl:if>
		<jstl:if test="${!isFinished}">
			<acme:form-option code="YES" value="True"/>
			<acme:form-option code="NO" value="False" selected="true"/>
		</jstl:if>
	</acme:form-select>
	
	<jstl:if test="${command == 'show'}">
	<acme:form-textbox code="manager.task.form.label.isPrivate" path="isPrivate" readonly="true"/>
	</jstl:if>
	<acme:form-select code="manager.task.form.label.newStatus" path="newStatus">
		<jstl:if test="${isPrivate}">
			<acme:form-option code="Private" value="True" selected="true"/>
			<acme:form-option code="Public" value="False"/>
		</jstl:if>
		<jstl:if test="${!isPrivate}">
			<acme:form-option code="Private" value="True"/>
			<acme:form-option code="Public" value="False" selected="true"/>
		</jstl:if>
	</acme:form-select>
	
	<acme:form-submit test="${command == 'create'}" code="manager.task.form.button.submit" action="/manager/task/create"/>
	<acme:form-submit test="${command == 'show'}" code="manager.task.form.button.update" action="/manager/task/update"/>
	<acme:form-submit test="${command == 'update'}" code="manager.task.form.button.update" action="/manager/task/update"/>
	<acme:form-submit  test="${command == 'show'}" code="manager.task.form.button.delete" action="/manager/task/delete"/>
  	<acme:form-return code="manager.task.form.button.return"/>
</acme:form>