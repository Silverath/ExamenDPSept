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

<p><spring:message code="hike.list" /></p>

<display:table pagesize="5" class="displaytag"
	name="hikes" requestURI="hike/list.do" id="row">

	<!-- Attributes -->
	
	<spring:message code="hike.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="false" />

	<spring:message code="hike.length" var="lengthHeader" />
	<display:column property="length" title="${lengthHeader}" sortable="false" />

	<spring:message code="hike.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" sortable="false" />
	
	<spring:message code="hike.originCity" var="originCityHeader" />
	<display:column property="originCity" title="${originCityHeader}" sortable="false" />
	
	<spring:message code="hike.destinyCity" var="destinyCityHeader" />
	<display:column property="destinyCity" title="${destinyCityHeader}" sortable="false" />
	
	<spring:message code="hike.difficulty" var="difficultyHeader" />
	<display:column property="difficulty" title="${difficultyHeader}" sortable="false" />
	
	<security:authorize access="hasRole('USER')">
	<spring:message code="comment.showPictures" var="showHeader"/>
	<display:column title="${showHeader}" sortable="false">	
		<a href="hike/showPictures.do?hikeId=${row.id}"><spring:message code="comment.showPictures" />	
		</a>
	</display:column>
	</security:authorize>
	
	

	
	</display:table>
	
	
	
<!-- Action links -->



	<security:authorize access="hasRole('USER')">
			<a href="hike/create.do">
				<spring:message	code="hike.create" />
			</a>
				
	</security:authorize>