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
	<meta http-equiv="Content-Type" 
		content="text/html; charset=UTF-8">
	<meta http-equiv="Pragma" 
		content="no-cache">
	<link rel="stylesheet" 
		href="${context}/search-map-style.css" />
	<link rel="stylesheet"
		href="${context}/static/leaflet/leaflet.css" />
</head>
<body>

	<c:set var="showAllUrl" value="${showAll ? '&showAll=true' : ''}" />

	<!-- Page Title -->
	<h1 id="title">
		<i class="initial">S</i>earch
	</h1>

	<!-- Page Description -->
	<c:if test="${resultSize != null && resultSize > 0}">

		<p>
			<c:out value="Found ${resultSize} "/>
			<b><c:out value=" ${userRite.getCalendarRiteNameFormatted(showAll)} "/></b>
			<img id="userRiteIcon"
				class="img-icon-vertical" 
				src="${context}/${userRite.getCalendarRiteCodeFormatted(showAll)}.svg"
				title="${userRite.getRiteName()}">
			results<br>
			for <c:out value="${searchType} " /><b><c:out value="${searchTerm}" /></b>
		</p>

		<!-- Location-Specific Fields -->
		<div class="search-detail 
			${locationImageId == null && locationRouteList == null ? 'hidden' : ''}"
			${locationRouteList != null ? 'style="width:100%;"' : 'style="width:330px;"'}>
			<c:if test="${locationImageId != null}">
				<!-- Location Image -->
				<img id="locationImage"
					class="img-icon-location" 
					src="${context}/${locationImageId}.jpg"
					title="${searchTerm}"
					${locationRouteList != null ? 'style="margin-bottom:15px;"' : ''}>
			</c:if>
			<c:if test="${locationRouteList != null}">
				<!-- Pilgrimage Route List -->
				<p><i>Lying along the following routes:</i></p>
				<c:forEach var="route" 
					items="${locationRouteList}" >
					<c:set var="routeStartLocationId" 
						value="${route.getStartLocationId()}" />
					<c:set var="routeEndLocationId" 
						value="${route.getEndLocationId()}" />
					<p>&#8627;
						<b><c:out value="${route.getRouteName()}"/></b>
						<c:if test="${routeStartLocationId != null}">
							from <a href='${context}/search/location?byId=${routeStartLocationId}${showAllUrl}' class='btn-loading'>
								${route.getStartLocationLabelName()}</a>
						</c:if>
						<c:if test="${routeEndLocationId != null}">
							to <a href='${context}/search/location?byId=${routeEndLocationId}${showAllUrl}' class='btn-loading'>
								${route.getEndLocationLabelName()}</a>
						</c:if>
						<br/>
						<c:out value="${route.getRouteDetail()}"/>
					</p>
				</c:forEach>
			</c:if>
		</div>
		<c:if test="${mapInset != null && mapInset.getInsetId() != null}">
			<!-- Link to Inset Parent on World Map -->
			<a class="popup-link btn-loading" 
					href="/search/location?byId=${mapInset.getInsetId()}${showAllUrl}">
				&uarr; <c:out value="${mapInset.getInsetNameCapitalized()}"/>
			</a>
			<br/>
		</c:if>
		<c:if test="${thisLocation != null}">
			<table>
				<tr>
					<!-- Link to Previous Location -->
					<td style="width:33vw; text-align:right;">
						<a id="prevLocationLink" href="/search/location?byId=${prevLocation.getLocationId()}${showAllUrl}">
							${prevLocation.getLabelName()} &larr;
						</a>
					</td>
					<!-- Current Location Icon -->
					<td>
						<img id="searchIcon"
							class="img-icon-square" 
							src="${context}/${thisLocation.getLocationTypeCode()}.svg"
							title="${thisLocation.getLabelName()}">
					</td>
					<!-- Link to Next Location -->
					<td style="width:33vw; text-align:left;">
						<a id="nextLocationLink" href="/search/location?byId=${nextLocation.getLocationId()}${showAllUrl}">
							&rarr; ${nextLocation.getLabelName()}
						</a>
					</td>
				</tr>
			</table>
			<c:if test="${thisLocation.getAltInsetId() != null}">
				<!-- Link to Inset Map for Location -->
				<div id="insetMapLink">
					<a href="/search/location?byId=${thisLocation.getAltInsetId()}${showAllUrl}" class="btn-loading">
						&darr; <c:out value="${thisLocation.getAltLabelName()}"/>
					</a>
				</div>
			</c:if>
			<c:if test="${thisLocation.getRelatedPatronageId() != null}">
				<!-- Link to Related Patronage for Location -->
				<div id="relatedSearchLink">
					<a href="/search/patronage?byId=${thisLocation.getRelatedPatronageId()}${showAllUrl}" class="btn-loading">
						View Related Entry
					</a>
				</div>
			</c:if>
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
		<c:if test="${thisPatronage != null && thisPatronage.getRelatedSearchLink() != null}">
			<!-- Link to Related Location for Patronage -->
			<div id="relatedSearchLink">
				<a href="${thisPatronage.getRelatedSearchLink()}${showAllUrl}" class="btn-loading">
					View Related Entry
				</a>
			</div>
		</c:if>

		<c:if test="${thisStar != null}">
			<!-- Star-Specific Fields -->
			<div class="search-detail">
				<!-- Star Image -->
				<img id="starImage" 
					class="img-icon-location" 
					src="${context}/${thisStar.getStarId()}.png"
					title="${searchTerm}"/>
				<!-- Star Detail -->
				<p><c:out value="${thisStar.getStarDetail()}"/>. Seen to culminate in the early evening hours of 
					<a href="${context}/search/day?byId=${thisStar.getCulminationDayId()}${showAllUrl}" class="btn-loading">
						${thisStar.getCulminationDayName()}</a>.
				</p>
				<p>
					See 
					<c:choose>
						<c:when test="${referenceLinkIsVisible}">
							<a target="_blank" 
								rel="noopener noreferrer"
								href="/reference-excerpt?forStar=${thisStar.getStarId()}" >
								<c:out value="${thisStar.getReferenceFormatted()}" />
							</a>
						</c:when>
						<c:otherwise>
							<c:out value="${thisStar.getReferenceFormatted()}" />
						</c:otherwise>
					</c:choose>
				</p>
			</div>
			<table>
				<tr>
					<!-- Link to Previous Star -->
					<td style="width:33vw; text-align:right;">
						<a id="prevLink" href="/search/star?byId=${thisStar.getLastStarId()}${showAllUrl}">
							${thisStar.getLastStarName()} &larr;
						</a>
					</td>
					<!-- Current Star Icon -->
					<td>
						<img id="searchIcon"
							class="img-icon-square-large" 
							src="${context}/${thisStar.getStarTypeCode()}.svg"
							title="${thisStar.getStarName()}">
					</td>
					<!-- Link to Next Star -->
					<td style="width:33vw; text-align:left;">
						<a id="nextLink" href="/search/star?byId=${thisStar.getNextStarId()}${showAllUrl}">
							&rarr; ${thisStar.getNextStarName()}
						</a>
					</td>
				</tr>
			</table>
		</c:if>
	</c:if>

	<!-- Conditionally-Displayed Fields -->
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

	<!-- Pass required fields from Controller to JQuery -->
	<div class="controller-fields">
		<div id="userRite" 
			data-role="page" 
			data-hidden="true" 
			data-options='{
				"displayRiteId":${userRite.getCurrentOrOverrideRiteId(showAll)},
				"originalRiteId":${userRite.getRiteId()},
				"displayCalendarId":${userRite.getCurrentOrOverrideCalendarId(showAll)},
				"originalCalendarId":${userRite.getCalendarId()}
			}'></div>
		<div id="mapInset" 
			data-role="page" 
			data-hidden="true" 
			data-options='{
				"insetId":${mapInset.getInsetId()},
				"insetName":"${mapInset.getInsetName()}",
				"insetX":${mapInset.getInsetX()},
				"insetY":${mapInset.getInsetY()},
				"insetBoundsX1":${mapInset.getInsetBoundsX1()},
				"insetBoundsY1":${mapInset.getInsetBoundsY1()},
				"insetBoundsX2":${mapInset.getInsetBoundsX2()},
				"insetBoundsY2":${mapInset.getInsetBoundsY2()},
				"minZoom":${mapInset.getMinZoom()},
				"maxZoom":${mapInset.getMaxZoom()}
			}'></div>
		<div id="prevLocation" 
			data-role="page" 
			data-hidden="true" 
			data-options='{
				"locationId":${prevLocation.getLocationId()},
				"pointX":${prevLocation.getPointX()},
				"pointY":${prevLocation.getPointY()}
			}'></div>
		<div id="thisLocation" 
			data-role="page" 
			data-hidden="true" 
			data-options='{
				"locationId":${thisLocation.getLocationId()},
				"pointX":${thisLocation.getPointX()},
				"pointY":${thisLocation.getPointY()}
			}'></div>
		<div id="nextLocation" 
			data-role="page" 
			data-hidden="true" 
			data-options='{
				"locationId":${nextLocation.getLocationId()},
				"pointX":${nextLocation.getPointX()},
				"pointY":${nextLocation.getPointY()}
			}'></div>
		<div id="thisPatronage" 
			data-role="page" 
			data-hidden="true" 
			data-options='{
				"patronageId":${thisPatronage.getPatronageId()},
				"pointX":${thisPatronage.getPointX()},
				"pointY":${thisPatronage.getPointY()}
			}'></div>
		<div id="thisStar" 
			data-role="page" 
			data-hidden="true" 
			data-options='{
				"starId":${thisStar.getStarId()},
				"pointX":${thisStar.getPointX()},
				"pointY":${thisStar.getPointY()}
			}'></div>
	</div>

</body>
</html>