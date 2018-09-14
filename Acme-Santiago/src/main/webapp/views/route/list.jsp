<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="route.list" /></p>

<display:table pagesize="5" class="displaytag"
	name="routes" requestURI="route/list.do" id="row">

	<!-- Attributes -->
	
	<spring:message code="route.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="false" />

	<spring:message code="route.length" var="lengthHeader" />
	<display:column property="length" title="${lengthHeader}" sortable="false" />

	<spring:message code="route.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" sortable="false" />
	
	<spring:message code="route.user" var="userTitle"/>
	<display:column title ="${userTitle}">
	${row.user.name}
	</display:column>
	
	<security:authorize access="hasRole('USER')">
	<spring:message code="route.commentsAssociated" var="commentsHeader" />	
		<display:column title="${commentsHeader}">	
			<a href="route/listComments.do?routeId=${row.id}"><spring:message code="route.comments"/>
			</a>
		</display:column>
		
		
	</security:authorize>
	<spring:message code="route.display" var="displayTitle"/>
	<display:column title ="${displayTitle}">	
		<a href="route/display.do?routeId=${row.id}"><spring:message code="route.display" />	
		</a>
	</display:column>
	 
	 <security:authorize access="hasRole('ADMIN')">
	<display:column>	
		<a href="route/deleteByAdmin.do?routeId=${row.id}"><spring:message code="route.delete" />	
		</a>
	</display:column>
	</security:authorize>
	<spring:message code="route.reports" var="reportsHeader" />	
		<display:column title="${reportsHeader}">	
			<a href="report/listByRoute.do?routeId=${row.id}"><spring:message code="route.reports"/>
			</a>
		</display:column>	
	
	</display:table>
	
	
	
<!-- Action links -->


