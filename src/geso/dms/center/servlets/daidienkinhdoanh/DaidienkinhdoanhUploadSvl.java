package geso.dms.center.servlets.daidienkinhdoanh;

import geso.dms.center.beans.daidienkinhdoanh.IDaidienkinhdoanhList;
import geso.dms.center.beans.daidienkinhdoanh.imp.Daidienkinhdoanh;
import geso.dms.center.beans.daidienkinhdoanh.imp.DaidienkinhdoanhList;
import geso.dms.center.db.sql.dbutils;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import com.aspose.cells.BorderLineType;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.oreilly.servlet.MultipartRequest;


public class DaidienkinhdoanhUploadSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet
{
	static final long serialVersionUID = 1L;
	private PrintWriter out;

	public DaidienkinhdoanhUploadSvl()
	{
		super();
	}
	
	private static Pattern pattern;
	private static Matcher matcher;
	private static final String IMAGE_PATTERN = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)";


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		this.out = response.getWriter();
		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		if (userId.length() == 0)
			userId = Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

		String id = util.getId(querystring);
		String view = Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		if (view == null) {
			view = "";
		}
		
		String param = "";
		if(view.trim().length() > 0) param ="&view="+view;
		if (Utility.CheckSessionUser(session, request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if (Utility.CheckRuleUser(session, request, response, "DaidienkinhdoanhSvl", param, Utility.XEM))
		{
			Utility.RedireactLogin(session, request, response);
		} 
		session.setAttribute("userId", userId);

		Daidienkinhdoanh ddkdBean = new Daidienkinhdoanh(id);
		ddkdBean.init();
		ddkdBean.setView(view);
		ddkdBean.setUserId(userId);

		session.setAttribute("ddkdBean", ddkdBean);
		session.setAttribute("userId", userId);
		String nextJSP = "";
		nextJSP = "/AHF/pages/Center/DaidienkinhdoanhUploadUpdate.jsp";			

		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();

		String id = null;
		String fileName = null;
		String userId = null;
		String action = "", view = "";	
		String folderupload = getServletContext().getInitParameter("Anh_DDKD");
		dbutils db = new dbutils();
		Daidienkinhdoanh ddkdBean = new Daidienkinhdoanh(db);

		ArrayList<String> imgList = new ArrayList<String>();
		if(ServletFileUpload.isMultipartContent(request)){
			List<FileItem> items;
			try {
				int index = 0;
				ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
				upload.setHeaderEncoding("UTF-8");
				items = upload.parseRequest(request);
				for (FileItem item : items) {
					if (item.isFormField()) {
						System.out.println("Field: " + item.getFieldName());
						System.out.println("Field value: " + item.getString("UTF-8"));
						if (item.getFieldName().equals("action")) {
							action = item.getString("UTF-8");
						}
						if (item.getFieldName().equals("userId")) {
							userId = item.getString("UTF-8");
						}
						if (item.getFieldName().equals("id")) {
							id = item.getString("UTF-8");
						}
						if (item.getFieldName().equals("view")) {
							view = item.getString("UTF-8");
						}
						if (item.getFieldName().equals("valuedownload")) {
							fileName = item.getString("UTF-8");
						}
					} else {
						System.out.println("File name: " + item.getName());
						System.out.println("File type: " + item.getContentType());

						String x = item.getName();
						pattern = Pattern.compile(IMAGE_PATTERN);
						matcher = pattern.matcher(x);
						if (x != null && x.length() > 0){
							
							if (!matcher.matches()) {
								ddkdBean = new Daidienkinhdoanh(id);							
								ddkdBean.init();
								ddkdBean.setUserId(userId);
								ddkdBean.setMessage("Không tìm thấy File hoặc File sai định dạng (jpg|png|gif|bmp)!");
								db.shutDown();
	
								session.setAttribute("ddkdBean", ddkdBean);
								session.setAttribute("userId", userId);
								String nextJSP = "";
								nextJSP = "/AHF/pages/Center/DaidienkinhdoanhUploadUpdate.jsp";			
								response.sendRedirect(nextJSP);
								return;
							}
							
							String temp_name = "";//getFullDateTime();
							imgList.add("DDKD_"+id+temp_name+matcher.group(2));
							//File sub = new File(folderupload+"\\\\"+id);
							String folderPath = folderupload.replace("\\\\","\\")+id+"\\";
							System.out.println("Folderpath: "+folderPath);
							ddkdBean.setFolderPath(folderPath); //Set folder path
							//sub.mkdir();
							//File uploadedFile = new File(folderupload+"\\\\"+id, "DDKD_"+id+temp_name+matcher.group(2));
							File uploadedFile = new File(folderupload+"\\\\", "DDKD_"+id+temp_name+matcher.group(2));
							try {
								item.write(uploadedFile);
							} catch (Exception e) {
								e.printStackTrace();
							}
							index++;
						}
					}					
				}
			} 
			catch (FileUploadException e) {
				e.printStackTrace();
			}
		}		
		ddkdBean.setView(view);
		ddkdBean.setId(id);
		ddkdBean.setUserId(userId);
		ddkdBean.setImgList(imgList);

		System.out.println("Action: "+action);

		if (action.equals("download"))
		{		
			System.out.println("DOWNLOAD FILE: "+fileName);
			if (fileName != null && fileName.trim().length() > 0)
			{
				try
				{
					FileInputStream fileToDownload = new FileInputStream(folderupload+fileName);
					ServletOutputStream output1 = response.getOutputStream();
					response.setContentType("application/octet-stream");
					response.setHeader("Content-Disposition", "attachment; fileName=\"" +fileName + "\"");
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
					e.printStackTrace();
				}
			} 
			db.shutDown();
		}

		session.setAttribute("userId", Utility.antiSQLInspection(userId));

		if (action.equals("save")) {
			String msg = "";
			boolean temp = ddkdBean.saveImage(db, imgList);
			db.shutDown();
			if (temp) {
				msg = "Upload thành công!";
			}
			else {
				msg = "Upload thất bại: "+ddkdBean.getMessage();
			}
			
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");	

			IDaidienkinhdoanhList obj = new DaidienkinhdoanhList();
			obj.setMsg(msg);
			obj.setView(view);
			obj.setRequestObj(request);
			obj.setUserId(userId);
			obj.init("");
			session.setAttribute("obj", obj);
			String nextJSP = "/AHF/pages/Center/DaiDienKinhDoanh.jsp";
			response.sendRedirect(nextJSP);
			return;
		}		
	}

	private String getFullDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static void main(String[] args) {		
		pattern = Pattern.compile(IMAGE_PATTERN);
		String text = "abcwqeqdee.qweqf.png";
		matcher = pattern.matcher(text);
		System.out.println(matcher.matches());
		System.out.println(matcher.group(0));
		System.out.println(matcher.group(1));
		System.out.println(matcher.group(2));
	}

}