package geso.dms.distributor.servlets.chitieunpp;

import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.chitieunpp.IChitieuSKUInList;
import geso.dms.distributor.beans.chitieunpp.IChitieuSKUInTT;
import geso.dms.distributor.beans.chitieunpp.imp.ChitieuSKUInList;
import geso.dms.distributor.beans.chitieunpp.imp.ChitieuSKUInTT;
import geso.dms.distributor.db.sql.dbutils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.io.FileInputStream;
import java.io.OutputStream;

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
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class ChiTieuSKUInTTUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
    public ChiTieuSKUInTTUpdateSvl() {
        super();
    }
    
    PrintWriter out;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IChitieuSKUInTT ctskuBean;
		HttpSession session = request.getSession();
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    	    
	    String id = util.getId(querystring);  	
	    ctskuBean = new ChitieuSKUInTT(id);
	    ctskuBean.init();
	   
        session.setAttribute("ctskuBean", ctskuBean);
        String nextJSP = "/AHF/pages/Center/ChiTieuSKUInUpdate.jsp";
        if(querystring.indexOf("display") >= 0)
        	nextJSP = "/AHF/pages/Center/ChiTieuSKUInDisplay.jsp";
        
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		IChitieuSKUInTT ctskuBean;
		Utility util = new Utility();
		
	    String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));	
	    if(id == null){  	
	    	ctskuBean = new ChitieuSKUInTT();
	    }else{
	    	ctskuBean = new ChitieuSKUInTT(id);
	    }
	    	    
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		ctskuBean.setUserId(userId);	       
    			
		String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
		if (diengiai == null)
			diengiai = "";
		ctskuBean.setDiengiai(diengiai);
		
		String thang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thang")));
		if (thang == null)
			thang = "";
		ctskuBean.setThang(thang);
		
		String nam = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nam")));
		if (nam == null)
			nam = "";
		ctskuBean.setNam(nam);
		
		String nspId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nspId")));
		if (nspId == null)
			nspId = "";
		ctskuBean.setNspId(nspId);
		
		String kbhid = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhid")));
		if (kbhid == null)
			kbhid = "";
		ctskuBean.setKbhId(kbhid);
		
		String dvkdid = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdid")));
		if (dvkdid == null)
			dvkdid = "";
		ctskuBean.setDvkdId(dvkdid);
		
		
		String[] nppIds = request.getParameterValues("nppIds");
		String nppId = "";
		if (nppIds != null)
		{
			for(int i = 0; i < nppIds.length; i++)
				nppId += nppIds[i] + ",";
		}
		if(nppId.length() > 0)
			nppId = nppId.substring(0, nppId.length() - 1);
		ctskuBean.setNppIds(nppId);
		
 		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
 		if(action.equals("save"))
 		{
    		if (id == null )
    		{
    			if (!ctskuBean.createChiTieuThang())
    			{
    				ctskuBean.setUserId(userId);
    		    	session.setAttribute("userId", userId);
    		    	
    				session.setAttribute("ctskuBean", ctskuBean);
    				
    				String nextJSP = "/AHF/pages/Center/ChiTieuSKUInNew.jsp";
    				response.sendRedirect(nextJSP);
    			}
    			else
    			{
    				IChitieuSKUInList obj = new ChitieuSKUInList();
    				obj.initTT("");
    				session.setAttribute("obj", obj);
    			    
    			    String nextJSP = "/AHF/pages/Center/ChiTieuSKUIn.jsp";
    				response.sendRedirect(nextJSP);	
    			}		
    		}
    		else{
    			if (!(ctskuBean.updateChiTieuThang()))
    			{			
    				ctskuBean.setUserId(userId);
    		    	session.setAttribute("userId", userId);
    		    	
    				session.setAttribute("ctskuBean", ctskuBean);
    				
    				String nextJSP = "/AHF/pages/Center/ChiTieuSKUInUpdate.jsp";
    				response.sendRedirect(nextJSP);
    			}
    			else
    			{
    				IChitieuSKUInList obj = new ChitieuSKUInList();
    				obj.initTT("");
    				session.setAttribute("obj", obj);
    			    
    			    String nextJSP = "/AHF/pages/Center/ChiTieuSKUIn.jsp";
    				response.sendRedirect(nextJSP);		
    			}
    		}
	    }
		else
		{		
			if(action.equals("print"))
			{
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=ChiTieuSKUIn.xlsm");
				
				OutputStream outPv = response.getOutputStream();
				
				String userName = (String) session.getAttribute("userTen");  
				ctskuBean.setUserId(userName);
				createPivotTable(ctskuBean, outPv);
			}
			else
			{
				session.setAttribute("userId", userId);
				session.setAttribute("ctskuBean", ctskuBean);
				
				String nextJSP;
				
				if (id == null){
					nextJSP = "/AHF/pages/Center/ChiTieuSKUInNew.jsp";
				}
				else{
					nextJSP = "/AHF/pages/Center/ChiTieuSKUInUpdate.jsp";					
				}
				response.sendRedirect(nextJSP);
			}
		}		
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private void createPivotTable(IChitieuSKUInTT ctskuBean, OutputStream outPv)
	{
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();	
		
		try 
		{
			//fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ChiTieuSKUIn.xlsm");
			File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\ChiTieuSKUIn.xlsm");
			fstream = new FileInputStream(f) ;
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			
		     CreateStaticHeader(workbook, ctskuBean.getThang(), ctskuBean.getNam(), ctskuBean.getUserId());	     
		     CreateStaticData(workbook, ctskuBean);
		    
		     workbook.save(outPv);
				
			fstream.close();
		} 
		catch (Exception e) {}
	}
	
	private void CreateStaticHeader(Workbook workbook, String dateFrom, String dateTo, String UserName) 
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
	    
	    String tieude = "CHỈ TIÊU SKU IN";
	    ReportAPI.getCellStyle(cell,Color.RED, true, 14, tieude);
	         
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("A4");
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Tháng: " + dateFrom + "" );
	    
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("B4"); 
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Năm: " + dateTo + "" );
		
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A5");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A6");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + UserName);
	    
	    cell = cells.getCell("AA1"); 	cell.setValue("BM");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AB1"); 	cell.setValue("Vung");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AC1"); 	cell.setValue("ASM");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AD1"); 	cell.setValue("KhuVuc");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AE1"); 	cell.setValue("GiamSatBanHang");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AF1"); 	cell.setValue("MaNhaPhanPhoi");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AG1"); 	cell.setValue("TenNhaPhanPhoi");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AH1"); 	cell.setValue("MaSanPham");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AI1"); 	cell.setValue("TenSanPham");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AJ1"); 	cell.setValue("ChiTieu"); ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AK1"); 	cell.setValue("ThucDat");   ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AL1"); 	cell.setValue("%");	   ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AM1"); 	cell.setValue("TRANGTHAI");	   ReportAPI.setCellHeader(cell);
	}
	

	private boolean CreateStaticData(Workbook workbook, IChitieuSKUInTT ctskuBean) 
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
		
	    String thang = ctskuBean.getThang();
	    if(thang.trim().length() < 2)
	    	thang = "0" + thang;
	    
		String query = "select CHITIEU.*, isnull(SKUIN.soluongNhap, 0) as THUCDAT, cast( (isnull(SKUIN.soluongNhap, 0) * 100 / CHITIEU.chitieu) as numeric(18, 0) )  as PhanTram, " +
							"isnull(GIAMSATBANHANG.TEN, 'na') as GSBH, isnull(ASM.ten, 'na') as ASM, isnull(BM.ten, 'na') as BM, " +
							"VUNG.TEN as vung, KHUVUC.ten as khuvuc " +
					"from " +
						"( " +
							"select a.pk_seq as nppId, a.ma, a.ten, a.diachi, a.khuvuc_fk, case b.trangthai when 0 then N'Chưa duyệt' else N'Đã duyệt' end as trangthai, " +
								"c.chitieu, d.ma as SKUCode, d.ten as spTen, d.pk_seq as spId " +
							"from nhaphanphoi a inner join CHITIEUSKUIN b on a.pk_seq = b.nhapp_fk  inner join CHITIEUSKUIN_SKU c on c.chitieuskuin_fk = b.pk_seq " +
							"inner join sanpham d on c.sku = d.pk_seq " +
							"where b.thang = '" + thang + "' and b.nam = '" + ctskuBean.getNam() + "' and a.pk_seq in (" + ctskuBean.getNppIds() + ") " +
						") CHITIEU " +
					"left join " +
						"( " +
							"select a.npp_fk, b.sanpham_fk as SKUCode, (sum(b.soluong) * d.soluong1) / d.soluong2  as soluongNhap  " +
							"from nhaphang a inner join nhaphang_sp b on a.pk_seq = b.nhaphang_fk " +
											"inner join sanpham c on b.sanpham_fk = c.ma inner join quycach d on c.pk_seq = d.sanpham_fk " +
							"where a.npp_fk in (" + ctskuBean.getNppIds() + ") and substring(a.ngaynhan, 0, 5) = '" + ctskuBean.getNam() + "' and " +
									" substring(ngaynhan, 6, 2) = '" + thang + "' and a.trangthai = '1' ";
					
					if(ctskuBean.getNspId().length() > 0)
						query += " and b.sanpham_fk in " +
								"( select ma from sanpham " +
								" where pk_seq in (select sp_fk from nhomsanpham_sanpham where nsp_fk = '" + ctskuBean.getNspId() + "' ) ) ";
		
					query += " group by a.npp_fk, b.sanpham_fk, d.soluong1, d.soluong2 " +
						") SKUIN on CHITIEU.nppId = SKUIN.npp_fk and CHITIEU.SKUCode = SKUIN.SKUCode " +
					"inner join nhapp_giamsatbh on nhapp_giamsatbh.npp_fk = CHITIEU.nppId " +
					"inner join  giamsatbanhang on nhapp_giamsatbh.gsbh_fk = giamsatbanhang.pk_seq " +
					"inner join KHUVUC on CHITIEU.khuvuc_fk = KHUVUC.pk_seq " +
					"left join ASM_KHUVUC on ASM_KHUVUC.khuvuc_fk = KHUVUC.pk_seq " +
					"left join ASM on ASM_KHUVUC.asm_fk = ASM.pk_seq " +
					"inner join VUNG on khuvuc.vung_fk = VUNG.pk_seq " +
					"left join BM_CHINHANH on BM_CHINHANH.VUNG_FK = VUNG.pk_seq " +
					"left join BM on BM_CHINHANH.BM_fk = BM.PK_SEQ " +
					"order by chitieu.nppId asc";
	
		
		System.out.println("1.Chi tieu SKU: " + query);
		ResultSet rs = db.get(query);
		
		int i = 2;
		if(rs!=null)
		{
			try 
			{
				for(int j = 0; j < 15; j++)
					cells.setColumnWidth(i, 15.0f);
				
				Cell cell = null;
				while(rs.next())
				{
					String BM = rs.getString("BM");
					String vung = rs.getString("vung");
					String ASM = rs.getString("ASM");
					String khuvuc = rs.getString("khuvuc");
					String gsbh = rs.getString("GSBH");
					String maNPP = rs.getString("ma");
					String tenNPP = rs.getString("ten");
					String maSP = rs.getString("SKUCODE");
					String tenSP = rs.getString("spTen");
					int chitieu = rs.getInt("chitieu");										
					int thucdat = rs.getInt("thucdat");
					float phantram = rs.getFloat("phantram");	
            		String trangthai = rs.getString("trangthai");
            		
					
					cell = cells.getCell("AA" + Integer.toString(i)); 	cell.setValue(BM);
					cell = cells.getCell("AB" + Integer.toString(i)); 	cell.setValue(vung);
					cell = cells.getCell("AC" + Integer.toString(i)); 	cell.setValue(ASM);
					cell = cells.getCell("AD" + Integer.toString(i)); 	cell.setValue(khuvuc);
					cell = cells.getCell("AE" + Integer.toString(i)); 	cell.setValue(gsbh);
					cell = cells.getCell("AF" + Integer.toString(i)); 	cell.setValue(maNPP);				
					cell = cells.getCell("AG" + Integer.toString(i)); 	cell.setValue(tenNPP);
					cell = cells.getCell("AH" + Integer.toString(i)); 	cell.setValue(maSP);
					cell = cells.getCell("AI" + Integer.toString(i)); 	cell.setValue(tenSP);
					cell = cells.getCell("AJ" + Integer.toString(i)); 	cell.setValue(chitieu); 
					cell = cells.getCell("AK" + Integer.toString(i)); 	cell.setValue(thucdat); 
					cell = cells.getCell("AL" + Integer.toString(i)); 	cell.setValue(phantram); 
					cell = cells.getCell("AM" + Integer.toString(i)); 	cell.setValue(trangthai);
			
					
					i++;
				}
				if(rs!=null)
					rs.close();
				if(db != null) 
					db.shutDown();
				if(i==2){
					throw new Exception("Khong co bao cao trong thoi gian nay...");
				}
			
			} 
			catch (Exception e) {}
		} 
		else 
		{
			if(db != null) db.shutDown();
			return false;
		}
		
		if(db != null) 
			db.shutDown();
		return true;
	}


}
