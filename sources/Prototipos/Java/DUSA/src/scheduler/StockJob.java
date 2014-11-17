package scheduler;

import interfaces.IStock;

import java.util.Properties;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import controladores.Excepciones;
import controladores.FabricaLogica;
/**
 * 
 * @author santiago
 * Se lee las propiedades del archivo alertaStock.properties 
 * es seguro que el archivo existe porque antes de ser invocada 
 * la opereacion execute se chequea la existencia del archivo y si no existe 
 * se crea
 * 
 * Se lee la fecha de ultima actualizacion si esta propiedad no existe 
 * se toma un mensa de anterioridad a la fecha del dia
 *
 */
public class StockJob implements Job {

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {


		Date date = null;

		Properties prop = new Properties();
		InputStream input = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");

			try {
				input = new FileInputStream("alertaStock.properties");
				prop.load(input);
				String sDate = prop.getProperty("fechaUltimaActualizacion");
				
				
				if (sDate==null || sDate.equalsIgnoreCase("")){
					Calendar cal =GregorianCalendar.getInstance();
					cal.add(GregorianCalendar.MONTH, -1);
					date = cal.getTime();
					
				}
				else {
					date = sdf.parse(sDate);
				}
					IStock st = FabricaLogica.getIStock();
					st.actualizarStock(date);
				

			} catch (IOException | Excepciones | java.text.ParseException e) {
				e.printStackTrace();
			}
	}		 
}