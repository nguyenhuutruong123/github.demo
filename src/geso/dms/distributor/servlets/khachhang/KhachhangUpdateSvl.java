package geso.dms.distributor.servlets.khachhang;

import geso.dms.distributor.beans.khachhang.*;
import geso.dms.distributor.beans.khachhang.imp.*;
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
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class KhachhangUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	
    public KhachhangUpdateSvl() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		
		if ( Utility.CheckSessionUser( session,  request, response)) {
			Utility.RedireactLogin(session, request, response);
		}
		
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}
		 else {
		IKhachhang khBean;
		PrintWriter out; 
						
		out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = Utility.antiSQLInspection(request.getParameter("userId"));
	    
	    String id = util.getId(querystring);  	
	    String action= util.getAction(querystring);
	    khBean = new Khachhang(id);
	    String view = Utility.antiSQLInspection(request.getParameter("view"));
	    if (view == null)
	    	view = "";
	    System.out.println("View: "+view);
	    String param ="";
	    if (view.trim().length() > 0) param ="&view="+view;
	    
	    int quyen =  action.equals("display") ? Utility.XEM : Utility.SUA;
	    
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		else if ( Utility.CheckRuleUser( session,  request, response, "KhachhangSvl", param, quyen ))
		{
			Utility.RedireactLogin(session, request, response);
		}
		 else {
			 khBean.setView(view);
		     khBean.setUserId(userId);
		     khBean.init();
		     session.setAttribute("khBean", khBean);
		     String nextJSP = "/AHF/pages/Distributor/KhachHangUpdate.jsp";
		     if(action.equals("display"))
		        	nextJSP = "/AHF/pages/Distributor/KhachHangDisplay.jsp";
		     response.sendRedirect(nextJSP);
		} 
		}
        
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		
		if ( Utility.CheckSessionUser( session,  request, response)) {
			Utility.RedireactLogin(session, request, response);
		}
		
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		} else {
			IKhachhang khBean;
			PrintWriter out; 

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			
			out = response.getWriter();
			Utility util = new Utility();
			String id = Utility.antiSQLInspection(request.getParameter("id"));
			if (id == null){  	
				khBean = new Khachhang("");
			} else {
				khBean = new Khachhang(id);
			}
			
			String coderoute = Utility.antiSQLInspection(request.getParameter("coderoute"));
			if (coderoute == null)
				coderoute = "";
			khBean.setCoderoute(coderoute);
			
			String routename = Utility.antiSQLInspection(request.getParameter("routename"));
			if (routename == null)
				routename = "";
			khBean.setRoutename(routename);

			String view = Utility.antiSQLInspection(request.getParameter("view"));
			if (view == null)
				view = "";
			khBean.setView(view);

			userId = Utility.antiSQLInspection(request.getParameter("userId"));
			khBean.setUserId(userId);

			String mathamchieu = Utility.antiSQLInspection(request.getParameter("mathamchieu"));
			if (mathamchieu == null)
				mathamchieu = "";				
			khBean.setMathamchieu(mathamchieu);

			String khTen = Utility.antiSQLInspection(request.getParameter("khTen"));
			if (khTen == null)
				khTen = "";				
			khBean.setTen(khTen);

			String phuongxa = Utility.antiSQLInspection(request.getParameter("phuongxa"));
			if (phuongxa == null)
				phuongxa = "";				
			khBean.setPhuongxa(phuongxa);
			//System.out.println("Phuongxa: "+phuongxa);
			
			String nppId = Utility.antiSQLInspection(request.getParameter("nppId"));
			if (nppId == null)
				nppId = "";
			khBean.setNppId(nppId);

			String trangthai = Utility.antiSQLInspection(request.getParameter("trangthai"));
			if (trangthai == null)
				trangthai = "0";
			else
				trangthai = "1";
			khBean.setTrangthai(trangthai);


			String sudungck = Utility.antiSQLInspection(request.getParameter("sudungck"));
			if (sudungck == null)
				sudungck = "0";
			else
				sudungck = "1";
			khBean.setSudungckTT(sudungck);
			System.out.println("sudungck "+sudungck);
			String diachi = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diachi"));
			if (diachi == null)
				diachi = "";
			khBean.setDiachi(diachi);
			String dtch = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dtch"));
			if (dtch == null)
				dtch = "";
			khBean.setDientichch(dtch);
			String tpId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tpId"));
			if (tpId == null)
				tpId = "";
			khBean.setTpId(tpId);		
			String songayno = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("songayno"));
			if (songayno == null)
				songayno = "";
			khBean.setSongayno(songayno);
			String sotienno = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sotienno"));
			if (sotienno == null)
				sotienno = "";
			khBean.setSotienno(sotienno);
			String qhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("qhId"));
			if (qhId == null)
				qhId = "";
			khBean.setQhId(qhId);

			//		String ghcnId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ghcnTen"));
			//		if (ghcnId.length()==0)
			//			ghcnId = "";
			//		khBean.setGhcnId(ghcnId);

			String mckId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("mckTen"));
			if (mckId == null)
				mckId = "";
			khBean.setMckId(mckId);
			////////////////

			String nguoidaidien = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nguoidaidien"));
			if (nguoidaidien == null)
				nguoidaidien = "";
			khBean.setNguoidaidien(nguoidaidien);
			//System.out.println("chietkhau :" + mckId);
			String dienthoai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dienthoai"));
			if (dienthoai == null)
				dienthoai = "";
			khBean.setSodienthoai(dienthoai);

			String masothue = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("masothue"));
			if (masothue == null)
				masothue = "";
			khBean.setMasothue(masothue);

			String kbhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhTen"));
			if (kbhId == null)
				kbhId = "";
			khBean.setKbhId(kbhId);

			String nvgnId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nvgnTen"));
			if (nvgnId == null)
				nvgnId = "";
			khBean.setNvgnId(nvgnId);

			String lchId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("lchTen"));
			if (lchId == null)
				lchId = "";
			khBean.setLchId(lchId);

			String hchId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("hchTen"));
			if (hchId == null)
				hchId = "";
			khBean.setHchId(hchId);

			String vtchId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vtchTen"));
			if (vtchId == null)
				vtchId = "";
			khBean.setVtId(vtchId);

			String chonchietkhau = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chonchietkhau"));
			if (chonchietkhau == null)
				chonchietkhau = "";
			khBean.setChonChietKhau(chonchietkhau);
			/*Cường_31/07/2019*/
			String smartid = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("maddt"));
			if (smartid == null)
				smartid = "";
			khBean.setsmartid(smartid);
			System.out.println("chọn chiết khấu " + chonchietkhau);
			//		if (chonchietkhau.equals("1"))
			//		{
			System.out.println("AAAAAAAAAAAAAA");
			List<IErpKhachHang_SPCK> spListCK = new ArrayList<IErpKhachHang_SPCK>();
			String[] idspCK = request.getParameterValues("idspCK");
			String[] maspCK = request.getParameterValues("maspCK");
			String[] tenspCK = request.getParameterValues("tenspCK");
			String[] dvtCK = request.getParameterValues("donvitinhCK");
			String[] ptspCK = request.getParameterValues("ptspCK");

			System.out.println("maspCK");
			if (maspCK != null)
			{
				int m = 0;
				while (m < maspCK.length)
				{

					System.out.println(maspCK[m]);
					if (maspCK[m].trim().length() > 0)
					{
						//						if (ptspCK[m].trim().length() > 0)
						//						{
						IErpKhachHang_SPCK sanphamCK = new ErpKhachHang_SPCK();

						sanphamCK.setIdSanPham(idspCK[m].trim());							
						sanphamCK.setMaSanPham(maspCK[m].trim());
						sanphamCK.setTenSanPham(tenspCK[m].trim());
						sanphamCK.setDonViTinh(dvtCK[m].trim());   
						sanphamCK.setPTChietKhau(ptspCK[m].trim());
						spListCK.add(sanphamCK);
						System.out.println(" # "+sanphamCK.getMaSanPham()+" # "+sanphamCK.getTenSanPham()+" # "+sanphamCK.getDonViTinh()+" # "+sanphamCK.getPTChietKhau());
						//						}
					}
					m++;
				}
				khBean.setListSanPhamCK(spListCK);
			} else {

			}
			//		}else
			//		{
			System.out.println("BBBBBBBBB");
			//SETCHUNGLOAI SP 
			List<IErpKhachHang_ChungLoaiSP> clListCk = new ArrayList<IErpKhachHang_ChungLoaiSP>();
			String[] idClCK = request.getParameterValues("idChungLoaiSP");
			String[] maClCK = request.getParameterValues("maChungLoaiSP");
			String[] tenClCK = request.getParameterValues("tenChungLoaiSP");
			String[] ptClCK = request.getParameterValues("ptCKChungLoai");


			if (maClCK != null)
			{
				int m = 0;
				while (m < maClCK.length)
				{


					if (maClCK[m].trim().length() > 0)
					{

						IErpKhachHang_ChungLoaiSP sanphamCK = new ErpKhachHang_ChungLoaiSP();

						sanphamCK.setIdChungLoai(idClCK[m].trim());							
						sanphamCK.setMaChungLoai(maClCK[m].trim());
						sanphamCK.setTenChungLoai(tenClCK[m].trim());  
						sanphamCK.setPTChietKhau(ptClCK[m].trim());
						clListCk.add(sanphamCK);
						System.out.println(" # "+sanphamCK.getMaChungLoai()+" # "+sanphamCK.getTenChungLoai()+" # "+sanphamCK.getPTChietKhau());

					}
					m++;
				}
				khBean.setListChungLoaiCK(clListCk);
			}
			//}

			String[] nkh_khIds = request.getParameterValues("nkh_khList");
			khBean.setNkh_KhIds(nkh_khIds);

			/*
			String phuong = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("phuong"));
			if (phuong == null)
				phuong = "";
			khBean.setPhuong(phuong);
			*/

			String htkdId = Utility.antiSQLInspection(Doisangchuoi(request.getParameterValues("htkdId")));
			if (htkdId == null)
				htkdId = "";
			khBean.setHtkdId(htkdId);
			System.out.println("hinh thuc kinh doanh: " + htkdId);
			String pxId = Utility.antiSQLInspection(request.getParameter("pxId")==null?"":request.getParameter("pxId"));
			khBean.setPxId(pxId);

			String phuong = Utility.antiSQLInspection(request.getParameter("phuong")==null?"":request.getParameter("phuong"));
			khBean.setPhuong(phuong);

			String ngaysua = getDateTime();
			khBean.setNgaysua(ngaysua);

			boolean error = false;

			if (kbhId.equals("")){
				khBean.setMessage("Vui lòng chọn kênh bán hàng.");
				error = true;
			}

			if (diachi.trim().length()== 0){
				khBean.setMessage("Vui lòng nhập địa chỉ.");
				error = true;
			}

			if (khTen.trim().length()== 0){
				khBean.setMessage("Vui lòng nhập tên khách hàng.");
				error = true;
			}

			if (tpId.trim().length()== 0){
				khBean.setMessage("Vui lòng nhập tỉnh/thành phố.");
				error = true;
			}

			if (tpId.trim().length()!=0 && qhId.trim().length()== 0){
				khBean.setMessage("Vui lòng nhập quận/huyện.");
				error = true;
			}
			
			/*
			if (pxId.equals(""))
			{
			khBean.setMessage("Vui lòng nhập phường");
			error = true;
			}
			*/
			
			if (nguoidaidien.equals("")){
				khBean.setMessage("Vui lòng nhập người đại diện");
				error = true;
			}
			
			String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));

			String param = "";
		    if (view.trim().length() > 0) param ="&view="+view;
		   
			if (!error){
				if (action.equals("save"))
				{					
					if (id == null){ 
					
						if ( Utility.CheckSessionUser( session,  request, response))
						{
							Utility.RedireactLogin(session, request, response);
						}
						else if ( Utility.CheckRuleUser( session,  request, response, "KhachhangSvl",param, Utility.THEM ))
						{
							Utility.RedireactLogin(session, request, response);
						}
						 else {
							if (!(khBean.CreateKh())){								
								khBean.setView(view);
								khBean.createRS();
								session.setAttribute("khBean", khBean);			
								String nextJSP = "/AHF/pages/Distributor/KhachHangNew.jsp";
								response.sendRedirect(nextJSP);
							} else {
								
								IKhachhangList obj = new KhachhangList();
								obj.setView(view);
								obj.setUserId(userId);
								obj.init("");
								session.setAttribute("obj", obj);

								String nextJSP = "/AHF/pages/Distributor/KhachHang.jsp";
								response.sendRedirect(nextJSP);			    			    									
							}

						}
							
						}
					 else {
						if ( Utility.CheckSessionUser( session,  request, response))
						{
							Utility.RedireactLogin(session, request, response);
						}
						else if ( Utility.CheckRuleUser( session,  request, response, "KhachhangSvl",param, Utility.SUA ))
						{
							Utility.RedireactLogin(session, request, response);
						}
						 else {
							if (!(khBean.UpdateKh())){
								khBean.setView(view);
								khBean.init();
								session.setAttribute("khBean", khBean);
								String nextJSP = "/AHF/pages/Distributor/KhachHangUpdate.jsp";
								response.sendRedirect(nextJSP);
							}
							 else {
								IKhachhangList obj = new KhachhangList();
								obj.setView(view);
								obj.setUserId(userId);
								obj.init("");
								session.setAttribute("obj", obj);

								String nextJSP = "/AHF/pages/Distributor/KhachHang.jsp";
								response.sendRedirect(nextJSP);			    			    									
							}
						}
						
					}
				} else if(action.equals("duyet")) {
					dbutils db = new dbutils();

					 

					String query = "";
					query = "update khachhang set daduyet =1 where  pk_Seq ='"+ id + "'";
					db.update(query);
					khBean.setMessage("Duyệt thành công");
					System.out.println("vào đây rồi !!!!!!!!");
					khBean.setView(view);
					khBean.setUserId(userId);
					khBean.setId(id);
					khBean.setIsduyet("1");
					khBean.setsmartid(smartid);
					khBean.createRS();
					session.setAttribute("khBean", khBean);

					String nextJSP;
					if (id == null){			
						nextJSP = "/AHF/pages/Distributor/KhachHangNew.jsp";
					} else {
						nextJSP = "/AHF/pages/Distributor/KhachHangUpdate.jsp";   						
					}
					response.sendRedirect(nextJSP);		
				}else {
					
					System.out.println("vào đây rồi !!!!!!!!");
					khBean.setView(view);
					khBean.setUserId(userId);
					khBean.setId(id);
					khBean.setsmartid(smartid);
					khBean.createRS();
					session.setAttribute("khBean", khBean);

					String nextJSP;
					if (id == null){			
						nextJSP = "/AHF/pages/Distributor/KhachHangNew.jsp";
					} else {
						nextJSP = "/AHF/pages/Distributor/KhachHangUpdate.jsp";   						
					}
					response.sendRedirect(nextJSP);		
				}
			} else {
				khBean.setView(view);
				khBean.setUserId(userId);
				//khBean.init();
				khBean.createRS();

				session.setAttribute("khBean", khBean);

				String nextJSP;
				if (id == null){			
					nextJSP = "/AHF/pages/Distributor/KhachHangNew.jsp";
				} else {
					nextJSP = "/AHF/pages/Distributor/KhachHangUpdate.jsp";   						
				}
				response.sendRedirect(nextJSP);		
			}
		}
	}
	
	private String Doisangchuoi(String[] checknpp)
	{
		// TODO Auto-generated method stub
		String str = "";
		if (checknpp != null)
		{
			for (int i = 0; i < checknpp.length; i++)
			{
				if (i == 0)
				{
					str = checknpp[i];
				} else
				{
					str = str + "," + checknpp[i];
				}
			}
		}
		return str;

	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}
