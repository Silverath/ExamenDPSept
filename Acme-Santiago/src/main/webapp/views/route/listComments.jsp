<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<display:table pagesize="10" class="displaytag" 
	name="comments" requestURI="route/listComments.do" id="row">
	
	<spring:message code="comment.author" var="userHeader"/>
	<display:column title="${userHeader}" sortable="true">
	${row.user.name}
	</display:column>
	
	<spring:message code="comment.description" var="description"/>
	<display:column property="description" title="${description}" sortable="false"/>
	
	<spring:message code="comment.moment" var="moment"/>
	<display:column property="moment" title="${moment}" sortable="true"/>
	
	<spring:message code="comment.rating" var="rating"/>
	<display:column property="rating" title="${rating}" sortable="false"/>
	
	<spring:message code="comment.showPictures" var="showHeader"/>
	<display:column title="${showHeader}" sortable="false">	
		<a href="comment/showPictures.do?commentId=${row.id}"><spring:message code="comment.showPictures" />	
		</a>
	</display:column>
	
</display:table> 

<br>
<hr>

<a href="comment/create.do?routeId=${route.id}"><spring:message code="route.comment"/></a>


<%-- <jstl:forEach items="${comments}" var="list">
	<div style="float:left; position:relative"> 
		<h4>${list.user.name}</h4>
		<p>${list.text}</p>
		<p>${list.moment}</p>
	</div>
	<div style="float:left; position:relative"> 
		<h4></h4>
		<p><a href="comment/edit.do?commentId=${list.id}"><spring:message code="comment.delete" /></a></p>
		<p><a href="comment/create.do?routeId=${list.id}"><spring:message code="route.comment"/></a></p>
	</div>
	<br>
	<hr>
</jstl:forEach> --%>

