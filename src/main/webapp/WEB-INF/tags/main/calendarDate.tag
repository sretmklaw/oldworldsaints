<%@ tag language="java" 
	pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" 
	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" 
	tagdir="/WEB-INF/tags" %>
<%@ attribute name="userCalendar" 
	required="true"
	type="com.cimeliarchium.model.dao.Calendar" %>
<%@ attribute name="userRiteId" 
	required="true"
	type="java.lang.Long" %>
<%@ attribute name="resultMap" 
	required="true"
	type="java.util.TreeMap" %>
<%@ attribute name="liturgyTypeList" 
	required="true"
	type="java.util.ArrayList" %>
<%@ attribute name="cycleDayName" 
	required="true"
	type="java.lang.String" %>
<%@ attribute name="liturgyTypeName" 
	required="true"
	type="java.lang.String" %>
<%@ attribute name="showAll" 
	required="true"
	type="java.lang.Boolean" %>
<%@ attribute name="requestButtonIsVisible" 
	required="true"
	type="java.lang.Boolean" %>

<!DOCTYPE html>
<html>
<head></head>
<body>

	<c:set var="commemorationWithRitesIterator" 
		value="${resultMap.entrySet()}" />

	<!-- Calendar Date Tab -->
	<div id="dayTabs"
		class="row flex-nowrap nav nav-tabs">
		<button id="dayTab" 
			class="result-header-parent active" 
			data-toggle="collapse"
			data-target="#dayTabContents">
			<c:choose>
				<c:when test="${liturgyTypeList == null || liturgyTypeList.isEmpty()}">
					<c:out value="No match for the specified calendar date." />
				</c:when>
				<c:otherwise>
					<table>
						<c:forEach var="ltype" items="${liturgyTypeList}">
							<c:set var="calendarDate" 
									value="${ltype.getCalendarDateString()}" />
							<c:set var="cycleDayName" 
									value="${ltype.getCycleDayName()}" />
							<c:set var="liturgyTypeName" 
									value="${ltype.getLiturgyName()}" />
							<tr>
								<c:if test="${userCalendar.getIsAllCommemorations()}">
									<td rowspan="2" 
										class="calendar-tab">
										<img id="calendarTabContentIcon${ltype.getLiturgyTypeId()}"
											class="img-icon-square" 
											src="${context}/${ltype.getCalendarCode()}.svg"
											title="${ltype.getCalendarName()}"/>
									</td>
								</c:if>
								<td>
									<c:out value="${calendarDate}"/>
								</td>
							</tr>
							<tr>
								<td class="calendar-detail">
									<c:out value="${cycleDayName} ${liturgyTypeName}" />
									<c:if test="${ltype.getLiturgyDetails() != null}">
										<c:out value=" (${ltype.getLiturgyDetails()})" />
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</table>
				</c:otherwise>
			</c:choose>
		</button>
	</div>

	<!-- Calendar Date Tab Contents -->
	<div id="dayTabContents" 
		class="collapse active show 
			${commemorationWithRitesIterator.size() > 0 ? 'result-content-parent' : 'result-content'}">
		<c:choose>
			<c:when test="${commemorationWithRitesIterator.size() > 0}">
				<c:forEach var="dayEntry" items="${commemorationWithRitesIterator}">
					<tags:commemoration dto="${dayEntry.getKey()}" 
						riteList="${dayEntry.getValue()}" 
						userCalendar="${userCalendar}" 
						userRiteId="${userRiteId}" 
						showAll="${showAll}" 
						showExpanded="${commemorationWithRitesIterator.size() == 1}" />
				</c:forEach>
			</c:when>
			<c:otherwise>
				<p class="top-padded">No commemorations found for current date and rite.</p>
				<p>
					Click <a href="${context}/search/commemoration?byId=000" class="btn-loading">here</a> to view a random entry.
				</p>
			</c:otherwise>
		</c:choose>
	</div>

</body>
</html>