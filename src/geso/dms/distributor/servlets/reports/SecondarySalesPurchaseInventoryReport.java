package geso.dms.distributor.servlets.reports;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.PivotTable;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.PivotFieldType;
import com.aspose.cells.PivotTables;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import java.util.*;

public class SecondarySalesPurchaseInventoryReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	public SecondarySalesPurchaseInventoryReport() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		Utility util = new Utility();
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		
		obj.setuserId(userId);
		obj.init();
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Distributor/SecondarySalesPurchaseInventoryReport.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		Utility util = new Utility();
			// OutputStream out = response.getOutputStream();
		if (userId == null)
			userId = "";
		obj.setuserId(userId);
		geso.dms.distributor.util.Utility Ult = new geso.dms.distributor.util.Utility();

		String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
		nppId = Ult.getIdNhapp(userId);
		obj.setnppId(nppId);

		String nppTen = Ult.getTenNhaPP();
		obj.setuserTen(userTen);

		String kenhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")));	//<!---
		if (kenhId == null)
			kenhId = "";
		obj.setkenhId(kenhId);

		String dvkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));	//<!---
		if (dvkdId == null)
			dvkdId = "";
		obj.setdvkdId(dvkdId);

		String nhanhangId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhanhangId")));	//<!---
		if (nhanhangId == null)
			nhanhangId = "";
		obj.setnhanhangId(nhanhangId);

		String chungloaiId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chungloaiId")));//<!---
		if (chungloaiId == null)
			chungloaiId = "";
		obj.setchungloaiId(chungloaiId);

		String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays")));	//<!---
		if (tungay == null)
			tungay = "";
		obj.settungay(tungay);

		String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays")));//<!---
		if (denngay == null)
			denngay = "";
		obj.setdenngay(denngay);

		String khoId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khoId")));	//<!---
		if (khoId == null)
			khoId = "";
		obj.setkhoId(khoId);

		String vat = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vat")));	//<!---
		obj.setvat(vat);
		String instransit = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("instransit")));	//<!---
		obj.setdiscount(instransit);

		String[] fieldsHien = request.getParameterValues("fieldsHien");
		obj.setFieldShow(fieldsHien);

		String[] fieldsAn = request.getParameterValues("fieldsAn");
		obj.setFieldHidden(fieldsAn);

		String sql = "";
		if (kenhId.length() > 0)
			sql = sql + " and kbh.pk_seq ='" + kenhId + "'";
		if (dvkdId.length() > 0)
			sql = sql + " and sp.dvkd_fk ='" + dvkdId + "'";
		if (nhanhangId.length() > 0)
			sql = sql + " and nhan.pk_seq ='" + nhanhangId + "'";
		if (chungloaiId.length() > 0)
			sql = sql + " and chungloai.pk_seq ='" + chungloaiId + "'";
		if (khoId.length() > 0)
			sql = sql + " and kho.pk_seq ='" + khoId + "'";
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		String query = setQuery(sql, obj);
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		if(action.equals("tao")){
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition",
					"attachment; filename=BaoCaoXuatNhapTon(NPP).xls");
			OutputStream out = response.getOutputStream();
			try {
				CreatePivotTable(out, response, request, fieldsHien, obj, query); // Create
				// PivotTable
			} catch (Exception ex) {
				request.getSession().setAttribute("errors", ex.getMessage());
			}
		}
		
		obj.init();
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Distributor/SecondarySalesPurchaseInventoryReport.jsp";
		response.sendRedirect(nextJSP);

	}
	

	private void CreatePivotTable(OutputStream out,
			HttpServletResponse response, HttpServletRequest request,
			String[] fieldsHien, IStockintransit obj, String query) throws Exception {
		try {
			Workbook workbook = new Workbook();
			CreateHeader(workbook, obj);
			FillData(workbook, fieldsHien,query);
			workbook.save(out, 0);
		} catch (Exception ex) {
			throw new Exception("Error Message: " + ex.getMessage());
		}
	}

	private void FillData(Workbook workbook, String[] fieldsHien,String query)
			throws Exception {
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("SecondarySalesPurchaseInventoryReport");
		Cells cells = worksheet.getCells();

		dbutils db = new dbutils();
		ResultSet rs = db.get(query);
		int index = 2;
		Cell cell = null;
		while (rs.next()) {
			cell = cells.getCell("AA" + String.valueOf(index));
			cell.setValue(rs.getString("KENH"));
			cell = cells.getCell("AB" + String.valueOf(index));
			cell.setValue(rs.getString("VUNG"));
			cell = cells.getCell("AC" + String.valueOf(index));
			cell.setValue(rs.getString("KHUVUC"));
			cell = cells.getCell("AD" + String.valueOf(index));
			cell.setValue(rs.getString("NHAN"));
			cell = cells.getCell("AE" + String.valueOf(index));
			cell.setValue(rs.getString("CHUNGLOAI"));
			cell = cells.getCell("AF" + String.valueOf(index));
			cell.setValue(rs.getString("Type"));
			cell = cells.getCell("AG" + String.valueOf(index));
			cell.setValue(rs.getString("DVKD"));
			cell = cells.getCell("AH" + String.valueOf(index));
			cell.setValue(rs.getString("NPPID"));
			cell = cells.getCell("AI" + String.valueOf(index));
			cell.setValue(rs.getString("NPP"));
			cell = cells.getCell("AJ" + String.valueOf(index));
			cell.setValue(rs.getString("KHO"));
			cell = cells.getCell("AK" + String.valueOf(index));
			cell.setValue(rs.getString("MASP"));
			cell = cells.getCell("AL" + String.valueOf(index));
			cell.setValue(rs.getString("TENSP"));
			cell = cells.getCell("AM" + String.valueOf(index));
			cell.setValue(Float.parseFloat(rs.getString("Quantily")));
			cell = cells.getCell("AN" + String.valueOf(index));
			cell.setValue(Float.parseFloat(rs.getString("soluong"))); // Quantity
			cell = cells.getCell("AO" + String.valueOf(index));
			cell.setValue(Float.parseFloat(rs.getString("AMOUNT"))); // Piece
			index++;
		}
		if(rs != null) rs.close();
		if(db != null) db.shutDown();
		
		// Set key
		Hashtable<String, Integer> selected = new Hashtable<String, Integer>();
		selected.put("Channel", 0);
		selected.put("Region", 1);
		selected.put("Area", 2);
		selected.put("Warehouse", 3);
		selected.put("Distributor", 4);
		selected.put("Begin_Invetory", 5);
		selected.put("Purchase", 6);
		selected.put("Secondary_Sales", 7);
		selected.put("Promotion_Out", 8);
		selected.put("Promotion_In", 9);
		selected.put("Adjust_Inventory", 10);
		selected.put("Ending_Inventory", 11);
		selected.put("Sales_Sup", 12);
		selected.put("Distributor_Code", 13);
		selected.put("Business_Unit", 14);
		selected.put("Brands", 15);
		selected.put("Catergory", 16);
		selected.put("SKU", 17);
		selected.put("Quantity", 18);
		selected.put("Piece", 19);
		selected.put("Amount", 20);

		ReportAPI.setHidden(workbook, 20);
		PivotTables pivotTables = worksheet.getPivotTables();
		String pos = Integer.toString(index - 1);
		int j = pivotTables.add("=SecondarySalesPurchaseInventoryReport!AA1:AO"
				+ pos, "B12", "PivotTable");

		PivotTable pivotTable = pivotTables.get(j);
		pivotTable.setRowGrand(true);
		pivotTable.setColumnGrand(true);
		pivotTable.setAutoFormat(true);

		boolean flag = false;
		int dataCount = 1;
		for (int i = 0; i < fieldsHien.length; ++i) {
			int value = selected.get(fieldsHien[i]);
			switch (value) {
			case 0:	case 1:	case 2:	case 3:	case 4:
				pivotTable.addFieldToArea(PivotFieldType.ROW, value);
				break;
			case 12: 
				break;
			case 13:
				pivotTable.addFieldToArea(PivotFieldType.ROW, 7);
				pivotTable.getRowFields().get(0).setAutoSubtotals(false);
				break;
			case 14:
				pivotTable.addFieldToArea(PivotFieldType.ROW, 6);
				break;
			case 15:
				pivotTable.addFieldToArea(PivotFieldType.ROW, 3);
				break;
			case 16:
				pivotTable.addFieldToArea(PivotFieldType.ROW, 4);
				break;
			case 17:
				pivotTable.addFieldToArea(PivotFieldType.ROW, 11);
				break;
			case 18:
				pivotTable.addFieldToArea(PivotFieldType.DATA, 12);				
				++dataCount;
				break;
			case 19:
				pivotTable.addFieldToArea(PivotFieldType.DATA, 13);				
				++dataCount;
				break;
			case 20:
				pivotTable.addFieldToArea(PivotFieldType.DATA, 14);				
				++dataCount;
				break;
			}
			if (flag == false) {
				if ((value == 5) || (value == 6) || (value == 7)
						|| (value == 8) || (value == 9) || (value == 10)
						|| (value == 11)) {
					pivotTable.addFieldToArea(PivotFieldType.COLUMN, 5);
					flag = true;
				}
			}			
		}
		for(int i=0;i<pivotTable.getRowFields().size();++i){
			pivotTable.getRowFields().get(i).setAutoSubtotals(false);
		}
		if (dataCount > 2) {
			pivotTable.addFieldToArea(PivotFieldType.COLUMN,
					pivotTable.getDataField());
		}

	}

	private void CreateHeader(Workbook workbook, IStockintransit obj)
			throws Exception {

		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("SecondarySalesPurchaseInventoryReport");

		Cells cells = worksheet.getCells();
		cells.setRowHeight(0, 50.0f);
		Cell cell = cells.getCell("A1");
		ReportAPI.getCellStyle(cell, Color.RED, true, 16,
				"XUẤT NHẬP TỒN");
		cell = cells.getCell("A2");
		ReportAPI.getCellStyle(cell, Color.BLUE, true, 10,
				"Từ ngày : " + obj.gettungay() + "   Đến ngày: " + obj.getdenngay());
		cell = cells.getCell("A3");
		ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Ngày tạo : "
				+ obj.getDateTime());
		cell = cells.getCell("A4");
		ReportAPI.getCellStyle(cell, Color.BLUE, true, 10,
				"Tạo bởi : " + obj.getuserTen());
		
		
		

		cell = cells.getCell("AA1");
		cell.setValue("Kênh Bán Hàng");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AB1");
		cell.setValue("Vùng");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AC1");
		cell.setValue("Khu vực");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AD1");
		cell.setValue("Nhãn Hàng");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AE1");
		cell.setValue("Chủng Loại");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AF1");
		cell.setValue("Dữ Liệu");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AG1");
		cell.setValue("Đơn Vị Kinh Doanh");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AH1");
		cell.setValue("Mã Nhà Phân Phối");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AI1");
		cell.setValue("Tên Nhà Phân Phối");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AJ1");
		cell.setValue("Kho");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AK1");
		cell.setValue("Mã Sản Phẩm");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AL1");
		cell.setValue("Tên Sản Phẩm");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AM1");
		cell.setValue("Thùng");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AN1");
		cell.setValue("Số Lượng Lẻ");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AO1");
		cell.setValue("Số Tiền");
		ReportAPI.setCellHeader(cell);
	}
	private String setQuery(String sql,IStockintransit obj){		
	      Calendar tondau = Calendar.getInstance();
	      int nam = Integer.parseInt(obj.gettungay().substring(0,4));
	      int thang = Integer.parseInt(obj.gettungay().substring(5,7));
	      int ngay = Integer.parseInt(obj.gettungay().substring(8,10));
	      
	      System.out.println("ngay" +nam +"-"+thang+"-"+ngay);
	      // tondau.set(nam,ngay,thang);
	        System.out.println("ngay" +nam +"-"+thang+"-"+ngay);
	      // tondau.set(nam,ngay,thang);
	      thang = thang -1;
	       tondau.set(nam, thang ,ngay);
	       tondau.add(Calendar.DATE, -1);
	       
	       nam = tondau.get(Calendar.YEAR);
	       thang =tondau.get(Calendar.MONTH);
	       thang = thang+1;//trong java thang bat dau la 0
	       ngay =tondau.get(Calendar.DATE);
	       String ngay1 = ""+ngay;
	       String  thang1 = ""+thang;
	       if(thang<10) thang1 ="0"+thang;
	       if(ngay <10)ngay1 ="0"+ngay;
	  
	      
	       String chuoi = nam +"-"+ thang1 +"-"+ ngay1;
	       System.out.println("ngay ton day"+chuoi);
			String query ="SELECT" +
					"	     TONG.KBH_FK AS KENHID, KBH.TEN AS KENH," +
					"		 NPP.MA AS NPPID, NPP.TEN AS NPP," +
					"		 DVKD.PK_SEQ AS DVKDID, DVKD.DONVIKINHDOANH AS DVKD," +
					"		 KHO.PK_SEQ AS KHOID, KHO.TEN AS KHO," +
					"		 SP.MA AS MASP, SP.TEN AS TENSP,VUNG.TEN AS VUNG,KV.TEN AS KHUVUC," +
					"		 NHAN.PK_SEQ AS NHANID, NHAN.TEN AS NHAN," +
					"		 CHUNGLOAI.PK_SEQ AS CHUNGLOAIID, CHUNGLOAI.TEN AS CHUNGLOAI," +
					"        TONG.TYPE, case when qc.soluong1 is null then 0 else TONG.SOLUONG/qc.soluong1 end as Quantily," +
					"        TONG.SOLUONG,TONG.SOLUONG * isnull(nppk.GIAMUANPP,0)*"+ obj.getvat()+" AS AMOUNT " +
					"FROM (" +
					"		(	SELECT  NPP_FK,KBH_FK, KHO_FK, SANPHAM_FK, SUM(SOLUONG) AS SOLUONG ,'1.Ton Dau' as 'Type' " +
					"			FROM TONKHONGAY" +
					"			WHERE NGAY = '"+ chuoi +"' AND NPP_FK =" + obj.getnppId() +
					"			GROUP BY KBH_FK, NPP_FK, KHO_FK, SANPHAM_FK" +
					"		) " +
					"		union" +
					"		(  " +
					"		SELECT NPP_FK,KBH_FK, KHO_FK, SANPHAM_FK, SUM(SOLUONG) AS SOLUONG,'7.Ton Cuoi' as 'Type'" +
					"			FROM TONKHONGAY" +
					"			WHERE NGAY ='"+ obj.getdenngay() +"'  AND NPP_FK =" + obj.getnppId()+" and sanpham_fk in (select pk_seq from sanpham where trangthai =1)"+	
					"			GROUP BY KBH_FK, NPP_FK, KHO_FK, SANPHAM_FK" +
					"		)" +
					"      union " +
					"		(	" +
					"			select spnhap_npp.npp_fk,spnhap_npp.kbh_fk,spnhap_npp.kho_fk,spnhap_npp.sanpham_fk ,spnhap_npp.soluong - isnull(sptrave_tt.soluong,0) as soluong ,'2.Luong Hang Thuc Te' as 'Type'" +
					"          from" +
					"			(		select b.npp_fk,b.kbh_fk,b.kho_fk,c.pk_seq as sanpham_fk,sum(cast(soluong as int)) as soluong" +
					"					from nhaphang_sp a inner join nhaphang b on a.nhaphang_fk = b.pk_seq" +
					"					inner join sanpham c on c.ma = a.sanpham_fk" ;
										if(obj.getdiscount().equals("1"))//hang van chuyen
											query += " where a.gianet <> 0 and b.npp_fk ='" + obj.getnppId() +"' and b.ngaynhan >='" + obj.gettungay() +"' and b.ngaynhan <='" + obj.getdenngay()+"'" ;
										else
											query += " where a.gianet <> 0 and b.trangthai ='1' and b.ngaynhan >='" + obj.gettungay() +"' and b.ngaynhan <='" + obj.getdenngay()+"' and b.npp_fk ='" + obj.getnppId() +"'" ;
										query += " group by b.npp_fk,b.kbh_fk,c.pk_seq,b.kho_fk" +
					"   	   ) spnhap_npp" +
					"	      left join " +
					"		  (" +
					"			 select dth.npp_fk,dth.kbh_fk,thsp.sanpham_fk ,dth.kho_fk ,sum(thsp.soluong) as soluong " +
					"			 from dontrahang_sp	 thsp inner join  dontrahang dth on  dth.pk_seq = thsp.dontrahang_fk" +
					"            where dth.trangthai ='1' and dth.ngaytra >='" + obj.gettungay() +"' and dth.ngaytra <='" + obj.gettungay() +"' and  dth.npp_fk ='"+ obj.getnppId() +"'" +
					"			 group by dth.npp_fk,dth.kbh_fk,thsp.sanpham_fk,dth.kho_fk" +
					"		  )sptrave_tt on sptrave_tt.npp_fk = spnhap_npp.npp_fk and sptrave_tt.kbh_fk = spnhap_npp.kbh_fk and sptrave_tt.sanpham_fk = spnhap_npp.sanpham_fk and sptrave_tt.kho_fk = spnhap_npp.kho_fk" +
					"	   )"+	
					" union" +
					"		(	select nppban.npp_fk, nppban.kbh_fk, nppban.kho_fk ,nppban.sanpham_fk, nppban.soluong - isnull(khachhang_tra.soluong,0) as soluong ,'5.Doanh So Ban' as'Type'" +
					"			from" +
					"			   ( select dh.npp_fk,dh.kbh_fk,dhsp.sanpham_fk,dhsp.kho_fk,sum(soluong) as soluong " +
					"				 from donhang_sanpham dhsp " +
					"						inner join donhang dh on dh.pk_seq = dhsp.donhang_fk" +
					"				 where dh.trangthai ='1' and dh.ngaynhap >= '" + obj.gettungay() +"' and dh.ngaynhap <= '" + obj.getdenngay() +"' and dh.npp_fk ='"+ obj.getnppId() +"'" +
					"						and pk_seq not in (" +
					"											select isnull(tra.donhang_fk,0) " +
					"											from donhangtrave tra " +
					"											where tra.trangthai ='1' and tra.npp_fk = dh.npp_fk and tra.ngaynhap >='" + obj.gettungay() +"' and tra.ngaynhap <= '" + obj.getdenngay() + "' and tra.npp_fk ='"+ obj.getnppId() +"')" +
					"											group by  dh.npp_fk,dh.kbh_fk,dhsp.sanpham_fk,dhsp.kho_fk  " +
					"										  ) nppban" +
					"			   							  left join" +
					"										  (	" +
					"											select dontra.npp_fk, dontra.kbh_fk,dontra_sp.sanpham_fk,dontra_sp.kho_fk,sum(soluong) as soluong" +
					"											from  donhangtrave_sanpham dontra_sp" +
					"											inner join donhangtrave dontra  on dontra.pk_seq = dontra_sp.donhangtrave_fk" +
					"											where dontra.trangthai ='3' and dontra.ngaynhap >= '" + obj.gettungay() + "' and dontra.ngaynhap <= '" + obj.getdenngay() +"' and dontra.npp_fk ='"+ obj.getnppId() +"'" +
					"											group by dontra.npp_fk, dontra.kbh_fk,dontra_sp.sanpham_fk,dontra_sp.kho_fk" +
					"										  )khachhang_tra on khachhang_tra.npp_fk =nppban.npp_fk and khachhang_tra.kbh_fk =nppban.kbh_fk and khachhang_tra.sanpham_fk =nppban.sanpham_fk and khachhang_tra.kho_fk = nppban.kho_fk" +
					"		)" +
					"		union" +
						"(" +
						" select nhap_km.npp_fk,nhap_km.kbh_fk,nhap_km.kho_fk,c.pk_seq as sanpham_fk,sum(convert(numeric(18,0),nhap_sp_km.soluong)) as soluong ,'3.KM Nhap' as 'Type' "+ 
						" from nhaphang_sp nhap_sp_km   "+
						"		inner join nhaphang nhap_km on nhap_sp_km.nhaphang_fk = nhap_km.pk_seq "+ 
						"		inner join sanpham c on c.ma = nhap_sp_km.sanpham_fk  "+
						" where nhap_sp_km.gianet = 0 and nhap_km.ngaynhan >='"+obj.gettungay()+"' and nhap_km.ngaynhan <= '"+obj.getdenngay()+"' and nhap_km.npp_fk ='"+obj.getnppId()+"' AND nhap_sp_km.SOLUONG> 0 AND nhap_sp_km.gianet= 0 "+
						 " group by nhap_km.npp_fk,nhap_km.kbh_fk,c.pk_seq,nhap_km.kho_fk "+
						") "+
				//	"		( 	select khuyenmai_nhap.npp_fk,khuyenmai_nhap.kbh_fk ,nhap_km.kho_fk,khuyenmai_nhap.sanpham_fk,khuyenmai_nhap.soluong,'3.Promotion in' as 'Type' " +
				//	"			from ( " +
				//	"					(select nhap_km.npp_fk,nhap_km.kbh_fk,c.pk_seq as sanpham_fk,nhap_km.kho_fk,sum(convert(numeric(18,0),nhap_sp_km.soluong)) as soluong" +
				//	"					 from nhaphang_sp nhap_sp_km " +
				//	"							inner join nhaphang nhap_km on nhap_sp_km.nhaphang_fk = nhap_km.pk_seq" +
				//	"							inner join sanpham c on c.ma = nhap_sp_km.sanpham_fk" ;
										
					//"							inner join ctkhuyenmai ctkm on ctkm.pk_seq = cast(nhap_sp_km.scheme as int)" ;
				//	if(obj.getdiscount().equals("1")){//hang dang van chuyen
				//		query +="		 where nhap_sp_km.gianet = 0 and nhap_sp_km.soluong>0 and nhap_km.ngaynhan >='" + obj.gettungay() +"' and nhap_km.ngaynhan <= '" + obj.getdenngay() + "' and nhap_km.npp_fk ='"+ obj.getnppId() +"'";
				//	}
				//	else{
				//		query +="		 where nhap_sp_km.gianet = 0 and nhap_sp_km.soluong>0 and nhap_km.trangthai ='1'  and nhap_km.ngaynhan >='" + obj.gettungay() +"' and nhap_km.ngaynhan <='" + obj.getdenngay() + "' and  nhap_km.npp_fk ='"+ obj.getnppId() +"'";
				//	}
				//	
				//	query +="					 group by nhap_km.npp_fk,nhap_km.kbh_fk,c.pk_seq,nhap_km.kho_fk" +
					//" 				   )" +
					//"				 union" +
					//"				(" +
					//"					select nhap_km.npp_fk,nhap_km.kbh_fk,c.pk_seq as sanpham_fk,nhap_km.kho_fk,sum(cast(nhap_sp_km.soluong as int)) as soluong" +
					//"					from nhaphang_sp nhap_sp_km " +
					//"						inner join nhaphang nhap_km on nhap_sp_km.nhaphang_fk = nhap_km.pk_seq" +
					//"							inner join sanpham c on c.ma = nhap_sp_km.sanpham_fk" +
					//"	    			where nhap_sp_km.gianet = 0" +
					//"						   and cast(nhap_sp_km.scheme as int) not in (select isnull(pk_seq,0) from ctkhuyenmai)" +
					//"							and nhap_km.trangthai ='1' and nhap_km.npp_fk ='" + obj.getnppId() + "' and nhap_km.ngaynhan >= '" + obj.gettungay() +"' and nhap_km.ngaynhan <= '" + obj.getdenngay() +"'" +
					//"					group by nhap_km.npp_fk,nhap_km.kbh_fk,c.pk_seq,nhap_km.kho_fk" +
					//"				 )" +
														
					//"	 		) khuyenmai_nhap" +
					//"		)" +
					"		union" +
					"		(  	select donh.npp_fk,donh.kbh_fk,km.kho_fk,spkm.pk_seq as sanpham_fk,sum(trakm_khachhang.soluong) as soluong,'4.KM Xuat' as 'Type'" +
					"			from donhang_ctkm_trakm trakm_khachhang" +
					"				inner join (" +
					"							select * " +
					"							from donhang " +
					"							where trangthai ='1' and npp_fk ='" + obj.getnppId() +"' and ngaynhap >='"+ obj.gettungay() +"' and ngaynhap <= '"+ obj.getdenngay() +"' " +
					"							) donh on donh.pk_seq = trakm_khachhang.donhangid" +
					"				inner join sanpham spkm on spkm.ma = trakm_khachhang.spma" +
					"				inner join ctkhuyenmai km on km.pk_seq = trakm_khachhang.ctkmid" +
					"				where len(trakm_khachhang.spma) >0 and  donh.pk_seq not in (select isnull(donhang_fk,0) from donhangtrave where trangthai ='1')" +
					"				group by donh.npp_fk,donh.kbh_fk,km.kho_fk,spkm.pk_seq" +
					"		)" +
					"		union" +
					"		(" +
					//"			SELECT DCTK.NPP_FK, DCTK.KBH_FK, DCTK.KHO_FK, DCTK_SP.SANPHAM_FK, SUM(cast(DCTK_SP.DIEUCHINH as int)) AS SOLUONG,'5.Adjust inventory' as 'Type'" +
					//"			FROM DIEUCHINHTONKHO DCTK" +
					//"				INNER JOIN DIEUCHINHTONKHO_SP DCTK_SP ON DCTK_SP.DIEUCHINHTONKHO_FK = DCTK.PK_SEQ" +
					//"			WHERE DCTK.TRANGTHAI = '1' AND DCTK.NPP_FK = '" + obj.getnppId() + "' AND DCTK.NGAYDC >= '"+ obj.gettungay() +"' AND DCTK.NGAYDC <='"+ obj.getdenngay()+"'" +
					
					 "SELECT NPP.PK_SEQ AS NPP_FK, KBH.PK_SEQ AS KBH_FK, NPP_KHO.KHO_FK AS KHO_FK,SP.PK_SEQ AS SANPHAM_FK ,"+
					 "SUM( cast( ISNULL(DCTK_SP.DIEUCHINH,0) as int) ) AS SOLUONG, '6.Dieu Chinh' as 'Type' " +
						"				 FROM NHAPHANPHOI NPP " +
						"				 INNER JOIN NHAPP_KBH NPP_KBH ON NPP_KBH.NPP_FK = NPP.PK_SEQ " +
						"				 INNER JOIN KENHBANHANG KBH ON NPP_KBH.KBH_FK = KBH.PK_SEQ " +
						"				  INNER JOIN NHAPP_KHO NPP_KHO ON NPP_KHO.NPP_FK = NPP.PK_SEQ AND NPP_KHO.KBH_FK = KBH.PK_SEQ "+ 
						"				 INNER JOIN SANPHAM SP ON SP.PK_SEQ = NPP_KHO.SANPHAM_FK "+
						"				 LEFT JOIN DIEUCHINHTONKHO DCTK ON DCTK.NPP_FK = NPP.PK_SEQ AND DCTK.KHO_FK = NPP_KHO.KHO_FK AND DCTK.KBH_FK = KBH.PK_SEQ  and DCTK.TRANGTHAI = '1' AND DCTK.NGAYDC >= '"+ obj.gettungay() +"' AND DCTK.NGAYDC <='"+ obj.getdenngay() +"'" + 
						"				 LEFT JOIN DIEUCHINHTONKHO_SP DCTK_SP ON DCTK_SP.DIEUCHINHTONKHO_FK = DCTK.PK_SEQ AND DCTK_SP.SANPHAM_FK = SP.PK_SEQ " +
						"				WHERE NPP_KHO.NPP_FK = '"+obj.getnppId()+"'" +
						"							GROUP BY NPP.PK_SEQ , KBH.PK_SEQ , NPP_KHO.KHO_FK ,SP.PK_SEQ  " +
					
					"		)" + 
					
				"	) tong" +
					"	INNER JOIN (select * from sanpham) SP ON SP.PK_SEQ = tong.SANPHAM_FK " +
					"	LEFT JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK " +
					"   INNER JOIN KHO KHO ON KHO.PK_SEQ = 	tong.KHO_FK " +
					"   LEFT JOIN NHANHANG NHAN ON NHAN.PK_SEQ = SP.NHANHANG_FK  " +
					"   LEFT JOIN CHUNGLOAI CHUNGLOAI ON CHUNGLOAI.PK_SEQ = SP.CHUNGLOAI_FK  " +
					"   INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ = tong.KBH_FK  " +
					"   INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = tong.NPP_FK " +
					"	LEFT JOIN KHUVUC KV ON KV.PK_SEQ = NPP.KHUVUC_FK "+
					" 	LEFT JOIN VUNG VUNG ON VUNG.PK_SEQ = KV.VUNG_FK "+
					"	left join quycach qc on qc.dvdl1_fk = sp.dvdl_fk and qc.sanpham_fk = sp.pk_seq "+
					"	left join ( "+ 
					"		  select distinct bgm.kenh_fk as kbh_fk,bgm_sp.sanpham_fk,bgmnpp.npp_fk,bgm_sp.giamuanpp as GIAMUANPP from banggiamuanpp_npp bgmnpp "+ 
					"		  inner join banggiamuanpp bgm on bgm.pk_seq = bgmnpp.banggiamuanpp_fk "+
					"		  inner join bgmuanpp_sanpham bgm_sp on bgm_sp.bgmuanpp_fk = bgm.pk_seq "+
						"	 ) nppk on nppk.npp_fk = tong.npp_fk and nppk.sanpham_fk = tong.sanpham_fk and nppk.kbh_fk = tong.kbh_fk ";
						  query +=" order by substring(TONG.Type,1,2) asc ";						   
					return query;
					
					
		}

}
