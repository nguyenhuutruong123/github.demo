package geso.dms.center.servlets.khoasotudong;

import geso.dms.distributor.beans.khoasongay.imp.Khoasongay;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
 
public class khoasotudong_listener implements ServletContextListener 
{
	/*private ScheduledExecutorService scheduler;
	private ScheduledExecutorService scheduler2;
	String company = "SGP";
	String dateRegister = "2018-06-01";
	private String licenseKey = "AJU19-93KSF-27YEL-OSU46-9OAG3";*/
	 
    @Override
    public void contextInitialized(ServletContextEvent event) 
    { 
       /* scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler2 = Executors.newSingleThreadScheduledExecutor();
        Runnable command = new LincenseThread(event.getServletContext(), company, dateRegister, licenseKey);
        Runnable command1 = new LincenseThread2(event.getServletContext(), company, dateRegister, licenseKey);
        // Delay 1 Minute to first execution
        long initialDelay = 1;
        TimeUnit unit = TimeUnit.MINUTES;
        // period the period between successive executions
        long period = 120;// 50 Minute!
        long period2 = 60;
 
        scheduler.scheduleAtFixedRate(command, initialDelay, period, unit);
        scheduler2.scheduleAtFixedRate(command1, initialDelay, period2, unit);*/
    	
    	System.out.println("start khóa sổ ahihi");
    	runTask();
    }
 
    @Override
    public void contextDestroyed(ServletContextEvent event) 
    {
        System.out.println("off khoa so hihia");
    }
	
    
    public void runTask() {

    	 Calendar calendar = Calendar.getInstance();
         calendar.set(Calendar.HOUR_OF_DAY, 22);
         calendar.set(Calendar.MINUTE, 1);
         calendar.set(Calendar.SECOND, 0);
         calendar.set(Calendar.MILLISECOND, 0);
  
         Date dateSchedule = calendar.getTime();
        
         TimerTask timerTask = new TimerTask() {
             @Override
             public void run() {
            	 Khoasongay.KhoaSoTuDong();
                 System.out.println("KHÓA SỔ TỰ ĐỘNG...... !");
                 
             }
         };
  
         Timer timer = new Timer();
         timer.schedule(timerTask, dateSchedule, TimeUnit.DAYS.toMillis(1));
    }
    
}