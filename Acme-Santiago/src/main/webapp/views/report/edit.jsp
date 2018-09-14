<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt"	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles"	uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="report/edit.do" modelAttribute="report">
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="moment"/>
	<form:hidden path="ticker"/>
	<form:hidden path="user"/>
	<form:hidden path="route"/>
	<form:hidden path="approved"/>
	<form:hidden path="rejected"/>
	<form:hidden path="comment"/>
	<form:hidden path="administrator"/>

	<form:label path="title">
		<spring:message code="report.title"/>
	</form:label>
	<form:input path="title"/>
	<form:errors cssClass="error" path="title" />
	<br />
	<br/>
	<form:label path="gauge">
		<spring:message code="report.gauge"/>
	</form:label>
	<form:input path="gauge"/>
	<form:errors cssClass="error" path="gauge" />
	
	<br />
	<br/>
	<form:label path="description">
		<spring:message code="report.description"/>
	</form:label>
	<form:input path="description"/>
	<form:errors cssClass="error" path="description" />
	
	<br />
 <br>
	
	<spring:message code="report.finalMode" />:
	<input type="checkbox" name="finalMode"
		<jstl:if test="${report.finalMode==true}">
			checked
		</jstl:if> 
		value="true" />
	<br>
	
	<input type="submit" name="save"
		value="<spring:message code="report.save" />" />&nbsp; 	

	<input type="button" name="cancel"
		value="<spring:message code="report.cancel" />"
		onclick="javascript: relativeRedir('welcome/index.do');" />
	<br />
</form:form>
