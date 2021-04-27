  

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="administrator.task.list.label.title" path="title" width="20%"/>
	<acme:list-column code="administrator.task.list.label.description" path="description" width="20%"/>
	<acme:list-column code="administrator.task.list.label.info" path="info" width="20%"/>
</acme:list>