package geso.dms.distributor.util;
import geso.dms.center.db.sql.dbutils;

import javax.servlet.http.*;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import java.util.Hashtable;
import java.sql.ResultSet;

public class MySessionAttributeListener implements HttpSessionAttributeListener, HttpSessionListener, ServletContextListener, ServletContextAttributeListener, HttpSessionActivationListener, HttpSessionBindingListener, ServletRequestListener, ServletRequestAttributeListener {

	private Hashtable<String, HttpSession> sessions = new Hashtable<String, HttpSession>();
	
	public MySessionAttributeListener() {
        
    }

    public void requestDestroyed(ServletRequestEvent arg0) {
    }

    public void attributeAdded(HttpSessionBindingEvent arg0) {
/*    	HttpSession session = arg0.getSession();
		System.out.println("[SessionAttr] "+new java.util.Date()+ 
    			" Attribute added, session "+ session.getId() +": "+ arg0.getName()+ "=" + arg0.getValue());    	
		
		if(arg0.getName().equals("userId")){
				sessions.put((String)arg0.getValue(), session);    	
				System.out.println("I am here" + (String)arg0.getValue());    	
		}*/
    }

    public void contextInitialized(ServletContextEvent arg0) {
    }

    public void sessionDidActivate(HttpSessionEvent arg0) {
    }

    public void valueBound(HttpSessionBindingEvent arg0) {
    }

    public void attributeAdded(ServletContextAttributeEvent arg0) {
    }

    public void sessionDestroyed(HttpSessionEvent arg0) {
    }

    public void attributeRemoved(ServletContextAttributeEvent arg0) {
    }

    public void attributeRemoved(HttpSessionBindingEvent arg0) {
    }

    public void attributeAdded(ServletRequestAttributeEvent arg0) {
/*    	System.out.println("New request attribute added:" + (String)arg0.getValue());
        HttpServletRequest request = (HttpServletRequest) arg0.getServletRequest();
       
       	if (sessions.containsKey((String)arg0.getValue())){
        		HttpSession ses = (HttpSession)this.sessions.get(arg0.getValue());
        		ses.invalidate();	
        		sessions.remove((String)arg0.getValue());
//        		System.out.println("Name of Attribute added: " + arg0.getValue());
            
        		dbutils db = new dbutils();
        		String query = "update nhanvien set islogin='0', sessionId = 'No' where pk_seq = '" + arg0.getValue() + "'";
        		System.out.print(query);
        		db.update(query);
        	}*/
    }

    public void valueUnbound(HttpSessionBindingEvent arg0) {
    }

    public void sessionWillPassivate(HttpSessionEvent arg0) {
    }

    public void attributeReplaced(HttpSessionBindingEvent arg0) {
    }

    public void sessionCreated(HttpSessionEvent arg0) {
    }

    public void attributeReplaced(ServletContextAttributeEvent arg0) {
    }

    public void attributeRemoved(ServletRequestAttributeEvent arg0) {
    }

    public void contextDestroyed(ServletContextEvent arg0) {
    }

    public void attributeReplaced(ServletRequestAttributeEvent arg0) {
    }

    public void requestInitialized(ServletRequestEvent arg0) {

    }
	
}
