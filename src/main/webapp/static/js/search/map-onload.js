const BODY = 'body';
const CLICK_TRIGGER = 'click';
const MAX_WINDOW = 'maximized';
const OPTIONS = 'options';
const SHOW_RESULTS = 'Show Results';
const OPENING_MAP = 'Opening Map...';
const SHOW_MAP = 'Show Map';
const SEARCH_ID = '#search';
const TOGGLE_ID = '#toggle';
const LOADING_ID = '#loading';
const MAP_ID = '#map';
const MAP_INSET = '#mapInset';
const THIS_LOCATION_ID = '#thisLocation';
const PREV_LOCATION_ID = '#prevLocation';
const NEXT_LOCATION_ID = '#nextLocation';
const THIS_PATRONAGE_ID = '#thisPatronage';
const THIS_STAR_ID = '#thisStar';
const PREV_STAR_ID = '#prevStar';
const NEXT_STAR_ID = '#nextStar';
const USER_RITE_ID = '#userRite';
const SHOW_ALL_ID = '#showAll';
const CONTROLLER_FIELDS = ' ' + USER_RITE_ID + ', ' + MAP_INSET + ', ' + THIS_PATRONAGE_ID + ', '
				+ PREV_STAR_ID + ', ' + THIS_STAR_ID + ', ' + NEXT_STAR_ID + ', '
				+ PREV_LOCATION_ID + ', ' + THIS_LOCATION_ID + ', ' + NEXT_LOCATION_ID;

// Define Map data as global variables to access from inside function calls
var map, mapBounds, mapCenter, minZoom, maxZoom, 
locationLayer, locationHighlightLayer, 
patronageLayer, patronageHighlightLayer,
starLayer, starHighlightLayer,
routeLayer, routeHighlightLayer,
asterismLayer, asterismHighlightLayer;

