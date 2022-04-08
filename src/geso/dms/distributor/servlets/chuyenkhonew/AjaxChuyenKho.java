package geso.dms.distributor.servlets.chuyenkhonew;

import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AjaxChuyenKho")
public class AjaxChuyenKho extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	NumberFormat formatter = new DecimalFormat("#,###,###");
	public AjaxChuyenKho()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PrintWriter out = response.getWriter();	
	//	HttpSession session = request.getSession();
		Utility util = new Utility();
		
		String type = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("type") == null ? "" : request.getParameter("type"));
		if(type.equals("QuyDoi"))
		{
			String spMa = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("spMa")));
			String dvdlId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvdlId")));
			String soluongChuan = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("soluongchuan")));
			
			dbutils db = new dbutils();
			
			String query = "select sp.MA, case when sp.dvdl_fk =  '" + dvdlId + "' then " + soluongChuan + " else  " +
										"( " +
											"select " + soluongChuan + " * b.SOLUONG1 / b.SOLUONG2  " +
											"from SANPHAM a left join QUYCACH b on a.PK_SEQ = b.SANPHAM_FK and a.DVDL_FK = b.DVDL1_FK and b.DVDL2_FK = '" + dvdlId + "'  " +
											"where a.MA = sp.MA " +
										")  " +
										"end as QuyDoi  " +
										"from SANPHAM sp  " +
										"where sp.MA = '" + spMa + "'";
		//	System.out.println("__Lay quy doi: " + query);
			ResultSet rs = db.get(query);
			double quydoi = 0;
			if(rs != null)
			{
				try 
				{
					if(rs.next())
					{
						quydoi = rs.getDouble("QuyDoi");
					}
					rs.close();
				} 
				catch (Exception e) 
				{
					quydoi = 0;
				}
			}
			System.out.println("__Ket qua Quy Doi: " + quydoi);
			out.write(quydoi+"");
			db.shutDown();
		}else if(type.equals("GetDonGia"))
		{
			String spId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("spId")));
			String spMa = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("spMa")));
			String dvdlId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvdlId")));
			String kenhId= util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")));
			String khoId= util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khoId")));
			String dvkdId= util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));
			String nppId= util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
			
			dbutils db = new dbutils();
			
			String query =
			" select npp_fk,b.sanpham_fk as pk_seq ,giamuanpp, \n"+
			"	case when sp.dvdl_fk="+dvdlId+" then 1 else (  select soluong1/soluong2 from quycach where sanpham_fk=sp.pk_seq and dvdl2_fk="+dvdlId+" ) \n"+
			"	end   as QuyDoi, isnull((select available from erp_khott_sanpham where  khott_fk = '"+khoId+"'   and sanpham_fk =sp.pk_seq),0) as available \n"+ 
			" from BangGiaMuaNPP bgmnpp inner join BANGGIAMUANPP_NPP a    \n"+
			" on bgmnpp.pk_Seq=a.bangGiaMuaNpp_fk   \n"+
			"	inner join BGMUANPP_SANPHAM   b on a.bangGiaMuaNpp_fk=b.bgmuanpp_fk \n"+  
			"	inner join sanpham sp on sp.pk_seq=b.sanpham_fk \n"+
			"	where bgmnpp.trangthai='1' and  kenh_fk="+kenhId+" and bgmnpp.dvkd_fk="+dvkdId+" and  npp_fk="+nppId+"  and sp.ma= '"+spMa+"' "  ;
			
			System.out.println("__Lay quy doi: " + query);
			ResultSet rs = db.get(query);
			double quydoi = 0;
			double dongia=0;
			if(rs != null)
			{
				try 
				{
					if(rs.next())
					{
						quydoi = rs.getDouble("QuyDoi");
						dongia=quydoi*rs.getDouble("giamuanpp");
					}
					rs.close();
				} 
				catch (Exception e) 
				{
					quydoi = 0;
					dongia=0;
				}
			}
			System.out.println("__Ket qua Quy Doi: " + dongia);
			out.write(formatter.format(dongia));
			db.shutDown();
		}else if(type.equals("GetTonKho"))
		{
			String spId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("spId")));
			String spMa = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("spMa")));
			String dvdlId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvdlId")));
			String kenhId= util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")));
			String khoId= util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khoId")));
			String dvkdId= util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));
			String nppId= util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
			
			dbutils db = new dbutils();
			
			String query =
			" select npp_fk,b.sanpham_fk as pk_seq ,giamuanpp, \n"+
			"	case when sp.dvdl_fk="+dvdlId+" then 1 else (  select soluong1/soluong2 from quycach where sanpham_fk=sp.pk_seq and dvdl2_fk="+dvdlId+" ) \n"+
			"	end   as QuyDoi, isnull((select available from erp_khott_sanpham where  khott_fk = '"+khoId+"'   and sanpham_fk =sp.pk_seq),0) as available \n"+ 
			" from BangGiaMuaNPP bgmnpp inner join BANGGIAMUANPP_NPP a    \n"+
			" on bgmnpp.pk_Seq=a.bangGiaMuaNpp_fk   \n"+
			"	inner join BGMUANPP_SANPHAM   b on a.bangGiaMuaNpp_fk=b.bgmuanpp_fk \n"+  
			"	inner join sanpham sp on sp.pk_seq=b.sanpham_fk \n"+
			"	where bgmnpp.trangthai='1' and  kenh_fk="+kenhId+" and bgmnpp.dvkd_fk="+dvkdId+" and  npp_fk="+nppId+"  and sp.ma= '"+spMa+"' "  ;
			
			System.out.println("__Lay quy doi: " + query);
			ResultSet rs = db.get(query);
			double quydoi = 0;
			double available=0;
			if(rs != null)
			{
				try 
				{
					if(rs.next())
					{
						quydoi = rs.getDouble("QuyDoi");
						available=rs.getDouble("available");
					}
					rs.close();
				} 
				catch (Exception e) 
				{
					quydoi = 0;
					available=0;
				}
			}
			System.out.println("__Ket qua Quy Doi: " + available);
			out.write(formatter.format(available));
			db.shutDown();
		}
	}

}
