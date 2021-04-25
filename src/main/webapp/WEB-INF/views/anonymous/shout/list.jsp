

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-return action="/anonymous/shout/list"
		code="anonymous.shout.list.button.all" />
	<acme:form-return action="/anonymous/shout/list-last-month"
		code="anonymous.shout.list.button.last-month" />
</acme:form>

<acme:list readonly="true">
	<acme:list-column code="anonymous.shout.list.label.moment"
		path="moment" width="20%" />
	<acme:list-column code="anonymous.shout.list.label.author"
		path="author" width="20%" />
	<acme:list-column code="anonymous.shout.list.label.text" path="text"
		width="60%" />
</acme:list>