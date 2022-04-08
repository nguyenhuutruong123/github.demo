package geso.dms.distributor.servlets.khuyenmai;

import geso.dms.distributor.beans.ctkhuyenmai.*;
import geso.dms.distributor.beans.ctkhuyenmai.imp.*;
import geso.dms.distributor.beans.donhang.IDonhang;
import geso.dms.distributor.beans.donhang.imp.Donhang;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.servlets.donhang.DonhangUpdateSvl;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class KhuyenmaiSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	public KhuyenmaiSvl() 
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		this.doPost(request, response);
	}

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

			XLkhuyenmai xlkm;
			IDonhang dhBean;
			
			String chap_nhan_giam_so_suat_km = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chap_nhan_giam_so_suat_km"));
			if(chap_nhan_giam_so_suat_km == null || chap_nhan_giam_so_suat_km.length() <=0)
				chap_nhan_giam_so_suat_km="false";
			


			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();

			Utility util = new Utility();
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

			String khachhang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khachhang")));

			String dhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dhId")));

			String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
			if(nppId == null)
				nppId = "";

			String ngaygiaodich = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaygiaodich")));
			dhBean = new Donhang(dhId);
			dhBean.setUserId(userId);

			String tonggiatridh = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tonggiatri")));

			String[] masp = request.getParameterValues("spMa");
			String[] soluong = request.getParameterValues("spSoluong");
			String[] dongia = request.getParameterValues("spDongia");
			
			String[] chonTichluyDacBiet = request.getParameterValues("chonTichluyDacBiet");
			
			dhBean.setTichluyIdList(chonTichluyDacBiet);	
			

			String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
			if (action == null){
				action = "";
			}
			String error = "";
			if(action.equals("backForm"))
			{
				
				dhBean.init();
				dhBean.setAplaikhuyenmai(false);
				dhBean.setIsChuaApkhuyenmai(true);
				session.setAttribute("dhBean", dhBean);

				String nextJSP = "/AHF/pages/Distributor/DonHangUpdate.jsp";
				response.sendRedirect(nextJSP);	
			}
			else //submit
			if(action.equals("save"))
			{
				error = LuuKhuyenMai(request, dhBean) ;
				if(error.length() > 0 ) error += "\n";
				error += dhBean.ApTichluyDacBiet() ;
				if(error.length() > 0 ) error += "\n";
				Utility.Update_GiaTri_DonHang(dhId, dhBean.getDb());
				
				
				dhBean.init();
				dhBean.setMessage(error + dhBean.getMessage());
				dhBean.setAplaikhuyenmai(false);
				dhBean.setIsChuaApkhuyenmai(true);
				session.setAttribute("dhBean", dhBean);

				String nextJSP = "/AHF/pages/Distributor/DonHangUpdate.jsp";
				response.sendRedirect(nextJSP);	
			}
			else //submit
			{
				xlkm = new XLkhuyenmai(userId, ngaygiaodich,khachhang,dhId);
				
				xlkm.setTichluyIdList(chonTichluyDacBiet);	
				
				xlkm.setChap_nhan_giam_so_suat_km(Boolean.parseBoolean(chap_nhan_giam_so_suat_km));
				Hashtable<String,Integer> sp_sl = new Hashtable<String,Integer>();
				Hashtable<String,Float> sp_dg = new Hashtable<String,Float>();

				int m = 0; 
				while(m < masp.length)
				{
					sp_sl.put(masp[m], Integer.parseInt(soluong[m].replaceAll(",", "")));
					sp_dg.put(masp[m], Float.parseFloat(dongia[m].replaceAll(",", "")));
					m++;
				}

				xlkm.setHashA(sp_sl);
				xlkm.setHashB(sp_dg);

				String[] scheme = request.getParameterValues("Scheme");
				if(scheme != null)
				{
					List<ICtkhuyenmai> listCTKM = xlkm.SortList(xlkm.getCtkmList(), scheme);
					xlkm.setCtkmList(listCTKM); //Sort lai thu tu
				}

				String showAll = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ShowAll")));
				if(showAll == null)
					xlkm.setDieuchinh(true);
				else
					xlkm.setDieuchinh(false);

				xlkm.setMasp(masp);
				xlkm.setSoluong(soluong);
				xlkm.setDongia(dongia);
				xlkm.setIdDonhang(dhId);
				xlkm.setTonggiatriDh(Float.parseFloat(tonggiatridh));
				xlkm.setNgaygiaodich(ngaygiaodich);

				xlkm.ApKhuyenMai();
				xlkm.checkConfirm();
				xlkm.SetDungSanPham();
				xlkm.setKey_sanpham_sudung();
				session.setAttribute("xlkm", xlkm);

				String nextJSP = "/AHF/pages/Distributor/KhuyenMai.jsp";
				response.sendRedirect(nextJSP);
			}
		} 
	}


	private String checkTonKhoKhuyenMai(String kho_fk, String nppId, String[] spIdss, String khachhang, dbutils db)
	{/*
		//kiem tra trong kho khuyen mai con du san pham hay khong, khong du thi thoat
		String query = "select available from nhapp_kho where kho_fk = '" + kho_fk + "' and npp_fk = '" + nppId + "' and sanpham_fk = (select pk_seq from sanpham where ma = '" + spIdss[0].trim() + "') and kbh_fk = (select kbh_fk from khachhang where pk_seq = '" + khachhang + "')";
		System.out.println("2.Query check ton kho khuyen mai: " + query);
		ResultSet rsCheck = db.get(query);
		if(rsCheck != null)
		{
			try 
			{
				boolean flag = false;

				while(rsCheck.next())
				{
					if(rsCheck.getString("available") != null)
					{
						flag = true;
						String avai = rsCheck.getString("available");
						if(Integer.parseInt(avai) < Integer.parseInt(spIdss[1]))
						{
							return "Sản phẩm khuyến mại  " + spIdss[0] + " - Còn tối đa " + avai;	
						}
					}

				}

				rsCheck.close();
				if(flag == false) //khong co dong du lieu nao, Resualset van khac null
				{
					//	System.out.println("San pham khuyen mai " + spIdss[0] + " --- Con toi da 0 \n");
					return "San pham khuyen mai " + spIdss[0] + " - Con toi da 0";
				}
			}
			catch (SQLException e) 
			{ 
				return e.getMessage();
			}
		}
		else{
			return "error";
		}*/

		return "";
	}

	private String checkTonkho(String nppId, String ctkmId, String khId, String spId, String spMa, int slg, dbutils db) 
	{
		//kiem tra trong kho khuyen mai con du san pham hay khong, khong du thi thoat
		String query = "select available from nhapp_kho where kho_fk = (select kho_fk from ctkhuyenmai where pk_seq = '" + ctkmId + "') and npp_fk = '" + nppId + "' and sanpham_fk = '" + spId + "' and kbh_fk = (select kbh_fk from khachhang where pk_seq = '" + khId + "')";
		//System.out.println("Check ton kho: " + query + "\n");
		ResultSet rsCheck = db.get(query);
		if(rsCheck != null)
		{
			try 
			{
				boolean flag = false;

				while(rsCheck.next())
				{
					if(rsCheck.getString("available") != null)
					{
						flag = true;
						String avai = rsCheck.getString("available");
						if(Integer.parseInt(avai) < slg)
						{
							return "Sản phẩm khuyến mại " + spMa + " - Còn tối đa " + avai;
						}
					}
				}
				rsCheck.close();
				if(flag == false) //khong co dong du lieu nao, Resualset van khac null
				{
					//System.out.println("San pham khuyen mai " + spMa + " --- Con toi da 0 \n");
					return "San pham khuyen mai " + spMa + " - Con toi da 0";
				}
			}
			catch (SQLException e) { return "error"; }
		}else{
			return "error";
		}

		return "";
	}

	

	private String CreateKhuyenmai(String ctkmId, String phanbotheoDH, String trakmId, String id, String mucphanbo, String nppId, String khachhang, long soxuat, String khId, dbutils db)
	{
		String str = "";
		try 
		{ 	
			String sql = "\n select a.sanpham_fk as spId, a.soluong, b.ma as spMa " +
			"\n from trakhuyenmai_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq " +
			"\n where a.trakhuyenmai_fk = '" + trakmId + "' " +
			"\n and exists  " +
			"\n ( " +
			"\n     select 1 from nhapp_kho " +
			"\n     where kho_fk = (select kho_fk from ctKhuyenMai where pk_seq='"+ctkmId+"') and sanpham_fk = a.sanpham_fk " +
			"\n     and npp_Fk = '"+nppId+"' and KBH_FK = (SELECT KBH_FK from KhachHang where pk_Seq = '"+khachhang+"' ) " +
			"\n     --and AVAILABLE > 0" +
			"\n ) " ;
			System.out.println("10.Query truy van san pham khuyen mai: " + sql + "\n");
			ResultSet rsSQl = db.get(sql);
			long tongtien = 0;
			/*if(rsSQl != null)*/
			{
				while(rsSQl.next())
				{
					int slg = Integer.parseInt(rsSQl.getString("soluong")) * (int)soxuat;
					String query = 
						
						 "\n select  sp.ten as spTen ,dbo.[GiaBanLeNppSanPham](dh.khachhang_fk,dh.kbh_fk,dh.npp_fk ,sp.pk_seq , dh.ngaynhap ) dongia " +
							"\n from donhang dh, sanpham sp " +
							"\n where dh.pk_seq = "+id+" and sp.pk_seq = '" + rsSQl.getString("spId") + "' ";
	
					
						
					
					
					System.out.println("11.Lay don gia: " + query);
					double dongia = 0;
					ResultSet rsDg = db.get(query);
					if(rsDg != null)
					{
						if(rsDg.next())  
						{
							if(rsDg.getString("dongia") != null)
								tongtien = Math.round(slg * rsDg.getFloat("dongia"));
								dongia =  rsDg.getFloat("dongia");
							rsDg.close();
						}
					}
					
					/*********************************************************************************/
					String msg = "";
					
					
					if(msg.trim().length() > 0)
					{
						return msg;
					}
					/*********************************************************************************/
					
					geso.dms.center.util.Utility util = new geso.dms.center.util.Utility();
					//luu tong gia tri o moi dong sanpham
					query = "\n Insert into donhang_ctkm_trakm(donhangId, ctkmId, trakmId, soxuat, tonggiatri, spMa,SANPHAM_FK, soluong) " + 
					"\n values('" + id + "','" + ctkmId + "','" + trakmId + "','" + soxuat + "', '" + Long.toString(tongtien) + "', '" + rsSQl.getString("spMa").trim() + "', '" + rsSQl.getString("spId").trim() + "', '" + Integer.toString(slg) + "')";
					System.out.println("12.Chen khuyen mai: " + query);
					if(!db.update(query))
					{
						str = query;
						return str;
					}
					/*
					 
				String error = checkTonkho(nppId, ctkmId, khId, rsSQl.getString("spId"), rsSQl.getString("spMa"), slg, db);
					if(error.length() > 0)
					{
						return error;
					}
					//cap nhat kho
					query = " if((select count(*) From DonHang where pk_seq='"+id+"' and TrangThai=0)=1)  " +
							"Update nhapp_kho set available = available - '" + Integer.toString(slg) + "', booked = booked + '" + Integer.toString(slg) + "' where kho_fk = (select kho_fk from ctkhuyenmai where pk_seq = '" + ctkmId + "') and npp_fk = '" + nppId + "' and sanpham_fk = '" + rsSQl.getString("spId") + "' and kbh_fk = (select kbh_fk from khachhang where pk_seq = '" + khId+ "')";   							
					if(db.updateReturnInt(query)!=1)
					{
						str = query;
						return str;
					}
					
					
					query=  " select KHO_FK,SANPHAM_FK,KBH_FK,NGAYNHAPKHO,available, (select ngaynhap from donhang where pk_seq='"+id+"') as ngaynhap from NHAPP_KHO_CHITIET "+  
			    			" where NPP_FK ="+nppId+" and KBH_FK= (select kbh_fk from khachhang where pk_seq = '" + khId+ "')"  +
			    			" and KHO_FK=(select kho_fk from ctKhuyenMai where pk_seq='"+ctkmId+"')  and SANPHAM_FK =  "+ rsSQl.getString("spid").trim() +
			    			" AND AVAILABLE >0  and NGAYNHAPKHO<=(select ngaynhap from donhang where pk_seq='"+id+"')"+
			    			" order by NGAYNHAPKHO,AVAILABLE ";
			    	ResultSet rssp=db.get(query);
			    	double soluongdenghi=slg ;

			    	while(rssp.next() && soluongdenghi >0){
			    		double soluong_avai= rssp.getDouble("AVAILABLE");
			    		double soluongcapnhat=0;
			    		if(soluong_avai >soluongdenghi){
			    			soluongcapnhat= soluongdenghi;
			    			soluongdenghi=0;
			    		}else{
			    			soluongcapnhat =soluong_avai;
			    			soluongdenghi =soluongdenghi - soluong_avai;
			    		}

			    		String _khoid=rssp.getString("kho_fk");
			    		String _kbhid=rssp.getString("KBH_FK");
			    		String ngaynhapkho=rssp.getString("NGAYNHAPKHO");
			    		String ngaynhap=rssp.getString("ngaynhap");
			    		
			    		query = "Insert into donhang_ctkm_trakm_chitiet(donhangId, ctkmId, trakmId, soxuat, tonggiatri, spMa,SANPHAM_FK, soluong,ngaynhapkho) " + 
								" values('" + id + "','" + ctkmId + "','" + trakmId + "','" + soxuat + "', '" + dongia*soluongcapnhat + "', '" + rsSQl.getString("spMa").trim() + "', '" + rsSQl.getString("spid").trim() + "', '" + (int)(soluongcapnhat) + "','"+ngaynhapkho+"')";
						System.out.println("12.Chen khuyen mai: " + query);
						if(!db.update(query))
						{
							str = query;
							return str;
						}	
						
						msg=util.Update_NPP_Kho_Sp(ngaynhap,id, "updae ĐƠN HÀNG_1454552 apkm", db, _khoid, rsSQl.getString("spid").trim(), nppId, _kbhid, 0.0, soluongcapnhat, (-1)* soluongcapnhat, 0.0);
						if(msg.length()>0)
						{
							msg = "Lỗi kho khi tạo đơn hàng: " + msg;
							return msg;
						}	
						 
						String msg1=util.Update_NPP_Kho_Sp_Chitiet(ngaynhap, "update donhang DHID ctkm : "+id ,  db, _khoid, rsSQl.getString("spid").trim(), nppId, _kbhid, ngaynhapkho, 0,soluongcapnhat, (-1)*soluongcapnhat, 0, 0);
						if (msg1.length()> 0) 
						{
							msg=msg1;
							return msg1;
						}

			    	}
			    	rssp.close();
					
					if(soluongdenghi!=0){
						 
						msg=  "Số lượng đề xuất trong lô chi tiết của sản phẩm "+rsSQl.getString("spMa").trim()+"   tới ngày (ngày cấu hình hóa đơn) đơn hàng không còn đủ, " +
								" vui lòng kiểm tra báo cáo ( xuất nhập tồn,tồn hiện tại) theo lô để biết thêm chi tiết ";
						return msg;
						
					}
*/				}
			}
			rsSQl.close();
			
/*			String query = "select (select count(distinct ctkm_fk) as ctkmid  from donhang_ctkm_dkkm a"
					+ " where donhang_fk  = "
					+ id
					+ "  ) as sl, (select count(distinct ctkmid) as ctkmid  from donhang_ctkm_trakm a"
					+ " where donhangid  = " + id + "  ) as sl2 ";
			ResultSet rs = db.get(query);
			while (rs.next()) {
				String msg1 = "";
				if (rs.getDouble("sl") < rs.getDouble("sl2")) {
					msg1 = "vui lòng check lại khuyến mãi ";
				}
				if (msg1.length() > 0) {
					db.getConnection().rollback();
					return msg1;
				}

			}
*/
		} 
		catch (Exception e1) 
		{
			e1.printStackTrace();
			
			return "Loi khi tao ctkm: " + ctkmId + ", " + e1.toString();
		}
		return str;
	}


	private String LuuKhuyenMai(HttpServletRequest request, IDonhang dhBean) 
	{
		try
		{
			Utility util = new Utility();

			String khachhang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khachhang")));
			String dhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dhId")));
			String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
			if(nppId == null)
				nppId = "";

			String[] schemeList = request.getParameterValues("schemeList");
			String[] schemeOR = request.getParameterValues("schemeOR");
			String[] soxuatkm = request.getParameterValues("soxuatKM");
			String[] schemePhanBo = request.getParameterValues("schemePhanBo");

			String[] schemeDiengiai = request.getParameterValues("schemeDiengiai");



			String msg = "";
			if(schemeList.length > 0)
			{

				System.out.println("schemeList.length = "+ schemeList.length);

				dbutils db = new dbutils();
				db.getConnection().setAutoCommit(false);
				String sql="update donhang set trangthai=0, thoidiemsuakm = getdate() where pk_seq="+dhId+" and trangthai=0";
				if (db.updateReturnInt(sql)!= 1)  
				{
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
 					return "Trạng thái đơn không hợp lệ";
				}
				String kq = capNhatKM(dhId, nppId, khachhang,dhBean.getTrangthai(), db);
				if (kq.trim().length() > 0)
				{
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return msg;
				}
				for( int i = 0; i < schemeList.length; i ++)
				{
					System.out.println("schemeList ["+i+"] =  "+ schemeList[i]);
					String error = LuuSCHEME(db, request, i, schemeList, schemeOR, soxuatkm, schemePhanBo, schemeDiengiai, nppId, dhId, khachhang);
					if(error.trim().length() > 0)
					{
						db.getConnection().rollback();
						db.getConnection().setAutoCommit(true);
						db.shutDown();
						return error;
					}
				}
				
				String error = "";
				String query =  "\n select ctkm.scheme, tkm.DIENGIAI, dh.DONHANGID, dh.CTKMID, dh.TRAKMID ,  max(tkm.tongluong) * max(dh.soxuat) BatBuocNhan, sum(dh.soluong) slTra " +
					"\n from donhang_ctkm_trakm dh " +
					"\n inner join TRAKHUYENMAI tkm on tkm.PK_SEQ = dh.TRAKMID and tkm.LOAI = 3 and tkm.HINHTHUC = 2 " +
					"\n inner join CTKHUYENMAI ctkm on ctkm.pk_seq = dh.ctkmId " +
					"\n where dh.DONHANGID =" + dhId + 
					"\n group by ctkm.scheme, tkm.DIENGIAI, dh.DONHANGID, dh.CTKMID, dh.TRAKMID " +
					"\n having max(tkm.tongluong) * max(dh.soxuat) != sum(dh.soluong) " ;
				ResultSet rs = db.get(query);
				while(rs.next())
				{
					 kq = 	"Số lượng trả khuyến mãi  bạn nhập của  CTKM ("+rs.getString("scheme")+") chưa bằng với tổng lượng ("+rs.getString("BatBuocNhan")+") được hưởng" ;
					error += error.trim().length() > 0 ? "\n ."  + kq : kq;  
				}
				if(error.trim().length() > 0)
				{
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					db.shutDown();
					return error;
				}
				
				
				String jsoStrDaSuDung = geso.dms.center.util.Utility.giaiMa_ver1_noreplace( request.getParameter("sudungkhuyenmai"));

				if( jsoStrDaSuDung.trim().length() <=0)
				{
					msg = "Lỗi bảo mật hệ thống!";
					db.getConnection().rollback();
					return msg;
				}

				JsonArray json_da_su_dung = (JsonArray) new JsonParser().parse(jsoStrDaSuDung);
				if(json_da_su_dung == null || json_da_su_dung.size() <=0)
				{
					msg = "Lỗi bảo mật hệ thống 2!";
					db.getConnection().rollback();
					return msg;
				}
				for(int xxx = 0 ; xxx < json_da_su_dung.size(); xxx++)
				{ 
					JsonObject rec = (JsonObject) json_da_su_dung.get(xxx);
					String ctkm_fk= rec.get("ctkm_fk").getAsString();
					String dkkm_fk= rec.get("dkkm_fk").getAsString();
					String sanpham_fk= rec.get("sanpham_fk").getAsString();
					String soxuat= rec.get("soxuat").getAsString();
					String soluongmua= rec.get("soluongmua").getAsString();
					query = "Insert into DONHANG_CTKM_DKKM( ctkm_fk, dkkm_fk, soxuat, sanpham_fk, soluongmua, donhang_fk) " + 
					" values('" + ctkm_fk + "','" + dkkm_fk + "','" + soxuat + "',"+sanpham_fk+", " + soluongmua + ", '"+dhId+"')";
					if(db.updateReturnInt(query)<=0)
					{
						System.out.println("query = "+ query);
						msg = "Lỗi ghi nhận sản phẩm  sử dụng !";
						db.getConnection().rollback();
						return msg;
					}
				}
				
				
				
				// check ngansach
				query =  "\n select ctkmId,max(soxuat) soxuat ,sum(tonggiatri)  tonggiatri " +
				"\n from donhang_ctkm_trakm dhkm " +
				"\n where donhangId = " + dhId + 
				"\n group by ctkmId  ";
				rs= db.get(query);
				while(rs.next())
				{
					String ctkmId = rs.getString("ctkmId");
					String checkmsg = DonhangUpdateSvl.CheckNghanSach(ctkmId,nppId,  khachhang,  db) ;

					if (checkmsg.trim().length() > 0) {
						dhBean.getCtkm_bi_giam_so_suat().add(checkmsg);
						DonhangUpdateSvl.Xoa_Khuyen_Mai( db ,  dhId,  ctkmId)	;	
					}
					msg += checkmsg ;
				}
				
				
				
				
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
				db.shutDown();
			}
			
			
			return msg;
		}
		catch (Exception e) {
			e.printStackTrace();
			return "Exception:"  + e.getMessage();
		}
	}
	private String capNhatKM(String id, String nppId, String khId, String trangthai, dbutils db) {
		String msg = "";
		String query= "";
		geso.dms.center.util.Utility util = new geso.dms.center.util.Utility();
		
		/*
		String query = "SELECT DH.NGAYNHAP,SP.PK_SEQ AS SANPHAM_FK,CTKM.KHO_FK,DH.NPP_FK,DH.KBH_FK,KM.SOLUONG "
			+ "	FROM DONHANG_CTKM_TRAKM   "
			+ "		KM INNER JOIN DONHANG DH  ON DH.PK_SEQ=KM.DONHANGID "
			+ "		INNER JOIN CTKHUYENMAI CTKM ON CTKM.PK_SEQ=KM.CTKMID "
			+ "		INNER JOIN SANPHAM SP ON SP.MA=KM.SPMA  "
			+ "	WHERE DH.TRANGTHAI=0 AND DH.PK_SEQ='"+ id+ "'  "
			+ "	GROUP BY DH.NPP_FK,DH.KBH_FK,CTKM.KHO_FK,SP.PK_SEQ, DH.NGAYNHAP, KM.SOLUONG ";

		System.out.println("UPDATE NPP KHO: " + query  );
		ResultSet ckRs = db.get(query);
		try {
			while(ckRs.next())
			{
				String kho_fk=ckRs.getString("kho_fk");
				String kenh =ckRs.getString("kbh_fk");
				String sanpham_fk=ckRs.getString("sanpham_fk");
				String ngaynhap  =ckRs.getString("ngaynhap");
				//String tensp  =ckRs.getString("tensp");
				Double soluong = ckRs.getDouble("soluong");


				msg=util.Update_NPP_Kho_Sp(ngaynhap,id, "SPKM_1454552", db, kho_fk, sanpham_fk, nppId, kenh, 0.0, (-1)* soluong, soluong, 0.0);
				if (msg.length() > 0)
				{
					db.getConnection().rollback();
					msg = "Lỗi kho khi tạo đơn hàng: " + msg;
					return msg;
				}		
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}				    


		String	query_ct=	 "SELECT DH.NGAYNHAP,SP.PK_SEQ AS SANPHAM_FK,CTKM.KHO_FK,DH.NPP_FK,DH.KBH_FK,KM.SOLUONG, km.ngaynhapkho "
			+ "	FROM DONHANG_CTKM_TRAKM_chitiet   "
			+ "		KM INNER JOIN DONHANG DH  ON DH.PK_SEQ=KM.DONHANGID "
			+ "		INNER JOIN CTKHUYENMAI CTKM ON CTKM.PK_SEQ=KM.CTKMID "
			+ "		INNER JOIN SANPHAM SP ON SP.MA=KM.SPMA  "
			+ "	WHERE DH.TRANGTHAI=0 AND DH.PK_SEQ='"+ id+ "'  "
			+ "	GROUP BY DH.NPP_FK,DH.KBH_FK,CTKM.KHO_FK,SP.PK_SEQ,DH.NGAYNHAP,KM.SOLUONG, km.ngaynhapkho   ";

		System.out.println("UPDATE NPP KHO: " + query_ct  );
		ckRs = db.get(query_ct);
		try {
			while(ckRs.next())
			{
				String kho_fk=ckRs.getString("kho_fk");
				String kenh =ckRs.getString("kbh_fk");
				String sanpham_fk=ckRs.getString("sanpham_fk");
				String ngaynhap  =ckRs.getString("ngaynhap");
				//String tensp  =ckRs.getString("tensp");
				Double soluong = ckRs.getDouble("soluong");
				String ngaynhapkho=ckRs.getString("NGAYNHAPKHO");
				String msg1=util.Update_NPP_Kho_Sp_Chitiet(ngaynhap, "update donhang DHID: "+id ,  db, kho_fk, sanpham_fk, nppId, kenh, ngaynhapkho, 0,(-1)*soluong, soluong, 0, 0);
				if (msg1.length()> 0) 
				{
					msg=msg1;
					return msg;
				}
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}				
		*/
        
		String query_ct = "";
		query = "delete from DonHang_CTKM_TRAKM WHERE DONHANGID = '" + id + "' ";
		System.out.println("in ra xem nào ___"+query);
		if (!db.update(query)) {
			msg = "Lỗi xóa KM 1.";
		}
		
		query_ct = "delete from DonHang_CTKM_TRAKM_chitiet WHERE DONHANGID='" + id	+ "' ";
		
		if (!db.update(query_ct)) {
			msg = "Lỗi xóa KM 2.";
		}
		query_ct="update donhang set trangthai=0 where pk_seq='" + id	+ "'  and trangthai=0";
		if (db.updateReturnInt(query_ct)<=0) {
			msg = "Không được áp khuyến mãi khi đơn đã chốt";
		}
		/*
		query = "select count(*) as sl "
		+ " from NHAPP_KHO kho left join v_Booked book "
		+ "			on kho.NPP_FK = book.npp_fk and kho.KHO_FK = book.kho_fk and kho.KBH_FK = book.kbh_fk and kho.SANPHAM_FK = book.sanpham_fk "
		+ " where kho.NPP_FK = '" + nppId
		+ "' and kho.BOOKED != ISNULL(book.soluong, 0) ";
		if (id.trim().length() > 0)
			query += " and kho.sanpham_fk in ( select sanpham_fk from DONHANG_SANPHAM where DONHANG_FK = '"	+ id+ "' "
					+ "union select b.PK_SEQ from DONHANG_CTKM_TRAKM a inner join SANPHAM b on a.SPMA = b.MA where a.DONHANGID = '"	+ id + "' ) ";

		ResultSet rs = db.get(query);
		try {
			rs.next();
			int sl = rs.getInt("sl");
			rs.close();
			if (sl > 0) {
				query = "update kho set kho.BOOKED = isnull(book.soluong, 0), "
						+ " kho.AVAILABLE = case when kho.SOLUONG >= isnull(book.soluong, 0) then kho.SOLUONG - isnull(book.soluong, 0) else 0 end "
						+ " from NHAPP_KHO kho left join v_Booked book "
						+ "			on kho.NPP_FK = book.npp_fk and kho.KHO_FK = book.kho_fk and kho.KBH_FK = book.kbh_fk and kho.SANPHAM_FK = book.sanpham_fk "
						+ " where kho.NPP_FK = '" + nppId
						+ "' and kho.BOOKED != ISNULL(book.soluong, 0) ";
				if (id.trim().length() > 0)
					query += " and kho.sanpham_fk in ( select sanpham_fk from DONHANG_SANPHAM where DONHANG_FK = '"	+ id+ "' "
							+ "union select b.PK_SEQ from DONHANG_CTKM_TRAKM a inner join SANPHAM b on a.SPMA = b.MA where a.DONHANGID = '"	+ id + "' ) ";

				System.out.println("---FIX BOOKED TONG:: " + query);
				if (!db.update(query)) {
					return "1.2.Lỗi cập nhật NHAPP_KHO " + query;
				}

			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		*/
		
		return msg;
	}
	private String LuuSCHEME(dbutils db, HttpServletRequest request, int i, String[] schemeList, String[] schemeOR, String[] soxuatkm, String[] schemePhanBo, String[] schemeDiengiai,
			String nppId, String dhId, String khachhang	) 
	{
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String[] trakm = request.getParameterValues(schemeList[i] + ".trakmId");
			String[] trakmType = request.getParameterValues(schemeList[i] + ".trakmType");
			String[] trakmHinhThuc = request.getParameterValues(schemeList[i] + ".trakmHinhThuc");
			String[] trakmPrimary = request.getParameterValues(schemeList[i] + ".trakmPrimary");
			System.out.println("Scheme OR cua CTKM: " + schemeList[i] + ", la: " + schemeOR[i]);
			
			if(Boolean.parseBoolean(schemeOR[i]))
			{
				String trakmSelected = request.getParameter(schemeList[i] + ".chon");
				System.out.println("1145. Ban chon TKM: " + trakmSelected);
				if(trakmSelected == null)
					trakmSelected = trakm[0];

				int index_OR = -1;
				for(int kk = 0; kk < trakm.length; kk++)
				{
					if(trakm[kk].trim().equals(trakmSelected))
					{
						index_OR = kk;
						break;
					}
				}

				String trakmTypeSelected = trakmType[index_OR];
				String trakmHinhthucSelected = trakmHinhThuc[index_OR];
				String trakmPrimarySelected = trakmPrimary[index_OR];

				//thay tra khuyen mai OR duoc chon trong List
				trakm = new String[]{trakmSelected};
				trakmType = new String[]{trakmTypeSelected};
				trakmHinhThuc = new String[]{trakmHinhthucSelected};
				trakmPrimary = new String[]{trakmPrimarySelected};

			}

			String	query = "select kho_fk, level_phanbo, phanbotheodonhang from ctkhuyenmai where pk_seq = '" + schemeList[i] + "'";
			ResultSet KhoRS = db.get(query);
			String mucphanbo = "";
			String phanbotheoDH = "";
			String kho_fk="";
			if(KhoRS != null)
			{
				KhoRS.next();
				mucphanbo = KhoRS.getString("level_phanbo");
				kho_fk = KhoRS.getString("kho_fk");
				phanbotheoDH = KhoRS.getString("phanbotheodonhang");
				KhoRS.close();
			}

			for(int count = 0; count < trakm.length; count++)
			{
				String ttTrakm = request.getParameter(schemeList[i] + "." + trakm[count] + ".tonggiatriTKM");
				String spId = request.getParameter(schemeList[i] + "." + trakm[count] + ".spSelected");
				if(spId == null)
					spId = "";
				
				if( !( trakmType[count].trim().equals("3") && trakmHinhThuc[count].trim().equals("1") ) ) //tra khuyen mai voi san pham tuy chon
				{			    				
					String flag = "";
					

					//Thoa ngan sach
					if(trakmPrimary[count].equals("0"))
					{
						if(spId.length() > 0 ) //masp1-soluong1;masp2-soluong2...
						{
							if(spId.indexOf(";") > 0) //nhieu san pham
							{
								String[] spIds = spId.split(";");

								int toalTRA = 0;
								for(int j = 0; j < spIds.length; j++)
								{
									String[] spIdss = spIds[j].split("-");
									System.out.println("_____-San pham______"+spIdss);
									msg = checkTonKhoKhuyenMai(kho_fk, nppId, spIdss, khachhang, db);

									if(msg.equals(""))
									{
										long tongtri = 0;
										
										query = "\n select  sp.ten as spTen ,dbo.[GiaBanLeNppSanPham](dh.khachhang_fk,dh.kbh_fk,dh.npp_fk ,sp.pk_seq , dh.ngaynhap ) dongia " +
												"\n from donhang dh, sanpham sp " +
												"\n where dh.pk_seq = "+dhId+" and sp.ma = '" + spIdss[0].trim() + "'";
										
										ResultSet rsKm = db.get(query);
										String spTen="";
										{
											if(rsKm.next())
											{
												tongtri = Math.round(rsKm.getFloat("dongia"));
												spTen =rsKm.getString("spTen");
											}
											rsKm.close();
										}
										long gtriKm = tongtri * Integer.parseInt(spIdss[1].trim());

									/*	if(tongtri<=0)
										{
											db.getConnection().rollback(); 
											return  "Vui lòng liên hệ TT khai báo giá cho sản phẩm hưởng KM '" + spIdss[0].trim() + "' - '"+spTen+"' ";

										}	*/
										
										query = "Insert into donhang_ctkm_trakm(donhangId, ctkmId, trakmId, soxuat, spMa,SANPHAM_FK, soluong, tonggiatri) " + 
												" values('" + dhId + "','" + schemeList[i] + "','" + trakm[count] + "','" + Math.round(Double.parseDouble(soxuatkm[i])) + "','" + spIdss[0].trim() + "',( select pk_seq from SANPHAM where MA = '" + spIdss[0].trim() + "' ),'" + spIdss[1].trim() + "','" + Long.toString(gtriKm) + "')";
										System.out.println("1.Cau lenh chen du lieu: " + query);
										if(!db.update(query))
										{
											db.getConnection().rollback(); 
											return "552.Loi khi tao moi 'donhang_ctkm_trakm': " + query;
										}

										
										toalTRA += Integer.parseInt(spIdss[1].trim().replaceAll(",", ""));
									}
									else
									{
										;
										return  msg + " nên bạn không được hưởng ctkm '" + (schemeDiengiai[i] == null ? schemeList[i] : schemeDiengiai[i]) + "'. Vui lòng điều chỉnh lại.\n";
									}
								}

								if(toalTRA > 0)
								{
									//NEU PHAN BO THEO SOLUONG THI PHAI CHECK TONG LONG TRA SO VOI TONG LUONG PHAN BO
									
								}
							}
							else  //mot san pham
							{
								String[] spIdss = spId.split("-");
								//NEU PHAN BO THEO SOLUONG THI PHAI CHECK TONG LONG TRA SO VOI TONG LUONG PHAN BO
								

								//kiem tra trong kho khuyen mai con du san pham hay khong, khong du thi thoat
								msg = checkTonKhoKhuyenMai(kho_fk, nppId, spIdss, khachhang, db);
								if(msg.equals(""))
								{
									long tongtri = 0;
									query =  "\n select  sp.ten as spTen ,dbo.[GiaBanLeNppSanPham](dh.khachhang_fk,dh.kbh_fk,dh.npp_fk ,sp.pk_seq , dh.ngaynhap ) dongia " +
											"\n from donhang dh, sanpham sp " +
											"\n where dh.pk_seq = "+dhId+" and sp.ma = '" + spIdss[0].trim() + "'";
			
										
									ResultSet rsKm = db.get(query);
									String spTen="";
									{
										if(rsKm.next())
										{
											tongtri = Math.round(rsKm.getFloat("dongia"));
											spTen =rsKm.getString("spTen");
										}
										rsKm.close();
									}
									long gtriKm = tongtri * Integer.parseInt(spIdss[1].trim());

									/*if(tongtri<=0)
									{
										; 
										return  "Vui lòng liên hệ TT khai báo giá cho sản phẩm hưởng KM '" + spIdss[0].trim() + "' - '"+spTen+"' ";
									}	*/

									query = "Insert into donhang_ctkm_trakm(donhangId, ctkmId, trakmId, soxuat, spMa,SANPHAM_FK, soluong, tonggiatri)" +
											" values('" + dhId + "','" + schemeList[i] + "','" + trakm[count] + "','" + Math.round(Double.parseDouble(soxuatkm[i])) + "','" + spIdss[0].trim() + "',( select pk_seq from SANPHAM where MA = '" + spIdss[0].trim() + "' ),'" + spIdss[1].trim() + "','" + Long.toString(gtriKm) + "')";	
									System.out.println("3.Cau lenh insert du lieu: " + query);
									if(!db.update(query))
									{
										; 
										return  "635.Loi khi tao moi 'donhang_ctkm_trakm' "+query ;
									}
									//int slg = Integer.parseInt(spIdss[1].trim());
									long slg = Math.round(Double.parseDouble(spIdss[1].trim()));
									
									
									/*query = " if((select count(*) From DonHang where pk_seq='"+dhId+"' and TrangThai=0)=1)  " +
											"Update nhapp_kho set available = available - '" + slg + "', booked = booked + '" + slg + "' where kho_fk = '" + kho_fk + "' and npp_fk = '" + nppId + "' and sanpham_fk = (select pk_seq from sanpham where ma = '" + spIdss[0].trim() + "') and kbh_fk = (select kbh_fk from khachhang where pk_seq = '" + khachhang + "')";
									System.out.println("4.Cau lenh cap nhat nhapp_kho: " + query);
									if(db.updateReturnInt(query)!=1)
									{
										;
										return  "644.Loi khi tao moi 'donhang_ctkm_trakm' "+query ;

									}*/
									
						/*			
										///////////////////////////////////////////////////////////////////////////////
										geso.dms.center.util.Utility util = new geso.dms.center.util.Utility();
										query=  " select KHO_FK,SANPHAM_FK,KBH_FK,NGAYNHAPKHO,available, (select ngaynhap from donhang where pk_seq='"+dhId+"') as ngaynhap,(select pk_seq from sanpham where ma = '" + spIdss[0].trim() + "') as sanpham_fk from NHAPP_KHO_CHITIET "+  
												" where NPP_FK ="+nppId+" and KBH_FK= (select kbh_fk from khachhang where pk_seq = '" + khachhang+ "')"  +
												" and KHO_FK=(select kho_fk from ctKhuyenMai where pk_seq='"+schemeList[i]+"')  and SANPHAM_FK = (select pk_seq from sanpham where ma = '" + spIdss[0].trim() + "') "+
												" AND AVAILABLE >0  and NGAYNHAPKHO<=(select ngaynhap from donhang where pk_seq='"+dhId+"')"+
												" order by NGAYNHAPKHO,AVAILABLE ";
										ResultSet rssp=db.get(query);
										double soluongdenghi=slg ;
										
										while(rssp.next() && soluongdenghi >0){
											double soluong_avai= rssp.getDouble("AVAILABLE");
											double soluongcapnhat=0;
											if(soluong_avai >soluongdenghi){
												soluongcapnhat= soluongdenghi;
												soluongdenghi=0;
											}else{
												soluongcapnhat =soluong_avai;
												soluongdenghi =soluongdenghi - soluong_avai;
											}
										
											String _khoid=rssp.getString("kho_fk");
											String _kbhid=rssp.getString("KBH_FK");
											String ngaynhapkho=rssp.getString("NGAYNHAPKHO");
											String ngaynhap=rssp.getString("ngaynhap");
											String sanpham_fk=rssp.getString("sanpham_fk");
											
											query = "Insert into donhang_ctkm_trakm_chitiet(donhangId, ctkmId, trakmId, soxuat, spMa,SANPHAM_FK, soluong, tonggiatri,ngaynhapkho) " + 
													" values('" + dhId + "','" + schemeList[i] + "','" + trakm[count] + "','" + Math.round(Double.parseDouble(soxuatkm[i])) + "','" + spIdss[0].trim() + "',( select pk_seq from SANPHAM where MA = '" + spIdss[0].trim() + "' ),'" + (int)(soluongcapnhat) + "','" + Double.toString(tongtri *soluongcapnhat) + "','"+ngaynhapkho+"')";
											System.out.println("1.Cau lenh chen du lieu: " + query);
											if(!db.update(query))
											{
												; 
												return "552.Loi khi tao moi 'donhang_ctkm_trakm': " + query;
											}
											
											msg=util.Update_NPP_Kho_Sp(ngaynhap,dhId, "updae ĐƠN HÀNG_1454552 apkm", db, _khoid,sanpham_fk, nppId, _kbhid, 0.0, slg, (-1)* slg, 0.0);
											if(msg.length()>0)
											{
												msg = "Lỗi kho khi tạo đơn hàng: " + msg;
												return msg;
											}	
											 
											String msg1=util.Update_NPP_Kho_Sp_Chitiet(ngaynhap, "update donhang DHID ctkm : "+dhId ,  db, _khoid, sanpham_fk, nppId, _kbhid, ngaynhapkho, 0,soluongcapnhat, (-1)*soluongcapnhat, 0, 0);
											if (msg1.length()> 0) 
											{
												msg=msg1;
												return msg1;
											}
										
										}
										rssp.close();
										
										if(soluongdenghi!=0){
											 
											msg=  "Số lượng đề xuất trong lô chi tiết của sản phẩm "+spIdss[0].trim()+"  tới ngày (ngày cấu hình hóa đơn) đơn hàng không còn đủ, " +
													" vui lòng kiểm tra báo cáo ( xuất nhập tồn,tồn hiện tại) theo lô để biết thêm chi tiết ";
											return msg;
											
										}
										
										///////////////////////////////////////////////////////////////////////////////

*/								}
								else
								{
									;
									return  msg + " nên bạn không được hưởng  ctkm '" + (schemeDiengiai[i] == null ? schemeList[i] : schemeDiengiai[i]) + "'.Vui lòng điều chỉnh lại.\n";
								}
							}
						}
						else //KHONG BAO GIO CO TRUOGN HOP NAY< NO CO KO CHO LUU, FIX CHUONG TRINH - DANH SỬA CHO LƯU 30/10
						{
							System.out.println("132.Hinh thuc la tra tien.....");
							
							
							query = "Insert into donhang_ctkm_trakm(donhangId, ctkmId, trakmId, soxuat, tonggiatri) values('" + dhId + "','" + schemeList[i] + "','" + trakm[count] + "','" + Math.round(Double.parseDouble(soxuatkm[i])) + "','" + ttTrakm + "')";
							System.out.println("5.Cau lenh tao moi donhang_ctkm_trakm: " + query);
							if(!db.update(query))
							{
								; 
								return  "662.Loi khi tao moi 'donhang_ctkm_trakm': " + query;
							}
							query = "Insert into donhang_ctkm_trakm_chitiet(donhangId, ctkmId, trakmId, soxuat, tonggiatri) values('" + dhId + "','" + schemeList[i] + "','" + trakm[count] + "','" + Math.round(Double.parseDouble(soxuatkm[i])) + "','" + ttTrakm + "')";
							System.out.println("5.Cau lenh tao moi donhang_ctkm_trakm: " + query);
							if(!db.update(query))
							{
								; 
								return  "662.Loi khi tao moi 'donhang_ctkm_trakm': " + query;
							}
							
							
						}										
					}
					else
					{
						///Chon hinh thuc la tra tien
						System.out.println("123.Hinh thuc la tra tien.....");
					
						
						query = "Insert into donhang_ctkm_trakm(donhangId, ctkmId, trakmId, soxuat, tonggiatri) values('" + dhId + "','" + schemeList[i] + "','" + trakm[count] + "','" + Math.round(Double.parseDouble(soxuatkm[i])) + "','" + ttTrakm + "')";
						if(!db.update(query))
						{
							;
							return  "674.Loi khi tao moi 'donhang_ctkm_trakm': " + query;
						}
						query = "Insert into donhang_ctkm_trakm_chitiet(donhangId, ctkmId, trakmId, soxuat, tonggiatri) values('" + dhId + "','" + schemeList[i] + "','" + trakm[count] + "','" + Math.round(Double.parseDouble(soxuatkm[i])) + "','" + ttTrakm + "')";
						if(!db.update(query))
						{
							;
							return  "674.Loi khi tao moi 'donhang_ctkm_trakm': " + query;
						}
					}
				}
				else
				{
					System.out.println("10.Toi da vao trong nay....");
					if(trakmPrimary[count].equals("0"))
					{
						msg = CreateKhuyenmai(schemeList[i], phanbotheoDH, trakm[count], dhId, mucphanbo, nppId, khachhang, Math.round(Double.parseDouble(soxuatkm[i])), khachhang, db);
						if(msg.length() > 0)
						{
							;
							return  msg;
						}
					}
					else //SCHEME TRẢ TIỀN, CHIẾT KHẤU THÌ CHECK NGÂN SÁCH CHỖ NÀY
					{
						System.out.println("12.Vao trong luu tien trong san pham");
						//LUU Y TRUONG HOP TRA TIEN / CHIET KHAU MA LAI PHAN BO THEO SO LUONG, THI CHO NAY NGUOI DUNG BAT BUOC PHAI KHAI BAO LAI
						
						
						query = "Insert into donhang_ctkm_trakm(donhangId, ctkmId, trakmId, soxuat, tonggiatri) " + 
								" values('" + dhId + "','" + schemeList[i] + "','" + trakm[count] + "','" + Math.round(Double.parseDouble(soxuatkm[i])) + "','" + ttTrakm + "')";
						System.out.println("13.Chen khuyen mai: " + query);
						if(!db.update(query))
						{
							;
							return  "708.Loi khi tao moi 'donhang_ctkm_trakm': " + query;
						}
						query = "Insert into donhang_ctkm_trakm_chitiet(donhangId, ctkmId, trakmId, soxuat, tonggiatri) " + 
								" values('" + dhId + "','" + schemeList[i] + "','" + trakm[count] + "','" + Math.round(Double.parseDouble(soxuatkm[i])) + "','" + ttTrakm + "')";
						System.out.println("13.Chen khuyen mai: " + query);
						if(!db.update(query))
						{
							;
							return  "708.Loi khi tao moi 'donhang_ctkm_trakm': " + query;
						}
					}
				}
			}
			
			return "";
			

		}
		catch (Exception e) 
		{
			try 
			{
				db.getConnection().rollback();
			} catch (SQLException e1) { }

			e.printStackTrace();
			return "Lỗi khi lưu khuyến mại: " + e.getMessage();
		}
		
	}


}
