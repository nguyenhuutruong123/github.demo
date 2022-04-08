<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.nio.charset.Charset"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.db.sql.dbutils" %>
<%@ page  import = "geso.dms.center.util.Utility" %>
<%@ page  import = "java.net.URLDecoder;" %>

<%
	String dvkdId = "100001";
	if(session.getAttribute("dvkdId") != null )
		dvkdId = (String) session.getAttribute("dvkdId");
	
	String ngaychungtu = Utility.getNgayHienTai();
	if(session.getAttribute("ngaychungtu") != null)
		ngaychungtu =  (String) session.getAttribute("ngaychungtu");
			
	String kbhId = "-1";
	if(session.getAttribute("kbhId") != null )
		kbhId = (String) session.getAttribute("kbhId");
	
	String nppId = "-1";
	if(session.getAttribute("nppId") != null )
		nppId = (String) session.getAttribute("nppId");
	
	String khonhapId = "-1";
	if(session.getAttribute("khoId") != null )
		khonhapId = (String) session.getAttribute("khoId");
	if(khonhapId.equals("-1"))
		return;
	System.out.println("id kho ban hang: "+khonhapId);
	System.out.println("--LOC SP..." + dvkdId + "  -- KBH ID: " + kbhId + "  -- Đối tác ID: " + nppId );
	System.out.println("dvkdId: "+dvkdId);
	
	if( dvkdId.trim().length() > 0 && kbhId.trim().length() > 0 && nppId.trim().length() > 0 )
	{
		dbutils db = new dbutils();
		
		
		try
		{		
						
			 //BAN DAU LAY GIA CHUAN SAU DO CHON LAI GIA THI CAP NHAT LAI GIA THEO DON VI (BANG GIA BEN NAY KHONG CHIA THEO KENH)
			 String command = "";
			 
			 command =  "select ISNULL((select sum(kho.available) from nhapp_kho_chitiet kho where kho.ngaynhapkho<='"+ngaychungtu+"'  and kho.sanpham_fk=a.pk_seq and kho.KHO_FK= "+khonhapId+" and NPP_FK='"+nppId+"' and kho.KBH_FK="+kbhId+" ) ,0)  as soluongton,a.ma, a.ten, b.donvi, ISNULL(trongluong, 0) as trongluong, ISNULL(thetich, 0) as thetich, " +
						"	cast (  isnull( ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = a.PK_SEQ and DVDL1_FK = a.DVDL_FK	and DVDL2_FK = '100018' ), 0 ) as numeric(18, 2) ) as qc,	" +
						" 	 case when  "+khonhapId+"=100001 then 0 else isnull( ( select GIAMUANPP * ( 1 - isnull( ( select chietkhau from BANGGIAMUANPP_NPP where  banggiamuaNPP_FK = bg_sp.bgmuaNPP_FK and NPP_FK = '" + nppId + "' ), 0) / 100 ) " +
						" 				from BGMUANPP_SANPHAM bg_sp " +
						"			    where SANPHAM_FK = a.pk_seq  " +
						"					and BGMUANPP_FK in ( select top(1) PK_SEQ from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK where bg.kenh_fk="+kbhId+" and  bg.TRANGTHAI = '1' and bg_npp.NPP_FK = '" + nppId + "' and bg.DVKD_FK = '" + dvkdId + "'  order by bg.TUNGAY desc ) ), 0) end  as giamua, a.ptThue  " +
						"from SANPHAM a left join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq " +
						"where a.pk_seq > 0 and a.DVKD_FK = '" + dvkdId + "' and a.pk_seq in( select c.sanpham_fk from BANGGIAMUANPP_NPP a inner join BANGGIAMUANPP b on a.BANGGIAMUANPP_FK= b.pk_seq inner join BGMUANPP_SANPHAM c on b.pk_seq=c.BGMUANPP_FK where b.TRANGTHAI = '1' and  a.npp_fk = '" + nppId + "' and b.DVKD_FK= '" + dvkdId + "' ) ";				
							 
			System.out.println("Lay san pham: " + command);
			
			response.setHeader("Content-Type", "text/html");
			
			String query = (String)request.getQueryString();			
			query = new String(query.substring(query.indexOf("&letters=") + 9, query.length()).getBytes("UTF-8"), "UTF-8");
		    query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
		    		    			
			ResultSet sp = db.get(command);
			int j = 0;
			if(sp != null)
			{
				while(sp.next())
				{					
					if(sp.getString("ma").toUpperCase().contains(query.toUpperCase()) || sp.getString("ten").toUpperCase().contains(query.toUpperCase()) )
					{
						String tensp = sp.getString("ten");
						out.print("###" + sp.getString("ma") + " - " + tensp + " [" + sp.getString("donvi") + "] [" + sp.getString("giamua") + "] [" + sp.getString("ptThue") + "] [" + sp.getString("soluongton") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
					}						
				}	
				sp.close();
			}			
			db.shutDown();
		}
		catch(Exception ex){ System.out.println("Xay ra exception roi ban..."); ex.printStackTrace(); }
	}		 	
	
%>

