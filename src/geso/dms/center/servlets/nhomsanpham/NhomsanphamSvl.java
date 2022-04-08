package geso.dms.center.servlets.nhomsanpham;

import geso.dms.center.beans.nhomsanpham.INhomsanpham;
import geso.dms.center.beans.nhomsanpham.imp.Nhomsanpham;
import geso.dms.center.beans.nhomsanpham.INhomsanphamList;
import geso.dms.center.beans.nhomsanpham.imp.NhomsanphamList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NhomsanphamSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	static final long serialVersionUID = 1L;
	PrintWriter out;

	public NhomsanphamSvl() {
		super();
	}   	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		//HttpServletRequest request;
		//  HttpServletResponse response;
		INhomsanphamList obj;
		dbutils db;


		db = new dbutils();

		response.setContentType("text/html");

		HttpSession session = request.getSession();	    

		Utility util = new Utility();
		out = response.getWriter();
		obj = new NhomsanphamList();
		obj.setView( geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view") == null ? "" : request.getParameter("view"))    );
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		out.println(userId);

		if (userId.length()==0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

		String action = util.getAction(querystring);
		out.println(action);

		String nspId = util.getId(querystring);

		if (action.equals("delete")){	   		  	    	
			Delete(nspId);

		}

		// String query = "select a.nsp_parent_fk as parent, a.pk_seq, a.ten, a.diengiai, a.loaithanhvien, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from nhomsanpham a, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ order by nsp_parent_fk ";

		List<INhomsanpham> nsplist = new ArrayList<INhomsanpham>(); 

		getNspBeanList("0", nsplist,userId);

		// Save data into session
		obj.setNspList(nsplist);

		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);

		String nextJSP = "/AHF/pages/Center/NhomSanPham.jsp";
		response.sendRedirect(nextJSP);

	}  	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		dbutils db = null;
		try
		{
			INhomsanphamList obj;


			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();

			Utility util = new Utility();

			HttpSession session = request.getSession();
			String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

			obj = new NhomsanphamList();
			obj.setView( geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view") == null ? "" : request.getParameter("view"))    );
			db = new dbutils();
		

			String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
			if (action == null){
				action = "";
			}
			out.println(action); 

			// Perform searching. Each Nhomsanpham is saved into Nhomsanpham
			if ( !obj.getView().equals("NPP") &&  action.equals("new")){
				// Empty Bean for distributor
				INhomsanpham nspBean = (INhomsanpham) new Nhomsanpham();
				nspBean.setView( geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view") == null ? "" : request.getParameter("view"))    );
				nspBean.UpdateRS();
				// Save Data into session
				session.setAttribute("nspBean", nspBean);
				session.setAttribute("userId", userId);


				String nextJSP = "/AHF/pages/Center/NhomSanPhamNew.jsp";
				response.sendRedirect(nextJSP);

			}else
				if (action.equals("search")){

					String search = getSearchQuery(request,obj);
					//		    	out.println(search);

					List<INhomsanpham> nsplist = getNspListS(search);	    	

					// Saving data into session
					obj.setNspList(nsplist);
					obj.setSearch(true);
					session.setAttribute("obj", obj);

					session.setAttribute("userId", userId);

					response.sendRedirect("/AHF/pages/Center/NhomSanPham.jsp");
				}
				else
					if (action.equals("1")){
						List<INhomsanpham> nsplist = new ArrayList<INhomsanpham>(); 

						getNspBeanList("0", nsplist,userId);

						// 	Save data into session
						obj.setNspList(nsplist);

						session.setAttribute("obj", obj);
						session.setAttribute("userId", userId);

						String nextJSP = "/AHF/pages/Center/NhomSanPham.jsp";
						response.sendRedirect(nextJSP);
					}
		}catch (Exception e) {
			// TODO: handle exception
		}
		finally{
			if(db != null)
				db.shutDown();
		}

	}

	private void Delete(String nspId){

		//HttpServletRequest request;
		//  HttpServletResponse response;
		INhomsanphamList obj;
		dbutils db = new dbutils();

		String query;
		String command;
		query = "select loaithanhvien from nhomsanpham where pk_seq ='" + nspId + "'";
		out.print(query);
		ResultSet rs = db.get(query);
		String ltv = "";
		try{
			rs.next();
			ltv = rs.getString("loaithanhvien");
			rs.close();	
		}catch(Exception e){
			out.println(e.toString());

		}


		if(ltv.equals("1")){
			query = "select pk_seq from nhomsanpham where nsp_parent_fk ='" + nspId + "'";
			rs = db.get(query);
			try{
				while(rs.next()){
					command = "update nhomsanpham set nsp_parent_fk = 0 where pk_seq = '" + rs.getString("pk_seq")+ "'";
					db.update(command);
				}
				rs.close();
			}catch(Exception e){}
		}else{
			command = "delete from nhomsanpham_sanpham where nsp_fk ='" + nspId + "'";
			db.update(command);
		}

		command = "delete from nhomsanpham where pk_seq ='" + nspId + "'";
		db.update(command);

	}


	private void  getNspBeanList(String parent, List<INhomsanpham> nsplist,String userid){	


		//HttpServletRequest request;
		//  HttpServletResponse response;
		INhomsanphamList obj;
		dbutils db = new dbutils();
		Utility util = new Utility();
		String query = "select a.nsp_parent_fk as parent,isnull(a.smartid,'') as ma, a.loainhom, a.pk_seq, a.ten, a.diengiai, a.loaithanhvien, a.trangthai," +
		" a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from nhomsanpham a, nhanvien b," +
		" nhanvien c where a.nguoitao = b.PK_SEQ and " +
		" a.nguoisua = c.PK_SEQ and a.nsp_parent_fk = '" + parent + "' and a.type='0' and a.loainhom = '0' ";
		//query+= " and exists ( select 1 from NHOMSANPHAM_SANPHAM nsp  where nsp.nsp_fk = a.pk_seq and  nsp.sp_fk in "+util.quyen_sanpham(userid)+" )";      
		query+= " order by pk_seq "; 
		System.out.println("1.Khoi tao NSP: " + query);
		ResultSet rs = db.get(query);
		try{	
			String[] param = new String[12];
			INhomsanpham nspBean;
			while (rs.next())
			{	    			
				param[0]= rs.getString("pk_seq");
				param[1]= rs.getString("ten");	
				param[2]= rs.getString("diengiai");
				param[3]= rs.getString("loaithanhvien");
				param[4]= rs.getString("trangthai");
				param[5]= rs.getString("ngaytao");
				param[6]= rs.getString("ngaysua");
				param[7]= rs.getString("nguoitao");
				param[8]= rs.getString("nguoisua");			
				param[9]= rs.getString("parent");
				param[10] = rs.getString("loainhom");
				param[11] = rs.getString("ma");
				nspBean = new Nhomsanpham(param,false);					
				nsplist.add(nspBean);
				getNspBeanList(param[0], nsplist,userid);
			}    		
			if(rs!=null)
			{
				rs.close();
			}

		}
		catch(Exception e){}
	}

	private List<INhomsanpham> getNspListS(String query){  	

		//HttpServletRequest request;
		//  HttpServletResponse response;
		INhomsanphamList obj;
		dbutils db = new dbutils();


		ResultSet rs = db.get(query);
		List<INhomsanpham> nsplist = new ArrayList<INhomsanpham>();			

		INhomsanpham nspBean;
		String[] param = new String[11];
		try{
			while(rs.next()){
				param[0]= rs.getString("pk_seq");
				param[1]= rs.getString("ten");	
				param[2]= rs.getString("diengiai");
				param[3]= rs.getString("loaithanhvien");
				param[4]= rs.getString("trangthai");
				param[5]= rs.getString("ngaytao");
				param[6]= rs.getString("ngaysua");
				param[7]= rs.getString("nguoitao");
				param[8]= rs.getString("nguoisua");			
				param[9]= rs.getString("parent");
				param[10]= rs.getString("loainhom");
				nspBean = new Nhomsanpham(param,false);
				nsplist.add(nspBean);			
			}
			if(rs!=null){
				rs.close();
			}
		}catch(Exception e){}
		return nsplist;
	}

	private String getSearchQuery(ServletRequest request,INhomsanphamList obj){

		//HttpServletRequest request;
		//  HttpServletResponse response;
		/*		   INhomsanphamList obj = new NhomsanphamList();*/
		dbutils db = new dbutils();

		//	    PrintWriter out = response.getWriter();
		Utility util = new Utility();
		String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
		if (diengiai == null)
			diengiai = "";
		obj.setDiengiai(diengiai);
		String thanhvien = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thanhvien")));
		if (thanhvien == null)
			thanhvien = "";    	
		obj.setLoaithanhvien(thanhvien);
		String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
		if (tungay == null)
			tungay = "";    	
		obj.setTungay(tungay);
		String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
		if (denngay == null)
			denngay = "";    	
		obj.setDenngay(denngay);
		String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));   	
		if (trangthai == null)
			trangthai = "";   

		String lnhom = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("lnhom")));   	
		if (lnhom == null)
			lnhom = "";
		obj.setLoainhom(lnhom);

		if (trangthai.equals("2"))
			trangthai = "";
		obj.setTrangthai(trangthai);
		String query = "select a.nsp_parent_fk as parent,isnull(a.smartid,'') as ma,a.loainhom,  a.pk_seq, a.ten, a.diengiai, a.loaithanhvien, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from nhomsanpham a, nhanvien b, nhanvien c where a.type='0' and a.loainhom = '0' and  a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ ";

		if (diengiai.length()>0){
			query = query + " and upper(dbo.ftBoDau(a.diengiai)) like upper('%" + new geso.dms.distributor.util.Utility().replaceAEIOU(diengiai) + "%')";
			//obj.setDiengiai(diengiai);
		}

		if (thanhvien.length()>0){
			query = query + " and a.loaithanhvien ='" + thanhvien + "'";
			//obj.setLoaithanhvien(thanhvien);
		}

		if (tungay.length() > 0) {
			query = query + " and a.ngaytao >= '" + tungay + "'";
			//obj.setTungay(tungay);
		}

		if (denngay.length() > 0) {
			query = query + " and a.ngaytao <= '" + denngay + "'";
			//obj.setDenngay(denngay);
		}

		if(trangthai.length() > 0){
			query = query + " and a.trangthai = '" + trangthai + "'";
			//obj.setTrangthai(trangthai);
		}

		if(lnhom.length() > 0){
			query = query + " and a.loainhom = '"+ lnhom +"'";
		}
		query = query + "  order by nsp_parent_fk ";
		return query;
	}

}