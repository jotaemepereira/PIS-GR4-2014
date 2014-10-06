$(document).ready(function(){
	$('[id*=RUT]').trigger('focus');
	
	$(':input').bind('keydown', 'Ctrl+return', function(){altaProveedor(); });
});

