package controladores;


import interfaces.IFacturacionPersistencia;
import interfaces.IPredictor;
import interfaces.IStockPersistencia;

import java.util.Calendar;
import java.util.Date;


import org.apache.commons.math3.stat.regression.SimpleRegression;

/**
 * 
 * @author santiago
 *
 */

public class PredecirEnBaseAHistorico implements IPredictor{
/** 
 * 
 * @var CANT_DIAS_HABILES
 * @var CANTIADA_ANIOS_ANTERIORES
 * @var j son los milisegundos que pasaron desde la feha que se esta calculando hasta el dia de hoy
 * @var P1 y P2 coeficientes para ponderar dias anteriores frente a anios anteriores
 * CANT_DIAS_HABILES CANT_DIAS_HABILES P1 Y P2 Van hardcodeadas deberian ir en el .properties
 */
	final double P1 =0.7;
	final double P2 =0.3;
	final int CANT_DIAS_HABILES = 24;
	final int CANT_ANIOS_ANTEIORES = 2;
	final long CANT_MILISEC_EN_UN_DIA = 1000*60*60*24;
	private int diasApredecir = 0;
	
	PredecirEnBaseAHistorico(int diasAPredecir){
	
		this.diasApredecir = diasAPredecir;
	
	}
	
	@Override
	public int predecir(Long idArticulo) throws Excepciones{
		
		IFacturacionPersistencia fp = FabricaPersistencia.getInstanciaFacturacionPersistencia();
		IStockPersistencia st = FabricaPersistencia.getStockPersistencia();
		SimpleRegression recta = new SimpleRegression();
		
		final Calendar hoy = Calendar.getInstance();
		Calendar desde = Calendar.getInstance();		
		Calendar hasta = Calendar.getInstance();
		long j= CANT_MILISEC_EN_UN_DIA;
		
		//Trunco la fecha de hoy, para calcular las cantidades vendidas por dia.
		desde.set(Calendar.HOUR_OF_DAY	, 0);
		desde.set(Calendar.MINUTE		, 0);
		desde.set(Calendar.SECOND		, 0);
		desde.set(Calendar.MILLISECOND	, 0);
		
		for (int i=CANT_DIAS_HABILES; i!=0; j += CANT_MILISEC_EN_UN_DIA){
			desde.setTimeInMillis(hoy.getTimeInMillis()-j);
			
			//Se considera los domingo como feriado.
			//Hay que evitar calcular los dias cerrados ya que influyen negativamente el la prediccion.
			if (Calendar.DAY_OF_WEEK!=1 ){
				
				int cantidad = fp.cantidadVendidaEnPeriodo(idArticulo, new Date(desde.getTimeInMillis()), new Date(desde.getTimeInMillis() + CANT_MILISEC_EN_UN_DIA));
				recta.addData(i,cantidad);
				i--;
			}
		}
		
		//Calculo por minimos cuadrados
		int cantPredecidaMinimosCuadrados = 0;
		for (int i=1; i <= this.diasApredecir; i++){
			cantPredecidaMinimosCuadrados += recta.predict(CANT_DIAS_HABILES + i);
		}
		
		//Promedio de ventas realizadas en años anteriores
		int cantPromVendidaAniosAnt = 0;
		long dias_a_predecir_en_milisec = this.diasApredecir * CANT_MILISEC_EN_UN_DIA;
		for (int i=1; i<= CANT_ANIOS_ANTEIORES; i++){
			desde = Calendar.getInstance();
			hasta = Calendar.getInstance();
			desde.add(Calendar.YEAR, -i);
			hasta.setTimeInMillis(desde.getTimeInMillis()+dias_a_predecir_en_milisec);
			cantPromVendidaAniosAnt += fp.cantidadVendidaEnPeriodo(idArticulo, new Date(desde.getTimeInMillis()), new Date(hasta.getTimeInMillis()));
		}
		
		cantPromVendidaAniosAnt /= CANT_ANIOS_ANTEIORES;
		double cantidaPredecida = P1 * cantPredecidaMinimosCuadrados + P2 * cantPromVendidaAniosAnt;
		
		long minStock = st.getStockMinimo(idArticulo);
		int cantAPedir = (int) Math.ceil(Math.max(cantidaPredecida, minStock) - st.getStock(idArticulo));
		
		if (cantAPedir < 0)
			return 0;
		else
			return cantAPedir;
	}

}
