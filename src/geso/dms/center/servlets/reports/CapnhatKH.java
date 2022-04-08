package geso.dms.center.servlets.reports;

import geso.dms.center.beans.Router.IDRouter;
import geso.dms.center.beans.Router.imp.Router;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

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
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class CapnhatKH extends HttpServlet {
	
       
	private static final long serialVersionUID = 1L;
	 
    public CapnhatKH() {
        super();
        
    }
    
    private Hashtable< Integer, String > htbValueCell ()
	{
		Hashtable<Integer, String> htb=new Hashtable<Integer, String>();
		htb.put(1,"E");htb.put(2,"F");htb.put(3,"G");htb.put(4,"H");htb.put(5,"I");
		htb.put(6,"J");htb.put(7,"K");htb.put(8,"L");htb.put(9,"M");htb.put(10,"N");
		htb.put(11,"O");htb.put(12,"P");htb.put(13,"Q");htb.put(14,"R");htb.put(15,"S");
		htb.put(16,"T");htb.put(17,"U");htb.put(18,"V");htb.put(19,"W");htb.put(20,"X");
		htb.put(21,"Y");htb.put(22,"Z");htb.put(23,"AA");htb.put(24,"AB");htb.put(25,"AC");
		htb.put(26,"AD");htb.put(27,"AE");htb.put(28,"AF");htb.put(29,"AG");htb.put(30,"AH");
		htb.put(31,"AI");htb.put(32,"AJ");htb.put(33,"AK");htb.put(34,"AL");htb.put(35,"AM");
		htb.put(36,"AN");htb.put(37,"AO");htb.put(38,"AP");htb.put(39,"AQ");htb.put(40,"AR");
		htb.put(41,"AS");htb.put(42,"AT");htb.put(43,"AU");htb.put(44,"AV");htb.put(45,"AW");
		htb.put(46,"AX");htb.put(47,"AY");htb.put(48,"AZ");htb.put(49,"BA");htb.put(50,"BB");		
		return htb; 
	}
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utility util=new Utility();	 
		dbutils db;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");	
		HttpSession session = request.getSession();
		String userTen = (String)session.getAttribute("userTen");
		String querystring=request.getQueryString();
		String userId = util.getUserId(querystring);
		 String Id = util.getId(querystring); 
		session.setAttribute("userId", userId);
		/*session.setAttribute("tungay", "");
 		session.setAttribute("denngay","");*/
    	session.setAttribute("loi", "");
		session.setAttribute("userTen", userTen);	
		IDRouter obj = new Router();
		String action = util.getAction(querystring);	    	  	    	 
	    
	    
	    if (action.equals("delete")){	   		  	    	
	    	Delete(Id);	    	
	    }
	    
	    else if(action.equals("chot")){	    		 	  
	    	
	    	Chot(Id);
	    }
	    
	    else if(action.equals("display"))
	    {
	    	db = new dbutils();        	            	    	
    		System.out.println("ID : "+Id);
    		OutputStream out = response.getOutputStream();
        	response.setContentType("application/xlsm");
	        response.setHeader("Content-Disposition","attachment; filename=XuatThongTin.xlsm");
	        
	        //String query = setQuery2(id);
	        String sql ="select distinct(npp.pk_seq), npp.ten from capnhatkh_chitiet a "+
	        			" inner join nhaphanphoi npp on npp.pk_seq = a.npp_fk "+
	        			" where capnhatkh_fk = '"+ Id +"'";
	        System.out.println("SQL : "+sql);
	        ResultSet rs1 = db.get(sql);
	        String nppId = "";
	        String tennpp = "";
	        try {
				rs1.next();
				nppId = rs1.getString("pk_seq");
				tennpp = rs1.getString("ten");
			} catch (SQLException e) {				
				e.printStackTrace();
			}
	        	        
	        try {
				CreatePivotTableXuat(out, obj, Id, userId, userTen, tennpp, nppId);
			} catch (Exception e) {			
				e.printStackTrace();
				System.out.println("Loi : "+e.toString());
			}	
	        	        
	    }
		
    	obj.setStatus("1");
		obj.init();		
		obj.createCapnhatKHList();
		
		session.setAttribute("obj",obj);
		String nextJSP = "/AHF/pages/Center/CapNhatKH.jsp";
		response.sendRedirect(nextJSP);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		dbutils db;
		Utility util=new Utility();	 
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
	//	PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
        IDRouter obj = new Router();
        String userId = (String) session.getAttribute("userId");
        String userTen = (String)session.getAttribute("userTen");
        String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
        
        String vungId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")));
        if(vungId == null)
        	vungId = "";
        obj.setvungId(vungId);
        
        String kenhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")));
        if(kenhId == null)
        	kenhId = "";
        obj.setKenhId(kenhId);
        
        if(nppId ==null)
        	nppId ="";
        obj.setnppId(nppId);
        
        String ddkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdId")));
        
        if(ddkdId == null)
        	ddkdId ="";
        obj.setddkdId(ddkdId);
        
        String tuyenId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tuyenId")));
        if(tuyenId == null)
        	tuyenId ="";
        obj.settuyenId(tuyenId);
        
        obj.init();
        obj.createCapnhatKHList();
        String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
        System.out.println("Action la "+action);
        
        if(action.equals("export")){
        	String sql ="select ten from nhaphanphoi where pk_seq = '"+nppId+"'";
            db = new dbutils();
            ResultSet rs = db.get(sql); 
            String tennpp = "";
    		try {
				rs.next();
				tennpp = rs.getString("ten");
	    		rs.close();  
			} catch (SQLException e) {				
				e.printStackTrace();
			}
    		 
    		            
        	OutputStream out = response.getOutputStream(); 
			userTen = (String)session.getAttribute("userTen");
			
			response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Content-Disposition", "attachment; filename=CapNhatKH.xls");
	        
	    	/*response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Content-Disposition", "attachment; filename=RouteReport.xls");*/
	        
			CreatePivotTable(out,response,request, obj, userId, userTen, tennpp, obj.getnppId()); //userTen, nppId, tuyenId, ddkdId, kenhId, userId );

        }
        else if(action.equals("new"))
        {        	
        	IDRouter cnkhBean = (IDRouter) new Router();
        	cnkhBean.setuserId(userId);
        	session.setAttribute("cnkhBean", cnkhBean);
        	String nextJSP = "/AHF/pages/Center/CapNhatKHNew.jsp";
        	response.sendRedirect(nextJSP);
        }             
        
        else{
        	
        	String khuvucId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")));
        	if(khuvucId.trim().length() > 0)
	        	obj.setkhuvucId(khuvucId);        	
        	obj.setStatus("1");
        	obj.createNPP();  
        	obj.createCapnhatKHList();
        	
        	session.setAttribute("obj",obj);
        	String nextJSP = "/AHF/pages/Center/CapNhatKH.jsp";
        	response.sendRedirect(nextJSP);
        }
	}
	
	private void Delete(String id){
		dbutils db = new dbutils();
		
		db.update("delete from CAPNHATKH_CHITIET where CAPNHATKH_FK = '" + id + "'");
		db.update("delete from CAPNHATKHACHHANG where pk_seq = '" + id + "'");
		db.shutDown();		
	}	
	
	private void Chot(String id){
		dbutils db = new dbutils();
		
		db.update("update CAPNHATKHACHHANG set trangthai = '1' where pk_seq = '" + id + "'");		
		db.shutDown();		
	}	
	
	/*private String setQuery2(String id) {
		String sql = "select npp.TEN as tennpp, ddkd.TEN as tenddkd, a.tbh_fk as tbh,kh.SMARTID as makh, kh.TEN as tenkh, sp.TEN as tensp, a.soluong from CAPNHATKH_CHITIET a "+
					 "inner join CAPNHATKHACHHANG cnkh on cnkh.PK_SEQ = a.capnhatkh_fk "+
					 "inner join NHAPHANPHOI npp on npp.PK_SEQ = a.npp_fk "+ 
					 "inner join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = a.ddkd_fk "+ 
					"inner join KHACHHANG kh on kh.SMARTID = a.makh "+
					"inner join KHACHHANG_TUYENBH kh_tbh on kh_tbh.KHACHHANG_FK = kh.PK_SEQ "+
					"inner join TUYENBANHANG tbh on tbh.PK_SEQ = kh_tbh.TBH_FK and tbh.DDKD_FK = ddkd.PK_SEQ and tbh.NPP_FK = npp.PK_SEQ "+
					"inner join SANPHAM sp on sp.MA = a.masp "+
					"where cnkh.PK_SEQ = '"+ id +"' "+
					"order by tennpp, tenddkd, tbh, makh, tenkh, tensp";
		return sql;
	}*/
	
	/*private void CreatePivotTable2(OutputStream out,HttpServletResponse response,HttpServletRequest request, IDRouter obj, String userId, String userTen, String tennpp, String nppId, String Id) throws IOException
    {    					
			FileInputStream fstream = null;
			String chuoi = "";
			Workbook workbook = new Workbook();			
			
							
			fstream = new FileInputStream(chuoi);
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			String sql="select b.pk_seq, b.ten from capnhatkh_chitiet a "+
					   "inner join daidienkinhdoanh b on b.pk_seq = a.ddkd_fk "+
					   "where capnhatkh_fk = '"+ Id +"' order by ddkd_fk asc";		    		   		    	 
			
		    dbutils db=new dbutils();
		    ResultSet rs=db.get(sql);		    		    
		    try {
				rs.next();
				CreatePivotTableXuat(obj, Id, userId ,userTen,tennpp, nppId, rs.getString("pk_seq"),rs.getString("ten"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 				    				    				    	    	    			    			    			   
		    db.shutDown();		   
		    workbook.save(out);						     
   }*/
	
	private boolean CreatePivotTableXuat(OutputStream out, IDRouter obj, String id, String userId,String userTen, String tennpp, String nppid) throws Exception 
	{
		boolean isFillData = false;
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
		String chuoi=getServletContext().getInitParameter("path") + "\\ThongtinKH-SP.xlsm";
		fstream = new FileInputStream(chuoi);
		
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		
		CreateHeaderXuat(workbook, obj, userTen, tennpp, nppid);
		isFillData = CreateStaticDataXuat(workbook, obj, userId, id);
		
		if (!isFillData){
			if(fstream != null) 
				fstream.close();
			return false;
		}
		
		workbook.save(out);
		fstream.close();
		return true;
	}
	
	private void CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request, IDRouter obj, String userId, String userTen, String tennpp, String nppId) /*String userTen, String nppId, String tuyenId, String ddkdId, String kenhId, String userId)*/ throws IOException 
    {   
		Workbook workbook = new Workbook();
	    workbook.setFileFormatType(FileFormatType.EXCEL2003);
	     
		String sql="select pk_seq, ten  from daidienkinhdoanh where npp_fk="+obj.getnppId() +" and trangthai=1";
	    
	     if(obj.getddkdId().length() >0){
	    	 sql=sql + " and pk_seq="+ obj.getddkdId();
	    	 
	     }	     
	     dbutils db=new dbutils();
	     ResultSet rs=db.get(sql);
	     if(rs!=null){
	    	 try{
	    		 int i=0;
	    	 while (rs.next()){
	    		 Worksheets worksheets = workbook.getWorksheets();
	    	
	    		 Worksheet worksheet = worksheets.addSheet(rs.getString("pk_seq")+"-"+rs.getString("ten"));	    		 
	    		 CreateKHTonkho(worksheet, obj, userId, userTen, tennpp, nppId, rs.getString("pk_seq"), rs.getString("ten"));
	    		 ////System.out.println("Lan thu "+i);
	    	     i++;	    		
	    	 }
	    	 rs.close();
	    	 }catch(Exception er){
	    		 //System.out.println(er.toString());
	    	 }
	     }
	     db.shutDown();
	    

	     workbook.save(out);
   }
	
	private void CreateHeaderXuat(Workbook workbook, IDRouter obj, String userTen, String tennpp, String nppId)  
	{	
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");		
		Cells cells = worksheet.getCells();
	   	Style style;
	    //cells.setColumnWidth(0, 200.0f);
	    cells.setRowHeight(0, 20.0f);
	    Cell cell = cells.getCell("A1");  
	    cell.setValue("CẬP NHẬT KHÁCH HÀNG");   		      
	    style = cell.getStyle();
	  
	    Font font2 = new Font();
	    font2.setColor(Color.RED);//mau chu
	    font2.setSize(16);// size chu
	    font2.setBold(true);
	    
	    style.setFont(font2); 
	    style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu       
	    cell.setStyle(style); 
	    
	    cell = cells.getCell("A3");
	    ReportAPI.getCellStyle(cell,Color.BLUE,true,10, "Ngày tạo: " + this.getDateTime());
	   
	    cell = cells.getCell("A4");
	    ReportAPI.getCellStyle(cell,Color.BLUE,true,10, "Tạo bởi:  " + userTen);	    	    
	    
	    cell = cells.getCell("A5");
	    ReportAPI.getCellStyle(cell,Color.BLUE,true,10, "Mã NPP: "+nppId);	    	    	 
	    
	    cell = cells.getCell("A6");
	    ReportAPI.getCellStyle(cell,Color.BLUE,true,10, "Tên NPP:  " + tennpp);		    	   
	    
	    cell = cells.getCell("EA1");		cell.setValue("NhaPhanPhoi");
		cell = cells.getCell("EB1");		cell.setValue("DaiDienKinhDoanh");
		cell = cells.getCell("EC1");		cell.setValue("TuyenBanHang");
		cell = cells.getCell("ED1");		cell.setValue("MaKhachHang");
		cell = cells.getCell("EE1");		cell.setValue("KhachHang");			
		cell = cells.getCell("EF1");		cell.setValue("SanPham");		
		cell = cells.getCell("EG1");		cell.setValue("SoLuong");
	}
	
	private void CreateStaticData(Worksheet worksheet, IDRouter obj, String userId, String ddkd, String ddkdten)
	{
		dbutils db = new dbutils();
	    Cells cells = worksheet.getCells();
	    String st="";
	    //int dem =0;
	    
	    if(obj.getnppId().length()>0)
	    {
	    	st = st + "tbh.npp_fk ='"+ obj.getnppId() +"'";
	    }
	    
	    if(obj.gettuyenId().length()>0)
	    {
	    	if(st.length()>0)
	    		st = st + " and tbh.ngaylamviec ='"+ obj.gettuyenId() +"' ";
	    	else
	    		st ="tbh.ngaylamviec ='"+ obj.gettuyenId() +"' ";
	    }
	    
	    if(obj.getddkdId().length()>0)
	    {
	    	if(st.length()>0)
	    		st = st + " and tbh.ddkd_fk ='"+ obj.getddkdId() +"' ";
	    	else
	    		st = st + " tbh.ddkd_fk ='"+ obj.getddkdId() +"' ";
	    }
	    
	    if (obj.getkenhId().length() > 0)
	    {
	    	if(st.length()>0)
	    		st = st + " and kh.kbh_fk ='"+ obj.getkenhId() +"' ";
	    	else
	    		st = st + " kh.kbh_fk ='"+ obj.getkenhId() +"' ";
	    }
	    if(st.length()>0)
	    	st = " where " + st;
	    //khoi tao ket noi csdl
		String st1="";
	  
	    if(obj.getnppId() != "")
	    {
	    	st1 =st1 + " npp_fk = '"+obj.getnppId()+"' ";
	    }
	    if (obj.gettuyenId() != ""){
	    
	    	st1 = st1+ " and ngaylamviec = '"+obj.gettuyenId()+"' ";
	    }
	    
	    if (st1 == "")
	    {
	    	st1 = " group by ngayid";
	    }
	    else
	    {
	    	st1 = " where " + st1 + " group by ngayid";
	    }
	    String sql2 = "select ngayid  from tuyenbanhang " + st1;
	    //System.out.println("Lay Du Lieu :"+sql2);
		ResultSet rs2 = db.get(sql2);
		int i = 8;
		
		if (rs2 != null)
		{
			try
			{
				while(rs2.next())
				{
					 i = i + 1;
					 
					Integer ngay = Integer.parseInt(rs2.getString("ngayid"));
					if (ngay == 2)
					{
						Cell cell = cells.getCell("A" + i); cell.setValue("Thứ 2");
					}else if (ngay  == 3)
					{
						Cell cell = cells.getCell("A" + i); cell.setValue("Thứ 3");
					}
					else if (ngay  == 4)
					{
						Cell cell = cells.getCell("A" + i); cell.setValue("Thứ 4");
					}
					else if (ngay  == 5)
					{
						Cell cell = cells.getCell("A" + i); cell.setValue("Thứ 5");
					}
					else if (ngay  == 6)
					{
						Cell cell = cells.getCell("A" + i); cell.setValue("Thứ 6");
					}
					else
					{
						Cell cell = cells.getCell("A" + i); cell.setValue("Thứ 7");
					}
					i=i+1;
					tieudeKH(worksheet,String.valueOf(i));
					
					geso.dms.center.util.Utility ut = new geso.dms.center.util.Utility();
					 String sql = "select v.ten as vung, kv.ten as khuvuc,tt.ten as Province ,npp.ten as npp, ddkd.ten as ddkd,  " +
					 " tbh.ngaylamviec,kh.smartid as Customer_Key,kh.ten as Customer_Name,kh.diachi as Address,isnull(cast(kh.Phuong as nvarchar(50)),N'Chưa xác định') as phuong, isnull(cast(kh.dienthoai as nvarchar(50)),N'Chưa xác định') as dienthoai ,qh.ten as province,case when ds.tonggiatri is null then 0 else ds.tonggiatri end as Average_Volume, lch.diengiai as Outlet_Type, "+
	    			 "vt.vitri as Outlet_Location,hch.hang as Outlet_Class,kh_tuyen.tanso as Frequency "+
	    			 "from khachhang kh "+
	    			 "inner join nhaphanphoi npp on npp.pk_seq = kh.npp_fk "+
      				 "inner join khuvuc kv on kv.pk_seq = npp.khuvuc_fk "+
	    		 	 "inner join vung v on v.pk_seq = kv.vung_fk "+
	    			 "left join quanhuyen qh on kh.quanhuyen_fk = qh.pk_seq "+
	    			 "left join tinhthanh tt on kh.tinhthanh_fk = tt.pk_seq "+
	    			 "left join loaicuahang lch on lch.pk_seq = kh.lch_fk "+
	    			 "left join vitricuahang vt on vt.pk_seq = kh.vtch_fk "+
	    			 "left join hangcuahang hch on hch.pk_seq = kh.hch_fk "+
	    			 "left join KHACHHANG_TUYENBH kh_tuyen on kh_tuyen.khachhang_fk = kh.pk_seq "+
	    			 "left join (select khachhang_fk,cast(sum(tonggiatri)/3 as int) as tonggiatri from donhang where ngaynhap >'2011-10-01' and ngaynhap < '2011-12-31' group by khachhang_fk) as ds "+
	    			 "on ds.khachhang_fk = kh.pk_seq " +
	    			 "left join (select * from tuyenbanhang where ngayid = "+ngay+") tbh on tbh.pk_seq = kh_tuyen.tbh_fk " +   
	    			 "inner join daidienkinhdoanh ddkd on ddkd.pk_seq = tbh.ddkd_fk " + st  

	    				
	    			 +" and ddkd.pk_seq = "+ddkd+" and npp.pk_seq in "+ ut.quyen_npp(userId)  //sua cho nay
	    			 +" order by v.ten, kv.ten, npp.ten ";
					 
					 

					 System.out.println("Lay Du Lieu cuoi cung :"+sql);
					 ResultSet rs = db.get(sql);
					 i=i+1;
					 
					 if(rs!= null)
					 {
						 	
						 	cells.setColumnWidth(0, 8.0f);
							cells.setColumnWidth(1, 10.0f);
							cells.setColumnWidth(2, 15.0f);
							cells.setColumnWidth(3, 15.0f);
							cells.setColumnWidth(4, 15.0f);			
							cells.setColumnWidth(5, 15.0f);	
							cells.setColumnWidth(6, 15.0f);	
							cells.setColumnWidth(7, 15.0f);
							cells.setColumnWidth(8, 15.0f);
							cells.setColumnWidth(9, 15.0f);
							cells.setColumnWidth(10, 15.0f);
							cells.setColumnWidth(11, 20.0f);
							cells.setColumnWidth(12, 15.0f);
							cells.setColumnWidth(13, 15.0f);
							cells.setColumnWidth(14, 20.0f);
							cells.setColumnWidth(15, 20.0f);
							
						 try 
						 {
				
							 Cell cell = null;
							 int stt = 1;
							 while(rs.next())// lap den cuoi bang du lieu
							 {
				
					//lay tu co so du lieu, gan bien
					
								 String Stt = String.valueOf(stt);
								 String Region =rs.getString("vung");
								 String Area =rs.getString("khuvuc");
								 String province =rs.getString("Province");
								 String Distributor =rs.getString("npp");
								 String SalesRep =rs.getString("ddkd");								 
								 String CustomerCode =rs.getString("Customer_Key");
								 String CustomerName =rs.getString("Customer_Name");
								 String Address =rs.getString("Address");
								 String Dienthoai =rs.getString("dienthoai");
								 String Town = rs.getString("province");
								 String Phuong = rs.getString("phuong");
								 String AverageVolume =rs.getString("Average_Volume");
								 String OutletType = rs.getString("Outlet_Type");
								 String OutletLocation = rs.getString("Outlet_Location");
								 String OutletClass = rs.getString("Outlet_Class");
								 String Frequency = rs.getString("Frequency");
					
					
					//cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(Channel);
								 cell = cells.getCell("A" + Integer.toString(i)); cell.setValue(Stt);			CreateBorderSetting(worksheet,"A" + Integer.toString(i));
								 /*cell = cells.getCell("B" + Integer.toString(i)); cell.setValue(Region);		CreateBorderSetting(worksheet,"B" + Integer.toString(i));
								 cell = cells.getCell("C" + Integer.toString(i)); cell.setValue(Area);			CreateBorderSetting(worksheet,"C" + Integer.toString(i));
								 
								 cell = cells.getCell("D" + Integer.toString(i)); cell.setValue(province);	 	CreateBorderSetting(worksheet,"D" + Integer.toString(i));
								 
								 cell = cells.getCell("E" + Integer.toString(i)); cell.setValue(Distributor);	CreateBorderSetting(worksheet,"E" + Integer.toString(i));
								 cell = cells.getCell("F" + Integer.toString(i)); cell.setValue(SalesRep);		CreateBorderSetting(worksheet,"F" + Integer.toString(i));
								 //cell = cells.getCell("F" + Integer.toString(i)); cell.setValue(Thu);			CreateBorderSetting(worksheet,"F" + Integer.toString(i));
*/								 cell = cells.getCell("B" + Integer.toString(i)); cell.setValue(CustomerCode);	CreateBorderSetting(worksheet,"B" + Integer.toString(i));
								 cell = cells.getCell("C" + Integer.toString(i)); cell.setValue(CustomerName);	CreateBorderSetting(worksheet,"C" + Integer.toString(i));
								 cell = cells.getCell("D" + Integer.toString(i)); cell.setValue(Address);		CreateBorderSetting(worksheet,"D" + Integer.toString(i));
								/* cell = cells.getCell("J" + Integer.toString(i)); cell.setValue(Phuong);			CreateBorderSetting(worksheet,"J" + Integer.toString(i));
								 cell = cells.getCell("K" + Integer.toString(i)); cell.setValue(Town);			CreateBorderSetting(worksheet,"K" + Integer.toString(i));
								 cell = cells.getCell("L" + Integer.toString(i)); cell.setValue(Dienthoai);CreateBorderSetting(worksheet,"L" + Integer.toString(i));
								 cell = cells.getCell("M" + Integer.toString(i)); cell.setValue(Float.parseFloat(AverageVolume));	CreateBorderSetting(worksheet,"M" + Integer.toString(i));
								 cell = cells.getCell("N" + Integer.toString(i)); cell.setValue(OutletType);CreateBorderSetting(worksheet,"N" + Integer.toString(i));
								 cell = cells.getCell("O" + Integer.toString(i)); cell.setValue(OutletLocation);	CreateBorderSetting(worksheet,"O" + Integer.toString(i));
								 cell = cells.getCell("P" + Integer.toString(i)); cell.setValue(OutletClass);		CreateBorderSetting(worksheet,"P" + Integer.toString(i));
								 cell = cells.getCell("Q" + Integer.toString(i)); cell.setValue(Frequency);		CreateBorderSetting(worksheet,"Q" + Integer.toString(i));*/
					
								 i++;
								 stt++;
							 }// end while
					
							 }//end try
						 catch (Exception e){}
					 		finally{
					 			if(rs != null)
					 			rs.close();
					 			/*if(db!=null){
					 				db.shutDown();
					 			}*/
					 		}
					 }//end if
				}// end while
			} catch (Exception e){
				//System.out.println("Errr" + e.toString());
			}//end try
			
	 		finally{
	 			if(rs2 != null)
					try {
						rs2.close();
					} catch(Exception e) {
						//System.out.println("Errr" + e.toString());					
						e.printStackTrace();
					}
	 		}
			if(db!=null){
				db.shutDown();
			}
		}// end if
		
	}
	
	private void CreateKHTonkho(Worksheet worksheet, IDRouter obj, String userId,String userTen, String tennpp, String nppId, String ddkdid, String ddkdten) 
	{
		CreateStaticHeader(worksheet, userTen, obj, tennpp, nppId, ddkdid, ddkdten);
		 CreateStaticData(worksheet, obj, userId, ddkdid, ddkdten);
		
	}

	private void CreateStaticHeader(Worksheet worksheet,String userTen, IDRouter obj, String tennpp, String nppId, String ddkdid, String tenddkd)  
	{		  
	    Cells cells = worksheet.getCells();
	   
	   	Style style;
	    //cells.setColumnWidth(0, 200.0f);
	    cells.setRowHeight(0, 20.0f);
	    Cell cell = cells.getCell("A1");  
	    cell.setValue("CẬP NHẬT KHÁCH HÀNG");   		      
	    style = cell.getStyle();
	  
	    Font font2 = new Font();
	    font2.setColor(Color.RED);//mau chu
	    font2.setSize(16);// size chu
	    font2.setBold(true);
	    
	    style.setFont(font2); 
	    style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu       
	    cell.setStyle(style);
    
	    cell = cells.getCell("A3");getCellStyle(worksheet,"A3",Color.BLUE,true,10);
	    cell.setValue("Ngày tạo: " + this.getDateTime());
	     
	    cell = cells.getCell("A4");getCellStyle(worksheet,"A4",Color.BLUE,true,10);
	    cell.setValue("Tạo bởi:  " + userTen);
	    
	    cell = cells.getCell("A5");getCellStyle(worksheet,"A5",Color.BLUE,true,10);
	    cell.setValue("Mã NPP : ");
	    
	    cell = cells.getCell("B5");getCellStyle(worksheet,"B5",Color.BLUE,true,10);
	    cell.setValue(nppId);
	    
	    cell = cells.getCell("A6");getCellStyle(worksheet,"A6",Color.BLUE,true,10);
	    cell.setValue("Tên NPP:  " + tennpp);
	    
	    cell = cells.getCell("A7");getCellStyle(worksheet,"A7",Color.BLUE,true,10);
	    cell.setValue("Tên NVBH:  " + tenddkd);
	}
	
	private boolean CreateStaticDataXuat(Workbook workbook, IDRouter obj,String userId, String id) throws Exception
	{		
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Cells cells = worksheet.getCells();		
	    /*String st="";
	    int dem =0;*/
	    int i = 2;
	    
	   	geso.dms.center.util.Utility ut = new geso.dms.center.util.Utility();
	   	String sql = " select npp.TEN as tennpp, ddkd.TEN as tenddkd, a.tbh_fk as tbh,kh.SMARTID as makh, kh.TEN as tenkh, sp.TEN as tensp, a.soluong from CAPNHATKH_CHITIET a"+
	   				 " inner join CAPNHATKHACHHANG cnkh on cnkh.PK_SEQ = a.capnhatkh_fk"+
	   				 " inner join NHAPHANPHOI npp on npp.PK_SEQ = a.npp_fk"+ 
	   				 " inner join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = a.ddkd_fk"+	   	
	   				 " inner join KHACHHANG kh on kh.SMARTID = a.makh"+
	   				 " inner join KHACHHANG_TUYENBH kh_tbh on kh_tbh.KHACHHANG_FK = kh.PK_SEQ"+
	   				 " inner join TUYENBANHANG tbh on tbh.PK_SEQ = kh_tbh.TBH_FK and tbh.DDKD_FK = ddkd.PK_SEQ and tbh.NPP_FK = npp.PK_SEQ"+
	   				 " inner join SANPHAM sp on sp.MA = a.masp"+
	   				 " where cnkh.PK_SEQ = '"+ id +"' and npp.pk_seq in "+ ut.quyen_npp(userId) +
	   				 " order by tennpp, tenddkd, tbh, makh, tenkh, tensp";					
					 
					 System.out.println("Lay Du Lieu cuoi cung :"+sql);					 
					 ResultSet rs = db.get(sql);					
					 
					 if (rs != null) {
							try {
								Cell cell = null;
								cells.setColumnWidth(0, 15.0f);
								cells.setColumnWidth(1, 15.0f);
								cells.setColumnWidth(2, 15.0f);
								cells.setColumnWidth(3, 15.0f);
								cells.setColumnWidth(4, 15.0f);			
								cells.setColumnWidth(5, 15.0f);	
								cells.setColumnWidth(6, 15.0f);	
								cells.setColumnWidth(7, 15.0f);	
								cells.setColumnWidth(8, 20.0f);
								cells.setColumnWidth(9, 20.0f);
								cells.setColumnWidth(10, 20.0f);
								cells.setColumnWidth(11, 20.0f);
								cells.setColumnWidth(12, 20.0f);
								cells.setColumnWidth(13, 20.0f);

								while (rs.next()) 
								{																																												
									 cell = cells.getCell("EA" + Integer.toString(i)); cell.setValue(rs.getString("tennpp"));		
									 cell = cells.getCell("EB" + Integer.toString(i)); cell.setValue(rs.getString("tenddkd"));		
									 cell = cells.getCell("EC" + Integer.toString(i)); cell.setValue(rs.getString("tbh"));		  
									 cell = cells.getCell("ED" + Integer.toString(i)); cell.setValue(rs.getString("makh"));	 	
									 cell = cells.getCell("EE" + Integer.toString(i)); cell.setValue(rs.getString("tenkh"));	 		
									 cell = cells.getCell("EF" + Integer.toString(i)); cell.setValue(rs.getString("tensp"));	 		
									 cell = cells.getCell("EG" + Integer.toString(i)); cell.setValue(rs.getFloat("soluong"));	 	
									 ++i;			
								}// end while
									
								if (rs != null) rs.close();
								
								if(db != null) db.shutDown();
								
								if(i==2){					
									throw new Exception("Xin loi,khong co bao cao voi dieu kien da chon....!!!");
								}
								  
							}catch(Exception ex){
								throw new Exception(ex.getMessage());
							}
						}else{
							return false;
						}		
						return true;					
}									
	
		private void tieude(Worksheet worksheet,String a) throws IOException{

	    Cells cells = worksheet.getCells();
	    Cell cell = cells.getCell("A" + a);  cell.setValue("NhaPhanPhoi");	CreateBorderSetting(worksheet,"A"+a);	getCellStyle(worksheet,"A"+a,Color.BLUE,true,10);
	    cell = cells.getCell("B"  + a); cell.setValue("DaiDienKinhDoanh");	CreateBorderSetting(worksheet,"B"+a);	getCellStyle(worksheet,"B"+a,Color.BLUE,true,10);
	    cell = cells.getCell("C"  + a); cell.setValue("TuyenBanHang");		CreateBorderSetting(worksheet,"C"+a);	getCellStyle(worksheet,"C"+a,Color.BLUE,true,10);	    
	    cell = cells.getCell("D"  + a); cell.setValue("MaKH");				CreateBorderSetting(worksheet,"D"+a);	getCellStyle(worksheet,"D"+a,Color.BLUE,true,10);	    
	    cell = cells.getCell("E"  + a); cell.setValue("TenKH");				CreateBorderSetting(worksheet,"E"+a);	getCellStyle(worksheet,"E"+a,Color.BLUE,true,10);
	    cell = cells.getCell("F"  + a); cell.setValue("TenSP");				CreateBorderSetting(worksheet,"F"+a);	getCellStyle(worksheet,"F"+a,Color.BLUE,true,10);	   
	    cell = cells.getCell("G"  + a); cell.setValue("SoLuong");			CreateBorderSetting(worksheet,"G"+a);	getCellStyle(worksheet,"G"+a,Color.BLUE,true,10);	    
	}
	
	private void getCellStyle(Worksheet worksheet, String a, Color mau, Boolean dam, int size)
	{
	   	   
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

	private void tieudeKH(Worksheet worksheet,String a) throws IOException{
		Hashtable<Integer, String> htb=this.htbValueCell();
		dbutils db = new dbutils();
	    Cells cells = worksheet.getCells();
	    Cell cell;
	    cell = cells.getCell("A"+a);  cell.setValue("STT"); 			CreateBorderSetting(worksheet,"A"+a); 	getCellStyle(worksheet,"A"+a,Color.BLUE,true,10);
	    cell = cells.getCell("B"+a); cell.setValue("Mã Khách Hàng");	CreateBorderSetting(worksheet,"B"+a);	getCellStyle(worksheet,"B"+a,Color.BLUE,true,10);
	    cell = cells.getCell("C"+a); cell.setValue("Tên Khách Hàng");	CreateBorderSetting(worksheet,"C"+a);	getCellStyle(worksheet,"C"+a,Color.BLUE,true,10);	    
	    cell = cells.getCell("D"+a); cell.setValue("Địa Chỉ");			CreateBorderSetting(worksheet,"D"+a);	getCellStyle(worksheet,"D"+a,Color.BLUE,true,10);
	    
	    int k = 1;
		cell = null;
		String sql = "select ma, ten from sanpham where trangthai = '1'";
		ResultSet rs = db.get(sql);
		try {
			while(rs.next()){
				String maSP = rs.getString("ma");
				String tenSP = rs.getString("ten");
				cell = cells.getCell(htb.get(k) + (Integer.parseInt(a)-1)); cell.setValue(maSP);	 	CreateBorderSetting(worksheet,htb.get(k) + (Integer.parseInt(a)-1));	getCellStyle(worksheet,htb.get(k) + (Integer.parseInt(a)-1),Color.BLUE,true,10);					
				cell = cells.getCell(htb.get(k) + a); cell.setValue(tenSP);	 							CreateBorderSetting(worksheet,htb.get(k) + a);	getCellStyle(worksheet,htb.get(k) + a,Color.BLUE,true,10);
				//cells.setColumnWidth(k+3, 50.0f);
				k++;				
			}
		} catch (SQLException e) {		
			e.printStackTrace();
		}
		cells.setColumnWidth(4, 27.0f); 
		cells.setColumnWidth(5, 28.0f); 
		cells.setColumnWidth(6, 32.0f); 
		cells.setColumnWidth(7, 32.0f); 
		cells.setColumnWidth(8, 24.0f); 
		
		cells.setColumnWidth(9, 18.0f);
		cells.setColumnWidth(10, 25.0f);
		cells.setColumnWidth(11, 14.0f);
		cells.setColumnWidth(12, 36.0f);
		cells.setColumnWidth(13, 38.0f);
		
		cells.setColumnWidth(14, 31.0f); 
		cells.setColumnWidth(15, 38.0f); 
		cells.setColumnWidth(16, 38.0f); 
		cells.setColumnWidth(17, 45.0f); 
		cells.setColumnWidth(18, 45.0f);
	}
	
	
	 public void CreateBorderSetting(Worksheet worksheet,String fileName) throws IOException
	    {
	        Cells cells = worksheet.getCells();
	        Cell cell;
	        Style style;

	        cell = cells.getCell(fileName);
	        style = cell.getStyle();

	        //Set border color
	        style.setBorderColor(BorderType.TOP, Color.BLACK);
	        style.setBorderColor(BorderType.BOTTOM, Color.BLACK);
	        style.setBorderColor(BorderType.LEFT, Color.BLACK);
	        style.setBorderColor(BorderType.RIGHT, Color.BLACK);
	        //style.setBorderColor(BorderType.DIAGONAL_DOWN, Color.BLUE);
	        //style.setBorderColor(BorderType.DIAGONAL_UP, Color.BLUE);

	        //Set the cell border type
	        style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
	        style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
	        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
	        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
	        //style.setBorderLine(BorderType.DIAGONAL_DOWN, BorderLineType.DASHED);
	        //style.setBorderLine(BorderType.DIAGONAL_UP, BorderLineType.DASHED);

	        cell.setStyle(style);

	       
	    }
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh-mm-ss");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}
