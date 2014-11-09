package controladores;

import java.util.Map;

import datatypes.DTComprobanteFactura;
import datatypes.DTTiposDGI;
import interfaces.ICompras;
import model.Orden;

public class ComprasControlador implements ICompras {

	@Override
	public void ingresarFacturaCompra(Orden orden) throws Excepciones {
		if(orden.getOrdenDeCompra() == 0){ // En el caso de una compra manual ingreso la factura
			FabricaPersistencia.getInstanciaComprasPersistencia().ingresarFacturaCompra(orden); 
		}else{ // En el caso de una factura de DUSA, paso la factura a procesada
			FabricaPersistencia.getInstanciaComprasPersistencia().actualizarFacturaCompraDUSA(orden);
		}
		
		// Actualizo el stock de todos los artículos en la compra
		FabricaPersistencia.getStockPersistencia().actualizarStockCompra(orden.getDetalle());
	}

	@Override
	public Map<Integer, DTTiposDGI> obtenerTiposDGI() throws Excepciones {
		return FabricaPersistencia.getInstanciaComprasPersistencia().obtenerTiposDGI();
	}

	@Override
	public Map<Long, DTComprobanteFactura> obtenerFacturasDUSA() throws Excepciones {
		// Primero traigo facturas nuevas que pueden haber en el sistema
		FabricaServicios.getIServicios().obtenerFacturasDUSA();

		// Luego, obtengo todas las facturas pendientes en la base de datos (incluyendo las nuevas)
		return FabricaPersistencia.getInstanciaComprasPersistencia().obtenerFacturasPendientes();
	}
	
}
