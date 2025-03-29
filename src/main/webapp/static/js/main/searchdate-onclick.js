/**
 * Allows the user to display a specific date
 */
$(document).ready(function() {

	var searchInput = $('#byDateInput');
	var searchBtn = $('#byDateSearchBtn');

	// Enable search button when selection is made
	searchInput.on('change', function() {
		if (searchInput.val() != '') {
			searchBtn.prop('disabled', false);
		}
	});
});