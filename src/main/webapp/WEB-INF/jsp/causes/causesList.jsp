<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="causes">
    <h2>Causas</h2>

    <table id="causeTable" class="table table-striped">
        <thead>
        <tr>
            <th>Nombre</th>
            <th>Dinero recaudado</th>
            <th>Presupuesto objetivo</th>
            <th>Meta alcanzada</th>
            <th>Detalles</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${causes}" var="cause">
            <tr>
                <td>
                    <c:out value="${cause.name}"/>
                </td>
                <td>
                    <c:out value="${cause.budgetAchieved}"/>
                </td>
                <td>
                    <c:out value="${cause.budgetTarget}"/>
                </td>
                <td>
                	<c:if test="${cause.budgetAchieved > cause.budgetTarget}">
                    	<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;X</p>
                    </c:if>
                </td>
                <td>
					<spring:url value="/causes/{causeId}" var="CauseDetailsUrl">
       					<spring:param name="causeId" value="${cause.id}"/>
   					</spring:url>
   					<a href="${fn:escapeXml(CauseDetailsUrl)}" class="btn btn-default">Mas Detalles</a>
		 		</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a class="btn btn-default" href='<spring:url value="/causes/new" htmlEscape="true"/>'>Añadir causa</a>
</petclinic:layout>
