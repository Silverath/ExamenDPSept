<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="route/searchHikes.do"
	modelAttribute="searchForm">

	

	<form:label path="minHikes">
		<spring:message code="route.minHikes" />:
	</form:label>
	<form:input type= "number" min ="0" path="minHikes" />
	<form:errors cssClass="error" path="minHikes" />
	<br />
	<br />
	
		<form:label path="maxHikes">
		<spring:message code="route.maxHikes" />:
	</form:label>
	<form:input type="number" min ="0" path="maxHikes" />
	<form:errors cssClass="error" path="maxHikes" />
	<br />
	<br />

	<input type="submit" name="search"
			value="<spring:message code="route.search" />" />&nbsp; 
	

</form:form>