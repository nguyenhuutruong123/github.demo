package geso.dms.center.servlets.reports;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Hashtable;

import javax.servlet.ServletException;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.PivotFieldType;
import com.aspose.cells.PivotTable;
import com.aspose.cells.PivotTables;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;

import com.aspose.cells.Worksheets;



public class DailysalesTT extends HttpServlet {
	private static final long serialVersionUID = 1L;	
    public DailysalesTT() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		String querystring = request.getQueryString();
		Utility util = new Utility();
		
		obj.setuserId(util.getUserId(querystring));
		obj.setuserTen((String) session.getAttribute("userTen"));
		session.setAttribute("groupCustomer","");
		session.setAttribute("gorupSKU","");	
		obj.init();
		session.setAttribute("obj", obj);
		String nextJSP = "/AHF/pages/Center/DailySecondarySalesReportCenter.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		OutputStream out = response.getOutputStream();
		
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		Utility util = new Utility();
		
		obj.settungay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays"))));
		obj.setdenngay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays"))));
		
		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");
		obj.setkenhId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")):""));
		
		obj.setvungId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")):""));
			
		obj.setkhuvucId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")):""));
		
		obj.setgsbhId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("gsbhs")))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("gsbhs")):""));
		
		obj.setnppId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")):""));
		
		obj.setdvkdId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")):""));
		
		obj.setnhanhangId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhanhangId")))!=null?
			util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhanhangId")):""));
		obj.setchungloaiId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chungloaiId")))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chungloaiId")):""));

		String groupCustomer = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("groupCus")));
		if(groupCustomer==null)
			groupCustomer = "";
		String gorupSKU = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("groupSKU")));
		if(gorupSKU==null)
			gorupSKU="";

		obj.setdiscount(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("discount"))));
		String[] fieldsHien = request.getParameterValues("fieldsHien");
		obj.setFieldShow(fieldsHien);
		
		String sql = "";
		if (obj.getkenhId().length() > 0)
			sql = sql + " and kbh.pk_seq ='" + obj.getkenhId() + "'";
		if (obj.getvungId().length() > 0)
			sql = sql + " and vung.pk_seq ='" + obj.getvungId() + "'";
		if (obj.getkhuvucId().length() > 0)
			sql = sql + " and kv.pk_seq ='" + obj.getkhuvucId() + "'";
		if (obj.getdvkdId().length() > 0)
			sql = sql + " and sp.dvkd_fk ='" + obj.getdvkdId() + "'";
		if (obj.getnppId().length() > 0)
			sql = sql + " and npp.pk_seq ='" + obj.getnppId() + "'";
		if (obj.getnhanhangId().length() > 0)
			sql = sql + " and nhan.pk_seq ='" + obj.getnhanhangId() + "'";
		if (obj.getchungloaiId().length() > 0)
			sql = sql + " and chungloai.pk_seq ='" + obj.getchungloaiId()	+ "'";
		
		String action = (String) geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		String nextJSP = "/AHF/pages/Center/DailySecondarySalesReportCenter.jsp";		
		try{
			if (action.equals("create")) {			
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition",
						"attachment; filename=DailySecondarySalesCenter.xls");
				String query =  setQuery(sql,obj);
		        if(!CreatePivotTable(out,obj,query)){
		        	response.setContentType("text/html");
		            PrintWriter writer = new PrintWriter(out);
		            writer.print("Xin loi khong co bao cao trong thoi gian nay");
		            writer.close();
		        }
			}else{
				response.sendRedirect(nextJSP);
			}
		}catch(Exception ex){
			obj.setMsg(ex.getMessage());
			response.sendRedirect(nextJSP);
		}
		session.setAttribute("groupCustomer", groupCustomer);
		session.setAttribute("gorupSKU", gorupSKU);		
	}	
	private String setQuery(String sql, IStockintransit obj) {
		String query ="select  v.ten as region, kv.ten as area, npp.ten as distributor, npp.sitecode as distcode, nh.ten as brand,cl.ten as category, sp.ma + '_' + sp.ten as SKU, "+ 
	    		" sp.ma as SKUcode, ddkd.ten as SalesRep, npp.sitecode + '_' + kh.ten + '( ' + kh.diachi +  ' )' as customer,kh.pk_seq as customercode, vtch.vitri as outletposition, "+
	     		" isnull(m.diengiai, 'Chua Xac Dinh') as outlettype, kbh.diengiai as chanel, dh.ngaynhap as offdate, dhsp.soluong as piece, " +
	     		" case when dhsp.soluong <0 then dhsp.giamua *dhsp.soluong " +
	     		" else dhsp.giamua *dhsp.soluong end as amount ,"+  
	     		" isnull(gsbh.ten, 'Chua xac dinh') as salesuper,"+
	     		" case when round((cast((qc.soluong2 * dhsp.soluong) as float) / cast(qc.soluong1 as float)),3) is null then 0 else round((cast((qc.soluong2 * dhsp.soluong) as float) / cast(qc.soluong1 as float)),3) end as quantity," +
	     		
	     		" isnull(qh.ten, 'Chua xac dinh') as town, "+
	     		" isnull(tt.ten, 'Chua xac dinh') as province,"+ 
	     		" hch.diengiai as OutletClass, nkh.diengiai as nhomkhachhang, nsp.diengiai as nhomsanpham"+
	     		" from donhang dh inner join donhang_sanpham dhsp on dh.pk_seq = dhsp.donhang_fk" +
	     		" inner join sanpham sp on dhsp.sanpham_fk = sp.pk_seq "+ 
	     		" inner join nhaphanphoi npp on dh.npp_fk = npp.pk_seq " +
	     		" left join khuvuc kv on npp.khuvuc_fk = kv.pk_seq  " +
	     		" left join vung v on kv.vung_fk = v.pk_seq "+
	     		" inner join nhanhang nh on sp.nhanhang_fk = nh.pk_seq " +
	     		" left join chungloai cl on sp.chungloai_fk = cl.pk_seq "+
	     		" inner join daidienkinhdoanh ddkd on dh.ddkd_fk = ddkd.pk_seq left join khachhang kh on dh.khachhang_fk = kh.pk_seq"+  
	     		" left join vitricuahang vtch on kh.vtch_fk = vtch.pk_seq left join kenhbanhang kbh on kh.kbh_fk = kbh.pk_seq"+
	     		" left join loaicuahang m on kh.lch_fk = m.pk_seq left join giamsatbanhang gsbh on dh.gsbh_fk = gsbh.pk_seq "+
	     		" left join quycach qc on qc.sanpham_fk = sp.pk_seq  left join quanhuyen qh on kh.quanhuyen_fk = qh.pk_seq "+
	     		" left join tinhthanh tt on kh.tinhthanh_fk = tt.pk_seq left join hangcuahang hch on kh.hch_fk = hch.pk_seq "+
	     		" left join nhomkhachhang_khachhang nkhkh on nkhkh.kh_fk = kh.pk_seq left join nhomkhachhang nkh on nkh.pk_seq = nkhkh.nkh_fk"+ 
	     		" left join (select sp_fk, max(nsp_fk) as nhomsp from nhomsanpham_sanpham group by sp_fk) nspsp on nspsp.sp_fk = sp.pk_seq "+
	     		" left join nhomsanpham nsp on nsp.pk_seq = nspsp.nhomsp  " +
	     		" where dh.trangthai = '1'and dh.ngaynhap >='"+obj.gettungay()+"' and dh.ngaynhap <= '"+obj.getdenngay()+"'" + sql;
	        
			
		System.out.print("Query DailySales Center: " + query);	    
		return query;
	}
	private boolean CreatePivotTable(OutputStream out, IStockintransit obj,String query)throws Exception {
		 Workbook workbook = new Workbook();	    
	     CreateStaticHeader(workbook,obj);	    
	     boolean isFillData = CreateStaticData(workbook,obj,query);
	     if(!isFillData)
	    	 return false;
	     workbook.save(out,0);
	     return true;		
	}

	private void CreateStaticHeader(Workbook workbook, IStockintransit obj)throws Exception {
		try{
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("DailySecondarySalesReport");

			Cells cells = worksheet.getCells();
			cells.setRowHeight(0, 50.0f);
			Cell cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell, Color.RED, true, 16,
					"Daily Secondary Sales Report");
			cell = cells.getCell("A2");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10,
					"From : " + obj.gettungay() + " To: " + obj.getdenngay());
			cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Date Create : "
					+ obj.getDateTime());
			cell = cells.getCell("A4");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10,
					"User : " + obj.getuserTen());

			cell = cells.getCell("AA1");		cell.setValue("Channel");
			cell = cells.getCell("AB1");		cell.setValue("Region");
			cell = cells.getCell("AC1");		cell.setValue("Area");
			cell = cells.getCell("AD1");		cell.setValue("Sales Sup");
			cell = cells.getCell("AE1");		cell.setValue("Distributor");
			cell = cells.getCell("AF1");		cell.setValue("Brands");
			cell = cells.getCell("AG1");		cell.setValue("Category");
			cell = cells.getCell("AH1");		cell.setValue("Distributor Code");
			cell = cells.getCell("AI1");		cell.setValue("SKU");
			cell = cells.getCell("AJ1");		cell.setValue("SKU Code");
			cell = cells.getCell("AK1");		cell.setValue("Customer");
			cell = cells.getCell("AL1");		cell.setValue("Outlet Class");
			cell = cells.getCell("AM1");		cell.setValue("Outlet Type");
			cell = cells.getCell("AN1");		cell.setValue("Date");
			cell = cells.getCell("AO1");		cell.setValue("Group SKU");
			cell = cells.getCell("AP1");		cell.setValue("Group Customer");
			cell = cells.getCell("AQ1");		cell.setValue("Amount");
			cell = cells.getCell("AR1");		cell.setValue("Piece");
			cell = cells.getCell("AS1");		cell.setValue("Quantity");
		}catch(Exception ex){
			throw new Exception("Khong tao duoc header cho bao cao");
		}
		
	}
	
	private boolean CreateStaticData(Workbook workbook, IStockintransit obj, String query)throws Exception 
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();		
		ResultSet rs = db.get(query);
		int i = 2;
		if (rs != null) {
			try {
				cells.setColumnWidth(0, 15.0f);
				cells.setColumnWidth(1, 15.0f);
				cells.setColumnWidth(2, 15.0f);
				cells.setColumnWidth(3, 15.0f);
				cells.setColumnWidth(4, 15.0f);
				cells.setColumnWidth(5, 15.0f);
				cells.setColumnWidth(6, 15.0f);
				cells.setColumnWidth(7, 15.0f);
				cells.setColumnWidth(8, 15.0f);
				cells.setColumnWidth(9, 15.0f);
				cells.setColumnWidth(10, 15.0f);
				cells.setColumnWidth(11, 15.0f);
				cells.setColumnWidth(12, 15.0f);
				cells.setColumnWidth(13, 15.0f);
				cells.setColumnWidth(14, 15.0f);
				cells.setColumnWidth(15, 15.0f);
				cells.setColumnWidth(16, 15.0f);
				cells.setColumnWidth(17, 15.0f);
				cells.setColumnWidth(18, 15.0f);
				cells.setColumnWidth(19, 15.0f);
				cells.setColumnWidth(20, 15.0f);
				cells.setColumnWidth(21, 15.0f);
				cells.setColumnWidth(22, 15.0f);

				for (int j = 12; j <= 22; j++)
					cells.setRowHeight(j, 14.0f);

				Cell cell = null;
				while (rs.next()) {
					cell = cells.getCell("AA" + Integer.toString(i));	cell.setValue(rs.getString("chanel"));
					cell = cells.getCell("AB" + Integer.toString(i));	cell.setValue(rs.getString("region"));
					cell = cells.getCell("AC" + Integer.toString(i));	cell.setValue(rs.getString("area"));
					cell = cells.getCell("AD" + Integer.toString(i));	cell.setValue(rs.getString("salesuper"));
					cell = cells.getCell("AE" + Integer.toString(i));	cell.setValue(rs.getString("distributor"));
					cell = cells.getCell("AF" + Integer.toString(i));	cell.setValue(rs.getString("brand"));
					cell = cells.getCell("AG" + Integer.toString(i));	cell.setValue(rs.getString("category"));
					cell = cells.getCell("AH" + Integer.toString(i));	cell.setValue(rs.getString("distcode"));
					cell = cells.getCell("AI" + Integer.toString(i));	cell.setValue(rs.getString("SKU"));
					cell = cells.getCell("AJ" + Integer.toString(i));	cell.setValue(rs.getString("SKUcode"));
					cell = cells.getCell("AK" + Integer.toString(i));	cell.setValue(rs.getString("customer"));
					cell = cells.getCell("AL" + Integer.toString(i));	cell.setValue(rs.getString("OutletClass"));
					cell = cells.getCell("AM" + Integer.toString(i));	cell.setValue(rs.getString("outlettype"));
					cell = cells.getCell("AN" + Integer.toString(i));	cell.setValue(rs.getString("offdate"));
					cell = cells.getCell("AO" + Integer.toString(i));	cell.setValue(rs.getString("nhomkhachhang"));
					cell = cells.getCell("AP" + Integer.toString(i));	cell.setValue(rs.getString("nhomsanpham"));
					cell = cells.getCell("AQ" + Integer.toString(i));	cell.setValue(rs.getFloat("amount"));
					cell = cells.getCell("AR" + Integer.toString(i));		cell.setValue(rs.getFloat("piece"));
					cell = cells.getCell("AS" + Integer.toString(i));		cell.setValue(rs.getFloat("quantity"));					
					++i;
					if(i>65000){
						if (rs != null) rs.close();
						if(db != null) db.shutDown();
						throw new Exception("Du lieu vuot qua gioi han file Excel. Vui long chon dieu kien can lay bao cao");							
					}
				}
				if (rs != null)
					rs.close();
				
				if(db != null) db.shutDown();
				
				if(i==2){
					throw new Exception("Xin loi,khong co bao cao voi dieu kien da chon....!!!");
				}
				ReportAPI.setHidden(workbook, 20);
				PivotTables pivotTables = worksheet.getPivotTables();
				String pos = Integer.toString(i - 1);
				int index = pivotTables.add("=DailySecondarySalesReport!AA1:AS" + pos, "A12","PivotTable");
				PivotTable pivotTable = pivotTables.get(index);
				pivotTable.setRowGrand(true);
				pivotTable.setColumnGrand(true);
				pivotTable.setAutoFormat(true);

				Hashtable<String, Integer> selected = new Hashtable<String, Integer>();
				selected.put("Channel",0);
			    selected.put("Region",1);
			    selected.put("Area",2);
			    selected.put("SalesSup",3);
			    selected.put("Distributor",4);		   
			    selected.put("Brands",5);
			    selected.put("Category",6);		   
			    selected.put("DistributorCode",7);
			    selected.put("SKU",8);
			    selected.put("SKUCode",9);
			    selected.put("Customer",10);
			    selected.put("OutletClass",11);
			    selected.put("OutletType",12);
			    selected.put("Date",13);
			    selected.put("GroupSKU",14);
			    selected.put("GroupCustomer",15);
			    selected.put("Amount",16);
			    selected.put("Piece",17);
			    selected.put("Quantity",18);
			    
			    for(String value:obj.getFieldShow()){
			    	int VALUE = selected.get(value);
			    	if(VALUE==16 || VALUE==17 || VALUE==18){
			    		pivotTable.addFieldToArea(PivotFieldType.DATA, VALUE);
			    	}else{
			    		pivotTable.addFieldToArea(PivotFieldType.ROW, VALUE);
			    	}
			    }
			    for(int indexRow=0;indexRow<pivotTable.getRowFields().size();++indexRow){
			    	pivotTable.getRowFields().get(indexRow).setAutoSubtotals(false);
			    }
			    if(pivotTable.getDataFields().size()>2){
			    	pivotTable.addFieldToArea(PivotFieldType.COLUMN, pivotTable.getDataField());
			    	pivotTable.getColumnFields().get(0).setDisplayName("Data");
			    }			    
			    	
			} catch (Exception e) {
				throw new Exception(e.getMessage()); 
			}
		} else {
			return false;
		}
		return true;
		
	}
}
