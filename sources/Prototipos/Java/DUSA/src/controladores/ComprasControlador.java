package controladores;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import datatypes.DTComprobanteFactura;
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
		FabricaPersistencia.getStockPersistencia().actualizarStockCompra(orden.getDetalle());
	}

	@Override
	public Map<Integer, DTTiposDGI> obtenerTiposDGI() throws Excepciones {
		return FabricaPersistencia.getInstanciaComprasPersistencia().obtenerTiposDGI();
	}

	@Override
	public Map<Long, DTComprobanteFactura> obtenerFacturasDUSA() throws Excepciones {
		return FabricaServicios.getIServicios().obtenerFacturasDUSA();
	}
	
}