$(document).ready(function() {
	// Default Icon constructor, can be overridden during population
	var LeafIcon = L.Icon.extend({
		options : {
			// Default icon image to minor Levantine edifice
			'iconUrl' : 'LEV-MIN.png',
			// Default icon size
			'iconSize' : [50, 50],
			// Default popup offset
			'popupAnchor' : [0, -20],
			// Default zoom granularity
			'snapZoom' : 2
		}
	});

	// Default PolyLine constructor, can be overridden during population
	var myPolyLine = L.Polyline.extend({
		options : {
			'locationIds' : ''
		}
	});

	/**
	 * Map Behavior
	 */
	var thisLocation = $(THIS_LOCATION_ID).data(OPTIONS);
	var thisPatronage = $(THIS_PATRONAGE_ID).data(OPTIONS);
	var thisStar = $(THIS_STAR_ID).data(OPTIONS);

	var loadTarget = (thisStar.starId != null) 
		? thisStar : (thisLocation.locationId != null) 
			? thisLocation : thisPatronage;
	var mapInset = $(MAP_INSET).data(OPTIONS);
	var insetIdParam, insetNameParam;

	/**
	 * Step 1: Define base map settings
	 */
	// Star data populates default Star Map settings when specified
	if (thisStar.starId != null) {
		insetIdParam = '';
		mapBounds = [[-133,-189],[133,189]];
		mapCenter = L.latLng(
				parseFloat(loadTarget.pointY),
				parseFloat(loadTarget.pointX));
		minZoom = 2.0;
		maxZoom = 4.0;
	}
	// Inset data evaluates whether to display Inset map from controller params
	else if (mapInset.insetId != null) {
		insetIdParam = '?withInset=' + mapInset.insetId;
		insetNameParam = '?withInset=' + mapInset.insetName;
		minZoom = mapInset.minZoom;
		maxZoom = mapInset.maxZoom;
		var mapBound1 = L.latLng(
				parseFloat(mapInset.insetBoundsY1),
				parseFloat(mapInset.insetBoundsX1)
		);
		var mapBound2 = L.latLng(
				parseFloat(mapInset.insetBoundsY2),
				parseFloat(mapInset.insetBoundsX2)
		);
		mapBounds = [mapBound1,mapBound2];
		mapCenter = L.latLng(
				parseFloat(loadTarget.pointX),
				parseFloat(loadTarget.pointY));
	} 
	// Otherwise default Location Map settings applied
	else {
		insetIdParam = '';
		mapBounds = [[-20,-30],[70,150]];
		mapCenter = L.latLng(
				parseFloat(loadTarget.pointX),
				parseFloat(loadTarget.pointY));
		minZoom = 4.0;
		maxZoom = 6.0;
	}
	/** 
	 * Step 2: Apply base map settings
	 */
	map = new L.Map('map', {
		// Center map on Jerusalem when loading page
		'center' : mapCenter, 
		// Set maximum Map Bounds to predefined coordinates
		'maxBounds' : mapBounds,
		// Prevent pan beyond map bounds
		'maxBoundsViscosity': 1.0,
		// Define minimum and maximum Zoom levels
		'minZoom' : minZoom, 
		'maxZoom' : maxZoom,
		// Override individual DOM element per marker
		'preferCanvas' : true,
		// Performance improvements
		'updateWhenZooming' : false,
		'updateWhenIdle' : false,
		// Disable default zoom controller
		'zoomControl': false,
		// Prevent pop-up auto-closure on mobile devices
		'tap': false
	});
	L_DISABLE_3D = true;

	/**
	 * Step 3: Define base map bounds and coordinate system
	 */
	map.fitBounds(mapBounds);
	map.setView(mapCenter, maxZoom);
	// Conditionally display celestial base map image if Star search type specified
	if (thisStar.starId != null) {
		L.imageOverlay('/map-background?withInset=celestial', mapBounds).addTo(map);
	}
	// Otherwise if Inset is specified, import map background image fit to map bounds
	else if (insetNameParam != null) {
		L.imageOverlay('/map-background' + insetNameParam, mapBounds).addTo(map);
	} 
	// Default world map uses title layers from classpath to optimize page load times
	else {
		map.addLayer(L.tileLayer('/{z}-{x}-{y}.png'));
	}
	// Current display options from Controller-mapped user settings
	var displayRiteId = $(USER_RITE_ID).data(OPTIONS).displayRiteId;

	/**
	 * Step 4: Populate map points from controller-returned values
	 */
	// Populate Star map points
	if (thisStar.starId != null) {
		starLayer = L.featureGroup().addTo(map);
		layerTarget = starLayer;
		starHighlightLayer = L.featureGroup().addTo(map);
		$.getJSON('/star-map-points', function (markers) {
			$.each(markers, function(i) {
				// Star Marker Popup
				var popupContent;
				if (markers[i].starNameAlt != null) {
					popupContent = '<p>' 
						+ markers[i].starName + '<br/>'
						+ '<i class="popup-link">' + markers[i].starNameAlt + '</i></p>';
				} else {
					popupContent = markers[i].starName;
				}
				// Star Marker Highlight
				L.marker([markers[i].pointY, markers[i].pointX], {
						'starId' : 'bkg_' + markers[i].starId
					})
					.setIcon(new LeafIcon({
						'iconUrl' : '../SHIMMER.gif',
						'iconSize' : [0, 0] // Minimized on load
					}))
					.addTo(starHighlightLayer);
				// Star Marker
				L.marker([markers[i].pointY, markers[i].pointX], { 
						'title' : markers[i].starName,
						'starId' : markers[i].starId,
						'allCommemorationCount' : markers[i].allCommemorationCount,
						'catholicCommemorationCount' : markers[i].catholicCommemorationCount,
						'protestantCommemorationCount' : markers[i].protestantCommemorationCount,
						'orthodoxCommemorationCount' : markers[i].orthodoxCommemorationCount,
						'sunniCommemorationCount' : markers[i].sunniCommemorationCount,
						'shiiteCommemorationCount' : markers[i].shiiteCommemorationCount,
						'jewishCommemorationCount' : markers[i].jewishCommemorationCount
					})
					// Apply custom icon corresponding to Star type
					.setIcon(new LeafIcon({
						'iconUrl' : window.location.origin + '/' 
								+ markers[i].starTypeCode + '.svg',
						'iconSize' : [80, 80]
					}))
					.setOpacity(getMarkerOpacityForRite(markers[i], displayRiteId))
					// Bind link within Popup, where an Inset map is available.
					// Otherwise, just bind a plain text label.
					.bindPopup(popupContent)
					// Define center-and-zoom on-click behavior
					.on(CLICK_TRIGGER, clickZoomToStar)
					// Finally, add marker to marker layer
					.addTo(starLayer);
			});
		});
		// Populate map asterisms from Controller-mapped Constellations
		asterismLayer = L.featureGroup().addTo(map);
		asterismLayer.setZIndex(50);
		asterismHighlightLayer = L.featureGroup().addTo(map);
		asterismHighlightLayer.setZIndex(40);
		$.getJSON('/map-asterisms', function (asterisms) {
			$.each(asterisms, function(i) {
				if (asterisms[i].constellationId != null) {
					var asterismPoints = []; // Asterism Points as array of LatLng
					$.each(asterisms[i].asterismPoints.split(';'), function() {
						var astPts = this.split(',');
						asterismPoints.push(L.latLng(
								parseFloat(astPts[1]), // Latitude
								parseFloat(astPts[0]))); // Longitude
					});
					// Add hidden strokes as asterism background highlighting
					var asterismBkgOuter = new myPolyLine(asterismPoints, {
						'color': '#ffff00', 
						'lineCap': 'round',
						'opacity': 0.5,
						'weight': 0,
						'starIds' : asterisms[i].asterismStarIds
					});
					asterismBkgOuter.addTo(asterismHighlightLayer);
					var asterismBkgInner = new myPolyLine(asterismPoints, {
						'color': '#ffffff', 
						'lineCap': 'round',
						'opacity': 0.8,
						'weight': 0,
						'starIds' : asterisms[i].asterismStarIds
					});
					asterismBkgInner.addTo(asterismHighlightLayer);
					// Add visible line as asterism path, always start transparent
					var asterismLine = new L.Polyline(asterismPoints, {
						'color' : (asterisms[i].isZodiacSign) ? '#8b0000' : '#000000',
						'lineCap' : 'round',
						'weight' : 6,
						'opacity' : 1
					});
					asterismLine.addTo(asterismLayer);
					var asterismPopupContent = '<p>' + asterisms[i].constellationNameLatin + '</p>';
					asterismLine.bindPopup(asterismPopupContent);
					asterismLine.on(CLICK_TRIGGER, function () {
						// Reset highlight and opacity for asterism.
						resetPreviousAsterismsAndMarkers();
						// Add asterism highlighting.
						asterismBkgOuter.setStyle({'weight':18});
						asterismBkgInner.setStyle({'weight':13});
						// Also set opacity and add highlighting to each asterism star.
						$.each(asterisms[i].asterismStarIds.split(','), function() {
							var asterismStarId = new Number(this);
							var asterismStar = findMarkerByStarId(starLayer, asterismStarId);
							asterismStar.setOpacity(1); // Make visible
							resizeStarMarkerBackgroundIcon(asterismStarId, 85); // Add highlight
						});
						// Update Star controller attributes, so that we can
						// correctly reload search results from brightest star on toggle.
						reloadStarControllerFields(asterisms[i].starIdPrimary);
					});
				}
			});
		});
		// On click anywhere other than a marker, reset highlighting and opacity.
		$(map).on(CLICK_TRIGGER, function() {
			resetPreviousAsterismsAndMarkers();
		});
	}
	// Populate Patronage map points
	else if (thisPatronage.patronageId != null) {
		patronageLayer = L.featureGroup().addTo(map);
		layerTarget = patronageLayer;
		patronageHighlightLayer = L.featureGroup().addTo(map);
		$.getJSON('/patronage-map-points' + insetIdParam, function (markers) {
			$.each(markers, function(i) {
				// Patronage Marker Popup
				var popupContent = markers[i].patronageName;
				// Patronage Marker Highlight
				L.marker([markers[i].pointX, markers[i].pointY], {
						'patronageId' : 'bkg_' + markers[i].patronageId
					})
					.setIcon(new LeafIcon({
						'iconUrl' : '../SHIMMER.gif',
						'iconSize' : [0, 0] // Minimized on load
					}))
					.addTo(patronageHighlightLayer);
				// Patronage Marker
				L.marker([markers[i].pointX, markers[i].pointY], { 
						'title' : markers[i].patronageName,
						'patronageId' : markers[i].patronageId,
						'allCommemorationCount' : markers[i].allCommemorationCount,
						'catholicCommemorationCount' : markers[i].catholicCommemorationCount,
						'protestantCommemorationCount' : markers[i].protestantCommemorationCount,
						'orthodoxCommemorationCount' : markers[i].orthodoxCommemorationCount,
						'sunniCommemorationCount' : markers[i].sunniCommemorationCount,
						'shiiteCommemorationCount' : markers[i].shiiteCommemorationCount,
						'jewishCommemorationCount' : markers[i].jewishCommemorationCount
					})
					// Apply custom icon corresponding to Patronage image
					.setIcon(new LeafIcon({
						'iconUrl' : window.location.origin + '/' + markers[i].patronageId + '-sm.png',
						'iconSize' : [35, 50]
					}))
					.setOpacity(getMarkerOpacityForRite(markers[i], displayRiteId))
					// Bind link within Popup, where an Inset map is available.
					// Otherwise, just bind a plain text label.
					.bindPopup(popupContent)
					// Define center-and-zoom on-click behavior
					.on(CLICK_TRIGGER, clickZoomToPatronage)
					// Finally, add marker to marker layer
					.addTo(patronageLayer);
			});
		});
		// On click anywhere other than a marker, reset highlighting and opacity.
		$(map).on(CLICK_TRIGGER, function() {
			resetPreviousMarker();
		});
	} 
	// Populate Location map points
	else {
		locationLayer = L.featureGroup().addTo(map);
		layerTarget = locationLayer;
		locationHighlightLayer = L.featureGroup().addTo(map);
		$.getJSON('/location-map-points' + insetIdParam, function (markers) {
			$.each(markers, function(i) {
				// Location Marker Popup
				var popupContent;
				if (markers[i].altLabelName != null) {
					popupContent = '<p>' 
						+ markers[i].labelName + '<br/>'
						+ 	'<a class="popup-link btn-loading" ' 
						+ 			'href="/search/location?' 
						+ 				'byId=' + markers[i].altInsetId + '">'
						+ 		'&darr; ' + markers[i].altLabelName 
						+ 	'</a>' 
						+ '</p>';
				} else {
					popupContent = markers[i].labelName;
				}
				// Location Marker Highlight
				L.marker([markers[i].pointX, markers[i].pointY], {
						'locationId' : 'bkg_' + markers[i].locationId
					})
					.setIcon(new LeafIcon({
						'iconUrl' : '../SHIMMER.gif',
						'iconSize' : [0, 0] // Minimized on load
					}))
					.addTo(locationHighlightLayer);
				// Location Marker
				L.marker([markers[i].pointX, markers[i].pointY], { 
						'title' : markers[i].labelName,
						'locationId' : markers[i].locationId,
						'allCommemorationCount' : markers[i].allCommemorationCount,
						'catholicCommemorationCount' : markers[i].catholicCommemorationCount,
						'protestantCommemorationCount' : markers[i].protestantCommemorationCount,
						'orthodoxCommemorationCount' : markers[i].orthodoxCommemorationCount,
						'sunniCommemorationCount' : markers[i].sunniCommemorationCount,
						'shiiteCommemorationCount' : markers[i].shiiteCommemorationCount,
						'jewishCommemorationCount' : markers[i].jewishCommemorationCount
					})
					// Apply custom icon corresponding to Location type
					.setIcon(new LeafIcon({
						'iconUrl' : window.location.origin + '/' 
								+ markers[i].locationTypeCode + '.svg',
					}))
					.setOpacity(getMarkerOpacityForRite(markers[i], displayRiteId))
					// Bind link within Popup, where an Inset map is available.
					// Otherwise, just bind a plain text label.
					.bindPopup(popupContent)
					// Define center-and-zoom on-click behavior
					.on(CLICK_TRIGGER, clickZoomToLocation)
					// Finally, add marker to marker layer
					.addTo(locationLayer);
			});
		});
		// Populate map pilgrimage routes from Controller-mapped Routes
		routeLayer = L.featureGroup().addTo(map);
		routeLayer.setZIndex(50);
		routeHighlightLayer = L.featureGroup().addTo(map);
		routeHighlightLayer.setZIndex(40);
		$.getJSON('/map-routes' + insetIdParam, function (routes) {
			$.each(routes, function(i) {
				if (routes[i].routeId != null) {
					var routePoints = []; // Route Points as array of LatLng
					$.each(routes[i].routePoints.split(';'), function() {
						var rtPts = this.split(',');
						routePoints.push(L.latLng(
								parseFloat(rtPts[0]), // Latitude
								parseFloat(rtPts[1]))); // Longitude
					});
					// Add hidden strokes as route background highlighting
					var routeBkgOuter = new myPolyLine(routePoints, {
						'color': '#ffff00', 
						'lineCap': 'round',
						'opacity': 0.5,
						'weight': 0,
						'locationIds' : routes[i].locationIds
					});
					routeBkgOuter.addTo(routeHighlightLayer);
					var routeBkgInner = new myPolyLine(routePoints, {
						'color': '#ffffff', 
						'lineCap': 'round',
						'opacity': 0.8,
						'weight': 0,
						'locationIds' : routes[i].locationIds
					});
					routeBkgInner.addTo(routeHighlightLayer);
					// Add visible line as route path, always start transparent
					var routeLine = new L.Polyline(routePoints, {
						'color' : '#ff0000',
						'lineCap' : 'round',
						'dashArray': '1, 10',
						'dashOffset': '10',
						'weight' : 6,
						'opacity' : 0.33
					});
					routeLine.addTo(routeLayer);
					var routePopupContent = '<p>' 
						+ routes[i].routeName + '</br>'
						+ '<span class="popup-link btn-loading">' + routes[i].startLocationLabelName
						+ ' to ' + routes[i].endLocationLabelName
						+ '</span></p>';
					routeLine.bindPopup(routePopupContent);
					routeLine.on(CLICK_TRIGGER, function () {
						// Reset highlight and opacity for route.
						resetPreviousRoutesAndMarkers();
						this.setStyle({'opacity':1});
						// Add route highlighting.
						routeBkgOuter.setStyle({'weight':18});
						routeBkgInner.setStyle({'weight':13});
						// Also set opacity and add highlighting to each route location.
						$.each(routes[i].locationIds.split(','), function() {
							var routeLocationId = new Number(this);
							var routeLocation = findMarkerByLocationId(locationLayer, routeLocationId);
							routeLocation.setOpacity(1); // Make visible
							resizeLocationMarkerBackgroundIcon(routeLocationId, 85); // Add highlight
						});
						// Update Location controller attributes, so that we can
						// correctly reload search results from end location on toggle.
						reloadLocationControllerFields(routes[i].endLocationId);
					});
				}
			});
		});
		// On click anywhere other than a marker or route, reset highlighting and opacity.
		$(map).on(CLICK_TRIGGER, function() {
			resetPreviousRoutesAndMarkers();
		});
	}

	/**
	 * Step 5: Add custom controls
	 */
	// Add custom zoom controller
	L.control.zoom({
		position: 'topright'
	}).addTo(map);
	// Add custom coordinate search input
	map.addControl( new L.Control.Search({
		// Input target data
		layer: layerTarget,
		// Search bar options
		position: 'topright',
		wrapper: 'findbox',
		zoom: maxZoom, // Zoom level set to map maximum
		initial: true, // Show input bar on load
		collapsed: true, // Start with collapsed input bar
		autoCollapse: true, // Collapse input bar when not selected
		// Input formatting options
		textPlaceholder: 'Search by Name...',
		autoType: true, // Auto-complete with first result
		autoResize: false, // Never resize input bar
		tipAutoSubmit: true, // Submit with first or selected
		minLength: 1, // Allow any length input
		tooltipLimit: 5, // Max tool-tip results
		// Search result options
		marker: false, // Don't draw circle on target
		moveToLocation: function(latlng) {
			latlng.layer.openPopup();
			if (thisLocation.locationId != null) {
				clickZoomToLocationMarker(latlng.layer);
			} else if (thisPatronage.patronageId != null) {
				clickZoomToPatronageMarker(latlng.layer);
			} else if (thisStar.starId != null) {
				clickZoomToStarMarker(latlng.layer);
			}
		}
	}));

	/**
	 * Step 6: Define expander behavior
	 */
	$(TOGGLE_ID).on(CLICK_TRIGGER, function() {
		var currentLocation = $(THIS_LOCATION_ID).data(OPTIONS);
		var currentPatronage = $(THIS_PATRONAGE_ID).data(OPTIONS);
		var currentStar = $(THIS_STAR_ID).data(OPTIONS);
		var mapOpacity, toggleText, currentLocationId, currentPatronageId;
		// Select 'Show Map' Behavior
		var showingSearch = $(SEARCH_ID).hasClass(MAX_WINDOW);
		if (showingSearch) {
			$(TOGGLE_ID).text(OPENING_MAP); // 'In Progress' notification
			toggleText = SHOW_RESULTS;
			mapOpacity = 1;
		} 
		// Select 'Show Results' Behavior
		else {
			showAndRestartLoadingScreen(); // Display the loading screen
			toggleText = SHOW_MAP;
			mapOpacity = 0;
		}
		// Step 6.1: Recalculate results (only when expanding search window)
		$.getScript("../reset-dependent-inputs.js", function() {
			if (currentLocation != null && currentLocation.locationId != null) {
				currentLocationId = currentLocation.locationId;
				reloadLocationSearchWindowWithReload(
						currentLocationId, 
						null,
						!showingSearch);
			} else if (currentPatronage != null && currentPatronage.patronageId != null) {
				currentPatronageId = currentPatronage.patronageId;
				reloadPatronageSearchWindowWithReload(
						currentPatronageId, 
						null,
						!showingSearch);
			} else if (currentStar != null && currentStar.starId != null) {
				currentStarId = currentStar.starId;
				reloadStarSearchWindowWithReload(
						currentStarId, 
						null,
						!showingSearch);
			}
		})
		// Step 6.2: Hide the loading screen and animate the toggle transition.
		.done(function() {
			$(LOADING_ID).fadeOut(300); // Always hide loading screen.
			$(MAP_ID).fadeTo("slow", mapOpacity, function() {
				if (showingSearch) {
					$(SEARCH_ID).animate({height:"25px"}, 100).removeClass(MAX_WINDOW);
				} else {
					$(SEARCH_ID).animate({height:"100%"}, 100).addClass(MAX_WINDOW);
				}
				if (currentLocationId != null) {
					goToLocation(currentLocation);
				} else if (currentPatronageId != null) {
					goToPatronage(currentPatronage);
				} else if (currentStarId != null) {
					goToStar(currentStar);
				}
				$(TOGGLE_ID).text(toggleText);
			});
		});
	});

	/**
	 * Step 7: Dynamically update User Rite ID based on Show All selection.
	 */
	$(BODY).on(CLICK_TRIGGER, SHOW_ALL_ID, function(e) {
		e.preventDefault();
		var originalRiteId = $(USER_RITE_ID).data(OPTIONS).originalRiteId;
		var displayRiteId = ($(SHOW_ALL_ID).prop('checked')) 
			? 7 
			: originalRiteId;
		var originalCalendarId = $(USER_RITE_ID).data(OPTIONS).originalCalendarId;
		var displayCalendarId = ($(SHOW_ALL_ID).prop('checked')) 
			? 1
			: originalCalendarId;
		$(USER_RITE_ID).data(OPTIONS, '{' 
				+ '"displayRiteId":' + displayRiteId + ',' 
				+ '"originalRiteId":' + originalRiteId + ',' 
				+ '"displayCalendarId:"' + displayCalendarId + ','
				+ '"originalCalendarId":' + originalCalendarId + '}');
	});

	/**
	 * Step 8: Define link behavior - note that we must re-bind each target 
	 * after query call-back, rather than simply referencing 
	 * the original bind target in a static manner.
	 */
	$(BODY).on(CLICK_TRIGGER, '#prevLocationLink', function(e) {
		e.preventDefault();
		var prevLoc = $(PREV_LOCATION_ID).data(OPTIONS);
		reloadSearchWindow(prevLoc.locationId, null, null, null, false);
		goToLocation(prevLoc);
	});
	$(BODY).on(CLICK_TRIGGER, '#nextLocationLink', function(e) {
		e.preventDefault();
		var nextLoc = $(NEXT_LOCATION_ID).data(OPTIONS);
		reloadSearchWindow(nextLoc.locationId, null, null, null, false);
		goToLocation(nextLoc);
	});
	$(BODY).on(CLICK_TRIGGER, '#prevStarLink', function(e) {
		e.preventDefault();
		var prevStar = $(PREV_STAR_ID).data(OPTIONS);
		reloadSearchWindow(null, null, prevStar.starId, null, false);
		goToLocation(prevStar);
	});
	$(BODY).on(CLICK_TRIGGER, '#nextStarLink', function(e) {
		e.preventDefault();
		var nextStar = $(NEXT_STAR_ID).data(OPTIONS);
		reloadSearchWindow(null, null, nextStar.starId, null, false);
		goToLocation(nextStar);
	});
	$(BODY).on(CLICK_TRIGGER, '#searchIcon', function(e) {
		e.preventDefault();
		var thisLoc = $(THIS_LOCATION_ID).data(OPTIONS);
		var locationId = thisLoc.locationId;
		var thisPat = $(THIS_PATRONAGE_ID).data(OPTIONS);
		var patronageId = thisPat.patronageId;
		if (locationId != null) {
			reloadSearchWindow(locationId, null, null, null, false);
			goToLocation(thisLoc);
		} else if (patronageId != null) {
			reloadSearchWindow(null, patronageId, null, null, false);
			goToPatronage(thisPat);
		} else if (starId != null) {
			reloadSearchWindow(null, null, starId, null, false);
			goToStar(thisStar);
		}
	});
	$(BODY).on(CLICK_TRIGGER, '#showAll', function(e) {
		e.preventDefault();
		var thisLoc = $(THIS_LOCATION_ID).data(OPTIONS);
		var locationId = thisLoc.locationId;
		var thisPat = $(THIS_PATRONAGE_ID).data(OPTIONS);
		var patronageId = thisPat.patronageId;
		if (locationId != null) {
			reloadSearchWindow(locationId, null, null, null, false);
			goToLocation(thisLoc);
		} else if (patronageId != null) {
			reloadSearchWindow(null, patronageId, null, null, false);
			goToPatronage(thisPat);
		} else if (starId != null) {
			reloadSearchWindow(null, null, starId, null, false);
			goToStar(thisStar);
		}
	});
	$(BODY).on(CLICK_TRIGGER, '.pagination-link', function(e) {
		e.preventDefault();
		var idParam = getUrlParamValue(e.target.search, 'byId') 
		var locationId = (thisLocation.locationId != null) ? idParam : null;
		var patronageId = (thisPatronage.patronageId != null) ? idParam : null;
		var starId = (thisStar.starId != null) ? idParam : null;
		var pageNumber = getUrlParamValue(e.target.search, 'page');
		reloadSearchWindow(locationId, patronageId, starId, pageNumber, true);
	});
});

