function elegirTipo(e){
	console.log('elegir');
	// En caso que no haya ninguno seleccionado, llamo al bean y en caso contrario pregunto por cancelar el actual
	if($("[id*='tablePedido']").find("tbody").children("tr").hasClass('ui-datatable-empty-message')){
		PF('confirmDlg').show();
	}else{	
		PF('confirmDlg').show();
	}
	
}

$(document).ready(function(e){
	$('table').on('keydown', ":input", 'Alt+backspace', function(){console.log('eliminar'); $(this).parents('tr').find('button').trigger('click'); });
	$('table').on('keydown', ":input", 'Ctrl+return', function(){ console.log('enviar'); enviarPedido(); });
});



