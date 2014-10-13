package controladores;


import interfaces.IFacturacionPersistencia;
import interfaces.IPredictor;
import interfaces.IStockPersistencia;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

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
	
	public int predecir(Long idArticulo) throws Exception{
		
		IFacturacionPersistencia fp = FabricaPersistencia.getInstanciaFacturacionPersistencia();
		IStockPersistencia st = FabricaPersistencia.getStockPersistencia();
		SimpleRegression recta = new SimpleRegression();
		
		final Calendar hoy = Calendar.getInstance();
		Calendar desde = Calendar.getInstance();		
		Calendar hasta = Calendar.getInstance();
		long j= CANT_MILISEC_EN_UN_DIA;
		
		for (int i=CANT_DIAS_HABILES; i!=0; j++){
			desde.setTimeInMillis(hoy.getTimeInMillis()-j);
			if (desde.DAY_OF_WEEK!=1 ){
				int cantidad = fp.cantidadVendidaEnPeriodo(idArticulo, new Date(desde.getTimeInMillis()), new Date(desde.getTimeInMillis()));
				recta.addData(i,cantidad);
				i--;
			}
		}
		int cantPredecidaMinimosCuadrados = 0;
		for (int i=1; i <= this.diasApredecir; i++){
			cantPredecidaMinimosCuadrados += recta.predict(CANT_DIAS_HABILES + i);
		}
		int cantPromVendidaAniosAnt = 0;
		long dias_a_predecir_en_milisec = this.diasApredecir * CANT_MILISEC_EN_UN_DIA;
		for (int i=1; i<= CANT_ANIOS_ANTEIORES; i++){
			desde = Calendar.getInstance();
			hasta = Calendar.getInstance();
			desde.add(hoy.YEAR, -i);
			hasta.setTimeInMillis(desde.getTimeInMillis()+dias_a_predecir_en_milisec);
			cantPromVendidaAniosAnt += fp.cantidadVendidaEnPeriodo(idArticulo, new Date(desde.getTimeInMillis()), new Date(hasta.getTimeInMillis()));
		}
		
		cantPromVendidaAniosAnt /= CANT_ANIOS_ANTEIORES;
		double cantidaPredecida = P1 * cantPredecidaMinimosCuadrados + P2 * cantPromVendidaAniosAnt;
		int cantAPedir = (int) Math.ceil(cantidaPredecida - st.getStock(idArticulo));
		if (cantAPedir < 0)
			return 0;
		else
			return cantAPedir;
	}

}
