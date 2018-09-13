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
<%@taglib prefix="fmt"	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles"	uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<p><spring:message code="administrator.dashboard" /></p>

<p>
	<spring:message code="avg.route" var="avgRoute"/>
	<jstl:out value="${avgRoute}"></jstl:out>
	:
	<jstl:out value="${avgR}"></jstl:out>
</p>

<p>
	<spring:message code="stddev.route" var="stddevRoute"/>
	<jstl:out value="${stddevRoute}"></jstl:out>
	:
	<jstl:out value="${stddevR}"></jstl:out>
</p>

<p>
	<spring:message code="avg.hike" var="avgHike"/>
	<jstl:out value="${avgHike}"></jstl:out>
	:
	<jstl:out value="${avgH}"></jstl:out>
</p>


<p>
	<spring:message code="stddev.hike" var="stddevHike"/>
	<jstl:out value="${stddevHike}"></jstl:out>
	:
	<jstl:out value="${stddevH}"></jstl:out>
</p>

<p>
	<spring:message code="avg.length.route" var="avgLengthRoute"/>
	<jstl:out value="${avgLengthRoute}"></jstl:out>
	:
	<jstl:out value="${avgLengthR}"></jstl:out>
</p>

<p>
	<spring:message code="stddev.length.route" var="stddevLengthRoute"/>
	<jstl:out value="${stddevLengthRoute}"></jstl:out>
	:
	<jstl:out value="${stddevLengthR}"></jstl:out>
</p>

<p>
	<spring:message code="avg.length.hike" var="avgLengthHike"/>
	<jstl:out value="${avgLengthHike}"></jstl:out>
	:
	<jstl:out value="${avgLengthH}"></jstl:out>
</p>

<p>
	<spring:message code="stddev.length.hike" var="stddevLengthHike"/>
	<jstl:out value="${stddevLengthHike}"></jstl:out>
	:
	<jstl:out value="${stddevLengthH}"></jstl:out>
</p>

<p>
<spring:message code="outlier.routes" />

<table>
    <jstl:forEach items="${outlier}" var="outlierR">
    <tr>
           <td> <jstl:out value="${outlierR.name}"></jstl:out></td>
            </tr>
    </jstl:forEach>
	</table>
	</p>
	
	<p>
	<spring:message code="avg.chirps.user" var="avgChirpsUser"/>
	<jstl:out value="${avgChirpsUser}"></jstl:out>
	:
	<jstl:out value="${avgChirpsPerUser}"></jstl:out>
</p>

<p>
<spring:message code="user.more.chirps" />

<table>
    <jstl:forEach items="${userMoreChirps}" var="userMoreChirp">
    <tr>
           <td> <jstl:out value="${userMoreChirp.name}"></jstl:out></td>
            </tr>
    </jstl:forEach>
	</table>
	</p>
	
	<p>
<spring:message code="user.less.chirps" />

<table>
    <jstl:forEach items="${userLessChirps}" var="userLessChirp">
    <tr>
           <td> <jstl:out value="${userLessChirp.name}"></jstl:out></td>
            </tr>
    </jstl:forEach>
	</table>
	</p>
	
		<p>
	<spring:message code="avg.comments.route" var="avgCommentsRoute"/>
	<jstl:out value="${avgCommentsRoute}"></jstl:out>
	:
	<jstl:out value="${avgCommentsPerRoute}"></jstl:out>
</p>

	<p>
	<spring:message code="ratio.adv.taboo" var="ratioAdvTaboo"/>
	<jstl:out value="${ratioAdvTaboo}"></jstl:out>
	:
	<jstl:out value="${ratioAdvWithTaboo}"></jstl:out>
</p>

	<p>
	<spring:message code="ratio.route.adv" var="ratioRouteAdv"/>
	<jstl:out value="${ratioRouteAdv}"></jstl:out>
	:
	<jstl:out value="${ratioRoutesWithAdvVSNot}"></jstl:out>
</p>

	<p>
	<spring:message code="ratio.compostela.wait" var="ratioCompostelaWait"/>
	<jstl:out value="${ratioCompostelaWait}"></jstl:out>
	:
	<jstl:out value="${ratioCompostelaWaitingVSTotal}"></jstl:out>
</p>

	<p>
	<spring:message code="ratio.compostela.appr" var="ratioCompostelaAppr"/>
	<jstl:out value="${ratioCompostelaAppr}"></jstl:out>
	:
	<jstl:out value="${ratioCompostelaApprovedVSRejected}"></jstl:out>
</p>
	
