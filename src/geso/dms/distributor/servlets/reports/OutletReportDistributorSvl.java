package geso.dms.distributor.servlets.reports;

import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
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

/**
 * Servlet implementation class OutletReportDistributorSvl
 */
public class OutletReportDistributorSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public OutletReportDistributorSvl() {
        super();
       
    }
    String query="";
	
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
		String nextJSP = "/AHF/pages/Distributor/OutletReportDistributor.jsp";
		response.sendRedirect(nextJSP);	
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    HttpSession session = request.getSession();
	    IStockintransit obj = new Stockintransit();
	    Utility util = new Utility();
	    try{
	    	obj.settungay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays"))));
			obj.setdenngay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays"))));
			obj.setkenhId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhs"))));
			obj.setDdkd(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkds"))));
			String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
			String check = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("check"));
			if(check==null)
				check = "0";
			else check = "1";
			obj.setCheck(check);
			String userTen = (String)session.getAttribute("userTen");
			String userId = (String)session.getAttribute("userId");
		    obj.setuserId(userId);
		    obj.setuserTen(userTen);
			geso.dms.distributor.util.Utility Ult = new geso.dms.distributor.util.Utility();
			obj.setnppId(Ult.getIdNhapp(userId)) ;
			obj.setnppTen(Ult.getTenNhaPP());
			
			String[] fieldsHien = request.getParameterValues("fieldsHien");
			obj.setFieldShow(fieldsHien);

			String[] fieldsAn = request.getParameterValues("fieldsAn");
			obj.setFieldHidden(fieldsAn);
			
			
			 String sql ="";
			 if(obj.getkenhId().length()>0) sql = sql +" and kbh.pk_seq ='"+ obj.getkenhId() +"'";
			 
			 
			//Xu ly dieu kien dai dien kinh doanh....
			 	// Do something.....
			 //
			 if (action.equals("create")) {
				 
				 response.setContentType("application/xlsm");
			        response.setHeader("Content-Disposition", "attachment; filename=BAOCAODOANHSOCUAHIEU(NPP).xlsm");
			        OutputStream out = response.getOutputStream();
					setQuery(sql,obj);
					ExportToExcel(out,obj);
				}
	    }catch(Exception ex){
			session.setAttribute("errors", ex.getMessage());
		}
		obj.init();
		session.setAttribute("obj", obj);
		String nextJSP = "/AHF/pages/Distributor/OutletReportDistributor.jsp";
		response.sendRedirect(nextJSP);	
	}
	private void ExportToExcel(OutputStream out,IStockintransit obj)throws Exception{
		try{
			String chuoi=getServletContext().getInitParameter("path") + "\\BAOCAODOANHSOCUAHIEU(NPP).xlsm";		
			FileInputStream fstream = new FileInputStream(chuoi);
			/*File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\BAOCAODOANHSOCUAHIEU(NPP).xlsm");
			FileInputStream fstream = new FileInputStream(f) ;*/
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			
			CreateHeader(workbook,obj);
			
			FillData(workbook,obj);
			
			workbook.save(out);	
			fstream.close();			
		}catch(Exception ex){
			System.out.println(ex.toString());
			throw new Exception(ex.getMessage());
		}
	}

	private void CreateHeader(Workbook workbook,IStockintransit obj) throws Exception {
		try {
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");

			Cells cells = worksheet.getCells();
			cells.setRowHeight(0, 50.0f);
			Cell cell = cells.getCell("A1");
			
			ReportAPI.getCellStyle(cell, Color.RED, true, 16,
					"BÁO CÁO DOANH SỐ THEO CỬA HIỆU");
			cell = cells.getCell("A2");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10,
					"Từ ngày : " + obj.gettungay() + "    Đến Ngày: " + obj.getdenngay());
			cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Ngày tạo : "
					+ obj.getDateTime());
			cell = cells.getCell("A4");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10,
					"Tạo Bởi : " + obj.getuserTen());
			
			cell = cells.getCell("AA1");		cell.setValue("Kenh Ban Hang");
			cell = cells.getCell("AB1");		cell.setValue("Nhan Vien Ban Hang");
			cell = cells.getCell("AC1");		cell.setValue("Khach Hang");
			cell = cells.getCell("AD1");		cell.setValue("Doanh So Cao Nhat");
			cell = cells.getCell("AE1");		cell.setValue("Ngay Mua Dau Tien");
			cell = cells.getCell("AF1");		cell.setValue("Ngay Mua Lan Cuoi");
			cell = cells.getCell("AG1");		cell.setValue("Doanh So Ban Tu NPP");
			cell = cells.getCell("AH1");		cell.setValue("Tong Don Hang");
			cell = cells.getCell("AI1");		cell.setValue("Tinh Thanh");
			cell = cells.getCell("AJ1");		cell.setValue("Ma Nha Phan Phoi");
			cell = cells.getCell("AK1");		cell.setValue("Doanh So Trung Binh Tren Thang");
			cell = cells.getCell("AL1");		cell.setValue("Don Hang Tra Ve");	
			cell = cells.getCell("AM1");		cell.setValue("Ma Khach Hang");
			cell = cells.getCell("AN1");		cell.setValue("Dia Chi");
		} catch (Exception ex) {
			throw new Exception("Khong the tao duoc Header cho bao cao.!!!");
		}
	}	
	private void FillData(Workbook workbook,IStockintransit obj)throws Exception{
		try{
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			
			Cells cells = worksheet.getCells();
			dbutils db = new dbutils();
			ResultSet rs = db.get(getQuery());
			Cell cell = null;
			int countRow = 2;
			while(rs.next()){
				cell = cells.getCell("AA" + String.valueOf(countRow));		cell.setValue(rs.getString("Channel"));
				cell = cells.getCell("AB" + String.valueOf(countRow));		cell.setValue(rs.getString("Sales_Rep"));
				cell = cells.getCell("AC" + String.valueOf(countRow));		cell.setValue(rs.getString("Customer"));
				cell = cells.getCell("AD" + String.valueOf(countRow));		cell.setValue(rs.getDouble("Highest_ever_Volume"));
				cell = cells.getCell("AE" + String.valueOf(countRow));		cell.setValue(rs.getString("First_Buy_date"));
				cell = cells.getCell("AF" + String.valueOf(countRow));		cell.setValue(rs.getString("Last_buy_date"));
				cell = cells.getCell("AG" + String.valueOf(countRow));		cell.setValue(rs.getFloat("Monthly_archive_second_sales"));
				cell = cells.getCell("AH" + String.valueOf(countRow));		cell.setValue(rs.getFloat("#Order"));
				cell = cells.getCell("AI" + String.valueOf(countRow));		cell.setValue(rs.getString("Province"));
				cell = cells.getCell("AJ" + String.valueOf(countRow));		cell.setValue(rs.getString("Distributor_code"));
				cell = cells.getCell("AK" + String.valueOf(countRow));		cell.setValue(rs.getFloat("Monthly_Avg_second_sales"));
				cell = cells.getCell("AL" + String.valueOf(countRow));		cell.setValue(rs.getFloat("Sodhtv"));
				cell = cells.getCell("AM" + String.valueOf(countRow));		cell.setValue(rs.getString("makh"));
				cell = cells.getCell("AN" + String.valueOf(countRow));		cell.setValue(rs.getString("diachi"));
				++countRow;
			}
			if(rs!=null) rs.close();
		    db.shutDown();
		    ReportAPI.setHidden(workbook, 20);
		}catch(Exception ex){
			System.out.println(ex.toString());
			throw new Exception("Qua trinh dien du lieu vao file Excel va tao PivotTable bi loi.!!!");
		}
	}
	
	private void  setQuery(String sql,IStockintransit obj){
		//Utility Ult =new Utility();
		query = "select kbh.ten as Channel,v.ten as Region,kv.ten as Area," +
		"		gsbh.ten as Sales_Sup,npp.ten as Distributor,ddkd.ten as Sales_Rep," +
		"		kh.smartid AS makh ,kh.ten as Customer , kh.diachi ,npp.convsitecode as  Distributor_code,kh.smartid as  custkey," +
		"		tt.ten as Province,qh.ten as Town,hch.diengiai as Outlettype, " +
		"		lch.diengiai as outletpostion, " +
		"		(	select min(a.ngaynhap) " +
		"			from donhang a " +
		"			where a.khachhang_fk = dh.khachhang_fk " +
		"		)as First_Buy_date, " +
		"		(select max(mat.ngaynhap) from donhang mat where mat.ddkd_fk = dh.ddkd_fk  " +
		"    and mat.khachhang_fk = dh.khachhang_fk and mat.gsbh_fk = dh.gsbh_fk )as Last_buy_date, " +
		"		(	select top(1) sum(dhsp.soluong*dhsp.giamua) " +
		"		 	from donhang_sanpham dhsp " +
		"		 		inner join donhang dhs on dhs.pk_seq = dhsp.donhang_fk " +
		"			where dh.ddkd_fk = dhs.ddkd_fk	 and dh.gsbh_fk=dhs.gsbh_fk " +
		"					 and dh.khachhang_fk=dhs.khachhang_fk and dh.kbh_fk =dhs.kbh_fk	 and dh.npp_fk =dhs.npp_fk " +
		"			group by substring(dhs.ngaynhap,1,4), " +
		"					 substring(dhs.ngaynhap,6,2) order by sum(dhsp.soluong*dhsp.giamua) desc " +
		"	    )as  Highest_ever_Volume, " +
		"	    (	select sum(dhsp1.soluong*dhsp1.giamua)/3 " +
		"	     	from donhang_sanpham dhsp1 " +
		"				inner join donhang dhs on dhs.pk_seq = dhsp1.donhang_fk " +
		"	    	where dh.ddkd_fk = dhs.ddkd_fk	and dh.gsbh_fk=dhs.gsbh_fk	and dh.khachhang_fk=dhs.khachhang_fk " +
		"				and dh.kbh_fk =dhs.kbh_fk and dh.npp_fk =dhs.npp_fk and dhs.ngaynhap >(SELECT CONVERT(VARCHAR(10),DATEADD(day,-12*7-2,dbo.GetLocalDate(DEFAULT)),120)) " +
		"			and dhs.ngaynhap <=(SELECT CONVERT(VARCHAR(10),DATEADD(day,-2,dbo.GetLocalDate(DEFAULT)),120)) " +
		"		 )as Monthly_Avg_second_sales,  " +
		"          (select count(dhg.pk_seq ) " +
		"				from donhangtrave dhg " +
		"					" +
		"				where dh.ddkd_fk = dhg.ddkd_fk and dh.gsbh_fk=dhg.gsbh_fk and dh.khachhang_fk=dhg.khachhang_fk		" +
		"				and dh.kbh_fk =dhg.kbh_fk and dh.npp_fk =dhg.npp_fk	 " +
		"  ) as  Sodhtv ," +
		"		(	" +
		" 			select SUM(giatri)" +
		" 			from " +
		"			(				" +
		"				select ISNULL(DH_SP.GIAMUA, DH_SP1.GIAMUA) * (-1)*ISNULL(DH_SP.SOLUONG, DH_SP1.SOLUONG)  as giatri" +
		"				FROM  DONHANGTRAVE dht  	" +
		"				LEFT OUTER JOIN  DONHANGTRAVE_SANPHAM DH_SP ON DH_SP.DONHANGTRAVE_FK = dht.PK_SEQ   	" +
		"				LEFT OUTER JOIN  DONHANG_SANPHAM DH_SP1 ON dht.DONHANG_FK = DH_SP1.DONHANG_FK   " +
		"				WHERE dht.trangthai='3' and dh.ddkd_fk = dht.ddkd_fk and dh.gsbh_fk=dht.gsbh_fk and dh.khachhang_fk=dht.khachhang_fk		" +
		"				and dh.kbh_fk =dht.kbh_fk and dh.npp_fk =dht.npp_fk			" +
		"				and dht.ngaynhap >='"+obj.gettungay()+"' " +
		"				and dht.ngaynhap <= '"+obj.getdenngay()+"'"  +
		"				UNION ALL  " +
		"				select dhsp2.soluong*dhsp2.giamua as giatri" +
		"				from donhang dhg" +
		"				inner join donhang_sanpham dhsp2 on dhg.pk_seq = dhsp2.donhang_fk	" +
		"				where dh.ddkd_fk = dhg.ddkd_fk and dh.gsbh_fk=dhg.gsbh_fk and dh.khachhang_fk=dhg.khachhang_fk		" +
		"				and dh.kbh_fk =dhg.kbh_fk and dh.npp_fk =dhg.npp_fk	 and dhg.trangthai=1		" +
		"				and dhg.ngaynhap >='"+obj.gettungay()+"' " +
		"				and dhg.ngaynhap <='"+obj.getdenngay()+"'" +
		"			) as tinhdoanhso" +

		" 		)" +
		" 		as Monthly_archive_second_sales," +
		"       (	select count(dhd.pk_seq) from donhang dhd" +
		"	    	where dh.ddkd_fk = dhd.ddkd_fk	and dh.gsbh_fk=dhd.gsbh_fk		and dh.khachhang_fk=dhd.khachhang_fk" +
		"				and dh.kbh_fk =dhd.kbh_fk	and dh.npp_fk =dhd.npp_fk		and dhd.ngaynhap >='" + obj.gettungay() +"'" +
		"				and dhd.ngaynhap <='" + obj.getdenngay() +"'" +
		"   	 )as #Order" ;
  


		
		if(obj.getCheck()=="1"){
			query+= " from (select distinct kh.npp_fk, kh.pk_seq as khachhang_fk,d.ddkd_fk,b.gsbh_fk, kh.kbh_fk from khachhang kh "+
					"inner join khachhang_tuyenbh c on c.khachhang_fk = kh.pk_seq "+
					"inner join tuyenbanhang d on d.pk_seq = c.tbh_fk "+
					"inner join ddkd_gsbh b on b.ddkd_fk = d.ddkd_fk "+
					"where kh.npp_fk = '"+ obj.getnppId() +"' "+
					") dh ";
		}
		else		
			query += " from (select distinct npp_fk,khachhang_fk,ddkd_fk,kbh_fk,gsbh_fk from donhang where ngaynhap >='" + obj.gettungay() +"' and ngaynhap <='" + obj.getdenngay() +"' and npp_fk="+obj.getnppId()+") dh ";
/*if(obj.getnppId().length()>0)
		query += "    from (select distinct npp_fk,khachhang_fk,ddkd_fk,kbh_fk,gsbh_fk  from donhang where ngaynhap >='" + obj.gettungay() +"' and ngaynhap <='" + obj.getdenngay() +"' and npp_fk ='"+ obj.getnppId() +"') dh ";
else
	query += "    from (select distinct npp_fk,khachhang_fk,ddkd_fk,kbh_fk,gsbh_fk from donhang where ngaynhap >='" + obj.gettungay() +"' and ngaynhap <='" + obj.getdenngay() +"' and npp_fk="+obj.getnppId()+") dh ";*/
	query +="			inner join kenhbanhang kbh on kbh.pk_seq = dh.kbh_fk" +
		"			inner join nhaphanphoi npp on npp.pk_seq = dh.npp_fk		" +
		"			left join khuvuc kv on kv.pk_seq = npp.khuvuc_fk" +
		"			left join vung v on v.pk_seq = kv.vung_fk" +
		"			left join giamsatbanhang gsbh on gsbh.pk_seq = dh.gsbh_fk" +
		"		   inner join daidienkinhdoanh ddkd on ddkd.pk_seq = dh.ddkd_fk" +
		"		   inner join khachhang kh on kh.pk_seq = dh.khachhang_fk" +
		"		   left join tinhthanh tt on tt.pk_seq = kh.tinhthanh_fk" +
		"		   left join quanhuyen qh on qh.pk_seq = kh.quanhuyen_fk" +
		"		   left join hangcuahang hch on hch.pk_seq = kh.hch_fk" +
		"		   left join loaicuahang lch on lch.pk_seq = kh.lch_fk " +
			    "		   where npp.pk_seq = '"+ obj.getnppId() +"' ";
			if(obj.getkenhId().length()>0) query+= " AND dh.KBH_FK='"+obj.getkenhId()+"'";
			if(obj.getDdkd().length()>0) query+= " AND dh.ddkd_fk='"+obj.getDdkd()+"'";
			System.out.println("1/ lay muc Nha phan phoi: "+query);
				
	}
	public String getQuery()
	{
		return this.query;
	}
}





