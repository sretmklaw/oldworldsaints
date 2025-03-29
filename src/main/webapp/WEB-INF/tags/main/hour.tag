<%@ tag language="java" 
	pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" 
	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ attribute name="hour" 
	required="true"
	type="com.cimeliarchium.model.dao.Hour" %>
<%@ attribute name="userCalendar" 
	required="true" 
	type="com.cimeliarchium.model.dao.Calendar" %>
<%@ attribute name="readingList" 
	required="true"
	type="java.util.List" %>

<!DOCTYPE html>
<html>
<head></head>
<body>

	<c:set var="userCalendarId" 
		value="${userCalendar.getCalendarId()}" />

	<!-- Hour Tab Content -->
	<c:forEach var="reading" items="${readingList}" varStatus="i">
		<c:if test="${reading.getReadingContent() != null}">
			<button id="hourTabContentBtn${hour.getHourId()}"
				class="result-header" 
				data-toggle="collapse" 
				data-target="#hourTabContent${hour.getHourId()}Reading${reading.getReadingId()}">
					<c:out value="${reading.getReadingName()}" />
			</button>
			<div id="hourTabContent${hour.getHourId()}Reading${reading.getReadingId()}" 
				class="result-content collapse ${isActive && i.index == 1 ? 'show' : 'collapsed'}">
				<c:if test="${reading.getReadingNameAlt() != ''}">
					<b><c:out value="${reading.getReadingNameAlt()}" /></b>
					<hr/>
				</c:if>
				<c:if test="${showAudio}">
					<audio controls 
						id="${reading.getReadingId()}"
						title="${reading.getReadingName()}"
						preload="auto" >
						<source type="audio/mp3" 
							src="/${reading.getReadingId()}.mp3" />
					</audio>
					<hr/>
				</c:if>
				<c:if test="${userCalendar.isIslamicCreedApplicable(userCalendarId, null)}">
					<!-- Islamic Bismillah -->
					<div class="top-padded">
						<i>In the Name of God, the Compassionate, the Merciful:</i>
					</div>
				</c:if>
				<c:forEach var="verse" 
						items="${fn:split(reading.getReadingContent(),'[')}">
					<c:set var="verseNum" 
						value="${fn:split(verse,']')[0]}"/>
					<c:set var="verseText" 
						value="${fn:split(verse,']')[1]}"/>
					<div class="verse">
						<span class="col-label">
							<c:out value="${verseNum}"/>
						</span>
						<c:out value="${verseText}"/>
					</div>
				</c:forEach>
				<c:if test="${userCalendar.isTrinitarianCreedApplicable(userCalendarId, null)}">
					<!-- Trinitarian Doxology -->
					<div class="top-padded">
						<i>Glory be to the Father, and to the Son, and to the Holy Ghost.
						As it was in the beginning, is now and ever shall be, 
						world without end. Amen.</i>
					</div>
				</c:if>
			</div>
		</c:if>
	</c:forEach>

</body>
</html>