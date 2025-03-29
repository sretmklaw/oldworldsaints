<%@ tag language="java" 
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 
	uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" 
	uri="http://www.springframework.org/tags"%>
<%@ attribute name="captchaImage" 
	required="true" 
	type="java.lang.String" %>
<%@ attribute name="isFormHidden" 
	required="true" 
	type="java.lang.Boolean" %>
<%@ attribute name="isFormEnabled" 
	required="true" 
	type="java.lang.Boolean" %>

<!DOCTYPE html>
<html>
<head></head>
<body>

	<!-- CAPTCHA -->
	<div id="captcha" class="${isFormHidden ? 'hidden' : ''}">
		<div>
			<img id="captchaIcon" 
				src="data:image/jpeg;base64,${captchaImage}" />
			<button id="refreshBtn">
				<img id="refreshIcon" 
					class="img-icon-square-small" 
					src="${context}/REFRESH.svg">
			</button>
		</div>
		<spring:bind path="captcha">
			<c:set var="captchaError">
				<form:errors class="alert-non-padded" path="captcha"/>
			</c:set>
			<div class="captcha-text-input form-group">
				<form:input id="captchaInput" 
					disabled="${!isFormEnabled && captchaError != null}"
					type="text" 
					class="form-control"
					path="captcha"
					placeholder="Enter above text to submit"
					value=""></form:input>
			</div>
		</spring:bind>
		<div id="captchaErrors" 
			class="errors ${(captchaError != null) ? 'form-group' : 'hidden'}">
			${captchaError}
		</div>
	</div>

</body>