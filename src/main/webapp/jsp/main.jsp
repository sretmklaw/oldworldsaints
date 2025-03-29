<%@ include file="_import.jsp" %>
<%@ taglib prefix="main" 
	tagdir="/WEB-INF/tags/main" %>

<!DOCTYPE html>
<html lang="en" 
	class="background"
	style="background-image: url(${context}/default-background.png)">

<head>
	<%@ include file="_header.jsp" %>
	<link rel="stylesheet" 
		type="text/css"
		href="${context}/main-style.css" />
	<link rel="stylesheet" 
		type="text/css"
		href="${context}/search-style.css" />
	<link rel="stylesheet" 
		type="text/css"
		href="${context}/navbar-style.css" />
</head>

<body>

	<!-- Page Contents Mask -->
	<div id="pageMask" class="page-mask"></div>

	<main:maintenanceNotification />
	<main:imageGalleryWindow 
		locationImageMap="${locationImageMap}"/>

	<tags:timeout />
	<tags:navbar />
	<tags:wrapper>

		<!-- Page Title -->
		<h1 id="title">
			<i class="initial">T</i>oday
		</h1>
		<c:choose>
			<c:when test="${showForDate != null}">
				<p class="success"><c:out value="Currently Showing ${showForDate}"/></p>
			</c:when>
			<c:when test="${showForDateError != null}">
				<p class="alert"><c:out value="${showForDateError}"/></p>
			</c:when>
		</c:choose>
		<c:if test="${dateSearchButtonIsVisible}">
			<div class="d-flex justify-content-center">
				<form:form method="GET" 
					action="${context}/main?byDate="
					class="form-horizontal dropdown-select"
					modelAttribute="searchCriteria" >
					<spring:bind path="byDate">
						<div class="form-group ${status.error ? 'alert' : ''}">
							<form:errors path="byDate"></form:errors>
							<form:input id="byDateInput"
								type="text" 
								path="byDate"
								class="dropdown-option form-control" />
						</div>
					</spring:bind>
					<button disabled id="byDateSearchBtn" 
						class="search btn btn-secondary btn-loading">
						&#128270;
					</button>
				</form:form>
			</div>
		</c:if>

		<main:locationPatronageIcons 
			locationPatronageList="${locationPatronageList}" 
			locationImageMap="${locationImageMap}" />

		<main:celestialDate 
			userCalendar="${userCalendar}" 
			lunarPhase="${lunarPhase}" 
			zodiacSign="${zodiacSign}" 
			culminatingStar="${culminatingStar}" />

		<main:calendarDate
			userCalendar="${userCalendar}" 
			userRiteId="${userRite.getRiteId()}" 
			resultMap="${resultMap}"
			liturgyTypeList="${liturgyTypeList}"
			cycleDayName="${cycleDayName}"
			liturgyTypeName="${liturgyTypeName}"
			showAll="${showAll}"
			requestButtonIsVisible="${requestButtonIsVisible}" />

		<main:hourTabs
			hourTabMap="${hourTabMap}" 
			userCalendar="${userCalendar}" 
			showAudio="${showAudio}" />

	</tags:wrapper>
	<script src="${context}/notify-onload.js"></script>
	<script src="${context}/imagegallery-onclick.js"></script>
	<script src="${context}/collapseall-onclick.js"></script>
	<script src="${context}/dropdown-onclick-navbar.js"></script>
	<script src="${context}/searchdate-onclick.js"></script>
	<c:if test="${showAudio}" >
		<script src="${context}/playall-onclick.js"></script>
	</c:if>
	<script src="${context}/reset-dependent-inputs.js"></script>
	<script src="${context}/reset-dependent-inputs-navbar.js"></script>
	<script src="${context}/navbar-onclick.js"></script>
	<script src="${context}/calendar-onchange-navbar.js"></script>
	<script src="${context}/century-onchange-navbar.js"></script>
	<script src="${context}/constellation-onchange-navbar.js"></script>
	<script src="${context}/day-onchange-navbar.js"></script>
	<script src="${context}/month-onchange-navbar.js"></script>
	<script src="${context}/name-onchange-navbar.js"></script>
	<script src="${context}/nation-onchange-navbar.js"></script>
	<script src="${context}/patronage-onchange-navbar.js"></script>
	<script src="${context}/patronagesubtype-onchange-navbar.js"></script>
	<script src="${context}/patronagetype-onchange-navbar.js"></script>
	<script src="${context}/star-onchange-navbar.js"></script>
	<script src="${context}/static/jquery-ui/jquery-ui.js"></script>
	<script src="${context}/tag-onchange-navbar.js"></script>
	<script src="${context}/timeout-onload.js"></script>
	<script src="${context}/tooltip-onmouseover.js"></script>

</body>
</html>
