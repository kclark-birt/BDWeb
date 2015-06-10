$(document).ready(function() {	
	// Check if user is logged in
	if($.cookie('actToken') == null) {
	}
	
	$('.rptdesignTitle').html('new report ' + new Date($.now()) + '.rptdesign');
});