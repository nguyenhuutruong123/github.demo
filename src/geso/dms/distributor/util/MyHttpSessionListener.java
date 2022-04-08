package geso.dms.distributor.util;

import javax.servlet.http.*;

import java.util.Date;
import geso.dms.distributor.db.sql.*;

public class MyHttpSessionListener implements HttpSessionListener{
    public void sessionCreated(HttpSessionEvent se)
    {
/*        HttpSession session = se.getSession();
        System.out.print(getTime() + " (session) Created:");
        System.out.println("ID=" + session.getId() + " MaxInactiveInterval=" + session.getMaxInactiveInterval());*/
    }
    public void sessionDestroyed(HttpSessionEvent se)
    {
    	
/*        HttpSession session = se.getSession();
        dbutils db = new dbutils();
        String query = "update nhanvien set islogin = '0', sessionId ='No session yet' where sessionid='" + session.getId() + "'";
        db.update(query);
        System.out.println(getTime() + " (session) Destroyed:ID=" + session.getId());*/
    }
    
    private String getTime()
    {
        return new Date(System.currentTimeMillis()).toString();
    }
}
