/**
 * This global function resets all search buttons on navigation bar,
 * other than the current selection.
 */
function resetNavbarTextAndDropdownInputs(targetInputId, searchBtnId) {

	const DISABLED = 'disabled';
	// Reset navigation bar drop-down menus other than the current one.
	$.each([
		'commByNameInput',
		'nationDropdown',
		'centuryDropdown',
		'tagDropdown',
	], function() {
		if (targetInputId != this) {
			resetNavbarTextAndDropdownInput(this, false);
		}
	});
	// Disable navigation bar Search buttons other than the current one.
	$.each([
		'commByNameSearchBtn',
		'nationSearchBtn',
		'centurySearchBtn',
		'tagSearchBtn'
	], function() {
		
		if (searchBtnId != this) {
			resetNavbarTextAndDropdownInput(this, true);
		}
	});
	// Date and Patronage drop-down specific display logic.
	const CALENDAR_DROPDOWN = 'calendarDropdown';
	const MONTH_DROPDOWN = 'monthDropdown';
	const DAY_DROPDOWN = 'dayDropdown';
	const DAY_SEARCH_BUTTON = 'daySearchBtn';
	const PATRONAGE_TYPE_DROPDOWN = 'patronageTypeDropdown';
	const PATRONAGE_SUBTYPE_DROPDOWN = 'patronageSubtypeDropdown';
	const PATRONAGE_DROPDOWN = 'patronageDropdown';
	const PATRONAGE_SEARCH_BUTTON = 'patronageSearchBtn';
	const CONSTELLATION_DROPDOWN = 'constellationDropdown';
	const STAR_DROPDOWN = 'starDropdown';
	const STAR_SEARCH_BUTTON = 'starSearchBtn';
	// Date
	if (targetInputId == CALENDAR_DROPDOWN 
			|| targetInputId == MONTH_DROPDOWN) {
		if (targetInputId == CALENDAR_DROPDOWN) {
			resetNavbarTextAndDropdownInput(MONTH_DROPDOWN, false);
		}
		resetNavbarTextAndDropdownInput(DAY_DROPDOWN, true);
		resetNavbarTextAndDropdownInput(DAY_SEARCH_BUTTON, true);
		resetNavbarTextAndDropdownInput(PATRONAGE_TYPE_DROPDOWN, false);
		resetNavbarTextAndDropdownInput(PATRONAGE_SUBTYPE_DROPDOWN, true);
		resetNavbarTextAndDropdownInput(PATRONAGE_DROPDOWN, true);
		resetNavbarTextAndDropdownInput(PATRONAGE_SEARCH_BUTTON, true);
		resetNavbarTextAndDropdownInput(CONSTELLATION_DROPDOWN, false);
		resetNavbarTextAndDropdownInput(STAR_DROPDOWN, true);
		resetNavbarTextAndDropdownInput(STAR_SEARCH_BUTTON, true);
	}
	// Patronage
	else if (targetInputId == PATRONAGE_TYPE_DROPDOWN 
			|| targetInputId == PATRONAGE_SUBTYPE_DROPDOWN) {
		resetNavbarTextAndDropdownInput(CALENDAR_DROPDOWN, false);
		resetNavbarTextAndDropdownInput(MONTH_DROPDOWN, true);
		resetNavbarTextAndDropdownInput(DAY_DROPDOWN, true);
		resetNavbarTextAndDropdownInput(DAY_SEARCH_BUTTON, true);
		if (targetInputId == PATRONAGE_TYPE_DROPDOWN) {
			resetNavbarTextAndDropdownInput(PATRONAGE_SUBTYPE_DROPDOWN, false);
		}
		resetNavbarTextAndDropdownInput(PATRONAGE_DROPDOWN, true);
		resetNavbarTextAndDropdownInput(PATRONAGE_SEARCH_BUTTON, true);
		resetNavbarTextAndDropdownInput(CONSTELLATION_DROPDOWN, false);
		resetNavbarTextAndDropdownInput(STAR_DROPDOWN, true);
		resetNavbarTextAndDropdownInput(STAR_SEARCH_BUTTON, true);
	} // Star
	else if (targetInputId == CONSTELLATION_DROPDOWN) {
		resetNavbarTextAndDropdownInput(CALENDAR_DROPDOWN, false);
		resetNavbarTextAndDropdownInput(MONTH_DROPDOWN, true);
		resetNavbarTextAndDropdownInput(DAY_DROPDOWN, true);
		resetNavbarTextAndDropdownInput(DAY_SEARCH_BUTTON, true);
		resetNavbarTextAndDropdownInput(PATRONAGE_TYPE_DROPDOWN, false);
		resetNavbarTextAndDropdownInput(PATRONAGE_SUBTYPE_DROPDOWN, true);
		resetNavbarTextAndDropdownInput(PATRONAGE_DROPDOWN, true);
		resetNavbarTextAndDropdownInput(PATRONAGE_SEARCH_BUTTON, true);
		resetNavbarTextAndDropdownInput(STAR_DROPDOWN, false);
		resetNavbarTextAndDropdownInput(STAR_SEARCH_BUTTON, true);
	} else {
		// Reset Calendar and Month drop-downs when target input is
		// other than Calendar, Month, or Day.
		if (targetInputId != DAY_DROPDOWN) {
			resetNavbarTextAndDropdownInput(CALENDAR_DROPDOWN, false);
			resetNavbarTextAndDropdownInput(MONTH_DROPDOWN, true);
			resetNavbarTextAndDropdownInput(DAY_DROPDOWN, true);
			resetNavbarTextAndDropdownInput(DAY_SEARCH_BUTTON, true);
		}
		// Reset Patronage Type/Subtype drop-downs when target input is
		// other than Patronage, Type, or Subtype.
		if (targetInputId != PATRONAGE_DROPDOWN) {
			resetNavbarTextAndDropdownInput(PATRONAGE_TYPE_DROPDOWN, false);
			resetNavbarTextAndDropdownInput(PATRONAGE_SUBTYPE_DROPDOWN, true);
			resetNavbarTextAndDropdownInput(PATRONAGE_DROPDOWN, true);
			resetNavbarTextAndDropdownInput(PATRONAGE_SEARCH_BUTTON, true);
		}
		// Reset Constellation and Star drop-downs when target input is
		// other than Constellation or Star.
		if (targetInputId != STAR_DROPDOWN) {
			resetNavbarTextAndDropdownInput(CONSTELLATION_DROPDOWN, false);
			resetNavbarTextAndDropdownInput(STAR_DROPDOWN, true);
			resetNavbarTextAndDropdownInput(STAR_SEARCH_BUTTON, true);
		}
		// Enable target search button when any selection is made,
		// other than Calendar and Month.
		var searchBtn = $('#' + searchBtnId);
		if ($('#' + targetInputId).val() != '') {
			searchBtn.prop(DISABLED, false);
		} else {
			searchBtn.prop(DISABLED, true);
		}
	}
}

/**
 * Function used to reset a single input field.
 * Duplicates method from reset-dependent-inputs to cut down
 * on the number of calls required to external scripts.
 * 
 * @param inputId the input element to set
 * @param shouldDisable whether or not to lock the element
 */
function resetNavbarTextAndDropdownInput(inputId, shouldDisable) {

	var input = $('#' + inputId);
	// Conditionally disable each target input
	if (shouldDisable) {
		input.prop('disabled', true);
	}
	// Reset text input values
	var inputType = input.prop('type');
	if (inputType === 'text' || inputType === 'textarea') {
		input.val('');
	}
	// Reset the drop-down menu inputs to default option
	else {
		input.val($("this option:first").val());
	}
	// Finally, reset any errors on text input
	$('#commByNameErrors').empty();
}
