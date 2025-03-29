<%@ tag language="java" 
	pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 
	uri="http://www.springframework.org/tags/form" %>
<%@ attribute name="searchCriteria" 
	required="true"
	type="com.cimeliarchium.model.dao.SearchCriteria" %>
<%@ attribute name="nationList" 
	required="true"
	type="java.util.List" %>

<!DOCTYPE html>
<html>
<head></head>
<body>

	<!-- Nation Drop-Down Select Menu -->
	<form:form id="nationForm"
		method="GET"
		action="${context}/search/nation" 
		class="form-horizontal dropdown-select"
		modelAttribute="searchCriteria" >

		<form:select id="nationDropdown"
			class="dropdown-option form-control" 
			path="byId">

			<!-- Default Option -->
			<option selected
				value="" 
				class="default-option" 
				disabled>Nationality</option>

			<!-- Available Options -->
			<c:forEach items="${nationList}" 
				var="nation">
				<option class="selected-option" 
					value="${nation.getNationId()}">
					<c:out value="${nation.getNationName()}" />
				</option>
			</c:forEach>
		</form:select>

		<!-- Submit Button -->
		<button disabled
			id="nationSearchBtn"
			class="search btn btn-secondary btn-loading">
			&#128270;
		</button>
	</form:form>

</body>
</html>