$(document).ready(function() {
    var editor = CodeMirror.fromTextArea(document.getElementById("xmlText"), {
        lineNumbers: true,
        smartIndent: true,
        indentWithTabs: true,
        mode: "xml"
    });
})