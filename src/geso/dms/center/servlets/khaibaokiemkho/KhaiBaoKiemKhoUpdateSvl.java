package geso.dms.center.servlets.khaibaokiemkho;

import geso.dms.center.beans.khaibaokiemkho.IKhaiBaoKiemKho;
import geso.dms.center.beans.khaibaokiemkho.IKhaiBaoKiemKho;
import geso.dms.center.beans.khaibaokiemkho.IKhaiBaoKiemKhoList;
import geso.dms.center.beans.khaibaokiemkho.imp.KhaiBaoKiemKho;
import geso.dms.center.beans.khaibaokiemkho.imp.KhaiBaoKiemKhoList;
import geso.dms.center.util.Utility;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
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

@WebServlet("/KhaiBaoKiemKhoUpdateSvl")
public class KhaiBaoKiemKhoUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	public KhaiBaoKiemKhoUpdateSvl()
	{
		super();
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		IKhaiBaoKiemKho csxBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		
		out.println(userId);
		
		if (userId.length() == 0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		
		String id = util.getId(querystring);
		
		csxBean = new KhaiBaoKiemKho(id);
		
		csxBean.setId(id);
		csxBean.setUserId(userId);
		
		csxBean.init();
		session.setAttribute("csxBean", csxBean);
		String nextJSP = "/AHF/pages/Center/KhaiBaoKiemKhoUpdate.jsp";	
		if(querystring.contains("view"))	
			nextJSP = "/AHF/pages/Center/KhaiBaoKiemKhoDisplay.jsp";	
		response.sendRedirect(nextJSP);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IKhaiBaoKiemKho csxBean;
		String contentType = request.getContentType();
		Utility util = new Utility();
		String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
		if (id == null)
		{
			csxBean = new KhaiBaoKiemKho();
		} else
		{
			csxBean = new KhaiBaoKiemKho(id);
		}
		
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		csxBean.setUserId(userId);
		
		String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
		if (diengiai == null)
			diengiai = "";
		csxBean.setDiengiai(diengiai);
		
		String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
		if (tungay == null)
			tungay = "";
		csxBean.setTuNgay(tungay);
		
		String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
		if (denngay == null)
			denngay = "";
		csxBean.setDenNgay(denngay);
		String[] chon = request.getParameterValues("spId");
		String spChonIds = "";
		if (chon != null)
		{
			for (int i = 0; i < chon.length; i++)
			{
				if(chon[i].length() > 0)
					spChonIds += chon[i] + ",";
			}
			
			if (spChonIds.trim().length() > 0)
			{
				spChonIds = spChonIds.substring(0, spChonIds.length() - 1);
			}
		}
		System.out.println("spChonIds " + spChonIds);
		
		csxBean.setSpIds(spChonIds);
		
		String[] khIds = request.getParameterValues("khIds");
		String khchon = "";
		if (khIds != null)
		{
			for (int i = 0; i < khIds.length; i++)
			{
				if(khIds[i].length() > 0)
					khchon += khIds[i] + ",";
			}
			
			if (khchon.trim().length() > 0)
			{
				khchon = khchon.substring(0, khchon.length() - 1);
			}
		}
		csxBean.setKhId(khchon);
		
		 if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) 
			{
			//MultipartRequest multi = new MultipartRequest(request, "C:\\java-tomcat\\data\\", 20000000, "UTF-8");
				MultipartRequest multi = new MultipartRequest(request, getServletContext().getRealPath("\\upload\\"), 20000000, "UTF-8"); 
			    userId = util.antiSQLInspection(multi.getParameter("userId"));
			 	csxBean.setUserId(userId);
				
				csxBean.setId(util.antiSQLInspection(multi.getParameter("id"))==null? "":util.antiSQLInspection(multi.getParameter("id")));	 
				csxBean.setTuNgay(util.antiSQLInspection(multi.getParameter("tungay")));
				csxBean.setDenNgay(util.antiSQLInspection(multi.getParameter("denngay")));
				csxBean.setDiengiai(util.antiSQLInspection(multi.getParameter("diengiai")));
				 chon = multi.getParameterValues(("spId"));
				 spChonIds = "";
				if (chon != null)
				{
					for (int i = 0; i < chon.length; i++)
					{
						if(chon[i].length() > 0)
							spChonIds += chon[i] + ",";
					}
					
					if (spChonIds.trim().length() > 0)
					{
						spChonIds = spChonIds.substring(0, spChonIds.length() - 1);
					}
				}
				System.out.println("spChonIds " + spChonIds);
				
				csxBean.setSpIds(spChonIds);
				
				khIds = multi.getParameterValues(("khIds"));
				khchon = "";
				if (khIds != null)
				{
					for (int i = 0; i < khIds.length; i++)
					{
						if(khIds[i].length() > 0)
							khchon += khIds[i] + ",";
					}
					
					if (khchon.trim().length() > 0)
					{
						khchon = khchon.substring(0, khchon.length() - 1);
					}
				}
				csxBean.setKhId(khchon);
				csxBean.setId(util.antiSQLInspection(multi.getParameter("khchon")));
				
			  	Enumeration files = multi.getFileNames(); 
			  	String filenameu  ="";
			  	while (files.hasMoreElements())
	            {
	                 String name = (String)files.nextElement();
	                 filenameu = multi.getFilesystemName(name); 
	                 System.out.println("name :   "+name);;
	            }
			  	boolean err = false;
			  //String filename = "C:\\java-tomcat\\data\\" + filenameu;
				String filename=    getServletContext().getRealPath("\\upload\\")+ "\\"+ filenameu;
				if(filenameu == null)
				{
					csxBean.init();
					csxBean.setMsg("Không có file nào được upload");
				}
				if (filenameu != null && filename.length() > 0)
				{
					//doc file excel
					File file = new File(filename);
					System.out.println("filename  "+file);
					Workbook workbook;
					int indexRow=4;
			
					try 
					{						
						System.out.println(file);
						workbook = Workbook.getWorkbook(file);
			
						Sheet[] sheet1 = workbook.getSheets();
						Sheet sheet=sheet1[0];					 
						Cell[] cells ;
						String khachhang_fks = "";
						int sokh = 0;
						System.out.println("getRows = " +sheet.getRows());
						for(int i= indexRow; i < sheet.getRows();i++)
						{	
							cells = sheet.getRow(i);
							
							if (cells.length >= 2){
								String ma = cells[1].getContents().toString();
								
								if(ma.length() > 0)
								{
									String stt = cells[0].getContents().toString();
									String KHACHHANG_FK = cells[1].getContents().toString();
								
									khachhang_fks += "'" + KHACHHANG_FK + "',";
									sokh ++;
								}
								
							}							
						}
						if(sokh == 0)
							csxBean.setMsg("Không có khách hàng nào được chọn!");
						if(sokh > 0)
							csxBean.setMsg("Có "+sokh+" khách hàng được upload!");
						if(khachhang_fks.length() > 0)
							khachhang_fks = khachhang_fks.substring(0, khachhang_fks.length() - 1);

						csxBean.getId_Khachhang(khachhang_fks);
						
					}catch(Exception er){
						er.printStackTrace();
						System.out.println("Exception. "+er.getMessage());
						csxBean.setMsg("Lỗi trong quá trình upload: " + er.getMessage());
					}
				}
				
		    	csxBean.createRs();
		    	session.setAttribute("csxBean", csxBean);  	 		
		    	response.sendRedirect("/AHF/pages/Center/KhaiBaoKiemKhoNew.jsp");
		    	return;
			}
		    
		
		
		
		
		/*String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));
		if (trangthai == null)
			trangthai = "0";
		csxBean.setTrangthai(trangthai);*/
		
		String[] spDoithu = request.getParameterValues("spdt");
		String[] dvtDoithu = request.getParameterValues("dvdt");
		
		csxBean.setSpDoithu(spDoithu);
		csxBean.setDvtDoithu(dvtDoithu);
		
		//String[] spId = request.getParameterValues("spId");
		//String[] spMa = request.getParameterValues("spMa");
		//String[] spTen = request.getParameterValues("spTen");
		//String[] giaban = request.getParameterValues("giaban");
		//String[] donvi = request.getParameterValues("donvi");
		
		
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		if (action.equals("save"))
		{
			if (id == null)
			{
				if (!(csxBean.create()))
				{
					csxBean.createRs();
					session.setAttribute("csxBean", csxBean);
					session.setAttribute("userId", userId);
					
					String nextJSP = "/AHF/pages/Center/KhaiBaoKiemKhoNew.jsp";
					response.sendRedirect(nextJSP);
				} else
				{
					IKhaiBaoKiemKhoList obj = new  KhaiBaoKiemKhoList();
					obj.setUserId(userId);
					obj.init("");
					
					session.setAttribute("obj", obj);
					
					String nextJSP = "/AHF/pages/Center/KhaiBaoKiemKho.jsp";
					response.sendRedirect(nextJSP);
				}
			} else
			{
				if (!(csxBean.update()))
				{
					csxBean.createRs();
					session.setAttribute("csxBean", csxBean);
					session.setAttribute("userId", userId);
					
					String nextJSP = "/AHF/pages/Center/KhaiBaoKiemKhoUpdate.jsp";
					response.sendRedirect(nextJSP);
				} else
				{

					IKhaiBaoKiemKhoList obj = new  KhaiBaoKiemKhoList();
					obj.setUserId(userId);
					obj.init("");
					
					session.setAttribute("obj", obj);
					
					String nextJSP = "/AHF/pages/Center/KhaiBaoKiemKho.jsp";
					response.sendRedirect(nextJSP);

				}
			}
		}
	}
	
	private String Doisangchuoi(String[] checknpp)
	{
		// TODO Auto-generated method stub
		String str = "";
		if (checknpp != null)
		{
			for (int i = 0; i < checknpp.length; i++)
			{
				if (i == 0)
				{
					str = checknpp[i];
				} else
				{
					str = str + "," + checknpp[i];
				}
			}
		}
		return str;
		
	}
	
}
