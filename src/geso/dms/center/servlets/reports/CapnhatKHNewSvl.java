package geso.dms.center.servlets.reports;

import geso.dms.center.beans.Router.IDRouter;
import geso.dms.center.beans.Router.imp.Router;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import com.oreilly.servlet.MultipartRequest;


@WebServlet("/ChuyenkhtrungbayNewSvl")
public class CapnhatKHNewSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	dbutils db;
   
    public CapnhatKHNewSvl() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String querystring = request.getQueryString();
		Utility util=new Utility();
		String userId = util.getUserId(querystring); 
		if (userId.length()==0)
		userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		String id=util.getId(querystring);
		
		IDRouter cnkhBean = new Router(id);
		cnkhBean.setuserId(userId);
				
		//cnkhBean.initDisplay();
		session.setAttribute("userId", userId);	
		session.setAttribute("cnkhBean",cnkhBean);			
		String nextJSP = "/AHF/pages/Center/CapNhatKHUpdate.jsp";//default
		response.sendRedirect(nextJSP);		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		
		List<IDRouter> valueList;
		IDRouter cnkhBean;
		valueList = new ArrayList<IDRouter>();		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String contentType = request.getContentType();	
		Utility util = new Utility();									    	 		
 		
		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) 
		{	
			//MultipartRequest multi = new MultipartRequest(request, "C:\\java-tomcat\\data\\", 20000000, "UTF-8");
			MultipartRequest multi = new MultipartRequest(request, getServletContext().getRealPath("\\upload\\"), 20000000, "UTF-8"); 	 		
			Enumeration files = multi.getFileNames(); 
			String filenameu  ="";
			
			String id = util.antiSQLInspection(multi.getParameter("id"));
			System.out.println("ID : "+id);
		    if(id == null)
		    {  	
		    	cnkhBean = new Router("");
		    }
		    else
		    {
		    	cnkhBean = new Router(id);
		    }
			
			String action = multi.getParameter("action");
	 		System.out.println("Action la: " + action);
	 		
	 		String userId = util.antiSQLInspection(multi.getParameter("userId"));
	 		cnkhBean.setuserId(userId);
	 		
	 		String diengiai = util.antiSQLInspection(multi.getParameter("diengiai"));
	 		cnkhBean.setDiengiai(diengiai);	 	
	 		
			while (files.hasMoreElements())
			{
                 String name = (String)files.nextElement();
                 filenameu = multi.getFilesystemName(name); 
                 System.out.println("name  "+filenameu);
			}			  				  
			String[] resultstr;
			//String filename = "C:\\java-tomcat\\data\\" + filenameu;
			String filename=    getServletContext().getRealPath("\\upload\\")+ "\\"+ filenameu;
			if (filename.length() > 0)
			{
				//doc file excel
				File file = new File(filename);
				System.out.println("filename  "+file);
				Workbook workbook;
				
				
				int j;
				String values = "";
				
				try 
				{	
					workbook = Workbook.getWorkbook(file);
					for(int s=1; s <= (workbook.getNumberOfSheets()-2); s++)
					{	System.out.println("--------------------------------------------------------- Sheet thu : "+s);
						Sheet sheet = workbook.getSheet(s);												
						int dem = 0;
						int indexRow=8;
						int dodai = 9;
						String spIdstr = "";
						String result = "";
						String tuyenId = "";
						
						Cell[] cellnpp = sheet.getRow(4);
						String nppId = cellnpp[1].getContents();
						
						System.out.println("getrows : "+sheet.getRows());
						for(int i= (int)(indexRow+2); i < sheet.getRows();i++)
						{	
							
							
							Cell[] cells = sheet.getRow(indexRow);
							
							while( dodai < cells.length){
								if(cells[dodai].getContents().trim().length() > 0)
								{
									dodai=dodai+1;								
								}
								else
								{	break;	}
							}	
							System.out.println("cell : "+"'"+cells[0].getContents());
							System.out.println("cell : "+"'"+cells[0].getContents().substring(4).trim()+"'");
							
							if(cells[0].getContents().substring(4).trim().equals("2"))
							{
								tuyenId = "2";
							}
							else if(cells[0].getContents().substring(4).trim().equals("3"))
							{
								tuyenId = "3";
							}
							else if(cells[0].getContents().substring(4).trim().equals("4"))
							{
								tuyenId = "4";
							}
							else if(cells[0].getContents().substring(4).trim().equals("5"))
							{
								tuyenId = "5";
							}
							else if(cells[0].getContents().substring(4).trim().equals("6"))
							{
								tuyenId = "6";
							}
							else if(cells[0].getContents().substring(4).trim().equals("7"))
							{
								tuyenId = "7";
							}
							else
							{
								tuyenId = "0";
							}
							for(int k = 0; k < cells.length; k++){
								if(k==0)
								{
									values = cells[k].getContents();									
								}
								for(k=4; k<cells.length; k++)
								{ 																						
									values=values + "," + cells[k].getContents();									
								}																	
							}							
							
							cells = sheet.getRow(i);														
														
							if (cells.length>0)
							{	System.out.println("STT : "+ cells[0].getContents());							
								if(cells[0].getContents().toString().length() >0)
								{	dem++;
																						
									for(j = 4; j < dodai; j++)
									{								
										String[] valuesstr = values.split(",");																				
										spIdstr = spIdstr + tuyenId +","+ cells[1].getContents()+","+valuesstr[(j-3)]+","+cells[j].getContents()+";";											
									}									
								}								
							}							
							else 
							{
								indexRow = (indexRow + dem) + 3;
								i = indexRow + 1;
								dem = 0;																
							}																					
							
						} //for i
						System.out.println("Chuoi lay ttin sp : "+spIdstr);	
						String [] sheetNames = workbook.getSheetNames();
						String[] ddkdId = sheetNames[s].split("-");  // lay ma DDKD
						String[] spId = spIdstr.split(";");							
						for(int str2=0; str2 < spId.length; str2++)
						{																			
							result += nppId + "," + ddkdId[0] + "," + spId[str2] + ";";								
						}						
						
						String arr[] = result.split(";");						
						for(int t = 0; t < arr.length; t++ )
						{
							//System.out.println("Dong "+t+" : "+ arr[t]);
							String arr2[] = arr[t].split(",");
							IDRouter cnkhBean1 = new Router(arr2[0], arr2[1], arr2[2],arr2[3], arr2[4], arr2[5]);
							valueList.add(cnkhBean1);														
						}																									
					}
					cnkhBean.setcnkhValue(valueList);													
				
				}catch(Exception er)
				{
					cnkhBean.setMessage("Thong bao loi : "+er.toString());
					System.out.println(er.toString());	
					return;
				}														
			}
		//} //if ((contentType != null)
		
		if(action.equals("save"))
		{				
			if(id == null)
			{
				/*session.setAttribute("cnkhBean", cnkhBean);
				String nextJSP = "/AHF/pages/Center/CapNhatKHNew.jsp";
				response.sendRedirect(nextJSP);*/
				
				if (!cnkhBean.CreateCnkh(valueList))
				{					
					session.setAttribute("cnkhBean", cnkhBean);
					String nextJSP = "/AHF/pages/Center/CapNhatKHNew.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{	
					System.out.println("Doc thanh cong!");		
					IDRouter obj = new Router();
					obj.setuserId(userId);					
					obj.setStatus("1");
					obj.init();		
					obj.createCapnhatKHList();
					session.setAttribute("obj", obj);
					String nextJSP = "/AHF/pages/Center/CapNhatKH.jsp";
					response.sendRedirect(nextJSP);									
				}				
			}
			
			else
			{	
				if (!cnkhBean.UpdateCnkh(valueList))
				{							
								
					session.setAttribute("cnkhBean", cnkhBean);
					String nextJSP = "/AHF/pages/Center/CapNhatKHUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					System.out.println("Doc thanh cong!");		
					IDRouter obj = new Router();
					obj.setuserId(userId);					
					obj.setStatus("1");
					obj.init();		
					obj.createCapnhatKHList();
					session.setAttribute("obj", obj);
					String nextJSP = "/AHF/pages/Center/CapNhatKH.jsp";
					response.sendRedirect(nextJSP);	
				}					
			}
		} //action = "save" 
		}
	}
	
	private String getDateTime() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

}
