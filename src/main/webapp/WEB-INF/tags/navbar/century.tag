<%@ tag language="java" 
	pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 
	uri="http://www.springframework.org/tags/form" %>
<%@ attribute name="searchCriteria" 
	required="true"
	type="com.cimeliarchium.model.dao.SearchCriteria" %>
<%@ attribute name="centuryList" 
	required="true"
	type="java.util.List" %>

<!DOCTYPE html>
<html>
<head></head>
<body>

	<!-- Century Drop-Down Select Menu -->
	<form:form id="centuryForm"
		method="GET"
		action="${context}/search/century" 
		class="form-horizontal dropdown-select"
		modelAttribute="searchCriteria" >

		<form:select id="centuryDropdown"
			class="dropdown-option form-control" 
			path="byId">

			<!-- Default Option -->
			<option selected
				value="" 
				class="default-option" 
				disabled>Century</option>

			<!-- Available Options -->
			<c:set var="calendarId" 
				value="${_currentUserDetails.getCalendar().getCalendarId()}" />
			<c:forEach items="${centuryList}" 
				var="century">
				<option class="selected-option" 
					value="${century.getCenturyId()}">
					<c:out value="${century.getCenturyNameFormatted(calendarId)}" />
				</option>
			</c:forEach>
		</form:select>

		<!-- Submit Button -->
		<button disabled
			id="centurySearchBtn"
			class="search btn btn-secondary btn-loading">
			&#128270;
		</button>
	</form:form>

</body>
</html>