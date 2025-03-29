<%@ tag language="java" 
	pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 
	uri="http://www.springframework.org/tags/form" %>
<%@ attribute name="searchCriteria" 
	required="true"
	type="com.cimeliarchium.model.dao.SearchCriteria" %>
<%@ attribute name="calendarList" 
	required="true"
	type="java.util.List" %>

<!DOCTYPE html>
<html>
<head></head>
<body>

	<!-- Calendar Drop-Down Select Menu -->
	<form:form id="calendarForm"
		class="form-horizontal dropdown-select"
		modelAttribute="searchCriteria" >

		<form:select id="calendarDropdown"
			class="dropdown-option form-control" 
			path="byId">

			<!-- Default Option -->
			<option selected
				value="" 
				class="default-option" 
				disabled>Calendar</option>

			<!-- Available Options are populated from the session attribute -->
			<c:forEach items="${calendarList}" 
				var="calendar">
				<option class="selected-option" 
					value="${calendar.getCalendarId()}">
					<c:out value="${calendar.getCalendarDetail().split(' ')[0]}" />
				</option>
			</c:forEach>
		</form:select>
	</form:form>

</body>
</html>