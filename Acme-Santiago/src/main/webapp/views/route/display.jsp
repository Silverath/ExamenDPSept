<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<p>
<h3><spring:message code="route.pictures" /></h3>
 <jstl:forEach items="${route.pictures}" var="picture">
   <img src="${picture}" style="width:400px;height:280px;"/>
   <br>
   <br>
    </jstl:forEach>
    </p>

    
						
<p>
	<spring:message code="route.name" var="nameHeader"/>
	<jstl:out value="${nameHeader}"></jstl:out>
	:
	<jstl:out value="${route.name}"></jstl:out>
</p>

<p>
	<spring:message code="route.length" var="lengthHeader"/>
	<jstl:out value="${lengthHeader}"></jstl:out>
	:
	<jstl:out value="${route.length}"></jstl:out>
</p>

<p>
	<spring:message code="route.description" var="descriptionHeader"/>
	<jstl:out value="${descriptionHeader}"></jstl:out>
	:
	<jstl:out value="${route.description}"></jstl:out>
</p>

<p>
	<spring:message code="route.user" var="userHeader"/>
	<jstl:out value="${userHeader}"></jstl:out>
	:
	<jstl:out value="${route.user.name}"></jstl:out>
	<jstl:out value=" ${route.user.surname}"></jstl:out>
</p>
<p>
<h3><spring:message code="route.comments" /></h3>
<display:table name="${route.comments}" id="comment">

<spring:message code="comment.moment" var="momentTitle"/>
	<display:column title="${momentTitle}" sortable="true">
		${comment.moment}
	</display:column>
	
	<spring:message code="comment.description" var="descriptionTitle"/>
	<display:column title="${descriptionTitle}" sortable="true">
		${comment.description}
	</display:column>
	
	<spring:message code="comment.user" var="userTitle"/>
	<display:column title="${userTitle}" sortable="true">
		${comment.user.name}
	</display:column>
	
	
	</display:table>
	</p>
	<p>
	<h3><spring:message code="route.hikes" /></h3>
<display:table name="${route.hikes}" id="hike">

<spring:message code="hike.name" var="nameTitle"/>
	<display:column title="${nameTitle}" sortable="true">
		${hike.name}
	</display:column>
	
	<spring:message code="hike.length" var="lengthTitle"/>
	<display:column title="${lengthTitle}" sortable="true">
		${hike.length}
	</display:column>
	
	<spring:message code="hike.originCity" var="originCityTitle"/>
	<display:column title="${originCityTitle}" sortable="true">
		${hike.originCity}
	</display:column>
	
	<spring:message code="hike.destinyCity" var="destinyCityTitle"/>
	<display:column title="${destinyCityTitle}" sortable="true">
		${hike.destinyCity}
	</display:column>
	
	<spring:message code="hike.difficulty" var="difficultyTitle"/>
	<display:column title="${difficultyTitle}" sortable="true">
		${hike.difficulty}
	</display:column>
	
	
	</display:table>
	</p>

