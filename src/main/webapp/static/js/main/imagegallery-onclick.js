/**
 * This function is used to display location image gallery on Main.
 */
$(document).ready(function() {

	const BODY = 'body';
	const MAIN_ACTIVE = 'active';
	const CLICK_TRIGGER = 'click';
	const MAIN_BACKGROUND_CLASS = $('.background');
	const MAIN_HIDDEN = 'hidden';
	const MAIN_IMAGE_GALLERY_WINDOW = $('#imageGalleryWindow');
	const MAIN_NO_SCROLL = 'no-scroll';
	const MAIN_PAGE_MASK = $('#pageMask');
	const GALLERY_TAB = '.gallery-tab';

	// Click image pop-up activation
	$('#imageGalleryPopupBtn').on(CLICK_TRIGGER, function() {
		MAIN_IMAGE_GALLERY_WINDOW
			.removeClass(MAIN_HIDDEN)
			.addClass(MAIN_ACTIVE);
		MAIN_PAGE_MASK.addClass(MAIN_ACTIVE);
		MAIN_BACKGROUND_CLASS.addClass(MAIN_NO_SCROLL);
	});

	$(BODY).on(CLICK_TRIGGER, '#imageGalleryPrevBtn', function() {
		var currentTab = $(GALLERY_TAB + '.' + MAIN_ACTIVE);
		hideInactiveImageGalleryTabs();
		var prevTab = currentTab.prev(GALLERY_TAB);
		// If first tab, go back to last
		if (prevTab.length === 0) {
			prevTab = $(GALLERY_TAB).last();
		}
		unhideActiveImageGalleryTab(prevTab);
	});

	$(BODY).on(CLICK_TRIGGER, '#imageGalleryNextBtn', function() {
		var currentTab = $(GALLERY_TAB + '.' + MAIN_ACTIVE);
		hideInactiveImageGalleryTabs();
		var nextTab = currentTab.next(GALLERY_TAB);
		// If last tab, go back to first
		if (nextTab.length === 0) {
			nextTab = $(GALLERY_TAB).first();
		}
		unhideActiveImageGalleryTab(nextTab);
	});

	function hideInactiveImageGalleryTabs() {
		$(GALLERY_TAB)
			.fadeOut()
			.removeClass(MAIN_ACTIVE)
			.addClass(MAIN_HIDDEN);
	}

	function unhideActiveImageGalleryTab(imageGalleryTab) {
		if (imageGalleryTab != null) {
			imageGalleryTab
				.fadeIn()
				.addClass(MAIN_ACTIVE)
				.removeClass(MAIN_HIDDEN);
		}
	}

	// Click-off image pop-up actions
	$(document).on('keydown', function(e) {
		if (e.key == 'Escape') {
			hideImagePopupContents();
		}
	});
	$('#pageMask,#imageGalleryExitBtn').on(CLICK_TRIGGER, function() {
		hideImagePopupContents();
	});

	function hideImagePopupContents() {
		if (MAIN_IMAGE_GALLERY_WINDOW.hasClass(MAIN_ACTIVE)) {
			MAIN_IMAGE_GALLERY_WINDOW
				.removeClass(MAIN_ACTIVE)
				.addClass(MAIN_HIDDEN);
			MAIN_PAGE_MASK.removeClass(MAIN_ACTIVE);
			MAIN_BACKGROUND_CLASS.removeClass(MAIN_NO_SCROLL);
		}
	}

});