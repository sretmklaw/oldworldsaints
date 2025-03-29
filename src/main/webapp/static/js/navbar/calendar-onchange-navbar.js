$(document).ready(function() {
	$('#calendarDropdown').on('change', function() {

		// Reset all other Search Bar inputs
		$.getScript("../reset-dependent-inputs.js", function() {
			resetNavbarTextAndDropdownInputs('calendarDropdown', 'daySearchBtn');
		})
		// On call-back, proceed with updates
		.done(function () {

			var monthDropdown = $('#monthDropdown');
			// Clear current value of dependent options on update
			$('option', monthDropdown).remove();
			// Call to Controller for response
			$.ajax({ type:"GET", url:'/month', 
				data:'calendarId=' + $('#calendarDropdown').val(),
				success : function(data) {
					// Enable the target drop-down
					monthDropdown.prop('disabled', false);
					// Add Default Option
					monthDropdown.append($('<option disabled selected value="" '
							+ 'class="default-option">Month</option>'));
					// Add Controller Returned Options with divider
					$(data).each(function() {
						monthDropdown.append($('<option/>')
								.val(this.monthId)
								.text(this.monthName));
					});
				}
			});
		});
	});
});