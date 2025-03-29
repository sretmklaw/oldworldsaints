$(document).ready(function(){
	$('.dropdown-submenu .submenu').on('click', function(e){
		e.preventDefault();
		e.stopPropagation();

		// User Options Button actions on click
		if ($(this).is('#userDropdownBtn')) {
			// Always reset all drop-down inputs
			resetNavbarTextAndDropdownInputs(null, null);
			// On Enter
			if (!($('#userDropdownIcon').hasClass('activated'))) {
				hideCommemorationSearchOptions();
				hideCommemorationSearchByTypeOptions();
				hideCommemorationSearchByDateOptions();
				hideCommemorationSearchByPatronageOptions();
				resetTextAndDropdownInputs([
					'commByNameInput',
					'nationDropdown',
					'centuryDropdown',
					'tagDropdown',
					'calendarDropdown',
					'patronageTypeDropdown',
					'constellationDropdown'
				], false, null); // Reset without disabling
				resetTextAndDropdownInputs([
					'commByNameSearchBtn',
					'nationSearchBtn',
					'centurySearchBtn',
					'tagSearchBtn',
					'daySearchBtn',
					'monthDropdown',
					'dayDropdown',
					'patronageSearchBtn',
					'patronageSubtypeDropdown',
					'patronageDropdown',
					'starSearchBtn',
					'starDropdown'
				], true, null); // Reset and disable
				showUserOptions();
			} 
			// On Exit
			else {
				hideUserOptions();
			}
		}
		// Commemoration Search Top Menu actions on click
		else if ($(this).is('#commDropdownBtn')) {
			// Always reset all drop-down inputs
			resetNavbarTextAndDropdownInputs(null, null);
			// On Enter
			if (!($('#commDropdownIcon').hasClass('activated'))) {
				hideUserOptions();
				showCommemorationSearchOptions();
				hideCommemorationSearchByTypeOptions();
				hideCommemorationSearchByDateOptions();
				hideCommemorationSearchByPatronageOptions();
				hideCommemorationSearchByStarOptions();
			} 
			// On Exit
			else {
				hideCommemorationSearchOptions();
				hideCommemorationSearchByTypeOptions();
				hideCommemorationSearchByDateOptions();
				hideCommemorationSearchByPatronageOptions();
				hideCommemorationSearchByStarOptions();
			}
		}

		// Commemoration Search by Type Sub-Menu actions on click
		else if ($(this).is('#commDropdownByDetailsBtn')) {
			// Always reset all drop-down inputs
			resetNavbarTextAndDropdownInputs(null, null);
			// On Enter
			if (!($('#commDropdownByDetailsIcon').hasClass('activated'))) {
				hideUserOptions();
				$('#commByNameGroup').slideUp();
				showCommemorationSearchByTypeOptions();
				hideCommemorationSearchByDateOptions();
				hideCommemorationSearchByPatronageOptions();
				hideCommemorationSearchByStarOptions();
			} 
			// On Exit
			else {
				$('#commByNameGroup').slideDown();
				hideCommemorationSearchByTypeOptions();
				hideCommemorationSearchByDateOptions();
				hideCommemorationSearchByPatronageOptions();
				hideCommemorationSearchByStarOptions();
			}
		}
		// Commemoration Search by Date Sub-Menu actions on click
		else if ($(this).is('#commDropdownByDateBtn')) {
			// Always reset all drop-down inputs
			resetNavbarTextAndDropdownInputs(null, null);
			// On Enter
			if (!($('#commDropdownByDateIcon').hasClass('activated'))) {
				hideUserOptions();
				showCommemorationSearchByDateOptions();
			} 
			// On Exit
			else {
				hideCommemorationSearchByDateOptions();
			}
		}
		// Commemoration Search by Patronage Sub-Menu actions on click
		else if ($(this).is('#commDropdownByPatronBtn')) {
			// Always reset all drop-down inputs
			resetNavbarTextAndDropdownInputs(null, null);
			// On Enter
			if (!($('#commDropdownByPatronIcon').hasClass('activated'))) {
				hideUserOptions();
				showCommemorationSearchByPatronageOptions();
			} 
			// On Exit
			else {
				hideCommemorationSearchByPatronageOptions();
			}
		}
		// Commemoration Search by Star Sub-Menu actions on click
		else if ($(this).is('#commDropdownByStarBtn')) {
			// Always reset all drop-down inputs
			resetNavbarTextAndDropdownInputs(null, null);
			// On Enter
			if (!($('#commDropdownByStarIcon').hasClass('activated'))) {
				hideUserOptions();
				showCommemorationSearchByStarOptions();
			} 
			// On Exit
			else {
				hideCommemorationSearchByStarOptions();
			}
		}
	});
});

