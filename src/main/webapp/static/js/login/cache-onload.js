/**
 * This function caches all frequently-used image files on site landing page.
 */
const CONTENT_TYPE_HEADER = 'Content-Type';
if ('caches' in window) {
	caches.open('static').then(function(cache) {
		$.each($('#cachedImages').find('img'), function() {
			var img = $(this);
			cache.put(img.data('path'), new Response(
					document.querySelector('#' + img.data('id')).outerHTML,
					{ headers: {CONTENT_TYPE_HEADER: img.data('filetype')} }
			));
		});
	});
}
