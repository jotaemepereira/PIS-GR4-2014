package controladores;

import java.sql.Date;
import java.util.List;

import datatypes.DTFormasVenta;
import datatypes.DTTiposDGI;
import interfaces.ICompras;
import model.LineaPedido;
import model.LineaVenta;
import model.Orden;

public class ComprasControlador implements ICompras {

	@Override
	public void ingresarFacturaCompra(Orden orden) throws Excepciones {
		FabricaPersistencia.getInstanciaComprasPersistencia().ingresarFacturaCompra(orden);
	}

	@Override
	public List<DTTiposDGI> obtenerTiposDGI() throws Excepciones {
		return FabricaPersistencia.getInstanciaComprasPersistencia().obtenerTiposDGI();
	}
	
}
