package geso.dms.center.servlets.nhaphanphoi;

import geso.dms.center.beans.nhaphanphoi.*;
import geso.dms.center.beans.nhaphanphoi.imp.*;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;



import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
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

public class NhaphanphoiSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;   
    
    public NhaphanphoiSvl() 
    {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		INhaphanphoiList obj;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    out = response.getWriter();
	    obj = new NhaphanphoiList(); 

	    obj.setRequestObj(request);
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	  //--- check user
	 
		String param = "";
	
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			//obj.DBClose();
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "NhaphanphoiSvl", param, Utility.XEM ))
		{
			//obj.DBClose();
			Utility.RedireactLogin(session, request, response);
		}
		 //--- check user
		
	    String nppId = util.getId(querystring);
	    if (action.equals("delete")){		
	    	Delete(nppId);	
	    	out.println(nppId);
	    }else 
	    	if (action.equals("xoatd1")){	   		  	    	
	    	DeleteToaDo(nppId, "1");
	    	out.print(nppId);
	    }else 
	    	if (action.equals("xoatd2")){	   		  	    	
	    	DeleteToaDo(nppId, "2");
	    	out.print(nppId);
	    }
	   	
	    
	    obj.setUserId(userId);
	    obj.init("");
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Center/NhaPhanPhoi.jsp";
		response.sendRedirect(nextJSP);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		INhaphanphoiList obj = new NhaphanphoiList();
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    OutputStream out = response.getOutputStream();
	    
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	   
		
		String ten = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppTen")));
    	if ( ten == null)
    		ten = "";
    	obj.setTen(ten);
    	
    	String sodienthoai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("DienThoai")));
    	if (sodienthoai == null)
    		sodienthoai = "";    	
    	obj.setSodienthoai(sodienthoai);
    	    	
    	String kvId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kvId")));
    	if (kvId == null)
    		kvId = "";    	
    	obj.setKvId(kvId);
    	
    	String loainppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loainppId")));
    	if(loainppId ==  null)
    		loainppId = "";
    	obj.setLoaiNppId(loainppId);
    	
    	String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("TrangThai")));   	
    	if (trangthai == null)
    		trangthai = "";    	
	
    	if (trangthai.equals("2"))
    		trangthai = "";
    	
    	obj.setTrangthai(trangthai);
    	
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    //--- check user
		 
		String param = "";
	
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			//obj.DBClose();
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "NhaphanphoiSvl", param, Utility.XEM ))
		{
			//obj.DBClose();
			Utility.RedireactLogin(session, request, response);
		}
		 //--- check user
	    
	    System.out.println("Action Save: "+action);
	    String nextJSP = "";
	          
	    if (action.equals("new")){
	    	
	    	// Empty Bean for distributor
	    	INhaphanphoi nppBean = (INhaphanphoi) new Nhaphanphoi("");
	    	nppBean.setUserId(userId);
	    	nppBean.createRS();
	    	// Save Data into session
	    	session.setAttribute("nppBean", nppBean);
    		
    		nextJSP = "/AHF/pages/Center/NhaPhanPhoiNew.jsp";
    		session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
    	    response.sendRedirect(nextJSP);
    		
	    } else  if (action.equals("search")){  	
	    	
	    	obj.setRequestObj(request);
	    	obj.setUserId(userId);
	    	String search = getSearchQuery(request, obj);

	    	//obj.setNxtApprSplitting(Integer.parseInt(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
	    	obj.init(search);
	    	//obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	
    		nextJSP = "/AHF/pages/Center/NhaPhanPhoi.jsp"; 
    		session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
    	    response.sendRedirect(nextJSP);
	    }
	    else if(action.equals("view") || action.equals("next") || action.equals("prev")){
	    	
	 
	    	obj.setUserId(userId);
	    	obj.setNxtApprSplitting(Integer.parseInt(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting")))));
	    	obj.init("");
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	
	    	session.setAttribute("obj", obj);
			
			nextJSP = "/AHF/pages/Center/NhaPhanPhoi.jsp";
			response.sendRedirect(nextJSP);
	    	
	    	
	    }
	    
	    else if(action.equals("excel")){
	    	obj = new NhaphanphoiList();
	    	try
		    {
		    	response.setContentType("application/vnd.ms-excel");
		        response.setHeader("Content-Disposition", "attachment; filename=DanhSachNhaPhanPhoi.xls");
		        
		        Workbook workbook = new Workbook();
		    	
			    CreateStaticHeader(workbook,"Admin");
			    CreateStaticData(workbook, getSearchQuery1(request));
			
			    //Saving the Excel file
			    workbook.save(out);
		    }
		    catch (Exception ex){}
	    	
	    }
	    
	  
	   
	}
	
	private void CreateStaticHeader(Workbook workbook, String UserName) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	   
	    
	    
	   	Style style;
	    //cells.setColumnWidth(0, 200.0f);
	    cells.setRowHeight(0, 20.0f);
	    Cell cell = cells.getCell("A1");  
	    cell.setValue("Danh sách nhà phân phối");   	
	    
	  
	    style = cell.getStyle();
	   
	   Font font2 = new Font();
	   font2.setColor(Color.RED);//mau chu
	   font2.setSize(16);// size chu
	   style.setFont(font2); 
	   style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu       
	   cell.setStyle(style);  
	    cell = cells.getCell("A2"); getCellStyle(workbook,"A2",Color.BLUE,true,10);
	    cell.setValue("Ngày tạo: " + this.getDateTime());
	    cell = cells.getCell("A3");getCellStyle(workbook,"A4",Color.BLUE,true,10);
	    cell.setValue("Người tạo:  " + UserName);
	    
	    //tieu de, hang dau tien cua bang du lieu, cell la toa do cua exel
	    cell = cells.getCell("AA1"); cell.setValue("Id"); 
	   	cell = cells.getCell("AB1"); cell.setValue("Mã NPP");
	   	cell = cells.getCell("AC1"); cell.setValue("Tên");
	   	cell = cells.getCell("AD1"); cell.setValue("Địa chỉ");
	   	cell = cells.getCell("AE1"); cell.setValue("Điện thoại");
	   	cell = cells.getCell("AF1"); cell.setValue("Kho SAP");
	   	cell = cells.getCell("AG1"); cell.setValue("Mã số thuế");
	   	cell = cells.getCell("AH1"); cell.setValue("Địa chỉ XHD");
	   	cell = cells.getCell("AI1"); cell.setValue("Tỉnh thành");
	   	cell = cells.getCell("AJ1"); cell.setValue("Quận huyện");
	   	cell = cells.getCell("AK1"); cell.setValue("Vùng");
	   	cell = cells.getCell("AL1"); cell.setValue("Khu vực");
	   	cell = cells.getCell("AM1"); cell.setValue("Trạng thái");
	   
	   	cell = cells.getCell("AN1"); cell.setValue("Mã giám sát");
	   	cell = cells.getCell("AO1"); cell.setValue("Giám sát bán hàng");
	   	cell = cells.getCell("AP1"); cell.setValue("Địa chỉ GS");
	   	cell = cells.getCell("AQ1"); cell.setValue("Điện thoại GS");
	   	cell = cells.getCell("AR1"); cell.setValue("Kênh giám sát");
	    worksheet.setName("Distributor Infomation");
	    }
	   
	private void CreateStaticData(Workbook workbook,String sql) 
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();

	System.out.println("Get Du Lieu :"+sql);
	ResultSet rs = db.get(sql);
		int i = 2;
		if(rs!= null)
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
				cells.setColumnWidth(17, 15.0f);
				//set do rong cho dong
				//for(int j = 12; j <= 22; j++)
				//	cells.setRowHeight(j, 14.0f);
				
				Cell cell = null;
				while(rs.next())// lap den cuoi bang du lieu
				{ 

					cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(rs.getString("pk_seq"));
					cell = cells.getCell("AB" + Integer.toString(i)); cell.setValue(rs.getString("manpp"));
					cell = cells.getCell("AC" + Integer.toString(i)); cell.setValue(rs.getString("ten"));
					cell = cells.getCell("AD" + Integer.toString(i)); cell.setValue(rs.getString("diachi"));
					cell = cells.getCell("AE" + Integer.toString(i)); cell.setValue(rs.getString("dienthoai"));
					cell = cells.getCell("AF" + Integer.toString(i)); cell.setValue(rs.getString("khosap"));
					cell = cells.getCell("AG" + Integer.toString(i)); cell.setValue(rs.getString("masothue"));
					cell = cells.getCell("AH" + Integer.toString(i)); cell.setValue(rs.getString("diachixhd"));
					cell = cells.getCell("AI" + Integer.toString(i)); cell.setValue(rs.getString("tinhthanh"));
					cell = cells.getCell("AJ" + Integer.toString(i)); cell.setValue(rs.getString("quanhuyen"));
					cell = cells.getCell("AK" + Integer.toString(i)); cell.setValue(rs.getString("tenvung"));
					cell = cells.getCell("AL" + Integer.toString(i)); cell.setValue(rs.getString("tenkhuvuc"));
					cell = cells.getCell("AM" + Integer.toString(i)); cell.setValue(rs.getString("trangthai"));
					cell = cells.getCell("AN" + Integer.toString(i)); cell.setValue(rs.getString("gsid"));
					cell = cells.getCell("AO" + Integer.toString(i)); cell.setValue(rs.getString("tengs"));
					cell = cells.getCell("AP" + Integer.toString(i)); cell.setValue(rs.getString("diachigs"));
					cell = cells.getCell("AQ" + Integer.toString(i)); cell.setValue(rs.getString("dienthoaigs"));
					cell = cells.getCell("AR" + Integer.toString(i)); cell.setValue(rs.getString("kenh"));
					i++;
				}
			
			
		
		//create pivot
	 getAn(workbook,49);
		PivotTables pivotTables = worksheet.getPivotTables();

	    //Adding a PivotTable to the worksheet
		String pos = Integer.toString(i-1);	//pos la dong cuoi cung	,A12 la toa do dau cua banng du lieu, Q pos la dong cuoi
	    int index = pivotTables.add("=AA1:AR" + pos,"A12","PivotTableDemo");
	     System.out.println("index:"+index);
	    //Accessing the instance of the newly added PivotTable
	    PivotTable pivotTable = pivotTables.get(index);//truyen index

	    pivotTable.setRowGrand(false);
	    pivotTable.setColumnGrand(false);
	    pivotTable.setAutoFormat(true);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 0); pivotTable.getRowFields().get(0).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 1);	pivotTable.getRowFields().get(1).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 2);	pivotTable.getRowFields().get(2).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 3);	  pivotTable.getRowFields().get(3).setAutoSubtotals(false);
	 
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 4);  pivotTable.getRowFields().get(4).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 5);  pivotTable.getRowFields().get(5).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 6);  pivotTable.getRowFields().get(6).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 7); pivotTable.getRowFields().get(7).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 8);pivotTable.getRowFields().get(8).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 9); pivotTable.getRowFields().get(9).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 10); pivotTable.getRowFields().get(10).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 11); pivotTable.getRowFields().get(11).setAutoSubtotals(false);
	    
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 12);	pivotTable.getRowFields().get(12).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 13);	  pivotTable.getRowFields().get(13).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 14);   pivotTable.getRowFields().get(14).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 15);pivotTable.getRowFields().get(15).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 16);pivotTable.getRowFields().get(16).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 17);pivotTable.getRowFields().get(17).setAutoSubtotals(false);
	    
	    
	 //   pivotTable.addFieldToArea(PivotFieldType.DATA, 12);pivotTable.getDataFields().get(0).setDisplayName("Id");
	//    pivotTable.addFieldToArea(PivotFieldType.DATA, 13);pivotTable.getDataFields().get(1).setDisplayName("Tên");
	    //pivotTable.getDataFields().get(0).setNumber(3);
	    db.shutDown();
			}
			catch (Exception e){
				
				e.printStackTrace(); 
				System.out.println("Error : Promotionnalfordist : "+e.toString());
			}
		}else{
			
		}
		
	}



	private String getSearchQuery(HttpServletRequest request, INhaphanphoiList obj)
	{		
		
		obj.getDataSearch().clear();
		Utility util = new Utility();
		
			String ten = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppTen")));
	    	if ( ten == null)
	    		ten = "";
	    	obj.setTen(ten.trim());
	    	
	    	String sodienthoai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("DienThoai")));
	    	if (sodienthoai == null)
	    		sodienthoai = "";    	
	    	obj.setSodienthoai(sodienthoai);
	    	    	
	    System.out.println(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kvId")));
	    	String kvId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kvId")));
	    	if (kvId == null)
	    		kvId = "";    	
	    	obj.setKvId(kvId);
	    	
	    	String loainppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loainppId")));
	    	if(loainppId ==  null)
	    		loainppId = "";
	    	obj.setLoaiNppId(loainppId);
	    	
	    	String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("TrangThai")));   	
	    	if (trangthai == null)
	    		trangthai = "";    	
		
	    	if (trangthai.equals("2"))
	    		trangthai = "";
	    	
	    	obj.setTrangthai(trangthai);
	    	
	    	String query = "\n select a.manpp as nppMa,a.pk_seq as id, " + 
	    	"\n ISNULL((select lnpp.Ten from LoaiNPP lnpp where lnpp.pk_seq=a.loainpp),'') as tenloainpp, " +
	    	"\n a.ten as nppTen, a.diachi, a.dienthoai, d.ten as khuvuc, a.trangthai, a.ngaytao, " +
			"\n a.ngaysua, b.ten as nguoitao, c.ten as nguoisua,isnull(lat,'') as lat,isnull(lat1,'') as lat1, isnull(long,'')long,isnull(long1,'') as long1 " +
			"\n from nhaphanphoi a inner join nhanvien b on b.pk_seq=a.nguoitao "+ 
			"\n inner join  nhanvien c on c.pk_seq=a.nguoisua "+
			"\n left join  khuvuc d  on a.khuvuc_fk=d.pk_seq where 1=1  ";
	    	System.out.println("query search: "+query);
	    	if (ten.length()>0){
	    		//geso.dms.distributor.util.Utility util = new geso.dms.distributor.util.Utility();

				query = query + " and ( upper(dbo.ftBoDau(a.ten)) like upper(?) or upper(dbo.ftBoDau(a.manpp)) like upper(?)  )";
				obj.getDataSearch().add( "%" + util.replaceAEIOU(ten) + "%" );
				obj.getDataSearch().add( "%" + util.replaceAEIOU(ten) + "%" );
				
	    	}
	    	
	    	if (sodienthoai.length()>0){
				query = query + " and upper(a.dienthoai) like upper(?)";
				obj.getDataSearch().add( "%" +sodienthoai+ "%" );
	    	}
	    	
    	
	    	if (kvId.length()>0){
				query = query + " and d.pk_seq = ?";
				obj.getDataSearch().add(kvId);
				
	    	}
	    	
	    	if(trangthai.length() > 0){
	    		query = query + " and a.trangthai =?";
	    		obj.getDataSearch().add(trangthai);
	    		
	    	}
	    	
	    	if(loainppId.length() > 0){
	    		query = query + " and a.LoaiNPP= ?";
	    		obj.getDataSearch().add(loainppId);
	    	}
	    	//query = query + "  order by d.ten";
	    	System.out.println("Search query: " + query);

	    	return query;
		}	
	private String getSearchQuery1(HttpServletRequest request)
	{	
		
		INhaphanphoiList obj = new NhaphanphoiList();
		
		Utility util = new Utility();
		
			String ten = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppTen")));
	    	if ( ten == null)
	    		ten = "";
	    	obj.setTen(ten);
	    	
	    	String sodienthoai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("DienThoai")));
	    	if (sodienthoai == null)
	    		sodienthoai = "";    	
	    	obj.setSodienthoai(sodienthoai);
	    	    	
	    	String kvId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kvId")));
	    	if (kvId == null)
	    		kvId = "";    	
	    	obj.setKvId(kvId);
	    	
	    	String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("TrangThai")));   	
	    	if (trangthai == null)
	    		trangthai = "";    	
		
	    	if (trangthai.equals("2"))
	    		trangthai = "";
	    	
	    	obj.setTrangthai(trangthai);
	    	String query ="select distinct kbh.diengiai as kenh,npp.manpp, npp.pk_seq,npp.sitecode,isnull(npp.ten,N'Chưa xác định') as ten,isnull(npp.diachi,N'Chưa xác định') as diachi,isnull(npp.dienthoai,N'Chưa xác định') as dienthoai ,npp.khosap,case when npp.trangthai='1' then N'Hoạt động' else N'Ngưng hoạt động' end as trangthai, "+ 
	    	" isnull(npp.masothue,N'Chưa xác đinh') as masothue,isnull(npp.diachixhd,N'Chưa xác định') as diachixhd,isnull(tinhthanh.ten,N'Chưa xác đinh') as tinhthanh, isnull(quanhuyen.ten,N'Chưa xác định') as quanhuyen, "+
	    	" isnull(vung.ten,N'Chưa xác định') as tenvung,isnull(khuvuc.ten,N'Chưa xác đinh') as tenkhuvuc,gsbh.pk_seq as gsid,isnull(gsbh.ten,N'Chưa xác định') as tengs,isnull(gsbh.dienthoai,N'Chưa xác định') as dienthoaigs,isnull(gsbh.diachi,N'Chưa xác định') as diachigs  "+
	    	" from nhaphanphoi npp "+
	    	" left join  NHAPP_GIAMSATBH b on npp.pk_seq=b.npp_fk "+
	    	" left join giamsatbanhang gsbh on gsbh.pk_Seq=b.gsbh_fk" +
	    	" left join kenhbanhang kbh on kbh.pk_seq=gsbh.kbh_fk  "+
	    	" left join khuvuc on khuvuc.pk_seq=npp.khuvuc_fk  "+
	    	" left join vung on vung.pk_seq=khuvuc.vung_fk "+
	    	" left join tinhthanh on tinhthanh.pk_Seq=npp.tinhthanh_fk "+
	    	" left join quanhuyen on quanhuyen.pk_Seq=npp.quanhuyen_fk  where 1=1";
	    	
	    	if (ten.length()>0){
	    		//Utility util = new Utility();
				query = query + " and upper(npp.ten) like upper(N'%" + util.replaceAEIOU(ten) + "%')";
				
	    	}
	    	
	    	if (sodienthoai.length()>0){
				query = query + " and upper(npp.dienthoai) like upper('%" + sodienthoai + "%')";
				
	    	}
	    	
    	
	    	if (kvId.length()>0){
				query = query + " and npp.khuvuc_fk = '" + kvId + "'";
				
	    	}
	    	
	    	if(trangthai.length() > 0){
	    		query = query + " and npp.trangthai = '" + trangthai + "'";
	    		
	    	}
	    	//query = query + "  order by d.ten";
	    	
	    	System.out.println("Jkkdd :"+query);
	    	
	    	return query;
	    	
		}	
	boolean kiemtra(String sql)
	{dbutils db =new dbutils();
    	ResultSet rs = db.get(sql);
		try {//kiem tra ben san pham
		while(rs.next())
		{ if(rs.getString("num").equals("0"))
		   return true;
		}
		} catch(Exception e) {
		
			e.printStackTrace();
		}
		 return false;
	}

	private void Delete(String id)
	{
		
		//INhaphanphoiList obj = new NhaphanphoiList();
		
		dbutils db = new dbutils();
		try {
		if(!Utility.KiemTra_PK_SEQ_HopLe( id, "nhaphanphoi", db))
		{
			db.getConnection().rollback();
			db.getConnection().setAutoCommit(true);
			
		}
		String sql =" update nhaphanphoi set trangthai = '0' where pk_seq = '"+ id +"' ";
		db.update(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.shutDown();
	}
	private void DeleteToaDo(String id,String loai)
	{
		String sql  = "";
		dbutils db = new dbutils();
		try {
			if(!Utility.KiemTra_PK_SEQ_HopLe( id, "nhaphanphoi", db))
			{
				
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				
			}
			if(loai.equals("1"))
				sql =" update nhaphanphoi set lat = null,long = null where pk_seq = '"+ id +"' ";
			else
				sql =" update nhaphanphoi set lat1 = null,long1 = null where pk_seq = '"+ id +"' ";
			db.update(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		db.shutDown();
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
	    for(int j = 26; j <= i; j++)
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
