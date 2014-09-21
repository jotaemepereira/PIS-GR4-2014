package controladores;

import java.util.List;

import model.Venta;
import interfaces.IFacturacion;
import interfaces.IFacturacionPersistencia;

public class FacturacionControlador implements IFacturacion {

	@Override
	public List<Venta> listarVentasPendientes() throws Exception {
		try{
			IFacturacionPersistencia ifp = FabricaPersistencia.getInstanciaFacturacionPersistencia();
			return ifp.listarVentasPendientes();
		} catch(Exception e){
			throw e;
		}
	}

	@Override
	public void facturarVenta(long ventaId) throws Exception {
		// TODO Auto-generated method stub
		
	}

	
}
