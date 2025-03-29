/**
 * Subsequently, configure tool-tip plug-in
 */
$(document).ready(function () {

	// Configure tool-tip actions on hover
	// Enabled only on non-mobile devices.
	if (window.innerWidth > 600){
		$(document).tooltip({
			position: {
				my: "center bottom-10",
				at: "center top",
				using: function(position, feedback) {
					$(this).css(position);
					$("<div>")
						.addClass("arrow" )
						.addClass(feedback.vertical)
						.addClass(feedback.horizontal)
						.appendTo(this);
				}
			}
		});
	}
});