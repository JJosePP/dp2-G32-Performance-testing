<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-textbox code="manager.workplan.form.label.title" path="title"/>
	<acme:form-moment code="manager.workplan.form.label.startExecution" path="startExecution"/>
	<acme:form-moment code="manager.workplan.form.label.endExecution" path="endExecution"/>
	<acme:form-textbox code="manager.workplan.form.label.workload" path="workload" readonly="true"/>
	<acme:form-select code="manager.workplan.form.label.isPrivate" path="isPrivate">
		<acme:form-option code="manager.workplan.form.label.private" value="${true}"/>
		<acme:form-option code="manager.workplan.form.label.public" value="${false}"/>
	</acme:form-select>

	<jstl:if test="${command == 'show' }">
		<acme:form-select code="manager.workplan.form.label.taskAssinged" path="">
			<jstl:forEach items="${tasks}" var="task">
				<acme:form-option code="${task.title}" value="${task.id}"></acme:form-option>
			</jstl:forEach>
		</acme:form-select>
		<acme:form-select code="manager.workplan.form.label.addTask" path="tasksUnassigned">
			<acme:form-option code="NONE" value="-1" selected="true"></acme:form-option>
			<jstl:forEach items="${tasksUnassigned}" var="task">
				<acme:form-option code="${task.title}" value="${task.id}"></acme:form-option>
			</jstl:forEach>
		</acme:form-select>
	</jstl:if>
	<acme:form-submit test="${command == 'create'}" code="manager.workplan.form.button.submit" action="/manager/workplan/create"/>
	<acme:form-submit test="${command == 'show'}" code="manager.workplan.form.button.update" action="/manager/workplan/update"/>
	<acme:form-submit test="${command == 'show'}" code="manager.workplan.form.button.delete" action="/manager/workplan/delete"/>
	<acme:form-return code="manager.workplan.form.button.return"/>
</acme:form>