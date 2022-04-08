package geso.dms.center.servlets.reports;

import geso.dms.center.beans.report.IBcSuDungKhuyenMai;
import geso.dms.center.beans.report.imp.BcSuDungKhuyenMai;
import geso.dms.center.beans.tinnhan.ITinNhan;
import geso.dms.center.beans.tinnhan.imp.TinNhan;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
import com.aspose.cells.Range;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

@WebServlet("/BcSuDungKhuyenMaiSvl")
public class BcSuDungKhuyenMaiSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public BcSuDungKhuyenMaiSvl()
	{
		super();
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IBcSuDungKhuyenMai obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		
		Utility util = new Utility();
		
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		
		if (userId.length() == 0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		
		String loaihoadon = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaihoadon"));
		if (loaihoadon == null)
			loaihoadon = "0";
		
		obj = new BcSuDungKhuyenMai();
		obj.setUserId(userId);
		obj.setView("TT");
		String nextJSP = "";
		obj.init("");
		
		nextJSP = "/AHF/pages/Center/BcSuDungKhuyenMai.jsp";
		session.setAttribute("obj", obj);
		response.sendRedirect(nextJSP);
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		Utility util = new Utility();
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		HttpSession session = request.getSession();
		
		OutputStream out = response.getOutputStream();
		
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		if (action == null)
			action = "";
		
		IBcSuDungKhuyenMai obj = new BcSuDungKhuyenMai();
		obj.setUserId(userId);
		
		obj.setView("TT");
		
		String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays") == null ? "" : request.getParameter("Sdays"));
		obj.setTuNgay(tungay);
		
		String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays") == null ? "" : request.getParameter("Edays"));
		obj.setDenNgay(denngay);
		
		String vungId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId") == null ? "" : request.getParameter("vungId"));
		obj.setVungId(vungId);
		
		String kbhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhId") == null ? "" : request.getParameter("kbhId"));
		obj.setKbhId(kbhId);
		
		String ttId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ttId") == null ? "" : request.getParameter("ttId"));
		obj.setTtId(ttId);
		
		String nhomId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhomId") == null ? "" : request.getParameter("nhomId"));
		obj.setNhomId(nhomId);
		
		String khId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khId") == null ? "" : request.getParameter("khId"));
		obj.setKhId(khId);
		
		String ddkdId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdId") == null ? "" : request.getParameter("ddkdId"));
		obj.setDdkdId(ddkdId);
		
		String spId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("spId") == null ? "" : request.getParameter("spId"));
		obj.setSpId(spId);
		
		String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId") == null ? "" : request.getParameter("nppId"));
		obj.setNppId(nppId);
		
		String kvId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kvId") == null ? "" : request.getParameter("kvId"));
		obj.setKvId(kvId);
		
		String muclay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("muclay") == null ? "0" : request.getParameter("muclay"));
		obj.setMuclay(muclay);
	
		 String[] ctkmId = request.getParameterValues("ctkmId");
	    obj.setCtkmId(Doisangchuoi(ctkmId));
		obj.setAction(action);
		if (action.equals("excel"))
		{
			try
			{
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BcSuDungKhuyenMai_Details.xlsm");
				//FileInputStream fstream = new FileInputStream( getServletContext().getInitParameter("path") + "\\BcSuDungKhuyenMai_Details.xlsm");
				File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\BcSuDungKhuyenMai_Details.xlsm");
				FileInputStream fstream = new FileInputStream(f) ;
				
				Workbook workbook = new Workbook();
				workbook.open(fstream);
				workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
				CreateStaticHeader_ByCtKmId(workbook, obj);
				obj.setUserId(userId);
				obj.init("");
				String query = obj.getQueryHd();
				if(muclay.equals("0"))
				{
					FillData_TongHop(workbook,obj,query);
				}
				else 
				{
					FillData_Details(workbook,obj,query);
				}
				
				
				workbook.save(out);
				fstream.close();
			} catch (Exception ex)
			{
				ex.printStackTrace();
				obj.setMsg("Khong the tao pivot.");
			}
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			String nextJSP = "";
			nextJSP = "/AHF/pages/Center/BcSuDungKhuyenMai.jsp";
			response.sendRedirect(nextJSP);
		} else if (action.equals("view") || action.equals("next") || action.equals("prev"))
		{
			obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
			obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
			obj.setUserId(userId);
			obj.init("");
			session.setAttribute("obj", obj);
			response.sendRedirect("/AHF/pages/Center/BcSuDungKhuyenMai.jsp");
		}
		
		else if (action.equals("search"))
		{
			obj.setUserId(userId);
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			obj.init("");
			String nextJSP = "";
			nextJSP = "/AHF/pages/Center/BcSuDungKhuyenMai.jsp";
			response.sendRedirect(nextJSP);
		} else
		{
			session.setAttribute("obj", obj);
			String nextJSP = "";
			nextJSP = "/AHF/pages/Center/BcSuDungKhuyenMai.jsp";
			obj.init("");
			response.sendRedirect(nextJSP);
		}
		
	}
	
	private boolean FillData_TongHop(Workbook workbook, IBcSuDungKhuyenMai obj, String query) throws Exception
	{
		dbutils db = new dbutils();
		
		String sql=
		"		select c.PK_SEQ as sanpham_fk,c.MA as spMa,c.TEN as spTen           "+
		"		from donhang a inner join donhang_ctkm_trakm b on b.donhangid=a.pk_seq "+          
		"			inner join sanpham c on c.ma=b.spma    "+       
		"		where a.trangthai!=2 and b.soluong!=0 "+       
		"			and b.CTKMID in ("+obj.getCtkmId()+" )   "+
		"		group by c.PK_SEQ ,c.MA,c.TEN ";   
		 ResultSet rs=db.get(sql);
		 
		 String[] spKmIds = null;
		 String[] spKmTens = null;
		 String spKmId="";
		 String spKmTEN="";
		 String chuoiselct="";
		 
		 String chuoiselctSUM="";
		 
		 String chuoingoac="";
		 int i=0;
		 try 
		 {
			while (rs.next())
			{
				 if(i==0)
				 {
					 chuoingoac="["+rs.getString("sanpham_fk")+"]";
					 chuoiselct = "isnull(km.["+rs.getString("sanpham_fk")+"],0) AS km"+rs.getString("sanpham_fk");
					 
					 chuoiselctSUM = "SUM( isnull(km.["+rs.getString("sanpham_fk")+"],0)) AS km"+rs.getString("sanpham_fk");
					 
					 spKmId = rs.getString("sanpham_fk");
					 spKmTEN = rs.getString("spTen");
				 }else
				 {
					 chuoiselct += "," + "isnull(km.["+rs.getString("sanpham_fk")+"],0) AS km"+rs.getString("sanpham_fk");
					 chuoiselctSUM += "," + "SUM( isnull(km.["+rs.getString("sanpham_fk")+"],0)) AS km"+rs.getString("sanpham_fk");
					 
					 chuoingoac =chuoingoac+",["+rs.getString("sanpham_fk")+"]";
					 spKmId +="__" + rs.getString("sanpham_fk") ;
					 spKmTEN +="__" + rs.getString("spTen") ;
				 }
				 i++;
			 }
			spKmIds=spKmId.split("__");
			spKmTens=spKmTEN.split("__");
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		 
		 sql=
			"   		select  b.SANPHAM_FK,f.ten as spTen             \n "+      
			"   		from donhang a inner join donhang_sanpham b on b.donhang_fk=a.pk_seq             \n "+      
			"   		inner join              \n "+      
			"   		(             \n "+      
			"   			select donhangid ,ctkmid,sum(soluong) as soluong             \n "+      
			"   			from donhang_ctkm_trakm              \n "+      
			"      \n "+      
			"   		where spma is not null             \n "+      
			"   			group by donhangid,ctkmid             \n "+      
			"   		)c on c.donhangid=b.donhang_fk             \n "+      
			"   			inner join ctkm_dkkm d on d.ctkhuyenmai_fk=c.ctkmid             \n "+      
			"   			inner join dieukienkm_sanpham e on e.dieukienkhuyenmai_fk=d.dkkhuyenmai_fk and e.sanpham_fk=b.sanpham_fk             \n "+      
			"   			inner join sanpham f on f.pk_seq=e.sanpham_fk             \n "+      
			"   		where a.trangthai!=2             \n "+      
			"   			and d.CTKHUYENMAI_FK in ("+obj.getCtkmId()+" )     \n "+      
			"   		group by b.SANPHAM_FK ,f.ten                  \n ";
		 
		 rs=db.get(sql);
		 String[] spBanIds = null;
		 String[] spBanTENs = null;
		 String spBanId="";
		 String spBanTEN="";
		 
		 String chuoiselct_BAN="";
		 String chuoiselct_BANSUM="";
		 String chuoingoac_BAN="";
		 i=0;
		 try 
		 {
			while (rs.next())
			{
				 if(i==0)
				 {
					 chuoingoac_BAN="["+rs.getString("sanpham_fk")+"]";
					 chuoiselct_BAN = "isnull(ban.["+rs.getString("sanpham_fk")+"],0) AS ban"+rs.getString("sanpham_fk");
					 chuoiselct_BANSUM = "SUM( isnull(ban.["+rs.getString("sanpham_fk")+"],0)) AS ban"+rs.getString("sanpham_fk");
					 
					 
					 spBanId  = rs.getString("sanpham_fk");
					 spBanTEN = rs.getString("spTEN");
				 }else
				 {
					 chuoiselct_BAN += "," + "isnull(ban.["+rs.getString("sanpham_fk")+"],0) AS ban"+rs.getString("sanpham_fk");
					 chuoiselct_BANSUM += "," + "SUM( isnull(ban.["+rs.getString("sanpham_fk")+"],0)) AS ban"+rs.getString("sanpham_fk") ;
					 
					 chuoingoac_BAN += ",["+rs.getString("sanpham_fk")+"]";
					 spBanId +="__" + rs.getString("sanpham_fk") ;
					 spBanTEN +="__" + rs.getString("spTEN") ;
				 }
				 i++;
			 }
			spBanIds =spBanId.split("__");
			spBanTENs  =spBanTEN.split("__");
			System.out.println("[spKmIds]"+spKmIds.length);
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		query=
		"select   " +
		" COUNT(kv.TEN)	OVER( PARTITION BY v.ten,kv.ten ) AS 'Row' , \n "+      
		"   	v.ten as vTEN,kv.TEN as kvTEN,npp.MA as nppMA,npp.TEN as nppTEN,   \n "+      
		"     data.NPP_FK , "+chuoiselct+","+chuoiselct_BAN+",1 as Loai  \n "+      
		"   from   \n "+      
		"   (   \n "+      
		"   	select a.npp_fk           \n "+      
		"   	from donhang a inner join donhang_ctkm_trakm b on b.donhangid=a.pk_seq                  \n "+      
		"   	where a.trangthai!=2 and b.soluong!=0      \n "+      
		"   		and b.CTKMID in ("+obj.getCtkmId()+" )     \n "+      
		"   	group by a.npp_fk            \n "+      
		"   )as data   \n "+      
		"   left join    \n "+      
		"   (   \n "+      
		"   	select NPP_FK,"+chuoingoac+"   \n "+      
		"   	from   \n "+      
		"   	(   \n "+      
		"   		select a.npp_fk,sum(b.soluong*c.trongluong) as SoLuong,c.PK_SEQ as sanpham_fk             \n "+      
		"   		from donhang a inner join donhang_ctkm_trakm b on b.donhangid=a.pk_seq             \n "+      
		"   			inner join sanpham c on c.ma=b.spma             \n "+      
		"   		where a.trangthai!=2 and b.soluong!=0          \n "+      
		"   			and b.CTKMID in ("+obj.getCtkmId()+" )     \n "+      
		"   		group by a.npp_fk ,c.PK_SEQ            \n "+      
		"   	) as p	PIVOT ( 		 sum(SoLuong) 	 FOR sanpham_fk IN ("+chuoingoac+" )  ) AS t   \n "+      
		"   )as km on km.NPP_FK=data.NPP_FK   \n "+      
		"   left join    \n "+      
		"   (   \n "+      
		"   	select npp_fk,"+chuoingoac_BAN+"    \n "+      
		"   	from    \n "+      
		"   	(   \n "+      
		"   		select a.npp_fk,sum(b.soluong*f.trongluong) as soluong,b.SANPHAM_FK             \n "+      
		"   		from donhang a inner join donhang_sanpham b on b.donhang_fk=a.pk_seq             \n "+      
		"   		inner join              \n "+      
		"   		(             \n "+      
		"   			select donhangid ,ctkmid,sum(soluong) as soluong             \n "+      
		"   			from donhang_ctkm_trakm              \n "+      
		"      \n "+      
		"   		where spma is not null             \n "+      
		"   			group by donhangid,ctkmid             \n "+      
		"   		)c on c.donhangid=b.donhang_fk             \n "+      
		"   			inner join ctkm_dkkm d on d.ctkhuyenmai_fk=c.ctkmid             \n "+      
		"   			inner join dieukienkm_sanpham e on e.dieukienkhuyenmai_fk=d.dkkhuyenmai_fk and e.sanpham_fk=b.sanpham_fk             \n "+      
		"   			inner join sanpham f on f.pk_seq=e.sanpham_fk             \n "+      
		"   		where a.trangthai!=2             \n "+      
		"   			and d.CTKHUYENMAI_FK in ("+obj.getCtkmId()+" )     \n "+      
		"   		group by a.npp_fk,b.SANPHAM_FK                   \n "+      
		"   	) as p	PIVOT ( 		 sum(SoLuong) 	 FOR sanpham_fk IN 	( "+chuoingoac_BAN+"  )  ) AS t   \n "+      
		"   )as ban on ban.NPP_FK=data.NPP_FK   \n "+      
		"   INNER JOIN NHAPHANPHOI npp on npp.PK_SEQ=data.NPP_FK   \n "+      
		"   inner join KHUVUC kv on kv.PK_SEQ=npp.KHUVUC_FK       \n "+      
		"   inner join VUNG v on v.PK_SEQ=kv.VUNG_FK         \n "+      
		" union all  "+
		"select   " +
		" COUNT(kv.TEN)	OVER( PARTITION BY v.ten,kv.ten ) AS 'Row' , \n "+      
		"   	v.ten as vTEN,kv.TEN + ' Total'  as kvTEN,''  as nppMA,'' as nppTEN,   \n "+      
		" NULL AS NPP_FK , "+chuoiselctSUM+","+chuoiselct_BANSUM+",2 as Loai  \n "+      
		"   from   \n "+      
		"   (   \n "+      
		"   	select a.npp_fk           \n "+      
		"   	from donhang a inner join donhang_ctkm_trakm b on b.donhangid=a.pk_seq                  \n "+      
		"   	where a.trangthai!=2 and b.soluong!=0      \n "+      
		"   		and b.CTKMID in ("+obj.getCtkmId()+" )     \n "+      
		"   	group by a.npp_fk            \n "+      
		"   )as data   \n "+      
		"   left join    \n "+      
		"   (   \n "+      
		"   	select NPP_FK,"+chuoingoac+"   \n "+      
		"   	from   \n "+      
		"   	(   \n "+      
		"   		select a.npp_fk,sum(b.soluong*c.trongluong) as SoLuong,c.PK_SEQ as sanpham_fk             \n "+      
		"   		from donhang a inner join donhang_ctkm_trakm b on b.donhangid=a.pk_seq             \n "+      
		"   			inner join sanpham c on c.ma=b.spma             \n "+      
		"   		where a.trangthai!=2 and b.soluong!=0          \n "+      
		"   			and b.CTKMID in ("+obj.getCtkmId()+" )     \n "+      
		"   		group by a.npp_fk ,c.PK_SEQ            \n "+      
		"   	) as p	PIVOT ( 		 sum(SoLuong) 	 FOR sanpham_fk IN ("+chuoingoac+" )  ) AS t   \n "+      
		"   )as km on  km.NPP_FK=data.NPP_FK   \n "+      
		"   left join    \n "+      
		"   (   \n "+      
		"   	select npp_fk,"+chuoingoac_BAN+"    \n "+      
		"   	from    \n "+      
		"   	(   \n "+      
		"   		select a.npp_fk,sum(b.soluong*f.trongluong) as soluong,b.SANPHAM_FK             \n "+      
		"   		from donhang a inner join donhang_sanpham b on b.donhang_fk=a.pk_seq             \n "+      
		"   		inner join              \n "+      
		"   		(             \n "+      
		"   			select donhangid ,ctkmid,sum(soluong) as soluong             \n "+      
		"   			from donhang_ctkm_trakm              \n "+      
		"      \n "+      
		"   		where spma is not null             \n "+      
		"   			group by donhangid,ctkmid             \n "+      
		"   		)c on c.donhangid=b.donhang_fk             \n "+      
		"   			inner join ctkm_dkkm d on d.ctkhuyenmai_fk=c.ctkmid             \n "+      
		"   			inner join dieukienkm_sanpham e on e.dieukienkhuyenmai_fk=d.dkkhuyenmai_fk and e.sanpham_fk=b.sanpham_fk             \n "+      
		"   			inner join sanpham f on f.pk_seq=e.sanpham_fk             \n "+      
		"   		where a.trangthai!=2             \n "+      
		"   			and d.CTKHUYENMAI_FK in ("+obj.getCtkmId()+" )     \n "+      
		"   		group by a.npp_fk,b.SANPHAM_FK                   \n "+      
		"   	) as p	PIVOT ( 		 sum(SoLuong) 	 FOR sanpham_fk IN 	( "+chuoingoac_BAN+"  )  ) AS t   \n "+      
		"   )as ban on  ban.NPP_FK=data.NPP_FK   \n "+      
		"   INNER JOIN NHAPHANPHOI npp on npp.PK_SEQ=data.NPP_FK   \n "+      
		"   inner join KHUVUC kv on kv.PK_SEQ=npp.KHUVUC_FK       \n "+      
		"   inner join VUNG v on v.PK_SEQ=kv.VUNG_FK         \n "+      
		"  group by kv.TEN,v.TEN     " +
		
		" union all  "+
		"select   " +
		" 1 AS 'Row' , \n "+      
		"   	'Total' as vTEN, ' Total'  as kvTEN,''  as nppMA,'' as nppTEN,   \n "+      
		" NULL AS NPP_FK , "+chuoiselctSUM+","+chuoiselct_BANSUM+",3 as Loai  \n "+      
		"   from   \n "+      
		"   (   \n "+      
		"   	select a.npp_fk           \n "+      
		"   	from donhang a inner join donhang_ctkm_trakm b on b.donhangid=a.pk_seq                  \n "+      
		"   	where a.trangthai!=2 and b.soluong!=0      \n "+      
		"   		and b.CTKMID in ("+obj.getCtkmId()+" )     \n "+      
		"   	group by a.npp_fk            \n "+      
		"   )as data   \n "+      
		"   left join    \n "+      
		"   (   \n "+      
		"   	select NPP_FK,"+chuoingoac+"   \n "+      
		"   	from   \n "+      
		"   	(   \n "+      
		"   		select a.npp_fk,sum(b.soluong*c.trongluong) as SoLuong,c.PK_SEQ as sanpham_fk             \n "+      
		"   		from donhang a inner join donhang_ctkm_trakm b on b.donhangid=a.pk_seq             \n "+      
		"   			inner join sanpham c on c.ma=b.spma             \n "+      
		"   		where a.trangthai!=2 and b.soluong!=0          \n "+      
		"   			and b.CTKMID in ("+obj.getCtkmId()+" )     \n "+      
		"   		group by a.npp_fk ,c.PK_SEQ            \n "+      
		"   	) as p	PIVOT ( 		 sum(SoLuong) 	 FOR sanpham_fk IN ("+chuoingoac+" )  ) AS t   \n "+      
		"   )as km on  km.NPP_FK=data.NPP_FK   \n "+      
		"   left join    \n "+      
		"   (   \n "+      
		"   	select npp_fk,"+chuoingoac_BAN+"    \n "+      
		"   	from    \n "+      
		"   	(   \n "+      
		"   		select a.npp_fk,sum(b.soluong*f.trongluong) as soluong,b.SANPHAM_FK             \n "+      
		"   		from donhang a inner join donhang_sanpham b on b.donhang_fk=a.pk_seq             \n "+      
		"   		inner join              \n "+      
		"   		(             \n "+      
		"   			select donhangid ,ctkmid,sum(soluong) as soluong             \n "+      
		"   			from donhang_ctkm_trakm              \n "+      
		"      \n "+      
		"   		where spma is not null             \n "+      
		"   			group by donhangid,ctkmid             \n "+      
		"   		)c on c.donhangid=b.donhang_fk             \n "+      
		"   			inner join ctkm_dkkm d on d.ctkhuyenmai_fk=c.ctkmid             \n "+      
		"   			inner join dieukienkm_sanpham e on e.dieukienkhuyenmai_fk=d.dkkhuyenmai_fk and e.sanpham_fk=b.sanpham_fk             \n "+      
		"   			inner join sanpham f on f.pk_seq=e.sanpham_fk             \n "+      
		"   		where a.trangthai!=2             \n "+      
		"   			and d.CTKHUYENMAI_FK in ("+obj.getCtkmId()+" )     \n "+      
		"   		group by a.npp_fk,b.SANPHAM_FK                   \n "+      
		"   	) as p	PIVOT ( 		 sum(SoLuong) 	 FOR sanpham_fk IN 	( "+chuoingoac_BAN+"  )  ) AS t   \n "+      
		"   )as ban on  ban.NPP_FK=data.NPP_FK   \n "+      
		"   INNER JOIN NHAPHANPHOI npp on npp.PK_SEQ=data.NPP_FK   \n "+      
		"   inner join KHUVUC kv on kv.PK_SEQ=npp.KHUVUC_FK       \n "+      
		"   inner join VUNG v on v.PK_SEQ=kv.VUNG_FK         \n "+      
		"  ORDER BY vTEN,kv.TEN ,Loai   ";
		
		System.out.println("[BcSuDungKhuyenMaiSvl]"+query);
		
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);		
		
		Cells cells = worksheet.getCells();
		
		
		rs = db.get("select scheme,diengiai,tungay,denngay from Ctkhuyenmai where pk_Seq in ("+obj.getCtkmId()+") " );
		Cell cell = null;
		String diengiai="";
		
		
		while(rs!=null && rs.next())
		{
			diengiai +=rs.getString("scheme")+"-"+rs.getString("diengiai")  + "    ";
			cell = cells.getCell("C" + Integer.toString(8));
			cell.setValue(rs.getString("TUNGAY") + " - " +rs.getString("DENNGAY") );
		}
		cell = cells.getCell("C" + Integer.toString(7));
		cell.setValue(diengiai);
		
		ResultSet hdRs = db.get(query);
		double total_BAN = 0;
		double total_KM = 0;
		i = 14;
		String kvTen_Prev="";
		String kvTEN="";
		Style style;
		if (hdRs != null)
		{
			try
			{
				 cell = null;
				while (hdRs.next())
				{
					kvTEN=hdRs.getString("kvTEN");
					if(!kvTen_Prev.equals(kvTEN))
					{
						kvTen_Prev=hdRs.getString("kvTEN");
						cell = cells.getCell("A" + Integer.toString(i));
						cell.setValue(kvTEN);
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
						ReportAPI.mergeCells(worksheet, i-1, i-1+hdRs.getInt("Row")-1, 0, 0);
					}
					else 
					{
						cell = cells.getCell("A" + Integer.toString(i));
						cell.setValue("");
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.NONE, true, 0);
					}
					cell = cells.getCell("B" + Integer.toString(i));
					cell.setValue(hdRs.getString("nppTEN").trim().toUpperCase());
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);					
					
					int indexColumn=3;
					for(int ii=0;ii<spBanIds.length;ii++)
					{
						total_BAN+=hdRs.getDouble("ban"+spBanIds[ii].trim());
						indexColumn=3;
						cell = cells.getCell(GetExcelColumnName(indexColumn+ii)+Integer.toString(i));	
						cell.setValue(hdRs.getDouble("ban"+spBanIds[ii].trim()));
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
						
						cell = cells.getCell(GetExcelColumnName(indexColumn+ii)+12);	cell.setValue(spBanTENs[ii]);
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
						cells.setColumnWidth(indexColumn+ii, 10.0f);
						cells.setRowHeight(12,25.0f);
						style=cell.getStyle();
					
						style.setTextWrapped(true);
						style.setHAlignment(TextAlignmentType.CENTER);
						style.setVAlignment(TextAlignmentType.CENTER);
						cell.setStyle(style);
						cell = cells.getCell(GetExcelColumnName(indexColumn+ii)+13);	cell.setValue("KG");
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
						
						if(ii==0 && i==14)
						{
							cell = cells.getCell(GetExcelColumnName(indexColumn+ii)+11);	cell.setValue("LƯỢNG BÁN TRONG KỲ");
							ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
							ReportAPI.mergeCells(worksheet,10,10,indexColumn+ii-1,indexColumn+spBanIds.length-2);
							cells.setRangeOutlineBorder(10,10, indexColumn+ii-1 , indexColumn+spBanIds.length-2 ,BorderLineType.THIN,Color.BLACK);
							style=cell.getStyle();
							style.setTextWrapped(true);
							style.setHAlignment(TextAlignmentType.CENTER);
							style.setVAlignment(TextAlignmentType.CENTER);
							cell.setStyle(style);
						}
					}
					
					for(int ii=0;ii<spKmIds.length;ii++)
					{
						total_KM+=hdRs.getDouble("km"+spKmIds[ii].trim());
						indexColumn=3+spBanIds.length;
						cell = cells.getCell(GetExcelColumnName(indexColumn+ii)+Integer.toString(i));	
						cell.setValue(hdRs.getDouble("km"+spKmIds[ii].trim()));
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
							
						cell = cells.getCell(GetExcelColumnName(indexColumn+ii)+12);	cell.setValue(spKmTens[ii]);
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
						cells.setColumnWidth(indexColumn+ii, 10.0f);
						cells.setRowHeight(12,25.0f);
						style=cell.getStyle();
					
						style.setTextWrapped(true);
						style.setHAlignment(TextAlignmentType.CENTER);
						style.setVAlignment(TextAlignmentType.CENTER);
						cell.setStyle(style);
						
						cell = cells.getCell(GetExcelColumnName(indexColumn+ii)+13);	cell.setValue("KG");
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
						
						cell = cells.getCell(GetExcelColumnName(spBanIds.length+spKmIds.length+3)+12);	cell.setValue("TỶ LỆ KM");
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
						style=cell.getStyle();
						
						style.setTextWrapped(true);
						style.setHAlignment(TextAlignmentType.CENTER);
						style.setVAlignment(TextAlignmentType.CENTER);
						cell.setStyle(style);
						
						cell = cells.getCell(GetExcelColumnName(spBanIds.length+spKmIds.length+3)+13);	cell.setValue("%");
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
						
						if(ii==0 && i==14)
						{
							cell = cells.getCell(GetExcelColumnName(indexColumn+ii)+11);	cell.setValue("LƯỢNG KHUYẾN MẠI");
							ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
							ReportAPI.mergeCells(worksheet,10,10,indexColumn-1,indexColumn+spKmIds.length-2);
							cells.setRangeOutlineBorder(10,10, indexColumn-1 , indexColumn+spKmIds.length-1 ,BorderLineType.THIN,Color.BLACK);
							
							style=cell.getStyle();
							style.setTextWrapped(true);
							style.setHAlignment(TextAlignmentType.CENTER);
							style.setVAlignment(TextAlignmentType.CENTER);
							cell.setStyle(style);
							
							cell = cells.getCell(GetExcelColumnName(indexColumn)+10);	cell.setValue("DỮ LIỆU HỆ THỐNG");
							ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
							ReportAPI.mergeCells(worksheet,9,9,2,spKmIds.length+spBanIds.length+2);
							cells.setRangeOutlineBorder(9,9, 2 , spKmIds.length+spBanIds.length+2 ,BorderLineType.THIN+1,Color.BLACK);
							
							
							style=cell.getStyle();
							style.setTextWrapped(true);
							style.setHAlignment(TextAlignmentType.CENTER);
							style.setVAlignment(TextAlignmentType.CENTER);
							cell.setStyle(style);
						}
					}
					cell = cells.getCell(GetExcelColumnName( 3+spBanIds.length +spKmIds.length )+Integer.toString(i));	
					cell.setValue(total_KM/total_BAN );
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 10);
					++i;
				}				
				if (hdRs != null)
					hdRs.close();
				
				if (db != null)
					db.shutDown();
				
				if (i == 14)
				{
					throw new Exception("Không có báo cáo trong thời gian đã chọn !! ");
				}
				
			} catch (Exception ex)
			{
				ex.printStackTrace();
				throw new Exception(ex.getMessage());
			}
		} else
		{
			return false;
		}
		return true;
		
	}
	
	private boolean FillData_Details(Workbook workbook, IBcSuDungKhuyenMai obj, String query) throws Exception
	{
		dbutils db = new dbutils();		
		String[] ctkmId=obj.getCtkmId().split(",");
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);		
		Cells cells = worksheet.getCells();
		
		ResultSet	rs = db.get("select scheme,diengiai,tungay,denngay from Ctkhuyenmai where pk_Seq in ("+obj.getCtkmId()+") " );
		Cell cell = null;
		String diengiai="";
		
		while(rs!=null && rs.next())
		{
			diengiai +=rs.getString("scheme")+"-"+rs.getString("diengiai")  + "    ";
			cell = cells.getCell("C" + Integer.toString(8));
			cell.setValue(rs.getString("TUNGAY") + " - " +rs.getString("DENNGAY") );
		}
		if(rs!=null)rs.close();
		
		cell = cells.getCell("C" + Integer.toString(7));
		cell.setValue(diengiai);
		
		int indexColumn=3;
		Hashtable<Integer,Integer> htb = new Hashtable<Integer, Integer>();
		int nCtkm=0;
		for (String ctId : ctkmId)
    {
			String ctkmTen="";
			nCtkm++;
			int	i = 14;
			String sql=
					"		select (select diengiai from ctkhuyenmai where pk_seq='"+ctId+"') as ctkmTen, c.PK_SEQ as sanpham_fk,c.MA as spMa,c.TEN as spTen           "+
					"		from donhang a inner join donhang_ctkm_trakm b on b.donhangid=a.pk_seq "+          
					"			inner join sanpham c on c.ma=b.spma    "+       
					"		where a.trangthai!=2 and b.soluong!=0 "+       
					"			and b.CTKMID in ("+ctId+" )   "+
					"		group by c.PK_SEQ ,c.MA,c.TEN ";   
					 rs=db.get(sql);
					 String[] spKmIds = null;
					 String[] spKmTens = null;
					 String spKmId="";
					 String spKmTEN="";
					 String chuoiselct="";
					 String chuoiselctSUM="";
					 String chuoingoac="";
					 i=0;
					 try 
					 {
						while (rs.next())
						{
							 ctkmTen=rs.getString("ctkmTen");
							 if(i==0)
							 {
								 chuoingoac="["+rs.getString("sanpham_fk")+"]";
								 chuoiselct = "isnull(km.["+rs.getString("sanpham_fk")+"],0) AS km"+rs.getString("sanpham_fk");
								 chuoiselctSUM = "SUM( isnull(km.["+rs.getString("sanpham_fk")+"],0)) AS km"+rs.getString("sanpham_fk");
								 spKmId = rs.getString("sanpham_fk");
								 spKmTEN = rs.getString("spTen");
							 }else
							 {
								 chuoiselct += "," + "isnull(km.["+rs.getString("sanpham_fk")+"],0) AS km"+rs.getString("sanpham_fk");
								 chuoiselctSUM += "," + "SUM( isnull(km.["+rs.getString("sanpham_fk")+"],0)) AS km"+rs.getString("sanpham_fk");
								 chuoingoac =chuoingoac+",["+rs.getString("sanpham_fk")+"]";
								 spKmId +="__" + rs.getString("sanpham_fk") ;
								 spKmTEN +="__" + rs.getString("spTen") ;
							 }
							 i++;
						 }
						spKmIds=spKmId.split("__");
						spKmTens=spKmTEN.split("__");
					} catch (Exception e) 
					{
						e.printStackTrace();
					}
					 
					 sql=
						"   		select  b.SANPHAM_FK,f.ten as spTen             \n "+      
						"   		from donhang a inner join donhang_sanpham b on b.donhang_fk=a.pk_seq             \n "+      
						"   		inner join              \n "+      
						"   		(             \n "+      
						"   			select donhangid ,ctkmid,sum(soluong) as soluong             \n "+      
						"   			from donhang_ctkm_trakm              \n "+      
						"      \n "+      
						"   		where spma is not null             \n "+      
						"   			group by donhangid,ctkmid             \n "+      
						"   		)c on c.donhangid=b.donhang_fk             \n "+      
						"   			inner join ctkm_dkkm d on d.ctkhuyenmai_fk=c.ctkmid             \n "+      
						"   			inner join dieukienkm_sanpham e on e.dieukienkhuyenmai_fk=d.dkkhuyenmai_fk and e.sanpham_fk=b.sanpham_fk             \n "+      
						"   			inner join sanpham f on f.pk_seq=e.sanpham_fk             \n "+      
						"   		where a.trangthai!=2             \n "+      
						"   			and d.CTKHUYENMAI_FK in ("+ctId+" )     \n "+      
						"   		group by b.SANPHAM_FK ,f.ten                  \n ";
					 
					 rs=db.get(sql);
					 String[] spBanIds = null;
					 String[] spBanTENs = null;
					 String spBanId="";
					 String spBanTEN="";
					 String chuoiselct_BAN="";
					 String chuoiselct_BANSUM="";
					 String chuoingoac_BAN="";
					 i=0;
					 try 
					 {
						while (rs.next())
						{
							 if(i==0)
							 {
								 chuoingoac_BAN="["+rs.getString("sanpham_fk")+"]";
								 chuoiselct_BAN = "isnull(ban.["+rs.getString("sanpham_fk")+"],0) AS ban"+rs.getString("sanpham_fk");
								 chuoiselct_BANSUM = "SUM( isnull(ban.["+rs.getString("sanpham_fk")+"],0)) AS ban"+rs.getString("sanpham_fk");
								 spBanId  = rs.getString("sanpham_fk");
								 spBanTEN = rs.getString("spTEN");
							 }else
							 {
								 chuoiselct_BAN += "," + "isnull(ban.["+rs.getString("sanpham_fk")+"],0) AS ban"+rs.getString("sanpham_fk");
								 chuoiselct_BANSUM += "," + "SUM( isnull(ban.["+rs.getString("sanpham_fk")+"],0)) AS ban"+rs.getString("sanpham_fk") ;
								 chuoingoac_BAN += ",["+rs.getString("sanpham_fk")+"]";
								 spBanId +="__" + rs.getString("sanpham_fk") ;
								 spBanTEN +="__" + rs.getString("spTEN") ;
							 }
							 i++;
						 }
						spBanIds =spBanId.split("__");
						spBanTENs  =spBanTEN.split("__");
					} catch (Exception e) 
					{
						e.printStackTrace();
					}
					 
					if(nCtkm==1)
					{
						indexColumn=3;
						htb.put(nCtkm, 3+spBanIds.length+spKmIds.length);
					}
					else 
					{
						htb.put(nCtkm,htb.get(nCtkm-1)+spBanIds.length+spKmIds.length+1);
						indexColumn=htb.get(nCtkm-1);
					}
					query=
					"select   " +
					" COUNT(kv.TEN)	OVER( PARTITION BY v.ten,kv.ten ) AS 'Row' , \n "+      
					"   	v.ten as vTEN,kv.TEN as kvTEN,npp.MA as nppMA,npp.TEN as nppTEN,   \n "+      
					"     data.NPP_FK , "+chuoiselct+","+chuoiselct_BAN+",1 as Loai  \n "+      
					"   from   \n "+      
					"   (   \n "+      
					"   	select a.npp_fk           \n "+      
					"   	from donhang a inner join donhang_ctkm_trakm b on b.donhangid=a.pk_seq                  \n "+      
					"   	where a.trangthai!=2 and b.soluong!=0      \n "+      
					"   		and b.CTKMID in ("+obj.getCtkmId()+" )     \n "+      
					"   	group by a.npp_fk            \n "+      
					"   )as data   \n "+      
					"   left join    \n "+      
					"   (   \n "+      
					"   	select NPP_FK,"+chuoingoac+"   \n "+      
					"   	from   \n "+      
					"   	(   \n "+      
					"   		select a.npp_fk,sum(b.soluong*c.trongluong) as SoLuong,c.PK_SEQ as sanpham_fk             \n "+      
					"   		from donhang a inner join donhang_ctkm_trakm b on b.donhangid=a.pk_seq             \n "+      
					"   			inner join sanpham c on c.ma=b.spma             \n "+      
					"   		where a.trangthai!=2 and b.soluong!=0          \n "+      
					"   			and b.CTKMID in ("+ctId+" )     \n "+      
					"   		group by a.npp_fk ,c.PK_SEQ            \n "+      
					"   	) as p	PIVOT ( 		 sum(SoLuong) 	 FOR sanpham_fk IN ("+chuoingoac+" )  ) AS t   \n "+      
					"   )as km on km.NPP_FK=data.NPP_FK   \n "+      
					"   left join    \n "+      
					"   (   \n "+      
					"   	select npp_fk,"+chuoingoac_BAN+"    \n "+      
					"   	from    \n "+      
					"   	(   \n "+      
					"   		select a.npp_fk,sum(b.soluong*f.trongluong) as soluong,b.SANPHAM_FK             \n "+      
					"   		from donhang a inner join donhang_sanpham b on b.donhang_fk=a.pk_seq             \n "+      
					"   		inner join              \n "+      
					"   		(             \n "+      
					"   			select donhangid ,ctkmid,sum(soluong) as soluong             \n "+      
					"   			from donhang_ctkm_trakm              \n "+      
					"      \n "+      
					"   		where spma is not null             \n "+      
					"   			group by donhangid,ctkmid             \n "+      
					"   		)c on c.donhangid=b.donhang_fk             \n "+      
					"   			inner join ctkm_dkkm d on d.ctkhuyenmai_fk=c.ctkmid             \n "+      
					"   			inner join dieukienkm_sanpham e on e.dieukienkhuyenmai_fk=d.dkkhuyenmai_fk and e.sanpham_fk=b.sanpham_fk             \n "+      
					"   			inner join sanpham f on f.pk_seq=e.sanpham_fk             \n "+      
					"   		where a.trangthai!=2             \n "+      
					"   			and d.CTKHUYENMAI_FK in ("+ctId+" )     \n "+      
					"   		group by a.npp_fk,b.SANPHAM_FK                   \n "+      
					"   	) as p	PIVOT ( 		 sum(SoLuong) 	 FOR sanpham_fk IN 	( "+chuoingoac_BAN+"  )  ) AS t   \n "+      
					"   )as ban on ban.NPP_FK=data.NPP_FK   \n "+      
					"   INNER JOIN NHAPHANPHOI npp on npp.PK_SEQ=data.NPP_FK   \n "+      
					"   inner join KHUVUC kv on kv.PK_SEQ=npp.KHUVUC_FK       \n "+      
					"   inner join VUNG v on v.PK_SEQ=kv.VUNG_FK         \n "+      
					" union all  "+
					"select   " +
					" COUNT(kv.TEN)	OVER( PARTITION BY v.ten,kv.ten ) AS 'Row' , \n "+      
					"   	v.ten as vTEN,kv.TEN + ' Total'  as kvTEN,''  as nppMA,'' as nppTEN,   \n "+      
					" NULL AS NPP_FK , "+chuoiselctSUM+","+chuoiselct_BANSUM+",2 as Loai  \n "+      
					"   from   \n "+      
					"   (   \n "+      
					"   	select a.npp_fk           \n "+      
					"   	from donhang a inner join donhang_ctkm_trakm b on b.donhangid=a.pk_seq                  \n "+      
					"   	where a.trangthai!=2 and b.soluong!=0      \n "+      
					"   		and b.CTKMID in ("+ctId+" )     \n "+      
					"   	group by a.npp_fk            \n "+      
					"   )as data   \n "+      
					"   left join    \n "+      
					"   (   \n "+      
					"   	select NPP_FK,"+chuoingoac+"   \n "+      
					"   	from   \n "+      
					"   	(   \n "+      
					"   		select a.npp_fk,sum(b.soluong*c.trongluong) as SoLuong,c.PK_SEQ as sanpham_fk             \n "+      
					"   		from donhang a inner join donhang_ctkm_trakm b on b.donhangid=a.pk_seq             \n "+      
					"   			inner join sanpham c on c.ma=b.spma             \n "+      
					"   		where a.trangthai!=2 and b.soluong!=0          \n "+      
					"   			and b.CTKMID in ("+ctId+" )     \n "+      
					"   		group by a.npp_fk ,c.PK_SEQ            \n "+      
					"   	) as p	PIVOT ( 		 sum(SoLuong) 	 FOR sanpham_fk IN ("+chuoingoac+" )  ) AS t   \n "+      
					"   )as km on  km.NPP_FK=data.NPP_FK   \n "+      
					"   left join    \n "+      
					"   (   \n "+      
					"   	select npp_fk,"+chuoingoac_BAN+"    \n "+      
					"   	from    \n "+      
					"   	(   \n "+      
					"   		select a.npp_fk,sum(b.soluong*f.trongluong) as soluong,b.SANPHAM_FK             \n "+      
					"   		from donhang a inner join donhang_sanpham b on b.donhang_fk=a.pk_seq             \n "+      
					"   		inner join              \n "+      
					"   		(             \n "+      
					"   			select donhangid ,ctkmid,sum(soluong) as soluong             \n "+      
					"   			from donhang_ctkm_trakm              \n "+      
					"      \n "+      
					"   		where spma is not null             \n "+      
					"   			group by donhangid,ctkmid             \n "+      
					"   		)c on c.donhangid=b.donhang_fk             \n "+      
					"   			inner join ctkm_dkkm d on d.ctkhuyenmai_fk=c.ctkmid             \n "+      
					"   			inner join dieukienkm_sanpham e on e.dieukienkhuyenmai_fk=d.dkkhuyenmai_fk and e.sanpham_fk=b.sanpham_fk             \n "+      
					"   			inner join sanpham f on f.pk_seq=e.sanpham_fk             \n "+      
					"   		where a.trangthai!=2             \n "+      
					"   			and d.CTKHUYENMAI_FK in ("+ctId+" )     \n "+      
					"   		group by a.npp_fk,b.SANPHAM_FK                   \n "+      
					"   	) as p	PIVOT ( 		 sum(SoLuong) 	 FOR sanpham_fk IN 	( "+chuoingoac_BAN+"  )  ) AS t   \n "+      
					"   )as ban on  ban.NPP_FK=data.NPP_FK   \n "+      
					"   INNER JOIN NHAPHANPHOI npp on npp.PK_SEQ=data.NPP_FK   \n "+      
					"   inner join KHUVUC kv on kv.PK_SEQ=npp.KHUVUC_FK       \n "+      
					"   inner join VUNG v on v.PK_SEQ=kv.VUNG_FK         \n "+      
					"  group by kv.TEN,v.TEN     " +
					" union all  "+
					"select   " +
					" 1 AS 'Row' , \n "+      
					"   	'Total' as vTEN, ' Total'  as kvTEN,''  as nppMA,'' as nppTEN,   \n "+      
					" NULL AS NPP_FK , "+chuoiselctSUM+","+chuoiselct_BANSUM+",3 as Loai  \n "+      
					"   from   \n "+      
					"   (   \n "+      
					"   	select a.npp_fk           \n "+      
					"   	from donhang a inner join donhang_ctkm_trakm b on b.donhangid=a.pk_seq                  \n "+      
					"   	where a.trangthai!=2 and b.soluong!=0      \n "+      
					"   		and b.CTKMID in ("+ctId+" )     \n "+      
					"   	group by a.npp_fk            \n "+      
					"   )as data   \n "+      
					"   left join    \n "+      
					"   (   \n "+      
					"   	select NPP_FK,"+chuoingoac+"   \n "+      
					"   	from   \n "+      
					"   	(   \n "+      
					"   		select a.npp_fk,sum(b.soluong*c.trongluong) as SoLuong,c.PK_SEQ as sanpham_fk             \n "+      
					"   		from donhang a inner join donhang_ctkm_trakm b on b.donhangid=a.pk_seq             \n "+      
					"   			inner join sanpham c on c.ma=b.spma             \n "+      
					"   		where a.trangthai!=2 and b.soluong!=0          \n "+      
					"   			and b.CTKMID in ("+ctId+" )     \n "+      
					"   		group by a.npp_fk ,c.PK_SEQ            \n "+      
					"   	) as p	PIVOT ( 		 sum(SoLuong) 	 FOR sanpham_fk IN ("+chuoingoac+" )  ) AS t   \n "+      
					"   )as km on  km.NPP_FK=data.NPP_FK   \n "+      
					"   left join    \n "+      
					"   (   \n "+      
					"   	select npp_fk,"+chuoingoac_BAN+"    \n "+      
					"   	from    \n "+      
					"   	(   \n "+      
					"   		select a.npp_fk,sum(b.soluong*f.trongluong) as soluong,b.SANPHAM_FK             \n "+      
					"   		from donhang a inner join donhang_sanpham b on b.donhang_fk=a.pk_seq             \n "+      
					"   		inner join              \n "+      
					"   		(             \n "+      
					"   			select donhangid ,ctkmid,sum(soluong) as soluong             \n "+      
					"   			from donhang_ctkm_trakm              \n "+      
					"      \n "+      
					"   		where spma is not null             \n "+      
					"   			group by donhangid,ctkmid             \n "+      
					"   		)c on c.donhangid=b.donhang_fk             \n "+      
					"   			inner join ctkm_dkkm d on d.ctkhuyenmai_fk=c.ctkmid             \n "+      
					"   			inner join dieukienkm_sanpham e on e.dieukienkhuyenmai_fk=d.dkkhuyenmai_fk and e.sanpham_fk=b.sanpham_fk             \n "+      
					"   			inner join sanpham f on f.pk_seq=e.sanpham_fk             \n "+      
					"   		where a.trangthai!=2             \n "+      
					"   			and d.CTKHUYENMAI_FK in ("+ctId+" )     \n "+      
					"   		group by a.npp_fk,b.SANPHAM_FK                   \n "+      
					"   	) as p	PIVOT ( 		 sum(SoLuong) 	 FOR sanpham_fk IN 	( "+chuoingoac_BAN+"  )  ) AS t   \n "+      
					"   )as ban on  ban.NPP_FK=data.NPP_FK   \n "+      
					"   INNER JOIN NHAPHANPHOI npp on npp.PK_SEQ=data.NPP_FK   \n "+      
					"   inner join KHUVUC kv on kv.PK_SEQ=npp.KHUVUC_FK       \n "+      
					"   inner join VUNG v on v.PK_SEQ=kv.VUNG_FK         \n "+      
					"  ORDER BY vTEN,kv.TEN ,Loai   ";
					String kvTen_Prev="";
					String kvTEN="";
					double total_BAN = 0;
					double total_KM = 0;
					i = 14;
					Style style;
					ResultSet hdRs = db.get(query);
					while (hdRs.next())
					{
						kvTEN=hdRs.getString("kvTEN");
						if(!kvTen_Prev.equals(kvTEN))
						{
							kvTen_Prev=hdRs.getString("kvTEN");
							cell = cells.getCell("A" + Integer.toString(i));
							cell.setValue(kvTEN);
							ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
							if(nCtkm==1)
								ReportAPI.mergeCells(worksheet, i-1, i-1+hdRs.getInt("Row")-1, 0, 0);
						}
						else 
						{
							cell = cells.getCell("A" + Integer.toString(i));
							cell.setValue("");
							ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.NONE, true, 0);
						}
					
						cell = cells.getCell("B" + Integer.toString(i));
						cell.setValue(hdRs.getString("nppTEN").trim().toUpperCase());
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);					
						
						for(int ii=0;ii<spBanIds.length;ii++)
						{
							total_BAN+=hdRs.getDouble("ban"+spBanIds[ii].trim());
							
							if(nCtkm==1)
								indexColumn=3;
							else 
								indexColumn=htb.get(nCtkm-1)+1;
							
							cell = cells.getCell(GetExcelColumnName(indexColumn+ii)+Integer.toString(i));	
							cell.setValue(hdRs.getDouble("ban"+spBanIds[ii].trim()));
							ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
							
							cell = cells.getCell(GetExcelColumnName(indexColumn+ii)+12);	cell.setValue(spBanTENs[ii]);
							ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
							cells.setColumnWidth(indexColumn+ii, 10.0f);
							cells.setRowHeight(12,25.0f);
							style=cell.getStyle();
						
							style.setTextWrapped(true);
							style.setHAlignment(TextAlignmentType.CENTER);
							style.setVAlignment(TextAlignmentType.CENTER);
							cell.setStyle(style);
							cell = cells.getCell(GetExcelColumnName(indexColumn+ii)+13);	cell.setValue("KG");
							ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
							
							if(ii==0 && i==14)
							{
								cell = cells.getCell(GetExcelColumnName(indexColumn)+11);	cell.setValue("LƯỢNG BÁN TRONG KỲ");
								style=cell.getStyle();
								style.setTextWrapped(true);
								style.setHAlignment(TextAlignmentType.CENTER);
								style.setVAlignment(TextAlignmentType.CENTER);
								cell.setStyle(style);
								
								ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
								ReportAPI.mergeCells(worksheet,10,10,indexColumn-1,indexColumn+spBanIds.length-2);
								cells.setRangeOutlineBorder(10,10, indexColumn-1 , indexColumn+spBanIds.length-2 ,BorderLineType.THIN,Color.BLACK);


							}
						}
						
						for(int ii=0;ii<spKmIds.length;ii++)
						{
							total_KM+=hdRs.getDouble("km"+spKmIds[ii].trim());
						
							if(nCtkm==1)
								indexColumn=3+spBanIds.length;
							else 
								indexColumn=htb.get(nCtkm-1)+1+spBanIds.length; 
							
							cell = cells.getCell(GetExcelColumnName(indexColumn+ii)+Integer.toString(i));	
							cell.setValue(hdRs.getDouble("km"+spKmIds[ii].trim()));
							ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
								
							cell = cells.getCell(GetExcelColumnName(indexColumn+ii)+12);	cell.setValue(spKmTens[ii]);
							ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
							cells.setColumnWidth(indexColumn+ii, 10.0f);
							cells.setRowHeight(12,25.0f);
							style=cell.getStyle();
						
							style.setTextWrapped(true);
							style.setHAlignment(TextAlignmentType.CENTER);
							style.setVAlignment(TextAlignmentType.CENTER);
							cell.setStyle(style);
							
							cell = cells.getCell(GetExcelColumnName(indexColumn+ii)+13);	cell.setValue("KG");
							ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
							
							if(nCtkm==1)
							{
								cell = cells.getCell(GetExcelColumnName(3+spBanIds.length+spKmIds.length)+12);	cell.setValue("TỶ LỆ KM");
								ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
								cell = cells.getCell(GetExcelColumnName(3+spBanIds.length+spKmIds.length)+13);	cell.setValue("%");
								ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
							}
							else
							{
								cell = cells.getCell(GetExcelColumnName(htb.get(nCtkm-1)+1+spBanIds.length+spKmIds.length)+12);	cell.setValue("TỶ LỆ KM");
								ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
								cell = cells.getCell(GetExcelColumnName(htb.get(nCtkm-1)+1+spBanIds.length+spKmIds.length)+13);	cell.setValue("%");
								ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
							}
							
							
							ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
							style=cell.getStyle();
							
							style.setTextWrapped(true);
							style.setHAlignment(TextAlignmentType.CENTER);
							style.setVAlignment(TextAlignmentType.CENTER);
							cell.setStyle(style);
							
							if(ii==0 && i==14)
							{
								cell = cells.getCell(GetExcelColumnName(indexColumn)+11);	cell.setValue("LƯỢNG KHUYẾN MẠI");
								ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
								
								ReportAPI.mergeCells(worksheet,10,10,indexColumn-1,indexColumn+spKmIds.length-1);
								cells.setRangeOutlineBorder(10,10, indexColumn-1 , indexColumn+spKmIds.length-1 ,BorderLineType.THIN,Color.BLACK);
								
								style=cell.getStyle();
								style.setTextWrapped(true);
								style.setHAlignment(TextAlignmentType.CENTER);
								style.setVAlignment(TextAlignmentType.CENTER);
								cell.setStyle(style);
								
								if(nCtkm==1)
								{
									cell = cells.getCell(GetExcelColumnName(3)+10);	cell.setValue(ctkmTen);
									ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
									ReportAPI.mergeCells(worksheet,9,9,2,3+spKmIds.length+spBanIds.length-1);
									cells.setRangeOutlineBorder(9,9, 2,3 +spKmIds.length+spBanIds.length-1 ,BorderLineType.THIN+1,Color.BLACK);	
								}
								else
								{
									cell = cells.getCell(GetExcelColumnName(htb.get(nCtkm-1)+1)+10);	cell.setValue(ctkmTen);
									ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
									ReportAPI.mergeCells(worksheet,9,9,htb.get(nCtkm-1)  ,htb.get(nCtkm-1)+spKmIds.length+spBanIds.length);
									cells.setRangeOutlineBorder(9,9, htb.get(nCtkm-1),htb.get(nCtkm-1)+spKmIds.length+spBanIds.length ,BorderLineType.THIN+1,Color.BLACK);
								}
								
								
								style=cell.getStyle();
								style.setTextWrapped(true);
								style.setHAlignment(TextAlignmentType.CENTER);
								style.setVAlignment(TextAlignmentType.CENTER);
								cell.setStyle(style);
							}
						}
						if(nCtkm==1)
						{
							cell = cells.getCell(GetExcelColumnName(3+spBanIds.length+spKmIds.length)+Integer.toString(i));
						}
						else
						{
							cell = cells.getCell(GetExcelColumnName(htb.get(nCtkm-1)+1+spBanIds.length+spKmIds.length)+Integer.toString(i));
						}
						cell.setValue(total_KM/total_BAN );
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 10);
						++i;
					}
    }
		System.out.println("[BcSuDungKhuyenMaiSvl]"+query);
		return true;
	}
	
	
	private void CreateStaticHeader_ByCtKmId(Workbook workbook, IBcSuDungKhuyenMai obj)
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");
		Cells cells = worksheet.getCells();
		
		Style style;
		Font font = new Font();
		font.setColor(Color.RED);// mau chu
		font.setSize(16);// size chu
		font.setBold(true);
		
		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
		style = cell.getStyle();
		style.setFont(font);
		style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu
		
		/*String tieude = "DOANH THU BÁN HÀNG";
		ReportAPI.getCellStyle(cell, Color.RED, true, 14, tieude);
		
		String message = "";
		cells.setRowHeight(2, 18.0f);
		cell = cells.getCell("A3");
		ReportAPI.getCellStyle(cell, Color.RED, true, 9, message);
		
		cells.setRowHeight(3, 18.0f);
		cell = cells.getCell("A4");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Từ ngày : " + obj.getTuNgay() + "");
		
		cells.setRowHeight(3, 18.0f);
		cell = cells.getCell("B4");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Đến ngày : " + obj.getDenNgay() + "");
		
		cells.setRowHeight(4, 18.0f);
		cell = cells.getCell("A5");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
		
		cells.setRowHeight(5, 18.0f);
		cell = cells.getCell("A6");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  ");
		
		cell = cells.getCell("A8");
		cell.setValue("STT");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		
		cell = cells.getCell("B8");
		cell.setValue("MIỀN");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		
		cell = cells.getCell("C8");
		cell.setValue("ĐỊA BÀN");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		
		cell = cells.getCell("D8");
		cell.setValue("CHI NHÁNH/ĐỐI TÁC");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		
		cell = cells.getCell("E8");
		cell.setValue("DOANH THU");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);*/
		
	}
	
	
	public static void main(String[] arg)
	{		
		try 
		{
			File fileDir = new File("D:\\File_TN.txt");
	 
			BufferedReader in = new BufferedReader(
			   new InputStreamReader(
	                      new FileInputStream(fileDir), "UTF8"));
	 
			dbutils db = new dbutils();
			String str;
			String pattern="	";
			int i=0;
			while ((str = in.readLine()) != null) 
			{
				String Cong="";
				String NgayGio="";
				String SDT="";
				String STT="";
				String NoiDung="";
				System.out.println("[STR]"+str.split(pattern).length);
				
			if(str.split(pattern).length>=5)
			{
				System.out.println("[i]"+i);
			  Cong=str.trim().split(pattern)[0].trim();
			  STT = str.trim().split(pattern)[1].trim();
			  NgayGio = str.trim().split(pattern)[2].trim();
			  SDT= str.trim().split(pattern)[3].trim();
			  NoiDung =str.trim().split(pattern)[4].trim();
			  ITinNhan obj = new TinNhan(SDT,NgayGio,NoiDung,STT,Cong);
			  obj.Save(db);
			  System.out.println("[KetQua]"+obj.getMsg());
			}
			  System.out.println("[COM]"+Cong);			  
			 i++; 
			}
	 
	                in.close();
		    } 
		    catch (UnsupportedEncodingException e) 
		    {
				System.out.println(e.getMessage());
		    } 
		    catch (IOException e) 
		    {
				System.out.println(e.getMessage());
		    }
		    catch (Exception e)
		    {
		    	e.printStackTrace();
				System.out.println(e.getMessage());
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
	
	private String Doisangchuoi(String[] checknpp)
	{
		// TODO Auto-generated method stub
		String str = "";
		if (checknpp != null)
		{
			for (int i = 0; i < checknpp.length; i++)
			{
				if (i == 0)
				{
					str = checknpp[i];
				} else
				{
					str = str + "," + checknpp[i];
				}
			}
		}
		return str;

	}
	
}
