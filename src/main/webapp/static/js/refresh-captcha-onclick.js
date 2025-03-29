$(document).ready(function() {

	const CAPTCHA_INPUT = $('#captchaInput');
	const CAPTCHA_ERRORS = $('#captchaErrors');
	const CAPTCHA_DISABLED = 'disabled';

	$('#refreshBtn').on('click', function(e) {
		e.preventDefault();
		// Rotate the refresh button icon
		$('#refreshIcon').toggleClass('rotate');
		// Reset CAPTCHA field and errors
		CAPTCHA_INPUT.val('');
		if (CAPTCHA_ERRORS.children().length > 0) {
			CAPTCHA_INPUT.prop(CAPTCHA_DISABLED, false);
		}
		CAPTCHA_ERRORS.empty();
		// Regenerate CAPTCHA image and text
		$.ajax({
			type :	"GET",
			url :	'/captcha',
			success : function(data) {
				$('#captchaIcon')
					.removeAttr('src')
					.attr('src', ("data:image/jpeg;base64,").concat(data));
			}
		}).done(function() {
			// Request-specific functionality
			if (CAPTCHA_INPUT.length < 7) {
				$('#submissionPanel').slideUp();
				$('#submitBtn, #commitBtn').prop(CAPTCHA_DISABLED, true);
			} else {
				$('#submissionPanel').slideDown();
				$('#submitBtn, #commitBtn').prop(CAPTCHA_DISABLED, false);
			}
		});
	});

	// Enable form submit only after CAPTCHA challenge is answered
	CAPTCHA_INPUT.on('input', function() {
		$('#submitBtn').prop(CAPTCHA_DISABLED, this.value.length < 7);
	});

	// Reset CAPTCHA and errors on change to any input field on the page.
	$('body').on('input change keyup', function(e) {
		CAPTCHA_ERRORS.empty();
		if (e.target.id != 'captchaInput' 
				&& !e.target.classList.contains('submitAction')) {
			CAPTCHA_INPUT.val('');
		}
	});
});