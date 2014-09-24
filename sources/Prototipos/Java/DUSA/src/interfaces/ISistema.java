package interfaces;

import java.util.List;

import controladores.Excepciones;
import datatypes.DTLineaPedido;
import model.Proveedor;

public interface ISistema {
	
	public void altaProveedor(Proveedor proveedor) throws Excepciones;
	public List<DTLineaPedido> pedidoAutomaticoVentas();

}
