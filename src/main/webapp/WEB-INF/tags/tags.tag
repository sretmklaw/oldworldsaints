<%@ tag language="java" 
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="dto" 
	required="true" 
	type="com.cimeliarchium.model.dto.DayCommemorationDto" %>

<!DOCTYPE html>
<html>
<head></head>
<body>

	<!-- Formatted Label Content -->
	<c:set var="tagUrl" value="${context}/search/tag?byId=" />
	<c:set var="tagA" value="${dto.getTagA()}" />
	<c:if test="${tagA != null && tagA.getTagId() != null}">
		<span class="tag-sticky">
			<a href="<c:out value='${tagUrl}${tagA.getTagId()}' />" class="btn-loading">
				<c:out value="${tagA.getTagName()}" />
			</a>
		</span>
	</c:if>
	<c:set var="tagB" value="${dto.getTagB()}" />
	<c:if test="${tagB != null && tagB.getTagId() != null}">
		<span class="tag-sticky">
			<a href="<c:out value='${tagUrl}${tagB.getTagId()}' />" class="btn-loading">
				<c:out value="${tagB.getTagName()}" />
			</a>
		</span>
	</c:if>
	<c:set var="tagC" value="${dto.getTagC()}" />
	<c:if test="${tagC != null && tagC.getTagId() != null}">
		<span class="tag-sticky">
			<a href="<c:out value='${tagUrl}${tagC.getTagId()}' />" class="btn-loading">
				<c:out value="${tagC.getTagName()}" />
			</a>
		</span>
	</c:if>
	<c:set var="tagD" value="${dto.getTagD()}" />
	<c:if test="${tagD != null && tagD.getTagId() != null}">
		<span class="tag-sticky">
			<a href="<c:out value='${tagUrl}${tagD.getTagId()}' />" class="btn-loading">
				<c:out value="${tagD.getTagName()}" />
			</a>
		</span>
	</c:if>
	<c:set var="tagE" value="${dto.getTagE()}" />
	<c:if test="${tagE != null && tagE.getTagId() != null}">
		<span class="tag-sticky">
			<a href="<c:out value='${tagUrl}${tagE.getTagId()}' />" class="btn-loading">
				<c:out value="${tagE.getTagName()}" />
			</a>
		</span>
	</c:if>

</body>