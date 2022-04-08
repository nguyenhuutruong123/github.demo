<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Date" %>
<%@ page  import = "geso.dms.distributor.db.sql.dbutils" %>

<% String userId = (String) session.getAttribute("userId"); %>
<% dbutils db = new dbutils(); %>
<%
	String maSP = "";
	String tenSP = "";
	String dvtSP = "";
	String dongiaSP = "";
	ArrayList spList;
	String[] tmp = new String[4];
	Date d = new Date();
	System.out.println(d.toString());
	
//	spList = (ArrayList)session.getAttribute("sp");
	
	//Lay Ma Nha Phan Phoi Tu userId
	String nppId = "";
	ResultSet rs = db.get("select a.pk_seq from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + userId + "'");
	if(rs != null)
	{
		rs.next();
		nppId = rs.getString("pk_seq");
		rs.close();
	}
	try
	{
//		if(spList == null){
			spList = new ArrayList();
			String command = "select DISTINCT a.ma, a.ten, c.giabanlenpp as dongia, isnull(b.donvi, 'Chua xac dinh') as donvi from sanpham a left join donvidoluong b on a.dvdl_fk = b.pk_seq ";
			command = command + "inner join bgbanlenpp_sanpham c on a.pk_seq = c.sanpham_fk ";
			command = command + "inner join nhapp_kho d on d.npp_fk='" + nppId + "' and d.sanpham_fk=a.pk_seq and d.available>0 ";
			command = command + "where c.bgbanlenpp_fk in (select distinct pk_seq from banggiabanlenpp where npp_fk = '" + nppId + "') and c.giabanlenpp > '0' and a.trangthai='1'";
	
   			System.out.println(command);
	
			ResultSet sp = db.get(command);
		
			while(sp.next())
			{
				maSP = maSP + sp.getString("ma") + ",";
				tenSP = tenSP + sp.getString("ten") + ",";
				dvtSP = dvtSP + sp.getString("donvi") + ",";
				dongiaSP = dongiaSP + sp.getString("dongia") + ",";
			}
			sp.close();
		
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
			for(int i=0;i<maspList.length;i++)
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
				//out.print("###" + maspList[i] + " - " + tenspList[i] +" [" + dvtspList[i] + "] ["+ dongiaspList[i] + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
				//System.out.println("###" + maspList[i] + " - " + tenspList[i] +" [" + dvtspList[i] + "] ["+ dongiaspList[i] + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
//				tmp[0] = maspList[i];
//				tmp[1] = tenspList[i];
//				tmp[2] = dvtspList[i];
//				tmp[3] = dongiaspList[i];
//				spList.add(i, tmp);
			}
			
/*		session.setAttribute("sp", spList);
		}else{
			response.setHeader("Content-Type", "text/html");
			String query = (String)geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("letters"));
			d = new Date();
			System.out.println("Bat dau truyen :" + d.toString() + ", size: " + spList.size());
			for(int i=0;i<spList.size();i++)
			{
				
				tmp = (String[])spList.get(i);
				//countries[i] = countries[i].toLowerCase();
				
				if(tmp[0].toUpperCase().startsWith(query.toUpperCase()) ||
					tmp[0].toUpperCase().indexOf(query.toUpperCase()) >= 0 || tmp[1].toUpperCase().indexOf(query.toUpperCase()) >= 0 ||
					tmp[2].toUpperCase().indexOf(query.toUpperCase()) >= 0 )
				{
				if(tmp[1].length() > 50)
					tmp[1] = tmp[1].substring(0, 50);
				}
				out.print("###" + tmp[0] + " - " + tmp[1] +" [" + tmp[2] + "] ["+ tmp[3] + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
				System.out.println("###" + tmp[0] + " - " + tmp[1] +" [" + tmp[2] + "] ["+ tmp[3] + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
				
			}
			d= new Date();
			System.out.println("Da truyen xong: " + d.toString());
		}*/
	}catch(java.sql.SQLException e){}
%>