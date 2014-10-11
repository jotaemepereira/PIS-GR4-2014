package interfaces;

import java.util.List;
import java.util.Map;

import controladores.Excepciones;
import datatypes.DTLineaPedido;
import datatypes.DTProveedor;
import model.AccionTer;
import model.Articulo;
import model.Droga;
import model.Presentacion;
import model.Proveedor;

public interface ISistema {
	
	public void altaProveedor(Proveedor proveedor) throws Excepciones;
	public List<DTLineaPedido> pedidoAutomaticoVentas();
	public void altaArticulo(Articulo articulo) throws Excepciones;
	public Map<Integer, DTProveedor> obtenerProveedores() throws Excepciones;

}
