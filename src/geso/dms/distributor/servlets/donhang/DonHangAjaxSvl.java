package geso.dms.distributor.servlets.donhang;

import geso.dms.center.beans.tinhthunhap.INhaPhanPhoi;
import geso.dms.center.beans.tinhthunhap.INhanvien;
import geso.dms.center.beans.tinhthunhap.imp.NhaPhanPhoi;
import geso.dms.center.beans.tinhthunhap.imp.Nhanvien;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

public class DonHangAjaxSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public DonHangAjaxSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();		
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")==null?"":request.getParameter("action"));
		String KvId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kvId")==null?"": request.getParameter("kvId"));
		String nppSelected = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppSelected")==null?"":request.getParameter("nppSelected"));
		String chucvu=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chucvu")==null?"":request.getParameter("chucvu"));
		System.out.println("____action____" + action + "____KvId____" + KvId + "___nppSelected__" + nppSelected+"__ChucVu_"+chucvu);
		String query = "";
		if (action.equals("ajaxNpp"))
		{
			dbutils db = new dbutils();
			if (KvId.trim().length() > 0 && nppSelected.trim().length() <= 0)
			{
				query = "SELECT PK_SEQ ,TEN as nppTen FROM NHAPHANPHOI WHERE TRANGTHAI=1 AND " + "KHUVUC_FK IN(" + KvId +
					")";
			} else
			{
				query = "SELECT PK_SEQ ,TEN as nppTen FROM NHAPHANPHOI WHERE TRANGTHAI=1 AND " + "KHUVUC_FK IN(" + KvId +
					") AND PK_SEQ NOT IN(" + nppSelected + ")";
			}
			System.out.println("___________JSON NPP_________________"+query);
			ResultSet rs = db.get(query);
			List<INhaPhanPhoi> nppList = new ArrayList<INhaPhanPhoi>();
			if (rs != null)
			{
				try
				{
					INhaPhanPhoi npp = null;
					while (rs.next())
					{
						npp = new NhaPhanPhoi();
						npp.setTen(rs.getString("nppTen"));
						npp.setId(rs.getString("PK_SEQ"));
						nppList.add(npp);
					}
					rs.close();
				} catch (Exception e)
				{
				}
			}
			db.shutDown();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(gson.toJson(nppList));
		}else if(action.equals("ajaxNv"))
		{
			dbutils db = new dbutils();
			 query = "";
			if(chucvu.equals("SS")&&KvId.trim().length()>0)
			{
				query = "select '' as nppTen, PK_SEQ, TEN from GIAMSATBANHANG where TRANGTHAI = '1' and KHUVUC_FK in ( " + KvId + " )";
			}
			else if(nppSelected.trim().length()>0&&chucvu.equals("SR"))
			{
				query = "select b.ten as nppTen, a.PK_SEQ, a.TEN from DAIDIENKINHDOANH a inner join NhaPhanPhoi b on a.npp_fk = b.pk_seq " +
						"where a.TRANGTHAI = '1'  and a.NPP_FK in ("+nppSelected+ "  ) ";
			}
			System.out.println("___________JSON Nhan Vien_________________"+query);
			System.out.println("Khoi tao nhan vien: " + query);
			ResultSet rs=null;
			if(query.length()>0)
				rs = db.get(query);
			List<INhanvien> nvList = new ArrayList<INhanvien>();
			if(rs != null)
			{
				try 
				{
					INhanvien nv = null;
					while(rs.next())
					{
						nv = new Nhanvien();
						
						nv.setNppTen(rs.getString("nppTen"));
						nv.setId(rs.getString("PK_SEQ"));
						nv.setTen(rs.getString("TEN"));
						nv.setLuongCB("");
						nvList.add(nv);
					}
					rs.close();
				} 
				catch (Exception e) {}
			}
			db.shutDown();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(gson.toJson(nvList));
		}
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();
		Utility util = new Utility();
		String q = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("q")));
		String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
		String nppId = (String) session.getAttribute("nppId");

		if (id.equals("ddkdTen")) // loc dai dien kinh doanh
		{
			// System.out.println("Giam sat ban hang Id la: " + q + "\n");
			if (q != null)
			{
				dbutils db = new dbutils();
				String query = "select ten as ddkdTen, pk_seq as ddkdId from daidienkinhdoanh where trangthai = '1' and npp_fk = '" +
					nppId +
					"' and pk_seq in (select ddkd_fk from ddkd_gsbh where gsbh_fk in (select gsbh_fk from nhapp_giamsatbh where ngaybatdau <='" +
					this.getDateTime() + "' and ngayketthuc >='" + getDateTime() + "' and  gsbh_fk ='" + q + "') )";

				// System.out.println("Query lay dai dien kinh doanh la: " +
				// query + "\n");
				ResultSet rs = db.get(query);
				if (rs != null)
				{
					try
					{
						out.write("<option value=''></option>");
						while (rs.next())
						{
							out.write("<option value='" + rs.getString("ddkdId") + "'>" + rs.getString("ddkdTen") +
								"</option>");
						}
					} catch (Exception e)
					{
					}
				}
			}
		} else
		{
			if (id.equals("smartId"))
			{
				// System.out.println("Dai dien kinh doanh Id la: " + q + "\n");
				session.setAttribute("ddkdId", q);
			}
		}
	}

}
