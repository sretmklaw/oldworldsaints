<%@ tag language="java" 
	pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 
	uri="http://www.springframework.org/tags/form" %>
<%@ attribute name="searchCriteria" 
	required="true"
	type="com.cimeliarchium.model.dao.SearchCriteria" %>
<%@ attribute name="patronageList" 
	required="true"
	type="java.util.List" %>

<!DOCTYPE html>
<html>
<head></head>
<body>

	<!-- Patronage Drop-Down Select Menu -->
	<form:form id="patronageForm"
		method="GET" 
		action="${context}/search/patronage" 
		class="form-horizontal dropdown-select"
		modelAttribute="searchCriteria" >

		<form:select id="patronageDropdown"
			class="dropdown-option form-control" 
			path="byId"
			disabled="true">

			<!-- Default Option -->
			<option selected
				value="" 
				class="default-option" 
				disabled>Patronage</option>

			<!-- Available Options -->
			<c:forEach items="${patronageList}" 
				var="patronage">
				<option class="selected-option" 
					value="${patronage.getPatronageId()}">
					<c:out value="${patronage.getPatronageName()}" />
				</option>
			</c:forEach>
		</form:select>

		<!-- Submit Button -->
		<button disabled
			id="patronageSearchBtn"
			class="search btn btn-secondary btn-loading">
			&#128270;
		</button>
	</form:form>

</body>
</html>