function showUserOptions() {
	$('#userDropdownDiv').slideDown();
	$('#userDropdownIcon').addClass('activated');
}

function hideUserOptions() {
	$('#userDropdownDiv').slideUp();
	$('#userDropdownIcon').removeClass('activated');
}

function showCommemorationSearchOptions() {
	$('#commDropdownDiv').slideDown();
	$('#commByNameGroup').slideDown();
	$('#commDropdownIcon').addClass('activated');
}

function hideCommemorationSearchOptions() {
	$('#commDropdownDiv').slideUp();
	$('#commByNameGroup').slideUp();
	$('#commDropdownIcon').removeClass('activated');
}

function showCommemorationSearchByTypeOptions() {
	$('#commDropdownByDetailsDiv').slideDown();
	$('#commDropdownByDetailsIcon').addClass('activated');
}

function hideCommemorationSearchByTypeOptions() {
	$('#commDropdownByDetailsDiv').slideUp();
	$('#commDropdownByDetailsIcon').removeClass('activated');
}

function showCommemorationSearchByDateOptions() {
	$('#commByDetailsGroup').slideUp();
	$('#commDropdownByDetailsBtn').slideUp();
	$('#commDropdownByDateDiv').slideDown();
	$('#commDropdownByDateIcon').addClass('activated');
	$('#commDropdownByPatronDiv').slideUp();
	$('#commDropdownByPatronIcon').removeClass('activated');
	$('#commDropdownByStarDiv').slideUp();
	$('#commDropdownByStarIcon').removeClass('activated');
}

function hideCommemorationSearchByDateOptions() {
	$('#commByDetailsGroup').slideDown();
	$('#commDropdownByDetailsBtn').slideDown();
	$('#commDropdownByDateDiv').slideUp();
	$('#commDropdownByDateIcon').removeClass('activated');
}

function showCommemorationSearchByPatronageOptions() {
	$('#commByDetailsGroup').slideUp();
	$('#commDropdownByDetailsBtn').slideUp();
	$('#commDropdownByDateDiv').slideUp();
	$('#commDropdownByDateIcon').removeClass('activated');
	$('#commDropdownByPatronDiv').slideDown();
	$('#commDropdownByPatronIcon').addClass('activated');
	$('#commDropdownByStarDiv').slideUp();
	$('#commDropdownByStarIcon').removeClass('activated');
}

function hideCommemorationSearchByPatronageOptions() {
	$('#commByDetailsGroup').slideDown();
	$('#commDropdownByDetailsBtn').slideDown();
	$('#commDropdownByPatronDiv').slideUp();
	$('#commDropdownByPatronIcon').removeClass('activated');
}

function showCommemorationSearchByStarOptions() {
	$('#commByDetailsGroup').slideUp();
	$('#commDropdownByDetailsBtn').slideUp();
	$('#commDropdownByDateDiv').slideUp();
	$('#commDropdownByDateIcon').removeClass('activated');
	$('#commDropdownByPatronDiv').slideUp();
	$('#commDropdownByPatronIcon').removeClass('activated');
	$('#commDropdownByStarDiv').slideDown();
	$('#commDropdownByStarIcon').addClass('activated');
}

function hideCommemorationSearchByStarOptions() {
	$('#commByDetailsGroup').slideDown();
	$('#commDropdownByDetailsBtn').slideDown();
	$('#commDropdownByStarDiv').slideUp();
	$('#commDropdownByStarIcon').removeClass('activated');
}
