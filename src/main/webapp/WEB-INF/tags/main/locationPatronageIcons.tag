<%@ tag language="java" 
	pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" 
	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ attribute name="locationPatronageList" 
	required="true"
	type="java.util.ArrayList" %>
<%@ attribute name="locationImageMap" 
	required="true"
	type="java.util.HashMap" %>

<!DOCTYPE html>
<html>
<head></head>
<body>

	<!-- Location Patronage Icons -->
	<div id="locationPatronageIcons" style="margin-left:30px;">
		<c:forEach var="lpat" items="${locationPatronageList}">
			<a target="_blank" 
				rel="noopener noreferrer"
				href="${context}/search/patronage?byId=${lpat.getPatronageId()}">
				<img id="patronageImage${lpat.getPatronageId()}" 
					class="img-icon-patronage-small" 
					src="${context}/${lpat.getPatronageId()}.png" 
					title="${lpat.getPatronageName()}">
			</a>
		</c:forEach>
		<c:if test="${locationImageMap.size() > 0}">
			<button id="imageGalleryPopupBtn">
				<img id="locationImageGallery" 
					class="img-icon-patronage-small" 
					style="margin-left: -40px;"
					src="${context}/gallery.png" 
					title="View Locations">
			</button>
		</c:if>
	</div>


</body>
</html>