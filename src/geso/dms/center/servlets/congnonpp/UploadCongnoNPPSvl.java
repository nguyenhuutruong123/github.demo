package geso.dms.center.servlets.congnonpp;

import geso.dms.center.beans.congnonpp.*;
import geso.dms.center.beans.congnonpp.imp.*;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.User;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import com.oreilly.servlet.MultipartRequest;

@WebServlet("/UploadCongnoNPPSvl")
public class UploadCongnoNPPSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public UploadCongnoNPPSvl() {
        super();
        
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();		
		Utility util = new Utility();
	
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
    
		if (userId.length()==0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));		
		String upload = "upload";		
		ICongnonpp ctBean = new Congnonpp(upload);	
		session.setAttribute("ctBean", ctBean);
	 	response.sendRedirect("/AHF/pages/Center/UploadCongnoNPP.jsp");	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		dbutils db =new dbutils();
		ICongnonpp ctBean;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");		
		
		String contentType = request.getContentType();   
		HttpSession session = request.getSession();
		
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		System.out.println("action đây nè : "+action);		
		Utility util = new Utility();			
		
		//String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));	    		    	  
		ctBean = new Congnonpp("upload");	    
	    	    
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		ctBean.setuserId(userId);	  		
		
		String thoigian = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thoigian")));
		if(thoigian ==null)
			thoigian = "";
		ctBean.setThoigian(thoigian);
		
		String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
		if(diengiai ==null)
			diengiai = "";
		ctBean.setDiengiai(diengiai);
		
		String dvkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));
		if(dvkdId ==null)
			dvkdId ="";
		ctBean.setDvkdId(dvkdId);
		
		String kbhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhId")));
		if(kbhId ==null)
			kbhId ="";
		ctBean.setKbhId(kbhId);
		
		String httt = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("httt")));
		if(httt ==null)
			httt ="";
		ctBean.setHinhthuctt(httt); 		
								
		try
		{	    
		    if(action.equals("save"))			    
		    {	    	
	    		if(!ctBean.SaveUpload(thoigian, dvkdId, kbhId))
	    		{	    			
    		    	session.setAttribute("ctBean", ctBean);
    		    	String nextJSP = "/AHF/pages/Center/UploadCongnoNPP.jsp";
    				response.sendRedirect(nextJSP);
	    		}
	    		else
	    		{
	    			ICongnonppList obj = new CongnonppList();
				    obj.init("");    
					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);
						
					String nextJSP = "/AHF/pages/Center/CongnoNPP.jsp";
					response.sendRedirect(nextJSP);
	    		}			    	   
		    }		  		    
	    }
		catch(Exception ex)
		{	ex.printStackTrace();	}		
		
		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) 
		{		     	 
			//MultipartRequest multi = new MultipartRequest(request, "C:\\java-tomcat\\data\\", 20000000, "UTF-8");
			MultipartRequest multi = new MultipartRequest(request, getServletContext().getRealPath("\\upload\\"), 20000000, "UTF-8");      	
	     	Enumeration files = multi.getFileNames(); 
		  	String filenameu  ="";
		  	while (files.hasMoreElements())
            {
                 String name = (String)files.nextElement();
                 filenameu = multi.getFilesystemName(name); 
                 System.out.println("name  "+name);
            }		  
		  	
		  	userId = util.antiSQLInspection(multi.getParameter("userId"));		  	
		  	ctBean.setuserId(userId);		  			  		  			
		  	
		  	action = multi.getParameter("action");
		  	
		  	thoigian = util.antiSQLInspection(multi.getParameter("thoigian"));
			if(thoigian ==null)
				thoigian = "";
			ctBean.setThoigian(thoigian);
			
			diengiai = util.antiSQLInspection(multi.getParameter("diengiai"));
			if(diengiai ==null)
				diengiai = "";
			ctBean.setDiengiai(diengiai);
			
			dvkdId = util.antiSQLInspection(multi.getParameter("dvkdId"));
			if(dvkdId ==null)
				dvkdId ="";
			ctBean.setDvkdId(dvkdId);
			
			kbhId = util.antiSQLInspection(multi.getParameter("kbhId"));
			if(kbhId ==null)
				kbhId ="";
			ctBean.setKbhId(kbhId);	  			 
			
			httt = util.antiSQLInspection(multi.getParameter("httt"));
			if(httt ==null)
				httt ="";
			ctBean.setHinhthuctt(httt); 
		  	
			//String filename = "C:\\java-tomcat\\data\\" + filenameu;
			String filename=    getServletContext().getRealPath("\\upload\\")+ "\\"+ filenameu;
			if (filename.length() > 0)
			{
				//doc file excel
				File file = new File(filename);
				System.out.println("filename  "+file);
				Workbook workbook;
				ResultSet rs = null;
				
				int indexRow=4;				
				try 
				{
					
					System.out.println(file);
					workbook = Workbook.getWorkbook(file);
					Sheet sheet = workbook.getSheet(0);
					Cell[] cells = sheet.getRow(indexRow);			
					
					System.out.println("So Dong : "+ sheet.getRows());
					String maNPP = "";					
					double sotien = 0;
					
					String str = "";
					String sql = "";
					
					sql = "DELETE CONGNONPP_CT_TMP WHERE NGUOITAO = '"+ userId +"'";
					System.out.println("sql DELETE CONGNONPP_CT_TMP : "+sql);
					if(!db.update(sql))
					{
						db.getConnection().rollback();
						ctBean.setMsg("Lỗi khi chèn dữ liệu vào bảng tạm : " + sql);				
						return;
					}									
					
					String khstr = "";	
					int j = 0;
					for(int i= indexRow+1; i < sheet.getRows();i++)
					{								
						cells = sheet.getRow(i);						
						if (cells.length>0){							
							if(cells[0].getContents().toString().length() > 0)
							{															
						    	try
						    	{
						    		maNPP = cells[0].getContents();
						    		System.out.println("maNPP : "+maNPP);						    							    	
						    	
						    		sotien = Double.parseDouble(cells[2].getContents());
						    		System.out.println("so tien : "+sotien);						    								    	
						    						    		
						    		SaveTmpData(ctBean, maNPP, sotien, thoigian, diengiai, dvkdId, kbhId, httt);						    		
						    	}
						    	catch (Exception e) {e.printStackTrace();}						    							    						    							    
							}
						}																			
					} 									
					
					session.setAttribute("userId", userId);
					session.setAttribute("ctBean", ctBean);
					ctBean.setMsg("Đọc file thành công !");
					String nextJSP;					
					nextJSP = "/AHF/pages/Center/UploadCongnoNPP.jsp";   											
					response.sendRedirect(nextJSP);
				}catch (Exception e) {
					e.printStackTrace();
					System.out.println("Loi : "+e.toString());
				}
			}
		}
		
	}

	private boolean SaveTmpData(ICongnonpp vouBean, String maNPP, double sotien, String thoigian, String diengiai, String dvkdId, String kbhId, String httt)
	{
		dbutils db = new dbutils();
		try
		{		
			db.getConnection().setAutoCommit(false);
			
	    	String sql= "";
	    	
	    	sql = "SELECT PK_SEQ FROM NHAPHANPHOI WHERE SITECODE = '"+ maNPP +"'";
	    	ResultSet rsNPP = db.get(sql);
	    	if(rsNPP.next())
	    	{
	    		sql =
	    			"INSERT INTO CONGNONPP_CT_TMP(THOIGIAN, DIENGIAI, NPP_FK, SOTIEN, DVKD_FK, KBH_FK, HINHTHUCTT, NGUOITAO) " +
	    			"VALUES('"+ thoigian +"', N'"+ diengiai +"', '"+ rsNPP.getString("PK_SEQ") +"', "+ sotien +", "+ dvkdId +", "+ kbhId +", N'"+ httt +"', '"+ vouBean.getuserId() +"')";	    		    		   
				System.out.println("sql insert : "+sql);
				if(!db.update(sql))
				{
					db.getConnection().rollback();
					vouBean.setMsg("Lỗi khi chèn dữ liệu vào bảng tạm. Đã tồn tại dữ liệu");				
					return false;			
				}	
	    	}
	    	else
	    	{
	    		
	    	}	    		    				
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);				
			return true;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			vouBean.setMsg("Lỗi xảy ra trong quá trình đọc file :"+ e.toString());
			return false; 
		}
	}
}
