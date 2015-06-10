$.fn.setCursorPosition = function(position){
    if(this.length == 0) return this;
    return $(this).setSelection(position, position);
}

$.fn.setSelection = function(selectionStart, selectionEnd) {
    if(this.length == 0) return this;
    input = this[0];

    if (input.createTextRange) {
        var range = input.createTextRange();
        range.collapse(true);
        range.moveEnd('character', selectionEnd);
        range.moveStart('character', selectionStart);
        range.select();
    } else if (input.setSelectionRange) {
        input.focus();
        input.setSelectionRange(selectionStart, selectionEnd);
    }

    return this;
}

$.fn.focusEnd = function(){
    this.setCursorPosition(this.val().length);
            return this;
}

$(document).ready(function() {
	$('.palette-container').offset({
		top: 120,
		left: 70
	});
	$('#xmlLabel').on('click', function() {
		alert('click');
		$('#xmlText').focus();
		$('#xmlText').focusEnd();
	})
	// Make the palette draggable
	$('.palette-container').draggable();
	
	$('.palette-container').tabs();
	
	$('.paletteItem').mouseenter(function() {
		$(this).css("background-color", "lightgrey");
	});
	
	$('.paletteItem').mouseout(function() {
		$(this).css("background-color", "#EEEEEE");
	});	
});