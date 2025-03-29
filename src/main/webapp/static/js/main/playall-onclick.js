$(document).ready(function() {

	var amUrls = [];
	var pmUrls = [];
	$('.hour').each(function(i) {
		if (i < 3) {
			$(this).find('audio').each(function() {
				amUrls.push(this.id);
			});
		} else {
			$(this).find('audio').each(function() {
				pmUrls.push(this.id);
			});
		}
	});

	$('#amReadingsAudioBtn').click(function() {
		playAllAudioForMeridian($('#amReadingsAudioBtn'), amUrls);
	});
	$('#pmReadingsAudioBtn').click(function() {
		playAllAudioForMeridian($('#pmReadingsAudioBtn'), pmUrls);
	});
	$('audio').on('play', function() {
		playSelectedAudio(this);
	});
});

function playAllAudioForMeridian(playBtn, urls) {

	// Toggle activation highlighting
	$('.btn-playall').not(playBtn).removeClass('playall-active');
	playBtn.addClass('playall-active');

	// Begin play track-list from the beginning
	var player = setupTrackList(playBtn, urls);
	$(player)[0].play();
	$(player).addClass('autoplay');
	playSelectedAudio(player);
}

function setupTrackList(playBtn, urls) {

	const lastPlayerIndex = $(urls).length;
	var firstPlayer, prevPlayer, player;

	$(urls).each(function(i) {
		if (i == 0) {
			firstPlayer = player = $('#' + this);
		} else {
			prevPlayer = player;
			player = $('#' + this);
			$(prevPlayer).attr('data-next', ''+this);
			$(prevPlayer)[0].addEventListener('ended', function() {
				var prevPlayer = $(this);
				var nextPlayer = $('#' + $(this).data('next'));
				prevPlayer.removeClass('playing autoplay');
				$(nextPlayer)[0].play();
				$(nextPlayer).addClass('autoplay');
			});
			// If this is the last player in the list, add a listener 
			// to de-activate the Play All Audio button when finished.
			if (i == lastPlayerIndex-1) {
				$(player)[0].addEventListener('ended', function() {
					playBtn.removeClass('playall-active');
				});
			}
		}
	});
	return firstPlayer;
}

function playSelectedAudio(playingAudio) {

	const playingAudioContent = $(playingAudio).closest('.result-content');
	const playingAudioHeader = $('[data-target="#' + $(playingAudioContent).prop('id') + '"]');
	const playingAudioTabPane = $(playingAudioHeader).closest('.hour');
	const playingAudioTab = $('a[href="#' + $(playingAudioHeader).closest('.hour').prop('id') + '"]');

	if (!$(playingAudio).hasClass('playing')) {
		if (!$(playingAudio).hasClass('autoplay')) {
			$('.btn-playall').removeClass('playall-active');
		}
		$(playingAudio).addClass('playing');

		// Stop all other playing audio
		$('audio').not(playingAudio).each(function() {
			$(this).removeClass('playing autoplay');
			$(this)[0].pause();
			$(this)[0].currentTime = 0;
		});

		// Collapse all hour reading accordion content
		$('.nav-item').not(playingAudioTab).removeClass('active');
		$('.hour').not(playingAudioTabPane).removeClass('active show');
		$('.result-header').not(playingAudioHeader).removeClass('active').addClass('collapsed');
		$('.result-header-parent').removeClass('active').addClass('collapsed');
		$('.result-content').not(playingAudioContent).removeClass('active show').addClass('collapsed').slideUp(600);
		$('.result-content-parent').removeClass('active show').addClass('collapsed').slideUp(600);

		// Then, un-collapse only the selected result
		$(playingAudioTab).addClass('active');
		$(playingAudioTabPane).addClass('active show');
		$(playingAudioHeader).addClass('active').removeClass('collapsed');
		$(playingAudioContent).slideDown(600);

		// Ensure that we reset the playing status on completion
		$(playingAudio)[0].addEventListener('ended', function() {
			if ($(this).hasClass('playing')) {
				$(this).removeClass('playing');
				$(this)[0].currentTime = 0;
			}
		});
	}
}
