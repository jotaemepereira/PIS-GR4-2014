$(document).ready(function(){
	$(':input').bind('keydown', 'Ctrl+return', altaProveedor);
	
	// inicializo tooltip
	$("[data-toggle='tooltip']").tooltip();
});

