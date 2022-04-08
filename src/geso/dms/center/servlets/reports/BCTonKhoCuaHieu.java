package geso.dms.center.servlets.reports;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.distributor.beans.khachhangtt.IKhachhangTTList;
import geso.dms.distributor.beans.khachhangtt.imp.KhachhangTTList;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.HorizontalAlignmentType;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class BCTonKhoCuaHieu extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private int items = 50;
	private int splittings = 20;


	public BCTonKhoCuaHieu() 
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
		if(!cutil.check(userId, userTen, sum)) {
			response.sendRedirect("/AHF/index.jsp");
		} else {

			IKhachhangTTList obj;
			PrintWriter out; 


			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			out  = response.getWriter();

			Utility util = new Utility();
			out = response.getWriter();

			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);
			out.println(userId);

			if (userId.length()==0)
				userId = util.antiSQLInspection(request.getParameter("userId"));

			String action = util.getAction(querystring);
			out.println(action);

			obj = new KhachhangTTList();


			obj.setUserId(userId);
			obj.init("select 1 ");
			//obj.createRS();

			session.setAttribute("obj", obj);

			String nextJSP = "/AHF/pages/Center/BCTonKhoCuaHieu.jsp";
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
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{

			OutputStream out = response.getOutputStream();	

			IKhachhangTTList obj = new KhachhangTTList();


			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			obj.setUserId(userId);
		

			Utility util = new Utility();
			userId = util.antiSQLInspection(request.getParameter("userId"));

			String action = request.getParameter("action");
			if (action == null){
				action = "";
			}



			if (action.equals("search"))
			{	    

				String search = getSearchQuery(request, obj);
				obj.setUserId(userId);
				obj.init("select 1");

				session.setAttribute("obj", obj);  	
				session.setAttribute("userId", userId);

				response.sendRedirect("/AHF/pages/Center/BCTonKhoCuaHieu.jsp");	    		    	
			}
			else if (action.equals("excel"))
			{
			
				try
				{
					obj.setTen(userTen);
					System.out.println("userTen: "+userTen);
					ExportToExcel(response,out, obj, request,userTen);
					return;
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
					obj.setMsg("Khong the tao pivot.");
				}

				obj.setUserId(userId);
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);

				response.sendRedirect("/AHF/pages/Center/BCTonKhoCuaHieu.jsp");	    		
			}
		}
	}

	private String getSearchQuery(HttpServletRequest request, IKhachhangTTList obj)
	{		
		Utility util = new Utility();
		String ten = util.antiSQLInspection(request.getParameter("khTen"));
		if ( ten == null)
			ten = "";
		obj.setTen(ten);

		String nppId = util.antiSQLInspection(request.getParameter("nppId"));
		if ( nppId == null)
			nppId = "";
		obj.setNppId(nppId);

		String hchId = util.antiSQLInspection(request.getParameter("hchTen"));
		if (hchId == null)
			hchId = "";    	
		obj.setHchId(hchId);

		String kbhId = util.antiSQLInspection(request.getParameter("kbhTen"));
		if (kbhId == null)
			kbhId = "";    	
		obj.setKbhId(kbhId);

		String vtchId = util.antiSQLInspection(request.getParameter("vtchTen"));
		if (vtchId == null)
			vtchId = "";    	
		obj.setVtId(vtchId);

		String lchId = util.antiSQLInspection(request.getParameter("lchTen"));
		if (lchId == null)
			lchId = "";    	
		obj.setLchId(lchId);

		String diadiemId = util.antiSQLInspection(request.getParameter("diadiemId"));
		if (diadiemId == null)
			diadiemId = "";    	
		obj.setDiadiemId(diadiemId);

		String xuatkhau = util.antiSQLInspection(request.getParameter("xuatkhau"));
		if (xuatkhau == null)
			xuatkhau = "0";    	
		obj.setXuatkhau(xuatkhau);

		String diachi = util.antiSQLInspection(request.getParameter("diachi"));
		if (diachi == null)
			diachi = "";    	
		obj.setDiachi(diachi.trim());

		String maFAST = util.antiSQLInspection(request.getParameter("maFAST"));
		if (maFAST == null)
			maFAST = "";    	
		obj.setMaFAST(maFAST);

		String ddkdId = util.antiSQLInspection(request.getParameter("ddkdId"));
		if (ddkdId == null)
			ddkdId = "";    	
		obj.setDdkdId(ddkdId);

		String tbhId = util.antiSQLInspection(request.getParameter("tbhId"));
		if (tbhId == null)
			tbhId = "";    	
		obj.setTbhId(tbhId);

		String trangthai = util.antiSQLInspection(request.getParameter("trangthai")); 	
		if (trangthai == null)
			trangthai = "";    		
		obj.setTrangthai(trangthai);

		String tungay = util.antiSQLInspection(request.getParameter("tungay")); 	
		if (tungay == null)
			tungay = "";    		
		obj.setTungay(tungay);

		String denngay = util.antiSQLInspection(request.getParameter("denngay")); 	
		if (denngay == null)
			denngay = "";    		
		obj.setDenngay(denngay);
		System.out.println("[den ngay] : " + obj.getDenngay());

		String loaiKH = util.antiSQLInspection(request.getParameter("loaikh")); 	
		if (loaiKH == null)
			loaiKH = "";    		
		obj.setloaiKH(loaiKH);

		String hopdong = util.antiSQLInspection(request.getParameter("hopdong")); 	
		if (hopdong == null)
			hopdong = "";    		
		obj.setHopdong(hopdong);
		String vung = util.antiSQLInspection(request.getParameter("vungid"));
		if ( vung == null)
			vung = "";
		obj.setVungid(vung);
		String mien = util.antiSQLInspection(request.getParameter("mienid"));
		if ( mien == null)
			mien = "";
		obj.setMienid(mien);

		String dayOfWeekId = util.antiSQLInspection(request.getParameter("dayOfWeekId"));
		if ( dayOfWeekId == null)
			dayOfWeekId = "";
		obj.setDayOfWeekId(dayOfWeekId);

		String query = "\n select tk.Ngay as [Ngày],kbh.TEN as [Kênh Bán Hàng], tt.TEN as [Tỉnh Thành],npp.TEN as [Nhà Phân Phối],  " +
		"\n  ddkd.ten as [Trình Dược Viên], kh.SMARTID as [Mã KH], kh.TEN as [Khách Hàng], sp.ma as [Mã SP], " +
		"\n  sp.ten as [Sản Phẩm], cl.ten as [Chủng loại],nh.ten as [Nhãn Hàng], " +
		"\n  CAST(tk.SoLuong AS FLOAT) as [Tồn Kho] " +
		"\n  from TONKHO_KHACHHANG tk   " +
		"\n inner join sanpham sp on sp.pk_Seq = tk.sanpham_fk	" +
		"\n  inner join khachhang kh on tk.kh_fk = kh.pk_seq     " +
		"\n  left join" +
		"\n  ( " +
		"\n     select khachhang_fk,max(ddkd_fk) ddkd_fk" +
		"\n     from khachhang_tuyenbh kht " +
		"\n     inner join tuyenbanhang tbh on kht.tbh_fk = tbh.pk_seq" +
		"\n     group by khachHang_fk " +
		"\n  )ddkd_kh on ddkd_kh.khachhang_fk = kh.pk_seq	" +
		"\n left join daidienkinhdoanh ddkd on ddkd.pk_Seq = ddkd_kh.ddkd_fk" +
		"\n left join loaicuahang lch on kh.lch_fk = lch.pk_seq     " + 
		"\n left join nhaphanphoi npp on tk.npp_fk = npp.pk_seq " +
		"\n  left join KENHBANHANG kbh on kbh.PK_SEQ = kh.KBH_FK " +
		"\n  left join Chungloai cl on cl.PK_SEQ = sp.chungloai_FK " +
		"\n  left join nhanhang nh on nh.PK_SEQ = sp.nhanhang_FK " +
		"\n  left join TINHTHANH tt on tt.PK_SEQ = kh.TINHTHANH_FK " +
		"\n where 1 = 1 ";
		 
		if(nppId.trim().length() > 0)
			query += " and tk.npp_fk = '" + nppId + "' ";

		geso.dms.center.util.Utility ut = new geso.dms.center.util.Utility();	
		query +=  "\n and tk.npp_fk in "+ ut.quyen_npp(obj.getUserId())+ " ";
		if (ten.length()>0)
		{ 
			query = query + "\n and ( upper(dbo.ftBoDau(kh.ten)) like upper(N'%" + util.replaceAEIOU(ten) + "%') or kh.smartid like upper(N'%" + ten.trim()+ "%')) ";			
		}

		if (kbhId.length()>0){
			query = query + "\n and kh.kbh_fk ='" + kbhId + "'";	
		}
		
		if (loaiKH.length()>0){
			query = query + "\n and kh.lch_fk='" + lchId + "'";			
		}
		
		if(diachi.length()>0)
		{
			query+="\n and (upper(dbo.ftBoDau(kh.diachi)) like (N'%" + util.replaceAEIOU(diachi) + "%') )  ";
		}

		if(maFAST.length()>0)
		{
			query+= "\n and kh.maFAST like '%"+maFAST+"%' ";
		}

		if(ddkdId.length()>0 && dayOfWeekId.length()>0)
		{
			query+= "\n and kh.pk_Seq in (select khachhang_fk from KHACHHANG_TUYENBH where tbh_fk in (select PK_SEQ from tuyenbanhang where ddkd_Fk='"+ddkdId+"' and ngayId = "+dayOfWeekId+")) ";
		}
		else if(ddkdId.length()>0 )
		{
			query+= "\n and kh.pk_Seq in (select khachhang_fk from KHACHHANG_TUYENBH where tbh_fk in (select PK_SEQ from tuyenbanhang where ddkd_Fk='"+ddkdId+"')) ";
		}
		else if(dayOfWeekId.length()>0)
		{
			query+= "\n and kh.pk_Seq in (select KHACHHANG_FK from KHACHHANG_TUYENBH where tbh_fk in (select pk_seq from tuyenbanhang where ngayId = "+dayOfWeekId+") ) ";
		}

		if (tungay.length() > 0)
		{
			query = query + "\n and tk.Ngay>='" + tungay + "'";
		}

		if (denngay.length() > 0)
		{
			query = query + "\n and tk.Ngay<='" + denngay + "'";
		}
		if (vung.length() > 0)
		{
			query = query + "\n and v.pk_seq='" + vung + "'";
		}
		if (mien.length() > 0)
		{
			query = query + "\n and tt.pk_seq='" + mien + "'";
		}
		
		query +="\n order by tk.ngay  desc,tt.TEN  desc,npp.TEN  desc";
		System.out.println("Query tìm kiếm: "+query);
		return query;
	}	

	private String getSearchQuery_DoiThu(HttpServletRequest request, IKhachhangTTList obj)
	{		
		Utility util = new Utility();
		String ten = util.antiSQLInspection(request.getParameter("khTen"));
		if ( ten == null)
			ten = "";
		obj.setTen(ten);

		String nppId = util.antiSQLInspection(request.getParameter("nppId"));
		if ( nppId == null)
			nppId = "";
		obj.setNppId(nppId);

		String hchId = util.antiSQLInspection(request.getParameter("hchTen"));
		if (hchId == null)
			hchId = "";    	
		obj.setHchId(hchId);

		String kbhId = util.antiSQLInspection(request.getParameter("kbhTen"));
		if (kbhId == null)
			kbhId = "";    	
		obj.setKbhId(kbhId);

		String vtchId = util.antiSQLInspection(request.getParameter("vtchTen"));
		if (vtchId == null)
			vtchId = "";    	
		obj.setVtId(vtchId);

		String lchId = util.antiSQLInspection(request.getParameter("lchTen"));
		if (lchId == null)
			lchId = "";    	
		obj.setLchId(lchId);


		String diadiemId = util.antiSQLInspection(request.getParameter("diadiemId"));
		if (diadiemId == null)
			diadiemId = "";    	
		obj.setDiadiemId(diadiemId);


		String xuatkhau = util.antiSQLInspection(request.getParameter("xuatkhau"));
		if (xuatkhau == null)
			xuatkhau = "0";    	
		obj.setXuatkhau(xuatkhau);

		String diachi = util.antiSQLInspection(request.getParameter("diachi"));
		if (diachi == null)
			diachi = "";    	
		obj.setDiachi(diachi.trim());


		String maFAST = util.antiSQLInspection(request.getParameter("maFAST"));
		if (maFAST == null)
			maFAST = "";    	
		obj.setMaFAST(maFAST);


		String ddkdId = util.antiSQLInspection(request.getParameter("ddkdId"));
		if (ddkdId == null)
			ddkdId = "";    	
		obj.setDdkdId(ddkdId);


		String tbhId = util.antiSQLInspection(request.getParameter("tbhId"));
		if (tbhId == null)
			tbhId = "";    	
		obj.setTbhId(tbhId);

		String trangthai = util.antiSQLInspection(request.getParameter("trangthai")); 	
		if (trangthai == null)
			trangthai = "";    		
		obj.setTrangthai(trangthai);

		String tungay = util.antiSQLInspection(request.getParameter("tungay")); 	
		if (tungay == null)
			tungay = "";    		
		obj.setTungay(tungay);

		String denngay = util.antiSQLInspection(request.getParameter("denngay")); 	
		if (denngay == null)
			denngay = "";    		
		obj.setDenngay(denngay);
		System.out.println("[den ngay] : " + obj.getDenngay());

		String loaiKH = util.antiSQLInspection(request.getParameter("loaikh")); 	
		if (loaiKH == null)
			loaiKH = "";    		
		obj.setloaiKH(loaiKH);

		String hopdong = util.antiSQLInspection(request.getParameter("hopdong")); 	
		if (hopdong == null)
			hopdong = "";    		
		obj.setHopdong(hopdong);
		String vung = util.antiSQLInspection(request.getParameter("vungid"));
		if ( vung == null)
			vung = "";
		obj.setVungid(vung);
		String mien = util.antiSQLInspection(request.getParameter("mienid"));
		if ( mien == null)
			mien = "";
		obj.setMienid(mien);

		String dayOfWeekId = util.antiSQLInspection(request.getParameter("dayOfWeekId"));
		if ( dayOfWeekId == null)
			dayOfWeekId = "";
		obj.setDayOfWeekId(dayOfWeekId);

		String query = 
			 "\n 			  select tk.Ngay as [Ngày],kbh.TEN as [Kênh Bán Hàng],v.ten as [Miền],kv.ten as [Khu vực],tt.TEN as [Tỉnh Thành]  " + 
			 "\n 				,'' as [Địa Bàn]	,ddkd.ten as [Trình Dược Viên]	,kh.smartId as [Mã KH], kh.TEN as [Khách Hàng],tk.SANPHAMTEN as [Sản Phẩm] ,CAST(tk.SoLuong AS FLOAT) as [Tồn Kho], " +
			 "\n				convert(float,isnull(tk.gia,0)) [Giá], isnull(tk.ctkm,'') [CTKM] " + 
			 "\n 			  from KHACHHANG_KHODOITHU tk   " +
			 "\n 			  inner join khachhang kh on tk.kh_fk = kh.pk_seq     " +
			 "\n	  		  left join" +
			 "\n	  		  (" +
			 "\n				 select khachhang_fk,max(ddkd_fk) ddkd_fk" +
			 "\n				 from khachhang_tuyenbh kht " +
			 "\n				 inner join tuyenbanhang tbh on kht.tbh_fk = tbh.pk_seq" +
			 "\n				 group by khachHang_fk " +
			 "\n	  		  )ddkd_kh on ddkd_kh.khachhang_fk = kh.pk_seq	" + 
			 "\n			  left join daidienkinhdoanh ddkd on ddkd.pk_Seq = ddkd_kh.ddkd_fk" +
			 "\n			  inner join nhaphanphoi npp on npp.pk_seq = kh.npp_fk	" +
			 "\n 			  inner join loaicuahang lch on kh.lch_fk = lch.pk_seq      " +
			 "\n 			  inner join KENHBANHANG kbh on kbh.PK_SEQ = kh.KBH_FK  " + 
			 "\n 			  left join TINHTHANH tt on tt.PK_SEQ = kh.TINHTHANH_FK " +
			 "\n		      left join khuvuc kv on kv.pk_seq = npp.khuvuc_fk  " + 
			 "\n 			  left join VUNG v on v.PK_SEQ = kv.VUNG_FK  " +
			 "\n			  where 1=1 	";		
		if(nppId.trim().length() > 0)
			query += " and tk.npp_fk = '" + nppId + "' ";

		geso.dms.center.util.Utility ut = new geso.dms.center.util.Utility();	
		if (ten.length()>0)
		{ 
			query = query + "\n and ( upper(dbo.ftBoDau(kh.ten)) like upper(N'%" + util.replaceAEIOU(ten) + "%') or kh.smartid like upper(N'%" + ten.trim()+ "%')) ";			
		}

		if (kbhId.length()>0){
			query = query + "\n and kh.kbh_fk ='" + kbhId + "'";	
		}
		if (loaiKH.length()>0){
			query = query + "\n and kh.lch_fk='" + lchId + "'";			
		}
		if(diachi.length()>0)
		{
			query+="\n and (upper(dbo.ftBoDau(kh.diachi)) like (N'%" + util.replaceAEIOU(diachi) + "%') )  ";
		}

		if(maFAST.length()>0)
		{
			query+= "\n and kh.maFAST like '%"+maFAST+"%' ";
		}

		if(ddkdId.length()>0 && dayOfWeekId.length()>0)
		{
			query+= "\n and kh.pk_Seq in (select khachhang_fk from KHACHHANG_TUYENBH where tbh_fk in (select PK_SEQ from tuyenbanhang where ddkd_Fk='"+ddkdId+"' and ngayId = "+dayOfWeekId+")) ";
		}
		else if(ddkdId.length()>0 )
		{
			query+= "\n and kh.pk_Seq in (select khachhang_fk from KHACHHANG_TUYENBH where tbh_fk in (select PK_SEQ from tuyenbanhang where ddkd_Fk='"+ddkdId+"')) ";
		}
		else if(dayOfWeekId.length()>0)
		{
			query+= "\n and kh.pk_Seq in (select KHACHHANG_FK from KHACHHANG_TUYENBH where tbh_fk in (select pk_seq from tuyenbanhang where ngayId = "+dayOfWeekId+") ) ";
		}

		if (tungay.length() > 0)
		{
			query = query + "\n and tk.Ngay>='" + tungay + "'";
		}

		if (denngay.length() > 0)
		{
			query = query + "\n and tk.Ngay<='" + denngay + "'";
		}
		if (vung.length() > 0)
		{
			query = query + "\n and v.pk_seq='" + vung + "'";
		}
		if (mien.length() > 0)
		{
			query = query + "\n and tt.pk_seq='" + mien + "'";
		}
		query +="\n order by tk.ngay  desc,v.TEN  desc,tt.TEN desc";




		System.out.println("Query KHO_DOITHU: " + query + "\n");


		return query;
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	private void TaoBaoCao(Workbook workbook,IKhachhangTTList obj,String query,int sheetnum,String nguoitao )throws Exception
	{
		try{


			Worksheets worksheets = workbook.getWorksheets();
			if(sheetnum == 1)
				workbook.getWorksheets().addSheet();
			Worksheet worksheet = worksheets.getSheet(sheetnum);
			if(sheetnum == 0)
				worksheet.setName("KhachHang");
			else
				worksheet.setName("DoiThu");

			Cells cells = worksheet.getCells();
			cells.setRowHeight(0, 50.0f);
			Cell cell = cells.getCell("A1");
			if(sheetnum == 0)
				ReportAPI.getCellStyle(cell, Color.RED, true, 16,"TỒN KHO KHÁCH HÀNG");
			else
				ReportAPI.getCellStyle(cell, Color.RED, true, 16,"TỒN KHO ĐỐI THỦ");

			cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Ngày tạo : "+ getDateTime());
			cell = cells.getCell("A4");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10,"Người tạo : " +nguoitao );

			worksheet.setGridlinesVisible(false);
			dbutils db = new dbutils();
			ResultSet rs = db.get(query);

			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();

			int countRow = 8;

			for( int i =1 ; i <=socottrongSql ; i ++  )
			{
				cell = cells.getCell(countRow,i-1 );cell.setValue(rsmd.getColumnName(i));
				ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);	

			}
			countRow ++;


			while(rs.next())
			{
				for(int i =1;i <=socottrongSql ; i ++)
				{
					cell = cells.getCell(countRow,i-1 );
					if(rsmd.getColumnType(i) == Types.DOUBLE )
					{
						cell.setValue(rs.getDouble(i));
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
					}
					else
					{
						cell.setValue(rs.getString(i));
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					}
				}
				++countRow;
			}

			if(rs!=null)rs.close();
			if(db!=null){
				db.shutDown();
			}


		}catch(Exception ex){

			System.out.println("Errrorr : "+ex.toString());
			throw new Exception("Qua trinh dien du lieu vao file Excel va tao PivotTable bi loi.!!!");
		}
	}

	private void ExportToExcel(HttpServletResponse response,OutputStream out,IKhachhangTTList obj,HttpServletRequest request,String nguoitao )throws Exception
	{
		try{ 		
			response.setContentType("application/xlsm");
    		response.setHeader("Content-Disposition", "attachment; filename=BCTonKhoKhachHang.xlsm");
    		String query = getSearchQuery(request, (IKhachhangTTList) obj);

			Workbook workbook = new Workbook();
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			TaoBaoCao(workbook,obj,query,0,nguoitao);	
			query = getSearchQuery_DoiThu(request, (IKhachhangTTList) obj);
			TaoBaoCao(workbook,obj,query,1,nguoitao);	
			workbook.save(out);	

		}catch(Exception ex){
			throw new Exception(ex.getMessage());
		}

	}


}
