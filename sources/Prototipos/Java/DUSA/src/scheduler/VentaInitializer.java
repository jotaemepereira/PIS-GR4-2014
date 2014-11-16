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
 * se lee el archivo ventaStock.properties para leer la expreciin cron
 * si el archivo no existe se crea y se guarda la exprecion crontime 
 * que esta ene le codfigo
 * Se crea un Job que se dispara cuadno se cumple la espresion cron
 *  
 */
public class VentaInitializer implements ServletContextListener {
	
	Scheduler sched;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
				
		Properties prop = new Properties();
		OutputStream output = null;
		InputStream input = null;

		try {
			sched = new StdSchedulerFactory().getScheduler();

			JobDetail job = newJob(VentaJob.class).withIdentity("ventaStock",
					"grupo01").build();
			try {
				input = new FileInputStream("ventaStock.properties");
				prop.load(input);
			} catch (IOException e) {

				try {
					output = new FileOutputStream("ventaStock.properties");
					prop.setProperty("expresionCron", "0 4 18 * * ? *");
					prop.store(output, null);
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			} finally {
				String cron = prop.getProperty("expresionCron");
				CronTrigger trigger = newTrigger()
						.withIdentity("trigger02", "grupo02")
						.withSchedule(cronSchedule(cron)).build();

				sched.scheduleJob(job, trigger);
				sched.start();
			}
		} catch (SchedulerException e2) {
			e2.printStackTrace();
		}

	}


}
