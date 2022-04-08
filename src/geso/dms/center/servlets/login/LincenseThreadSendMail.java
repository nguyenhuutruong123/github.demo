package geso.dms.center.servlets.login;

import geso.dms.center.db.sql.dbutils;
 import geso.dms.center.util.SendMail; 
import geso.dms.distributor.beans.khoasongay.imp.Khoasongay;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.rp.util.DateTime;
 
public class LincenseThreadSendMail implements Runnable   
{
	private ServletContext context;
	InetAddress ip;
	String company;
	String dateRegister;
	String licenseKey;
	 
    public LincenseThreadSendMail(ServletContext context, String company, String dateRegister, String licenseKey) 
    {
        this.context = context;
        this.company = company;
        this.dateRegister = dateRegister;
        this.licenseKey = licenseKey;
    }
     private String  nameproject = "AHF";     
     private String username = "dms";     
	 private String password = "geso!@#";  
	 private String url ="jdbc:jtds:sqlserver://118.69.168.124:1433/SendMail_CheckKho_DMS";
	 private Connection connection;
	 private Statement statement;
    @Override
    public void run() 
    {
        try 
        {
        	
        	Class.forName("net.sourceforge.jtds.jdbc.Driver");
    		connection = DriverManager.getConnection(url, username, password);
        	
        	ip = InetAddress.getLocalHost();
        	
        	DateTime dt = new DateTime();  // current time
        	String hour = dt.getHour();     // gets the current month
        	String date = dt.getDate(); // gets hour of day
        	System.out.println("...Date: " + date + " Hour: " + hour +"--"+date);
        	System.out.println("-----"+getDateTime());
 
        	if(hour.equals("07") || hour.equals("7") )
        	{ 

            	int sl=0;
            	geso.dms.distributor.db.sql.dbutils db=new geso.dms.distributor.db.sql.dbutils();
        		String sql=" select count(*) as sl from LOG_RUN_TONKHO";
        		ResultSet rs=db.get(sql);
        		try {
        			rs.next();
        			sl=rs.getInt("sl");
        			rs.close();
        		} catch (SQLException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        		if(sl>0){
        			String query="INSERT SendMail_CheckKho(SchemeDuAn,TrangThaiKho,TrangThaiLogTBH,NGAY) SELECT '"+nameproject+"','S','D',convert(char(10),getdate(),126) ";
        			statement = connection.createStatement();  
            		statement.executeUpdate(query);  
        		}else{
        			String query="INSERT SendMail_CheckKho(SchemeDuAn,TrangThaiKho,TrangThaiLogTBH,NGAY) SELECT '"+nameproject+"','D','D',convert(char(10),getdate(),126)  ";
        			statement = connection.createStatement();  
            		statement.executeUpdate(query);  
        		}
        		if(statement != null)
        			statement.close();
        		if(connection != null)
        			connection.close();  
        		
                	
        				
        	}
        	
			

        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        	if(statement != null)
				try {
					statement.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    		if(connection != null)
				try {
					connection.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}  
        }
    }
     
 	 
	
     
    

 
	private int CompareDATE(String _date1, String _date2)
	{
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			//Date date1 = sdf.parse("2014-10-01");
			//Date date2 = sdf.parse("2014-10-01");

			Date date1 = sdf.parse(_date1);
			Date date2 = sdf.parse(_date2);

			//System.out.println(sdf.format(date1));
			//System.out.println(sdf.format(date2));

			return date1.compareTo(date2);
		}
		catch (Exception e) {
			return 0;
		}

	}
	
	private String getDateTime() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}



 
}