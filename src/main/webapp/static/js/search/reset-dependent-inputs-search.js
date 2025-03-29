/**
 * Method used to reload Search window contents only when location changes.
 * Includes parameter to trigger reload regardless of current location, 
 * in order to ensure that toggle search results is always up-to-date.
 * 
 * @param locationId
 * @param pageNumber
 * @param alwaysReload
 * @returns location
 */
function reloadLocationSearchWindowWithReload(locationId, pageNumber, alwaysReload) {

	var thisLocation = $('#thisLocation').data('options');
	var pageParam = (pageNumber != null) ? '&page=' + pageNumber : '';
	var showAllParam = ($('#userRite').data('options').displayRiteId == 1) ? '&showAll=true' : '';

	if (thisLocation != null 
			&& (thisLocation.locationId != locationId || alwaysReload)) {
		$('#search-window').load(
				'/search/location?byId=' + locationId 
				+ pageParam
				+ showAllParam 
				+ ' #search-window-contents');
	}
}

/**
 * Method used to reload Search window contents only when patronage changes.
 * Includes parameter to trigger reload regardless of current patronage, 
 * in order to ensure that toggle search results is always up-to-date.
 * 
 * @param patronageId
 * @param pageNumber
 * @param alwaysReload
 * @returns patronage
 */
function reloadPatronageSearchWindowWithReload(patronageId, pageNumber, alwaysReload) {

	var thisPatronage = $('#thisPatronage').data('options');
	var pageParam = (pageNumber != null) ? '&page=' + pageNumber : '';
	var showAllParam = ($('#userRite').data('options').displayRiteId == 1) ? '&showAll=true' : '';

	if (thisPatronage != null 
			&& (thisPatronage.patronageId != patronageId || alwaysReload)) {
		$('#search-window').load(
				'/search/patronage?byId=' + patronageId 
				+ pageParam
				+ showAllParam 
				+ ' #search-window-contents');
	}
}

/**
 * Method used to reload Search window contents only when star changes.
 * Includes parameter to trigger reload regardless of current star, 
 * in order to ensure that toggle search results is always up-to-date.
 * 
 * @param starId
 * @param pageNumber
 * @param alwaysReload
 * @returns star
 */
function reloadStarSearchWindowWithReload(starId, pageNumber, alwaysReload) {

	var thisStar = $('#thisStar').data('options');
	var pageParam = (pageNumber != null) ? '&page=' + pageNumber : '';
	var showAllParam = ($('#userRite').data('options').displayRiteId == 1) ? '&showAll=true' : '';

	if (thisStar != null 
			&& (thisStar.starId != starId || alwaysReload)) {
		$('#search-window').load(
				'/search/star?byId=' + starId 
				+ pageParam
				+ showAllParam 
				+ ' #search-window-contents');
	}
}