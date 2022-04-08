package geso.dms.center.servlets.reports;

import geso.dms.center.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Font;
import com.aspose.cells.PivotFieldType;
import com.aspose.cells.PivotTable;
import com.aspose.cells.PivotTables;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class SPInpp extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public SPInpp() {
        super();
        
    }
    private String gettenuser(String userId_){
		dbutils db=new dbutils();
		String sql_getnam="select ten from nhanvien where pk_seq="+ userId_;
		ResultSet rs_tenuser=db.get(sql_getnam);
		if(rs_tenuser!= null){
			try{
			  while(rs_tenuser.next()){
				  return rs_tenuser.getString("ten");
			  }
			}catch(Exception er){
				return "";
			}
		}
		return "";
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utility util=new Utility();
	    String userTen;
	    String tungay;
	    String denngay;
	    boolean bfasle=true;

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
	//	PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		userTen = (String)session.getAttribute("userTen");
		String querystring=request.getQueryString();
		String userId=	util.getUserId(querystring);
		session.setAttribute("tungay", "");
 		session.setAttribute("denngay","");
    	 session.setAttribute("loi", "");
		session.setAttribute("userTen", userTen);
		String nextJSP = "/AHF/pages/Center/SPInpp.jsp";
		response.sendRedirect(nextJSP);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utility util=new Utility();
	    String userTen;
	    String tungay;
	    String denngay;
	    boolean bfasle=true;

		OutputStream out = response.getOutputStream(); 

		try
		    {
			HttpSession session = request.getSession();
			 userTen = (String)session.getAttribute("userTen");
		 tungay=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
		 denngay=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
	
	    	response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Content-Disposition", "attachment; filename=SPI.xls");
	        CreatePivotTable(out,response,request, tungay, denngay, userTen, bfasle);
	     }
	    catch (Exception ex)
	    {
	        ex.printStackTrace();
	
	        // say sorry
	        response.setContentType("text/html");
	        PrintWriter writer = new PrintWriter(out);
	
	        writer.println("<html>");
	        writer.println("<head>");
	        writer.println("<title>sorry</title>");
	        writer.println("</head>");
	        writer.println("<body>");
	        writer.println("<h1>Xin loi, khong the tao pivot table...</h1>");
	        ex.printStackTrace(writer);
	        writer.println("</body>");
	        writer.println("</html>");
	        writer.close();
	    }
	}
	private void CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request, String tungay, String denngay, String userTen, boolean bfasle) throws IOException
    {    //khoi tao de viet pivottable
		//buoc 1
	     Workbook workbook = new Workbook();
	     //Buoc2 tao khung
	    //ham tao khu du lieu
	     CreateStaticHeader(workbook, tungay,denngay, userTen);
	     //Buoc3 
	     // day du lieu vao
	     CreateStaticData(workbook, tungay, denngay, bfasle);
	     if(bfasle==false){
	    	String loi="chua co bao cao trong thoi gian nay, vui long chon lai thoi gian xem bao cao";
	    	
	    	 HttpSession session = request.getSession();
	 		session.setAttribute("loi", loi);
	 		session.setAttribute("tungay", tungay);
	 		session.setAttribute("denngay", denngay);
	    	 session.setAttribute("userTen", userTen);
	 		String nextJSP = "/AHF/pages/Center/SPInpp.jsp";
	 		response.sendRedirect(nextJSP);
	     }else{
	    	 //Saving the Excel file
	    	 workbook.save(out);
	     }
   }
	
	private void CreateStaticHeader(Workbook workbook, String dateFrom, String dateTo, String UserName) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	   
	    
	    
	   	Style style;
	    //cells.setColumnWidth(0, 200.0f);
	    cells.setRowHeight(0, 20.0f);
	    Cell cell = cells.getCell("A1");  
	    cell.setValue("Secondary Sales - Purchase - Inventory(SPI) Report");   	
	    
	  
	    style = cell.getStyle();
	   
	   Font font2 = new Font();
       font2.setColor(Color.RED);//mau chu
       font2.setSize(16);// size chu
       style.setFont(font2); 
       style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu       
       cell.setStyle(style);
	    cell = cells.getCell("A2"); getCellStyle(workbook,"A2",Color.BLUE,true,10);
	    cell.setValue("From " + dateFrom + "      To " + dateTo);    
	    cell = cells.getCell("A3");getCellStyle(workbook,"A3",Color.BLUE,true,10);
	     cell.setValue("Reporting Date: " + this.getDateTime());
	    cell = cells.getCell("A4");getCellStyle(workbook,"A4",Color.BLUE,true,10);
	    cell.setValue("Created by:  " + UserName);
	    

	    

	  

	  
	  
	    //tieu de, hang dau tien cua bang du lieu, cell la toa do cua exel
	   
	    cell = cells.getCell("BA1"); cell.setValue("SKU");
	    cell = cells.getCell("BB1"); cell.setValue("Begin Inventory P");
	    cell = cells.getCell("BC1"); cell.setValue("Begin Inventory A");
	    cell = cells.getCell("BD1"); cell.setValue("Begin Inventory Q");
	    cell = cells.getCell("BE1"); cell.setValue("Purchase P");	  
	    cell = cells.getCell("BF1"); cell.setValue("Purchase A");
	    cell = cells.getCell("BG1"); cell.setValue("Purchase Q");
	    cell = cells.getCell("BH1"); cell.setValue("Secondary sale P");
	    cell = cells.getCell("BI1"); cell.setValue("Secondary sale A");
	    cell = cells.getCell("BJ1"); cell.setValue("Secondary sale Q");
	    cell = cells.getCell("BK1"); cell.setValue("Promotion out P");
	    cell = cells.getCell("BL1"); cell.setValue("Promotion out A");
	    cell = cells.getCell("BM1"); cell.setValue("Promotion out Q");
	    cell = cells.getCell("BN1"); cell.setValue("Promotion in P");
	    cell = cells.getCell("BO1"); cell.setValue("Promotion in A");
	    cell = cells.getCell("BP1"); cell.setValue("Promotion in Q");
	    cell = cells.getCell("BQ1"); cell.setValue("Adjust inventory P");
	    cell = cells.getCell("BR1"); cell.setValue("Adjust inventory A");
	    cell = cells.getCell("BS1"); cell.setValue("Adjust inventory Q");
	    cell = cells.getCell("BT1"); cell.setValue("Ending Inventory P");
	    cell = cells.getCell("BU1"); cell.setValue("Ending Inventory A");
	    cell = cells.getCell("BV1"); cell.setValue("Ending Inventory Q");
	    cell = cells.getCell("BW1"); cell.setValue("Warehouse");
	    cell = cells.getCell("BX1"); cell.setValue("Distributor Code");
	    cell = cells.getCell("BY1"); cell.setValue("Business Unit");
	    cell = cells.getCell("BZ1"); cell.setValue("Brands");
	    cell = cells.getCell("CA1"); cell.setValue("Category");
	    
	    
	    worksheet.setName("Promotion Report (npp)");
	}
	private void CreateStaticData(Workbook workbook, String tungay, String denngay, boolean bfasle) 
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	   
	   
	    
	    //khoi tao ket noi csdl
		
		String sql  = "select Channel, Region, Area, Province, Distributor_code, Distributor, Sales_Sup, Business_unit, Warehouse, Brands, Category, SKU, Begin_Inventory_P, Begin_Inventory_A,"+
				" Begin_Inventory_Q, Purchase_P, Purchase_A, Purchase_Q, Secondary_Sale_P, Secondary_Sale_A, Secondary_Sale_Q, Promotion_in_P, Promotion_in_A, Promotion_in_Q, Promotion_out_P,"+
				" Promotion_out_A, Promotion_out_Q, Adjust_inventory_P, Adjust_inventory_A, Adjust_inventory_Q,"+
				" (Begin_Inventory_P + Purchase_P - Secondary_Sale_P + (Promotion_in_P - Promotion_out_P) +  Adjust_inventory_P) as Ending_inventory_P,"+
				" (Begin_Inventory_A + Purchase_A - Secondary_Sale_A + (Promotion_in_A - Promotion_out_A) +  Adjust_inventory_A) as Ending_inventory_A,"+
				" (Begin_Inventory_Q + Purchase_Q - Secondary_Sale_Q + (Promotion_in_Q - Promotion_out_Q) +  Adjust_inventory_Q) as Ending_inventory_Q"+
				" from ( select  kbh.ten as Channel,"+
				" v.ten as Region,"+
				" kv.ten as Area,  "+
				" t.ten as Province,"+
				" npp.pk_seq as Distributor_code,"+
				" npp.ten as Distributor,"+
				" gsbh.ten as Sales_Sup,"+
				" dvkd.donvikinhdoanh as Business_unit,"+
				" kh.ten as Warehouse,"+
				" nh.ten as Brands,"+
				" cl.ten as Category,"+
				" sp.ten as SKU,"+
				" case when tkn.soluong is null then 0 else tkn.soluong end  as Begin_Inventory_P,"+
        		" case when tkn.Amount is null then 0 else tkn.Amount end as Begin_Inventory_A,"+
        		" case when tkn.soluong/qc.soluong1 is null then 0 else tkn.soluong/qc.soluong1 end as Begin_Inventory_Q,"+
				" case when hdn.soluong is null then 0 else hdn.soluong  end as Purchase_P,"+
        		" case when hdn.Amount is null then 0 else hdn.Amount  end as Purchase_A,"+
        		" case when hdn.soluong is null then 0 else hdn.soluong /qc.soluong1 end as Purchase_Q,"+
				" case when ds.soluong is null then 0 else ds.soluong end as Secondary_Sale_P,"+
        		" case when ds.tongtien is null then 0 else ds.tongtien end as Secondary_Sale_A,"+
  				" case when ds.soluong is null then 0 else ds.soluong/qc.soluong1 end as Secondary_Sale_Q,"+      
				" case when hkm.soluong is null then 0 else hkm.soluong end as Promotion_in_P ,"+
        		" case when hkm.Amount is null then 0 else hkm.Amount end as Promotion_in_A ,"+
        		" case when hkm.soluong is null then 0 else hkm.soluong/qc.soluong1 end as Promotion_in_Q ,"+      
				" case when km.soluong is null then 0 else km.soluong end as Promotion_out_P,"+
    			" case when km.Amount is null then 0 else km.Amount end as Promotion_out_A,"+
        		" case when km.soluong is null then 0 else km.soluong/qc.soluong1 end as Promotion_out_Q,"+
				" case when dc.dieuchinh is null then 0 else dc.dieuchinh end as Adjust_inventory_P,"+
        		" case when dc.Amount is null then 0 else dc.Amount end as Adjust_inventory_A,"+
	    		" case when dc.dieuchinh is null then 0 else dc.dieuchinh/qc.soluong1 end as Adjust_inventory_Q"+		
	    		" from (select npp_gs.kbh_fk, b.kho_fk, b.npp_fk, b.sanpham_fk, b.soluong, (a.giamua * b.soluong) as Amount,npp_gs.gsbh_fk from"+ 
	    		" (select distinct kho_fk, npp_fk, sanpham_fk, giamua from nhapp_kho where npp_fk = '100015') a"+
	    		" inner join (select kho_fk, npp_fk, sanpham_fk, sum(soluong) as soluong from tonkhongay "+
	    		" where npp_fk = '100015' and ngay < '"+tungay+"' group by kho_fk, npp_fk, sanpham_fk) b on a.kho_fk = b.kho_fk and a.npp_fk = b.npp_fk and a.sanpham_fk = b.sanpham_fk"+
	    		" inner join (select * from nhapp_giamsatbh e, (select * from giamsatbanhang where dvkd_fk = '100026') gsbh "+
	    		" where e.gsbh_fk = gsbh.pk_seq and e.npp_fk = '100015') npp_gs on npp_gs.npp_fk = a.npp_fk) tkn"+
	    		" left join (select b.npp_fk,sp.pk_seq as sanpham_fk,b.kbh_fk,ddh.gsbh_fk,b.kho_fk,sum(cast(a.soluong as int)) as soluong ,sum(cast(a.tienbvat as int)) as Amount"+
	    		" from nhaphang_sp a"+ 
                " inner join nhaphang b on a.nhaphang_fk = b.pk_seq "+
                " inner join sanpham sp on sp.ma = a.sanpham_fk "+
                " inner join dondathang ddh on ddh.pk_seq = b.dathang_fk"+
                " where b.ngaynhan > '"+tungay+"' and b.ngaynhan <'"+denngay+"'and b.npp_fk ='100015' and b.dathang_fk in (select pk_seq from dondathang where loaidonhang ='1')"+ 
                " group by sp.pk_seq,b.kbh_fk,b.npp_fk,ddh.gsbh_fk,b.kho_fk ) hdn"+
                " on tkn.npp_fk = hdn.npp_fk and tkn.sanpham_fk = hdn.sanpham_fk and "+
                " tkn.kbh_fk = hdn.kbh_fk and tkn.gsbh_fk = hdn.gsbh_fk and tkn.kho_fk =hdn.kho_fk"+
                " left join (select dh.npp_fk,dh.kbh_fk,dh.gsbh_fk,dhsp.sanpham_fk,dh.kho_fk ,sum(dhsp.soluong) as soluong,sum(dhsp.soluong*dhsp.giamua) as tongtien"+
                " from donhang_sanpham dhsp "+
                " inner join donhang dh on dhsp.donhang_fk = dh.pk_seq  where dh.ngaynhap > '"+tungay+"' and dh.ngaynhap < '"+denngay+"' and dh.npp_fk = '100015'"+
                " group by dh.npp_fk,dh.kbh_fk,dhsp.sanpham_fk,dh.gsbh_fk,dh.kho_fk) ds"+
                " on tkn.npp_fk = ds.npp_fk and ds.sanpham_fk = tkn.sanpham_fk and tkn.kbh_fk = ds.kbh_fk and tkn.gsbh_fk = ds.gsbh_fk and ds.kho_fk = tkn.kho_fk"+
                " left join (select b.npp_fk,sp.pk_seq as sanpham_fk,b.kbh_fk,ddh.gsbh_fk,b.kho_fk,sum(cast(a.soluong as int)) as soluong ,sum(cast(tienbvat as int)) as Amount"+
                " from nhaphang_sp a "+
                " inner join nhaphang b on a.nhaphang_fk = b.pk_seq"+              
                " inner join sanpham sp on sp.ma = a.sanpham_fk "+
                " inner join dondathang ddh on ddh.pk_seq = b.dathang_fk"+
                " where b.ngaynhan > '"+tungay+"' and b.ngaynhan <'"+denngay+"' and b.npp_fk = '100015' and b.trangthai ='1' and b.dathang_fk in (select pk_seq from dondathang where loaidonhang ='2')"+
                " group by sp.pk_seq,b.kbh_fk,b.npp_fk,ddh.gsbh_fk,b.kho_fk) hkm"+
                " on tkn.npp_fk = hkm.npp_fk and tkn.sanpham_fk = hkm.sanpham_fk and tkn.kbh_fk = hkm.kbh_fk  and hkm.gsbh_fk = tkn.gsbh_fk and hkm.kho_fk = tkn.kho_fk"+
                " left join (select dh.npp_fk,dh.kbh_fk,dh.gsbh_fk,sp.pk_seq as sanpham_fk,ctkm.kho_fk,sum(soluong) as soluong,sum(km.tonggiatri) as Amount"+
                " from DONHANG_CTKM_TRAKM km "+
                " inner join donhang dh on km.donhangid = dh.pk_seq"+ 
                " inner join sanpham sp on km.spma = sp.ma "+
                " inner join ctkhuyenmai ctkm on ctkm.pk_seq = km.ctkmid"+
                " where dh.ngaynhap > '"+tungay+"' and dh.ngaynhap <'"+denngay+"' and dh.npp_fk = '100015'"+
                " group by dh.npp_fk,dh.kbh_fk,sp.pk_seq,dh.gsbh_fk,ctkm.kho_fk ) km "+
                " on tkn.npp_fk = km.npp_fk and tkn.sanpham_fk = km.sanpham_fk and tkn.kbh_fk = km.kbh_fk and tkn.gsbh_fk = km.gsbh_fk and km.kho_fk = tkn.kho_fk"+
                " left join ("+
                " select tk.npp_fk,npp_gs.gsbh_fk,gsbh.kbh_fk,tk.kho_fk,tksp.sanpham_fk,sum(cast(tksp.dieuchinh as int)) as dieuchinh,sum(cast(tksp.thanhtien as int)) as Amount"+ 
                " from dieuchinhtonkho_sp tksp "+
                " inner join dieuchinhtonkho tk on tk.pk_seq = tksp.dieuchinhtonkho_fk"+ 
                " inner join NHAPP_GIAMSATBH npp_gs on npp_gs.npp_fk = tk.npp_fk"+
                " inner join giamsatbanhang gsbh on gsbh.pk_seq = npp_gs.gsbh_fk"+
                " where tk.ngaydc > '"+tungay+"' and tk.ngaydc <'"+denngay+"' and tk.npp_fk = '100015'"+
                " group by tk.npp_fk,tk.kbh_fk,npp_gs.gsbh_fk,gsbh.kbh_fk,tksp.sanpham_fk,tk.kho_fk"+
                " ) dc"+
                " on dc.npp_fk = tkn.npp_fk and dc.kbh_fk = tkn.kbh_fk and dc.gsbh_fk = tkn.gsbh_fk"+ 
                " and dc.sanpham_fk = tkn.sanpham_fk and dc.kho_fk = tkn.kho_fk"+
                " inner join kenhbanhang kbh on kbh.pk_seq = tkn.kbh_fk"+
                " inner join nhaphanphoi npp on npp.pk_seq = tkn.npp_fk"+
                " left join khuvuc kv on kv.pk_seq = npp.khuvuc_fk"+
                " left join vung v on v.pk_seq = kv.vung_fk"+
                " inner join kho kh on kh.pk_seq = tkn.kho_fk"+
                " left join tinhthanh t on t.pk_seq =npp.tinhthanh_fk"+
                " left join giamsatbanhang gsbh on gsbh.pk_seq = tkn.gsbh_fk"+
                " inner join donvikinhdoanh dvkd on dvkd.pk_seq = gsbh.dvkd_fk"+
                " inner join sanpham sp on sp.pk_seq = tkn.sanpham_fk"+
                " inner join nhanhang nh on nh.pk_seq = sp.nhanhang_fk"+
                " inner join chungloai cl on cl.pk_seq = sp.chungloai_fk"+
                " left join quycach qc on qc.dvdl1_fk = sp.dvdl_fk and qc.sanpham_fk = sp.pk_seq ) as spi";


		System.out.println("Lay Du Lieu :"+sql);
		ResultSet rs = db.get(sql);
		
		int i = 2;
		if(rs!=null)
		{
			
			
			try 
			{// se do rong cho cac cot se dung
				cells.setColumnWidth(0, 15.0f);
				cells.setColumnWidth(1, 15.0f);
				cells.setColumnWidth(2, 15.0f);
				cells.setColumnWidth(3, 15.0f);
				cells.setColumnWidth(4, 15.0f);			
				cells.setColumnWidth(5, 15.0f);	
				cells.setColumnWidth(6, 15.0f);	
				cells.setColumnWidth(7, 15.0f);
				
			
				
				Cell cell = null;
				while(rs.next())// lap den cuoi bang du lieu
				{
				
					//lay tu co so du lieu, gan bien
					String SKU = rs.getString("SKU");
					String BeginInventory_P =rs.getString("Begin_Inventory_P");
					String BeginInventory_A =rs.getString("Begin_Inventory_A");
					String BeginInventory_Q =rs.getString("Begin_Inventory_Q");
					String Purchase_P =rs.getString("Purchase_P");			
					String Purchase_A =rs.getString("Purchase_A");
					String Purchase_Q =rs.getString("Purchase_Q");
					String SecondarySale_P = rs.getString("Secondary_Sale_P");
					String SecondarySale_A = rs.getString("Secondary_Sale_A");
					String SecondarySale_Q = rs.getString("Secondary_Sale_Q");
					String PromotionOut_P =rs.getString("Promotion_out_P");
					String PromotionOut_A =rs.getString("Promotion_out_A");
					String PromotionOut_Q =rs.getString("Promotion_out_Q");
					String PromotionIn_P = rs.getString("Promotion_in_P");
					String PromotionIn_A = rs.getString("Promotion_in_A");
					String PromotionIn_Q = rs.getString("Promotion_in_Q");
					String AdjustInventory_P = rs.getString("Adjust_inventory_P");
					String AdjustInventory_A = rs.getString("Adjust_inventory_A");
					String AdjustInventory_Q = rs.getString("Adjust_inventory_Q");
					String EndingInventory_P = rs.getString("Ending_inventory_P");
					String EndingInventory_A = rs.getString("Ending_inventory_A");
					String EndingInventory_Q = rs.getString("Ending_inventory_Q");			
					String DistributorCode = rs.getString("Distributor_code");
					String BusinessUnit = rs.getString("Business_unit");
					String Barnds = rs.getString("Brands");
					String Category = rs.getString("Category");
					String Warehouse = rs.getString("Warehouse");
					
					
					
					cell = cells.getCell("BA" + Integer.toString(i)); cell.setValue(SKU);
					cell = cells.getCell("BB" + Integer.toString(i)); cell.setValue(Float.parseFloat(BeginInventory_P));
					cell = cells.getCell("BC" + Integer.toString(i)); cell.setValue(Float.parseFloat(BeginInventory_A));
					cell = cells.getCell("BD" + Integer.toString(i)); cell.setValue(Float.parseFloat(BeginInventory_Q));
					cell = cells.getCell("BE" + Integer.toString(i)); cell.setValue(Float.parseFloat(Purchase_P));
					cell = cells.getCell("BF" + Integer.toString(i)); cell.setValue(Float.parseFloat(Purchase_A));
					cell = cells.getCell("BG" + Integer.toString(i)); cell.setValue(Float.parseFloat(Purchase_Q));
					cell = cells.getCell("BH" + Integer.toString(i)); cell.setValue(Float.parseFloat(SecondarySale_P));
					cell = cells.getCell("BI" + Integer.toString(i)); cell.setValue(Float.parseFloat(SecondarySale_A));
					cell = cells.getCell("BJ" + Integer.toString(i)); cell.setValue(Float.parseFloat(SecondarySale_Q));
					cell = cells.getCell("BK" + Integer.toString(i)); cell.setValue(Float.parseFloat(PromotionOut_P));
					cell = cells.getCell("BL" + Integer.toString(i)); cell.setValue(Float.parseFloat(PromotionOut_A));
					cell = cells.getCell("BM" + Integer.toString(i)); cell.setValue(Float.parseFloat(PromotionOut_Q));
					cell = cells.getCell("BN" + Integer.toString(i)); cell.setValue(Float.parseFloat(PromotionIn_P));
					cell = cells.getCell("BO" + Integer.toString(i)); cell.setValue(Float.parseFloat(PromotionIn_A));
					cell = cells.getCell("BP" + Integer.toString(i)); cell.setValue(Float.parseFloat(PromotionIn_Q));
					cell = cells.getCell("BQ" + Integer.toString(i)); cell.setValue(Float.parseFloat(AdjustInventory_P));
					cell = cells.getCell("BR" + Integer.toString(i)); cell.setValue(Float.parseFloat(AdjustInventory_A));
					cell = cells.getCell("BS" + Integer.toString(i)); cell.setValue(Float.parseFloat(AdjustInventory_Q));
					cell = cells.getCell("BT" + Integer.toString(i)); cell.setValue(Float.parseFloat(EndingInventory_P));
					cell = cells.getCell("BU" + Integer.toString(i)); cell.setValue(Float.parseFloat(EndingInventory_A));
					cell = cells.getCell("BV" + Integer.toString(i)); cell.setValue(Float.parseFloat(EndingInventory_Q));
					cell = cells.getCell("BW" + Integer.toString(i)); cell.setValue(DistributorCode);
					cell = cells.getCell("BX" + Integer.toString(i)); cell.setValue(BusinessUnit);
					cell = cells.getCell("BY" + Integer.toString(i)); cell.setValue(Barnds);
					cell = cells.getCell("BZ" + Integer.toString(i)); cell.setValue(Category);
					cell = cells.getCell("CA" + Integer.toString(i)); cell.setValue(Warehouse);
				
					i++;
				}
			
		
		//xong buoc dua du lieu vao exel
		
		
		
	    
	
		//create pivot
		 getAn(workbook,90);
		PivotTables pivotTables = worksheet.getPivotTables();

		String pos = Integer.toString(i-1);
	    int index = pivotTables.add("=BA1:CA" + pos,"A12","PivotTableDemo");
        // System.out.println("index:"+index);
	    //Accessing the instance of the newly added PivotTable
	    PivotTable pivotTable = pivotTables.get(index);//truyen index

	    pivotTable.setRowGrand(false);
	    pivotTable.setColumnGrand(true);
	    pivotTable.setAutoFormat(true);
	    //Setting the PivotTable autoformat type.
	    //pivotTable.setAutoFormatType(PivotTableAutoFormatType.REPORT6);
 
	   
	  
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 0);	pivotTable.getRowFields().get(0).setAutoSubtotals(false);  	  
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 1);	pivotTable.getDataFields().get(0).setDisplayName("Begin Inventory P");
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 2);	pivotTable.getDataFields().get(1).setDisplayName("Begin Inventory A");
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 3);	pivotTable.getDataFields().get(2).setDisplayName("Begin Inventory Q");
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 4);	pivotTable.getDataFields().get(3).setDisplayName("Purchase P");
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 5);	pivotTable.getDataFields().get(4).setDisplayName("Purchase A");
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 6);	pivotTable.getDataFields().get(5).setDisplayName("Purchase Q");
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 7);	pivotTable.getDataFields().get(6).setDisplayName("Secondary sale P");
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 8);	pivotTable.getDataFields().get(7).setDisplayName("Secondary sale A");
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 9);	pivotTable.getDataFields().get(8).setDisplayName("Secondary sale Q");
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 10);	pivotTable.getDataFields().get(9).setDisplayName("Promotion out P");
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 11);	pivotTable.getDataFields().get(10).setDisplayName("Promotion out A");
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 12);	pivotTable.getDataFields().get(11).setDisplayName("Promotion out Q");
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 13);	pivotTable.getDataFields().get(12).setDisplayName("Promotion in  P");
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 14);	pivotTable.getDataFields().get(13).setDisplayName("Promotion in A");
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 15);	pivotTable.getDataFields().get(14).setDisplayName("Promotion in  Q");
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 16);	pivotTable.getDataFields().get(15).setDisplayName("Adjust inventory P");
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 17);	pivotTable.getDataFields().get(16).setDisplayName("Adjust inventory A");
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 18);	pivotTable.getDataFields().get(17).setDisplayName("Adjust inventory Q");
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 19);	pivotTable.getDataFields().get(18).setDisplayName("Ending inventory P");
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 20);	pivotTable.getDataFields().get(19).setDisplayName("Ending inventory A");
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 21);	pivotTable.getDataFields().get(20).setDisplayName("Ending inventory Q");
	  
	    pivotTable.addFieldToArea(PivotFieldType.COLUMN, pivotTable.getDataField()); 
	    for (i =0 ; i<=20; i++)
	    {
	    	 pivotTable.getDataFields().get(i).setNumber(3);
	  	   
	    }
	   
	  
	   
	    bfasle=true;
			}
			catch (Exception e){
				bfasle=false;
				e.printStackTrace(); 
				System.out.println("Error : Inventory Report : " +e.toString());
			}
		}else{
			bfasle=false;
		}
		
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
	private void getAn(Workbook workbook,int i)
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	    for(int j = 52; j <= i; j++)
	    { 
	    	cells.hideColumn(j);
	    }
		
	}
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh-mm-ss");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}
