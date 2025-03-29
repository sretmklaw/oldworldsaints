<%@ tag language="java" 
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head></head>
<body>

	<!-- Maintenance Notification -->
	<c:if test="${maintenanceNotification != null && userShowNotification}">
		<div id="maintenanceNotification" 
			class="notification-window">
			<c:out value="${maintenanceNotification}"/>
			<button id="acknowledgeMaintenanceNotification"
				class="exit-button" 
				type="button">&#10060;
			</button>
		</div>
	</c:if>

</body>