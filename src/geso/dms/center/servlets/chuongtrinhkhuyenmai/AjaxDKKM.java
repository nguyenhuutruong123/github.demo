package geso.dms.center.servlets.chuongtrinhkhuyenmai;

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

public class AjaxDKKM extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
    public AjaxDKKM() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
		PrintWriter out = response.getWriter();
		
		String query = (String)request.getQueryString();
		String diengiai = new String(query.substring(query.indexOf("diengiai=") + 9, query.indexOf("&loaidieukien=")).getBytes("UTF-8"), "UTF-8");
		diengiai = URLDecoder.decode(diengiai, "UTF-8").replace("+", " ");
		
		/*String dg = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai"));
		dg =  new String(dg.getBytes("UTF-8"), "UTF-8");
		dg = URLDecoder.decode(dg, "UTF-8").replace("+", " ");*/
		
		String loaidieukien = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaidieukien"));
		String hinhthuc = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("hinhthuc"));
		String sotong = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sotong"));
		String sanpham = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sanpham"));
		String isThung = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("isThung"));
		
		//System.out.println("San pham la: " + sanpham + " --- is Thung: " + isThung);
		/*System.out.println("Loai dieu kien: " + loaidieukien);
		System.out.println("Hinh thuc la: " + hinhthuc);
		System.out.println("So tong la: " + sotong);
		System.out.println("San pham la: " + sanpham);*/
		
		//Luu dieu kien khuyen mai
		HttpSession session = request.getSession();
		String nguoitao = (String) session.getAttribute("userId");
		
		String msg = this.createDKKM(diengiai, loaidieukien, hinhthuc, sotong, sanpham, nguoitao, isThung);
		
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
		System.out.println("Nhom san pham: " + nspId);
		System.out.println("Pos: " + nspId);
		
		dbutils db = new dbutils();
		
		int count = 0;
		if(nspId.length() > 0)
		{
			String query = "select b.pk_seq, b.MA, b.TEN from NHOMSANPHAMKM_SANPHAM a inner join SANPHAM b on a.SP_FK = b.PK_SEQ " +
							"where a.NSP_FK = '" + nspId + "'";
			
			System.out.println("345.Lay san pham: " + query);
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
									"<input type='text' value='" + nhomspRs.getString("ma") + "' style='width: 100px' name='dieukienkhuyenmai" + pos + ".masanpham' " +
										"onkeyup=\"ajax_showOptions(this,'sanpham',event)\" AUTOCOMPLETE='off'> " +
								"</td> " +
								"<td width='250px' align='left'> " +
									"<input type='text' value='" + nhomspRs.getString("ten") + "' name='dieukienkhuyenmai" + pos + ".tensanpham' style='width: 250px' readonly='readonly'> " +
								"</td>" +
								"<td width='60px' align='center'> " +
									"<input type='text' value='' name='dieukienkhuyenmai" + pos + ".soluong' style='width: 60px; text-align: right;'> " +
								"</td> " +
							"</tr>";
						count++;
					}
					nhomspRs.close();
				} 
				catch (SQLException e) { System.out.println("Exception: " + e.getMessage()); }
		    	
				while(count < 15)
		    	{
					table += "<tr> " +
								"<td width='100px' align='center'> " +
									"<input type='text' value='' style='width: 100px' name='dieukienkhuyenmai" + pos + ".masanpham' " +
										"onkeyup=\"ajax_showOptions(this,'sanpham',event)\" AUTOCOMPLETE='off'> " +
								"</td> " +
								"<td width='250px' align='left'> " +
									"<input type='text' value='' name='dieukienkhuyenmai" + pos + ".tensanpham' style='width: 250px' readonly='readonly'> " +
								"</td>" +
								"<td width='60px' align='center'> " +
									"<input type='text' value='' name='dieukienkhuyenmai" + pos + ".soluong' style='width: 60px; text-align: right;'> " +
								"</td> " +
							"</tr>";
					count++;
		    	}
		    	
				
				
		    	table += "</table>";
		    	
		    	System.out.println("45678.Table ket qua la: " + table);
		    	db.shutDown();
		    	out.write(table);
			}
		}
		else
		{
			String table = "<table align='left' cellpadding='2px' cellspacing='2px'> ";
			for(int k = 0; k < 10; k++)
			{
				table += "<tr> " +
				"<td width='100px' align='center'> " +
					"<input type='text' value='' style='width: 100px' name='dieukienkhuyenmai" + pos + ".masanpham' " +
						"onkeyup=\"ajax_showOptions(this,'sanpham',event)\" AUTOCOMPLETE='off'> " +
				"</td> " +
				"<td width='250px' align='left'> " +
					"<input type='text' value='' name='dieukienkhuyenmai" + pos + ".tensanpham' style='width: 250px' readonly='readonly'> " +
				"</td>" +
				"<td width='60px' align='center'> " +
					"<input type='text' value='' name='dieukienkhuyenmai" + pos + ".soluong' style='width: 60px; text-align: right;'> " +
				"</td> " +
				"</tr>";
			}
			table += "</table>";
			System.out.println("Table la: " + table);
			out.write(table);
		}
	}
	
	private String createDKKM(String diengiai, String loaidieukien, String hinhthuc, String sotong, String sanpham, String nguoitao, String isThung)
	{
		dbutils db = new dbutils();
		String msg = "";
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String ngaytao = getDateTime();
			
			String tongluong = "";
			String tongtien = "";
			
			if(loaidieukien.equals("1"))
				tongluong = tongtien = "";
			
			if(hinhthuc.equals("1"))
				tongluong = sotong;
			else
				tongtien = sotong;
			
			if(tongluong== null || tongluong.trim().length() <=0)
				tongluong = "null";
			
			if(tongtien== null || tongtien.trim().length() <= 0 )
				tongtien = "null";
			
			String thung = "0";
			if(isThung.equals("true"))
				thung = "1";
			
			String query = "Insert into Dieukienkhuyenmai(diengiai, tongluong, tongtien, loai, ngaytao, nguoitao, ngaysua, nguoisua, IsThung) values(";
			query = query + "N'" + diengiai + "', " + tongluong + ", " + tongtien + ", '" + loaidieukien + "', '" + ngaytao + "', '" + nguoitao + "', " +
					"'" + ngaytao + "', '" + nguoitao + "', '" + thung + "')";
			
			if(!db.update(query))
			{
				System.out.println("query = " + query);
				db.getConnection().rollback();
				msg = "Khong the tao moi 'Dieukienkhuyenmai', " + query;
				return msg; 
			}
			
			if(sanpham.length() > 0)
			{
				//lay dkkm current
				String dkkkmCurrent = "";
				query = "select IDENT_CURRENT('Dieukienkhuyenmai') as dkkmId";
				
				ResultSet rsDkkm = db.get(query);						
				rsDkkm.next();
				dkkkmCurrent = rsDkkm.getString("dkkmId");
				rsDkkm.close();
				msg = dkkkmCurrent;
				
				sanpham = sanpham.substring(0, sanpham.length() - 1);
				String[] masp = sanpham.split(";");
				
				if(loaidieukien.equals("1"))  //Dieu kien co dinh tung san pham
				{	
					String masanphamTest = "";
					for(int i = 0; i < masp.length; i++)
					{
						if(masp[i].length() > 0)
						{
							String[] sp = masp[i].split("__");
							
							String sql = "";
							/*if(isThung.equals("true"))
							{
								sql = "select a.PK_SEQ, b.SOLUONG1 / b.SOLUONG2 as quycach " +
									  "from SANPHAM a inner join QUYCACH b on a.PK_SEQ = b.SANPHAM_FK where a.MA = '" + sp[0].trim() + "' and b.DVDL2_FK = '100018'";
							}
							else
							{
								sql = "select pk_seq, 1 as quycach from sanpham where ma='" + sp[0].trim() + "'";
							}*/
							sql = "select pk_seq, 1 as quycach from sanpham where ma='" + sp[0].trim() + "'";
							
							ResultSet rs = db.get(sql);
							String pk_seq = "";
							long quycach = 1;
							if(rs != null) 
							{
								try
								{
									rs.next();
									pk_seq = rs.getString("pk_seq");
									quycach = rs.getLong("quycach");
									rs.close();
								}
								catch (SQLException e) {}
							} 
							
							masanphamTest += pk_seq + ",";
									
							query = "Insert into dieukienkm_sanpham(dieukienkhuyenmai_fk, sanpham_fk, soluong, batbuoc) " +
									"values('" + dkkkmCurrent + "', '" + pk_seq + "', " + sp[1] + " * " + quycach + ", 1)";
							
							if(!db.update(query))
							{
								geso.dms.center.util.Utility.rollback_throw_exception(db);
								msg = "Loi khi them moi bang dieukienkm_sanpham, " + query;
								return msg; 
							}
						}					
					}
					
					/*if(masanphamTest.length() > 0 && isThung.equals("true"))
					{
						masanphamTest = masanphamTest.substring(0, masanphamTest.length() - 1);
						query = "select AVG(SOLUONG1) as quycach, COUNT(distinct SOLUONG1) as sodong " +
								"from QUYCACH where SANPHAM_FK in ( select pk_seq from SANPHAM where PK_SEQ in ( " + masanphamTest + " ) ) " +
								"group by DVDL2_FK";
						
						System.out.println("1.Query check quy cach: " + query);
						
						ResultSet rsCheck = db.get(query);
						if(rsCheck != null)
						{
							if(rsCheck.next())
							{
								if(rsCheck.getInt("sodong") >= 2)
								{
									rsCheck.close();
									db.getConnection().rollback();
									msg = "Các sản phẩm trong điều kiện khuyến mại không cùng quy cách, vui lòng kiểm tra lại";
									return msg; 
								}
							}
							rsCheck.close();
						}
					}*/
				}
				else
				{
					String masanphamTest = "";
					for(int i = 0; i < masp.length; i++)
					{
						ResultSet rs = db.get("select pk_seq from sanpham where ma='" + masp[i].trim() + "'");
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
					
						masanphamTest += pk_seq + ",";
						query = "Insert into dieukienkm_sanpham(dieukienkhuyenmai_fk, sanpham_fk, soluong, batbuoc) values('" + dkkkmCurrent + "', '" + pk_seq + "', null, null)";
						
						if(!db.update(query))
						{
							geso.dms.center.util.Utility.rollback_throw_exception(db);
							msg = "Loi khi them moi bang dieukienkm_sanpham, " + query;
							return msg; 
						}
					}
					
					/*if(masanphamTest.length() > 0 && isThung.equals("true"))
					{
						masanphamTest = masanphamTest.substring(0, masanphamTest.length() - 1);
						query = "select AVG(SOLUONG1) as quycach, COUNT(distinct SOLUONG1) as sodong " +
								"from QUYCACH where SANPHAM_FK in ( select pk_seq from SANPHAM where PK_SEQ in ( " + masanphamTest + " ) ) " +
								"group by DVDL2_FK";
						
						System.out.println("1.Query check quy cach: " + query);
						
						ResultSet rsCheck = db.get(query);
						if(rsCheck != null)
						{
							if(rsCheck.next())
							{
								if(rsCheck.getInt("sodong") >= 2)
								{
									rsCheck.close();
									db.getConnection().rollback();
									msg = "Các sản phẩm trong điều kiện khuyến mại không cùng quy cách, vui lòng kiểm tra lại";
									return msg; 
								}
								else
								{
									System.out.println("2.tong luong la: " + query);
									
									if(tongluong != "null")
									{
										query = "update DieuKienKhuyenMai set tongluong = tongluong * " + rsCheck.getInt("quycach") + " " +
												"where pk_seq = '" + dkkkmCurrent + "'";
										
										if(!db.update(query))
										{
											rsCheck.close();
											geso.dms.center.util.Utility.rollback_throw_exception(db);
											msg = "Loi khi cap nhat DieuKienKhuyenMai, " + query;
											return msg; 
										}
									}
								}
							}
							rsCheck.close();
						}
					}*/
				}
			
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
			}
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
