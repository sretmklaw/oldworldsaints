$(document).ready(function() {
	$('#dayDropdown').on('change', function() {

		// Reset all other Search Bar inputs
		$.getScript("../reset-dependent-inputs.js", function() {
			resetNavbarTextAndDropdownInputs('dayDropdown', 'daySearchBtn');
		});
	});
});