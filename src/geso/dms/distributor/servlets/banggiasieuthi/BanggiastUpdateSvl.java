package geso.dms.distributor.servlets.banggiasieuthi;


import geso.dms.distributor.beans.banggiasieuthi.*;
import geso.dms.distributor.beans.banggiasieuthi.imp.*;

import geso.dms.distributor.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BanggiastUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	
	
    public BanggiastUpdateSvl() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
		IBanggiasieuthi bgstBean;
		List<ISanpham> spList;
		PrintWriter out; 
		
		out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	    
	    String id = util.getId(querystring);  	

	    bgstBean = new Banggiasieuthi(id);
        bgstBean.setUserId(userId);
        bgstBean.init();
        
        session.setAttribute("bgstBean", bgstBean);
        String nextJSP = "/AHF/pages/Distributor/BangGiaSieuThiUpdate.jsp";
        response.sendRedirect(nextJSP);
		}
	}

	@SuppressWarnings("null")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
		
		IBanggiasieuthi bgstBean;
		List<ISanpham> spList;
		PrintWriter out; 
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		
		
		out = response.getWriter();
		//Utility ult = new Utility();
	    String id = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id"));	
	    System.out.print("bang:"+ id);
	    if(id == null){  	
	    	bgstBean = new Banggiasieuthi("");
	    }else{
	    	bgstBean = new Banggiasieuthi(id);
	    }
	    	    
		userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
		bgstBean.setUserId(userId);
	    
    	String bgstTen = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("bgstTen"));
		if (bgstTen == null)
			bgstTen = "";				
    	bgstBean.setTenbanggia(bgstTen);
    	
    	String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
		if (nppId == null)
			nppId = "";
		bgstBean.setNppId(nppId);
    	
		String nccId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nccTen"));
		if (nccId == null)
			nccId = "";
		bgstBean.setNccId(nccId);
		
		String dvkdId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdTen"));
		if (dvkdId == null)
			dvkdId = "";
		bgstBean.setDvkdId(dvkdId);
		
		String[] spId = request.getParameterValues("spId");
		String[] spMa = request.getParameterValues("spMa");
		String[] spTen = request.getParameterValues("spTen");
	
		String[] spGiasieuthi = request.getParameterValues("spGiasieuthi");
		
		//Tao List San Pham
		spList = new ArrayList<ISanpham>();
			
		ISanpham sanpham = null;
		String[] param = new String[5];
		int m = 0;
		if(spId != null)
		{
			while(m < spId.length)
			{
				
				if(spGiasieuthi == null)
					spGiasieuthi[m] = "";
				param[0] = spId[m];
				param[1] = spMa[m];
				param[2] = spTen[m];
				
				param[4] = spGiasieuthi[m].replaceAll(",","");
				
				sanpham = new Sanpham(param);
				spList.add(sanpham);							
				m++;
			}	
		}	
		String ngaysua = getDateTime();
    	bgstBean.setNgaysua(ngaysua);
		
		boolean error = false;
				
		if (bgstTen.trim().length()== 0){
			bgstBean.setMessage("Vui Long Nhap Ten Bang Gia");
			error = true;
		}

		if (nppId.trim().length()== 0){
			bgstBean.setMessage("Vui Long Chon Nha Phan Phoi");
			error = true;
		}
		
 		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    
		if(action.equals("save"))
		{
			if ( id == null){
				if (!(bgstBean.CreateBgst(spId, spGiasieuthi))){	
					bgstBean.createRS();
					if(spList.size() > 0 )
						bgstBean.setSpList(spList);
					
					session.setAttribute("bgstBean", bgstBean);			
					String nextJSP = "/AHF/pages/Distributor/BangGiaSieuThiNew.jsp";
					response.sendRedirect(nextJSP);
				}else{
					IBanggiasieuthiList obj = new BanggiasieuthiList();
					obj.setUserId(userId);
					obj.init("");
					session.setAttribute("obj", obj);
					//initSanPhamSearch(request,obj.getNppId());//them session load lai bang gia
					String nextJSP = "/AHF/pages/Distributor/BangGiaSieuThi.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
				
			}else{
				if (!(bgstBean.UpdateBgst(spId, spGiasieuthi)))
				{
					bgstBean.init();
					if(spList.size() > 0 )
						bgstBean.setSpList(spList);
					
					session.setAttribute("bgstBean", bgstBean);
					String nextJSP = "/AHF/pages/Distributor/BangGiaSieuThiUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else{
					IBanggiasieuthiList obj = new BanggiasieuthiList();
					obj.setUserId(userId);
					obj.init("");
					session.setAttribute("obj", obj);
				//	initSanPhamSearch(request,obj.getNppId());//them session load lai bang gia
					String nextJSP = "/AHF/pages/Distributor/BangGiaSieuThi.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
			}
		}else
		{
			bgstBean.createRS();
			session.setAttribute("bgstBean", bgstBean);
			
			String nextJSP;
			if (id == null){			
				nextJSP = "/AHF/pages/Distributor/BangGiaSieuThiNew.jsp";
			}else{
				nextJSP = "/AHF/pages/Distributor/BangGiaSieuThiUpdate.jsp";   						
			}
			response.sendRedirect(nextJSP);		
		}
		}
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
/*	public void initSanPhamSearch(HttpServletRequest request,String npp)
    {
		dbutils db = new dbutils();
		HttpSession session = request.getSession();

		String query = "";
		query = "select DISTINCT a.ma, a.ten, c.giabanlenpp as dongia, isnull(b.donvi, 'Chua xac dinh') as donvi from sanpham a left join donvidoluong b on a.dvdl_fk = b.pk_seq ";
		query = query + "inner join bgbanlenpp_sanpham c on a.pk_seq = c.sanpham_fk inner join nhapp_kho d on d.npp_fk= '"+ npp +"' and d.sanpham_fk=a.pk_seq and d.available > 0 ";
		query = query + "where c.bgbanlenpp_fk in (select distinct pk_seq from banggiabanlenpp where npp_fk = '"+ npp +"' ) and c.giabanlenpp > '0' and a.trangthai='1' ";
		
		ResultSet spList = db.get(query);
		
		String list = "";
		//List<ISanpham> list = new ArrayList<ISanpham>();
		if(spList != null)
		{		
			//ISanpham sanpham = null;
			String[] param = new String[8];

			try 
			{
				while(spList.next())
				{
					//dung ky tu dac biet, tranh truong hop trong ten sp co nhung ky tu do
					list += spList.getString("ma") + "&&" + spList.getString("ten") + "&&" + spList.getString("donvi") + "&&" + spList.getString("dongia") + "->";
				}
				spList.close();
			} 
			catch(Exception e) {}	
		}
		if(list.length() > 0)
		{
			list = list.substring(0, list.length() - 2);
			session.setAttribute("ListSP", list);
		}
			session.setAttribute("bgstId", "0");
		
		//neu nhapp co kenh sieu thi
		query = "select pk_seq from banggiasieuthi where npp_fk = '"+ npp +"'";
		
		//System.out.println("Kenh sieu thi la: " + query + "\n");
		
		ResultSet rsBgst = db.get(query);
		//List<ISanpham> listBgst = null;
		
		String listBgst = "";
		if(rsBgst != null)
		{
			try 
			{
				while(rsBgst.next())
				{
					//listBgst = new ArrayList<ISanpham>();
					//Khoi gan lai bgst moi
					listBgst = "";
					
					query = "select DISTINCT b.ma, b.ten, a.GIASIEUTHI as dongia, isnull(c.donvi, 'Chua xac dinh') as donvi from BANGGIAST_SANPHAM a inner join SANPHAM b on a.SANPHAM_FK = b.PK_SEQ left join donvidoluong c on b.DVDL_FK = c.pk_seq inner join nhapp_kho d on d.npp_fk= '"+ npp +"' and d.sanpham_fk=b.pk_seq and d.available > 0 ";
					query += " where a.BGST_FK = '" + rsBgst.getString("pk_seq") + "' and a.GIASIEUTHI > '0' and b.trangthai = '1'";
					
					//System.out.print("\nQuery cua ban La: " + query + "\n");
					
					ResultSet rsSt = db.get(query);
					if(rsSt != null)
					{		
						//ISanpham sanpham = null;
						//String[] param = new String[8];
						try 
						{
							while(rsSt.next())
							{
								listBgst += rsSt.getString("ma") + "&&" + rsSt.getString("ten") + "&&" + rsSt.getString("donvi") + "&&" + rsSt.getString("dongia") + "->";
							}
							rsSt.close();
						} 
						catch(Exception e) {}	
					}
					//System.out.println("List san pham la: " + listBgst + "\n");
					if(listBgst.length() > 0)
					{
						listBgst = listBgst.substring(0, listBgst.length() - 2);
						session.setAttribute("bgst" + rsBgst.getString("pk_seq"), listBgst);
					}
				}
				rsBgst.close();
			} 
			catch(Exception e) {}
		}
   }
   */
}
