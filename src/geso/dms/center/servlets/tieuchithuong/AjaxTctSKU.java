package geso.dms.center.servlets.tieuchithuong;

import geso.dms.center.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
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

public class AjaxTctSKU extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
   
    public AjaxTctSKU() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
		PrintWriter out = response.getWriter();
		
		String query = (String)request.getQueryString();
		System.out.println("___Query la: " + query);
		
		String diengiai = new String(query.substring(query.indexOf("diengiai=") + 9, query.indexOf("&trongso=")).getBytes("UTF-8"), "UTF-8");
		diengiai = URLDecoder.decode(diengiai, "UTF-8").replace("+", " ");
		
		String tongluong = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trongso"));
		String sanpham = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sanpham"));
		String thuongSS = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thuongSS"));
		String thuongTDSS = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thuongTDSS"));
		String thuongSR = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thuongSR"));
		String thuongTDSR = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thuongTDSR"));
		String thuongASM = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thuongASM"));
		String thuongTDASM = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thuongTDASM"));
		
		//Luu dieu kien khuyen mai
		HttpSession session = request.getSession();
		String nguoitao = (String) session.getAttribute("userId");
		
		String msg = this.createNsp(diengiai, tongluong, sanpham, thuongSS, thuongTDSS, thuongSR, thuongTDSR, thuongASM, thuongTDASM, nguoitao);
		
		out.write(msg);
	}

	private String createNsp(String diengiai, String tongluong, String sanpham, String thuongSS, String thuongTDSS, String thuongSR, 
			String thuongTDSR, String thuongASM, String thuongTDASM, String nguoitao)
	{
		dbutils db = new dbutils();
		String msg = "";
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			//2 la nhom SP thuong SKU
			String query = "insert nhomsanpham(diengiai, nsp_parent_fk, loaithanhvien, trangthai, ngaytao, ngaysua, nguoitao, nguoisua, ten, type, loainhom) " +
					"values(N'" + diengiai + "', '0', '2', '0', '" + this.getDateTime() + "', '" + this.getDateTime() +"', '" + nguoitao + "', '" + nguoitao + "', N'" + diengiai + "','2', '0')";
			
			System.out.println("1.Insert NSP: " + query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				msg = "1.Lỗi khi thêm mới nhóm sản phẩm: " + query;
				return msg;
			}
			
			if(sanpham.length() > 0)
			{
				//lay dkkm current
				String nspCurrent = "";
				query = "select IDENT_CURRENT('nhomsanpham')as nspId";
				
				ResultSet rsDkkm = db.get(query);						
				rsDkkm.next();
				nspCurrent = rsDkkm.getString("nspId");
				rsDkkm.close();
				msg = nspCurrent;
				
				sanpham = sanpham.substring(0, sanpham.length() - 1);
				String[] masp = sanpham.split(";");
				
				thuongSS = thuongSS.substring(0, thuongSS.length() - 1);
				String[] tss = thuongSS.split(";");
				
				thuongTDSS = thuongTDSS.substring(0, thuongTDSS.length() - 1);
				String[] ttdss = thuongTDSS.split(";");
				
				thuongSR = thuongSR.substring(0, thuongSR.length() - 1);
				String[] tsr = thuongSR.split(";");
				
				thuongTDSR = thuongTDSR.substring(0, thuongTDSR.length() - 1);
				String[] ttdsr = thuongTDSR.split(";");
				
				thuongASM = thuongASM.substring(0, thuongASM.length() - 1);
				String[] tasm = thuongASM.split(";");
				
				thuongTDASM = thuongTDASM.substring(0, thuongTDASM.length() - 1);
				String[] ttdasm = thuongTDASM.split(";");
				
				for(int i = 0; i < masp.length; i++)
				{
					if(masp[i].length() > 0)
					{
						String[] sp = masp[i].split("__");
						
						ResultSet rs = db.get("select pk_seq from sanpham where ma='" + sp[0].trim() + "'");
						String pk_seq = "";
						if(rs != null) 
						{
							try
							{
								rs.next();
								pk_seq = rs.getString("pk_seq");
								rs.close();
							}
							catch (SQLException e) {}
						} 
								
						query = "insert nhomsanpham_sanpham(nsp_fk, sp_fk, thuong_ss, thuong_td_ss, thuong_sr, thuong_td_sr, thuong_asm, thuong_td_asm) " +
								"values('" + nspCurrent + "', '" + pk_seq + "', '" + tss[i] + "', '" + ttdss[i] + "', '" + tsr[i] + "', '" + ttdsr[i] + "', '" + tasm[i] + "', '" + ttdasm[i] + "')";
						System.out.println("2.Insert nhomsanpham_sanpham: " + query);
						
						if(!db.update(query))
						{ 
							geso.dms.center.util.Utility.rollback_throw_exception(db);
							msg = "Loi khi them moi bang nhomsanpham_sanpham, " + query;
							return msg; 
						}
					}					
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (SQLException e)
		{
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {}
			return "Khong the them moi NSP: " + e.getMessage();
		}
		
		return msg;
	}

	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
