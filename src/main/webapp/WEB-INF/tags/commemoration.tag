<%@ tag language="java" 
	pageEncoding="ISO-8859-1" %>
<%@ include file="/jsp/_import.jsp" %>
<%@ attribute name="dto" 
	required="true"
	type="com.cimeliarchium.model.dto.DayCommemorationDto" %>
<%@ attribute name="riteList" 
	required="true"
	type="java.util.List" %>
<%@ attribute name="userCalendar" 
	required="true" 
	type="com.cimeliarchium.model.dao.Calendar" %>
<%@ attribute name="userRiteId" 
	required="true" 
	type="java.lang.Long" %>
<%@ attribute name="showAll" 
	required="true" 
	type="java.lang.Boolean" %>
<%@ attribute name="showExpanded" 
	required="false" 
	type="java.lang.Boolean" %>

<!DOCTYPE html>
<html>
<head></head>
<body>

	<c:set var="showAllUrl" 
		value="${showAll ? '&showAll=true' : ''}" />
	<c:set var="cm" 
		value="${dto.getCommemoration()}" />
	<c:set var="cmId" 
		value="${cm.getCommemorationId()}" />
	<c:set var="userCalendarId" 
		value="${userCalendar.getUserCalendarOrAllCommemorations(showAll)}" />
	<c:set var="isUserCalendarAllCommemorations" 
		value="${userCalendar.getIsAllCommemorations(showAll)}" />
	<c:set var="cmYear" 
		value="${cm.getCommemorationYearFormatted(userCalendarId)}" />
	<c:set var="century" 
		value="${dto.getCentury()}" />
	<c:set var="centuryName" 
		value="${dto.getCenturyNameFormatted()}" />
	<c:set var="cmType" 
		value="${dto.getCommemorationType()}" />

	<!-- Search Result Header -->
	<button id="commemorationTab${cmId}"
		class="result-header ${showExpanded ? 'active' : ''}" 
		data-toggle="collapse" 
		data-target="#commemorationTabContent${cmId}">

		<table>
			<tr>
				<td class="align-left" colspan="2">
					<c:out value="${cm.getCommemorationNameFormatted(userCalendarId)}"/>
				</td>
			</tr>
			<tr>
				<td class="align-left">
					<i class="date-name">
						<c:choose>
							<c:when test="${cmYear != null && !cmYear.equals('')}">
								<c:out value="${cmYear}"/>
							</c:when>
							<c:otherwise>
								<c:out value="${centuryName}"/>
							</c:otherwise>
						</c:choose>
					</i>
				</td>
				<td class="align-right">
					<c:if test="${isUserCalendarAllCommemorations}">
						<c:forEach var="rite" items="${riteList}">
							<img id="riteIcon${cmId}-${rite.getRiteId()}"
								class="img-icon-vertical" 
								src="${context}/${rite.getRiteCode()}.svg"
								title="${rite.getRiteName()}"/>
						</c:forEach>
					</c:if>
				</td>
			</tr>
		</table>
	</button>

	<!-- Search Result Details -->
	<div id="commemorationTabContent${cmId}" 
		class="result-content top-padded collapse ${showExpanded ? 'show' : 'collapsed'}">

		<!-- Type Field -->
		<div class="row">
			<div class="col-sm-2 col-label">Type</div>
			<div class="col-sm-10 col-value">
				<c:out value="${cmType.getCommemorationTypeNameFormatted(userRiteId)}" />
			</div>
		</div>

		<!-- Date Field -->
		<c:set var="day" 
				value="${dto.getDay()}" />
		<c:set var="altDayAd" 
				value="${dto.getAltDayAd()}" />
		<c:set var="altDayAh" 
				value="${dto.getAltDayAh()}" />
		<c:set var="altDayAm" 
				value="${dto.getAltDayAm()}" />
		<div class="row">
			<div class="col-sm-2 col-label">Date</div>
			<div class="col-sm-10 col-value">
				<c:if test="${day.getDayId() != null}">
					<a href="${context}/search/day?byId=${day.getDayId()}${showAllUrl}" class="btn-loading">
						<c:out value="${day.getMonthAndDayFormattedIndicateJulian(julianDate)}" />
					</a>
				</c:if>
				<c:if test="${altDayAd.getDayId() != null}">
					/ <a href="${context}/search/day?byId=${altDayAd.getDayId()}${showAllUrl}" class="btn-loading">
						<c:out value="${altDayAd.getMonthAndDayFormatted()}" />
					</a>
				</c:if>
				<c:if test="${altDayAh.getDayId() != null}">
					/ <a href="${context}/search/day?byId=${altDayAh.getDayId()}${showAllUrl}" class="btn-loading">
						<c:out value="${altDayAh.getMonthAndDayFormatted()}" />
					</a>
				</c:if>
				<c:if test="${altDayAm.getDayId() != null}">
					/ <a href="${context}/search/day?byId=${altDayAm.getDayId()}${showAllUrl}" class="btn-loading">
						<c:out value="${altDayAm.getMonthAndDayFormatted()}" />
					</a>
				</c:if>
			</div>
		</div>

		<c:if test="${century != null 
				&& centuryName != null && !centuryName.isEmpty()}">
			<!-- Century Field -->
			<div class="row">
				<div class="col-sm-2 col-label">Century</div>
				<div class="col-sm-10 col-value">
					<a href="${context}/search/century?byId=${century.getCenturyId()}${showAllUrl}" class="btn-loading">
						<c:out value="${fn:replace(centuryName,' Century','')}" />
					</a>
				</div>
			</div>
		</c:if>

		<c:set var="nat" 
				value="${dto.getNation()}" />
		<c:if test="${nat != null
				&& nat.getNationName() != null 
				&& !nat.getNationName().isEmpty()}">
			<!-- Nation Field -->
			<div class="row">
				<div class="col-sm-2 col-label">Nation</div>
				<div class="col-sm-10 col-value">
					<img id="cNationIcon${cmId}"
						class="flag img-icon-horizontal" 
						src="${context}/${nat.getNationCode()}.svg">
					<c:if test="${nat.getAltNationCode() != null 
							&& !nat.getAltNationCode().isEmpty()}" >
						<img id="cAltNationIcon${cmId}"
							class="flag img-icon-horizontal" 
							src="${context}/${nat.getAltNationCode()}.svg">
					</c:if>
					<a href="${context}/search/nation?byId=${nat
							.getNationId()}${showAllUrl}" class="btn-loading">
						<c:out value="${nat.getNationName()}" />
					</a>
				</div>
			</div>
		</c:if>

		<c:set var="loc"
				value="${dto.getLocation()}" />
		<c:set var="altLoc"
				value="${dto.getAltLocation()}" />
		<c:if test="${loc != null
				&& loc.getLabelName() != null 
				&& !loc.getLabelName().isEmpty()}">
			<!-- Location Field(s) -->
			<div class="row">
				<div class="col-sm-2 col-label">Location</div>
				<div class="col-sm-10 col-value">
					<img id="cLocationIcon${cmId}"
						class="img-icon-square" 
						src="${context}/${loc.getLocationTypeCode()}.svg">
					<a href="${context}/search/location?byId=
							${loc.getLocationId()}${showAllUrl}" class="btn-loading">
						<c:out value="${loc.getLabelName()}" />
					</a>
					<c:if test="${altLoc != null
							&& altLoc.getLabelName() != null 
							&& !altLoc.getLabelName().isEmpty()}">
						/ <a href="${context}/search/location?byId=
								${altLoc.getLocationId()}${showAllUrl}" class="btn-loading">
							<c:out value="${altLoc.getLabelName()}" />
						</a>
					</c:if>
				</div>
			</div>
		</c:if>

		<c:set var="star"
				value="${dto.getStar()}" />
		<c:if test="${star != null
				&& star.getStarName() != null 
				&& !star.getStarName().isEmpty()}">
			<!-- Star Field(s) -->
			<div class="row">
				<div class="col-sm-2 col-label">Star</div>
				<div class="col-sm-10 col-value">
					<img id="cStarIcon${cmId}"
						class="img-icon-square" 
						src="${context}/${star.getConstellationId()}.png">
					<a href="${context}/search/star?byId=
							${star.getStarId()}${showAllUrl}" class="btn-loading">
						<c:out value="${star.getStarNameFormatted()}" />
					</a>
				</div>
			</div>
		</c:if>

		<c:set var="pat" 
				value="${dto.getPatronage()}" />
		<c:if test="${pat != null
				&& pat.getPatronageName() != null 
				&& !pat.getPatronageName().isEmpty()}">
			<!-- Patronage Field -->
			<div class="row">
				<div class="col-sm-2 col-label">Patronage</div>
				<div class="col-sm-10 col-value">
					<c:if test="${pat.getPointX() != null && pat.getPointY() != null}">
						<img id="cPatronageIcon${cmId}" 
							class="img-icon-patronage-cm" 
							src="${context}/${pat.getPatronageId()}.png"/>
					</c:if>
					<a href="${context}/search/patronage?byId=${pat
							.getPatronageId()}${showAllUrl}" class="btn-loading">
						<c:out value="${pat.getPatronageNameFormatted()}" />
					</a>
				</div>
			</div>
		</c:if>

		<c:if test="${dto.hasTags()}">
			<!-- Tags Field -->
			<div class="row">
				<div class="col-sm-2 col-label">Tags</div>
				<div class="col-sm-10 col-value">
					<tags:tags dto="${dto}" />
				</div>
			</div>
		</c:if>

		<!-- Details Field -->
		<div class="row">
			<div class="col-sm-2 col-label">Details</div>
			<div class="col-sm-10 col-value">
				<c:out value="Commemorating ${cm.getCommemorationDetail()}." />

				<!-- Display Alternate Calendar details, where applicable -->
				<c:set var="altTypeAndReason"
						value="${dto.getCalendarAltTypeAndReasonFormatted(userCalendarId)}" />
				<c:if test="${altTypeAndReason != null}">
					<c:out value="${altTypeAndReason}" />
				</c:if>
			</div>
		</div>

		<c:set var="ref"
				value="${dto.getReference()}" />
		<c:if test="${ref != null
				&& ref.getReferenceName() != null 
				&& !ref.getReferenceName().isEmpty()}">
			<!-- Reference Field -->
			<div class="row">
				<div class="col-sm-2 col-label">References</div>
				<div class="col-sm-10 col-value">
					<c:set var="referenceDisplayName" 
						value="${ref.getReferenceDisplayName()}"/>
					<c:choose>
						<c:when test="${referenceLinkIsVisible}">
							<a target="_blank" 
								rel="noopener noreferrer"
								href="/reference-excerpt?forCommemoration=${dto.getCommemorationId()}" >
								<c:out value="${referenceDisplayName}" />
							</a>
						</c:when>
						<c:otherwise>
							<c:out value="${referenceDisplayName}" />
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</c:if>

		<c:set var="reading"
				value="${dto.getReading()}" />
		<c:if test="${reading != null 
				&& reading.getReadingContent() != null
				&& !reading.getReadingContent().isEmpty()}">
			<hr />
			<!-- Reading Content -->
			<div>
				<b><c:out value="${reading.getReadingNameFormatted()}" /></b>
			</div>
			<c:set var="crd" value="${dto.getCreed()}"/>
			<c:if test="${userCalendar.isIslamicCreedApplicable(userCalendarId, cm.getCreedId())}">
				<!-- Islamic Bismillah -->
				<div class="top-padded">
					<i>In the Name of God, the Compassionate, the Merciful:</i>
				</div>
			</c:if>
			<!-- Verses -->
			<c:choose>
				<c:when test="${reading.getReadingContent().contains('[')}">
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
				</c:when>
				<c:otherwise>
					<c:forEach var="verse" 
							items="${fn:split(fn:replace(reading.getReadingContent(),'. ','|'),'|')}">
						<div class="verse">
							<c:out value="${verse}"/><c:if test="${!verse.endsWith('.')}">.</c:if>
						</div>
					</c:forEach>
				</c:otherwise>
			</c:choose>
			<c:if test="${userCalendar.isTrinitarianCreedApplicable(userCalendarId, cm.getCreedId())}">
				<!-- Trinitarian Doxology -->
				<div class="top-padded">
					<i>Glory be to the Father, and to the Son, and to the Holy Ghost.
					As it was in the beginning, is now and after shall be, 
					world without end. Amen.</i>
				</div>
			</c:if>
		</c:if>
	</div>

</body>
</html>