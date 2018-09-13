<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt"	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles"	uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="route/edit.do" modelAttribute="route">
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="hikes"/>
	<form:hidden path="comments"/>
	<form:hidden path="user"/>
	<form:hidden path="pictures"/>

	<acme:textbox code="route.name" path="name"/>
	<acme:textbox code="route.length" path="length"/>
	<acme:textbox code="route.description" path="description"/>
 <br>
 <br><form:label path="pictures">
  <spring:message code="route.pictures" />
 </form:label>
 <form:input path="pictures" placeholder="http://www.link1.com, http://www.link2.com"/>
 <form:errors cssClass="error" path="pictures" />

 <br>
 <br>
	
	<%-- <form:label path="hikes">
		<spring:message code="route.hikes"/>
	</form:label>
	<form:select mulitple="true" path="hikes">
		<form:options items="${hikes}" itemLabel="name" itemValue="id" />
	</form:select>
	<form:errors cssClass="error" path="hikes" /> --%>
	<br />
	
	<security:authorize access="hasRole('USER')">	
	<input type="submit" name="save"
		value="<spring:message code="route.save" />" />&nbsp; 
	</security:authorize>	
	
	<input type="button" name="cancel"
		value="<spring:message code="route.cancel" />"
		onclick="javascript: relativeRedir('route/myRoutes.do');" />
</form:form>
