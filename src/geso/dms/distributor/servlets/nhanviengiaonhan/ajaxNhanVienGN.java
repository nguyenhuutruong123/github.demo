package geso.dms.distributor.servlets.nhanviengiaonhan;

import geso.dms.distributor.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ajaxNhanVienGN extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       

    public ajaxNhanVienGN() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
		PrintWriter out = response.getWriter();
		String ddkdIds = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdIds"));
		String tbhIds = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tbhIds"));
		
		System.out.println("Tuyen ban hang luu lai duoc la: " + tbhIds + "\n");
		
		String id = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id"));
		String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
		String update = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("update"));
		
		dbutils db = new dbutils();
		if(id.equals("tbhIds")) //loc dai dien kinh doanh
		{
			//System.out.println("Giam sat ban hang Id la: " + q + "\n");
			String str = "";
			if(ddkdIds.length() > 0)
			{
				ddkdIds = ddkdIds.substring(0, ddkdIds.length() - 1);
				String query = "select tuyenbanhang.pk_seq as tbhId,tuyenbanhang.diengiai as tbhTen, ddkd.ten, ngaylamviec " +
								" from tuyenbanhang inner join daidienkinhdoanh ddkd on ddkd.pk_seq =tuyenbanhang.ddkd_fk  "+
								" where tuyenbanhang.npp_fk = '" + nppId + "' and tuyenbanhang.ddkd_fk in (" + ddkdIds + ") ";
				
				System.out.println("Query lay tuyen ban hang la: " + query + "\n");
				ResultSet rs = db.get(query);
				
				if(rs != null)
				{
					try 
					{
						//out.write("<option value=''></option>");
						str = "<option value=''></option>";
						while(rs.next())
						{
							//out.write("<option value='" + rs.getString("tbhId") + "'>" + rs.getString("ten") + "-" + rs.getString("tbhTen") + "-" + rs.getString("ngaylamviec") + "</option>");
							if(tbhIds.indexOf(rs.getString("tbhId")) >= 0)
							{
								str += "<option style=\"padding: 2px; width: 90%\" value='" + rs.getString("tbhId") + "' selected>" + rs.getString("ten") + " - " + rs.getString("tbhTen") + " - " + rs.getString("ngaylamviec") + "</option>";
								System.out.println("Id nhay vo day...\n");
							}
							else
								str += "<option style=\"padding: 2px; width: 90%\" value='" + rs.getString("tbhId") + "'>" + rs.getString("ten") + " - " + rs.getString("tbhTen") + " - " + rs.getString("ngaylamviec") + "</option>";
						}
						rs.close();
					} 
					catch(Exception e) {}
				}
			}
			//System.out.println("Ket qua la: " + str + "\n");
			out.write(str);
		}
		else
		{
			System.out.println("___Tuyen ban hang Id: " + tbhIds);
			
			String maNvgn = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("maNvgn"));
			if(maNvgn == null)
				maNvgn = "";
			System.out.println("____Ma NVGN: " + maNvgn);
			
			if(tbhIds.length() > 0)
			{
				tbhIds = tbhIds.substring(0, tbhIds.length() - 1);
				String sql = "select distinct A.SMARTID , a.PK_SEQ as khId, a.ten as khTen, a.diachi, isnull(a.dienthoai, 'na') as dienthoai from khachhang a, khachhang_tuyenbh b, tuyenbanhang c where a.pk_seq = b.khachhang_fk and b.tbh_fk = c.pk_seq and c.npp_fk='" + nppId + "'";
				sql += " and c.pk_seq in (" + tbhIds + ")";
				
				if(maNvgn.trim().length() <= 0)
				{
					sql = sql + " and a.pk_seq not in (select khachhang_fk from NVGN_KH where nvgn_fk in (select pk_seq from nhanviengiaonhan where npp_fk = '" + nppId + "' and trangthai = '1') )";
				}
				else
				{
					sql = sql + " and a.pk_seq not in (select khachhang_fk from NVGN_KH where nvgn_fk in (select pk_seq from nhanviengiaonhan where npp_fk = '" + nppId + "' and trangthai = '1' and pk_seq != '" + maNvgn + "' ) )";
				}
				
				System.out.println("Query lay khach hang: " + sql + "\n");
				
				String str = "";
				if(update.equals("0"))
				{
					str = "<table width=\"100%\" border=\"0\" cellspacing=\"1\" cellpadding=\"3px\">" +
							"<tr class=\"tbheader\"><th width=\"150px\" align=\"center\">Mã khách hàng </th>" +
							"<th width=\"200px\" align=\"left\"> Họ tên </th><th width=\"400px\" align=\"left\"> địa chỉ </th><th width=\"150px\" align=\"left\">Điện thoại </th>" +
							"<th align=\"center\" width=\"10%\">Chọn <input type=\"checkbox\" id=\"selectAll\" onChange=\"CheckAll()\"/></th></tr>";
				}
				else
					str = "<table width=\"100%\" border=\"0\" cellspacing=\"1\" cellpadding=\"3px\">";
				
				ResultSet khList = db.get(sql);
				if(khList != null)
				{
					try 
					{
						int i = 0;
						while(khList.next())
						{
							if (i % 2 == 0)
								str += "<TR class= \"tblightrow\" > ";
							else
								str +=	"<TR class= \"tbdarkrow\" > ";
	
							str += "<TD width=\"150px\" align=\"center\">" + khList.getString("SMARTID") + "</TD>";
							str += "<TD width=\"200px\" align='left'>" + khList.getString("khTen") + "</TD>";
							str += "<TD width=\"400px\" align='left'>" + khList.getString("diachi") + "</TD>";
							str += "<TD width=\"150px\" align='left'>" + khList.getString("dienthoai") + "</TD>";									
							
						    str += "<TD align='center'>";
						   	str += "<input name=\"khIds\" type=\"checkbox\" value =" + khList.getString("khId") + " checked onChange=\"UnCheckedAll()\"></TD></TR>"; 
						   	i++;
						   	
						}
						khList.close();
						str += "<tr><td class=\"plainlabel\" colspan=\"6\">&nbsp;</td></tr>";
					} 
					catch(Exception e) {}
				}

				str += "</table>";
				System.out.println("Ket qua khachh hang la: " + str + "\n");
				out.write(str);
			}
			else
			{
				String str = "<table width=\"100%\" border=\"0\" cellspacing=\"1\" cellpadding=\"3px\">";
				out.write(str);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
	}

}
