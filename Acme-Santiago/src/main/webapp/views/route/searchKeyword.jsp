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

<form:form action="route/searchKeyword.do"
	modelAttribute="searchForm">

	

	<form:label path="keyword">
		<spring:message code="route.keyword" />:
	</form:label>
	<form:input path="keyword" />
	<form:errors cssClass="error" path="keyword" />
	<br />
	<br />

	<input type="submit" name="search"
			value="<spring:message code="route.search" />" />&nbsp; 
	

</form:form>