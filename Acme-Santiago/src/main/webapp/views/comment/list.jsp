<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt"	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles"	uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table pagesize="5" class="displaytag"
	name="comments" requestURI="comment/list.do" id="row">
	
	
	<spring:message code="comment.moment" var="moment" />
	<jstl:choose>
	<jstl:when test="${pageContext.response.locale.language=='en'}">
	<display:column property="moment" title="${moment}" 
		sortable="true" format="{0,date,yy/MM/dd HH:mm}">${row.moment}
		</display:column>	
	</jstl:when>
	<jstl:otherwise>
	<display:column property="moment" title="${moment}" 
		sortable="true" format="{0,date,dd-MM-yy HH:mm}">${row.moment}
		</display:column>	
	</jstl:otherwise>
	</jstl:choose>
	
	<spring:message code="comment.description" var="description"/>
	<display:column property="description" title="${description}" sortable="false"/>
	
	<spring:message code="comment.rating" var="rating"/>
	<display:column property="rating" title="${rating}" sortable="false"/>
	
	<spring:message code="comment.user" var="userHeader"/>
	<display:column title="${userHeader}" sortable="false">
	${row.user.name}
	</display:column>
	
	<spring:message code="comment.showPictures" var="showHeader"/>
	<display:column title="${showHeader}" sortable="false">	
		<a href="comment/showPictures.do?commentId=${row.id}"><spring:message code="comment.showPictures" />	
		</a>
	</display:column>
	
		
	 <security:authorize access="hasRole('ADMIN')">
	<display:column>	
		<a href="administrator/deleteCommentsByAdmin.do?commentId=${row.id}"><spring:message code="comment.delete" />	
		</a>
	</display:column>
	</security:authorize>
	
	
</display:table> 

	<security:authorize access="hasRole('USER')">	
	<a href="comment/create.do?partyId=${partyId}">
			<spring:message code="comment.create"/>
	</a>
	</security:authorize>
	