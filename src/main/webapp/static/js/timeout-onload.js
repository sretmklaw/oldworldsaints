/**
 * Manages display of session timeout notification.
 */
$(document).ready(function() {

	var timeoutPeriod = 1000*60*60*4; // 4 hours
	var startTime = recalculateStartTime();
	var endTime = recalculateEndTime();
	recalculateTimeout();

	// Poll for idle interval once per second.
	setInterval(pollTimeout, 1000);

	// Listener to reset the count-down and interval on any user action.
	$(window).on('click keyup keydown', function () {
		startTime = recalculateStartTime();
		endTime = recalculateEndTime();
		minutesToTimeout = recalculateTimeout();
		// Also hide the warning dialog, when visible.
		var timeoutWarning = $('#timeoutWarning');
		if (timeoutWarning.is(':visible')) {
			timeoutWarning.hide();
			$('#timeoutMask').fadeTo("slow", 0).removeClass('active');
		}
	});

	function pollTimeout() {
		// Determine timeout based on previously calculated end time.
		var currentTime = new Date().getTime();
		var diffMillis = -(currentTime - endTime);
		var minutesToTimeout = Math.round(diffMillis / 60000);
		console.debug('Session timeout in: ' + diffMillis + ' ms')
		// Once timeout is reached, logout and display a notification.
		if (currentTime >= endTime) {
			console.warn('Session timeout limit reached... Logging out.');
			window.location.href = '/logout?hasTimedOut=true';
		}
		// Within warning window, show a count-down with minutes remaining.
		else if (minutesToTimeout < 11) {
			$('#timeoutMask').fadeTo("slow", 0.5).addClass('active');
			$('#timeoutWarning').show();
			$('#remainingTime').text(minutesToTimeout);
		}
	}

	function recalculateStartTime() {
		return new Date().getTime();
	}

	function recalculateEndTime() {
		return new Date( startTime + timeoutPeriod ).getTime();
	}

	function recalculateTimeout() {
		var diffMillis = endTime - startTime;
		console.debug('Resetting session timeout due to user action: is now ' 
				+ endTime + ' (' + diffMillis + ' ms)');
		return Math.round(diffMillis / 60000);
	}

	// Listener to show loading screen on button click
	$('.btn-loading').on('click', function(e) {
		$('#loading').fadeIn(300);
	});

});
