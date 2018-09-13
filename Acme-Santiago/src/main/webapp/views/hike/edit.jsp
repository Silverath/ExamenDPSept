<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<form:form action="hike/edit.do" modelAttribute="hike">
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<%-- <form:hidden path="pictures"/> --%>
	<form:hidden path="comments"/>
<%-- 	<form:hidden path="route"/> --%>

	<acme:textbox code="hike.name" path="name"/>
	<acme:textbox code="hike.description" path="description"/>
	<acme:textbox code="hike.length" path="length"/>
	<acme:textbox code="hike.originCity" path="originCity"/>
	<acme:textbox code="hike.destinyCity" path="destinyCity"/>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i>(url1,url2,...)</i>
	<acme:textarea code="hike.pictures" path="pictures" />
	
	<form:label path="difficulty">
		<spring:message code="hike.difficulty" /> 
	</form:label>
	

<jstl:choose>
		<jstl:when test="${pageContext.response.locale.language=='en'}">
	<form:select path="difficulty">
		<form:option value="EASY" label="EASY" />
		<form:option value="MEDIUM" label="MEDIUM" />		
		<form:option value="DIFFICULT" label="DIFFICULT" />		
	</form:select>	
	</jstl:when>
	<jstl:otherwise>
	<form:select path="difficulty">
		<form:option value="EASY" label="FÁCIL" />
		<form:option value="MEDIUM" label="MEDIO" />		
		<form:option value="DIFFICULT" label="DIFÍCIL" />			
	</form:select>
	</jstl:otherwise>
	</jstl:choose>
	<br />
	
	
<security:authorize access="hasRole('USER')">	
	<input type="submit" name="save"
		value="<spring:message code="hike.save" />" />&nbsp; 
	</security:authorize>
	
	<acme:cancel code="hike.cancel" url="hike/list.do" />

</form:form>
