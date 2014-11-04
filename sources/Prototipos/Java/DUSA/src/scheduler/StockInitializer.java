package scheduler;

import javax.servlet.ServletContextEvent;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import controladores.Excepciones;

public class StockInitializer implements IservletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent servContEv) throws Excepciones {
		
		SchedulerFactory sf = new StdSchedulerFactory();
		try {
			Scheduler sched = sf.getScheduler();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JobDetail job = org.quartz.JobBuilder.newJob(StockJob.class).withIdentity("alertaStock", "grupo01").build();
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent servContEv)
			throws Excepciones {
		// TODO Auto-generated method stub
		
	}
	

}
