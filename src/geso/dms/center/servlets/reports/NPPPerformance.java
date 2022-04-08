package geso.dms.center.servlets.reports;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.util.Hashtable;

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


public class NPPPerformance extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public NPPPerformance() {
        super();
    }
	private Hashtable< Integer, String > htbValueCell (){
		Hashtable<Integer, String> htb=new Hashtable<Integer, String>();
		htb.put(1,"DA");htb.put(2,"DB");htb.put(3,"DC");htb.put(4,"DD");htb.put(5,"DE");
		htb.put(6,"DF");htb.put(7,"DG");htb.put(8,"DH");htb.put(9,"DI");htb.put(10,"DJ");
		htb.put(11,"DK");htb.put(12,"DL");htb.put(13,"DM");htb.put(14,"DN");htb.put(15,"DO");
		htb.put(16,"DP");htb.put(17,"DQ");htb.put(18,"DR");htb.put(19,"DS");htb.put(20,"DT");
		htb.put(21,"DU");htb.put(22,"DV");htb.put(23,"DW");htb.put(24,"DX");htb.put(25,"DY");
		htb.put(26,"DZ");htb.put(27,"EA");htb.put(28,"EB");htb.put(29,"EC");htb.put(30,"ED");
		htb.put(31,"EE");htb.put(32,"EF");htb.put(33,"EG");htb.put(34,"EH");htb.put(35,"EI");
		htb.put(36,"EJ");htb.put(37,"EK");htb.put(38,"EL");htb.put(39,"EM");htb.put(40,"EN");
		htb.put(41,"EO");htb.put(42,"EP");htb.put(43,"EQ");htb.put(44,"ER");htb.put(45,"ES");
		htb.put(46,"ET");htb.put(47,"EU");htb.put(48,"EV");htb.put(49,"EW");htb.put(50,"EX");
		htb.put(51,"EY");htb.put(52,"EZ");htb.put(53,"FA");htb.put(54,"FB");htb.put(55,"FC");
		htb.put(56,"FD");htb.put(57,"FE");htb.put(58,"FF");htb.put(59,"FG");htb.put(60,"FH");
		htb.put(61,"FI");htb.put(62,"FJ");htb.put(63,"FK");htb.put(64,"FL");htb.put(65,"FM");
		htb.put(66,"FN");htb.put(67,"FO");htb.put(68,"FP");htb.put(69,"FQ");htb.put(70,"FR");
		htb.put(71,"FS");htb.put(72,"FT");htb.put(73,"FU");htb.put(74,"FV");htb.put(75,"FW");
		htb.put(76,"FX");htb.put(77,"FY");htb.put(78,"FZ");htb.put(79,"GA");htb.put(80,"GB");
		
		
		return htb; 
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
	    IStockintransit obj = new Stockintransit();
	    Utility util = new Utility();
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    obj.setuserId(userId);
	    obj.init();
	    
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Center/NPPPerformance.jsp";
		response.sendRedirect(nextJSP);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
	    IStockintransit obj = new Stockintransit();	
	    Utility util = new Utility();
	  
	    obj.setuserId((String)session.getAttribute("userId")==null?"":(String) session.getAttribute("userId"));
	    
	    obj.setuserTen((String)session.getAttribute("userTen")==null? "":(String) session.getAttribute("userTen"));
	    
   	 	obj.setnppId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"))==null?"":util.antiSQLInspection(request.getParameter("nppId"))));
   	 	
   	 	obj.setkenhId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId"))==null? "":util.antiSQLInspection(request.getParameter("kenhId"))));
   	 	
	   	 obj.setdvkdId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId"))==null? "":util.antiSQLInspection(request.getParameter("dvkdId"))));
	   	 
	   	 obj.setMonth(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("month"))==null? "":util.antiSQLInspection(request.getParameter("month"))));
	   	 
	   	 obj.setYear(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("year"))==null? "":util.antiSQLInspection(request.getParameter("year"))));	   	 
	 	 
	   	 obj.setvungId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"))==null? "":util.antiSQLInspection(request.getParameter("vungId"))));	   	 
	   	 
	   	 obj.setkhuvucId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId"))==null? "":util.antiSQLInspection(request.getParameter("khuvucId"))));	 
	   	 	   	    	
		 obj.setdvkdId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId"))==null? "":util.antiSQLInspection(request.getParameter("dvkdId"))));		 
		
		 obj.setgsbhId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("gsbhId"))==null? "":util.antiSQLInspection(request.getParameter("gsbhId"))));
		 
		 obj.setnppId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"))==null? "":util.antiSQLInspection(request.getParameter("nppId"))));
		 
		String nextJSP = "/AHF/pages/Center/NPPPerformance.jsp";		 
		try{
			String action=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")));
			if(action.equals("Taomoi")){
				response.setContentType("application/xlsm");
		        response.setHeader("Content-Disposition", "attachment; filename=ThucHienChiTieuNPPTT.xlsm");
		        OutputStream out = response.getOutputStream();
		        CreatePivotTable(out,obj);
			}	else{
				obj.init();	    
				session.setAttribute("obj", obj);
				session.setAttribute("userId", obj.getuserId());		
				response.sendRedirect(nextJSP);
			}
		}catch(Exception ex){
			obj.setMsg(ex.getMessage());
			obj.init();	    
			session.setAttribute("obj", obj);
			session.setAttribute("userId", obj.getuserId());		
			response.sendRedirect(nextJSP);
		}
	
	}
	
	private String setQuery( IStockintransit obj) {
		
		String fromYear = obj.getYear();
		String fromMonth = obj.getMonth();
		String fromDate=fromYear+'-'+fromMonth;
		String toDate=fromYear+'-'+Integer.parseInt(fromMonth)+1;
		String query="";
		geso.dms.center.db.sql.dbutils db=new geso.dms.center.db.sql.dbutils();
		String sql="select distinct nhomsp_fk as nhomsanpham_fk ,dbo.ftBoDau(nsp.ten) as ten  from  ChiTieuNhanVien_DDKD_NHOMSP "+  
				" inner join ChiTieuNhanVien b on b.pk_Seq=ChiTieuNhanVien_fk  "+
				" inner join nhomsanpham nsp on nsp.pk_seq=nhomsp_fk "+
				" where b.thang="+obj.getMonth()+" and b.nam="+ obj.getYear() ;
		ResultSet rs=db.get(sql);
		String chuoi="";
		String[] arraychuoi= new String[20];
		String chuoiselct=" '' ";
		String chuoingoac="[0]";//co dau []
		if(rs!=null){
			int i=0;
			try {
				while (rs.next()){

					if(i==0){
						chuoingoac="["+rs.getString("nhomsanpham_fk")+"]";
						chuoi=rs.getString("nhomsanpham_fk");
						chuoiselct="sum(isnull(ctBan.["+rs.getString("nhomsanpham_fk")+"],0)) AS ctBan"+rs.getString("nhomsanpham_fk")+", "+
									"sum(ISNULL(tdBan.["+rs.getString("nhomsanpham_fk")+"],0)) AS tdBan"+rs.getString("nhomsanpham_fk")+" ";
					}else{
						chuoi=chuoi+","+rs.getString("nhomsanpham_fk");
						chuoiselct=chuoiselct+", sum(isnull(ctBan.["+rs.getString("nhomsanpham_fk")+"],0)) AS ctBan"+rs.getString("nhomsanpham_fk")+", "+
								 	"sum(ISNULL(DS.["+rs.getString("nhomsanpham_fk")+"],0)) AS tdBan"+rs.getString("nhomsanpham_fk")+" ";
						chuoingoac=chuoingoac+",["+rs.getString("nhomsanpham_fk")+"]";
					}
					arraychuoi[i]=rs.getString("nhomsanpham_fk");
					i++;

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		sql = "select distinct nhomsp_fk as nhomsanpham_fk ,dbo.ftBoDau(nsp.ten) as ten "+
				" from CHITIEU a inner join CHITIEU_NHAPP_NHOMSP b on a.PK_SEQ = b.CHITIEU_FK "+
				" inner join NHOMSANPHAM nsp on b.NHOMSP_FK = nsp.PK_SEQ "+
				" where b.nhomsp_fk != 0 and a.thang="+obj.getMonth()+" and a.nam="+ obj.getYear() ;
		rs=db.get(sql);
		String chuoisi="";
		String[] arraychuoisi= new String[20];
		String chuoiselctsi=" '' ";
		String chuoingoacsi="[0]";//co dau []
		if(rs!=null){
			int i=0;
			try {
				while (rs.next()){

					if(i==0){
						chuoingoacsi="["+rs.getString("nhomsanpham_fk")+"]";
						chuoisi=rs.getString("nhomsanpham_fk");
						chuoiselctsi="isnull(ctSIn.["+rs.getString("nhomsanpham_fk")+"], 0) as ctSI"+rs.getString("nhomsanpham_fk")+", "+
									"isnull(tdSIn.["+rs.getString("nhomsanpham_fk")+"], 0) as tdSI"+rs.getString("nhomsanpham_fk");
					}else{
						chuoisi=chuoisi+","+rs.getString("nhomsanpham_fk");
						chuoiselctsi=chuoiselctsi+", isnull(ctSIn.["+rs.getString("nhomsanpham_fk")+"], 0) as ctSI"+rs.getString("nhomsanpham_fk")+", "+
								 	"isnull(tdSIn.["+rs.getString("nhomsanpham_fk")+"], 0) as tdSI"+rs.getString("nhomsanpham_fk")+", ";
						chuoingoacsi=chuoingoac+",["+rs.getString("nhomsanpham_fk")+"]";
					}
					arraychuoisi[i]=rs.getString("nhomsanpham_fk");
					i++;

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		query=
				"\n select ctban.nppMa, ctban.nppTEN, ctDs as ctDs, dsBan.DoanhSo as tdDS, ctSI.CHITIEU as ctSI, isnull(tdSI.td, 0) as tdSI,"+
				"\n SoDonHang as sodonhang, isnull(sodh, 0) as sodh,"+
				"\n giatritbdonhang as giatritbdonhang, isnull(Gttbdh, 0) as gttbdh, SoKhachHangMoi as sokhachhangmoi, isnull(soKHmoi.sokhmoi,0) as khmoi, "+
				"\n SoKhachHangMuaHang as sokhachhangmuahang, ISNULL(sokh,0) as sokhds, "+chuoiselct+", "+chuoiselctsi+"  "+ 
				"\n from  "+ 
				"\n	(  "+ 
				"\n		select  d.MA as nppMA, D.TEN as nppTEN,"+
				"\n 			sum(b.ChiTieu)  as ctDs, sum(b.SoDonHang) as sodonhang, sum(b.SoKhachHangMoi) as sokhachhangmoi, "+
				"\n 			sum(b.SoKhachHangMuaHang) as sokhachhangmuahang, sum(b.GiaTriTBDonHang) as giatritbdonhang, "+ 
				"\n				b.NPP_FK, "+chuoingoac+"  "+ 
				"\n		from ChiTieuNhanVien a inner join ChiTieuNhanVien_DDKD  b on a.Pk_Seq=b.ChiTieuNhanVien_FK  "+ 
				"\n		left join  "+ 
				"\n		(    "+ 
				"\n			select ChiTieuNhanVien_FK,DDKD_fk,"+chuoingoac+"  "+ 
				"\n			from   "+ 
				"\n			(    "+ 
				"\n				select ChiTieuNhanVien_FK,DDKD_fk,CHITIEU,nhomsp_fk    "+ 
				"\n				from ChiTieuNhanVien_DDKD_NHOMSP     "+ 
				"\n			) as nhom pivot  ( sum(CHITIEU) for nhomsp_fk in ("+chuoingoac+"  )) as t   "+ 
				"\n		)as aa on aa.ChiTieuNhanVien_FK=b.ChiTieuNhanVien_FK and aa.ddkd_fk=b.ddkd_fk    "+ 
				"\n	inner join NHAPHANPHOI d on d.PK_SEQ=b.NPP_FK  "+ 
				"\n	inner join KHUVUC kv on kv.PK_SEQ=d.KHUVUC_FK  "+ 
				"\n	inner join VUNG v on v.PK_SEQ=kv.VUNG_FK  "+ 
				"\n	inner join TINHTHANH tt on tt.PK_SEQ=d.TINHTHANH_FK  " +
				"\n  where a.thang ='"+fromMonth+"' and a.Nam='"+fromYear+"' group by d.MA, D.TEN, b.NPP_FK, "+chuoingoac+" "+ 
				"\n )as ctBan  "+ 
				"\n left join   "+ 
				"\n (  "+ 
				"\n	 select a.NPP_FK, SUM(b.SoLuong*b.dongiagoc) as DoanhSo, count(distinct b.DONHANG_FK) as sodh, "+
				"\n  SUM(b.SoLuong*b.dongiagoc)/count(distinct b.DONHANG_FK) as Gttbdh, count(distinct a.KHACHHANG_FK) as sokh  "+ 
				"\n	 from DONHANG a  "+ 
				"\n		inner join DONHANG_SANPHAM b on b.DONHANG_FK=a.PK_SEQ  "+ 
				"\n	 where a.TRANGTHAI=1 and a.NgayNhap like  '"+fromDate+"%' "+ 
				"\n	 group by a.NPP_FK  "+ 
				"\n )as dsBan on dsBan.NPP_FK = ctBan.NPP_FK  "+ 
				"\n left join   "+ 
				"\n (  "+ 
				"\n    select t.NPP_FK, "+chuoingoac+"  "+ 
				"\n    from  "+ 
				"\n	(  "+ 
				"\n	 select a.NPP_FK, SUM(b.SoLuong) as SoLuong, c.NSP_FK  "+ 
				"\n	 from DONHANG a  "+ 
				"\n		inner join DONHANG_SANPHAM b on b.DONHANG_FK=a.PK_SEQ  "+ 
				"\n		inner join NHOMSANPHAM_SANPHAM  c on c.SP_FK=b.SANPHAM_FK  "+ 
				"\n	 where a.TRANGTHAI=1 and a.NgayNhap like  '"+fromDate+"%'   "+ 
				"\n	 group by a.NPP_FK, c.NSP_FK  "+ 
				"\n	 )as t pivot  ( sum(SoLuong) for NSP_FK in ( "+chuoingoac+"  )) as t   "+ 
				"\n )as tdBan on tdBan.NPP_FK=ctBan.NPP_FK  "+
				"\n left join "+
				"\n ( "+
				"\n	select count(distinct a.pk_seq) as sokhmoi, a.NPP_FK from KHACHHANG a inner join KHACHHANG_TUYENBH b on a.PK_SEQ = b.KHACHHANG_FK "+ 
				"\n	inner join TUYENBANHANG c on b.TBH_FK = c.PK_SEQ "+
				"\n	where a.ngaytao like '"+fromDate+"%' and a.TRANGTHAI = 1 "+
				"\n	group by a.NPP_FK "+
				"\n )as soKHmoi on soKHmoi.NPP_FK=ctBan.NPP_FK "+  
				"\n left join "+  
				"\n (	"+
				"\n	select d.MA as nppMA,D.TEN as nppTEN, aa.NPP_FK, aa.CHITIEU from CHITIEU a "+ 
				"\n		inner join CHITIEU_NHAPP_NHOMSP aa on a.PK_SEQ = aa.CHITIEU_FK "+
				"\n		inner join NHAPHANPHOI d on d.PK_SEQ=aa.NPP_FK  	"+
				"\n		inner join KHUVUC kv on kv.PK_SEQ=d.KHUVUC_FK  	"+
				"\n		inner join VUNG v on v.PK_SEQ=kv.VUNG_FK  	"+
				"\n		inner join TINHTHANH tt on tt.PK_SEQ=d.TINHTHANH_FK "+  	
				"\n	where a.thang ='"+fromMonth+"' and a.Nam='"+fromYear+"' and aa.NHOMSP_FK = 0 "+	 
				"\n ) as ctSI on ctSI.NPP_FK = ctBan.NPP_FK "+
				"\n left join  "+
				"\n (	"+
				"\n	select a.NPP_FK, SUM(b.soluongttduyet * b.DonGiaGoc) as td "+  		
				"\n	from ERP_DONDATHANG a inner join ERP_DONDATHANG_SANPHAM b on b.dondathang_fk=a.PK_SEQ "+   		
				"\n	where 1=1  and a.NgayDonHang like '"+fromDate+"%' and a.TRANGTHAI in (2, 4) group by a.NPP_FK "+
				"\n ) as tdSI on tdSI.NPP_FK = ctBan.NPP_FK "+
				"\n left join "+
				"\n ( "+
				"\n	select d.MA as nppMA,D.TEN as nppTEN, aa.NPP_FK, "+chuoingoacsi+" "+
				"\n	from CHITIEU a inner join "+
				"\n	(select CHITIEU_FK, NPP_FK, "+chuoingoacsi+" "+
				"\n		from "+
				"\n		( "+
				"\n			select CHITIEU_FK, NPP_FK, NHOMSP_FK, CHITIEU from CHITIEU_NHAPP_NHOMSP "+
				"\n		) p "+
				"\n	pivot (sum(chitieu) for nhomsp_fk in ("+chuoingoacsi+")) AS t) aa on aa.CHITIEU_FK = a.PK_SEQ "+
				"\n	inner join NHAPHANPHOI d on d.PK_SEQ=aa.NPP_FK  "+
				"\n	inner join KHUVUC kv on kv.PK_SEQ=d.KHUVUC_FK  "+
				"\n	inner join VUNG v on v.PK_SEQ=kv.VUNG_FK  "+
				"\n	inner join TINHTHANH tt on tt.PK_SEQ=d.TINHTHANH_FK  "+
				"\n	where a.thang ='"+fromMonth+"' and a.Nam='"+fromYear+"' 	"+
				"\n ) as ctSIn on ctSIn.NPP_FK = ctBan.NPP_FK "+
				"\n left join "+
				"\n ("+
				"\n	select t.NPP_FK, "+chuoingoacsi+"  "+
				"\n    from  ( "+
				"\n		select a.NPP_FK, c.NSP_FK, SUM(b.soluongttduyet * b.DonGiaGoc) as td  "+
				"\n		from ERP_DONDATHANG a inner join ERP_DONDATHANG_SANPHAM b  "+
				"\n		on b.dondathang_fk=a.PK_SEQ   "+
				"\n		inner join NHOMSANPHAM_SANPHAM  c on c.SP_FK=b.SANPHAM_FK   "+
				"\n		where 1=1  and a.NgayDonHang like '"+fromDate+"%' and a.TRANGTHAI in (2, 4) "+
				"\n		group by a.NPP_FK, c.NSP_FK "+
				"\n	)as t pivot  ( sum(td) for NSP_FK in ( "+chuoingoacsi+"  )) as t "+
				"\n ) as tdSIn on tdSIn.NPP_FK = ctBan.NPP_FK";

		System.out.println("[NPPperformance]"+query);
		
		return query;
	}

	
	private void CreatePivotTable(OutputStream out,IStockintransit obj) throws Exception
    {   
		try{
			
			//String chuoi=getServletContext().getInitParameter("path") + "\\ThucHienChiTieuNPPTT.xlsm";
		
			//FileInputStream fstream = new FileInputStream(chuoi);
			File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\ThucHienChiTieuNPPTT.xlsm");
			FileInputStream fstream = new FileInputStream(f) ;
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			
			CreateStaticHeader(workbook,obj);
			
			String query= setQuery(obj);
			FillData(workbook, obj, query);
			workbook.save(out);
			fstream.close();
	     }catch(Exception ex){
	    	 ex.printStackTrace();
	    	 throw new Exception(ex.getMessage());
	     }	    
   }
	
	private void CreateStaticHeader(Workbook workbook, IStockintransit obj) 
	{
		
		Hashtable<Integer, String> htb=this.htbValueCell();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");
		Cells cells = worksheet.getCells();

		Style style;		
		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
		cell.setValue("TÌNH HÌNH THỰC HIỆN CHỈ TIÊU NHÀ PHÂN PHỐI");

		style = cell.getStyle();

		Font font2 = new Font();
		font2.setColor(Color.RED);// mau chu
		font2.setSize(14);// size chu
		font2.setBold(true);
		style.setFont(font2);
		style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu
		cell.setStyle(style);

	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("A3");
	    
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Tháng : " + obj.getMonth() + "" );
	    
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("B3"); 
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Năm : " + obj.getYear() + "" );
		
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A4");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A5");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());
	   
	    int i = 105;
	    /*cell = cells.getCell(GetExcelColumnName(index)+"1"); cell.setValue("KenhBanHang");index++;
	    cell = cells.getCell(GetExcelColumnName(index)+"1"); cell.setValue("DonViKinhDoanh");index++;
	    cell = cells.getCell(GetExcelColumnName(index)+"1"); cell.setValue("ChiNhanh");index++;
	    cell = cells.getCell(GetExcelColumnName(index)+"1"); cell.setValue("KhuVuc");index++;
	    cell = cells.getCell(GetExcelColumnName(index)+"1"); cell.setValue("MANPP");	index++;
	    cell = cells.getCell( GetExcelColumnName(index)+"1");cell.setValue("TENNPP");  	  index++;  
	    cell = cells.getCell(GetExcelColumnName(index)+"1"); cell.setValue("NgayLamViec");index++;
	    cell = cells.getCell(GetExcelColumnName(index)+"1"); cell.setValue("ChiTieuSoDH");	  index++;  
	    cell = cells.getCell(GetExcelColumnName(index)+"1"); cell.setValue("ThucDatSoDH");index++;
	    cell = cells.getCell(GetExcelColumnName(index)+"1"); cell.setValue("PhanTramDonHang");	index++;
	    
	    cell = cells.getCell(GetExcelColumnName(index)+"1"); cell.setValue("ChiTieu_TonKho");	index++;
	    cell = cells.getCell(GetExcelColumnName(index)+"1"); cell.setValue("ThucDat_TonKho");	index++;
	    cell = cells.getCell(GetExcelColumnName(index)+"1"); cell.setValue("PhanTram_TonKho");	index++;
	    cell = cells.getCell(GetExcelColumnName(index)+"1"); cell.setValue("SoDonHang");	index++;
	    cell = cells.getCell(GetExcelColumnName(index)+"1"); cell.setValue("ChiTieu_GiaoHang");	index++;
	    cell = cells.getCell(GetExcelColumnName(index)+"1"); cell.setValue("ThucDat_GiaoHang");	index++;
	    cell = cells.getCell(GetExcelColumnName(index)+"1"); cell.setValue("PhanTram_GiaoHang");	index++;*/
	    cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("MaNPP");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1"); cell.setValue("NhaPhanPhoi");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ChiTieuDoanhSo");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ThucDatDoanhSo");
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("%DoanhSo");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ThuongDoanhSo");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ChiTieuSI");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ThucDatSI");
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("%SI");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ThuongSI");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ChiTieuSoDonHang");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ThucDatSoDonHang");
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("%SoDonHang");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ThuongSoDonHang");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ChiTieuGiaTriTrungBinh");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ThucDatGiaTriTrungBinh");
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("%GiaTriTrungBinh");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ThuongGiaTriTrungBinh");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ChiTieuSoKhachHangMoi");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ThucDatSoKhachHangMoi");
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("%SoKhachHangMoi");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ThuongSoKhachHangMoi");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ChiTieuSoKhachHangMuaHang");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ThucDatSoKhachHangMuaHang");
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("%SoKhachHangMuaHang");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ThuongSoKhachHangMuaHang");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);

		String sql = "select distinct nhomsp_fk as nhomsanpham_fk,dbo.ftBoDau(nsp.ten) as ten  from  ChiTieuNhanVien_DDKD_NHOMSP "+  
				" inner join ChiTieuNhanVien b on b.pk_Seq=ChiTieuNhanVien_fk  "+
				" inner join nhomsanpham nsp on nsp.pk_seq=nhomsp_fk "+
				" where b.thang="+obj.getMonth()+" and b.nam="+ obj.getYear() ;
		dbutils db=new dbutils();
		ResultSet rs=db.get(sql);

		if(rs!=null){

			try {
				while (rs.next())
				{
					cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ChiTieu-"+rs.getString("ten"));	
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ThucDat-"+rs.getString("ten"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("PhanTram-"+rs.getString("ten"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
				}
				rs.close();
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}

		sql = "select distinct nhomsp_fk as nhomsanpham_fk ,dbo.ftBoDau(nsp.ten) as ten "+
				" from CHITIEU a inner join CHITIEU_NHAPP_NHOMSP b on a.PK_SEQ = b.CHITIEU_FK "+
				" inner join NHOMSANPHAM nsp on b.NHOMSP_FK = nsp.PK_SEQ "+
				" where b.nhomsp_fk != 0 and a.thang="+obj.getMonth()+" and a.nam="+ obj.getYear();
		
		rs=db.get(sql);

		if(rs!=null){

			try {
				while (rs.next())
				{	
					cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ChiTieuSI-"+rs.getString("ten"));	
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ThucDatSI-"+rs.getString("ten"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("PhanTramSI-"+rs.getString("ten"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ThuongSI-"+rs.getString("ten"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
				}
				rs.close();
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}

		cell = cells.getCell("M1");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, ( i-1 ) + "");	
	}

	private void FillData(Workbook workbook,IStockintransit obj, String query)throws Exception 
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();
		dbutils db = new dbutils();		
		int indexCol = 2;
		int indexRow=2;
		String colID="";

		//Lay danh sach mang SALEOUT,CHO NAY NO RA HET MANG SALESUP,TRONG KHI CAI
		// NHÓM THƯỞNG SALESOUT ISNHOMTHUONGSALESOUT=1
		String sql="select distinct nhomsp_fk as nhomsanpham_fk,dbo.ftBoDau(nsp.ten) as ten  from  ChiTieuNhanVien_DDKD_NHOMSP "+  
				" inner join ChiTieuNhanVien b on b.pk_Seq=ChiTieuNhanVien_fk  "+
				" inner join nhomsanpham nsp on nsp.pk_seq=nhomsp_fk "+
				" where b.thang="+obj.getMonth()+" and b.nam="+ obj.getYear() ;

		System.out.println("[NhomSaleOut]"+sql );
		ResultSet	rs = db.get(sql);
		String[] thuongsalesout = new String[20];
		int i = 0;
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					thuongsalesout[i] = rs.getString("nhomsanpham_fk");
					i++;
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		sql="select distinct nhomsp_fk as nhomsanpham_fk ,dbo.ftBoDau(nsp.ten) as ten "+
		" from CHITIEU a inner join CHITIEU_NHAPP_NHOMSP b on a.PK_SEQ = b.CHITIEU_FK "+
		" inner join NHOMSANPHAM nsp on b.NHOMSP_FK = nsp.PK_SEQ "+
		" where b.nhomsp_fk != 0 and a.thang="+obj.getMonth()+" and a.nam="+ obj.getYear() ;
		System.out.println("[NhomSaleIn]"+sql );
		rs = db.get(sql);
		String[] thuongsalein = new String[20];
		i = 0;
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					thuongsalein[i] = rs.getString("nhomsanpham_fk");
					i++;
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		String fromYear = obj.getYear();
		String fromMonth = obj.getMonth();
		ResultSet rsthuong;
		
		rs=db.get(query);
		try 
		{				

			Cell cell = null;
			while(rs.next())
			{ 				
				indexCol = 105;
				
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getString("nppMa"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);

				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getString("nppTen"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
				//---------------------------------SALE--OUT--------------------------------------------------------
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getDouble("ctDS"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);

				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getDouble("tdDS"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);

				float		phantramMTD = 0;
				if (rs.getFloat("ctDS") > 0)
				{
					phantramMTD = rs.getFloat("tdDS") / rs.getFloat("ctDS" );
				}
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(phantramMTD);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 10);
				phantramMTD = phantramMTD * 100;
				sql = "select tieuchi, TU, den, thuong from TIEUCHITINHTHUONG a inner join TIEUCHITINHTHUONG_CHITIET b on a.PK_SEQ = b.TIEUCHITINHTHUONG_FK "+ 
						  "\n	where TRANGTHAI = 1 and LOAI = 5 and tieuchi = 1 and nam = "+fromYear+" and thang = '"+fromMonth + "' and "+phantramMTD+" >= tu and "+phantramMTD+" <= den";
				System.out.println("rsthuong ds "+sql);
				rsthuong = db.get(sql);
				boolean next = rsthuong.next();
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				if(next) cell.setValue(rsthuong.getDouble("Thuong"));
				else cell.setValue(0);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
				
				//---------------------------------SALE--IN---------------------------------------------------------
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getDouble("ctSI"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);

				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getDouble("tdSI"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);

				phantramMTD = 0;
				if (rs.getFloat("ctSI") > 0)
				{
					phantramMTD = rs.getFloat("tdSI") / rs.getFloat("ctSI" );
				}
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(phantramMTD);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 10);
				phantramMTD = phantramMTD * 100;
				sql = "select tieuchi, TU, den, thuong from TIEUCHITINHTHUONG a inner join TIEUCHITINHTHUONG_CHITIET b on a.PK_SEQ = b.TIEUCHITINHTHUONG_FK "+ 
						  "\n	where TRANGTHAI = 1 and LOAI = 5 and tieuchi = 6 and nam = "+fromYear+" and thang = '"+fromMonth + "' and "+phantramMTD+" >= tu and "+phantramMTD+" <= den";
				System.out.println("rsthuong ds "+sql);
				rsthuong = db.get(sql);
				next = rsthuong.next();
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				if(next) cell.setValue(rsthuong.getDouble("Thuong"));
				else cell.setValue(0);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
				//---------------------------------SO--DON--HANG---------------------------------------------------

				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getDouble("SoDonHang"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
				
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getDouble("sodh"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
				
				phantramMTD = 0;
				if (rs.getFloat("SoDonHang") > 0)
				{
					phantramMTD = rs.getFloat("sodh") / rs.getFloat("SoDonHang" );
				}
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(phantramMTD);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 10);
				phantramMTD = phantramMTD * 100;
				sql = "select tieuchi, TU, den, thuong from TIEUCHITINHTHUONG a inner join TIEUCHITINHTHUONG_CHITIET b on a.PK_SEQ = b.TIEUCHITINHTHUONG_FK "+ 
						  "\n	where TRANGTHAI = 1 and LOAI = 5 and tieuchi = 2 and nam = "+fromYear+" and thang = '"+fromMonth + "' and "+phantramMTD+" >= tu and "+phantramMTD+" <= den";
				rsthuong = db.get(sql);
				next = rsthuong.next();
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				if(next) cell.setValue(rsthuong.getDouble("Thuong"));
				else cell.setValue(0);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
				//---------------------------------SO--DON--HANG---------------------------------------------------
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getDouble("giatritbdonhang"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
				
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getDouble("gttbdh"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
				
				phantramMTD = 0;
				if (rs.getFloat("giatritbdonhang") > 0)
				{
					phantramMTD = rs.getFloat("gttbdh") / rs.getFloat("giatritbdonhang" );
				}
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(phantramMTD);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 10);
				phantramMTD = phantramMTD * 100;
				sql = "select tieuchi, TU, den, thuong from TIEUCHITINHTHUONG a inner join TIEUCHITINHTHUONG_CHITIET b on a.PK_SEQ = b.TIEUCHITINHTHUONG_FK "+ 
						  "\n	where TRANGTHAI = 1 and LOAI = 5 and tieuchi = 3 and nam = "+fromYear+" and thang = '"+fromMonth + "' and "+phantramMTD+" >= tu and "+phantramMTD+" <= den";
				rsthuong = db.get(sql);
				next = rsthuong.next();
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				if(next) cell.setValue(rsthuong.getDouble("Thuong"));
				else cell.setValue(0);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
				
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getDouble("SoKhachHangMoi"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
				
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getDouble("khmoi"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
				
				phantramMTD = 0;
				if (rs.getFloat("SoKhachHangMoi") > 0)
				{
					phantramMTD = rs.getFloat("khmoi") / rs.getFloat("SoKhachHangMoi" );
				}
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(phantramMTD);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 10);
				phantramMTD = phantramMTD * 100;
				sql = "select tieuchi, TU, den, thuong from TIEUCHITINHTHUONG a inner join TIEUCHITINHTHUONG_CHITIET b on a.PK_SEQ = b.TIEUCHITINHTHUONG_FK "+ 
						  "\n	where TRANGTHAI = 1 and LOAI = 5 and tieuchi = 4 and nam = "+fromYear+" and thang = '"+fromMonth + "' and "+phantramMTD+" >= tu and "+phantramMTD+" <= den";
				rsthuong = db.get(sql);
				next = rsthuong.next();
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				if(next) cell.setValue(rsthuong.getDouble("Thuong"));
				else cell.setValue(0);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
				
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getDouble("SoKhachHangMuaHang"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
				
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getDouble("sokhds"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
				
				phantramMTD = 0;
				if (rs.getFloat("SoKhachHangMuaHang") > 0)
				{
					phantramMTD = rs.getFloat("sokhds") / rs.getFloat("SoKhachHangMuaHang" );
				}
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(phantramMTD);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 10);
				phantramMTD = phantramMTD * 100;
				sql = "select tieuchi, TU, den, thuong from TIEUCHITINHTHUONG a inner join TIEUCHITINHTHUONG_CHITIET b on a.PK_SEQ = b.TIEUCHITINHTHUONG_FK "+ 
						  "\n	where TRANGTHAI = 1 and LOAI = 5 and tieuchi = 5 and nam = "+fromYear+" and thang = '"+fromMonth + "' and "+phantramMTD+" >= tu and "+phantramMTD+" <= den";
				rsthuong = db.get(sql);
				next = rsthuong.next();
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				if(next) cell.setValue(rsthuong.getDouble("Thuong"));
				else cell.setValue(0);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
				
				for (i = 0; i < thuongsalesout.length; i++)
				{
					if (thuongsalesout[i] != null)
					{
						cell = cells.getCell(GetExcelColumnName(indexCol++) + Integer.toString(indexRow));
						cell.setValue(rs.getFloat("ctBan" + thuongsalesout[i]));
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);

						cell = cells.getCell(GetExcelColumnName(indexCol++) + Integer.toString(indexRow));
						cell.setValue(rs.getFloat("tdBan" + thuongsalesout[i]));
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
						phantramMTD = 0;

						if (rs.getFloat("ctBan" + thuongsalesout[i]) > 0)
						{
							phantramMTD = rs.getFloat("tdBan" + thuongsalesout[i])  / rs.getFloat("ctBan" + thuongsalesout[i]);
						}

						cell = cells.getCell(GetExcelColumnName(indexCol++) + Integer.toString(indexRow));
						cell.setValue(phantramMTD);

						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 10);
					}
				}
				
				//---------------------------SALE--IN--------------------------------------------------------
				for (i = 0; i < thuongsalein.length; i++)
				{
					if (thuongsalein[i] != null)
					{
						cell = cells.getCell(GetExcelColumnName(indexCol++) + Integer.toString(indexRow));
						cell.setValue(rs.getFloat("ctSIn" + thuongsalein[i]));
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);

						cell = cells.getCell(GetExcelColumnName(indexCol++) + Integer.toString(indexRow));
						cell.setValue(rs.getFloat("tdSIn" + thuongsalein[i]));
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
						phantramMTD = 0;

						if (rs.getFloat("ctSIn" + thuongsalein[i]) > 0)
						{
							phantramMTD = rs.getFloat("tdSIn" + thuongsalein[i])  / rs.getFloat("ctSIn" + thuongsalein[i]);
						}

						cell = cells.getCell(GetExcelColumnName(indexCol++) + Integer.toString(indexRow));
						cell.setValue(phantramMTD);

						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 10);
					}
				}

				indexRow++;
			}
			if(rs != null) rs.close();
			if(db!=null){
				db.shutDown();
			}
		}catch(java.sql.SQLException err){
			err.printStackTrace();
			System.out.println(err.toString());
			throw new Exception("Khong the tao duoc bao cao trong thoi gian nay. Error :"+err.toString());
		}
	}
	private String GetExcelColumnName(int columnNumber)
	 {
	     int dividend = columnNumber;
	     String columnName = "";
	     int modulo;

	     while (dividend > 0)
	     {
	         modulo = (dividend - 1) % 26;
	         columnName = (char)(65 + modulo) + columnName;
	         dividend = (int)((dividend - modulo) / 26);
	     } 

	     return columnName;
	 }
	
}
