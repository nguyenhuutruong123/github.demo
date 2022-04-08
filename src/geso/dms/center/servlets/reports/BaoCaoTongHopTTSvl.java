package geso.dms.center.servlets.reports;
import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.PivotFieldType;
import com.aspose.cells.PivotTable;
import com.aspose.cells.PivotTables;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class BaoCaoTongHopTTSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
       
    public BaoCaoTongHopTTSvl() {
        super();
    }
    
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		String querystring = request.getQueryString();
		Utility util = new Utility();
		
		obj.setuserId(util.getUserId(querystring));
		obj.setuserTen((String) session.getAttribute("userTen"));
		
		obj.setdiscount("1");
		obj.setvat("1");
		obj.init();
		
		session.setAttribute("obj", obj);
		String nextJSP = "/AHF/pages/Center/BaoCaoTongHopTT.jsp";
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
		
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		Utility util = new Utility();
		obj.setuserId(userId);
		
		obj.settungay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays"))));
		obj.setdenngay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays"))));
		
		obj.setYear(obj.gettungay().substring(0, 4));
		obj.setMonth(obj.gettungay().substring(5, 7));
		
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
		
	
			obj.setvat("1");
		String dsc = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("discount")));
		if (dsc.equals("1"))
			obj.setdiscount("1");
		else
			obj.setdiscount("0");	
		String promotion = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("promotion"));
		if(promotion == null)
			promotion = "0";
		obj.setpromotion(promotion);
				
		geso.dms.center.util.Utility utilcenter = new geso.dms.center.util.Utility();
		
		String level = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("level")));
		
		String sql = " where npp.pk_seq in " + utilcenter.quyen_npp(obj.getuserId()) + " and kbh.pk_seq in  " + utilcenter.quyen_kenh(obj.getuserId());
		if (obj.getkenhId().length() > 0)
			sql = sql + " and kbh.pk_seq ='" + obj.getkenhId() + "'";
		if (obj.getvungId().length() > 0)
			sql = sql + " and v.pk_seq ='" + obj.getvungId() + "'";
		if (obj.getkhuvucId().length() > 0)
			sql = sql + " and kv.pk_seq ='" + obj.getkhuvucId() + "'";
		if (obj.getdvkdId().length() > 0)
			sql = sql + " and dvkd.PK_SEQ ='" + obj.getdvkdId() + "'";
		if (obj.getnppId().length() > 0)
			sql = sql + " and npp.pk_seq ='" + obj.getnppId() + "'";
		if (obj.getnhanhangId().length() > 0)
			sql = sql + " and kbh.pk_seq ='" + obj.getnhanhangId() + "'";
		if (obj.getchungloaiId().length() > 0)
			sql = sql + " and cl.pk_seq ='" + obj.getchungloaiId()	+ "'";

		
		String action = (String) util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")));
		String nextJSP = "/AHF/pages/Center/BaoCaoTongHopTT.jsp";
		
		session.setAttribute("level", level);
		
		String gia ="nhsp.GIAGROSS";
		if(Integer.parseInt(dsc) >0) 
			gia ="nhsp.gianet";
		
		obj.init();
		
		if(action.equals("Taomoi"))
		{
			try
			{   
				response.setCharacterEncoding("utf-8");			
				response.setContentType("application/xlsm");
		        response.setHeader("Content-Disposition", "attachment; filename=BCTongHop.xlsm");
		        
		        
		        //String strfstream = getServletContext().getInitParameter("path") + "\\BaoCaoTongHop(TT).xlsm";
		        //FileInputStream fstream = new FileInputStream(strfstream);
		        File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\BaoCaoTongHop(TT).xlsm");
				FileInputStream fstream = new FileInputStream(f) ;
			Workbook workbook = new Workbook();
				workbook.open(fstream);
						        
		        workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		        Worksheets worksheets = workbook.getWorksheets();
		        
		        Worksheet worksheet = worksheets.getSheet("BanHangHangNgay");		
		        
		        CreatePivotBanHangHangNgay(worksheet, obj, level, sql);		        
		        
		        worksheet = worksheets.addSheet("DoanhSoMuaHang");

		        CreatePivotMuaHangHangNgay(worksheet, obj);
		        		        		        
		        worksheet = worksheets.addSheet("TonKhoNgay");
		        
		        CreatePivotDailyStock(worksheet, obj);
		        
		        worksheet = worksheets.addSheet("KhoaSoNgay");

		        CreatePivotKhoaSo(worksheet, obj);
		        
		        worksheet = worksheets.addSheet("XuatKhuyenMai");
		        CreatePivotTableKhuyenMai(worksheet,obj);
		        
		        worksheet = worksheets.addSheet("Summary");
		        CreatePivotTableSummary(worksheet,obj);
		        
		        workbook.save(out);
		        fstream.close();
			}
			catch(Exception ex)
			{
				System.out.println("Xay ra exception roi..." + ex.toString());
				session.setAttribute("obj", obj);
				obj.setMsg(ex.getMessage());
				response.sendRedirect(nextJSP);
			}
		}else{
			
			session.setAttribute("obj", obj);
			 nextJSP = "/AHF/pages/Center/BaoCaoTongHopTT.jsp";
			response.sendRedirect(nextJSP);
		}
	}
	//CreatePivotTableSummary
	private boolean CreatePivotTableSummary(Worksheet worksheet, IStockintransit obj)throws Exception 
	{ 																
	
		CreateStaticHeaderSummary(worksheet, obj);
		
	     CreateStaticDataSummary(worksheet, obj );

		return true;

	}
	//xuat khuyen mai
	
	private boolean CreatePivotTableKhuyenMai(Worksheet worksheet, IStockintransit obj)throws Exception 
	{ 																
	
		CreateStaticHeader(worksheet, obj);
		
	     CreateStaticData(worksheet, obj );
	
		
		
		return true;

	}

	private void CreateStaticDataSummary(Worksheet worksheet,
			IStockintransit obj) {
		// TODO Auto-generated method stub
		Utility util=new Utility();
		Cells cells = worksheet.getCells();
		dbutils db = new dbutils();

		String query ="   SELECT DVKD.DIENGIAI AS DVKDTEN ,KBH.TEN AS KenhBanHang,  "+
				" ( "+
				"  select top 1 asm.ten from ASM_KHUVUC ASMKV INNER JOIN ASM ON ASM.PK_SEQ=ASMKV.ASM_FK "+ 
				" where ASMKV.KHUVUC_FK=NPP.KHUVUC_FK  AND ASMKV.NGAYBATDAU <='"+obj.getYear()+"-"+obj.getMonth()+"-01' AND ASMKV.NGAYKETTHUC >='"+obj.getYear()+"-"+obj.getMonth()+"-31' and asm.trangthai = '1' "+ 
				" ) AS ASM, ( "+
				" select top 1 gsbh.ten from  NHAPP_GIAMSATBH NPP_GS "+ 
				" INNER JOIN  GIAMSATBANHANG GSBH ON GSBH.PK_SEQ=NPP_GS.GSBH_FK "+  
				" where  NPP_GS.NPP_FK=CTSEC.NPP_FK AND  "+
			   " NPP_GS.NGAYBATDAU <='"+obj.getYear()+"-"+obj.getMonth()+"-01' AND NPP_GS.NGAYKETTHUC >='"+obj.getYear()+"-"+obj.getMonth()+"-31' and gsbh.trangthai='1' "+   
			   " ) AS GSBHTEN  " +
					" ,V.TEN AS CHINHANH,KV.TEN AS KHUVUC,NPP.MA AS MaNPP , NPP.TEN AS TENNPP,CTSEC.NPP_FK, "+ 
			" CTSEC.KBH_FK,CTSEC.DVKD_FK,CTSEC.CTSEC ,DSSEC.DSSEC,CTPRI.CTPRI,DSPRI.DSPRI,TONKHO.SANLUONGTON as TONHIENTAI,TONCUOIKS.TONCUOI AS TONCUOI FROM  "+
			" (     "+
			" SELECT CTSECNPP.NHAPP_FK AS NPP_FK, KENH_FK AS KBH_FK , CTSEC.DVKD_FK, CTSECNPP.CHITIEU  AS CTSEC "+
			" FROM CHITIEU_SEC CTSEC  "+
			" INNER JOIN CHITIEU_NHAPP_SEC CTSECNPP     ON CTSEC.PK_SEQ = CTSECNPP.CHITIEU_SEC_FK  "+
			" WHERE CTSEC.TRANGTHAI <> 2  AND  CTSEC.THANG ='"+obj.getMonth()+"' AND CTSEC.NAM ='"+obj.getYear()+"' "+
			" )CTSEC "+
			" LEFT JOIN "+
			"  ( "+
			" SELECT NPP_FK ,  KBH_FK, DVKD_FK ,SUM(THANHTIEN)AS DSSEC  FROM ( "+
			" SELECT   DH.NPP_FK ,  DH.KBH_FK, SP.DVKD_FK, "+
			" SUM (SP.TRONGLUONG* "+
			" (-1)*ISNULL(DH_SP.SOLUONG, DH_SP1.SOLUONG)) AS THANHTIEN "+
			" FROM  DONHANGTRAVE DH  LEFT OUTER JOIN  DONHANGTRAVE_SANPHAM DH_SP ON DH_SP.DONHANGTRAVE_FK = DH.PK_SEQ "+  
			" LEFT OUTER JOIN  DONHANG_SANPHAM DH_SP1  ON DH.DONHANG_FK = DH_SP1.DONHANG_FK  "+
			" LEFT JOIN SANPHAM SP ON SP.PK_SEQ = ISNULL(DH_SP1.SANPHAM_FK,DH_SP.SANPHAM_FK) "+
			" WHERE DH.TRANGTHAI = 3 AND DH.NGAYNHAP >= '"+obj.gettungay()+"' AND DH.NGAYNHAP <= '"+obj.getdenngay()+"' AND SP.TRANGTHAI='1'  "+
			" GROUP BY DH.NPP_FK,  DH.KBH_FK, SP.DVKD_FK "+
			" UNION ALL   "+
			"  SELECT  DH.NPP_FK ,DH.KBH_FK,SP.DVKD_FK, "+
			" SUM(SP.TRONGLUONG* DH_SP.SOLUONG) AS THANHTIEN  "+
			"  FROM DONHANG DH   INNER JOIN DONHANG_SANPHAM  DH_SP ON DH.PK_SEQ = DH_SP.DONHANG_FK  "+
			" INNER JOIN SANPHAM SP ON SP.PK_SEQ=DH_SP.SANPHAM_FK "+
			" WHERE  DH.TRANGTHAI = 1 AND DH.NGAYNHAP >= '"+obj.gettungay()+"' AND DH.NGAYNHAP <= '"+obj.getdenngay()+"'  AND SP.TRANGTHAI='1'  "+
			" GROUP BY DH.NPP_FK ,SP.DVKD_FK,DH.KBH_FK "+
			" )  DH GROUP BY NPP_FK ,DVKD_FK,KBH_FK "+
			" ) DSSEC ON CTSEC.NPP_FK=DSSEC.NPP_FK AND CTSEC.DVKD_FK=DSSEC.DVKD_FK AND CTSEC.KBH_FK=DSSEC.KBH_FK   "+
			" LEFT JOIN "+ 
			" ( "+
			" SELECT CTSECNPP.NHAPP_FK AS NPP_FK, KENH_FK AS KBH_FK , CTSEC.DVKD_FK, CTSECNPP.CHITIEU  AS CTPRI "+
			" FROM CHITIEU CTSEC  "+
			" INNER JOIN CHITIEU_NHAPP CTSECNPP     ON CTSEC.PK_SEQ = CTSECNPP.CHITIEU_FK "+ 
			" WHERE CTSEC.TRANGTHAI <> 2  AND  CTSEC.THANG ='"+obj.getMonth()+"' AND CTSEC.NAM ='"+obj.getYear()+"' "+
			" )  CTPRI ON  CTSEC.NPP_FK=CTPRI.NPP_FK AND CTSEC.DVKD_FK=CTPRI.DVKD_FK AND CTSEC.KBH_FK=CTPRI.KBH_FK "+
			" LEFT JOIN "+ 
			" ( "+
			" SELECT SUM(SOLUONG* SP.TRONGLUONG) AS DSPRI,NPP_FK,SP.DVKD_FK,HD.KBH_FK "+
			" FROM HDNHAPHANG HD INNER JOIN HDNHAPHANG_SP HD_SP ON HD.PK_SEQ=HD_SP.NHAPHANG_FK "+
			" INNER JOIN SANPHAM SP ON SP.MA=HD_SP.SANPHAM_FK "+
			" WHERE HD.NGAYCHUNGTU LIKE '"+obj.getYear()+"-"+obj.getMonth()+"%' and HD.trangthai <>'2' and hd.loaidonhang='0' AND SP.TRANGTHAI='1' "+
			" GROUP BY  NPP_FK,SP.DVKD_FK,HD.KBH_FK "+
			" ) DSPRI ON  CTSEC.NPP_FK=DSPRI.NPP_FK AND CTSEC.DVKD_FK=DSPRI.DVKD_FK AND CTSEC.KBH_FK=DSPRI.KBH_FK "+
			" LEFT  JOIN ( "+
			" SELECT NPP_FK,SP.DVKD_FK,KBH_FK,SUM(AVAILABLE*SP.TRONGLUONG) AS SANLUONGTON "+
			" FROM NHAPP_KHO  "+
			" INNER JOIN SANPHAM SP ON SP.PK_SEQ=SANPHAM_FK "+
			" GROUP BY NPP_FK,SP.DVKD_FK,KBH_FK  "+
			" ) TONKHO  ON  CTSEC.NPP_FK=TONKHO.NPP_FK AND CTSEC.DVKD_FK=TONKHO.DVKD_FK AND CTSEC.KBH_FK=TONKHO.KBH_FK "+
			"  LEFT JOIN  " +
			" ( " +
			"SELECT NPP_FK,SP.DVKD_FK,KBH_FK,SUM( SOLUONG * SP.TRONGLUONG) AS TONCUOI "+
			"	FROM TONKHONGAY	 INNER JOIN SANPHAM SP ON SP.PK_SEQ=TONKHONGAY.SANPHAM_FK "+ 
			"WHERE NGAY =case when (select max(ngayks) from KHOASONGAY where NPP_FK=TONKHONGAY.NPP_FK )<'"+obj.getdenngay()+"' "+ 
			"	then ( select max(ngayks) from KHOASONGAY where NPP_FK=TONKHONGAY.NPP_FK) "+ 
			"	else '"+obj.getdenngay()+"' end "+
			"AND SP.TRANGTHAI='1'"+
			" GROUP BY  " +
			" NPP_FK,SP.DVKD_FK,KBH_FK   ) AS  TONCUOIKS ON   " +
			"   CTSEC.NPP_FK=TONCUOIKS.NPP_FK AND CTSEC.DVKD_FK=TONCUOIKS.DVKD_FK AND CTSEC.KBH_FK=TONCUOIKS.KBH_FK  " +			
			" INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=CTSEC.NPP_FK "+
			" INNER JOIN KHUVUC KV ON KV.PK_SEQ=NPP.KHUVUC_FK "+
			" INNER JOIN VUNG V ON V.PK_SEQ=KV.VUNG_FK " +
			" INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ=CTSEC.KBH_FK  " +
			" INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ=CTSEC.DVKD_FK " ;
			/*" INNER JOIN NHAPP_GIAMSATBH NPP_GS ON  NPP_GS.NPP_FK=CTSEC.NPP_FK AND  "+
			" NPP_GS.NGAYBATDAU <='"+obj.getYear()+"-"+obj.getMonth()+"-01' AND NPP_GS.NGAYKETTHUC >='"+obj.getYear()+"-"+obj.getMonth()+"-31' "+
			" INNER JOIN  GIAMSATBANHANG GSBH ON GSBH.PK_SEQ=NPP_GS.GSBH_FK "+
			" INNER JOIN ASM_KHUVUC ASMKV ON ASMKV.KHUVUC_FK=NPP.KHUVUC_FK "+
			" AND ASMKV.NGAYBATDAU <='"+obj.getYear()+"-"+obj.getMonth()+"-01' AND ASMKV.NGAYKETTHUC >='"+obj.getYear()+"-"+obj.getMonth()+"-31' "+
			" INNER JOIN ASM ON ASM.PK_SEQ=ASMKV.ASM_FK ";*/
		 query =query +  "  where npp.pk_seq in " + util.quyen_npp(obj.getuserId()) + " and kbh.pk_seq in  " + util.quyen_kenh(obj.getuserId());

		
		if (obj.getkenhId().length() > 0) {
			query += " and KBH.PK_SEQ='" + obj.getkenhId() +"' ";
		}
		if (obj.getvungId().length() > 0) {
			query += " and V.PK_SEQ='" + obj.getvungId() +"' ";
		}
		if (obj.getkhuvucId().length() > 0) {
			query += " and KV.PK_SEQ='" + obj.getkhuvucId() +"' ";
		}
		if (obj.getnppId().length() > 0) {
			query +=" and NPP.PK_SEQ='" + obj.getnppId()  +"' ";
		}
		
		
		System.out.println("1.BC SUMMARY: " + query);
		ResultSet rs = db.get(query);
		int i = 2;
		if (rs != null) 
		{
			try 
			{// se do rong cho cac cot se dung
				cells.setColumnWidth(0, 10.0f);
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
				cells.setColumnWidth(20, 15.0f);
				
				Cell cell = null;
				double PhanTramSec=0;
				double PhanTramPri=0;
				while (rs.next())
				{				
					cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(rs.getString("KenhBanHang"));
					cell = cells.getCell("AB" + Integer.toString(i)); cell.setValue(rs.getString("ChiNhanh"));
					cell = cells.getCell("AC" + Integer.toString(i)); cell.setValue(rs.getString("Khuvuc"));
					cell = cells.getCell("AD" + Integer.toString(i)); cell.setValue(rs.getString("MaNPP"));
					cell = cells.getCell("AE" + Integer.toString(i)); cell.setValue(rs.getString("TenNPP")); 
					cell = cells.getCell("AF" + Integer.toString(i)); cell.setValue(rs.getString("GSBHTEN"));
					cell = cells.getCell("AG" + Integer.toString(i)); cell.setValue(rs.getString("ASM"));
					cell = cells.getCell("AH" + Integer.toString(i)); cell.setValue(rs.getString("DVKDTEN"));
					cell = cells.getCell("AI" + Integer.toString(i)); cell.setValue(rs.getDouble("CTSEC"));
					cell = cells.getCell("AJ" + Integer.toString(i)); cell.setValue(rs.getDouble("DSSEC"));
					PhanTramSec=0;
					PhanTramPri=0;
					if(rs.getDouble("CTSEC") >0){
						PhanTramSec=rs.getDouble("DSSEC")*100/rs.getDouble("CTSEC");
					}
					
					
					cell = cells.getCell("AK" + Integer.toString(i)); cell.setValue(PhanTramSec);
					
					
					cell = cells.getCell("AL" + Integer.toString(i)); cell.setValue(rs.getDouble("CTPRI"));
					cell = cells.getCell("AM" + Integer.toString(i)); cell.setValue(rs.getDouble("DSPRI"));
					
					
					if(rs.getDouble("CTPRI") >0){
						PhanTramPri=rs.getDouble("DSPRI")*100/rs.getDouble("CTPRI");
					}
					
					
					
					cell = cells.getCell("AN" + Integer.toString(i)); cell.setValue(PhanTramPri);
					
					
					
					cell = cells.getCell("AO" + Integer.toString(i)); cell.setValue(rs.getDouble("TONHIENTAI"));
					cell = cells.getCell("AP" + Integer.toString(i)); cell.setValue(rs.getDouble("TONCUOI"));
			
					Style style;
					for(int j=1;j<=20;j++)
					{
						cell=cells.getCell(i, j);
						style=cell.getStyle();
						style.setNumber(3);
						cell.setStyle(style);
					}
					
					i++;

				}
				
				

				
				if(rs != null) rs.close();
				if(db != null)  db.shutDown();
				
				
				
				//setAn(workbook, 49);
		   

				
			} 
			catch (Exception e) 
			{	System.out.println(e.toString());
				if (db != null)
					db.shutDown();	
			
			}


		} 
		else 
		{
			if (db != null)
				db.shutDown();

		}
			
	}


	private void CreateStaticHeaderSummary(Worksheet worksheet,
			IStockintransit obj) {
		// TODO Auto-generated method stub
		try
		{
	 		
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
		    
		    cell.setValue("BÁO CÁO SUMMARY");  getCellStyle(worksheet,"A1",Color.RED,true,14);	  	
		    
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("A3");
		    
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Tháng : " + obj.getMonth() + "" );
		    
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("B3"); 
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Năm : " + obj.getYear() + "" );
		    
		
		    
		    cells.setRowHeight(5, 18.0f);
		    cell = cells.getCell("A5");getCellStyle(worksheet,"A5",Color.NAVY,true,9);
		    cell.setValue("Được tạo bởi:  " + obj.getuserTen());
		    
		    cell = cells.getCell("A6");getCellStyle(worksheet,"A6",Color.NAVY,true,9);
		    cell.setValue("Ghi chú : ");
		    cell = cells.getCell("B6");getCellStyle(worksheet,"B6",Color.NAVY,true,9);
		    cell.setValue("Tồn hiện tại là tồn kho hiện tại của nhà phân phối: ");
		    cell = cells.getCell("B7");getCellStyle(worksheet,"B7",Color.NAVY,true,9);
		    cell.setValue("Tồn cuối là tồn kho ngày khóa sổ cuối cùng của nhà phân phối : ");
		    
		    
			cell = cells.getCell("AA1");		cell.setValue("KenhBanHang");
			cell = cells.getCell("AB1");		cell.setValue("ChiNhanh");
			cell = cells.getCell("AC1");		cell.setValue("KhuVuc");
			cell = cells.getCell("AD1");		cell.setValue("MaNhaPhanPhoi");
			cell = cells.getCell("AE1");		cell.setValue("NhaPhanPhoi");
			cell = cells.getCell("AF1");		cell.setValue("GiamSatBanHang");
			cell = cells.getCell("AG1");		cell.setValue("ASM");
			cell = cells.getCell("AH1");		cell.setValue("DonViKinhDoanh");			
			cell = cells.getCell("AI1");		cell.setValue("ChiTieuXuat");
			cell = cells.getCell("AJ1");		cell.setValue("ThucXuat");
			cell = cells.getCell("AK1");		cell.setValue("PhanTramXuat/ChiTieu");
			cell = cells.getCell("AL1");		cell.setValue("ChiTieuNhap");
			cell = cells.getCell("AM1");		cell.setValue("ThucNhap");
			cell = cells.getCell("AN1");		cell.setValue("PhanTramNhap/ChiTieu");
			cell = cells.getCell("AO1");		cell.setValue("TonHienTai");
			cell = cells.getCell("AP1");		cell.setValue("TonCuoi");
			
			
		}
		catch(Exception e)
		{
			
		}
	}


	private void CreateStaticHeader(Worksheet worksheet, IStockintransit obj)throws Exception 
	{
		try
		{
	 		
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
		    
		    cell.setValue("BÁO CÁO XUẤT KHUYẾN MÃI");  getCellStyle(worksheet,"A1",Color.RED,true,14);	  	
		    
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("A3");
		    
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Tháng : " + obj.getMonth() + "" );
		    
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("B3"); 
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Năm : " + obj.getYear() + "" );
		    
		
		    
		    cells.setRowHeight(5, 18.0f);
		    cell = cells.getCell("A5");getCellStyle(worksheet,"A5",Color.NAVY,true,9);
		    cell.setValue("Được tạo bởi:  " + obj.getuserTen());

			cell = cells.getCell("AA1");		cell.setValue("KenhBanHang");
			cell = cells.getCell("AB1");		cell.setValue("ChiNhanh");
			cell = cells.getCell("AC1");		cell.setValue("KhuVuc");
			cell = cells.getCell("AD1");		cell.setValue("MaNhaPhanPhoi");
			cell = cells.getCell("AE1");		cell.setValue("NhaPhanPhoi");
			cell = cells.getCell("AF1");		cell.setValue("MaChuongTrinh");
			cell = cells.getCell("AG1");		cell.setValue("DienGiai");
			cell = cells.getCell("AH1");		cell.setValue("MaSanPham");			
			cell = cells.getCell("AI1");		cell.setValue("SanPham");
			cell = cells.getCell("AJ1");		cell.setValue("NhanHang");
			cell = cells.getCell("AK1");		cell.setValue("ChungLoai");
			cell = cells.getCell("AL1");		cell.setValue("Tinh/Thanh");
			cell = cells.getCell("AM1");		cell.setValue("Quan/Huyen");
			cell = cells.getCell("AN1");		cell.setValue("LoaiChuongTrinh");
			cell = cells.getCell("AO1");		cell.setValue("DaiDienKinhDoanh");
			cell = cells.getCell("AP1");		cell.setValue("MaKhachHang");
			cell = cells.getCell("AQ1");		cell.setValue("TenKhachHang");
			cell = cells.getCell("AR1");		cell.setValue("Ngay");
			cell = cells.getCell("AS1");		cell.setValue("SoLuong(Le)");
			cell = cells.getCell("AT1");		cell.setValue("SoTien");
			cell = cells.getCell("AU1");		cell.setValue("SoLuong(Thung)");
			cell = cells.getCell("AV1");		cell.setValue("DoanhSo");
			
		}
		catch(Exception e)
		{
			throw new Exception(e.getMessage());
		}
	}

	private boolean CreateStaticData(Worksheet worksheet,IStockintransit obj)throws Exception 
	{
		
		Utility util=new Utility();
		Cells cells = worksheet.getCells();
		dbutils db = new dbutils();

		String query = "SELECT KBH.PK_SEQ AS CHANNELID, KBH.TEN AS CHANNEL,VUNG.PK_SEQ AS REGIONID,VUNG.TEN AS REGION," +
							"KHUVUC.PK_SEQ AS AREAID,KHUVUC.TEN AS AREA,  " +
							"TINH.PK_SEQ AS PROVINCEID,TINH.TEN AS PROVINCE, QUAN.PK_SEQ AS TOWNID, " +
							"QUAN.TEN AS TOWN,ISNULL(GSBH.PK_SEQ,0) AS SALESSUPID,  " +
							"ISNULL(GSBH.TEN, 'Khong xac dinh') AS SALESSUP, NPP.PK_SEQ AS DISTRIBUTORID, " +
							"NPP.TEN AS DISTRIBUTOR, DDKD.PK_SEQ AS SALEREPID, isnull(DDKD.TEN, 'Chua khai bao') AS SALESREP,  " +
							"CAST(KH.PK_SEQ as nvarchar(8)) as khId, KH.ten AS CUSTOMER, DH.NGAYNHAP AS DATE,CTKM.SCHEME AS PROGRAM_CODE, " +
							"ISNULL(ctkm.diengiai + '__' + ctkm.tungay + '-' + ctkm.denngay, 'Khong xac dinh') AS PROGRAM,  " +
							"CASE WHEN CTKM.LOAICT = 1 THEN 'Binh Thuong' WHEN CTKM.LOAICT = 2 THEN 'On Top'  " +
							"WHEN CTKM.LOAICT = 3 THEN 'Tich Luy' else 'Chua Xac Dinh' END AS PROGRAM_TYPE,  " +
							"NH.PK_SEQ AS BRANDID, NH.TEN AS BRAND,CL.PK_SEQ AS CATEGORYID,CL.TEN AS CATEGORY, " +
							"SP.MA AS SKU_CODE,SP.TEN AS SKU,ISNULL(TRAKM.SOLUONG, 0) AS PIECE,   " +
							"ISNULL(TRAKM.TONGGIATRI, '0') AS AMOUNT, CASE WHEN (TRAKM.SOLUONG/QC.SOLUONG1) IS NULL " +
							"THEN 0 ELSE (TRAKM.SOLUONG/QC.SOLUONG1) END AS QUANTITY, isnull(DOANHSO.doanhso, '0') as DOANHSO  " +
						"FROM DONHANG_CTKM_TRAKM TRAKM " +
						"INNER JOIN CTKHUYENMAI CTKM ON TRAKM.CTKMID = CTKM.PK_SEQ " +
						"								AND SUBSTRING(CTKM.DENNGAY, 1 , 7)  = '" + obj.getYear() + "-" + obj.getMonth() + "' " +
						"INNER JOIN DONHANG DH ON TRAKM.DONHANGID = DH.PK_SEQ AND DH.TRANGTHAI='1' " +
						"INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = DH.NPP_FK " +
						"INNER JOIN TINHTHANH TINH ON TINH.PK_SEQ = NPP.TINHTHANH_FK " +
						"INNER JOIN QUANHUYEN QUAN ON QUAN.PK_SEQ = TINH.PK_SEQ  " +
						"LEFT JOIN SANPHAM SP ON TRAKM.SPMA = SP.MA " +
						"LEFT JOIN NHANHANG NH ON NH.PK_SEQ = SP.NHANHANG_FK " +
						"LEFT JOIN CHUNGLOAI CL ON CL.PK_SEQ = SP.CHUNGLOAI_FK " +
						"INNER JOIN KHACHHANG KH ON KH.PK_SEQ = DH.KHACHHANG_FK " +
						"LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK = SP.PK_SEQ  " +
						"INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ = KH.KBH_FK " +
						"INNER JOIN DAIDIENKINHDOANH DDKD ON DDKD.PK_SEQ = DH.DDKD_FK " +
						"LEFT JOIN GIAMSATBANHANG GSBH ON GSBH.PK_SEQ = DH.GSBH_FK " +
						"INNER JOIN KHUVUC KHUVUC ON KHUVUC.PK_SEQ = NPP.KHUVUC_FK " +
						"INNER JOIN VUNG VUNG ON VUNG.PK_SEQ = KHUVUC.VUNG_FK " +
						"LEFT JOIN " +
						"( " +
							"select a.pk_seq, c.donhang_fk, sum(c.soluong * c.giamua - isnull(c.chietkhau, '0') ) as doanhso " +
							"from ctkhuyenmai a inner join donhang_ctkm_trakm b on a.pk_seq = b.ctkmId  " +
								"inner join donhang_sanpham c on b.donhangId = c.donhang_fk " +
								"inner join ctkm_dkkm d on a.pk_seq = d.ctkhuyenmai_fk " +
								"inner join dieukienkm_sanpham e on d.dkkhuyenmai_fk = e.dieukienkhuyenmai_fk and c.sanpham_fk = e.sanpham_fk " +
							"where SUBSTRING(a.DENNGAY, 1 , 7)  = '" + obj.getYear() + "-" + obj.getMonth() + "' " +
										"AND b.donhangId NOT IN ( SELECT DONHANG_FK FROM DONHANGTRAVE WHERE DONHANG_FK IS NOT NULL AND TRANGTHAI = '3' ) " +
							"group by a.pk_seq, c.donhang_fk " +
						") " +
						"DOANHSO on  CTKM.pk_seq = DOANHSO.pk_seq and DH.pk_seq = DOANHSO.donhang_fk " +
						"WHERE ISNULL(TRAKM.SOLUONG, '0') >= 0 " +
						"AND DH.PK_SEQ NOT IN " +
						"(" +
						"	SELECT DONHANG_FK FROM DONHANGTRAVE " +
						"	WHERE DONHANG_FK IS NOT NULL AND TRANGTHAI = '3' " +
						") ";
		 query =query +  " and npp.pk_seq in " + util.quyen_npp(obj.getuserId()) + " and kbh.pk_seq in  " + util.quyen_kenh(obj.getuserId());

		
		if (obj.getkenhId().length() > 0) {
			query += " and KBH.PK_SEQ='" + obj.getkenhId() +"' ";
		}
		if (obj.getvungId().length() > 0) {
			query += " and VUNG.PK_SEQ='" + obj.getvungId() +"' ";
		}
		if (obj.getkhuvucId().length() > 0) {
			query += " and KHUVUC.PK_SEQ='" + obj.getkhuvucId() +"' ";
		}
		if (obj.getnppId().length() > 0) {
			query +=" and NPP.PK_SEQ='" + obj.getnppId()  +"' ";
		}
		if (obj.getPrograms().length() > 0) {
			query += " and CTKM.SCHEME='" + obj.getPrograms() +"' ";
		}
		
		if(obj.getUnghang().equals("0")){
			query = query + " AND CTKM.KHO_FK ='100000' ";
		}
		
		System.out.print("1.BC Xuat khuyen mai TT: " + query);
		ResultSet rs = db.get(query);
		int i = 2;
		if (rs != null) 
		{
			try 
			{// se do rong cho cac cot se dung
				cells.setColumnWidth(0, 10.0f);
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
				cells.setColumnWidth(20, 15.0f);
				
				Cell cell = null;
				while (rs.next())
				{
					String Chanel = rs.getString("Channel");
					String Region = rs.getString("Region");
					String area = rs.getString("Area");

					String Distributor = rs.getString("Distributor");
					String PromotionProgram = rs.getString("PROGRAM");

					String Brand = "" ;
					
					if(rs.getString("Brand") == null)
						Brand = "Khong xac dinh";
					else
						Brand = rs.getString("Brand");
					
					String Category = "";

					if(rs.getString("Category") == null)
						Category = "Khong xac dinh";
					else
						Category = rs.getString("Category");
					
					String Province = rs.getString("Province");
					String Town = rs.getString("Town");
					String DisCode = rs.getString("DISTRIBUTORID");
					String ProgramCode = rs.getString("PROGRAM_CODE");
					String ProgramType = rs.getString("PROGRAM_TYPE");
					
					String SKUCode = "";
					
					if(rs.getString("SKU_code") == null)
						SKUCode = "Khong xac dinh";
					else
						SKUCode = rs.getString("SKU_code");

					String SKU = "";
					if(rs.getString("SKU") == null)
						SKU = "Khong xac dinh";
					else
						SKU = rs.getString("SKU");
					
					String SaleRep = rs.getString("SALESREP");
					//String CustomerKey = rs.getString("khId");
					String Customer = rs.getString("Customer");
					int CustomerKey = Integer.parseInt(rs.getString("khId"));
					String Offdate = rs.getString("DATE");

					String Piece = "0";
					if (rs.getString("Piece") != null) {
						Piece = rs.getString("Piece");
					}

					String Amount = rs.getString("Amount");
					String Quanlity = "0";
					if (rs.getString("QUANTITY") != null) {
						Quanlity = rs.getString("QUANTITY");
					}
					
					long doanhso = Math.round(rs.getDouble("DOANHSO"));
										
					cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(Chanel);
					cell = cells.getCell("AB" + Integer.toString(i)); cell.setValue(Region);
					cell = cells.getCell("AC" + Integer.toString(i)); cell.setValue(area);
					cell = cells.getCell("AD" + Integer.toString(i)); cell.setValue(DisCode);
					cell = cells.getCell("AE" + Integer.toString(i)); cell.setValue(Distributor); 
					cell = cells.getCell("AF" + Integer.toString(i)); cell.setValue(ProgramCode);
					cell = cells.getCell("AG" + Integer.toString(i)); cell.setValue(PromotionProgram);
					cell = cells.getCell("AH" + Integer.toString(i)); cell.setValue(SKUCode);
					cell = cells.getCell("AI" + Integer.toString(i)); cell.setValue(SKU);
					cell = cells.getCell("AJ" + Integer.toString(i)); cell.setValue(Brand);
					cell = cells.getCell("AK" + Integer.toString(i)); cell.setValue(Category);
					cell = cells.getCell("AL" + Integer.toString(i)); cell.setValue(Province);
					cell = cells.getCell("AM" + Integer.toString(i)); cell.setValue(Town);					
					cell = cells.getCell("AN" + Integer.toString(i)); cell.setValue(ProgramType);
					cell = cells.getCell("AO" + Integer.toString(i)); cell.setValue(SaleRep);
					cell = cells.getCell("AP" + Integer.toString(i)); cell.setValue(CustomerKey);
					cell = cells.getCell("AQ" + Integer.toString(i)); cell.setValue(Customer);
					cell = cells.getCell("AR" + Integer.toString(i)); cell.setValue(Offdate);
					cell = cells.getCell("AS" + Integer.toString(i)); cell.setValue(Float.parseFloat(Piece));
					cell = cells.getCell("AT" + Integer.toString(i)); cell.setValue(Float.parseFloat(Amount));
					cell = cells.getCell("AU" + Integer.toString(i)); cell.setValue(Float.parseFloat(Quanlity));
					cell = cells.getCell("AV" + Integer.toString(i)); cell.setValue(doanhso);
					i++;

				}
				
				

				
				if(rs != null) rs.close();
				if(db != null)  db.shutDown();
				
				
				
				//setAn(workbook, 49);
		    	return true;

				
			} 
			catch (Exception e) 
			{	System.out.println(e.toString());
				if (db != null)
					db.shutDown();	
				throw new Exception(e.getMessage());
			}


		} 
		else 
		{
			if (db != null)
				db.shutDown();
			return false;
		}
			
	}

	
	//Khoa so ngay
	private boolean CreatePivotKhoaSo(Worksheet worksheet, IStockintransit obj)throws Exception
	{
		String sql = "";
		if(obj.getvungId().length()>0){
			sql += " and v.pk_seq = '" + obj.getvungId() + "'";
		}
		if(obj.getkhuvucId().length()>0){
			sql += " and kv.pk_seq='"+ obj.getkhuvucId() + "'";
		}
		if(obj.getnppId().length()>0){
			sql +=" and n.pk_seq='" + obj.getnppId() +"'";
		}
		
		String query = "SELECT  v.TEN AS Region,kv.TEN AS Area,n.PK_SEQ AS DistributorCode," +
				"		n.SITECODE AS SiteCode,	n.TEN AS Distributor,k.NGAYKS AS Date," +
				"		CASE WHEN n.PK_SEQ NOT IN (SELECT ksn.NPP_FK FROM KHOASONGAY ksn) THEN 0 " +
				"		ELSE 1" +
				"		END AS Data " +
				"    FROM KHOASONGAY k" +
				"		INNER JOIN NHAPHANPHOI n ON n.PK_SEQ = k.NPP_FK" +
				"		INNER JOIN KHUVUC kv ON kv.PK_SEQ = n.KHUVUC_FK" +
				"		INNER JOIN VUNG v ON v.PK_SEQ = kv.VUNG_FK" +
				"	WHERE k.NGAYKS >= '" + obj.gettungay() + "' AND k.NGAYKS <='" + obj.getdenngay() + "' " + sql + 
				" 	ORDER BY V.TEN, KV.TEN, N.TEN, K.NGAYKS" ;
		
		System.out.println(query);
		
		CreateHeaderKS(worksheet, obj);
		FillDataKSNgay(worksheet, obj, query);
		
		return true;	
	}

	private void CreateHeaderKS(Worksheet worksheet, IStockintransit obj)throws Exception 
	{
		try
		{
			Cells cells = worksheet.getCells();

			cells.setRowHeight(0, 20.0f);
			Cell cell = cells.getCell("A1");
			getCellStyle(worksheet,"A1",Color.RED,true,14);	    
			cell.setValue("THEO DÕI KHÓA SỔ NGÀY");   	
//			cells.merge(0, 0, 0, 2);
			
		    cells.setRowHeight(2, 18.0f);
		    cell = cells.getCell("A3"); 
		    getCellStyle(worksheet,"A3",Color.NAVY,true,10);	    
		    cell.setValue("Từ ngày: " + obj.gettungay());
		    
		    
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("B3"); getCellStyle(worksheet,"B3",Color.NAVY,true,9);	        
		    cell.setValue("Đến ngày: " + obj.getdenngay());    
		    
		    cells.setRowHeight(4, 18.0f);
		    cell = cells.getCell("A4");getCellStyle(worksheet,"A4",Color.NAVY,true,9);
		    cell.setValue("Ngày báo cáo: " + obj.getDateTime());
		    
		    cells.setRowHeight(5, 18.0f);
		    cell = cells.getCell("A5");getCellStyle(worksheet,"A5",Color.NAVY,true,9);
		    cell.setValue("Được tạo bởi:  " + obj.getuserTen());

			
			cell = cells.getCell("EA1");		cell.setValue("ChiNhanh");
			cell = cells.getCell("EB1");		cell.setValue("KhuVuc");			
			cell = cells.getCell("EC1");		cell.setValue("MaNhaPhanPhoi");
			cell = cells.getCell("ED1");		cell.setValue("SiteCode");	
			cell = cells.getCell("EE1");		cell.setValue("NhaPhanPhoi");
			cell = cells.getCell("EF1");		cell.setValue("Ngay");		
			cell = cells.getCell("EG1");		cell.setValue("KhoaSo");			
		}
		catch(Exception ex)
		{
			System.out.println(ex.toString());
			throw new Exception("Khong tao duoc Header cho bao cao khoa so ngay");
		}

	}
	
	
	
	
	private boolean FillDataKSNgay(Worksheet worksheet, IStockintransit obj, String query) throws Exception 
	{
		dbutils db = new dbutils();
		Cells cells = worksheet.getCells();		
		
		ResultSet rs = db.get(query);
		int i = 2;
		if(rs!=null){
			Cell cell = null;
	    	cells.setColumnWidth(0, 15.0f);
	    	cells.setColumnWidth(1, 15.0f);
	    	cells.setColumnWidth(2, 15.0f);
	    	cells.setColumnWidth(3, 15.0f);
	    	cells.setColumnWidth(4, 25.0f);

			try{
				while(rs.next())
				{					
					String region = rs.getString("Region");
					String area = rs.getString("Area");
					String distributorCode = rs.getString("DistributorCode");
					String siteCode = rs.getString("SiteCode");
					String distributor = rs.getString("Distributor");
					String date = rs.getString("Date");
					String data = rs.getString("Data");
					
					
					cell = cells.getCell("EA" + Integer.toString(i));	cell.setValue(region);
					cell = cells.getCell("EB" + Integer.toString(i));	cell.setValue(area);
					cell = cells.getCell("EC" + Integer.toString(i));	cell.setValue(distributorCode);
					cell = cells.getCell("ED" + Integer.toString(i));	cell.setValue(siteCode);
					cell = cells.getCell("EE" + Integer.toString(i));	cell.setValue(distributor);
					cell = cells.getCell("EF" + Integer.toString(i));	cell.setValue(date);										
					cell = cells.getCell("EG" + Integer.toString(i));	cell.setValue(data);
					++i;					

				}
				if (rs != null)
					rs.close();
				
				if(db != null) db.shutDown();
				
				if(i == 2){					
					throw new Exception("Xin loi,khong co bao cao voi dieu kien da chon....!!!");
				}
				
//				setHidden(workbook, 170);
				PivotTables pivotTables = worksheet.getPivotTables();
				String pos = Integer.toString(i - 1);
				int index = pivotTables.add("=KhoaSoNgay!EA1:EG" + pos, "A10","PivotTable1");
				PivotTable pivotTable = pivotTables.get(index);
				
				
				pivotTable.addFieldToArea(PivotFieldType.ROW, 0);	
				pivotTable.getRowFields().get(0).setAutoSubtotals(false);
				
				pivotTable.addFieldToArea(PivotFieldType.ROW, 1);
				pivotTable.getRowFields().get(1).setAutoSubtotals(false);
				
				pivotTable.addFieldToArea(PivotFieldType.ROW, 2);	
				pivotTable.getRowFields().get(2).setAutoSubtotals(false);
				
				pivotTable.addFieldToArea(PivotFieldType.ROW, 3);
				pivotTable.getRowFields().get(3).setAutoSubtotals(false);
				
				pivotTable.addFieldToArea(PivotFieldType.ROW, 4);
				pivotTable.getRowFields().get(4).setAutoSubtotals(false);
								
				pivotTable.addFieldToArea(PivotFieldType.COLUMN, 5);
				pivotTable.getColumnFields().get(0).setDisplayName("Ngày");
				
				pivotTable.addFieldToArea(PivotFieldType.DATA, 6);
				pivotTable.getDataFields().get(0).setDisplayName("KhoaSoNgay");
				
//				worksheet.autoFitColumns();
			}catch(Exception ex){
				throw new Exception(ex.getMessage());
			}
		}else{
			return false;
		}
		return true;
	}

	//Khoa so ngay
	private boolean CreatePivotMuaHangHangNgay(Worksheet worksheet, IStockintransit obj)throws Exception
	{
		 String sql ="";
		 if(obj.gettungay().length() > 0) 
			 sql = sql +" and nh.ngaychungtu >='"+ obj.gettungay() + "'";
		 if(obj.getdenngay().length() > 0) 
			 sql = sql +" and  nh.ngaychungtu <='"+ obj.getdenngay() + "'";
		 if(obj.getkenhId().length() > 0) 
			 sql = sql +" and kbh.pk_seq ='"+ obj.getkenhId() + "'";
		 if(obj.getvungId().length() > 0) 
			 sql = sql +" and v.pk_seq ='" + obj.getvungId() + "'";
		 if(obj.getkhuvucId().length() > 0)
			 sql = sql + " and kv.pk_seq ='"+ obj.getkhuvucId() + "'";
		 if(obj.getdvdlId().length() > 0) 
			 sql = sql + " and sp.dvkd_fk ='"+ obj.getdvdlId() + "'";
		 if(obj.getnppId().length() > 0)
			 sql =sql +" and npp.pk_seq ='"+ obj.getnppId() + "'";
		 if(obj.getnhanhangId().length() > 0) 
			 sql = sql +" and nhan.pk_seq ='"+ obj.getnhanhangId() + "'";
		 if(obj.getchungloaiId().length() > 0)
			 sql = sql +" and cl.pk_seq ='"+ obj.getchungloaiId() + "'";
		 if(obj.getchungloaiId().length() > 0) 
			 sql = sql + " and sp.dvdl_fk ='"+ obj.getchungloaiId() +"'";
		 
		 if(Integer.parseInt(obj.getpromotion()) == 0) // khng lay khuyen mai + trung bay
		 {
			 sql = sql + " and nhsp.gianet >0  ";
		 }
		 

		 String gia ="nhsp.gianet";
		 if(Integer.parseInt(obj.getdiscount()) > 0)
			 gia ="nhsp.GIAGROSS";
		String userId = obj.getuserId();
		
		String  query = "select " +
  		"kbh.ten as Channel,v.ten as Region, kv.ten as Area,nhan.ten as Brands,cl.ten as Category,nh.ngaychungtu as Shipdate," +
  		"npp.sitecode as Distributor_code, npp.ten as Distributor,tt.ten as Province,nh.chungtu as Docno,nh.pk_seq as Distcode," +
  		"nhsp.sanpham_fk as SKU_code,sp.ten as SKU, DVKD.DONVIKINHDOANH, nhsp.soluong as Piece, nhsp.soluong * "+ gia +"*"+ obj.getvat() +" as Amount " +
		" , nhsp.soluong* sp.trongluong as trongluong  from hdnhaphang nh " +
			"inner join hdnhaphang_sp nhsp on nh.pk_seq = nhsp.nhaphang_fk " +
			"inner join sanpham sp on sp.ma = nhsp.sanpham_fk " +
			"INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK " +
			"inner join nhaphanphoi npp on npp.pk_seq = nh.npp_fk " +
			"left join khuvuc kv on kv.pk_seq = npp.khuvuc_fk " +
			"left join vung v on v.pk_seq = kv.vung_fk " +
			"left join nhanhang nhan on nhan.pk_seq = sp.nhanhang_fk " +
			"left join chungloai cl on cl.pk_seq = sp.chungloai_fk " +
			"left join tinhthanh tt on tt.pk_seq = npp.tinhthanh_fk " +
			"left join kenhbanhang kbh on kbh.pk_seq = nh.kbh_fk where nh.trangthai <> '2' and nh.loaidonhang='0' " ;
	//	"where scheme is not null ";// --de dam bao la thuoc dat hang";
  	//phanquyen
		geso.dms.center.util.Utility ut = new geso.dms.center.util.Utility();
		query += " and npp.pk_seq in "+ ut.quyen_npp(userId) + " and kbh.pk_seq in " + ut.quyen_kenh(userId)
							+" and sp.pk_seq in "+ ut.quyen_sanpham(userId);

  if(query.length()>0)
  	query = query + sql;
  System.out.print("Doanh So Mua Hang: " + query);

			if(query.length() > 0)
				query = query + sql + " order by kbh.ten, v.ten, kv.ten, npp.ten, nh.ngaychungtu, nhsp.sanpham_fk, sp.ten ";
				
		System.out.println(query);
		
		CreateHeaderMuaHangHangNgay(worksheet, obj);
		FillDataMuahanghangngay(worksheet, obj, query);
		
		return true;	
	}
	
	//Bao cao mua hang hang ngay
	private void CreateHeaderMuaHangHangNgay(Worksheet worksheet, IStockintransit obj) 
	{    
	    Cells cells = worksheet.getCells();	    
	    cells.setRowHeight(0, 20.0f);	  
	    
	    Cell cell = cells.getCell("A1");	
	    getCellStyle(worksheet, "A1" , Color.RED, true, 14 );
	    cell.setValue("BÁO CÁO MUA HÀNG");
//	    cells.merge(0, 0, 0, 2);
	    
	    cells.setRowHeight(2, 18.0f);
	    cell = cells.getCell("A3");
	    getCellStyle(worksheet, "A3", Color.NAVY,true,9);
	    cell.setValue("Từ ngày: " + obj.gettungay());
	    
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("B3");
	    getCellStyle(worksheet, "B3", Color.NAVY,true,9);
	    cell.setValue("Đến ngày: " + obj.getdenngay());
	    
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A4");
	    getCellStyle(worksheet, "A4" , Color.NAVY,true, 9);	    
	    cell.setValue("Ngày báo cáo: " +obj.getDateTime());
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A5");
	    getCellStyle(worksheet, "A5" , Color.NAVY,true, 9);
	    cell.setValue("Được tạo bởi: " + obj.getuserTen());
	    
	    cell = cells.getCell("AA1");		cell.setValue("KenhBanHang");		ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AB1");		cell.setValue("DonViKinhDoanh");	ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AC1");		cell.setValue("ChiNhanh");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AD1");		cell.setValue("KhuVuc");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AE1");		cell.setValue("Tinh/Thanh");		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AF1");		cell.setValue("MaNhaPhanPhoi");		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AG1");		cell.setValue("NhaPhanPhoi");		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AH1");		cell.setValue("NhanHang");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AI1");		cell.setValue("ChungLoai");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AJ1");		cell.setValue("SoChungTu");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AK1");		cell.setValue("Ngay");				ReportAPI.setCellHeader(cell);		
		cell = cells.getCell("AL1");		cell.setValue("MaSanPham");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AM1");		cell.setValue("SanPham");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AN1");		cell.setValue("SoLuong");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AO1");		cell.setValue("SoTien");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AP1");		cell.setValue("TrongLuong");			ReportAPI.setCellHeader(cell);
	}
	
	private void FillDataMuahanghangngay(Worksheet worksheet, IStockintransit obj, String query) throws Exception 
	{
		Cells cells = worksheet.getCells();
		
    	cells.setColumnWidth(0, 15.0f);
    	cells.setColumnWidth(1, 15.0f);
    	cells.setColumnWidth(2, 15.0f);
    	cells.setColumnWidth(3, 25.0f);
    	cells.setColumnWidth(4, 15.0f);
    	cells.setColumnWidth(5, 25.0f);
    	cells.setColumnWidth(6, 15.0f);
    	cells.setColumnWidth(7, 15.0f);
    	cells.setColumnWidth(5, 25.0f);
    	cells.setColumnWidth(6, 15.0f);
    	cells.setColumnWidth(7, 15.0f);
    	
		dbutils db = new dbutils();

		ResultSet rs = db.get(query);
		int index = 2;
	    Cell cell = null;	    
	    try{
	    	while (rs.next()) {
	    		cell = cells.getCell("AA" + String.valueOf(index));		cell.setValue(rs.getString("Channel"));	//Kenh ban hang  0	
	    		cell = cells.getCell("AB" + String.valueOf(index));		cell.setValue(rs.getString("DONVIKINHDOANH"));	// don vi kinh doanh 0
	    		cell = cells.getCell("AC" + String.valueOf(index));		cell.setValue(rs.getString("Region"));	//Vung/Mien  	1
	    		cell = cells.getCell("AD" + String.valueOf(index));		cell.setValue(rs.getString("Area"));		//khu vuc    2
	    		cell = cells.getCell("AE" + String.valueOf(index));		cell.setValue(rs.getString("Province"));	//Tinh thanh 3
	    		cell = cells.getCell("AF" + String.valueOf(index));		cell.setValue(rs.getString("Distributor_code"));	//Ma NPP 4
	    		cell = cells.getCell("AG" + String.valueOf(index));		cell.setValue(rs.getString("Distributor"));	//Ten NPP   5
	    		cell = cells.getCell("AH" + String.valueOf(index));		cell.setValue(rs.getString("Brands"));	//Nhan hang   	6
	    		cell = cells.getCell("AI" + String.valueOf(index));		cell.setValue(rs.getString("Category"));	//Chung loai 7
	    		cell = cells.getCell("AJ" + String.valueOf(index));		cell.setValue(rs.getString("Docno"));	//So luong quy le san pham 8
	    		cell = cells.getCell("AK" + String.valueOf(index));		cell.setValue(rs.getString("Shipdate"));	//So Tien    9
	    		cell = cells.getCell("AL" + String.valueOf(index));		cell.setValue(rs.getString("SKU_code"));	//Ma san pham   10
	    		cell = cells.getCell("AM" + String.valueOf(index));		cell.setValue(rs.getString("SKU"));				//Ten san pham 11
	    		cell = cells.getCell("AN" + String.valueOf(index));		cell.setValue(rs.getDouble("Piece"));	//Ngay xuat hoa don tu cong ty 12
	    		cell = cells.getCell("AO" + String.valueOf(index));		cell.setValue(rs.getDouble("Amount")); //13
	    		cell = cells.getCell("AP" + String.valueOf(index));		cell.setValue(rs.getDouble("TrongLuong")); //13
	    		index++;
	    	}
	    }catch(Exception err){
	    	System.out.println(err.toString());	  
	    }
	    
	    if(rs!=null) rs.close();
	    if(db != null) db.shutDown();
//	    ReportAPI.setHidden(workbook,13);
		
	  /*  PivotTables pivotTables = worksheet.getPivotTables();
		String pos = Integer.toString(index-1);	
	    int j = pivotTables.add("=DoanhSoMuaHang!AA1:AO" + pos,"A10","PivotTable2");	   
	    
	    PivotTable pivotTable = pivotTables.get(j);	    
	    pivotTable.setRowGrand(false);
	    pivotTable.setColumnGrand(false);
	    pivotTable.setAutoFormat(false);
	    
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 0);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 1);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 2);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 3);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 5);
	    
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 10);
	    pivotTable.getRowFields().get(4).setAutoSubtotals(false);
	    
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 11);	    
	    pivotTable.getRowFields().get(5).setAutoSubtotals(false);
	    
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 13);
	    pivotTable.getFields(PivotFieldType.DATA).get(0).setDisplayName("SoLuong");
	    pivotTable.getDataFields().get(0).setNumber(3);
	    
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 15);
	    
	    pivotTable.getFields(PivotFieldType.DATA).get(1).setDisplayName("TrongLuong");
	    pivotTable.getDataFields().get(1).setNumber(3);
	    
	    pivotTable.addFieldToArea(PivotFieldType.COLUMN, pivotTable.getDataField());
	    pivotTable.getColumnFields().get(0).setDisplayName("Data");*/
   	
//	    worksheet.autoFitColumns();
	    
	}

	private boolean CreatePivotBanHangHangNgay(Worksheet worksheet, IStockintransit obj, String level, String sql)throws Exception 
	{
		String query = this.createQueryBCDoanhSo(sql, obj, level);
		boolean isFillData = false;
		
		switch (Integer.parseInt(level)) {
		case 0:			
			CreateHeaderLevelOne(worksheet , obj);
			isFillData = FillDataLevelOne(worksheet, obj, query);
			break;
		case 1:			
			CreateHeaderLevelTwo(worksheet, obj);
			isFillData = FillDataLevelTwo(worksheet, obj, query);
			break;
		case 2:
			CreateHeaderLevelThree(worksheet,obj);
			isFillData = FillDataLevelThree(worksheet, obj, query);			
			break;
		}	   
		if (!isFillData){
			return false;
		}
		
		return true;	
	}
	
	private void CreateHeaderLevelOne(Worksheet worksheet, IStockintransit obj)throws Exception
	{	
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
	    
	    String tieude = "BÁO CÁO DOANH SỐ BÁN HÀNG THEO NGÀY";
	    if(obj.getFromMonth().length() > 0)
	    	tieude = "BÁO CÁO DOANH SỐ BÁN HÀNG THEO THÁNG";
	    ReportAPI.getCellStyle(cell,Color.RED, true, 14, tieude);
	            
	    String message = "";
		if(obj.getdiscount().equals("0")){
			message += "Không chiết khấu";

			if(obj.getvat().equals("1")){
				message += ", không VAT";
			}else{
				message += ", có VAT";
			}			
		}else{
			message += "Có chiết khấu";
			if(obj.getvat().equals("1")){
				message += ", không VAT";
			}else{
				message +=", có VAT";
			}
		}
		
		cells.setRowHeight(2, 18.0f);
		cell = cells.getCell("A2");
		ReportAPI.getCellStyle(cell, Color.RED, true, 9, message);   

	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("A4");
	    
	    if(obj.getFromMonth() == "")
	    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ ngày : " + obj.gettungay() + "" );
	    else
	    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ tháng : " + obj.getFromMonth() + "" );
	    
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("B4"); 
	    if(obj.getFromMonth() == "")
	    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Đến ngày : " + obj.getdenngay() + "" );
	    else
	    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Đến tháng : " + obj.getToMonth() + "" );
		
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A5");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A6");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());
	    		
		
		cell = cells.getCell("DA1");		cell.setValue("KenhBanHang");
		cell = cells.getCell("DB1");		cell.setValue("DonViKinhDoanh");
		cell = cells.getCell("DC1");		cell.setValue("ChiNhanh");
		cell = cells.getCell("DD1");		cell.setValue("KhuVuc");
		cell = cells.getCell("DE1");		cell.setValue("NhaPhanPhoi");
		cell = cells.getCell("DF1");		cell.setValue("NhanHang");
		cell = cells.getCell("DG1");		cell.setValue("ChungLoai");
		cell = cells.getCell("DH1");		cell.setValue("MaNhaPhanPhoi");
		cell = cells.getCell("DI1");		cell.setValue("TenSanPham");
		cell = cells.getCell("DJ1");		cell.setValue("MaSanPham");
		cell = cells.getCell("DK1");
		if(obj.getFromMonth() != "")
			cell.setValue("Thang");
		else	
			cell.setValue("Ngay");
		cell = cells.getCell("DL1");		cell.setValue("SoTien");
		cell = cells.getCell("DM1");		cell.setValue("SoLuong(Le)");
		cell = cells.getCell("DN1");		cell.setValue("SoLuong(Thung)");
		cell = cells.getCell("DO1");		cell.setValue("TrongLuong");
	}
	
	private boolean FillDataLevelOne(Worksheet worksheet, IStockintransit obj, String query) throws Exception{
		dbutils db = new dbutils();

		Cells cells = worksheet.getCells();		
		ResultSet rs = db.get(query);
		int i = 2;
		
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
				
				cell = cells.getCell("Z1"); cell.setValue("1");
				while (rs.next()) 
				{					
					String chanel = rs.getString("chanel");
					String bu = rs.getString("bu");
					String region = rs.getString("region");
					String area = rs.getString("area");
					String distributor = rs.getString("distributor");
					String brand = rs.getString("brand");
					String category = rs.getString("category");
					String discode = rs.getString("distcode");
					String sku = rs.getString("SKU");
					String skuCode = rs.getString("SKUcode");
					String ngay = rs.getString("ngay");
					double amount = rs.getDouble("amount");
					float piece = rs.getFloat("piece");
					//float q = rs.getFloat("soluong2")*piece/rs.getFloat("soluong1");
					float q = rs.getFloat("quantity");
					
					
					cell = cells.getCell("DA" + Integer.toString(i));	cell.setValue(chanel);	//0
					cell = cells.getCell("DB" + Integer.toString(i));	cell.setValue(bu); 		//1
					cell = cells.getCell("DC" + Integer.toString(i));	cell.setValue(region); 	//2
					cell = cells.getCell("DD" + Integer.toString(i));	cell.setValue(area);	//3
					cell = cells.getCell("DE" + Integer.toString(i));	cell.setValue(distributor); //4					
					cell = cells.getCell("DF" + Integer.toString(i));	cell.setValue(brand);	//5
					cell = cells.getCell("DG" + Integer.toString(i));	cell.setValue(category);//6
					cell = cells.getCell("DH" + Integer.toString(i));	cell.setValue(discode);	//7
					cell = cells.getCell("DI" + Integer.toString(i));	cell.setValue(sku);		//8
					cell = cells.getCell("DJ" + Integer.toString(i));	cell.setValue(skuCode);	//9
					cell = cells.getCell("DK" + Integer.toString(i));	cell.setValue(ngay);	//10
					cell = cells.getCell("DL" + Integer.toString(i));	cell.setValue(amount);	//11
					cell = cells.getCell("DM" + Integer.toString(i));	cell.setValue(piece);	//12
					cell = cells.getCell("DN" + Integer.toString(i));	cell.setValue(q);		//13
					cell = cells.getCell("DO" + Integer.toString(i));	cell.setValue( rs.getDouble("trongluong"));	
					++i;					
				}

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
	
	//Level Dai dien kinh doanh
	private void CreateHeaderLevelTwo(Worksheet worksheet, IStockintransit obj)throws Exception{	
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
	    
	    ReportAPI.getCellStyle(cell,Color.RED, true, 14, "BÁO CÁO DOANH SỐ BÁN HÀNG THEO NGÀY");
	            
	    String message = "";
		if(obj.getdiscount().equals("0")){
			message += "Không chiết khấu";

			if(obj.getvat().equals("1")){
				message += ", không VAT";
			}else{
				message += ", có VAT";
			}			
		}else{
			message += "Có chiết khấu";
			if(obj.getvat().equals("1")){
				message += ", không VAT";
			}else{
				message +=", có VAT";
			}
		}
		
		cells.setRowHeight(2, 18.0f);
		cell = cells.getCell("A2");
		ReportAPI.getCellStyle(cell, Color.RED, true, 9, message);   

	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("A4");
	    
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ ngày : " + obj.gettungay() + "" );
	    
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("B4"); 
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Đến ngày : " + obj.getdenngay() + "" );
		
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A5");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A6");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());

		
		cell = cells.getCell("DA1");		cell.setValue("KenhBanHang");
		cell = cells.getCell("DB1");		cell.setValue("DonViKinhDoanh");
		cell = cells.getCell("DC1");		cell.setValue("ChiNhanh");
		cell = cells.getCell("DD1");		cell.setValue("KhuVuc");
		cell = cells.getCell("DE1");		cell.setValue("GiamSat");			
		cell = cells.getCell("DF1");		cell.setValue("NhaPhanPhoi");
		cell = cells.getCell("DG1");		cell.setValue("DaiDienKinhDoanh");	
		cell = cells.getCell("DH1");		cell.setValue("NhanHang");
		cell = cells.getCell("DI1");		cell.setValue("ChungLoai");
		cell = cells.getCell("DJ1");		cell.setValue("MaNhaPhanPhoi");
		cell = cells.getCell("DK1");		cell.setValue("MaDaiDienKinhDoanh");
		cell = cells.getCell("DL1");		cell.setValue("TenSanPham");
		cell = cells.getCell("DM1");		cell.setValue("MaSanPham");		
		cell = cells.getCell("DN1");		
		if(obj.getFromMonth() != "")
			cell.setValue("Thang");
		else	
			cell.setValue("Ngay");
		cell = cells.getCell("DO1");		cell.setValue("SoTien");
		cell = cells.getCell("DP1");		cell.setValue("SoLuong(Le)");
		cell = cells.getCell("DQ1");		cell.setValue("SoLuong(Thung)");
		cell = cells.getCell("DR1");		cell.setValue("TrongLuong");
	}
	
	private boolean FillDataLevelTwo(Worksheet worksheet, IStockintransit obj, String query) throws Exception{
		dbutils db = new dbutils();
		Cells cells = worksheet.getCells();		
		ResultSet rs = db.get(query);
		int i = 2;
		
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
				cells.setColumnWidth(7, 20.0f);
				cells.setColumnWidth(8, 20.0f);
				cells.setColumnWidth(9, 20.0f);
				cells.setColumnWidth(10, 20.0f);
				cells.setColumnWidth(11, 20.0f);
				cells.setColumnWidth(12, 20.0f);
				cells.setColumnWidth(13, 20.0f);
				cells.setColumnWidth(14, 20.0f);
				cells.setColumnWidth(15, 20.0f);
				
				cell = cells.getCell("Z1"); cell.setValue(2);
				
				while (rs.next()) {					
					String chanel = rs.getString("chanel");
					String bu = rs.getString("bu");
					String region = rs.getString("region");
					String area = rs.getString("area");
					String salesuper = rs.getString("salesuper");
					String distributor = rs.getString("distributor");					
					String salesRep = rs.getString("SalesRep");
					String salesRepId = rs.getString("SalesRepId");
					String brand = rs.getString("brand");
					String category = rs.getString("category");
					String discode = rs.getString("distcode");
					String sku = rs.getString("SKU");
					String skuCode = rs.getString("SKUcode");
					String ngay = rs.getString("ngay");
					double amount = rs.getDouble("amount");
					float piece = rs.getFloat("piece");
					
					//float q = rs.getFloat("soluong2")*piece/rs.getFloat("soluong1");
					float q = rs.getFloat("quantity");
										
					cell = cells.getCell("DA" + Integer.toString(i));	cell.setValue(chanel); //0
					cell = cells.getCell("DB" + Integer.toString(i));	cell.setValue(bu);	   //1
					cell = cells.getCell("DC" + Integer.toString(i));	cell.setValue(region); //2
					cell = cells.getCell("DD" + Integer.toString(i));	cell.setValue(area);   //3
					cell = cells.getCell("DE" + Integer.toString(i));	cell.setValue(salesuper); //4					
					cell = cells.getCell("DF" + Integer.toString(i));	cell.setValue(distributor);	//5				
					cell = cells.getCell("DG" + Integer.toString(i));	cell.setValue(salesRep); //6
					cell = cells.getCell("DH" + Integer.toString(i));	cell.setValue(brand); //7
					cell = cells.getCell("DI" + Integer.toString(i));	cell.setValue(category); //8
					cell = cells.getCell("DJ"+ Integer.toString(i));	cell.setValue(discode);	//9			
					cell = cells.getCell("DK" + Integer.toString(i));	cell.setValue(salesRepId);//10
					cell = cells.getCell("DL" + Integer.toString(i));	cell.setValue(sku);//11
					cell = cells.getCell("DM" + Integer.toString(i));	cell.setValue(skuCode);//12
					cell = cells.getCell("DN" + Integer.toString(i));	cell.setValue(ngay);//13
					cell = cells.getCell("DO" + Integer.toString(i));	cell.setValue(amount);//14
					cell = cells.getCell("DP" + Integer.toString(i));	cell.setValue(piece);//15
					cell = cells.getCell("DQ" + Integer.toString(i));	cell.setValue(q);	//16	
					cell = cells.getCell("DR" + Integer.toString(i));	cell.setValue(rs.getDouble("trongluong"));	//16		
					++i;
					
				}

				if (rs != null)
					rs.close();
				
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
	
	private void CreateHeaderLevelThree(Worksheet worksheet, IStockintransit obj)throws Exception{
		try{
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
		    
		    ReportAPI.getCellStyle(cell,Color.RED, true, 14, "BÁO CÁO DOANH SỐ BÁN HÀNG THEO NGÀY");
		            
		    String message = "";
			if(obj.getdiscount().equals("0")){
				message += "Không chiết khấu";

				if(obj.getvat().equals("1")){
					message += ", không VAT";
				}else{
					message += ", có VAT";
				}			
			}else{
				message += "Có chiết khấu";
				if(obj.getvat().equals("1")){
					message += ", không VAT";
				}else{
					message +=", có VAT";
				}
			}
			
			cells.setRowHeight(2, 18.0f);
			cell = cells.getCell("A2");
			ReportAPI.getCellStyle(cell, Color.RED, true, 9, message);   

		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("A4");
		    
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ ngày : " + obj.gettungay() + "" );
		    
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("C4"); 
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Đến ngày : " + obj.getdenngay() + "" );
			
		    cells.setRowHeight(4, 18.0f);
		    cell = cells.getCell("A5");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
		    
		    cells.setRowHeight(5, 18.0f);
		    cell = cells.getCell("A6");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());

			
			cell = cells.getCell("DA1");		cell.setValue("KenhBanHang");
			cell = cells.getCell("DB1");		cell.setValue("DonViKinhDoanh");
			cell = cells.getCell("DC1");		cell.setValue("ChiNhanh");
			cell = cells.getCell("DD1");		cell.setValue("KhuVuc");
			cell = cells.getCell("DE1");		cell.setValue("GiamSat");			
			cell = cells.getCell("DF1");		cell.setValue("NhaPhanPhoi");		
			cell = cells.getCell("DG1");		cell.setValue("NhanHang");
			cell = cells.getCell("DH1");		cell.setValue("ChungLoai");
			cell = cells.getCell("DI1");		cell.setValue("MaNhaPhanPhoi"); //cell.setValue("Smart Id"); 
			cell = cells.getCell("DJ1");		cell.setValue("MaDaiDienKinhDoanh");
			cell = cells.getCell("DK1");		cell.setValue("DaiDienKinhDoanh");
			cell = cells.getCell("DL1");		cell.setValue("TenSanPham");
			cell = cells.getCell("DM1");		cell.setValue("MaSanPham");
			cell = cells.getCell("DN1");		cell.setValue("KhachHang");	
			cell = cells.getCell("DO1");		cell.setValue("ViTriCuaHang");
			cell = cells.getCell("DP1");		cell.setValue("LoaiCuaHang");
			cell = cells.getCell("DQ1");
			if(obj.getFromMonth() != "")
				cell.setValue("Thang");
			else	
				cell.setValue("Ngay");
			cell = cells.getCell("DR1");		cell.setValue("NhomSanPham");
			cell = cells.getCell("DS1");		cell.setValue("NhomKhachHang");
			cell = cells.getCell("DT1");		cell.setValue("SoTien");
			cell = cells.getCell("DU1");		cell.setValue("SoLuong(Le)");
			cell = cells.getCell("DV1");		cell.setValue("SoLuong(Thung)");
			cell = cells.getCell("DW1");		cell.setValue("SmartId");
			cell = cells.getCell("DX1");		cell.setValue("MaKhachHang");
			cell = cells.getCell("DY1");		cell.setValue("TrongLuong");
		}catch(Exception ex){
			throw new Exception("Xin loi,Khong tao duoc header cho bao cao");
		}
		
	}
	
	private boolean FillDataLevelThree(Worksheet worksheet, IStockintransit obj, String query) throws Exception{
		
		dbutils db = new dbutils();
		Cells cells = worksheet.getCells();		
		ResultSet rs = db.get(query);
		int i = 2;		
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
				cells.setColumnWidth(8, 15.0f);
				cells.setColumnWidth(9, 15.0f);
				cells.setColumnWidth(10, 20.0f);
				cells.setColumnWidth(11, 20.0f);
				cells.setColumnWidth(12, 20.0f);
				cells.setColumnWidth(13, 20.0f);
				cells.setColumnWidth(14, 20.0f);
				cells.setColumnWidth(15, 20.0f);
				cells.setColumnWidth(16, 20.0f);
				cells.setColumnWidth(17, 20.0f);
				cells.setColumnWidth(18, 20.0f);
				cells.setColumnWidth(19, 20.0f);
				cells.setColumnWidth(20, 20.0f);
				cells.setColumnWidth(21, 20.0f);
				cells.setColumnWidth(22, 20.0f);
			
				cell = cells.getCell("Z1"); cell.setValue(3);
				
				while (rs.next()) {					
					String chanel = rs.getString("chanel");
					String bu = rs.getString("bu");
					String region = rs.getString("region");
					String area = rs.getString("area");
					String salesuper = rs.getString("salesuper");
					String distributor = rs.getString("distributor");				
					String salesRep = rs.getString("SalesRep");
					String salesRepId = rs.getString("SalesRepId");					
					String brand = rs.getString("brand");
					String category = rs.getString("category");
					String discode = rs.getString("distcode");
					String sku = rs.getString("SKU");
					String skuCode = rs.getString("SKUcode");					
					String customer = rs.getString("customer");	
					String customer_code = rs.getString("customercode");
					String outlessClass = rs.getString("OutletClass");
					String outlettype = rs.getString("outlettype");
					String offdate =  rs.getString("offdate");
					String groupCustomer = rs.getString("nhomkhachhang");
					String groupSKU = rs.getString("nhomsanpham");
					double amount = rs.getDouble("amount");
					float piece = rs.getFloat("piece");
					float quantity = rs.getFloat("quantity");	
					
					//String smartId = rs.getString("SmartId");
					String smartId = "123456";
					
					cell = cells.getCell("DA" + Integer.toString(i));	cell.setValue(chanel);
					cell = cells.getCell("DB" + Integer.toString(i));	cell.setValue(bu);
					cell = cells.getCell("DC" + Integer.toString(i));	cell.setValue(region);
					cell = cells.getCell("DD" + Integer.toString(i));	cell.setValue(area);
					cell = cells.getCell("DE" + Integer.toString(i));	cell.setValue(salesuper);					
					cell = cells.getCell("DF" + Integer.toString(i));	cell.setValue(distributor);					
					cell = cells.getCell("DG" + Integer.toString(i));	cell.setValue(brand);
					cell = cells.getCell("DH" + Integer.toString(i));	cell.setValue(category);
					cell = cells.getCell("DI" + Integer.toString(i));	cell.setValue(discode);					
					cell = cells.getCell("DJ" + Integer.toString(i));	cell.setValue(salesRepId);
					cell = cells.getCell("DK" + Integer.toString(i));	cell.setValue(salesRep);					
					cell = cells.getCell("DL" + Integer.toString(i));	cell.setValue(sku);
					cell = cells.getCell("DM" + Integer.toString(i));	cell.setValue(skuCode);
					cell = cells.getCell("DN" + Integer.toString(i));	cell.setValue(customer);
					cell = cells.getCell("DO" + Integer.toString(i));	cell.setValue(outlessClass);
					cell = cells.getCell("DP" + Integer.toString(i));	cell.setValue(outlettype);
					cell = cells.getCell("DQ" + Integer.toString(i));	cell.setValue(offdate);
					cell = cells.getCell("DR" + Integer.toString(i));	cell.setValue(groupSKU);
					cell = cells.getCell("DS" + Integer.toString(i));	cell.setValue(groupCustomer);
					cell = cells.getCell("DT" + Integer.toString(i));	cell.setValue(amount);
					cell = cells.getCell("DU" + Integer.toString(i));	cell.setValue(piece);
					cell = cells.getCell("DV" + Integer.toString(i));	cell.setValue(quantity);	
					cell = cells.getCell("DW" + Integer.toString(i));	cell.setValue(smartId);
					cell = cells.getCell("DX" + Integer.toString(i));	cell.setValue(customer_code);	
					cell = cells.getCell("DY" + Integer.toString(i));	cell.setValue(rs.getDouble("trongluong"));
					++i;					
				}
				if (rs != null)
					rs.close();
				
				if(db != null) 
					db.shutDown();
				
				if(i==2){					
					throw new Exception("Chưa có dữ liệu báo cáo trong thời gian này.");
				}
				
			}catch(Exception ex){
				throw new Exception(ex.getMessage());
			}
		}else{
			return false;
		}		
		return true;
	}		
	
	private String createQueryBCDoanhSo(String sql, IStockintransit obj,String level) {
		String Distributor = "";
		String	Customer = "";
		/*
		 * Kiem tra ngay bat dau tao bao cao,neu ngay bat dau >2012-04-01 thi lay co giam sat
		 */
		
		 String SalesRep= "";
		 
		 int nam = 0;
		 int thang = 0;
		 if(obj.getFromMonth() != "")
		 {
			 nam = Integer.parseInt(obj.getYear());
			 thang = Integer.parseInt(obj.getFromMonth());
		 }
		 else
		 {
			 nam = Integer.valueOf(obj.gettungay().substring(0,4));
			 thang = Integer.valueOf(obj.gettungay().substring(6,7));
		 }

		 if(obj.getFromMonth().length() <= 0) //xem theo ngay
		 {
					SalesRep =  "SELECT	dvkd.donvikinhdoanh as BU, V.TEN AS REGION, KV.TEN AS AREA, NPP.TEN AS DISTRIBUTOR, NPP.SITECODE AS DISTCODE," +
					  		"NH.TEN AS BRAND,  CL.TEN AS CATEGORY, SP.TEN AS SKU, SP.MA AS SKUCODE, DDKD.TEN AS SALESREP, DDKD.pk_seq as SALESREPID, KBH.DIENGIAI AS CHANEL," +
					  		"DH.NGAYNHAP AS NGAY, ISNULL(GSBH.TEN, 'Chua xac dinh') AS SALESUPER, sum(DH.SOLUONG) AS PIECE, sum( DH.SOLUONG*ISNULL(SP.TRONGLUONG,0) ) AS trongluong, ";
					if(obj.getdiscount().equals("1"))
							SalesRep+=	"sum(dh.giamua * dh.soluong * " + obj.getvat() +") as amount, ";
					else
							SalesRep+=	"sum(dh.giamua * dh.soluong * " + obj.getvat() +" - dh.CHIETKHAU ) as amount, ";
					
					SalesRep += "sum(ISNULL(ROUND((CAST((QC.SOLUONG2 * DH.SOLUONG) AS FLOAT) / CAST(QC.SOLUONG1 AS FLOAT)),3), 0)) AS QUANTITY	FROM( " +
					"SELECT  DH.NGAYNHAP, DH.DDKD_FK, DH.KHACHHANG_FK, DH.NPP_FK, DH.GSBH_FK, DH.KHO_FK, DH.KBH_FK, ISNULL(DH_SP.SANPHAM_FK, DH_SP1.SANPHAM_FK) AS SANPHAM_FK, " +
					"ISNULL(DH_SP.GIAMUA, DH_SP1.GIAMUA) AS GIAMUA , (-1)*ISNULL(DH_SP.SOLUONG, DH_SP1.SOLUONG) AS SOLUONG, " +
					"(-1)*ISNULL(DH_SP1.CHIETKHAU, 0) AS CHIETKHAU  FROM  DONHANGTRAVE DH LEFT OUTER JOIN  DONHANGTRAVE_SANPHAM DH_SP ON DH_SP.DONHANGTRAVE_FK = DH.PK_SEQ " +
					"LEFT OUTER JOIN  DONHANG_SANPHAM DH_SP1 ON DH.DONHANG_FK = DH_SP1.DONHANG_FK  " +
					"WHERE DH.TRANGTHAI = 3 AND DH.NGAYNHAP >='" + obj.gettungay() + "' AND DH.NGAYNHAP <= '" + obj.getdenngay() + "' " +
					"UNION ALL SELECT  DH.NGAYNHAP, DH.DDKD_FK, DH.KHACHHANG_FK, DH.NPP_FK, DH.GSBH_FK, DH.KHO_FK, DH.KBH_FK, DH_SP.SANPHAM_FK, DH_SP.GIAMUA, " +
					"DH_SP.SOLUONG, DH_SP.CHIETKHAU  FROM DONHANG DH  INNER JOIN DONHANG_SANPHAM  DH_SP ON DH.PK_SEQ = DH_SP.DONHANG_FK WHERE DH.TRANGTHAI = 1 " +
					"AND DH.NGAYNHAP >='" + obj.gettungay() + "' AND DH.NGAYNHAP <= '" + obj.getdenngay() + "' ) DH " +
					"INNER JOIN SANPHAM SP ON DH.SANPHAM_FK = SP.PK_SEQ INNER JOIN NHAPHANPHOI NPP ON DH.NPP_FK = NPP.PK_SEQ " +
					"INNER JOIN KHUVUC KV ON NPP.KHUVUC_FK = KV.PK_SEQ INNER JOIN VUNG V ON KV.VUNG_FK = V.PK_SEQ " +
					"INNER JOIN NHANHANG NH ON SP.NHANHANG_FK = NH.PK_SEQ LEFT JOIN CHUNGLOAI CL ON SP.CHUNGLOAI_FK = CL.PK_SEQ " +
					"INNER JOIN DAIDIENKINHDOANH DDKD ON DH.DDKD_FK = DDKD.PK_SEQ INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK " +
					"inner join kenhbanhang KBH on DH.kbh_fk = KBH.pk_seq " +
					"LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK = SP.PK_SEQ LEFT JOIN GIAMSATBANHANG GSBH ON DH.GSBH_FK = GSBH.PK_SEQ " + sql +
					"group by dvkd.donvikinhdoanh, V.TEN, KV.TEN,  NPP.TEN, NPP.SITECODE, NH.TEN, CL.TEN, SP.TEN, SP.MA, DDKD.TEN, DDKD.pk_seq, KBH.DIENGIAI, DH.NGAYNHAP, GSBH.TEN ORDER BY DH.NGAYNHAP";
					
					
					Distributor = "SELECT	dvkd.donvikinhdoanh as BU, V.TEN AS REGION, KV.TEN AS AREA, NPP.TEN AS DISTRIBUTOR, NPP.SITECODE AS DISTCODE, " +
							"NH.TEN AS BRAND, CL.TEN AS CATEGORY, SP.TEN AS SKU, SP.MA AS SKUCODE, KBH.DIENGIAI AS CHANEL, DH.NGAYNHAP as ngay, SUM(DH.SOLUONG) AS PIECE, sum( DH.SOLUONG*ISNULL(SP.TRONGLUONG,0) ) AS trongluong, ";
					
					if(obj.getdiscount().equals("1"))
						   Distributor+= " sum(dh.giamua * dh.soluong * " + obj.getvat() +") as amount, ";
					else
						Distributor+= " sum(dh.giamua * dh.soluong *  "+ obj.getvat() +" - dh.CHIETKHAU) as amount, ";
							
					Distributor += "sum(ISNULL(ROUND((CAST((QC.SOLUONG2 * DH.SOLUONG) AS FLOAT) / CAST(QC.SOLUONG1 AS FLOAT)),3), 0)) AS QUANTITY " +
							"FROM (SELECT  DH.NGAYNHAP, DH.DDKD_FK, DH.NPP_FK, DH.GSBH_FK, DH.KHO_FK, DH.KBH_FK, ISNULL(DH_SP.SANPHAM_FK, DH_SP1.SANPHAM_FK) AS SANPHAM_FK, " +
							"ISNULL(DH_SP.GIAMUA, DH_SP1.GIAMUA) AS GIAMUA , (-1)*ISNULL(DH_SP.SOLUONG, DH_SP1.SOLUONG) AS SOLUONG, " +
							"(-1)* ISNULL(DH_SP1.CHIETKHAU, 0) AS CHIETKHAU  FROM  DONHANGTRAVE DH LEFT OUTER JOIN  DONHANGTRAVE_SANPHAM DH_SP ON DH_SP.DONHANGTRAVE_FK = DH.PK_SEQ  " +
							"LEFT OUTER JOIN  DONHANG_SANPHAM DH_SP1 ON DH.DONHANG_FK = DH_SP1.DONHANG_FK  " +
							"WHERE DH.TRANGTHAI = 3 AND DH.NGAYNHAP >='" + obj.gettungay() + "' AND DH.NGAYNHAP <= '" + obj.getdenngay() + "' " +
							"UNION ALL SELECT  DH.NGAYNHAP, DH.DDKD_FK, DH.NPP_FK, DH.GSBH_FK, DH.KHO_FK, DH.KBH_FK, DH_SP.SANPHAM_FK, DH_SP.GIAMUA, " +
							"DH_SP.SOLUONG, DH_SP.CHIETKHAU  FROM DONHANG DH  INNER JOIN DONHANG_SANPHAM  DH_SP ON DH.PK_SEQ = DH_SP.DONHANG_FK WHERE DH.TRANGTHAI = 1 " +
							"AND DH.NGAYNHAP >='" + obj.gettungay() + "' AND DH.NGAYNHAP <= '" + obj.getdenngay() + "' ) DH " +
							"INNER JOIN SANPHAM SP ON DH.SANPHAM_FK = SP.PK_SEQ " +
							"INNER JOIN NHAPHANPHOI NPP ON DH.NPP_FK = NPP.PK_SEQ INNER JOIN KHUVUC KV ON NPP.KHUVUC_FK = KV.PK_SEQ INNER JOIN VUNG V ON KV.VUNG_FK = V.PK_SEQ " +
							"INNER JOIN NHANHANG NH ON SP.NHANHANG_FK = NH.PK_SEQ LEFT JOIN CHUNGLOAI CL ON SP.CHUNGLOAI_FK = CL.PK_SEQ " +
							"inner join kenhbanhang KBH on DH.kbh_fk = KBH.pk_seq " +
							"INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK = SP.PK_SEQ " + sql + 
							"group by dvkd.donvikinhdoanh, V.TEN, KV.TEN, NPP.TEN, NPP.SITECODE, NH.TEN, CL.TEN, SP.TEN, SP.MA, KBH.DIENGIAI, DH.NGAYNHAP ORDER BY DH.NGAYNHAP";
			
					
					Customer = "SELECT	dvkd.donvikinhdoanh as BU, V.TEN AS REGION, V.pk_seq as regionId, KV.TEN AS AREA, KV.PK_seq as areaId, NPP.TEN AS DISTRIBUTOR, NPP.SITECODE AS DISTCODE, NPP.pk_seq as nppId, " +
							"NH.TEN AS BRAND, NH.PK_SEQ as BRANDID, CL.TEN AS CATEGORY, CL.pk_seq as CATEGORYID, SP.TEN AS SKU, SP.MA AS SKUCODE, DDKD.TEN AS SALESREP, DDKD.pk_seq as SALESREPID, " +
							"NPP.SITECODE + '_' + KH.TEN + '( ' + KH.DIACHI +  ' )' AS CUSTOMER, KH.PK_SEQ AS CUSTOMERCODE, case when CHARINDEX('_', kh.smartid) > 0 then rtrim(substring(kh.smartid, CHARINDEX('_', kh.smartid)+1, 10)) + npp.sitecode " +
							"when CHARINDEX('_', kh.smartid) <= 0 then kh.smartid + '-' + npp.sitecode end as smartid, " +
							"VTCH.VITRI AS OUTLETPOSITION, ISNULL(M.DIENGIAI, 'Chua Xac Dinh') AS OUTLETTYPE, KBH.DIENGIAI AS CHANEL, KBH.pk_seq as CHANELID, " +
							"DH.NGAYNHAP AS OFFDATE, DH.SOLUONG AS PIECE, DH.SOLUONG * SP.TRONGLUONG AS TRONGLUONG,";
					
					 if(obj.getdiscount().equals("1"))
				    	 Customer +=" dh.giamua * dh.soluong * " + obj.getvat() + " as amount ,";
				     else
				    	 Customer +=" dh.giamua * dh.soluong * " + obj.getvat()+" - dhsp.chietkhau  as amount ,";
	
					Customer += "ISNULL(GSBH.TEN, 'Chua xac dinh') AS SALESUPER, ISNULL(ROUND((CAST((QC.SOLUONG2 * DH.SOLUONG) AS FLOAT) / CAST(QC.SOLUONG1 AS FLOAT)),3), 0) AS QUANTITY, " +
					"ISNULL(QH.TEN, 'Chua xac dinh') AS TOWN, ISNULL(TT.TEN, 'Chua xac dinh') AS PROVINCE, HCH.DIENGIAI AS OUTLETCLASS, NKH.DIENGIAI AS NHOMKHACHHANG, NSP.DIENGIAI AS NHOMSANPHAM " +
					"FROM( SELECT  DH.NGAYNHAP, DH.DDKD_FK, DH.KHACHHANG_FK, DH.NPP_FK, DH.GSBH_FK, DH.KHO_FK, DH.KBH_FK, ISNULL(DH_SP.SANPHAM_FK, DH_SP1.SANPHAM_FK) AS SANPHAM_FK, " +
					"ISNULL(DH_SP.GIAMUA, DH_SP1.GIAMUA) AS GIAMUA , (-1)*ISNULL(DH_SP.SOLUONG, DH_SP1.SOLUONG) AS SOLUONG, " +
					"(-1)*ISNULL(DH_SP1.CHIETKHAU, 0) AS CHIETKHAU  FROM  DONHANGTRAVE DH LEFT OUTER JOIN  DONHANGTRAVE_SANPHAM DH_SP ON DH_SP.DONHANGTRAVE_FK = DH.PK_SEQ " +
					"LEFT OUTER JOIN  DONHANG_SANPHAM DH_SP1 ON DH.DONHANG_FK = DH_SP1.DONHANG_FK  " +
					"WHERE DH.TRANGTHAI = 3 AND  DH.NGAYNHAP >='" + obj.gettungay() + "' AND DH.NGAYNHAP <= '" + obj.getdenngay() + "' " +
					"UNION ALL SELECT  DH.NGAYNHAP, DH.DDKD_FK, DH.KHACHHANG_FK, DH.NPP_FK, DH.GSBH_FK, DH.KHO_FK, DH.KBH_FK, DH_SP.SANPHAM_FK, DH_SP.GIAMUA, " +
					"DH_SP.SOLUONG, DH_SP.CHIETKHAU  FROM DONHANG DH  INNER JOIN DONHANG_SANPHAM  DH_SP ON DH.PK_SEQ = DH_SP.DONHANG_FK WHERE DH.TRANGTHAI = 1 " +
					"AND DH.NGAYNHAP >='" + obj.gettungay() + "' AND DH.NGAYNHAP <= '" + obj.getdenngay() + "' ) DH INNER JOIN SANPHAM SP ON DH.SANPHAM_FK = SP.PK_SEQ " +
					"INNER JOIN NHAPHANPHOI NPP ON DH.NPP_FK = NPP.PK_SEQ INNER JOIN KHUVUC KV ON NPP.KHUVUC_FK = KV.PK_SEQ INNER JOIN VUNG V ON KV.VUNG_FK = V.PK_SEQ " +
					"INNER JOIN NHANHANG NH ON SP.NHANHANG_FK = NH.PK_SEQ LEFT JOIN CHUNGLOAI CL ON SP.CHUNGLOAI_FK = CL.PK_SEQ " +
					"INNER JOIN DAIDIENKINHDOANH DDKD ON DH.DDKD_FK = DDKD.PK_SEQ INNER JOIN KHACHHANG KH ON DH.KHACHHANG_FK = KH.PK_SEQ " +
					"LEFT JOIN VITRICUAHANG VTCH ON KH.VTCH_FK = VTCH.PK_SEQ INNER JOIN KENHBANHANG KBH ON DH.KBH_FK = KBH.PK_SEQ " +
					"LEFT JOIN LOAICUAHANG M ON KH.LCH_FK = M.PK_SEQ LEFT JOIN GIAMSATBANHANG GSBH ON DH.GSBH_FK = GSBH.PK_SEQ " +
					"INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK = SP.PK_SEQ " +
					"LEFT JOIN QUANHUYEN QH ON KH.QUANHUYEN_FK = QH.PK_SEQ LEFT JOIN TINHTHANH TT ON KH.TINHTHANH_FK = TT.PK_SEQ " +
					"LEFT JOIN HANGCUAHANG HCH ON KH.HCH_FK = HCH.PK_SEQ LEFT JOIN NHOMKHACHHANG_KHACHHANG NKHKH ON NKHKH.KH_FK = KH.PK_SEQ " +
					"LEFT JOIN NHOMKHACHHANG NKH ON NKH.PK_SEQ = NKHKH.NKH_FK LEFT JOIN ( SELECT SP_FK, MAX(NSP_FK) AS NHOMSP FROM NHOMSANPHAM_SANPHAM  GROUP BY SP_FK ) NSPSP " +
					"ON NSPSP.SP_FK = SP.PK_SEQ LEFT JOIN NHOMSANPHAM NSP ON NSP.PK_SEQ = NSPSP.NHOMSP " + sql + " ORDER BY DH.NGAYNHAP";
					
			  }
			  else //xem theo thang
			  {
				  System.out.println("Xem theo thang...");
				  
				  	geso.dms.center.util.Utility utilcenter = new geso.dms.center.util.Utility();
					sql = "";
					if (obj.getkenhId().length() > 0)
						sql = sql + " and chanelId ='" + obj.getkenhId() + "'";
					else
						sql = sql + " and chanelId in  " + utilcenter.quyen_kenh(obj.getuserId());
					
					if (obj.getvungId().length() > 0)
						sql = sql + " and regionId ='" + obj.getvungId() + "'";
					if (obj.getkhuvucId().length() > 0)
						sql = sql + " and areaId ='" + obj.getkhuvucId() + "'";			
					if (obj.getnppId().length() > 0)
						sql = sql + " and nppId ='" + obj.getnppId() + "'";
					else
						sql = sql + " and nppId in " + utilcenter.quyen_npp(obj.getuserId());
					
					if (obj.getnhanhangId().length() > 0)
						sql = sql + " and brandId ='" + obj.getnhanhangId() + "'";
					
					if (obj.getchungloaiId().length() > 0)
						sql = sql + " and categoryId ='" + obj.getchungloaiId()	+ "' ";
					
				  
				  SalesRep =  "select BU, region, area, isnull(salesuper, 'Chua xac dinh') as salesuper, SalesRepId, SalesRep, distcode, brand, " +
			  		"category, distributor, SKU, SKUcode, chanel, sum(piece) as piece, sum(quantity) as quantity, thang as offdate, ";
			  
				  if(obj.getdiscount().equals("1")) //co ck
				  {
						if(obj.getvat().equals("1")) //khong VAT
							SalesRep += " sum(amount_CK_YES_VAT_NO) as amount ";
						else
							SalesRep += " sum(amount_CK_YES_VAT_YES) as amount ";
				  }
				  else
				  {
					  //Distributor+= "  sum(a.giamua * a.soluong *  " + obj.getvat() +" - a.CHIETKHAU) as amount";
					  if(obj.getvat().equals("1")) //khong VAT
						  SalesRep += " sum(amount_CK_NO_VAT_NO) as amount ";
					  else
						  SalesRep += " sum(amount_CK_NO_VAT_YES) as amount ";
				  }
				  
				  SalesRep += "from doanhsobanhang where thang >= '" + obj.getFromMonth() + "' and thang <= '" + obj.getToMonth() + "' and nam = '" + obj.getYear() + "' " + sql + 
					 " group by BU, region, area, salesuper, SalesRepId, SalesRep, distcode, brand, category, distributor, SKU, SKUcode, chanel, thang order by thang";
				
				Distributor = "select BU, region, area, brand, category, distcode, distributor, SKU, SKUcode, chanel, sum(quantity) as quantity, sum(piece) as piece, thang as ngay, ";
				if(obj.getdiscount().equals("1")) //co ck
				{
					if(obj.getvat().equals("1")) //khong VAT
						Distributor += " sum(amount_CK_YES_VAT_NO) as amount ";
					else
						Distributor += " sum(amount_CK_YES_VAT_YES) as amount ";
				}
				else
				{
					//Distributor+= "  sum(a.giamua * a.soluong *  " + obj.getvat() +" - a.CHIETKHAU) as amount";
					if(obj.getvat().equals("1")) //khong VAT
						Distributor += " sum(amount_CK_NO_VAT_NO) as amount ";
					else
						Distributor += " sum(amount_CK_NO_VAT_YES) as amount ";
				}
						
				Distributor += "from doanhsobanhang where thang >= '" + obj.getFromMonth() + "' and thang <= '" + obj.getToMonth() + "' and nam = '" + obj.getYear() + "' " + sql + 
					" group by BU, region, area, brand, category, distcode, distributor, SKU, SKUcode, chanel, thang order by thang";
				
				System.out.println("Distributor: " + Distributor);
				
				Customer = "select bu, region, area, distributor, distcode, brand, category, sku, skuCode, salesRep, salesRepId, customer, customercode, smartId, " +
						"outletposition, outlettype, chanel, salesuper, town, province, outletclass, nhomkhachhang, nhomsanpham, thang as offdate, " +
						"sum(piece) as piece, sum(quantity) as quantity, ";
				if(obj.getdiscount().equals("1")) //co ck
				{
					if(obj.getvat().equals("1")) //khong VAT
						Customer += " sum(amount_CK_YES_VAT_NO) as amount from doanhsobanhang where thang >= '" + obj.getFromMonth() + "' and thang <= '" + obj.getToMonth() + "' and nam = '" + obj.getYear() + "' " + sql;
					else
						Customer += " sum(amount_CK_YES_VAT_YES) as amount from doanhsobanhang where thang >= '" + obj.getFromMonth() + "' and thang <= '" + obj.getToMonth() + "' and nam = '" + obj.getYear() + "' " + sql;
				}
				else
				{
					//Distributor+= "  sum(a.giamua * a.soluong *  " + obj.getvat() +" - a.CHIETKHAU) as amount";
					if(obj.getvat().equals("1")) //khong VAT
						Customer += " sum(amount_CK_NO_VAT_NO) as amount from doanhsobanhang where thang >= '" + obj.getFromMonth() + "' and thang <= '" + obj.getToMonth() + "' and nam = '" + obj.getYear() + "' " + sql;
					else
						Customer += " sum(amount_CK_NO_VAT_YES) as amount from doanhsobanhang where thang >= '" + obj.getFromMonth() + "' and thang <= '" + obj.getToMonth() + "' and nam = '" + obj.getYear() + "' " + sql;
				}
				Customer += " group by  bu, region, area, distributor, distcode, brand, category, sku, skuCode, salesRep, salesRepId, customer, customercode, smartId," +
							" outletposition, outlettype, chanel, salesuper, town, province, outletclass, nhomkhachhang, nhomsanpham, thang order by thang";
			  }  
	     
	     String query = "";
	     switch (Integer.parseInt(level)) 
	     {
			case 0:		
				query = Distributor;
				System.out.println("QUERY Doanh So Distributor la: " + query);
				break;
			case 1:	
				query = SalesRep;
				System.out.println("QUERY Doanh So SalesRep la: " + query);
				break;
			case 2:
				query = Customer;
				System.out.println("QUERY Doanh So Customer la: " + query);
				break;
	     }
	    
	     return query;			
		
	}	
	
	private void CreatePivotDailyStock(Worksheet worksheet, IStockintransit obj) throws IOException
    {         
	    CreateHeaderDailyStock(worksheet, obj);
 
	    FillDataDailyStock(worksheet,obj);
   }

	private void CreateHeaderDailyStock(Worksheet worksheet, IStockintransit obj) 
	{

		Cells cells = worksheet.getCells();

		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
		getCellStyle(worksheet,"A1",Color.RED,true,14);
	    cell.setValue("TỒN KHO HÀNG NGÀY");   	
	    
	    cells.setRowHeight(2, 20.0f);
	    cell = cells.getCell("A3"); 
	    getCellStyle(worksheet,"A3",Color.NAVY,true,9);	    
	    cell.setValue("Từ ngày: " + obj.gettungay());
	    
	    
	    cells.setRowHeight(3, 20.0f);
	    cell = cells.getCell("B3"); getCellStyle(worksheet,"B3",Color.NAVY,true,9);	        
	    cell.setValue("Đến ngày: " + obj.getdenngay());    
	    
	    cells.setRowHeight(4, 20.0f);
	    cell = cells.getCell("A4");getCellStyle(worksheet,"A4",Color.NAVY,true,9);
	    cell.setValue("Ngày báo cáo: " + obj.getDateTime());
	    
	    cells.setRowHeight(5, 20.0f);
	    cell = cells.getCell("A5");getCellStyle(worksheet,"A5",Color.NAVY,true,9);
	    cell.setValue("Được tạo bởi:  " + obj.getuserTen());
			    
	    cell = cells.getCell("EA1"); cell.setValue("KenhBanHang");
	    cell = cells.getCell("EB1"); cell.setValue("DonViKinhDoanh");
	    cell = cells.getCell("EC1"); cell.setValue("ChiNhanh");
	    cell = cells.getCell("ED1"); cell.setValue("Vung");
	    cell = cells.getCell("EE1"); cell.setValue("MaNhaPhanPhoi");
	    cell = cells.getCell("EF1"); cell.setValue("NhaPhanPhoi"); //6
	    cell = cells.getCell("EG1"); cell.setValue("NhanHang");
	    cell = cells.getCell("EH1"); cell.setValue("ChungLoai");
	    cell = cells.getCell("EI1"); cell.setValue("MaSanPham");
	    cell = cells.getCell("EJ1"); cell.setValue("TenSanPham"); //9
	    cell = cells.getCell("EK1"); cell.setValue("Kho");  //10
	    cell = cells.getCell("EL1"); cell.setValue("SoLuongLe");	//11  
	    cell = cells.getCell("EM1"); cell.setValue("SoTien");
	    cell = cells.getCell("EN1"); cell.setValue("Date");
	    cell = cells.getCell("EO1"); cell.setValue("TrongLuong");
	}
	
	private void FillDataDailyStock(Worksheet worksheet, IStockintransit obj) 
	{
	    Cells cells = worksheet.getCells();
	    dbutils db = new dbutils();

	    String sql = " select isnull(d.ten, 'Chua xac dinh') as Chanel, h.ten as Region," +
	    		" f.ten as Area, e.ten as Distributor, e.sitecode as Distcode, "+
		   " b.ten as SKU, b.ma as SKUcode, a.ngay as Date, c.ten as Warehouse, g.ten as Province, "+
		   " i.donvikinhdoanh as BusinessUnit, k.ten as Brands, j.ten as Category, a.soluong as Piece, "+
		   " case when a.soluong is null then 0 else a.soluong/isnull(qc.soluong1,1) end as Quatity,"+   		   
			"	isnull( "+ 
			"	(  "+
			"		select top(1) bgmsp.giamuanpp  "+
			"		from banggiamuanpp  bgm inner join banggiamuanpp_npp bgmnpp on bgm.pk_seq=bgmnpp.banggiamuanpp_fk "+ 
			"			inner join bgmuanpp_sanpham bgmsp on bgmsp.bgmuanpp_fk=bgm.pk_seq "+ 
			"		where bgmsp.sanpham_fk=b.pk_seq  "+
			"			and bgmnpp.npp_fk=a.npp_fk  "+
			"			and bgm.kenh_fk=a.kbh_fk and bgm.dvkd_fk = b.dvkd_fk "+  
			"		order by bgm.tungay desc   "+
			"	),0) * a.soluong as Amount , "+		   
		   " a.soluong*b.trongluong as trongluong  "+
		   " from (" +
		   "select * from tonkhongay " +
			" where ngay >='" + obj.gettungay() + "' and ngay <= '" + obj.getdenngay() + "' and soluong > 0" +
		   ") a inner join sanpham b on a.sanpham_fk = b.pk_seq inner join kho c on a.kho_fk = c.pk_seq " + 
		   " inner join kenhbanhang d on a.kbh_fk = d.pk_seq	inner join nhaphanphoi e on a.npp_fk = e.pk_seq " +
		   " inner join khuvuc f on e.khuvuc_fk = f.pk_seq inner join tinhthanh g on e.tinhthanh_fk = g.pk_seq " +
		   " inner join vung h on f.vung_fk = h.pk_seq inner join donvikinhdoanh i on b.dvkd_fk = i.pk_seq " +
		   " inner join chungloai j on b.chungloai_fk = j.pk_seq inner join nhanhang k on b.nhanhang_fk = k.pk_seq " + 
	       /*" left join ( "+
		   " select distinct bgm.kenh_fk as kbh_fk,bgm_sp.sanpham_fk,bgmnpp.npp_fk,bgm_sp.giamuanpp as giamua from banggiamuanpp_npp bgmnpp "+ 
		   " inner join banggiamuanpp bgm on bgm.pk_seq = bgmnpp.banggiamuanpp_fk "+
		   " inner join bgmuanpp_sanpham bgm_sp on bgm_sp.bgmuanpp_fk = bgm.pk_seq ";
		
	    	if(obj.getnppId().length() > 0)
	    		sql = sql + " where bgmnpp.npp_fk ='"+ obj.getnppId() +"'";
	    
		sql = sql + " ) nppk on " +
        " nppk.npp_fk = a.npp_fk " + 
        " and nppk.sanpham_fk = a.sanpham_fk and nppk.kbh_fk = a.kbh_fk " + */
	    " left join quycach qc on qc.dvdl1_fk = b.dvdl_fk and a.sanpham_fk = qc.sanpham_fk where 1=1  ";
	    
		geso.dms.center.util.Utility Uti_center = new geso.dms.center.util.Utility();
		
		if (obj.getkenhId().length() > 0)
			sql = sql + " and d.pk_seq ='" + obj.getkenhId() + "'";
		else
			sql = sql + " and d.pk_seq in " + Uti_center.quyen_kenh(obj.getuserId());
		
		if (obj.getvungId().length() > 0)
			sql = sql + " and h.pk_seq ='" + obj.getvungId() + "'";
		if (obj.getkhuvucId().length() > 0)
			sql = sql + " and f.pk_seq ='" + obj.getkhuvucId() + "'";
		if (obj.getdvkdId().length() > 0)
			sql = sql + " and i.PK_SEQ ='" + obj.getdvkdId() + "'";
		
		if(obj.getnppId().length() > 0)
			sql = sql + " and e.pk_seq ='" + obj.getnppId() + "'";
		else
			sql = sql + " and e.pk_seq in " + Uti_center.quyen_npp(obj.getuserId());
		
		if (obj.getnhanhangId().length() > 0)
			sql = sql + " and k.pk_seq ='" + obj.getnhanhangId() + "'";
		if (obj.getchungloaiId().length() > 0)
			sql = sql + " and j.pk_seq ='" + obj.getchungloaiId() + "'";
		
		sql = sql + " and b.pk_seq in " + Uti_center.quyen_sanpham(obj.getuserId());
	    
	    //String sql = "select * from BCTonKhoNgay";
		System.out.print("Ton kho ngay: " + sql + "\n");
		ResultSet rs = db.get(sql);
		int i = 2;
		 
		Utility util = new Utility();
		if(rs != null)
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
				
				//set do rong cho dong
				for(int j = 1; j <= 7; j++)
					cells.setRowHeight(j, 14.0f);
				
				Cell cell = null;
				long Piece = 0;
				float Amount = 0;
				while(rs.next())
				{ 
					String Channel = rs.getString("Chanel");
					String Region = rs.getString("Region");
					String Area = rs.getString("Area");				
					String Distributor = rs.getString("Distributor");
					String DistributorCode = rs.getString("Distcode");			
					String Sku = rs.getString("SKU");
					String SkuCode = rs.getString("SKUcode");
					String Warehouse = rs.getString("Warehouse");
					String Date= rs.getString("Date");
					
					if(rs.getString("Amount") != null);
					 	Amount = rs.getLong("Amount");
					 	
					if(rs.getString("Piece") != null)
						Piece = rs.getLong("Piece");
					
					String BusinessUnit = "HPC";
					if(rs.getString("BusinessUnit") != null)
						BusinessUnit = rs.getString("BusinessUnit");
					
					String Brands = rs.getString("Brands");
					String Category = rs.getString("Category");
					
					cell = cells.getCell("EA" + Integer.toString(i)); cell.setValue(Channel); //0
					cell = cells.getCell("EB" + Integer.toString(i)); cell.setValue(BusinessUnit); //1
					cell = cells.getCell("EC" + Integer.toString(i)); cell.setValue(Region); //2
					cell = cells.getCell("ED" + Integer.toString(i)); cell.setValue(Area);   ///3
					cell = cells.getCell("EE" + Integer.toString(i)); cell.setValue(DistributorCode); //4
					cell = cells.getCell("EF" + Integer.toString(i)); cell.setValue(Distributor); //5
					cell = cells.getCell("EG" + Integer.toString(i)); cell.setValue(Brands); //6
					cell = cells.getCell("EH" + Integer.toString(i)); cell.setValue(Category); //7
					cell = cells.getCell("EI" + Integer.toString(i)); cell.setValue(SkuCode); //8
					cell = cells.getCell("EJ" + Integer.toString(i)); cell.setValue(Sku); //9
					cell = cells.getCell("EK" + Integer.toString(i)); cell.setValue(Warehouse); //10
					cell = cells.getCell("EL" + Integer.toString(i)); cell.setValue(Piece); //11
					cell = cells.getCell("EM" + Integer.toString(i)); cell.setValue(Amount); //12
					cell = cells.getCell("EN" + Integer.toString(i)); cell.setValue(Date);	//13
					cell = cells.getCell("EO" + Integer.toString(i)); cell.setValue(rs.getDouble("trongluong"));	//13
					i++;
				}
			
			if(rs != null) rs.close();
			if(db != null) db.shutDown();
/*			PivotTables pivotTables = worksheet.getPivotTables();
			
		    //Adding a PivotTable to the worksheet
			String pos = Integer.toString(i-1);	
		    int index = pivotTables.add("=EA1:EN" + pos,"A10","PivotTable4");
		    PivotTable pivotTable = pivotTables.get(index);//truyen index
		    
		    pivotTable.setRowGrand(false);
		    pivotTable.setColumnGrand(true);
		    pivotTable.setAutoFormat(true);
//		    pivotTable.setAutoFormatType(PivotTableAutoFormatType.TABLE10);
		    
		    pivotTable.addFieldToArea(PivotFieldType.ROW, 0); pivotTable.getRowFields().get(0).setAutoSubtotals(true); 
		    pivotTable.addFieldToArea(PivotFieldType.ROW, 1); pivotTable.getRowFields().get(1).setAutoSubtotals(true);
		    pivotTable.addFieldToArea(PivotFieldType.ROW, 2); pivotTable.getRowFields().get(2).setAutoSubtotals(true);  
		    pivotTable.addFieldToArea(PivotFieldType.ROW, 3); pivotTable.getRowFields().get(3).setAutoSubtotals(true);
		    pivotTable.addFieldToArea(PivotFieldType.ROW, 8); pivotTable.getRowFields().get(4).setAutoSubtotals(false);  
		    pivotTable.addFieldToArea(PivotFieldType.ROW, 9); pivotTable.getRowFields().get(5).setAutoSubtotals(false);  
		    
		    pivotTable.addFieldToArea(PivotFieldType.DATA, 11);   pivotTable.getDataFields().get(0).setDisplayName("SoLuongLe");
		    //pivotTable.addFieldToArea(PivotFieldType.DATA, 12);   pivotTable.getDataFields().get(1).setDisplayName("SoTien");
		    pivotTable.getDataFields().get(0).setNumber(3);
		    pivotTable.addFieldToArea(PivotFieldType.COLUMN, 13);*/
		    
		
			}
			catch (Exception e)
			{
				e.printStackTrace(); 
				System.out.println("Error : loi daily stock : " +e.toString());
			}
		}
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
	
}
