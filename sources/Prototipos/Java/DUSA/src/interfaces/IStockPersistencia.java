package interfaces;

import java.sql.Date;
import java.util.List;

import model.Articulo;
import model.LineaPedido;

public interface IStockPersistencia {
	
	public void persistirArticulo(Articulo articulo);
	public Date getUltimoPedido() throws Exception;
	public List<LineaPedido> obtenerArticulosDesde(Date fechaDesde) throws Exception;
	
}
