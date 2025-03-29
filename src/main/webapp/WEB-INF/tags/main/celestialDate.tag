<%@ tag language="java" 
	pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" 
	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ attribute name="userCalendar" 
	required="true"
	type="com.cimeliarchium.model.dao.Calendar" %>
<%@ attribute name="lunarPhase" 
	required="true"
	type="com.cimeliarchium.model.dao.LunarPhase" %>
<%@ attribute name="zodiacSign" 
	required="true"
	type="com.cimeliarchium.model.dao.Constellation" %>
<%@ attribute name="culminatingStar" 
	required="true"
	type="com.cimeliarchium.model.dao.Star" %>

<!DOCTYPE html>
<html>
<head></head>
<body>

	<c:set var="isUserCalendarAllCommemorations" 
		value="${userCalendar.getIsAllCommemorations()}" />

	<!-- Celestial Date Tab -->
	<div id="celestialTabs">
		<button id="celestialTab" 
			class="celestial-sphere result-header active d-flex justify-content-center" 
			data-toggle="collapse"
			data-target="#celestialTabContents">
			<div id="celestialDateInfo" class="row">
				<div id="zodiacSignIcons">
					<img id="zodiacSignIcon"
						class="img-planisphere" 
						src="${context}/${zodiacSign.getConstellationCode()}.svg"/>
					<img id="zodiacSun"
						src="${context}/SHIMMER.gif"
						title="${zodiacSign.getConstellationNameLatin()}"/>
					<img id="lunarPhase"
						class="img-lunar-phase" 
						src="${context}/${lunarPhase.getLunarPhaseCode()}.svg"
						title="${lunarPhase.getLunarPhaseDetail()}"/>
					<img id="star"
						class="img-star-culmination" 
						src="${context}/${culminatingStar.getConstellationId()}.png"
						title="${culminatingStar.getStarName()}"/>
				</div>
			</div>
		</button>

		<!-- Celestial Date Tab Contents -->
		<div id="celestialTabContents" 
			class="result-content collapse collapsed">

			<p>The current lunar phase is <b><c:out value="${lunarPhase.getLunarPhaseDetail()}"/></b> 
				<c:choose>
					<c:when test="${isUserCalendarAllCommemorations}">
						- the Latin <b><c:out value="${lunarPhase.getLunarPhaseNameLatin()}"/></b>, 
						Greek <b><c:out value="${lunarPhase.getLunarPhaseNameGreek()}"/></b>, 
						Arabic <b><c:out value="${lunarPhase.getLunarPhaseNameArabic()}"/></b>, 
						and Hebrew <b><c:out value="${lunarPhase.getLunarPhaseNameHebrew()}"/></b>.
					</c:when>
					<c:otherwise>
						<c:out value=" (${lunarPhase.getLunarPhaseDisplayName()})."/>
					</c:otherwise>
				</c:choose>
			</p>

			<p>Reaching its culmination early this evening is 
				<b><c:out value="${culminatingStar.getStarNameFormatted()}"/></b> - 
				<c:out value="${culminatingStar.getStarDetail()}"/> 
				(<a target='_blank' 
					rel='noopener noreferrer'
					href='${context}/search/star?byId=${culminatingStar.getStarId()}'>see Detail</a>).
			</p>

			<c:set var="commemorationUrl1" 
				value="<a target='_blank' 
							rel='noopener noreferrer'
							href='${context}/search/commemoration?byId=${zodiacSign.getCommemorationId1()}'>${zodiacSign.getCommemorationName1()}</a>" />
			<c:set var="zodiacDetail1" 
				value="${fn:replace(zodiacSign.getConstellationDisplayDetail(), \"{1}\", commemorationUrl1)}" />
			<c:set var="commemorationUrl2" 
				value="<a target='_blank' 
							rel='noopener noreferrer'
							href='${context}/search/commemoration?byId=${zodiacSign.getCommemorationId2()}'>${zodiacSign.getCommemorationName2()}</a>" />
			<c:set var="zodiacDetail2" 
				value="${fn:replace(zodiacDetail1, \"{2}\", commemorationUrl2)}" />
			<c:set var="commemorationUrl3" 
				value="<a target='_blank' 
							rel='noopener noreferrer'
							href='${context}/search/commemoration?byId=${zodiacSign.getCommemorationId3()}'>${zodiacSign.getCommemorationName3()}</a>" />
			<c:set var="zodiacDetail3" 
				value="${fn:replace(zodiacDetail2, \"{3}\", commemorationUrl3)}" />
			<c:set var="commemorationUrl4" 
				value="<a target='_blank' 
							rel='noopener noreferrer'
							href='${context}/search/commemoration?byId=${zodiacSign.getCommemorationId4()}'>${zodiacSign.getCommemorationName4()}</a>" />
			<c:set var="zodiacDetail4" 
				value="${fn:replace(zodiacDetail3, \"{4}\", commemorationUrl4)}" />
			<c:set var="commemorationUrl5" 
				value="<a target='_blank' 
							rel='noopener noreferrer'
							href='${context}/search/commemoration?byId=${zodiacSign.getCommemorationId5()}'>${zodiacSign.getCommemorationName5()}</a>" />
			<c:set var="zodiacDetail5" 
				value="${fn:replace(zodiacDetail4, \"{5}\", commemorationUrl5)}" />
			<c:set var="commemorationUrl6" 
				value="<a target='_blank' 
							rel='noopener noreferrer'
							href='${context}/search/star?byId=${zodiacSign.getStarIdPrimary()}'>${zodiacSign.getStarNamePrimary()}</a>" />
			<c:set var="zodiacDetail6" 
				value="${fn:replace(zodiacDetail5, \"{6}\", commemorationUrl6)}" />
			<c:set var="commemorationUrl7" 
				value="<a target='_blank' 
							rel='noopener noreferrer'
							href='${context}/search/star?byId=${zodiacSign.getStarIdSecondary()}'>${zodiacSign.getStarNameSecondary()}</a>" />
			<c:set var="zodiacDetail7" 
				value="${fn:replace(zodiacDetail6, \"{7}\", commemorationUrl7)}" />
			<c:set var="zodiacDetailParts" 
				value="${fn:split(zodiacDetail7,'%')}" />

			<c:choose>
				<c:when test="${isUserCalendarAllCommemorations}">
					<p>The sun is rising in the constellation of 
						<b><c:out value="${zodiacSign.getConstellationNameLatin()}"/></b> 
						(the Greek <b><c:out value="${zodiacSign.getConstellationNameGreek()}"/></b>, 
						Arabic <b><c:out value="${zodiacSign.getConstellationNameArabic()}"/></b>, 
						and Hebrew <b><c:out value="${zodiacSign.getConstellationNameHebrew()}"/></b>),
						${zodiacDetailParts[0]} Hence its association with:
					</p>
					<ul>
						<li>${zodiacDetailParts[1]}</li>
						<li>${zodiacDetailParts[2]}</li>
						<li>${zodiacDetailParts[3]}</li>
					</ul>
				</c:when>
				<c:otherwise>
					<p>The sun is rising in the constellation of 
					<b><c:out value="${zodiacSign.getConstellationDisplayName()}"/></b>, 
					${zodiacDetailParts[0]} ${zodiacDetailParts[1]}.</p>
				</c:otherwise>
			</c:choose>
		</div>
	</div>

</body>
</html>