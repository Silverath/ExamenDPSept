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
	name="routes" requestURI="route/myRoutes.do" id="row">

	<!-- Attributes -->
	
	<spring:message code="route.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="false" />

	<spring:message code="route.length" var="lengthHeader" />
	<display:column property="length" title="${lengthHeader}" sortable="false" />

	<spring:message code="route.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" sortable="false" />
	
	<spring:message code="route.hikes" var="hikesTitle"/>
	<spring:message code="route.add" var="addTitle"/>
	<display:column title="${hikesTitle}">	
	<a href="route/add.do?routeId=${row.id}">${addTitle}</a>
	</display:column>
		
	<spring:message code="route.hikes" var="hikesTitle"/>
	<spring:message code="route.remove" var="removeTitle"/>
	<display:column title="${hikesTitle}">	
	<a href="route/remove.do?routeId=${row.id}">${removeTitle}</a>
	</display:column>
	
	<security:authorize access="hasRole('USER')">
	<spring:message code="route.edit" var="editTitle"/>
	<display:column title ="${editTitle}">
	<a href="route/edit.do?routeId=${row.id}"><spring:message code="route.edit" />
	</a>
	</display:column>
	 
	 
	<display:column>	
		<a href="route/delete.do?routeId=${row.id}"><spring:message code="route.delete" />	
		</a>
	</display:column>
	
	</security:authorize>
	
	</display:table>
	
	
	
<!-- Action links -->



	<security:authorize access="hasRole('USER')">
			<a href="route/create.do">
				<spring:message	code="route.create" />
			</a>
				
	</security:authorize>