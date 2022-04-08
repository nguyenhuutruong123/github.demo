package geso.dms.center.util;

import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.WebService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
@WebServlet("/ThongBaoSmsSvl")
public class ChatSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ChatSvl() {
		super();

	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");	    	    
		HttpSession session = request.getSession();	    			   		    
		String send = request.getParameter("check");

		Utility util=new Utility();
		String action = request.getParameter("action");	    

		if(action != null && action.equals("queue"))
		{
			String _path = send;
			String kq = "";
			
			String sccFile = _path +"success.txt";
			String failF = _path +"fail.txt";
			
			
			if(new File(sccFile).exists())
			{
				kq="SUCCESS";
			}
			else if (new File(failF).exists())
			{
				kq="FAIL";
			}
			//System.out.println("sccFile la "+sccFile);
			//System.out.println("failF la "+failF);
			//System.out.println("kq la "+kq);
			JsonArray NEW = new JsonArray();
			JsonObject json = new JsonObject();
			JsonArray g = new JsonArray();
			g.add(new JsonPrimitive(kq));

			json.add("KETQUA", g);
			NEW.add(json);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(NEW.toString());	
		}
		else
		if (send != null && send.length() > 0)
		{
			//System.out.println("check file: "+send);
			File f = new File(send);
			boolean kq = f.exists();
			//System.out.println("ket qua check la: "+kq);
			String kq1 = "FALSE";
			if (kq)
			{
				kq1 = "TRUE";
			}
			//System.out.println("kq1 la "+kq1);
			JsonArray NEW = new JsonArray();
			JsonObject json = new JsonObject();
			JsonArray g = new JsonArray();
			g.add(new JsonPrimitive(kq1));

			json.add("KETQUA", g);
			NEW.add(json);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(NEW.toString());	
		}
		else if (action != null && action.equals("DoDuLieu")){
			System.out.println("Bố mày đây!");
			response.setContentType("application/xlsm");

			String report_name = util.antiSQLInspection(request.getParameter("report_name"));
			response.setHeader("Content-Disposition", "attachment; filename = "+report_name + getCurrentDate()+ ".xlsm");
			String Url = util.antiSQLInspection(request.getParameter("linkUrl"));

			System.out.println("Link "+Url);
			if (Url == null)
				Url = "";

			try {
				LayFile(response.getOutputStream(),Url);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void LayFile(OutputStream out,String path) throws Exception {

		try {
			String file = path;
			System.out.println(file);
			FileInputStream fstream = new FileInputStream(file);
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);	

			//Worksheets worksheets = workbook.getWorksheets();
			workbook.save(out);
			fstream.close();

		} catch (Exception ex) {
			System.out.println(ex.toString());
			ex.printStackTrace();
			throw new Exception("Không tạo được báo cáo.");
		}
	}

	public static String getCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
