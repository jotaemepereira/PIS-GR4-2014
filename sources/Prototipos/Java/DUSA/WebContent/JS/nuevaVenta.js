$(document).ready(function(e){
	// Busco el articulo al hacer enter en el input de busqueda
	$("html").on('keydown', "[id*='descripcion2']", 'return', function(e){
		e.stopPropagation();
		e.preventDefault();
		console.log('enter');
		buscarArticulo();
	});
	
	// Busco el artículo al hacer enter en el input de busqueda lector
	$("[id*='ajax7']").bind('keydown', 'return', function(e){
		e.stopPropagation();
		e.preventDefault();
		console.log('enter');
		buscarArticuloLector();
	});
});