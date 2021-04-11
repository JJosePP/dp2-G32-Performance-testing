
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="authenticated.task.list.label.title" path="title" width="20%"/>
	<acme:list-column code="authenticated.task.list.label.description" path="description" width="20%"/>
	<acme:list-column code="authenticated.task.list.label.startExecution" path="startExecution" width="20%"/>
	<acme:list-column code="authenticated.task.list.label.endExecution" path="endExecution" width="20%"/>
	<acme:list-column code="authenticated.task.list.label.workload" path="workload" width="20%"/>
	<acme:list-column code="authenticated.task.list.label.isFinished" path="isFinished" width="20%"/>
</acme:list>