<%@ include file="_import.jsp" %>

<!DOCTYPE html>
<html lang="en" 
	class="background"
	style="background-image: url(${context}/default-background.png)">

<head>
	<!-- Common Header Meta Attributes -->
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" 
		content="IE=edge" />
	<meta http-equiv="cache-control" 
		content="public" />
	<meta http-equiv="pragma" 
		content="public" />
	<meta http-equiv="expires" 
		content="86400" />
	<meta name="viewport"
		content="width=device-width, initial-scale=1, maximum-scale=1" />

	<!-- Common Header Links -->
	<link rel="icon" 
		type="image/svg"
		href="${context}/ALERT.svg" />
	<link rel="stylesheet" 
		type="text/css"
		href="${context}/static/bootstrap/css/bootstrap.min.css" />
	<link rel="stylesheet" 
		type="text/css"
		href="${context}/style.css" />
	<link rel="stylesheet" 
		type="text/css"
		href="${context}/error-style.css" />
</head>

<body>

	<tags:wrapper>

		<!-- Page Title -->
		<h1 id="title">
			<i class="initial">E</i>rror
		</h1>
		<br />

		<!-- Page Description -->
		<p>The application has encountered a problem while attempting 
			to process your request.</p>
		<p>You may need to open a new browser session or re-authenticate 
			if you are using a book-marked link.</p>
		<c:if test="${captchaEnabled}">
			<p>If you wish to flag this error to Technical Support, 
				please click the button below to archive the following info:</p>
			<br />
		</c:if>

		<!-- Error Form -->
		<div class="content">
			<form:form method="POST" 
				modelAttribute="request" 
				class="form-horizontal align-items-center"
				action="${context}/error">
				<table>

					<!-- Error Code -->
					<c:if test="${request.getErrorCode() != null 
							&& !request.getErrorCode().isEmpty()}">
						<tr>
							<th>Code</th>
							<td>
								<div class="form-group">
									<input name="errorCode" 
										type="text" 
										class="form-control read-only-input"
										readonly="readonly"
										value="${request.getErrorCode()}" />
								</div>
							</td>
						</tr>
					</c:if>

					<!-- Error Type -->
					<tr>
						<th>Message</th>
						<td>
							<div class="form-group">
								<input name="errorType"
									type="text" 
									class="form-control read-only-input"
									readonly="readonly"
									value="${request.getErrorType()}" />
							</div>
						</td>
					</tr>

					<!-- Error Path -->
					<c:set var="errorPath" 
						value="${request.getErrorPath()}" />
					<tr>
						<th>Path</th>
						<td>
							<div class="form-group">
								<input name="errorPath"
									type="text" 
									class="form-control read-only-input"
									readonly="readonly"
									value="${errorPath}" />
							</div>
						</td>
					</tr>

					<!-- Error Origin -->
					<tr>
						<th>Origin</th>
						<td>
							<div class="form-group">
								<input name="errorIp"
									type="text" 
									class="form-control read-only-input"
									readonly="readonly"
									value="${request.getErrorIp()}" />
							</div>
						</td>
					</tr>

					<!-- Error Timestamp -->
					<tr>
						<th>Time</th>
						<td>
							<div class="form-group">
								<input name="requestTimestamp"
									type="text" 
									class="form-control read-only-input"
									readonly="readonly" 
									value="${request.getRequestTimestamp()}" />
							</div>
						</td>
					</tr>
				</table>

				<c:if test="${captchaEnabled}">
					<tags:captcha captchaImage="${captchaImage}" 
						isFormHidden="${confirm != null || pageErrorMessage != null}"
						isFormEnabled="${formEnabled != null || confirm != null || pageErrorMessage != null}"/>
				</c:if>

				<!-- Form Submission -->
				<input name="requestId" 
					type="hidden" 
					value="${request.getRequestId()}"/>
				<input name="userId" 
					type="hidden" 
					value="${request.getUserId()}"/>
				<input name="username" 
					type="hidden" 
					value="${request.getUsername()}"/>
				<input name="requestTypeId" 
					type="hidden" 
					value="${request.getRequestTypeId()}"/>
				<input name="requestStatusCode" 
					type="hidden" 
					value="${request.getRequestStatusCode()}"/>
				<input name="${_csrf.parameterName}" 
					type="hidden" 
					value="${_csrf.token}"/>
				<button id="submitBtn" 
					class="btn btn-secondary mx-auto submit-action
						${captchaEnabled ? 'visible' : 'hidden'}"
					disabled>Submit Error Report
				</button>

			</form:form>
		</div>
		<br />
		<tags:pageAlerts/>

		<!-- Static Page Links -->
		<c:set var="redirectPath" 
			value="${errorPath != null && errorPath.equals('login') ? 'logout' : ''}" />
		<p>
			<a href="${context}/${redirectPath}" class="btn-loading">Home</a>
		</p>

	</tags:wrapper>
	<script src="${context}/refresh-captcha-onclick.js"></script>

</body>
</html>