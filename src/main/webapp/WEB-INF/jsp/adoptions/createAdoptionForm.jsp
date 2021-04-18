<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="adoptions">
    <jsp:body>
        <h2>
            Poner en Adopción
        </h2>
        <form:form modelAttribute="adoption"
                   class="form-horizontal">
            <input type="hidden" name="id" value="${adoption.id}"/>
            <div class="form-group has-feedback">
                <div class="control-group">
                	<petclinic:selectFieldMap name="pet" label="Mascota" names="${pets}" size="5"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
					<button class="btn btn-default" type="submit">Añadir Mascota</button>
                </div>
            </div>
        </form:form>
    </jsp:body>
</petclinic:layout>