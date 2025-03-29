<%@ tag language="java" 
	pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 
	uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" 
	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="navbar" 
	tagdir="/WEB-INF/tags/navbar" %>

<!DOCTYPE html>
<html>
<head></head>
<body>

	<!-- Navigation Bar Toggle Button -->
	<button id="navbarToggle" class="navbar-toggler">
		<i class="menu-initial">M</i>enu
	</button>

	<!-- Page Contents Mask -->
	<div id="pageMask" class="page-mask"></div>

	<!-- Navigation Bar Content -->
	<div id="navbarContents" class="navbar-side-panel">

		<!-- User Information -->
		<div id="userInformation" class="hidden">
			<div id="username" 
				class="dropdown-details">
				<b><c:out value="Hello, ${username}" /></b>
			</div>
			<div id="userNationRegion" 
				class="dropdown-details">
				<img id="userNationIcon" 
					class="flag img-icon-horizontal" 
					src="${context}/${userNation.getNationCode()}.svg">
				<c:out value="${userNation.getNationName()}"></c:out>
				<br />
				<i class="detail"><c:out 
					value="GMT ${userRegion.getUtcOffset()}"></c:out></i>
				<br />
				<c:out value="${userRegion.getRegionName()}"></c:out>
			</div>
			<div id="userCalendarRite" 
				class="dropdown-details">
				<img id="userRiteIcon" 
					class="img-icon-vertical" 
					src="${context}/${userRite.getRiteCode()}.svg">
				<c:out value="${userRite.getRiteName()}"/>
				<br />
				<i class="detail"><c:out 
					value="${userCalendar.getCalendarDetail()} 
						${userRite.getRiteDetail()}"></c:out></i>
			</div>
			<hr />
		</div>

		<!-- Navigation Options -->
		<div class="nav-option-container hidden">

			<!-- Commemorations Menu -->
			<div id="commDropdown" 
				class="nav-option dropdown-submenu dropdown-container">

				<!-- Search Drop-Down -->
				<a id="commDropdownBtn" 
					type="button"
					class="navbar-dropdown dropdown-option submenu btn" 
					data-toggle="dropdown">Search
					<img id="commDropdownIcon" 
						src="${context}/BULLET.svg" 
						class="bullet" /></a>
				<div id="commDropdownDiv" 
					class="dropdown-menu dropdown-parent">

					<!-- Search by Name Text Input -->
					<div id="commByNameGroup" 
						class="input-group">
						<form:form method="GET" 
							action="${context}/search/name" 
							class="form-horizontal dropdown-select"
							modelAttribute="searchCriteria" >
							<spring:bind path="byText">
								<div class="form-group ${status.error ? 'alert' : ''}">
									<form:input id="commByNameInput"
										type="text" 
										path="byText"
										class="dropdown-option form-control"
										placeholder="Search by Name" />
								</div>
							</spring:bind>
							<button disabled id="commByNameSearchBtn" 
								class="search btn btn-secondary btn-loading">
								&#128270;
							</button>
						</form:form>
						<div id="commByNameErrors"
							class="errors hidden"></div>
						<hr />
					</div>

					<!-- Browse by Details Menu -->
					<div id="commDropdownByDetails" 
						class="dropdown-submenu">
						<a id="commDropdownByDetailsBtn" 
							type="button" 
							class="dropdown-option submenu btn" 
							data-toggle="dropdown">Browse by Type 
							<img id="commDropdownByDetailsIcon"
								src="${context}/BULLET.svg" 
								class="bullet-right" /></a>
						<div id="commDropdownByDetailsDiv"
							class="dropdown-menu dropdown-menu-right">
							<div id="commByDetailsGroup" 
								class="input-group">

								<!-- Select by Nation Drop-Down Input -->
								<navbar:nation 
									searchCriteria="${searchCriteria}" 
									nationList="${nationList}" />

								<!-- Select by Century Drop-Down Input -->
								<navbar:century 
									searchCriteria="${searchCriteria}" 
									centuryList="${centuryList}" />

								<!-- Select by Tag Drop-Down Input -->
								<navbar:tag 
									searchCriteria="${searchCriteria}" 
									tagList="${tagList}" />

								<hr />
							</div>

							<!-- Browse by Date Drop-Down -->
							<div id="commDropdownByDate" 
								class="dropdown-submenu">
								<a id="commDropdownByDateBtn" 
									type="button" 
									class="dropdown-option submenu btn" 
									data-toggle="dropdown">Browse by Date 
									<img id="commDropdownByDateIcon" 
										src="${context}/BULLET.svg" 
										class="bullet-right" /></a>
								<div id="commDropdownByDateDiv" 
									class="dropdown-menu dropdown-menu-right">
									<div id="commByDateGroup" 
										class="input-group">

										<!-- Select by Calendar Drop-Down Input -->
										<navbar:calendar 
											searchCriteria="${searchCriteria}"
											calendarList="${calendarList}" />

										<!-- Select by Month Drop-Down Input -->
										<navbar:month 
											searchCriteria="${searchCriteria}"
											monthList="${monthList}" />

										<!-- Select by Day Drop-Down Input -->
										<navbar:day 
											searchCriteria="${searchCriteria}" 
											dayList="${dayList}" />

									</div>
								</div>
							</div>

							<!-- Browse by Patronage Drop-Down -->
							<div id="commDropdownByPatron" 
								class="dropdown-submenu">
								<a id="commDropdownByPatronBtn" 
									type="button" 
									class="dropdown-option submenu btn" 
									data-toggle="dropdown">Browse by Patronage 
									<img id="commDropdownByPatronIcon" 
										src="${context}/BULLET.svg" 
										class="bullet-right" /></a>
								<div id="commDropdownByPatronDiv" 
									class="dropdown-menu dropdown-menu-right">
									<div id="commByPatronGroup" 
										class="input-group">

										<!-- Select Patronage Type Drop-Down Input -->
										<navbar:patronageType 
											searchCriteria="${searchCriteria}"
											patronageTypeList="${patronageTypeList}" />

										<!-- Select Patronage Subtype Drop-Down Input -->
										<navbar:patronageSubtype 
											searchCriteria="${searchCriteria}"
											patronageSubtypeList="${patronageSubtypeList}" />

										<!-- Select by Patronage Drop-Down Input -->
										<navbar:patronage 
											searchCriteria="${searchCriteria}" 
											patronageList="${patronageList}" />

									</div>
								</div>
							</div>

							<!-- Browse by Star Drop-Down -->
							<div id="commDropdownByStar" 
								class="dropdown-submenu">
								<a id="commDropdownByStarBtn" 
									type="button" 
									class="dropdown-option submenu btn" 
									data-toggle="dropdown">Browse by Star 
									<img id="commDropdownByStarIcon" 
										src="${context}/BULLET.svg" 
										class="bullet-right" /></a>
								<div id="commDropdownByStarDiv" 
									class="dropdown-menu dropdown-menu-right">
									<div id="commByStarGroup" 
										class="input-group">

										<!-- Select by Constellation Drop-Down Input -->
										<navbar:constellation 
											searchCriteria="${searchCriteria}"
											constellationList="${constellationList}" />

										<!-- Select by Star Drop-Down Input -->
										<navbar:star 
											searchCriteria="${searchCriteria}"
											starList="${starList}" />

									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

			<!-- User Options Drop-Down -->
			<div id="userDropdown" 
				class="nav-option dropdown-submenu dropdown-container">
				<a id="userDropdownBtn" 
					type="button" 
					class="dropdown-option submenu btn" 
					data-toggle="dropdown"><c:out 
						value="Options" />
					<img id="userDropdownIcon" 
						class="bullet" 
						src="${context}/BULLET.svg" />
				</a>
				<div id="userDropdownDiv" 
					class="dropdown-menu dropdown-parent">
					<c:if test="${adminButtonIsVisible}">
						<a id="adminBtn" 
							class="dropdown-option menu-elem btn" 
							href="${context}/admin">Administration
							<c:if test="${openRequestCount != null}">
								<span class="preview-count">
									<c:out value="${openRequestCount}"/>
								</span>
							</c:if>
						</a>
					</c:if>
					<a id="aboutBtn" 
						class="dropdown-option menu-elem btn" 
						href="${context}/about">About this Site</a>
					<c:if test="${updateButtonIsVisible}">
						<a id="updateBtn" 
							class="dropdown-option menu-elem btn" 
							href="${context}/register">Update Account</a>
					</c:if>
					<c:if test="${donateButtonIsVisible}">
						<a id="donateBtn" 
							class="dropdown-option menu-elem btn" 
							href="${context}/donate">
							Donate<span style="color:#ff0000"> &#9829;</span></a>
					</c:if>
					<a id="logoutBtn" 
						class="dropdown-option menu-elem btn" 
						href="${context}/logout"><b>Logout</b></a>
				</div>
			</div>
		</div>

		<!-- Logout Form -->
		<form id="logoutForm" 
			method="POST" 
			action="${context}/logout">
			<input type="hidden" 
				name="${_csrf.parameterName}" 
				value="${_csrf.token}"/>
		</form>

	</div>

</body>
</html>
