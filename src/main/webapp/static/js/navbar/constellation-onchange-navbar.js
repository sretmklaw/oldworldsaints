$(document).ready(function() {
	$('#constellationDropdown').on('change', function() {

		// Reset all other Search Bar inputs
		$.getScript("../reset-dependent-inputs.js", function() {
			resetNavbarTextAndDropdownInputs('constellationDropdown', 'starSearchBtn');
		})
		// On call-back, proceed with updates
		.done(function () {

			var starDropdown = $('#starDropdown');
			// Clear current value of dependent options on update
			$('option', starDropdown).remove();
			// Call to Controller for response
			$.ajax({ type:"GET", url:'/star', 
				data:'constellationId=' + $('#constellationDropdown').val(),
				success : function(data) {
					// Enable the target drop-down
					starDropdown.prop('disabled', false);
					// Add Default Option
					starDropdown.append($('<option disabled selected value="" '
							+ 'class="default-option">Star</option>'));
					// Add Controller Returned Options with divider
					$(data).each(function() {
						starDropdown.append($('<option/>')
								.val(this.starId)
								.text(this.starName));
					});
				}
			});
		});
	});
});