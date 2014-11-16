//$(document).ready(function(e) {
//
//	$("html").bind('keydown', 'return', function(e) {
//		e.stopPropagation();
//		e.preventDefault();
//	});
//	// Busco el articulo al hacer enter en el input de busqueda
//	$("html").on('keydown', "[id*='codigoBusqueda']", 'return', function(e) {
//		e.stopPropagation();
//		e.preventDefault();
//		console.log('enter');
//		buscarArticulo();
//	});
//
//	// Busco el artículo al hacer enter en el input de busqueda lector
//	$("[id*='codigoLector']").bind('keydown', 'return', function(e) {
//		e.stopPropagation();
//		e.preventDefault();
//		buscarArticuloLector();
//	});
//});

function keypressBusqueda(e) {
	if (event.keyCode == 13) {
		e.preventDefault();
		buscarArticulo();
		return false;
	}
}

function keypressLector(e){
	if (event.keyCode == 13) {
		e.preventDefault();
		buscarArticuloLector();
		return false;
	}
}