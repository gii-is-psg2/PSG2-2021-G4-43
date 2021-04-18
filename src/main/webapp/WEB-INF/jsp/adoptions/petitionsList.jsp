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
    <h2>Peticiones de adopciones</h2>

    <table id="petitionTable" class="table table-striped">
        <thead>
        <tr>
            <th>Nombre</th>
            <th>Especie</th>
            <th>Descripción</th>
            <th>Estado</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${petitions}" var="petition">
            <tr>
                <td>
                    <c:out value="${petition.adoption.pet.name}"/>
                </td>
                <td>
                    <c:out value="${petition.adoption.pet.type}"/>
                </td>
                <td>
                    <c:out value="${petition.description}"/>
                </td>
                <td>
                	<c:out value="${petition.state}"/>
                </td>
		 	</tr>
        </c:forEach>
        </tbody>
    </table>
    <a class="btn btn-default" href='<spring:url value="/adoptions" htmlEscape="true"/>'>Volver a la lista de adopciones</a>
</petclinic:layout>
		 	
