<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt"	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles"	uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
	
	<form:form action="report/selectRoute.do" modelAttribute="report">
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="moment"/>
	<form:hidden path="ticker"/>
	<form:hidden path="user"/>
	<form:hidden path="title"/>
	<form:hidden path="description"/>
	<form:hidden path="gauge"/>
	<form:hidden path="finalMode"/>
	<form:hidden path="approved"/>
	<form:hidden path="rejected"/>
	<form:hidden path="comment"/>
	<form:hidden path="administrator"/>
	
	<form:label path="route">
		<spring:message code="report.selectRoute" />:
	</form:label>
	<br>
	<form:select mulitple="false" path="route">
		<form:options items="${routes}" itemLabel="name" itemValue="id" />
	</form:select>
	<form:errors cssClass="error" path="route" />
	
	<input type="submit" name="save"
		value="<spring:message code="report.confirm" />" />&nbsp;

	<input type="button" name="cancel"
		value="<spring:message code="report.cancel" />"
		onclick="javascript: relativeRedir('welcome/index.do');" />
	<br />
	
	</form:form>