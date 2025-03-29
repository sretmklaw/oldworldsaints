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
	<c:if test="${searchTerm != 'commemorationId' && searchTerm != 'UNKNOWN'}">
		<p>
			<c:out value="Found ${resultSize} "/>
			<b><c:out value=" ${userRite.getCalendarRiteNameFormatted(showAll)} "/></b>
			<img id="userRiteIcon"
				class="img-icon-vertical" 
				src="${context}/${userRite.getCalendarRiteCodeFormatted(showAll)}.svg">
			results<br>
			for <c:out value="${searchType} " />
			<c:if test="${searchIcon != null}">
				<img id="searchIcon"
					class="img-icon-horizontal ${(searchType).equals('nation of') ? 'flag' : ''}" 
					src="${context}/${searchIcon}.svg">
			</c:if>
			<c:if test="${altSearchIcon != null}">
				<img id="altSearchIcon"
					class="img-icon-horizontal ${(searchType).equals('nation of') ? 'flag' : ''}" 
					src="${context}/${altSearchIcon}.svg">
			</c:if>
			<b><c:out value="${searchTerm}" /></b>
		</p>
	</c:if>

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
			<c:if test="${patronageImageId != null}">
				<!-- Patronage Image -->
				<div id="patronageImageRow">
					<img id="patronageImage" 
						class="img-icon-patronage" 
						src="${context}/${patronageImageId}.png"
						title="${searchTerm}"/>
				</div>
			</c:if>
			<c:if test="${searchDetail != null}">
				<!-- Search Detail -->
				<div class="search-detail">
					<p>
						<c:out value="${searchDetail}" />
					</p>
				</div>
			</c:if>
			<c:if test="${relatedSearchLink != null}">
				<!-- Related Search Link -->
				<div id="relatedSearchLink">
					<a href="${context}${relatedSearchLink}${showAllUrl}" class="btn-loading">View Related Entry</a>
				</div>
			</c:if>
		
			<search:results showAll="${showAll}"/>

		</c:otherwise>
	</c:choose>

</body>
</html>