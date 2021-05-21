<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="support">
    <h2>Detalles de contacto del Equipo de Soporte</h2>

    <table id="supportTable" class="table table-striped">
        <thead>
        <tr>
            <th>Email</th>
            <th>Fecha de inicio</th>
            <th>Fecha de fin</th>
            <th>Habitación</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${books}" var="book">
            <tr>
                <td>
                    <c:out value="${book.pet.name}"/>
                </td>
                <td>
                    <c:out value="${book.arrivalDate}"/>
                </td>
                <td>
                    <c:out value="${book.departureDate}"/>
                </td>
                <td>
                    <c:out value="${book.room.id}"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a class="btn btn-default" href='<spring:url value="/books/new" htmlEscape="true"/>'>Añadir reserva</a>
</petclinic:layout>
