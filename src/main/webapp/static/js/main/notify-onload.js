/**
 * This function is used to conditionally display active 
 * application notifications to users upon navigation to Main.
 */
$(document).ready(function() {

	const MAINTENANCE_NOTIFICATION = '#maintenanceNotification';

	$('#acknowledgeMaintenanceNotification').on('click', function() {
		$.ajax({ 
			type:"GET", 
			url:'/hide-maintenance-notification' 
		}).done(function() {
			$(MAINTENANCE_NOTIFICATION).fadeOut(100);
		});
	});

	$(function() {
		$('#byDateInput').datepicker({ dateFormat: 'yy-mm-dd' }).val();
	});

});