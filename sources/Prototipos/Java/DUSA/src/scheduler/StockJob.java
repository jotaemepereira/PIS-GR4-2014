package scheduler;

import interfaces.IStock;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import controladores.Excepciones;
import controladores.FabricaLogica;

public class StockJob implements Job , IJob{

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		
		Properties propiedades = new Properties();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		//String dateInString = "07/06/2013";
		InputStream entrada = null;
		Date date = null;
		try {
			entrada = new FileInputStream("alertaStock.properties");
			propiedades.load(entrada);
			String sDate = propiedades.getProperty("fechaUltimaActualizacion");
			
			if (sDate==null || sDate.equalsIgnoreCase("")){
				Calendar cal =GregorianCalendar.getInstance();
				cal.setTime(date);
				cal.add(GregorianCalendar.MONTH, -6);
				date = cal.getTime();
			}else	
				date = formatter.parse(sDate);
			IStock st = FabricaLogica.getIStock();
			st.actualizarStock(date);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (Excepciones e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
                                                