/**
 * Method used to parse specific parameter values from the passed-in URL.
 */
function getUrlParamValue(url, paramName) {
	var results = new RegExp('[\?&]' + paramName + '=([^&#]*)').exec(url);
	return results[1] || null;
}

function clickZoomToLocation(point) {
	clickZoomToLocationMarker(point.target);
}

function clickZoomToPatronage(point) {
	clickZoomToPatronageMarker(point.target);
}

function clickZoomToStar(point) {
	clickZoomToStarMarker(point.target);
}

/**
 * Method used to focus point given a specific Location marker.
 * 
 * @param marker
 */
function clickZoomToLocationMarker(marker) {
	var locationId = marker.options.locationId;
	// Reset bounds on click, to ensure center correctly following map resize
	map.invalidateSize(); 
	// Set point as map center
	var target = marker.getLatLng();
	recenterMap(target.lat, target.lng);
	// Reset all previous selections
	resetPreviousRoutesAndMarkers();
	// Set point fully opaque, reset previously-selected marker opacity.
	marker.setOpacity(1);
	// Display glowing background beneath current marker
	resizeLocationMarkerBackgroundIcon(locationId, 150);
	// Update Location controller attributes, so that we can
	// correctly reload search results from this location on toggle.
	reloadLocationControllerFields(locationId);
}

