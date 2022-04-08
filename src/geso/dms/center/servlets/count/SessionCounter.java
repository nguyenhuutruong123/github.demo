package geso.dms.center.servlets.count;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.ArrayList;

public class SessionCounter implements HttpSessionListener 
{

	/*private List<String> sessions = new ArrayList<String>();
	
	public SessionCounter() 
	{
	}
	
	public void sessionCreated(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		System.out.println("Khoi tao session "+ session.getId());
		sessions.add(session.getId());	
		System.out.println("Gia tri session la "+ session.getValue("captcha")));
		session.setAttribute("counter", this);
	}
	
	public void sessionDestroyed(HttpSessionEvent event)
	{
		HttpSession session = event.getSession();
		System.out.println("Huy session "+ session.getId());
		sessions.remove(session.getId());
		session.setAttribute("counter", this);
	}*/
	/*
	public int getActiveSessionNumber() 
	{
		return sessions.size();
	}*/
	public static int activeSessions = 0;

	public void sessionCreated(HttpSessionEvent se) 
	{
		HttpSession session = se.getSession();
		System.out.println("Tao session "+ session.getId());
		//activeSessions++;
	}

	public void sessionDestroyed(HttpSessionEvent se) 
	{
		HttpSession session = se.getSession();
		System.out.println("session duoc huy "+session.getId());
		if(activeSessions > 0)
			activeSessions--;
	}

	public static int getActiveSessions() {
		
		return activeSessions;
	}
}
