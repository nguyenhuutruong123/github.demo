package geso.dms.center.servlets.reports;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.awt.geom.QuadCurve2D;
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

public class UsingPromotionTTSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UsingPromotionTTSvl() {
		super();

	}  

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utility util=new Utility();

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");	
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();	 
		
		String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"))==null? "":util.antiSQLInspection(request.getParameter("view")));
		obj.setView(view);
		String querystring=request.getQueryString();

		String userId=util.getUserId(querystring);
		if(userId==null)
			obj.setuserId("");
		obj.setuserId(userId);
		String userTen=(String)session.getAttribute("userTen");
		if(userTen==null)
			obj.setuserTen("");
		obj.setuserTen(userTen);
		obj.init();		    
		session.setAttribute("obj", obj);
		session.setAttribute("util", util);
		session.setAttribute("userTen", userTen);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Center/UsingPromotionAllocationReportCenter.jsp";
		response.sendRedirect(nextJSP);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utility util=new Utility();

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();		
		OutputStream out = response.getOutputStream(); 
		IStockintransit obj = new Stockintransit();	
		String nextJSP = "/AHF/pages/Center/UsingPromotionAllocationReportCenter.jsp";
		try
		{
			
			String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"))==null? "":util.antiSQLInspection(request.getParameter("view")));
			obj.setView(view);
			
			String userId = (String) session.getAttribute("userId");
			String userTen = (String) session.getAttribute("userTen");			

			obj.setuserId(userId==null? "":userId);
			obj.setuserTen(userTen==null? "":userTen);
			obj.settungay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays"))==null? "":util.antiSQLInspection(request.getParameter("Sdays"))));			
			obj.setdenngay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays"))==null? "":util.antiSQLInspection(request.getParameter("Edays"))));


			obj.setkenhId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId"))==null? "":util.antiSQLInspection(request.getParameter("kenhId"))));
			obj.setvungId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"))==null? "":util.antiSQLInspection(request.getParameter("vungId"))));
			obj.setkhuvucId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId"))==null? "":util.antiSQLInspection(request.getParameter("khuvucId"))));			
			obj.setgsbhId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("gsbhs"))==null? "":util.antiSQLInspection(request.getParameter("gsbhs"))));			
			obj.setnppId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"))==null? "":util.antiSQLInspection(request.getParameter("nppId"))));
			obj.setdvkdId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId"))==null? "":util.antiSQLInspection(request.getParameter("dvkdId"))));
			obj.setnhanhangId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhanhangId"))==null? "":util.antiSQLInspection(request.getParameter("nhanhangId"))));
			obj.setchungloaiId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chungloaiId"))==null? "":util.antiSQLInspection(request.getParameter("chungloaiId"))));
			obj.setPrograms(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("programs"))==null? "":util.antiSQLInspection(request.getParameter("programs"))));
			obj.setFieldShow(request.getParameterValues("fieldsHien")!=null? request.getParameterValues("fieldsHien"):null);

			//Add condition

			String condition = "";
			if(obj.getkenhId().length()>0)
				condition +=" AND kbh.pk_seq='" + obj.getkenhId() + "'";
			if(obj.getnppId().length()>0)
				condition += " AND npp.pk_seq='" + obj.getnppId() + "'";
			if(obj.getPrograms().length()>0)
				condition +=" AND km.SCHEME='" + obj.getPrograms() + "'";
			if(obj.getvungId().length()>0)
				condition +=" AND v.pk_seq='" + obj.getvungId() + "'";
			if(obj.getkhuvucId().length()>0)
				condition +=" AND kv.pk_seq='" + obj.getkhuvucId() + "'";

			String action = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")));

			if (action.equals("Taomoi")) {
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=SuDungPhanBoKM.xlsm");
				CreatePivotTable(out,obj,condition);
				return;
			}			
		}
		catch (Exception ex) {
			obj.setMsg(ex.getMessage());
		}
		obj.init();
		session.setAttribute("obj", obj);
		session.setAttribute("util", util);
		session.setAttribute("userTen", obj.getuserTen());
		session.setAttribute("userId", obj.getuserId());
		response.sendRedirect(nextJSP);
	}

	private void CreatePivotTable(OutputStream out,IStockintransit obj, String condition) throws Exception
	{       	
		String streamstr = getServletContext().getInitParameter("path") + "\\SuDungPhanBoKhuyenMaiTT.xlsm";
		FileInputStream  fstream = new FileInputStream(streamstr);
		//File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\SuDungPhanBoKhuyenMaiTT.xlsm");
		//FileInputStream fstream = new FileInputStream(f) ;
		Workbook workbook = new Workbook();
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateStaticHeader(workbook,obj);	     
		CreateStaticData(workbook,obj,condition);
		workbook.save(out);
		fstream.close();
	}

	private void CreateStaticHeader(Workbook workbook, IStockintransit obj) {

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
	    //cell = cells.getCell("AO1"); cell.setValue("Đơn giá");

	}
	private void CreateStaticData(Workbook workbook,IStockintransit obj, String condition) throws Exception
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();
		dbutils db = new dbutils();
		Utility Ult = new  Utility();
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
					"\n where 1=1 	 " ;
		if(obj.getView().equals("TT"))			
		{
			sql +="\n and npp.pk_seq in "+ Ult.quyen_npp(obj.getuserId())  +
					"\n  	and kbh.pk_seq in "+ Ult.quyen_kenh(obj.getuserId()) ;
			
		}
					
					
		if(obj.getvungId().trim().length() > 0)
			sql += " and v.pk_seq = "+ obj.getvungId();
		if(obj.getkhuvucId().trim().length() > 0)
			sql += " and kv.pk_seq = "+ obj.getkhuvucId();
		if(obj.getPrograms().trim().length() > 0)
			sql += " and ctkm.scheme = '"+ obj.getPrograms()+"'";
		if(obj.getnppId().trim().length() > 0)
			sql += " and npp.pk_seq = '"+ obj.getnppId()+"'";
		if(obj.getkenhId().trim().length() > 0)
			sql += " and kbh.pk_seq = '"+ obj.getkenhId()+"'";
		
