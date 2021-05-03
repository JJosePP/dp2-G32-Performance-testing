<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-textbox code="manager.workplan.form.label.title" path="title"/>
	<acme:form-moment code="manager.workplan.form.label.startExecution" path="startExecution"/>
	<acme:form-moment code="manager.workplan.form.label.endExecution" path="endExecution"/>
	<acme:form-textbox code="manager.workplan.form.label.workload" path="workload"/>
	<acme:form-select code="manager.workplan.form.label.isPrivate" path="isPrivate">
		<acme:form-option code="manager.workplan.form.label.private" value="${true}"/>
		<acme:form-option code="manager.workplan.form.label.public" value="${false}"/>
	</acme:form-select>
	<jstl:if test="${command == 'create' }">
		<acme:form-select code="mM" path="tasks">
			<jstl:forEach items="${tasks}" var="task">
				<acme:form-option code="" value="${task.id}"/>
			</jstl:forEach>
		</acme:form-select>
	</jstl:if>

		<acme:form-select code="mM" path="tasks">
			<jstl:forEach items="${tasks}" var="task">
				<acme:form-option code="${task.title}" value="${task.id}"></acme:form-option>
			</jstl:forEach>
		</acme:form-select>
	<acme:form-submit test="${command == 'create'}" code="manager.workplan.form.button.submit" action="/manager/workplan/create"/>
	<acme:form-submit test="${command == 'show'}" code="manager.workplan.form.button.update" action="/manager/workplan/update"/>
</acme:form>