<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<form:form action="user/edit.do" modelAttribute="user">
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="userAccount"/>
	<form:hidden path="routes"/>
	<form:hidden path="comments"/>
	<form:hidden path="chirps"/>
	<form:hidden path="follower"/>
	<form:hidden path="follow"/>

	<acme:textbox code="user.name" path="name"/>
	<acme:textbox code="user.surname" path="surname"/>
	<acme:textbox code="user.email" path="email"/>
	<acme:textbox code="user.phone" path="phone"/>
	<acme:textbox code="user.address" path="address"/>
	<acme:textbox code="user.picture" path="picture"/>
	<acme:textbox code="user.isBanned" path="isBanned"/>
	<acme:textbox code="user.isSuspicious" path="isSuspicious"/>
	<br />

	<acme:submit code="user.save" name="save" />
	<jstl:if test="${user.id != 0}">
		<acme:submit code="user.delete" name="delete" confirm="user.confirm.delete"" />
	</jstl:if>
	<acme:cancel code="user.cancel" url="user/list.do" />

</form:form>
