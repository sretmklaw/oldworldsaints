$(document).ready(function() {

	var navbarToggle = $('#navbarToggle');
	var navbarContents = $('#navbarContents');
	var pageMask = $('#pageMask');

	navbarToggle.on('click', function() {
		if (!navbarContents.hasClass('active')) {
			pageMask.fadeTo("slow", 0.5);
			pageMask.addClass('active');
			navbarToggle.addClass('active');
			navbarContents.addClass('active');
			setTimeout(function () { 
				$(navbarContents).find('div').removeClass('hidden');
			}, 300);
		} else {

			// Reset menu option expander buttons
			$('.bullet').each(function() {
				$(this).addClass('deactivated');
				$(this).removeClass('activated');
			});
			// Reset menu option expander content
			$('.dropdown-menu').each(function() {
				$(this).slideUp();
			});
			// Reset drop-down menu options
			$('.dropdown-option').each(function() {
				$(this).val($("this option:first").val());
			});
			// Disable drop-down menu search buttons
			$('.search').each(function() {
				$(this).prop('disabled', true);
			});
			// Hide all navigation bar contents
			$(navbarContents).find('div').addClass('hidden');
			navbarContents.removeClass('active');
			navbarToggle.removeClass('active');

			// Unlock page content
			pageMask.removeClass('active');
			pageMask.fadeTo("slow", 0);
		}
	});

	pageMask.on('click', function() {
		if (navbarContents.hasClass('active')) {

			// Reset menu option expander buttons
			$('.bullet').each(function() {
				$(this).addClass('deactivated');
				$(this).removeClass('activated');
			});
			// Reset menu option expander content
			$('.dropdown-menu').each(function() {
				$(this).slideUp();
			});
			// Reset drop-down menu options
			$('.dropdown-option').each(function() {
				$(this).val($("this option:first").val());
			});
			// Disable drop-down menu search buttons
			$('.search').each(function() {
				$(this).prop('disabled', true);
			});
			// Hide all navigation bar contents
			$(navbarContents).find('div').addClass('hidden');
			navbarContents.removeClass('active');
			navbarToggle.removeClass('active');

			// Unlock page content
			pageMask.removeClass('active');
			pageMask.fadeTo("slow", 0);
		}
	});
});