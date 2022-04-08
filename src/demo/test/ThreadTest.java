package demo.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ThreadTest extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ThreadTest() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter out = response.getWriter();
		String userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
		userId = this.getDateTime();
		/*
		int i = 10;
		while( i > 0)
		{
			out.println(userId);
		}
		*/
		int i = 0;
		while(i < 120)
		{
			try 
			{
				//out.println("UserId: " + userId);
				System.out.println("UserId: " + userId + " -- i: " + Integer.toString(i));
				Thread.sleep(1000);
				i++;
				//if(i >= 200)
					//break;
			} 
			catch (InterruptedException e) {
				//out.println("exception: " + e.toString());
			}
		}
		
		System.out.println("User id cuoi la: " + userId);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
	}
	
	private String getDateTime() 
	{
		String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
	    return sdf.format(cal.getTime());
	}

}
