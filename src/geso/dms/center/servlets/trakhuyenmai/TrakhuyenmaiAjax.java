package geso.dms.center.servlets.trakhuyenmai;

import geso.dms.distributor.db.sql.dbutils;

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

public class TrakhuyenmaiAjax extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    
    public TrakhuyenmaiAjax() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
		PrintWriter out = response.getWriter();
		
		String query = (String)request.getQueryString();
		String diengiai = new String(query.substring(query.indexOf("diengiai=") + 9, query.indexOf("&loaitra=")).getBytes("UTF-8"), "UTF-8");
		diengiai = URLDecoder.decode(diengiai, "UTF-8").replace("+", " ");
		
		String loaitra = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaitra"));
		String hinhthuc = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("hinhthuc"));
		String sotong = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sotong"));
		String sanpham = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sanpham"));
		
		System.out.println("Dien giai la: " + diengiai);
		System.out.println("Loai dieu kien: " + loaitra);
		System.out.println("Hinh thuc la: " + hinhthuc);
		System.out.println("So tong la: " + sotong);
		System.out.println("San pham la: " + sanpham);
		
		//Luu dieu kien khuyen mai
		HttpSession session = request.getSession();
		String nguoitao = (String) session.getAttribute("userId");
		
		String msg = this.createTRAKM(diengiai, loaitra, hinhthuc, sotong, sanpham, nguoitao);
		
		out.write(msg);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
		PrintWriter out = response.getWriter();
		
		String nspId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nspId"));
		String pos = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("pos"));
		//System.out.println("Nhom san pham: " + nspId);
		System.out.println("Pos: " + nspId);
		
		dbutils db = new dbutils();
		
		int count = 0;
		if(nspId.length() > 0)
		{
			String query = "select b.pk_seq, b.MA, b.TEN from NHOMSANPHAMKM_SANPHAM a inner join SANPHAM b on a.SP_FK = b.PK_SEQ " +
							"where a.NSP_FK = '" + nspId + "'";
			
			ResultSet nhomspRs = db.get(query);	
			
			String table = "<table align='left' cellpadding='2px' cellspacing='2px'> ";
			
			if(nhomspRs != null)
			{
				try 
				{
					while(nhomspRs.next())
					{
						table += "<tr> " +
								"<td width='100px' align='center'> " +
									"<input type='text' value='" + nhomspRs.getString("ma") + "' style='width: 100px' name='trakhuyenmai" + pos + ".masanpham' " +
										"onkeyup=\"ajax_showOptions(this,'sanpham',event)\" AUTOCOMPLETE='off'> " +
								"</td> " +
								"<td width='250px' align='left'> " +
									"<input type='text' value='" + nhomspRs.getString("ten") + "' name='trakhuyenmai" + pos + ".tensanpham' style='width: 250px' readonly='readonly'> " +
								"</td>" +
								"<td width='60px' align='center'> " +
									"<input type='text' value='' name='trakhuyenmai" + pos + ".soluong' style='width: 60px; text-align: right;'> " +
								"</td> " +
							"</tr>";
						count ++;
					}
					nhomspRs.close();
				} 
				catch (SQLException e) {}
		    	
				while(count < 15)
		    	{
					table += "<tr> " +
								"<td width='100px' align='center'> " +
									"<input type='text' value='' style='width: 100px' name='trakhuyenmai" + pos + ".masanpham' " +
										"onkeyup=\"ajax_showOptions(this,'sanpham',event)\" AUTOCOMPLETE='off'> " +
								"</td> " +
								"<td width='250px' align='left'> " +
									"<input type='text' value='' name='trakhuyenmai" + pos + ".tensanpham' style='width: 250px' readonly='readonly'> " +
								"</td>" +
								"<td width='60px' align='center'> " +
									"<input type='text' value='' name='trakhuyenmai" + pos + ".soluong' style='width: 60px; text-align: right;'> " +
								"</td> " +
							"</tr>";
					count++;
		    	}
		    	
		    	table += "</table>";
		    	
		    	//System.out.println("Table la: " + table);
		    	db.shutDown();
		    	out.write(table);
			}
		}
		else
		{
			String table = "<table align='left' cellpadding='2px' cellspacing='2px'> ";
			while(count < 15)
	    	{
				table += "<tr> " +
							"<td width='100px' align='center'> " +
								"<input type='text' value='' style='width: 100px' name='trakhuyenmai" + pos + ".masanpham' " +
									"onkeyup=\"ajax_showOptions(this,'sanpham',event)\" AUTOCOMPLETE='off'> " +
							"</td> " +
							"<td width='250px' align='left'> " +
								"<input type='text' value='' name='trakhuyenmai" + pos + ".tensanpham' style='width: 250px' readonly='readonly'> " +
							"</td>" +
							"<td width='60px' align='center'> " +
								"<input type='text' value='' name='trakhuyenmai" + pos + ".soluong' style='width: 60px; text-align: right;'> " +
							"</td> " +
						"</tr>";
				count++;
	    	}
	    	
	    	table += "</table>";
	    	
	    	out.write(table);
		}
	}
	

	private String createTRAKM(String diengiai, String loaitra, String hinhthuc, String sotong, String sanpham, String nguoitao)
	{
		dbutils db = new dbutils();
		String msg = "";
		String query = "";
		ResultSet rsCheck;
		try 
		{
			query = " select COUNT(*) AS SODONG from TRAKHUYENMAI where DIENGIAI=N'"+diengiai+"'";			
			System.out.print("[Check]"+query);
			rsCheck = db.get(query);
			while(rsCheck.next())
			{
				if(rsCheck.getInt(1)>0)
				{
					return "Diễn giải này đã có, vui lòng nhập diễn giải khác !";
				}
			}	
			rsCheck.close();
			
			db.getConnection().setAutoCommit(false);
			
			String ngaytao = getDateTime();
			
			
			String tongtien = "";
			String tongck = "";
			String tongluong = "";
			
			if(hinhthuc.equals("1"))
				tongluong = tongtien = tongck = "";
			
			if(loaitra.equals("1"))
			{
				tongtien = sotong;
			}
			else
			{
				if(loaitra.equals("2"))
				{
					tongck = sotong;
				}
				else
					tongluong = sotong;
			}
			
			if(tongluong.trim().length() <=0)
				tongluong = "null";
			
			if(tongtien.trim().length() <=0)
				tongtien = "null";
			
			if(tongck.trim().length() <=0)
				tongck = "null";
			
			if (loaitra != null && !loaitra.equals("3")) {
				try {
					if ( (tongtien != null && Double.parseDouble(tongtien) <= 0) 
							|| (tongck != null &&Double.parseDouble(tongck) <= 0)) {
						msg = "Tổng lượng/tổng tiền không được = 0";
						return msg;
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			query = "Insert into Trakhuyenmai(diengiai, tongluong, tongtien, chietkhau, loai, hinhthuc, ngaytao, nguoitao, ngaysua, nguoisua) " +
					"values(N'" + diengiai + "', " + tongluong + ", " + tongtien + "," + tongck + ", '" + loaitra + "', '" + hinhthuc + "', '" + ngaytao + "', '" + nguoitao + "', '" + ngaytao + "', '" + nguoitao + "')";
			System.out.println("Cau lenh Insert: " + query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				msg = "Khong the tao moi 'Trakhuyenmai', " + query;
				return msg; 
			}
			
			String trakmCurrent = "";
			query = "select IDENT_CURRENT('Trakhuyenmai') as trakmId";
			
			ResultSet rsDkkm = db.get(query);						
			rsDkkm.next();
			trakmCurrent = rsDkkm.getString("trakmId");
			rsDkkm.close();
			msg = trakmCurrent;
			
			if(loaitra.equals("3")) //tra san pham
			{
				if(sanpham.length() > 0)
				{
					sanpham = sanpham.substring(0, sanpham.length() - 1);
					String[] masp = sanpham.split(";");
					
					for(int i = 0; i < masp.length; i++)
					{
						if(masp[i].length() > 0)
						{
							String[] sp = new String[2];
							if(masp[i].indexOf("__") > 0 )
							{
								sp = masp[i].split("__");
							}
							else
							{
								sp[0] = masp[i];
								sp[1] = "null"; //khong co so luong
							}
							
							query = "select pk_seq from sanpham where ma='" + sp[0].trim() + "'";
							ResultSet rs = db.get(query);
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
							
							if(hinhthuc.equals("1"))  //Dieu kien co dinh tung san pham
							{	
								query = "Insert into trakhuyenmai_sanpham(trakhuyenmai_fk, sanpham_fk, soluong, dongia) values('" + trakmCurrent + "', '" + pk_seq + "', " + sp[1].trim() + ", NULL)";
							}
							else
							{
								query = "Insert into trakhuyenmai_sanpham(trakhuyenmai_fk, sanpham_fk, soluong, dongia) values('" + trakmCurrent + "', '" + pk_seq + "', NULL, NULL)";
							}
							
							System.out.println("12.Insert San pham" + query);
							if(!db.update(query))
							{
								db.getConnection().rollback();
								msg = "Loi khi them moi bang trakhuyenmai_sanpham, " + query;
								return msg; 
							}
						}					
					}
					
				}
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (SQLException e) 
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
