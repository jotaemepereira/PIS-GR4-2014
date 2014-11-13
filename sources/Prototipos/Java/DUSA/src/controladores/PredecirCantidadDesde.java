package controladores;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import datatypes.DTLineaPedido;
import interfaces.IFacturacionPersistencia;
import interfaces.IPredictor;

/**
 * @author Santiago
 *
 */

public class PredecirCantidadDesde implements IPredictor {

	Date fechaDesde;

	/**
	 * @author Guille
	 */
	public PredecirCantidadDesde(Date f) {

		super();
		this.fechaDesde = f;
	}

	/**
	 * @author Guille
	 */
	@Override
	public List<DTLineaPedido> predecir() throws Excepciones {

		IFacturacionPersistencia fp = FabricaPersistencia
				.getInstanciaFacturacionPersistencia();
		return new ArrayList<DTLineaPedido>(
				fp.obtenerCantidadVendidaDeArticulosDeDusa(fechaDesde,
						new Date(Calendar.getInstance().getTimeInMillis())).values());
	}

}
