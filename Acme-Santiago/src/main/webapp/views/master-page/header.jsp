<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<img src="images/logo.png" alt="Acme-Santiago Co., Inc." height="200px" width="832px"/>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/dashboard.do"><spring:message code="master.page.administrator.dashboard" /></a></li>					
				</ul>
			</li>
			
			<li><a class="fNiv"><spring:message	code="master.page.taboo" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/listTabooWord.do"><spring:message code="master.page.administrator.tabooWords" /></a></li>	
					<li><a href="administrator/listTabooChirps.do"><spring:message code="master.page.administrator.tabooChirps" /></a></li>	
					<li><a href="administrator/listTabooComments.do"><spring:message code="master.page.administrator.tabooComments" /></a></li>	
					<li><a href="advertisement/listTaboo.do"><spring:message code="master.page.advertisement.taboo" /></a></li>			
				</ul>
			</li>
			
			<li><a class="fNiv"><spring:message	code="master.page.route" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="route/list.do"><spring:message code="master.page.route.list" /></a></li>					
				</ul>
			</li>
			
			<li><a class="fNiv"><spring:message code="master.page.report" /></a>
				<ul>
					<li class="arrow"></li>

					<li><a href="report/listNotApprovedOrRejected.do"><spring:message code="master.page.report.not" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('USER')">
			<li><a class="fNiv"><spring:message	code="master.page.route" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="route/list.do"><spring:message code="master.page.route.list" /></a></li>
					<li><a href="route/myRoutes.do"><spring:message code="master.page.route.myRoutes" /></a></li>
					<li><a href="hike/list.do"><spring:message code="master.page.hikes" /></a></li>
				</ul>
			</li>
				<li><a class="fNiv"><spring:message code="master.page.follow" /></a>
				<ul>
					<li class="arrow"></li>

					<li><a href="follow/listUserFollows.do"><spring:message code="master.page.followsList" /></a></li>
					<li><a href="follow/listUserFollowers.do"><spring:message code="master.page.followersList" /></a></li>
				</ul>
			</li>
			
			<li><a class="fNiv"><spring:message code="master.page.report" /></a>
				<ul>
					<li class="arrow"></li>

					<li><a href="report/listMine.do"><spring:message code="master.page.report.mine" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.chirp" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="chirp/list.do"><spring:message code="master.page.chirp.list" /></a></li>
					<li><a href="chirp/listChirpsPostedByMyFollows.do"><spring:message code="master.page.chirp.listChirpsPostedByMyFollows" /></a></li>										
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('AGENT')">
			<li><a class="fNiv"><spring:message	code="master.page.advertisement" /></a>
			
				<ul>
					<li class="arrow"></li>
					<li><a href="advertisement/create.do"><spring:message code="master.page.advertisement.create" /></a></li>
					<li><a href="hike/listAdvertisements.do"><spring:message code="master.page.hike.listAdvertisements" /></a></li>
					<li><a href="hike/listWithoutAdvertisements.do"><spring:message code="master.page.hike.listWithoutAdvertisements" /></a></li>					
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv" href="actor/register.do"><spring:message code="master.page.actor.register" /></a></li>
		</security:authorize>
		
		<security:authorize access="permitAll">
			<li><a class="fNiv"><spring:message	code="master.page.route.search" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="route/searchKeyword.do"><spring:message code="master.page.route.search.keyword" /></a></li>
					<li><a href="route/searchLength.do"><spring:message code="master.page.route.search.length" /></a></li>
					<li><a href="route/searchHikes.do"><spring:message code="master.page.route.search.hikes" /></a></li>
				</ul>
			</li>
			<%-- <li><a class="fNiv" href="route/list.do"><spring:message code="master.page.route.list" /></a></li> --%>
			<li><a class="fNiv" href="user/list.do"><spring:message code="master.page.user.list" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAnonymous() or hasRole('ADMIN')">
		<li><a class="fNiv" href="route/list.do"><spring:message code="master.page.route.list" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
						
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

