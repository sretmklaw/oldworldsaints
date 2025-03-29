$(document).ready(function() {

	const ASCII_REGEX = /[^a-zA-Z0-9 \\|\/'",.!?:;\-\(\)\[\]{}@#$%^&*_+=~<>]/g;
	const ASCII_ERROR = 'basic ASCII input';
	const REG_DISABLED = 'disabled';
	const REG_USERNAME_INPUT = $('#usernameInput');
	const REG_EMAIL_INPUT = $('#emailInput');
	const REG_PASSWORD_INPUT = $('#passwordInput');
	const REG_PASSWORD_CONFIRM_INPUT = $('#passwordConfirmInput');
	const REG_CALENDAR_DROPDOWN = $('#calendarDropdown');
	const REG_NATION_DROPDOWN = $('#nationDropdown');
	const REG_CAPTCHA_INPUT = $('#captchaInput');

	// Triggered whenever there is a change to Username input
	REG_USERNAME_INPUT.on('input', function() {
		verifyTextInput(this, 
				'Username', // Field Name
				'usernameErrors', // Error ID
				ASCII_REGEX, // Input RegEx
				ASCII_ERROR, // Allowed Special Characters
				24); // Max Characters
	});

	// Triggered whenever there is a change to Email input
	REG_EMAIL_INPUT.on('input', function() {
		verifyTextInput(this, 
				'Email', // Field Name
				'emailErrors', // Error ID
				ASCII_REGEX, // Input RegEx
				ASCII_ERROR, // Allowed Special Characters
				32); // Max Characters
	});

	// Triggered whenever there is a change to Password input
	REG_PASSWORD_INPUT.on('input', function() {
		verifyTextInput(this, 
				'Password', // Field Name
				'passwordErrors', // Error ID
				ASCII_REGEX, // Input RegEx
				ASCII_ERROR, // Allowed Special Characters
				24); // Max Characters
	});

	// Triggered whenever there is a change to Confirm Password input
	REG_PASSWORD_CONFIRM_INPUT.on('input', function() {
		verifyTextInput(this, 
				'Password', // Field Name
				'passwordConfirmErrors', // Error ID
				ASCII_REGEX, // Input RegEx
				ASCII_ERROR, // Allowed Special Characters
				24); // Max Characters
	});


	// Triggered whenever there is a change to Old Password input
	$('#oldPasswordInput').on('input', function() {
		verifyTextInput(this, 
				'Password', // Field Name
				'oldPasswordErrors', // Error ID
				ASCII_REGEX, // Input RegEx
				ASCII_ERROR, // Allowed Special Characters
				24); // Max Characters
	});

	// Enable CAPTCHA when input exists on all required fields
	$('body').on('input change keyup', function(e) {
		if (REG_USERNAME_INPUT.val() != ''
				&& REG_PASSWORD_INPUT.val() != ''
				&& REG_PASSWORD_CONFIRM_INPUT.val() != ''
				&& REG_CALENDAR_DROPDOWN.find(':selected').val() != ''
				&& REG_NATION_DROPDOWN.find(':selected').val() != '') {
			REG_CAPTCHA_INPUT.prop(REG_DISABLED, false);
		} else {
			REG_CAPTCHA_INPUT.prop(REG_DISABLED, true);
		}
	});
});