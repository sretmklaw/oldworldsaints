<%@ include file="_import.jsp" %>

<!DOCTYPE html>
<html lang="en" 
	class="background"
	style="background-image: url(${context}/default-background.png)">

<head>
	<%@ include file="_header.jsp" %>
	<link rel="stylesheet" 
		type="text/css"
		href="${context}/search-style.css" />
	<link rel="stylesheet" 
		type="text/css"
		href="${context}/admin-style.css" />
</head>

<body>

	<tags:timeout />

	<tags:wrapper>
		<h1 id="title">
			<i class="initial">A</i>dministration
		</h1>
		<p>
			<a href="${context}/" class="btn-loading">Return to Current Day</a>
		</p>
		<br />

		<!-- User Details Section -->
		<div>
			<!-- User Details Header -->
			<c:set var="userSessionInfoListSize"
				value="${userSessionInfoList.size()}" />
			<button class="result-header collapsed" 
				data-toggle="collapse"
				data-target="#userDetailsSection">
				User Details
				<span id="userPreviewCount" 
					class="preview-count">
					<c:out value="${(userSessionInfoListSize != null) 
						? userSessionInfoListSize 
						: 0}"/>
				</span>
			</button>

			<!-- User Details Content -->
			<div id="userDetailsSection" 
				class="result-content collapse">
				<form:form id="userSessionInfoForm" 
					method="POST"
					modelAttribute="userSessionInfoForm">
					<!-- Pagination Control placeholder -->
					<div id="userStatusCallback" class="alert hidden"></div>
					<table id="sortableTableUserDetails">
						<thead>
							<tr>
								<th class="select-all">
									<input id="userSelectAllCheckbox"
										type="checkbox" 
										value="0" />
								</th>
								<th style="text-align:left;min-width:200px;">User</th>
								<th>Rite</th>
								<th>Nation</th>
								<th>Status</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="sessionInfo" 
								items="${userSessionInfoList}" >
								<c:set var="user" 
									value="${sessionInfo.getUser()}" />
								<c:set var="rite" 
									value="${sessionInfo.getRite()}" />
								<c:set var="nat" 
									value="${sessionInfo.getNation()}" />
								<c:set var="reg" 
									value="${sessionInfo.getRegion()}" />
								<tr>
									<!-- Check-Box Select by Key -->
									<td>
										<input class="user-checkbox" 
											type="checkbox" 
											value="${user.getUserId()}" 
											${user.getHasAdminRole() ? 'disabled' : ''}/>
									</td>
									<!-- User -->
									<td style="text-align: left">
										<div style="font-weight:bold;" class="
												${user.getHasAdminRole() ? 'isAdminUser' : ''}">
											<c:out value="${user.getUsername()}"/>
										</div>
										<div id="popup-${user.getUserId()}" 
											class="detail">
											<c:out value="${user.getEmail()}"/>
											<br/>
											<c:out value="Logins: ${user.getTotalLoginCount()}"/>
											<c:out value="Requests: ${user.getRequestCount()}"/>
											<br/>
											<c:out value="Last Seen: ${user.getLastLoginSuccessDateFormatted()}"/>
											<br/>
											<c:out value="${user.getLastLoginIp()}"/>
										</div>
									</td>
									<!-- Rite -->
									<td>
										<div class="detail">
											<c:out value="${rite.getRiteCode()}" />
										</div>
										<img class="img-icon-vertical" 
											src="${context}/${rite.getRiteCode()}.svg">
									</td>
									<!-- Nation -->
									<td>
										<div class="detail">
											<c:out value="${reg.getRegionCode()}" />
										</div>
										<img class="img-icon-horizontal flag" 
											src="${context}/${nat.getNationCode()}.svg">
									</td>
									<!-- Status -->
									<td>
										<c:set var="userStatusCode" 
											value="${user.getStatusCode()}" />
										<div id="userStatus${user.getUserId()}"
											class="user-status ${userStatusCode != null 
												&& userStatusCode.equals('GREEN') 
													? 'active' 
													: ''}">
											<img class="img-icon-square-small" 
												src="${context}/${user.getStatusCode()}.svg">
										</div>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</form:form>
			</div>
		</div>

		<!-- Bugfix Requests Section -->
		<div>
			<!-- Bugfix Requests Header -->
			<c:set var="bugfixRequestListSize"
				value="${bugfixRequestList.size()}" />
			<button class="result-header collapsed" 
				data-toggle="collapse" 
				data-target="#bugfixRequestsSection">
				Bugfix Requests
				<span id="bugfixRequestPreviewCount" 
					class="preview-count">
					<c:out value="${(bugfixRequestListSize != null) 
						? bugfixRequestListSize 
						: 0}"/>
				</span>
			</button>

			<!-- Bugfix Requests Content -->
			<div id="bugfixRequestsSection" 
				class="result-content collapse">
				<form:form id="bugfixRequestsForm" 
					method="POST"
					modelAttribute="bugfixRequestsForm">
					<!-- Pagination Control placeholder -->
					<div id="bugfixReqsStatusCallback" class="alert hidden"></div>
					<table id="sortableTableBugfixRequests">
						<thead>
							<tr>
								<th class="select-all">
									<input id="bugfixReqsSelectAllCheckbox"
										type="checkbox" 
										value="0" />
								</th>
								<th>Error</th>
								<th>Code</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="err" 
								items="${bugfixRequestList}" >
								<tr>
									<!-- Check-Box Select by Key -->
									<td>
										<input class="bugfix-reqs-checkbox" 
											type="checkbox" 
											value="${err.getRequestId()}" />
									</td>
									<!-- Error Details -->
									<td style="text-align: left">
										<c:set var="errPath" 
											value="${err.getErrorPath()}" />
										<c:set var="errPathRelative" 
											value="${err.getErrorPathRelative()}" />
										<c:set var="errPathParams" 
											value="${err.getErrorPathParams()}" />
										<a target="_blank" 
												rel="noopener noreferrer" 
												href="${context}/${errPath}">
											<c:choose>
												<c:when test="${errPathRelative != null}">
													<b><c:out value="/${errPathRelative}"/></b>
													<br/>
													<c:if test="${errPathParams.size() > 0}">
														<c:forEach var="pathParam" 
																items="${errPathParams}">
															<c:out value="${pathParam}"/>
															<br/>
														</c:forEach>
													</c:if>
												</c:when>
												<c:otherwise>
														<b><c:out value="/${errPath}" /></b>
												</c:otherwise>
											</c:choose>
										</a>
										<div class="detail">
											<c:out value="User: ${err.getUsername()}"/>
											<br/>
											<c:out value="Code: ${err.getErrorCode()}"/>
											<br/>
											<c:out value="Time: ${err.getRequestTimestamp()}"/>
										</div>
									</td>
									<!-- Error Code -->
									<td style="text-align: left">
										<b><c:out value="${fn:split(err.getErrorType(), ' ')[0]}"/></b>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</form:form>
			</div>
		</div>

		<!-- Feature Flag Section -->
		<div>
			<!-- Feature Flag Section Header -->
			<c:set var="featureFlagListSize"
				value="${featureFlagList.size()}" />
			<button class="result-header collapsed"
				data-toggle="collapse"
				data-target="#featureFlagSection">
				Feature Flags
				<span id="featureFlagPreviewCount" 
					class="preview-count">
					<c:out value="${(featureFlagListSize != null) 
						? featureFlagListSize 
						: 0}"/>
				</span>
			</button>

			<!-- Feature Flag Section Content -->
			<div id="featureFlagSection"
				class="result-content collapse">
				<form:form id="featureFlagForm" 
					method="POST"
					modelAttribute="featureFlag">
					<c:if test="${featureFlagUpdateResult != null}" >
						<div id="featureFlagAlert" class="alert">
							<c:out value="${featureFlagUpdateResult}" />
						</div>
					</c:if>
					<!-- Pagination Control placeholder -->
					<div id="featureFlagStatusCallback" class="alert hidden"></div>
					<table id="sortableTableFeatureFlags">
						<thead>
							<tr>
								<th class="select-all ">
									<input id="featureFlagSelectAllCheckbox"
										type="checkbox" 
										value="0" />
								</th>
								<th style="text-align: left">Functionality</th>
								<th style="width: 20%">Status</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="flag"
								items="${featureFlagList}" >
								<c:set var="flagId" 
									value="${flag.getFeatureFlagId()}" />
								<c:set var="flagVal" 
									value="${flag.getFeatureFlagValue()}" />
								<tr>
									<!-- Check-Box Select by Key -->
									<td>
										<input class="feature-flag-checkbox"
											type="checkbox" 
											value="${flagId}" />
									</td>
									<td class="detail">
										<div>
											<c:out value="${flag.getFeatureFlagName()}"/>
										</div>
										<c:if test="${flagId == 2}">
											<div style="padding-top: 7px;">
											<!-- Input for Request Limit -->
											Limit per User:
											<form:select class="flag-value input"
												path="featureFlagValue">
												<c:set var="currentLimit" 
													value="${flagVal != null 
															? flagVal.trim() 
															: '0'}" />
												<c:forEach var="limit" 
														begin="1" 
														end="10">
													<option ${limit.toString().equals(currentLimit) 
															? 'selected' 
															: ''}
														value="${limit}">
														<c:out value="${limit}" />
													</option>
												</c:forEach>
											</form:select>
											</div>
										</c:if>
										<c:if test="${flagId == 4}">
											<div style="padding-top: 7px;">
											<!-- Input for Notification Text -->
											<textarea class="flag-value input textarea"
												rows="4"><c:out value="${flagVal}"/></textarea>
											</div>
										</c:if>
									</td>
									<td>
										<div id="featureFlagStatus${flagId}" 
											class="flag-status ${flag.getIsFeatureFlagActive() 
												? 'active' 
												: ''}">
											<c:choose>
												<c:when test="${flag.getIsFeatureFlagActive()}">
													<img class="img-icon-square-small" 
														src="${context}/GREEN.svg">
												</c:when>
												<c:otherwise>
													<img class="img-icon-square-small" 
														src="${context}/RED.svg">
												</c:otherwise>
											</c:choose>
										</div>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</form:form>
			</div>
		</div>

	</tags:wrapper>

	<!-- Script Imports -->
	<script src="${context}/static/datatables/js/jquery.dataTables.min.js"></script> 
	<script src="${context}/admin-sortabletables-onload.js"></script>
	<script src="${context}/collapseall-onclick.js"></script>
	<script src="${context}/timeout-onload.js"></script>
	<script src="${context}/static/jquery-ui/jquery-ui.js"></script>
	<script src="${context}/tooltip-onmouseover.js"></script>

</body>
</html>