<%@ tag language="java" 
	pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 
	uri="http://www.springframework.org/tags/form" %>
<%@ attribute name="searchCriteria" 
	required="true"
	type="com.cimeliarchium.model.dao.SearchCriteria" %>
<%@ attribute name="monthList" 
	required="true"
	type="java.util.List" %>

<!DOCTYPE html>
<html>
<head></head>
<body>

	<!-- Month Drop-Down Select Menu -->
	<form:form id="monthForm"
		class="form-horizontal dropdown-select"
		modelAttribute="searchCriteria" >

		<form:select id="monthDropdown"
			class="dropdown-option form-control" 
			path="byId"
			disabled="true">

			<!-- Default Option -->
			<option selected
				value="" 
				class="default-option" 
				disabled>Month</option>

			<!-- Available Options - Ensure Moveable Dates not shown -->
			<c:forEach items="${monthList}" 
				var="month">
				<c:if test="${day.getMonthId() != 99}">
					<option class="selected-option" 
						value="${month.getMonthId()}">
						<c:out value="${month.getMonthName()}" />
					</option>
				</c:if>
			</c:forEach>
		</form:select>
	</form:form>

</body>
</html>