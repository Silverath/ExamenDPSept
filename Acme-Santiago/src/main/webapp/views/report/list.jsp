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


<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="reports" requestURI="report/list.do" id="row">
	
	<jstl:if test="${row.gauge == 1}">
		<jstl:set var="style" value="background-color:LightSteelBlue" />
	</jstl:if>
	
	<jstl:if test="${row.gauge == 2}">
		<jstl:set var="style" value="background-color:DarkBlue" />
	</jstl:if>
	
	<jstl:if test="${row.gauge == 3}">
		<jstl:set var="style" value="background-color:Gray" />
	</jstl:if>

<security:authentication property="principal" var ="loggedactor"/>
	<!-- Attributes -->
	
	<spring:message code="report.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="false" style="${style}"/>

	<spring:message code="report.ticker" var="tickerHeader" />
	<display:column property="ticker" title="${tickerHeader}" sortable="true" style="${style}"/>

	<spring:message code="report.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" sortable="false" style="${style}"/>
	
	<spring:message code="report.moment" var="momentHeader" />
		<jstl:choose>
			<jstl:when test="${pageContext.response.locale.language=='en'}">
				<display:column property="moment" title="${momentHeader}" sortable="true" format="{0,date,yy/MM/dd HH:mm}" style="${style}"/>
			</jstl:when>
			
			<jstl:otherwise>
				<display:column property="moment" title="${momentHeader}" sortable="true" format="{0,date,dd-MM-yy HH:mm}" style="${style}"/>
			</jstl:otherwise>
		</jstl:choose>
		
	<spring:message code="report.route" var="routeHeader" />
	<display:column title="${routeHeader}" style="${style}">
	${row.route.name}
	 </display:column>
	 
	 <spring:message code="report.user" var="userHeader" />
	<display:column title="${userHeader}" style="${style}">
	${row.user.name}
	 </display:column>
	 
	 <spring:message code="report.gauge" var="gaugeHeader" />
	<display:column property="gauge" title="${gaugeHeader}" sortable="true" style="${style}"/>
	
	<spring:message code="report.finalMode" var="finalModeHeader" />
	<display:column property="finalMode" title="${finalModeHeader}" sortable="false" style="${style}"/>
	
	<spring:message code="report.comment" var="commentHeader" />
	<display:column property="comment" title="${commentHeader}" sortable="false" style="${style}"/>
	 
	 <security:authorize access="hasRole('ADMIN')">
	 
	 <spring:message code="report.approved" var="approvedHeader" />
	<display:column property="approved" title="${approvedHeader}" sortable="false" style="${style}"/>
	
	<spring:message code="report.rejected" var="rejectedHeader" />
	<display:column property="rejected" title="${rejectedHeader}" sortable="false" style="${style}"/>
	
	 <display:column style="${style}">	
		<jstl:if test="${row.finalMode == true and row.approved == false and row.rejected == false and not empty row.route and empty row.administrator}">
		<a href="report/approve.do?reportId=${row.id}"><spring:message code="report.approve" />	
		</a>
		</jstl:if>
	</display:column>
	
	<display:column style="${style}">	
		<jstl:if test="${row.finalMode == true and row.approved == false and row.rejected == false and not empty row.route and empty row.administrator}">
		<a href="report/reject.do?reportId=${row.id}"><spring:message code="report.reject" />	
		</a>
		</jstl:if>
	</display:column>
	
	
	
	</security:authorize>
	
	<security:authorize access="hasRole('USER')">
		
		<display:column style="${style}">	
		<jstl:if test="${!row.finalMode and !row.approved  and !row.rejected and empty row.route  }">
		<a href="report/edit.do?reportId=${row.id}"><spring:message code="report.edit" />	
		</a>
		</jstl:if>
		</display:column>
	
	
		<display:column style="${style}">
		<jstl:if test="${row.user.userAccount.username==loggedactor.username and !row.approved and !row.rejected and empty row.route}">
		<a href="report/delete.do?reportId=${row.id}"><spring:message code="report.delete" />	
		</a>
		</jstl:if>
		</display:column>
		
		<display:column style="${style}">
		<jstl:if test="${row.finalMode	 and !row.approved and !row.rejected and empty row.route}">
		<a href="report/selectRoute.do?reportId=${row.id}"><spring:message code="report.selectRoute" />	
		</a>
		</jstl:if>
		</display:column>
		
		
	
	
	</security:authorize>
	
	
</display:table>
<security:authorize access="hasRole('USER')">
<a href="report/create.do"><spring:message code="report.create" />	
		</a>
</security:authorize>
<!-- Action links -->
