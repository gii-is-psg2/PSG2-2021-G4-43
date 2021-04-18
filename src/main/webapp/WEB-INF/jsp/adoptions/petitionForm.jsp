<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="adoptions">
    <h2>
        Nueva petición de adopción.
    </h2>
    <form:form modelAttribute="petition" class="form-horizontal" id="add-petition-form">
    	<h3>Adoptando a: <c:out value="${petition.adoption.pet.name}"/></h3>
    	
        <div class="form-group has-feedback">
            <petclinic:areaField label="Realiza una descripción de como tratarás a la mascota" name="description"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
            	<input type="hidden" name="owner.id" value="${petition.owner.id}"/>
            	<input type="hidden" name="adoption.id" value="${petition.adoption.id}"/>
            	<input type="hidden" name="state" value="${petition.state}"/>
            	<button class="btn btn-default" type="submit">Realizar solicitud</button>
            </div>
        </div>
    </form:form>
</petclinic:layout>
