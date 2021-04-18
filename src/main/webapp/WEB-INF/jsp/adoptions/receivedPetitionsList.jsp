<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->


<petclinic:layout pageName="adoptions">
    <h2>Peticiones de adopciones para <c:out value="${adoption.pet.name}"/></h2>

    <table id="receivedPetitionTable" class="table table-striped">
        <thead>
        <tr>
            <th>Solicitado por</th>
            <th>Descripción</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${petitions}" var="petition">
            <tr>
                <td>
                    <c:out value="${petition.owner.firstName} ${petition.owner.lastName}"/>
                </td>
                <td>
                    <c:out value="${petition.description}"/>
                </td>
                <td>
                	<spring:url value="/adoptions/{adoptionId}/accept/{petitionId}" var="AcceptUrl">
        				<spring:param name="adoptionId" value="${adoption.id}"/>
        				<spring:param name="petitionId" value="${petition.id}"/>
    				</spring:url>
    				<a href="${fn:escapeXml(AcceptUrl)}" class="btn btn-default">Aceptar</a>
    				<spring:url value="/adoptions/{adoptionId}/deny/{petitionId}" var="DenyUrl">
        				<spring:param name="adoptionId" value="${adoption.id}"/>
        				<spring:param name="petitionId" value="${petition.id}"/>
    				</spring:url>
    				<a href="${fn:escapeXml(DenyUrl)}" class="btn btn-default">Rechazar</a>
                </td>
		 	</tr>
        </c:forEach>
        </tbody>
    </table>
    <a class="btn btn-default" href='<spring:url value="/adoptions" htmlEscape="true"/>'>Volver a la lista de adopciones</a>
</petclinic:layout>
		 	
