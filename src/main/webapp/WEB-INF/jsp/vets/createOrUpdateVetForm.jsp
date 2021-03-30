<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="vets">
    <h2>
        <c:if test="${vet['new']}">Nuevo </c:if> Veterinario
    </h2>
    <form:form modelAttribute="vet" class="form-horizontal" id="add-vet-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Nombre" name="firstName"/>
            <petclinic:inputField label="Apellidos" name="lastName"/>
            <div class="formGroup">
				<div class="col-sm-10">
					<label>Especialidades:</label>
					<form:checkboxes items="${specialties}" path="specialties"
							delimiter="&nbsp;&nbsp;&nbsp;" itemLabel="name" style="border:10px;" />
				</div>
			</div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${vet['new']}">
                        <button class="btn btn-default" type="submit">Añadir Veterinario</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Actualizar Veterinario</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout>

