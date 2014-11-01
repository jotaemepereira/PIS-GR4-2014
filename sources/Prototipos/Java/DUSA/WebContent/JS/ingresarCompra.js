$(document).ready(function(e){
	// saco todas las clases agregadas por jquery ui a los p:buttonCommand
	$("body").on("focus mouseover mousedown click", ".btn", function(e){ $(this).removeClass("ui-state-focus ui-state-hover ui-state-active"); });
	actualizarBotones();
	
});

function actualizarBotones(){
	// saco todas las clases agregadas por jquery ui a los p:buttonCommand
	$(".btn").removeClass("ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");
	
	// inicializo tooltip
	$("[data-toggle='tooltip']").tooltip();
}