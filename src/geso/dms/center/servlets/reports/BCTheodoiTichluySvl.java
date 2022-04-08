package geso.dms.center.servlets.reports;

import geso.dms.center.beans.report.ITheodoiTichluy;
import geso.dms.center.beans.report.imp.TheodoiTichluy;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;

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
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;

public class BCTheodoiTichluySvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public BCTheodoiTichluySvl() {
		super();

	}  

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utility util=new Utility();

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");	
		HttpSession session = request.getSession();
		ITheodoiTichluy obj = new TheodoiTichluy();	  
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
		String nextJSP = "/AHF/pages/Center/BCTheodoiTichluy.jsp";
		response.sendRedirect(nextJSP);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utility util=new Utility();

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();		
		OutputStream out = response.getOutputStream(); 
		ITheodoiTichluy obj = new TheodoiTichluy();	
		String nextJSP = "/AHF/pages/Center/BCTheodoiTichluy.jsp";
		try
		{
			String userId = (String) session.getAttribute("userId");
			String userTen = (String) session.getAttribute("userTen");			

			obj.setuserId(userId==null? "":userId);
			obj.setuserTen(userTen==null? "":userTen);
			//obj.settungay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays"))==null? "":util.antiSQLInspection(request.getParameter("Sdays"))));			
			//obj.setdenngay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays"))==null? "":util.antiSQLInspection(request.getParameter("Edays"))));


			//obj.setkenhId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId"))==null? "":util.antiSQLInspection(request.getParameter("kenhId"))));
			obj.setvungId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"))==null? "":util.antiSQLInspection(request.getParameter("vungId"))));
			obj.setkhuvucId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId"))==null? "":util.antiSQLInspection(request.getParameter("khuvucId"))));			
			obj.setnppId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"))==null? "":util.antiSQLInspection(request.getParameter("nppId"))));
			obj.setDdkdId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdId"))==null? "":util.antiSQLInspection(request.getParameter("ddkdId"))));
			//Add condition
			
			String[] programs = request.getParameterValues("programs");
			String ctkmIds = "";
			if(programs != null){
				for(int i=0; i< programs.length; i++)
					ctkmIds += programs[i] + ",";
			}
			if(ctkmIds.length() > 0)
				ctkmIds = ctkmIds.substring(0, ctkmIds.length() - 1);
			obj.setpromotion(ctkmIds);
			

			String action = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")));
			if(ctkmIds.length() == 0){
				obj.setMsg("Vui lòng chọn chương trình tích lũy");
				obj.init();
				session.setAttribute("obj", obj);
				session.setAttribute("util", util);
				session.setAttribute("userTen", obj.getuserTen());
				session.setAttribute("userId", obj.getuserId());
				response.sendRedirect(nextJSP);
				return;
			}
			if (action.equals("Taomoi")) {
				String query = getExcelQuery(obj);
				response.setContentType("application/xlsm");
	    		response.setHeader("Content-Disposition", "attachment; filename=BCTheodoiTichluy.xlsm");
	    		OutputStream out1 = response.getOutputStream();
				ExportToExcel(out1, obj, query);
				return;
			}	
			if (action.equals("Taomoi1")) {
				String query = getExcelQuery1(obj);
				response.setContentType("application/xlsm");
	    		response.setHeader("Content-Disposition", "attachment; filename=BCTheodoiTichluy.xlsm");
	    		OutputStream out1 = response.getOutputStream();
				ExportToExcel1(out1, obj, query);
				return;
			}	
		}
		catch (Exception ex) {
			ex.printStackTrace();
			obj.setMsg(ex.getMessage());
		}
		obj.init();
		session.setAttribute("obj", obj);
		session.setAttribute("util", util);
		session.setAttribute("userTen", obj.getuserTen());
		session.setAttribute("userId", obj.getuserId());
		response.sendRedirect(nextJSP);
	}


	private String getExcelQuery1(ITheodoiTichluy obj) {
		/*String sql = "" +
				"\n IF OBJECT_ID('tempdb.dbo.#DONHANG') IS NOT NULL DROP TABLE #DONHANG " +      
				"\n CREATE TABLE #DONHANG    " +      
				"\n (  " +      
				"\n   	KHACHHANG_FK NUMERIC(18,0), " +      
				"\n   	DS FLOAT,  " +      
				"\n   	SOLUONG INT, " +      
				"\n   	THUONGTL_FK NUMERIC(18,0) " +      
				"\n ) " +      
				"\n   " +      
				"\n INSERT #DONHANG(KHACHHANG_FK, DS, SOLUONG, THUONGTL_FK) " +      
				"\n select dh.KHACHHANG_FK, SUM(sp.SOLUONG * sp.GIAMUA) as DS, SUM(sp.SOLUONG) as SOLUONG, tl.PK_SEQ  " +      
				"\n from DONHANG dh inner join DONHANG_SANPHAM sp on sp.DONHANG_FK = dh.PK_SEQ  " +      
				"\n inner join TIEUCHITHUONGTL_SANPHAM tc on tc.sanpham_fk = sp.SANPHAM_FK  " +      
				"\n inner join TIEUCHITHUONGTL tl on tc.thuongtl_fk = tl.PK_SEQ  " +      
				"\n inner join DANGKYKM_TICHLUY_KHACHHANG dk on dk.KHACHHANG_FK = dh.KHACHHANG_FK " +
				"\n inner join DANGKYKM_TICHLUY x on  dk.DKKMTICHLUY_FK = x.PK_SEQ  and x.TRANGTHAI != 2 AND x.tieuchitl_fk = tl.PK_SEQ" +      
				"\n where dh.TRANGTHAI = 1 and dh.NGAYNHAP >= tl.ngayds_tungay and dh.NGAYNHAP <= ngayds_denngay and tl.PK_SEQ in ("+obj.getpromotion()+")" +      
				"\n group by dh.KHACHHANG_FK, tl.PK_SEQ " +      
				"\n 			  " +      
				"\n select v.TEN as VUNG, kv.TEN as KHUVUC, " +      
				"\n 	p.TEN as NPP, ddkd.TEN as NVBH, tc.SCHEME, kh.SMARTID, kh.TEN as TEN, kh.DIACHI, " +      
				"\n 	ISNULL(x.Ten,'') as PHUONGXA, ISNULL(q.TEN,'') as QUANHUYEN, ISNULL(t.TEN,'') as TINHTHANH, " +      
				"\n 	--CASE WHEN kh_dk.MUCDANGKY_FK = 1 THEN 'X' ELSE '' END AS MUC1, " +      
				"\n 	--CASE WHEN kh_dk.MUCDANGKY_FK = 2 THEN 'X' ELSE '' END AS MUC2, " +      
				"\n 	--CASE WHEN kh_dk.MUCDANGKY_FK = 3 THEN 'X' ELSE '' END AS MUC3, " +      
				"\n 	kh_dk.MUCDANGKY_FK, " +      
				"\n 	ISNULL(ds.DS,0) as DOANHSO, " +      
				"\n 	CASE WHEN ISNULL(ds.DS,0) >= muc.tumuc then 'X' else '' end as DAT, " +      
				"\n 	CASE WHEN ISNULL(ds.DS,0) < muc.tumuc then 'X' else '' end as KHONGDAT " +      
				"\n from  " +      
				"\n DANGKYKM_TICHLUY dk inner join DANGKYKM_TICHLUY_KHACHHANG kh_dk on kh_dk.DKKMTICHLUY_FK = dk.PK_SEQ  and dk.TRANGTHAI = 1 " +      
				"\n inner join KHACHHANG kh on kh.PK_SEQ = kh_dk.KHACHHANG_FK  " +      
				"\n inner join NHAPHANPHOI p on p.PK_SEQ = kh.NPP_FK "
				+ "\n inner join DUYETTRAKHUYENMAI_KHACHHANG duyet on duyet.khid = kh.pk_seq " +    
				"\n LEFT JOIN KHUVUC kv on kv.PK_SEQ = p.KHUVUC_FK " +      
				"\n left join VUNG v on v.PK_SEQ = kv.VUNG_FK " +      
				"\n LEFT JOIN PhuongXa x on x.pk_Seq = kh.PhuongXa_FK " +      
				"\n left join QUANHUYEN q on q.PK_SEQ = kh.QUANHUYEN_FK " +      
				"\n left join TINHTHANH t on t.PK_SEQ = kh.TINHTHANH_FK " +      
				"\n left join (  " +      
				"\n 	select thuongtl_fk, tumuc, denmuc, chietkhau, donvi, muc from TIEUCHITHUONGTL_TIEUCHI where thuongtl_fk in ("+obj.getpromotion()+") " +      
				"\n 	union all  " +      
				"\n 	select thuongtl_fk, 0, tumuc - 1, 0, 0, -1 from TIEUCHITHUONGTL_TIEUCHI where thuongtl_fk in ("+obj.getpromotion()+") and muc = 0  " +      
				"\n ) muc on muc.thuongtl_fk = dk.TIEUCHITL_FK  and muc.muc + 1 = kh_dk.MUCDANGKY_FK " +      
				"\n inner JOIN TIEUCHITHUONGTL tc on tc.PK_SEQ = muc.thuongtl_fk  " +      
				"\n left join #DONHANG ds on ds.KHACHHANG_FK = kh_dk.KHACHHANG_FK and ds.THUONGTL_FK = tc.PK_SEQ " +      
				"\n LEFT JOIN ( " +      
				"\n 	SELECT DISTINCT a.DDKD_FK, t.KHACHHANG_FK  " +      
				"\n 	FROM KHACHHANG_TUYENBH t inner join TUYENBANHANG a on t.TBH_FK = a.PK_SEQ " +
				"\n ) d on d.KHACHHANG_FK = kh.PK_SEQ " +
				"\n left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = d.DDKD_FK" +      
				"\n where dk.TIEUCHITL_FK in ("+obj.getpromotion()+")";
		if(obj.getnppId().length()>0)
			sql += " AND p.pk_seq='" + obj.getnppId() + "'";
		if(obj.getvungId().length()>0)
			sql +=" AND v.pk_seq='" + obj.getvungId() + "'";
		if(obj.getkhuvucId().length()>0)
			sql +=" AND kv.pk_seq='" + obj.getkhuvucId() + "'";
		if(obj.getDdkdId().length() > 0)
			sql += " AND ddkd.PK_sEQ = " + obj.getDdkdId();
		sql += "\n order by v.TEN, kv.TEN, p.TEN, ddkd.TEN, tc.SCHEME, kh.SMARTID";*/
		dbutils db = new dbutils();
		String sqlsub = "select dkkhuyenmai_fk from tieuchithuongtl_dktl where ctkhuyenmai_fk in  ("+obj.getpromotion()+")  order by dkkhuyenmai_fk ";
		System.out.println(sqlsub);
		ResultSet r = db.getScrol(sqlsub);
		String subdkkm = "";
		String subPivot = "";
		String subIn = "";
		String select_mcp = "";
		String select_cods = "";
		String selectall = "";
		String tongch = "";
		String  tongcods = "";
		String select_mcp1 = "";
		int col = 0;
	
		
		int i = 0;
		try {
			while(r.next()){
				subdkkm += " ISNULL(SP.["+r.getString("dkkhuyenmai_fk")+"],0) AS [CODS_"+r.getString("dkkhuyenmai_fk")+"], \n";
				select_cods += " PIVOTTABLE1.[" + r.getString("dkkhuyenmai_fk")+"] as [DSD_" + r.getString("dkkhuyenmai_fk")+"], \n";
			subIn += "["+r.getString("dkkhuyenmai_fk")+"], ";
			selectall += " sp.[" + r.getString("dkkhuyenmai_fk")+"] AS ["+r.getString("dkkhuyenmai_fk")+"],"
					+ " sp.[DSD_" + r.getString("dkkhuyenmai_fk")+"] AS [DSD_"+r.getString("dkkhuyenmai_fk")+"], \n";
			tongch += "["+r.getString("dkkhuyenmai_fk")+"], [Dat_"+r.getString("dkkhuyenmai_fk")+"] , \n";
			 tongcods += "["+r.getString("dkkhuyenmai_fk")+"] float, [Dat_"+r.getString("dkkhuyenmai_fk")+"] float, \n";
			 select_mcp += " sum(ds.[" + r.getString("dkkhuyenmai_fk")+"]) as DKKM" + r.getString("dkkhuyenmai_fk")+", \n";
			 select_mcp1 += "case when sum(ds.[Dat_" + r.getString("dkkhuyenmai_fk")+"]) = 1 then 'X' else '' end as DAT" + r.getString("dkkhuyenmai_fk")+", \n";
			i++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		subdkkm = subdkkm.substring(0, subdkkm.length() - 3);
		subIn = subIn.substring(0, subIn.length() - 2);
		select_cods = select_cods.substring(0, select_cods.length() - 3);
		selectall = selectall.substring(0, selectall.length() - 3);
		tongch = tongch.substring(0, tongch.length() - 3);
		tongcods = tongcods.substring(0, tongcods.length() - 3);
		select_mcp = select_mcp.substring(0, select_mcp.length() - 3);
		select_mcp1 = select_mcp1.substring(0, select_mcp1.length() - 3);
		
		
		
		String sql ="\n 	 IF OBJECT_ID('tempdb.dbo.#DONHANG') IS NOT NULL DROP TABLE #DONHANG   " +
				"\n 	CREATE TABLE #DONHANG      " +
				"\n 	(    " +
				"\n 	   	KHACHHANG_FK NUMERIC(18,0),   " +
				"\n 	  	muc numeric(18, 0),  " +
				"\n 	   	tiendo numeric(18,0 ),  " +
				"\n 	   	DST FLOAT,   " +
				"\n 	   	DS1 Float,  " +
				"\n 	   	DS2 Float,  " +
				"\n 	   	DS3 Float,   " +
				"\n 	  	PD1 Float,  " +
				"\n 	   	PD2 Float,  " +
				"\n 	   	PD3 Float,  " +
				"\n			TongTien float, "+
				tongcods+
				"\n 	  	,THUONGTL_FK NUMERIC(18,0)   " +
				"\n 	 )   " +
				   
				"\n 	 INSERT #DONHANG(KHACHHANG_FK, muc, DST,ds1, ds2, ds3, PD1, PD2, PD3,"+tongch+" ,THUONGTL_FK)   " +

				"\n 	  select distinct ds.*, pd.PD1, pd.PD2, pd.PD3,"+selectall+", pd.PK_SEQ from (  " +
				"\n 	 select PIVOTTABLE.Khachhang_fk, PIVOTTABLE.muc,PIVOTTABLE.dst, PIVOTTABLE.[0], PIVOTTABLE.[1], PIVOTTABLE.[2]  " +
				"\n 	from (  " +
				"\n 	 select dh.KHACHHANG_FK,td.muc,td.tiendo  " +
				"\n 	 ,SUM(sp.SOLUONG * sp.GIAMUA) as DS,f.ds as dst, tl.PK_SEQ  " +
				"\n 	 from DONHANG dh inner join DONHANG_SANPHAM sp on sp.DONHANG_FK = dh.PK_SEQ    " +
				"\n 	 inner join TIEUCHITHUONGTL_SANPHAM tc on tc.sanpham_fk = sp.SANPHAM_FK    " +
				"\n 	 inner join TIEUCHITHUONGTL tl on tc.thuongtl_fk = tl.PK_SEQ    " +
				"\n 	 inner join TIEUCHITHUONGTL_TIENDO td on td.thuongtl_fk = tc.thuongtl_fk  " +
				"\n 	 inner join DANGKYKM_TICHLUY_KHACHHANG dk on dk.KHACHHANG_FK = dh.KHACHHANG_FK   " +
				"\n 	 inner join DANGKYKM_TICHLUY x on  dk.DKKMTICHLUY_FK = x.PK_SEQ  and x.TRANGTHAI != 2 AND x.tieuchitl_fk = tl.PK_SEQ  " +
				"\n 	 left join (select dh.KHACHHANG_FK, SUM(sp.SOLUONG * sp.GIAMUA) as DS, SUM(sp.SOLUONG) as SOLUONG, tl.PK_SEQ    " +
				"\n 	 from DONHANG dh inner join DONHANG_SANPHAM sp on sp.DONHANG_FK = dh.PK_SEQ    " +
				"\n 	 inner join TIEUCHITHUONGTL_SANPHAM tc on tc.sanpham_fk = sp.SANPHAM_FK    " +
				"\n 	 inner join TIEUCHITHUONGTL tl on tc.thuongtl_fk = tl.PK_SEQ    " +
				"\n 	 inner join DANGKYKM_TICHLUY_KHACHHANG dk on dk.KHACHHANG_FK = dh.KHACHHANG_FK   " +
				"\n 	 inner join DANGKYKM_TICHLUY x on  dk.DKKMTICHLUY_FK = x.PK_SEQ  and x.TRANGTHAI != 2 AND x.tieuchitl_fk = tl.PK_SEQ  " +
				"\n 	 where dh.TRANGTHAI = 1 and dh.NGAYNHAP >= tl.ngayds_tungay and dh.NGAYNHAP <= ngayds_denngay and tl.PK_SEQ in ("+obj.getpromotion()+")  " +
				"\n 	 group by dh.KHACHHANG_FK, tl.PK_SEQ  ) f on f.KHACHHANG_FK = dh.KHACHHANG_FK   " +
				"\n 	 where dh.TRANGTHAI = 1 and dh.NGAYNHAP >= td.tungay and dh.NGAYNHAP <= td.denngay and tl.PK_SEQ in ("+obj.getpromotion()+")  " +
				"\n 	 group by dh.KHACHHANG_FK,td.muc, td.tiendo , tl.PK_SEQ,tl.ngayds_denngay,tl.ngayds_tungay, f.DS  " +
				"\n 	 ) st PIVOT (sum(st.ds) for st.tiendo in ([0], [1], [2]) ) AS PIVOTTABLE   " +
				"\n 	 ) DS full outer join   " +
				"\n 	  (  " +
				"\n 	 select PIVOTTABLE.Khachhang_fk, PIVOTTABLE.muc, PIVOTTABLE.[0] as PD1, PIVOTTABLE.[1] as PD2, PIVOTTABLE.[2] as PD3, PIVOTTABLE.PK_SEQ  " +
				"\n 	from (  " +
				"\n 	  select dh.KHACHHANG_FK,td.muc,td.tiendo  " +
				"\n 	 ,td.phaidat, tl.PK_SEQ  " +
				"\n 	 from DONHANG dh inner join DONHANG_SANPHAM sp on sp.DONHANG_FK = dh.PK_SEQ    " +
				"\n 	 inner join TIEUCHITHUONGTL_SANPHAM tc on tc.sanpham_fk = sp.SANPHAM_FK    " +
				"\n 	 inner join TIEUCHITHUONGTL tl on tc.thuongtl_fk = tl.PK_SEQ    " +
				"\n 	inner join TIEUCHITHUONGTL_TIENDO td on td.thuongtl_fk = tc.thuongtl_fk  " +
				"\n 	inner join DANGKYKM_TICHLUY_KHACHHANG dk on dk.KHACHHANG_FK = dh.KHACHHANG_FK   " +
				"\n 	 inner join DANGKYKM_TICHLUY x on  dk.DKKMTICHLUY_FK = x.PK_SEQ  and x.TRANGTHAI != 2 AND x.tieuchitl_fk = tl.PK_SEQ  " +
				"\n 	 left join (select dh.KHACHHANG_FK, SUM(sp.SOLUONG * sp.GIAMUA) as DS, SUM(sp.SOLUONG) as SOLUONG, tl.PK_SEQ    " +
				"\n 	 from DONHANG dh inner join DONHANG_SANPHAM sp on sp.DONHANG_FK = dh.PK_SEQ    " +
				"\n 	 inner join TIEUCHITHUONGTL_SANPHAM tc on tc.sanpham_fk = sp.SANPHAM_FK    " +
				"\n 	 inner join TIEUCHITHUONGTL tl on tc.thuongtl_fk = tl.PK_SEQ    " +
				"\n 	 inner join DANGKYKM_TICHLUY_KHACHHANG dk on dk.KHACHHANG_FK = dh.KHACHHANG_FK   " +
				"\n 	 inner join DANGKYKM_TICHLUY x on  dk.DKKMTICHLUY_FK = x.PK_SEQ  and x.TRANGTHAI != 2 AND x.tieuchitl_fk = tl.PK_SEQ  " +
				"\n 	 where dh.TRANGTHAI = 1 and dh.NGAYNHAP >= tl.ngayds_tungay and dh.NGAYNHAP <= ngayds_denngay and tl.PK_SEQ in ("+obj.getpromotion()+")  " +
				"\n 	 group by dh.KHACHHANG_FK, tl.PK_SEQ  ) f on f.KHACHHANG_FK = dh.KHACHHANG_FK   " +
				"\n 	 where dh.TRANGTHAI = 1 and dh.NGAYNHAP >= td.tungay and dh.NGAYNHAP <= td.denngay and tl.PK_SEQ in ("+obj.getpromotion()+")  " +
				"\n 	 group by dh.KHACHHANG_FK,td.muc, td.tiendo , tl.PK_SEQ,tl.ngayds_denngay,tl.ngayds_tungay,td.phaidat  " +
				"\n 	 ) st PIVOT (sum(st.phaidat) for st.tiendo in ([0], [1], [2]) ) AS PIVOTTABLE   " +
				"\n 	 ) PD on pd.KHACHHANG_FK = ds.KHACHHANG_FK and pd.muc = ds.muc  " +
				"\n 	inner join DANGKYKM_TICHLUY_KHACHHANG kh_dk on kh_dk.KHACHHANG_FK = PD.KHACHHANG_FK and kh_dk.MUCDANGKY_FK = pd.muc+1   " +
				"\n 	 left join   " +
		/*		"\n 	 ( 	 select PIVOTTABLE.Khachhang_fk,PIVOTTABLE.muc,PIVOTTABLE.TONGTIEN, PIVOTTABLE.CTKHUYENMAI_FK,  " +select_cods+
				"\n 	from (  " +
				"\n 		 -- bất kỳ trong tổng tiền  " +
				"\n 		 select tlsp.DIEUKIENKHUYENMAI_FK,dh.KHACHHANG_FK,tlsp.SANPHAM_FK,tldk.muc,sp.SOLUONG*GIAMUA as dsmua ,dkkm.TONGTIEN, tldk.CTKHUYENMAI_FK, dkkm.LOAI   " +
				"\n 		 from dieukienkmtl_sanpham tlsp   " +
				"\n 		 inner join DONHANG_SANPHAM sp on sp.SANPHAM_FK = tlsp.SANPHAM_FK  " +
				"\n 		 inner join donhang dh on dh.PK_SEQ = sp.DONHANG_FK  " +
				"\n 		 inner join dieukienkhuyenmaitl dkkm on dkkm.PK_SEQ = tlsp.DIEUKIENKHUYENMAI_FK  " +
				"\n 		 inner join tieuchithuongtl_dktl tldk on tlsp.DIEUKIENKHUYENMAI_FK = tldk.DKKHUYENMAI_FK  " +
				"\n 		 inner join tieuchithuongtl tl on tl.PK_SEQ = tldk.CTKHUYENMAI_FK  " +
				"\n 		 where  dkkm.LOAI = 2  and dkkm.TONGTIEN > 0  and dh.trangthai = 1 and dh.NGAYNHAP >= tl.ngayds_tungay and dh.NGAYNHAP <= tl.ngayds_denngay  " +
				"\n 		   " +
				"\n 		 -- bất kỳ trong tổng lượng  " +
				"\n 		 union   " +
				"\n 		 select tlsp.DIEUKIENKHUYENMAI_FK,dh.KHACHHANG_FK,tlsp.SANPHAM_FK,tldk.muc,sp.SOLUONG*sp.GIAMUA as dsmua,dkkm.TONGLUONG*GIAMUA as TONGTIEN, tldk.CTKHUYENMAI_FK, dkkm.LOAI    " +
				"\n 		 from dieukienkmtl_sanpham tlsp   " +
				"\n 		 inner join DONHANG_SANPHAM sp on sp.SANPHAM_FK = tlsp.SANPHAM_FK  " +
				"\n 		 inner join donhang dh on dh.PK_SEQ = sp.DONHANG_FK  " +
				"\n 		 inner join dieukienkhuyenmaitl dkkm on dkkm.PK_SEQ = tlsp.DIEUKIENKHUYENMAI_FK  " +
				"\n 		 inner join tieuchithuongtl_dktl tldk on tlsp.DIEUKIENKHUYENMAI_FK = tldk.DKKHUYENMAI_FK   " +
				"\n 		 where  dkkm.LOAI = 2  and dkkm.TONGLUONG > 0   " +
				"\n 	 	 union  " +
				"\n 		 --bắt buộc tiền  " +
				"\n 	 	 select tlsp.DIEUKIENKHUYENMAI_FK,dh.KHACHHANG_FK,tlsp.SANPHAM_FK,tldk.muc,sp.SOLUONG*sp.GIAMUA as dsmua ,dkkm.TONGTIEN, tldk.CTKHUYENMAI_FK, dkkm.LOAI   " +
				"\n 	 	 from dieukienkmtl_sanpham tlsp   " +
				"\n 	 	 inner join DONHANG_SANPHAM sp on sp.SANPHAM_FK = tlsp.SANPHAM_FK  " +
				"\n 	 	 inner join donhang dh on dh.PK_SEQ = sp.DONHANG_FK  " +
				"\n 	 	 inner join dieukienkhuyenmaitl dkkm on dkkm.PK_SEQ = tlsp.DIEUKIENKHUYENMAI_FK  " +
				"\n 	 	 inner join tieuchithuongtl_dktl tldk on tlsp.DIEUKIENKHUYENMAI_FK = tldk.DKKHUYENMAI_FK  " +
				"\n 	 	 where dkkm.LOAI = 1  and dkkm.TONGTIEN > 0  " +
				"\n 		 union   " +
				"\n 		 -- bắt buộc lượng   " +
				"\n 	 	 select tlsp.DIEUKIENKHUYENMAI_FK,dh.KHACHHANG_FK,tlsp.SANPHAM_FK,tldk.muc,sp.SOLUONG*sp.GIAMUA as dsmua , dkkm.TONGLUONG*GIAMUA as TONGTIEN, tldk.CTKHUYENMAI_FK, dkkm.LOAI  " + 
				 	"\n 		 from dieukienkmtl_sanpham tlsp   " +
				"\n 		 inner join DONHANG_SANPHAM sp on sp.SANPHAM_FK = tlsp.SANPHAM_FK  " +
				 			"\n 	 	 inner join donhang dh on dh.PK_SEQ = sp.DONHANG_FK  " +
				"\n 	 	 inner join dieukienkhuyenmaitl dkkm on dkkm.PK_SEQ = tlsp.DIEUKIENKHUYENMAI_FK  " +
				"\n 	 	 inner join tieuchithuongtl_dktl tldk on tlsp.DIEUKIENKHUYENMAI_FK = tldk.DKKHUYENMAI_FK  " +
				 			"\n 	 	 where  dkkm.LOAI = 1  and dkkm.TONGLUONG > 0  " +
				"\n 	 	  ) st PIVOT (sum(st.dsmua) for st.DIEUKIENKHUYENMAI_FK in ("+subIn+" ) ) AS PIVOTTABLE   " +
				"\n 		 	  group by  PIVOTTABLE.Khachhang_fk,PIVOTTABLE.TONGTIEN	, PIVOTTABLE.CTKHUYENMAI_FK, PIVOTTABLE.muc  " +*/
				"\n 	 (  select PIVOTTABLE.*, "+ select_cods +
				"\n 			 	from (    " +
				"\n 			 		 -- b?t k? trong t?ng ti?n    " +
				"\n 			 		 select tlsp.DIEUKIENKHUYENMAI_FK,dh.KHACHHANG_FK,tldk.muc,sum(sp.SOLUONG*GIAMUA) as dsmua, tldk.CTKHUYENMAI_FK  " +
				"\n 			 		 from dieukienkmtl_sanpham tlsp     " +
				"\n 			 		 inner join DONHANG_SANPHAM sp on sp.SANPHAM_FK = tlsp.SANPHAM_FK    " +
				"\n 			 		 inner join donhang dh on dh.PK_SEQ = sp.DONHANG_FK    " +
				"\n 			 		 inner join dieukienkhuyenmaitl dkkm on dkkm.PK_SEQ = tlsp.DIEUKIENKHUYENMAI_FK   " + 
				"\n 			 		 inner join tieuchithuongtl_dktl tldk on tlsp.DIEUKIENKHUYENMAI_FK = tldk.DKKHUYENMAI_FK    " +
				"\n 			 		 inner join tieuchithuongtl tl on tl.PK_SEQ = tldk.CTKHUYENMAI_FK    " +
				"\n 			 		 where  dkkm.LOAI = 2  and dkkm.TONGTIEN > 0  and dh.trangthai = 1 and dh.NGAYNHAP >= tl.ngayds_tungay and dh.NGAYNHAP <= tl.ngayds_denngay    " +
				
				"\n 			 		 group by tlsp.DIEUKIENKHUYENMAI_FK,dh.KHACHHANG_FK,tldk.muc , tldk.CTKHUYENMAI_FK  " +
				"\n 			 	 	  ) st PIVOT (sum(st.dsmua) for st.DIEUKIENKHUYENMAI_FK in ("+subIn+" ) ) AS PIVOTTABLE   " +
				"\n 			 	 	  full outer join  " +
				"\n 			 	 	  (    " +
				"\n 			 		 -- b?t k? trong t?ng ti?n    " +
				"\n 			 		 select tlsp.DIEUKIENKHUYENMAI_FK,dh.KHACHHANG_FK,tldk.muc,  " +
				"\n 			 		 case when sum(sp.SOLUONG*GIAMUA) >= dkkm.TONGTIEN then 1 else 0 end as dsmua , tldk.CTKHUYENMAI_FK  " +
				"\n 			 		 from dieukienkmtl_sanpham tlsp     " +
				"\n 			 		 inner join DONHANG_SANPHAM sp on sp.SANPHAM_FK = tlsp.SANPHAM_FK    " +
				"\n 			 		 inner join donhang dh on dh.PK_SEQ = sp.DONHANG_FK    " +
				"\n 			 		 inner join dieukienkhuyenmaitl dkkm on dkkm.PK_SEQ = tlsp.DIEUKIENKHUYENMAI_FK    " +
				"\n 			 		 inner join tieuchithuongtl_dktl tldk on tlsp.DIEUKIENKHUYENMAI_FK = tldk.DKKHUYENMAI_FK    " +
				"\n 			 		 inner join tieuchithuongtl tl on tl.PK_SEQ = tldk.CTKHUYENMAI_FK    " +
				"\n 	where  dkkm.LOAI = 2  and dkkm.TONGTIEN > 0  and dh.trangthai = 1 and dh.NGAYNHAP >= tl.ngayds_tungay and dh.NGAYNHAP <= tl.ngayds_denngay    " +
				
				"\n 			 		 group by  tlsp.DIEUKIENKHUYENMAI_FK,dh.KHACHHANG_FK,tldk.muc,  " +
				"\n 			 		 dkkm.TONGTIEN, tldk.CTKHUYENMAI_FK  " +
				"\n 			 	 	  ) st PIVOT (sum(st.dsmua) for st.DIEUKIENKHUYENMAI_FK in ("+subIn+" )   " +
				"\n 			 	 	  ) AS PIVOTTABLE1 on PIVOTTABLE.KHACHHANG_FK = PIVOTTABLE1.KHACHHANG_FK and PIVOTTABLE.muc= PIVOTTABLE1.muc  " +
						 	 	
				"\n 	 	 )sp on sp.CTKHUYENMAI_FK = pd.PK_SEQ and sp.KHACHHANG_FK = pd.KHACHHANG_FK and sp.muc = pd.muc  " +
				 	 
				//"\n 		 group by ds.[0],ds.[1], ds.[2], ds.KHACHHANG_FK, ds.dst, ds.muc, pd.PD1, pd.PD2, pd.PD3, pd.PK_SEQ   " +

				 			  
				"\n 	 select v.TEN as VUNG, kv.TEN as KHUVUC,   " +
				"\n 	 	p.TEN as NPP, ddkd.TEN as NVBH, tc.SCHEME, kh.SMARTID, kh.TEN as TEN, kh.DIACHI,   " +
				"\n 		ISNULL(x.Ten,'') as PHUONGXA, ISNULL(q.TEN,'') as QUANHUYEN, ISNULL(t.TEN,'') as TINHTHANH,   " +
				"\n 		CASE WHEN kh_dk.MUCDANGKY_FK = 1 THEN 'X' ELSE '' END AS MUC1,   " +
				"\n 		CASE WHEN kh_dk.MUCDANGKY_FK = 2 THEN 'X' ELSE '' END AS MUC2,   " +
				"\n 		CASE WHEN kh_dk.MUCDANGKY_FK = 3 THEN 'X' ELSE '' END AS MUC3,   " +
				"\n 		kh_dk.MUCDANGKY_FK,   " +
				"\n 		ISNULL(ds.DST,0) as DOANHSO,ds.tongtien,   " +
				"\n 		--CASE WHEN ISNULL(ds.DST,0) >= muc.tumuc then 'X' else '' end as DAT,   " +
				"\n 		CASE WHEN ISNULL(sum(ds.ds1),0) >= (ds.PD1*muc.tumuc)/100 and "
				+ "\n 		ISNULL(sum(ds.ds2),0) >= (ds.PD2*muc.tumuc)/100 and"
				+ "\n		ISNULL(sum(ds.ds3),0) >= (ds.PD3*muc.tumuc)/100 then 'X' else '' end as DAT,   " +
				"\n 		--CASE WHEN ISNULL(ds.DST,0) < muc.tumuc then 'X' else '' end as KHONGDAT,  " +
				"\n 		CASE WHEN ISNULL(sum(ds.ds1),0) < (ds.PD1*muc.tumuc)/100 or "
				+ "\n 		ISNULL(sum(ds.ds2),0) < (ds.PD2*muc.tumuc)/100 or"
				+ "\n		ISNULL(sum(ds.ds3),0) < (ds.PD3*muc.tumuc)/100 then 'X' else '' end as KHONGDAT,  " +
				"\n 		sum(ds.ds1) AS DSGD1,   " +
				"\n 		CASE WHEN ISNULL(sum(ds.ds1),0) >= (ds.PD1*muc.tumuc)/100 then 'X' else '' end as DATGD1,  " +
				"\n 		sum(ds.ds2) AS DSGD2,   " +
				"\n 		CASE WHEN ISNULL(sum(ds.ds2),0) >= (ds.PD2*muc.tumuc)/100 then 'X' else '' end as DATGD2,  " +
				"\n 		sum(ds.ds3) AS DSGD3,  " +
				"\n 		CASE WHEN ISNULL(sum(ds.ds3),0) >= (ds.PD3*muc.tumuc)/100 then 'X' else '' end as DATGD3,  " +
							select_mcp +","+
							select_mcp1 +
				"\n 	 from    " +
				"\n 	 DANGKYKM_TICHLUY dk inner join DANGKYKM_TICHLUY_KHACHHANG kh_dk on kh_dk.DKKMTICHLUY_FK = dk.PK_SEQ  and dk.TRANGTHAI = 1   " +
				"\n 	 inner join KHACHHANG kh on kh.PK_SEQ = kh_dk.KHACHHANG_FK    " +
				"\n 	 inner join NHAPHANPHOI p on p.PK_SEQ = kh.NPP_FK   " +
				"\n 	 --inner join DUYETTRAKHUYENMAI_KHACHHANG duyet on duyet.khid = kh.pk_seq  " + 
				"\n 	 LEFT JOIN KHUVUC kv on kv.PK_SEQ = p.KHUVUC_FK   " +
				"\n 	 left join VUNG v on v.PK_SEQ = kv.VUNG_FK   " +
				"\n 	 LEFT JOIN PhuongXa x on x.pk_Seq = kh.PhuongXa_FK   " +
				"\n 	 left join QUANHUYEN q on q.PK_SEQ = kh.QUANHUYEN_FK   " +
				"\n 	 left join TINHTHANH t on t.PK_SEQ = kh.TINHTHANH_FK   " +
				"\n 	 left join (    " +
				"\n 		select thuongtl_fk, tumuc, denmuc, chietkhau, donvi, muc from TIEUCHITHUONGTL_TIEUCHI where thuongtl_fk in ("+obj.getpromotion()+")   " +
				"\n 	 	union all    " +
				"\n 	 	select thuongtl_fk, 0, tumuc - 1, 0, 0, -1 from TIEUCHITHUONGTL_TIEUCHI where thuongtl_fk in ("+obj.getpromotion()+") and muc = 0    " +
				"\n 	 ) muc on muc.thuongtl_fk = dk.TIEUCHITL_FK  and muc.muc + 1 = kh_dk.MUCDANGKY_FK   " +
				"\n 	 inner JOIN TIEUCHITHUONGTL tc on tc.PK_SEQ = muc.thuongtl_fk    " +
				"\n 	 left join #DONHANG ds on ds.KHACHHANG_FK = kh_dk.KHACHHANG_FK and ds.THUONGTL_FK = tc.PK_SEQ and muc.muc = ds.muc  " +
				"\n 	 LEFT JOIN (   " +
				"\n 	 	SELECT DISTINCT a.DDKD_FK, t.KHACHHANG_FK    " +
				"\n 		 	FROM KHACHHANG_TUYENBH t inner join TUYENBANHANG a on t.TBH_FK = a.PK_SEQ   " +
				"\n 	 ) d on d.KHACHHANG_FK = kh.PK_SEQ   " +
				"\n 	left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = d.DDKD_FK  " +
				"\n 	 where dk.TIEUCHITL_FK in ("+obj.getpromotion()+") "+
				"\n 	 group by  v.TEN , kv.TEN,   " +
				"\n 		p.TEN, ddkd.TEN, tc.SCHEME, kh.SMARTID, kh.TEN, kh.DIACHI,    " +
				"\n 		x.Ten,q.TEN,t.TEN,kh_dk.MUCDANGKY_FK,ds.DST, muc.tumuc,ds.PD1,ds.PD2,ds.PD3, ds.tongtien    ";
				
		if(obj.getnppId().length()>0)
			sql += " AND p.pk_seq='" + obj.getnppId() + "'";
		if(obj.getvungId().length()>0)
			sql +=" AND v.pk_seq='" + obj.getvungId() + "'";
		if(obj.getkhuvucId().length()>0)
			sql +=" AND kv.pk_seq='" + obj.getkhuvucId() + "'";
		if(obj.getDdkdId().length() > 0)
			sql += " AND ddkd.PK_sEQ = " + obj.getDdkdId();
		sql += "\n order by v.TEN, kv.TEN, p.TEN, ddkd.TEN, tc.SCHEME, kh.SMARTID";
		System.out.println("____________:\n"+sql);
		return sql;
	}

	private String getExcelQuery(ITheodoiTichluy obj) {
		String sql = "" +
				"\n IF OBJECT_ID('tempdb.dbo.#DONHANG') IS NOT NULL DROP TABLE #DONHANG " +      
				"\n CREATE TABLE #DONHANG    " +      
				"\n (  " +      
				"\n   	KHACHHANG_FK NUMERIC(18,0), " +      
				"\n   	DS FLOAT,  " +      
				"\n   	SOLUONG INT, " +      
				"\n   	THUONGTL_FK NUMERIC(18,0) " +      
				"\n ) " +      
				"\n   " +      
				"\n INSERT #DONHANG(KHACHHANG_FK, DS, SOLUONG, THUONGTL_FK) " +      
				"\n select dh.KHACHHANG_FK, SUM(sp.SOLUONG * sp.GIAMUA) as DS, SUM(sp.SOLUONG) as SOLUONG, tl.PK_SEQ  " +      
				"\n from DONHANG dh inner join DONHANG_SANPHAM sp on sp.DONHANG_FK = dh.PK_SEQ  " +      
				"\n inner join TIEUCHITHUONGTL_SANPHAM tc on tc.sanpham_fk = sp.SANPHAM_FK  " +      
				"\n inner join TIEUCHITHUONGTL tl on tc.thuongtl_fk = tl.PK_SEQ  " +      
				"\n inner join DANGKYKM_TICHLUY_KHACHHANG dk on dk.KHACHHANG_FK = dh.KHACHHANG_FK " +
				"\n inner join DANGKYKM_TICHLUY x on  dk.DKKMTICHLUY_FK = x.PK_SEQ  and x.TRANGTHAI != 2 AND x.tieuchitl_fk = tl.PK_SEQ" +      
				"\n where dh.TRANGTHAI = 1 and dh.NGAYNHAP >= tl.ngayds_tungay and dh.NGAYNHAP <= ngayds_denngay and tl.PK_SEQ in ("+obj.getpromotion()+")" +      
				"\n group by dh.KHACHHANG_FK, tl.PK_SEQ " +      
				"\n 			  " +      
				"\n select v.TEN as VUNG, kv.TEN as KHUVUC, " +      
				"\n 	p.TEN as NPP, ddkd.TEN as NVBH, tc.SCHEME, kh.SMARTID, kh.TEN as TEN, kh.DIACHI, " +      
				"\n 	ISNULL(x.Ten,'') as PHUONGXA, ISNULL(q.TEN,'') as QUANHUYEN, ISNULL(t.TEN,'') as TINHTHANH, " +      
				"\n 	--CASE WHEN kh_dk.MUCDANGKY_FK = 1 THEN 'X' ELSE '' END AS MUC1, " +      
				"\n 	--CASE WHEN kh_dk.MUCDANGKY_FK = 2 THEN 'X' ELSE '' END AS MUC2, " +      
				"\n 	--CASE WHEN kh_dk.MUCDANGKY_FK = 3 THEN 'X' ELSE '' END AS MUC3, " +      
				"\n 	kh_dk.MUCDANGKY_FK, " +      
				"\n 	ISNULL(ds.DS,0) as DOANHSO, " +      
				"\n 	CASE WHEN ISNULL(ds.DS,0) >= muc.tumuc then 'X' else '' end as DAT, " +      
				"\n 	CASE WHEN ISNULL(ds.DS,0) < muc.tumuc then 'X' else '' end as KHONGDAT " +      
				"\n from  " +      
				"\n DANGKYKM_TICHLUY dk inner join DANGKYKM_TICHLUY_KHACHHANG kh_dk on kh_dk.DKKMTICHLUY_FK = dk.PK_SEQ  and dk.TRANGTHAI = 1 " +      
				"\n inner join KHACHHANG kh on kh.PK_SEQ = kh_dk.KHACHHANG_FK  " +      
				"\n inner join NHAPHANPHOI p on p.PK_SEQ = kh.NPP_FK  " + 
				"\n  inner join DUYETTRAKHUYENMAI dkh on dkh.CTKM_FK = dk.thuongtl_fk  " + 
				"\n  inner join DUYETTRAKHUYENMAI_KHACHHANG duyet on duyet.duyetkm_fk = dkh.PK_SEQ     " + 
				"\n LEFT JOIN KHUVUC kv on kv.PK_SEQ = p.KHUVUC_FK " +      
				"\n left join VUNG v on v.PK_SEQ = kv.VUNG_FK " +      
				"\n LEFT JOIN PhuongXa x on x.pk_Seq = kh.PhuongXa_FK " +      
				"\n left join QUANHUYEN q on q.PK_SEQ = kh.QUANHUYEN_FK " +      
				"\n left join TINHTHANH t on t.PK_SEQ = kh.TINHTHANH_FK " +      
				"\n left join (  " +      
				"\n 	select thuongtl_fk, tumuc, denmuc, chietkhau, donvi, muc from TIEUCHITHUONGTL_TIEUCHI where thuongtl_fk in ("+obj.getpromotion()+") " +      
				"\n 	union all  " +      
				"\n 	select thuongtl_fk, 0, tumuc - 1, 0, 0, -1 from TIEUCHITHUONGTL_TIEUCHI where thuongtl_fk in ("+obj.getpromotion()+") and muc = 0  " +      
				"\n ) muc on muc.thuongtl_fk = dk.TIEUCHITL_FK  and muc.muc + 1 = kh_dk.MUCDANGKY_FK " +      
				"\n inner JOIN TIEUCHITHUONGTL tc on tc.PK_SEQ = muc.thuongtl_fk  " +      
				"\n left join #DONHANG ds on ds.KHACHHANG_FK = kh_dk.KHACHHANG_FK and ds.THUONGTL_FK = tc.PK_SEQ " +      
				"\n LEFT JOIN ( " +      
				"\n 	SELECT DISTINCT a.DDKD_FK, t.KHACHHANG_FK  " +      
				"\n 	FROM KHACHHANG_TUYENBH t inner join TUYENBANHANG a on t.TBH_FK = a.PK_SEQ " +
				"\n ) d on d.KHACHHANG_FK = kh.PK_SEQ " +
				"\n left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = d.DDKD_FK" +      
				"\n where dk.TIEUCHITL_FK in ("+obj.getpromotion()+")";
		if(obj.getnppId().length()>0)
			sql += " AND p.pk_seq='" + obj.getnppId() + "'";
		if(obj.getvungId().length()>0)
			sql +=" AND v.pk_seq='" + obj.getvungId() + "'";
		if(obj.getkhuvucId().length()>0)
			sql +=" AND kv.pk_seq='" + obj.getkhuvucId() + "'";
		if(obj.getDdkdId().length() > 0)
			sql += " AND ddkd.PK_sEQ = " + obj.getDdkdId();
		sql += "\n order by v.TEN, kv.TEN, p.TEN, ddkd.TEN, tc.SCHEME, kh.SMARTID";
		/*dbutils db = new dbutils();
		String sqlsub = "select dkkhuyenmai_fk from tieuchithuongtl_dktl where ctkhuyenmai_fk in  ("+obj.getpromotion()+")  order by dkkhuyenmai_fk ";
		System.out.println(sqlsub);
		ResultSet r = db.getScrol(sqlsub);
		String subdkkm = "";
		String subPivot = "";
		String subIn = "";
		String select_mcp = "";
		String select_cods = "";
		String selectall = "";
		String tongch = "";
		String  tongcods = "";
		String select_mcp1 = "";
		int col = 0;
	
		
		int i = 0;
		try {
			while(r.next()){
				subdkkm += " ISNULL(SP.["+r.getString("dkkhuyenmai_fk")+"],0) AS [CODS_"+r.getString("dkkhuyenmai_fk")+"], \n";
				select_cods += " sum(PIVOTTABLE.[" + r.getString("dkkhuyenmai_fk")+"]) as [DS_" + r.getString("dkkhuyenmai_fk")+"], \n";
			subIn += "["+r.getString("dkkhuyenmai_fk")+"], ";
			selectall += " sum([DS_" + r.getString("dkkhuyenmai_fk")+"]) AS ["+r.getString("dkkhuyenmai_fk")+"], \n";
			tongch += "["+r.getString("dkkhuyenmai_fk")+"], \n";
			 tongcods += "["+r.getString("dkkhuyenmai_fk")+"] float, \n";
			 select_mcp += " sum(ds.[" + r.getString("dkkhuyenmai_fk")+"]) as DKKM" + r.getString("dkkhuyenmai_fk")+", \n";
			 select_mcp1 += "case when sum(ds.[" + r.getString("dkkhuyenmai_fk")+"]) >= ds.tongtien then 'X' else '' end as DAT" + r.getString("dkkhuyenmai_fk")+", \n";
			i++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		subdkkm = subdkkm.substring(0, subdkkm.length() - 3);
		subIn = subIn.substring(0, subIn.length() - 2);
		select_cods = select_cods.substring(0, select_cods.length() - 3);
		selectall = selectall.substring(0, selectall.length() - 3);
		tongch = tongch.substring(0, tongch.length() - 3);
		tongcods = tongcods.substring(0, tongcods.length() - 3);
		select_mcp = select_mcp.substring(0, select_mcp.length() - 3);
		select_mcp1 = select_mcp1.substring(0, select_mcp1.length() - 3);
		
		
		
		String sql ="\n 	 IF OBJECT_ID('tempdb.dbo.#DONHANG') IS NOT NULL DROP TABLE #DONHANG   " +
				"\n 	CREATE TABLE #DONHANG      " +
				"\n 	(    " +
				"\n 	   	KHACHHANG_FK NUMERIC(18,0),   " +
				"\n 	  	muc numeric(18, 0),  " +
				"\n 	   	tiendo numeric(18,0 ),  " +
				"\n 	   	DST FLOAT,   " +
				"\n 	   	DS1 Float,  " +
				"\n 	   	DS2 Float,  " +
				"\n 	   	DS3 Float,   " +
				"\n 	  	PD1 Float,  " +
				"\n 	   	PD2 Float,  " +
				"\n 	   	PD3 Float,  " +
				"\n			TongTien float, "+
				tongcods+
				"\n 	  	,THUONGTL_FK NUMERIC(18,0)   " +
				"\n 	 )   " +
				   
				"\n 	 INSERT #DONHANG(KHACHHANG_FK, muc, DST,ds1, ds2, ds3, PD1, PD2, PD3,tongtien,"+tongch+" ,THUONGTL_FK)   " +

				"\n 	  select ds.*, pd.PD1, pd.PD2, pd.PD3, sp.tongtien,"+selectall+", pd.PK_SEQ from (  " +
				"\n 	 select PIVOTTABLE.Khachhang_fk, PIVOTTABLE.muc,PIVOTTABLE.dst, PIVOTTABLE.[0], PIVOTTABLE.[1], PIVOTTABLE.[2]  " +
				"\n 	from (  " +
				"\n 	 select dh.KHACHHANG_FK,td.muc,td.tiendo  " +
				"\n 	 ,SUM(sp.SOLUONG * sp.GIAMUA) as DS,f.ds as dst, tl.PK_SEQ  " +
				"\n 	 from DONHANG dh inner join DONHANG_SANPHAM sp on sp.DONHANG_FK = dh.PK_SEQ    " +
				"\n 	 inner join TIEUCHITHUONGTL_SANPHAM tc on tc.sanpham_fk = sp.SANPHAM_FK    " +
				"\n 	 inner join TIEUCHITHUONGTL tl on tc.thuongtl_fk = tl.PK_SEQ    " +
				"\n 	 inner join TIEUCHITHUONGTL_TIENDO td on td.thuongtl_fk = tc.thuongtl_fk  " +
				"\n 	 inner join DANGKYKM_TICHLUY_KHACHHANG dk on dk.KHACHHANG_FK = dh.KHACHHANG_FK   " +
				"\n 	 inner join DANGKYKM_TICHLUY x on  dk.DKKMTICHLUY_FK = x.PK_SEQ  and x.TRANGTHAI != 2 AND x.tieuchitl_fk = tl.PK_SEQ  " +
				"\n 	 left join (select dh.KHACHHANG_FK, SUM(sp.SOLUONG * sp.GIAMUA) as DS, SUM(sp.SOLUONG) as SOLUONG, tl.PK_SEQ    " +
				"\n 	 from DONHANG dh inner join DONHANG_SANPHAM sp on sp.DONHANG_FK = dh.PK_SEQ    " +
				"\n 	 inner join TIEUCHITHUONGTL_SANPHAM tc on tc.sanpham_fk = sp.SANPHAM_FK    " +
				"\n 	 inner join TIEUCHITHUONGTL tl on tc.thuongtl_fk = tl.PK_SEQ    " +
				"\n 	 inner join DANGKYKM_TICHLUY_KHACHHANG dk on dk.KHACHHANG_FK = dh.KHACHHANG_FK   " +
				"\n 	 inner join DANGKYKM_TICHLUY x on  dk.DKKMTICHLUY_FK = x.PK_SEQ  and x.TRANGTHAI != 2 AND x.tieuchitl_fk = tl.PK_SEQ  " +
				"\n 	 where dh.TRANGTHAI = 1 and dh.NGAYNHAP >= tl.ngayds_tungay and dh.NGAYNHAP <= ngayds_denngay and tl.PK_SEQ in (100058)  " +
				"\n 	 group by dh.KHACHHANG_FK, tl.PK_SEQ  ) f on f.KHACHHANG_FK = dh.KHACHHANG_FK   " +
				"\n 	 where dh.TRANGTHAI = 1 and dh.NGAYNHAP >= td.tungay and dh.NGAYNHAP <= td.denngay and tl.PK_SEQ in (100058)  " +
				"\n 	 group by dh.KHACHHANG_FK,td.muc, td.tiendo , tl.PK_SEQ,tl.ngayds_denngay,tl.ngayds_tungay, f.DS  " +
				"\n 	 ) st PIVOT (sum(st.ds) for st.tiendo in ([0], [1], [2]) ) AS PIVOTTABLE   " +
				"\n 	 ) DS full outer join   " +
				"\n 	  (  " +
				"\n 	 select PIVOTTABLE.Khachhang_fk, PIVOTTABLE.muc, PIVOTTABLE.[0] as PD1, PIVOTTABLE.[1] as PD2, PIVOTTABLE.[2] as PD3, PIVOTTABLE.PK_SEQ  " +
				"\n 	from (  " +
				"\n 	  select dh.KHACHHANG_FK,td.muc,td.tiendo  " +
				"\n 	 ,td.phaidat, tl.PK_SEQ  " +
				"\n 	 from DONHANG dh inner join DONHANG_SANPHAM sp on sp.DONHANG_FK = dh.PK_SEQ    " +
				"\n 	 inner join TIEUCHITHUONGTL_SANPHAM tc on tc.sanpham_fk = sp.SANPHAM_FK    " +
				"\n 	 inner join TIEUCHITHUONGTL tl on tc.thuongtl_fk = tl.PK_SEQ    " +
				"\n 	inner join TIEUCHITHUONGTL_TIENDO td on td.thuongtl_fk = tc.thuongtl_fk  " +
				"\n 	inner join DANGKYKM_TICHLUY_KHACHHANG dk on dk.KHACHHANG_FK = dh.KHACHHANG_FK   " +
				"\n 	 inner join DANGKYKM_TICHLUY x on  dk.DKKMTICHLUY_FK = x.PK_SEQ  and x.TRANGTHAI != 2 AND x.tieuchitl_fk = tl.PK_SEQ  " +
				"\n 	 left join (select dh.KHACHHANG_FK, SUM(sp.SOLUONG * sp.GIAMUA) as DS, SUM(sp.SOLUONG) as SOLUONG, tl.PK_SEQ    " +
				"\n 	 from DONHANG dh inner join DONHANG_SANPHAM sp on sp.DONHANG_FK = dh.PK_SEQ    " +
				"\n 	 inner join TIEUCHITHUONGTL_SANPHAM tc on tc.sanpham_fk = sp.SANPHAM_FK    " +
				"\n 	 inner join TIEUCHITHUONGTL tl on tc.thuongtl_fk = tl.PK_SEQ    " +
				"\n 	 inner join DANGKYKM_TICHLUY_KHACHHANG dk on dk.KHACHHANG_FK = dh.KHACHHANG_FK   " +
				"\n 	 inner join DANGKYKM_TICHLUY x on  dk.DKKMTICHLUY_FK = x.PK_SEQ  and x.TRANGTHAI != 2 AND x.tieuchitl_fk = tl.PK_SEQ  " +
				"\n 	 where dh.TRANGTHAI = 1 and dh.NGAYNHAP >= tl.ngayds_tungay and dh.NGAYNHAP <= ngayds_denngay and tl.PK_SEQ in (100058)  " +
				"\n 	 group by dh.KHACHHANG_FK, tl.PK_SEQ  ) f on f.KHACHHANG_FK = dh.KHACHHANG_FK   " +
				"\n 	 where dh.TRANGTHAI = 1 and dh.NGAYNHAP >= td.tungay and dh.NGAYNHAP <= td.denngay and tl.PK_SEQ in (100058)  " +
				"\n 	 group by dh.KHACHHANG_FK,td.muc, td.tiendo , tl.PK_SEQ,tl.ngayds_denngay,tl.ngayds_tungay,td.phaidat  " +
				"\n 	 ) st PIVOT (sum(st.phaidat) for st.tiendo in ([0], [1], [2]) ) AS PIVOTTABLE   " +
				"\n 	 ) PD on pd.KHACHHANG_FK = ds.KHACHHANG_FK and pd.muc = ds.muc  " +
				"\n 	inner join DANGKYKM_TICHLUY_KHACHHANG kh_dk on kh_dk.KHACHHANG_FK = PD.KHACHHANG_FK and kh_dk.MUCDANGKY_FK = pd.muc+1   " +
				"\n 	 left join   " +
				"\n 	 ( 	 select PIVOTTABLE.Khachhang_fk,PIVOTTABLE.muc,PIVOTTABLE.TONGTIEN, PIVOTTABLE.CTKHUYENMAI_FK,  " +select_cods+
				"\n 	from (  " +
				"\n 		 -- bất kỳ trong tổng tiền  " +
				"\n 		 select tlsp.DIEUKIENKHUYENMAI_FK,dh.KHACHHANG_FK,tlsp.SANPHAM_FK,tldk.muc,sp.SOLUONG*GIAMUA as dsmua ,dkkm.TONGTIEN, tldk.CTKHUYENMAI_FK, dkkm.LOAI   " +
				"\n 		 from dieukienkmtl_sanpham tlsp   " +
				"\n 		 inner join DONHANG_SANPHAM sp on sp.SANPHAM_FK = tlsp.SANPHAM_FK  " +
				"\n 		 inner join donhang dh on dh.PK_SEQ = sp.DONHANG_FK  " +
				"\n 		 inner join dieukienkhuyenmaitl dkkm on dkkm.PK_SEQ = tlsp.DIEUKIENKHUYENMAI_FK  " +
				"\n 		 inner join tieuchithuongtl_dktl tldk on tlsp.DIEUKIENKHUYENMAI_FK = tldk.DKKHUYENMAI_FK  " +
				"\n 		 inner join tieuchithuongtl tl on tl.PK_SEQ = tldk.CTKHUYENMAI_FK  " +
				"\n 		 where  dkkm.LOAI = 2  and dkkm.TONGTIEN > 0  and dh.trangthai = 1 and dh.NGAYNHAP >= tl.ngayds_tungay and dh.NGAYNHAP <= tl.ngayds_denngay  " +
				"\n 		   " +
				"\n 		 -- bất kỳ trong tổng lượng  " +
				"\n 		 union   " +
				"\n 		 select tlsp.DIEUKIENKHUYENMAI_FK,dh.KHACHHANG_FK,tlsp.SANPHAM_FK,tldk.muc,sp.SOLUONG*sp.GIAMUA as dsmua,dkkm.TONGLUONG*GIAMUA as TONGTIEN, tldk.CTKHUYENMAI_FK, dkkm.LOAI    " +
				"\n 		 from dieukienkmtl_sanpham tlsp   " +
				"\n 		 inner join DONHANG_SANPHAM sp on sp.SANPHAM_FK = tlsp.SANPHAM_FK  " +
				"\n 		 inner join donhang dh on dh.PK_SEQ = sp.DONHANG_FK  " +
				"\n 		 inner join dieukienkhuyenmaitl dkkm on dkkm.PK_SEQ = tlsp.DIEUKIENKHUYENMAI_FK  " +
				"\n 		 inner join tieuchithuongtl_dktl tldk on tlsp.DIEUKIENKHUYENMAI_FK = tldk.DKKHUYENMAI_FK   " +
				"\n 		 where  dkkm.LOAI = 2  and dkkm.TONGLUONG > 0   " +
				"\n 	 	 union  " +
				"\n 		 --bắt buộc tiền  " +
				"\n 	 	 select tlsp.DIEUKIENKHUYENMAI_FK,dh.KHACHHANG_FK,tlsp.SANPHAM_FK,tldk.muc,sp.SOLUONG*sp.GIAMUA as dsmua ,dkkm.TONGTIEN, tldk.CTKHUYENMAI_FK, dkkm.LOAI   " +
				"\n 	 	 from dieukienkmtl_sanpham tlsp   " +
				"\n 	 	 inner join DONHANG_SANPHAM sp on sp.SANPHAM_FK = tlsp.SANPHAM_FK  " +
				"\n 	 	 inner join donhang dh on dh.PK_SEQ = sp.DONHANG_FK  " +
				"\n 	 	 inner join dieukienkhuyenmaitl dkkm on dkkm.PK_SEQ = tlsp.DIEUKIENKHUYENMAI_FK  " +
				"\n 	 	 inner join tieuchithuongtl_dktl tldk on tlsp.DIEUKIENKHUYENMAI_FK = tldk.DKKHUYENMAI_FK  " +
				"\n 	 	 where dkkm.LOAI = 1  and dkkm.TONGTIEN > 0  " +
				"\n 		 union   " +
				"\n 		 -- bắt buộc lượng   " +
				"\n 	 	 select tlsp.DIEUKIENKHUYENMAI_FK,dh.KHACHHANG_FK,tlsp.SANPHAM_FK,tldk.muc,sp.SOLUONG*sp.GIAMUA as dsmua , dkkm.TONGLUONG*GIAMUA as TONGTIEN, tldk.CTKHUYENMAI_FK, dkkm.LOAI  " + 
				 	"\n 		 from dieukienkmtl_sanpham tlsp   " +
				"\n 		 inner join DONHANG_SANPHAM sp on sp.SANPHAM_FK = tlsp.SANPHAM_FK  " +
				 			"\n 	 	 inner join donhang dh on dh.PK_SEQ = sp.DONHANG_FK  " +
				"\n 	 	 inner join dieukienkhuyenmaitl dkkm on dkkm.PK_SEQ = tlsp.DIEUKIENKHUYENMAI_FK  " +
				"\n 	 	 inner join tieuchithuongtl_dktl tldk on tlsp.DIEUKIENKHUYENMAI_FK = tldk.DKKHUYENMAI_FK  " +
				 			"\n 	 	 where  dkkm.LOAI = 1  and dkkm.TONGLUONG > 0  " +
				"\n 	 	  ) st PIVOT (sum(st.dsmua) for st.DIEUKIENKHUYENMAI_FK in ("+subIn+" ) ) AS PIVOTTABLE   " +
				"\n 		 	  group by  PIVOTTABLE.Khachhang_fk,PIVOTTABLE.TONGTIEN	, PIVOTTABLE.CTKHUYENMAI_FK, PIVOTTABLE.muc  " +
				"\n 	 	 )sp on sp.CTKHUYENMAI_FK = pd.PK_SEQ and sp.KHACHHANG_FK = pd.KHACHHANG_FK and sp.muc = pd.muc  " +
				 	 
				"\n 		 group by ds.[0],ds.[1], ds.[2], ds.KHACHHANG_FK,sp.tongtien, ds.dst, ds.muc, pd.PD1, pd.PD2, pd.PD3, pd.PK_SEQ   " +

				 			  
				"\n 	 select v.TEN as VUNG, kv.TEN as KHUVUC,   " +
				"\n 	 	p.TEN as NPP, ddkd.TEN as NVBH, tc.SCHEME, kh.SMARTID, kh.TEN as TEN, kh.DIACHI,   " +
				"\n 		ISNULL(x.Ten,'') as PHUONGXA, ISNULL(q.TEN,'') as QUANHUYEN, ISNULL(t.TEN,'') as TINHTHANH,   " +
				"\n 		CASE WHEN kh_dk.MUCDANGKY_FK = 1 THEN 'X' ELSE '' END AS MUC1,   " +
				"\n 		CASE WHEN kh_dk.MUCDANGKY_FK = 2 THEN 'X' ELSE '' END AS MUC2,   " +
				"\n 		CASE WHEN kh_dk.MUCDANGKY_FK = 3 THEN 'X' ELSE '' END AS MUC3,   " +
				"\n 		kh_dk.MUCDANGKY_FK,   " +
				"\n 		ISNULL(ds.DST,0) as DOANHSO,ds.tongtien,   " +
				"\n 		--CASE WHEN ISNULL(ds.DST,0) >= muc.tumuc then 'X' else '' end as DAT,   " +
				"\n 		CASE WHEN ISNULL(sum(ds.ds1),0) >= (ds.PD1*muc.tumuc)/100 and "
				+ "\n 		ISNULL(sum(ds.ds2),0) >= (ds.PD2*muc.tumuc)/100 and"
				+ "\n		ISNULL(sum(ds.ds3),0) >= (ds.PD3*muc.tumuc)/100 then 'X' else '' end as DAT,   " +
				"\n 		--CASE WHEN ISNULL(ds.DST,0) < muc.tumuc then 'X' else '' end as KHONGDAT,  " +
				"\n 		CASE WHEN ISNULL(sum(ds.ds1),0) < (ds.PD1*muc.tumuc)/100 and "
				+ "\n 		ISNULL(sum(ds.ds2),0) < (ds.PD2*muc.tumuc)/100 and"
				+ "\n		ISNULL(sum(ds.ds3),0) < (ds.PD3*muc.tumuc)/100 then 'X' else '' end as KHONGDAT,  " +
				"\n 		sum(ds.ds1) AS DSGD1,   " +
				"\n 		CASE WHEN ISNULL(sum(ds.ds1),0) >= (ds.PD1*muc.tumuc)/100 then 'X' else '' end as DATGD1,  " +
				"\n 		sum(ds.ds2) AS DSGD2,   " +
				"\n 		CASE WHEN ISNULL(sum(ds.ds2),0) >= (ds.PD2*muc.tumuc)/100 then 'X' else '' end as DATGD2,  " +
				"\n 		sum(ds.ds3) AS DSGD3,  " +
				"\n 		CASE WHEN ISNULL(sum(ds.ds3),0) >= (ds.PD3*muc.tumuc)/100 then 'X' else '' end as DATGD3,  " +
							select_mcp +","+
							select_mcp1 +
				"\n 	 from    " +
				"\n 	 DANGKYKM_TICHLUY dk inner join DANGKYKM_TICHLUY_KHACHHANG kh_dk on kh_dk.DKKMTICHLUY_FK = dk.PK_SEQ  and dk.TRANGTHAI = 1   " +
				"\n 	 inner join KHACHHANG kh on kh.PK_SEQ = kh_dk.KHACHHANG_FK    " +
				"\n 	 inner join NHAPHANPHOI p on p.PK_SEQ = kh.NPP_FK   " +
				"\n 	 --inner join DUYETTRAKHUYENMAI_KHACHHANG duyet on duyet.khid = kh.pk_seq  " + 
				"\n 	 LEFT JOIN KHUVUC kv on kv.PK_SEQ = p.KHUVUC_FK   " +
				"\n 	 left join VUNG v on v.PK_SEQ = kv.VUNG_FK   " +
				"\n 	 LEFT JOIN PhuongXa x on x.pk_Seq = kh.PhuongXa_FK   " +
				"\n 	 left join QUANHUYEN q on q.PK_SEQ = kh.QUANHUYEN_FK   " +
				"\n 	 left join TINHTHANH t on t.PK_SEQ = kh.TINHTHANH_FK   " +
				"\n 	 left join (    " +
				"\n 		select thuongtl_fk, tumuc, denmuc, chietkhau, donvi, muc from TIEUCHITHUONGTL_TIEUCHI where thuongtl_fk in ("+obj.getpromotion()+")   " +
				"\n 	 	union all    " +
				"\n 	 	select thuongtl_fk, 0, tumuc - 1, 0, 0, -1 from TIEUCHITHUONGTL_TIEUCHI where thuongtl_fk in ("+obj.getpromotion()+") and muc = 0    " +
				"\n 	 ) muc on muc.thuongtl_fk = dk.TIEUCHITL_FK  and muc.muc + 1 = kh_dk.MUCDANGKY_FK   " +
				"\n 	 inner JOIN TIEUCHITHUONGTL tc on tc.PK_SEQ = muc.thuongtl_fk    " +
				"\n 	 left join #DONHANG ds on ds.KHACHHANG_FK = kh_dk.KHACHHANG_FK and ds.THUONGTL_FK = tc.PK_SEQ and muc.muc = ds.muc  " +
				"\n 	 LEFT JOIN (   " +
				"\n 	 	SELECT DISTINCT a.DDKD_FK, t.KHACHHANG_FK    " +
				"\n 		 	FROM KHACHHANG_TUYENBH t inner join TUYENBANHANG a on t.TBH_FK = a.PK_SEQ   " +
				"\n 	 ) d on d.KHACHHANG_FK = kh.PK_SEQ   " +
				"\n 	left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = d.DDKD_FK  " +
				"\n 	 where dk.TIEUCHITL_FK in ("+obj.getpromotion()+") "+
				"\n 	 group by  v.TEN , kv.TEN,   " +
				"\n 		p.TEN, ddkd.TEN, tc.SCHEME, kh.SMARTID, kh.TEN, kh.DIACHI,    " +
				"\n 		x.Ten,q.TEN,t.TEN,kh_dk.MUCDANGKY_FK,ds.DST, muc.tumuc,ds.PD1,ds.PD2,ds.PD3, ds.tongtien    ";
				
		if(obj.getnppId().length()>0)
			sql += " AND p.pk_seq='" + obj.getnppId() + "'";
		if(obj.getvungId().length()>0)
			sql +=" AND v.pk_seq='" + obj.getvungId() + "'";
		if(obj.getkhuvucId().length()>0)
			sql +=" AND kv.pk_seq='" + obj.getkhuvucId() + "'";
		if(obj.getDdkdId().length() > 0)
			sql += " AND ddkd.PK_sEQ = " + obj.getDdkdId();
		sql += "\n order by v.TEN, kv.TEN, p.TEN, ddkd.TEN, tc.SCHEME, kh.SMARTID";
		System.out.println("____________:\n"+sql);*/
		return sql;
	}

	
	
	private void ExportToExcel(OutputStream out, ITheodoiTichluy obj, String query) {
		try{ 
			//String chuoi=getServletContext().getInitParameter("path") + "\\BCTheodoiTichluy.xlsm";
			
			//FileInputStream fstream = new FileInputStream(chuoi);
			File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\BCTheodoiTichluy.xlsm");
			FileInputStream fstream = new FileInputStream(f) ;
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			
			FillData(workbook, obj, query);
			System.out.println(query);
			
			workbook.save(out);	
			fstream.close();
		}catch(Exception ex){
			
		}
	}
	
	
	private void ExportToExcel1(OutputStream out, ITheodoiTichluy obj, String query) {
		try{ 
			//String chuoi=getServletContext().getInitParameter("path") + "\\BCTheodoiTichluy.xlsm";
			
			//FileInputStream fstream = new FileInputStream(chuoi);
			File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\BCTheodoiTichluy.xlsm");
			FileInputStream fstream = new FileInputStream(f) ;
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			
			FillData1(workbook, obj, query);
			System.out.println(query);
			workbook.save(out);	
			fstream.close();
		}catch(Exception ex){
			
		}
	}
	
	private void FillData(Workbook workbook, ITheodoiTichluy obj, String query) throws SQLException {
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");

		Cells cells = worksheet.getCells();
		cells.setRowHeight(0, 50.0f);
		Cell cell = cells.getCell("A1");
		ReportAPI.getCellStyle(cell, Color.RED, true, 16, "BÁO CÁO THEO DÕI TÍCH LŨY");
		
		
		dbutils db = new dbutils();
		ResultSet rs = db.get(query);
		try {
		String sql = "select DISTINCT muc + 1 as MUC from TIEUCHITHUONGTL_TIEUCHI where thuongtl_fk in ("+obj.getpromotion()+")";
		ResultSet muc = db.get(sql);
		
		
	    int countRow = 3;
	    
	    Style s = cell.getStyle();
	    s.setTextWrapped(true);
	    s.setHAlignment(TextAlignmentType.CENTER);
	    s.setVAlignment(TextAlignmentType.JUSTIFY);
	    
	    int col = 0;
	    cell = cells.getCell(countRow, col++);
	    cell.setStyle(s);
    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
    	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, "STT");
    	
    	cell = cells.getCell(countRow, col++);
	    cell.setStyle(s);
    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
    	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, "VÙNG");
    	
    	cell = cells.getCell(countRow, col++);
	    cell.setStyle(s);
    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
    	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, "KHU VỰC");
    	
    	cell = cells.getCell(countRow, col++);
	    cell.setStyle(s);
    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
    	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, "NHÀ PHÂN PHỐI");
    	
    	cell = cells.getCell(countRow, col++);
	    cell.setStyle(s);
    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
    	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, "NVBH");
    	
    	cell = cells.getCell(countRow, col++);
	    cell.setStyle(s);
    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
    	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, "SCHEME");
    	
    	cell = cells.getCell(countRow, col++);
	    cell.setStyle(s);
    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
    	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, "MÃ KHÁCH HÀNG");
    	
    	cell = cells.getCell(countRow, col++);
	    cell.setStyle(s);
    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
    	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, "TÊN KHÁCH HÀNG");
    	
    	cell = cells.getCell(countRow, col++);
	    cell.setStyle(s);
    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
    	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, "ĐỊA CHỈ");
    	
    	cell = cells.getCell(countRow, col++);
	    cell.setStyle(s);
    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
    	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, "PHƯỜNG XÃ");
    	
    	cell = cells.getCell(countRow, col++);
	    cell.setStyle(s);
    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
    	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, "QUẬN HUYỆN");
    	
    	cell = cells.getCell(countRow, col++);
	    cell.setStyle(s);
    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
    	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, "TỈNH THÀNH");
    	
    	////////////////////////
    	int countmuc = 0;
    	while(muc.next()){
    		cell = cells.getCell(countRow, col++);
    	    cell.setStyle(s);
        	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
        	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, "MỨC " + muc.getString("MUC"));
        	countmuc ++;
    	}
    	///////////////////////
    	
    	cell = cells.getCell(countRow, col++);
	    cell.setStyle(s);
    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
    	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, "DOANH SỐ");
    	
    	cell = cells.getCell(countRow, col++);
	    cell.setStyle(s);
    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
    	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, "ĐẠT");
    	
    	cell = cells.getCell(countRow, col++);
	    cell.setStyle(s);
    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
    	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, "KHÔNG ĐẠT");
    	
    	//////////////////Tiep
    	
	    
	    countRow ++;
	    NumberFormat formatter = new DecimalFormat("#,###,###"); 
	    
	    while(rs.next())
	    {
	    	col = 0;
		    cell = cells.getCell(countRow, col++);
	    	ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, (countRow -3) + "");
	    	
	    	cell = cells.getCell(countRow, col++);
	    	ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, rs.getString("VUNG"));
	    	
	    	cell = cells.getCell(countRow, col++);
	    	ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, rs.getString("KHUVUC"));
	    	
	    	cell = cells.getCell(countRow, col++);
	    	ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, rs.getString("NPP"));
	    	
	    	cell = cells.getCell(countRow, col++);
	    	ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, rs.getString("NVBH"));
	    	
	    	cell = cells.getCell(countRow, col++);
	    	ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, rs.getString("SCHEME"));
	    	
	    	cell = cells.getCell(countRow, col++);
	    	ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, rs.getString("SMARTID"));
	    	
	    	cell = cells.getCell(countRow, col++);
	    	ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, rs.getString("TEN"));
	    	
	    	cell = cells.getCell(countRow, col++);
	    	ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, rs.getString("DIACHI"));
	    	
	    	cell = cells.getCell(countRow, col++);
	    	ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, rs.getString("PHUONGXA"));
	    	
	    	cell = cells.getCell(countRow, col++);
	    	ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, rs.getString("QUANHUYEN"));
	    	
	    	cell = cells.getCell(countRow, col++);
	    	ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, rs.getString("TINHTHANH"));
	    	
	    	////////////////////////
	    	
	    	for(int i=1; i<= countmuc; i++){
	    		cell = cells.getCell(countRow, col++);
	    	    cell.setStyle(s);
	        	if(rs.getInt("MUCDANGKY_FK") == i)
	        		ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, "X");
	        	else
	        		ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, "");
	    	}
	    	///////////////////////
	    	
	    	cell = cells.getCell(countRow, col++);
	    	
	    	cell.setValue(rs.getDouble("DOANHSO"));
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41, TextAlignmentType.RIGHT);
	    	
	    	cell = cells.getCell(countRow, col++);
	    	cell.setStyle(s);
	    	ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, rs.getString("DAT"));
	    	
	    	cell = cells.getCell(countRow, col++);
	    	cell.setStyle(s);
	    	ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, rs.getString("KHONGDAT"));
	    	
	    	
	    	
	    	countRow ++;
	    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.update("IF OBJECT_ID('tempdb.dbo.#DONHANG') IS NOT NULL DROP TABLE #DONHANG");
	    if(rs!=null)
	    	rs.close();
	    if (db != null)
	    	db.shutDown();
	}
	
	
	private void FillData1(Workbook workbook, ITheodoiTichluy obj, String query) throws SQLException {
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");

		Cells cells = worksheet.getCells();
		cells.setRowHeight(0, 50.0f);
		Cell cell = cells.getCell("A1");
		ReportAPI.getCellStyle(cell, Color.RED, true, 16, "BÁO CÁO THEO DÕI TÍCH LŨY");
		
		
		dbutils db = new dbutils();
		ResultSet rs = db.get(query);
		try {
		String sql = "select DISTINCT muc + 1 as MUC from TIEUCHITHUONGTL_TIEUCHI where thuongtl_fk in ("+obj.getpromotion()+")";
		ResultSet muc = db.get(sql);
		
		
		String sqldkmua = "select dkkhuyenmai_fk from TIEUCHITHUONGTL_dktl where ctkhuyenmai_fk in ("+obj.getpromotion()+") order by dkkhuyenmai_fk";
		ResultSet dkmua = db.get(sqldkmua);
		
	   
	    int countRow = 3;
	    
	    Style s = cell.getStyle();
	    s.setTextWrapped(true);
	    s.setHAlignment(TextAlignmentType.CENTER);
	    s.setVAlignment(TextAlignmentType.JUSTIFY);
	    
	    int col = 0;
	    cell = cells.getCell(countRow, col++);
	    cell.setStyle(s);
    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
    	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, "STT");
    	
    	cell = cells.getCell(countRow, col++);
	    cell.setStyle(s);
    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
    	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, "VÙNG");
    	
    	cell = cells.getCell(countRow, col++);
	    cell.setStyle(s);
    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
    	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, "KHU VỰC");
    	
    	cell = cells.getCell(countRow, col++);
	    cell.setStyle(s);
    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
    	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, "NHÀ PHÂN PHỐI");
    	
    	cell = cells.getCell(countRow, col++);
	    cell.setStyle(s);
    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
    	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, "NVBH");
    	
    	cell = cells.getCell(countRow, col++);
	    cell.setStyle(s);
    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
    	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, "SCHEME");
    	
    	cell = cells.getCell(countRow, col++);
	    cell.setStyle(s);
    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
    	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, "MÃ KHÁCH HÀNG");
    	
    	cell = cells.getCell(countRow, col++);
	    cell.setStyle(s);
    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
    	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, "TÊN KHÁCH HÀNG");
    	
    	cell = cells.getCell(countRow, col++);
	    cell.setStyle(s);
    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
    	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, "ĐỊA CHỈ");
    	
    	cell = cells.getCell(countRow, col++);
	    cell.setStyle(s);
    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
    	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, "PHƯỜNG XÃ");
    	
    	cell = cells.getCell(countRow, col++);
	    cell.setStyle(s);
    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
    	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, "QUẬN HUYỆN");
    	
    	cell = cells.getCell(countRow, col++);
	    cell.setStyle(s);
    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
    	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, "TỈNH THÀNH");
    	
    	////////////////////////
    	int countmuc = 0;
    	while(muc.next()){
    		cell = cells.getCell(countRow, col++);
    	    cell.setStyle(s);
        	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
        	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, "MỨC " + muc.getString("MUC"));
        	countmuc ++;
    	}
    	///////////////////////
    	
    	cell = cells.getCell(countRow, col++);
	    cell.setStyle(s);
    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
    	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, "DOANH SỐ");
    	
    	cell = cells.getCell(countRow, col++);
	    cell.setStyle(s);
    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
    	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, "ĐẠT");
    	
    	cell = cells.getCell(countRow, col++);
	    cell.setStyle(s);
    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
    	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, "KHÔNG ĐẠT");
    	
    	//////////////////Tiep
    	
    	cell = cells.getCell(countRow, col++);
	    cell.setStyle(s);
    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
    	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, "DS GĐ1");
    	
    	
    	cell = cells.getCell(countRow, col++);
	    cell.setStyle(s);
    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
    	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, "ĐẠT");
    	
    	cell = cells.getCell(countRow, col++);
	    cell.setStyle(s);
    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
    	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, "DS GĐ2");
    	
    	
    	cell = cells.getCell(countRow, col++);
	    cell.setStyle(s);
    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
    	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, "ĐẠT");
    	
    	
    	cell = cells.getCell(countRow, col++);
	    cell.setStyle(s);
    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
    	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, "DS GĐ3");
    	
    	
    	cell = cells.getCell(countRow, col++);
	    cell.setStyle(s);
    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
    	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, "ĐẠT");
    	
    	
    	int countdkmua = 0;
    	while(dkmua.next()){
    		cell = cells.getCell(countRow, col++);
    	    cell.setStyle(s);
        	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
        	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, "DKMUA " + dkmua.getString("dkkhuyenmai_fk"));
        	
        	
        	cell = cells.getCell(countRow, col++);
    	    cell.setStyle(s);
        	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
        	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, "ĐẠT " + dkmua.getString("dkkhuyenmai_fk"));
        	countdkmua ++;
    	}
    	
    	

	    
	    countRow ++;
	    NumberFormat formatter = new DecimalFormat("#,###,###"); 
	    
	    while(rs.next())
	    {
	    	col = 0;
		    cell = cells.getCell(countRow, col++);
	    	ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, (countRow -3) + "");
	    	
	    	cell = cells.getCell(countRow, col++);
	    	ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, rs.getString("VUNG"));
	    	
	    	cell = cells.getCell(countRow, col++);
	    	ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, rs.getString("KHUVUC"));
	    	
	    	cell = cells.getCell(countRow, col++);
	    	ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, rs.getString("NPP"));
	    	
	    	cell = cells.getCell(countRow, col++);
	    	ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, rs.getString("NVBH"));
	    	
	    	cell = cells.getCell(countRow, col++);
	    	ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, rs.getString("SCHEME"));
	    	
	    	cell = cells.getCell(countRow, col++);
	    	ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, rs.getString("SMARTID"));
	    	
	    	cell = cells.getCell(countRow, col++);
	    	ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, rs.getString("TEN"));
	    	
	    	cell = cells.getCell(countRow, col++);
	    	ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, rs.getString("DIACHI"));
	    	
	    	cell = cells.getCell(countRow, col++);
	    	ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, rs.getString("PHUONGXA"));
	    	
	    	cell = cells.getCell(countRow, col++);
	    	ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, rs.getString("QUANHUYEN"));
	    	
	    	cell = cells.getCell(countRow, col++);
	    	ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, rs.getString("TINHTHANH"));
	    	
	    	////////////////////////
	    	
	    	for(int i=1; i<= countmuc; i++){
	    		cell = cells.getCell(countRow, col++);
	    	    cell.setStyle(s);
	        	if(rs.getInt("MUCDANGKY_FK") == i)
	        		ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, "X");
	        	else
	        		ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, "");
	    	}
	    	///////////////////////
	    	
	    	cell = cells.getCell(countRow, col++);
	    	
	    	cell.setValue(rs.getDouble("DOANHSO"));
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41, TextAlignmentType.RIGHT);
	    	
	    	cell = cells.getCell(countRow, col++);
	    	cell.setStyle(s);
	    	ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, rs.getString("DAT"));
	    	
	    	cell = cells.getCell(countRow, col++);
	    	cell.setStyle(s);
	    	ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, rs.getString("KHONGDAT"));
	    	
	    	////////////////TIep
	    	cell = cells.getCell(countRow, col++);
	    	cell.setValue(rs.getDouble("DSGD1"));
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41, TextAlignmentType.RIGHT);
			
			
	    	cell = cells.getCell(countRow, col++);
	    	cell.setStyle(s);
	    	ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, rs.getString("DATGD1"));
	    	
	    	
	    	cell = cells.getCell(countRow, col++);
	    	cell.setValue(rs.getDouble("DSGD2"));
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41, TextAlignmentType.RIGHT);
			
			
	    	cell = cells.getCell(countRow, col++);
	    	cell.setStyle(s);
	    	ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, rs.getString("DATGD2"));
	    	
	    	
	    	cell = cells.getCell(countRow, col++);
	    	cell.setValue(rs.getDouble("DSGD3"));
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41, TextAlignmentType.RIGHT);
	    	
	    	cell = cells.getCell(countRow, col++);
	    	cell.setStyle(s);
	    	ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, rs.getString("DATGD3"));
	    	sqldkmua = "select dkkhuyenmai_fk from TIEUCHITHUONGTL_dktl where ctkhuyenmai_fk in ("+obj.getpromotion()+") order by dkkhuyenmai_fk";
			dkmua = db.get(sqldkmua);
	    	
	    	int k= 0;
	    	while(dkmua.next()){
	    		cell = cells.getCell(countRow, col++);
	        	cell.setValue(rs.getDouble("DKKM" + dkmua.getString("dkkhuyenmai_fk")+""));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41, TextAlignmentType.RIGHT);
	        	cell = cells.getCell(countRow, col++);
	    	    cell.setStyle(s);
	        	ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, rs.getString("DAT" + dkmua.getString("dkkhuyenmai_fk")+""));
	    	k++;
	    	}
	    	
	    	countRow ++;
	    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    if(rs!=null)
	    	rs.close();
	    if (db != null)
	    	db.shutDown();
	}
	
	private String getdate() 
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy: hh:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);	
	} 	
}