/**
 * Method used to focus point given a specific Patronage marker.
 * 
 * @param marker
 */
function clickZoomToPatronageMarker(marker) {
	var patronageId = marker.options.patronageId;
	// Reset bounds on click, to ensure center correctly following map resize
	map.invalidateSize(); 
	// Set point as map center
	var target = marker.getLatLng();
	recenterMap(target.lat, target.lng);
	// Reset all previous selections
	resetPreviousMarker();
	// Set point fully opaque, reset previously-selected marker opacity.
	marker.setOpacity(1);
	// Display glowing background beneath current marker
	resizePatronageMarkerBackgroundIcon(patronageId, 150);
	// Update Patronage controller attributes, so that we can
	// correctly reload search results from this patronage on toggle.
	reloadPatronageControllerFields(patronageId);
}

/**
 * Method used to focus point given a specific Star marker.
 * 
 * @param marker
 */
function clickZoomToStarMarker(marker) {
	var starId = marker.options.starId;
	// Reset bounds on click, to ensure center correctly following map resize
	map.invalidateSize(); 
	// Set point as map center
	var target = marker.getLatLng();
	recenterMap(target.lat, target.lng);
	// Reset all previous selections
	resetPreviousAsterismsAndMarkers();
	// Set point fully opaque, reset previously-selected marker opacity.
	marker.setOpacity(1);
	// Display glowing background beneath current marker
	resizeStarMarkerBackgroundIcon(starId, 150);
	// Update Star controller attributes, so that we can
	// correctly reload search results from this star on toggle.
	reloadStarControllerFields(starId);
}

