<%@ tag language="java" 
	pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 
	uri="http://www.springframework.org/tags/form" %>
<%@ attribute name="searchCriteria" 
	required="true"
	type="com.cimeliarchium.model.dao.SearchCriteria" %>
<%@ attribute name="starList" 
	required="true"
	type="java.util.List" %>

<!DOCTYPE html>
<html>
<head></head>
<body>

	<!-- Star Drop-Down Select Menu -->
	<form:form id="starForm"
		method="GET" 
		action="${context}/search/star" 
		class="form-horizontal dropdown-select"
		modelAttribute="searchCriteria" >

		<form:select id="starDropdown"
			class="dropdown-option form-control" 
			path="byId"
			disabled="true">

			<!-- Default Option -->
			<option selected
				value="" 
				class="default-option" 
				disabled>Star</option>

			<!-- Available Options -->
			<c:forEach items="${starList}" 
				var="star">
				<option class="selected-option" 
					value="${star.getStarId()}">
					<c:out value="${star.getStarNameFormatted()}" />
				</option>
			</c:forEach>
		</form:select>

		<!-- Submit Button -->
		<button disabled
			id="starSearchBtn"
			class="search btn btn-secondary btn-loading">
			&#128270;
		</button>
	</form:form>

</body>
</html>