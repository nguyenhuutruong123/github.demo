package geso.dms.center.servlets.thaydoikhuyenmai;

import geso.dms.center.beans.thaydoikhuyenmai.IThayDoiKhuyenMai;
import geso.dms.center.beans.thaydoikhuyenmai.IThayDoiKhuyenMaiList;
import geso.dms.center.beans.thaydoikhuyenmai.imp.ThayDoiKhuyenMai;
import geso.dms.center.beans.thaydoikhuyenmai.imp.ThayDoiKhuyenMaiList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@WebServlet("/ThayDoiKhuyenMaiSvl")
public class ThayDoiKhuyenMaiSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ThayDoiKhuyenMaiSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if (cutil==null ||!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/AHF/index.jsp");
		} else
		{
			IThayDoiKhuyenMaiList obj;
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
	
			Utility util = new Utility();
			
			//--- check user
		    String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
			if (view == null) {
				view = "";
			}
			String param = "";
			if(view.trim().length() > 0) param ="&view="+view;
			if ( Utility.CheckSessionUser( session,  request, response))
			{
				Utility.RedireactLogin(session, request, response);
			}
			if( Utility.CheckRuleUser( session,  request, response, "ThayDoiKhuyenMaiSvl", param, Utility.XEM ))
			{
				Utility.RedireactLogin(session, request, response);
			}
			 //--- check user 
	
			String querystring = request.getQueryString();
		    String action = util.getAction(querystring);
			userId = util.getUserId(querystring);
			String ddhId = util.getId(querystring);
			if (userId.length() == 0)
				userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
			obj = new ThayDoiKhuyenMaiList();
			
			if (action.equals("delete"))
		    {
				obj.setMsg(Delete(ddhId));
		    }else if(action.equals("chot"))
		    {
		    	obj.setMsg(Chot(ddhId,userId));
		    }
		    	
			obj.setUserId(userId);
			obj.createNhaphanglist("");
			session.setAttribute("obj", obj);
			String nextJSP = "/AHF/pages/Center/ThayDoiKhuyenMai.jsp";
			response.sendRedirect(nextJSP);
		}
	}

	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/AHF/index.jsp");
		} else
		{
			IThayDoiKhuyenMaiList obj;
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			obj = new ThayDoiKhuyenMaiList();

			Utility util = new Utility();
			
			//--- check user
		    String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
			if (view == null) {
				view = "";
			}
			String param = "";
			if(view.trim().length() > 0) param ="&view="+view;
			if ( Utility.CheckSessionUser( session,  request, response))
			{
				Utility.RedireactLogin(session, request, response);
			}
			if( Utility.CheckRuleUser( session,  request, response, "ThayDoiKhuyenMaiSvl", param, Utility.XEM ))
			{
				Utility.RedireactLogin(session, request, response);
			}
			 //--- check user 
			
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
			String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
			if (action == null)
			{
				action = "";
			}
		
			String search = getSearchQuery(request, obj);
			if (action.equals("view") || action.equals("next") || action.equals("prev"))
			{
				obj.setUserId(userId);
				obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
				obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
				obj.setUserId(userId);
				obj.createNhaphanglist(search);
				System.out.println("[HoaDonList]"+search);
				session.setAttribute("obj", obj);
				String nextJSP = "/AHF/pages/Center/ThayDoiKhuyenMai.jsp";
				response.sendRedirect(nextJSP);
			}
			else if(action.equals("new"))
			{
				IThayDoiKhuyenMai tdkmBean=new ThayDoiKhuyenMai();
				tdkmBean.createRs();
		    	session.setAttribute("tdkmBean", tdkmBean);
		    	String nextJSP = "/AHF/pages/Center/ThayDoiKhuyenMaiNew.jsp";	 
		    	response.sendRedirect(nextJSP);
			}
			else if(action.equals("toExcel"))
			{
				ToExcel(response,obj,search);
			}else 
			{
				obj.createNhaphanglist(search);
				session.setAttribute("obj", obj);
				String nextJSP = "/AHF/pages/Center/ThayDoiKhuyenMai.jsp";
				response.sendRedirect(nextJSP);
			}
		}
	}
	private String getSearchQuery(HttpServletRequest request, IThayDoiKhuyenMaiList obj)
	{
		Utility util = new Utility();
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		if (userId == null)
			userId = "";
		obj.setUserId(userId);

		

		String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
		if (tungay == null)
			tungay = "";
		obj.setTungay(tungay);

		String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
		if (denngay == null)
			denngay = "";
		obj.setDenngay(denngay);

		
		
		
		String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));
		if (trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		

		String query=
		"	select a.PK_SEQ as thaydoiId,a.Loai, "+
		"		a.traKm_fk,a.dkkm_fk,ISNULL(cast(b.pk_seq as varchar(20)),'') +' - '+ isnull(b.DienGiai,'') as dkKm,ISNULL(cast(c.pk_seq as varchar(20)),'') +' - ' + isnull(c.DIENGIAI,'')  as TraKm, "+
		"		nt.TEN as NguoiTao,a.NGAYTAO,ns.TEN as NguoiSua,a.NgaySua,isnull(nd.TEN,'') as NguoiDuyet,isnull(a.NGAYDUYET,'') as NgayDuyet,a.TRANGTHAI "+
		"	from ThayDoiKM a "+
		"		left join DIEUKIENKHUYENMAI b on b.PK_SEQ=a.DKKM_FK "+
		"		left join TRAKHUYENMAI c on c.PK_SEQ=a.TRAKM_FK "+
		"		left join NHANVIEN nt on nt.PK_SEQ=a.NGUOITAO "+
		"		left join NHANVIEN ns on ns.PK_SEQ=a.NGUOISUA "+
		"		left join NHANVIEN nd on nd.PK_SEQ=a.NGUOIDUYET " + 
		"  where 1=1 ";
		
		obj.getDataSearch().clear();
		
		if (tungay.length() > 0)
		{
//			query = query + " and a.NgayTao >= '" + tungay + "'";
			
			query = query + "\n and a.NgayTao >= ? ";	
			obj.getDataSearch().add( "" + tungay + "" );
		}

		if (denngay.length() > 0)
		{
//			query = query + " and a.NgayTao <= '" + denngay + "'";
			
			query = query + "\n and a.NgayTao <= ? ";	
			obj.getDataSearch().add( "" + denngay + "" );
		}

		if (trangthai.length() > 0)
		{
//			query = query + " and a.trangthai = '" + trangthai + "'";
			
			query = query + "\n and a.trangthai = ? ";	
			obj.getDataSearch().add( "" + trangthai + "" );
		}
	
		return query;
	}
	
	private void ToExcel(HttpServletResponse response, IThayDoiKhuyenMaiList obj,String query) throws IOException
	{
		OutputStream out = null;
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=HoaDon.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());

			int k = 0;
			int j = 5;
		
			WritableSheet sheet=null;
			
			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 14);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);
			
			sheet = w.createSheet("HoaDon", k);
			sheet.addCell(new Label(0, 1, "HÓA ĐƠN: ",new WritableCellFormat(cellTitle)));

			sheet.addCell(new Label(0, 2, "Ngày tạo: "));
			sheet.addCell(new Label(1, 2, "" + getDateTime()));
			
			sheet.addCell(new Label(2, 4, "Đơn vị tiền tệ:VND"));				

			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
			cellFont.setColour(Colour.BLACK);

			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

			
		
			cellFormat.setBackground(jxl.format.Colour.LIME);
			cellFormat.setWrap(true);
			cellFormat.setAlignment(Alignment.CENTRE);
			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			
			WritableCellFormat cellFormatSpecical = new WritableCellFormat(cellFont);

			cellFormatSpecical.setBackground(jxl.format.Colour.LIGHT_ORANGE);
			cellFormatSpecical.setWrap(true);
			cellFormatSpecical.setAlignment(Alignment.CENTRE);
			cellFormatSpecical.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormatSpecical.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormatSpecical.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormatSpecical.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormatSpecical.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			sheet.addCell(new Label(0, 4, "STT", cellFormat));
			sheet.addCell(new Label(1, 4, "ID", cellFormatSpecical));
			sheet.addCell(new Label(2, 4, "MÃ TRẢ KM", cellFormat));
			sheet.addCell(new Label(3, 4, "MÃ ĐIỀU KIỆN KM", cellFormat));
			sheet.addCell(new Label(4, 4, "NGƯỜI TẠO", cellFormat));
			sheet.addCell(new Label(5, 4, "NGÀY TẠO", cellFormat));
			sheet.addCell(new Label(6, 4, "NGƯỜI SỬA", cellFormat));
			sheet.addCell(new Label(7, 4, "NGÀY SỬA", cellFormat));
			sheet.addCell(new Label(8, 4, "NGƯỜI DUYỆT", cellFormat));
			sheet.addCell(new Label(9, 4, "NGÀY DUYỆT", cellFormat));
			sheet.addCell(new Label(10, 4, "TRẠNG THÁI", cellFormat));
			
			sheet.setRowView(100, 4);
			
			sheet.setColumnView(0, 15);
			sheet.setColumnView(1, 20);
			sheet.setColumnView(2, 30);
			sheet.setColumnView(3, 25);
			sheet.setColumnView(4, 20);
			sheet.setColumnView(5, 20);
			sheet.setColumnView(6, 15);
			sheet.setColumnView(7, 35);
			sheet.setColumnView(8, 15);
			sheet.setColumnView(9, 15);
			sheet.setColumnView(10, 15);
			sheet.setColumnView(11, 15);
			sheet.setColumnView(12, 15);
			sheet.setColumnView(13, 15);
			sheet.setColumnView(14, 60);
			dbutils db = new dbutils();
		
			ResultSet rs = db.get(query);
			
			WritableCellFormat cellFormat2 = new WritableCellFormat(new  jxl.write.NumberFormat("#,###,###"));

			cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			WritableCellFormat cellFormat3 = new WritableCellFormat(new  jxl.write.NumberFormat("#,###,###"));
			cellFormat3.setBackground(jxl.format.Colour.YELLOW);
			cellFormat3.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			WritableCellFormat cformat =null;
			Label label;
			Number number;
			int stt=0;
			while (rs.next())
			{
				String type = "0";
				cformat = type.equals("1") ? cellFormat3 : cellFormat2;
				stt++;
				
				String trangthai="";
                if(rs.getString("trangthai").equals("0"))
                		trangthai="Chưa duyệt";
                else if(rs.getString("trangthai").equals("1"))
                		{
                			trangthai="Đã duyệt";
                		}	
				
				
				number = new Number(0, j, stt, cformat);sheet.addCell(number);
				label = new Label(1, j, rs.getString("thaydoiId"), cformat);sheet.addCell(label);
				label = new Label(2, j, rs.getString("TraKm"), cformat);sheet.addCell(label);
				label = new Label(3, j, rs.getString("dkKm"), cformat);sheet.addCell(label);
				label = new Label(4, j, rs.getString("NguoiTao"), cformat);sheet.addCell(label);
				label = new Label(5, j, rs.getString("NgayTao"), cformat);sheet.addCell(label);
				label = new Label(6, j, rs.getString("NguoiSua"), cformat);sheet.addCell(label);
				label = new Label(7, j, rs.getString("NgaySua"), cformat);sheet.addCell(label);
				label= new Label(8,j , rs.getString("NguoiDuyet"),cformat);sheet.addCell(label);
				label= new Label(9,j , rs.getString("NgayDuyet"),cformat);sheet.addCell(label);
				label = new Label(10, j, trangthai, cformat);sheet.addCell(label);
				j++;
			}
			w.write();
			w.close();
			rs.close();
			db.shutDown();
		} catch (Exception e)
		{
			System.out.println("Error Cac Ban : " + e.getMessage());
			e.printStackTrace();
		} finally
		{
			if (out != null)
				out.close();
			
		}
	}
	
	
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private String Delete(String ddhId)
	{
		dbutils db = new dbutils();
		try
		{
			db.getConnection().setAutoCommit(false);
			String query = " DELETE FROM ThayDoiKM_SanPham WHERE THAYDOIKM_FK='"+ddhId+"'";
			System.out.println("1.ThayDoiKM_SanPham"+query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Lỗi hệ thống vui lòng liên hệ "+query;
			}
			
			query=" DELETE FROM  THAYDOIKM_CTKM WHERE THAYDOIKM_FK='"+ddhId+"'";
			System.out.println("2.THAYDOIKM_CTKM"+query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Lỗi hệ thống vui lòng liên hệ "+query;
			}
			query=" DELETE FROM  THAYDOIKM WHERE PK_SEQ='"+ddhId+"' AND TRANGTHAI=0 ";
			System.out.println("3.THAYDOIKM"+query);
			if(db.updateReturnInt(query)!=1)
			{
				db.getConnection().rollback();
				return "Lỗi hệ thống vui lòng liên hệ "+query;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return "";
		}catch(Exception er)
		{
			er.printStackTrace();
			try
			{
				db.getConnection().rollback();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			return "Exeption "+er.getMessage()+" ";
		}
		finally
		{
			db.shutDown();
		}
	}	
	
	
	private String Chot(String ddhId, String userId)
	{
		dbutils db = new dbutils();
		try
		{
			db.getConnection().setAutoCommit(false);
			String query = 
			" INSERT INTO DIEUKIENKM_SANPHAM(DIEUKIENKHUYENMAI_FK,SANPHAM_FK,SOLUONG,SOTIEN,BATBUOC) "+
	 		" SELECT B.DKKM_FK,A.SANPHAM_FK,A.SOLUONG,A.SOTIEN,A.BATBUOC "+
            "   FROM THAYDOIKM_SANPHAM A INNER JOIN THAYDOIKM B ON B.PK_SEQ=A.THAYDOIKM_FK "+
            "  WHERE A.ThayDoiKM_FK='"+ddhId+"' AND B.DKKM_FK IS NOT NULL ";
			System.out.println("1.DIEUKIENKM_SANPHAM"+query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Lỗi hệ thống vui lòng liên hệ "+query;
			}
			query=
			"INSERT INTO TRAKHUYENMAI_SANPHAM(TRAKHUYENMAI_FK,SANPHAM_FK,SOLUONG) "+
			" SELECT B.TRAKM_FK,A.SanPham_FK,A.SoLuong "+
			" FROM THAYDOIKM_SANPHAM A INNER JOIN THAYDOIKM B ON B.PK_SEQ=A.THAYDOIKM_FK "+
			" WHERE A.ThayDoiKM_FK='"+ddhId+"' AND B.TRAKM_FK IS NOT NULL   ";
			System.out.println("1.TRAKHUYENMAI_SANPHAM"+query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Lỗi hệ thống vui lòng liên hệ "+query;
			}
			
			query=" UPDATE THAYDOIKM SET TRANGTHAI=1,DATEDUYET=dbo.GetLocalDate(DEFAULT),NGUOIDUYET='"+userId+"',NgayDuyet='"+getDateTime()+"' WHERE PK_SEQ='"+ddhId+"' AND TRANGTHAI=0 ";
			System.out.println("2.THAYDOIKM_CTKM"+query);
			if(db.updateReturnInt(query)!=1)
			{
				db.getConnection().rollback();
				return "Lỗi hệ thống vui lòng liên hệ "+query;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return "";
		}catch(Exception er)
		{
			er.printStackTrace();
			try
			{
				db.getConnection().rollback();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			return "Exeption "+er.getMessage()+" ";
		}
		finally
		{
			db.shutDown();
		}
	}
	
}
