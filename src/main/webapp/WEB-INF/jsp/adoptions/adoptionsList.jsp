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
    <h2>Adopciones</h2>

    <table id="adoptionTable" class="table table-striped">
        <thead>
        <tr>
            <th>Nombre</th>
            <th>Especie</th>
            <th>Dueño</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${adoptions}" var="adoption">
            <tr>
                <td>
                    <c:out value="${adoption.pet.name}"/>
                </td>
                <td>
                    <c:out value="${adoption.pet.type}"/>
                </td>
                <td>
                    <c:out value="${adoption.pet.owner.firstName} ${adoption.pet.owner.lastName}"/>
                </td>
                <td>
                <c:choose>
                    <c:when test="${adoption.pet.owner == owner}">
                    	<spring:url value="/adoptions/{adoptionId}" var="ReceivedRequestsUrl">
        					<spring:param name="adoptionId" value="${adoption.id}"/>
    					</spring:url>
    					<a href="${fn:escapeXml(ReceivedRequestsUrl)}" class="btn btn-default">Gestionar solicitudes</a>
                    </c:when>
                    <c:otherwise>
                    	<spring:url value="/adoptions/petitions/request/{adoptionId}" var="AdoptionRequestUrl">
        					<spring:param name="adoptionId" value="${adoption.id}"/>
    					</spring:url>
    					<a href="${fn:escapeXml(AdoptionRequestUrl)}" class="btn btn-default">Solicitar adopción</a>
                    </c:otherwise>
                </c:choose>
                </td>
		 	</tr>
        </c:forEach>
        </tbody>
    </table>
    <a class="btn btn-default" href='<spring:url value="/adoptions/new" htmlEscape="true"/>'>Poner una mascota en adopción</a>
    <a class="btn btn-default" href='<spring:url value="/adoptions/petitions" htmlEscape="true"/>'>Mis peticiones de adopción</a>
</petclinic:layout>
		 	
