<%@ tag language="java" 
	pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 
	uri="http://www.springframework.org/tags/form" %>
<%@ attribute name="searchCriteria" 
	required="true"
	type="com.cimeliarchium.model.dao.SearchCriteria" %>
<%@ attribute name="patronageTypeList" 
	required="true"
	type="java.util.List" %>

<!DOCTYPE html>
<html>
<head></head>
<body>

	<!-- PatronageType Drop-Down Select Menu -->
	<form:form id="patronageTypeForm"
		class="form-horizontal dropdown-select"
		modelAttribute="searchCriteria" >

		<form:select id="patronageTypeDropdown"
			class="dropdown-option form-control" 
			path="byId">

			<!-- Default Option -->
			<option selected
				value="" 
				class="default-option" 
				disabled>Patronage Type</option>

			<!-- Available Options are populated from the session attribute -->
			<c:forEach items="${patronageTypeList}" 
				var="patronageType">
				<option class="selected-option" 
					value="${patronageType.getPatronageTypeId()}">
					<c:out value="${patronageType.getPatronageTypeName()}" />
				</option>
			</c:forEach>
		</form:select>
	</form:form>

</body>
</html>