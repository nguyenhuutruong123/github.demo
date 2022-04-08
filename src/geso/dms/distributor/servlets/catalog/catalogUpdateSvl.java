package geso.dms.distributor.servlets.catalog;

import geso.dms.distributor.beans.catalog.*;
import geso.dms.distributor.beans.catalog.imp.*;
import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.oreilly.servlet.MultipartRequest;

public class catalogUpdateSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet
{
	static final long serialVersionUID = 1L;
	private PrintWriter out;

	public catalogUpdateSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		HttpSession session = request.getSession();
		this.out = response.getWriter();
		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		out.println(userId);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

		

		String id = util.getId(querystring);
		
		Icatalog nspBean = new catalog();
		nspBean.setId(id);
		nspBean.setUserId(userId);
	
		nspBean.init();
		session.setAttribute("nspBean", nspBean);
		session.setAttribute("userId", userId);
		String nextJSP = "";
		if (request.getQueryString().indexOf("display") >= 0) {
			nextJSP = "/AHF/pages/Center/catalogDisplay.jsp";
		} else {
			nextJSP = "/AHF/pages/Center/catalogUpdate.jsp";
		}
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		Icatalog nspBean = new catalog();

		Utility util = new Utility();


	
		String folderupload= getServletContext().getInitParameter("catalogpath");
		System.err.println("folder upload  "+folderupload);

		String action = null;
		session.setAttribute("userId", util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"))));
	
		String contentType = request.getContentType();
		String id = null ;
		String chungloai=null ;
		String ghichu=null;
		String filename=null;
		String userId=null;
		String duongdan=null;
		System.out.println("vao do post : ");
		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
			MultipartRequest multi = new MultipartRequest(request, folderupload, 20000000, "UTF-8");
			try 
			{
			 action=	multi.getParameter("action");
				 userId = util.antiSQLInspection(multi.getParameter("userId"));
				nspBean.setUserId(userId);
				String ten = util.antiSQLInspection(multi.getParameter("ten"));
				nspBean.setTen(ten);

				 id = util.antiSQLInspection(multi.getParameter("nspId"));
				nspBean.setId(id);
				
				

				chungloai = util.antiSQLInspection(multi.getParameter("chungloai"));
				if (!(chungloai == null))
					nspBean.setChungloai(chungloai);
				
				 ghichu = util.antiSQLInspection(multi.getParameter("ghichu"));
				if (!(ghichu == null))
					nspBean.setGhichu(ghichu);

				 String nhanhang = util.antiSQLInspection(multi.getParameter("nhanhang"));
					if (!(nhanhang == null))
						nspBean.setNhanhang(nhanhang);
				
					 String ma = util.antiSQLInspection(multi.getParameter("Ma"));
						if (!(ma == null))
							nspBean.setMa(ma);
					 
				 Enumeration files = multi.getFileNames();
					String filenameu = "";
					while (files.hasMoreElements())
					{
						String name = (String) files.nextElement();
						filenameu = multi.getFilesystemName(name);
						System.out.println("name  " + name);
						
						File f=new File(folderupload+filenameu);
						f.renameTo(new File (folderupload+ma+"___"+filenameu));
					}

					 filename =  filenameu;

					 String fileName1 = multi.getParameter("tenfile");
						if (fileName1 == null)
							fileName1 = "";
				 if (!(filename == null))
					nspBean.setDuongdan(ma+"___"+filename);
				 else
					 nspBean.setDuongdan(fileName1);
			
				 
				 
				 if (action.equals("download"))
				 {
					 System.out.println("___Vao DOWNLOAD FILE....");
						
						 System.out.println("___Vao DOWNLOAD FILE...."+fileName1);
						if (fileName1.trim().length() > 0)
						{
							try
							{
								FileInputStream fileToDownload = new FileInputStream(folderupload+ fileName1);
								ServletOutputStream output1 = response.getOutputStream();
								response.setContentType("application/octet-stream");
								
								System.out.println(fileName1);
								response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName1 + "\"");
								response.setContentLength(fileToDownload.available());
								int c;
								while ((c = fileToDownload.read()) != -1)
								{
									output1.write(c);
								}
								output1.flush();
								output1.close();
								fileToDownload.close();
							} catch (Exception e)
							{
								System.out.println("___Loi DOWNLOAD file: " + e.getMessage());
							}
						} 
						
				 }
				
				 
				 
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	/*	else
		{
			 action=	geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		 userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		nspBean.setUserId(userId);
		String ten = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ten")));
		nspBean.setTen(ten);

		String fileName1 = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tenfile"));
		if (fileName1 == null)
			fileName1 = "";
		nspBean.setDuongdan(fileName1);
		
		 id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nspId")));
		nspBean.setId(id);

		 chungloai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chungloai")));
		if (!(chungloai == null))
			nspBean.setChungloai(chungloai);
		
		 ghichu = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ghichu")));
		if (!(ghichu == null))
			nspBean.setGhichu(ghichu);

		 filename = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("filename")));
		if (!(filename == null))
			nspBean.setDuongdan(filename);
		}*/
		if (action.equals("save")) {
			if(id.trim().length() <= 0)
			{
				if(!nspBean.saveNsp())
				{
					System.out.println("MSG: "+nspBean.getMsg());
					session.setAttribute("nspBean", nspBean);
					session.setAttribute("userId", userId);
					String nextJSP = "/AHF/pages/Center/catalogNew.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IcatalogList obj = new catalogList();
					obj.init("");
					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);
					String nextJSP = "/AHF/pages/Center/catalog.jsp";
					response.sendRedirect(nextJSP);
				}
			}
			else
			{
				if(!nspBean.updateNsp())
				{
					
					session.setAttribute("nspBean", nspBean);
					session.setAttribute("userId", userId);
					String nextJSP = "/AHF/pages/Center/catalogUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IcatalogList obj = new catalogList();
					obj.init("");
					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);
					String nextJSP = "/AHF/pages/Center/catalog.jsp";
					response.sendRedirect(nextJSP);
				}
			}
		}
		
	}
	
	
	
}