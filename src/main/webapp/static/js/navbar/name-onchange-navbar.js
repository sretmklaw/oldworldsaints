$(document).ready(function() {

	const COMM_BY_NAME_SEARCH_BTN = $('#commByNameSearchBtn');

	$('#commByNameInput').on('input', function() {

		verifyTextInput(this, 
				'Name', // Field Name
				'commByNameErrors', // Error ID
				/[^a-zA-Z /',\.-]/g, // Input RegEx
				'alphabetic or / \' , . -', // Allowed Special Characters
				50); // Max Characters

		// Disable search button, if Name is empty.
		if (this.value.length == 0) {
			COMM_BY_NAME_SEARCH_BTN.prop('disabled', true);
		} else {
			COMM_BY_NAME_SEARCH_BTN.prop('disabled', false);
		}
	});
});