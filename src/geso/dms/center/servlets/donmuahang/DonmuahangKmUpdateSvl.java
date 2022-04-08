package geso.dms.center.servlets.donmuahangkm;

import geso.dms.center.beans.donmuahang.IDonmuahangList;
import geso.dms.center.beans.donmuahang.IERP_DonDatHang;
import geso.dms.center.beans.donmuahang.IERP_DonDatHang_SP;
import geso.dms.center.beans.donmuahang.imp.DonmuahangList;
import geso.dms.center.beans.donmuahang.imp.ERP_DonDatHang;
import geso.dms.center.beans.donmuahang.imp.ERP_DonDatHang_SP;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class DonmuahangKmUpdateSvl
 */
@WebServlet("/DonmuahangKmUpdateSvl")
public class DonmuahangKmUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DonmuahangKmUpdateSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    out = response.getWriter();
	    
	    String querystring = request.getQueryString();
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String ddhId = util.getId(querystring);
	    
	    
	    String userId = util.getUserId(querystring);
	   System.out.println("Action : "+action);
	    if(action.equals("edit")){
	    	 IERP_DonDatHang dhBean=new ERP_DonDatHang(ddhId);
	 	    dhBean.Init();
	 	    session.setAttribute("kenhid", dhBean.getIDKenhBanHang());
	    	session.setAttribute("nhappid",dhBean.getNppId());
	    	session.setAttribute("dvkdid",dhBean.getdvkdid());
	    	session.setAttribute("donhangid", dhBean.getId());
	    	
	 	 String  nextJSP = "/Growth/pages/Center/ERP_DonMuaHangKmUpdate.jsp";
	 	 
	 	 session.setAttribute("obj", dhBean);
	 	response.sendRedirect(nextJSP);
	 	    
	    }
	}
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		int loi=0;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		dbutils db = new dbutils();
		
		Utility util= new  Utility();
		session.setAttribute("util",util);
	    String id = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id"));    
		String userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
		String userTen=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userTen"));
		
		
		session.setAttribute("userId", userId);
		session.setAttribute("userTen", userTen);
		
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));		
 		String tenform=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tenform"));
 		String nextJSP="";
 		if(tenform.equals("newform")){
 			nextJSP = "/Growth/pages/Center/ERP_DonMuaHangKmNew.jsp";
 		}else{
 			nextJSP = "/Growth/pages/Center/Erp_DonMuaHangKmUpdate.jsp";
 		}
		IERP_DonDatHang dhBean=new ERP_DonDatHang();
		dhBean.Init();
		String ngaygiaodich = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaygiaodich")));
		dhBean.setNgaygiaodich(ngaygiaodich);
	    dhBean.setId(id);
	    
    	String ngaytao=this.getDateTime();
		String ngaysua=ngaytao;			
    	dhBean.setNgaytao(ngaytao);
    	dhBean.setNgaysua(ngaysua);
    	dhBean.setNguoitao(userId);
    	dhBean.setNguoisua(userId);
    
    	String loaichietkhau=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaick"));
    	
    	/*String chietkhau=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chietkhau"));*/
    	
    	if(loaichietkhau==null){
    		loaichietkhau="0";
    	}
    	dhBean.setloaichietkhau(loaichietkhau);
    	
    	System.out.println("loai chiet khai :"+loaichietkhau);
    	
    	/*try{
    	dhBean.setChietkhau(Double.parseDouble(chietkhau));
    	}catch(Exception er){
    		dhBean.setChietkhau(0);
    	}*/
    	dhBean.setChietkhau(0);//Tự set chiệc khấu là 0 do là đơn hàng khuyến mãi
    	String vat = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("VAT")));
    	try{
    		dhBean.setVAT(Double.parseDouble(vat));
    	}catch(Exception er){
    		dhBean.setVAT(10);
    	}
    	
    	String nhaccid=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhaccid")));
    	
    	if(nhaccid==null){
    		nhaccid="";
    	}
    	dhBean.setIdNhaCungCap(nhaccid);
    	
    
    	String kenhbanhang=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhbanhang")));
    	if(kenhbanhang==null){
    		kenhbanhang="";
    		
    	}
    	
    	dhBean.setIDKenhBanHang(kenhbanhang);
    	
    	
    	String nhappid = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhappid")));
    	
    	if(nhappid == null){
    		nhappid = "";
    	}
    	
    	dhBean.setNppId(nhappid);
    	System.out.println("Nha Phan Phoi Id : "+nhappid);
    	String tennpp = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tennpp")));
    	dhBean.setNppTen(tennpp);
    	
    	String sql="select khosap from nhaphanphoi where pk_seq=" +nhappid;
    	ResultSet rs=db.get(sql);
    	if(rs!=null){
    		try{
	    		if(rs.next()){
	    			dhBean.setKhottId(rs.getString("khosap"));
	    		}
    		}catch(Exception er){
    			
    		}
    	}
    	
    	
    	
    	String dvkdid=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdid")));
    	if(dvkdid==null){
    		dvkdid="";
    	}
    	//set de lay bang gia.
    	session.setAttribute("kenhid", kenhbanhang);
    	session.setAttribute("nhappid",nhappid);
    	session.setAttribute("dvkdid",dvkdid);
    	session.setAttribute("donhangid",id);
    	dhBean.setdvkdid(dvkdid);
    	
    	List<IERP_DonDatHang_SP>	spList = new ArrayList<IERP_DonDatHang_SP>();
    	//String[] idsp=request.getParameterValues("idsp");
		String[] masp = request.getParameterValues("masp");
		String[] tensp = request.getParameterValues("tensp");
		String[] soluong = request.getParameterValues("soluong");
		String[] donvitinh = request.getParameterValues("donvitinh");
		String[] sheme= request.getParameterValues("sheme");
		//String[] dongia = request.getParameterValues("dongia");
		Hashtable<String, Integer> spThieuList = new Hashtable<String, Integer>();
		
		
		
		if(action.equals("reload")||action.equals("save")||action.equals("update")){
			
			if(masp != null)
			{		
			
			
				int m = 0;
				while(m < masp.length)
				{
				if(masp[m] != "")
				{
					if(soluong[m].length() >  0) //chi them nhung san pham co so luong > 0
					{
						IERP_DonDatHang_SP sanpham = new ERP_DonDatHang_SP();
						 sql="select pk_seq from sanpham where trangthai!='2' and ma='"+masp[m]+"'";
						//System.out.println("Don Gia Nek :"+sql);
						ResultSet rs_getid=db.get(sql);
						try
						{
						if(rs_getid.next()){
							sanpham.setIdSanPham(rs_getid.getString("pk_seq"));
						}
						
						}catch(Exception er){
							sanpham.setIdSanPham("");
						}
						
						sanpham.setMaSanPham(masp[m]);
						sanpham.setTenSanPham(tensp[m]);
						sanpham.setDonViTinh(donvitinh[m]);
						sanpham.setSoLuong(Integer.parseInt(soluong[m]));
						System.out.println("Scheme la xxxxxxx"+sheme[m]);
						sanpham.setSHEME(sheme[m]);
						
					/*	//thuc hien update lai gia khi co truong hop thay doi nha phan phoi,vi moi nha phan phoi co mot bang gia khac nhau
						String sql_getgia="select npp_fk,sanpham_fk,giamuanpp from BangGiaMuaNPP bgmnpp inner join BANGGIAMUANPP_NPP a "+
											" on bgmnpp.pk_Seq=a.bangGiaMuaNpp_fk    inner join BGMUANPP_SANPHAM  "+
											" b on a.bangGiaMuaNpp_fk=b.bgmuanpp_fk where bgmnpp.trangthai='1'"+
											" and  kenh_fk='"+kenhbanhang+"' and dvkd_fk='"+dvkdid+"' and  npp_fk="+nhappid +"  and sanpham_fk="+sanpham.getIdSanPham();
						
						
					  System.out.println("HoaDonNewSvl : line 249 :"+sql_getgia);
						
						ResultSet rs_getgia =db.get(sql_getgia);
						try{
						if(rs_getgia.next()){
							sanpham.setDonGia(rs_getgia.getDouble("giamuanpp"));
						}
						else{
							sanpham.setDonGia(0);
						}
						}catch(Exception er){
							
						}*/
						spList.add(sanpham);
						
						//kiem tra san pham da du ton kho khong
						int sl = 0;
						
						String sql_gettonkho="select available from erp_khott_sanpham where  khott_fk = '"+dhBean.getKhottId()+"'  and sanpham_fk ="+ sanpham.getIdSanPham();
						System.out.println(sql_gettonkho);
						ResultSet slspAvailable = db.get(sql_gettonkho);
						if(slspAvailable != null)
						{
							try
							{
							if(	slspAvailable.next()){
								sl = slspAvailable.getInt("available");
							}else{
							
							}
								slspAvailable.close();
								
								//lay nhung sp da co trong don hang
								if(id != null)
								{
									int slg = 0;
									String sqlquery = "select ISNULL(soluong, 0) as soluong from dondathang_sp where dondathang_fk = '" + id + "' and SANPHAM_FK in (select pk_seq from sanpham where ma='" + masp[m].trim() + "')";
									ResultSet SlgRs = db.get(sqlquery);	
									if(SlgRs != null)
									{
										if(SlgRs.next())
										{
											slg = SlgRs.getInt("soluong");
										}
										SlgRs.close();
									}
									sl = sl+slg;
								}							
							} 
							catch (SQLException e) {
							System.out.println("Khong Lam Gi Duoc : "+e.toString());
								
							}
							
							if(soluong[m] == "")
								soluong[m] = "0";
							//System.out.println(sl);
							sanpham.setSoluongton(sl);
							if(Integer.parseInt(soluong[m]) > sl)
								spThieuList.put(masp[m],sl);
						}
						
					}
				}
				m++;
			}	
		}
		dhBean.setListSanPham(spList);	
		dhBean.setSpThieuList(spThieuList);
    	}
		System.out.println("Da o day  : "+spList.size());
		if(action.equals("save"))
		{
			if(id == null)
			{
				if (!(dhBean.SaveKm()))
				{		
					session.setAttribute("obj", dhBean);
					response.sendRedirect(nextJSP);	 
					
					
				}else
				{
					DonmuahangList obj = new DonmuahangList();
				    obj.setUserId(userId);
				    
					String query1 =" select distinct a.ngaydat, a.pk_seq as chungtu, f.ten as nppTen, e.donvikinhdoanh,  "+
					" a.sotienavat, b.ten as nguoitao, c.ten as nguoisua, a.trangthai,isnull( a.soid ,'0') as soid, isnull (nh.ngaychungtu,'0')as ngayhd, "+
					" isnull(nh.sotienbvat,'0') as tienhd from dondathang a inner join "+ 
					" nhanvien b on  a.nguoitao = b.pk_seq   "+
					" inner join  nhanvien c on a.nguoisua = c.pk_seq  "+
					" inner join dondathang_sp d on a.pk_seq = d.dondathang_fk "+ 
					" inner join donvikinhdoanh e on a.dvkd_fk = e.pk_seq  "+
					" inner join  nhaphanphoi  f  on a.npp_fk = f.pk_seq  "+ 
					" left join nhaphang nh on nh.dathang_fk=a.pk_seq " +
					" where ( a.trangthai <>'0') and  iskm='1'  and  f.pk_seq in "+ util.quyen_npp(userId) ; 
				    obj.createDdhlist(query1);
				    
					session.setAttribute("obj", obj);							
					nextJSP = "/Growth/pages/Center/ERP_DonMuaHangKm.jsp";
					response.sendRedirect(nextJSP);								
				}
			}else{
				String ischot=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ischot"));
				if(!dhBean.Edit(ischot)){
					session.setAttribute("obj", dhBean);
					response.sendRedirect(nextJSP);	 
				}else{
					DonmuahangList obj = new DonmuahangList();
				    obj.setUserId(userId);
				    
					String query1 =" select distinct a.ngaydat, a.pk_seq as chungtu, f.ten as nppTen, e.donvikinhdoanh,  "+
					" a.sotienavat, b.ten as nguoitao, c.ten as nguoisua, a.trangthai,isnull( a.soid ,'0') as soid, isnull (nh.ngaychungtu,'0')as ngayhd, "+
					" isnull(nh.sotienbvat,'0') as tienhd from dondathang a inner join "+ 
					" nhanvien b on  a.nguoitao = b.pk_seq   "+
					" inner join  nhanvien c on a.nguoisua = c.pk_seq  "+
					" inner join dondathang_sp d on a.pk_seq = d.dondathang_fk "+ 
					" inner join donvikinhdoanh e on a.dvkd_fk = e.pk_seq  "+
					" inner join  nhaphanphoi  f  on a.npp_fk = f.pk_seq  "+ 
					" left join nhaphang nh on nh.dathang_fk=a.pk_seq " +
					" where ( a.trangthai <>'0') and  iskm='1'  and  f.pk_seq in "+ util.quyen_npp(userId) ; 
				    obj.createDdhlist(query1);
				    
					session.setAttribute("obj", obj);							
					nextJSP = "/Growth/pages/Center/ERP_DonMuaHangKm.jsp";
					System.out.println("Vao day roi ku");
					response.sendRedirect(nextJSP);	
				}
				
			}
			
		}else if(action.equals("reload")){
			session.setAttribute("obj", dhBean);
			response.sendRedirect(nextJSP);	
			
		}
	}

}
