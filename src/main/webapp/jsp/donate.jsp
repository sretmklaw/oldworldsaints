<%@ include file="_import.jsp" %>

<!DOCTYPE html>
<html lang="en" 
	class="background"
	style="background-image: url(${context}/default-background.png)">

<head>
	<%@ include file="_header.jsp" %>
	<link rel="stylesheet" 
		type="text/css"
		href="${context}/style.css" />
	<link rel="stylesheet" 
		type="text/css"
		href="${context}/donate-style.css" />
</head>

<body>

	<c:set var="hasErrors" value="${pageErrorMessage != null}"/>

	<!-- Page Contents Mask -->
	<div id="pageMask" 
		class="page-mask ${paypalOrderAmount != null ? 'active' : ''}"></div>

	<!-- Overview Map Window -->
	<div id="confirmDialog" 
		class="confirm-dialog ${paypalOrderAmount != null ? 'active' : 'hidden'}">
		<img class="paypal-logo" src="${context}/PAYPAL.svg"/>
		<p>
			Submit payment of <b>USD <c:out value="${paypalOrderAmount}"/></b>?
		</p>
		<button id="confirmBtn" type="button">OK</button>
		<button id="cancelBtn" type="button">Cancel</button>
	</div>

	<tags:wrapper>

		<!-- Page Title -->
		<h1 id="title">
			<i class="initial">D</i>onate
		</h1>
		<br />

		<!-- Page Description -->
		<p>Help keep Old World Saints online.</p>

		<p>Any contribution is greatly appreciated!</p>

		<!-- Donation Form -->
		<div id="donationForm" class="content">
			<form:form method="POST" 
				modelAttribute="payPalOrder" 
				class="form-horizontal align-items-center"
				action="${context}/donate/process">

				<!-- USD Amount -->
				<div id="donateInputs" 
					class="input-group ${hasErrors ? '' : 'form-group'}">
					<div class="input-group-prepend">
						<span class="input-group-text">$</span>
					</div>
					<input id="amount"
						name="amount" 
						type="text" 
						class="form-control"
						placeholder="0.00"
						value="${paypalOrderAmount}"
						${hasErrors ? 'disabled' : ''}/>
				</div>

				<c:set var="amountError">
					<form:errors class="alert-non-padded" path="amount"/>
				</c:set>
				<div id="donateErrors"
					class="errors ${(amountError != null) ? 'form-group' : 'hidden'}">
					${amountError}
				</div>

				<!-- Form Submission -->
				<input name="${_csrf.parameterName}" 
					type="hidden" 
					value="${_csrf.token}"/>
				<button id="submitBtn" 
					class="btn btn-light mx-auto submit-action ${hasErrors ? 'hidden' : ''}"
					disabled>
					<img class="paypal-logo" src="${context}/PAYPAL.svg"/>
				</button>

			</form:form>
		</div>
		<br/>
		<!-- Conditionally Displayed fields dynamically updated by JS -->
		<div id="confirmMessage" class="success hidden">
			<c:out value="${confirm}" />
		</div>
		<div id="pageErrorMessage" class="alert hidden">
			<c:out value="${pageErrorMessage}" />
		</div>

		<!-- Static Page Links -->
		<p>
			<a href="${context}/main" class="btn-loading">Home</a>
		</p>

	</tags:wrapper>
	<script src="${context}/donate-onchange.js"></script>

</body>
</html>