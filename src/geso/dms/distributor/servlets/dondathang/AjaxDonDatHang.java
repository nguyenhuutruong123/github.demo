package geso.dms.distributor.servlets.dondathang;

import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

@WebServlet("/AjaxDonDatHang")
public class AjaxDonDatHang extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public AjaxDonDatHang()
	{
		super();

	}
	NumberFormat formatter = new DecimalFormat("#,###,###");

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{		

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PrintWriter out = response.getWriter();	
		HttpSession session = request.getSession();
		Utility util = new Utility();

		String type = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("type") == null ? "" : request.getParameter("type"));
		if(type.equals("QuyRaLe"))
		{
			Gson gson = new Gson();
			String spMa = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("spMa")));
			String dvdlId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvdlId")));
			String khoId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khoId")));
			String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
			String kbhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")));
			String dvkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));
			String soluongChuan = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("soluongchuan")));
			dbutils db = new dbutils();

			String query = 
				" select a.DVDL_FK as dvCHUAN, ( select PK_SEQ from DONVIDOLUONG where DONVI = N'" + dvdlId + "' ) as dvNEW, " + 
				"	isnull( ( select SOLUONG1 / SOLUONG2 from QUYCACH where SANPHAM_FK = a.pk_seq and DVDL1_FK = a.DVDL_FK and DVDL2_FK = ( select PK_SEQ from DONVIDOLUONG where DONVI = N'" + dvdlId + "' ) ), 0 ) as quydoi,  	  " +  
				" isnull( ( select GIAMUANPP * ( 1 - isnull( ( select chietkhau from BANGGIAMUANPP_NPP where banggiamuaNPP_FK = bg_sp.bgmuaNPP_FK and NPP_FK = '"+nppId+"' ), 0) / 100 )  				" +
				"from BGMUANPP_SANPHAM bg_sp 			    where SANPHAM_FK = a.pk_seq  " +
				"and BGMUANPP_FK in ( select top(1) PK_SEQ from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK where bg.TRANGTHAI = '1'" +
				" and bg_npp.NPP_FK = '"+nppId+"' and bg.DVKD_FK = '"+dvkdId+"' and bg.KENH_FK = '"+kbhId+"' order by bg.TUNGAY desc ) ), 0) as GiaMua "+ 
				" from SANPHAM a where a.MA = '" + spMa + "'  ";

			System.out.println("[Sql]"+query);

			ResultSet rs = db.get(query);
			{
				try 
				{
					if(rs.next())
					{
						double DonGia = rs.getDouble("giamua");
						SanPham sp = new SanPham();
						sp.setDongia( formatter.format(DonGia));
						sp.setSoluongle(formatter.format(rs.getDouble("SoLuongLe") ) );
						response.setContentType("application/json");
						response.setCharacterEncoding("UTF-8");
						response.getWriter().write(gson.toJson(sp));

					}
					rs.close();
				} 
				catch (Exception e) 
				{

				}
				finally
				{
					if(db!=null)db.shutDown();
				}
			}

		}		
	}


	class SanPham
	{

		public String getDongia() 
		{
			return dongia;
		}
		public void setDongia(String dongia) 
		{
			this.dongia = dongia;
		}
		String kehoachsanxuat;
		String tonkho;
		String dongia;
		String quycach ;
		String soluongle;
		public String getSoluongle() {
			return soluongle;
		}
		public void setSoluongle(String soluongle) {
			this.soluongle = soluongle;
		}
		public String getQuycach() {
			return quycach;
		}
		public void setQuycach(String quycach) {
			this.quycach = quycach;
		}

		String quycachthung;
		public String getQuycachthung()
		{
			return quycachthung;
		}
		public void setQuycachthung(String quycachthung)
		{
			this.quycachthung = quycachthung;
		}

	}
}

