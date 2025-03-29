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
		href="${context}/about-style.css" />
</head>

<body>

	<!-- Page Contents Mask -->
	<div id="pageMask" class="page-mask"></div>

	<!-- Overview Map Window -->
	<div id="overviewMapWindow" class="background-map-window hidden">
		<button id="exitBtn"
			class="exit-button" 
			type="button">&#10060;
		</button>
		<img id="overviewMapZoomed"
			src="${context}/map-background-preview.png">
	</div>

	<!-- Celestial Map Window -->
	<div id="celestialMapWindow" class="celestial-map-window hidden">
		<button id="exitBtn"
			class="exit-button" 
			type="button">&#10060;
		</button>
		<img id="celestialMapZoomed"
			src="${context}/celestial-background-preview.png">
	</div>

	<tags:wrapper>

		<!-- Page Title -->
		<h1 id="title">
			<i class="initial">A</i>bout
		</h1>

		<!-- Static Page Links -->
		<p>
			<a href="${context}/" class="btn-loading">Home</a>
		</p>

		<!-- Paragraph 1 -->

		<div>
			<!-- Paragraph 1 Header -->
			<c:set var="heading1" value="${headingMap.get(1)}"/>
			<c:set var="p1a" value="${paragraphMap.get(101)}"/>
			<c:set var="p1b" value="${paragraphMap.get(102)}"/>
			<c:set var="p1c" value="${paragraphMap.get(103)}"/>
			<c:set var="p1d" value="${paragraphMap.get(104)}"/>
			<button id="${heading1.getCode()}-header"
				class="result-header 
					${activeParagraph == null 
						? 'active' 
						: activeParagraph == heading1.getCode() 
								? 'active redirected' 
								: 'collapsed'}"
				data-toggle="collapse" 
				data-target="#paragraph1">
				<c:out value="${heading1.getTitle()}"/></button>

			<!-- Paragraph 1 Content -->
			<div id="${heading1.getCode()}-content" 
				class="result-content collapse 
					${activeParagraph == null 
						? 'show active' 
						: activeParagraph == heading1.getCode() 
								? 'show active' 
								: ''}">

				<!-- Overview Map of Old World -->
				<div id="overviewMapMini">
					<div class="inner-shadow"></div>
					<img class="img-app-overview-map" 
						src="${context}/map-background-preview.png">
					<span class="zoom-icon">&#128270;</span>
				</div>

				<p class="drop-cap"><c:out value="${p1a.getContent()}" /></p>
				<p class="drop-cap"><c:out value="${p1b.getContent()}" /></p>

				<!-- Standard Location Types -->
				<p><b><c:out value="${p1c.getTitle()}" /></b></p>
				<p class="drop-cap"><c:out value="${p1c.getContent()}" /></p>
				<table id="standardLocationTypes">
					<c:forEach var="loc" 
						items="${standardLocationTypeList}" >
						<tr>
							<td class="detail"><c:out value="${loc.getName()}"/></td>
							<td>
								<img class="img-icon-square" 
									src="/${loc.getMonumentalEdificeCode()}.svg">
							</td>
							<td>
								<img class="img-icon-square" 
									src="/${loc.getMajorEdificeCode()}.svg">
							</td>
							<td>
								<img class="img-icon-square" 
									src="/${loc.getMinorEdificeCode()}.svg">
							</td>
						</tr>
					</c:forEach>
				</table>

				<!-- Special Location Types -->
				<p class="drop-cap"><c:out value="${p1d.getContent()}" /></p>
				<div id="specialLocationTypeInfo" class="row">
					<div id="specialLocationIcons" class="col-sm-7">
						<img id="specialLocationBackground"
							class="img-planisphere" 
							src="${context}/map-background-paris-preview.png">
						<img id="specialLocationGlowIcon" 
							src="${context}/SHIMMER.gif">
						<img id="specialLocationTypeIcon"
							src="${context}/GOT-MON-PAR.svg">
					</div>
					<div id="specialLocationDetails" class="col-sm-5">
						<table id="specialLocationTable"
							class="toggle-comparison">
							<c:forEach var="spec" 
								items="${specialLocationTypeList}" >
								<tr id="${spec.getMonumentalEdificeCode()}" 
									data-city="${spec.getCityName().toLowerCase()}"
									class="location-type-row 
										${spec.getRank() == 1 ? 'selected-row' : ''}">
									<td><img id="specialLocationNationIcon" 
										class="img-icon-horizontal flag"
										src="${context}/${spec.getNationCode()}.svg"></td>
									<td><c:out value="${spec.getCityName()} - ${spec.getName()}"/></td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>
			</div>
		</div>

		<!-- Paragraph 2 -->
		<div>
			<!-- Paragraph 2 Header -->
			<c:set var="heading2" value="${headingMap.get(2)}"/>
			<c:set var="p2a" value="${paragraphMap.get(201)}"/>
			<c:set var="p2b" value="${paragraphMap.get(202)}"/>
			<c:set var="p2c" value="${paragraphMap.get(203)}"/>
			<c:set var="p2d" value="${paragraphMap.get(204)}"/>
			<c:set var="p2e" value="${paragraphMap.get(205)}"/>
			<button id="${heading2.getCode()}-header"
				class="result-header 
					${activeParagraph == heading2.getCode() 
							? 'active redirected' 
							: 'collapsed'}" 
				data-toggle="collapse" 
				data-target="#paragraph2">
				<c:out value="${heading2.getTitle()}"/></button>

			<!-- Paragraph 2 Content -->
			<div id="${heading2.getCode()}-content" 
				class="result-content collapse 
					${activeParagraph == heading2.getCode() 
							? 'show active' 
							: ''}">

				<p class="drop-cap"><c:out value="${p2a.getContent()}" /></p>
				<p class="drop-cap"><c:out value="${p2b.getContent()}" /></p>

				<!-- Calendar Details -->
				<p><b><c:out value="${p2c.getTitle()}" /></b></p>
				<p class="drop-cap">
					<c:out value="${p2c.getContent()}" />
				</p>
				<div id="calendarRiteInfo" class="row">
					<table id="riteIcons">
						<tr>
							<c:forEach var="cr" 
								items="${calendarRiteList}" >
								<c:set var="cal" value="${cr.getCalendar()}"/>
								<c:set var="rt" value="${cr.getRite()}"/>
								<td id="${rt.getRiteCode()}"
									class="rite-cell ${rt.getRiteId() == 1 
											? 'selected-cell' 
											: 'unselected-cell'}">
									<img class="img-icon-cell" 
										src="${context}/${rt.getRiteCode()}.svg">
								</td>
							</c:forEach>
						</tr>
					</table>
					<table id="calendarRiteTable"
						class="toggle-comparison">
						<c:forEach var="crt" 
							items="${calendarRiteList}" >
							<c:set var="calt" value="${crt.getCalendar()}"/>
							<c:set var="rtt" value="${crt.getRite()}"/>
							<tr id="${rtt.getRiteCode()}" 
								class="rite-row 
									${rtt.getRiteId() == 1 ? 'selected-row' : ''}">
								<td><c:out value="${rtt.getRiteName()} - ${calt.getCalendarDetail()} ${rtt.getRiteDetail()}"/></td>
							</tr>
						</c:forEach>
					</table>
				</div>

				<!-- Overview Celestial Map -->
				<p><b><c:out value="${p2d.getTitle()}" /></b></p>
				<p class="drop-cap"><c:out value="${p2d.getContent()}" /></p>
				<div id="celestialMapMini">
					<div class="inner-shadow"></div>
					<img class="img-app-overview-map" 
						src="${context}/celestial-background-preview.png">
					<span class="zoom-icon">&#128270;</span>
				</div>

				<!-- Star Types -->
				<p class="drop-cap" style="margin-bottom: 0px;"><c:out value="${p2e.getContent()}" /></p>
				<table id="starTypes">
					<c:forEach var="st" 
						items="${starTypeList}" >
						<tr>
							<td>
								<img class="img-icon-square-large" 
									src="/${st.getCode()}.svg">
							</td>
							<td class="detail"><c:out value="${st.getName()}"/></td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>

		<!-- Paragraph 3 - How To Use This Site -->
		<div>
			<!-- Paragraph 3 Header -->
			<c:set var="heading3"
				value="${headingMap.get(3)}"/>
			<c:set var="isHowToParagraphActive" 
				value="${activeParagraph != null 
						&& activeParagraph.startsWith(heading3.getCode())}" />
			<button id="${heading3.getCode()}-header"
				class="result-header 
					${isHowToParagraphActive ? 'active redirected' : 'collapsed'}" 
				data-toggle="collapse" 
				data-target="#paragraph3">
				<c:out value="${heading3.getTitle()}"/></button>

			<!-- Paragraph 3 Content -->
			<div id="${heading3.getCode()}-content" 
				class="result-content collapse 
					${isHowToParagraphActive ? 'show active' : ''}">

				<!-- Paragraph 3 Tabs -->
				<div id="tabs" class="row nav nav-tabs nav-justified">
					<c:set var="activeHowToTabId" 
						value="${heading3.getActiveHowToUseSubHeading(activeParagraph)}" />
					<c:forEach var="howToTab" 
						items="${howToUseSubHeadingsMap.values()}">
						<c:set var="howToTabId" value="${howToTab.getRank()}" />
						<a id="howToTab${howToTabId}"
							class="nav-item nav-link tab col-xs-3 show visible 
								${howToTabId == activeHowToTabId ? 'active' : ''}" 
							href="#${howToTab.getCode()}" 
							role="tab" 
							data-toggle="tab">
							<c:out value="${howToTab.getTitle()}"/>
						</a>
					</c:forEach>
				</div>

				<!-- How To Tab Contents -->
				<div id="howToTabContents" class="row tab-content">

					<!-- Register Input Fields -->
					<c:set var="howToUseRegisterTab" 
						value="${howToUseSubHeadingsMap.get(31)}" />
					<div id="how-to-use-registration" 
						class="tab-pane fade 
							${howToUseRegisterTab.getRank() == activeHowToTabId 
									? 'active show' 
									: ''}">
						<p class="drop-cap">
							<c:out value="${paragraphMap.get(301).getContent()}" />
						</p>
						<div id="registrationInputFields"
							class="sample-input-fields">
							<c:forEach var="reg" 
								items="${registerInputFieldList}" >
								<span><c:out value="${reg.getFieldName()}"/>
									<c:if test="${reg.getIsDropdownField()}">
										<button disabled>&#x25BC;</button>
									</c:if>
								</span>
								<p><c:out value="${reg.getDetail()}"/></p>
							</c:forEach>
						</div>
					</div>

					<!-- Main Content -->
					<c:set var="howToUseMainTab" 
						value="${howToUseSubHeadingsMap.get(32)}" />
					<div id="how-to-use-main" 
						class="tab-pane fade 
							${howToUseMainTab.getRank() == activeHowToTabId 
									? 'active show' 
									: ''}">

						<!-- Celestial Date Info -->
						<p class="drop-cap">
							<c:out value="${paragraphMap.get(302).getContent()}" />
						</p>
						<div id="celestialDateInfo" class="row">
							<div class="row">
								<div id="zodiacSignIcons" class="col-sm-7">
									<img id="zodiacSignIcon"
										class="img-planisphere" 
										src="${context}/ZS-AQ.svg">
									<img id="zodiacSun" 
										src="${context}/SHIMMER.gif">
								</div>
								<div id="zodiacDetail" class="col-sm-5">
									<span>Centered on the star Hamal, named for 
										the celestial shepherd Tammuz in 
										ancient Near Eastern mythology.</span>
								</div>
							</div>
							<table id="zodiacTable"
								class="toggle-comparison">
								<tr>
									<th>Latin</th>
									<th>Greek</th>
									<th>Arabic</th>
									<th>Hebrew</th>
								</tr>
								<c:forEach var="zs" 
									items="${zodiacSignList}" >
									<tr id="${zs.getCode()}" 
										data-detail="${zs.getDetail()}"
										class="zodiac-sign-row 
											${zs.getRank() == 1 ? 'selected-row' : ''}">
										<td><c:out value="${zs.getLatinName()}"/></td>
										<td><c:out value="${zs.getGreekName()}"/></td>
										<td><c:out value="${zs.getArabicName()}"/></td>
										<td><c:out value="${zs.getHebrewName()}"/></td>
									</tr>
								</c:forEach>
							</table>
						</div>

						<!-- Hour Info -->
						<p class="drop-cap">
							<c:out value="${paragraphMap.get(303).getContent()}" />
						</p>
						<div id="hourInfo" class="row">
							<table id="hourIcons">
								<tr>
									<c:forEach var="hr" 
										items="${hourTypeList}" >
										<td id="${hr.getCode()}" 
											class="hour-cell ${hr.getRank() == 1 
													? 'selected-cell' 
													: 'unselected-cell'}">
											<img class="img-icon-cell" 
												src="${context}/${hr.getCode()}.svg">
										</td>
									</c:forEach>
								</tr>
							</table>
							<table id="hourTable"
								class="toggle-comparison">
								<tr>
									<th>Latin</th>
									<th>Greek</th>
									<th>Arabic</th>
									<th>Hebrew</th>
								</tr>
								<c:forEach var="hrt" 
									items="${hourTypeList}" >
									<tr id="${hrt.getCode()}" 
										class="hour-row 
											${hrt.getRank() == 1 ? 'selected-row' : ''}">
										<td><c:out value="${hrt.getLatinName()}"/></td>
										<td><c:out value="${hrt.getGreekName()}"/></td>
										<td><c:out value="${hrt.getArabicName()}"/></td>
										<td><c:out value="${hrt.getHebrewName()}"/></td>
									</tr>
								</c:forEach>
							</table>
						</div>
					</div>

					<!-- Search Content -->
					<c:set var="howToUseSearchTab" 
						value="${howToUseSubHeadingsMap.get(33)}" />
					<div id="how-to-use-search" 
						class="tab-pane fade 
							${howToUseSearchTab.getRank() == activeHowToTabId 
									? 'active show' 
									: ''}">

						<!-- Search Input Fields -->
						<p class="drop-cap">
							<c:out value="${paragraphMap.get(304).getContent()}" />
						</p>
						<div id="searchInputFields"
							class="sample-input-fields">
							<c:forEach var="srch" 
								items="${searchInputFieldList}" >
								<span><c:out value="${srch.getFieldName()}"/>
									<c:if test="${srch.getIsDropdownField()}">
										<button disabled>&#x25BC;</button>
									</c:if>
								</span>
								<p><c:out value="${srch.getDetail()}"/></p>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- Paragraph 4 -->
		<div>
			<!-- Paragraph 4 Header -->
			<c:set var="heading4" value="${headingMap.get(4)}"/>
			<button id="${heading4.getCode()}-header"
				class="result-header 
					${activeParagraph == heading4.getCode() 
							? 'active redirected' 
							: 'collapsed'}" 
				data-toggle="collapse" 
				data-target="#paragraph4">
				<c:out value="${heading4.getTitle()}"/></button>

			<!-- Paragraph 4 Content -->
			<div id="${heading4.getCode()}-content" 
				class="result-content collapse 
					${activeParagraph == heading4.getCode() 
							? 'show active' 
							: ''}">
				<c:forEach var="p4" 
					items="${whyWrittenThisWayParagraphList}" >
					<p><b><c:out value="${p4.getTitle()}"/></b></p>
					<p class="drop-cap">
						<c:out value="${p4.getContent()}" />
					</p>
				</c:forEach>
			</div>
		</div>

		<!-- Paragraph 5 -->
		<div>
			<!-- Paragraph 5 Header -->
			<c:set var="heading5" value="${headingMap.get(5)}"/>
			<button id="${heading5.getCode()}-header"
				class="result-header 
					${activeParagraph == heading5.getCode() 
							? 'active redirected' 
							: 'collapsed'}" 
				data-toggle="collapse" 
				data-target="#paragraph5">
				<c:out value="${heading5.getTitle()}"/></button>

			<!-- Paragraph 5 Content -->
			<div id="${heading5.getCode()}-content" 
				class="result-content collapse 
					${activeParagraph == heading5.getCode() 
							? 'show active' 
							: ''}">

				<c:set var="p5a" value="${paragraphMap.get(501)}"/>
				<p><b><c:out value="${p5a.getTitle()}"/></b></p>
				<p><c:out value="${p5a.getContent()}" /></p>
				<ul>
					<c:forEach var="printRef" 
						items="${printReferenceList}" >
						<li>
							<c:out value="${printRef}" />
						</li>
					</c:forEach>
				</ul>

				<c:set var="p5b" value="${paragraphMap.get(502)}"/>
				<p><b><c:out value="${p5b.getTitle()}"/></b></p>
				<p><c:out value="${p5b.getContent()}" /></p>
				<ul>
					<c:forEach var="webRef" 
						items="${webReferenceMap.entrySet()}" >
						<li>
							<a target="_blank" 
								rel="noopener noreferrer"
								href="${webRef.getValue()}">
							<c:out value="${webRef.getKey()}"/></a>
						</li>
					</c:forEach>
				</ul>

			</div>
		</div>

		<!-- Paragraph 6 -->
		<div>
			<!-- Paragraph 6 Header -->
			<c:set var="heading6" value="${headingMap.get(6)}"/>
			<button id="${heading6.getCode()}"
				class="result-header 
					${activeParagraph == heading6.getCode() 
							? 'active redirected' 
							: 'collapsed'}" 
				data-toggle="collapse" 
				data-target="#paragraph6">
				<c:out value="${heading6.getTitle()}"/></button>

			<!-- Paragraph 6 Content -->
			<div id="paragraph6" 
				class="result-content collapse 
					${activeParagraph == heading6.getCode() 
							? 'show active' 
							: ''}">
				<c:forEach var="p6" 
					items="${howToContributeParagraphList}" >
					<p><b><c:out value="${p6.getTitle()}"/></b></p>
					<p class="drop-cap">
						<c:out value="${p6.getContent()}" />
					</p>
				</c:forEach>
				<p><b>Thank you in advance for your contribution!</b></p>
			</div>
		</div>
	
		<!-- Version Release Notes -->
		<div id="versionReleaseNotes" class="release-notes">
			<p><b><c:out value="${versionReleaseNotes.getTitle()}" /></b></p>
			<p><c:out value="${versionReleaseNotes.getContent()}" /></p>
			<img id="aboutLogo" 
				src="${context}/LOGO.gif">
			<p><b>Copyright - Old World Saints 2024</b></p>
		</div>

	</tags:wrapper>
	<script src="${context}/collapseall-onclick.js"></script>
	<script src="${context}/about-onload.js"></script>

</body>
</html>