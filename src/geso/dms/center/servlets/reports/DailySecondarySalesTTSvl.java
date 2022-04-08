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

/*
 Thiet lap 3 function cho thuc hien viec lay doanh so 
 theo cac muc khach nhau.... 
 */

public class DailySecondarySalesTTSvl extends HttpServlet {
		
	private static final long serialVersionUID = 1L;

	public DailySecondarySalesTTSvl() {
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
		obj.settype("0");
		obj.init();
		
		session.setAttribute("obj", obj);
		String nextJSP = "/AHF/pages/Center/DailySecondarySalesReportCenter.jsp";
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
		
		obj.settungay(Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays"))));
		obj.setdenngay(Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays"))));
		
		obj.settumuc(Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tumuc"))));	   	
	   	obj.setdenmuc(Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denmuc"))));	
		
		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");
		obj.setkenhId(Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")))!=null?
				Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId"))):"");
		
		obj.setvungId(Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")))!=null?
				Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"))):"");
			
		obj.setkhuvucId(Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")))!=null?
				Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId"))):"");
		
		obj.setgsbhId(Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("gsbhs")))!=null?
				Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("gsbhs"))):"");
		
		obj.setnppId(Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")))!=null?
				Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"))):"");
		
		obj.setdvkdId(Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")))!=null?
				Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId"))):"");
		
		obj.setnhanhangId(Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhanhangId")))!=null?
				Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhanhangId"))):"");
		
		obj.setchungloaiId(Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chungloaiId")))!=null?
				Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chungloaiId"))):"");
		
		obj.setloainppId(Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loainppId")))!=null?
				Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loainppId"))):"");
				
		obj.settype(Utility.antiSQLInspection(request.getParameter("xemtheo")!=null?
				Utility.antiSQLInspection(request.getParameter("xemtheo")):""));			 		
	
		obj.setvat("1");
		System.out.println("Loai bao cao : "+obj.gettype());
		
	/*	String dsc = Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("discount")));
		if (dsc.equals("1"))
			obj.setdiscount("1");
		else
			obj.setdiscount("0");	*/	
		
		String xemtheo = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("xemtheo"));
		System.out.println("Xem theo: " + xemtheo + "\n");
		if(xemtheo.equals("1")) //xem theo thang
		{
			String tuthang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tuthang"));
			if(tuthang == null)
				tuthang = "";
			obj.setFromMonth(tuthang);
			System.out.println("Tu thang: " + tuthang);
			
			String denthang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denthang"));
			if(denthang == null)
				denthang = "";
			obj.setToMonth(denthang);
			System.out.println("Den thang: " + denthang);
		}
		
		
		geso.dms.center.util.Utility utilcenter = new geso.dms.center.util.Utility();
		
		String level = Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("level")));
		// dung bien less day de set level
		obj.setlessday(level);
		System.out.println("Den thang: " + obj.getlessday());
		
		String sql = " where npp.pk_seq in " + utilcenter.quyen_npp(obj.getuserId()) + " and kbh.pk_seq in  " + utilcenter.quyen_kenh(obj.getuserId());
		//String sql = "";
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
			sql = sql + " and nh.pk_seq ='" + obj.getnhanhangId() + "'";
		if (obj.getchungloaiId().length() > 0)
			sql = sql + " and cl.pk_seq ='" + obj.getchungloaiId()	+ "'";
/*		if (obj.getloainppId().length() > 0)
			sql = sql + " and zzzz ='" + obj.getchungloaiId()	+ "'"*/
		
		System.out.println("SQL la: " + sql + "\n");
		
		String action = (String) Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")));
		String nextJSP = "/AHF/pages/Center/DailySecondarySalesReportCenter.jsp";
		
		
		
		try{			
			if (action.equals("Taomoi")) //xuat excel
			{
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=DoanhSoTT.xlsm");
								
				String query = setQuery(sql, obj,level);
				//System.out.println("Query lay du lieu: " + query + "\n");
		        if(!CreatePivotTable(out,obj,query,level))
		        {
		        	obj.init();
		    		session.setAttribute("obj", obj);	
		        	response.sendRedirect(nextJSP);
		        }
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
			System.out.println("Xay ra exception roi..." + ex.toString());
			ex.printStackTrace();
			obj.init();
		
			obj.setMsg(ex.getMessage());
			
			session.setAttribute("obj", obj);
			response.sendRedirect(nextJSP);
			return;
		}
		
	}	
	
	private String setQuery(String sql, IStockintransit obj,String level) {
		String sql1=sql;
		String Distributor = "";
		String	Customer = "";
		 String NhanVienGiaoNhan= "";
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


		      String str = "";

			  if(obj.getFromMonth().length() <= 0) //xem theo ngay***************************************
			  {
					SalesRep = 
					" SELECT dvkd.donvikinhdoanh as BU, V.TEN AS REGION, KV.TEN AS AREA, NPP.TEN AS DISTRIBUTOR, NPP.SITECODE AS DISTCODE, \n" +
			  		"        NH.TEN AS BRAND,  CL.TEN AS CATEGORY, SP.MA + '_' + SP.TEN AS SKU, SP.MA AS SKUCODE, ISNULL(SP.MA_DDT,'') MADDT, DDKD.TEN AS SALESREP, DDKD.pk_seq as SALESREPID, KBH.DIENGIAI AS CHANEL, \n" +
			  		"        DH.NGAYNHAP AS NGAY, ISNULL(GSBH.TEN, 'Chua xac dinh') AS SALESUPER, sum(DH.SOLUONG) AS PIECE,  sum(DH.soluong * SP.trongluong) as SLTT, \n" +
			  		"        sum(round(isnull(dh.soluong,0)*isnull(dh.giamua,0)*" + obj.getvat() + " ,0)) as amount, \n";
					/*if(obj.getdiscount().equals("1"))
					{		SalesRep+=	"sum(dh.giamua * dh.soluong * " + obj.getvat() +") as amount, ";
							str = "sum(giamua * soluong * "+ obj.getvat() +") ";
					}
					else
					{
							SalesRep+=	"sum(dh.giamua * dh.soluong * " + obj.getvat() +" - dh.CHIETKHAU ) as amount, ";
							str = "sum(giamua * soluong * "+ obj.getvat() +" - CHIETKHAU) ";
					}*/
					
					SalesRep += 
					"     '' AS QUANTITY, DH.CHIETKHAUNPP AS CHIETKHAU , \n"+
				    "     DH.tennhomnpp, isnull((select lnpp.Ma from LoaiNPP lnpp where lnpp.pk_seq=NPP.LoaiNPP),'N/A') as maloainpp, DH.CHIETKHAUTT, (dh.giamua * " + obj.getvat() +") as dongiagoc, ISNULL(SP.TENVIETTAT,'') TENTATSP \n" +
					" FROM( \n" +
					/*"     SELECT  DH.NGAYNHAP, DH.DDKD_FK, DH.KHACHHANG_FK, DH.NPP_FK, DH.GSBH_FK, DH.KHO_FK, DH.KBH_FK, ISNULL(DH_SP.SANPHAM_FK, DH_SP1.SANPHAM_FK) AS SANPHAM_FK, \n" +
					"             ISNULL(DH_SP.GIAMUA,0) AS GIAMUA , (-1)*ISNULL(DH_SP.SOLUONG, DH_SP1.SOLUONG) AS SOLUONG, \n" +
					"             (-1)* ( ISNULL(DH_SP1.CHIETKHAU, 0) + ISNULL(CK.ptchietkhau,0) ) AS CHIETKHAUNPP , \n"+
					"       	  (-1)* ISNULL((select top 1 c.ptChietKhau  \n" +
					"		                    from BANGGIABANLECHUAN_NPP a inner join BANGGIABANLECHUAN b on a.BANGGIABANLECHUAN_FK = b.PK_SEQ  \n" +
					"		                      inner join BANGGIABLC_SANPHAM c on c.BGBLC_FK = b.PK_SEQ and c.SANPHAM_FK = DH_SP1.SANPHAM_FK  \n" +
					"		                    where a.NPP_FK= DH.NPP_FK and b.TuNgay <= DH.NGAYNHAP and b.KENH_FK =  DH.KBH_FK  \n" +
					"		                    order by b.TuNgay desc),0) CHIETKHAUTT , \n" +
					"             nnpp.Ten as tennhomnpp   \n" +
					"     FROM  DONHANGTRAVE DH LEFT OUTER JOIN  DONHANGTRAVE_SANPHAM DH_SP ON DH_SP.DONHANGTRAVE_FK = DH.PK_SEQ \n" +
					"           LEFT OUTER JOIN  DONHANG_SANPHAM DH_SP1 ON DH.DONHANG_FK = DH_SP1.DONHANG_FK  \n" +
					"           left join NhomNpp_Npp npp on DH.NPP_FK=npp.Npp_FK  \n"+
					"           left join NhomNpp nnpp on nnpp.PK_SEQ=npp.NhomNpp_FK  \n"+ 
					"      		left join KHACHHANG_SANPHAMCK CK on DH.KHACHHANG_FK = CK.khachhang_fk and DH.KBH_FK = CK.KENHBANHANG_FK and DH_SP1.SANPHAM_FK = CK.sanpham_fk   \n" +
					"     WHERE DH.TRANGTHAI = 3 AND DH.NGAYNHAP >='" + obj.gettungay() + "' AND DH.NGAYNHAP <= '" + obj.getdenngay() + "' " +
					"     UNION ALL \n" +*/
					"     SELECT  DH.NGAYNHAP, DH.DDKD_FK, DH.KHACHHANG_FK, DH.NPP_FK, DH.GSBH_FK, DH.KHO_FK, DH.KBH_FK, DH_SP.SANPHAM_FK, CAST(DH_SP.giamua as float) GIAMUA,\n" +
					"             DH_SP.SOLUONG, DH_SP.CHIETKHAU + ISNULL(CK.ptchietkhau,0) CHIETKHAUNPP , \n"+
					"        	  ISNULL((select top 1 c.ptChietKhau  \n" +
					"		              from BANGGIABANLECHUAN_NPP a inner join BANGGIABANLECHUAN b on a.BANGGIABANLECHUAN_FK = b.PK_SEQ  \n" +
					"		               inner join BANGGIABLC_SANPHAM c on c.BGBLC_FK = b.PK_SEQ and c.SANPHAM_FK = DH_SP.SANPHAM_FK  \n" +
					"		              where a.NPP_FK= DH.NPP_FK and b.TuNgay <= DH.NGAYNHAP and b.KENH_FK =  DH.KBH_FK  \n" +
					"		              order by b.TuNgay desc),0) CHIETKHAUTT , \n" +
					"             nnpp.Ten as tennhomnpp \n" +
					"     FROM DONHANG DH  INNER JOIN DONHANG_SANPHAM  DH_SP ON DH.PK_SEQ = DH_SP.DONHANG_FK \n" +
					" 		   left join NhomNpp_Npp npp on DH.NPP_FK=npp.Npp_FK \n"+
					"          left join NhomNpp nnpp on nnpp.PK_SEQ=npp.NhomNpp_FK \n"+ 
					"          left join KHACHHANG_SANPHAMCK CK on DH.KHACHHANG_FK = CK.khachhang_fk and DH.KBH_FK = CK.KENHBANHANG_FK and DH_SP.SANPHAM_FK = CK.sanpham_fk "+
					"     WHERE DH.TRANGTHAI = 1 AND DH.NGAYNHAP >='" + obj.gettungay() + "' AND DH.NGAYNHAP <= '" + obj.getdenngay() + "'";
										
					SalesRep += " ) DH  \n" +
					" INNER JOIN SANPHAM SP ON DH.SANPHAM_FK = SP.PK_SEQ \n" +
					" INNER JOIN NHAPHANPHOI NPP ON DH.NPP_FK = NPP.PK_SEQ ";
					
					if(obj.getloainppId().length()>0)
					{
						SalesRep += " and NPP.LoaiNPP = " + obj.getloainppId() + " ";
					}
					
					SalesRep +=
					" INNER JOIN KHUVUC KV ON NPP.KHUVUC_FK = KV.PK_SEQ INNER JOIN VUNG V ON KV.VUNG_FK = V.PK_SEQ \n" +
					" INNER JOIN NHANHANG NH ON SP.NHANHANG_FK = NH.PK_SEQ LEFT JOIN CHUNGLOAI CL ON SP.CHUNGLOAI_FK = CL.PK_SEQ\n" +
					" INNER JOIN DAIDIENKINHDOANH DDKD ON DH.DDKD_FK = DDKD.PK_SEQ INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK \n" +
					" INNER JOIN kenhbanhang KBH on DH.kbh_fk = KBH.pk_seq \n" +
					"  LEFT JOIN GIAMSATBANHANG GSBH ON DH.GSBH_FK = GSBH.PK_SEQ  \n" + sql;
					
					SalesRep += 
					" GROUP BY dvkd.donvikinhdoanh, V.TEN, KV.TEN,  NPP.TEN, NPP.SITECODE, NH.TEN, CL.TEN, SP.MA + '_' + SP.TEN, SP.MA, DDKD.TEN, \n" + 
					"          DDKD.pk_seq, KBH.DIENGIAI, DH.NGAYNHAP, GSBH.TEN, DH.CHIETKHAUNPP, DH.tennhomnpp, NPP.LoaiNPP, DH.CHIETKHAUTT, dh.giamua, ISNULL(SP.TENVIETTAT,''), ISNULL(SP.MA_DDT,'')    \n";
							
							
					
					if(obj.gettumuc().length()>0 && obj.getdenmuc().length()>0 )
					{
						//SalesRep += "  HAVING "+str+" BETWEEN "+ obj.gettumuc().replaceAll(",", "") +" AND "+ obj.getdenmuc().replaceAll(",", "") ;
						SalesRep += "  HAVING sum(giamua * soluong * "+ obj.getvat() +") BETWEEN "+ obj.gettumuc().replaceAll(",", "") +" AND "+ obj.getdenmuc().replaceAll(",", "") ;
					}
					
					
					Distributor = 
					" SELECT NPPID, dvkd.donvikinhdoanh as BU, V.TEN AS REGION, KV.TEN AS AREA, NPP.TEN AS DISTRIBUTOR, NPP.SITECODE AS DISTCODE, NH.TEN AS BRAND, \n" +
					"        CL.TEN AS CATEGORY, SP.MA + '_' + SP.TEN AS SKU, SP.MA AS SKUCODE, ISNULL(SP.MA_DDT,'') MADDT, KBH.DIENGIAI AS CHANEL, DH.NGAYNHAP as ngay, SUM(DH.SOLUONG) AS PIECE, sum(DH.soluong * SP.trongluong) as SLTT, \n" +
					"        sum(round(dh.giamua * dh.soluong * " + obj.getvat() +", 0)) as amount, \n";  

					/*if(obj.getdiscount().equals("1"))
					{		
						   	Distributor+= " sum(dh.giamua * dh.soluong * " + obj.getvat() +") as amount, ";
						   	str = " sum(giamua * soluong * "+obj.getvat()+") ";
					}
					else
					{		 
							Distributor+= " sum(DH.GIAMUA * DH.SOLUONG *  "+ obj.getvat() +" - dh.CHIETKHAU) as amount, ";
							str = " sum(GIAMUA * SOLUONG *  "+ obj.getvat() +" - CHIETKHAU) ";
					} */

					 Distributor +=  
					"      '' AS QUANTITY, DH.CHIETKHAUNPP AS CHIETKHAU ,DH.tennhomnpp, \n" +
					"       isnull((select lnpp.Ma from LoaiNPP lnpp where lnpp.pk_seq=NPP.LoaiNPP),'N/A') as maloainpp, DH.chietkhauTT, (dh.giamua * " + obj.getvat() +") as dongiagoc, ISNULL(SP.TENVIETTAT,'') TENTATSP \n" +
					" FROM (\n" +
				/*	"      SELECT  DH.NGAYNHAP, DH.DDKD_FK, DH.NPP_FK as NPPID, DH.GSBH_FK, DH.KHO_FK, DH.KBH_FK, ISNULL(DH_SP.SANPHAM_FK, DH_SP1.SANPHAM_FK) AS SANPHAM_FK, \n" +
					"              ISNULL(DH_SP.GIAMUA, 0) AS GIAMUA , (-1)*ISNULL(DH_SP.SOLUONG, DH_SP1.SOLUONG) AS SOLUONG, \n" +
					"       		(-1)* ( ISNULL(DH_SP1.CHIETKHAU, 0) + ISNULL(CK.ptchietkhau,0) ) AS CHIETKHAUNPP, \n" +
					"       		(-1)* ISNULL((select top 1 c.ptChietKhau  \n" +
					"		                      from BANGGIABANLECHUAN_NPP a inner join BANGGIABANLECHUAN b on a.BANGGIABANLECHUAN_FK = b.PK_SEQ  \n" +
					"		                      inner join BANGGIABLC_SANPHAM c on c.BGBLC_FK = b.PK_SEQ and c.SANPHAM_FK = DH_SP1.SANPHAM_FK  \n" +
					"		                      where a.NPP_FK= DH.NPP_FK and b.TuNgay <= DH.NGAYNHAP and b.KENH_FK =  DH.KBH_FK  \n" +
					"		                      order by b.TuNgay desc),0) CHIETKHAUTT , \n" +
					"        		nnpp.ten as tennhomnpp \n" +
					"  		FROM  DONHANGTRAVE DH \n" +
					"     		 LEFT OUTER JOIN  DONHANGTRAVE_SANPHAM DH_SP ON DH_SP.DONHANGTRAVE_FK = DH.PK_SEQ  \n" +
					"      		 LEFT OUTER JOIN  DONHANG_SANPHAM DH_SP1 ON DH.DONHANG_FK = DH_SP1.DONHANG_FK \n" +
					"      		 left join NhomNpp_Npp npp on DH.NPP_FK=npp.Npp_FK \n"+
					"      		 left join NhomNpp nnpp on nnpp.PK_SEQ=npp.NhomNpp_FK \n"+
					"      		 left join KHACHHANG_SANPHAMCK CK on DH.KHACHHANG_FK = CK.khachhang_fk and DH.KBH_FK = CK.KENHBANHANG_FK and DH_SP1.SANPHAM_FK = CK.sanpham_fk   \n" +
					"  		WHERE DH.TRANGTHAI = 3 AND DH.NGAYNHAP >='"+obj.gettungay()+"' AND DH.NGAYNHAP <= '"+obj.getdenngay()+"' \n" +
					"  		UNION ALL \n" +*/

					" 		 SELECT DH.NGAYNHAP, DH.DDKD_FK, DH.NPP_FK as NPPID, DH.GSBH_FK, DH.KHO_FK, DH.KBH_FK, DH_SP.SANPHAM_FK, DH_SP.giamua as GIAMUA, DH_SP.SOLUONG, \n" +
					"       		DH_SP.CHIETKHAU + ISNULL(CK.ptchietkhau,0) CHIETKHAUNPP, \n" +
					"        		ISNULL((select top 1 c.ptChietKhau  \n" +
					"		               from BANGGIABANLECHUAN_NPP a inner join BANGGIABANLECHUAN b on a.BANGGIABANLECHUAN_FK = b.PK_SEQ  \n" +
					"		               inner join BANGGIABLC_SANPHAM c on c.BGBLC_FK = b.PK_SEQ and c.SANPHAM_FK = DH_SP.SANPHAM_FK  \n" +
					"		               where a.NPP_FK= DH.NPP_FK and b.TuNgay <= DH.NGAYNHAP and b.KENH_FK =  DH.KBH_FK  \n" +
					"		               order by b.TuNgay desc),0) CHIETKHAUTT , \n" +
					"        	    nnpp.ten as tennhomnpp  \n" +
					"  		FROM DONHANG DH  INNER JOIN DONHANG_SANPHAM  DH_SP ON DH.PK_SEQ = DH_SP.DONHANG_FK \n" +
					"      		left join NhomNpp_Npp npp on DH.NPP_FK=npp.Npp_FK \n"+
					"           left join NhomNpp nnpp on nnpp.PK_SEQ=npp.NhomNpp_FK \n"+ 
					"           left join KHACHHANG_SANPHAMCK CK on DH.KHACHHANG_FK = CK.khachhang_fk and DH.KBH_FK = CK.KENHBANHANG_FK and DH_SP.SANPHAM_FK = CK.sanpham_fk "+
					"            WHERE  DH.TRANGTHAI = 1 AND DH.NGAYNHAP >='"+obj.gettungay()+"' AND DH.NGAYNHAP <= '"+obj.getdenngay()+"' "+
					"        ) DH \n" +

					"   INNER JOIN SANPHAM SP ON DH.SANPHAM_FK = SP.PK_SEQ \n" +
					"	INNER JOIN NHAPHANPHOI NPP ON DH.NPPID = NPP.PK_SEQ	\n";
					 	
					 	if(obj.getloainppId().length()>0)
					 	{
					 		Distributor += " and NPP.LoaiNPP = " + obj.getloainppId() + " \n";
					 	}
					 
					 Distributor +=
					"  INNER JOIN KHUVUC KV ON NPP.KHUVUC_FK = KV.PK_SEQ INNER JOIN VUNG V ON KV.VUNG_FK = V.PK_SEQ \n" +
					"  INNER JOIN NHANHANG NH ON SP.NHANHANG_FK = NH.PK_SEQ LEFT JOIN CHUNGLOAI CL ON SP.CHUNGLOAI_FK = CL.PK_SEQ \n" +
					"  inner join kenhbanhang KBH on DH.kbh_fk = KBH.pk_seq INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK \n" +
					"    " + sql +
					" GROUP BY nppid, dvkd.donvikinhdoanh, V.TEN, KV.TEN, NPP.TEN, NPP.SITECODE, NH.TEN, CL.TEN, SP.MA + '_' + SP.TEN, SP.MA, KBH.DIENGIAI, DH.NGAYNHAP, DH.CHIETKHAUNPP ,DH.tennhomnpp, NPP.LoaiNPP, DH.chietkhauTT, DH.GIAMUA, ISNULL(SP.TENVIETTAT,''), ISNULL(SP.MA_DDT,'')     \n";
				
					 
					 if(obj.gettumuc().length() > 0 && obj.getdenmuc().length() > 0)
					 {
						 Distributor += " HAVING  sum(giamua * soluong * "+obj.getvat()+") BETWEEN "+obj.gettumuc().replaceAll(",", "")+" AND "+obj.getdenmuc().replaceAll(",", "");
					 }
					 
					 
					Customer = 
					" SELECT dvkd.donvikinhdoanh as BU, V.TEN AS REGION, V.pk_seq as regionId, KV.TEN AS AREA, KV.PK_seq as areaId, NPP.TEN AS DISTRIBUTOR, NPP.SITECODE AS DISTCODE, NPP.pk_seq as nppId, \n" +
					"        NH.TEN AS BRAND, NH.PK_SEQ as BRANDID, CL.TEN AS CATEGORY, CL.pk_seq as CATEGORYID, SP.MA + '_' + SP.TEN AS SKU, SP.MA AS SKUCODE, ISNULL(SP.MA_DDT,'') MADDT, DDKD.TEN AS SALESREP, DDKD.pk_seq as SALESREPID, \n" +
					"        KH.TEN  AS CUSTOMER,isnull(KH.DIACHI,N'Chưa xác định') as DIACHI, KH.PK_SEQ AS CUSTOMERCODE, case when CHARINDEX('_', kh.smartid) > 0 then rtrim(substring(kh.smartid, CHARINDEX('_', kh.smartid)+1, 10)) + npp.sitecode \n" +
					"        when CHARINDEX('_', kh.smartid) <= 0 then kh.smartid + '-' + npp.sitecode end as smartid, \n" +
					"        ISNULL(VTCH.VITRI,N'Chua Xac Dinh') AS OUTLETPOSITION, ISNULL(M.DIENGIAI, 'Chua Xac Dinh') AS OUTLETTYPE, KBH.DIENGIAI AS CHANEL, KBH.pk_seq as CHANELID,\n" +
					"        DH.NGAYNHAP AS OFFDATE, DH.SOLUONG AS PIECE, (DH.soluong * SP.trongluong) as SLTT,round(isnull(dh.soluong,0)*isnull(dh.giamua,0)*" + obj.getvat() + " ,0)  as amount , \n";
			
					 /*if(obj.getdiscount().equals("1"))
					 { 
						 Customer +=" dh.giamua * dh.soluong * " + obj.getvat() + " as amount ,";
						 str = " giamua * soluong * " + obj.getvat()+" ";
					 }
				     else
				     {
				    	 Customer +=" dh.giamua * dh.soluong * " + obj.getvat()+" - dh.chietkhau  as amount ,";
				    	 str = "giamua * soluong * "+ obj.getvat() +" - chietkhau ";
				     }*/
					 
					Customer += 
					"       ISNULL(GSBH.TEN, 'Chua xac dinh') AS SALESUPER, '' AS QUANTITY, \n" +
					"       ISNULL(QH.TEN, 'Chua xac dinh') AS TOWN, ISNULL(TT.TEN, 'Chua xac dinh') AS PROVINCE, HCH.DIENGIAI AS OUTLETCLASS, NKH.DIENGIAI AS NHOMKHACHHANG, NSP.DIENGIAI AS NHOMSANPHAM, isnull(KH.PHUONG,N'Chưa xác định') as PHUONG, DH.CHIETKHAUNPP AS CHIETKHAU, \n" +
					"       DH.tennhomnpp, isnull((select lnpp.Ma from LoaiNPP lnpp where lnpp.pk_seq=NPP.LoaiNPP),'N/A') as maloainpp, DH.CHIETKHAUTT, dh.giamua * " + obj.getvat() + " as dongiagoc, ISNULL(SP.TENVIETTAT,'') TENTATSP \n" +
					" FROM( \n" +
					/*"      SELECT  DH.NGAYNHAP, DH.DDKD_FK, DH.KHACHHANG_FK, DH.NPP_FK, DH.GSBH_FK, DH.KHO_FK, DH.KBH_FK, ISNULL(DH_SP.SANPHAM_FK, DH_SP1.SANPHAM_FK) AS SANPHAM_FK, \n" +
					"              ISNULL(DH_SP.GIAMUA,0) AS GIAMUA , (-1)*ISNULL(DH_SP.SOLUONG, DH_SP1.SOLUONG) AS SOLUONG, \n" +
					"              (-1)*( ISNULL(DH_SP1.CHIETKHAU, 0) + ISNULL(CK.ptchietkhau,0) ) AS CHIETKHAUNPP , \n"+
					"       	   (-1)* ISNULL((select top 1 c.ptChietKhau  \n" +
					"		                     from BANGGIABANLECHUAN_NPP a inner join BANGGIABANLECHUAN b on a.BANGGIABANLECHUAN_FK = b.PK_SEQ  \n" +
					"		                      inner join BANGGIABLC_SANPHAM c on c.BGBLC_FK = b.PK_SEQ and c.SANPHAM_FK = DH_SP1.SANPHAM_FK  \n" +
					"		                     where a.NPP_FK= DH.NPP_FK and b.TuNgay <= DH.NGAYNHAP and b.KENH_FK =  DH.KBH_FK  \n" +
					"		                     order by b.TuNgay desc),0) CHIETKHAUTT , \n" +
					"              nnpp.ten as tennhomnpp  \n" +
					"      FROM DONHANGTRAVE DH LEFT OUTER JOIN  DONHANGTRAVE_SANPHAM DH_SP ON DH_SP.DONHANGTRAVE_FK = DH.PK_SEQ \n" +
					"           LEFT OUTER JOIN  DONHANG_SANPHAM DH_SP1 ON DH.DONHANG_FK = DH_SP1.DONHANG_FK  \n" +
					"           left join NhomNpp_Npp npp on DH.NPP_FK=npp.Npp_FK \n"+
					"           left join NhomNpp nnpp on nnpp.PK_SEQ=npp.NhomNpp_FK \n"+ 
					"      		 left join KHACHHANG_SANPHAMCK CK on DH.KHACHHANG_FK = CK.khachhang_fk and DH.KBH_FK = CK.KENHBANHANG_FK and DH_SP1.SANPHAM_FK = CK.sanpham_fk   \n" +
					"      WHERE DH.TRANGTHAI = 3 AND  DH.NGAYNHAP >='" + obj.gettungay() + "' AND DH.NGAYNHAP <= '" + obj.getdenngay() + "' \n" +
					"      UNION ALL \n" +*/
				    "      SELECT  DH.NGAYNHAP, DH.DDKD_FK, DH.KHACHHANG_FK, DH.NPP_FK, DH.GSBH_FK, DH.KHO_FK, DH.KBH_FK, DH_SP.SANPHAM_FK, DH_SP.giamua as GIAMUA, \n" +
					"              DH_SP.SOLUONG, DH_SP.CHIETKHAU + ISNULL(CK.ptchietkhau,0) CHIETKHAUNPP, \n"+
					"        	   ISNULL((select top 1 c.ptChietKhau  \n" +
					"		               from BANGGIABANLECHUAN_NPP a inner join BANGGIABANLECHUAN b on a.BANGGIABANLECHUAN_FK = b.PK_SEQ  \n" +
					"		               inner join BANGGIABLC_SANPHAM c on c.BGBLC_FK = b.PK_SEQ and c.SANPHAM_FK = DH_SP.SANPHAM_FK  \n" +
					"		               where a.NPP_FK= DH.NPP_FK and b.TuNgay <= DH.NGAYNHAP and b.KENH_FK =  DH.KBH_FK  \n" +
					"		               order by b.TuNgay desc),0) CHIETKHAUTT , \n" +
					"              nnpp.ten as tennhomnpp  \n" +
					"      FROM DONHANG DH  INNER JOIN DONHANG_SANPHAM  DH_SP ON DH.PK_SEQ = DH_SP.DONHANG_FK \n" +
					"           left join NhomNpp_Npp npp on DH.NPP_FK=npp.Npp_FK \n"+
					"           left join NhomNpp nnpp on nnpp.PK_SEQ=npp.NhomNpp_FK \n"+ 
					"           left join KHACHHANG_SANPHAMCK CK on DH.KHACHHANG_FK = CK.khachhang_fk and DH.KBH_FK = CK.KENHBANHANG_FK and DH_SP.SANPHAM_FK = CK.sanpham_fk "+
					"      WHERE  DH.TRANGTHAI = 1 AND  DH.NGAYNHAP >='" + obj.gettungay() + "' AND DH.NGAYNHAP <= '" + obj.getdenngay() + "' \n"; 										
					
				Customer += " ) DH \n" +
					" INNER JOIN SANPHAM SP ON DH.SANPHAM_FK = SP.PK_SEQ \n" +
					" INNER JOIN NHAPHANPHOI NPP ON DH.NPP_FK = NPP.PK_SEQ \n";
						if(obj.getloainppId().length()>0)
						{
							Customer += " and NPP.LoaiNPP = " + obj.getloainppId() + " \n";
						}
				
					Customer+= 
					" INNER JOIN KHUVUC KV ON NPP.KHUVUC_FK = KV.PK_SEQ INNER JOIN VUNG V ON KV.VUNG_FK = V.PK_SEQ \n" +
					" INNER JOIN NHANHANG NH ON SP.NHANHANG_FK = NH.PK_SEQ LEFT JOIN CHUNGLOAI CL ON SP.CHUNGLOAI_FK = CL.PK_SEQ \n" +
					" INNER JOIN DAIDIENKINHDOANH DDKD ON DH.DDKD_FK = DDKD.PK_SEQ INNER JOIN KHACHHANG KH ON DH.KHACHHANG_FK = KH.PK_SEQ \n" +
					" LEFT JOIN VITRICUAHANG VTCH ON KH.VTCH_FK = VTCH.PK_SEQ INNER JOIN KENHBANHANG KBH ON DH.KBH_FK = KBH.PK_SEQ \n" +
					" LEFT JOIN LOAICUAHANG M ON KH.LCH_FK = M.PK_SEQ LEFT JOIN GIAMSATBANHANG GSBH ON DH.GSBH_FK = GSBH.PK_SEQ \n" +
					" INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK  \n" +
					" LEFT JOIN QUANHUYEN QH ON KH.QUANHUYEN_FK = QH.PK_SEQ LEFT JOIN TINHTHANH TT ON KH.TINHTHANH_FK = TT.PK_SEQ \n" +
					" LEFT JOIN HANGCUAHANG HCH ON KH.HCH_FK = HCH.PK_SEQ LEFT JOIN NHOMKHACHHANG_KHACHHANG NKHKH ON NKHKH.KH_FK = KH.PK_SEQ \n" +
					" LEFT JOIN NHOMKHACHHANG NKH ON NKH.PK_SEQ = NKHKH.NKH_FK \n" +
					" LEFT JOIN ( SELECT SP_FK, MAX(NSP_FK) AS NHOMSP FROM NHOMSANPHAM_SANPHAM  GROUP BY SP_FK ) NSPSP \n" +
					"           ON NSPSP.SP_FK = SP.PK_SEQ LEFT JOIN NHOMSANPHAM NSP ON NSP.PK_SEQ = NSPSP.NHOMSP  \n" +
					" LEFT JOIN \n" +
					" ( \n" +
					"  SELECT  KHACHHANG_FK,SUM(MUCDS) AS DS \n" +
					"  FROM ( " +
					"       SELECT  DH.KHACHHANG_FK,SUM( ISNULL(DH_SP.GIAMUA, DH_SP1.GIAMUA) *   \n" +
					"               (-1)*ISNULL(DH_SP.SOLUONG, DH_SP1.SOLUONG) )  AS MUCDS \n" +
					"       FROM DONHANGTRAVE DH LEFT OUTER JOIN  DONHANGTRAVE_SANPHAM DH_SP  ON DH_SP.DONHANGTRAVE_FK = DH.PK_SEQ  \n" +
					"              LEFT OUTER JOIN  DONHANG_SANPHAM DH_SP1 ON DH.DONHANG_FK = DH_SP1.DONHANG_FK     \n" +
					"       WHERE DH.TRANGTHAI = 3 AND  DH.NGAYNHAP >='"+obj.gettungay()+"' AND  DH.NGAYNHAP <= '"+obj.getdenngay()+"'   \n" +
					"       GROUP BY DH.KHACHHANG_FK \n" +
					"       UNION ALL \n" +
					"      SELECT DH.KHACHHANG_FK, SUM( DH_SP.GIAMUA* DH_SP.SOLUONG ) AS MUCDS  \n" +
					"      FROM DONHANG DH  INNER JOIN DONHANG_SANPHAM  DH_SP ON DH.PK_SEQ = DH_SP.DONHANG_FK \n" +
					"      WHERE  DH.TRANGTHAI = 1 AND DH.NGAYNHAP >='"+obj.gettungay()+"' AND DH.NGAYNHAP <= '"+obj.getdenngay()+"'  \n" +
					"      GROUP BY KHACHHANG_FK \n" +
					"     ) MUCDS \n" +
					" GROUP BY KHACHHANG_FK \n" +
					" ) MUCDS ON MUCDS.KHACHHANG_FK=KH.PK_SEQ \n" ;
					
					
				 if(obj.gettumuc().length() > 0  )
				 {
					 sql+=" and mucds.ds >= "+obj.gettumuc().replace(",","");
				 }
			     if(obj.getdenmuc().length() >0)
			     {
			    	 sql+=" and mucds.ds <= "+obj.getdenmuc().replace(",", "");
			     }
			  	
			     Customer += sql;
			  }
			  else //xem theo thang***************************************
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
						sql = sql + " and categoryId ='" + obj.getchungloaiId()	+ "'";

				  SalesRep =  
					"select BU, region, area, isnull(salesuper, 'Chua xac dinh') as salesuper, SalesRepId, SalesRep, distcode, brand, \n" +
			  		"       category, distributor, SKU, SKUcode, ISNULL(SP.MA_DDT,'') MADDT, chanel, sum(piece) as piece,  sum( piece* isnull(sp.trongluong ,0)) as SLTT  , \n" +
			  		"       sum(quantity) as quantity, thang as ngay,SUM(amount_CK_NO_VAT_YES)-SUM(amount_CK_YES_VAT_NO) as ChietKhau,nnpp.ten as tennhomnpp, \n";
			  
				  if(obj.getdiscount().equals("1"))
				  {
						
							SalesRep += " sum(amount_CK_YES_VAT_NO) as amount \n";
						
				  }
				  else
				  {
				
						  SalesRep += " sum(amount_CK_NO_VAT_NO) as amount \n";
					 
				  }
				  
				  SalesRep += " , isnull((select lnpp.Ma from LoaiNPP lnpp where lnpp.pk_seq=npp2.LoaiNPP),'N/A') as maloainpp, ISNULL(SP.TENVIETTAT,'') TENTATSP \n";
				  
				  SalesRep += 
				     " from doanhsobanhang left join sanpham sp  on sp.ma=doanhsobanhang.skucode    \n" +
					 "      left join NhomNpp_Npp npp on doanhsobanhang.NPPID=npp.Npp_FK \n"+
					 "      left join NhomNpp nnpp on nnpp.PK_SEQ=npp.NhomNpp_FK \n"+ 
					 "      inner join NHAPHANPHOI npp2 on npp2.PK_SEQ=DoanhSoBanHang.nppId \n" +
				  	 " where thang >= '" + obj.getFromMonth() + "' and thang <= '" + obj.getToMonth() + "' and nam = '" + obj.getYear() + "' \n";
				  				if(obj.getloainppId().length()>0)
				  				{
				  					SalesRep+= " and npp2.LoaiNPP=" + obj.getloainppId() + " \n";
				  				}
				  		SalesRep+= sql + 
					 " group by BU, region, area, salesuper, SalesRepId, SalesRep, distcode, brand, category, distributor, SKU, SKUcode, chanel, thang,nnpp.ten, npp2.LoaiNPP, ISNULL(SP.TENVIETTAT,''), ISNULL(SP.MA_DDT,'')   order by thang";
				
				Distributor = 
				" select BU, region, area,distributor,  distcode,  brand, category,SKU, SKUcode, ISNULL(SP.MA_DDT,'') MADDT, chanel,thang as ngay, sum(piece) as piece, sum( piece* isnull(sp.trongluong ,0)) as SLTT, ";
  
				if(obj.getdiscount().equals("1")) 
				{
					
						Distributor += " sum(amount_CK_YES_VAT_NO) as amount \n";
					
				}
				else
				{
					
					
						Distributor += " sum(amount_CK_NO_VAT_NO) as amount \n";
					
				}
						
				Distributor += 
				"   , sum(quantity) as quantity,SUM(amount_CK_NO_VAT_YES)-SUM(amount_CK_YES_VAT_NO) as ChietKhau,nnpp.ten as tennhomnpp, isnull((select lnpp.Ma from LoaiNPP lnpp where lnpp.pk_seq=npp2.LoaiNPP),'N/A') as maloainpp, ISNULL(SP.TENVIETTAT,'') TENTATSP \n" +
				" from doanhsobanhang left join sanpham sp  on sp.ma=doanhsobanhang.skucode \n" +
				"      left join NhomNpp_Npp npp on doanhsobanhang.NPPID=npp.Npp_FK \n"+
				"      left join NhomNpp nnpp on nnpp.PK_SEQ=npp.NhomNpp_FK \n"+ 
				"	   inner join NHAPHANPHOI npp2 on npp2.PK_SEQ=DoanhSoBanHang.nppId	 \n"+
				" where thang >= '" + obj.getFromMonth() + "' and thang <= '" + obj.getToMonth() + "' and nam = '" + obj.getYear() + "' \n";
						if(obj.getloainppId().length()>0)
						{
							Distributor += " and npp2.LoaiNPP=" + obj.getloainppId() + " \n";
						}
						
						Distributor += sql +
				" group by BU, region, area, brand, category, distcode, distributor, SKU, SKUcode, chanel, thang ,nnpp.ten, npp2.LoaiNPP, ISNULL(SP.TENVIETTAT,''), ISNULL(SP.MA_DDT,'')   ";
				
			
				
				Customer = 
				" select bu, region, area, distributor, distcode, brand, category, sku, skuCode, ISNULL(SP.MA_DDT,'') MADDT, salesRep, salesRepId, customer, customercode, a.smartId, " +
				"        isnull(outletposition,N'chua xac dinh') as outletposition, outlettype, chanel, salesuper, town, province, outletclass, nhomkhachhang, nhomsanpham, thang as offdate, kh.phuong,isnull(kh.DIACHI,N'Chưa xác định') as DIACHI, " +
				"        sum(piece) as piece, sum(quantity) as quantity, sum( piece* isnull(sp.trongluong ,0)) as SLTT ,SUM(amount_CK_NO_VAT_YES)-SUM(amount_CK_YES_VAT_NO) as ChietKhau,nnpp.ten as tennhomnpp, ISNULL(SP.TENVIETTAT,'') TENTATSP, ";
		if(obj.getdiscount().equals("1")) //co ck
				{
					
						Customer += 
						" sum(amount_CK_YES_VAT_NO) as amount, isnull((select lnpp.Ma from LoaiNPP lnpp where lnpp.pk_seq=npp2.LoaiNPP),'N/A') as maloainpp \n" +
						" from doanhsobanhang  a left join sanpham sp  on sp.ma=a.skucode \n" +
						"      left join KHACHHANG kh on kh.PK_SEQ = a.customercode   \n" +
						"      left join NhomNpp_Npp npp on a.NPPID=npp.Npp_FK  \n"+
						"      left join NhomNpp nnpp on nnpp.PK_SEQ=npp.NhomNpp_FK \n"+ 
						"      inner join NHAPHANPHOI npp2 on npp2.PK_SEQ=a.nppId \n " +
						"  where thang >= '" + obj.getFromMonth() + "' and thang <= '" + obj.getToMonth() + "' and nam = '" + obj.getYear() + "' ";
				}
				else
				{
								
						Customer += 
						" sum(amount_CK_NO_VAT_NO) as amount, isnull((select lnpp.Ma from LoaiNPP lnpp where lnpp.pk_seq=npp2.LoaiNPP),'N/A') as maloainpp \n" +
						" from doanhsobanhang a left join sanpham sp  on sp.ma=a.skucode \n" +
						"      left join KHACHHANG kh on kh.PK_SEQ = a.customercode   \n" +
						"      left join NhomNpp_Npp npp on a.NPPID=npp.Npp_FK  \n"+
						"      left join NhomNpp nnpp on nnpp.PK_SEQ=npp.NhomNpp_FK  \n" +
						"      inner join NHAPHANPHOI npp2 on npp2.PK_SEQ=a.nppId \n "+ 
						" where thang >= '" + obj.getFromMonth() + "' and thang <= '" + obj.getToMonth() + "' and nam = '" + obj.getYear() + "' \n";
				}
					if(obj.getloainppId().length()>0)
					{
						Customer += " and npp2.LoaiNPP=" + obj.getloainppId() + " \n";
					}
					
					Customer += sql + " ";
					Customer += " group by  bu, region, area, distributor, distcode, brand, category, sku, skuCode, salesRep, salesRepId, customer, customercode, a.smartId, kh.diachi,\n" +
							    "           outletposition, outlettype, chanel, salesuper, town, province, outletclass, nhomkhachhang, nhomsanpham, thang, kh.phuong, nnpp.ten, npp2.LoaiNPP, ISNULL(SP.TENVIETTAT,''),ISNULL(SP.MA_DDT,'')  order by thang ";
			  }  
		
	     
	     String query = "";
	     switch (Integer.parseInt(level)) 
	     {
			case 0:		
				query = Distributor;
				System.out.println("**********case 0***********");
				System.out.println("QUERY Doanh So Distributor la: " + query);
				break;
			case 1:	
				query = SalesRep;
				System.out.println("**********case 1***********");
				System.out.println("QUERY Doanh So SalesRep la: " + query);
				break;
			case 2:
				query = Customer;
				System.out.println("**********case 2***********");
				System.out.println("QUERY Doanh So Customer la: " + query);
				break;
			case 3: 
				query= NhanVienGiaoNhan;
				System.out.println("**********case 3***********");
				System.out.println("QUERY Doanh So NhanVienGiaoNhan la: " + query);
				break;
	     }
	    
	     return query;			
		
	}	
	
	private boolean CreatePivotTable(OutputStream out, IStockintransit obj, String query, String level)throws Exception 
	{
		boolean isFillData = false;
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
		switch (Integer.parseInt(level)) {
		
		case 0:	
			if(obj.getFromMonth().length() <= 0) //Xem theo Ngay
			{
				String chuoi=getServletContext().getInitParameter("path") + "\\DoanhSoTT.xlsm";				
				 fstream = new FileInputStream(chuoi);
				//File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\DoanhSoTT.xlsm");
				//fstream = new FileInputStream(f) ;
				 System.out.println("Template case 0: a");
			}
			else
			{
				String chuoi=getServletContext().getInitParameter("path") + "\\DoanhSoTT_Thang.xlsm";
				 fstream = new FileInputStream(chuoi);	
				//File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\DoanhSoTT_Thang.xlsm");
				//fstream = new FileInputStream(f) ;
				 System.out.println("Template case 0: b");
			}
				
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

			CreateHeaderLevelOne(workbook,obj);
			isFillData = FillDataLevelOne(workbook, obj, query);
			break;
			
		case 1:	
			if(obj.getFromMonth().length() <= 0) //Xem theo Ngay
			{
				String chuoi=getServletContext().getInitParameter("path") + "\\DoanhSoTT-DDKD.xlsm";
				 fstream = new FileInputStream(chuoi);	
				//File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\DoanhSoTT_Thang.xlsm");
				//fstream = new FileInputStream(f) ;
				 System.out.println("Template case 1: a");
			}
			else
			{
				String chuoi=getServletContext().getInitParameter("path") + "\\DoanhSoTT-DDKD_Thang.xlsm";
				 fstream = new FileInputStream(chuoi);	
				 //File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\DoanhSoTT-DDKD_Thang.xlsm");
				//	fstream = new FileInputStream(f) ;
				 System.out.println("Template case 1: b");
			}
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

			CreateHeaderLevelTwo(workbook,obj);
			isFillData = FillDataLevelTwo(workbook, obj, query);
			break;
			
		case 2:
			if(obj.getFromMonth().length() <= 0) //Xem theo Ngay
			{
				String chuoi=getServletContext().getInitParameter("path") + "\\DoanhSoTT_Customer.xlsm";
				 fstream = new FileInputStream(chuoi);
				//File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\DoanhSoTT_Customer.xlsm");
				//fstream = new FileInputStream(f) ;
				 System.out.println("Template case 2: a");
			}
			else
			{
				String chuoi=getServletContext().getInitParameter("path") + "\\DoanhSoTT_Customer_Thang.xlsm";
				 fstream = new FileInputStream(chuoi);
				//File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\DoanhSoTT_Customer_Thang.xlsm");
				//fstream = new FileInputStream(f) ;
				 System.out.println("Template case 2: b");
			}
					
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

			CreateHeaderLevelThree(workbook,obj);
			isFillData = FillDataLevelThree(workbook, obj, query);			
			break;
		}	   
		
		if (!isFillData){
			if(fstream != null) 
				fstream.close();
			return false;
		}
		workbook.save(out);
		fstream.close();
		return true;	
	}
	
	private void CreateHeaderLevelOne(Workbook workbook, IStockintransit obj)throws Exception
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
		if(obj.getFromMonth().length() > 0){
		cells.setRowHeight(2, 18.0f);
		cell = cells.getCell("A2");
		ReportAPI.getCellStyle(cell, Color.RED, true, 9, message);}   

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
		
		/*cell = cells.getCell("DN1");		cell.setValue("SoLuong(Thung)");
		cell = cells.getCell("DO1");		cell.setValue("SanLuong(Kg)");*/
		
		cell = cells.getCell("DN1");		cell.setValue("Phuong");
		cell = cells.getCell("DO1");		cell.setValue("DiaChi");	
		cell = cells.getCell("DP1");
		if(obj.getFromMonth() == "")
			cell.setValue("ChietKhauNPP");
		else	
			cell.setValue("ChietKhau");
		cell = cells.getCell("DQ1");		cell.setValue("NhomNhaPhanPhoi");
		cell = cells.getCell("DR1");		cell.setValue("MaLoaiNPP");
		cell = cells.getCell("DS1");		cell.setValue("TenSanPham(VietTat)");
		
		if(obj.getFromMonth() == ""){
		cell = cells.getCell("DT1");		cell.setValue("ChietKhauTT");
		cell = cells.getCell("DU1");		cell.setValue("DonGiaGoc(VAT)");
		cell = cells.getCell("DV1");		cell.setValue("MaSanPham(DDT)");
		}
	}
	
	private boolean FillDataLevelOne(Workbook workbook, IStockintransit obj, String query) throws Exception{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

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
				cells.setColumnWidth(14, 20.0f);
				cells.setColumnWidth(15, 20.0f);
				cells.setColumnWidth(16, 20.0f);
				cells.setColumnWidth(17, 20.0f);
				cells.setColumnWidth(18, 20.0f);
				cells.setColumnWidth(19, 20.0f);

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
					String tentatsp = rs.getString("tentatsp");
					double amount = rs.getDouble("amount");
					float piece = rs.getFloat("piece");
					//float q = rs.getFloat("soluong2")*piece/rs.getFloat("soluong1");
					String q = rs.getString("quantity");
					float sanluong = rs.getFloat("SLTT");
					float chietkhau = rs.getFloat("chietkhau");  // CK NPP
					float chietkhauTT = 0 ;
					float dongiagoc = 0  ;
					if(obj.getFromMonth() == "") {
						chietkhauTT = rs.getFloat("chietkhauTT");
						dongiagoc = rs.getFloat("dongiagoc");				
					}
					
					String nhomnpp=rs.getString("tennhomnpp")==null?"":rs.getString("tennhomnpp");
					String maloainpp=rs.getString("maloainpp")==null?"":rs.getString("maloainpp");
					String maDDT =rs.getString("maDDT") ;
					
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
					
					/*cell = cells.getCell("DN" + Integer.toString(i));	cell.setValue(q);		//13	
					cell = cells.getCell("DO" + Integer.toString(i));	cell.setValue(sanluong);		//14
					*/					
					cell = cells.getCell("DN" + Integer.toString(i));	cell.setValue("");		//13	
					cell = cells.getCell("DO" + Integer.toString(i));	cell.setValue("");		//14					
					cell = cells.getCell("DP" + Integer.toString(i));	cell.setValue(chietkhau);		//17
					cell = cells.getCell("DQ" + Integer.toString(i));	cell.setValue(nhomnpp);		//18
					cell = cells.getCell("DR" + Integer.toString(i));	cell.setValue(maloainpp);		//19
					cell = cells.getCell("DS" + Integer.toString(i));	cell.setValue(tentatsp);		//19
					if(obj.getFromMonth() == ""){
					cell = cells.getCell("DT" + Integer.toString(i));	cell.setValue(chietkhauTT);		//20
					cell = cells.getCell("DU" + Integer.toString(i));	cell.setValue(dongiagoc);		//21
					cell = cells.getCell("DV" + Integer.toString(i));	cell.setValue(maDDT);		//21
					}
						
					++i;					
				}

				if (rs != null) rs.close();
				
				if(db != null) db.shutDown();
				
				if(i==2){					
					throw new Exception("Xin loi,khong co bao cao voi dieu kien da chon....!!!");
				}
				  
			}catch(Exception ex){
				ex.printStackTrace();
				throw new Exception(ex.getMessage());
			}
		}else{
			return false;
		}		
		return true;
	}	
	
	//Level Dai dien kinh doanh
	private void CreateHeaderLevelTwo(Workbook workbook, IStockintransit obj)throws Exception{	
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
		
		if(obj.getFromMonth().length() > 0){
		cells.setRowHeight(2, 18.0f);
		cell = cells.getCell("A2");
		ReportAPI.getCellStyle(cell, Color.RED, true, 9, message);}   

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
		cell = cells.getCell("DE1");		cell.setValue("GiamSat");			
		cell = cells.getCell("DF1");		cell.setValue("NhaPhanPhoi");
		cell = cells.getCell("DG1");		cell.setValue("NhanVienBanHang");	
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
		
/*		cell = cells.getCell("DQ1");		cell.setValue("SoLuong(Thung)");
		cell = cells.getCell("DR1");		cell.setValue("SanLuong(Kg)");*/
		
		cell = cells.getCell("DQ1");
		if(obj.getFromMonth() == "")
			cell.setValue("ChietKhauNPP");
		else	
			cell.setValue("ChietKhau");		
		cell = cells.getCell("DR1");		cell.setValue("NhomNhaPhanPhoi");
		cell = cells.getCell("DS1");		cell.setValue("MaLoaiNPP");
		cell = cells.getCell("DT1");		cell.setValue("TenSanPham(VietTat)");
		if(obj.getFromMonth() == ""){
		cell = cells.getCell("DU1");		cell.setValue("ChietKhauTT");
		cell = cells.getCell("DV1");		cell.setValue("DonGiaGoc(VAT)");
		cell = cells.getCell("DW1");		cell.setValue("MaSanPham(DDT)");
		}
	}
	
	private boolean FillDataLevelTwo(Workbook workbook, IStockintransit obj, String query) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
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
				cells.setColumnWidth(16, 20.0f);
				cells.setColumnWidth(17, 20.0f);
				cells.setColumnWidth(18, 20.0f);
				cells.setColumnWidth(19, 20.0f);
				cells.setColumnWidth(20, 20.0f);
						while (rs.next()) 
				{					
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
					String tentatsp = rs.getString("tentatsp");
					double amount = rs.getDouble("amount");
					float chietkhau = rs.getFloat("chietkhau");
					float chietkhauTT = 0;
					float dongiagoc = 0;
					if(obj.getFromMonth() == "") {
						chietkhauTT = rs.getFloat("chietkhauTT");
						dongiagoc = rs.getFloat("dongiagoc");
					}
						
					float piece = rs.getFloat("piece");
					
					String q = rs.getString("quantity");
					float sanluong = rs.getFloat("SLTT");
					String nhomnpp=rs.getString("tennhomnpp")==null?"":rs.getString("tennhomnpp");	
					String maloainpp=rs.getString("maloainpp")==null?"":rs.getString("maloainpp");
					String maDDT =rs.getString("maDDT");
					
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
					
					/*cell = cells.getCell("DQ" + Integer.toString(i));	cell.setValue(q);	//16	
					cell = cells.getCell("DR" + Integer.toString(i));	cell.setValue(sanluong);	//17 */	
					
					cell = cells.getCell("DQ" + Integer.toString(i));	cell.setValue(chietkhau);	//18
					cell = cells.getCell("DR" + Integer.toString(i));	cell.setValue(nhomnpp);	//19
					cell = cells.getCell("DS" + Integer.toString(i));	cell.setValue(maloainpp);	//20
					cell = cells.getCell("DT" + Integer.toString(i));	cell.setValue(tentatsp);	//20
					if(obj.getFromMonth() == ""){
					cell = cells.getCell("DU" + Integer.toString(i));	cell.setValue(chietkhauTT);	//21
					cell = cells.getCell("DV" + Integer.toString(i));	cell.setValue(dongiagoc);	//21
					cell = cells.getCell("DW" + Integer.toString(i));	cell.setValue(maDDT);	//21
					}
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
	
	private void CreateHeaderLevelThree(Workbook workbook, IStockintransit obj)throws Exception{
		try{
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
			
			if(obj.getFromMonth().length() > 0){
			cells.setRowHeight(2, 18.0f);
			cell = cells.getCell("A2");
			ReportAPI.getCellStyle(cell, Color.RED, true, 9, message);}   

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
			//cell = cells.getCell("DV1");		cell.setValue("SoLuong(Thung)");
			//cell = cells.getCell("DW1");		cell.setValue("SmartId");
			cell = cells.getCell("DV1");		cell.setValue("MaKhachHang");
			cell = cells.getCell("DW1");		cell.setValue("TinhThanh");
			cell = cells.getCell("DX1");		cell.setValue("QuanHuyen");
			//cell = cells.getCell("DZ1");		cell.setValue("SanLuong(Kg)");
			cell = cells.getCell("DY1");		cell.setValue("Phuong");
			cell = cells.getCell("DZ1");		cell.setValue("DiaChi");
			cell = cells.getCell("EA1");		
			if(obj.getFromMonth() == "")
				cell.setValue("ChietKhauNPP");
			else	
				cell.setValue("ChietKhau");
			cell = cells.getCell("EB1");		cell.setValue("NhomNhaPhanPhoi");
			cell = cells.getCell("EC1");		cell.setValue("MaLoaiNPP");
			cell = cells.getCell("ED1");		cell.setValue("TenSanPham(VietTat)");
			if(obj.getFromMonth() == ""){
			cell = cells.getCell("EE1");		cell.setValue("ChietKhauTT");
			cell = cells.getCell("EF1");		cell.setValue("DonGiaGoc(VAT)");
			cell = cells.getCell("EG1");		cell.setValue("MaSanPham(DDT)");
			
			}
			
			
		}catch(Exception ex){
			throw new Exception("Xin loi,Khong tao duoc header cho bao cao");
		}
		
	}
	
	private boolean FillDataLevelThree(Workbook workbook, IStockintransit obj, String query) throws Exception{
		
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
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
				cells.setColumnWidth(21, 20.0f);
				cells.setColumnWidth(22, 20.0f);
				cells.setColumnWidth(23, 20.0f);
				cells.setColumnWidth(24, 20.0f);
						
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
					String outletPosition=rs.getString("outletPosition");
					String offdate =  rs.getString("offdate");
					String groupCustomer = rs.getString("nhomkhachhang");
					String groupSKU = rs.getString("nhomsanpham");
					String tentatsp = rs.getString("tentatsp");
					double amount = rs.getDouble("amount");
					float piece = rs.getFloat("piece");
					String quantity = rs.getString("quantity");
					float sanluong = rs.getFloat("SLTT");
					String phuong = rs.getString("phuong");
					String diachi = rs.getString("diachi");
					float chietkhau = rs.getFloat("chietkhau");
					float chietkhauTT = 0;
					float dongiagoc = 0;
					if(obj.getFromMonth() == ""){
						chietkhauTT = rs.getFloat("chietkhauTT");
						dongiagoc= rs.getFloat("dongiagoc");
					}
				
					String nhomnpp=rs.getString("tennhomnpp")==null?"":rs.getString("tennhomnpp");		
					String maloainpp=rs.getString("maloainpp")==null?"":rs.getString("maloainpp");	
					
					String smartId = rs.getString("SmartId");
					String maDDT = rs.getString("maDDT");
					//String smartId = "123456";
					
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
					cell = cells.getCell("DO" + Integer.toString(i));	cell.setValue(outletPosition);
					cell = cells.getCell("DP" + Integer.toString(i));	cell.setValue(outlettype);
					cell = cells.getCell("DQ" + Integer.toString(i));	cell.setValue(offdate);
					cell = cells.getCell("DR" + Integer.toString(i));	cell.setValue(groupSKU);
					cell = cells.getCell("DS" + Integer.toString(i));	cell.setValue(groupCustomer);
					cell = cells.getCell("DT" + Integer.toString(i));	cell.setValue(amount);
					cell = cells.getCell("DU" + Integer.toString(i));	cell.setValue(piece);
					//cell = cells.getCell("DV" + Integer.toString(i));	cell.setValue(quantity);	
					//cell = cells.getCell("DW" + Integer.toString(i));	cell.setValue(smartId);
					cell = cells.getCell("DV" + Integer.toString(i));	cell.setValue(smartId);
					cell = cells.getCell("DW" + Integer.toString(i));	cell.setValue(rs.getString("PROVINCE"));
					cell = cells.getCell("DX" + Integer.toString(i));	cell.setValue(rs.getString("town"));
					//cell = cells.getCell("DZ" + Integer.toString(i));	cell.setValue(sanluong);
					cell = cells.getCell("DY" + Integer.toString(i));	cell.setValue(phuong);
					cell = cells.getCell("DZ" + Integer.toString(i));	cell.setValue(diachi);
					cell = cells.getCell("EA" + Integer.toString(i));	cell.setValue(chietkhau);
					cell = cells.getCell("EB" + Integer.toString(i));	cell.setValue(nhomnpp);
					cell = cells.getCell("EC" + Integer.toString(i));	cell.setValue(maloainpp);
					cell = cells.getCell("ED" + Integer.toString(i));	cell.setValue(tentatsp);
					if(obj.getFromMonth() == ""){
					cell = cells.getCell("EE" + Integer.toString(i));	cell.setValue(chietkhauTT);
					cell = cells.getCell("EF" + Integer.toString(i));	cell.setValue(dongiagoc);
					cell = cells.getCell("EG" + Integer.toString(i));	cell.setValue(maDDT);
					}
					
					++i;					
				}
				if (rs != null)
					rs.close();
				
				if(db != null) 
					db.shutDown();
				
				if(i==2){					
					throw new Exception("Chưa có dữ liệu báo cáo trong thời gian này.");
				}
				System.out.print("I = " + i);
				

			}catch(Exception ex){
				ex.printStackTrace();
				throw new Exception(ex.getMessage());
			}
		}else{
			return false;
		}		
		return true;
	}		
	
	
}
