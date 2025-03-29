/**
 * This script defines the accordion transition effects used
 * to smoothly expand and hide contents on parent tab click.
 */
const RESULT_HEADER = '.result-header';
const RESULT_HEADER_PARENT = '.result-header-parent';
const RESULT_CONTENT = '.result-content';
const RESULT_CONTENT_PARENT = '.result-content-parent';
const ACTIVE = 'active';
const COLLAPSED = 'collapsed';
const SHOW = 'show';

$(document).ready(function() {

	// Focus on targeted section from redirect, where applicable.
	focusOnActiveParagraph($('.redirected'));

	$('body').on('click', RESULT_HEADER_PARENT, function() {
		var targetHeader = $(this);
		var targetContent = $(this).next(RESULT_CONTENT);
		$.getScript("../reset-dependent-inputs.js", function() {
			deselectAllOthers(
					targetHeader, 
					null,
					targetContent, 
					null); 
		}).done(function() {
			toggleContentForHeader(targetHeader, null, targetContent, true);
			focusOnActiveParagraph(targetHeader);
		});
	})

	/**
	 * Result header collapse behavior - note that we must re-bind 
	 * each target after query call-back, rather than simply referencing 
	 * the original bind target in a static manner, since these will change
	 * while traversing search results.
	 */
	$('body').on('click', RESULT_HEADER, function() {
		var targetHeader = $(this);
		var targetContent = $(this).next(RESULT_CONTENT);
		var targetContentParent = $(this).parents(RESULT_CONTENT_PARENT);
		var targetHeaderParent = $('.result-header-parent[data-target="#'+targetContentParent.prop('id')+'"]');
		$.getScript("../reset-dependent-inputs.js", function() {
			deselectAllOthers(
					targetHeader, 
					targetHeaderParent,
					targetContent, 
					targetContentParent); 
		}).done(function() {
			toggleContentForHeader(targetHeader, targetHeaderParent, targetContent, true);
			focusOnActiveParagraph(targetHeader);
		});
	});

	$('.tab').on('click', function() {
		var hash = $(this).prop('hash');
		var targetContent;
		if (hash != null) {
			targetContent = hash.replace('#','');
		}
		// 'About' page specific tab filtering
		var targetHeader;
		if (targetContent != null && targetContent.startsWith('how-to-use')) {
			targetHeader = '#how-to-use-header';
			targetContent = '#how-to-use-content';
		}
		$.getScript("../reset-dependent-inputs.js", function() {
			deselectAllOthers(
					$(targetHeader), 
					null,
					$(targetContent), 
					null);
		}).done(function() {
			toggleContentForHeader($('#dayTab'), null, $('#dayTabContents'), false);
			focusOnActiveParagraph($('#tabs'));
		});
	});
});

function focusOnActiveParagraph(activeParagraph) {
	if (activeParagraph.length > 0) {
		$([document.documentElement, document.body]).animate({
			scrollTop: activeParagraph.offset().top
		}, 500);
	}
}

function toggleContentForHeader(targetHeader, targetHeaderParent, targetContent, canActivate) {
	// Toggle the clicked button
	if (targetHeader.hasClass(ACTIVE)) {
		targetHeader
			.removeClass(ACTIVE);
		targetContent
			.removeClass(SHOW)
			.addClass(COLLAPSED);
	} else if (canActivate) {
		targetHeader
			.addClass(ACTIVE);
		targetContent
			.addClass(SHOW)
			.removeClass(COLLAPSED);
	}
	// Also toggle the clicked button's parent button, where applicable.
	if (targetHeaderParent != null) {
		if (!targetHeaderParent.hasClass(ACTIVE) 
				&& targetHeader.hasClass(ACTIVE)) {
			targetHeaderParent
				.addClass(ACTIVE);
		}
	}
}