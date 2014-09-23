package interfaces;

import java.util.List;

import datatypes.DTLineaPedido;
import model.Proveedor;

public interface ISistema {
	
	public void altaProveedor(Proveedor proveedor);
	public List<DTLineaPedido> pedidoAutomaticoVentas();

}
