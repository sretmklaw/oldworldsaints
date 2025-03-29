<%@ tag language="java" 
	pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 
	uri="http://www.springframework.org/tags/form" %>
<%@ attribute name="searchCriteria" 
	required="true"
	type="com.cimeliarchium.model.dao.SearchCriteria" %>
<%@ attribute name="tagList" 
	required="true"
	type="java.util.Collection" %>

<!DOCTYPE html>
<html>
<head></head>
<body>

	<!-- Century Drop-Down Select Menu -->
	<form:form id="tagForm"
		method="GET"
		action="${context}/search/tag" 
		class="form-horizontal dropdown-select"
		modelAttribute="searchCriteria" >

		<form:select id="tagDropdown"
			class="dropdown-option form-control" 
			path="byId">

			<!-- Default Option -->
			<option selected
				value="" 
				class="default-option" 
				disabled>Tag</option>

			<!-- Available Options are populated from the session attribute -->
			<c:forEach items="${tagList}" 
				var="tag">
				<option class="selected-option" 
					value="${tag.getTagId()}">
					<c:out value="${tag.getTagName()}" />
				</option>
			</c:forEach>
		</form:select>

		<!-- Submit Button -->
		<button disabled
			id="tagSearchBtn"
			class="search btn btn-secondary btn-loading">
			&#128270;
		</button>
	</form:form>

</body>
</html>