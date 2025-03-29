$(document).ready(function() {
	$('#starDropdown').on('change', function() {

		// Reset all other Search Bar inputs
		$.getScript("../reset-dependent-inputs.js", function() {
			resetNavbarTextAndDropdownInputs('starDropdown', 'starSearchBtn');
		});
	});
});