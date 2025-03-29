<%@ tag language="java" 
	pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 
	uri="http://www.springframework.org/tags/form" %>
<%@ attribute name="searchCriteria" 
	required="true"
	type="com.cimeliarchium.model.dao.SearchCriteria" %>
<%@ attribute name="dayList" 
	required="true"
	type="java.util.List" %>

<!DOCTYPE html>
<html>
<head></head>
<body>

	<!-- Day Drop-Down Select Menu -->
	<form:form id="dayForm"
		method="GET" 
		action="${context}/search/day" 
		class="form-horizontal dropdown-select"
		modelAttribute="searchCriteria" >

		<form:select id="dayDropdown"
			class="dropdown-option form-control" 
			path="byId"
			disabled="true">

			<!-- Default Option -->
			<option selected
				value="" 
				class="default-option" 
				disabled>Day</option>

			<!-- Available Options -->
			<c:forEach items="${dayList}" 
				var="day">
				<option class="selected-option" 
					value="${day.getDayId()}">
					<c:out value="${day.getDayOfMonth()}" />
				</option>
			</c:forEach>
		</form:select>

		<!-- Submit Button -->
		<button disabled
			id="daySearchBtn"
			class="search btn btn-secondary btn-loading">
			&#128270;
		</button>
	</form:form>

</body>
</html>