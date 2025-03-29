$(document).ready(function() {
	$('#nationDropdown').on('change', function() {

		// Reset all other Search Bar inputs
		$.getScript("../reset-dependent-inputs.js", function() {
			resetNavbarTextAndDropdownInputs('nationDropdown', 'nationSearchBtn');
		});
	});
});