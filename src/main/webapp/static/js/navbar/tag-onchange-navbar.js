$(document).ready(function() {
	$('#tagDropdown').on('change', function() {

		// Reset all other Search Bar inputs
		$.getScript("../reset-dependent-inputs.js", function() {
			resetNavbarTextAndDropdownInputs('tagDropdown', 'tagSearchBtn');
		});
	});
});