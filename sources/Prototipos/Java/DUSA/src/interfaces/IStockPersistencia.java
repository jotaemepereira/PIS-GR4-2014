package interfaces;

import java.sql.Date;
import java.util.List;

import controladores.Excepciones;
import model.Articulo;
import model.LineaPedido;
import model.Pedido;

/**  
* @author Santiago
*
*/
public interface IStockPersistencia {
	
	public void persistirArticulo(Articulo articulo)throws Excepciones;
	public Date getUltimoPedido() throws Excepciones;
	//Deprecated 
//	public List<LineaPedido> obtenerArticulosDesde(Date fechaDesde) throws Excepciones;
	public void persistirPedido(Pedido p);
}
