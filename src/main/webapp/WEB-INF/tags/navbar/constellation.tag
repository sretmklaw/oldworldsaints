<%@ tag language="java" 
	pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 
	uri="http://www.springframework.org/tags/form" %>
<%@ attribute name="searchCriteria" 
	required="true"
	type="com.cimeliarchium.model.dao.SearchCriteria" %>
<%@ attribute name="constellationList" 
	required="true"
	type="java.util.List" %>

<!DOCTYPE html>
<html>
<head></head>
<body>

	<!-- Constellation Drop-Down Select Menu -->
	<form:form id="constellationForm"
		class="form-horizontal dropdown-select"
		modelAttribute="searchCriteria" >

		<form:select id="constellationDropdown"
			class="dropdown-option form-control" 
			path="byId">

			<!-- Default Option -->
			<option selected
				value="" 
				class="default-option" 
				disabled>Constellation</option>

			<!-- Available Options are populated from the session attribute -->
			<c:forEach items="${constellationList}" 
				var="constellation">
				<option class="selected-option" 
					value="${constellation.getConstellationId()}">
					<c:out value="${constellation.getConstellationNameLatin()}" />
				</option>
			</c:forEach>
		</form:select>
	</form:form>

</body>
</html>