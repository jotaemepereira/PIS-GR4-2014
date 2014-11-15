package controladores;

import interfaces.IFacturacionPersistencia;
import interfaces.IPredictor;
import interfaces.IStockPersistencia;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.commons.math3.stat.regression.SimpleRegression;

import datatypes.DTLineaPedido;

/**
 * 
 * @author santiago
 *
 */

public class PredecirEnBaseAHistorico implements IPredictor {
	/**
	 * 
	 * @var CANT_DIAS_HABILES
	 * @var CANTIADA_ANIOS_ANTERIORES
	 * @var j son los milisegundos que pasaron desde la feha que se esta
	 *      calculando hasta el dia de hoy
	 * @var P1 y P2 coeficientes para ponderar dias anteriores frente a anios
	 *      anteriores. Configurables mediante el webxml
	 */
	
	ExternalContext externalContext = FacesContext.getCurrentInstance()
			.getExternalContext();
	
	final double P1 = Double.parseDouble(externalContext
			.getInitParameter("PONDERADOR_DIAS_HABILES"));
	final double P2 = Double.parseDouble(externalContext
			.getInitParameter("PONDERADOR_ANIOS_ANTERIORES"));
	final int CANT_DIAS_HABILES = Integer.parseInt(externalContext
			.getInitParameter("CANT_DIAS_HABILES"));
	final int CANT_ANIOS_ANTERIORES = Integer.parseInt(externalContext
			.getInitParameter("CANT_ANIOS_ANTERIORES"));;
	final long CANT_MILISEC_EN_UN_DIA = 1000 * 60 * 60 * 24;
	private int diasApredecir = 0;
	IFacturacionPersistencia fp;
	IStockPersistencia st;

	PredecirEnBaseAHistorico(int diasAPredecir) {

		this.diasApredecir = diasAPredecir;
		fp = FabricaPersistencia.getInstanciaFacturacionPersistencia();
		st = FabricaPersistencia.getStockPersistencia();

	}

	@Override
	public List<DTLineaPedido> predecir() throws Excepciones {

		IFacturacionPersistencia fp = FabricaPersistencia
				.getInstanciaFacturacionPersistencia();
		Map<Long, DTLineaPedido> mapaCantidadesAPedir = null;
		Map<Long, DTLineaPedido> mapaCantVendidaAnosAnteriores;
		Map<Long, SimpleRegression> mapaRectas = new HashMap<Long, SimpleRegression>();

		final Calendar hoy = Calendar.getInstance();
		Calendar desde = Calendar.getInstance();
		Calendar hasta = Calendar.getInstance();
		long j = CANT_MILISEC_EN_UN_DIA;

		/*
		 * Trunco la fecha de hoy, para calcular las cantidades vendidas por
		 * dia.
		 */
		desde.set(Calendar.HOUR_OF_DAY, 0);
		desde.set(Calendar.MINUTE, 0);
		desde.set(Calendar.SECOND, 0);
		desde.set(Calendar.MILLISECOND, 0);

		int i = CANT_DIAS_HABILES;

		/*
		 * Obtengo los datos de ventas de CANT_DIAS_HABILES hacia atrás y agrego
		 * los datos a las rectas de los artículos para el cálculo de mínimos
		 * cuadrados.
		 */
		while (i != 0) {
			j += CANT_MILISEC_EN_UN_DIA;
			desde.setTimeInMillis(hoy.getTimeInMillis() - j);
			/*
			 * Se considera los domingo como feriado. Hay que evitar calcular
			 * los dias cerrados ya que influyen negativamente en la prediccion.
			 */
			if (Calendar.DAY_OF_WEEK != Calendar.SUNDAY) {
				mapaCantidadesAPedir = fp
						.obtenerCantidadVendidaDeArticulosDeDusa(
								new Date(desde.getTimeInMillis()), new Date(
										desde.getTimeInMillis()
												+ CANT_MILISEC_EN_UN_DIA));
				/*
				 * Agrego el resultado de la consulta a la recta de cada
				 * artículo para luego utilizar en el cálculo de mínimos
				 * cuadrados.
				 */
				agregarDatos(new ArrayList<DTLineaPedido>(
						mapaCantidadesAPedir.values()), mapaRectas, i);
				i--;
			}
		}

		/*
		 * Calculo por minimos cuadrados Para cada artículo hago la predicción
		 * de la recta tomando en cuenta la cantidad de días a predecir.
		 */
		for (i = 1; i <= this.diasApredecir; i++) {
			for (DTLineaPedido item : mapaCantidadesAPedir
					.values()) {
				item.setCantPredecidaMinimosCuadrados(item
						.getCantPredecidaMinimosCuadrados()
						+ mapaRectas.get(item.getIdArticulo()).predict(
								CANT_DIAS_HABILES + i));
			}
		}

		/*
		 * Obtengo los promedios de ventas realizadas en el mismo período para
		 * años anteriores.
		 */
		long dias_a_predecir_en_milisec = this.diasApredecir
				* CANT_MILISEC_EN_UN_DIA;
		/*
		 * En cada iteración voy sumando la cantidad para cada año para cada
		 * artículo.
		 */
		mapaCantVendidaAnosAnteriores = new HashMap<Long, DTLineaPedido>();
		i = 1;
		while (i <= CANT_ANIOS_ANTERIORES) {
			desde = Calendar.getInstance();
			hasta = Calendar.getInstance();
			desde.add(Calendar.YEAR, -i);
			hasta.setTimeInMillis(desde.getTimeInMillis()
					+ dias_a_predecir_en_milisec);
			Map<Long, DTLineaPedido> auxMapa = fp
					.obtenerCantidadVendidaDeArticulosDeDusa(
							new Date(desde.getTimeInMillis()),
							new Date(hasta.getTimeInMillis()));

			sumarCantidades(
					new ArrayList<DTLineaPedido>(auxMapa.values()),
					mapaCantVendidaAnosAnteriores);

			i++;
			/*
			 * La última vez que entra calculo los valores finales
			 * del algoritmo.
			 */
			if (i > CANT_ANIOS_ANTERIORES) {
				calcularCantidadesAPedir(mapaCantidadesAPedir, mapaCantVendidaAnosAnteriores);
			}
		}
		
		return new ArrayList<DTLineaPedido>(mapaCantidadesAPedir.values());

	}

