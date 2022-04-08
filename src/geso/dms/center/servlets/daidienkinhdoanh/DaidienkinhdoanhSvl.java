package geso.dms.center.servlets.daidienkinhdoanh;

import geso.dms.center.beans.daidienkinhdoanh.*;
import geso.dms.center.beans.daidienkinhdoanh.imp.*;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Picture;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

import java.sql.ResultSet;

public class DaidienkinhdoanhSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet
{
	private static final long serialVersionUID = 1L;

	public DaidienkinhdoanhSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		Utility util = new Utility();
		HttpSession session = request.getSession();
		
		//String ssUserId = (String)session.getAttribute("userId");
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		if (view == null) {
			view = "";
		}
		
		String param = "";
		/*if(view.trim().length() > 0) param ="&view="+view;
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "DaidienkinhdoanhSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}*/
 
		session.setAttribute("userId", userId);
		
		
		//PrintWriter out = response.getWriter();

		String querystring = request.getQueryString();

		
		
		IDaidienkinhdoanhList obj = new DaidienkinhdoanhList();

		
		
		
		String action = util.getAction(querystring);
		String ddkdId = util.getId(querystring);
		

		obj.setView(view);
		
		obj.setRequestObj(request);
		obj.setUserId(session.getAttribute("userId").toString());
		// System.out.println("user iad 1: "+userId);
		obj.init(""); 
		session.setAttribute("obj", obj);
		
