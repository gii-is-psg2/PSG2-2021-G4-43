<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<petclinic:layout pageName="donations">

<jsp:body>
<h2>Historial de donaciones</h2>
		<table id="causeTable" class="table table-striped">
			<thead>
			<tr>
				<th>Cliente</th>
				<th>Fecha</th>
				<th>Cantidad</th>
			</tr>
			</thead>
			<tbody>
				<c:forEach items="${donations}" var="donation">
					<tr>
						<td>
							<c:out value="${donation.client}"/>
						</td>
						<td>
							<c:out value="${donation.date}"/>
						</td>
						<td>
							<c:out value="${donation.amount}"/>
						</td>
				</c:forEach>
			</tbody>
		</table>
</jsp:body>
</petclinic:layout>