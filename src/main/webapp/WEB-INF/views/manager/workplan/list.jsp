<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="manager.workplan.list.label.title" path="title" width="20%"/>
	<acme:list-column code="manager.workplan.list.label.startExecution" path="startExecution" width="20%"/>
	<acme:list-column code="manager.workplan.list.label.endExecution" path="endExecution" width="20%"/>
	<acme:list-column code="manager.workplan.list.label.workload" path="workload" width="20%"/>
	<acme:list-column code="manager.workplan.list.label.isPrivate" path="isPrivate" width="20%"/>
</acme:list>