<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<form:form action="actor/edit.do" modelAttribute="actor">
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="userAccount"/>

	<acme:textbox code="actor.name" path="name"/>
	<acme:textbox code="actor.surname" path="surname"/>
	<acme:textbox code="actor.email" path="email"/>
	<acme:textbox code="actor.phone" path="phone"/>
	<acme:textbox code="actor.address" path="address"/>
	<acme:textbox code="actor.picture" path="picture"/>
	<br />

	<acme:submit code="actor.save" name="save" />
	<jstl:if test="${actor.id != 0}">
		<acme:submit code="actor.delete" name="delete" confirm="actor.confirm.delete"" />
	</jstl:if>
	<acme:cancel code="actor.cancel" url="actor/list.do" />

</form:form>
