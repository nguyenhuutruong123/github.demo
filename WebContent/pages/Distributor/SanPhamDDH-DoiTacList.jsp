<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.db.sql.dbutils" %>

<%
	String dvkdId = "100001";
	if(session.getAttribute("dvkdId") != null )
		dvkdId = (String) session.getAttribute("dvkdId");
	
	String kbhId = "-1";
	if(session.getAttribute("kbhId") != null )
		kbhId = (String) session.getAttribute("kbhId");
	
	String nppId = "-1";
	if(session.getAttribute("nppId") != null )
		nppId = (String) session.getAttribute("nppId");
	
	String doitacId = "-1";
	if(session.getAttribute("doitacId") != null )
		doitacId = (String) session.getAttribute("doitacId");
	
	String khonhapId = "-1";
	if(session.getAttribute("khoId") != null )
		khonhapId = (String) session.getAttribute("khoId");
	
	String ngaydonhang = "";
	if(session.getAttribute("ngaydh") != null )
		ngaydonhang = (String) session.getAttribute("ngaydh");
	
	
	
	System.out.println("--LOC SP..." + dvkdId + "  -- KBH ID: " + kbhId + "  -- Đối tác ID: " + nppId + " --- ngaydh  ="+ ngaydonhang );
	if( ngaydonhang.trim().length() > 0  && dvkdId.trim().length() > 0 && kbhId.trim().length() > 0 && nppId.trim().length() > 0 )
	{
		dbutils db = new dbutils();
		try
		{	
			//BÁN CHO ĐỐI TÁC DÙNG BẢNG GIÁ RIÊNG
			
			 //BAN DAU LAY GIA CHUAN SAU DO CHON LAI GIA THI CAP NHAT LAI GIA THEO DON VI (BANG GIA BEN NAY KHONG CHIA THEO KENH)
			 String command = "";
			 
						
			command =   "\n select d.AVAILABLE   soluongton "+
						"\n 	,a.ma, a.ten, b.donvi, ISNULL(trongluong, 0) as trongluong, ISNULL(thetich, 0) as thetich, dg.dongia  as giamua, a.ptThue thuexuat  " +
						"\n from SANPHAM a "+
						"\n	left join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq " +
						"\n inner join NGANHHANG c on a.nganhhang_fk = c.pk_seq " +
						"\n inner join "+
						"\n ( 	select KHO_FK,NPP_FK,SANPHAM_FK,sum(AVAILABLE) as AVAILABLE, KBH_FK "+
						"\n		from NHAPP_KHO_CHITIET "+
						"\n 	where NPP_FK = '"+nppId+"' and ngaynhapkho <= '"+ngaydonhang+"' and kho_fk = '"+khonhapId+"' and KBH_FK = "+ kbhId +
						"\n  	group by KHO_FK,NPP_FK,SANPHAM_FK, KBH_FK "+
						"\n ) d on a.PK_SEQ = d.SANPHAM_FK  "+
						"\n	cross apply ( select [dbo].[GiaDoitacSanpham](a.dvdl_fk,"+kbhId+","+nppId+",a.pk_seq,"+doitacId+",'"+ngaydonhang+"' ) dongia  )dg " + 
						"\n where dg.dongia > 0 and a.trangthai = 1 and d.AVAILABLE > 0   ";
					
						
			System.out.println("command Lay san pham________________: " + command);
			
			response.setHeader("Content-Type", "text/html");
			String query = (String)geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("letters"));
			
			ResultSet sp = db.get(command);
			int j = 0;
			if(sp != null)
			{
				while(sp.next())
				{
					//double quycach = rs.get
					if(sp.getString("ma").toUpperCase().contains(query.toUpperCase()) || sp.getString("ten").toUpperCase().contains(query.toUpperCase()) )
					{
						String tensp = sp.getString("ten");
						//out.print("###" + sp.getString("ma") + " - " + tensp + " [" + sp.getString("donvi") + "] [" + sp.getString("trongluong") + "] [" + sp.getString("thetich") + "] [" + sp.getString("qc") + "] [" + sp.getString("giamua") + "] [" + sp.getString("thuexuat") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
						out.print("###" + sp.getString("ma") + " - " + tensp + " [" + sp.getString("donvi") + "] [" + sp.getString("giamua") + "] [" + sp.getString("thuexuat") + "] [" + sp.getString("soluongton") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
					}	
					
				}	
				sp.close();
			}
			
			db.shutDown();
		}
		catch(Exception ex){ System.out.println("Xay ra exception roi ban..."); }
	}
%>

