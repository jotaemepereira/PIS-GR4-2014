$(document).ready(function(e){
	$(".elegirTipo").click(function(e){
		if(this.id == "desdeUltimoPedido"){
			$("#predicionDePedido").hide();
		}else{
			$("#desdeUltimoPedido").hide();
		}
	});
});

function showBotones(e){
	$(".elegirTipo").show();
}

function cancelarPedido(e){
	$(".continuarDialog").prop("actionListener", "#{stockBean.cancelarPedido}");
	confirmDialog.show();
}