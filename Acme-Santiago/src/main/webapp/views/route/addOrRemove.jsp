<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt"	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles"	uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<jstl:choose>

	<jstl:when test="${option == true }">
	
	<form:form action="route/addHikes.do" modelAttribute="route">
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="name"/>
	<form:hidden path="length"/>
	<form:hidden path="description"/>
	<form:hidden path="pictures"/>
	<form:hidden path="comments"/>
	<form:hidden path="user"/>
	
	<form:label path="hikes">
		<spring:message code="route.select" />:
	</form:label>
	<br>
	<form:select mulitple="true" path="hikes">
		<form:options items="${hikes}" itemLabel="name" itemValue="id" />
	</form:select>
	<form:errors cssClass="error" path="hikes" />
	
	<input type="submit" name="save"
		value="<spring:message code="route.confirm" />" />&nbsp;

	<input type="button" name="cancel"
		value="<spring:message code="route.cancel" />"
		onclick="javascript: relativeRedir('route/myRoutes.do');" />
	<br />
	
	</form:form>
	</jstl:when>
	
	<jstl:when test="${option == false }">
	
	<form:form action="route/removeHikes.do" modelAttribute="route">
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="name"/>
	<form:hidden path="length"/>
	<form:hidden path="description"/>
	<form:hidden path="pictures"/>
	<form:hidden path="comments"/>
	<form:hidden path="user"/>
	
	<form:label path="hikes">
		<spring:message code="route.selectRemove" />:
	</form:label>
	<br>
	<form:select mulitple="true" path="hikes">
		<form:options items="${hikes}" itemLabel="name" itemValue="id" />
	</form:select>
	<form:errors cssClass="error" path="hikes" />
	
	<input type="submit" name="remove"
		value="<spring:message code="route.remove" />" />&nbsp; 

	<input type="button" name="cancel"
		value="<spring:message code="route.cancel" />"
		onclick="javascript: relativeRedir('route/myRoutes.do');" />
	<br />
	
	</form:form>
	
	</jstl:when>
</jstl:choose>