function keypressBusqueda(e) {
	if (event.keyCode == 13) {
		e.preventDefault();
		buscarArticulo();
		return false;
	}
}

function keypressLector(e) {
	if (event.keyCode == 13) {
		e.preventDefault();
		buscarArticuloLector();
		return false;
	}
}

function selectCantidad() {
	var myInput = document.getElementById('ventaDetail:cant');
	myInput.focus();
	myInput.select()
}
