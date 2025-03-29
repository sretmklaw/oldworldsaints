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
			results<br>
			for calendar date of <b><c:out value="${searchTerm}" /></b>
		</p>

		<!-- Browse Links -->
		<div class="row h-100 justify-content-center align-items-center">
			<table id="dayBrowseTable" 
				class="browse-table 
					${dayOfMonthList != null && dayOfMonthList.size() > 0 ? '' : 'hidden'}">
				<c:forEach var="day" 
					varStatus="i" 
					items="${dayOfMonthList}" >
					<c:set var="dayOfMonth" value="${day.getDayOfMonthNumeric()}" />
					<c:if test="${dayOfMonth != null}">
						<c:choose>
							<c:when test="${dayOfMonth % 7 == 1}">
								<tr>
							</c:when>
						</c:choose>
						<c:set var="bkgColor" 
							value="${day.getDayId() == searchId ? '#d8a052' 
								: '#f1f1f1'}" />
						<td style="background-color:${bkgColor}">
							<a class="popup-link date-name" 
								href="/search/day?byId=${day.getDayId()}${showAllUrl}">
								${dayOfMonth}
							</a>
						</td>
					</c:if>
				</c:forEach>
			</table>
		</div>

		<!-- Conditionally-Displayed fields -->
		<c:set var="showAllUrl" value="${showAll ? '&showAll=true' : ''}" />
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