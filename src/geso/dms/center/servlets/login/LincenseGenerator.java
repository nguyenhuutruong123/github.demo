package geso.dms.center.servlets.login;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
 
public class LincenseGenerator implements ServletContextListener 
{
	private ScheduledExecutorService scheduler;
	String company = "SGP";
	String dateRegister = "2018-06-01";
	private String licenseKey = "AJU19-93KSF-27YEL-OSU46-9OAG3";
	 
    @Override
    public void contextInitialized(ServletContextEvent event) 
    { 
        scheduler = Executors.newSingleThreadScheduledExecutor();
        Runnable command = new LincenseThread(event.getServletContext(), company, dateRegister, licenseKey);
        // Delay 1 Minute to first execution
        long initialDelay = 1;
        TimeUnit unit = TimeUnit.MINUTES;
        // period the period between successive executions
        long period = 60;// 50 Minute!
 
        scheduler.scheduleAtFixedRate(command, initialDelay, period, unit);
    }
 
    @Override
    public void contextDestroyed(ServletContextEvent event) 
    {
        scheduler.shutdownNow();
    }
	
   
}