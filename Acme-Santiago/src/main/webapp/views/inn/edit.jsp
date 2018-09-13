<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<form:form action="inn/edit.do" modelAttribute="inn">
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="creditCard"/>
	<form:hidden path="innkeeper"/>

	<acme:textbox code="inn.name" path="name"/>
	<acme:textbox code="inn.badge" path="badge"/>
	<acme:textbox code="inn.address" path="address"/>
	<acme:textbox code="inn.phone" path="phone"/>
	<acme:textbox code="inn.email" path="email"/>
	<acme:textbox code="inn.website" path="website"/>
	<br />

	<acme:submit code="inn.save" name="save" />
	<jstl:if test="${inn.id != 0}">
		<acme:submit code="inn.delete" name="delete" confirm="inn.confirm.delete"" />
	</jstl:if>
	<acme:cancel code="inn.cancel" url="inn/list.do" />

</form:form>
