<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="geso.dms.distributor.db.sql.dbutils"%>
<%@ page import="geso.dms.center.util.*"%>
<%@ page import="java.net.URLDecoder"%>

<% String nppId = (String) session.getAttribute("nppId"); %>
<% String kbhId = (String) session.getAttribute("kbhId"); %>
<% String khoId = (String) session.getAttribute("khoId"); %>
<% String dvkdId = (String) session.getAttribute("dvkdId"); %>
<% String ngayghinhan = (String) session.getAttribute("ngayghinhan"); %>
<%
	dbutils db =new dbutils();
	try
	{

		response.setHeader("Content-Type", "text/html");
		request.setCharacterEncoding("UTF-8");
	   	String query = (String)request.getQueryString(); 	
	   	query = new String(query.substring(query.indexOf("&letters=") + 9, query.length()).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
	   	
	   	Utility Ult = new Utility();
	   	query = Ult.replaceAEIOU(query).toUpperCase();
		
	   //	System.out.println(query);
		String chuoi="a.timkiem  like '%"+query+"%' ";
		 
		   if(query.length() <5 && (query.substring(0,1).equals("M") || query.substring(0,1).equals("W"))){
			   chuoi="a.ma  like '"+query+"%' ";
		   }
	   	
		String command ="";
	  	command = 
		" select spid, ma, ten, donvi, sum(hienhuu) hienhuu, dongia "+
		" from "+
		" ( "+
		"	select  top 20  a.pk_seq as spId,a.ma, a.ten, isnull(b.donvi, 'Chua xac dinh') as donvi,  "+
		"		 sum(isnull(kho.available,0))   as hienhuu  ,	"+
		"	isnull( ( select GIABANLECHUAN from BANGGIABLC_SANPHAM sp where SANPHAM_FK = a.pk_seq and BGBLC_FK in   "+
		"	(	select top(1) PK_SEQ from BANGGIABANLECHUAN bg inner join BANGGIABANLECHUAN_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIABANLECHUAN_FK  "+ 
		"				where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = '" + nppId + "' and bg.DVKD_FK = a.dvkd_Fk and bg.KENH_FK = '"+kbhId+"'  "+
		"					 order by bg.TUNGAY desc )   "+
		"						),0 ) as dongia "+
		"	from sanpham a  left join "+
		"  ( select available,sanpham_fk ,ngaynhapkho from NHAPP_KHO_chitiet where NPP_FK='" + nppId + "'  and KBH_FK='"+kbhId+"'  and KHO_FK='" + khoId + "'  and ngaynhapkho<='"+ngayghinhan+"') kho on kho.sanpham_fk =a.pk_seq  " +
		" left join donvidoluong b on a.dvdl_fk = b.pk_seq     "+
		" where a.dvkd_fk = '"+dvkdId+"' and "+chuoi  + " group by  a.pk_seq ,a.ma, a.ten, donvi,DVKD_FK order by a.ten "+
		" ) as t group by spid, ma, ten, donvi, dongia ";  
			  	
			  	
		 
 		System.out.println("[sanpham]"+command);
 		
		ResultSet sp = db.get(command);
		int j = 0;
		String MASP="";
		String TENSP="";
		if(sp != null)
		{
			while(sp.next())
			{
				int hienhuu = sp.getInt("hienhuu");
				if(hienhuu <= 0)
					hienhuu = 0;
				
				  MASP = sp.getString("ma");
				  TENSP = sp.getString("ten");
					out.print("###" + sp.getString("ma") + " - " + TENSP +" [" + sp.getString("donvi") + "] ["+ sp.getString("dongia") + "] [" + Integer.toString(hienhuu) + "] ["+ sp.getString("spId") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
			 
					j++;
			}	
		}
		if(j==1)
		{
			//System.out.println("vo day j : "+ j);
			command = 
					  "	select top(20) a.pk_seq as spId,a.ma, a.ten, isnull(b.donvi, 'Chua xac dinh') as donvi,  "+
					  "		(select available from NHAPP_KHO where NPP_FK='" + nppId + "'  and KBH_FK='"+kbhId+"' and SANPHAM_FK=a.PK_SEQ and KHO_FK='" + khoId + "') as hienhuu  ,	"+
					  "	isnull( ( select GIABANLECHUAN from BANGGIABLC_SANPHAM sp where SANPHAM_FK = a.pk_seq and BGBLC_FK in   "+
					  "	(	select top(1) PK_SEQ from BANGGIABANLECHUAN bg inner join BANGGIABANLECHUAN_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIABANLECHUAN_FK  "+ 
					  "				where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = '" + nppId + "' and bg.DVKD_FK = a.dvkd_Fk and bg.KENH_FK = '"+kbhId+"'  "+
					  "					 order by bg.TUNGAY desc )   "+
					  "						),0 ) as dongia "+
					  "	from sanpham a  left join donvidoluong b on a.dvdl_fk = b.pk_seq     "+
							  " where   a.ma <> '"+MASP+"' and SUBSTRING(ten,0,CHARINDEX(' ',ten,0 ) ) "+ 
							  " in (  select    SUBSTRING(ten,0,CHARINDEX(' ',ten,0 ) )  from SANPHAM where MA =  '"+MASP+"') ";
											  
			sp = db.get(command);
			System.out.println("[sanpham]"+command);
			if(sp != null)
			{
				while(sp.next())
				{
					int hienhuu = sp.getInt("hienhuu");
					if(hienhuu <= 0)
						hienhuu = 0;
					
					  MASP = sp.getString("ma");
					  TENSP = sp.getString("ten");
	  
						out.print("###" + sp.getString("ma") + " - " + TENSP +" [" + sp.getString("donvi") + "] ["+ sp.getString("dongia") + "] [" + Integer.toString(hienhuu) + "] ["+ sp.getString("spId") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
				 
						j++;
				}	
			}
			
		}
			if(sp != null)
				sp.close();
		db.shutDown();
		db=null;
		nppId=null;
		kbhId=null;
		khoId=null;
	
	}	
	catch(Exception ex){
		ex.printStackTrace();
		if(db!=null){
			db.shutDown();
			db=null;
		}
	}
%>

