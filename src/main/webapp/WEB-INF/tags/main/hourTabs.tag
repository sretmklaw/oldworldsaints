<%@ tag language="java" 
	pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" 
	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="main" 
	tagdir="/WEB-INF/tags/main" %>
<%@ attribute name="hourTabMap" 
	required="true"
	type="java.util.LinkedHashMap" %>
<%@ attribute name="userCalendar" 
	required="true"
	type="com.cimeliarchium.model.dao.Calendar" %>
<%@ attribute name="showAudio" 
	required="true"
	type="java.lang.Boolean" %>

<!DOCTYPE html>
<html>
<head></head>
<body>

	<!-- Hour Reading Tabs -->
	<div id="tabs" class="row flex-nowrap nav nav-tabs hour-tabs">
		<c:forEach var="hourTabEntry" items="${hourTabMap.entrySet()}">
			<c:set var="hourId" 
				value="${hourTabEntry.getKey()}" />
			<c:set var="hourTab" 
				value="${hourTabEntry.getValue()}" />

			<a id="hourTab${hourId}" 
				class="nav-item nav-link tab col-xs-3 show visible 
					${hourTab.getIsHourActive() ? 'active' : ''}" 
				href="#hourTabContent${hourId}" 
				role="tab" 
				data-toggle="tab">
				<c:set var="hourTypeCode"
					value="${hourTab.getHourType().getHourTypeCode()}" />
				<img id="hourTabContentIcon${hourId}"
					class="img-hour-tab-content" 
					src="${context}/${hourTypeCode}.svg"
					title="${hourTab.getHourDisplayName()}"/>
			</a>
		</c:forEach>
	</div>

	<c:if test="${showAudio}">
		<!-- Play All Audio Options -->
		<div style="background-color:#f1f1f1; padding-top:30px;">
			<button id="amReadingsAudioBtn" 
				class="btn-playall">
				Play A.M. Audio
			</button>
			<button id="pmReadingsAudioBtn" 
				class="btn-playall">
				Play P.M. Audio
			</button>
		</div>
	</c:if>

	<!-- Hour Reading Tabs Content -->
	<div id="hourTabContents" class="row tab-content hour-tab-content">
		<c:forEach var="hourContentEntry" items="${hourTabMap.entrySet()}">
			<c:set var="hourContent" 
				value="${hourContentEntry.getValue()}" />
			<div id="hourTabContent${hourContentEntry.getKey()}" 
				class="hour tab-pane fade 
					${hourContent.getIsHourActive() ? 'active show' : ''}" 
				role="tabpanel">
				<main:hour hour="${hourContent}" 
					userCalendar="${userCalendar}" 
					readingList="${hourContent.getReadings()}" />
			</div>
		</c:forEach>
	</div>

</body>
</html>