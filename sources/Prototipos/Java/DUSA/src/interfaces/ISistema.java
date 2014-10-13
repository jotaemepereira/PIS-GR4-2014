package interfaces;

import java.util.List;

import controladores.Excepciones;
import datatypes.DTLineaPedido;
import datatypes.DTProveedor;
import model.AccionTer;
import model.Articulo;
import model.Droga;
import model.Pedido;
import model.Presentacion;
import model.Proveedor;

public interface ISistema {
	
	public void altaProveedor(Proveedor proveedor) throws Excepciones;
	public List<DTLineaPedido> pedidoAutomaticoVentas();
	public void altaArticulo(Articulo articulo) throws Excepciones;
	public List<DTProveedor> obtenerProveedores() throws Excepciones;

	/**
	 * @author Guille
	 * @return Genera un pedido de artículos, de D.U.S.A. La cantidad de cada artículo es igual a la cantidad vendida, del artículo, desde el pedido anterior. 
	 * @throws Excepciones
	 */
	public List<DTLineaPedido> generarPedidoEnBaseAPedidoAnterior() throws Excepciones;
}
