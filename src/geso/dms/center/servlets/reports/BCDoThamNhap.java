package geso.dms.center.servlets.reports;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
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

/*
 Thiet lap 3 function cho thuc hien viec lay doanh so 
 theo cac muc khach nhau.... 
 */

public class BCDoThamNhap extends HttpServlet {
		
	private static final long serialVersionUID = 1L;

	public BCDoThamNhap() {
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
		
		
		obj.init();
		
		String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
		if(view == null)
			view = "";
		
		obj.setLoaiMenu(view);
		
		Utility ut = new Utility();
		
		geso.dms.distributor.db.sql.dbutils db = new geso.dms.distributor.db.sql.dbutils();
		ut.getUserInfo((String) session.getAttribute("userId"), db);
		db.shutDown();
		if(ut.getLoaiNv().equals("2"))
			obj.setLoaiMenu("NPP");
		session.setAttribute("obj", obj);
		String nextJSP = "/AHF/pages/Center/BCDoThamNhap.jsp";
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
		
		
		
		String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
		System.out.println("loaiMenu = " + view);
		if(view == null)
			view = "";
		
		obj.setLoaiMenu(view);
		
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		
		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");
		Utility util = new Utility();
		
		obj.settungay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays"))));
		obj.setdenngay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays"))));
		
		
		
		String[] vungid = request.getParameterValues("vungId");
		String s = "";
		if(vungid != null)
		{
			
			for(int i = 0; i < vungid.length; i++)
			{
				s += vungid[i] +",";
			}
			if(s.length() > 0)
			{
				s = s.substring(0,s.length() -1);
			}
		
			
		}
		System.out.println("VUng: "+s);
		obj.setvungId(s);
		String[] khuvucId = request.getParameterValues("khuvucId");
		s = "";
		if(khuvucId != null)
		{
			
			for(int i = 0; i < khuvucId.length; i++)
			{
				s += khuvucId[i] +",";
			}
			if(s.length() > 0)
			{
				s = s.substring(0,s.length() -1);
			}
		
			
		}
		obj.setkhuvucId(s);
		String[] nppId = request.getParameterValues("nppId");
		 s = "";
		if(nppId != null)
		{
			
			for(int i = 0; i < nppId.length; i++)
			{
				s += nppId[i] +",";
			}
			if(s.length() > 0)
			{
				s = s.substring(0,s.length() -1);
			}
		
		}
		
		
		String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));		    
		if(trangthai == null)
			trangthai = "";	    	
		
		String chonid = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chonid")));		    
		if(chonid == null)
			chonid = "";	
		
		String nhomspid = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhomspid")));		    
		if(nhomspid == null)
			nhomspid = "";
		String nhanhangId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhanhangId")));		    
		if(nhanhangId == null)
			nhanhangId = "";
		String chungloaiId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chungloaiId")));		    
		if(chungloaiId == null)
			chungloaiId = "";
		String nhomskusId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhomskusId")));		    
		if(nhomskusId == null)
			nhomskusId = "";
		
		
		obj.setnhanhangId(nhanhangId);
		obj.setNhomskusId(nhomskusId);
		obj.setchungloaiId(chungloaiId);
		obj.SetNhoSPId(nhomspid);
		
		
		geso.dms.center.util.Utility Uti_center = new geso.dms.center.util.Utility();
		Utility ut = new Utility();
		geso.dms.distributor.db.sql.dbutils db = new geso.dms.distributor.db.sql.dbutils();
		ut.getUserInfo(userId, db);
		db.shutDown();
		if(ut.getLoaiNv().equals("2"))
			obj.setLoaiMenu("NPP");
		
		if(s.length() <= 0)
		{
			s = " select pk_seq from nhaphanphoi where 1 = 1 ";
			
			
		
			if(obj.getkhuvucId().length() > 0)
			{
				s+= " and khuvuc_fk in ("+obj.getkhuvucId()+") ";
			}
			
			if(view.equals("NPP"))
			{
				
				if(ut.getLoaiNv().equals("3"))
					s+= " and pk_seq in "+util.quyen_npp(userId)+" ";
				else
				{
					String nppid = ut.getIdNhapp(userId);
					s+= " and pk_seq in ("+nppid+") ";
				}
			
			}
			
			s+=" and a.pk_seq in " + Uti_center.quyen_npp(obj.getuserId()) +"";
			if(obj.getvungId().length() > 0)
			{
				s+= " and khuvuc_fk in (select pk_seq from khuvuc where vung_fk in ("+obj.getvungId()+") )";
			}
		}
		
		obj.setnppId(s);
		String action = (String) util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")));
		String nextJSP = "/AHF/pages/Center/BCDoThamNhap.jsp";
		
		System.out.println("Action la: " + action);
		
		try{
			if (action.equals("Taomoi")) 
			{			
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BCDoThamNhap.xlsm");
				Workbook workbook = new Workbook();
				TaoBaoCao(workbook, userTen,obj.gettungay(),obj.getdenngay(),obj.getnppId(),trangthai,chonid,nhomspid,nhanhangId,chungloaiId,nhomskusId);
				
				workbook.save(out);	
			}
			else
			{
				obj.init();
			session.setAttribute("obj", obj);
				response.sendRedirect(nextJSP);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			obj.init();
			obj.setMsg("Lỗi không tạo được báo cáo ! Vui Lòng kiểm tra lại");
			response.sendRedirect(nextJSP);
		}
		
		session.setAttribute("obj", obj);	
	}	
	
	
	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	private void TaoBaoCao(Workbook workbook,String nguoitao,String tungay,String denngay,String nppid, String trangthai,String chonid,String  nhomspid,String  nhanhangId,String  chungloaiId,String nhomskusId  )throws Exception
	{
		try{
			
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("TheoDoiDMS");
			dbutils db = new dbutils();
			Cells cells = worksheet.getCells();
			cells.setRowHeight(0, 50.0f);
			Cell cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell, Color.RED, true, 16,"Báo Cáo Độ Thâm Nhập");

			cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Ngày tạo : "+ getDateTime());
			
			cell = cells.getCell("A2");
			cells.setColumnWidth(0, 20.0f);
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Từ ngày: "+ tungay);
			cell = cells.getCell("B2");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Đến ngày: "+ denngay);
			cells.setColumnWidth(4, 20.0f);
			cells.setColumnWidth(9, 15.0f);
			cells.setColumnWidth(8, 15.0f);
			cells.setColumnWidth(7, 15.0f);
			cells.setColumnWidth(6, 15.0f);
			cell = cells.getCell("A4");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10,"Người tạo : " + nguoitao);
		
			String tt="";
			System.out.println("Trang thai "+trangthai);
			System.out.println("Trang thai22 "+chonid);
			if(trangthai.trim().length()>0)
			{
				if(trangthai.equals("0"))
					tt=" dh.TRANGTHAI <>  1 and dh.TRANGTHAI <>  2" ;
				else
					tt=" dh.TRANGTHAI =  1" ;
				
			}
			else
				tt="dh.TRANGTHAI <> 2";
			
			
			String query = 
					 "\n 		 IF OBJECT_ID('tempdb.dbo.#TBLDD') IS NOT NULL " 
					 +"\n 		 DROP TABLE #TBLDD  " 
					 +"\n 		 CREATE TABLE #TBLDD ( " 
					 +"\n 		 NVBH nvarchar(200),KenhBH nvarchar(200), trangthai nvarchar(200), Vung nvarchar(200) " 
					 +"\n 			 ,KhuVuc   nvarchar(200),NPP  nvarchar(200),PK_SEQ numeric(18,0), ASO float, AO float, VPO float " 
					 +"\n  " 
					 +"\n 			)  " ;
					 db.update(query);
					System.out.println("QR1: "+query);
					 
					
					//////////////// mức lấy
					//khách hàng
					String AO = "";
					
					String DS = "";
					 if(chonid.equals("0"))
					 {
						 AO =  "\n   isnull((select COUNT(distinct dh.KHACHHANG_FK) from DONHANG dh"
					 +"\n 		  where "+tt+" and dh.ddkd_fk = kd.PK_SEQ"
					 +"\n 		  and dh.NGAYNHAP >= '"+tungay+"' and  dh.NGAYNHAP <= '"+denngay+"'"
					 +"\n 		  ),0)	  ";
						 
						 DS = "\n isnull((select SUM(dhsp.soluong*giamua) from DONHANG dh inner join "
								 +"\n  DONHANG_SANPHAM dhsp on dh.PK_SEQ = dhsp.DONHANG_FK where "+tt+" and dh.ddkd_fk = kd.PK_SEQ"
								 +"\n  and dh.NGAYNHAP >= '"+tungay+"' and  dh.NGAYNHAP <= '"+denngay+"'"
								 +"\n  ),0) " ;
					 }
					 
					 //Sản phẩm
					 if(chonid.equals("1"))
					 {
						 AO =   "\n   isnull((select COUNT(distinct dh.KHACHHANG_FK) from DONHANG dh inner join donhang_sanpham b on dh.pk_seq = b.donhang_fk "
					 +"\n 		  where "+tt+" and dh.ddkd_fk = kd.PK_SEQ"
					 +"\n 		  and dh.NGAYNHAP >= '"+tungay+"' and  dh.NGAYNHAP <= '"+denngay+"'"
					 +"\n 		  ),0) 	  ";
					 }
					 
					 String dk = "";
					 //Nhóm sản phẩm
					 if(chonid.equals("2"))
					 {
						 
						 if(nhomspid.length() > 0)
						 {
							 dk = " and c.nsp_fk = '"+nhomspid+"' ";
						 }
						 
						 AO =   "\n   isnull((select COUNT(distinct dh.KHACHHANG_FK) from DONHANG dh inner join donhang_sanpham b on dh.pk_seq = b.donhang_fk inner join nhomsanpham_sanpham c on c.sp_fk = b.sanpham_fk"
					 +"\n 		  where "+tt+" and dh.ddkd_fk = kd.PK_SEQ"
					 +"\n 		  and dh.NGAYNHAP >= '"+tungay+"' and  dh.NGAYNHAP <= '"+denngay+"'" + dk
					 +"\n 		  ),0) 	  ";
						
						 DS = "\n isnull((select SUM(dhsp.soluong*giamua) from DONHANG dh inner join "
							 +"\n  DONHANG_SANPHAM dhsp on dh.PK_SEQ = dhsp.DONHANG_FK" 
							 +"\n  inner join nhomsanpham_sanpham c on c.sp_fk = dhsp.sanpham_fk  "
							 +"\n where "+tt+" and dh.ddkd_fk = kd.PK_SEQ"
							 +"\n  and dh.NGAYNHAP >= '"+tungay+"' and  dh.NGAYNHAP <= '"+denngay+"'" + dk
							 +"\n  ),0) " ;
						
					 }
					 
					 //Nhãn Hàng
					 if(chonid.equals("3"))
					 {
						 
						 if(nhanhangId.length() > 0)
						 {
							 dk = " and d.pk_seq = '"+nhanhangId+"' ";
						 }
						 
						 AO =   "\n   isnull((select COUNT(distinct dh.KHACHHANG_FK) from DONHANG dh inner join donhang_sanpham b on dh.pk_seq = b.donhang_fk inner join sanpham c on c.pk_seq = b.sanpham_fk inner join nhanhang d on d.pk_seq = c.nhanhang_fk "
					 +"\n 		  where "+tt+" and dh.ddkd_fk = kd.PK_SEQ"
					 +"\n 		  and dh.NGAYNHAP >= '"+tungay+"' and  dh.NGAYNHAP <= '"+denngay+"'" + dk 
					 +"\n 		  ),0) 	  ";
						 
						 
						 DS = "\n isnull((select SUM(dhsp.soluong*giamua) from DONHANG dh inner join "
							 +"\n  DONHANG_SANPHAM dhsp on dh.PK_SEQ = dhsp.DONHANG_FK "
							 +"\n  inner join sanpham c on c.pk_seq = dhsp.sanpham_fk inner join nhanhang d on d.pk_seq = c.nhanhang_fk   "
							 +"\n  where "+tt+" and dh.ddkd_fk = kd.PK_SEQ and dh.NGAYNHAP >= '"+tungay+"' and  dh.NGAYNHAP <= '"+denngay+"'" + dk
							 +"\n  ),0) " ;
						 
					 }
					 
					 //Chủng loại
					 if(chonid.equals("4"))
					 {
						 if(chungloaiId.length() > 0)
						 {
							 dk = " and d.pk_seq = '"+chungloaiId+"' ";
						 }
						 
						 
						 AO =   "\n   isnull((select COUNT(distinct dh.KHACHHANG_FK) from DONHANG dh inner join donhang_sanpham b on dh.pk_seq = b.donhang_fk inner join sanpham c on c.pk_seq = b.sanpham_fk inner join chungloai d on d.pk_seq = c.chungloai_fk "
					 +"\n 		  where "+tt+" and dh.ddkd_fk = kd.PK_SEQ"
					 +"\n 		  and dh.NGAYNHAP >= '"+tungay+"' and  dh.NGAYNHAP <= '"+denngay+"'" + dk 
					 +"\n 		  ),0) 	  ";
						 
						 DS = "\n isnull((select SUM(dhsp.soluong*giamua) from DONHANG dh inner join "
							 +"\n  DONHANG_SANPHAM dhsp on dh.PK_SEQ = dhsp.DONHANG_FK "
							 +"\n  inner join sanpham c on c.pk_seq = dhsp.sanpham_fk inner join chungloai d on d.pk_seq = c.chungloai_fk   "
							 +"\n  where "+tt+" and dh.ddkd_fk = kd.PK_SEQ and dh.NGAYNHAP >= '"+tungay+"' and  dh.NGAYNHAP <= '"+denngay+"'" + dk
							 +"\n  ),0) " ;
						 
					 }
					 
					//Nhom sku
					 if(chonid.equals("5"))
					 {
						 if(nhomskusId.length() > 0)
						 {
							 dk = " and C.NSKUS_FK = '"+nhomskusId+"' ";
						 }
						 
						 AO =   "\n   isnull((select COUNT(distinct dh.KHACHHANG_FK) from DONHANG dh inner join donhang_sanpham b on dh.pk_seq = b.donhang_fk inner join NHOMSKUS_SANPHAM C ON C.SP_FK = B.SANPHAM_FK "
					 +"\n 		  where "+tt+" and dh.ddkd_fk = kd.PK_SEQ"
					 +"\n 		  and dh.NGAYNHAP >= '"+tungay+"' and  dh.NGAYNHAP <= '"+denngay+"'"  + dk
					 +"\n 		  ),0) 	  ";
						 
						 DS = "\n isnull((select SUM(dhsp.soluong*giamua) from DONHANG dh inner join "
							 +"\n  DONHANG_SANPHAM dhsp on dh.PK_SEQ = dhsp.DONHANG_FK "
							 +"\n  inner join NHOMSKUS_SANPHAM C ON C.SP_FK = dhsp.SANPHAM_FK  "
							 +"\n  where "+tt+" and dh.ddkd_fk = kd.PK_SEQ and dh.NGAYNHAP >= '"+tungay+"' and  dh.NGAYNHAP <= '"+denngay+"'" + dk
							 +"\n  ),0) " ;
						
					 }
					
					
					////////////////
					 
						query = "\n  insert #TBLDD( NVBH,KenhBH, trangthai, Vung,KhuVuc,NPP,a.PK_SEQ, ASO, AO, VPO) " 
					 +"\n select kd.TEN as NVBH,kbh.ten as KenhBH, CASe when kd.TRANGTHAI = 0 then N'Ngung hoat dong' else 'Hoat dong' end as trangthai, v.TEN as Vung,kv.TEN as KhuVuc,a.TEN as NPP,a.PK_SEQ,"
					 +"\n isnull((select Count( distinct tbh.khachhang_fk) "
					 +"\n from khachhang dh inner join khachhang_tuyenbh tbh on tbh.khachhang_fk = dh.pk_seq " 
					 +"\n inner join TUYENBANHANG t on t.PK_SEQ = tbh.TBH_FK "
					 +"\n inner join DAIDIENKINHDOANH dd on dd.PK_SEQ = t.DDKD_FK "
					 +"\n  where dd.pk_seq = kd.PK_SEQ and dh.TRANGTHAI = 1 and dd.npp_fk = a.PK_SEQ "
					 +"\n and dh.pk_seq in (select distinct dhx.KHACHHANG_FK from DONHANG dhx"
							 +"\n 		  where dhx.trangthai = 1 and dhx.ddkd_fk = kd.PK_SEQ"
							 +"\n 		 and dhx.ngaynhap >= CONVERT(date,DATEADD(month, -2, dbo.GetLocalDate(DEFAULT))) "
							 +"\n 		  ) "
					 +"\n "
					 +"\n  ),0)  as ASO," + AO
					 + "\n 		 AO , dbo.PhepChia"
					 +"\n ( "+DS+" ,	"+AO+"    ) as VPO"
					 +"\n 	"
					 +"\n from DAIDIENKINHDOANH kd " +
					 " inner join NHAPHANPHOI a on kd.NPP_FK = a.PK_SEQ " +
					 " left join nhapp_kbh d on d.npp_fk = a.pk_seq " +
					 " inner join kenhbanhang kbh on a.kbh_fk = kbh.pk_seq "
					 +"\n left join KHUVUC kv on a.KHUVUC_FK = kv.PK_SEQ"
					 +"\n left join VUNG v on v.PK_SEQ = kv.VUNG_FK "
					 +"\n where 1= 1  and   a.PK_SEQ in ("+nppid+") and a.trangthai = 1" ;
					 db.update(query);
					System.out.println("QR1: "+query);
					 
						query = "\n select '' STT, Vung,KhuVuc,NPP, NVBH, KenhBH,TrangThai, SUM(ASO) ASO,SUM(AO) AO,dbo.PhepChia(SUM(AO),SUM(ASO))*100 as TyLe,AVG(a.VPO)VPO "
					 +"\n from "
					 +"\n #TBLDD a"
					 +"\n where a.Vung is not null "
					 +"\n group by Vung,KhuVuc,NPP, NVBH, trangthai, kenhBH ";
					 		
					 
				
			System.out.println("Get NVBH ko PDA: "+query);
			ResultSet rs = db.get(query);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();
			
			int countRow = 10;

			for( int i =1 ; i <=socottrongSql ; i ++  )
			{
				cell = cells.getCell(countRow,i-1 );cell.setValue(rsmd.getColumnName(i));
				cells.setColumnWidth(i, 20.0f);
				ReportAPI.setCellBackground(cell, new Color(70,130,180), BorderLineType.THIN, true, 0);	
			 
			}
			countRow ++;
			
			int stt = 0;
			while(rs.next())
			{
				
				stt++;
			
				for(int i =1;i <=socottrongSql ; i ++)
				{
					cell = cells.getCell(countRow,i-1 );
					if(rsmd.getColumnName(i).equals("STT"))
					{					
						cell.setValue(stt);
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
						//System.out.println("STT: "+stt);

					}else
					if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i) == Types.INTEGER || rsmd.getColumnType(i) == Types.DECIMAL)
					{
						cell.setValue(rs.getDouble(i));
						
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
					}
					else
					{
							
							
							cell.setValue(rs.getString(i));
							
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					}
				}
				++countRow;
			}
			
			
			db.update("IF OBJECT_ID('tempdb.dbo.#TBLDD') IS NOT NULL DROP TABLE #TBLDD");
			if(rs!=null)rs.close();
			if(db!=null){
				db.shutDown();
			}
			
	
		}catch(Exception ex){
			
			System.out.println("Errrorr : "+ex.toString());
			ex.printStackTrace();
			throw new Exception("Lỗi không tạo được báo cáo !");
		}
	}
	
}