	/**
	 * Agrega los datos de todos los artículos a sus rectas para el cálculo de
	 * mínimos cuadrados para el día hábil.
	 * 
	 * @param cantidadDeArticulos
	 * @param mapaRectas
	 * @param dia
	 */
	private void agregarDatos(List<DTLineaPedido> cantidadDeArticulos,
			Map<Long, SimpleRegression> mapaRectas, int dia) {
		SimpleRegression recta;
		for (DTLineaPedido item : cantidadDeArticulos) {
			if (!mapaRectas.containsKey(item.getIdArticulo())) {
				recta = new SimpleRegression();
				recta.addData(dia, item.getCantidad());
				mapaRectas.put(item.getIdArticulo(), recta);
			} else {
				recta = mapaRectas.get(item.getIdArticulo());
				recta.addData(dia, item.getCantidad());
			}
		}
	}

	/**
	 * Suma los valores de las cantidades al los valores guardados en el mapa
	 * 
	 * @param cantidadesAnio
	 * @param mapaCantVendidaAnosAnteriores
	 */
	private void sumarCantidades(
			ArrayList<DTLineaPedido> cantidadesAnio,
			Map<Long, DTLineaPedido> mapaCantVendidaAnosAnteriores) {
		for (DTLineaPedido item : cantidadesAnio) {
			if (!mapaCantVendidaAnosAnteriores
					.containsKey(item.getIdArticulo())) {
				mapaCantVendidaAnosAnteriores.put(item.getIdArticulo(), item);
			} else {
				mapaCantVendidaAnosAnteriores.get(item.getIdArticulo())
						.setCantidad(
								mapaCantVendidaAnosAnteriores.get(
										item.getIdArticulo()).getCantidad()
										+ item.getCantidad());
			}
		}

	}

	/**
	 * Calcula las cantidades finales a pedir.
	 * 
	 * @param mapaCantVendidaAnosAnteriores
	 * @param mapaCantVendidaAnosAnteriores2 
	 */
	private void calcularCantidadesAPedir(
			Map<Long, DTLineaPedido> mapaCantidadesAPedir, Map<Long, DTLineaPedido> mapaCantVendidaAnosAnteriores) {
		for (DTLineaPedido item : mapaCantVendidaAnosAnteriores
				.values()) {
			/*
			 * Calculo el promedio de acuerdo a CANT_ANIOS_ANTERIORES
			 */
			long cantPromVendidaAniosAnt = item.getCantidad() / CANT_ANIOS_ANTERIORES;
			
			/*
			 * Pondero la cantidad predecida como P1 * cantPredecidaMinimosCuadrados + P2 * cantPromVendidaAniosAnt
			 */
			double cantidadPredecida = P1 * mapaCantidadesAPedir.get(item.getIdArticulo()).getCantPredecidaMinimosCuadrados() + P2
					* cantPromVendidaAniosAnt;
			
			/* 
			 * Calculo la cantidad a pedir.
			 * Si resulta menor a cero se fija en cero, de lo contrario se fija el resultado del algoritmo
			 */
			long cantAPedir = (long) Math.ceil(Math.max(cantidadPredecida, item.getStockMinimo())
					- item.getStockActual());
			
			if (cantAPedir < 0){
				mapaCantidadesAPedir.get(item.getIdArticulo()).setCantidad(0);
			} else {
				mapaCantidadesAPedir.get(item.getIdArticulo()).setCantidad(cantAPedir);
			}
		}

	}

}
