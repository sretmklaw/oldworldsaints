<%@ include file="_import.jsp" %>

<!DOCTYPE html>
<html lang="en" 
	class="background"
	style="background-image: url(${context}/default-background.png)">

<head>
	<%@ include file="_header.jsp" %>
	<link rel="icon" 
		type="image/svg"
		href="${context}/ALERT.svg" />
	<link rel="stylesheet" 
		type="text/css"
		href="${context}/login-style.css" />
</head>

<body>

	<tags:wrapper>

		<!-- Page Title -->
		<h1 id="title">
			<i class="initial">L</i>ogin
		</h1>

		<!-- User Form -->
		<form:form method="POST" 
			modelAttribute="user" 
			class="form-horizontal">

			<!-- Username Text Input -->
			<spring:bind path="username">
				<div class="form-group">
					<form:input type="text" 
						path="username" 
						class="form-control"
						placeholder="Username"></form:input>
				</div>
			</spring:bind>

			<!-- Password Text Input -->
			<spring:bind path="password">
				<div class="form-group">
					<form:input type="password" 
						path="password" 
						class="form-control"
						placeholder="Password"></form:input>
				</div>
			</spring:bind>

			<tags:pageAlerts/>

			<!-- Form Submission -->
			<input type="hidden" 
				name="${_csrf.parameterName}" 
				value="${_csrf.token}"/>
			<button type="submit" 
				class="btn btn-primary btn-loading">Submit</button>

		</form:form>

		<!-- Static Page Links -->
		<p>
			<a href="${context}/about" class="btn-loading">Learn more about this site</a>
			<br/>
			<a href="${context}/register" class="btn-loading">Create new account</a>
		</p>

		<!-- Images Cache -->
		<div id="cachedImages">
			<c:forEach var="img" 
				items="${cachedImagesList}" >
				<img id="${img.getId()}"
					class="hidden" 
					data-id="${img.getId()}"
					data-path="${img.getPath()}"
					data-filetype="${img.getFileType()}"
					src="${img.getPath()}">
			</c:forEach>
		</div>

	</tags:wrapper>
	<script src="${context}/cache-onload.js"></script>

</body>
</html>