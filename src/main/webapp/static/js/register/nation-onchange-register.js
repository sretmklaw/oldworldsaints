$(document).ready(function() {
	$('#nationDropdown').on('change', function() {
		var regionDropdown = $('#regionDropdown');

		// Enable search button when selection is made
		var searchBtn = $('#nationSearchBtn');
		if (searchBtn.length > 0) {
			searchBtn.prop('disabled', false);
		}

		// Clear current value of dependent options on update
		$('option', regionDropdown).remove();
		$('#nationErrors').empty();
		$('#regionErrors').empty();
		regionDropdown.addClass('hidden');

		$.ajax({
			type :	"GET",
			url :	'/region',
			data :	{ 
				nationId : $('#nationDropdown').val()
			},
			success : function(data) {

				// Do not display Region drop-down where Nation has only one Region,
				// or Region Name is undefined. In such cases, automatically select
				// the default option without prompting user to select Region
				if (data.length === 1 || data[0].regionName === null) {
					regionDropdown.addClass('hidden');
					regionDropdown.append($('<option selected />')
							.val(data[0].regionId)
							.text(""));
				} 
				// Otherwise, add and display available Controller returned options
				else {
					regionDropdown.removeClass('hidden');
					// Add Default option
					regionDropdown.append($('<option disabled selected value="" '
							+ 'class="default-option">Select Region</option>'));
					// Add Controller returned options with top padding
					$(data).each(function() {
						regionDropdown.append($('<option />')
								.val(this.regionId)
								.text(this.regionName + this.utcOffsetFormatted));
					});
				}
			}
		});
	});

	$('#regionDropdown').on('change', function() {
		$('#regionErrors').empty();
	});
});