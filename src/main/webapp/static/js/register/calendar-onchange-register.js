$(document).ready(function() {
	$('#calendarDropdown').on('change', function() {
		var riteDropdown = $('#riteDropdown');

		// Clear current value of dependent options on update
		$('option', riteDropdown).remove();
		$('#calendarErrors').empty();
		$('#riteErrors').empty();
		riteDropdown.addClass('hidden');

		$.ajax({
			type :	"GET",
			url :	'/rite',
			data :	{ 
				calendarId : $('#calendarDropdown').val()
			},
			success : function(data) {

				// Do not display Rite drop-down where Calendar has only one Rite,
				// or Rite Name is undefined. In such cases, automatically select
				// the default option without prompting user to select Rite
				if (data.length === 1 || data[0].riteName === null) {
					riteDropdown.addClass('hidden');
					riteDropdown.append($('<option selected />')
							.val(data[0].riteId)
							.text(""));
				} 
				// Otherwise, add and display available Controller returned options
				else {
					riteDropdown.removeClass('hidden');
					// Add default option
					riteDropdown.append($('<option disabled selected value="" '
							+ 'class="default-option">Select Rite</option>'));
					// Add Controller returned options with top padding
					$(data).each(function() {
						riteDropdown.append($('<option />')
								.val(this.riteId)
								.text(this.riteName));
					});
				}
			}
		});
	});

	$('#riteDropdown').on('change', function() {
		$('#riteErrors').empty();
	});
});