/**
 * Method used to reset marker and route highlighting on de-selection.
 */
function resetPreviousRoutesAndMarkers() {
	// Reset the previously selected location marker.
	resetPreviousMarker();
	// Iterate over all route highlight markers to reset the background.
	for (var j in routeHighlightLayer._layers) {
		var thisRouteHighlightLayer = routeHighlightLayer._layers[j];
		thisRouteHighlightLayer.setStyle({'weight':0});
		var routeLocationIds = thisRouteHighlightLayer.options.locationIds;
		// Also iterate over each location marker associated with the route
		// in order to reset the marker opacity and background highlight.
		$.each(routeLocationIds.split(','), function() {
			var routeLocationId = new Number(this);
			var routeLocationMaker = findMarkerByLocationId(locationLayer, routeLocationId);
			resizeLocationMarkerBackgroundIcon(routeLocationId, 0);
			resetPreviousMarkerOpacity(routeLocationMaker);
		});
	}
	// Reset route opacity for all routes other than the current selected.
	for (var r in routeLayer._layers) {
		var route = routeLayer._layers[r];
		route.setStyle({'opacity':0.33});
	}
}

/**
 * Method used to reset marker and asterism highlighting on de-selection.
 */
function resetPreviousAsterismsAndMarkers() {
	// Reset the previously selected star marker.
	resetPreviousMarker();
	// Iterate over all asterism highlight markers to reset the background.
	for (var j in asterismHighlightLayer._layers) {
		var thisAsterismHighlightLayer = asterismHighlightLayer._layers[j];
		thisAsterismHighlightLayer.setStyle({'weight':0});
		var asterismStarIds = thisAsterismHighlightLayer.options.starIds;
		// Also iterate over each star marker associated with the asterism
		// in order to reset the marker opacity and background highlight.
		$.each(asterismStarIds.split(','), function() {
			var asterismStarId = new Number(this);
			var asterismStarMarker = findMarkerByStarId(starLayer, asterismStarId);
			resizeStarMarkerBackgroundIcon(asterismStarId, 0);
			resetPreviousMarkerOpacity(asterismStarMarker);
		});
	}
}

