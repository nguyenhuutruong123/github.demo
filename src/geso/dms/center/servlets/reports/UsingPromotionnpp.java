package geso.dms.center.servlets.reports;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;

public class UsingPromotionnpp extends HttpServlet {
	private static final long serialVersionUID = 1L;	

	public UsingPromotionnpp() {
		super();

	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		String nextJSP = "/AHF/pages/Distributor/UsingPromotionAllocationReportDis.jsp";
		response.sendRedirect(nextJSP);		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 		
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

			obj.setuserId(userId==null? "":userId);
			obj.setuserTen(userTen==null? "":userTen);
			obj.settungay(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays")==null? "":request.getParameter("Sdays")));			
			obj.setdenngay(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays")==null? "":request.getParameter("Edays")));


			obj.setkenhId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")==null? "":request.getParameter("kenhId")));
			obj.setdvkdId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")==null? "":request.getParameter("dvkdId")));
			obj.setnhanhangId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhanhangId")==null? "":request.getParameter("nhanhangId")));
			obj.setchungloaiId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chungloaiId")==null? "":request.getParameter("chungloaiId")));
			obj.setUnit(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("donviTinh")==null? "":request.getParameter("donviTinh")));
			obj.setPrograms(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("programs")==null? "":request.getParameter("programs")));
			obj.setFieldShow(request.getParameterValues("fieldsHien")!=null? request.getParameterValues("fieldsHien"):null);


			Utility Ult = new Utility();
			obj.setnppId(Ult.getIdNhapp(userId)) ;
			obj.setnppTen(Ult.getTenNhaPP());

			String condition = "";
			if(obj.getkenhId().length()>0)
				condition +=" and kbh.pk_seq ='" + obj.getkenhId() +"'";
			if(obj.getPrograms().length()>0)
				condition +=" and ctkm.scheme = '" + obj.getPrograms() +"'";
			String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));				
			if (action.equals("create")) {
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition",
						"attachment; filename=SuDungPhanBoKhuyenMai(NPP).xlsm");
				CreatePivotTable(out,obj,condition);
			}		        
		}
		catch (Exception ex)
		{
			obj.setMsg(ex.getMessage());
		}
		obj.init();
		session.setAttribute("obj", obj);	
		session.setAttribute("userTen", obj.getuserTen());
		String nextJSP = "/AHF/pages/Distributor/UsingPromotionAllocationReportDis.jsp";
		response.sendRedirect(nextJSP);		
	}

	private void CreatePivotTable(OutputStream out,IStockintransit obj, String condition) throws Exception
	{       
		String fstreamstr = getServletContext().getInitParameter("path") + "\\SuDungPhanBoKhuyenMaiTT.xlsm";
		FileInputStream fstream = new FileInputStream(fstreamstr);
		/*File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\SuDungPhanBoKhuyenMaiTT.xlsm");
		FileInputStream fstream = new FileInputStream(f) ;
*/		Workbook workbook = new Workbook();
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

		cell.setValue("BÁO CÁO SỬ DỤNG PHÂN BỔ KHUYẾN MÃI");  getCellStyle(workbook,"A1",Color.RED,true,14);	  	

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

		cell = cells.getCell("AA1"); cell.setValue("KenhBanHang");
		cell = cells.getCell("AB1"); cell.setValue("ChiNhanh");
		cell = cells.getCell("AC1"); cell.setValue("KhuVuc");
		cell = cells.getCell("AD1"); cell.setValue("Chi Nhanh/Doi Tac");
		cell = cells.getCell("AE1"); cell.setValue("MaChuongTrinh");
 	    cell = cells.getCell("AF1"); cell.setValue("TenChuongTrinh");
 	    cell = cells.getCell("AG1"); cell.setValue("MaChiNhanh/DoiTac");
	    cell = cells.getCell("AH1"); cell.setValue("TuNgay");
	    cell = cells.getCell("AI1"); cell.setValue("DenNgay");
	    cell = cells.getCell("AJ1"); cell.setValue("NganSachPhanBo");
	    cell = cells.getCell("AK1"); cell.setValue("DaSuDung");
	    cell = cells.getCell("AL1"); cell.setValue("NganSachConLai");
	    cell = cells.getCell("AM1"); cell.setValue("%SuDung");
	    cell = cells.getCell("AN1"); cell.setValue("HinhThuc");
	}

	private void CreateStaticData(Workbook workbook, IStockintransit obj, String condition) throws Exception
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();
		dbutils db = new dbutils();	   	    
