$(document).ready(function() {
	$('#patronageSubtypeDropdown').on('change', function() {

		// Reset all other Search Bar inputs
		$.getScript("../reset-dependent-inputs.js", function() {
			resetNavbarTextAndDropdownInputs('patronageSubtypeDropdown', 'patronageSearchBtn');
		})
		// On call-back, proceed with updates
		.done(function () {

			var patronageDropdown = $('#patronageDropdown');
			// Clear current value of dependent options on update
			$('option', patronageDropdown).remove();
			// Call to Controller for response
			$.ajax({ type:"GET", url:'/patronage',
				data:'patronageSubtypeId=' + $('#patronageSubtypeDropdown').val(),
				success : function(data) {
					// Enable the target drop-down
					patronageDropdown.prop('disabled', false);
					// Add Default Option
					patronageDropdown.append($('<option disabled selected value="" '
							+ 'class="default-option">Patronage</option>'));
					// Add Controller Returned Options
					$(data).each(function() {
						patronageDropdown.append($('<option/>')
								.val(this.patronageId)
								.text(this.patronageName));
					});
				}
			})
		});
	});
});