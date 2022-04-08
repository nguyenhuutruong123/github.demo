package geso.dms.center.servlets.reports;

import geso.dms.center.beans.Router.IDRouter;
import geso.dms.center.beans.Router.imp.Router;
import geso.dms.center.db.sql.dbutils;
import geso.dms.distributor.util.Utility;
import java.io.IOException;
import java.io.OutputStream;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

public class Routereport extends HttpServlet {
	
       
	private static final long serialVersionUID = 1L;
	   Utility util=new Utility();
	   //String userTen = "";
	   //String userId;
	   //String ddkdId = "";
	   //String nppId = "";
	   //String kenhId = "";
	   //String tuyenId = "";
	   //boolean bfasle=true;
    public Routereport() {
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
			  rs_tenuser.close();
			}catch(Exception er){
				return "";
			}
		}
		return "";
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
	//	PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String userTen = (String)session.getAttribute("userTen");
		String querystring=request.getQueryString();
		String userId = util.getUserId(querystring);
		session.setAttribute("userId", userId);
		session.setAttribute("tungay", "");
 		session.setAttribute("denngay","");
    	session.setAttribute("loi", "");
		session.setAttribute("userTen", userTen);	
		
		IDRouter obj = new Router();
		obj.setuserId(userId);
    	obj.setStatus("1");
		
		
		String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		if(view == null)
			view = "";
		obj.setView(view);
		
		geso.dms.center.util.Utility ut = new geso.dms.center.util.Utility();
		if(view.equals("NPP")) {
			obj.setnppId(ut.getIdNhapp(userId));
		}
		
		obj.init();
		
		session.setAttribute("obj",obj);
		String nextJSP = "/AHF/pages/Center/RouteReport.jsp";
		response.sendRedirect(nextJSP);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
	//	PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
        IDRouter obj = new Router();
        String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
        
        String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
        if(userId == null)
        	userId = "";
        obj.setuserId(userId);
        
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
        
        String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		System.out.println("loaiMenu = " + view);
		if(view == null)
			view = "";	
		obj.setView(view);
		
		geso.dms.center.util.Utility ut = new geso.dms.center.util.Utility();
		if(view.equals("NPP")) {
			obj.setnppId(ut.getIdNhapp(userId));
		}
        
        obj.init();
        String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
        
        if(action.equals("export")){
        	OutputStream out = response.getOutputStream(); 
			String userTen = (String)session.getAttribute("userTen");
			
			response.setContentType("application/xlsm");
	        response.setHeader("Content-Disposition", "attachment; filename=RouteReport.xlsm");
	        
	    	/*response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Content-Disposition", "attachment; filename=RouteReport.xls");*/
	        userId = (String) session.getAttribute("userId");
	        System.out.println("userId: "+userId);
			CreatePivotTable(out,response,request, obj, userId, userTen); //userTen, nppId, tuyenId, ddkdId, kenhId, userId );

        }else{
        	
        	String khuvucId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")));
        	if(khuvucId.trim().length() > 0)
	        	obj.setkhuvucId(khuvucId);
        	String status = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("status")));
        	obj.setStatus(status);
        	obj.createNPP();
        	
        	//System.out.println("status : "+status);
        	
        	session.setAttribute("obj",obj);
        	String nextJSP = "/AHF/pages/Center/RouteReport.jsp";
        	response.sendRedirect(nextJSP);
        }
	}
	private void CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request, IDRouter obj, String userId, String userTen) /*String userTen, String nppId, String tuyenId, String ddkdId, String kenhId, String userId)*/ throws IOException
    {   /* //khoi tao de viet pivottable
		//buoc 1
	     Workbook workbook = new Workbook();
	     //Buoc2 tao khung
	    //ham tao khu du lieu
	     CreateStaticHeader(workbook, userTen);
	     //Buoc3 
	     // day du lieu vao
	     CreateStaticData(workbook, nppId, tuyenId, ddkdId, kenhId, userId);

	     workbook.save(out);*/
		//System.out.println("vao tao pivot");
		Workbook workbook = new Workbook();
	    workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
	     
		String sql="select pk_seq, ten  from daidienkinhdoanh where npp_fk="+obj.getnppId() +" and trangthai=1";
	    
	     if(obj.getddkdId().length() >0){
	    	 sql=sql + " and pk_seq="+ obj.getddkdId();
	    	 
	     }
	     System.out.println("lay ddkd : "+sql);
	     dbutils db=new dbutils();
	     ResultSet rs=db.get(sql);
	     if(rs!=null){
	    	 try{
	    		 int i=0;
	    		 Worksheets worksheets = workbook.getWorksheets();
	    	 while (rs.next()){
	    		 	if(i == 0)
	    		 		worksheets.removeSheet(0);
	    			 Worksheet worksheet = worksheets.addSheet(rs.getString("pk_seq")+"-"+rs.getString("ten"));
	    			 
	    		 CreateTuyenDDKD(worksheet, obj, userId ,userTen, rs.getString("pk_seq"),rs.getString("ten"));
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
	
	private void CreateTuyenDDKD(Worksheet worksheet, IDRouter obj, String userId,String userTen, String ddkdid, String ddkdten) 
	{
		CreateStaticHeader(worksheet, userTen,obj, ddkdid, ddkdten);
		 CreateStaticData(worksheet,obj,userId, ddkdid,ddkdten);
		
	}

	private void CreateStaticHeader(Worksheet worksheet,String userTen, IDRouter obj, String ddkdid, String tenddkd)  
	{
	   	   
	    Cells cells = worksheet.getCells();
	   
	   	Style style;
	    //cells.setColumnWidth(0, 200.0f);
	    cells.setRowHeight(0, 20.0f);
	    Cell cell = cells.getCell("A1");  
	    cell.setValue("BÁO CÁO TUYẾN BÁN HÀNG - KÊNH: GT");   		      
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
	    
	  
	
	}
	private void CreateStaticData(Worksheet worksheet, IDRouter obj,String userId, String ddkd, String ddkdten) 
	{
		dbutils db = new dbutils();
	    Cells cells = worksheet.getCells();
	    String st="";
	    int dem =0;
	    
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
	    System.out.println("hihi"+obj.gettuyenId().length());
	    System.out.println("st1"+st1);

	    if (obj.gettuyenId().length()>0){
	    
	    	st1 = st1+ " and ngaylamviec = '"+obj.gettuyenId()+"' ";
	    }
	    System.out.println("st2"+st1);

	    if (st1 == "")
	    {
	    	st1 = " group by ngayid";
	    }
	    else
	    {
	    	st1 = " where " + st1 + " group by ngayid";
	    }
	    String sql2 = "select ngayid  from tuyenbanhang " + st1;
	    System.out.println("Lay Du Lieu :"+sql2);
		ResultSet rs2 = db.get(sql2);
		int i = 5;
		
		if (rs2 != null)
		{
			try
			{
				while(rs2.next())
				{
					 i = i + 2;
					 
					Integer ngay = Integer.parseInt(rs2.getString("ngayid"));
					if (ngay == 2)
					{
						Cell cell = cells.getCell("A" + i); cell.setValue("Thứ hai");
					}else if (ngay  == 3)
					{
						Cell cell = cells.getCell("A" + i); cell.setValue("Thứ ba");
					}
					else if (ngay  == 4)
					{
						Cell cell = cells.getCell("A" + i); cell.setValue("Thứ tư");
					}
					else if (ngay  == 5)
					{
						Cell cell = cells.getCell("A" + i); cell.setValue("Thứ năm");
					}
					else if (ngay  == 6)
					{
						Cell cell = cells.getCell("A" + i); cell.setValue("Thứ sáu");
					}
					else
					{
						Cell cell = cells.getCell("A" + i); cell.setValue("Thứ bảy");
					}
					i=i+1;
					tieude(worksheet,String.valueOf(i));
					
					geso.dms.center.util.Utility ut = new geso.dms.center.util.Utility();
					 String sql = "select v.ten as vung, kv.ten as khuvuc,tt.ten as Province ,npp.ten as npp, ddkd.ten as ddkd,  " +
					 " tbh.ngaylamviec,kh.smartid as Customer_Key,kh.ten as Customer_Name,kh.diachi as Address,isnull(px.ten,N'') as phuong, isnull(cast(kh.dienthoai as nvarchar(50)),N'Chưa xác định') as dienthoai ,qh.ten as quanhuyen,case when ds.tonggiatri is null then 0 else ds.tonggiatri end as Average_Volume, lch.diengiai as Outlet_Type, "+
	    			 "vt.vitri as Outlet_Location,hch.hang as Outlet_Class,kh_tuyen.tanso as Frequency, case when kh.trangthai = 1 then N'Hoạt động' else N'Ngưng hoạt động' end trangthai "+
	    			 "from khachhang kh "+
	    			 "inner join nhaphanphoi npp on npp.pk_seq = kh.npp_fk "+
      				 "inner join khuvuc kv on kv.pk_seq = npp.khuvuc_fk "+
	    		 	 "inner join vung v on v.pk_seq = kv.vung_fk "+
	    			 "left join quanhuyen qh on kh.quanhuyen_fk = qh.pk_seq "+
	    			 "left join tinhthanh tt on kh.tinhthanh_fk = tt.pk_seq "+
	    			 "left join phuongxa px on kh.phuongxa_fk = px.pk_seq "+
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
								 String Thu = rs.getString("ngaylamviec");
								 String CustomerCode =rs.getString("Customer_Key");
								 String CustomerName =rs.getString("Customer_Name");
								 String Address =rs.getString("Address");
								 String Dienthoai =rs.getString("dienthoai");
								 String Town = rs.getString("quanhuyen");
								 String Phuong = rs.getString("phuong");
								 String AverageVolume =rs.getString("Average_Volume");
								 String OutletType = rs.getString("Outlet_Type");
								 String OutletLocation = rs.getString("Outlet_Location");
								 String OutletClass = rs.getString("Outlet_Class");
								 String Frequency = rs.getString("Frequency");
								 String trangthai = rs.getString("trangthai");
					
					
								 //cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(Channel);
								 cell = cells.getCell("A" + Integer.toString(i)); cell.setValue(Stt);			CreateBorderSetting(worksheet,"A" + Integer.toString(i));
								 cell = cells.getCell("B" + Integer.toString(i)); cell.setValue(Region);		CreateBorderSetting(worksheet,"B" + Integer.toString(i));
								 cell = cells.getCell("C" + Integer.toString(i)); cell.setValue(Area);			CreateBorderSetting(worksheet,"C" + Integer.toString(i));
								 
								 cell = cells.getCell("D" + Integer.toString(i)); cell.setValue(province);	 	CreateBorderSetting(worksheet,"D" + Integer.toString(i));
								 
								 cell = cells.getCell("E" + Integer.toString(i)); cell.setValue(Distributor);	CreateBorderSetting(worksheet,"E" + Integer.toString(i));
								 cell = cells.getCell("F" + Integer.toString(i)); cell.setValue(SalesRep);		CreateBorderSetting(worksheet,"F" + Integer.toString(i));
								 //cell = cells.getCell("F" + Integer.toString(i)); cell.setValue(Thu);			CreateBorderSetting(worksheet,"F" + Integer.toString(i));
								 cell = cells.getCell("G" + Integer.toString(i)); cell.setValue(CustomerCode);	CreateBorderSetting(worksheet,"G" + Integer.toString(i));
								 cell = cells.getCell("H" + Integer.toString(i)); cell.setValue(CustomerName);	CreateBorderSetting(worksheet,"H" + Integer.toString(i));
								 cell = cells.getCell("I" + Integer.toString(i)); cell.setValue(Address);		CreateBorderSetting(worksheet,"I" + Integer.toString(i));
								 cell = cells.getCell("J" + Integer.toString(i)); cell.setValue(Phuong);			CreateBorderSetting(worksheet,"J" + Integer.toString(i));
								 cell = cells.getCell("K" + Integer.toString(i)); cell.setValue(Town);			CreateBorderSetting(worksheet,"K" + Integer.toString(i));
								 cell = cells.getCell("L" + Integer.toString(i)); cell.setValue(Dienthoai);CreateBorderSetting(worksheet,"L" + Integer.toString(i));
								 cell = cells.getCell("M" + Integer.toString(i)); cell.setValue(Float.parseFloat(AverageVolume));	CreateBorderSetting(worksheet,"M" + Integer.toString(i));
								 cell = cells.getCell("N" + Integer.toString(i)); cell.setValue(OutletType);CreateBorderSetting(worksheet,"N" + Integer.toString(i));
								 cell = cells.getCell("O" + Integer.toString(i)); cell.setValue(OutletLocation);	CreateBorderSetting(worksheet,"O" + Integer.toString(i));
								 cell = cells.getCell("P" + Integer.toString(i)); cell.setValue(OutletClass);		CreateBorderSetting(worksheet,"P" + Integer.toString(i));
								 cell = cells.getCell("Q" + Integer.toString(i)); cell.setValue(Frequency);		CreateBorderSetting(worksheet,"Q" + Integer.toString(i));
								 cell = cells.getCell("R" + Integer.toString(i)); cell.setValue(trangthai);		CreateBorderSetting(worksheet,"R" + Integer.toString(i));
					
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
	private void getAn(Worksheet worksheet,int i)
	{
	    Cells cells = worksheet.getCells();
	    for(int j = 26; j <= i; j++)
	    { 
	    	cells.hideColumn(j);
	    }
		
	}
	private void tieude(Worksheet worksheet,String a) throws IOException{

	    Cells cells = worksheet.getCells();
	    Cell cell = cells.getCell("A" + a);  cell.setValue("STT");				CreateBorderSetting(worksheet,"A"+a);	getCellStyle(worksheet,"A"+a,Color.BLUE,true,10);
	    cell = cells.getCell("B"  + a); cell.setValue("Vùng");					CreateBorderSetting(worksheet,"B"+a);	getCellStyle(worksheet,"B"+a,Color.BLUE,true,10);
	    cell = cells.getCell("C"  + a); cell.setValue("Khu vực");				CreateBorderSetting(worksheet,"C"+a);	getCellStyle(worksheet,"C"+a,Color.BLUE,true,10);	    
	    cell = cells.getCell("D"  + a); cell.setValue("Tỉnh thành");			CreateBorderSetting(worksheet,"D"+a);	getCellStyle(worksheet,"D"+a,Color.BLUE,true,10);	    
	    cell = cells.getCell("E"  + a); cell.setValue("Nhà phân phối");			CreateBorderSetting(worksheet,"E"+a);	getCellStyle(worksheet,"E"+a,Color.BLUE,true,10);
	    cell = cells.getCell("F"  + a); cell.setValue("Nhân Viên Bán Hàng");	CreateBorderSetting(worksheet,"F"+a);	getCellStyle(worksheet,"F"+a,Color.BLUE,true,10);
	    //cell = cells.getCell("F" + a); cell.setValue("Thu");					CreateBorderSetting(worksheet,"F"+a);	getCellStyle(worksheet,"F"+a,Color.BLUE,true,10);
	    cell = cells.getCell("G"  + a); cell.setValue("Mã KH");					CreateBorderSetting(worksheet,"G"+a);	getCellStyle(worksheet,"G"+a,Color.BLUE,true,10);
	    cell = cells.getCell("H" + a); cell.setValue("Tên KH");					CreateBorderSetting(worksheet,"H"+a);	getCellStyle(worksheet,"H"+a,Color.BLUE,true,10);
	    cell = cells.getCell("I" + a); cell.setValue("Đại chỉ");				CreateBorderSetting(worksheet,"I"+a);	getCellStyle(worksheet,"I"+a,Color.BLUE,true,10);
	    cell = cells.getCell("J" + a); cell.setValue("Phường/xã");				CreateBorderSetting(worksheet,"J"+a);	getCellStyle(worksheet,"J"+a,Color.BLUE,true,10);
	    cell = cells.getCell("K" + a); cell.setValue("Quận/huyện");				CreateBorderSetting(worksheet,"K"+a);	getCellStyle(worksheet,"K"+a,Color.BLUE,true,10);
	    cell = cells.getCell("L" + a); cell.setValue("Số điện thoại");			CreateBorderSetting(worksheet,"L"+a);	getCellStyle(worksheet,"L"+a,Color.BLUE,true,10);
	    cell = cells.getCell("M" + a); cell.setValue("Tổng giá trị");			CreateBorderSetting(worksheet,"M"+a);	getCellStyle(worksheet,"M"+a,Color.BLUE,true,10);
	    cell = cells.getCell("N" + a); cell.setValue("Loại cửa hàng");			CreateBorderSetting(worksheet,"N"+a);	getCellStyle(worksheet,"N"+a,Color.BLUE,true,10);
	    cell = cells.getCell("O" + a); cell.setValue("Vị trí cửa hàng");		CreateBorderSetting(worksheet,"O"+a);	getCellStyle(worksheet,"O"+a,Color.BLUE,true,10);	
	    cell = cells.getCell("P" + a); cell.setValue("Hạng cửa hàng");			CreateBorderSetting(worksheet,"P"+a);	getCellStyle(worksheet,"P"+a,Color.BLUE,true,10);
	    cell = cells.getCell("Q" + a); cell.setValue("Tần số");					CreateBorderSetting(worksheet,"Q"+a);	getCellStyle(worksheet,"P"+a,Color.BLUE,true,10);
	    cell = cells.getCell("R" + a); cell.setValue("Trạng thái KH");			CreateBorderSetting(worksheet,"R"+a);	getCellStyle(worksheet,"R"+a,Color.BLUE,true,10);
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
