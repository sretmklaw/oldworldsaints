<%@ tag language="java" 
	pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 
	uri="http://www.springframework.org/tags/form" %>
<%@ attribute name="searchCriteria" 
	required="true"
	type="com.cimeliarchium.model.dao.SearchCriteria" %>
<%@ attribute name="patronageSubtypeList" 
	required="true"
	type="java.util.List" %>

<!DOCTYPE html>
<html>
<head></head>
<body>

	<!-- PatronageSubtype Drop-Down Select Menu -->
	<form:form id="patronageSubtypeForm"
		class="form-horizontal dropdown-select"
		modelAttribute="searchCriteria" >

		<form:select id="patronageSubtypeDropdown"
			class="dropdown-option form-control" 
			path="byId"
			disabled="true">

			<!-- Default Option -->
			<option selected
				value="" 
				class="default-option" 
				disabled>Patronage Subtype</option>

			<!-- Available Options -->
			<c:forEach items="${patronageSubtypeList}" 
				var="patronageSubtype">
				<option class="selected-option" 
					value="${patronageSubtype.getPatronageSubtypeId()}">
					<c:out value="${patronageSubtype.getPatronageSubtypeName()}" />
				</option>
			</c:forEach>
		</form:select>
	</form:form>

</body>
</html>