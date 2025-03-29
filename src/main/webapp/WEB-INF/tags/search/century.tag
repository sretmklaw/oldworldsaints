<%@ tag language="java" 
	pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" 
	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="search" 
	tagdir="/WEB-INF/tags/search" %>

<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" 
		type="text/css"
		href="${context}/search-default-style.css" />
</head>
<body>

	<c:set var="showAllUrl" value="${showAll ? '&showAll=true' : ''}" />

	<!-- Page Title -->
	<h1 id="title">
		<i class="initial">S</i>earch
	</h1>

	<!-- Page Description -->
	<c:if test="${searchTerm != 'UNKNOWN'}">

		<p>
			<c:out value="Found ${resultSize} "/>
			<b><c:out value=" ${userRite.getCalendarRiteNameFormatted(showAll)} "/></b>
			<img id="userRiteIcon"
				class="img-icon-vertical" 
				src="${context}/${userRite.getCalendarRiteCodeFormatted(showAll)}.svg">
			results <br>
			for Century <b><c:out value="${searchTerm}" /></b>
		</p>

		<div id="centuryTimeline">
			<c:set var="prevNextCenturyIds" value="${century.getPrevNextCenturyIds(searchId)}" />
			<div class="row" style="flex-wrap:nowrap;">
				<!-- Previous Century Button -->
				<div class="col-2 browse-col">
					<a class="popup-link date-name" href="/search/century?byId=${prevNextCenturyIds.left}${showAllUrl}">
						<button class="btn-range" data-dir="prevCentury">&#10134;</button>
					</a>
				</div>
				<!-- Date Slider -->
				<div id="centuryTimelineSpan" class="col-8">
					<span id="centuryTimelineMarker" style="left:${century.getCenturyTimelineMarkerPosition(searchId)}%;"></span>
				</div>
				<!-- Next Century Button -->
				<div class="col-2 browse-col">
					<a class="popup-link date-name" href="/search/century?byId=${prevNextCenturyIds.right}${showAllUrl}">
						<button class="btn-range" data-dir="nextCentury">&#10133;</button>
					</a>
				</div>
			</div>
		</div>

		<!-- Conditionally-Displayed fields -->
		<c:choose>
			<c:when test="${pageErrorMessage != null}">
				<p class="alert">
					<c:out value="${pageErrorMessage}" />
				</p>
				<p>
					<a href="${context}/" class="btn-loading">Return to Current Day</a>
				</p>
			</c:when>
			<c:otherwise>
				<c:if test="${searchTerm != 'commemorationId' && userRite.getRiteId() != 1}">
					<!-- Show All Check-Box -->
					<p>
						<input id="showAllCheckbox" 
							type="checkbox" ${showAll ? 'checked' : ''}
							onclick="window.location.href='/${searchPath}${searchId}${showAll ? '' : '&showAll=true'}'">
						Show results for all calendars and rites
					</p>
				</c:if>
				<search:results showAll="${showAll}"/>
			</c:otherwise>
		</c:choose>
	</c:if>

</body>
</html>