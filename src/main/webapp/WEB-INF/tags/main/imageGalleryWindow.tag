<%@ tag language="java" 
	pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" 
	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ attribute name="locationImageMap" 
	required="true"
	type="java.util.HashMap" %>

<!DOCTYPE html>
<html>
<head></head>
<body>

	<!-- Image Gallery Window -->
	<c:if test="${locationImageMap.size() > 0}">
		<div class="row justify-content-center align-items-center">
			<div id="imageGalleryWindow" class="mx-auto gallery-window hidden">
				<button id="imageGalleryExitBtn"
					class="img-gallery-exit-button" 
					type="button">&#10060;
				</button>
				<button id="imageGalleryPrevBtn"
					class="img-gallery-nav-button" 
					style="left:0;" 
					type="button">&#10096;
				</button>
				<button id="imageGalleryNextBtn"
					class="img-gallery-nav-button" 
					style="right:0;" 
					type="button">&#10097;
				</button>
				<div class="row justify-content-center align-items-center">
					<c:forEach var="locationImageEntry" 
						items="${locationImageMap}" 
						varStatus="i">
						<c:set var="locationImageLabel" 
							value="${locationImageEntry.getKey()}" />
						<c:set var="locationImageDetails" 
							value="${locationImageEntry.getValue()}" />
						<div id="imageGalleryTab${i.count}"
							class="mx-auto gallery-tab ${i.count > 1 ? 'hidden' : 'active'} ">
							<img class="img-icon-location" 
								src="${context}/${locationImageDetails.getLocationId()}.jpg"
								title="${locationImageLabel}">
							<p>
								<a target="_blank" 
									rel="noopener noreferrer"
									href="${context}/search/location?byId=${locationImageDetails.getLocationId()}">
									${locationImageLabel}
								</a>
							</p>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</c:if>

</body>
</html>