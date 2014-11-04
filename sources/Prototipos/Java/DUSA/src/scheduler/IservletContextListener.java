package scheduler;

import javax.servlet.ServletContextEvent;

import org.quartz.Scheduler;

import controladores.Excepciones;


public interface IservletContextListener {
	
	public void contextInitialized(ServletContextEvent servContEv) throws Excepciones;
	public void contextDestroyed(ServletContextEvent servContEv) throws Excepciones;
	
}
