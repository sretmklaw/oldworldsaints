<%@ tag language="java" 
	pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" 
	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" 
	tagdir="/WEB-INF/tags" %>
<%@ attribute name="showAll" 
	required="true" 
	type="java.lang.Boolean" %>

<!DOCTYPE html>
<html>
<head></head>
<body>

	<c:set var="showAllUrl" value="${showAll ? '&showAll=true' : ''}" />

	<!-- Top Pagination -->
	<p>
		<a href="${context}/" class="btn-loading">Return to Current Day</a>
	</p>
	<c:if test="${pageNumberList != null}">
		<div id="topPagination">
			<p>
				Page <c:forEach var="pageNum" items="${pageNumberList}">
					<a href="${context}/${searchPath}${searchId}&page=${pageNum}${showAllUrl}" 
						class="pagination-link btn-loading 
							${pageNum == currentPageNumber || currentPageNumber == null && pageNum == 1
								? 'current-page' : ''}">${pageNum}</a>
				</c:forEach>
			</p>
		</div>
	</c:if>

	<!-- Search Results -->
	<c:choose>
		<c:when test="${resultMap.size() > 0}">
			<c:forEach var="dtoWithRites" items="${resultMap.entrySet()}">
				<c:if test="${dtoWithRites.getKey().getDayCommemorationDtoId() != null}">
					<tags:commemoration dto="${dtoWithRites.getKey()}" 
						riteList="${dtoWithRites.getValue()}" 
						userCalendar="${userCalendar}" 
						userRiteId="${userRite.getRiteId()}" 
						showAll="${showAll}"
						showExpanded="${searchTerm == 'commemorationId' 
							|| resultMap.size() == 1}" /> 
				</c:if>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<div class="none-found">
				<p>No commemorations found for current search criteria and rite.</p>
				<p>
					Click <a href="${context}/${searchPath}000" class="btn-loading">here</a> to view a random entry.
				</p>
			</div>
		</c:otherwise>
	</c:choose>

	<!-- Bottom Pagination -->
	<c:if test="${pageNumberList != null}">
		<div id="bottomPagination">
			<p>
				Page <c:forEach var="pageNum" items="${pageNumberList}">
					<a href="${context}/${searchPath}${searchId}&page=${pageNum}${showAllUrl}" 
						class="pagination-link btn-loading 
							${pageNum == currentPageNumber || currentPageNumber == null && pageNum == 1
								? 'current-page' : ''}">${pageNum}</a>
				</c:forEach>
			</p>
		</div>
		<p>
			<a href="${context}/" class="btn-loading">Return to Current Day</a>
		</p>
	</c:if>

</body>
</html>