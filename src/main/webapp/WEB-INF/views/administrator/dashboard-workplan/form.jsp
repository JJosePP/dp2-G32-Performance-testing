<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<h2>
	<acme:message
		code="administrator.dashboard-workplan.form.title.general-indicators" />
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard-workplan.form.label.total-public-workplans" /></th>
		<td><acme:print value="${totalPublic}"/></td>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard-workplan.form.label.total-private-workplans" /></th>
		<td><acme:print value="${totalPrivate}"/></td>
	</tr>
	
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard-workplan.form.label.total-finished-workplans" /></th>
		<td><acme:print value="${totalFinished}"/></td>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard-workplan.form.label.total-non-finished-workplans" />
		</th>
		<td><acme:print value="${totalNonFinished}"/></td>
	</tr>
	
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard-workplan.form.label.average-workplan-execution-periods" />
		</th>
		<td><acme:print value="${averageExecutionPeriod}"/></td>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard-workplan.form.label.deviation-workplan-execution-periods" />
		</th>
		<td><acme:print value="${deviationExecutionPeriod}"/></td>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard-workplan.form.label.minimum-workplan-execution-periods" />
		</th>
		<td><acme:print value="${minimunExecutionPeriod}"/></td>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard-workplan.form.label.maximum-workplan-execution-periods" />
		</th>
		<td><acme:print value="${maximunExecutionPeriod}"/></td>
	</tr>
	
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard-workplan.form.label.average-workplan-workloads" />
		</th>
		<td><acme:print value="${averageWorkloads}"/></td>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard-workplan.form.label.deviation-workplan-workloads" />
		</th>
		<td><acme:print value="${deviationWorkload}"/></td>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard-workplan.form.label.minimum-workplan-workloads" />
		</th>
		<td><acme:print value="${minimunWorkload}"/></td>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard-workplan.form.label.maximum-workplan-workloads" />
		</th>
		<td><acme:print value="${maximumWorkload}"/></td>
	</tr>
</table>

<h2>
	<acme:message code="administrator.dashboard-workplan.form.title.application-statuses"/>
</h2>

<div>
	<canvas id="canvas"></canvas>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		var data = {
			labels : [
					"TOTAL", "PUBLISHED", "NOT PUBLISHED"
			],
			datasets : [
				{
					data : [
						<jstl:out value="${totalPublic + totalPrivate}"/>, 
						<jstl:out value="${totalPublic}"/>, 
						<jstl:out value="${totalPrivate}"/>
					],
					backgroundColor:["blue", "green" , "red"],
				}
			]
		};
		var options = {
			scales : {
				yAxes : [
					{
						ticks : {
							suggestedMin : 0.0,
							suggestedMax : 1.0
						}
					}
				]
			},
			legend : {
				display : false
			}
		};
	
		var canvas, context;
	
		canvas = document.getElementById("canvas");
		context = canvas.getContext("2d");
		new Chart(context, {
			type : "bar",
			data : data,
			options : options
		});
	});
</script>
