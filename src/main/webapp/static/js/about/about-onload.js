$(document).ready(function() {

	const ABOUT_ACTIVE = 'active';
	const ABOUT_BACKGROUND_CLASS = $('.background');
	const ABOUT_HIDDEN = 'hidden';
	const ABOUT_NO_SCROLL = 'no-scroll';
	const ABOUT_OVERVIEW_MAP_WINDOW = $('#overviewMapWindow');
	const ABOUT_CELESTIAL_MAP_WINDOW = $('#celestialMapWindow');
	const ABOUT_PAGE_MASK = $('#pageMask');
	const ABOUT_SELECTED_CELL = 'selected-cell';
	const ABOUT_UNSELECTED_CELL = 'unselected-cell';

	// Click Overview Map pop-up activation
	$('#overviewMapMini').click(function() {
		ABOUT_OVERVIEW_MAP_WINDOW
			.removeClass(ABOUT_HIDDEN)
			.addClass(ABOUT_ACTIVE);
		ABOUT_PAGE_MASK.addClass(ABOUT_ACTIVE);
		ABOUT_BACKGROUND_CLASS.addClass(ABOUT_NO_SCROLL);
	});

	// Click-off Overview Map pop-up actions
	$(document).on('keydown', function(e) {
		if (e.key == 'Escape') {
			hideOverviewMapContents();
		}
	});
	$('#pageMask,#exitBtn').on('click', function() {
		hideOverviewMapContents();
	});

	function hideOverviewMapContents() {
		if (ABOUT_OVERVIEW_MAP_WINDOW.hasClass(ABOUT_ACTIVE)) {
			ABOUT_OVERVIEW_MAP_WINDOW
				.removeClass(ABOUT_ACTIVE)
				.addClass(ABOUT_HIDDEN);
			ABOUT_PAGE_MASK.removeClass(ABOUT_ACTIVE);
			ABOUT_BACKGROUND_CLASS.removeClass(ABOUT_NO_SCROLL);
		}
	}

	// Special Location Type table row click actions
	$('.location-type-row').click(function() {
		$('#specialLocationTypeIcon').prop('src', 
				$(this).prop('id') + '.svg');
		$('#specialLocationBackground').prop('src', 
				'map-background-' + $(this).data('city') + '-preview.png');
		markSelectedRowAndToggleIcon(
				this, // target
				'.location-type-row', // className
				null); // imgId
	});

	// Click Celestial Map pop-up activation
	$('#celestialMapMini').click(function() {
		ABOUT_CELESTIAL_MAP_WINDOW
			.removeClass(ABOUT_HIDDEN)
			.addClass(ABOUT_ACTIVE);
		ABOUT_PAGE_MASK.addClass(ABOUT_ACTIVE);
		ABOUT_BACKGROUND_CLASS.addClass(ABOUT_NO_SCROLL);
	});

	// Click-off Celestial Map pop-up actions
	$(document).on('keydown', function(e) {
		if (e.key == 'Escape') {
			hideCelestialMapContents();
		}
	});
	$('#pageMask,#exitBtn').on('click', function() {
		hideCelestialMapContents();
	});

	function hideCelestialMapContents() {
		if (ABOUT_CELESTIAL_MAP_WINDOW.hasClass(ABOUT_ACTIVE)) {
			ABOUT_CELESTIAL_MAP_WINDOW
				.removeClass(ABOUT_ACTIVE)
				.addClass(ABOUT_HIDDEN);
			ABOUT_PAGE_MASK.removeClass(ABOUT_ACTIVE);
			ABOUT_BACKGROUND_CLASS.removeClass(ABOUT_NO_SCROLL);
		}
	}

	// Calendar Rite table icon click actions
	$('.rite-cell').click(function() {
		var imgId = '#riteIcon';
		$(imgId + 's').find('td')
			.removeClass(ABOUT_SELECTED_CELL)
			.addClass(ABOUT_UNSELECTED_CELL);
		markSelectedRowAndToggleIcon(
				this, // target
				'.rite-row', // className
				imgId); // imgId
	});

	// Calendar Rite table row click actions
	$('.rite-row').click(function() {
		var imgId = '#riteIcon';
		$(imgId + 's').find('td')
			.removeClass(ABOUT_SELECTED_CELL)
			.addClass(ABOUT_UNSELECTED_CELL);
		markSelectedRowAndToggleIcon(
				this, // target
				'.rite-row', // className
				imgId); // imgId
	});

	// Zodiac Sign table row click actions
	$('.zodiac-sign-row').click(function() {
		var imgId = '#zodiacSignIcon';
		var detailId = '#zodiacDetail';
		if (detailId != null) {
			$(imgId).prop('src', $(this).prop('id') + '.svg');
			$(detailId)
				.empty()
				.append('<span>' + $(this).data('detail') + '</span>');
		} 
		markSelectedRowAndToggleIcon(
				this, // target
				'.zodiac-sign-row', // className
				imgId); // imgId
	});

	// Hour table icon click actions
	$('.hour-cell').click(function() {
		var imgId = '#hourIcon';
		$(imgId + 's').find('td')
			.removeClass(ABOUT_SELECTED_CELL)
			.addClass(ABOUT_UNSELECTED_CELL);
		markSelectedRowAndToggleIcon(
				this, // target
				'.hour-row', // className
				imgId); // imgId
	});

	// Hour table row click actions
	$('.hour-row').click(function() {
		var imgId = '#hourIcon';
		$(imgId + 's').find('td')
			.removeClass(ABOUT_SELECTED_CELL)
			.addClass(ABOUT_UNSELECTED_CELL);
		markSelectedRowAndToggleIcon(
				this, // target
				'.hour-row', // className
				imgId); // imgId
	});

	// Toggle table row click actions
	function markSelectedRowAndToggleIcon(target, className, imgId) {
		$.each($('body').find(className), function() {
			if ($(target).prop('id') == this.id) {
				$(this).addClass('selected-row');
				if (imgId != null) {
					$(imgId + 's').find('td[id=' + this.id + ']')
						.removeClass(ABOUT_UNSELECTED_CELL)
						.addClass(ABOUT_SELECTED_CELL);
				}
			} else {
				$(this).removeClass('selected-row');
			}
		});
	}
});