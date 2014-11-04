package scheduler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public interface IJob {
	
	public void execute(JobExecutionContext context) throws JobExecutionException;
	
}
