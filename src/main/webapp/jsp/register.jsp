<%@ include file="_import.jsp" %>

<!DOCTYPE html>
<html lang="en" 
	class="background"
	style="background-image: url(${context}/default-background.png)">

<head>
	<%@ include file="_header.jsp" %>
	<link rel="stylesheet" 
		type="text/css"
		href="${context}/register-style.css" />
</head>

<body>

	<tags:wrapper>

		<!-- Page Title -->
		<c:set var="dropCap"
			value="${fn:toUpperCase(fn:substring(formHeader,0,1))}" />
		<c:set var="fh"
			value="${fn:substring(formHeader,1,fn:length(formHeader))}" />
		<h1 id="title">
			<i class="initial"><c:out value="${dropCap}" /></i><c:out value="${fh}" />
		</h1>

		<!-- Page Description -->
		<c:set var="username" 
			value="${(user != null) ? user.getUsername() : ''}" />
		<c:if test="${formHeader.equals('update')}">
			<p><b><c:out value="Hello, ${username}" /></b></p>
		</c:if>
		<div>
			Please fill in the fields below
			<a class="how-to-link"
				target="_blank" rel="noopener noreferrer" 
				href="/about?byText=how-to-use-register">
				<span>?</span>
			</a>
		</div>

		<!-- User Form -->
		<br/>
		<form:form method="POST" 
			modelAttribute="user"
			class="form-horizontal">

			<!-- Username Text Input (Hidden on Update) -->
			<spring:bind path="username">
				<div class="form-group ${status.error ? 'alert' : ''}
						${formHeader.equals('update')? 'hidden' : 'visible'}">
					<form:input id="usernameInput"
						type="text" 
						class="form-control"
						path="username" 
						placeholder="Username"
						value="${username}" />
				</div>
			</spring:bind>
			<c:set var="usernameError">
				<form:errors class="alert-non-padded" path="username"/>
			</c:set>
			<div id="usernameErrors" 
				class="errors ${(usernameError != null) ? 'form-group' : 'hidden'}">
				${usernameError}
			</div>

			<!-- Email Text Input -->
			<spring:bind path="email">
				<div class="form-group ${status.error ? 'alert' : ''}">
					<form:input id="emailInput"
						type="text" 
						class="form-control"
						path="email" 
						placeholder="Email address"
						value="${(user != null) ? user.getEmail() : ''}" />
				</div>
			</spring:bind>
			<c:set var="emailError">
				<form:errors class="alert-non-padded" path="email"/>
			</c:set>
			<div id="emailErrors" 
				class="errors ${(emailError != null) ? 'form-group' : 'hidden'}">
				${emailError}
			</div>

			<!-- Old Password Text Input (Hidden on Create) -->
			<spring:bind path="oldPassword">
				<div class="form-group ${status.error ? 'alert' : ''}
						${formHeader.equals('update') ? 'visible' : 'hidden'}">
					<form:input id="oldPasswordInput"
						type="password" 
						class="form-control"
						path="oldPassword"
						placeholder="Old password"></form:input>
				</div>
			</spring:bind>
			<c:set var="oldPasswordError">
				<form:errors class="alert-non-padded" path="oldPassword"/>
			</c:set>
			<div id="oldPasswordErrors" 
				class="errors ${(oldPasswordError != null) ? 'form-group' : 'hidden'}">
				${oldPasswordError}
			</div>

			<!-- Password Text Input -->
			<spring:bind path="password">
				<div class="form-group ${status.error ? 'alert' : ''}">
					<form:input id="passwordInput"
						type="password" 
						class="form-control"
						path="password"
						placeholder="${username != null ? 'New ' : ''}Password"></form:input>
				</div>
			</spring:bind>
			<c:set var="passwordError">
				<form:errors class="alert-non-padded" path="password"/>
			</c:set>
			<div id="passwordErrors" 
				class="errors ${(passwordError != null) ? 'form-group' : 'hidden'}">
				${passwordError}
			</div>

			<!-- Confirm Password Text Input -->
			<spring:bind path="passwordConfirm">
				<div class="form-group ${status.error ? 'alert' : ''}">
					<form:input id="passwordConfirmInput"
						type="password" 
						class="form-control"
						path="passwordConfirm" 
						placeholder="Confirm password"></form:input>
				</div>
			</spring:bind>
			<c:set var="passwordConfirmError">
				<form:errors class="alert-non-padded" path="passwordConfirm"/>
			</c:set>
			<div id="passwordConfirmErrors" 
				class="errors ${(passwordConfirmError != null) ? 'form-group' : 'hidden'}">
				${passwordConfirmError}
			</div>

			<!-- Calendar Drop-Down Menu -->
			<spring:bind path="calendarId">
				<div class="form-group ${status.error ? 'alert' : ''}">
					<form:select id="calendarDropdown" class="form-control"
						path="calendarId">
						<!-- Default Option -->
						<c:set var="selectedCalendarId"
							value="${(user != null) ? user.getCalendarId() : 1}" />
						<option ${selectedCalendarId == null ? 'selected' : ''}
							disabled value="" class="default-option">
							Select Calendar</option>
						<!-- Controller-Returned Options -->
						<c:forEach var="cal" items="${calendarList}">
							<option ${selectedCalendarId == cal.getCalendarId() 
									? 'selected' : null}
								value="${cal.getCalendarId()}">
								<c:out value="${cal.getCalendarName()}" />
							</option>
						</c:forEach>
					</form:select>
				</div>
			</spring:bind>
			<c:set var="calendarError">
				<form:errors class="alert-non-padded" path="calendarId"/>
			</c:set>
			<div id="calendarErrors" 
				class="errors ${(calendarError != null) ? 'form-group' : 'hidden'}">
				${calendarError}
			</div>

			<!-- Rite Drop-Down Menu -->
			<spring:bind path="riteId">
				<c:set var="selectedRiteId"
					value="${(user != null) ? user.getRiteId() : 1}" />
				<div class="form-group ${status.error ? 'alert' : ''}">
					<form:select id="riteDropdown" 
						class="form-control ${riteList.size() > 1 ? '' : 'hidden'}"
						path="riteId">
						<!-- Default Option -->
						<option ${selectedRiteId == null ? 'selected' : null} 
							disabled value="" 
							class="default-option">Select Rite</option>
						<!-- Controller-Returned Options Follow -->
						<c:if test="${riteList.size() > 1}">
							<c:forEach var="rite" items="${riteList}">
								<option ${selectedRiteId == rite.getRiteId() 
										? 'selected' : null}
									value="${rite.getRiteId()}">
									<c:out value="${rite.getRiteName()}" />
								</option>
							</c:forEach>
						</c:if>
					</form:select>
				</div>
			</spring:bind>
			<c:set var="riteError">
				<form:errors class="alert-non-padded" path="riteId"/>
			</c:set>
			<div id="riteErrors" 
				class="errors ${(riteError != null) ? 'form-group' : 'hidden'}">
				${riteError}
			</div>

			<!-- Nation Drop-Down Menu -->
			<spring:bind path="nationId">
				<div class="form-group ${status.error ? 'alert' : ''}">
					<form:select id="nationDropdown" class="form-control"
						path="nationId">
						<!-- Default Option -->
						<c:set var="selectedNationId"
							value="${(user != null) ? user.getNationId() : 10}" />
						<option ${selectedNationId == null ? 'selected' : ''}
							disabled value="" class="default-option">
							Select Nation</option>
						<!-- Controller-Returned Options -->
						<c:forEach var="nat" items="${nationList}">
							<option ${selectedNationId == nat.getNationId() 
									? 'selected' : null}
								value="${nat.getNationId()}">
								<c:out value="${nat.getNationName()}" />
							</option>
						</c:forEach>
					</form:select>
				</div>
			</spring:bind>
			<c:set var="nationError">
				<form:errors class="alert-non-padded" path="nationId"/>
			</c:set>
			<div id="nationErrors" 
				class="errors ${(nationError != null) ? 'form-group' : 'hidden'}">
				${nationError}
			</div>

			<!-- Region Drop-Down Menu -->
			<spring:bind path="regionId">
				<c:set var="selectedRegionId"
					value="${(user != null) ? user.getRegionId() : 1010}" />
				<div class="form-group ${status.error ? 'alert' : ''}">
					<form:select id="regionDropdown" 
						class="form-control 
							${regionList.size() > 1 ? '' : 'hidden'}"
						path="regionId">
						<!-- Default Option -->
						<option ${selectedRegionId == null ? 'selected' : ''} 
							disabled value="" 
							class="default-option">Select Region</option>
						<!-- Controller-Returned Options Follow -->
						<c:if test="${regionList.size() > 1}">
							<c:forEach var="region" items="${regionList}">
								<option ${selectedRegionId == region.getRegionId() 
										? 'selected' : null}
									value="${region.getRegionId()}">
									<c:out value="${region.getRegionName()}
											${region.getUtcOffsetFormatted()}" />
								</option>
							</c:forEach>
						</c:if>
					</form:select>
				</div>
			</spring:bind>
			<c:set var="regionError">
				<form:errors class="alert-non-padded" path="regionId"/>
			</c:set>
			<div id="regionErrors" 
				class="errors ${(regionError != null) ? 'form-group' : 'hidden'}">
				${regionError}
			</div>

			<tags:captcha captchaImage="${captchaImage}"
				isFormHidden="${confirm != null || pageErrorMessage != null}"
				isFormEnabled="${formEnabled != null || confirm != null || pageErrorMessage != null}" />

			<!-- Form Submission -->
			<p class="${notFound != null 
				? 'alert visible' : 'hidden'}">${notFoundMessage}</p>
			<input type="hidden" 
				name="${_csrf.parameterName}" 
				value="${_csrf.token}" />
			<button id="submitBtn" 
				class="btn btn-primary submit-action" 
				type="submit" 
				disabled>Submit</button>

		</form:form>

	</tags:wrapper>
	<script src="${context}/reset-dependent-inputs.js"></script>
	<script src="${context}/inputs-onchange-register.js"></script>
	<script src="${context}/nation-onchange-register.js"></script>
	<script src="${context}/calendar-onchange-register.js"></script>
	<script src="${context}/refresh-captcha-onclick.js"></script>

</body>
</html>
