package geso.dms.center.servlets.tieuchithuong;

import geso.dms.distributor.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;

public class AjaxTctDP extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
    public AjaxTctDP() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
		PrintWriter out = response.getWriter();
		
		String query = (String)request.getQueryString();
		String diengiai = new String(query.substring(query.indexOf("diengiai=") + 9, query.indexOf("&soluongNhom=")).getBytes("UTF-8"), "UTF-8");
		diengiai = URLDecoder.decode(diengiai, "UTF-8").replace("+", " ");
		
		String soluongNhom = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("soluongNhom"));
		String sotienNhom = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sotienNhom"));
		String IsThung = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("IsThung"));
		String sanpham = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sanpham"));
		
		System.out.println("San pham la: " + sanpham );
		
		//Luu nhom dieu kien
		HttpSession session = request.getSession();
		String nguoitao = (String) session.getAttribute("userId");
		
		String msg = this.createDK(diengiai, soluongNhom, sotienNhom, sanpham, nguoitao, IsThung);
		
		out.write(msg);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
		PrintWriter out = response.getWriter();
		
		String nspId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nspId"));
		
		System.out.println("Nhom san pham: " + nspId);
		
		dbutils db = new dbutils();
		
		int count = 0;
		if(nspId.length() > 0)
		{
			String query = "select b.pk_seq, b.MA, b.TEN from NHOMSANPHAM_SANPHAM a inner join SANPHAM b on a.SP_FK = b.PK_SEQ " +
							"where a.NSP_FK = '" + nspId + "'";
			
			//System.out.println("345.Lay san pham: " + query);
			ResultSet nhomspRs = db.get(query);	
			
			String table = "<table align='left' cellpadding='0px' cellspacing='1px'> ";
			
			if(nhomspRs != null)
			{
				try 
				{
					while(nhomspRs.next())
					{
						table += "<tr> " +
								"<td width='100px' align='center'> " +
									"<input type='text' value='" + nhomspRs.getString("ma") + "' style='width: 100px' name='spMa' " +
										"onkeyup=\"ajax_showOptions(this,'sanpham',event)\" AUTOCOMPLETE='off'> " +
								"</td> " +
								"<td width='250px' align='left'> " +
									"<input type='text' value='" + nhomspRs.getString("ten") + "' name='spTen' style='width: 300px' readonly='readonly'> " +
								"</td>" +
							"</tr>";
						count++;
					}
					nhomspRs.close();
				} 
				catch (Exception e) { System.out.println("Exception: " + e.getMessage()); }
		    	
				while(count < 40)
		    	{
					table += "<tr> " +
								"<td width='100px' align='center'> " +
									"<input type='text' value='' style='width: 100px' name='spMa' " +
										"onkeyup=\"ajax_showOptions(this,'sanpham',event)\" AUTOCOMPLETE='off'> " +
								"</td> " +
								"<td width='250px' align='left'> " +
									"<input type='text' value='' name='spTen' style='width: 300px' readonly='readonly'> " +
								"</td>" +
							"</tr>";
					count++;
		    	}
		    	
		    	table += "</table>";
		    	
		    	//System.out.println("45678.Table ket qua la: " + table);
		    	db.shutDown();
		    	out.write(table);
			}
		}
		else
		{
			String table = "<table align='left' cellpadding='0px' cellspacing='1px'> ";
			for(int k = 0; k < 10; k++)
			{
				table += "<tr> " +
				"<td width='100px' align='center'> " +
					"<input type='text' value='' style='width: 100px' name='spMa' " +
						"onkeyup=\"ajax_showOptions(this,'sanpham',event)\" AUTOCOMPLETE='off'> " +
				"</td> " +
				"<td width='300px' align='left'> " +
					"<input type='text' value='' name='spTen' style='width: 300px' readonly='readonly'> " +
				"</td>" +
				
				"</tr>";
			}
			table += "</table>";
			//System.out.println("Table la: " + table);
			out.write(table);
		}
	}
	
	private String createDK(String diengiai, String soluongNhom, String sotienNhom, String sanpham, String nguoitao, String isThung)
	{
		dbutils db = new dbutils();
		String msg = "";

		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String ngaytao = getDateTime();
			
			String soluong = "null";
			String sotien = "null";
			
			if(soluongNhom.trim().length() > 0 )
				soluong = soluongNhom.trim();
			
			if(sotienNhom.trim().length() > 0)
				sotien = sotienNhom.trim();
			
			String query = "Insert into DieuKienDoPhu(diengiai, soluong, sotien, isThung, trangthai, ngaytao, nguoitao, ngaysua, nguoisua) " +
						   "values ( N'" + diengiai + "', " + soluong + ", " + sotien + ", '" + isThung + "', '1', '" + ngaytao + "', '" + nguoitao + "', '" + ngaytao + "', '" + nguoitao + "' ) ";
			
			if(!db.update(query))
			{
				db.getConnection().rollback();
				msg = "Khong the tao moi nhom DieuKien, " + query;
				return msg; 
			}
			
			query = "select IDENT_CURRENT('DieuKienDoPhu') as dkId";
			ResultSet rsDK = db.get(query);
			if(rsDK.next())
			{
				msg = rsDK.getString("dkId");
			}
			rsDK.close();
			
			if(sanpham.trim().length() > 0)
			{
				sanpham = sanpham.substring(0, sanpham.length() - 1);
				
				String[] spIds = sanpham.split(";");
				String sql = "";
				for(int i = 0; i < spIds.length; i++)
				{
					sql += " select pk_seq as spId from SanPham where ma = '" + spIds[i] + "' union ";
				}
				
				if(sql.trim().length() > 0)
					sql = sql.substring(0, sql.length() - 6);
				
				query = "Insert DieuKienDoPhu_SanPham( dieukien_fk, sanpham_fk ) " +
						"select IDENT_CURRENT('DieuKienDoPhu') as dkId, sanpham.spId " +
						"from ( " + sql + " ) sanpham  ";
				
				System.out.println("___CHEN SP: " + query);
				if(!db.update(query))
				{
					db.getConnection().rollback();
					msg = "Khong the tao moi nhom DieuKienDoPhu_SanPham, " + query;
					return msg; 
				}
				
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			db.shutDown();
			return e.getMessage();
		}
		
		db.shutDown();
		
		return msg;
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

}
