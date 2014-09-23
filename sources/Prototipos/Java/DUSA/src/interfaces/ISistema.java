package interfaces;

import java.util.List;

import datatypes.DTLineaPedido;
import datatypes.DTProveedor;
import model.AccionTer;
import model.Articulo;
import model.Droga;
import model.Presentacion;
import model.Proveedor;

public interface ISistema {
	
	public void altaProveedor(Proveedor proveedor);
	public List<DTLineaPedido> pedidoAutomaticoVentas();
	public void altaArticulo(Articulo articulo);

}
