/**
 * Page load actions
 */
window.addEventListener("load", function() {

	// Displays page loading icon until completed.
	$('#loading').fadeOut(300);

	// Add document title as page tab title.
	this.document.title = "Old World Saints - " + $('#title').text();
});