/*		" select kbh.ten as kenh, ctkm.scheme, v.ten as Region, kv.ten as Area, npp.ma as "+
		" Distributor_code, npp.ten as Distributor, ctkm.scheme as "+
		"  Programs_code, ctkm.diengiai as Programs_Name, ctkm.tungay as From_Date, ctkm.denngay as To_Date "+
		" , sum(dh.sudung) as dasudung, "+
		" case when dh.ngansach > 0 and dh.ngansach < 99999999999 then dh.ngansach else '-1'  "+
		" end as sophanbo , case when dh.ngansach >0 and dh.ngansach < 99999999999 "+
		" then dh.ngansach - abs(sum(dh.sudung)) else 0 end as conlai , "+
		" case when dh.ngansach >0 and dh.ngansach < 99999999999 then   "+
		" cast(100*(cast(sum(dh.sudung) as float) /abs(dh.ngansach)) as float) "+  
		" else 0 end as phantramsudung,  ctkm.kho_fk as khoKM "+
		" from ( "+ 
		" select dh.kbh_fk, dh.npp_fk,dhkm.ctkmid, "+
		"	case  "+
		"		when km.PHANBOTHEODONHANG=0  "+
		"		then sum(dhkm.tonggiatri) else sum(dhkm.SOLUONG) end as sudung ,   "+
		"				(	select ngansach from phanbokhuyenmai where npp_fk=dh.NPP_FK and dhkm.CTKMID=CTKM_FK "+  
		"				)  as ngansach, dhkm.SPMA, dh.KHACHHANG_FK  "+
		"	from donhang dh inner join donhang_ctkm_trakm dhkm on dh.pk_seq=dhkm.donhangid  "+ 
		"			inner join CTKHUYENMAI km on km.PK_SEQ=dhkm.CTKMID  "+				
		" 	 where  dh.ngaynhap >= '"+obj.gettungay()+"' and dh.ngaynhap <= '"+obj.getdenngay()+"'  and dh.trangthai <> '2' "+
		" 	 and dh.PK_SEQ not in (select donhang_fk from DONHANGTRAVE  where DONHANG_FK is not null   and  trangthai=3 ) "+
		" 	group by dh.npp_fk,dhkm.ctkmid ,dh.kbh_fk,km.PHANBOTHEODONHANG, dhkm.SPMA,dh.KHACHHANG_FK "+
		"  ) as  dh   inner join ctkhuyenmai ctkm on ctkm.pk_Seq =dh.ctkmid "+
		" inner join nhaphanphoi npp on npp.pk_seq = dh.npp_fk  "+
		" left join khuvuc kv on kv.pk_seq = npp.khuvuc_fk left join vung v on v.pk_seq = kv.vung_fk "+
		" left join kenhbanhang kbh on kbh.pk_seq=dh.kbh_fk where 1=1 ";
		sql += "group by kbh.ten, ctkm.scheme, v.ten, kv.ten, npp.ma, npp.ten, dh.ngansach, "+ 
			   "ctkm.scheme, ctkm.diengiai, ctkm.tungay, ctkm.denngay, ctkm.kho_fk";*/

		System.out.println("Using Promotion: " + sql);
		ResultSet rs = db.get(sql);
		int i = 2; 	     	    
		if(rs!=null)
		{
			try 
			{
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

				Cell cell = null;
				Style style;
				while (rs.next()) 
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
					
					/*cell = cells.getCell("AO" + Integer.toString(i));	cell.setValue(rs.getDouble("dongia") );
					style = cell.getStyle();
					style.setNumber(3);
					cell.setStyle(style);*/
					i++;
				}

				if(rs != null)
					rs.close();
				if(db!=null){
					db.shutDown();
				}
			}catch (Exception e){	 			
				throw new Exception(e.getMessage());
			}
			finally{
				if(rs != null)
					rs.close();
			}
		}else{	 			
			throw new Exception("Khong the tao bao cao trong thoi gian nay");
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
}

