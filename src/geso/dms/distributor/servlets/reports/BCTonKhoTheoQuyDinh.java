package geso.dms.distributor.servlets.reports;

import geso.dms.center.beans.khoahuanluyen.IKhoahuanluyenList;
import geso.dms.center.beans.khoahuanluyen.imp.KhoahuanluyenList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.distributor.util.Utility;

import java.io.File;
import java.io.FileInputStream;
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

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class BCTonKhoTheoQuyDinh extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public BCTonKhoTheoQuyDinh() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		IKhoahuanluyenList obj = new KhoahuanluyenList();
		String querystring = request.getQueryString();
		
		Utility util = new Utility();
		obj.setUserId(util.getUserId(querystring));
		obj.setUserten((String) session.getAttribute("userTen"));
		
		obj.initBC("");
		session.setAttribute("obj", obj);		
		session.setAttribute("userId", obj.getUserId());
		session.setAttribute("userTen", obj.getUserTen());
		
	
		
				String nextJSP = "/AHF/pages/Distributor/BCTonKHoTheoQuyDinhNPP.jsp";
				response.sendRedirect(nextJSP);
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		IKhoahuanluyenList obj = new KhoahuanluyenList();
		OutputStream out = response.getOutputStream();
		Utility util = new Utility();
		
		String userId = (String) session.getAttribute("userId");
		obj.setUserId(userId);
		
		String userTen = (String) session.getAttribute("userTen");
		obj.setUserten(userTen);
		
		
		String kbh=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId"));
		if(kbh==null)
			kbh="";
		System.out.println("Kenh ban hang :"+kbh);
		
		obj.setKbhId(kbh);
		
		String nppid=util.getIdNhapp(userId);
		obj.setNppId(nppid);
		
		
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action") != null? util.antiSQLInspection(request.getParameter("action"))) : "";
		String nextJSP = "";
			
				nextJSP = "/AHF/pages/Distributor/BCTonKHoTheoQuyDinhNPP.jsp";
			
		String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
		if(tungay == null)
			tungay = getDateTime();
		obj.setTungay(tungay);
		
		
		String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"));
		if( denngay == null)
			 denngay = getDateTime();
		obj.setDenngay(denngay);
		
		
		if(action.equals("Taomoi"))
		{
			try
			{
			

				
				String[] nppIds =new String[1];
				
				nppIds[0]=nppid;
				
				String[] ngayquydinh =new String[1]; 
				
				ngayquydinh[0]=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngayquydinh"));
				
				System.out.println("ngay quy dinh : "+ngayquydinh[0]);
				System.out.println("ngay npp  : "+nppIds[0]);
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=TonKhoTheoQuyDinh.xlsm");
				boolean kq=CreatePivotTable_TKQD(out, obj, nppIds, ngayquydinh);
				System.out.println("Ket qua..: "+kq);
				if(!kq)
		        {
		        	obj.setMsg("Không có dữ liệu trong thời gian này.");
		        	obj.initBC("");
		    		session.setAttribute("obj", obj);	
		    		System.out.println("Loi tren..: "+obj.getMsg());
		    		response.sendRedirect(nextJSP);
		    		return;
		    		
		        }
				
						
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				System.out.println("Loi: "+ex.toString());
				obj.setMsg(ex.getMessage());
			}
		}
	
		obj.initBC("");
		session.setAttribute("obj", obj);	
		response.sendRedirect(nextJSP);
	}

	

	
	
	/***************** Begin Ton Kho Theo Quy Dinh ***********************/
	private boolean CreatePivotTable_TKQD(OutputStream out, IKhoahuanluyenList obj, String[] nppIds, String[] ngayquydinh) throws Exception
	{
		String chuoi = getServletContext().getInitParameter("path") + "\\TonKhoTheoQuyDinh.xlsm";
		
		FileInputStream fstream = new FileInputStream(chuoi);
		//File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\TonKhoTheoQuyDinh.xlsm");
		//FileInputStream fstream = new FileInputStream(f) ;	
		Workbook workbook = new Workbook();
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateStaticHeader_TKQD(workbook, obj);

		boolean isFill = CreateStaticData_TKQD(workbook, obj, nppIds, ngayquydinh);
		
		if (!isFill){
			fstream.close();
			return false;
		}else {
			workbook.save(out);
			fstream.close();
			return true;
		}
	}
	
	private void CreateStaticHeader_TKQD(Workbook workbook, IKhoahuanluyenList obj) throws Exception
	{
		try
		{	
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");

			Cells cells = worksheet.getCells();

			cells.setRowHeight(0, 20.0f);
			Cell cell = cells.getCell("A1");
		    cell.setValue("TỒN KHO THEO QUY ĐỊNH");   	
		    
		    cells.setRowHeight(4, 18.0f);
		    cell = cells.getCell("A2");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
		    
		    cells.setRowHeight(5, 18.0f);
		    cell = cells.getCell("A3");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getUserTen());
			
		    cell = cells.getCell("DA1"); 	cell.setValue("Vung");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DB1"); 	cell.setValue("KhuVuc");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DC1"); 	cell.setValue("NhaPhanPhoi");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DD1"); 	cell.setValue("SoNgayLamViec");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DE1"); 	cell.setValue("ChiTieu");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DF1"); 	cell.setValue("TonQuyDinh");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DG1"); 	cell.setValue("TonThucTe");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DH1"); 	cell.setValue("Ngay");  ReportAPI.setCellHeader(cell);
		    
		}
		catch(Exception ex)
		{
			throw new Exception("Bao cao bi loi khi khoi tao");
		}
	}

	private boolean CreateStaticData_TKQD(Workbook workbook, IKhoahuanluyenList obj, String[] nppIds, String[] ngayquydinh) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
		geso.dms.center.util.Utility util=new geso.dms.center.util.Utility();
		
	    String nam = obj.getTungay().substring(0, 4);
	    String thang = obj.getTungay().substring(5, 7);	    	  
	    	    
	    Integer tungay = Integer.parseInt(obj.getTungay().substring(8,obj.getTungay().trim().length()));
	    Integer denngay = Integer.parseInt(obj.getDenngay().substring(8,obj.getDenngay().trim().length()));
	    int i = 2;
	    System.out.println("Tu Ngay: " + tungay + "Den ngay: " + denngay + "Thang: " + thang + " -- Nam: " + nam);
	    
	    String query = "";	    
	    if(nppIds != null)
	    {
		    for(int k=tungay; k<=denngay; k++ )
		    {
		    	String str = "00"+k;
		    	System.out.println("STR : "+str);
		    	String d = str.substring(str.length()-2, str.length());
		    	System.out.println("D : "+d);
		    	String temp = "";
			    	for(int z = 0; z < nppIds.length; z++)
			    	{
			    		if(ngayquydinh[z] == "")
			    			ngayquydinh[z] = "0";
			    		
			    		temp +=  " select " + nppIds[z] + " as nppId, '" + ngayquydinh[z] + "' as ngaytonkho union";
			    	}
			    	
			    	if(temp.trim().length() > 0)
			    	{
			    		temp = temp.substring(0, temp.length() - 6);
			    	}
			    	
			    	query = 
				    		" select v.pk_seq as vungId, v.ten as vungTen, kv.pk_seq as kvId, kv.ten as kvTen, npp.ten as nppTen, tonquydinh.nppId, \n" +
							" tonquydinh.songaylamviec, tonquydinh.chitieu, tonquydinh.tonquydinh, tonthucte.tonkhoNpp,'"+obj.getTungay().substring(0,8)+d+"' as ngay  \n" +
							" from \n" +
							" ( \n" +
							"	select temp.nppId, isnull(chitieu.SONGAYLAMVIEC, 0) as SONGAYLAMVIEC, isnull(chitieu.ct, 0) as chitieu,  \n" +
							"	ISNULL( cast( (chitieu.ct *  temp.ngaytonkho   / chitieu.SONGAYLAMVIEC ) as numeric(18, 0) ) , 0) as tonquydinh, '1' as loaiTon    \n" +
							"	from \n" +
							"	( \n" +
									temp +
							"	) temp \n" +
									
							"	left join \n" +
							"	( \n" +
							"		select b.nhapp_fk as nppId, a.songaylamviec, b.chitieu as ct \n" +
							"		from CHITIEU a inner join CHITIEU_NHAPP b on a.pk_seq = b.chitieu_fk   \n" +
							"		where a.trangthai = '1' and a.thang = '" + Integer.parseInt(thang) +  "' and a.nam = '" + nam + "'	\n " +
							"	) chitieu on temp.nppId = chitieu.nppId \n" +
							") tonquydinh \n" +
							" INNER join \n" +
							"( \n" +
							/*"	select tonngay.NPP_FK, tonngay.tongtonkho + isnull(chuanhap.tongluong, '0') - isnull(danhap.tongluongDaNhap, '0') + isnull(unghang.nppUnghang, 0) as tonkhoNpp, '1' as loaiTon     \n" +
							"	from \n" +
							"	( \n" +
							"		select npp_fk, isnull(sum(a.SOLUONG * b.TRONGLUONG ), 0) as tongtonkho, '1' as type   \n" +
							"		from tonkhongay a inner join SANPHAM b on a.SANPHAM_FK = b.PK_SEQ where ngay = '" + obj.getTungay().substring(0,8)+d + "' \n" +
							"		group by npp_fk \n" +
							"	) tonngay \n" +
									
							"	left join \n" +
							"	( \n" +
							"		select a.NPP_FK, sum( cast(b.soluong as float) * c.TRONGLUONG ) as tongluong, '1' as type   \n" +
							"		from hdnhaphang a inner  join hdnhaphang_sp b on a.pk_seq = b.nhaphang_fk   \n" +
							"		inner join SANPHAM c on b.SANPHAM_FK = c.MA  \n" +
							"		where a.ngaychungtu <=  '" + obj.getTungay().substring(0,8)+d + "' and a.ngaychungtu >= '2013-10-01' and a.trangthai != '2'   \n" +
							"		group by NPP_FK \n" +
							"	) chuanhap on tonngay.type = chuanhap.type and tonngay.NPP_FK = chuanhap.NPP_FK \n" +
								
							"	left join \n" +
							"	( \n" +
							"		select a.NPP_FK, isnull(sum(cast(b.soluong as float) * c.TRONGLUONG ), 0) as tongluongDaNhap, '1' as type   \n" +
							"		from nhaphang a inner join nhaphang_sp b on a.pk_seq = b.nhaphang_fk    inner join SANPHAM c on b.SANPHAM_FK = c.MA \n" +
							"		where a.ngaynhan <= '" + obj.getTungay().substring(0,8)+d + "' and a.ngaynhan >= '2013-10-01' " +
							"		AND a.ngaychungtu <= '" + obj.getTungay().substring(0,8)+d + "' and a.ngaychungtu >= '2013-10-01' " +							
							"		and a.trangthai = '1' \n" +
							"		group by a.NPP_FK \n" +
							"	) danhap on tonngay.type = danhap.type and tonngay.NPP_FK = danhap.NPP_FK \n" +
												
							"	left join \n" +
							"	( \n" +
							"		select NPP_FK,sum(nppUnghang) as nppUnghang,'1'as type \n"+
							"		from \n"+ 
							"		( \n"+
							"			select b.NPP_FK,  isnull( sum(soluong * c.TRONGLUONG) , 0) as nppUnghang, '1' as type \n"+    
							"			from donhang_ctkm_trakm a inner join DONHANG b on a.DONHANGID = b.PK_SEQ \n"+   
							"			inner join SANPHAM c on a.SPMA = c.MA \n"+     
							"			inner join ctkhuyenmai ctkm on ctkm.pk_seq=a.ctkmid  and ctkm.kho_fk=100000 \n"+ 
							"			where spMa is not null and b.ngaynhap <= '" + obj.getTungay().substring(0,8)+d + "' and b.ngaynhap >= '2013-10-01' and b.trangthai = '1' \n"+  
							"			AND b.PK_SEQ NOT IN (	SELECT DONHANG_FK FROM DONHANGTRAVE WHERE DONHANG_FK IS NOT NULL AND TRANGTHAI = '3' ) \n"+ 
							"			group by b.NPP_FK \n"+
							
							"			union all \n"+
							"			select a.NPP_FK,(-1)* sum( cast(b.soluong as float) * c.TRONGLUONG ) as nppUnghang, '1' as type \n"+   
							"			from hdnhaphang a inner  join hdnhaphang_sp b on a.pk_seq = b.nhaphang_fk \n"+   
							"			inner join SANPHAM c on b.SANPHAM_FK = c.MA \n" +
							"			inner join CTKHUYENMAI ctkm on ctkm.SCHEME = b.SCHEME \n"+															
							"			where a.trangthai != '2' and a.loaidonhang=1 and a.KHO_FK = '100000' \n" +
							"			and ctkm.PK_SEQ in \n" +
							"			( \n" +
							" 	 			select distinct ctkm.pk_seq " +
							"				from donhang_ctkm_trakm a inner join DONHANG b on a.DONHANGID = b.PK_SEQ " +
							"    			inner join SANPHAM c on a.SPMA = c.MA " +
							"				inner join ctkhuyenmai ctkm on ctkm.pk_seq=a.ctkmid  and ctkm.kho_fk=100000 " +
							"				where spMa is not null and b.ngaynhap <= '" + obj.getTungay().substring(0,8)+d + "' and b.ngaynhap >= '2013-10-01' " +
							"	    		and b.trangthai = '1' " +
							"	    		AND b.PK_SEQ NOT IN (SELECT DONHANG_FK FROM DONHANGTRAVE WHERE DONHANG_FK IS NOT NULL AND TRANGTHAI = '3' ) " +
							"			) " +												
							"			group by NPP_FK \n"+ 						
							"		) as ctkm \n"+
							"		group by npp_fk \n"+ 
							"	) unghang on tonngay.type = unghang.type and tonngay.NPP_FK = unghang.NPP_FK  \n" +*/
							   " select tonngay.NPP_FK,SUM( tonngay.tongtonkho ) as tonkhoNpp, '1' as loaiTon      \n" +  
							   "  	from  \n" +  
							   "  	(  \n" +  
							   "  		select npp_fk, isnull(sum(a.SOLUONG * b.TRONGLUONG ), 0) as tongtonkho, '1' as type    \n" +  
							   "  		from tonkhongay a inner join SANPHAM b on a.SANPHAM_FK = b.PK_SEQ where  KHO_FK=100000 and ngay = '" + obj.getTungay().substring(0,8)+d + "'  \n" +  
							   "  		group by npp_fk  \n" +  
							   "  		 union all \n" +  
							   "  		select a.NPP_FK, sum( cast(b.soluong as float) * c.TRONGLUONG ) as tongluong, '1' as type    \n" +  
							   "  		from hdnhaphang a inner  join hdnhaphang_sp b on a.pk_seq = b.nhaphang_fk    \n" +  
							   "  		inner join SANPHAM c on b.SANPHAM_FK = c.MA   \n" +  
							   "  		where a.ngaychungtu <=  '" + obj.getTungay().substring(0,8)+d + "' and a.ngaychungtu >= '2013-10-01' and a.trangthai != '2'   AND  ISNULL(a.LOAIDONHANG,'0')='0'   \n" +  
							   "  		group by NPP_FK  \n" +  
							   "  			 union all \n" +  
							   "  		select a.NPP_FK, (-1)* isnull(sum(cast(b.soluong as float) * c.TRONGLUONG ), 0) as tongluongDaNhap, '1' as type    \n" +  
							   "  		from nhaphang a inner join nhaphang_sp b on a.pk_seq = b.nhaphang_fk    inner join SANPHAM c on b.SANPHAM_FK = c.MA  \n" +  
							   "  		where a.ngaynhan <= '" + obj.getTungay().substring(0,8)+d + "' and a.ngaynhan >= '2013-10-01' 	\n" +
							    "	AND a.ngaychungtu <= '" + obj.getTungay().substring(0,8)+d + "' and a.ngaychungtu >= '2013-10-01' 		and a.trangthai = '1' \n" +
							    " and exists (SELECT PK_SEQ FROM HDNHAPHANG HDNH WHERE HDNH.CHUNGTU=A.HDTAICHINH AND HDNH.NPP_FK=A.NPP_FK and a.ngaychungtu >= '2013-10-01' AND ISNULL(HDNH.LOAIDONHANG,'0')='0'  ) \n" +  
							   "  		group by a.NPP_FK  \n" +  
							/*   "  			 union all \n" +
							   "  			select b.NPP_FK,  isnull( sum(soluong * c.TRONGLUONG) , 0) as nppUnghang, '1' as type  \n" +  
							   "  			from donhang_ctkm_trakm a inner join DONHANG b on a.DONHANGID = b.PK_SEQ  \n" +  
							   "  			inner join SANPHAM c on a.SPMA = c.MA  " +  
							   "  			inner join ctkhuyenmai ctkm on ctkm.pk_seq=a.ctkmid  and ctkm.kho_fk=100000  \n" +  
							   "  			where spMa is not null and b.ngaynhap = '" + obj.getTungay().substring(0,8)+d + "' and b.trangthai = '1'  \n" +  
							   "  			AND b.PK_SEQ NOT IN (	SELECT DONHANG_FK FROM DONHANGTRAVE WHERE DONHANG_FK IS NOT NULL AND TRANGTHAI = '3' )  \n" +  
							   "  			group by b.NPP_FK  \n" +  
		*/
							   
							   " 			union all  		\n" +
							   "			select b.NPP_FK,  isnull( sum(soluong * c.TRONGLUONG) , 0) as nppUnghang, '1' as type \n " +  
							   "  			from donhang_ctkm_trakm a inner join DONHANG b on a.DONHANGID = b.PK_SEQ  \n" +  
							   "  			inner join SANPHAM c on a.SPMA = c.MA  \n" +  
							  // "  			inner join ctkhuyenmai ctkm on ctkm.pk_seq=a.ctkmid  and ctkm.kho_fk=100000  " +  
							   "  			where spMa is not null and b.ngaynhap <= '" + obj.getTungay().substring(0,8)+d + "' and b.ngaynhap >= '2013-09-01' and b.trangthai = '1'  \n" +  
							   "  			AND b.PK_SEQ NOT IN (	SELECT DONHANG_FK FROM DONHANGTRAVE WHERE DONHANG_FK IS NOT NULL AND TRANGTHAI = '3' )  \n" +  
							   "  			group by b.NPP_FK  \n" + 
							   "  			union all  \n" +  
		 

							   "    select a.NPP_FK, (-1)* isnull(sum(cast(b.soluong as float) * c.TRONGLUONG ), 0) as tongluongDaNhap, '1' as type "+     
							   "    from nhaphang a inner join nhaphang_sp b on a.pk_seq = b.nhaphang_fk    inner join SANPHAM c on b.SANPHAM_FK = c.MA "+  
							   "    where a.ngaynhan <='" + obj.getTungay().substring(0,8)+d + "' and a.ngaynhan >= '2013-10-01' 	 "+
							   "    AND a.ngaychungtu <= '" + obj.getTungay().substring(0,8)+d + "' and a.ngaychungtu >= '2013-10-01' 		and a.trangthai = '1' and a.LOAIHOADON=1   "+
							   "    and exists (SELECT PK_SEQ FROM HDNHAPHANG HDNH WHERE HDNH.CHUNGTU=A.HDTAICHINH AND HDNH.NPP_FK=A.NPP_FK and a.ngaychungtu >= '2013-10-01' AND ISNULL(HDNH.LOAIDONHANG,'0')='1'  ) "+ 
							   "    group by a.NPP_FK "+
							   
							  /* "  			union all  " +  
							   "  			select a.NPP_FK,(-1)* sum( cast(b.soluong as float) * c.TRONGLUONG ) as nppUnghang, '1' as type  " +  
							   "  			from hdnhaphang a inner  join hdnhaphang_sp b on a.pk_seq = b.nhaphang_fk  " +  
							   "  			inner join SANPHAM c on b.SANPHAM_FK = c.MA  " +  
							   "  			inner join CTKHUYENMAI ctkm on ctkm.SCHEME = b.SCHEME  " +  
							   "  			where a.trangthai != '2' and a.loaidonhang=1 and a.KHO_FK = '100000'  " +  
							   "  			and ctkm.PK_SEQ in  " +  
							   "  			(  " +  
							   "  				select distinct ctkm.pk_seq 				from donhang_ctkm_trakm a inner join DONHANG b on a.DONHANGID = b.PK_SEQ      " +  
							   "  				inner join SANPHAM c on a.SPMA = c.MA 				inner join ctkhuyenmai ctkm on ctkm.pk_seq=a.ctkmid  and ctkm.kho_fk=100000 " +  
							   "  				where spMa is not null and b.ngaynhap <= '" + obj.getTungay().substring(0,8)+d + "' and b.ngaynhap >= '2013-10-01' 	    " +  
							   "  				and b.trangthai = '1' 	    		AND b.PK_SEQ NOT IN (SELECT DONHANG_FK FROM DONHANGTRAVE " +  
							   "  				WHERE DONHANG_FK IS NOT NULL AND TRANGTHAI = '3' ) 		 " +  
							   "  			) 		 " +  
							   "  				group by NPP_FK  " +  */
						 
							   "  	) tonngay   " +  
							   "  	group by tonngay.NPP_FK "+
							") \n" +
							"tonthucte on tonquydinh.loaiTon = tonthucte.loaiTon and tonquydinh.nppId = tonthucte.NPP_FK \n" +
							" left join nhaphanphoi npp on tonquydinh.nppId = npp.pk_seq \n" +
							" left join khuvuc kv on npp.khuvuc_fk = kv.pk_seq \n" +
							" left join vung v on kv.vung_fk = v.pk_seq where 1=1  and npp.pk_seq in (select npp_fk from khoasongay where ngayks ='"+obj.getTungay().substring(0,8)+d+"')";
				    	
				    
			    
			     
			    if(obj.getVungId().length() > 0)
			    	query += " and v.pk_seq = '" + obj.getVungId() + "' ";
			    
			    if(obj.getKvId().length() > 0)
			    	query += " and kv.pk_seq = '" + obj.getKvId() + "' ";
			    
			    if(obj.getNppId().length()>0)
			    	query+=" and npp.pk_seq ="+obj.getNppId()+" ";
			    
			    System.out.println("1.Ton kho theo quy dinh: " + query);
			
				ResultSet rs = db.get(query);
			
			// vong lap o day 
				if(rs!=null)
				{
					try 
					{
						for(int j = 0; j < 15; j++)
							cells.setColumnWidth(i, 15.0f);
						
						Cell cell = null;
						while(rs.next())
						{
							String vung = rs.getString("vungTen");
							String khuvuc = rs.getString("kvTen");
							String nppTen = rs.getString("nppTen");
							int songaylv = rs.getInt("songaylamviec");	
							double chitieu = rs.getDouble("chitieu");
							double tonquydinh = rs.getDouble("tonquydinh");
							double tonkhoNpp = rs.getDouble("tonkhoNpp");
							String ngay = rs.getString("ngay");
		            		
							cell = cells.getCell("DA" + Integer.toString(i)); 	cell.setValue(vung);
							cell = cells.getCell("DB" + Integer.toString(i)); 	cell.setValue(khuvuc);
							cell = cells.getCell("DC" + Integer.toString(i)); 	cell.setValue(nppTen);
							cell = cells.getCell("DD" + Integer.toString(i)); 	cell.setValue(songaylv);
							cell = cells.getCell("DE" + Integer.toString(i)); 	cell.setValue(chitieu);
							cell = cells.getCell("DF" + Integer.toString(i)); 	cell.setValue(tonquydinh);				
							cell = cells.getCell("DG" + Integer.toString(i)); 	cell.setValue(tonkhoNpp);
							cell = cells.getCell("DH" + Integer.toString(i)); 	cell.setValue(ngay);
		
							i++;
						}
						if(rs!=null)
							rs.close();
					
					} 
					catch (Exception e) 
					{
						System.out.println("115.Error: " + e.getMessage());
						e.printStackTrace();
						return false;
					}
				} 
			}
	    
	    } //vong lap 
		else 
		{
			if(db != null) 
				db.shutDown();
			return false;
		}
		if(i==2){
			throw new Exception("Khong co bao cao trong thoi gian nay...");
		}
		if(db != null) 
			db.shutDown();
		return true;
	}
	
	/***************** End Ton Kho Theo Quy Dinh ***********************/
	
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
}