/**
 * Method used to reset previous marker highlight and opacity on de-selection.
 */
function resetPreviousMarker() {
	// Reset Location
	var prevLocation = $(THIS_LOCATION_ID).data(OPTIONS);
	if (prevLocation != null && prevLocation.locationId != null) {
		var prevLocationId = prevLocation.locationId;
		resizeLocationMarkerBackgroundIcon(prevLocationId, 0); // Hide background
		resetPreviousMarkerOpacity(findMarkerByLocationId(locationLayer, prevLocationId));
	}
	// Reset Patronage
	var prevPatronage = $(THIS_PATRONAGE_ID).data(OPTIONS);
	if (prevPatronage != null && prevPatronage.patronageId != null) {
		var prevPatronageId = prevPatronage.patronageId;
		resizePatronageMarkerBackgroundIcon(prevPatronageId, 0); // Hide background
		resetPreviousMarkerOpacity(findMarkerByPatronageId(patronageLayer, prevPatronageId));
	}
	// Reset Star
	var prevStar = $(THIS_STAR_ID).data(OPTIONS);
	if (prevStar != null && prevStar.starId != null) {
		var prevStarId = prevStar.starId;
		resizeStarMarkerBackgroundIcon(prevStarId, 0); // Hide background
		resetPreviousMarkerOpacity(findMarkerByStarId(starLayer, prevStarId));
	}
}

