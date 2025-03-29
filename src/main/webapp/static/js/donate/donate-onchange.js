$(document).ready(function() {

	const DONATE_ACTIVE = 'active';
	const DONATE_DISABLED = 'disabled';
	const DONATE_FORMGROUP = 'form-group';
	const DONATE_HIDDEN = 'hidden';
	const DONATE_CONFIRM_DIALOG = $('#confirmDialog');
	const DONATE_PAGE_MASK = $('#pageMask');
	const DONATE_ERROR_HEADER = '<span class="alert-non-padded ';
	const DONATE_ERRORS = $('#donateErrors');
	const DONATE_INPUT_REGEX = /^[0-9]*(\.\d{0,2})?$/;
	const DONATE_BADCHAR_CLASS = 'input-error-badchar';

	// Initiate PayPal order functionality
	$('#amount').on('input', function(e) {
		$('#submitBtn').prop('disabled', 
				this.value.length < 1 || !DONATE_INPUT_REGEX.test(this.value));
	});

	// Confirm PayPal order functionality
	$('#confirmBtn').on('click', function() {
		closeConfirmDialogWithSubmit(true);
	});
	$('#cancelBtn').on('click', function() {
		closeConfirmDialogWithSubmit(false);
	});

	function closeConfirmDialogWithSubmit(shouldSubmit) {
		if (shouldSubmit) {
			$('#donateErrors, #donateInputs').removeClass(DONATE_FORMGROUP);
			$('#amount').prop(DONATE_DISABLED, true);
			$('#submitBtn').addClass(DONATE_HIDDEN);
			// Displays page loading icon until completed.
			$('#loading').fadeIn(300);
			$.ajax({ type:"GET", url:'/donate/submit', 
				success: function() {
					$('.content').css({'background-color':'transparent'});
					$('#confirmMessage')
						.removeClass(DONATE_HIDDEN)
						.append('<p>Donation received. Thank you!</p>');
				},
				error: function(xhr, ajaxOptions, thrownError) {
					$('.content').css({'background-color':'transparent'});
					$('#pageErrorMessage')
						.removeClass(DONATE_HIDDEN)
						.append('<p>Problem submitting request. Please try again later.</p>');
				}
			}).done(function() {
				$('#loading').fadeOut(300); // Remove page load icon.
			});
		} else {
			$('#donateInputs').addClass(DONATE_FORMGROUP);
			$('#amount').prop(DONATE_DISABLED, false);
			$('#submitBtn').removeClass(DONATE_HIDDEN);
		}
		if (DONATE_CONFIRM_DIALOG.hasClass(DONATE_ACTIVE)) {
			DONATE_CONFIRM_DIALOG
				.removeClass(DONATE_ACTIVE)
				.addClass(DONATE_HIDDEN);
			DONATE_PAGE_MASK.removeClass(DONATE_ACTIVE);
		}
	}

	// Validate donation amount input
	$('#amount').on('input', function() {
		verifyDollarInput(this);
	});

	/**
	 * Used to filter and verify dollar-amount inputs.
	 * 
	 * @param input
	 */
	function verifyDollarInput(textInput) {

		// Prevent entry of unacceptable input characters
		DONATE_ERRORS.empty();
		if (!DONATE_INPUT_REGEX.test(textInput.value)) {
			$(textInput).val($(textInput).val().replace(DONATE_INPUT_REGEX,''));
			DONATE_ERRORS.append(DONATE_ERROR_HEADER + DONATE_BADCHAR_CLASS 
					+ '">Must be valid dollar amount</span>');
		}
		// Clear errors on acceptable input
		if (DONATE_ERRORS.children().length > 0) {
			DONATE_ERRORS.removeClass(DONATE_HIDDEN).addClass(DONATE_FORMGROUP);
		} else {
			DONATE_ERRORS.addClass(DONATE_HIDDEN).removeClass(DONATE_FORMGROUP);
		}
	}
});