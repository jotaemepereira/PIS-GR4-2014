$(document).ready(function(e){
	// saco todas las clases agregadas por jquery ui a los p:buttonCommand
	$(".btn").removeClass("ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");
	$(".btn").focus( function(e){ $(this).removeClass("ui-state-focus"); });
	$(".btn").mouseover(function(e){ $(this).removeClass('ui-state-hover'); });
	$(".btn").mousedown(function(e){ $(this).removeClass('ui-state-active'); });
	$(".btn").click(function(e){ $(this).removeClass('ui-state-hover'); });
});
