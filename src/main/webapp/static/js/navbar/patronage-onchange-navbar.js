$(document).ready(function() {
	$('#patronageDropdown').on('change', function() {

		// Reset all other Search Bar inputs
		$.getScript("../reset-dependent-inputs.js", function() {
			resetNavbarTextAndDropdownInputs('patronageDropdown', 'patronageSearchBtn');
		});
	});
});