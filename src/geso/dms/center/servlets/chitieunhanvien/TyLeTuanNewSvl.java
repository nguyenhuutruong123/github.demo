package geso.htp.center.servlets.chitieunhanvien;

import geso.htp.center.beans.chitieunhanvien.INhanvien;
import geso.htp.center.beans.chitieunhanvien.ITieuchi;
import geso.htp.center.beans.chitieunhanvien.ITyLeTuan;
import geso.htp.center.beans.chitieunhanvien.imp.Nhanvien;
import geso.htp.center.beans.chitieunhanvien.imp.Tieuchi;
import geso.htp.center.beans.chitieunhanvien.imp.TyLeTuan;
import geso.htp.center.servlets.report.ReportAPI;
import geso.htp.center.util.Utility;
import geso.htp.distributor.db.sql.dbutils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Worksheet;
import com.oreilly.servlet.MultipartRequest;

public class TyLeTuanNewSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TyLeTuanNewSvl()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	private String gettenuser(String userId_)
	{
		dbutils db = new dbutils();
		String sql_getnam = "select ten from nhanvien where pk_seq=" + userId_;
		ResultSet rs_tenuser = db.get(sql_getnam);
		if (rs_tenuser != null)
		{
			try
			{
				while (rs_tenuser.next())
				{
					return rs_tenuser.getString("ten");
				}
			} catch (Exception er)
			{
				return "";
			}
		}
		return "";
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String querystring = request.getQueryString();
		Utility util = new Utility();
		String userId = util.getUserId(querystring);
		// System.out.println("Ten user:  "+ userId);
		if (userId.length() == 0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		String id = util.getId(querystring);

		ITyLeTuan obj = new TyLeTuan(id);
		
		String action = util.getAction(querystring);
		if (action.equals("display"))
			obj.setIsDisplay("1");

		obj.setUserId(userId);
		obj.initBCNhanVien();
		
		session.setAttribute("userId", userId);
		String tenuser = gettenuser(userId);
		session.setAttribute("userTen", tenuser);
		session.setAttribute("obj", obj);
		session.setAttribute("check", "0");
		String nextJSP = "/HTP/pages/Center/TyLeTuanUpdate.jsp";// default
		response.sendRedirect(nextJSP);

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
			//MultipartRequest multi = new MultipartRequest(request, "C:\\java-tomcat\\data\\", 20000000, "UTF-8");
			MultipartRequest multi = new MultipartRequest(request, getServletContext().getRealPath("\\upload\\"), 20000000, "UTF-8"); 
			String loainv = util.antiSQLInspection(multi.getParameter("loai"));
			System.out.println("loainv:  " + loainv);
			try {
				//xulySR(request,response,util,session,contentType,multi);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{

			ITyLeTuan chitieu = new TyLeTuan();
			
			String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
			if(id == null ) id ="";
			chitieu.setID(id);
			
			String isDisplay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("isDisplay")));
			if(isDisplay == null ) isDisplay ="1";
			chitieu.setIsDisplay(isDisplay);
			
			try
			{
				int thang = Integer.parseInt(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thang"))));
				chitieu.setThang(thang);
			
				int nam = Integer.parseInt(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nam"))));
				chitieu.setNam(nam);
			} catch (Exception er)
			{

			}
			

			String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
			chitieu.setUserId(userId);

			String loainv = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loai")));
			chitieu.setLoai(loainv);
			System.out.println("loainv:  " + loainv);
			String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
			chitieu.setDienGiai(diengiai);
			chitieu.setNguoiSua(userId);
			chitieu.setNguoiTao(userId);
			chitieu.setNgayTao(this.getDateTime());
			chitieu.setNgaySua(this.getDateTime());
			
			String[] tcarr = request.getParameterValues("tcid");
			String[] dgarr = request.getParameterValues("tcdiengiai");
			if(tcarr != null){
				List<ITieuchi> lsttc = new ArrayList<ITieuchi>();
				for (int i = 0; i < tcarr.length; i++) {
					ITieuchi tc = new Tieuchi();
					tc.setID(tcarr[i]);
					tc.setDiengiai(dgarr[i]);
					System.out.println("tc Id = " + tcarr[i] + ", dg = " + dgarr[i]);
					
					String[] nvid = request.getParameterValues("nvid"+i);
					
					if(nvid != null)
					{
						List<INhanvien> lstnv = new ArrayList<INhanvien>();
						String[] nvten = request.getParameterValues("nvten"+i);
						String[] tuan1 = request.getParameterValues("tuan1"+i);
						String[] tuan2 = request.getParameterValues("tuan2"+i);
						String[] tuan3 = request.getParameterValues("tuan3"+i);
						String[] tuan4 = request.getParameterValues("tuan4"+i);
						
						for (int j = 0; j < nvid.length; j++) {
							INhanvien nv = new Nhanvien();
							nv.setID(nvid[j]);
							nv.setTen(nvten[j]);
							nv.setTuan1(tuan1[j]);
							nv.setTuan2(tuan2[j]);
							nv.setTuan3(tuan3[j]);
							nv.setTuan4(tuan4[j]);
							
							lstnv.add(nv);
						}
						tc.setNhanvien(lstnv);
					}
					lsttc.add(tc);
				}
				chitieu.setListTieuChi(lsttc);
			}
			
			chitieu.initBCNhanVien();
			
			String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
			System.out.println("action la "+action);
			if(action == null) action ="";
			
			if(action.equals("save"))
			{
				ResultSet rs = null;
				dbutils db =new dbutils();
				String query ="select count(*)so  from TieuChiThuong_ChiTiet a " +
							 " inner join TIEUCHITINHTHUONG b on a.TIEUCHITINHTHUONG_FK = b.pk_Seq " +
							 " where b.loai = "+loainv+" and b.trangthai = 1 and b.thang ="+chitieu.getThang()+" and b.nam = "+ chitieu.getNam()+" and b.tructhuoc_fk = "+chitieu.getTructhuocId();
				rs = db.get(query);
				int soKpi = 0;
				try
				{
					if(rs != null )
					{
						if(rs.next())
							soKpi = rs.getInt("so");
					}
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
				if(soKpi <=0)
				{
					System.out.println("query check:" + query);
					chitieu.setMessage("Chưa thiết lập KPI cho tháng");
					String nextJSP ="";
					nextJSP = "/HTP/pages/Center/TyLeTuanUpdate.jsp";// default						
					// Xoa het chi tieu cu di
					session.setAttribute("obj", chitieu);
					response.sendRedirect(nextJSP);
				}
				else if ( 
							( id == null || id.trim().length() <=0) ?
							chitieu.Create()
							:chitieu.Update()
						)
					{
						// Thanh cong
						session.setAttribute("nam", 0);
						session.setAttribute("thang", 0);
						chitieu.setListChiTieu("");
						session.setAttribute("obj", chitieu);
						String nextJSP = "/HTP/pages/Center/TyLeTuan.jsp";
						response.sendRedirect(nextJSP);
	
						System.out.println(chitieu.getMessage());
					} 
					else
					{
						if(( id == null || id.trim().length() <=0))
							chitieu.setID("");
						
						System.out.println("Khong Thanh Cong");
						String nextJSP = "/HTP/pages/Center/TyLeTuanUpdate.jsp";// default
						session.setAttribute("obj", chitieu);
						response.sendRedirect(nextJSP);
	
						System.out.println(chitieu.getMessage());
					}
			}
			else
			{
				String nextJSP = "/HTP/pages/Center/TyLeTuanUpdate.jsp";// default
				session.setAttribute("obj", chitieu);
				response.sendRedirect(nextJSP);
				System.out.println(chitieu.getMessage());
			}
		}
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		return dateFormat.format(date);
	}
}
