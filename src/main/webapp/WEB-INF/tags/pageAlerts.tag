<%@ tag language="java" 
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head></head>
<body>

	<!-- Conditionally-Displayed Page Alert Fields -->
	<c:if test="${confirm != null}">
		<div id="confirmMessage" class="success">
			<c:out value="${confirm}" />
		</div>
	</c:if>
	<c:if test="${pageErrorMessage != null}">
		<div id="pageErrorMessage" class="alert">
			<c:out value="${pageErrorMessage}" />
		</div>
	</c:if>

</body>