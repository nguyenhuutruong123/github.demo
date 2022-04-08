package geso.dms.center.servlets.reports;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.distributor.util.Utility;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class ThucDatVaChiTieuSKUIn extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    
    public ThucDatVaChiTieuSKUIn() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		String querystring = request.getQueryString();
		
		Utility util = new Utility();
		obj.setuserId(util.getUserId(querystring));
		obj.setuserTen((String) session.getAttribute("userTen"));
		obj.setnppId(util.getIdNhapp(obj.getuserId()));
		
		obj.init();
		obj.settype("1");
		session.setAttribute("obj", obj);		
		session.setAttribute("userId", obj.getuserId());
		session.setAttribute("userTen", obj.getuserTen());
		//String nextJSP = "/AHF/pages/Center/KPITT.jsp";
		String nextJSP = "/AHF/pages/Center/ThucDatVaChiTieuSKUIn.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		OutputStream out = response.getOutputStream();
		Utility util = new Utility();
		
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		
		obj.settungay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays"))));
		obj.setdenngay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays"))));
		
		
		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");
		obj.setkenhId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId"))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")):""));
		
		obj.setvungId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")):""));
			
		obj.setkhuvucId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId"))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")):""));
		
		obj.setgsbhId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("gsbhs"))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("gsbhs")):""));
		
		obj.setnppId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")):""));
		
		obj.setdvkdId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId"))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")):""));
		
		obj.setDdkd(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdId"))!=null?	
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdId")):""));
		
		String nspId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nspId"));
		if(nspId == null)
			nspId = "";
		obj.setNspId(nspId);
		
		System.out.println("Tu thang la :"+geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tuthang")));
		String tuthang=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tuthang").length()< 2 ? ("0"+request.getParameter("tuthang")) :request.getParameter("tuthang")) ;
		
		String toithang=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denthang").length()< 2 ? ("0"+request.getParameter("denthang")) :request.getParameter("denthang")) ;
		obj.setFromMonth(tuthang);
		
		obj.setToMonth(toithang);
			obj.setToYear(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dennam")));
			obj.setFromYear(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tunam")));
		
		 obj.settype(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("typeid")));
		
		System.out.println(obj.gettype());
		 //truong hop bao cao thang type la 1.
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")!=null? util.antiSQLInspection(request.getParameter("action"))):"";
		String nextJSP = "/AHF/pages/Center/KPITT.jsp";
		
		if(action.equals("Taomoi")){
			try{
		
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=ChiTieuSKUIn.xlsm");
		        if(!CreatePivotTable(out,obj)){
		        
		        	
		        	
		        }
			}catch(Exception ex){
				obj.setMsg(ex.getMessage());
			}
		}else{			
			
		}
		obj.init();
		session.setAttribute("obj", obj);	
		response.sendRedirect(nextJSP);
	}
	
	private boolean CreatePivotTable(OutputStream out, IStockintransit obj) throws Exception 
	{
		String chuoi = "";
		
		if(obj.gettype().equals("1"))
	    {
			chuoi = getServletContext().getInitParameter("path") + "\\ChiTieuSKUIn_Thang.xlsm";
	    }
	    else
	    {
	    	if(obj.gettype().equals("0"))
	    	{
	    		chuoi = getServletContext().getInitParameter("path") + "\\ChiTieuSKUIn.xlsm";
	    	}
	    	else
	    	{
	    		chuoi = getServletContext().getInitParameter("path") + "\\ChiTieuSKUIn_Tuan.xlsm";
	    	}	
	    }
		
		FileInputStream fstream = new FileInputStream(chuoi);
			
		Workbook workbook = new Workbook();
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateStaticHeader(workbook, obj);

		boolean isFill = CreateStaticData(workbook, obj);
		
		if (!isFill){
			fstream.close();
			return false;
		}else {
			workbook.save(out);
			fstream.close();
			return true;
		}
	}

	private void CreateStaticHeader(Workbook workbook, IStockintransit obj) throws Exception 
	{
		try{
			
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");

			Cells cells = worksheet.getCells();

			cells.setRowHeight(0, 20.0f);
			Cell cell = cells.getCell("A1");
		    cell.setValue("THỰC ĐẠT VÀ CHỈ TIÊU SKU IN");   	
		    
		    cells.setRowHeight(2, 18.0f);
		    cell = cells.getCell("A3"); 
		    getCellStyle(workbook,"A3",Color.NAVY,true,10);
		    if(obj.gettype().equals("1"))
		    {
		    	 cell.setValue("Từ tháng: " +obj.getFromYear()+"-"+ obj.getFromMonth());  
		    }
		    else
		    {
		    	//if(obj.gettype().equals("0"))
		    		cell.setValue("Từ ngày: " + obj.gettungay());	
		    	//else
		    		//cell.setValue("Từ tuần: " + obj.gettungay());	
		    }
		    
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("B3"); 
		    getCellStyle(workbook,"B3",Color.NAVY,true,9);
		    
		   if(obj.gettype().equals("1"))
		   {
				cell.setValue("Đến tháng: " + obj.getToYear() + "-" + obj.getToMonth());
		   }
		   else
		   {
			   //if(obj.gettype().equals("0"))
				   cell.setValue("Đến ngày: " + obj.getdenngay());  
			  // else
				   //cell.setValue("Đến tuần: " + obj.getdenngay());  
		   }
		    cells.setRowHeight(4, 18.0f);
		    cell = cells.getCell("A4");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
		    
		    cells.setRowHeight(5, 18.0f);
		    cell = cells.getCell("A6");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());
					
		 	String str = "";
		    if(obj.gettype().equals("1"))
		    {
		    	str = "Thang";
		    }
		    else
		    {
		    	if(obj.gettype().equals("0"))
		    		str = "Ngay";
		    	else
		    		str = "Tuan";
		    }
			
		    cell = cells.getCell("DA1"); 	cell.setValue("BM");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DB1"); 	cell.setValue("Vung");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DC1"); 	cell.setValue("ASM");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DD1"); 	cell.setValue("KhuVuc");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DE1"); 	cell.setValue("GiamSatBanHang");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DF1"); 	cell.setValue("MaNhaPhanPhoi");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DG1"); 	cell.setValue("TenNhaPhanPhoi");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DH1"); 	cell.setValue("MaSanPham");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DI1"); 	cell.setValue("TenSanPham");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DJ1"); 	cell.setValue(str);  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DK1"); 	cell.setValue("ChiTieu"); ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DL1"); 	cell.setValue("ThucDat");   ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DM1"); 	cell.setValue("%");	   ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DN1"); 	cell.setValue("Nam");	   ReportAPI.setCellHeader(cell);

		}catch(Exception ex){
			throw new Exception("Bao cao bi loi khi khoi tao");
		}
		
	      
	}
	
	private boolean CreateStaticData(Workbook workbook, IStockintransit obj) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
		
	    String thang = obj.getMonth();
	    if(thang.trim().length() < 2)
	    	thang = "0" + thang;
	    
	    String query = "";
	    if(obj.gettype().equals("1"))
	    {
	    	query = getQuery_Thang(obj);
	    }
	    else
	    {
	    	if(obj.gettype().equals("0"))
	    	{
	    		query = getQuery_Ngay(obj);
	    	}
	    	else
	    	{
	    		query = getQuery_Tuan(obj);
	    	}
	    }
		System.out.println("1.Chi tieu SKU: " + query);
		ResultSet rs = db.get(query);
		
		int i = 2;
		if(rs!=null)
		{
			try 
			{
				for(int j = 0; j < 15; j++)
					cells.setColumnWidth(i, 15.0f);
				
				Cell cell = null;
				while(rs.next())
				{
					String BM = rs.getString("BM");
					String vung = rs.getString("vung");
					String ASM = rs.getString("ASM");
					String khuvuc = rs.getString("khuvuc");
					String gsbh = rs.getString("GSBH");
					String maNPP = rs.getString("ma");
					String tenNPP = rs.getString("ten");
					String maSP = rs.getString("SKUCODE");
					String tenSP = rs.getString("spTen");
					int chitieu = rs.getInt("chitieu");										
					int thucdat = rs.getInt("thucdat");
					float phantram = rs.getFloat("phantram");	
            		String ngay = rs.getString("NgayThang");
					String nam = rs.getString("nam");
            		
					cell = cells.getCell("DA" + Integer.toString(i)); 	cell.setValue(BM);
					cell = cells.getCell("DB" + Integer.toString(i)); 	cell.setValue(vung);
					cell = cells.getCell("DC" + Integer.toString(i)); 	cell.setValue(ASM);
					cell = cells.getCell("DD" + Integer.toString(i)); 	cell.setValue(khuvuc);
					cell = cells.getCell("DE" + Integer.toString(i)); 	cell.setValue(gsbh);
					cell = cells.getCell("DF" + Integer.toString(i)); 	cell.setValue(maNPP);				
					cell = cells.getCell("DG" + Integer.toString(i)); 	cell.setValue(tenNPP);
					cell = cells.getCell("DH" + Integer.toString(i)); 	cell.setValue(maSP);
					cell = cells.getCell("DI" + Integer.toString(i)); 	cell.setValue(tenSP);
					cell = cells.getCell("DJ" + Integer.toString(i)); 	cell.setValue(ngay);
					cell = cells.getCell("DK" + Integer.toString(i)); 	cell.setValue(chitieu); 
					cell = cells.getCell("DL" + Integer.toString(i)); 	cell.setValue(thucdat); 
					cell = cells.getCell("DM" + Integer.toString(i)); 	cell.setValue(phantram); 
					cell = cells.getCell("DN" + Integer.toString(i)); 	cell.setValue(nam);
					
					i++;
				}
				if(rs!=null)
					rs.close();
				if(db != null) 
					db.shutDown();
				if(i==2){
					throw new Exception("Khong co bao cao trong thoi gian nay...");
				}
			
			} 
			catch (Exception e) {}
		} 
		else 
		{
			if(db != null) db.shutDown();
			return false;
		}
		
		if(db != null) 
			db.shutDown();
		return true;
	}	
	
	private String getQuery_Ngay(IStockintransit obj)
	{
		String fromMonth = obj.gettungay().substring(5, 7);
		String fromYear = obj.gettungay().substring(0, 4);
		String toMonth = obj.getdenngay().substring(5, 7);
		String toYear = obj.getdenngay().substring(0, 4);
		
		String query = "select CHITIEU.*, isnull(SKUIN.soluongNhap, 0) as THUCDAT, cast( (isnull(SKUIN.soluongNhap, 0) * 100 / CHITIEU.chitieu) as numeric(18, 0) )  as PhanTram, " +
						"isnull(GIAMSATBANHANG.TEN, 'na') as GSBH, isnull(ASM.ten, 'na') as ASM, isnull(BM.ten, 'na') as BM, " +
						"VUNG.TEN as vung, KHUVUC.ten as khuvuc, SKUIN.NgayThang as NgayThang " +
				"from " +
					"( " +
						"select a.pk_seq as nppId, a.ma, a.ten, a.diachi, a.khuvuc_fk,  " +
							"c.chitieu, d.ma as SKUCode, d.ten as spTen, d.pk_seq as spId, b.thang, b.nam " +
						"from nhaphanphoi a inner join CHITIEUSKUIN b on a.pk_seq = b.nhapp_fk  inner join CHITIEUSKUIN_SKU c on c.chitieuskuin_fk = b.pk_seq " +
						"inner join sanpham d on c.sku = d.pk_seq " +
						"where '" + fromMonth + "' <= b.thang and b.thang <= '" +toMonth + "' and '" + fromYear + "' <= b.nam and b.nam <= '" + toYear + "' ";
				
						if(obj.getnppId().length() > 0)		
							query +=	" and a.pk_seq in (" + obj.getnppId() + ") ";
						
				query += ") CHITIEU " +
				"inner join " +
					"( " +
						"select a.npp_fk, b.sanpham_fk as SKUCode, a.ngaynhan as NgayThang, substring(a.ngaynhan, 0, 5) as nam, substring(ngaynhan, 6, 2) as thang, " +
						"	(sum(b.soluong) * d.soluong1) / d.soluong2  as soluongNhap   " +
						"from nhaphang a inner join nhaphang_sp b on a.pk_seq = b.nhaphang_fk " +
										"inner join sanpham c on b.sanpham_fk = c.ma inner join quycach d on c.pk_seq = d.sanpham_fk " +
						"where '" + obj.gettungay() + "' <= a.ngaynhan and a.ngaynhan <= '" + obj.getdenngay() + "' and a.trangthai = '1'   ";
				
				if(obj.getnppId().length() > 0)		
					query +=	" and a.pk_seq in (" + obj.getnppId() + ") ";
				
				if(obj.getNspId().length() > 0)
					query += " and b.sanpham_fk in " +
							"( select ma from sanpham " +
							" where pk_seq in (select sp_fk from nhomsanpham_sanpham where nsp_fk = '" + obj.getNspId() + "' ) ) ";
				
				query += " group by a.npp_fk, b.sanpham_fk, substring(a.ngaynhan, 0, 5), substring(ngaynhan, 6, 2), a.ngaynhan, d.soluong1, d.soluong2 " +
					") SKUIN on CHITIEU.nppId = SKUIN.npp_fk and CHITIEU.SKUCode = SKUIN.SKUCode  and CHITIEU.nam = SKUIN.nam and CHITIEU.thang = SKUIN.thang " +
				"inner join nhapp_giamsatbh on nhapp_giamsatbh.npp_fk = CHITIEU.nppId " +
				"inner join  giamsatbanhang on nhapp_giamsatbh.gsbh_fk = giamsatbanhang.pk_seq " +
				"inner join KHUVUC on CHITIEU.khuvuc_fk = KHUVUC.pk_seq " +
				"left join ASM_KHUVUC on ASM_KHUVUC.khuvuc_fk = KHUVUC.pk_seq " +
				"left join ASM on ASM_KHUVUC.asm_fk = ASM.pk_seq " +
				"inner join VUNG on khuvuc.vung_fk = VUNG.pk_seq " +
				"left join BM_CHINHANH on BM_CHINHANH.VUNG_FK = VUNG.pk_seq " +
				"left join BM on BM_CHINHANH.BM_fk = BM.PK_SEQ " +
				"order by CHITIEU.nam desc, CHITIEU.thang asc, SKUIN.NGAYTHANG asc, chitieu.nppId asc";
				

			return query;
	}

	private String getQuery_Thang(IStockintransit obj)
	{
		String query = "select CHITIEU.*, isnull(SKUIN.soluongNhap, 0) as THUCDAT, cast( (isnull(SKUIN.soluongNhap, 0) * 100 / CHITIEU.chitieu) as numeric(18, 0) )  as PhanTram, " +
						"isnull(GIAMSATBANHANG.TEN, 'na') as GSBH, isnull(ASM.ten, 'na') as ASM, isnull(BM.ten, 'na') as BM, " +
						"VUNG.TEN as vung, KHUVUC.ten as khuvuc " +
				"from " +
					"( " +
						"select a.pk_seq as nppId, a.ma, a.ten, a.diachi, a.khuvuc_fk,  " +
							"c.chitieu, d.ma as SKUCode, d.ten as spTen, d.pk_seq as spId, b.thang as NgayThang, b.nam " +
						"from nhaphanphoi a inner join CHITIEUSKUIN b on a.pk_seq = b.nhapp_fk  inner join CHITIEUSKUIN_SKU c on c.chitieuskuin_fk = b.pk_seq " +
						"inner join sanpham d on c.sku = d.pk_seq " +
						"where '" + obj.getFromMonth() + "' <= b.thang and b.thang <= '" + obj.getToMonth() + "' and '" + obj.getFromYear() + "' <= b.nam and b.nam <= '" + obj.getFromYear() + "' ";
		
						if(obj.getnppId().length() > 0)		
							query +=	" and a.pk_seq in (" + obj.getnppId() + ") ";
						
			query += ") CHITIEU " +
				"inner join " +
					"( " +
						"select a.npp_fk, b.sanpham_fk as SKUCode, (sum(b.soluong) * d.soluong1) / d.soluong2  as soluongNhap  " +
						"from nhaphang a inner join nhaphang_sp b on a.pk_seq = b.nhaphang_fk " +
										"inner join sanpham c on b.sanpham_fk = c.ma inner join quycach d on c.pk_seq = d.sanpham_fk " +
						"where '" + obj.getFromYear() + "' <= substring(a.ngaynhan, 0, 5) and substring(a.ngaynhan, 0, 5) <= '" + obj.getToYear() + "' and " +
								" '" + obj.getFromMonth() + "' <= substring(ngaynhan, 6, 2) and substring(ngaynhan, 6, 2) <= '" + obj.getToMonth() + "' and a.trangthai = '1'   ";
				
				if(obj.getnppId().length() > 0)		
					query +=	" and a.pk_seq in (" + obj.getnppId() + ") ";
				
				if(obj.getNspId().length() > 0)
					query += " and b.sanpham_fk in " +
							"( select ma from sanpham " +
							" where pk_seq in (select sp_fk from nhomsanpham_sanpham where nsp_fk = '" + obj.getNspId() + "' ) ) ";
				
				query += " group by a.npp_fk, b.sanpham_fk, d.soluong1, d.soluong2 " +
					") SKUIN on CHITIEU.nppId = SKUIN.npp_fk and CHITIEU.SKUCode = SKUIN.SKUCode " +
				"inner join nhapp_giamsatbh on nhapp_giamsatbh.npp_fk = CHITIEU.nppId " +
				"inner join  giamsatbanhang on nhapp_giamsatbh.gsbh_fk = giamsatbanhang.pk_seq " +
				"inner join KHUVUC on CHITIEU.khuvuc_fk = KHUVUC.pk_seq " +
				"left join ASM_KHUVUC on ASM_KHUVUC.khuvuc_fk = KHUVUC.pk_seq " +
				"left join ASM on ASM_KHUVUC.asm_fk = ASM.pk_seq " +
				"inner join VUNG on khuvuc.vung_fk = VUNG.pk_seq " +
				"left join BM_CHINHANH on BM_CHINHANH.VUNG_FK = VUNG.pk_seq " +
				"left join BM on BM_CHINHANH.BM_fk = BM.PK_SEQ " +
				"order by CHITIEU.nam desc, CHITIEU.NgayThang asc, chitieu.nppId asc";


		return query;
	}
	
	private String getQuery_Tuan(IStockintransit obj)
	{
		String fromMonth = obj.gettungay().substring(5, 7);
		String fromYear = obj.gettungay().substring(0, 4);
		String toMonth = obj.getdenngay().substring(5, 7);
		String toYear = obj.getdenngay().substring(0, 4);
		
		String query = "select CHITIEU.*, isnull(SKUIN.soluongNhap, 0) as THUCDAT, cast( (isnull(SKUIN.soluongNhap, 0) * 100 / CHITIEU.chitieu) as numeric(18, 0) )  as PhanTram, " +
						"isnull(GIAMSATBANHANG.TEN, 'na') as GSBH, isnull(ASM.ten, 'na') as ASM, isnull(BM.ten, 'na') as BM, " +
						"VUNG.TEN as vung, KHUVUC.ten as khuvuc, SKUIN.NgayThang as NgayThang " +
				"from " +
					"( " +
						"select a.pk_seq as nppId, a.ma, a.ten, a.diachi, a.khuvuc_fk,  " +
							"c.chitieu, d.ma as SKUCode, d.ten as spTen, d.pk_seq as spId, b.thang, b.nam " +
						"from nhaphanphoi a inner join CHITIEUSKUIN b on a.pk_seq = b.nhapp_fk  inner join CHITIEUSKUIN_SKU c on c.chitieuskuin_fk = b.pk_seq " +
						"inner join sanpham d on c.sku = d.pk_seq " +
						"where '" + fromMonth + "' <= b.thang and b.thang <= '" +toMonth + "' and '" + fromYear + "' <= b.nam and b.nam <= '" + toYear + "' ";
				
						if(obj.getnppId().length() > 0)		
							query +=	" and a.pk_seq in (" + obj.getnppId() + ") ";
						
				query += ") CHITIEU " +
				"inner join " +
					"( " +
						"select a.npp_fk, b.sanpham_fk as SKUCode, DATEPART(wk, a.ngaynhan) as NgayThang, substring(a.ngaynhan, 0, 5) as nam, substring(ngaynhan, 6, 2) as thang, " +
						"	(sum(b.soluong) * d.soluong1) / d.soluong2  as soluongNhap   " +
						"from nhaphang a inner join nhaphang_sp b on a.pk_seq = b.nhaphang_fk " +
										"inner join sanpham c on b.sanpham_fk = c.ma inner join quycach d on c.pk_seq = d.sanpham_fk " +
						"where '" + obj.gettungay() + "' <= a.ngaynhan and a.ngaynhan <= '" + obj.getdenngay() + "' and a.trangthai = '1'   ";
				
				if(obj.getnppId().length() > 0)		
					query +=	" and a.pk_seq in (" + obj.getnppId() + ") ";
				
				if(obj.getNspId().length() > 0)
					query += " and b.sanpham_fk in " +
							"( select ma from sanpham " +
							" where pk_seq in (select sp_fk from nhomsanpham_sanpham where nsp_fk = '" + obj.getNspId() + "' ) ) ";
				
				query += " group by a.npp_fk, b.sanpham_fk, substring(a.ngaynhan, 0, 5), substring(ngaynhan, 6, 2), DATEPART(wk, a.ngaynhan), d.soluong1, d.soluong2 " +
					") SKUIN on CHITIEU.nppId = SKUIN.npp_fk and CHITIEU.SKUCode = SKUIN.SKUCode  and CHITIEU.nam = SKUIN.nam and CHITIEU.thang = SKUIN.thang " +
				"inner join nhapp_giamsatbh on nhapp_giamsatbh.npp_fk = CHITIEU.nppId " +
				"inner join  giamsatbanhang on nhapp_giamsatbh.gsbh_fk = giamsatbanhang.pk_seq " +
				"inner join KHUVUC on CHITIEU.khuvuc_fk = KHUVUC.pk_seq " +
				"left join ASM_KHUVUC on ASM_KHUVUC.khuvuc_fk = KHUVUC.pk_seq " +
				"left join ASM on ASM_KHUVUC.asm_fk = ASM.pk_seq " +
				"inner join VUNG on khuvuc.vung_fk = VUNG.pk_seq " +
				"left join BM_CHINHANH on BM_CHINHANH.VUNG_FK = VUNG.pk_seq " +
				"left join BM on BM_CHINHANH.BM_fk = BM.PK_SEQ " +
				"order by CHITIEU.nam desc, SKUIN.thang asc, SKUIN.NGAYTHANG asc, chitieu.nppId asc";
				

			return query;
	}

	
	private void getCellStyle(Workbook workbook, String a, Color mau, Boolean dam, int size)
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
		Style style;
		Cell cell = cells.getCell(a); 
		 style = cell.getStyle();
	        Font font1 = new Font();
	        font1.setColor(mau);
	        font1.setBold(dam);
	        font1.setSize(size);
	        style.setFont(font1);
	        cell.setStyle(style);
	}

}
