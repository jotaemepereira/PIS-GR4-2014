$(document).ready(function(e){
	// saco todas las clases agregadas por jquery ui a los p:buttonCommand
	$(".btn").removeClass("ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");
	$(".btn").focus( function(e){ $(this).removeClass("ui-state-focus"); });
	$(".btn").mouseover(function(e){ $(this).removeClass('ui-state-hover'); });
	$(".btn").mousedown(function(e){ $(this).removeClass('ui-state-active'); });
	
	// shotcuts para la tabla
	//$('table').on('keydown', ":input", 'Alt+backspace', function(){console.log('eliminar'); $(this).parents('tr').find('button').trigger('click'); });
	//$('table').on('keydown', ":input", 'Ctrl+return', function(){ console.log('enviar'); enviarPedido(); });
	
});

$(function () { $("[data-toggle='tooltip']").tooltip(); });

$(document).ajaxComplete(function(e) {
	// saco todas las clases agregadas por jquery ui a los p:buttonCommand
	$(".btn").removeClass("ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");
	$(".btn").focus( function(e){ $(this).removeClass("ui-state-focus"); });
	$(".btn").mouseover(function(e){ $(this).removeClass('ui-state-hover'); });
	$(".btn").mousedown(function(e){ $(this).removeClass('ui-state-active'); });
});