<%@ page import="geso.dms.center.util.Utility"%>
<%@ page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page import = "java.net.URLDecoder" %>
<%@ page  import = "geso.dms.distributor.db.sql.dbutils" %>

<% dbutils db = new dbutils(); %>
<%
	String maDkkm = "";
	String diengiai = "";
	String tongluong = "";
	String tongtien = "";
	String command;
	String userId = (String) session.getAttribute("userId");
	Utility Ult = new Utility();
	
	response.setHeader("Content-Type", "text/html");
	request.setCharacterEncoding("UTF-8");
	
   	String query = (String)request.getQueryString(); 
   	String view = query;
   	System.out.println("Query String: " + query);
   	
   	query = new String(query.substring(query.indexOf("&letters=") + 9, query.length()).getBytes("UTF-8"), "UTF-8");
   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
   	query = Ult.replaceAEIOU(query);
    
    if(view.indexOf("trakhuyenmai") >= 0)
    {
    	command = "select pk_seq as trakmId, diengiai, cast(isnull(tongluong, 0) as numeric(18, 0)) as tongluong, cast(isnull(tongtien, 0) as numeric(18, 0)) as tongtien, cast(isnull(chietkhau, 0) as numeric(18, 0)) as chietkhau ";
	    command = command + " from trakhuyenmai where nguoitao is not null";
	
	    //System.out.println("Lay TRA KM: " + command);
		
		ResultSet dkkm = db.get(command);
		
		if(dkkm != null)
		{
			int m = 0;
			try
			{
				while(dkkm.next())
				{
					if(dkkm.getString("trakmId") != null)
					{
						maDkkm =  dkkm.getString("trakmId") ;
						diengiai = dkkm.getString("diengiai") ;
						tongluong =  dkkm.getString("tongluong") ;
						tongtien =  dkkm.getString("tongtien") ;
						String chietkhau =  dkkm.getString("chietkhau");
						
						String madkkm = Ult.replaceAEIOU(maDkkm);
						String dg = Ult.replaceAEIOU(diengiai);
					
						if(madkkm.trim().toUpperCase().startsWith(query.trim().toUpperCase()) ||
								madkkm.trim().toUpperCase().indexOf(query.trim().toUpperCase()) >= 0 || dg.toUpperCase().indexOf(query.toUpperCase()) >= 0)
						{
							if(diengiai.length() > 50)
								diengiai = diengiai.substring(0, 50);
							out.print("###" + maDkkm + " - " + diengiai + " [" + tongluong + "] [" + tongtien + "] [" + chietkhau + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
							
							m++;
							if(m > 40)
								break;
						}
					}
				}
				dkkm.close();
			}
			catch(SQLException e){}
		}	
    }
    else
    {
    	if(view.indexOf("sanpham") >= 0)
    	{
			command = "select ma, ten from sanpham where trangthai = '1'";
    		
    		//System.out.println("Lay SanPham: " + command);
    		ResultSet sp = db.get(command);
    		
    		if(sp != null)
    		{
    			try
    			{
    				int m = 0;
    				while(sp.next())
    				{
    					if(sp.getString("ma") != null)
    					{
    						String maSP =  sp.getString("ma");
    						String tenSP = sp.getString("ten");
    						//System.out.println("Sp Ma: " + maSP + " -- Sp Ten: " + tenSP);
    						
    						String masp =  Ult.replaceAEIOU(sp.getString("ma"));
    						String tensp = Ult.replaceAEIOU(sp.getString("ten"));
    						
    						if(masp.trim().toUpperCase().startsWith(query.trim().toUpperCase()) ||
    								masp.trim().toUpperCase().indexOf(query.trim().toUpperCase()) >= 0 || tensp.toUpperCase().indexOf(query.toUpperCase()) >= 0)
    						{
    							if(tenSP.length() > 50)
    								tenSP = tenSP.substring(0, 50);
    							out.print("###" + maSP + " - " + tenSP + "|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
    							
    							m++;
    							if(m > 40)
    								break;
    						}
    					}
    				}
    				sp.close();
    			}
    			catch(SQLException e){}
    		} 
    	}
    	else
    	{
    		command = "select pk_seq as dkkmId, diengiai, cast(isnull(tongluong, 0) as numeric(18, 0)) as tongluong, cast(isnull(tongtien, 0) as numeric(18, 0)) as tongtien ";
    	    command = command + " from dieukienkhuyenmai where nguoitao is not null and pk_seq in (select dieukienkhuyenmai_fk from DIEUKIENKM_SANPHAM where sanpham_fk in " + Ult.quyen_sanpham(userId) + ")";
    	
    	    //System.out.println("Lay DKKM: " + command);
    	    
    		ResultSet dkkm = db.get(command);
    		
    		if(dkkm != null)
    		{
    			int m = 0;
    			try
    			{
    				while(dkkm.next())
    				{
    					if(dkkm.getString("dkkmId") != null)
    					{
    						maDkkm =  dkkm.getString("dkkmId") ;
    						diengiai = dkkm.getString("diengiai") ;
    						tongluong =  dkkm.getString("tongluong") ;
    						tongtien =  dkkm.getString("tongtien") ;
    						
    						String madkkm = Ult.replaceAEIOU(maDkkm);
    						String dg = Ult.replaceAEIOU(diengiai);
    					
    						if(madkkm.trim().toUpperCase().startsWith(query.trim().toUpperCase()) ||
    								madkkm.trim().toUpperCase().indexOf(query.trim().toUpperCase()) >= 0 || dg.toUpperCase().indexOf(query.toUpperCase()) >= 0)
    						{
    							if(diengiai.length() > 50)
    								diengiai = diengiai.substring(0, 50);
    							out.print("###" + maDkkm + " - " + diengiai + " [" +tongluong + "] [" + tongtien + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
    							
    							m++;
    							if(m > 40)
    								break;
    						}
    					}
    				}
    				dkkm.close();
    			}
    			catch(SQLException e){}
    		}	
    	}
    }
    
	db.shutDown();
%>