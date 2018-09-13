<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<form:form action="chirp/edit.do" modelAttribute="chirp">
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="user"/>
	<form:hidden path="moment"/>

	<acme:textbox code="chirp.title" path="title"/>
	<acme:textbox code="chirp.description" path="description"/>
	<br />

	<jstl:if test="${chirp.id == 0}">
	<input type="submit" name="save"
		value="<spring:message code="chirp.save" />" />&nbsp; 
	</jstl:if>
	
	<security:authorize access="hasRole('ADMIN')">
	<jstl:if test="${chirp.id != 0}">
	
	<input type="submit" name="delete"
			value="<spring:message code="chirp.delete" />"
			onclick="return confirm('<spring:message code="chirp.confirm.delete" />')" />&nbsp;
	</jstl:if>
	</security:authorize> 
	

</form:form>
