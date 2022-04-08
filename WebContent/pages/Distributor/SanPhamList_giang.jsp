<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.distributor.db.sql.dbutils" %>
<% //String userId = (String) session.getAttribute("userId"); %>
<% //String khId = (String) session.getAttribute("khId"); %>

<%@ page import = "geso.dms.distributor.beans.donhang.ISanpham" %>
<%@ page import = "geso.dms.distributor.beans.donhang.imp.Sanpham" %>

<% //dbutils db = new dbutils(); %>
<%
	String maSP = "";
	String tenSP = "";
	String dvtSP = "";
	String dongiaSP = "";
	
	//Lay Ma Nha Phan Phoi Tu userId
	/*
	String nppId = "";
	ResultSet rs = db.get("select a.pk_seq from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + userId + "'");
	if(rs != null)
	{
		rs.next();
		nppId = rs.getString("pk_seq");
		rs.close();
	}
	
	
	String bgstId = "";
	ResultSet rsBgst = db.get("select isnull(bgst_fk, -1) as bgst_fk from KHACHHANG where PK_SEQ = '" + khId + "'");
	if(rsBgst != null)
	{
		try
		{
			rsBgst.next();
			if(rsBgst.getString("bgst_fk") != null)
				bgstId = rsBgst.getString("bgst_fk");
			rsBgst.close();
		}
		catch(java.sql.SQLException e){}
	}
	
	String command = "";
	if(bgstId.length() > 0)
	{		
		if(bgstId.equals("-1")) //khong co bgst, phai chon khach hang, lay bang gia chuan npp
		{
			command = "select DISTINCT a.ma, a.ten, c.giabanlenpp as dongia, isnull(b.donvi, 'Chua xac dinh') as donvi from sanpham a left join donvidoluong b on a.dvdl_fk = b.pk_seq ";
			command = command + "inner join bgbanlenpp_sanpham c on a.pk_seq = c.sanpham_fk ";
			command = command + "where c.bgbanlenpp_fk in (select distinct pk_seq from banggiabanlenpp where npp_fk = '" + nppId + "') and c.giabanlenpp > '0'";
		}
		else
			command = "select DISTINCT b.ma, b.ten, a.GIASIEUTHI as dongia, isnull(c.donvi, 'Chua xac dinh') as donvi from BANGGIAST_SANPHAM a inner join SANPHAM b on a.SANPHAM_FK = b.PK_SEQ inner join donvidoluong c on b.DVDL_FK = c.pk_seq where a.BGST_FK = '" + bgstId + "' and a.GIASIEUTHI > '0'";
	}
	//System.out.println("San pham:"+ command);
	ResultSet sp = db.get(command);
	if(sp != null)
	{
		while(sp.next())
		{
			maSP = maSP + sp.getString("ma") + ",";
			tenSP = tenSP + sp.getString("ten") + ",";
			dvtSP = dvtSP + sp.getString("donvi") + ",";
			dongiaSP = dongiaSP + sp.getString("dongia") + ",";
		}

		if(maSP.length() > 0)
		{
			maSP = maSP.substring(0, maSP.length() - 1);
			tenSP = tenSP.substring(0, tenSP.length() - 1);
			dvtSP = dvtSP.substring(0, dvtSP.length() - 1);
			dongiaSP = dongiaSP.substring(0, dongiaSP.length() - 1);
			
			String[] maspList = maSP.split(",");
			String[] tenspList = tenSP.split(",");
			String[] dvtspList = dvtSP.split(",");
			String[] dongiaspList = dongiaSP.split(",");
					
			response.setHeader("Content-Type", "text/html");
			String query = (String)geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("letters"));
			for(int i = 0;i< maspList.length;i++)
			{
				//countries[i] = countries[i].toLowerCase();
				if(maspList[i].toUpperCase().startsWith(query.toUpperCase()) ||
					maspList[i].toUpperCase().indexOf(query.toUpperCase()) >= 0 || tenspList[i].toUpperCase().indexOf(query.toUpperCase()) >= 0 ||
					dvtspList[i].toUpperCase().indexOf(query.toUpperCase()) >= 0 )
				{
					if(tenspList[i].length() > 50)
						tenspList[i] = tenspList[i].substring(0, 50);
					out.print("###" + maspList[i] + " - " + tenspList[i] +" [" + dvtspList[i] + "] ["+ dongiaspList[i] + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
				}
			}
		}	
	}
	*/
	response.setHeader("Content-Type", "text/html");
	String query = (String)geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("letters"));
	
	//ResultSet spList = (ResultSet)session.getAttribute("spDhList");
	
	//if(spList != null)
	//{
		//System.out.print("\nResualset khac null " + Integer.toString(spList.getRow()) + "\n");
		//while(spList.next())
		//{
			//if(spList.getString("ma").toUpperCase().startsWith(query.toUpperCase()) || spList.getString("ma").toUpperCase().indexOf(query.toUpperCase()) >= 0
				//|| spList.getString("ten").toUpperCase().startsWith(query.toUpperCase()) || spList.getString("ten").toUpperCase().indexOf(query.toUpperCase()) >= 0)
			//{
				//out.print("###" + spList.getString("ma") + " - " + spList.getString("ten") +" [" + spList.getString("donvi") + "] ["+ spList.getString("dongia") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
			//}
			//System.out.print(spList.getString("ma") + "\n");
		//}
		//spList.close();
	//}
	//else
	//	System.out.print("\nResualset la null\n");
	
	String bgstId = (String)session.getAttribute("bgstId");
	String[] bgst=null ;
	if(bgstId !=null)
	 bgst = bgstId.split("-");
	
	List<ISanpham> spL = new ArrayList<ISanpham>();
	
	if(bgstId.equals("0")) //khong co bang gia sieu thi
	{
		spL = (List<ISanpham>)session.getAttribute("ListSP");
		for(int i = 0; i < spL.size(); i++)
		{
			Sanpham sp = (Sanpham)spL.get(i);
			if(sp.getMasanpham().toUpperCase().startsWith(query.toUpperCase()) || sp.getMasanpham().toUpperCase().indexOf(query.toUpperCase()) >= 0
				|| sp.getTensanpham().toUpperCase().startsWith(query.toUpperCase()) || sp.getTensanpham().toUpperCase().indexOf(query.toUpperCase()) >= 0)
			{
				out.print("###" + sp.getMasanpham() + " - " + sp.getTensanpham() +" [" + sp.getDonvitinh() + "] ["+ sp.getDongia() + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
			}
		}
	}
	else
	{
		for(int j =0; j< bgst.length ; j++)
		{
			spL = (List<ISanpham>)session.getAttribute("bgst" + bgst[j]);
	
			//System.out.print("\nSize cua list la: " + spL.size() + "\n");
			for(int i = 0; i < spL.size(); i++)
			{
				Sanpham sp = (Sanpham)spL.get(i);
				if(sp.getMasanpham().toUpperCase().startsWith(query.toUpperCase()) || sp.getMasanpham().toUpperCase().indexOf(query.toUpperCase()) >= 0
				|| sp.getTensanpham().toUpperCase().startsWith(query.toUpperCase()) || sp.getTensanpham().toUpperCase().indexOf(query.toUpperCase()) >= 0)
				{
					out.print("###" + sp.getMasanpham() + " - " + sp.getTensanpham() +" [" + sp.getDonvitinh() + "] ["+ sp.getDongia() + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
				}
		}
	}
	}
%>
