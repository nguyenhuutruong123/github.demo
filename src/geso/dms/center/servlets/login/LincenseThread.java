package geso.dms.center.servlets.login;

import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.NgungKhachHang;
import geso.dms.center.util.SendMail; 

import java.net.InetAddress;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
 
public class LincenseThread implements Runnable   
{
	private ServletContext context;
	InetAddress ip;
	String company;
	String dateRegister;
	String licenseKey;
	 
    public LincenseThread(ServletContext context, String company, String dateRegister, String licenseKey) 
    {
        this.context = context;
        this.company = company;
        this.dateRegister = dateRegister;
        this.licenseKey = licenseKey;
    }
 
    @Override
    public void run() 
    {
        try 
        {
        	
        	 
        	
        	ip = InetAddress.getLocalHost();
        	
        	DateTime dt = new DateTime();  // current time
        	String hour = dt.getHour();     // gets the current month
        	String date = dt.getDate(); // gets hour of day
        	String minute=dt.getMinute();  
        		
        		//Tùy chỉnh thời gian chạy chỗ này, test cho mỗi ngày chạy 1 lần vào 19 giờ đêm
			if( Integer.parseInt(hour) >= 20 ){
				
				NgungKhachHang ng=new NgungKhachHang();
				ng.NgungkhachhangkhongphatsinhDS();
			}
			
				
			

        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
	
    
	private String getDateTime() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
 
}