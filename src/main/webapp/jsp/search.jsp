<%@ include file="_import.jsp" %>
<%@ taglib prefix="search" 
	tagdir="/WEB-INF/tags/search" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="_header.jsp" %>
	<link rel="stylesheet" 
		type="text/css"
		href="${context}/search-style.css" />
	<link rel="stylesheet" 
		type="text/css"
		href="${context}/navbar-style.css" />
</head>
<body>

	<tags:timeout />
	<tags:navbar />

	<c:set var="requestUri" 
		value="${requestScope['javax.servlet.forward.request_uri']}" />
	<c:choose>
		<c:when test="${fn:contains(requestUri, 'location') 
				|| (fn:contains(requestUri, 'patronage') && patronageCoordinates != null)
				|| (fn:contains(requestUri, 'star') && starCoordinates != null)}">

			<div id="loading">
				<img id="loading-image" src="${context}/LOAD.gif" onerror="this.style.display='none'" />
			</div>

			<div class="parent">
				<div id="map" style="display: none">
					<!-- Map Contents populated dynamically below -->
				</div>
				<div id="search" class="child maximized" style="height: 100%">
					<div id="toggle-wrapper" class="row align-items-center">
						<div id="toggle" class="mx-auto">Show Map</div>
					</div>
					<div id="search-window">
						<div id="search-window-contents" class="container">
							<div class="row h-100 justify-content-center align-items-center">
								<div class="mx-auto">
									<div class="text-area text-center">
										<search:map/>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<script src="${context}/static/jquery/jquery.min.js"></script>
			<script src="${context}/static/bootstrap/js/bootstrap.min.js"></script>
			<script src="${context}/map-onload.js"></script>
			<script src="${context}/static/leaflet/leaflet.js"></script>
			<script src="${context}/leaflet-search.src.js"></script>

		</c:when>
		<c:when test="${fn:contains(requestUri, '/century')}">
			<tags:wrapper>
				<search:century/>
			</tags:wrapper>
		</c:when>
		<c:when test="${fn:contains(requestUri, '/day')}">
			<tags:wrapper>
				<search:day/>
			</tags:wrapper>
		</c:when>
		<c:otherwise>
			<tags:wrapper>
				<search:default/>
			</tags:wrapper>
		</c:otherwise>
	</c:choose>

	<script src="${context}/collapseall-onclick.js"></script>
	<script src="${context}/dropdown-onclick-navbar.js"></script>
	<script src="${context}/reset-dependent-inputs.js"></script>
	<script src="${context}/reset-dependent-inputs-navbar.js"></script>
	<script src="${context}/reset-dependent-inputs-search.js"></script>
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
	<script src="${context}/window-onload.js"></script>
	<script src="${context}/cache-onload.js"></script>

</body>
</html>