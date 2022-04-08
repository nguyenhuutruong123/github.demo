package geso.dms.center.servlets.reports;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;


@WebServlet("/UsingPromoTT")
public class UsingPromoTT extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public UsingPromoTT() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		Utility util=new Utility();
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();	  
		
		String userTen = (String)session.getAttribute("userTen");
		obj.setuserTen(userTen==null? "":userTen);
		
		String querystring=request.getQueryString();
		String userId=	util.getUserId(querystring);
		obj.setuserId(userId==null? "":userId);
		
		obj.init();
		session.setAttribute("obj", obj);	
		session.setAttribute("userTen", userTen);
		String nextJSP = "/AHF/pages/Center/UsingPromoTT.jsp";
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
		try
		    {
				String userId = (String) session.getAttribute("userId");
				String userTen = (String) session.getAttribute("userTen");	
				//String nppId = (String) session.getAttribute("nppId");
				//obj.setnppId(nppId);
				obj.setuserId(userId==null? "":userId);
				obj.setuserTen(userTen==null? "":userTen);
				obj.settungay(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays")==null? "":request.getParameter("Sdays")));			
				obj.setdenngay(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays")==null? "":request.getParameter("Edays")));
				
				
				obj.setkenhId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")==null? "":request.getParameter("kenhId")));				
				obj.setdvkdId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")==null? "":request.getParameter("dvkdId")));
				obj.setnhanhangId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhanhangId")==null? "":request.getParameter("nhanhangId")));
				obj.setchungloaiId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chungloaiId")==null? "":request.getParameter("chungloaiId")));				
				obj.setPrograms(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("programs")==null? "":request.getParameter("programs")));
				obj.setnppId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("npp")==null? "":request.getParameter("npp")));
				obj.setvungId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vung")==null? "":request.getParameter("vung")));
				obj.setkhuvucId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvuc")==null? "":request.getParameter("khuvuc")));
		
				
				String condition = "";
				if(obj.getvungId().length()>0)
					condition +=" and v.pk_seq = '"+ obj.getvungId() +"'";
				
				if(obj.getkhuvucId().length()>0)
					condition +=" and kv.pk_seq = '"+ obj.getkhuvucId() +"'";
				
				if(obj.getnppId().length()>0)
					condition +=" and npp.pk_seq = '"+ obj.getnppId() +"'";
				
				if(obj.getkenhId().length()>0)
					condition +=" and kbh.pk_seq ='" + obj.getkenhId() +"'";
				
				if(obj.getPrograms().length()>0)
					condition +=" and ctkm.scheme = '" + obj.getPrograms() +"'";
				
				if(obj.getdvkdId().length()>0)
					condition +=" and dvkd.pk_seq = '" + obj.getdvkdId() +"'";
				
				if(obj.getnhanhangId().length()>0)
					condition +=" and nh.pk_seq = '" + obj.getnhanhangId() +"'";
				
				if(obj.getchungloaiId().length()>0)
					condition +=" and cl.pk_seq = '" + obj.getchungloaiId() +"'";
				
				String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));				
				if (action.equals("create")) {
					response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition",
							"attachment; filename=SuDungChiTraKhuyenMai(TT).xlsm");
					CreatePivotTable(out,obj,condition);
				}		        
		     }
		    catch (Exception ex)
		    {
		       obj.setMsg(ex.getMessage());
		       System.out.println("Loi : "+ex.toString());
		    }
		    obj.init();
			session.setAttribute("obj", obj);	
			session.setAttribute("userTen", obj.getuserTen());
			String nextJSP = "/AHF/pages/Center/UsingPromoTT.jsp";
			response.sendRedirect(nextJSP);		
	}
	
	private void CreatePivotTable(OutputStream out,IStockintransit obj, String condition) throws Exception
    {       
 		//String strfstream = getServletContext().getInitParameter("path")+"\\SuDungChiTraKhuyenMaiTT.xlsm";
 		//FileInputStream fstream = new FileInputStream(strfstream);
 		File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\SuDungChiTraKhuyenMaiTT.xlsm");
		FileInputStream fstream = new FileInputStream(f) ;
		Workbook workbook = new Workbook();
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateStaticHeader(workbook,obj);	     
	    CreateStaticData(workbook, obj, condition);
	    workbook.save(out);
	    fstream.close();
	}
 	
 	private void CreateStaticHeader(Workbook workbook, IStockintransit obj)throws Exception 
 	{
 		
 		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");
		
	    Cells cells = worksheet.getCells();
	    Style style;
	    Font font = new Font();
	    font.setColor(Color.RED);//mau chu
	    font.setSize(16);// size chu
	   	font.setBold(true);
	   	
	    cells.setRowHeight(0, 20.0f);
	    Cell cell = cells.getCell("A1");
	    style = cell.getStyle();
	    style.setFont(font); 
	    style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu 	   
	    
	    cell.setValue("BÁO CÁO SỬ DỤNG VÀ CHI TRẢ KHUYẾN MÃI");  getCellStyle(workbook,"A1",Color.RED,true,14);	  	
	    
	    cells.setRowHeight(2, 18.0f);
	    cell = cells.getCell("A3"); 
	    getCellStyle(workbook,"A3",Color.NAVY,true,10);	    
	    cell.setValue("Từ ngày: " + obj.gettungay());
	    
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("B3"); getCellStyle(workbook,"B3",Color.NAVY,true,9);	        
	    cell.setValue("Đến ngày: " + obj.getdenngay());    
	    
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A4");getCellStyle(workbook,"A4",Color.NAVY,true,9);
	    cell.setValue("Ngày báo cáo: " + this.getdate());
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A5");getCellStyle(workbook,"A5",Color.NAVY,true,9);
	    cell.setValue("Được tạo bởi:  " + obj.getuserTen());
			  
	    cell = cells.getCell("AA1"); cell.setValue("Kenh");
		cell = cells.getCell("AB1"); cell.setValue("DonViKinhDoanh");
		cell = cells.getCell("AC1"); cell.setValue("Vung");
 	    cell = cells.getCell("AD1"); cell.setValue("Khu vuc");  	
 	   cell = cells.getCell("AE1"); cell.setValue("MaNhaPP");
	    cell = cells.getCell("AF1"); cell.setValue("Nha Phan Phoi");
	    cell = cells.getCell("AG1"); cell.setValue("Ma Chuong Trinh KM");
	    cell = cells.getCell("AH1"); cell.setValue("Chuong Trinh KM");
	    
	    cell = cells.getCell("AI1"); cell.setValue("NhanHang");
	    cell = cells.getCell("AJ1"); cell.setValue("ChungLoai");
	    cell = cells.getCell("AK1"); cell.setValue("MaSanPham");
	    cell = cells.getCell("AL1"); cell.setValue("TenSanPham");
	    cell = cells.getCell("AM1"); cell.setValue("SoLuong");
	    cell = cells.getCell("AN1"); cell.setValue("ThanhTien");
	    cell = cells.getCell("AO1"); cell.setValue("Type");
	    cell = cells.getCell("AP1"); cell.setValue("LoaiPhanBo");
	    cell = cells.getCell("AQ1"); cell.setValue("TenVietTat");
	    
 	}
 	
	private void CreateStaticData(Workbook workbook, IStockintransit obj, String condition) throws Exception
 	{
 		Worksheets worksheets = workbook.getWorksheets();
 	    Worksheet worksheet = worksheets.getSheet(0);
 	    Cells cells = worksheet.getCells();
 	    dbutils db = new dbutils();	  
 	    Utility Ult =new Utility();
 	    String sql = " select CTKM.Type AS Type,kbh.TEN AS Kenh,npp.SITECODE as Manpp,npp.TEN AS NPP, ct_km.SCHEME as MaCTKM,ct_km.DIENGIAI AS CTKM,sp.MA as MaSp,sp.TEN AS sanpham, sp.tenviettat,nh.TEN AS Nhanhang, " +
 	    			 " cl.TEN as chungloai,kv.TEN AS KhuVuc,v.TEN AS Vung,dvkd.diengiai as dvkd,CTKM.SoLuong AS SoLuong, " +
 	    			 " CTKM.SoLuong as SanLuong,CTKM.tonggiatri AS TongGiaTri , ct_km.PHANBOTHEODONHANG " +
 	    			 " from " +
 	    			 " ( " +
 	    			 " 		   select N'Sử dụng' as Type,dh.KBH_FK,dh.NPP_FK,dh_km.CTKMID,isnull(dh_km.SPMA,'') as spma ,SUM(dh_km.SOLUONG) as SoLuong ,SUM(dh_km.TONGGIATRI) as tonggiatri "+
 	    			 " 		   from  DONHANG dh "+
 	    			 " 		   inner join DONHANG_CTKM_TRAKM dh_km on dh_km.DONHANGID=dh.PK_SEQ  "+
 	    			 " 		   where dh.TRANGTHAI=1 and dh_km.DONHANGID not in(select DONHANG_FK from DONHANGTRAVE where DONHANG_FK is not null and TRANGTHAI=3) "+
 	    			 " 		   and dh.NGAYNHAP >='" + obj.gettungay() + "' and dh.NGAYNHAP <= '" + obj.getdenngay() + "' "+
 	    			 " 		   group by dh.KBH_FK,dh.NPP_FK,dh_km.CTKMID,dh_km.SPMA "+
 	    			 " 			   UNION ALL "+
					" select N'Chi trả' as Type,a.KBH_FK,a.NPP_FK,a.CTKMID,a.spma,isnull(( select  sum(soluong) "+ 
					"   from nhaphang hd  "+
					" 	   inner join nhaphang_sp hd_sp on hd.pk_seq=hd_sp.nhaphang_fk "+ 
					" 	   inner join ctkhuyenmai ctkm on ctkm.SCHEME=hd_sp.SCHEME "+  
					"  where hd_sp.GIANET=0   "+
					" 		and hd.KBH_FK=a.KBH_FK and a.NPP_FK=hd.NPP_FK and a.CTKMID=ctkm.PK_SEQ and a.spma= isnull(hd_sp.SANPHAM_FK,'') "+ 
					" 			),0 )as soluong , "+
					" 		 0 as thanhtien   "+
 	    			 " 		   from ( "+
 	    			 " 				   select  dh.KBH_FK,dh.NPP_FK,dh_km.CTKMID,isnull(dh_km.SPMA,'') as spma    "+
 	    			 " 				   from  DONHANG dh "+
 	    			 " 				   inner join DONHANG_CTKM_TRAKM dh_km on dh_km.DONHANGID=dh.PK_SEQ  "+
 	    			 " 				   where dh.TRANGTHAI=1 and dh_km.DONHANGID not in(select DONHANG_FK from DONHANGTRAVE where DONHANG_FK is not null and TRANGTHAI=3) "+
 	    			 " 				   and dh.NGAYNHAP >='" + obj.gettungay() + "' and dh.NGAYNHAP <= '" + obj.getdenngay() + "' "+
 	    			 " 				   group by dh.KBH_FK,dh.NPP_FK,dh_km.CTKMID,dh_km.SPMA "+
 	    			 " 		        ) a "+
 	    			 " ) CTKM "+
 	    			 " 		   left join KENHBANHANG kbh on CTKM.KBH_FK = kbh.PK_SEQ "+
 	    			 " 		   left join NHAPHANPHOI npp on npp.PK_SEQ=CTKM.NPP_FK "+
 	    			 " 		   left join CTKHUYENMAI ct_km on ct_km.PK_SEQ=CTKM.CTKMID "+
 	    			 " 		   left join SANPHAM sp on CTKM.SPMA=sp.MA "+
 	    			 "		   left join NHANHANG nh on sp.NHANHANG_FK=nh.PK_SEQ " +
 	    			 "		   left join CHUNGLOAI cl on cl.PK_SEQ=sp.CHUNGLOAI_FK	" +
 	    			 "		   left join KHUVUC kv on kv.PK_SEQ=npp.KHUVUC_FK " +
 	    			 "		   left join VUNG v on v.PK_SEQ=kv.VUNG_FK" +
 	    			 "		   left join DONVIKINHDOANH dvkd on dvkd.pk_seq=nh.dvkd_fk " +
 	    			 " 		   where 1=1 AND npp.pk_seq in " + Ult.quyen_npp(obj.getuserId()) +" and kbh.pk_seq in " + Ult.quyen_kenh(obj.getuserId());
 	    			if(obj.getvungId().length()>0)
 	    				sql += " AND v.pk_seq = '" + obj.getvungId() + "' ";
 	    			if(obj.getkenhId().length()>0)
 	    				sql += " AND kbh.pk_seq='" + obj.getkenhId() + "' ";
 	    			if(obj.getnhanhangId().length()>0)
 	    				sql += " AND nh.pk_seq='" + obj.getnhanhangId() + "' ";
 	    			if(obj.getkhuvucId().length()>0)
 	    				sql += " AND kv.pk_seq='" + obj.getkhuvucId() + "' ";
 	    			if(obj.getdvkdId().length()>0)
 	    				sql += " AND dvkd.pk_seq='" + obj.getdvkdId() + "' ";
 	    			if(obj.getnppId().length()>0)
 	    				sql += " AND npp.pk_seq='" + obj.getnppId() + "'";
 	    			if(obj.getchungloaiId().length()>0)
 	    				sql += " AND cl.pk_seq='" + obj.getchungloaiId() + "' ";
 	    			
		System.out.println("Get Sql abcefg : "+sql);
 	   	ResultSet rs = db.get(sql); 	   
 	    int i = 2;
 		if(rs!=null)
 		{
 			
 			try 
 			{
 				cells.setColumnWidth(0, 19.0f);
 				cells.setColumnWidth(1, 50.0f);
 				cells.setColumnWidth(2, 12.0f);
 				cells.setColumnWidth(3, 12.0f);
 				cells.setColumnWidth(4, 20.0f);
 				cells.setColumnWidth(5, 20.0f);
 				cells.setColumnWidth(6, 20.0f);
 				cells.setColumnWidth(7, 20.0f);
 				cells.setColumnWidth(8, 20.0f);
 				cells.setColumnWidth(9, 20.0f);
 				cells.setColumnWidth(10, 20.0f);
 				cells.setColumnWidth(6, 20.0f);
 				cells.setColumnWidth(7, 20.0f);
 				cells.setColumnWidth(8, 20.0f);
 				cells.setColumnWidth(9, 20.0f);
 				cells.setColumnWidth(10, 20.0f);
 				cells.setColumnWidth(11, 20.0f);
 				
 				Cell cell = null;
 				Style style;
 				
 				while(rs.next())
 				{  
 					
					cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(rs.getString("Kenh"));
					cell = cells.getCell("AB" + Integer.toString(i)); cell.setValue(rs.getString("dvkd"));			
					cell = cells.getCell("AC" + Integer.toString(i)); cell.setValue(rs.getString("Vung"));
 					cell = cells.getCell("AD" + Integer.toString(i)); cell.setValue(rs.getString("KhuVuc"));
 					cell = cells.getCell("AE" + Integer.toString(i)); cell.setValue(rs.getString("Manpp"));
					cell = cells.getCell("AF" + Integer.toString(i)); cell.setValue(rs.getString("NPP"));
					
					cell = cells.getCell("AG" + Integer.toString(i)); cell.setValue(rs.getString("MaCTKM"));
					cell = cells.getCell("AH" + Integer.toString(i)); cell.setValue(rs.getString("CTKM"));
					
					cell = cells.getCell("AI" + Integer.toString(i)); cell.setValue(rs.getString("Nhanhang"));
					cell = cells.getCell("AJ" + Integer.toString(i)); cell.setValue(rs.getString("chungloai"));
					cell = cells.getCell("AK" + Integer.toString(i)); cell.setValue(rs.getString("MaSp"));
					cell = cells.getCell("AL" + Integer.toString(i)); cell.setValue(rs.getString("sanpham"));

					style = cell.getStyle();
					style.setNumber(2);
					cell.setStyle(style);

					cell = cells.getCell("AM" + Integer.toString(i)); cell.setValue(rs.getDouble("SoLuong"));
					style = cell.getStyle();
					style.setNumber(2);
					cell.setStyle(style);

					cell = cells.getCell("AN" + Integer.toString(i)); cell.setValue(rs.getDouble("TongGiaTri"));
					
					cell = cells.getCell("AO" + Integer.toString(i)); cell.setValue(rs.getString("Type"));
					cell = cells.getCell("AP" + Integer.toString(i)); cell.setValue(rs.getDouble("PHANBOTHEODONHANG")==0?"Tiền":"Số lượng");
					cell = cells.getCell("AQ" + Integer.toString(i)); cell.setValue(rs.getString("Tenviettat"));
					i++;
 				}
			    setAn(workbook, 50);
 		}catch (Exception e){ 	
 			System.out.println("LOI : "+e.toString());
 			throw new Exception("Khong tao duoc bao cao trong thoi gian nay. Loi : "+e.toString());
 		}
 		finally{
 			if(rs != null)
 			rs.close();
 			if(db!=null){
 				db.shutDown();
 			}
 		}
 		}else{
 			throw new Exception("Khong tao duoc bao cao trong thoi gian nay...");
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
	    
		//Setting the horizontal alignment of the text in the cell 
	    style.setHAlignment(TextAlignmentType.LEFT);
	    cell.setStyle(style);
	}

	private String getdate() 
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy: hh:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	private void setAn(Workbook workbook,int i)
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	    for(int j = 26; j <= i; j++)
	    { 
	    	cells.hideColumn(j);
	    }
		
	}

}
