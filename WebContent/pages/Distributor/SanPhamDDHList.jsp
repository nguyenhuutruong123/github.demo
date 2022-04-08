<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.db.sql.dbutils" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page import="java.net.URLDecoder"%>
<%@ page import="geso.dms.center.util.*"%>
<%
	String dvkdId = "";
	if(session.getAttribute("dvkdId") != null )
		dvkdId = (String) session.getAttribute("dvkdId");
	
	String kbhId = "";
	if(session.getAttribute("kbhId") != null )
		kbhId = (String) session.getAttribute("kbhId");
	
	String nppId = "";
	if(session.getAttribute("nppId") != null )
		nppId = (String) session.getAttribute("nppId");
	
	String khoId = "";
	if(session.getAttribute("khoId") != null )
		khoId = (String) session.getAttribute("khoId");
	
	DecimalFormat df2 = new DecimalFormat( "#,###,###,###" );

String ngaydonhang = (String)session.getAttribute("ngaydonhang"); 
if(ngaydonhang == null)
	ngaydonhang = "";
	
	
	if( dvkdId.trim().length() > 0 && kbhId.trim().length() > 0 && nppId.trim().length() > 0 )
	{
		dbutils db = new dbutils();
		OracleConnUtils sync = new OracleConnUtils();
		try
		{		
			String query = "select loainpp, nppc1 from nhaphanphoi where pk_seq = "+nppId;
			String loaiNPP = "", nppc1 = "";
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				rs.next();
				loaiNPP = rs.getString("loainpp");
				nppc1 = rs.getString("nppc1");
			}
			
			String command = 	
			"		select a.ma, a.ten, b.donvi, a.ptThue as thuexuat,  "+
			"			isnull( ( select GIAMUANPP from BGMUANPP_SANPHAM sp where SANPHAM_FK = a.pk_seq and BGMUANPP_FK in "+
			"						(	select top(1) PK_SEQ from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK "+
			"									where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = '"+nppId+"' and bg.DVKD_FK = '"+dvkdId+"' and bg.KENH_FK = '"+kbhId+"'  "+
			"										and bg.TUNGAY <='"+ngaydonhang+"' order by bg.TUNGAY desc )  "+
			"					),0 ) as giamua, "+
			"			isnull( ( select ptChietKhau from BGMUANPP_SANPHAM sp where SANPHAM_FK = a.pk_seq and BGMUANPP_FK in "+
			"						(	select top(1) PK_SEQ from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK "+
			"									where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = '"+nppId+"' and bg.DVKD_FK = '"+dvkdId+"' and bg.KENH_FK = '"+kbhId+"'  "+
			"										and bg.TUNGAY <='"+ngaydonhang+"' order by bg.TUNGAY desc )  "+
			"					),0 ) as ptChietKhau, "+
			"				(	 "+
			"					select top(1) PK_SEQ from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK "+
			"									where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = '"+nppId+"' and bg.DVKD_FK = '"+dvkdId+"' and bg.KENH_FK = '"+kbhId+"' "+ 
			"										and bg.TUNGAY <='"+ngaydonhang+"' order by bg.TUNGAY desc  "+
			"				) as bgId";
			if(loaiNPP.equals("6")) command += ", isnull(c.available, 0) as soluong ";
			command += "		from SANPHAM a inner join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq ";
			if(loaiNPP.equals("6"))
				command += "		left join NHAPP_KHO c on a.PK_SEQ = c.SANPHAM_FK and c.npp_fk = "+nppc1+" and c.KHO_FK = "+khoId;
			command += "		where a.pk_seq > 0 and a.DVKD_FK = '"+dvkdId+"' ";
			
			System.out.println("[command] " + command);
			
			response.setHeader("Content-Type", "text/html");
			request.setCharacterEncoding("UTF-8");
			
		   	query = (String)request.getQueryString(); 
		   	
		   	query = new String(query.substring(query.indexOf("&letters=") + 9, query.length()).getBytes("UTF-8"), "UTF-8");
		   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
		   	
		   	Utility Ult = new Utility();
		   	query = Ult.replaceAEIOU(query);
			
		   	System.out.println("[query] "+query);
			ResultSet sp = db.get(command);
			int j = 0;
			if(sp != null)
			{
				while(sp.next())
				{
					String spTen = sp.getString("ten");
					
					String masp = Ult.replaceAEIOU(sp.getString("ma"));
					String tensp = Ult.replaceAEIOU(sp.getString("ten"));
					String donvi = Ult.replaceAEIOU(sp.getString("donvi"));
					
					if(masp.toUpperCase().startsWith(query.toUpperCase()) ||masp.toUpperCase().indexOf(query.toUpperCase()) >= 0 
							|| tensp.toUpperCase().indexOf(query.toUpperCase()) >= 0 || donvi.toUpperCase().indexOf(query.toUpperCase()) >= 0 )
					{
						float ptChietKhau =sp.getFloat("ptChietKhau");
						float DonGia =sp.getFloat("giamua");
						float GiaSauCK = DonGia*(1-ptChietKhau/100);
						float tonkho = 0;
						String kho = "", dvkd = "";
						if(loaiNPP.equals("1"))
						{
							command = "select ten from khott where pk_seq = "+khoId;
							rs = db.get(command);
							if(rs != null)
							{
								rs.next();
								kho = rs.getString("ten");
								rs.close();
							}
							command = "select donvikinhdoanh from donvikinhdoanh where pk_seq = "+dvkdId;
							rs = db.get(command);
							if(rs != null)
							{
								rs.next();
								dvkd = rs.getString("donvikinhdoanh");
								rs.close();
							}
							command = "select ON_HAND as soluong from apps.V_TONKHO where ORGANIZATION_CODE = '"+kho+"' and ITEM_CODE = '"+masp+"' and PRODUCT_GROUP = upper('"+dvkd+"')";
							System.out.println("[v_tonkho] "+command);
							rs = sync.get(command);
							if(rs != null)
							{
								if(rs.next())
									tonkho = rs.getFloat("soluong");
								rs.close();
							} 
						}
						else
							tonkho = sp.getFloat("soluong"); 
						
						out.print("###" + sp.getString("ma") + " - " + spTen + " [" + sp.getString("donvi") + "] [" + GiaSauCK + "] [" + sp.getDouble("thuexuat") + "] [" +  DonGia + "] [" + ptChietKhau + "] [" + tonkho + "] [" + sp.getString("bgId") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
					}	
				}	
				sp.close();
			}
			
			db.shutDown();
		}
		catch(Exception ex){ ex.printStackTrace(); System.out.println("Xay ra exception roi ban..."); }
	}
%>

