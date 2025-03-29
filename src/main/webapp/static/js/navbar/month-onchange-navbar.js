$(document).ready(function() {
	$('#monthDropdown').on('change', function() {

		// Reset all other Search Bar inputs
		$.getScript("../reset-dependent-inputs.js", function() {
			resetNavbarTextAndDropdownInputs('monthDropdown', 'daySearchBtn');
		})
		// On call-back, proceed with updates
		.done(function () {

			var dayDropdown = $('#dayDropdown');
			// Clear current value of dependent options on update
			$('option', dayDropdown).remove();
			// Call to Controller for response
			$.ajax({ type:"GET", url:'/day',
				data:'monthId=' + $('#monthDropdown').val(),
				success : function(data) {
					// Enable the target drop-down
					dayDropdown.prop('disabled', false);
					// Add Default Option
					dayDropdown.append($('<option disabled selected value="" '
							+ 'class="default-option">Day</option>'));
					// Add Controller Returned Options
					$(data).each(function() {
						dayDropdown.append($('<option/>')
								.val(this.dayId)
								.text(this.dayOfMonth));
					});
				}
			})
		});
	});
});