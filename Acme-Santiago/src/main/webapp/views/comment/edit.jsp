<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<form:form action="comment/edit.do" modelAttribute="comment">
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="user"/>
	<form:hidden path="hike"/>
	<form:hidden path="route"/>
	<form:hidden path="moment"/>

	<acme:textbox code="comment.description" path="description"/>
	<acme:textbox code="comment.rating" path="rating"/>
	<acme:textarea code="comment.pictures" path="pictures" />
	<br />

	<security:authorize access="hasRole('USER')">
	<input type="submit" name="save"
		value="<spring:message code="comment.save" />" />&nbsp; 
	</security:authorize> 
	
	<acme:cancel code="comment.cancel" url="comment/list.do" />

</form:form>
