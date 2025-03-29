$(document).ready(function() {
	$('#centuryDropdown').on('change', function() {

		// Reset all other Search Bar inputs
		$.getScript("../reset-dependent-inputs.js", function() {
			resetNavbarTextAndDropdownInputs('centuryDropdown', 'centurySearchBtn');
		});
	});
});