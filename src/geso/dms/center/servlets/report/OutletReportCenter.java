package geso.dms.center.servlets.report;

import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;

import geso.dms.center.beans.stockintransit.IStockintransit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;


public class OutletReportCenter extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	
	String query="";
   
    public OutletReportCenter() {
        super();
        }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		 Utility util = new Utility();
		 String querystring = request.getQueryString();
		 String  userId = util.getUserId(querystring);
		 IStockintransit obj = new Stockintransit();
	    //obj.init();	    
	    obj.setuserId(userId);
	    obj.init();	    
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Center/OutletReportCenter.jsp";
		response.sendRedirect(nextJSP);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    HttpSession session = request.getSession();
		String userTen = (String)session.getAttribute("userTen");
		String userId = (String)session.getAttribute("userId");

	    IStockintransit obj = new Stockintransit();
		try{
			obj.settungay(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays")));
			obj.setdenngay(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays")));
			obj.setkenhId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhs")));
			obj.setvungId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungs")));
			obj.setkhuvucId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucs")));
			obj.setgsbhId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("gsbhs")));
			obj.setnppId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("npps")));
			obj.setDdkd(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkds")));
			obj.setNhomkhId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nkh")));
			String check = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("check"));
			if(check==null)
				check = "0";
			else check = "1";
			obj.setCheck(check);
			String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
			obj.setuserId(userId);
			obj.setuserTen(userTen);
			
			 String sql ="";
			 if(obj.getkenhId().length()>0) sql = sql +" and kbh.pk_seq ='"+ obj.getkenhId() +"'";
			 if(obj.getvungId().length()>0) sql =sql +" and vung.pk_seq ='"+ obj.getvungId() +"'";
			 if(obj.getkhuvucId().length()>0)sql = sql + " and kv.pk_seq ='"+ obj.getkhuvucId() +"'";			 
			 if(obj.getnppId().length()>0)sql =sql +" and npp.pk_seq ='"+ obj.getnppId() +"'";
			 
			
			if (action.equals("create")) {
				response.setContentType("application/xlsm");
		        response.setHeader("Content-Disposition", "attachment; filename=BAOCAODOANHSOCUAHIEUTT.xlsm");
		        OutputStream out = response.getOutputStream();
				setQuery(sql,obj);
				ExportToExcel(out,obj);
				
			}	
			
		}catch(Exception ex){
			session.setAttribute("errors", ex.getMessage());
		}
		obj.init();
		session.setAttribute("obj", obj);
		String nextJSP = "/AHF/pages/Center/OutletReportCenter.jsp";
		response.sendRedirect(nextJSP);
	}
	private void ExportToExcel(OutputStream out,IStockintransit obj)throws Exception{
		try{ 
			
			
			String chuoi=getServletContext().getInitParameter("path") + "\\BAOCAODOANHSOCUAHIEUTT.xlsm";
			
			FileInputStream fstream = new FileInputStream(chuoi);
			
			
			/*File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\BAOCAODOANHSOCUAHIEUTT.xlsm");
			FileInputStream fstream = new FileInputStream(f) ;*/
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			//System.out.println("2/den day: " + chuoi);
			CreateHeader(workbook,obj);
			
			FillData(workbook,obj);
			
			workbook.save(out);	
			fstream.close();
		}catch(Exception ex){
			throw new Exception(ex.getMessage());
		}
	}

	private void CreateHeader(Workbook workbook,IStockintransit obj) throws Exception 
	{
		try 
		{
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");
			
			Cells cells = worksheet.getCells();
			cells.setRowHeight(0, 50.0f);
			Cell cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell, Color.RED, true, 16,
					"DOANH S??? THEO C???A HI???U");
			cell = cells.getCell("A2");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10,
					"T??? ng??y : " + obj.gettungay() + " T???i ng??y: " + obj.getdenngay());
			cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Ng??y t???o : "
					+ obj.getDateTime());
			cell = cells.getCell("A4");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10,
					"Ng?????i t???o : " + obj.getuserTen());
			
			cell = cells.getCell("AA1");		cell.setValue("Kenh");
			cell = cells.getCell("AB1");		cell.setValue("Vung");
			cell = cells.getCell("AC1");		cell.setValue("KhuVuc");
			cell = cells.getCell("AD1");		cell.setValue("GiamSatBanHang");
			cell = cells.getCell("AE1");		cell.setValue("NhaPhanPhoi");
			cell = cells.getCell("AF1");		cell.setValue("DaiDienKinhDoanh");
			cell = cells.getCell("AG1");		cell.setValue("KhachHang");
			cell = cells.getCell("AH1");		cell.setValue("DoanhSoCaoNhat");
			cell = cells.getCell("AI1");		cell.setValue("NgayBatDauMuaHang");
			cell = cells.getCell("AJ1");		cell.setValue("NgayCuoiCungMuaHang");
			cell = cells.getCell("AK1");		cell.setValue("DoanhSoTB/Thang");
			cell = cells.getCell("AL1");		cell.setValue("DoanhSoThucDat");
			cell = cells.getCell("AM1");		cell.setValue("SoDonHang");
			cell = cells.getCell("AN1");		cell.setValue("TinhThanh");
			cell = cells.getCell("AO1");		cell.setValue("MaNhaPhanPhoi");
			cell = cells.getCell("AP1");		cell.setValue("KhachHangId");
			cell = cells.getCell("AQ1");		cell.setValue("MaKhachHang");
			cell = cells.getCell("AR1");		cell.setValue("LoaiCuaHang");
			cell = cells.getCell("AS1");		cell.setValue("HangCuaHang");
			cell = cells.getCell("AT1");		cell.setValue("ViTriCuaHang");

		} catch (Exception ex) {
			System.out.println(ex.toString());
			throw new Exception("Khong the tao duoc Header cho bao cao.!!!");
		}
	}	
	private void FillData(Workbook workbook,IStockintransit obj)throws Exception{
		try{
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setGridlinesVisible(false);
			Cells cells = worksheet.getCells();
			dbutils db = new dbutils();
			ResultSet rs = db.get(getQuery());
			Cell cell = null;
			int countRow = 2;
			while(rs.next()){
				cell = cells.getCell("AA" + String.valueOf(countRow));		cell.setValue(rs.getString("Channel"));
				cell = cells.getCell("AB" + String.valueOf(countRow));		cell.setValue(rs.getString("Region"));
				cell = cells.getCell("AC" + String.valueOf(countRow));		cell.setValue(rs.getString("Area"));
				cell = cells.getCell("AD" + String.valueOf(countRow));		cell.setValue(rs.getString("Sales_Sup"));
				cell = cells.getCell("AE" + String.valueOf(countRow));		cell.setValue(rs.getString("Distributor"));
				cell = cells.getCell("AF" + String.valueOf(countRow));		cell.setValue(rs.getString("Sales_Rep"));
				cell = cells.getCell("AG" + String.valueOf(countRow));		cell.setValue(rs.getString("Customer"));
				cell = cells.getCell("AH" + String.valueOf(countRow));		cell.setValue(rs.getString("Highest_ever_Volume"));
				cell = cells.getCell("AI" + String.valueOf(countRow));		cell.setValue(rs.getString("First_Buy_date"));
				cell = cells.getCell("AJ" + String.valueOf(countRow));		cell.setValue(rs.getString("Last_buy_date"));
				cell = cells.getCell("AK" + String.valueOf(countRow));		cell.setValue(rs.getDouble("Monthly_Avg_second_sales"));
				cell = cells.getCell("AL" + String.valueOf(countRow));		cell.setValue(rs.getDouble("Monthly_archive_second_sales"));
				cell = cells.getCell("AM" + String.valueOf(countRow));		cell.setValue(rs.getDouble("#Order"));
				cell = cells.getCell("AN" + String.valueOf(countRow));		cell.setValue(rs.getString("Province"));
				cell = cells.getCell("AO" + String.valueOf(countRow));		cell.setValue(rs.getString("Distributor_code"));
				cell = cells.getCell("AP" + String.valueOf(countRow));		cell.setValue(rs.getString("KhachHangId"));
				cell = cells.getCell("AQ" + String.valueOf(countRow));		cell.setValue(rs.getString("MaKhachHang"));
				//Loai cua Hang
				cell = cells.getCell("AR" + String.valueOf(countRow));		cell.setValue(rs.getString("OutLetClass"));
				//Hang Cua Hang
				cell = cells.getCell("AS" + String.valueOf(countRow));		cell.setValue(rs.getString("OutLetType"));
				//Vi Tri cua Hang
				cell = cells.getCell("AT" + String.valueOf(countRow));		cell.setValue(rs.getString("OutLetPosition"));
				++countRow;
			}
			if(rs!=null)rs.close();
			if(db!=null){
				db.shutDown();
			}
			ReportAPI.setHidden(workbook,20);
	
		}catch(Exception ex){
			System.out.println("Errrorr : "+ex.toString());
			throw new Exception("Qua trinh dien du lieu vao file Excel va tao PivotTable bi loi.!!!");
		}
	}
	private void setQuery(String sql,IStockintransit obj){
		Utility Ult =new Utility();
		query = " select kbh.ten as Channel,v.ten as Region,kv.ten as Area," +
				"\n		gsbh.ten as Sales_Sup,npp.ten as Distributor,ddkd.ten as Sales_Rep," +
				"\n		kh.pk_seq AS KhachHangId, kh.smartid AS MaKhachHang, kh.ten as Customer,npp.convsitecode as  Distributor_code,kh.smartid as  custkey," +
				"\n		tt.ten as Province,qh.ten as Town,isnull(hch.diengiai,'Chua Xac Dinh') as Outlettype," +
				"\n		isnull(lch.diengiai,'Chua Xac Dinh') as outletClass,isnull(vtch.DienGiai,'Chua Xac Dinh') as outletPosition," +
				"\n		(	select min(a.ngaynhap)" +
				"\n			from donhang a" +
				"\n			where a.khachhang_fk = dh.khachhang_fk" +
				"\n		)as First_Buy_date," +
				"\n		(select max(mat.ngaynhap) from donhang mat where mat.ddkd_fk = dh.ddkd_fk and mat.khachhang_fk = dh.khachhang_fk and mat.gsbh_fk = dh.gsbh_fk )as Last_buy_date," +
				"\n		(	select top(1) sum(dhsp.soluong*dhsp.giamua)" +
				"\n		 	from donhang_sanpham dhsp" +
				"\n		 		inner join donhang dhs on dhs.pk_seq = dhsp.donhang_fk" +
				"\n			where dh.ddkd_fk = dhs.ddkd_fk	 and dh.gsbh_fk=dhs.gsbh_fk" +
				"\n					 and dh.khachhang_fk=dhs.khachhang_fk and dh.kbh_fk =dhs.kbh_fk	 and dh.npp_fk =dhs.npp_fk" +
				"\n			group by substring(dhs.ngaynhap,1,4)," +
				"\n					 substring(dhs.ngaynhap,6,2) order by sum(dhsp.soluong*dhsp.giamua) desc" +
				"\n	    )as  Highest_ever_Volume," +
				"\n	    (	select sum(dhsp1.soluong*dhsp1.giamua)/3" +
				"\n	     	from donhang_sanpham dhsp1" +
				"\n				inner join donhang dhs on dhs.pk_seq = dhsp1.donhang_fk" +
				"\n	    	where dh.ddkd_fk = dhs.ddkd_fk	and dh.gsbh_fk=dhs.gsbh_fk	and dh.khachhang_fk=dhs.khachhang_fk" +
				"\n				and dh.kbh_fk =dhs.kbh_fk and dh.npp_fk =dhs.npp_fk and dhs.ngaynhap >(SELECT CONVERT(VARCHAR(10),DATEADD(day,-12*7-2,dbo.GetLocalDate(DEFAULT)),120))" +
				"\n			and dhs.ngaynhap <=(SELECT CONVERT(VARCHAR(10),DATEADD(day,-2,dbo.GetLocalDate(DEFAULT)),120))" +
				"\n		 )as Monthly_Avg_second_sales," +
				"\n		(	" +
				"\n			select SUM(giatri)" +
				"\n			from " +
				"\n			(				" +
				"\n				select ISNULL(DH_SP.GIAMUA, DH_SP1.GIAMUA) * (-1)*ISNULL(DH_SP.SOLUONG, DH_SP1.SOLUONG)  as giatri" +
				"\n				FROM  DONHANGTRAVE dht  	" +
				"\n				LEFT OUTER JOIN  DONHANGTRAVE_SANPHAM DH_SP ON DH_SP.DONHANGTRAVE_FK = dht.PK_SEQ   	" +
				"\n				LEFT OUTER JOIN  DONHANG_SANPHAM DH_SP1 ON dht.DONHANG_FK = DH_SP1.DONHANG_FK   " +
				"\n				WHERE  dht.trangthai=3 and dh.ddkd_fk = dht.ddkd_fk and dh.gsbh_fk=dht.gsbh_fk and dh.khachhang_fk=dht.khachhang_fk		" +
				"\n				and dh.kbh_fk =dht.kbh_fk and dh.npp_fk =dht.npp_fk			" +
				"\n					and dht.ngaynhap >='"+obj.gettungay()+"' " +
				"\n						and dht.ngaynhap <= '"+obj.getdenngay()+"'"  +
				"\n				UNION ALL  " +
				"\n				select dhsp2.soluong*dhsp2.giamua as giatri" +
				"\n				from donhang dhg" +
				"\n				inner join donhang_sanpham dhsp2 on dhg.pk_seq = dhsp2.donhang_fk	" +
				"\n				where dh.ddkd_fk = dhg.ddkd_fk and dh.gsbh_fk=dhg.gsbh_fk and dh.khachhang_fk=dhg.khachhang_fk		" +
				"\n				and dhg.trangthai='1'  and  dh.kbh_fk =dhg.kbh_fk and dh.npp_fk =dhg.npp_fk			" +
				"\n				and dhg.ngaynhap >='"+obj.gettungay()+"' " +
				"\n				and dhg.ngaynhap <='"+obj.getdenngay()+"'" +
				"\n			) as tinhdoanhso" +

				"\n 		)" +
				" \n		as Monthly_archive_second_sales," +
				"\n       (	select count(dhd.pk_seq) from donhang dhd" +
				"\n	    	where dh.ddkd_fk = dhd.ddkd_fk	and dh.gsbh_fk=dhd.gsbh_fk		and dh.khachhang_fk=dhd.khachhang_fk" +
				"\n				and dh.kbh_fk =dhd.kbh_fk	and dh.npp_fk =dhd.npp_fk		and dhd.ngaynhap >='" + obj.gettungay() +"'" +
				"\n				and dhd.ngaynhap <='" + obj.getdenngay() +"'" +
				" \n  	 )as #Order" ;
				
		if(obj.getnppId().length()>0)
		{
			if(obj.getCheck()=="1"){
				query+= "\n from (select distinct kh.npp_fk, kh.pk_seq as khachhang_fk,d.ddkd_fk,b.gsbh_fk, kh.kbh_fk from khachhang kh "+
						"\n inner join khachhang_tuyenbh c on c.khachhang_fk = kh.pk_seq "+
						"\n inner join tuyenbanhang d on d.pk_seq = c.tbh_fk "+
						"\n inner join ddkd_gsbh b on b.ddkd_fk = d.ddkd_fk "+
						"\n where kh.npp_fk = '"+ obj.getnppId() +"' "+
						"\n ) dh ";
			}
			else
				query += "\n    from (select distinct npp_fk,khachhang_fk,ddkd_fk,kbh_fk,gsbh_fk  from donhang where ngaynhap >='" + obj.gettungay() +"' and ngaynhap <='" + obj.getdenngay() +"' and npp_fk ='"+ obj.getnppId() +"') dh ";
		}
		else{
			if(obj.getCheck()=="1"){
				query+= "\n from (select distinct kh.npp_fk, kh.pk_seq as khachhang_fk,d.ddkd_fk,b.gsbh_fk, kh.kbh_fk from khachhang kh "+
						"\n inner join khachhang_tuyenbh c on c.khachhang_fk = kh.pk_seq "+
						"\n inner join tuyenbanhang d on d.pk_seq = c.tbh_fk "+
						"\n inner join ddkd_gsbh b on b.ddkd_fk = d.ddkd_fk "+						
						"\n ) dh ";
			}
			else
			query += "\n    from (select distinct npp_fk,khachhang_fk,ddkd_fk,kbh_fk,gsbh_fk from donhang where ngaynhap >='" + obj.gettungay() +"' and ngaynhap <='" + obj.getdenngay() +"') dh ";
		}
			query +="\n			inner join kenhbanhang kbh on kbh.pk_seq = dh.kbh_fk" +
				"\n			inner join nhaphanphoi npp on npp.pk_seq = dh.npp_fk		" +
				"\n			left join khuvuc kv on kv.pk_seq = npp.khuvuc_fk" +
				"\n			left join vung v on v.pk_seq = kv.vung_fk" +
				"\n			left join giamsatbanhang gsbh on gsbh.pk_seq = dh.gsbh_fk" +
				"\n		   inner join daidienkinhdoanh ddkd on ddkd.pk_seq = dh.ddkd_fk" +
				"\n		   inner join khachhang kh on kh.pk_seq = dh.khachhang_fk" +
				"\n		   left join tinhthanh tt on tt.pk_seq = kh.tinhthanh_fk" +
				"\n		   left join quanhuyen qh on qh.pk_seq = kh.quanhuyen_fk" +
				"\n		   left join hangcuahang hch on hch.pk_seq = kh.hch_fk" +
				"\n		   left join loaicuahang lch on lch.pk_seq = kh.lch_fk " +
				"\n 		   left join vitricuahang vtch on vtch.pk_Seq=kh.vtch_fk " +
			    "\n			where npp.pk_seq in " + Ult.quyen_npp(obj.getuserId()) +" and kbh.pk_seq in " + Ult.quyen_kenh(obj.getuserId());
			        if(obj.getkenhId().length()>0)
			        	query +=" and kbh.pk_seq ='"+ obj.getkenhId() +"'";
			        if(obj.getvungId().length() >0)
			        	query +=" and v.pk_seq ='"+ obj.getvungId() +"'";
			        if(obj.getkhuvucId().length()>0)
			        	query +=" and kv.pk_seq ='"+ obj.getkhuvucId() +"'";
			        if(obj.getgsbhId().length()>0    		)
			        	query +=" and gsbh.pk_seq ='"+ obj.getgsbhId() +"'";
			        if(obj.getDdkd().length()>0)
			        	query +=" and ddkd.pk_seq ='"+ obj.getDdkd() +"'";
			        if(obj.getNhomkhId().length() > 0)
			        	query += "\n and exists (select 1 from NHOMKHACHHANG_KHACHHANG nkh where nkh.KH_FK = kh.pk_seq and nkh.NKH_FK = '"+obj.getNhomkhId()+"')";
			        
		System.out.println("Doanh so cua hieu: " + query);
		
	}
	private String getQuery(){
		return query;
	}

}