/**
 * Method used to reset previous marker opacity
 * 
 * @param prevMarker the previous marker
 */
function resetPreviousMarkerOpacity(prevMarker) {
	var prevMarkerData = prevMarker.options;
	var displayRiteId = $(USER_RITE_ID).data(OPTIONS).displayRiteId;
	var prevMarkerOpacity = getMarkerOpacityForRite(prevMarkerData, displayRiteId);
	prevMarker.setOpacity(prevMarkerOpacity);
}

/**
 * Method used to toggle a glow effect background behind the selected Location marker.
 * 
 * @param locationId
 * @param radius
 */
function resizeLocationMarkerBackgroundIcon(locationId, radius) {
	var markerBkg = findMarkerByLocationId(locationHighlightLayer, 'bkg_' + locationId);
	var markerBkgIcon = markerBkg.options.icon;
	markerBkgIcon.options.iconSize = [radius, radius];
	markerBkg.setIcon(markerBkgIcon);
}

/**
 * Method used to toggle a glow effect background behind the selected Patronage marker.
 * 
 * @param patronageId
 * @param radius
 */
function resizePatronageMarkerBackgroundIcon(patronageId, radius) {
	var markerBkg = findMarkerByPatronageId(patronageHighlightLayer, 'bkg_' + patronageId);
	var markerBkgIcon = markerBkg.options.icon;
	markerBkgIcon.options.iconSize = [radius, radius];
	markerBkg.setIcon(markerBkgIcon);
}

/**
 * Method used to toggle a glow effect background behind the selected Star marker.
 * 
 * @param starId
 * @param radius
 */
function resizeStarMarkerBackgroundIcon(starId, radius) {
	var markerBkg = findMarkerByStarId(starHighlightLayer, 'bkg_' + starId);
	var markerBkgIcon = markerBkg.options.icon;
	markerBkgIcon.options.iconSize = [radius, radius];
	markerBkg.setIcon(markerBkgIcon);
}

/**
 * Method used to find a marker from a given Layer for a given Location ID
 * 
 * @param layer
 * @param locationId
 * @returns marker
 */
function findMarkerByLocationId(layer, locationId) {
	for (var i in layer._layers) {
		var thisLayer = layer._layers[i];
		var markerId = thisLayer.options.locationId;
		if (markerId == locationId) {
			return thisLayer;
		}
	}
	return null;
}

/**
 * Method used to find a marker from a given Layer for a given Patronage ID
 * 
 * @param layer
 * @param patronageId
 * @returns marker
 */
function findMarkerByPatronageId(layer, patronageId) {
	for (var i in layer._layers) {
		var thisLayer = layer._layers[i];
		var markerId = thisLayer.options.patronageId;
		if (markerId == patronageId) {
			return thisLayer;
		}
	}
	return null;
}

/**
 * Method used to find a marker from a given Layer for a given Star ID
 * 
 * @param layer
 * @param starId
 * @returns marker
 */
function findMarkerByStarId(layer, starId) {
	for (var i in layer._layers) {
		var thisLayer = layer._layers[i];
		var markerId = thisLayer.options.starId;
		if (markerId == starId) {
			return thisLayer;
		}
	}
	return null;
}

/**
 * Method used to reload Location details and focus on click Link.
 * 
 * @param loc
 * @returns location
 */
function goToLocation(loc) {
	resetPreviousRoutesAndMarkers();
	var target = findMarkerByLocationId(locationLayer, loc.locationId);
	target.setOpacity(1);
	target.openPopup();
	resizeLocationMarkerBackgroundIcon(loc.locationId, 150); // Show background
	recenterMap(loc.pointX, loc.pointY);
}

/**
 * Method used to reload Patronage details and focus on click Link.
 * 
 * @param pat
 * @returns patronage
 */