//		String sql="\n select kbh.ten as kenh, ctkm.scheme, v.ten as Region, kv.ten as Area, npp.ma as "+
//				"\n Distributor_code, npp.ten as Distributor, ctkm.scheme as "+
//				"\n  Programs_code, ctkm.diengiai as Programs_Name, ctkm.tungay as From_Date, ctkm.denngay as To_Date "+
//				"\n , dh.sudung as dasudung,case when dh.ngansach > 0 and dh.ngansach < 99999999999 then dh.ngansach else '-1'  "+
//				"\n end as sophanbo , case when dh.ngansach >0 and dh.ngansach < 99999999999 "+
//				"\n then dh.ngansach - abs(dh.sudung) else 0 end as conlai , "+
//				"\n case when dh.ngansach >0 and dh.ngansach < 99999999999 then   "+
//				"\n cast(100*(cast(dh.sudung as float) /abs(dh.ngansach)) as float) "+  
//				"\n else 0 end as phantramsudung,  ctkm.kho_fk as khoKM "+
//				"\n from ( "+ 
//				"\n select dh.kbh_fk, dh.npp_fk,dhkm.ctkmid, "+
//				"\n	case  "+
//				"\n		when km.PHANBOTHEODONHANG=0  "+
//				"\n		then sum(dhkm.tonggiatri) else sum(dhkm.SOLUONG) end as sudung ,   "+
//				"\n				(	select ngansach from phanbokhuyenmai where npp_fk=dh.NPP_FK and dhkm.CTKMID=CTKM_FK "+  
//				"\n				)  as ngansach  "+
//				"\n	from donhang dh inner join donhang_ctkm_trakm dhkm on dh.pk_seq=dhkm.donhangid  "+ 
//				"\n			inner join CTKHUYENMAI km on km.PK_SEQ=dhkm.CTKMID  "+				
//				"\n 	 where  dh.ngaynhap >= '"+obj.gettungay()+"' and dh.ngaynhap <= '"+obj.getdenngay()+"'  and dh.trangthai <> '2' "+
//				"\n 	 and dh.PK_SEQ not in (select donhang_fk from DONHANGTRAVE  where DONHANG_FK is not null   and  trangthai=3 ) "+
//				"\n 	group by dh.npp_fk,dhkm.ctkmid ,dh.kbh_fk,km.PHANBOTHEODONHANG "+
//				"\n  ) as  dh   inner join ctkhuyenmai ctkm on ctkm.pk_Seq =dh.ctkmid "+
//				"\n inner join nhaphanphoi npp on npp.pk_seq = dh.npp_fk  "+
//				"\n left join khuvuc kv on kv.pk_seq = npp.khuvuc_fk left join vung v on v.pk_seq = kv.vung_fk "+
//				"\n left join kenhbanhang kbh on kbh.pk_seq=dh.kbh_fk "+
//				"\n where dh.npp_fk = '"+ obj.getnppId() +"' " ;
		
		String sql= "\n select kbh.ten as kenh, ctkm.scheme, v.ten as Region, kv.ten as Area, npp.ma as  Distributor_code, npp.ten as Distributor, ctkm.scheme as   Programs_code " +
				"\n 	 , ctkm.diengiai as Programs_Name, ctkm.tungay as From_Date, ctkm.denngay as To_Date   " +
				"\n 	 ,case when ctkm.phanbotheodonhang =0 then isnull(dh.tonggiatri,0)  else isnull(dh.soxuat,0) end as dasudung " +
				"\n 	 ,  pb.ngansach sophanbo " +
				"\n 	 ,  case when ctkm.loaingansach = 0 then pb.ngansach  else   (pb.ngansach -  case when ctkm.phanbotheodonhang =0 then isnull(dh.tonggiatri,0)  else isnull(dh.soxuat,0) end) end conlai " +
				"\n 	 , 100 * (  case when ctkm.loaingansach = 1 and pb.ngansach > 0   then  (case when ctkm.phanbotheodonhang =0 then isnull(dh.tonggiatri,0)  else isnull(dh.soxuat,0) end  ) /pb.ngansach else 0 end )  phantramsudung " +
				"\n 	 ,  ctkm.kho_fk as khoKM  " +
				"\n from CTKHUYENMAI ctkm " +
				"\n cross apply  " +
				"\n (	 " +
				"\n 	select KBH_FK,NPP_FK,sum(soxuat)soxuat, sum(tonggiatri)tonggiatri  " +
				"\n 	from   " +
				"\n  	( " +
				"\n 			select dh.KBH_FK,dh.NPP_FK,dhkm.donhangId,max(soxuat) soxuat, sum(dhkm.tonggiatri) tonggiatri  " +
				"\n 			from donhang_ctkm_trakm dhkm inner join donhang dh on dh.pk_seq = dhkm.donhangId  " +
				"\n 			where dh.ngaynhap >= '"+obj.gettungay()+"' and dh.ngaynhap <= '"+obj.getdenngay()+"'  and dh.trangthai <> '2'" +
				"\n   					and dhkm.ctkmid = ctkm.PK_SEQ and not exists ( select 1 from donhangtrave where trangthai = 3 and donhang_fk = dh.pk_seq)  " +
				"\n 			group by dh.KBH_FK,dh.NPP_FK,dhkm.donhangId  " +
				"\n  	)ss  " +
				"\n 	group by KBH_FK,NPP_FK " +
				"\n )dh " +
				"\n inner join PHANBOKHUYENMAI pb on pb.CTKM_FK = ctkm.PK_SEQ and dh.NPP_FK = pb.NPP_FK " +
				"\n inner join nhaphanphoi npp on npp.pk_seq = dh.npp_fk  " +
				"\n left join khuvuc kv on kv.pk_seq = npp.khuvuc_fk left join vung v on v.pk_seq = kv.vung_fk " +
				"\n left join kenhbanhang kbh on kbh.pk_seq=dh.kbh_fk " +
				"\n where 1=1 and dh.npp_fk = '"+ obj.getnppId() +"'	 " + condition ;


		System.out.println(sql);
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

				Cell cell = null;
				Style style;
				while(rs.next())
				{  					
					String KenhBanHang = rs.getString("kenh");
					String ChiNhanh = rs.getString("Region");
					String KhuVuc = rs.getString("Area");
					String NhaPhanPhoi = rs.getString("Distributor");
					String MaNhaPhanPhoi = rs.getString("Distributor_code");
					String MaChuongTrinh = rs.getString("Programs_code");
					String TenChuongTrinh = rs.getString("Programs_Name");
					String TuNgay = rs.getString("From_Date");
					String DenNgay = rs.getString("To_Date");					
					String khoKM = rs.getString("khoKM");

					cell = cells.getCell("AA" + Integer.toString(i));	cell.setValue(KenhBanHang);
					cell = cells.getCell("AB" + Integer.toString(i));	cell.setValue(ChiNhanh);
					cell = cells.getCell("AC" + Integer.toString(i));	cell.setValue(KhuVuc);
					cell = cells.getCell("AD" + Integer.toString(i));	cell.setValue(NhaPhanPhoi);
					cell = cells.getCell("AE" + Integer.toString(i));	cell.setValue(MaChuongTrinh);
					cell = cells.getCell("AF" + Integer.toString(i));	cell.setValue(TenChuongTrinh);
					cell = cells.getCell("AG" + Integer.toString(i));	cell.setValue(MaNhaPhanPhoi);
					cell = cells.getCell("AH" + Integer.toString(i));	cell.setValue(TuNgay);
					cell = cells.getCell("AI" + Integer.toString(i));	cell.setValue(DenNgay);
					cell = cells.getCell("AJ" + Integer.toString(i));	cell.setValue(rs.getDouble("sophanbo"));
					style = cell.getStyle();
					style.setNumber(3);
					cell.setStyle(style);

					cell = cells.getCell("AK" + Integer.toString(i));	cell.setValue(rs.getDouble("dasudung") );
					style = cell.getStyle();
					style.setNumber(3);
					cell.setStyle(style);

					cell = cells.getCell("AL" + Integer.toString(i));	cell.setValue(rs.getDouble("conlai"));
					style = cell.getStyle();
					style.setNumber(3);
					cell.setStyle(style);


					cell = cells.getCell("AM" + Integer.toString(i));	cell.setValue(rs.getDouble("phantramsudung"));
					style = cell.getStyle();
					style.setNumber(2);
					cell.setStyle(style);

					if(khoKM.equals("100000")){
						cell = cells.getCell("AN" + Integer.toString(i)); cell.setValue("NPP ứng hàng");
					}else{
						if (khoKM.equals("100001")){
							cell = cells.getCell("AN" + Integer.toString(i)); cell.setValue("DDT cho ứng hàng");
						}else{
							cell = cells.getCell("AN" + Integer.toString(i)); cell.setValue("Không xác định");
						}
					}
					i++;
				}
			}catch (Exception e){ 		
				throw new Exception("Khong tao duoc bao cao trong thoi gian nay");
			}
			finally{
				if(rs != null)
					rs.close();
				if(db!=null){
					db.shutDown();
				}
			}
		}else{
			throw new Exception("Khong tao duoc bao cao trong thoi gian nay");
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

