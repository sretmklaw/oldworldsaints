/**
 * This script uses the DataTable DOM to populate sorted and paginated tables
 * used on the Administration screen. DOM override syntax is as follows:
 * 
 *   <div class="top">
 *     <div class="row1">
 *       <{f=filter} class="filter">
 *       <div class="dataTables_buttons">
 *       {buttons}
 *       <//div>
 *     <//div>
 *     <div class="row2">
 *       {p=pagination}
 *       {l=length}
 *     <//div>
 *   <//div>
 *   {t=table}
 */
$(document).ready(function() {

	const FEATURE_FLAG_INPUT_REGEX = /[^a-zA-Z0-9 \\|\/'",.!?:\-\(\)\[\]{}@#$%^&*_+=~<>]/g;
	const ADMIN_UNLOCK_SYMBOL = '\u2705';
	const ADMIN_LIMIT_SYMBOL = '\u26D4';
	const ADMIN_LOCK_SYMBOL = '\u274C';
	const ADMIN_ACTIVE = 'active';
	const ADMIN_FLAG_VALUE = '.flag-value';
	const ADMIN_HIDDEN = 'hidden';
	const ADMIN_BTN_CLASS = 'class="paginate_button btn btn-secondary mx-auto';
	const ADMIN_SORT_TABLE_PAGETYPE = 'simple';
	const ADMIN_SORT_TABLE_COLDEFS = [{targets:[1],orderData:[0,1]}];
	const ADMIN_ERROR_MSG = '<span class="alert">Error during callback.</span>';
	const ADMIN_NONE_SELECTED_MSG = '<span class="alert">Must select at least one row.</span>';
	const ADMIN_GREEN = 'GREEN';
	const ADMIN_YELLOW = 'YELLOW';
	const ADMIN_RED = 'RED';

	/**
	 * User Details Table
	 */
	$('#sortableTableUserDetails').DataTable({
		dom: '<"top"<"row1"f<"#userDetailsBtns">><"row2"pl>>t',
		pagingType: ADMIN_SORT_TABLE_PAGETYPE,
		columnDefs: ADMIN_SORT_TABLE_COLDEFS,
		language: { search: "", sSearchPlaceholder: "Search..." }
	});
	// Add User Detail table control buttons
	$("#userDetailsBtns").addClass("dataTables_buttons").html(
			'<button id="unlockUserBtn" ' + ADMIN_BTN_CLASS + ' unlock"' 
					+ ' title="Unlock User Account">' 
					+ ADMIN_UNLOCK_SYMBOL + '</button>' 
			+ '<button id="limitUserBtn" ' + ADMIN_BTN_CLASS + ' limit"' 
					+ ' title="Restrict User Requests">' 
					+ ADMIN_LIMIT_SYMBOL + '</button>'
			+ '<button id="lockUserBtn" ' + ADMIN_BTN_CLASS + ' lock"' 
					+ ' title="Lock User Account">' 
					+ ADMIN_LOCK_SYMBOL + '</button>');
	// Define button functionality to set all checked to 'Unlocked' (Green)
	$('#unlockUserBtn').click(function(e) {
		e.preventDefault(); // Prevent form submission
		updateUsers(ADMIN_GREEN); // statusCode
	});
	// Define button functionality to set all checked to 'Limit' (Yellow)
	$('#limitUserBtn').click(function(e) {
		e.preventDefault(); // Prevent form submission
		updateUsers(ADMIN_YELLOW); // statusCode
	});
	// Define button functionality to set all checked to 'Locked' (Red)
	$('#lockUserBtn').click(function(e) {
		e.preventDefault(); // Prevent form submission
		updateUsers(ADMIN_RED); // statusCode
	});
	// Define button functionality to check all User Requests check-boxes
	$('#userSelectAllCheckbox').click(function (e) {
		e.preventDefault(); // Prevent form submission
		selectAll('#userSelectAllCheckbox', // selectAllCheckbox
				'.user-checkbox'); // targetCheckboxClassName
	});

	/* 
	 * Bugfix Requests Table
	 */
	$('#sortableTableBugfixRequests').DataTable( {
		dom: '<"top"<"row1"f<"#bugfixRequestsBtns">><"row2"pl>>t',
		pagingType: ADMIN_SORT_TABLE_PAGETYPE,
		columnDefs: ADMIN_SORT_TABLE_COLDEFS,
		language: { search: "", sSearchPlaceholder: "Search..." }
	} );
	// Add User Detail table control buttons
	$("#bugfixRequestsBtns").addClass("dataTables_buttons").html(
			'<button id="closeBugfixBtn" ' + ADMIN_BTN_CLASS + ' lock"' 
					+ ' title="Close Request">' 
					+ ADMIN_LOCK_SYMBOL + '</button>');
	// Define button functionality to set all checked to 'Closed' (Green)
	$('#closeBugfixBtn').click(function(e) {
		e.preventDefault(); // Prevent form submission
		update('/update-reqs', // url
				'.bugfix-reqs-checkbox', // targetCheckboxClass
				'bugfixReqs', // targetIdPrefix
				true, // val
				true); // shouldRetire
	});
	// Define button functionality to check all Bugfix Requests check-boxes
	$('#bugfixReqsSelectAllCheckbox').click(function (e) {
		e.preventDefault(); // Prevent form submission
		selectAll('#bugfixReqsSelectAllCheckbox', // selectAllCheckbox
				'.bugfix-reqs-checkbox'); // targetCheckboxClassName
	});

	/* 
	 * Entry Requests Table
	 */
	$('#sortableTableEntryRequests').DataTable( {
		dom: '<"top"<"row1"f<"#entryRequestsBtns">><"row2"pl>>t',
		pagingType: ADMIN_SORT_TABLE_PAGETYPE,
		columnDefs: ADMIN_SORT_TABLE_COLDEFS,
		language: { search: "", sSearchPlaceholder: "Search..." }
	} );
	// Add User Detail table control buttons
	$("#entryRequestsBtns").addClass("dataTables_buttons").html(
			'<button id="closeEntryBtn" ' + ADMIN_BTN_CLASS + ' lock"' 
					+ ' title="Close Request">' 
					+ ADMIN_LOCK_SYMBOL + '</button>');
	// Define button functionality to set all checked to 'Closed' (Green)
	$('#closeEntryBtn').click(function(e) {
		e.preventDefault(); // Prevent form submission
		update('/update-reqs', // url
				'.entry-reqs-checkbox', // targetCheckboxClass
				'entryReqs', // targetIdPrefix
				true, // val
				true); // shouldRetire
	});
	// Define button functionality to check all Entry Requests check-boxes
	$('#entryReqsSelectAllCheckbox').click(function (e) {
		e.preventDefault(); // Prevent form submission
		selectAll('#entryReqsSelectAllCheckbox', // selectAllCheckbox
				'.entry-reqs-checkbox'); // targetCheckboxClassName
	});

	/* 
	 * Entity Update History Table
	 */
	$('#sortableTableEntityUpdateHistory').DataTable( {
		dom: '<"top"<"row1"f<"#exportDiffsBtns">><"row2"pl>>t',
		pagingType: ADMIN_SORT_TABLE_PAGETYPE,
		columnDefs: ADMIN_SORT_TABLE_COLDEFS,
		language: { search: "", sSearchPlaceholder: "Search..." }
	} );
	// Add User Detail table control buttons
	$("#exportDiffsBtns").addClass("dataTables_buttons").html(
			'<button id="exportDiffsBtn" ' + ADMIN_BTN_CLASS + ' unlock"' 
					+ ' title="Export Change">' 
					+ ADMIN_UNLOCK_SYMBOL + '</button>'
			+ '<button id="retireHistoryBtn" ' + ADMIN_BTN_CLASS + ' lock"' 
					+ ' title="Retire Change">' 
					+ ADMIN_LOCK_SYMBOL + '</button>');
	// Define button functionality to generate spreadsheet for all checked
	$('#exportDiffsBtn').click(function(e) {
		e.preventDefault(); // Prevent form submission
		downloadInNewTab();
	});
	// Define button functionality to set all checked to 'Closed' (Green)
	$('#retireHistoryBtn').click(function(e) {
		e.preventDefault(); // Prevent form submission
		update('/update-history', // url
				'.export-diffs-checkbox', // targetCheckboxClass
				'exportDiffs', // targetIdPrefix
				true, // val
				true); // shouldRetire
	});
	// Define button functionality to check all Export Diffs check-boxes
	$('#exportDiffsSelectAllCheckbox').click(function (e) {
		e.preventDefault(); // Prevent form submission
		selectAll('#exportDiffsSelectAllCheckbox', // selectAllCheckbox
				'.export-diffs-checkbox'); // targetCheckboxClassName
	});

	/* 
	 * Feature Flag Table
	 */
	$('#sortableTableFeatureFlags').DataTable( {
		dom: '<"top"<"row1"f<"#featureFlagBtns">><"row2"pl>>t',
		pagingType: ADMIN_SORT_TABLE_PAGETYPE,
		columnDefs: ADMIN_SORT_TABLE_COLDEFS,
		language: { search: "", sSearchPlaceholder: "Search..." }
	} );
	// Add Feature Flag table control buttons
	$("#featureFlagBtns").addClass("dataTables_buttons").html(
			'<button id="activateFlagBtn" ' + ADMIN_BTN_CLASS + ' unlock"' 
					+ ' title="Enable Feature">' 
					+ ADMIN_UNLOCK_SYMBOL + '</button>' 
			+ '<button id="disableFlagBtn" ' + ADMIN_BTN_CLASS + ' lock"' 
					+ ' title="Disable Feature">' 
					+ ADMIN_LOCK_SYMBOL + '</button>');
	// Define button functionality to set all checked to 'Active' (Green)
	$('#activateFlagBtn').click(function(e) {
		e.preventDefault(); // Prevent form submission
		updateFeatureFlags(true); // val
	});
	// Define button functionality to set all checked to 'Disabled' (Red)
	$('#disableFlagBtn').click(function(e) {
		e.preventDefault(); // Prevent form submission
		updateFeatureFlags(false); // val
	});
	// Define button functionality to check all Feature Flag entry check-boxes
	var featureFlagSelectAllCheckbox = $("#featureFlagSelectAllCheckbox");
	featureFlagSelectAllCheckbox.click(function (e) {
		e.preventDefault(); // Prevent form submission
		selectAll(featureFlagSelectAllCheckbox, // selectAllCheckbox
				'.feature-flag-checkbox'); // targetCheckboxClassName
	});
	// Prevent use of control characters in feature flag input fields
	$(".flag-value").on('input', function() {
		verifyFeatureFlagTextInput(this);
	});

	/**
	 * This is the update function used to perform user specific updates
	 */
	function updateUsers(status) {
		var targetStatusId = '#userStatusCallback';
		var targetSelectAllCheckboxId = '#userSelectAllCheckbox';
		var targetCheckboxClass = '.user-checkbox';
		var selectedElements = [];
		var idArray = [];
		$(targetCheckboxClass).each(function () {
			if (this.checked == true) {
				selectedElements.push(this);
				idArray.push(this.value);
			}
		});
		if (selectedElements.length > 0) {
			// Then submit request to batch update all affected records in the DB
			$.ajax({
				type : 'get',
				url : '/admin/update-users' 
						+ '?byIds=' + idArray 
						+ '&withStatus=' + status,
				success : function(id) {
					$(id).each(function () {
						$('#userStatus' + this).html('<img ' 
								+ 'class="img-icon-square-small" '
								+ 'src="/' + status + '.svg">');
					});
					$(targetStatusId).empty().addClass(ADMIN_HIDDEN);
				},
				error : function() {
					$(targetStatusId).empty()
						.append(ADMIN_ERROR_MSG)
						.removeClass(ADMIN_HIDDEN);
				}
			}).done(function() {
				// Clear all checked boxes, including select-all
				$(targetSelectAllCheckboxId).addClass(ADMIN_ACTIVE);
				selectAll(targetSelectAllCheckboxId, // selectAllCheckbox
						targetCheckboxClass); // targetCheckboxClassName
			});
		} else {
			statusCallback.empty()
				.append(ADMIN_NONE_SELECTED_MSG)
				.removeClass(ADMIN_HIDDEN);
		}
	}

	/**
	 * This is the update function used to perform feature flag specific updates
	 */
	function updateFeatureFlags(bool) {
		var targetStatusId = '#featureFlagStatusCallback';
		var targetSelectAllCheckboxId = '#featureFlagSelectAllCheckbox';
		var targetCheckboxClass = '.feature-flag-checkbox';
		var selectedElements = [];
		$(targetCheckboxClass).each(function () {
			if (this.checked == true) {
				selectedElements.push(this);
			}
		});
		var str = null;
		if (selectedElements.length > 0) {
			$(selectedElements).each(function () {
				var parentRow = $(this).parents('tr');
				if (parentRow.find(ADMIN_FLAG_VALUE) != null) {
					str = parentRow.find(ADMIN_FLAG_VALUE).val();
				}
				var idArray = [];
				idArray.push(this.value); // For Feature Flag, ID array is always singleton
				var url = '/admin/update-flags' 
					+ '?byIds=' + idArray 
					+ '&withBoolean=' + bool
					+ ((str != null) ? '&withString=' + str : '');
				$.ajax({
					type : 'get',
					url : url,
					success : function(id) {
						$(id).each(function () {
							var color = (bool === true) ? ADMIN_GREEN : ADMIN_RED;
							$('#featureFlagStatus' + this).html('<img ' 
									+ 'class="img-icon-square-small" '
									+ 'src="/' + color + '.svg">');
						});
						$(targetStatusId).empty().addClass(ADMIN_HIDDEN);
					},
					error : function() {
						$(targetStatusId)
							.empty()
							.append(ADMIN_ERROR_MSG)
							.removeClass(ADMIN_HIDDEN);
					}
				});
			});
			// Clear all checked boxes, including select-all
			$(targetSelectAllCheckboxId).addClass(ADMIN_ACTIVE);
			selectAll(targetSelectAllCheckboxId, // selectAllCheckbox
					targetCheckboxClass); // targetCheckboxClassName
		} else {
			$('#featureFlagStatusCallback').empty()
				.append(ADMIN_NONE_SELECTED_MSG)
				.removeClass(ADMIN_HIDDEN);
		}
	}

	/**
	 * This is the unified update function for all other button click updates above
	 */
	function update(url, targetCheckboxClass, targetIdPrefix, bool, shouldRetire) {
		// First, construct an array of IDs for elements requiring change
		var selectedElements = [];
		var idArray = [];
		$(targetCheckboxClass).each(function () {
			if (this.checked == true) {
				selectedElements.push(this);
				idArray.push(this.value);
			}
		});
		var statusCallback = $('#' + targetIdPrefix + 'StatusCallback');
		var selectAllCheckboxId = '#' + targetIdPrefix + 'SelectAllCheckbox';
		if (selectedElements.length > 0) {
			// Then submit request to batch update all affected records in the DB
			$.ajax({
				type : 'get',
				url : '/admin' + url 
						+ '?byIds=' + idArray 
						+ '&withBoolean=' + bool,
				success : function(id) {
					$(id).each(function () {
						var color;
						// For User hasAdminLock only, update the status icon
						if (targetIdPrefix === "user") {
							color = (bool === true) ? ADMIN_RED : ADMIN_GREEN;
							$('#userStatus' + this).html('<img ' 
									+ 'class="img-icon-square-small" '
									+ 'src="/' + color + '.svg">');
						} 
					});
					statusCallback.empty().addClass(ADMIN_HIDDEN);
				},
				error : function() {
					statusCallback.empty()
						.append(ADMIN_ERROR_MSG)
						.removeClass(ADMIN_HIDDEN);
				}
			}).done(function() {
				if (shouldRetire != null && shouldRetire == true) {
					$(selectedElements).each(function() { 
						$(this).parents('tr').addClass('retired');
						this.disabled = true; 
					});
				}
				// Clear all checked boxes, including select-all
				$(selectAllCheckboxId).addClass(ADMIN_ACTIVE);
				selectAll(selectAllCheckboxId, // selectAllCheckbox
						targetCheckboxClass); // targetCheckboxClassName
			});
		} else {
			statusCallback.empty()
				.append(ADMIN_NONE_SELECTED_MSG)
				.removeClass(ADMIN_HIDDEN);
		}
	}

	/**
	 * This handles checking and un-checking select-all check-boxes
	 */
	function selectAll(selectAllCheckboxId, targetCheckboxClassName) {
		$.getScript("reset-dependent-inputs.js", function() {
			toggleSelectedCheckboxes(selectAllCheckboxId, targetCheckboxClassName);
		})
		// Ensure that the select-all check-box is set on call-back
		.done(function() {
			var isActive = $(selectAllCheckboxId).hasClass(ADMIN_ACTIVE);
			$(selectAllCheckboxId).prop('checked', isActive);
		});
	}

	/**
	 * This handles the download of export entity update history
	 */
	function downloadInNewTab() {
		// First, construct an array of IDs for elements requiring change
		var selectedElements = [];
		var idArray = [];
		$('.export-diffs-checkbox').each(function() {
			if (this.checked == true) {
				selectedElements.push(this);
				idArray.push(this.value);
			}
		});
		if (selectedElements.length > 0) {
			// Download as attachment in new tab
			window.open('/admin/export-diffs?byIds=' + idArray, '_blank');
			// Clear all checked boxes, including select-all
			$('#exportDiffsSelectAllCheckbox').addClass(ADMIN_ACTIVE);
			selectAll('#exportDiffsSelectAllCheckbox', // selectAllCheckbox
					'.export-diffs-checkbox'); // targetCheckboxClassName
			// Clear status
			$('#exportStatusCallback').empty().addClass(ADMIN_HIDDEN);
		} else {
			$('#exportStatusCallback')
				.empty()
				.append(ADMIN_NONE_SELECTED_MSG)
				.removeClass(ADMIN_HIDDEN);
		}
	}

	/**
	 * Prevent invalid characters in feature flag input fields
	 */
	function verifyFeatureFlagTextInput(textInput) {
		if (FEATURE_FLAG_INPUT_REGEX.test(textInput.value)) {
			$(textInput).val($(textInput).val().replace(FEATURE_FLAG_INPUT_REGEX,''));
		}
	}
} );