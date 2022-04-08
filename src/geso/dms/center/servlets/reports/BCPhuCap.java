package geso.dms.center.servlets.reports;

import geso.dms.center.beans.khoahuanluyen.IKhoahuanluyenList;
import geso.dms.center.beans.khoahuanluyen.imp.KhoahuanluyenList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.distributor.util.Utility;

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
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class BCPhuCap extends HttpServlet
{
	private static final long serialVersionUID = 1L;

    public BCPhuCap() {
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
		String nextJSP = "/AHF/pages/Center/BCPhuCap.jsp";
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
		
		String thang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thang"));
		if(thang == null)
			thang = "";
		obj.setTungay(thang);
		
		String nam = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nam"));
		if(nam == null)
			nam = "";
		obj.setDenngay(nam);
		
		obj.setKbhId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId"))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")):""));
		
		obj.setVungId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")):""));
			
		obj.setKvId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId"))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")):""));
		
		obj.setNppId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")):""));
		
		String muclay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("muclay"));
		if(muclay == null)
			muclay = "0";

		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action") != null? util.antiSQLInspection(request.getParameter("action"))) : "";
		String nextJSP = "/AHF/pages/Center/BCPhuCap.jsp";
		
		if(action.equals("Taomoi"))
		{
			try
			{
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=PhuCapNhanVien.xlsm");
		        if(!CreatePivotTable(out, obj, muclay))
		        {
		        	obj.setMsg("Không có dữ liệu trong thời gian này.");
		        	obj.initBC("");
		    		session.setAttribute("obj", obj);	
		    		response.sendRedirect(nextJSP);
		    		return;
		        }
		        
			}
			catch(Exception ex)
			{
				obj.setMsg(ex.getMessage());
			}
		}
	
		obj.initBC("");
		session.setAttribute("obj", obj);	
		response.sendRedirect(nextJSP);
	}
	
	private boolean CreatePivotTable(OutputStream out, IKhoahuanluyenList obj, String muclay) throws Exception 
	{
		String chuoi = "";
		if(muclay.equals("0"))
			chuoi = getServletContext().getInitParameter("path") + "\\PhuCap.xlsm";
		else
			chuoi = getServletContext().getInitParameter("path") + "\\PhuCap_GSBH.xlsm";
		
		FileInputStream fstream = new FileInputStream(chuoi);
			
		Workbook workbook = new Workbook();
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateStaticHeader(workbook, obj, muclay);

		boolean isFill = CreateStaticData(workbook, obj, muclay);

		if (!isFill){
			fstream.close();
			return false;
		}else {
			workbook.save(out);
			fstream.close();
			return true;
		}
	}
	
	private void CreateStaticHeader(Workbook workbook, IKhoahuanluyenList obj, String muclay) throws Exception 
	{
		try
		{	
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");

			Cells cells = worksheet.getCells();

			cells.setRowHeight(0, 20.0f);
			Cell cell = cells.getCell("A1");
			
			if(muclay.equals("0"))
			{
				cell.setValue("BÁO CÁO PHỤ CẤP CỦA NHÂN VIÊN BÁN HÀNG");   	
			}
			else
			{
				cell.setValue("BÁO CÁO PHỤ CẤP CỦA GIÁM SÁT BÁN HÀNG"); 
			}
			
		    cells.setRowHeight(4, 18.0f);
		    cell = cells.getCell("A2");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
		    
		    cells.setRowHeight(5, 18.0f);
		    cell = cells.getCell("A3");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getUserTen());
			
		   
		    if(muclay.equals("0"))
		    {
		    	cell = cells.getCell("DA1"); 	cell.setValue("Vung");  ReportAPI.setCellHeader(cell);
				cell = cells.getCell("DB1"); 	cell.setValue("KhuVuc");  ReportAPI.setCellHeader(cell);
				cell = cells.getCell("DC1"); 	cell.setValue("NhaPhanPhoi");  ReportAPI.setCellHeader(cell);
		    	cell = cells.getCell("DD1"); 	cell.setValue("DaiDienKinhDoanh");  ReportAPI.setCellHeader(cell);
		    	cell = cells.getCell("DE1"); 	cell.setValue("DienThoai");  ReportAPI.setCellHeader(cell);
			    cell = cells.getCell("DF1"); 	cell.setValue("CTNgayLamViec");  ReportAPI.setCellHeader(cell);
			    cell = cells.getCell("DG1"); 	cell.setValue("TDNgayLamViec");  ReportAPI.setCellHeader(cell);
			    cell = cells.getCell("DH1"); 	cell.setValue("TieuChi");  ReportAPI.setCellHeader(cell);
			    cell = cells.getCell("DI1"); 	cell.setValue("PhuCap");  ReportAPI.setCellHeader(cell);
		    }
		    else
		    {
		    	cell = cells.getCell("DA1"); 	cell.setValue("Vung");  ReportAPI.setCellHeader(cell);
				cell = cells.getCell("DB1"); 	cell.setValue("KhuVuc");  ReportAPI.setCellHeader(cell);
		    	cell = cells.getCell("DC1"); 	cell.setValue("GiamSatBanHang");  ReportAPI.setCellHeader(cell);
		    	cell = cells.getCell("DD1"); 	cell.setValue("DienThoai");  ReportAPI.setCellHeader(cell);
			    cell = cells.getCell("DE1"); 	cell.setValue("CTNgayLamViec");  ReportAPI.setCellHeader(cell);
			    cell = cells.getCell("DF1"); 	cell.setValue("TDNgayLamViec");  ReportAPI.setCellHeader(cell);
			    cell = cells.getCell("DG1"); 	cell.setValue("TieuChi");  ReportAPI.setCellHeader(cell);
			    cell = cells.getCell("DH1"); 	cell.setValue("PhuCap");  ReportAPI.setCellHeader(cell);
		    }
		}
		catch(Exception ex)
		{
			throw new Exception("Bao cao bi loi khi khoi tao");

		}
	}
	
	private boolean CreateStaticData(Workbook workbook, IKhoahuanluyenList obj, String muclay) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
		
	    String query = "";
	    
	    String thang = obj.getTungay();
    	if(thang.length() < 2)
    		thang = "0" + thang;
    	String ngaydh = obj.getDenngay() + "-" + thang;
    	
    	System.out.println("____Muc lay la: " + muclay);
    	
	    if(muclay.equals("0")) //Lay theo Dai Dien Kinh Doanh
	    {
	    	query = "select v.TEN as vungTen, kv.TEN as kvTen, npp.TEN as nppTen, ddkd.TEN as ddkdTen, isnull(ddkd.DienThoai, 'NA') as DienThoai, chitieu.ddkdId, chitieu.nlvChitieu,   " +
	    			"thucdatdonhang.nlvThucte, isnull(TongPhuCap.ma, '') as TieuChi, isnull(TongPhuCap.TRONGSO, '0') as tienphucap,  isnull(TongPhuCap.tinhtheongaycong, '0') as tinhtheoNC  " +
	    		"from   " +
	    		"(  " +
	    			"select b.ddkd_fk as ddkdId, a.songaylamviec as nlvChitieu  " +
	    			"from chitieunpp a inner join  Chitieunpp_ddkd b on a.pk_seq = b.chitieunpp_fk   " +
	    			"where a.thang = '" + obj.getTungay() + "' and a.nam = '" + obj.getDenngay() + "' and a.trangthai = '1'   " +
	    			"group by b.ddkd_fk, a.songaylamviec   " +
	    		")   " +
	    		"chitieu inner join   " +
	    		"(  " +
	    			"select a.ddkd_fk as ddkdId, count(distinct a.ngaynhap) as nlvThucte  " +
	    			"from donhang a inner join nhaphanphoi b on a.npp_fk = b.pk_seq   " +
	    			"where a.ngaynhap like '" + ngaydh + "%' and a.trangthai = '1'  " +
	    			"group by b.khuvuc_fk, a.ddkd_fk  " +
	    		")   " +
	    		"thucdatdonhang on chitieu.ddkdId = thucdatdonhang.ddkdId   " +
	    		"inner join DAIDIENKINHDOANH ddkd on thucdatdonhang.ddkdId = ddkd.PK_SEQ  " +
	    		"inner join NHAPHANPHOI npp on ddkd.NPP_FK = npp.PK_SEQ    " +
	    		"inner join KHUVUC kv on npp.KHUVUC_FK = kv.PK_SEQ   " +
	    		"inner join VUNG v on kv.VUNG_FK = v.PK_SEQ   " +
	    		"inner join " +
	    		"( " +
	    			"select ddkd.ddkd_fk, tieuchiTNC.MA, tieuchiTNC.TRONGSO, tieuchiTNC.tinhtheongaycong  " +
	    			"from " +
	    			"( " +
	    				"select TINHPHUCAP_CHUCVU_FK, ddkd_fk   " +
	    				"from TINHPHUCAP_CHUCVU_DDKD " +
	    				"where TINHPHUCAP_CHUCVU_FK in (select pk_seq from TINHPHUCAP_CHUCVU where tinhphucap_fk in ( select PK_SEQ from TinhPhuCap where thang = '" + obj.getTungay() + "' and nam = '" + obj.getDenngay() + "' and trangthai = '1' ))   " +
	    			") " +
	    			"ddkd inner join " +
	    			"( " +
	    				"select TINHPHUCAP_CHUCVU_FK, MA, TRONGSO, tinhtheongaycong " +
	    				"from TINHPHUCAP_CHUCVU_PHUCAP  " +
	    				"where tinhtheongaycong = '1' and TINHPHUCAP_CHUCVU_FK in (select pk_seq from TINHPHUCAP_CHUCVU where tinhphucap_fk in ( select PK_SEQ from TinhPhuCap where thang = '" + obj.getTungay() + "' and nam = '" + obj.getDenngay() + "' and trangthai = '1' )) " +
	    			") " +
	    			"tieuchiTNC on tieuchiTNC.TINHPHUCAP_CHUCVU_FK = ddkd.tinhphucap_chucvu_fk  " +
	    		"union  " +
	    			"select daidienkinhdoanh.ddkd_fk, tieuchiKoTNC.MA, tieuchiKoTNC.TRONGSO, tieuchiKoTNC.tinhtheongaycong " +
	    			"from  " +
	    			"( " +
	    				"select TINHPHUCAP_CHUCVU_FK, ddkd_fk   " +
	    				"from TINHPHUCAP_CHUCVU_DDKD  " +
	    				"where TINHPHUCAP_CHUCVU_FK in (select pk_seq from TINHPHUCAP_CHUCVU where tinhphucap_fk in ( select PK_SEQ from TinhPhuCap where thang = '" + obj.getTungay() + "' and nam = '" + obj.getDenngay() + "' and trangthai = '1' ))   " +
	    			") " +
	    			"daidienkinhdoanh inner join " +
	    			"( " +
	    				"select TINHPHUCAP_CHUCVU_FK, MA, TRONGSO, tinhtheongaycong " +
	    				"from TINHPHUCAP_CHUCVU_PHUCAP  " +
	    				"where tinhtheongaycong = '0' and TINHPHUCAP_CHUCVU_FK in (select pk_seq from TINHPHUCAP_CHUCVU where tinhphucap_fk in ( select PK_SEQ from TinhPhuCap where thang = '" + obj.getTungay() + "' and nam = '" + obj.getDenngay() + "' and trangthai = '1' ))  " +
	    			") " +
	    			"tieuchiKoTNC on tieuchiKoTNC.TINHPHUCAP_CHUCVU_FK = daidienkinhdoanh.tinhphucap_chucvu_fk  " +
	    		")  " +
	    		"TongPhuCap on ddkd.PK_SEQ = TongPhuCap.ddkd_fk";
	    }
	    else  //Lay theo GSBH
	    {
	    	query = "select v.TEN as vungTen, isnull(kv.TEN, 'NA') as kvTen,  gsbh.TEN as gsbhTen, isnull(gsbh.DienThoai, 'NA') as DienThoai, chitieu.gsbhId, chitieu.nlvChitieu, " +
	    				"thucdatdonhang.nlvThucte, isnull(phucap.ma, '') as TieuChi, isnull(phucap.TRONGSO, '0') as tienphucap, ISNULL(phucap.tinhtheongaycong, '0') as tinhtheoNC   " +
	    			"from " +
	    			"( " +
	    				"select  c.gsbh_fk as gsbhId, sum(a.songaylamviec) / count(b.ddkd_fk) as nlvChitieu  " +
	    				"from chitieunpp a inner join  Chitieunpp_ddkd b on a.pk_seq = b.chitieunpp_fk  " +
	    						"inner join ddkd_gsbh c on b.ddkd_fk = c.ddkd_fk  " +
	    				"where a.thang = '" + obj.getTungay() + "' and a.nam = '" + obj.getDenngay() + "' and a.trangthai = '1'  " +
	    				"group by c.gsbh_fk  " +
	    			") " +
	    			"chitieu inner join  " +
	    			"(  " +
	    				"select gsbhId, sum(nlvThucte) / COUNT(distinct ddkdId) as nlvThucte   " +
	    				"from " +
	    				"( " +
	    					"select gsbh_fk as gsbhId, ddkd_fk as ddkdId, count(distinct ngaynhap) as nlvThucte  " +
	    					"from donhang " +
	    					"where ngaynhap like '" + ngaydh + "%' and trangthai = '1'  " +
	    					"group by gsbh_fk, ddkd_fk  " +
	    				") bsbh group by gsbhId  " +
	    			")  " +
	    			"thucdatdonhang on chitieu.gsbhId = thucdatdonhang.gsbhId   " +
	    				"inner join GIAMSATBANHANG gsbh on thucdatdonhang.gsbhId = gsbh.PK_SEQ  " +
	    				"inner join KHUVUC kv on gsbh.KHUVUC_FK = kv.PK_SEQ  " +
	    				"inner join VUNG v on kv.VUNG_FK = v.PK_SEQ  " +
	    				"inner join  " +
	    				"( " +
	    					"select gsbh.gsbh_fk, tieuchiTNC.MA, tieuchiTNC.TRONGSO, tieuchiTNC.tinhtheongaycong  " +
	    					"from " +
	    					"( " +
	    						"select TINHPHUCAP_CHUCVU_FK, gsbh_fk    " +
	    						"from TINHPHUCAP_CHUCVU_GSBH  " +
	    						"where TINHPHUCAP_CHUCVU_FK in (select pk_seq from TINHPHUCAP_CHUCVU where tinhphucap_fk in ( select PK_SEQ from TinhPhuCap where thang = '" + obj.getTungay() + "' and nam = '" + obj.getDenngay() + "' and trangthai = '1' ))  " +
	    					") " +
	    					"gsbh inner join " +
	    					"( " +
	    						"select TINHPHUCAP_CHUCVU_FK, MA, TRONGSO, tinhtheongaycong  " +
	    						"from TINHPHUCAP_CHUCVU_PHUCAP   " +
	    						"where tinhtheongaycong = '1' and TINHPHUCAP_CHUCVU_FK in (select pk_seq from TINHPHUCAP_CHUCVU where tinhphucap_fk in ( select PK_SEQ from TinhPhuCap where thang = '" + obj.getTungay() + "' and nam = '" + obj.getDenngay() + "' and trangthai = '1' )) " +
	    					") " +
	    					"tieuchiTNC on tieuchiTNC.TINHPHUCAP_CHUCVU_FK = gsbh.tinhphucap_chucvu_fk   " +
	    				"union  " +
	    					"select gsbh.gsbh_fk, tieuchiKoTNC.MA, tieuchiKoTNC.TRONGSO, tieuchiKoTNC.tinhtheongaycong  " +
	    					"from  " +
	    					"( " +
	    						"select TINHPHUCAP_CHUCVU_FK, gsbh_fk    " +
	    						"from TINHPHUCAP_CHUCVU_GSBH  " +
	    						"where TINHPHUCAP_CHUCVU_FK in (select pk_seq from TINHPHUCAP_CHUCVU where tinhphucap_fk in ( select PK_SEQ from TinhPhuCap where thang = '" + obj.getTungay() + "' and nam = '" + obj.getDenngay() + "' and trangthai = '1' ))   " +
	    					") " +
	    					"gsbh inner join  " +
	    					"(  " +
	    						"select TINHPHUCAP_CHUCVU_FK, MA, TRONGSO, tinhtheongaycong  " +
	    						"from TINHPHUCAP_CHUCVU_PHUCAP   " +
	    						"where tinhtheongaycong = '0' and TINHPHUCAP_CHUCVU_FK in (select pk_seq from TINHPHUCAP_CHUCVU where tinhphucap_fk in ( select PK_SEQ from TinhPhuCap where thang = '" + obj.getTungay() + "' and nam = '" + obj.getDenngay() + "' and trangthai = '1' ))   " +
	    					")  " +
	    					"tieuchiKoTNC on tieuchiKoTNC.TINHPHUCAP_CHUCVU_FK = gsbh.tinhphucap_chucvu_fk  " +
	    				")  " +
	    				"PhuCap on gsbh.PK_SEQ = PhuCap.gsbh_fk	  ";
		
	    }
	    
	    if(obj.getVungId().length() > 0)
	    	query += " and v.pk_seq = '" + obj.getVungId() + "' ";
	    
	    if(obj.getKvId().length() > 0)
	    	query += " and kv.pk_seq = '" + obj.getKhlId() + "' ";
	    
	
		System.out.println("1.Tinh phu cap: " + query);
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
					if(muclay.equals("0")) //DDKD
					{
						String vung = rs.getString("vungTen");
						String khuvuc = rs.getString("kvTen");
						String nppTen = rs.getString("nppTen");
						String ten = rs.getString("ddkdTen");
						String dienthoai = rs.getString("DienThoai");
						
						double nlvChitieu = rs.getDouble("nlvChitieu");
						double nlvThucte = rs.getDouble("nlvThucte");
						
						String tieuchi = rs.getString("TieuChi");
						
						
						double phucap = rs.getDouble("tienphucap");
						if(rs.getString("tinhtheoNC").equals("1"))
						{
							phucap = phucap * nlvThucte / nlvChitieu;
						}
						
						cell = cells.getCell("DA" + Integer.toString(i)); 	cell.setValue(vung);
						cell = cells.getCell("DB" + Integer.toString(i)); 	cell.setValue(khuvuc);
						cell = cells.getCell("DC" + Integer.toString(i)); 	cell.setValue(nppTen);
						cell = cells.getCell("DD" + Integer.toString(i)); 	cell.setValue(ten);
						cell = cells.getCell("DE" + Integer.toString(i)); 	cell.setValue(dienthoai);
						cell = cells.getCell("DF" + Integer.toString(i)); 	cell.setValue(nlvChitieu);
						cell = cells.getCell("DG" + Integer.toString(i)); 	cell.setValue(nlvThucte);
						cell = cells.getCell("DH" + Integer.toString(i)); 	cell.setValue(tieuchi);
						cell = cells.getCell("DI" + Integer.toString(i)); 	cell.setValue(phucap);
						
					}
					else //GSBH
					{
						String vung = rs.getString("vungTen");
						String khuvuc = rs.getString("kvTen");
						String ten = rs.getString("gsbhTen");
						String dienthoai = rs.getString("dienthoai");
						
						double nlvChitieu = rs.getDouble("nlvChitieu");
						double nlvThucte = rs.getDouble("nlvThucte");
						
						String tieuchi = rs.getString("TieuChi");
						
						double phucap = rs.getDouble("tienphucap");
						if(rs.getString("tinhtheoNC").equals("1"))
						{
							phucap = phucap * nlvThucte / nlvChitieu;
						}
						
						
						cell = cells.getCell("DA" + Integer.toString(i)); 	cell.setValue(vung);
						cell = cells.getCell("DB" + Integer.toString(i)); 	cell.setValue(khuvuc);
						cell = cells.getCell("DC" + Integer.toString(i)); 	cell.setValue(ten);
						cell = cells.getCell("DD" + Integer.toString(i)); 	cell.setValue(dienthoai);
						cell = cells.getCell("DE" + Integer.toString(i)); 	cell.setValue(nlvChitieu);
						cell = cells.getCell("DF" + Integer.toString(i)); 	cell.setValue(nlvThucte);
						cell = cells.getCell("DG" + Integer.toString(i)); 	cell.setValue(tieuchi);
						cell = cells.getCell("DH" + Integer.toString(i)); 	cell.setValue(phucap);
					}
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
			catch (Exception e) 
			{
				System.out.println("115.Error: " + e.getMessage());
				return false;
			}
		} 
		else 
		{
			if(db != null) 
				db.shutDown();
			return false;
		}
		
		if(db != null) 
			db.shutDown();
		return true;
	}	

}
