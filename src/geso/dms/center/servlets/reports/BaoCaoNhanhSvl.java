package geso.dms.center.servlets.reports;
import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.distributor.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import T.DH;

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

public class BaoCaoNhanhSvl extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public BaoCaoNhanhSvl() {
		super();
	}

	public String getCurrentMonth()
	{
		int k = (Calendar.getInstance().get(Calendar.MONTH)+1);
		return k<10?"0"+k:""+k;
	}
	public String getCurrentYear()
	{
		return (Calendar.getInstance().get(Calendar.YEAR)) + "";
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

		obj.init_user();

		String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
		if(view == null)
			view = "";		
		obj.setLoaiMenu(view);

		Utility ut = new Utility();
		ut.getUserInfo((String) session.getAttribute("userId"), obj.getDb());

		obj.setFromMonth(getCurrentMonth());
		obj.setFromYear(getCurrentYear());

		session.setAttribute("obj", obj);
		String nextJSP = "/AHF/pages/Center/BaoCaoNhanh.jsp";
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
		obj.init_user();
		Utility util = new Utility();

	
		String tuthang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tuthang").length()== 1 ? ("0"+request.getParameter("tuthang")) :request.getParameter("tuthang")) ;
		obj.setFromMonth(tuthang);
		obj.setFromYear(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tunam")));

		
		String action = (String) Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")));


		String kq = getDataRs ( obj,  action);
		obj.setText_baocaoSR(kq);

		String nextJSP = "/AHF/pages/Center/BaoCaoNhanh.jsp";

		System.out.println("Action la: " + action);

		try
		{
			
			session.setAttribute("obj", obj);
			response.sendRedirect(nextJSP);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			
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

	public String getDataRs (IStockintransit obj, String action)
	{
		try
		{
			if(obj.getFromMonth().trim().length() <=0)
			{
				return "Vui lòng nhập tháng";
			}
			if(obj.getFromYear().trim().length() <=0)
			{
				return "Vui lòng nhập năm";
			}

			DecimalFormat df = new DecimalFormat("###,###,##0");
			DecimalFormat df2 = new DecimalFormat("###,###,##0.00");
			String html = "";
			String query = GetQuery(obj, action);

			ResultSet rs = obj.getDb().get(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();
			String content = "";		
			content = "";
			for( int i =1 ; i <=socottrongSql ; i ++  )
			{
				content +=  " <th  style=\"color: black \" width=\"10%\" align=\"center\">"+rsmd.getColumnName(i).replace("isNum_","").replace("isNum2_","")+"</th> " ;						
			}
			String header = "<TR class=\"tbheader\">"  + content +  "</TR>" ;

			content = "";

			String lightrow = "tblightrow";
			String darkrow = "tbdarkrow";
			int m = 0;
			while(rs.next())
			{
				String rowcolor = m%2 == 0 ? lightrow : darkrow;
				String contentDetail  = "";
				for(int i =1;i <=socottrongSql ; i ++)
				{		
					boolean isNum =  rsmd.getColumnName(i).startsWith("isNum_");
					boolean isNum2 =  rsmd.getColumnName(i).startsWith("isNum2_");
					String textAl = isNum ?  "right":"center";
					String val =  "";
					if(isNum)
						val = df.format(rs.getDouble(i)).replace(".00", "");
					else if(isNum2)
						val = df2.format(rs.getDouble(i)).replace(".00", "");
					else
						val = rs.getString(i);

					contentDetail +="	<TD align=\""+textAl+"\" > " +
					val +
					" 	</TD> ";
				}
				content += "<TR class= '"+rowcolor+"' > " +contentDetail + "</TR>";
				m++;
			}

			String body = content;


			html = " <TABLE class = \"chitieutable\" > " +
			header + body +
			" </tbody></TABLE> ";

			return html;
		}
		catch (Exception e) {
			e.printStackTrace();
			return "Lỗi dữ liệu";
		}
	}

	public String GetQuery(IStockintransit obj, String action )
	{
		try 
		{
			Utility util = new Utility();
			String dauthang = obj.getFromYear() +"-" + obj.getFromMonth() +"-01";
			String pivot = "";
			String Sell_Out_Daily_Report = "";
			String Sell_Out_Daily_Report_Total = "";
			String Sell_Out_Daily_Report_Total1 = "";
			String pivot1="";
			String Sell_Out_Daily_Report1="";
			String data_sql =  " select ten data from vung  ";
			ResultSet rsData = obj.getDb().get(data_sql);

			while(rsData.next())
			{
				String data=  rsData.getString("data");
				pivot1 += pivot.trim().length() > 0 ?  ",0 ["+data+"]" : "0 ["+data+"]";
				pivot += pivot.trim().length() > 0 ?  ",["+data+"]" : "["+data+"]";
				Sell_Out_Daily_Report += Sell_Out_Daily_Report.trim().length() > 0 ?  ",isnull( dat.["+data+"],0 ) [isNum_"+data+"]" : "isnull( dat.["+data+"],0 ) [isNum_"+data+"] ";
				Sell_Out_Daily_Report1 += Sell_Out_Daily_Report.trim().length() > 0 ?  ",0 [isNum_"+data+"]" : "0 [isNum_"+data+"] ";

				Sell_Out_Daily_Report_Total += Sell_Out_Daily_Report_Total.trim().length() > 0 ?  " + isnull( dat.["+data+"],0 )" : "isnull( dat.["+data+"],0 )";
				Sell_Out_Daily_Report_Total1 += Sell_Out_Daily_Report_Total.trim().length() > 0 ?  " + isnull( vt.["+data+"],0 )" : "isnull( vt.["+data+"],0 )";

			}


			System.out.println(" pivot = "+ pivot);
			System.out.println(" Sell_Out_Daily_Report = "+ Sell_Out_Daily_Report);
			System.out.println(" Sell_Out_Daily_Report_Total = "+ Sell_Out_Daily_Report_Total);

			
			
			String query =  "";
			if(action.equals("Sell Out Daily Report"))
			{
				String conditionDH = "";
				if(obj.getPhanloai().equals("2"))
				{
					conditionDH += "\n and dh.kbh_fk in ( "+geso.dms.center.util.Utility.PhanQuyenKBH(obj.getuserId())+"  )  " ;
					conditionDH += "\n and dh.ddkd_fk in ( "+ geso.dms.center.util.Utility.get_DDKD_from_User( obj.getuserId(),obj.getLoaiNv() ) +" ) ";
				}
				
				query =  		"\n with a as  " +
				"\n ( " +

				"\n  	select khuvuc ,ngaynhap, sum(sellout)sellout " +
				"\n  	from " +
				"\n  	(	 " +
				"\n 		select v.Ten khuvuc , dh.NGAYNHAP , sum(soluong*giamua) sellout	  " +
				"\n 		from DONHANG dh  " +
				"\n 		inner join DONHANG_SANPHAM dhsp on dhsp.DONHANG_FK = dh.PK_SEQ  " +
				"\n 		inner join NHAPHANPHOI npp on npp.PK_SEQ = dh.NPP_FK  " +
				"\n 		inner join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK  " +
				"\n 		inner join vung v on v.PK_SEQ = kv.VUNG_FK  " +
				"\n 		where dh.TRANGTHAI = 1 and  month(dh.NGAYNHAP) ="+obj.getFromMonth()+" and year(dh.NGAYNHAP) = "+obj.getFromYear()+"" +
				"\n				and not exists ( select 1 from DONHANGTRAVE x where x.trangthai = 3 and x.DONHANG_FK = dh.PK_SEQ)   " +
				conditionDH +
				"\n 		group by v.Ten  , dh.NGAYNHAP " +
				"\n 		union all " +
				"\n 		select v.Ten khuvuc , dh.NGAYNHAP , sum((-1)*soluong*giamua) sellout	  " +
				"\n 		from DONHANGTRAVE dh  " +
				"\n 		inner join DONHANGTRAVE_SANPHAM dhsp on dhsp.DONHANGTRAVE_FK = dh.PK_SEQ  " +
				"\n 		inner join NHAPHANPHOI npp on npp.PK_SEQ = dh.NPP_FK  " +
				"\n 		inner join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK  " +
				"\n 		inner join vung v on v.PK_SEQ = kv.VUNG_FK  " +
				"\n 		where dh.donhang_fk is null and  dh.TRANGTHAI = 3 and  month(dh.NGAYNHAP) ="+obj.getFromMonth()+" and year(dh.NGAYNHAP) = "+obj.getFromYear()+"  " +
				conditionDH +
				"\n 		group by v.Ten  , dh.NGAYNHAP " +
				"\n 	)ds " +
				"\n 	group by khuvuc,	NGAYNHAP " +						
				"\n ) " +
				"\n select 'Total' Day ,  " + Sell_Out_Daily_Report +
				"\n 	, "+Sell_Out_Daily_Report_Total+" [isNum_Total GT] " +
				"\n from  " +
				"\n ( " +
				"\n 	select khuvuc,sum (sellout) sellout " +
				"\n 	from a " +
				"\n 	group by khuvuc " +
				"\n )a pivot (sum(sellout) for khuvuc in ("+pivot+")) as dat " +
				"\n union all " +
				"\n select dat.NGAYNHAP ,  " + Sell_Out_Daily_Report +
				"\n 	,  "+Sell_Out_Daily_Report_Total+"  TotalDay " +
				"\n from a  pivot (sum(sellout) for khuvuc in ("+pivot+")) as dat ";
			}
			else if( action.equals("Inventory Report") )
			{
				String conditionDH = "";
				String condition = "";
				if(obj.getPhanloai().equals("2"))
				{
					conditionDH += "\n and dh.kbh_fk in ( "+geso.dms.center.util.Utility.PhanQuyenKBH(obj.getuserId())+"  )  " ;
					conditionDH += "\n and dh.ddkd_fk in ( "+ geso.dms.center.util.Utility.get_DDKD_from_User( obj.getuserId(),obj.getLoaiNv() ) +" ) ";
					
					condition += "\n and kq.kbh_fk in ( "+geso.dms.center.util.Utility.PhanQuyenKBH(obj.getuserId())+"  )  " ;
					condition += "\n and npp.pk_seq in ( "+ geso.dms.center.util.Utility.get_NPP_from_User( obj.getuserId(),obj.getLoaiNv() ) +" ) ";
					
				}
				
				query = "\n with dsbh as " +
				"\n (	 " +
				"\n		select VUNG_FK,NGAYNHAP, sum(doanhso) doanhso" +
				"\n		from" +
				"\n		(  " +
				"\n 		select dh.npp_fk VUNG_FK, sum(soluong*giamua)doanhso, NGAYNHAP " +
				"\n 		from donhang dh " +
				"\n 		inner join DONHANG_SANPHAM dhsp on dh.PK_SEQ = dhsp.DONHANG_FK " +
				"\n 		where    month(dh.NGAYNHAP) = "+obj.getFromMonth()+" and year(dh.NGAYNHAP)="+obj.getFromYear()+"  " +
				"\n 			and not exists ( select 1 from DONHANGTRAVE x where x.trangthai = 3 and x.DONHANG_FK = dh.PK_SEQ)  " +
				"\n 			and dh.TRANGTHAI = 1  " + conditionDH +
				"\n 		group by  dh.npp_fk, NGAYNHAP " +
				"\n			union all " +
				"\n			select dh.npp_fk VUNG_FK, sum((-1)*soluong*giamua)doanhso, NGAYNHAP " +
				"\n 		from DONHANGTRAVE dh  " +
				"\n 		inner join DONHANGTRAVE_SANPHAM dhsp on dhsp.DONHANGTRAVE_FK = dh.PK_SEQ  " +
				"\n 		where    month(dh.NGAYNHAP) = "+obj.getFromMonth()+" and year(dh.NGAYNHAP)="+obj.getFromYear()+"  " +
				"\n 			and dh.TRANGTHAI = 3  " + conditionDH +
				"\n 		group by  dh.npp_fk, NGAYNHAP" +
				"\n		)ds" +
				"\n		group by VUNG_FK,NGAYNHAP " +
				"\n ) " +
				"\n ,info as " +
				"\n ( " +
				"\n 	select npp.pk_seq vungId,npp.ten,  sum(kq.dauky* g.gia) [Opening Inventory] ,   sum(kq.nhaphang* g.gia) [Sell in],  sum(kq.xuatban* g.gia) [Sell out] " +
				"\n 		, sum(kq.cuoiky* g.gia) - sum(kq.nhaphang* g.gia)  +  sum(kq.xuatban* g.gia)  - sum(kq.dauky* g.gia) [Adjusted] -- cheat tinh ajust " +
				"\n 		,	sum(kq.cuoiky* g.gia) [Closing Inventory]  " +
				"\n 	from NHAPHANPHOI npp  " +
				"\n 	cross apply ufn_XNT_TuNgay_DenNgay_FULL_New(npp.PK_SEQ,  '"+dauthang+"', convert(char(10),DATEADD(MONTH,1, '"+dauthang+"')- day(DATEADD(MONTH,1, '"+dauthang+"')),120) ) kq	  " +
				/*gia cho nay =0*/		"\n 	cross apply ( select 1 gia ) g" +
				"\n		where 1=1  " + condition +
				"\n		group by npp.pk_seq,npp.ten " +
				"\n ) " +
				"\n select  info.ten [Distributor] ,[Opening Inventory] [isNum_Opening Inventory],[Sell in] [isNum_Sell in], [Sell out] [isNum_Sell out] " +
				"\n 	,[Adjusted] [isNum_Adjusted], [Closing Inventory] [isNum_Closing Inventory],  dbo.phepchia(info.[Closing Inventory],ds.trungbinhban)  [isNum_Stock day]  " +   
				"\n from info " +
				"\n outer apply " +
				"\n ( " +
				"\n 	select dbo.PhepChia( sum ( dsbh.doanhso )  , count(distinct ngaynhap) )  trungbinhban " +
				"\n 	from dsbh where dsbh.VUNG_FK = info.vungId " +

				"\n )ds " +
				"\n union all " +
				"\n select 'Total',infoAll.* , dbo.phepchia(infoAll.[Closing Inventory],ds.trungbinhban)  [Stock day]  " + 
				"\n from  " +
				"\n (  " +
				"\n 	select  sum( [Opening Inventory])  [Opening Inventory],  sum( [Sell in]) [Sell in] " +
				"\n 		, sum([Sell out]) [Sell out], sum ( [Adjusted])  [Adjusted], sum( [Closing Inventory])  [Closing Inventory] " +
				"\n 	from info " +
				"\n )infoAll " +
				"\n inner join " +
				"\n ( " +
				"\n 	select dbo.PhepChia(  sum( dsbh.doanhso )  , count(distinct ngaynhap) )  trungbinhban " +  
				"\n 	from dsbh " +
				"\n )ds on 1=1  ";
			}
			else if(action.equals("Monthly Sales Report"))
			{
				String conditionDH = "";
				String condition = "";

				if(obj.getPhanloai().equals("2"))
				{
					condition += "\n and ddkd.kbh_fk in ( "+geso.dms.center.util.Utility.PhanQuyenKBH(obj.getuserId())+"  )  " ;
					condition += "\n and ddkd.pk_seq in ( "+ geso.dms.center.util.Utility.get_DDKD_from_User( obj.getuserId(),obj.getLoaiNv() ) +" ) ";
					
				}
				
				
				
				data_sql =  "\n  select ct.PK_SEQ ,ct.DIENGIAI " +
				"\n  from tieuchithuong_chitiet ct inner join tieuchitinhthuong t on ct.TIEUCHITINHTHUONG_FK = t.PK_SEQ  " +
				"\n  where t.THANG = "+obj.getFromMonth()+" and t.NAM =  "+obj.getFromYear()+"   and t.TRANGTHAI= 1 and tieuchi in (1,2)  ";
				rsData = obj.getDb().get(data_sql);
				String pivot_ct = "";
				String view  = "";
				String sum_ct = "";
				while(rsData.next())
				{
					String DIENGIAI = rsData.getString("DIENGIAI");
					String data_view  = "	[isNum_"+DIENGIAI+"_Target],[isNum_"+DIENGIAI+"_Actual]" +
					"	,100* dbo.PhepChia( [isNum_"+DIENGIAI+"_Actual], [isNum_"+DIENGIAI+"_Target])     [isNum_"+DIENGIAI+"_%]";

					view += pivot_ct.trim().length() > 0 ?  "," + data_view : data_view;

					String data_pivot_view  = "	[isNum_"+DIENGIAI+"_Target],[isNum_"+DIENGIAI+"_Actual],[isNum_"+DIENGIAI+"_%]";
					pivot_ct+= pivot_ct.trim().length() > 0 ?  "," + data_pivot_view : data_pivot_view;

					String data_sum = 	"sum(isnull([isNum_"+DIENGIAI+"_Target],0))[isNum_"+DIENGIAI+"_Target]" +
					",sum(isnull([isNum_"+DIENGIAI+"_Actual],0))[isNum_"+DIENGIAI+"_Actual]" ;
					sum_ct += sum_ct.trim().length() > 0 ?  "," + data_sum : data_sum;

				}
				query = "\n  with   so as  " +
				"\n   (  " +
				"\n  		 select  * " +
				"\n  		 from   " +
				"\n  		 ( " +
				"\n  		 	select [VUNG_FK],'isNum_' + [DIENGIAI]+'_'+col col,value from  " +
				"\n  		 	(  " +
				"\n  		 		 select kv.VUNG_FK, tct.DIENGIAI,sum(ct.ChiTieu) as [Target], sum(isnull(td.thucdat,0)) [Actual]  " +
				"\n  				 from ChiTieuNhanVien_DDKD ct " +
				"\n					 inner join  ChiTieuNhanVien t on ct.ctnv_fk = t.pk_seq and t.trangthai = 1 and t.THANG = "+obj.getFromMonth()+" and t.NAM =  "+obj.getFromYear()+"  " +
				"\n  				 inner join DaiDienKinhDoanh ddkd on ct.NhanVien_FK = ddkd.pk_seq  " +
				"\n  				 inner join nhaphanphoi npp on npp.pk_seq = ddkd.npp_fk  " +
				"\n  				 inner join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK " +
				"\n  				 inner join tieuchithuong_chitiet tct on ct.tctct_fk = tct.pk_seq   " +
				"\n  				 outer apply  [dbo].[ufn_KPI_DDKD]( '"+dauthang+"',ct.tctct_fk,ct.NhanVien_FK) td    " +
				"\n  				 where ct.chitieu > 0  and ct.TieuChi in ( 1,2)  " + condition +
				"\n  				 group by vung_fk, tct.DIENGIAI " +
				"\n  			) rt   " +
				"\n  		 	unpivot (value for col in ([Target], [Actual]) ) unpiv   " +
				"\n  		 ) tp  " +
				"\n  		 pivot ( SUM(value) FOR col in ( "+pivot_ct+" )) piv  " +
				"\n   ) " +
				"\n  select v.TEN [Region], " + view +
				"\n  from vung v   " +
				"\n  left join so on so.VUNG_FK = v.PK_SEQ " +
				"\n  union all" +
				"\n	 select x.[Region]	, " + view +
				"\n	 from ( select 'Total GT' [Region]  ) x  " +
				"\n	 outer apply ( select "+sum_ct+" from so )so  ";





			}
			else if(action.equals("Market Coverage and KPI"))
			{
				String conditionDH = "";
				String condition = "";
				if(obj.getPhanloai().equals("2"))
				{
					conditionDH += "\n and dh.kbh_fk in ( "+geso.dms.center.util.Utility.PhanQuyenKBH(obj.getuserId())+"  )  " ;
					condition += "\n and ddkd.kbh_fk in ( "+geso.dms.center.util.Utility.PhanQuyenKBH(obj.getuserId())+"  )  " ;
					
					conditionDH += "\n and dh.ddkd_fk in ( "+ geso.dms.center.util.Utility.get_DDKD_from_User( obj.getuserId(),obj.getLoaiNv() ) +" ) ";
					condition += "\n and ddkd.pk_seq in ( "+ geso.dms.center.util.Utility.get_DDKD_from_User( obj.getuserId(),obj.getLoaiNv() ) +" ) ";
					
					
					
					
					
				}
				
				query =  "\n with dsbh as " +
				"\n ( " +
				"\n 	select kv.VUNG_FK,dh.PK_SEQ dhId, dh.NGAYNHAP, dh.DDKD_FK,dh.NPP_FK,dh.KHACHHANG_FK,dhsp.SANPHAM_FK,dhsp.SOLUONG,dhsp.GIAMUA GIAMUA  " +
				"\n 	from donhang dh " +
				"\n 	inner join DONHANG_SANPHAM dhsp on dh.PK_SEQ = dhsp.DONHANG_FK " +
				"\n 	inner join NHAPHANPHOI npp on npp.PK_SEQ = dh.NPP_FK " +
				"\n 	inner join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK " +
				"\n 	inner join SANPHAM sp on sp.PK_SEQ = dhsp.SANPHAM_FK " +
				"\n 	where 1=1   " +
				"\n 		   and  month(dh.NGAYNHAP) = "+obj.getFromMonth()+" and year(dh.NGAYNHAP)="+obj.getFromYear()+"  " +
				"\n 			and not exists ( select 1 from DONHANGTRAVE x where x.trangthai = 3 and x.DONHANG_FK = dh.PK_SEQ)  " +
				"\n 			and dh.TRANGTHAI = 1 " + conditionDH +
				"\n		union all " +
				"\n 	select kv.VUNG_FK,null dhId, dh.NGAYNHAP, null DDKD_FK,dh.NPP_FK,null KHACHHANG_FK,null SANPHAM_FK,(-1)*dhsp.SOLUONG SOLUONG,dhsp.GIAMUA  " +
				"\n 	from DONHANGTRAVE dh  " +
				"\n 	inner join DONHANGTRAVE_SANPHAM dhsp on dhsp.DONHANGTRAVE_FK = dh.PK_SEQ  " +
				"\n 	inner join NHAPHANPHOI npp on npp.PK_SEQ = dh.NPP_FK " +
				"\n 	inner join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK " +
				"\n 	inner join SANPHAM sp on sp.PK_SEQ = dhsp.SANPHAM_FK " +
				"\n 	where 1=1   " +
				"\n 		   and  month(dh.NGAYNHAP) = "+obj.getFromMonth()+" and year(dh.NGAYNHAP)="+obj.getFromYear()+"  " +
				"\n 			and dh.TRANGTHAI = 3 " + conditionDH +
				"\n ) " +

				"\n , mcp as " +
				"\n ( " +
				"\n 	select kv.VUNG_FK,sum(AllSoKH) MCP " +
				"\n 	from DDKD_SOKH mcp " +
				"\n 	inner join NHAPHANPHOI npp on npp.PK_SEQ = mcp.NPP_FK" +
				"\n 	inner join daidienkinhdoanh ddkd on ddkd.pk_seq = mcp.DDKD_FK " +
				"\n 	inner join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK " +
				"\n 	where mcp.NGAY = (select top 1 ngay from DDKD_SOKH where month(ngay) =  "+obj.getFromMonth()+" and year(ngay)="+obj.getFromYear()+"  order by NGAY desc  ) " +
				condition +
				"\n 	group by kv.VUNG_FK " +
				"\n ) " +
				"\n	, vt as" +
				"\n	(" +
				"\n 	select kv.VUNG_FK,count( distinct vt.khachhang_fk ) AO " +
				"\n 	from ddkd_khachhang vt " +
				"\n		inner join khachhang kh on kh.pk_seq =vt.khachhang_fk " +
				"\n 	inner join NHAPHANPHOI npp on npp.PK_SEQ = kh.NPP_FK " +
				"\n 	inner join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK " +
				"\n 	inner join daidienkinhdoanh ddkd on ddkd.pk_seq = vt.DDKD_FK " +
				"\n 	where   month(vt.ngayghinhan) =  "+obj.getFromMonth()+" and year(vt.ngayghinhan)="+obj.getFromYear()+"  " +
				condition +
				"\n 	group by kv.VUNG_FK " +
				"\n )" +
				"\n select v.TEN [Region], mcp.MCP isNum_MCP, info.ASO isNum_ASO, isnull(vt.AO,0) AO , round(dbo.PhepChia ( info.ASO ,mcp.MCP),4) * 100 [isNum2_%Coverage],pc.PC isNum2_PC " +
				"\n 	,	round(  dbo.phepchia ( info.sumsku ,info.sodh) ,3) isNum2_LPPC " +
				"\n 	, round(  dbo.phepchia (   info.doanhso, info.sodh) ,3) isNum_DROPSIZE " +
				"\n 	, round(  dbo.phepchia (   info.doanhso, info.ASO) ,3) isNum_VPO " +
				"\n from vung v " +
				"\n inner join mcp  on mcp.VUNG_FK =v.PK_SEQ " +
				"\n outer apply " +
				"\n ( " +
				"\n 	select count(distinct khachhang_fk) ASO	 " +
				"\n 		,  sum(dsbh.SOLUONG * dsbh.GIAMUA) doanhso , count(distinct dsbh.dhId) sodh,  count(dsbh.sanpham_fk)  sumsku " +
				"\n 	from dsbh " +
				"\n 	where v.pk_seq = dsbh.VUNG_FK    " +

				"\n )info " +
				"\n left join vt on vt.vung_fk = v.pk_seq " +
				"\n outer apply " +
				"\n ( " +
				"\n 	select  round( dbo.phepchia ( sum ( sodh) , sum( nvbh)),3) PC " +
				"\n 	from " +
				"\n 	( " +
				"\n 		select  dsbh.NGAYNHAP,count(distinct dsbh.dhId) sodh , count(distinct dsbh.DDKD_FK) nvbh " +
				"\n 		from dsbh  " +
				"\n 		where v.pk_seq = dsbh.VUNG_FK   " +
				"\n 		group by dsbh.NGAYNHAP " +
				"\n 	) kq " +
				"\n )pc " +	 
				"\n where  1=1 " + 
				"\n 				union all " +
				"\n select 'Total', mcp.MCP, info.ASO, isnull(vt.AO,0) AO , round(dbo.PhepChia ( info.ASO ,mcp.MCP),4) * 100 [%Coverage],pc.PC " +
				"\n 					,	round(  dbo.phepchia ( info.sumsku ,info.sodh) ,3) LPPC " +
				"\n 					, round(  dbo.phepchia (   info.doanhso, info.sodh) ,3) DROPSIZE " +
				"\n 					, round(  dbo.phepchia (   info.doanhso, info.ASO) ,3) VPO " +
				"\n from  " +
				"\n (	 " +
				"\n 	select sum(MCP) MCP from mcp " +
				"\n )mcp " +
				"\n outer apply " +
				"\n ( " +
				"\n 	select count(distinct khachhang_fk) ASO	 " +
				"\n 		,  sum(dsbh.SOLUONG * dsbh.GIAMUA) doanhso , count(distinct dsbh.dhId) sodh,  count(dsbh.sanpham_fk)  sumsku " +
				"\n 	from dsbh  " +
				"\n )info " +
				"\n outer apply " +
				"\n ( " +
				"\n 	select sum(AO) AO	 " +
				"\n 	from vt  " +
				"\n )vt " +
				"\n outer apply " +
				"\n ( " +
				"\n 	select  round( dbo.phepchia ( sum ( sodh) , sum( nvbh)),3) PC " +
				"\n 	from " +
				"\n 	( " +
				"\n 		select  dsbh.NGAYNHAP,count(distinct dsbh.dhId) sodh , count(distinct dsbh.DDKD_FK) nvbh " +
				"\n 		from dsbh  " +
				"\n 		group by dsbh.NGAYNHAP " +
				"\n 	) kq " +
				"\n )pc " ;
			}
			else if( action.equals("Product Mix Report") )
			{
				
				String conditionDH = "";
				String condition = "";
				
				query = "\n with dsbh as " +
				"\n ( " +
				"\n 	select kv.VUNG_FK,nh.TEN NganhHang ,sum(soluong*giamua) sellout " +
				"\n 	from donhang dh " +
				"\n 	inner join DONHANG_SANPHAM dhsp on dh.PK_SEQ = dhsp.DONHANG_FK " +
				"\n 	inner join NHAPHANPHOI npp on npp.PK_SEQ = dh.NPP_FK " +
				"\n 	inner join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK " +
				"\n 	inner join SANPHAM sp on sp.PK_SEQ = dhsp.SANPHAM_FK  " +
				"\n 	inner join NGANHHANG nh on nh.PK_SEQ = sp.NganhHang_FK " +
				"\n 	where 1=1  " +
				"\n 		   and  month(dh.NGAYNHAP) =  "+obj.getFromMonth()+" and year(dh.NGAYNHAP) = "+obj.getFromYear()+"  " +
				"\n 			and not exists ( select 1 from DONHANGTRAVE x where x.trangthai = 3 and x.DONHANG_FK = dh.PK_SEQ)  " +
				"\n 			and dh.TRANGTHAI = 1 " + 
				"\n 	group by  kv.VUNG_FK,nh.TEN  " +
				"\n ) " +
				"\n select  v.TEN [Region] , isnull( a.[Rang xay],0) [isNum_R&G], isnull( a.[Hòa tan],0) [isNum_Instant], isnull( a.[Khác],0) [isNum_Other] " +
				"\n 	, isnull( a.[Rang xay],0)  + isnull( a.[Hòa tan],0)  + isnull( a.[Khác],0) isNum_Total" +
				"\n		, isnull( a.[Rang xay],0) * 100 / ( isnull( a.[Rang xay],0)  + isnull( a.[Hòa tan],0)  + isnull( a.[Khác],0) ) [isNum2_%R&G]" +
				"\n		, isnull( a.[Hòa tan],0)* 100  / ( isnull( a.[Rang xay],0)  + isnull( a.[Hòa tan],0)  + isnull( a.[Khác],0) ) [isNum2_%Instant]" +
				"\n		, isnull( a.[Khác],0)* 100  / ( isnull( a.[Rang xay],0)  + isnull( a.[Hòa tan],0)  + isnull( a.[Khác],0) )[isNum2_%Other] " +
				"\n		, 100 [isNum2_%Total]   " +
				"\n from vung v " +
				"\n outer apply " +
				"\n ( " +
				"\n 	select * " +
				"\n 	from " +
				"\n 	( " +
				"\n 		select * " +
				"\n 		from  dsbh where dsbh.VUNG_FK = v.PK_SEQ " +
				"\n 	)a  pivot (sum(sellout) for NganhHang in ([Rang xay],[Hòa tan],[Khác]))  as dat " +
				"\n )a " +
				"\n where  1=1 " +
				"\n union all " +
				"\n select 'Total' vung, isnull( a.[Rang xay],0) [R&G], isnull( a.[Hòa tan],0) [Instant], isnull( a.[Khác],0) [Other] " +
				"\n 	, isnull( a.[Rang xay],0)  + isnull( a.[Hòa tan],0)  + isnull( a.[Khác],0) Total " +
				"\n		, isnull( a.[Rang xay],0) * 100 / ( isnull( a.[Rang xay],0)  + isnull( a.[Hòa tan],0)  + isnull( a.[Khác],0) ) [isNum_%R&G]" +
				"\n		, isnull( a.[Hòa tan],0)* 100  / ( isnull( a.[Rang xay],0)  + isnull( a.[Hòa tan],0)  + isnull( a.[Khác],0) ) [isNum_%Instant]" +
				"\n		, isnull( a.[Khác],0)* 100  / ( isnull( a.[Rang xay],0)  + isnull( a.[Hòa tan],0)  + isnull( a.[Khác],0) )[isNum_%Other] " +
				"\n		, 100 [isNum_%Other]   " +
				"\n from " +
				"\n ( " +
				"\n 	select * " +
				"\n 	from " +
				"\n 	( " +
				"\n 		select NganhHang,sum(sellout)sellout " +
				"\n 		from  dsbh  " +
				"\n 		group by NganhHang " +
				"\n 	)a  pivot (sum(sellout) for NganhHang in ([Rang xay],[Hòa tan],[Khác]))  as dat " +
				"\n )a ";
			}
			else if( action.equals("Saleman Report"))
			{
				
				String condition = "";
				if(obj.getPhanloai().equals("2"))
				{
					
					
					condition += "\n and ddkd.kbh_fk in ( "+geso.dms.center.util.Utility.PhanQuyenKBH(obj.getuserId())+"  )  " ;
					condition += "\n and ddkd.pk_seq in ( "+ geso.dms.center.util.Utility.get_DDKD_from_User( obj.getuserId(),obj.getLoaiNv() ) +" ) ";
					
					
				}
				
				query ="\n   with vt as  " +
				"\n 			 (  " +
				"\n 					 	select kv.VUNG_FK ,v.TEN , dh.ngayghinhan, dh.ddkd_fk, count(distinct dh.khachhang_fk) sokh " +
				"\n 					 	from ddkd_khachhang dh  " +
				"\n 					 	inner join DAIDIENKINHDOANH ddkd on dh.ddkd_fk = ddkd.PK_SEQ  " +
				"\n 					 	inner join NHAPHANPHOI npp on npp.PK_SEQ = ddkd.NPP_FK  " +
				"\n 					 	inner join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK  " +
				"\n 						inner join VUNG v on v.PK_SEQ = kv.VUNG_FK	 " +
				"\n 					 	where 1=1  " + condition +
				"\n 					 		   and  month(dh.ngayghinhan) =  "+obj.getFromMonth()+" and year(dh.ngayghinhan) =  "+obj.getFromYear()+"   " + 
				"\n 					 	group by  kv.VUNG_FK ,v.ten, dh.ngayghinhan, dh.ddkd_fk " +
				"\n 			 )  " +
				"\n 					 select 'Plan' [_] "+ Sell_Out_Daily_Report1 +", 0  [isNum_Total GT] where 1=2 " +
				"\n 					 union all " +
				"\n 					 select *,"+Sell_Out_Daily_Report_Total1+" [isNum_Total GT] " +
				"\n	                     from " +
				"\n 					 ( " +
				"\n 						 select vt.TEN ,'Actual' ngayghinhan, count( distinct ddkd_fk) actual " +
				"\n 						 from vt  " +
				"\n 						 where sokh > 0 and ngayghinhan =(select top 1 ngay from DDKD_SOKH where month(ngay) =  "+obj.getFromMonth()+" and year(ngay)="+obj.getFromYear()+"  order by NGAY desc  ) " +
				"\n 						 group by   vt.TEN , vt.ngayghinhan " +
				"\n 					 )a  pivot (sum(actual) for TEN in ("+pivot+")) as vt " +
				"\n 					 union all " +
				"\n 					  select 'Gap' [_]"+Sell_Out_Daily_Report1+", 0  [isNum_Total GT]  where 1=2 " +
				"\n 					 union all " +
				"\n 					 select *, "+ Sell_Out_Daily_Report_Total1 +" [isNum_Total GT] " +
				"\n 					 from " +
				"\n 					 ( " +
				"\n 						 select vt.TEN ,'SM Active/Day: ' + vt.ngayghinhan ngayghinhan, count( distinct ddkd_fk) actual " +
				"\n 						 from vt  " +
				"\n 						 where sokh > 0 " +
				"\n 						 group by   vt.TEN , vt.ngayghinhan " +
				"\n 					 )a  pivot (sum(actual) for TEN in ("+pivot+")) as vt " ;
			}

			System.out.println("query = "+ query);
			return query;
		} catch (SQLException e) {
			return "";
		}

	}
}
