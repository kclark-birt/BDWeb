$(document).ready(function() {
	$('.palette-container').offset({
		top: 120,
		left: 70
	});
	
	$('.designArea-container').tabs();
    
    var editor = CodeMirror.fromTextArea(document.getElementById("scriptText"), {
        lineNumbers: true,
        mode: "javascript"
    });
});