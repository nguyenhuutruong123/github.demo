package geso.dms.distributor.servlets.dondathang;

import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.SendMail;
import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.dondathang.IErpDondathangNpp;
import geso.dms.distributor.beans.dondathang.IErpDondathangNppList;
import geso.dms.distributor.beans.dondathang.imp.ErpDondathangNpp;
import geso.dms.distributor.beans.dondathang.imp.ErpDondathangNppList;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpDondathangNppSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	public ErpDondathangNppSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpDondathangNppList obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();	    
		
		// kiểm tra quyền xem menu
	    String param="";
		if( Utility.CheckRuleUser( session,  request, response, "ErpDondathangNppSvl", param, Utility.XEM ) || Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
			return;
		}

		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		if (userId.length()==0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

		String action = util.getAction(querystring);

		String locgiaQUYDOI = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("locgiaQUYDOI"));
		if(locgiaQUYDOI == null)
			locgiaQUYDOI = "0";

		System.out.println("ACTION LA: " + action );
		if(locgiaQUYDOI.equals("1"))
		{
			String spMa = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("spMa"));
			if(spMa == null)
				spMa = "";

			String dvt = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvt"));
			if(dvt == null)
				dvt = "";

			String dvkdId = "";
			if(session.getAttribute("dvkdId") != null )
				dvkdId = (String) session.getAttribute("dvkdId");

			String kbhId = "";
			if(session.getAttribute("kbhId") != null )
				kbhId = (String) session.getAttribute("kbhId");

			String nppId = "";
			if(session.getAttribute("nppId") != null )
				nppId = (String) session.getAttribute("nppId");

			String query = (String)request.getQueryString();
			spMa = new String(query.substring(query.indexOf("&spMa=") + 6, query.indexOf("&dvt=")).getBytes("UTF-8"), "UTF-8");
			spMa = URLDecoder.decode(spMa, "UTF-8").replace("+", " ");

			dvt = new String(query.substring(query.indexOf("&dvt=") + 5, query.indexOf("&locgiaQUYDOI")).getBytes("UTF-8"), "UTF-8");
			dvt = URLDecoder.decode(dvt, "UTF-8").replace("+", " ");

			//System.out.println(" -- MA SP: " + spMa + " -- DVT: " + dvt );
			//spMa = URLDecoder.decode(spMa, "UTF-8").replace("+", " ");
			//dvt = URLDecoder.decode(dvt, "UTF-8").replace("+", " ");

			if(spMa.trim().length() > 0 && dvt.trim().length() > 0 )
			{
				dbutils db = new dbutils();

				String   command = 
					" select a.DVDL_FK as dvCHUAN, ( select PK_SEQ from DONVIDOLUONG where DONVI = ? ) as dvNEW, " + 
					"	isnull( ( select SOLUONG1 / SOLUONG2 from QUYCACH where SANPHAM_FK = a.pk_seq and DVDL1_FK = a.DVDL_FK and DVDL2_FK = ( select PK_SEQ from DONVIDOLUONG where DONVI = ? ) ), 0 ) as quydoi,  	  " +  
					" isnull( ( select GIAMUANPP * ( 1 - (isnull( ( select chietkhau from BANGGIAMUANPP_NPP where banggiamuaNPP_FK = bg_sp.bgmuaNPP_FK and NPP_FK = ? ), 0) + isnull(bg_sp.ptChietKhau, 0)) / 100 )  				" +
					"from BGMUANPP_SANPHAM bg_sp 			    where SANPHAM_FK = a.pk_seq  " +
					"and BGMUANPP_FK in ( select top(1) PK_SEQ from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK where bg.TRANGTHAI = '1'" +
					" and bg_npp.NPP_FK = ? and bg.DVKD_FK = ? and bg.KENH_FK = ? order by bg.TUNGAY desc ) ), 0) as GiaMua "+ 
					" from SANPHAM a where a.MA = ?  ";

				System.out.println("Lay don gia san pham: " + command);
				String kq  = "0";
				ResultSet rs = db.getByPreparedStatement(command, new Object[] {dvt,dvt,nppId,nppId,dvkdId,kbhId,spMa});
				try
				{
					if(rs.next())
					{
						String dvCHUAN = rs.getString("dvCHUAN");
						String dvNEW = rs.getString("dvNEW");
						double quydoi = rs.getDouble("quydoi");
						double dongia = rs.getDouble("giamua");

						//System.out.println("DON VI NEW: " + dvNEW);
						if(dvCHUAN.equals(dvNEW))
							kq = Double.toString(dongia);
						else
							kq = Double.toString(dongia * quydoi );

					}
					rs.close();
				} 
				catch (Exception e) { kq = "0"; }

				db.shutDown();

				System.out.println("GIA: " + kq);
				out.write(kq);
			}
			else
			{
				out.write("0");
			}

			return;
		}else if(locgiaQUYDOI.equals("addSKU"))
		{
			
			String spMa = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("spMa"));
			if(spMa == null)
				spMa = "";

			String dvt = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvt"));
			if(dvt == null)
				dvt = "";
			
			String dhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dhId"));
			if(dhId == null)
				dhId = "";
