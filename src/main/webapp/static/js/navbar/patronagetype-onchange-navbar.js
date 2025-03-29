$(document).ready(function() {
	$('#patronageTypeDropdown').on('change', function() {

		// Reset all other Search Bar inputs
		$.getScript("../reset-dependent-inputs.js", function() {
			resetNavbarTextAndDropdownInputs('patronageTypeDropdown', 'patronageSearchBtn');
		})
		// On call-back, proceed with updates
		.done(function () {

			var patronageSubtypeDropdown = $('#patronageSubtypeDropdown');
			// Clear current value of dependent options on update
			$('option', patronageSubtypeDropdown).remove();
			// Call to Controller for response
			$.ajax({ type:"GET", url:'/patronage-subtype', 
				data:'patronageTypeId=' + $('#patronageTypeDropdown').val(),
				success : function(data) {
					// Enable the target drop-down
					patronageSubtypeDropdown.prop('disabled', false);
					// Add Default Option
					patronageSubtypeDropdown.append($('<option disabled selected value="" '
							+ 'class="default-option">Patronage Subtype</option>'));
					// Add Controller Returned Options with divider
					$(data).each(function() {
						patronageSubtypeDropdown.append($('<option/>')
								.val(this.patronageSubtypeId)
								.text(this.patronageSubtypeName));
					});
				}
			});
		});
	});
});