$(document).ready(function(e) {
	// saco todas las clases agregadas por jquery ui a los p:buttonCommand
	$("body").on("focus mouseover mousedown click", ".btn", function(e) {
		$(this).removeClass("ui-state-focus ui-state-hover ui-state-active");
	});
	actualizarBotones();

	// Eventos para agregar un nuevo articulo
	$('body').on('keydown', 'input', 'Ctrl+a', function(e) {
		$('.agregarArticulo').trigger('click');
		return false;
	});
	
	$('body').bind('keydown', 'Ctrl+a', function(e) {
		$('.agregarArticulo').trigger('click');
		return false;
	});

	// Evento para enviar el formulario
	$('body').on('keydown', 'input', 'Ctrl+return', function(e) {
		ingresarCompra();
		return false;
	});
});

function actualizarBotones() {
	// saco todas las clases agregadas por jquery ui a los p:buttonCommand
	$(".btn").removeClass("ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");

	// inicializo tooltip
	$("[data-toggle='tooltip']").tooltip();
}