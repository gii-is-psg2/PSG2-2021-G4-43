<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="books">
<jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#departureDate").datepicker({dateFormat: 'yy/mm/dd'});
            });
            $(function () {
                $("#arrivalDate").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
<jsp:body>
    <h2>
        Añadir reserva
    </h2>
    <form:form modelAttribute="book" class="form-horizontal" id="add-book-form">
        <div class="form-group has-feedback">
            <petclinic:selectFieldMap name="pet" label="Mascota" names="${pets}" size="5"/>
             <petclinic:inputField label="Fecha de llegada" name="arrivalDate"/>
             <petclinic:inputField label="Fecha de salida" name="departureDate"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
           		<input type="hidden" name="id" value="${pet.id}"/>
               	<button class="btn btn-default" type="submit">Guardar</button>
            </div>
        </div>
    </form:form>
    </jsp:body>
</petclinic:layout>