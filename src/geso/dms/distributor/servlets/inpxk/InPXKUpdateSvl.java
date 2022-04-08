package geso.dms.distributor.servlets.inpxk;

import geso.dms.distributor.beans.donhang.ISanpham;
import geso.dms.distributor.beans.donhang.imp.Sanpham;
import geso.dms.distributor.beans.phieuxuatkho.*;
import geso.dms.distributor.beans.phieuxuatkho.imp.*;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import org.apache.poi.xssf.usermodel.examples.CreateCell;

public class InPXKUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
    public InPXKUpdateSvl() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		
		IPhieuxuatkho pxkBean;
		PrintWriter out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);  	

	    pxkBean = new Phieuxuatkho(id);
	    pxkBean.setUserId(userId);
        
          
        String nextJSP = "";
        if(querystring.indexOf("display") > 0)
        {
        	pxkBean.init3();
        	nextJSP = "/AHF/pages/Distributor/PhieuXuatKhoDisplay.jsp";
        }
        else
        {
        	pxkBean.init();
        	nextJSP = "/AHF/pages/Distributor/PhieuXuatKhoUpdate.jsp";
        }
        
        session.setAttribute("pxkBean", pxkBean);
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		IPhieuxuatkho pxkBean;
		
		
	    String id = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id"));	
	    if(id == null){  	
	    	pxkBean = new Phieuxuatkho("");
	    }else{
	    	pxkBean = new Phieuxuatkho(id);
	    }
	    	    
		String userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
		pxkBean.setUserId(userId);
	        	
    	String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
		if (nppId == null)
			nppId = "";
		pxkBean.setNppId(nppId);
    	
		String ngaylap = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaylap"));
		if (ngaylap == null || ngaylap.length() < 10)
			ngaylap = getDateTime();
		pxkBean.setNgaylap(ngaylap);
		
		String nvbhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nvbhTen"));
		if (nvbhId == null)
			nvbhId = "";
		pxkBean.setNvbhId(nvbhId);
		
		String nvgnId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nvgnTen"));
		if (nvgnId == null)
			nvgnId = "";
		pxkBean.setNvgnId(nvgnId);
		
		String[] donhangIds = null;
		if(request.getParameterValues("donhangList") != null)
		{
			donhangIds = request.getParameterValues("donhangList");
			pxkBean.setDonhangIds(donhangIds);
		}
		
		String ngaysua = getDateTime();
    	pxkBean.setNgaysua(ngaysua);

 		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
 		System.out.println("--------"+action);
	    
		if(action.equals("save"))
		{						
			if (id == null)
			{
				if (!(pxkBean.CreatePxk()))
				{	
					pxkBean.createRS();
					session.setAttribute("pxkBean", pxkBean);			
					String nextJSP = "/AHF/pages/Distributor/PhieuXuatKhoNew.jsp";
					response.sendRedirect(nextJSP);
				}
				else{
					
					IPhieuxuatkhoList obj = new PhieuxuatkhoList();
					obj.setUserId(userId);
					
					obj.init("");
					session.setAttribute("obj", obj);
						
					String nextJSP = "/AHF/pages/Distributor/PhieuXuatKho.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}				
			}		
		}
		else
		{
			if(action.equals("print"))
			{
					
				if(!pxkBean.UpdatePxk())
				{
					pxkBean.init();
					session.setAttribute("pxkBean", pxkBean);
					String nextJSP = "/AHF/pages/Distributor/PhieuXuatKhoUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else  //den trang in file Pdf
				{
					pxkBean.setId(id);
					pxkBean.init3();
			
					String nvgn_Ten = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nvgn_Ten"));
					if(nvgn_Ten == null)
						nvgn_Ten = "";
					pxkBean.setNvgnTen(nvgn_Ten);
					
					String nppTen = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppTen"));
					if(nppTen == null)
						nppTen = "";
					pxkBean.setNppTen(nppTen);
					
					session.setAttribute("pxkBean", pxkBean);
					String nextJSP = "PhieuxuatkhoPdfSvl";
					response.sendRedirect(nextJSP);
					
				}
			}
			else
			{
				if(action.equals("update"))
				{									
					if(!pxkBean.UpdatePxk())
					{
						pxkBean.init();
						session.setAttribute("pxkBean", pxkBean);						
						String nextJSP = "/AHF/pages/Distributor/PhieuXuatKhoUpdate.jsp";
						response.sendRedirect(nextJSP);
					}
					else
					{																														
						String nextJSP;																
						IPhieuxuatkhoList obj = new PhieuxuatkhoList();
						obj.setUserId(userId);						
						obj.init("");						
						session.setAttribute("obj", obj);
						nextJSP = "/AHF/pages/Distributor/PhieuXuatKho.jsp";   												
						response.sendRedirect(nextJSP);
					}
					
				}else
				{
					pxkBean.createRS();
					session.setAttribute("pxkBean", pxkBean);
					
					String nextJSP;
					if (id == null)
					{			
						nextJSP = "/AHF/pages/Distributor/PhieuXuatKhoNew.jsp";
					}
					else
					{
						nextJSP = "/AHF/pages/Distributor/PhieuXuatKhoUpdate.jsp";   						
					}
					response.sendRedirect(nextJSP);
				}
			}			
		}
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private List<ISanpham> createSanpham(String[] donhangIds, dbutils db)
	{
		//lay List San pham tu donhang
		List<ISanpham> sanphamList = new ArrayList<ISanpham>();
		String dhs = "";
		for(int i = 0; i < donhangIds.length; i++)
		{
			dhs += donhangIds[i] + ",";
		}
		
		if(dhs.length() > 0)
		{
			dhs = dhs.substring(0, dhs.length() - 1); //cat dau , cuoi cung
			
			String query = "select c.kho_fk as khoId, c.kbh_fk as kbhId, b.pk_seq as spId, b.ma as spMa, b.ten as spTen, sum(a.soluong) as soluong from donhang_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq inner join donhang c on a.donhang_fk = c.pk_seq ";
			query += "where a.donhang_fk in (" + dhs + ") group by c.kho_fk, c.kbh_fk, b.pk_seq, b.ma, b.ten";
			ResultSet sanphamRS = db.get(query);
			if(sanphamRS != null)
			{
				String[] param = new String[8];
				ISanpham sp = null;	
				try 
				{
					while(sanphamRS.next())
					{
						param[0] = sanphamRS.getString("spId");
						param[1] = sanphamRS.getString("spma");
						param[2] = sanphamRS.getString("spten");
						param[3] = sanphamRS.getString("soluong");
						
						//luu kho
						param[4] = "";
						if(sanphamRS.getString("khoId") != null)
							param[4] = sanphamRS.getString("khoId");
						
						//luu kenh ban hang
						param[5] = "";
						if(sanphamRS.getString("kbhId") != null)
							param[5] = sanphamRS.getString("kbhId");
						
						param[6] = "";
						param[7] = "";
						
						sp = new Sanpham(param);
						sanphamList.add(sp);
					}
					sanphamRS.close();
				} 
				catch(Exception e) {}
			}			
		}
		
		//cong don san pham trung ma (su dung group by)
		/*
		for(int i=0; i < sanphamList.size() - 1; i++)
		{
			Sanpham spA  = (Sanpham)sanphamList.get(i);
			for(int j = i+1; j < sanphamList.size(); j++)
			{				
				Sanpham spB = (Sanpham)sanphamList.get(j);
				if(spA.getMasanpham().trim().equals(spB.getMasanpham().trim()))
				{
					int slg = Integer.parseInt(spA.getSoluong()) + Integer.parseInt(spB.getSoluong());
					sanphamList.get(i).setSoluong(Integer.toString(slg));
					sanphamList.remove(j);
					i = -1;
					break;
				}
			}
		}
		*/
		return sanphamList;
	}
	
	private List<ISanpham> createSpKmList(String[] donhangIds, dbutils db)
	{
		//lay List San pham khuyen mai tu donhang
		List<ISanpham> sanphamKMList = new ArrayList<ISanpham>();
		String dhs = "";
		for(int i = 0; i < donhangIds.length; i++)
		{
			dhs += donhangIds[i] + ",";
		}
		if(dhs.length() > 0)
		{
			dhs = dhs.substring(0, dhs.length() - 1); //cat dau , cuoi cung
			
			String query = "select b.kho_fk as khoId, e.kbh_fk as kbhId, a.ctkmId, a.spMa, d.ten as spten, d.pk_seq as spId, sum(a.soluong) as soluong from donhang_ctkm_trakm a inner join ctkhuyenmai b on a.ctkmid = b.pk_seq inner join sanpham d on a.spMa = d.ma inner join donhang e on a.donhangId = e.pk_seq "; 
			query += "where a.spMa is not null and a.donhangId in (" + dhs + ") group by b.kho_fk, e.kbh_fk, a.ctkmId, a.spMa, d.ten, d.pk_seq";
			ResultSet spKhuyenmaiRS = db.get(query);

			if(spKhuyenmaiRS != null)
			{
				try 
				{
					while(spKhuyenmaiRS.next())
					{
						String[] param = new String[8];
						ISanpham sp = null;	
														
						param[0] = spKhuyenmaiRS.getString("spId");
						param[1] = spKhuyenmaiRS.getString("spma");
						param[2] = spKhuyenmaiRS.getString("spTen");
						param[3] = spKhuyenmaiRS.getString("soluong");
						
						//luu kho
						param[4] = "";
						if(spKhuyenmaiRS.getString("khoId") != null)
							param[4] = spKhuyenmaiRS.getString("khoId");
						
						//luu kenh ban hang
						param[5] = "";
						if(spKhuyenmaiRS.getString("kbhId") != null)
							param[5] = spKhuyenmaiRS.getString("kbhId");
						
						//luu ctkm
						param[6] = "";
						if(spKhuyenmaiRS.getString("ctkmId") != null)
							param[6] = spKhuyenmaiRS.getString("ctkmId");
						
						param[7] = "";
						
						sp = new Sanpham(param);
						sanphamKMList.add(sp);
					}
					spKhuyenmaiRS.close();
				}
				catch(Exception e) {}
			}
		}
		
		//cong don sanpham trung ma (group by o cau lenh SQL roi, nen ko can dung cai nay)
		/*
		for(int i=0; i < sanphamKMList.size() - 1; i++)
		{
			Sanpham spA  = (Sanpham)sanphamKMList.get(i);
			for(int j = i+1; j < sanphamKMList.size(); j++)
			{				
				Sanpham spB = (Sanpham)sanphamKMList.get(j);
				if(spA.getMasanpham().trim().equals(spB.getMasanpham().trim()))
				{
					int slg = Integer.parseInt(spA.getSoluong()) + Integer.parseInt(spB.getSoluong());
					sanphamKMList.get(i).setSoluong(Integer.toString(slg));
					sanphamKMList.remove(j);
					i = -1;
					break;
				}
			}
		}
		*/
		
		return sanphamKMList;
		
	}

	private List<ISanpham> createTienKmList(String[] donhangIds, dbutils db)
	{
		//lay List San pham khuyen mai tu donhang
		List<ISanpham> sanphamKMList = new ArrayList<ISanpham>();
		String dhs = "";
		for(int i = 0; i < donhangIds.length; i++)
		{
			dhs += donhangIds[i] + ",";
		}
		if(dhs.length() > 0)
		{
			dhs = dhs.substring(0, dhs.length() - 1); //cat dau , cuoi cung
			
			String query = "select ctkmID, sum(tonggiatri) as tonggiatri from donhang_ctkm_trakm "; 
			query += "where spMa is null and donhangId in (" + dhs + ") group by ctkmID";
			ResultSet spKhuyenmaiRS = db.get(query);

			if(spKhuyenmaiRS != null)
			{
				try 
				{
					while(spKhuyenmaiRS.next())
					{
						String[] param = new String[8];
						ISanpham sp = null;	
														
						param[0] = "";
						param[1] = "";
						param[2] = "";
						param[3] = "";
						param[4] = "";
						param[5] = "";
						
						//luu scheme
						param[6] = "";
						if(spKhuyenmaiRS.getString("ctkmId") != null)
							param[6] = spKhuyenmaiRS.getString("ctkmId");
						
						//luu tong gia tri
						param[7] = "";
						if(spKhuyenmaiRS.getString("tonggiatri") != null)
							param[7] = spKhuyenmaiRS.getString("tonggiatri");

						sp = new Sanpham(param);
						sanphamKMList.add(sp);
					}
					spKhuyenmaiRS.close();
				}
				catch(Exception e) {}
			}
		}
		
		return sanphamKMList;	
	}
	
	//dung khi xuat PDF chay cho nhanh
	private List<ISanpham> createSanpham2(String[] donhangIds, dbutils db)
	{
		//lay List San pham tu donhang
		List<ISanpham> sanphamList = new ArrayList<ISanpham>();
		String dhs = "";
		for(int i = 0; i < donhangIds.length; i++)
		{
			dhs += donhangIds[i] + ",";
		}
		
		if(dhs.length() > 0)
		{
			dhs = dhs.substring(0, dhs.length() - 1); //cat dau , cuoi cung
			
			String query = "select d.ten as khoTen, e.ten as kbhTen, b.pk_seq as spId, b.ma as spMa, b.ten as spTen, sum(a.soluong) as soluong from donhang_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq inner join donhang c on a.donhang_fk = c.pk_seq left join kho d on c.kho_fk = d.pk_seq left join kenhbanhang e on c.kbh_fk = e.pk_seq ";
			query += "where a.donhang_fk in (" + dhs + ") group by d.ten, e.ten, b.pk_seq, b.ma, b.ten";
			
			ResultSet sanphamRS = db.get(query);
			if(sanphamRS != null)
			{
				String[] param = new String[8];
				ISanpham sp = null;	
				try 
				{
					while(sanphamRS.next())
					{
						param[0] = sanphamRS.getString("spId");
						param[1] = sanphamRS.getString("spma");
						param[2] = sanphamRS.getString("spten");
						param[3] = sanphamRS.getString("soluong");
						
						//luu kho
						param[4] = "";
						if(sanphamRS.getString("khoTen") != null)
							param[4] = sanphamRS.getString("khoTen");
						
						//luu kenh ban hang
						param[5] = "";
						if(sanphamRS.getString("kbhTen") != null)
							param[5] = sanphamRS.getString("kbhTen");
						
						param[6] = "";
						param[7] = "";
						
						sp = new Sanpham(param);
						sanphamList.add(sp);
					}
					sanphamRS.close();
				} 
				catch(Exception e) {}
			}			
		}
		return sanphamList;
	}
	
	private List<ISanpham> createSpKmList2(String[] donhangIds, dbutils db)
	{
		//lay List San pham khuyen mai tu donhang
		List<ISanpham> sanphamKMList = new ArrayList<ISanpham>();
		String dhs = "";
		for(int i = 0; i < donhangIds.length; i++)
		{
			dhs += donhangIds[i] + ",";
		}
		
		if(dhs.length() > 0)
		{
			dhs = dhs.substring(0, dhs.length() - 1);
			
			String query = "select f.ten as khoTen, g.ten as kbhTen, h.scheme, a.spMa, d.ten as spTen, d.pk_seq as spId, sum(a.soluong) as soluong from donhang_ctkm_trakm a inner join ctkhuyenmai b on a.ctkmid = b.pk_seq inner join sanpham d on a.spMa = d.ma ";
			query += "inner join donhang e on a.donhangId = e.pk_seq left join kho f on b.kho_fk = f.pk_seq left join kenhbanhang g on e.kbh_fk = g.pk_seq inner join ctkhuyenmai h on a.ctkmId = h.pk_seq ";
			query += "where a.spMa is not null and a.donhangId in (" + dhs + ") group by f.ten, g.ten, h.scheme, a.spMa, d.ten, d.pk_seq";
			
			ResultSet spKhuyenmaiRS = db.get(query);
			if(spKhuyenmaiRS != null)
			{
				try 
				{
					while(spKhuyenmaiRS.next())
					{
						String[] param = new String[8];
						ISanpham sp = null;	
														
						param[0] = spKhuyenmaiRS.getString("spId");					
						param[1] = spKhuyenmaiRS.getString("spMa");		
						param[2] = spKhuyenmaiRS.getString("spTen");
						param[3] = spKhuyenmaiRS.getString("soluong");
						
						//luu kho
						param[4] = "";
						if(spKhuyenmaiRS.getString("khoTen") != null)
							param[4] = spKhuyenmaiRS.getString("khoTen");
						
						//luu kenh ban hang
						param[5] = "";
						if(spKhuyenmaiRS.getString("kbhTen") != null)
							param[5] = spKhuyenmaiRS.getString("kbhTen");

						//luu ten ctkm
						param[6] = spKhuyenmaiRS.getString("scheme");
						
						param[7] = "";
						
						sp = new Sanpham(param);
						sanphamKMList.add(sp);
					}
					spKhuyenmaiRS.close();
				} 
				catch(Exception e) { }
			}
			
			//nhung chuong trinh khuyen mai tra tien
			query = "select b.scheme, sum(a.tonggiatri) as tongtien from donhang_ctkm_trakm a inner join ctkhuyenmai b on a.ctkmId = b.pk_seq ";
			query += "where a.spMa is null and a.donhangId in (" + dhs + ") group by b.scheme";
			
			ResultSet rsTien = db.get(query);
			if(rsTien != null)
			{
				try 
				{
					if(rsTien.getRow() > 0)
					{
						while(rsTien.next())
						{
							String[] param = new String[8];
							ISanpham sp = null;	
															
							param[0] = "";
							param[1] = "";
							param[2] = "";
							//khi ko co so luong, thi luu so tien
							param[3] = rsTien.getString("tongtien");
							param[4] = "";
							param[5] = "";
							
							//luu ten ctkm
							param[6] = rsTien.getString("scheme");
							
							//luu tong tien
							param[7] = "";
							
							sp = new Sanpham(param);
							sanphamKMList.add(sp);
						}
						rsTien.close();
					}
				}
				catch(Exception e) {}
			}
		}
		
		return sanphamKMList;
		
	}
	
	
}
