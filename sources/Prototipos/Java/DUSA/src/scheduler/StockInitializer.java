package scheduler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

			JobDetail job = newJob(StockJob.class).withIdentity("alertaStock",
					"grupo01").build();
			try {
				input = new FileInputStream("alertaStock.properties");
				prop.load(input);
			} catch (IOException e) {

				try {
					output = new FileOutputStream("alertaStock.properties");
					prop.setProperty("expresionCron", "0 41 22 * * ? *");
					prop.store(output, null);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					return;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			} finally {
				String cron = prop.getProperty("expresionCron");
				CronTrigger trigger = newTrigger()
						.withIdentity("trigger01", "grupo01")
						.withSchedule(cronSchedule(cron)).build();

				sched.scheduleJob(job, trigger);
				sched.start();
			}
		} catch (SchedulerException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

	}

}