package scheduler;

import javax.servlet.ServletContextEvent;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import controladores.Excepciones;
import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.CronScheduleBuilder.cronSchedule;




public class StockInitializer implements IservletContextListener {

	
	Scheduler sched;
	@Override
	public void contextInitialized(ServletContextEvent servContEv) throws Excepciones {
		
		SchedulerFactory sf = new StdSchedulerFactory();
		try {
			sched = sf.getScheduler();
		
		JobDetail job = newJob(StockJob.class).withIdentity("alertaStock", "grupo01").build();
		
		String cron = "0 15 10 * * ? *";	
		// todos los dias a las 10:15 
		// hay que sacar la hora para el trigger y guardar fecha de la ultima vez que se disparo en un .properties  
		//String filename = "alertasStock.properties";
		//StockInitializer.class.getClassLoader().getResourceAsStream(filename);
		CronTrigger trigger = newTrigger().withIdentity("trigger01", "grupo01").
				withSchedule(cronSchedule(cron)).build();
		sched.scheduleJob(job, trigger);
		sched.start();
				
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}

	@Override
	public void contextDestroyed(ServletContextEvent servContEv)
			throws Excepciones{
		try {
			sched.shutdown(); 
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
