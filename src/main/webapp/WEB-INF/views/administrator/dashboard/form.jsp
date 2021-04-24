<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<h2>
	<acme:message
		code="administrator.dashboard.form.title.general-indicators" />
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.total-public-tasks" /></th>
		<td><acme:print value="${totalPublicTasks}"/></td>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.total-private-tasks" /></th>
		<td><acme:print value="${totalPrivateTasks}"/></td>
	</tr>
	
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.total-finished-tasks" /></th>
		<td><acme:print value="${totalFinishedTasks}"/></td>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.total-non-finished-tasks" />
		</th>
		<td><acme:print value="${totalNonFinishedTasks}"/></td>
	</tr>
	
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.average-task-execution-periods" />
		</th>
		<td><acme:print value="${averageTaskExecutionPeriod}"/></td>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.deviation-task-execution-periods" />
		</th>
		<td><acme:print value="${deviationTaskExecutionPeriod}"/></td>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.minimum-task-execution-periods" />
		</th>
		<td><acme:print value=""/></td>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.maximum-task-execution-periods" />
		</th>
		<td><acme:print value=""/></td>
	</tr>
	
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.average-task-workloads" />
		</th>
		<td><acme:print value=""/></td>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.deviation-task-workloads" />
		</th>
		<td><acme:print value=""/></td>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.minimum-task-workloads" />
		</th>
		<td><acme:print value=""/></td>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.maximum-task-workloads" />
		</th>
		<td><acme:print value=""/></td>
	</tr>
</table>