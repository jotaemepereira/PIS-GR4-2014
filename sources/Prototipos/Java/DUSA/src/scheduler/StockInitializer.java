package scheduler;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import javax.servlet.ServletContextEvent;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.CronScheduleBuilder.cronSchedule;

import javax.servlet.ServletContextListener;
/**
 * 
 * @author santiago
 * Se ejecuta automaticamente al iniciar el servidor
 * se lee el archivo alertaStock.properties para leer la expreciin cron
 * si el archivo no existe se crea y se guarda la exprecion crontime 
 * que esta ene le codfigo
 * Se crea un Job que se dispara cuadno se cumple la espresion cron
 *  
 */
public class StockInitializer implements ServletContextListener {

	Scheduler sched;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}
	//Se ejecuta cuando se iniciar el servidor web
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
	
		Properties prop = new Properties();
		OutputStream output = null; 
		InputStream input = null;
		
		

		try {
			sched = new StdSchedulerFactory().getScheduler();

			/**
			 * se crea el Job alertaStock
			 */
			JobDetail job = newJob(StockJob.class).withIdentity("alertaStock",
					"grupo01").build();
			try {
				input = new FileInputStream("alertaStock.properties");
				prop.load(input);
			} catch (IOException e) {

				try {
					output = new FileOutputStream("alertaStock.properties");
					prop.setProperty("expresionCron", "0 33 16 * * ? *");
					prop.store(output, null);
				
				}catch (IOException e1) {
					e1.printStackTrace();
				}

			} finally {
		        /**
		         * se setea el Job con la ecpresion cron 
		         */
				String cron = prop.getProperty("expresionCron");
				CronTrigger trigger = newTrigger()
						.withIdentity("trigger01", "grupo01")
						.withSchedule(cronSchedule(cron)).build();

				sched.scheduleJob(job, trigger);
				sched.start();
			}
		} catch (SchedulerException e2) {
			e2.printStackTrace();
		}

	}
}