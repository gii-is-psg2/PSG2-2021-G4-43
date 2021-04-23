<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="donations">

    <jsp:body>
	    <h2>
	        Realizar Donacion
	    </h2>
	    <form:form modelAttribute="donation" class="form-horizontal" id="add-donation-form">
	        <div class="form-group has-feedback">
	            <petclinic:inputField label="Cantidad" name="amount"/>
	        </div>
	        <div class="form-group">
	            <div class="col-sm-offset-2 col-sm-10">
	               	<button class="btn btn-default" type="submit">Realizar donacion</button>
	            </div>
	        </div>
	    </form:form>
	    <div class="col-lg-8">
		<spring:url value="/causes" var="back">
                	</spring:url>
    	<a href="${fn:escapeXml(back)}" class="btn btn-default">Atras</a>
	</div>
    </jsp:body>
</petclinic:layout>