<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<form:form action="innkeeper/edit.do" modelAttribute="innkeeper">
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="userAccount"/>
	<form:hidden path="inns"/>

	<acme:textbox code="innkeeper.name" path="name"/>
	<acme:textbox code="innkeeper.surname" path="surname"/>
	<acme:textbox code="innkeeper.email" path="email"/>
	<acme:textbox code="innkeeper.phone" path="phone"/>
	<acme:textbox code="innkeeper.address" path="address"/>
	<acme:textbox code="innkeeper.picture" path="picture"/>
	<br />

	<acme:submit code="innkeeper.save" name="save" />
	<jstl:if test="${innkeeper.id != 0}">
		<acme:submit code="innkeeper.delete" name="delete" confirm="innkeeper.confirm.delete"" />
	</jstl:if>
	<acme:cancel code="innkeeper.cancel" url="innkeeper/list.do" />

</form:form>
