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
            <th>Fecha</th>
            <th>Tipo</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${adoptions}" var="adoption">
            <tr>
                <td>
                    <c:out value="${adoption.pet.name}"/>
                </td>
                <td>
                    <c:out value="${adoption.pet.birthDate}"/>
                </td>
                <td>
                    <c:out value="${adoption.pet.type}"/>
                </td>
		 	</tr>
        </c:forEach>
        </tbody>
    </table>
    <a class="btn btn-default" href='<spring:url value="/adoptions/new" htmlEscape="true"/>'>Poner mascota en adopción</a>
</petclinic:layout>
		 	
