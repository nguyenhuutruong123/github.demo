package geso.dms.center.servlets.kehoachbanhang;

import geso.dms.distributor.util.Utility;
import geso.dms.distributor.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ajaxKHBH extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
  
    public ajaxKHBH() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Utility util = new Utility();
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
		PrintWriter out = response.getWriter();
		
		String khTen = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khTen"));
		String diachi = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diachi"));
		String ddkdid = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdid"));
		
		//if(id.equals("ddkdTen")) //loc dai dien kinh doanh
		//{
			//System.out.println("Giam sat ban hang Id la: " + q + "\n");
		dbutils db = new dbutils();
		String query = "select top(100) pk_seq as khId,smartid, ten, diachi from khachhang where trangthai = '1'  ";
		
			
		if(khTen != "")
		{  
			String st = util.replaceAEIOU(khTen.trim());
		  	query = query + " and ( smartid like '%" + st + "%' or upper(TimKiem) like upper(N'%" + st + "%') )";
			//query += " and upper(ten) like upper(N'%" + khTen + "%')";
		}
		
		if(diachi != "")
		{
			String st = util.replaceAEIOU(diachi.trim());
		  	query = query + " and upper(TimKiem) like upper(N'%" + st + "%')";
		}
		System.out.println("DDKD "+ddkdid);
		if(ddkdid != null && ddkdid.length() > 0)
		{
			String st = util.replaceAEIOU(ddkdid.trim());
		  	query = query + " and ddkd_fkip = N'" + st + "' ";
		}
		query += " order by pk_seq desc";
		System.out.println("QRKH: "+query);
		
		String table = "<TABLE width=\"100%\" border=\"0\" cellspacing=\"1\" cellpadding=\"6\" id=\"tb_kh_tbh_cdpt\"> " + 
		 		"<tbody id=\"kh_tbh_cdpt\"> " +
		 		"<TR class=\"tbheader\"> " +
		 		"<TH width=\"5%\">M?? KH</TH> " +
		 		"<TH width=\"45%\">T??n KH</TH> " +
				"<TH width=\"50%\">?????a ch??? </TH> " + 
				"<TH width=\"5%\">Ch???n</TH> </TR>";
		
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				out.write("<option value=''></option>");
				int i = 0;
				while(rs.next())
				{
					if(i % 2 != 0)
						table += "<TR class= 'tblightrow' >";
					else
						table += "<TR class= 'tbdarkrow' >";
					table += "<TD align=\"left\" class=\"textfont\">" + rs.getString("smartid") + "</TD>";
					table += "<TD align=\"left\" class=\"textfont\">" + rs.getString("ten") + "</TD>";
					table += "<TD align=\"center\">" + rs.getString("diachi") + "</TD>";
					table += "<TD align=\"center\">" +
							"<input name=\"kh_tbh_cdptList\" type=\"checkbox\" value ='" + rs.getString("khId")+ ";" + rs.getString("ten")+ ";"+ rs.getString("diachi") + "'></TD></tr>";
				i++;}
			} 
			catch(Exception e) {}
		}
		table += "</table>";
		out.write(table);
				
		//}
		//else
		//{
			//if(id.equals("smartId"))
			//{
				//HttpSession session = request.getSession();
				//System.out.println("Dai dien kinh doanh Id la: " + q + "\n");
				//session.setAttribute("ddkdId", q);
			//}
		//}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
	}

}