		String nextJSP = "/AHF/pages/Center/DaiDienKinhDoanh.jsp";
		response.sendRedirect(nextJSP);
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{
		IDaidienkinhdoanhList obj = new DaidienkinhdoanhList();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		Utility util = new Utility();

		HttpSession session = request.getSession();
		
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		
		
		obj.setUserId(userId);
		
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		
		String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("NhaPhanPhoi")));
		if (nppId == null)
			nppId = "";
		obj.setNppId(nppId);
		
		String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		if (view == null)
			view = "";
		obj.setView(view);
		
		
		String param = "";
		if(view.trim().length() > 0) param ="&view="+view;
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			obj.DbClose();
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "DaidienkinhdoanhSvl", param, Utility.XEM ))
		{
			obj.DbClose();
			Utility.RedireactLogin(session, request, response);
		}
		
		
		obj.setUserId(userId);
		obj.setRequestObj(request);
		String search = "";
		String nextJSP = "";
		
		if(action.equals("Xoa"))
		{
		
			
			String IdXoa = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("IdXoa")));
			if (IdXoa == null)
				IdXoa = "";
			System.out.println("IdXoa = "+ IdXoa);
			if(IdXoa.trim().length() > 0)
				Delete(IdXoa, obj);
			else
				obj.setMsg("Lỗi dữ liệu");
		}
		else
		if (action.equals("excel"))
		{
			if (view != null && !view.equals("TT")) {
				obj.setNppId(util.getIdNhapp(userId));
			}
			OutputStream outStream = response.getOutputStream();
			String userTen = (String) request.getSession().getAttribute("userTen");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; " + "filename=DanhSachDDKD(tt).xls");
			try
			{
				ExportToExcel(obj ,outStream, userTen);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			return;
		}
		if (action.equals("new"))
		{
			// Empty Bean for distributor
			IDaidienkinhdoanh ddkdBean = (IDaidienkinhdoanh) new Daidienkinhdoanh("",obj.getNppId());
			ddkdBean.setUserId(userId);
			ddkdBean.setView(view);
			// Save Data into session
			session.setAttribute("ddkdBean", ddkdBean);

			nextJSP = "/AHF/pages/Center/DaiDienKinhDoanhNew.jsp";
		} else if (action.equals("search"))
		{
			search = getSearchQuery(request, obj);

			obj.setUserId(userId);

			session.setAttribute("abc", search);

			nextJSP = "/AHF/pages/Center/DaiDienKinhDoanh.jsp";
		} else
		{

			// phantrang

			obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");

			// ------------------------

			search = getSearchQuery(request, obj);

			nextJSP = "/AHF/pages/Center/DaiDienKinhDoanh.jsp";
		}

		obj.init(search);

		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		response.sendRedirect(nextJSP);
	}

	private String getSearchQuery(HttpServletRequest request, IDaidienkinhdoanhList obj)
	{

		Utility util = new Utility();

		String ten = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdTen")));
		if (ten == null)
			ten = "";
		obj.setTen(ten);
		
		String maSmartid = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdma"));
		if(maSmartid == null)
			maSmartid = "";
		obj.setSmartId(maSmartid);
		
		String sodienthoai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("DienThoai")));
		if (sodienthoai == null)
			sodienthoai = "";
		obj.setSodienthoai(sodienthoai);

		String kbhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("KenhBanHang")));
		if (kbhId == null)
			kbhId = "";
		obj.setKbhId(kbhId);

		String gsbhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("GSBanHang")));
		if (gsbhId == null)
			gsbhId = "";
		obj.setGsbhId(gsbhId);

		String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("NhaPhanPhoi")));
		if (nppId == null)
			nppId = "";
		obj.setNppId(nppId);

		String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("TrangThai")));
		if (trangthai == null)
			trangthai = "";

		if (trangthai.equals("2"))
			trangthai = "";

		obj.setTrangthai(trangthai);
		String loaigiamsat = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaigiamsat")));
		if (loaigiamsat == null)
			loaigiamsat = "";
		if (loaigiamsat.equals("2"))
			trangthai = "";

		obj.setThuviec(loaigiamsat);

		String query = "\n select isnull(route.ten,'') route, isnull(a.tinhtrang,'0') as tinhtrang,a.cmnd,a.quequan,a.ngaysinh,a.ngaybatdau,a.ngayketthuc, a.pk_seq as id,a.smartid, a.ten , a.dienthoai, a.diachi, a.trangthai, a.ngaytao, "+
		"\n b.ten as nguoitao, a.ngaysua, c.ten as nguoisua, isnull(d.ten,'') as nppTen,  isnull(f.ten,'') as gsbhTen, "+
		"\n g.ten as kbhTen " +
		"\n from daidienkinhdoanh a inner join nhanvien b on a.nguoitao = b.pk_seq "+ 
		"\n inner join nhanvien c on a.nguoisua = c.pk_seq "+
		"\n left join nhaphanphoi d on a.npp_fk = d.pk_seq "+  
		"\n left join ddkd_gsbh e on a.pk_seq = e.ddkd_fk "+
		"\n left join giamsatbanhang f on e.gsbh_fk = f.pk_seq "+ 
		"\n left join kenhbanhang g on f.kbh_fk = g.pk_seq " +
		"\n left join dms_route route on route.pk_seq = a.route_fk " +
		"\n where 1=1 ";

		if (ten.length() > 0)
		{
			query = query + " and upper(dbo.ftBoDau(a.ten)) like upper(?)";
			obj.getDataSearch().add( "%" + util.replaceAEIOU(ten) + "%" );
		}

		if (sodienthoai.length() > 0)
		{
			query = query + " and upper(a.dienthoai) like upper(?)";
			obj.getDataSearch().add( "%" + sodienthoai+ "%" );
		}

		if (gsbhId.length() > 0)
		{
			query = query + " and e.gsbh_fk=?";
			obj.getDataSearch().add( gsbhId);

		}

		if (nppId.length() > 0)
		{
			query = query + " and a.npp_fk=?";
			obj.getDataSearch().add( nppId);

		}

		if (trangthai.length() > 0)
		{
			query = query + " and a.trangthai = ?";
			obj.getDataSearch().add( trangthai);

		}
		if (loaigiamsat.length() > 0)
		{
			query = query + " and isnull(a.tinhtrang,'0') = ?";
			obj.getDataSearch().add( loaigiamsat);
		}
		if(maSmartid.length() > 0)
			
		{
			query = query + " and a.smartid like ?";
			obj.getDataSearch().add("%"+ maSmartid +"%");
		}
		
		if(kbhId.length() > 0)
			
		{
			query = query + " and g.pk_seq like ?";
			obj.getDataSearch().add("%"+ kbhId +"%");
		}
		System.out.println("que ry la ........" + query);
		return query;
	}

	private void Delete(String id, IDaidienkinhdoanhList obj)
	{
		dbutils db = new dbutils();

		try
		{
			db.getConnection().setAutoCommit(false);
			
			
			
			String sql = "delete from ddkd_gsbh where ddkd_fk = '" + id + "'";
			if (!db.update(sql))
			{
				Utility.rollback_and_shutdown(db);
				obj.setMsg("Ban khong the xoa dai dien kinh doanh nay trong bang ddkd_gsbh");
			}
			sql = "delete from DDKD_SOKH where DDKD_FK = '" + id + "'";
			if (!db.update(sql))
			{
				Utility.rollback_and_shutdown(db);
				obj.setMsg("Bạn đã có khách hàng trong tuyến");
				return ;
			}
			sql = " select count(*)x from donhang where ddkd_fk = '" + id + "' ";
			ResultSet rs=  db.get(sql);
			rs.next();
			if(rs.getInt("x") > 0)
			{
				Utility.rollback_and_shutdown(db);
				obj.setMsg("NVBH đã phát sinh đơn hàng");
				return ;
			}
			sql = " select count(*)x from ddkd_khachhang where ddkd_fk = '" + id + "' ";
			rs=  db.get(sql);
			rs.next();
			if(rs.getInt("x") > 0)
			{
				Utility.rollback_and_shutdown(db);
				obj.setMsg("NVBH đã phát sinh viếng thăm");
				return ;
			}
			
			sql = "delete from tuyenbanhang where DDKD_FK = '" + id + "'";
			if (!db.update(sql))
			{
				Utility.rollback_and_shutdown(db);
				obj.setMsg("Bạn đã có khách hàng trong tuyến");
				return ;
			}
			
			sql = "delete from daidienkinhdoanh where pk_Seq = '" + id + "'";
			if (!db.update(sql))
			{
				Utility.rollback_and_shutdown(db);
				obj.setMsg("Bạn đã có tuyến đường hoặc đơn hàng rồi nên không xóa được");
				return ;
			}
			Utility.commit_and_shutdown(db);
		} catch (Exception e)
		{
			System.out.println("vao day roi" + e.toString());
			Utility.rollback_and_shutdown(db);
			obj.setMsg("Ban Khong Thuc Hien Xoa Duoc,Vui Long Kiem Tra Lai." + e.toString());
		}

	}

	private void ExportToExcel(IDaidienkinhdoanhList obj, OutputStream outStream, String UserCreate) throws Exception
	{
		try
		{
			Workbook workbook = new Workbook();
			createHeader(workbook, UserCreate);
			fillData(obj,workbook);
			workbook.save(outStream, 0);
		} catch (Exception ex)
		{
			throw new Exception(ex.getMessage());
		}

	}

	private void createHeader(Workbook workbook, String UserCreate) throws Exception
	{
		try
		{
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("DanhSachDDKD");

			Cells cells = worksheet.getCells();
			cells.setRowHeight(1, 13);
			Cell cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell, Color.RED, true, 16, "DANH SÁCH ĐẠI DIỆN KINH DOANH");
			cell = cells.getCell("A2");
			ReportAPI
					.getCellStyle(cell, Color.BLUE, true, 10, "Date Create: " + ReportAPI.NOW("dd/MM/yyyy : hh-mm-ss"));
			cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Distributor: " + UserCreate);

			
			
			
			cell = cells.getCell("A5");cell.setValue("Mã NPP");
			ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			
			cell = cells.getCell("B5");cell.setValue("Tên NPP");ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			cell = cells.getCell("C5");cell.setValue("Trạng thái NPP");ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			cell = cells.getCell("D5");cell.setValue("Mã GSBH");ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			cell = cells.getCell("E5");cell.setValue("Tên GSBH");ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			cell = cells.getCell("F5");cell.setValue("Trạng thái GSBH");ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			cell = cells.getCell("G5");cell.setValue("Mã DDKD");ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			cell = cells.getCell("H5");cell.setValue("Tên DDKD");ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			cell = cells.getCell("I5");cell.setValue("Trạng thái DDKD");ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			cell = cells.getCell("J5");cell.setValue("Khu vực");ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			cell = cells.getCell("K5");cell.setValue("Vùng/Miền");ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			
			
			cell = cells.getCell("L5");cell.setValue("ASM");ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			cell = cells.getCell("M5");cell.setValue("Ngày vào làm");ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			cell = cells.getCell("N5");cell.setValue("Ngày kết thúc");ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			cell = cells.getCell("O5");cell.setValue("IMEI");ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			
		} catch (Exception ex)
		{
			throw new Exception("Khong the tao duoc tua de cho bao cao");
		}

	}

	
	private void fillData(IDaidienkinhdoanhList obj, Workbook workbook) throws Exception
	{
		try
		{
			Utility util = new Utility();
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			Cells cells = worksheet.getCells();
			Cell cell = null;
			dbutils db = new dbutils();
			
			String query  = " select  phanloai,gsbh_fk, asm_fk from NHANVIEN where pk_seq =  " +obj.getUserId();
			ResultSet rs=db.get(query);
			String gsbh_fk = "";
			String asm_fk = "";
			int phanloai = 0;
			if(rs.next())
			{
				phanloai = rs.getInt("phanloai");
				gsbh_fk = rs.getString("gsbh_fk") == null ? "": rs.getString("gsbh_fk");
				asm_fk = rs.getString("asm_fk") == null ? "": rs.getString("asm_fk");
			}

			query =  		"\n select isnull(a.imei,'') imei,a.smartid as id,d.sitecode, case when d.trangthai =1 then N'Hoạt động' else N'Ngưng hoạt động' end  as ttnpp," +
							"\n 		a.ten, a.dienthoai, a.diachi, a.trangthai AS TTDDKD, a.ngaytao,"  +
							"\n			b.ten as nguoitao, a.ngaysua, c.ten as nguoisua, d.manpp as nppId, " +
							"\n			d.ten as nppTen, F.smartid AS gsbhId,f.ten as gsbhTen, f.TRANGTHAI AS TTGSBH, g.ten as kbhTen," +
							"\n			k.TEN AS TenKV,v.TEN AS TenVung,ISNULL(a.HinhAnh,'') as HinhAnh, " +
							"\n 		a.maydacap, a.tienmaycap, a.maythechan, a.tienmaythe,a.nppasmgiumay, " +
							"\n 		case when a.chonmaythe = 0 then N'Nhà phân phối' when a.chonmaythe = 1 then N'Nhân viên bán hàng' " +
							"\n				else N'' end as chonmaythe, a.maymat, a.tienmaymat, " +
							"\n 		ISNULL(a.NGAYBATDAU, 'N/A') as ngaybatdau, ISNULL(a.NGAYKETTHUC, 'N/A') as ngayketthuc, " +
							"\n 		ISNULL( " +
							"\n			(select ttpp.TEN from " +
							"\n				(select PK_SEQ,MA,TEN from TRUNGTAMPHANPHOI ttpp where ttpp.PK_SEQ=a.TTPP_FK) " +
							"\n				as ttpp),'N/A') as TenTTPP , asm.ten TenASM " +
							"\n	    from daidienkinhdoanh a left join nhanvien b on a.nguoitao = b.pk_seq  " +
							"\n		left join  nhanvien c on a.nguoisua = c.pk_seq  " +
							"\n		left join  nhaphanphoi d on a.npp_fk = d.pk_seq  " +
							"\n		left join  ddkd_gsbh e on a.pk_seq = e.ddkd_fk  "  +
							"\n		left join  giamsatbanhang f on e.gsbh_fk = f.pk_seq "  +
							"\n		left join kenhbanhang g on f.kbh_fk = g.pk_seq" +
							"\n		outer apply " +
							"\n		(	" +
							"\n			select top 1 asm.ten, asm.pk_seq " +
							"\n			from asm " +
							"\n			where asm.pk_seq = f.asm_fk " +
							"\n		)asm " +
							"\n		left join  KHUVUC k on k.PK_SEQ = d.KHUVUC_FK " +
							"\n		left join VUNG v on v.PK_SEQ = k.VUNG_FK " +
							"\n where 1=1 ";
			if (obj.getNppId() != null && obj.getNppId().length() > 0) {
				query += "\n and a.npp_fk = "+obj.getNppId();
			}
			if(phanloai == 2)
			{
				if(gsbh_fk.trim().length() > 0)
					query +=" and f.pk_seq = "+ gsbh_fk;
				else if(asm_fk.trim().length() > 0)
					query +=" and asm.pk_seq = "+ gsbh_fk;
				else
					query +=" and a.npp_fk  in "+  util.quyen_npp(obj.getUserId());
			}
			
			
			query += " order by nppId ";
			
			
			System.out.print("Query Excel: "+query);
			rs = db.get(query);
			int rowIndex = 6;
			for(int i=0;i<9;i++)
			{
				cells.setColumnWidth(0, 15f);
			}
			
			while (rs.next())
			{
				/*cells.setRowHeight(rowIndex-1, 50f);
				String img = rs.getString("HinhAnh");
				String dir = getServletContext().getInitParameter("path");
				img=dir+"\\images\\"+img;
				if (img.trim().length() > 0)
				{
					try
					{
						int pictureIndex=worksheet.getPictures().add(rowIndex-1,0,rowIndex-1,0,img);
						Picture picture = worksheet.getPictures().get(pictureIndex);						
						picture.setWidth(50);
						picture.setHeight(50);
					} catch (Exception e)
					{
						System.out.println("Exception: " + e.getMessage());
					}
				}*/
				
				
				cell = cells.getCell("A" + String.valueOf(rowIndex));
				cell.setValue(rs.getString("nppId"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
				
				cell = cells.getCell("B" + String.valueOf(rowIndex));
				cell.setValue(rs.getString("nppTen"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
				
				cell = cells.getCell("C" + String.valueOf(rowIndex));
				cell.setValue(rs.getString("ttnpp"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
				
				cell = cells.getCell("D" + String.valueOf(rowIndex));
				cell.setValue(rs.getString("gsbhId"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
				
				cell = cells.getCell("E" + String.valueOf(rowIndex));
				cell.setValue(rs.getString("gsbhTen"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
				
				cell = cells.getCell("F" + String.valueOf(rowIndex));
				cell.setValue(rs.getInt("TTGSBH")== 1 ? "Hoạt động" : "Ngưng Hoạt động");
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
				
				cell = cells.getCell("G" + String.valueOf(rowIndex));
				cell.setValue(rs.getString("id"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
				
				cell = cells.getCell("H" + String.valueOf(rowIndex));
				cell.setValue(rs.getString("ten"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
				
				cell = cells.getCell("I" + String.valueOf(rowIndex));
				cell.setValue(rs.getInt("TTDDKD") == 1 ? "Hoạt động" : "Ngưng Hoạt động");
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
				
				cell = cells.getCell("J" + String.valueOf(rowIndex));
				cell.setValue(rs.getString("TenKV"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
				
				cell = cells.getCell("K" + String.valueOf(rowIndex));
				cell.setValue(rs.getString("TenVung"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
				
				cell = cells.getCell("L" + String.valueOf(rowIndex));
				cell.setValue(rs.getString("TenASM"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
				
				cell = cells.getCell("M" + String.valueOf(rowIndex));
				cell.setValue(rs.getString("ngaybatdau"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
				
				cell = cells.getCell("N" + String.valueOf(rowIndex));
				cell.setValue(rs.getString("ngayketthuc"));			
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
				
				cell = cells.getCell("O" + String.valueOf(rowIndex));
				cell.setValue(rs.getString("imei"));			
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
				
				
				
				
				
				
				
				
			
				
			
				
				++rowIndex;
			}
			
			db.shutDown();
		} catch (Exception ex)
		{
			ex.printStackTrace();
			throw new Exception("Khong the dien du lieu vao file Excel hoac khong co du lieu");
			
		}
	}

	public static void main(String[] arg)
	{
		System.out.println(getExcelColumFromName("BZ"));
		System.out.println(GetExcelColumnName(78));

	}

	private static String GetExcelColumnName(int columnNumber)
	{
		int dividend = columnNumber;
		String columnName = "";
		int modulo;

		while (dividend > 0)
		{
			modulo = (dividend - 1) % 26;
			columnName = (char) (65 + modulo) + columnName;
			dividend = (int) ((dividend - modulo) / 26);
		}

		return columnName;
	}

	private static int getExcelColumFromName(String name)
	{
		name = name.toUpperCase();

		int he = 25;
		int heso = 1;
		int tong = 0;

		int y = 0;
		for (int i = name.length() - 1; i > -1; i--)
		{
			char c = name.charAt(i);
			tong += ((int) c - 64) * (heso + he * y);
			y++;
		}
		return tong;
	}

}


