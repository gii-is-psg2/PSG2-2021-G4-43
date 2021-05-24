<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="causes">

    <h2>Informaci&oacute;n de la causa</h2>


    <table class="table table-striped">
        <tr>
            <th>Nombre</th>
            <td><b><c:out value="${cause.name}"/></b></td>
        </tr>
        <tr>
            <th>Descripcion</th>
            <td><b><c:out value="${cause.description}"/></b></td>
        </tr>
        <tr>
            <th>Dinero recaudado</th>
            <td><b><c:out value="${cause.budgetAchieved}"/></b></td>
        </tr>
        <tr>
            <th>Presupuesto objetivo</th>
            <td><b><c:out value="${cause.budgetTarget}"/></b></td>
        </tr>
        <tr>
            <th>ONG</th>
            <td><b><c:out value="${cause.ong}"/></b></td>
        </tr>
        <tr>
            <th>Meta alcanzada</th>
            <td>
				<c:if test="${cause.budgetAchieved > cause.budgetTarget}">
                   	<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;X</p>
                </c:if>
			</td>
        </tr>
    </table>

    <spring:url value="/causes/{id}/delete" var="deleteCauseUrl">
       					<spring:param name="id" value="${cause.id}"/>
   					</spring:url>
    <a href="${fn:escapeXml(deleteCauseUrl)}" class="btn btn-default">Borrar Causa</a>
    
    <spring:url value="/causes/{id}/donations" var="showDonation">
                        <spring:param name="id" value="${cause.id}"/>
                </spring:url>
    <a href="${fn:escapeXml(showDonation)}" class="btn btn-default">Historial de donaciones</a>
	
	<div class="col-lg-8">
		<spring:url value="/causes" var="back">
                	</spring:url>
    	<a href="${fn:escapeXml(back)}" class="btn btn-default">Atr&aacute;s</a>
	</div>
	
</petclinic:layout>
