/**
 * This global function is used to reset drop-down 
 * and text input menus on the Request view.
 * 
 * @param inputIds the list of input elements to reset
 * @param shouldDisable whether or not to also lock each element
 * @param errorId the errors to clear on change
 */
function resetTextAndDropdownInputs(inputIds, shouldDisable, errorId) {
	$.each(inputIds, function () {
		resetTextAndDropdownInput(this, shouldDisable);
	});
	if (errorId != null) {
		$('#' + errorId).empty()
			.addClass('hidden')
			.removeClass('form-group');
	}
}

/**
 * Global function used to reset a single input field.
 * This function should only be called within resetTextAndDropdownInputs.
 * 
 * @param inputId the input element to set
 * @param shouldDisable whether or not to lock the element
 */
function resetTextAndDropdownInput(inputId, shouldDisable) {

	const INPUT = $('#' + inputId);
	// Conditionally disable each target input
	if (shouldDisable) {
		INPUT.prop('disabled', true);
	}
	// Reset text input values
	const INPUT_TYPE = INPUT.prop('type');
	if (INPUT_TYPE === 'text' || INPUT_TYPE === 'textarea') {
		INPUT.val('');
	} 
	// Reset error messages
	else if (INPUT.is('div') && INPUT.hasClass('errors')) {
		INPUT.empty();
	}
	// Reset the drop-down menu inputs to default option
	else {
		INPUT.val($('#' + inputId + ' option:first').val());
	}
}

/**
 * Global function used to conditionally mark or un-mark all associated check-boxes
 * 
 * @param target
 * @param className
 */
function toggleSelectedCheckboxes(target, className) {
	if ($(target).hasClass('active')) {
		$(target).removeClass('active');
		$(className).each(function() { 
			this.checked = false;
		});
	} else {
		$(target).addClass('active');
		$(className).each(function() { 
			var parentRow = $(this).parents('tr');
			if (!parentRow.hasClass('retired') 
					&& parentRow.find('.isAdminUser').length == 0) {
				this.checked = true;
			}
		});
	}
}

/**
 * Global function used to close all accordion expanders other than the current one
 * 
 * @param targetHeader
 * @param targetHeaderParent
 * @param targetContent
 * @param targetContentParent
 */
function deselectAllOthers(targetHeader, targetHeaderParent, targetContent, targetContentParent) {
	$('.result-header, .result-header-parent')
		.not(targetHeader)
		.not(targetHeaderParent)
		.removeClass('active');
	$('.result-content, .result-content-parent')
		.not(targetContent)
		.not(targetContentParent)
		.removeClass('show')
		.addClass('collapsed');
}

/**
 * Global function used to filter and verify free-form text inputs.
 * 
 * @param textInput
 * @param fieldName
 * @param errorId
 * @param inputRegex
 * @param allowedChars
 * @param maxLength
 */
function verifyTextInput(textInput, fieldName, errorId, inputRegex, allowedChars, maxLength) {

	const ERRORS = $('#' + errorId);
	const ERROR_HEADER = '<span class="alert-non-padded ';
	// Clear dynamic form errors on update
	ERRORS.empty();
	// Prevent entry of unacceptable input characters
	const BADTEXT_CLASS = 'input-error-badtext-' + fieldName.toLowerCase();
	if (inputRegex.test(textInput.value)) {
		// Replace newlines with whitespace
		$(textInput).val($(textInput).val().replace(/[\n\r]/g,' '));
		// Then, strip all other invalid input
		$(textInput).val($(textInput).val().replace(inputRegex,''));
		ERRORS.append(ERROR_HEADER + BADTEXT_CLASS + '">' + fieldName + ' is ' + allowedChars + '</span>');
	}
	// Strip pasted Google Drive link syntax without displaying an error
	const GOOGLE_DRIVE_URL_1 = /https:\/\/drive.google.com\/open\?id=/g;
	const GOOGLE_DRIVE_URL_2 = /https:\/\/drive.google.com\/file\/d\//g;
	const GOOGLE_DRIVE_URL_3 = /\/view\?usp=sharing/g;
	if (GOOGLE_DRIVE_URL_1.test(textInput.value) 
			|| GOOGLE_DRIVE_URL_2.test(textInput.value)
			|| GOOGLE_DRIVE_URL_3.test(textInput.value)) {
		$(textInput).val($(textInput).val()
				.replace(GOOGLE_DRIVE_URL_1,'')
				.replace(GOOGLE_DRIVE_URL_2,'')
				.replace(GOOGLE_DRIVE_URL_3,''));
	}
	// After filtering, ensure input does not exceed maximum length
	const MAXTEXT_CLASS = 'input-error-maxtext-' + fieldName.toLowerCase();
	if (textInput.value != '' && textInput.value.length > maxLength) {
		$(textInput).val($(textInput).val().substring(0, maxLength));
		ERRORS.append(ERROR_HEADER + MAXTEXT_CLASS + '">' + fieldName + ' has ' + maxLength + ' character limit</span>');
	}
	// Clear errors on acceptable input
	const HIDDEN = 'hidden';
	const FORMGROUP = 'form-group';
	if (ERRORS.children().length > 0) {
		ERRORS.removeClass(HIDDEN).addClass(FORMGROUP);
	} else {
		ERRORS.addClass(HIDDEN).removeClass(FORMGROUP);
	}
}