/*
			String query = (String)request.getQueryString();
			spMa = new String(query.substring(query.indexOf("&spMa=") + 6, query.indexOf("&dhId=")).getBytes("UTF-8"), "UTF-8");
			spMa = URLDecoder.decode(spMa, "UTF-8").replace("+", " ");

			dhId = new String(query.substring(query.indexOf("&dhId=") + 6, query.indexOf("&locgiaQUYDOI")).getBytes("UTF-8"), "UTF-8");
			dhId = URLDecoder.decode(dvt, "UTF-8").replace("+", " ");*/

			if(spMa.trim().length() > 0 && dhId.trim().length() > 0 )
			{
				dbutils db = new dbutils();
				String   command =
						"select count(*) as SoDong from Erp_DonDatHang_SanPham where dondathang_fk=? and " +
						" SanPham_fk=(select pk_Seq from SanPham where ma=? )"; 
				System.out.println("Erp_DonDatHang_SanPham: " + command);
				String kq  = "-1";
				ResultSet rs = db.getByPreparedStatement(command, new Object[]{dhId,spMa});
				try
				{
					if(rs.next())
					{
						kq=rs.getString("SoDong");
					}
					rs.close();
				} 
				catch (Exception e) { kq = "-1"; }
				db.shutDown();
				System.out.println("GIA: " + kq);
				out.write(kq);
			}
			else
			{
				out.write("-1");
			}
		}
			
		else
		{
			String lsxId = util.getId(querystring);
			obj = new ErpDondathangNppList();
			obj.setUserId(userId);
			

			String loaidonhang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaidonhang"));
			if(loaidonhang == null)
				loaidonhang = "0";
			obj.setLoaidonhang(loaidonhang);
			System.out.println("---LOAI DON HANG: " + loaidonhang);

			if (action.equals("delete") )
			{	
				String msg = this.DeleteChuyenKho(lsxId, obj);
				obj.setMsg(msg);
			}
			else
			{
				if(action.equals("chot"))
				{
					String msg = this.Chot(lsxId, obj);
					obj.setMsg(msg);
				}
			}

			
			obj.init("");

			session.setAttribute("obj", obj);

			String nextJSP = "/AHF/pages/Distributor/ErpDonDatHangNpp.jsp";
			response.sendRedirect(nextJSP);
		}
	}

	private String Chot(String lsxId, IErpDondathangNppList obj) 
	{
		// Kiểm tra id có hợp lệ không
	    if(lsxId != null && lsxId.trim().length() > 0 
	    		&& !Utility.KiemTra_PK_SEQ_HopLe(lsxId, "ERP_Dondathang", obj.getDb())){
	    	obj.DBclose();
    		return "Id không hợp lệ";
    	}
		dbutils db = new dbutils();
		String msg = "";
		String param = "";
		
		//if(obj.getView().trim().length() > 0) param ="&view="+obj.getView();
		int[] quyen = Utility.Getquyen("ErpDondathangNppSvl", param,obj.getUserId());
		if(quyen[Utility.CHOT]!=1)
		{
			
			return "";
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);

			String query = "update ERP_Dondathang set trangthai = '0', NPP_DACHOT = '1',TT_DuyetLai=0 where pk_seq = ? and TrangThai=0";
			if(db.updateQueryByPreparedStatement(query, new Object[]{lsxId})<1)
			{
				msg = "Khong the chot ";
				db.getConnection().rollback();
				return msg;
			}
			
			query=" delete from ERP_DONDATHANG_SANPHAM_LOG where DONDATHANG_FK=?";
			if(db.updateQueryByPreparedStatement(query,new Object[]{lsxId}) == -1)
			{
				msg = "Khong the chot: ";
				db.getConnection().rollback();
				return msg;
			}
			
			//Ghi nhan lai luc npp dat hang de so sanh xem tt co thay doi hay khong.??
			query=
					" INSERT INTO ERP_DONDATHANG_SANPHAM_LOG(DONDATHANG_FK,SANPHAM_FK,SOLUONG,DONGIA,DVDL_FK,CHIETKHAU,SOTT,THUEVAT,DonGiaGoc,BANGGIAMUANPP_FK) "+ 
					" SELECT DONDATHANG_FK,SANPHAM_FK,SOLUONG,DONGIA,DVDL_FK,CHIETKHAU,SOTT,THUEVAT,DonGiaGoc,BANGGIAMUANPP_FK  "+
					" FROM ERP_DONDATHANG_SANPHAM" +
					" where DONDATHANG_FK=?   ";
			if(db.updateQueryByPreparedStatement(query, new Object[]{lsxId}) < 1)
			{
				msg = "Khong the chot: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			/*String email = "";
			String noidung = "";
			
			query = "select distinct EMAIL from nhanvien a inner join PHAMVIHOATDONG b on a.PK_SEQ = b.Nhanvien_fk "+
					"where a.NHANMAILDMS = 1 and b.Npp_fk = (select npp_fk from ERP_Dondathang where PK_SEQ = "+lsxId+")";
			ResultSet rs = db.get(query);
			while(rs.next())
			{
				email += rs.getString("email") + ",";
			}
			rs.close();
			System.out.println("[email] "+email);
			
			String manpp = "", tennpp = "";
			query = 
					" select b.ma, b.ten from ERP_DONDATHANG a inner join nhaphanphoi b on a.npp_fk = b.pk_seq"+
					" where a.pk_seq = '"+lsxId+"'   ";
			rs = db.get(query);
			if(rs.next())
			{
				manpp = rs.getString("ma");
				tennpp = rs.getString("ten");
			}
			rs.close();
			noidung = 
					"Ngày: "+getDateTime()+" - Mã NPP: "+manpp+" - Tên NPP: "+tennpp+" đã đặt hàng.<br/>"+
					"Thông tin cụ thể: <br/>"+
					"<table style='border: 1px solid black; border-collapse: collapse;' >"+
					"<tr >"+
					"	<th style='border: 1px solid black; '>Mã sản <br/>phẩm</th>"+
					"	<th style='border: 1px solid black; '>Tên sản phẩm</th>"+
					"	<th style='border: 1px solid black; '>Số lượng</th>"+
					"	<th style='border: 1px solid black; '>Thành tiền</th>"+
					"</tr>";
			NumberFormat formater = new DecimalFormat("##,###,###");
			query = 
					" SELECT b.ma, b.ten, a.SOLUONG, a.DONGIA "+
					" FROM ERP_DONDATHANG_SANPHAM a inner join sanpham b on a.sanpham_fk = b.pk_seq " +
					" where DONDATHANG_FK='"+lsxId+"'   ";
			rs = db.get(query);
			while(rs.next())
			{
				String ma = rs.getString("ma");
				String ten = rs.getString("ten");
				double soluong = rs.getDouble("soluong");
				double dongia = rs.getDouble("dongia");
				double thanhtien = soluong * dongia;
				noidung += 
					"<tr >"+
					"	<td style='border: 1px solid black; '>"+ma+"</th>"+
					"	<td style='border: 1px solid black; '>"+ten+"</th>"+
					"	<td style='border: 1px solid black; '>"+formater.format(soluong)+"</th>"+
					"	<td style='border: 1px solid black; '>"+formater.format(thanhtien)+"</th>"+
					"</tr>";
			}
			
			noidung += "</table><br/>Vui lòng xử lý đơn hàng.";
			System.out.println("[noidung] "+noidung);

			SendMail mail = new SendMail();
			msg = mail.postMailHTML(email, "", "SGP - NPP đặt hàng", noidung);
			if(msg.trim().length() > 0)
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
    			return msg;
			}*/
			
			db.getConnection().commit();
			db.shutDown();
		}
		catch (Exception e) 
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		finally
		{
			db.shutDown();
		}
		return "";
	}

	private String DeleteChuyenKho(String lsxId, IErpDondathangNppList obj)
	{
		// Kiểm tra id có hợp lệ không
	    if(lsxId != null && lsxId.trim().length() > 0 
	    		&& !Utility.KiemTra_PK_SEQ_HopLe(lsxId, "ERP_Dondathang", obj.getDb())){
	    	obj.DBclose();
    		return "Id không hợp lệ";
    	}
		dbutils db = new dbutils();
		String msg = "";
		String param = "";
		
		//if(obj.getView().trim().length() > 0) param ="&view="+obj.getView();
		int[] quyen = Utility.Getquyen("ErpDondathangNppSvl", param,obj.getUserId());
		if(quyen[Utility.XOA]!=1)
		{
			System.out.println("Khong co quyen xoa");
			return "";
		}
		try
		{
			db.getConnection().setAutoCommit(false);

			String query = "update ERP_Dondathang set trangthai = '3' where pk_seq = ?";
			if(db.updateQueryByPreparedStatement(query, new Object[]{lsxId}) < 1)
			{
				msg = "1.Khong the xoa đơn đặt hàng";
				db.getConnection().rollback();
				return msg;
			}

			db.getConnection().commit();
			db.shutDown();
		}
		catch (Exception e) 
		{
			db.update("roolback");
			db.shutDown();
			return "Exception: " + e.getMessage();
		}

		return "";

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		

		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		if (action == null)
		{
			action = "";
		}

		String loaidonhang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaidonhang"));
		if(loaidonhang == null)
			loaidonhang = "0";


		IErpDondathangNppList obj = new ErpDondathangNppList();
		obj.setLoaidonhang(loaidonhang);

		Utility util = new Utility();

		HttpSession session = request.getSession();
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"))); 
		obj.setUserId(userId);
		
		// kiểm tra quyền xem menu
	    String param="";
		if( Utility.CheckRuleUser( session,  request, response, "ErpDondathangNppSvl", param, Utility.XEM ) || Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
			return;
		}

		if(action.equals("Tao moi"))
		{
			//check quyền
			int[] quyen = Utility.Getquyen("ErpDondathangNppSvl", "",obj.getUserId());
			if(quyen[Utility.THEM]==1){
				IErpDondathangNpp lsxBean = new ErpDondathangNpp();
				lsxBean.setLoaidonhang(loaidonhang);
				lsxBean.setUserId(userId);

				lsxBean.createRs();
				session.setAttribute("dvkdId", lsxBean.getDvkdId());
				session.setAttribute("kbhId", lsxBean.getKbhId());
				session.setAttribute("nppId", lsxBean.getNppId());
				session.setAttribute("ngaydonhang", lsxBean.getNgayyeucau());
				session.setAttribute("khoId", lsxBean.getKhoNhapId());
				session.setAttribute("lsxBean", lsxBean);

				String nextJSP = "/AHF/pages/Distributor/ErpDonDatHangNppNew.jsp";
				response.sendRedirect(nextJSP);
			}
			else{
				String search = getSearchQuery(request, obj);
				obj.setUserId(userId);
				obj.init(search);
				session.setAttribute("obj", obj);  	
				session.setAttribute("userId", userId);

				String nextJSP = "/AHF/pages/Distributor/ErpDonDatHangNpp.jsp";
				response.sendRedirect(nextJSP);
			}
			
		}
		else
		{
			if(action.equals("view") || action.equals("next") || action.equals("prev"))
			{
				String search = getSearchQuery(request, obj);
				obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
				obj.setUserId(userId);
				obj.init(search);
				obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
				session.setAttribute("obj", obj);

				String nextJSP = "/AHF/pages/Distributor/ErpDonDatHangNpp.jsp";
				response.sendRedirect(nextJSP);
			}
			else
			{
				String search = getSearchQuery(request, obj);
				obj.setUserId(userId);
				obj.init(search);
				session.setAttribute("obj", obj);  	
				session.setAttribute("userId", userId);

				String nextJSP = "/AHF/pages/Distributor/ErpDonDatHangNpp.jsp";
				response.sendRedirect(nextJSP);
			}
		}
	}

	private String getSearchQuery(HttpServletRequest request, IErpDondathangNppList obj)
	{
		String query =
				"\n select a.PK_SEQ, a.trangthai, a.ngaydonhang, c.ten as nppTEN, isnull(b.ten, d.ten) + '-' + isnull(b.diengiai, d.diengiai) as khoTEN, NV.TEN as nguoitao, isnull(b.ten, d.ten) + '-' + isnull(b.diengiai, d.diengiai) as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, a.NPP_DACHOT ,  " +
				"\n    	(	SELECT COUNT(*) "+   
				"\n    		FROM ERP_YCXUATKHO_DDH "+   
				"\n    			INNER JOIN ERP_YCXUATKHO ON ERP_YCXUATKHO_DDH.ycxk_fk=ERP_YCXUATKHO.PK_SEQ "+   
				"\n    		WHERE ERP_YCXUATKHO_DDH.ddh_fk=a.PK_SEQ AND ERP_YCXUATKHO.TRANGTHAI=2 "+   
				"\n    	) as DaXuatKho,a.TT_DuyetLai "+
				"\n from ERP_Dondathang a left join KHOTT b on a.kho_fk = b.pk_seq inner join NHAPHANPHOI c on a.NPP_FK = c.pk_seq  " +
				"\n left join kho d on a.kho_fk = d.pk_seq "+
				"\n inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
				"\n inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.pk_seq > 0 ";

		String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);

		String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"));
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);

		String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);

		String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
		if(nppId == null)
			nppId = "";
		obj.setNppTen(nppId);
		obj.setNppId(nppId);
		
		/*if(tungay.length() > 0)
			query += " and a.ngaydonhang >= '" + tungay + "'";*/
		if(tungay.length() > 0)
		{
			query += " and a.ngaydonhang >= ?";
			obj.getDataSearch().add(tungay);
		}
			

		/*if(denngay.length() > 0)
			query += " and a.ngaydonhang <= '" + denngay + "'";*/
		if(denngay.length() > 0)
		{
			query += " and a.ngaydonhang <= ?";
			obj.getDataSearch().add(denngay);
		}
			

		/*if(trangthai.length() > 0)
			query += " and a.TrangThai = '" + trangthai + "'";*/
		if(trangthai.length() > 0)
		{
			query += " and a.TrangThai = ?";
			obj.getDataSearch().add(trangthai);
		}
			

		/*if(nppId.length() > 0){
			query += " and a.NPP_FK= '" + nppId + "'";
		}*/
		if(nppId.length() > 0){
			query += " and a.NPP_FK= ?";
			obj.getDataSearch().add(nppId);
		}
		System.out.println("ugyuyguy" + nppId);
		System.out.print(query);
		return query;
	}

	public String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}


}
