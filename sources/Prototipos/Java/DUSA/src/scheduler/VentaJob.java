package scheduler;

import interfaces.IFacturacion;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import controladores.FabricaLogica;

public class VentaJob implements Job{

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {


		Properties prop = new Properties();
		InputStream input = null;
		
			try {
				input = new FileInputStream("ventaStock.properties");
				prop.load(input);
				
				Integer mesesAtras = Integer.parseInt(prop.getProperty("mesesAtras"));
				
				if ( mesesAtras==null || mesesAtras==0){
					mesesAtras=3;
				}
				
				IFacturacion f = FabricaLogica.getIFacturacion();
				f.articulosConPocasVentasEnLosUltimosMeses(mesesAtras);
				

			} catch  ( Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
			
	}		 

}
