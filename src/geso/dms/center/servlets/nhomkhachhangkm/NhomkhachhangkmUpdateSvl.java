package geso.dms.center.servlets.nhomkhachhangkm;

import geso.dms.center.beans.nhomkhachhangkm.INhomkhachhangkm;
import geso.dms.center.beans.nhomkhachhangkm.INhomkhachhangkmList;
import geso.dms.center.beans.nhomkhachhangkm.imp.Nhomkhachhangkm;
import geso.dms.center.beans.nhomkhachhangkm.imp.NhomkhachhangkmList;
import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.khachhang.imp.Khachhang;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.TimeZone;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;



import com.oreilly.servlet.MultipartRequest;

public class NhomkhachhangkmUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public NhomkhachhangkmUpdateSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		Utility util = new Utility();
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		String id = util.getId(querystring);
		INhomkhachhangkm nkhKmBean = new Nhomkhachhangkm(id);

		session.setAttribute("nkhKmBean", nkhKmBean);
		String action = util.getAction(querystring);
		
		String nextJsp = "/AHF/pages/Center/NhomkhachhangkmUpdate.jsp"; 
		if(action.equals("display")) {
			nextJsp = "/AHF/pages/Center/NhomkhachhangkmDisplay.jsp";
		}
		response.sendRedirect(nextJsp);
	}
	
	public void Upload(HttpServletRequest request, HttpServletResponse response,Utility util,HttpSession session,String contentType,MultipartRequest multi ) throws ServletException, IOException, SQLException
	{
		String id = multi.getParameter("id");
		if (id == null)
			id = "";
		INhomkhachhangkm nkhKmBean = new Nhomkhachhangkm(id);


		nkhKmBean.setAction("upload");

		
		
		nkhKmBean.setId(id);
		

		String userId = util.antiSQLInspection(multi.getParameter("userId"));
		nkhKmBean.setuserId(userId);

		String Ten = util.antiSQLInspection(multi.getParameter("ten"));
		if (Ten == null)
			Ten = "";
		nkhKmBean.setTen(Ten);

		String Diengiai = util.antiSQLInspection(multi.getParameter("diengiai"));
		if (Diengiai == null)
			Diengiai = "";
		nkhKmBean.setDiengiai(Diengiai);

		String Trangthai = util.antiSQLInspection(multi.getParameter("trangthai"));
		if (Trangthai == null)
			Trangthai = "0";
		nkhKmBean.setTrangthai(Trangthai);

		String active = util.antiSQLInspection(multi.getParameter("active"));
		if (active == null)
			active = "";
		nkhKmBean.setActive(active);

		

		
		/// mẫu upload file
		Enumeration files = multi.getFileNames();
		String filenameu = "";
		while (files.hasMoreElements())
		{
			String name = (String) files.nextElement();
			filenameu = multi.getFilesystemName(name);
			System.out.println("name  " + name);
			;
		}
		String filename = "C:\\java-tomcat\\data\\" + filenameu;// nhớ tạo máy local
		if(filename.trim().length() >0)
		{

			// doc file excel
			File file = new File(filename);
			System.out.println("filename  " + file);
			Workbook workbook;

			int indexRow = 1;// dòng đầu tiên đọc data

			try
			{
				
				workbook = Workbook.getWorkbook(file);
				Sheet[] sheet1 = workbook.getSheets();
				
				
				Cell[] cells;

				indexRow = 1;

				Sheet sheet = sheet1[0];
				
				String data ="";
				for (int i = indexRow;i <sheet.getRows(); i++)
				{
					cells = sheet.getRow(i);
					if (cells.length > 0)
					{
						if (cells[0].getContents().toString().length() > 0)
						{
							String makh = getStringValue(cells,0);
							if(makh.trim().length()<0)
							{
								nkhKmBean.setMsg(" Không lấy được mã kh ở dòng ("+(i+1)+") ");
								loi( nkhKmBean, session,  response,  id);
								return;
							}
							/*double doanhso = Utility.parseDouble(getStringValue(cells,2));
							if(doanhso <=0); // báo lỗi ra
							
							double chietkhau = Utility.parseDouble(getStringValue(cells,3));
							if(chietkhau <=0); // báo lỗi ra
							*/
							if(data.trim().length() >0)
								data +=" union select '"+makh+"' ";
							else
								data +=" select '"+makh+"'as makh  ";
								//data +=" select '"+makh+"'as makh,"+doanhso+" as  ds, "+ck+" as ck  ";
						}
						
						//query = " select * from ("+data+") data ";
					}
					indexRow++;
				}
				if(data.trim().length() <=0)
				{
					 loi( nkhKmBean, session,  response,  id);
					 return;
				}
				else
					nkhKmBean.setKhMaUpload(data);
				nkhKmBean.setMsg(data); 
				
				if (id == null || id.length()==0)
				{

					if (!(nkhKmBean.Save()))
					{
						loi( nkhKmBean, session,  response,  id);
						return;
					} else
					{
						INhomkhachhangkmList nkhBean1 = new NhomkhachhangkmList();
						nkhBean1.init("");
						session.setAttribute("obj", nkhBean1);
						session.setAttribute("userId", userId);
						String nextJSP = "/AHF/pages/Center/Nhomkhachhangkm.jsp";
						response.sendRedirect(nextJSP);
					}
				} else
				{
					if (!(nkhKmBean.Update()))
					{
						loi( nkhKmBean, session,  response,  id);
						return;
					} else
					{
						INhomkhachhangkmList nkhBean1 = new NhomkhachhangkmList();
						nkhBean1.init("");
						session.setAttribute("obj", nkhBean1);
						session.setAttribute("userId", userId);
						String nextJSP = "/AHF/pages/Center/Nhomkhachhangkm.jsp";
						response.sendRedirect(nextJSP);
					}
				}
	
				
			} catch (Exception er)
			{
				er.printStackTrace();
				nkhKmBean.setMsg("Lỗi:"+ er.getMessage());
				loi( nkhKmBean, session,  response,  id);
				 return;
			}
		
		}
		
		
	}
	public  void loi(INhomkhachhangkm nkhKmBean,HttpSession session, HttpServletResponse response, String id) throws IOException
	{
		if (id == null)
		{
			nkhKmBean.createRs();
			nkhKmBean.createNppRs();
			nkhKmBean.createDdkdRs();
			nkhKmBean.createKhRs();
			session.setAttribute("nkhKmBean", nkhKmBean);
			response.sendRedirect("/AHF/pages/Center/NhomKhachHangKmNew.jsp");
			return;
		}
		else
		{
			nkhKmBean.createRs();
			nkhKmBean.createNppRs();
			nkhKmBean.createDdkdRs();
			nkhKmBean.createKhRs();
			session.setAttribute("nkhKmBean", nkhKmBean);
			response.sendRedirect("/AHF/pages/Center/NhomkhachhangkmUpdate.jsp");
			return;
		}
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();
		String contentType = request.getContentType();
		
		
		Utility util = new Utility();
		
		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
			MultipartRequest multi = new MultipartRequest(request, "C:\\java-tomcat\\data\\", 20000000, "UTF-8");
			try {
				Upload( request,  response, util, session, contentType, multi);
			} catch (SQLException e) {
				
			}
			return;
		}
		

		INhomkhachhangkm nkhKmBean = new Nhomkhachhangkm();

		

		String userId = util.antiSQLInspection(request.getParameter("userId"));
		nkhKmBean.setuserId(userId);

		String Ten = util.antiSQLInspection(request.getParameter("ten"));
		if (Ten == null)
			Ten = "";
		nkhKmBean.setTen(Ten);

		String Diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (Diengiai == null)
			Diengiai = "";
		nkhKmBean.setDiengiai(Diengiai);

		String Trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if (Trangthai == null)
			Trangthai = "0";
		nkhKmBean.setTrangthai(Trangthai);

		String active = util.antiSQLInspection(request.getParameter("active"));
		if (active == null)
			active = "";
		nkhKmBean.setActive(active);

		String id = util.antiSQLInspection(request.getParameter("id"));
		String Id = request.getParameter("id");
		if (Id == null)
			Id = "";
		nkhKmBean.setId(Id);

		String kenhId = "";
		String[] kenhIds = request.getParameterValues("kenhId");
		if (kenhIds != null)
		{
			int size = kenhIds.length;
			for (int i = 0; i < size; i++)
			{
				kenhId += kenhIds[i] + ",";
			}
			if (kenhId.length() > 0)
			{
				kenhId = kenhId.substring(0, kenhId.length() - 1);
			}
		}
		nkhKmBean.setKenhId(kenhId);

		String kvId = "";
		String[] kvIds = request.getParameterValues("khuvucId");
		if (kvIds != null)
		{
			int size = kvIds.length;
			for (int i = 0; i < size; i++)
			{
				kvId += kvIds[i] + ",";
			}
			if (kvId.length() > 0)
			{
				kvId = kvId.substring(0, kvId.length() - 1);
			}
		}
		nkhKmBean.setKhuvucId(kvId);

		String vungId = "";
		String[] vungIds = request.getParameterValues("vungId");
		if (vungIds != null)
		{
			int size = vungIds.length;
			for (int i = 0; i < size; i++)
			{
				vungId += vungIds[i] + ",";
			}
			if (vungId.length() > 0)
			{
				vungId = vungId.substring(0, vungId.length() - 1);
			}
		}
		nkhKmBean.setVungId(vungId);

		String nppId = "";
		String[] nppIds = request.getParameterValues("nppId");
		if (nppIds != null)
		{
			int size = nppIds.length;
			for (int i = 0; i < size; i++)
			{
				nppId += nppIds[i] + ",";
			}
			if (nppId.length() > 0)
			{
				nppId = nppId.substring(0, nppId.length() - 1);
			}
		}
		nkhKmBean.setNppId(nppId);

		String ddkdId = "";
		String[] ddkdIds = request.getParameterValues("ddkdId");
		if (ddkdIds != null)
		{
			int size = ddkdIds.length;
			for (int i = 0; i < size; i++)
			{
				ddkdId += ddkdIds[i] + ",";
			}
			if (ddkdId.length() > 0)
			{
				ddkdId = ddkdId.substring(0, ddkdId.length() - 1);
			}
		}
		nkhKmBean.setDdkdId(ddkdId);

		String khId = "0";
		String[] khIds = request.getParameterValues("khId");
		if (khIds != null)
		{
			int size = khIds.length;
			for (int i = 0; i < size; i++)
			{
				khId += khIds[i] + ",";
			}
			if (khId.length() > 0)
			{
				khId = khId.substring(0, khId.length() - 1);
			}
		}
		nkhKmBean.setKhId(khId);
		String action = request.getParameter("action");
		if (action.equals("save"))
		{

			if (id == null)
			{

				if (!(nkhKmBean.Save()))
				{
					nkhKmBean.createRs();
					nkhKmBean.createNppRs();
					nkhKmBean.createDdkdRs();
					nkhKmBean.createKhRs();
					session.setAttribute("nkhKmBean", nkhKmBean);
					response.sendRedirect("/AHF/pages/Center/NhomKhachHangKmNew.jsp");
				} else
				{
					INhomkhachhangkmList nkhBean1 = new NhomkhachhangkmList();
					nkhBean1.init("");
					session.setAttribute("obj", nkhBean1);
					session.setAttribute("userId", userId);
					String nextJSP = "/AHF/pages/Center/Nhomkhachhangkm.jsp";
					response.sendRedirect(nextJSP);
				}
			} else
			{
				if (!(nkhKmBean.Update()))
				{
					nkhKmBean.createRs();
					nkhKmBean.createNppRs();
					nkhKmBean.createDdkdRs();
					nkhKmBean.createKhRs();
					session.setAttribute("nkhKmBean", nkhKmBean);
					response.sendRedirect("/AHF/pages/Center/NhomkhachhangkmUpdate.jsp");
				} else
				{
					INhomkhachhangkmList nkhBean1 = new NhomkhachhangkmList();
					nkhBean1.init("");
					session.setAttribute("obj", nkhBean1);
					session.setAttribute("userId", userId);
					String nextJSP = "/AHF/pages/Center/Nhomkhachhangkm.jsp";
					response.sendRedirect(nextJSP);
				}
			}
		} else
		{
			nkhKmBean.createRs();
			nkhKmBean.createNppRs();
			nkhKmBean.createDdkdRs();
			nkhKmBean.createKhRs();
			session.setAttribute("nkhKmBean", nkhKmBean);
			if (id == null)
			{
				response.sendRedirect("/AHF/pages/Center/NhomKhachHangKmNew.jsp");
			} else
			{
				response.sendRedirect("/AHF/pages/Center/NhomkhachhangkmUpdate.jsp");
			}
		}
	}
	public String getStringValue(Cell[] cells,int vitri)
	{
		try
		{
			return cells[vitri].getContents().toString().replace("\t", "").replace(",", "").replace(" ", "").trim();
		}
		catch (Exception e) {
			return "";
		}
	}
}