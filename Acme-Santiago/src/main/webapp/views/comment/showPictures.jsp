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
<jstl:choose>
<jstl:when test="${isEmpty == true }">
<spring:message code="comment.noPictures" />
</jstl:when>
<jstl:otherwise>
 <jstl:forEach items="${comment.pictures}" var="picture">
   <img src="${picture}" style="width:400px;height:280px;"/>
   <br>
   <br>
    </jstl:forEach>
</jstl:otherwise>
</jstl:choose>
</p>