function goToPatronage(pat) {
	resetPreviousMarker();
	var target = findMarkerByPatronageId(patronageLayer, pat.patronageId);
	target.setOpacity(1);
	target.openPopup();
	resizePatronageMarkerBackgroundIcon(pat.patronageId, 150); // Show background
	recenterMap(pat.pointX, pat.pointY);
}

/**
 * Method used to reload Star details and focus on click Link.
 * 
 * @param star
 * @returns star
 */
function goToStar(star) {
	resetPreviousAsterismsAndMarkers();
	var target = findMarkerByStarId(starLayer, star.starId);
	target.setOpacity(1);
	target.openPopup();
	resizeStarMarkerBackgroundIcon(star.starId, 150); // Show background
	recenterMap(star.pointY, star.pointX);
}

/**
 * Method used to reload Search window contents only.
 * This approach prevents expensive repeated reloads of the base Map.
 * 
 * @param locationId
 * @param patronageId
 * @param starId
 * @param pageNumber
 * @param alwaysReload
 * @returns location
 */
function reloadSearchWindow(locationId, patronageId, starId, pageNumber, alwaysReload) {
	showAndRestartLoadingScreen();
	$.getScript("../reset-dependent-inputs.js", function() {
		if (locationId != null) {
			reloadLocationSearchWindowWithReload(locationId, pageNumber, alwaysReload);
		} else if (patronageId != null) {
			reloadPatronageSearchWindowWithReload(patronageId, pageNumber, alwaysReload);
		} else if (starId != null) {
			reloadStarSearchWindowWithReload(starId, pageNumber, alwaysReload);
		}
	}).done(function() {
		$(LOADING_ID).fadeOut(300);
	});
}

/**
 * Method used to reload Controller fields only when location changes.
 * This approach prevents expensive repeated reloads of the base Map.
 * 
 * @param locationId
 * @returns searchWindow
 */
function reloadLocationControllerFields(locationId) {
	var showAllParam = ($(USER_RITE_ID).data(OPTIONS).displayRiteId == 1) 
			? '&showAll=true' 
			: '';
	if (locationId != $(THIS_LOCATION_ID).data(OPTIONS).locationId) {
		$('.controller-fields').load(
				// Base URL
				'/search/location?'
				// GET Parameters
				+ 'byId=' + locationId + showAllParam
				// Target elements for reload
				+ CONTROLLER_FIELDS);
	}
}

/**
 * Method used to reload Controller fields only when patronage changes.
 * This approach prevents expensive repeated reloads of the base Map.
 * 
 * @param patronageId
 * @returns searchWindow
 */
function reloadPatronageControllerFields(patronageId) {
	var showAllParam = ($(USER_RITE_ID).data(OPTIONS).displayRiteId == 1) 
			? '&showAll=true' 
			: '';
	if (patronageId != $(THIS_PATRONAGE_ID).data(OPTIONS).patronageId) {
		$('.controller-fields').load(
				// Base URL
				'/search/patronage?'
				// GET Parameters
				+ 'byId=' + patronageId + showAllParam
				// Target elements for reload
				+ CONTROLLER_FIELDS);
	}
}

/**
 * Method used to reload Controller fields only when star changes.
 * This approach prevents expensive repeated reloads of the base Map.
 * 
 * @param starId
 * @returns searchWindow
 */
function reloadStarControllerFields(starId) {
	var showAllParam = ($(USER_RITE_ID).data(OPTIONS).displayRiteId == 1) 
			? '&showAll=true' 
			: '';
	if (starId != $(THIS_STAR_ID).data(OPTIONS).starId) {
		$('.controller-fields').load(
				// Base URL
				'/search/star?'
				// GET Parameters
				+ 'byId=' + starId + showAllParam
				// Target elements for reload
				+ CONTROLLER_FIELDS);
	}
}

/**
 * Method used to reset bounds on click, to ensure center correctly following map resize.
 * 
 * @param pointX
 * @param pointY
 */
function recenterMap(pointX, pointY) {
	map.invalidateSize();
	map.setView(L.latLng(parseFloat(pointX),parseFloat(pointY)), maxZoom);
}

/**
 * Method used to reset and display the loading screen.
 */
function showAndRestartLoadingScreen() {
	var newLoadImg = new Image();
	newLoadImg.src='../LOAD.gif';
	$('#loading-image').prop('src', newLoadImg.src);
	$(LOADING_ID).show();
}

/**
 * Method used to dynamically set opacity based on location Rite.
 * 
 * @param marker
 * @param riteId
 * XXX: Update these values
 */
function getMarkerOpacityForRite(marker, riteId) {
	if (riteId == 1
		|| ((riteId == 2 || riteId == 3) && marker.catholicCommemorationCount > 0) 
		|| ((riteId == 4 || riteId == 5) && marker.protestantCommemorationCount > 0) 
		|| (riteId == 6 && marker.orthodoxCommemorationCount > 0) 
		|| (riteId == 7 && marker.sunniCommemorationCount > 0) 
		|| (riteId == 8 && marker.shiiteCommemorationCount > 0) 
		|| (riteId == 9 && marker.jewishCommemorationCount > 0)) {
		return 1;
	} else {
		return 0.33;
	}
}

function filterJSONCall(rawjson) {
	var json = {};
	var key, loc = [];
	for(var i in rawjson) {
		key = rawjson[i].formatted_address;
		loc = this._getLocation(this._input.value);
		json[ key ]= loc; //key,value format
	}
	return json;
}
