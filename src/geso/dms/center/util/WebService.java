package geso.dms.center.util;




import geso.dms.center.db.sql.dbutils;

import java.util.List;

import javax.xml.soap.*;
import javax.xml.soap.Node;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.*;

import org.w3c.dom.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import com.google.gson.Gson;
import com.oreilly.servlet.MultipartRequest;

import java.util.Hashtable;

public class WebService {
	public static  String urlService = "http://localhost:9999/AHF/ServiceBaoCao.asmx";
//	public static String urlService = "http://1.53.252.229/FMCGBaoCao/ServiceBaoCao.asmx";
//	public static String urlService = "http://localhost:62430/ServiceBaoCao.asmx";
//public static String urlService = "http://118.69.168.124:8089/AhfTest/ServiceBaoCao.asmx";
	
	public final static String namespace = "http://tempuri.org/"; //namespace ngoài 


	public static String getData(String methodName,String[]keys,String[]values )
	{
		SOAPConnectionFactory soapConnectionFactory = null;
		SOAPConnection soapConnection = null;
		try {
			// Create SOAP Connection
			soapConnectionFactory = SOAPConnectionFactory.newInstance();
			soapConnection = soapConnectionFactory.createConnection();
			// Send SOAP Message to SOAP Server
			URL endpoint = new URL (null, urlService, new URLStreamHandler () {
				protected URLConnection openConnection (URL url) throws IOException {
					// The url is the parent of this stream handler, so must create clone
					URL clone = new URL (url.toString ());
					URLConnection connection = clone.openConnection ();
					// If you cast to HttpURLConnection, you can set redirects
					// connection.setInstanceFollowRedirects (false); // no redirs
					connection.setConnectTimeout (5 * 1000);      // 15 sec
					connection.setReadTimeout (5 * 1000);         // 15 sec
					// Custom header
					connection.addRequestProperty ("Developer-Mood", "Happy");
					return connection;
				}});

			MessageFactory messageFactory = MessageFactory.newInstance();
			SOAPMessage soapMessage = messageFactory.createMessage();

			MimeHeaders headers = soapMessage.getMimeHeaders();
			headers.addHeader("SOAPAction", namespace+(namespace.lastIndexOf("/") == namespace.length() - 1?"":"/")+methodName); // +methodName DangNhap_SalesUp

			SOAPPart sp = soapMessage.getSOAPPart();

			SOAPEnvelope env = sp.getEnvelope();
			env.addNamespaceDeclaration("xsd","http://www.w3.org/2001/XMLSchema");
			env.addNamespaceDeclaration("xsi","http://www.w3.org/2001/XMLSchema-instance");
			SOAPBody bd = env.getBody();
			SOAPElement be = bd.addChildElement(env.createName(methodName,"",namespace)); 

			// them param vao input
			if(keys != null)
			{
				if(keys.length != values.length)       		
					System.out.println("keys.length !=  values.length ");      		
				else
					for(int i= 0; i < keys.length; i ++)       			
						be.addChildElement(keys[i]).addTextNode(values[i]);       			
			}
			soapMessage.saveChanges();

			SOAPMessage soapResponse = soapConnection.call(soapMessage, endpoint);

			/*ByteArrayOutputStream stream = new ByteArrayOutputStream();
			soapResponse.writeTo(stream);
			String message = new String(stream.toByteArray(), "utf-8"); */


			SOAPBody soapBody = soapResponse.getSOAPBody();
			NodeList nodes = soapBody.getElementsByTagName("BCDonHangBanTrongKyResult");

			String someMsgContent = null;
			org.w3c.dom.Node node = nodes.item(0);
			someMsgContent = node != null ? node.getTextContent() : "";
			return someMsgContent;

		} catch (Exception e) {
			System.err.println("Error occurred while sending SOAP Request to Server");
			e.printStackTrace();
			if(soapConnection != null) 
				try{soapConnection.close();}catch (Exception e2) {
				}
				return null;	
		}
	}
	
	public static String BCDonHangBanTrongKy(String path ,String filename,String extension,String query)
	{
		String methodName ="BCDonHangBanTrongKy";
		String[]keys = {"path","filename","extension","query"};
		String[]values  = {path,filename,extension,query};	    
		String str = getData(methodName, keys, values);
		return str;

	}
	
	public static String CallAPI(String methodName,String startRowCol,String path ,String filename,String extension,String query)
	{
		String[]keys = {"startRowCol","path","filename","extension","query"};
		String[]values  = {startRowCol,path,filename,extension,query};	   
		System.out.println(startRowCol);
		System.out.println(path);
		System.out.println(filename);
		System.out.println(extension);
		System.out.println(query);

		String str = getData(methodName, keys, values);
		return str;
	}

	
	public static String CallAPI_queue(String logId)
	{
		String[]keys = {"logId"};
		String[]values  = {logId};	    
		String str = getData("baocao_log", keys, values);
		return str;
	}

	public static String BCXuatKhuyenMai(String path ,String filename,String extension,String query)
	{
		String methodName ="BCXuatKhuyenMai";
		String[]keys = {"path","filename","extension","query"};
		String[]values  = {path,filename,extension,query};	    
		String str = getData(methodName, keys, values);
		return str;
	}


	public static String BCNhapxuatton(String path ,String filename,String extension,String query,String discount)
	{
		String methodName ="BCNhapxuatton";
		System.out.println("path "+path);
		System.out.println("filename "+filename);
		System.out.println("extension "+extension);
		System.out.println("query "+query);
		System.out.println("discount "+discount);
		String[]keys = {"path","filename","extension","query","discount"};
		String[]values  = {path,filename,extension,query,discount};	    
		String str = getData(methodName, keys, values);
		return str;
	}

	public static String BCNhapxuattonchitiet(String path ,String filename,String extension,String query,String discount)
	{
		String methodName ="BCNhapxuattonchitiet";
		String[]keys = {"path","filename","extension","query","discount"};
		String[]values  = {path,filename,extension,query,discount};	    
		String str = getData(methodName, keys, values);
		return str;

	}

	public static String BCXNT(String path ,String filename,String extension,String param)
	{
		String methodName ="BCXNT";
		String[]keys = {"path","filename","extension","param"};
		String[]values  = {path,filename,extension,param};	    
		String str = getData(methodName, keys, values);
		return str;

	}

	public static String BCDonHangBanTrongKy(String key)
	{
		String methodName ="BCDonHangBanTrongKy_reponse";
		String[]keys = {"key"};
		String[]values  = {key};	    
		String str = getData(methodName, keys, values);
		return str;

	}


	public static String getStringValue(Cell[] cells,int vitri)
	{
		try
		{
			return cells[vitri].getContents().toString().replace("\t", "").replace(",", "").replace(" ", "").trim();
		}
		catch (Exception e) {
			return "";
		}
	}

	public static  double getDoubleValue(Cell[] cells,int vitri)
	{
		try
		{
			return Double.parseDouble(cells[vitri].getContents().toString().replace("\t", "").replace(",", "").replace(" ", "").trim());
		}
		catch (Exception e) {
			return 0.0;
		}
	}
	public static  Integer getIntValue(Cell[] cells,int vitri)
	{
		try
		{
			return Integer.parseInt(cells[vitri].getContents().toString().replace("\t", "").replace(",", "").replace(" ", "").trim());
		}
		catch (Exception e) {
			return 0;
		}
	}

}