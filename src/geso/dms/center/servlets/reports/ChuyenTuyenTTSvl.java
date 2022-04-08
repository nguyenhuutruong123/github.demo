package geso.dms.center.servlets.reports;
import geso.dms.center.beans.daidienkinhdoanh.imp.Daidienkinhdoanh;
import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.Idbutils;
import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.khachhang.imp.Khachhang;
import geso.dms.distributor.db.sql.dbutils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.Label;
import jxl.write.WriteException;

import com.oreilly.servlet.MultipartRequest;
public class ChuyenTuyenTTSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;



	public ChuyenTuyenTTSvl() 
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		obj.setuserId(userId);
		obj.initChuyenTuyen();

		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Center/ChuyenTuyenTT.jsp";
		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String contentType = request.getContentType();

		String userId =geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		IStockintransit obj = new Stockintransit();
		obj.setuserId(userId);
		Utility util = new Utility();

		String loaichuyen = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaichuyen"));
		String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
		String ddkdFromId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdFromId"));
		String ddkdToId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdToId"));

		if(loaichuyen == null) loaichuyen = "0";
		if(nppId == null) nppId = "";
		if(ddkdFromId == null) ddkdFromId = "";
		if(ddkdToId == null) ddkdToId = "";
		
		obj.settype(loaichuyen);
		obj.setnppId(nppId);
		obj.settungay(ddkdFromId);
		obj.setdenngay(ddkdToId);
		
		obj.initChuyenTuyen();
		
		
		if(action.equals("save"))
		{
			String msg = ChuyenTuyen(obj);
			obj.setMsg(msg);
		}
		
		
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Center/ChuyenTuyenTT.jsp";
		response.sendRedirect(nextJSP);
	}
	
	public String ChuyenTuyen(IStockintransit obj )
	{
		Idbutils db = obj.getDb();
		String query = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String ddkdFromId = obj.gettungay();
			String ddkdToId = obj.getdenngay();
			String loai = obj.gettype();
			String nppId = obj.getnppId();
			if(nppId.trim().length() <=0)
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return "Vui lòng chọn Nhà phân phối";
			}
			if(ddkdFromId.trim().length() <=0)
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return "Bạn cần chọn chuyển từ NVBH nào";
			}
			if(ddkdToId.trim().length() <=0)
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return "Bạn cần chọn chuyển đến NVBH nào";
			}
			
			query = "\n select " +
					"\n			(select ten from daidienkinhdoanh where pk_seq = "+ddkdFromId+")fromName    " +
					"\n			,isnull((select route_fk from daidienkinhdoanh where pk_seq = "+ddkdFromId+"),0) from_route_fk    " +
					"\n			,(select ten from daidienkinhdoanh where pk_seq = "+ddkdToId+") toName 		" +
					"\n			,isnull((select route_fk from daidienkinhdoanh where pk_seq = "+ddkdToId+"),0)to_route_fk 		" +
					"\n			,(  select count(*) from khachhang_tuyenbh where tbh_fk in (select pk_seq from tuyenbanhang where DDKD_FK = "+ddkdToId+" and npp_fk = "+nppId+"  )   )sokhTo	 ";
			ResultSet rsInfo = db.get(query);
			rsInfo.next();
			String fromName = rsInfo.getString("fromName");
			String toName = rsInfo.getString("toName");
			int sokhTo = rsInfo.getInt("sokhTo");
			String from_route_fk = rsInfo.getString("from_route_fk");
			String to_route_fk = rsInfo.getString("to_route_fk");
			rsInfo.close();
			if(from_route_fk.equals("0"))
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return "Nhân viên " + fromName + " chưa chọn route!";
			}
			if(loai.equals("1") && to_route_fk.equals("0"))
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return "Nhân viên " + toName + " chưa chọn route!";
			}
			
			if(loai.equals("0"))// chuyển tuyến
			{
				if(sokhTo > 0)
				{
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return "Nhân viên " + toName + " đã có KH, không thể chuyển!";
				}
	
				query = " 	insert khachhang_tuyenbh( tbh_fk,khachhang_fk,sott,tanso)  " +
						"   select tuyen.tuyenToId ,a.khachhang_fk,a.sott,a.tanso " +
						"	from khachhang_tuyenbh a	" +
						"	inner join tuyenbanhang t on a.tbh_fk = t.pk_seq " +
						"	cross apply " +
						"	(	" +
						"		select tTo.pk_seq tuyenToId " +
						"		from tuyenbanhang tTo " +
						"		where tTo.ngayId = t.NgayId and tTo.DDKD_FK = "+ddkdToId+" and tTo.NPP_FK =   "+ nppId +	
						"	)tuyen " +
						" 	where t.DDKD_FK = "+ddkdFromId+"  and t.NPP_FK =   "+ nppId ;
				System.out.println("query = 1:"+ query);
				
				int x= db.updateReturnInt(query);
				System.out.println("sodong inser ="+ x);
				if(x <=0)
				{
					
					
					
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return "Không có Khách hàng thuộc nhân viên " + fromName;
				}
				query = " 	delete a from khachhang_tuyenbh a " +
						"	inner join tuyenbanhang t on a.tbh_fk = t.pk_seq " +
						" 	where t.DDKD_FK = "+ddkdFromId+"  and t.NPP_FK =   "+ nppId ;
				int y = db.updateReturnInt(query);
				System.out.println("delete :"+ query);
				System.out.println("sodong delete y ="+ y);
				if(y !=x )
				{
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return "Số lượng chuyển và xóa  không bằng nhau";
				}
				
				
				
				query = " update daidienkinhdoanh set trangthai = 0  where pk_seq =  "+ ddkdFromId;
				if(db.updateReturnInt(query) <= 0 )
				{
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return "Số route ghi nhận khác số KH chuyển sang";
				}
				query = " update daidienkinhdoanh set route_fk="+from_route_fk+", trangthai = 1   where pk_seq =  "+ ddkdToId;
				if(db.updateReturnInt(query) <= 0 )
				{
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return "Không thể cập nhạt route KH:" + query;
				}
				Daidienkinhdoanh.updateDaidienkinhdoanh_log(db ,ddkdFromId,"Chuyển tuyến ");
				Daidienkinhdoanh.updateDaidienkinhdoanh_log(db ,ddkdToId,"Chuyển tuyến ");
				
			}
			else if(loai.equals("1"))// đảo tuyến
			{
				query = //" 	insert khachhang_tuyenbh( tbh_fk,khachhang_fk,sott,tanso)  " +
					"   select a.khachhang_fk,a.sott,a.tanso , t.ngayId " +
					"	from khachhang_tuyenbh a	" +
					"	inner join tuyenbanhang t on a.tbh_fk = t.pk_seq " +
					" 	where t.DDKD_FK = "+ddkdFromId+"  and t.NPP_FK =   "+ nppId ;
				ResultSet rsFrom = db.get(query);
				
				query = //" 	insert khachhang_tuyenbh( tbh_fk,khachhang_fk,sott,tanso)  " +
					"   select a.khachhang_fk,a.sott,a.tanso  , t.ngayId " +
					"	from khachhang_tuyenbh a	" +
					"	inner join tuyenbanhang t on a.tbh_fk = t.pk_seq " +
					" 	where t.DDKD_FK = "+ddkdToId+"  and t.NPP_FK =   "+ nppId ;
				ResultSet rsTo = db.get(query);
				
				query = " delete khachhang_tuyenbh where tbh_fk in (select pk_seq from tuyenbanhang where npp_fk ="+nppId+" and ddkd_fk = "+ddkdFromId+" ) ";
				int sodongDeleteFrom = db.updateReturnInt(query);
				if(sodongDeleteFrom < 0) 
				{
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return "Lỗi xóa tuyến NVBH " + fromName;
				}
				
				query = " delete khachhang_tuyenbh where tbh_fk in (select pk_seq from tuyenbanhang where npp_fk ="+nppId+" and ddkd_fk = "+ddkdToId+" ) ";
				int sodongDeleteTo = db.updateReturnInt(query);
				if(sodongDeleteTo < 0) 
				{
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return "Lỗi xóa tuyến NVBH " + toName;
				}
				int sochenFromTo=0;
				int sochenToFrom=0;
				while(rsFrom.next())
				{
					query = " 	insert khachhang_tuyenbh( tbh_fk,khachhang_fk,sott,tanso)" +
							"	select tuyen.tuyenToId ,a.khachhang_fk,a.sott,a.tanso" +
							"  	from " +
							"	(" +
							"		select "+rsFrom.getString("khachhang_fk")+" khachhang_fk , "+rsFrom.getString("sott")+" sott, '"+rsFrom.getString("tanso")+"' tanso, "+rsFrom.getString("ngayId")+" ngayId " +
							"	)a "+	
							"	cross apply " +
							"	(	" +
							"		select tTo.pk_seq tuyenToId " +
							"		from tuyenbanhang tTo " +
							"		where tTo.ngayId = a.ngayId and tTo.DDKD_FK = "+ddkdToId+" and tTo.NPP_FK =   "+ nppId +	
							"	)tuyen " ;
					int soInsertFromTo = db.updateReturnInt(query);
					sochenFromTo += soInsertFromTo;
					if(soInsertFromTo <= 0) 
					{
						db.getConnection().rollback();
						db.getConnection().setAutoCommit(true);
						return "Lỗi Chuyển từ "+fromName+" qua "+toName+":  " + query;
					}
					
				}
				if(sochenFromTo != sodongDeleteFrom)
				{
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return "Lệch số lượng kh chuyển từ "+fromName+" qua "+toName+" ";
				}
				
				while(rsTo.next())
				{
					query = " 	insert khachhang_tuyenbh( tbh_fk,khachhang_fk,sott,tanso)" +
							"	select tuyen.tuyenFromId ,a.khachhang_fk,a.sott,a.tanso  " +
							"  	from " +
							"	(" +
							"		select "+rsTo.getString("khachhang_fk")+" khachhang_fk , '"+rsTo.getString("sott")+"' sott, '"+rsTo.getString("tanso")+"' tanso, "+rsTo.getString("ngayId")+" ngayId " +
							"	)a "+	
							"	cross apply " +
							"	(	" +
							"		select tFrom.pk_seq tuyenFromId " +
							"		from tuyenbanhang tFrom " +
							"		where tFrom.ngayId = a.ngayId and tFrom.DDKD_FK = "+ddkdFromId+" and tFrom.NPP_FK =   "+ nppId +	
							"	)tuyen " ;
					int soInsertToFrom = db.updateReturnInt(query);
					sochenToFrom += soInsertToFrom;
					if(soInsertToFrom <= 0) 
					{
						db.getConnection().rollback();
						db.getConnection().setAutoCommit(true);
						return "Lỗi Chuyển từ "+toName+" qua "+fromName+" : " + query;
					}
					
				}
				if(sochenToFrom != sodongDeleteTo)
				{
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return "Lệch số lượng kh chuyển từ "+toName+" qua "+fromName+" ";
				}
				
				query = " update daidienkinhdoanh set  trangthai = 0 ,route_fk = null  where pk_seq =  "+ ddkdFromId ;
				if(db.updateReturnInt(query) <= 0 )
				{
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return "Không thể cập nhật route"+ fromName +" , x = " + query;
				}
				query = " update daidienkinhdoanh set  trangthai = 0 ,route_fk = null  where pk_seq =  "+ ddkdToId ;
				if(db.updateReturnInt(query) <= 0 )
				{
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return "Không thể cập nhật route"+ fromName +" , x = " + query;
				}
				
				query =		"\n update daidienkinhdoanh set route_fk="+to_route_fk+", trangthai = 1   where trangthai = 0  and pk_seq =  "+ ddkdFromId;
				if(db.updateReturnInt(query) <= 0 )
				{
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return "Không thể cập nhật route"+ fromName +" , x = " + query;
				}
				
				query = "  update daidienkinhdoanh set route_fk="+from_route_fk+", trangthai = 1   where trangthai = 0  and pk_seq =  "+ ddkdToId;
				if(db.updateReturnInt(query) <= 0 )
				{
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return "Không thể cập nhật route"+ toName+" , x = " + query;
				}
				Daidienkinhdoanh.updateDaidienkinhdoanh_log(db ,ddkdFromId,"Đảo tuyến");
				Daidienkinhdoanh.updateDaidienkinhdoanh_log(db ,ddkdToId,"Đảo tuyến");
			}
			else
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return "Vui  lòng chọn tác vụ";
			}
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return "Thành công!";
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return "Exception::" + e.getMessage();
		}
		
	}

	private void XuatFileExcelSR(HttpServletResponse response,String NppId) throws IOException 
	{
		OutputStream out1 = null;
		try {

			dbutils db=new dbutils();
			String npp =  "select dbo.ftbodau2(ten) ten from nhaphanphoi where pk_seq = "+NppId+" ";
			String ten = "";
			ResultSet tennppRs = db.get(npp);
			if(tennppRs != null) {
				if(tennppRs.next())
				{
					ten = tennppRs.getString("ten");
					tennppRs.close();
				}
			}
			response.setContentType("application/vnd.ms-excel");
			response.setHeader
			("Content-Disposition", "attachment; filename=MCP_"+ten+".xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());

			String sql = "\n select npp.ten as tennpp ,npp.ma,kv.ten as tenkv " + // ,ddkd.pk_seq as ddkdid,ddkd.ten  as ddkdten 
			"\n from nhaphanphoi npp  "+
			"\n left join khuvuc kv  on kv.pk_Seq=npp.khuvuc_fk "+ 
			//"\n inner join daidienkinhdoanh  ddkd on ddkd.npp_fk=npp.pk_seq   "+
			"\n where npp.pk_seq="+NppId ;	

			System.out.println("sql upload : "+sql);

			ResultSet rs=db.get(sql);
			int k=0;
			if (rs.next()){
				WritableSheet sheet = w.createSheet("MCP", k); // rs.getString("ddkdid")

				sheet.addCell(new Label(0, 1, "NPP : "));sheet.addCell(new Label(1, 1, ""+rs.getString("tennpp") ));

				sheet.addCell(new Label(0, 2, "KHU VỰC : "));sheet.addCell(new Label(1, 2, ""+rs.getString("tenkv") ));

				//sheet.addCell(new Label(0, 3, "NVBH : "));sheet.addCell(new Label(1, 3, ""+rs.getString("ddkdten") ));

				WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
				cellFont.setColour(Colour.BLACK);

				WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

				cellFormat.setBackground(jxl.format.Colour.GRAY_25);
				cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

				//cellFormat.setFont()
				int count = 0;
				sheet.addCell(new Label(count++, 4, "SỐ TT",cellFormat));
				sheet.addCell(new Label(count++, 4, "Code Route",cellFormat));
				sheet.addCell(new Label(count++, 4, "Route Name",cellFormat));
				sheet.addCell(new Label(count++, 4, "Code NVBH",cellFormat));
				sheet.addCell(new Label(count++, 4, "Họ tên NVBH",cellFormat));
				sheet.addCell(new Label(count++, 4, "MÃ KH",cellFormat));
				sheet.addCell(new Label(count++, 4, "MÃ THAM CHIẾU",cellFormat));
				sheet.addCell(new Label(count++, 4 , "TÊN CỬA HIỆU",cellFormat));
				sheet.addCell(new Label(count++, 4, "CHỦ CỬA HIỆU",cellFormat));
				sheet.addCell(new Label(count++, 4, "SỐ ĐIỆN THOẠI",cellFormat));
				sheet.addCell(new Label(count++, 4, "ĐỊA CHỈ",cellFormat));
				sheet.addCell(new Label(count++, 4, "MÃ PHƯỜNG/XÃ",cellFormat));
				sheet.addCell(new Label(count++, 4, "TÊN PHƯỜNG/XÃ",cellFormat));
				sheet.addCell(new Label(count++, 4, "MÃ QUẬN/HUYỆN",cellFormat));
				sheet.addCell(new Label(count++, 4, "TÊN QUẬN/HUYỆN",cellFormat));
				sheet.addCell(new Label(count++, 4, "MÃ TỈNH/THÀNH PHỐ",cellFormat));
				sheet.addCell(new Label(count++, 4, "TÊN TỈNH/THÀNH PHỐ",cellFormat));
				sheet.addCell(new Label(count++, 4, "VỊ TRÍ CỬA HÀNG",cellFormat));
				sheet.addCell(new Label(count++, 4, "LOẠI CỬA HÀNG",cellFormat));
				sheet.addCell(new Label(count++, 4, "HẠNG CỬA HÀNG",cellFormat));
				sheet.addCell(new Label(count++, 4, "TUYẾN THỨ",cellFormat));
				sheet.addCell(new Label(count++, 4, "TẦN SUẤT VIẾNG THĂM",cellFormat));
				sheet.addCell(new Label(count++, 4, "TRẠNG THÁI",cellFormat));
				sheet.addCell(new Label(count++, 4, "TỌA ĐỘ",cellFormat));
				sheet.addCell(new Label(count++, 4, "HÌNH THỨC KINH DOANH",cellFormat));
				sheet.addCell(new Label(count++, 4, "SỐ NGÀY NỢ",cellFormat));
				sheet.addCell(new Label(count++, 4, "SỐ TIỀN NỢ",cellFormat));
				sheet.addCell(new Label(count++, 4, "DIỆN TÍCH CỬA HÀNG",cellFormat));
				sheet.addCell(new Label(count++, 4, "NHÓM KHÁCH HÀNG",cellFormat));
				sheet.addCell(new Label(count++, 4, "VĨ ĐỘ",cellFormat));
				sheet.addCell(new Label(count++, 4, "KINH ĐỘ",cellFormat));

				/*	sql="select vt.vitri ,hch.hang as hangcuahang,lch.loai as loaicuahang,kh.phuong,  "+ 
						" kh.nguoidaidien,kh.chucuahieu,kh.tinhthanh_fk,kh.quanhuyen_fk,  "+ 
						" kh.ten as tencuahieu,kh.smartid,kh.diachi,kh.dienthoai,tbh.ngayid,khtbh.tanso,khtbh.sott,kh.trangthai, " +
						" CASE WHEN kh.LAT IS NOT NULL AND kh.LONG IS NOT NULL THEN '1' ELSE '0' END AS TOADO ,htkd.DienGiai as HTKD " +
						" from tuyenbanhang tbh "+ 
						" inner join khachhang_tuyenbh khtbh on tbh.pk_seq=khtbh.tbh_fk "+ 
						" inner join khachhang kh on kh.pk_seq=khtbh.khachhang_fk "+ 
						" left join hangcuahang hch on hch.pk_seq=kh.hch_fk "+ 
						" left join loaicuahang lch on lch.pk_seq=kh.lch_fk "+ 
						" left join vitricuahang vt on vt.pk_seq=kh.vtch_fk " +
						"  left join HINHTHUCKINHDOANH htkd on khachhang_htkd bb on bb.pk_seq=htkd_fk	and bb.khachhang_fk=kh.pk_Seq "+ 
						" where tbh.npp_fk= "+NppId +" and tbh.ddkd_fk=" + rs.getString("ddkdid") +
						" order by ddkd_fk,ngayid,khtbh.sott ";*/

				sql = "\n select distinct isnull(px.ten,'')pxten,isnull(qh.ten,'')qhten,isnull(tt.ten,'')ttten, " +
				"\n ddkd.SMARTID codenvbh,ddkd.ten tennvbh,kh.coderoute,kh.routename,isnull(kh.mathamchieu,'')mathamchieu, " +
				"\n vt.vitri ,hch.hang as hangcuahang,lch.loai as loaicuahang,kh.phuongxa_FK as phuong,  "+  
				"\n	kh.nguoidaidien,kh.chucuahieu,kh.tinhthanh_fk,kh.quanhuyen_fk,   "+
				"\n	kh.ten as tencuahieu,kh.smartid,kh.diachi,kh.dienthoai,kh.trangthai, "+ 
				"\n	case when kh.lat is not null and kh.long is not null then '1' else '0' end as toado , "+
				"\n	stuff      "+
				"\n	(     "+
				"\n		(    "+	
				"\n			select distinct top 100 percent ' , ' + N''+cast(bb.ngayid as varchar(2)) "+
				"\n			from khachhang_tuyenbh aa inner join tuyenbanhang bb on bb.pk_seq=aa.tbh_fk "+ 
				"\n			where   aa.khachhang_fk=kh.pk_seq and bb.npp_fk=tbh.npp_fk and bb.ddkd_fk=tbh.ddkd_fk "+
				"\n			order by ' , ' + N''+cast(bb.ngayid as varchar(2)) "+
				"\n			for xml path('')       "+
				"\n		), 1, 2, ''    "+
				"\n	) + ' '  as ngayid, "+
				"\n	stuff      "+
				"\n	(     "+
				"\n		(    "+
				"\n			select distinct  top 100 percent ' , ' + N''+cast(aa.tanso as varchar(20)) "+
				"\n			from khachhang_tuyenbh aa inner join tuyenbanhang bb on bb.pk_seq=aa.tbh_fk  "+ 
				"\n			inner join daidienkinhdoanh cc on cc.pk_seq=bb.ddkd_fk "+
				"\n			where aa.khachhang_fk=kh.pk_seq and bb.npp_fk=tbh.npp_fk and bb.ddkd_fk=tbh.ddkd_fk "+
				"\n			order by ' , ' + N''+cast(aa.tanso as varchar(20)) "+
				"\n			for xml path('')       "+
				"\n		), 1, 2, ''   "+
				"\n	) + ' '  as tanso, "+
				"\n	stuff   "+     
				"\n	(     "+
				"\n		(    "+
				"\n			select distinct top 100 percent ' , ' + aa.diengiai "+ 
				"\n			from HINHTHUCKINHDOANH aa inner join khachhang_htkd  bb on aa.pk_seq=bb.htkd_fk "+	
				"\n			where  bb.khachhang_fk=kh.pk_Seq "+
				"\n			order by ' , ' + aa.diengiai  "+
				"\n			for xml path('')       "+
				"\n		 ), 1, 2, ''    "+
				"\n	) + ' '  as HTKD,kh.dientichcuahang, " +
				"\n stuff   "+     
				"\n	(     "+
				"\n		(    "+
				"\n			select distinct top 100 percent ' , ' + aa.diengiai "+ 
				"\n			from nhomkhachhang aa inner join NHOMKHACHHANG_KHACHHANG  bb on aa.pk_seq=bb.nkh_fk "+	
				"\n			where  bb.kh_fk=kh.pk_Seq "+
				"\n			order by ' , ' + aa.diengiai  "+
				"\n			for xml path('')       "+
				"\n		 ), 1, 2, ''    "+
				"\n	) + ' '  as nhomkh, kh.songayno,kh.sotienno , kh.lat , kh.long "+
				"\n	from tuyenbanhang tbh "+ 
				"\n	inner join khachhang_tuyenbh khtbh on tbh.pk_seq=khtbh.tbh_fk "+  
				"\n	inner join khachhang kh on kh.pk_seq=khtbh.khachhang_fk  and kh.npp_fk=tbh.npp_fk "+
				"\n	left join hangcuahang hch on hch.pk_seq=kh.hch_fk "+  
				"\n	left join loaicuahang lch on lch.pk_seq=kh.lch_fk "+  
				"\n	left join vitricuahang vt on vt.pk_seq=kh.vtch_fk  "+
				"\n left join daidienkinhdoanh ddkd on ddkd.pk_seq in (select ddkd_fk from tuyenbanhang where pk_seq = khtbh.tbh_fk) " +
				"\n left join phuongxa px on px.pk_seq = kh.phuongxa_fk " +
				"\n left join quanhuyen qh on qh.pk_seq = kh.quanhuyen_fk " +
				"\n left join tinhthanh tt on tt.pk_seq = kh.tinhthanh_fk " +
				"\n where tbh.npp_fk= "+NppId+" and tbh.ddkd_fk= xxx.pk_seq ";
				
				String sqlTong = "\n select data.*  " +
								 "\n from daidienkinhdoanh xxx	" +
								 "\n cross apply " +
								 "\n (" +
								  sql +
								 "\n ) data" +
								 "\n where xxx.trangthai = 1	" +
								 "\n order by xxx.pk_seq,ngayid ";
				
				System.out.println("Query Export MCP: "+sqlTong);

				ResultSet rs1=db.get(sqlTong);
				Label label ;

				Number number;
				int j=5;
				//set style to cell data
				WritableCellFormat cellFormat2 = new WritableCellFormat();

				cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
				int stt = 0;
				/*if(rs1!=null)*/
				while (rs1.next()){

					stt++;
					count = 0;
					label = new Label(count++, j, ""+stt,cellFormat2); 
					sheet.addCell(label); 

					label = new Label(count++, j, rs1.getString("CodeRoute"),cellFormat2); 
					sheet.addCell(label); 

					label = new Label(count++, j, rs1.getString("RouteName"),cellFormat2); 
					sheet.addCell(label); 

					label = new Label(count++, j, rs1.getString("codenvbh"),cellFormat2); 
					sheet.addCell(label); 

					label = new Label(count++, j, rs1.getString("tennvbh"),cellFormat2); 
					sheet.addCell(label); 

					label = new Label(count++, j, rs1.getString("smartid"),cellFormat2); 
					sheet.addCell(label); 

					label = new Label(count++, j, rs1.getString("mathamchieu"),cellFormat2); 
					sheet.addCell(label); 

					label = new Label(count++, j,rs1.getString("tencuahieu"),cellFormat2); 
					sheet.addCell(label); 

					label = new Label(count++, j,rs1.getString("chucuahieu"),cellFormat2); 
					sheet.addCell(label); 

					label = new Label(count++, j,rs1.getString("dienthoai"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(count++, j,rs1.getString("diachi"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(count++, j,rs1.getString("phuong"),cellFormat2); 
					sheet.addCell(label); 
					
					label = new Label(count++, j,rs1.getString("pxten"),cellFormat2); 
					sheet.addCell(label); 

					label = new Label(count++, j,rs1.getString("quanhuyen_fk"),cellFormat2); 
					sheet.addCell(label); 
					
					label = new Label(count++, j,rs1.getString("qhten"),cellFormat2); 
					sheet.addCell(label); 

					label = new Label(count++, j,rs1.getString("tinhthanh_fk"),cellFormat2); 
					sheet.addCell(label);
					
					label = new Label(count++, j,rs1.getString("ttten"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(count++, j,rs1.getString("vitri"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(count++, j,rs1.getString("loaicuahang"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(count++, j,rs1.getString("hangcuahang"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(count++, j,rs1.getString("ngayid"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(count++, j,rs1.getString("tanso"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(count++, j,rs1.getString("trangthai"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(count++, j,rs1.getString("TOADO"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(count++, j,rs1.getString("HTKD"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(count++, j,rs1.getString("songayno"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(count++, j,rs1.getString("sotienno"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(count++, j,rs1.getString("dientichcuahang"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(count++, j,rs1.getString("nhomkh"),cellFormat2); 
					sheet.addCell(label);
					
					label = new Label(count++, j,rs1.getString("lat"),cellFormat2); 
					sheet.addCell(label);
					
					label = new Label(count++, j,rs1.getString("long"),cellFormat2); 
					sheet.addCell(label);
					
					j++;
				}
				k++;
			}
			w.write();
			w.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Error Cac Ban : "+e.toString());
		} 
		finally{
			if (out1 != null)
				out1.close();
		}
	}

	private void XuatFileExcelSRDS(HttpServletResponse response,String NppId) throws IOException 
	{
		OutputStream out1 = null;
		try {

			dbutils db=new dbutils();
			String npp =  "select  dbo.ftbodau2(ten) ten from nhaphanphoi where pk_seq = "+NppId+" ";
			String ten = "";
			ResultSet tennppRs = db.get(npp);
			if(tennppRs != null)
				if(tennppRs.next())
				{
					ten = tennppRs.getString("ten");
					tennppRs.close();
				}
			response.setContentType("application/vnd.ms-excel");
			response.setHeader
			("Content-Disposition", "attachment; filename=MCP_"+ten+".xls");
			WritableWorkbook w = 
				jxl.Workbook.createWorkbook(response.getOutputStream());


			String sql = "\n select npp.ten as tennpp ,npp.ma,kv.ten as tenkv,ddkd.pk_seq as ddkdid,ddkd.ten  as ddkdten "+
			"\n from nhaphanphoi npp  "+
			"\n inner join khuvuc kv  on kv.pk_Seq=npp.khuvuc_fk "+ 
			"\n inner join daidienkinhdoanh  ddkd on ddkd.npp_fk=npp.pk_seq   "+
			"\n where npp.pk_seq="+NppId ;	

			System.out.println("sql upload : "+sql);

			ResultSet rs=db.get(sql);
			int k=0;
			while (rs.next()){
				WritableSheet sheet = w.createSheet(rs.getString("ddkdid"), k);

				sheet.addCell(new Label(0, 1, "NPP : "));sheet.addCell(new Label(1, 1, ""+rs.getString("tennpp") ));

				sheet.addCell(new Label(0, 2, "KHU VỰC : "));sheet.addCell(new Label(1, 2, ""+rs.getString("tenkv") ));

				sheet.addCell(new Label(0, 3, "NVBH : "));sheet.addCell(new Label(1, 3, ""+rs.getString("ddkdten") ));

				WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
				cellFont.setColour(Colour.BLACK);

				WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

				cellFormat.setBackground(jxl.format.Colour.GRAY_25);
				cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

				//cellFormat.setFont()
				sheet.addCell(new Label(0, 4, "SỐ TT",cellFormat));
				sheet.addCell(new Label(1, 4, "MÃ KH",cellFormat));
				sheet.addCell(new Label(2, 4 , "TÊN CỬA HIỆU",cellFormat));
				sheet.addCell(new Label(3, 4, "CHỦ CỬA HIỆU",cellFormat));
				sheet.addCell(new Label(4, 4, "ĐỊA CHỈ",cellFormat));
				sheet.addCell(new Label(5, 4, "PHƯỜNG XÃ",cellFormat));
				sheet.addCell(new Label(6, 4, "QUẬN/HUYỆN",cellFormat));
				sheet.addCell(new Label(7, 4, "TỈNH/THÀNH PHỐ",cellFormat));
				sheet.addCell(new Label(8, 4, "SỐ ĐIỆN THOẠI",cellFormat));
				sheet.addCell(new Label(9, 4, "VỊ TRÍ CỬA HÀNG",cellFormat));
				sheet.addCell(new Label(10, 4, "LOẠI CỬA HÀNG",cellFormat));
				sheet.addCell(new Label(11, 4, "HẠNG CỬA HÀNG",cellFormat));
				sheet.addCell(new Label(12, 4, "TUYẾN THỨ",cellFormat));
				sheet.addCell(new Label(13, 4, "TẦN SUẤT VIẾNG THĂM",cellFormat));
				sheet.addCell(new Label(14, 4, "TRẠNG THÁI",cellFormat));
				sheet.addCell(new Label(15, 4, "TỌA ĐỘ",cellFormat));
				sheet.addCell(new Label(16, 4, "HÌNH THỨC KINH DOANH",cellFormat));
				sheet.addCell(new Label(17, 4, "SỐ NGÀY NỢ",cellFormat));
				sheet.addCell(new Label(18, 4, "SỐ TIỀN NỢ",cellFormat));
				sheet.addCell(new Label(19, 4, "DIỆN TÍCH CỬA HÀNG",cellFormat));
				sheet.addCell(new Label(20, 4, "NHÓM KHÁCH HÀNG",cellFormat));
				sheet.addCell(new Label(21, 4, "DOANH SỐ KH",cellFormat));

				/*	sql="select vt.vitri ,hch.hang as hangcuahang,lch.loai as loaicuahang,kh.phuong,  "+ 
						" kh.nguoidaidien,kh.chucuahieu,kh.tinhthanh_fk,kh.quanhuyen_fk,  "+ 
						" kh.ten as tencuahieu,kh.smartid,kh.diachi,kh.dienthoai,tbh.ngayid,khtbh.tanso,khtbh.sott,kh.trangthai, " +
						" CASE WHEN kh.LAT IS NOT NULL AND kh.LONG IS NOT NULL THEN '1' ELSE '0' END AS TOADO ,htkd.DienGiai as HTKD " +
						" from tuyenbanhang tbh "+ 
						" inner join khachhang_tuyenbh khtbh on tbh.pk_seq=khtbh.tbh_fk "+ 
						" inner join khachhang kh on kh.pk_seq=khtbh.khachhang_fk "+ 
						" left join hangcuahang hch on hch.pk_seq=kh.hch_fk "+ 
						" left join loaicuahang lch on lch.pk_seq=kh.lch_fk "+ 
						" left join vitricuahang vt on vt.pk_seq=kh.vtch_fk " +
						"  left join HINHTHUCKINHDOANH htkd on khachhang_htkd bb on bb.pk_seq=htkd_fk	and bb.khachhang_fk=kh.pk_Seq "+ 
						" where tbh.npp_fk= "+NppId +" and tbh.ddkd_fk=" + rs.getString("ddkdid") +
						" order by ddkd_fk,ngayid,khtbh.sott ";*/


				sql=
					" select distinct vt.vitri ,hch.hang as hangcuahang,lch.loai as loaicuahang,kh.phuongxa_FK as phuong,  "+  
					"\n				 kh.nguoidaidien,kh.chucuahieu,kh.tinhthanh_fk,kh.quanhuyen_fk,   "+
					"\n				 kh.ten as tencuahieu,kh.smartid,kh.diachi,kh.dienthoai,kh.trangthai, "+ 
					"\n				 case when kh.lat is not null and kh.long is not null then '1' else '0' end as toado , "+
					"\n				stuff      "+
					"\n				(     "+
					"\n					(    "+	
					"\n						select distinct top 100 percent ' , ' + N''+cast(bb.ngayid as varchar(2)) "+
					"\n						from khachhang_tuyenbh aa inner join tuyenbanhang bb on bb.pk_seq=aa.tbh_fk "+ 
					"\n						where   aa.khachhang_fk=kh.pk_seq and bb.npp_fk=tbh.npp_fk and bb.ddkd_fk=tbh.ddkd_fk "+
					"\n						order by ' , ' + N''+cast(bb.ngayid as varchar(2)) "+
					"\n						for xml path('')       "+
					"\n					 ), 1, 2, ''    "+
					"\n				) + ' '  as ngayid, "+
					"\n				stuff      "+
					"\n				(     "+
					"\n					(    "+
					"\n							 "+
					"\n						select distinct  top 100 percent ' , ' + N''+cast(aa.tanso as varchar(20)) "+
					"\n						from khachhang_tuyenbh aa inner join tuyenbanhang bb on bb.pk_seq=aa.tbh_fk  "+ 
					"\n						  inner join daidienkinhdoanh cc on cc.pk_seq=bb.ddkd_fk "+
					"\n						where   aa.khachhang_fk=kh.pk_seq and bb.npp_fk=tbh.npp_fk and bb.ddkd_fk=tbh.ddkd_fk "+
					"\n						order by ' , ' + N''+cast(aa.tanso as varchar(20)) "+
					"\n						for xml path('')       "+
					"\n					 ), 1, 2, ''   "+
					"\n				) + ' '  as tanso "+
					"\n	,stuff   "+     
					"\n	(     "+
					"\n		(    "+
					"\n			select distinct top 100 percent ' , ' + aa.diengiai "+ 
					"\n			from HINHTHUCKINHDOANH aa inner join khachhang_htkd  bb on aa.pk_seq=bb.htkd_fk "+	
					"\n			where  bb.khachhang_fk=kh.pk_Seq "+
					"\n			order by ' , ' + aa.diengiai  "+
					"\n			for xml path('')       "+
					"\n		 ), 1, 2, ''    "+
					"\n	) + ' '  as HTKD,kh.dientichcuahang, stuff   "+     
					"\n	(     "+
					"\n		(    "+
					"\n			select distinct top 100 percent ' , ' + aa.diengiai "+ 
					"\n			from nhomkhachhang aa inner join NHOMKHACHHANG_KHACHHANG  bb on aa.pk_seq=bb.nkh_fk "+	
					"\n			where  bb.kh_fk=kh.pk_Seq "+
					"\n			order by ' , ' + aa.diengiai  "+
					"\n			for xml path('')       "+
					"\n		 ), 1, 2, ''    "+
					"\n	) + ' '  as nhomkh , kh.songayno,kh.sotienno, round(dh.doanhso,0) as dskh"+
					"\n			 from tuyenbanhang tbh "+ 
					"\n				 inner join khachhang_tuyenbh khtbh on tbh.pk_seq=khtbh.tbh_fk "+  
					"\n				 inner join khachhang kh on kh.pk_seq=khtbh.khachhang_fk  and kh.npp_fk=tbh.npp_fk "+
					"\n				 left join hangcuahang hch on hch.pk_seq=kh.hch_fk "+  
					"\n				 left join loaicuahang lch on lch.pk_seq=kh.lch_fk "+  
					"\n				 left join vitricuahang vt on vt.pk_seq=kh.vtch_fk  "+
					"\n				 left join ( "+
					"\n							select khachhang_fk, round(sum(dh_sp.soluong*dh_sp.giamua),0) as doanhso from donhang dh "+
					"\n							inner join donhang_sanpham dh_sp on dh_sp.donhang_fk = dh.pk_seq "+
					"\n							where dh.trangthai = 1 "+
					"\n							and dh.ngaynhap >= CONVERT(date,DATEADD(month, -2, dbo.GetLocalDate(DEFAULT))) and dh.ngaynhap <= CONVERT(date, dbo.GetLocalDate(DEFAULT))  and trangthai = 1 "+
					"\n							group by khachhang_fk "+
					"\n				 ) dh on dh.khachhang_fk = kh.pk_seq "+
					"\n where tbh.npp_fk= "+NppId +" and tbh.ddkd_fk=" + rs.getString("ddkdid") +""+
					"\n order by ngayid ";
				System.out.println(sql);

				ResultSet rs1=db.get(sql);
				Label label ;

				Number number;
				int j=5;
				//set style to cell data
				WritableCellFormat cellFormat2 = new WritableCellFormat();

				cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
				int stt = 0;
				/*if(rs1!=null)*/
				while (rs1.next()){
					stt++;
					label = new Label(0, j, ""+stt,cellFormat2); 

					sheet.addCell(label); 

					label = new Label(1, j, rs1.getString("smartid"),cellFormat2); 
					sheet.addCell(label); 

					label = new Label(2, j,rs1.getString("tencuahieu"),cellFormat2); 
					sheet.addCell(label); 

					label = new Label(3, j,rs1.getString("chucuahieu"),cellFormat2); 
					sheet.addCell(label); 


					label = new Label(4, j,rs1.getString("diachi"),cellFormat2); 
					sheet.addCell(label);


					label = new Label(5, j,rs1.getString("phuong"),cellFormat2); 
					sheet.addCell(label); 

					label = new Label(6, j,rs1.getString("quanhuyen_fk"),cellFormat2); 
					sheet.addCell(label); 

					label = new Label(7, j,rs1.getString("tinhthanh_fk"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(8, j,rs1.getString("dienthoai"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(9, j,rs1.getString("vitri"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(10, j,rs1.getString("loaicuahang"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(11, j,rs1.getString("hangcuahang"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(12, j,rs1.getString("ngayid"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(13, j,rs1.getString("tanso"),cellFormat2); 
					sheet.addCell(label);
					label = new Label(14, j,rs1.getString("trangthai"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(15, j,rs1.getString("TOADO"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(16, j,rs1.getString("HTKD"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(17, j,rs1.getString("songayno"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(18, j,rs1.getString("sotienno"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(19, j,rs1.getString("dientichcuahang"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(20, j,rs1.getString("nhomkh"),cellFormat2); 
					sheet.addCell(label);


					number = new Number(21, j, rs1.getDouble("dskh"), cellFormat2);
					sheet.addCell(number);


					j++;
				}


				k++;
			}
			w.write();
			w.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Error Cac Ban : "+e.toString());
		} 
		finally{
			if (out1 != null)
				out1.close();
		}
	}

	private Hashtable<String, String> gethtpQuanHuyen(dbutils db) {

		Hashtable<String,String> htbtuyen= new Hashtable<String, String>();
		String sql="select pk_seq from quanhuyen";

		ResultSet rs=db.get(sql);
		try{
			while (rs.next()){
				htbtuyen.put(rs.getString("pk_seq").trim(), rs.getString("pk_seq").trim());

			}
			rs.close();
		}catch(Exception er){
			System.out.println("Loi lay tinhthanh : "+er.toString());
		}
		return htbtuyen;
	}


	private Hashtable<String, String> getHTP_DDKD_Thuoc_NPP(dbutils db,String nppId) {

		Hashtable<String,String> htb= new Hashtable<String, String>();
		String sql="select smartId,pk_seq from daidienkinhdoanh where trangthai = 1 ";
		if(nppId.trim().length() > 0)
		{
			sql +=  " and npp_Fk =" + nppId;
		}

		ResultSet rs=db.get(sql);
		try{
			while (rs.next()){
				htb.put(rs.getString("smartId").trim(), rs.getString("pk_seq").trim());

			}
			rs.close();
		}catch(Exception er){
			System.out.println("Loi lay DDKD thuoc NPP : "+er.toString());
		}
		return htb;
	}

	private Hashtable<String, String> gethtpTinhThanh(dbutils db) {

		Hashtable<String,String> htbtuyen= new Hashtable<String, String>();
		String sql="select pk_seq from tinhthanh";

		ResultSet rs=db.get(sql);
		try{
			while (rs.next()){
				htbtuyen.put(rs.getString("pk_seq").trim(), rs.getString("pk_seq").trim());

			}
			rs.close();
		}catch(Exception er){
			System.out.println("Loi lay tinhthanh : "+er.toString());
		}
		return htbtuyen;
	}
	private Hashtable<String, String> gethtpVitricuahang(dbutils db) {

		Hashtable<String,String> htbtuyen= new Hashtable<String, String>();
		String sql="select vitri,pk_seq from vitricuahang";

		ResultSet rs=db.get(sql);
		try{
			while (rs.next()){
				htbtuyen.put(rs.getString("vitri").trim(), rs.getString("pk_seq").trim());

			}
			rs.close();
		}catch(Exception er){
			System.out.println("Loi lay vtch : "+er.toString());
		}
		return htbtuyen;
	}
	private Hashtable<String, String> gethtpLoaicuahang(dbutils db) {

		Hashtable<String,String> htbtuyen= new Hashtable<String, String>();
		String sql="select loai,pk_seq from loaicuahang";

		ResultSet rs=db.get(sql);
		try{
			while (rs.next()){
				htbtuyen.put(rs.getString("loai").trim(), rs.getString("pk_seq").trim());

			}
			rs.close();
		}catch(Exception er){
			System.out.println("Loi lay loai cuahang : "+er.toString());
		}
		return htbtuyen;
	}
	private Hashtable<String, String> gethtpHangcuahang(dbutils db) {

		Hashtable<String,String> htbtuyen= new Hashtable<String, String>();
		String sql="select hang,pk_seq from hangcuahang";

		ResultSet rs=db.get(sql);
		try{
			while (rs.next()){
				htbtuyen.put(rs.getString("hang").trim(), rs.getString("pk_seq").trim());
			}
			rs.close();
		}catch(Exception er){
			System.out.println("Loi lay hangcuahang : "+er.toString());
		}
		return htbtuyen;
	}

	private Hashtable<String, String> getNhomKh(dbutils db) {

		Hashtable<String,String> htbnhomkh= new Hashtable<String, String>();
		String sql="select diengiai,pk_seq from hangcuahang";

		ResultSet rs=db.get(sql);
		try{
			while (rs.next()){
				htbnhomkh.put(rs.getString("diengiai").trim(), rs.getString("pk_seq").trim());

			}
			rs.close();
		}catch(Exception er){
			System.out.println("Loi lay nhomkh : "+er.toString());
		}
		return htbnhomkh;
	}
	private String getDateTime() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		return dateFormat.format(date);	
	}
	private Hashtable<String,String>  Htb_TuyenBH(String nppId,String userid, String nvbhid, dbutils db) {

		String sql=" insert into tuyenbanhang  (diengiai,ngaylamviec,ngaytao,ngaysua,nguoitao,nguoisua,ddkd_fk,npp_fk,ngayid) "+
		" select N'Thứ Hai','Thu hai','"+this.getDateTime()+"','"+this.getDateTime()+"',"+userid+","+userid+",pk_seq,"+nppId+",2   "+
		" from  daidienkinhdoanh   "+
		" where pk_seq not in (select ddkd_fk from tuyenbanhang where ngayid=2 and ddkd_fk="+nvbhid+" and npp_fk="+nppId+") "+
		" and pk_seq= "+nvbhid+
		" union  "+
		" select N'Thứ Ba','Thu ba','"+this.getDateTime()+"','"+this.getDateTime()+"',"+userid+","+userid+",pk_seq,"+nppId+",3   "+
		" from  daidienkinhdoanh   "+
		" where pk_seq not in (select ddkd_fk from tuyenbanhang where ngayid=3 and ddkd_fk="+nvbhid+" and npp_fk="+nppId+") "+
		" and pk_seq="+nvbhid+
		" union  "+
		" select N'Thứ Tư','Thu tu','"+this.getDateTime()+"','"+this.getDateTime()+"',"+userid+","+userid+",pk_seq,"+nppId+",4   "+
		" from  daidienkinhdoanh   "+
		" where pk_seq not in (select ddkd_fk from tuyenbanhang where ngayid=4 and ddkd_fk="+nvbhid+" and npp_fk="+nppId+") "+
		" and pk_seq="+nvbhid+
		" union  "+
		" select N'Thứ Năm','Thu nam','"+this.getDateTime()+"','"+this.getDateTime()+"',"+userid+","+userid+",pk_seq,"+nppId+",5   "+
		" from  daidienkinhdoanh   "+
		" where pk_seq not in (select ddkd_fk from tuyenbanhang where ngayid=5 and ddkd_fk="+nvbhid+" and npp_fk="+nppId+") "+
		" and pk_seq="+nvbhid+
		" union  "+
		" select N'Thứ Sáu','Thu sau','"+this.getDateTime()+"','"+this.getDateTime()+"',"+userid+","+userid+",pk_seq,"+nppId+",6  "+ 
		" from  daidienkinhdoanh   "+
		" where pk_seq not in (select ddkd_fk from tuyenbanhang where ngayid=6 and ddkd_fk="+nvbhid+" and npp_fk="+nppId+") "+
		" and pk_seq="+nvbhid+
		" union  "+
		" select N'Thứ Bảy','Thu bay','"+this.getDateTime()+"','"+this.getDateTime()+"',"+userid+","+userid+",pk_seq,"+nppId+",7 "+  
		" from  daidienkinhdoanh   "+
		" where pk_seq not in (select ddkd_fk from tuyenbanhang where ngayid=7 and ddkd_fk="+nvbhid+" and npp_fk="+nppId+") "+
		" and pk_seq=" +nvbhid;
		db.update(sql);
		System.out.println("Them Tuyen ban hang : "+sql);
		// xoa het tuyen cu cua nhan vien ban hàng này đi
		sql="delete khachhang_tuyenbh where tbh_fk in (select pk_seq from tuyenbanhang where ddkd_fk="+nvbhid +") ";
		System.out.println("Xoa TBH : "+sql);
		if(! db.update(sql)){
			System.out.println("xoa Tuyen ban hang : "+sql);
		}

		Hashtable<String,String> htbtuyen= new Hashtable<String, String>();
		sql="select pk_seq,ngayid,npp_fk,ddkd_fk from tuyenbanhang where ddkd_fk="+nvbhid +" and npp_fk="+nppId;


		ResultSet rs=db.get(sql);
		try{
			while (rs.next()){
				htbtuyen.put(rs.getString("npp_fk").trim()+"_"+ rs.getString("ddkd_fk").trim()+"_"+ rs.getString("ngayid").trim() , rs.getString("pk_seq").trim());

			}
		}catch(Exception er){
			System.out.println("Loi lay tuyen : "+er.toString());
		}
		return htbtuyen;

	}

	String getValue(Sheet sheet,int col,int row,boolean replaceDauPhay)
	{
		try{
			if(replaceDauPhay)
				return sheet.getCell(col,row).getContents().trim().replaceAll(",", "").trim();
			else 
				return sheet.getCell(col,row).getContents().trim().trim();
		}catch (Exception e) {
			return "";
		}
	}
	public static boolean isNumericOnlyNumber(String str)
	{
		return str.matches("\\d+");
	}  
	String getValue(Sheet sheet,int col,int row)
	{
		return sheet.getCell(col,row).getContents().trim().replaceAll(",", "");
	}

	private String getDateTime_MaTmp()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		Date date = new Date();
		return dateFormat.format(date);
	}
	public static void main(String[] args) {
		dbutils db= new dbutils();
		String query = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			
			query = " select pk_seq,ten from KHACHHANG where pk_seq in (1023382)";
			ResultSet rs = db.get(query);
			
			query =" update KHACHHANG set Ten ='aa' where pk_seq = 1023382 ";
			db.update(query);
			rs.next();
			System.out.println("a= " + rs.getString("ten")  );
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			
		}
		catch (Exception e) {
			// TODO: handle exception
			db.shutDown();
		}
	}
	
}
