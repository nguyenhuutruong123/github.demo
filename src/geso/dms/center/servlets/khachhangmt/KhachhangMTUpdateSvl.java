package geso.dms.center.servlets.khachhangmt;

import geso.dms.center.beans.khachhangmt.imp.*;
import geso.dms.center.beans.khachhangmt.*;
import geso.dms.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
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

public class KhachhangMTUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	PrintWriter out;

	public KhachhangMTUpdateSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		

		IKhachhangMT nppBean;

		// this.out = response.getWriter();
		Utility util = new Utility();
		HttpSession session = request.getSession();
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		System.out.println(userId);
		if (userId.length() == 0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		String id = util.getId(querystring);
		nppBean = new KhachhangMT(id);
		nppBean.setUserId(userId);
		nppBean.setArrayDvkdSelected();
		nppBean.setArrayGsbhSelected();
		nppBean.setArrayNgaydhSelected();
		session.setAttribute("nppBean", nppBean);
		String nextJSP = "/AHF/pages/Center/KhachHangMTUpdate.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IKhachhangMT nppBean;

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();

		this.out = response.getWriter();

		Utility util = new Utility();

		String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
		if (id == null)
		{
			nppBean = new KhachhangMT("");
		} else
		{
			nppBean = new KhachhangMT(id);
		}

		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		nppBean.setUserId(userId);

		String nppTen = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppTen")));
		if (nppTen == null)
			nppTen = "";
		nppBean.setTen(nppTen);

		String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("TrangThai")));
		if (trangthai == null)
			trangthai = "0";
		else
			trangthai = "1";
		nppBean.setTrangthai(trangthai);

		String prisec = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("prisec")));
		if (prisec == null)
			prisec = "0";
		else
			prisec = "1";
		nppBean.setPriSec(prisec);

		String diachi = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("DiaChi")));
		if (diachi == null)
			diachi = "";
		nppBean.setDiachi(diachi);
		String diachixhd = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diachixhd")));
		if (diachixhd == null)
			diachixhd = "";
		nppBean.setDiaChiXuatHoaDon(diachixhd);

		String DiaBanHd = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("DiaBanHd")));
		if (DiaBanHd == null)
			DiaBanHd = "";
		nppBean.setDiaBanHd(DiaBanHd);

		//
		String masothue = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("masothue")));
		if (masothue == null)
			masothue = "";
		nppBean.setMaSoThue(masothue);

		//
		String khottid = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khottid")));
		if (khottid == null)
			khottid = "";
		nppBean.setKhoTT(khottid);

		String tpId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tpId")));
		if (tpId == null)
			tpId = "";
		nppBean.setTpId(tpId);

		String qhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("qhId")));
		if (qhId == null)
			qhId = "";
		nppBean.setQhId(qhId);

		String ma = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("maSAP")));
		if (ma == null)
			ma = "";
		nppBean.setMaSAP(ma);

		String dienthoai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("DienThoai")));
		if (dienthoai == null)
			dienthoai = "";
		nppBean.setSodienthoai(dienthoai);

		String kvId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kvId")));
		if (kvId == null)
			kvId = "";
		nppBean.setKvId(kvId);
		
		String ndd = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ndd")));
		if (ndd == null)
			ndd = "";
		nppBean.setNguoidaidien(ndd);

		String npptnId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("npptnTen")));
		if (npptnId == null)
			npptnId = "";
		nppBean.setNpptnId(npptnId);

		String[] dvkd_nccIds = request.getParameterValues("dvkd_nccList");		
		nppBean.setDvkd_NccIds(dvkd_nccIds);

		String[] gsbh_kbhIds = request.getParameterValues("gsbh_kbhList");
		nppBean.setGsbh_KbhIds(gsbh_kbhIds);

		String[] ngaydhIds = request.getParameterValues("ngaydhList");
		nppBean.setNgaydhIds(ngaydhIds);

		String ngaybatdau = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
		if (ngaybatdau == null)
			ngaybatdau = "";
		nppBean.setTungay(ngaybatdau);

		String ngayketthuc = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
		if (ngayketthuc == null)
			ngayketthuc = "";
		nppBean.setDenngay(ngayketthuc);

		String dckho = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dckho")));
		if (dckho == null)
			dckho = "";
		nppBean.setDiaChiKho(dckho);

		String gpkd = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("gpkd")));
		if (gpkd == null)
			gpkd = "";
		nppBean.setGiayphepKD(gpkd);

		String sotk = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sotk")));
		if (sotk == null)
			sotk = "";
		nppBean.setSotk(sotk);

		String fax = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("fax")));
		if (fax == null)
			fax = "";
		nppBean.setFax(fax);
		
		String maddt = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("maddt")));
		if (maddt == null)
			maddt = "";
		nppBean.setMaDDT(maddt);
		
		String diachi2 = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diachi2")));
		if (diachi2 == null)
			diachi2 = "";
		nppBean.setDiachi2(diachi2);
		
		String tsdathang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tsdathang")));
		if (tsdathang == null)
			tsdathang = "";
		nppBean.setTSdathang(tsdathang);

		String NganHangId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("NganHangId")));
		if (NganHangId == null || NganHangId.trim().length() == 0)
			NganHangId = "";
		nppBean.setNganHangId(NganHangId);

		String ChiNhanhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ChiNhanhId")));
		if (ChiNhanhId == null || ChiNhanhId.trim().length() == 0)
			ChiNhanhId = "";
		nppBean.setChiNhanhId(ChiNhanhId);

		String ChuTaiKhoan = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ChuTaiKhoan")));
		if (ChuTaiKhoan == null)
			ChuTaiKhoan = "";
		nppBean.setChuTaiKhoan(ChuTaiKhoan);
		
		// Nh???ng tr?????ng th??m v??o
		String ChuNhaPP = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ChuNhaPhanPhoi")));
		if (ChuNhaPP == null)
			ChuNhaPP = "";
		nppBean.setChuNhaPhanPhoi(ChuNhaPP);
		
		String TonAnToan = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tonantoan")));
		if (TonAnToan == null)
			TonAnToan = "";
		nppBean.setTonAnToan(TonAnToan);
		
		String MuaHangTu = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("noimuahang")));
		if(MuaHangTu==null){
			MuaHangTu="";
		}
		nppBean.setMuaHangTu(MuaHangTu);
		
		String LichDatHang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("LichDatHang")));
		if (LichDatHang == null)
			LichDatHang = "";
		nppBean.setLichDatHang(LichDatHang);
		
		String quytrinhbanhang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("quytrinhbanhang")));
		if (quytrinhbanhang == null)
			quytrinhbanhang = "";
		nppBean.setQuyTrinhBanHang(quytrinhbanhang);
		
		
		
		

		String tentat = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tentat") == null ? "" : request.getParameter("tentat")));
		nppBean.setTentat(tentat);

		String isChiNhanh = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("isChiNhanh") == null ? "0" : request.getParameter("isChiNhanh")));
		nppBean.setIsChiNhanh(isChiNhanh);
	
		String loainpp = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loai") == null ? "0" : request.getParameter("loai")));
		nppBean.setLoaiNpp(loainpp);
		
		String tructhuocId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tructhuocId") == null ? "" : request.getParameter("tructhuocId")));
		nppBean.setTructhuocId(tructhuocId);
		
		String songayno = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("songayno") == null ? "" : request.getParameter("songayno").replaceAll(",", "")));
		nppBean.setSongayno(songayno);

		String sotienno = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sotienno") == null ? "" : request.getParameter("sotienno").replaceAll(",", "")));
		nppBean.setSotienno(sotienno);
		
		 String[] ttppId = request.getParameterValues("ttppId");
		 nppBean.setTtppId(Doisangchuoi(ttppId));
				
		String ngaysua = getDateTime();
		nppBean.setNgaysua(ngaysua);

		String nguoisua = userId;
		nppBean.setNguoisua(nguoisua);

		
		
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		System.out.println("Action luu la gi: "+ action);
		
		if (action.equals("save")){
			boolean error = false;
			/*if (gsbh_kbhIds == null)
			{
				nppBean.setError("* Vui Long Chon Giam Sat Ban Hang Va Kenh Ban Hang");
				nppBean.setMessage("Vui Long Chon Giam Sat Ban Hang Va Kenh Ban Hang");
				error = true;
			}*/

			if (dvkd_nccIds == null)
			{   nppBean.setError("* Vui Long Chon Don Vi Kinh Doanh Va Nha Cung Cap");
				nppBean.setMessage("Vui Long Chon Don Vi Kinh Doanh Va Nha Cung Cap");
				error = true;
			}

			if (kvId.trim().length() == 0)
			{
				nppBean.setError("* Vui Long Chon Khu Vuc");
				nppBean.setMessage("Vui Long Chon Khu Vuc");
				error = true;
			}

			if (dienthoai.trim().length() == 0)
			{
				nppBean.setError("* Vui Long Nhap So Dien Thoai");
				nppBean.setMessage("Vui Long Nhap So Dien Thoai");
				error = true;

			}

			if (qhId.trim().length() == 0)
			{
				nppBean.setError("* Vui Long Chon Quan Huyen");
				nppBean.setMessage("Vui Long Chon Quan Huyen");
				error = true;

			}

			if (tpId.trim().length() == 0)
			{
				nppBean.setError("* Vui Long Chon Thanh Pho");
				nppBean.setMessage("Vui Long Chon Thanh Pho");
				error = true;

			}

			if (diachi.trim().length() == 0)
			{
				nppBean.setError("* Vui Long Nhap Dia Chi");
				nppBean.setMessage("Vui Long Nhap Dia Chi");
				error = true;
			}

			if (nppTen.trim().length() == 0)
			{
				nppBean.setError("* Vui Long Nhap Ten Dai Dien  Kinh Doanh");
				nppBean.setMessage("Vui Long Nhap Ten Dai Dien  Kinh Doanh");
				error = true;
			}

			if (ngaybatdau.trim().length() == 0)
			{
				nppBean.setError("* Vui l??ng ch???n ng??y b???t ?????u");
				nppBean.setMessage("Vui l??ng ch???n ng??y b???t ?????u");
				error = true;
			}
			if(quytrinhbanhang==""){
				nppBean.setError("* Vui l??ng quy tr??nh b??n h??ng!");
				nppBean.setMessage("Vui l??ng quy tr??nh b??n h??ng!");
				error = true;
			}
			if (ngayketthuc.trim().length() == 0)
			{
				nppBean.setError("* Vui l??ng ch???n ng??y k???t th??c");
				nppBean.setMessage("Vui l??ng ch???n ng??y k???t th??c");
				error = true;
			}
			if (dckho.trim().length() == 0)
			{
				
			//	nppBean.setMessage("Vui l??ng nh???p ?????a ch??? kho");
				//error = true;
			}

			if (gpkd.trim().length() == 0)
			{
				//nppBean.setMessage("Vui l??ng nh???p gi???y ph??p kinh doanh");
				//error = true;

			}

			/*if (sotk.trim().length() == 0)
			{
				nppBean.setError("* Vui l??ng nh???p s??? t??i kho???n ng??n h??ng");
				nppBean.setMessage("Vui l??ng nh???p s??? t??i kho???n ng??n h??ng");
				error = true;

			}
			*/
			if (fax.trim().length() == 0)
			{
				nppBean.setError("* Vui l??ng nh???p s??? fax");
				nppBean.setMessage("Vui l??ng nh???p s??? fax");
				error = true;
			}
			if(id == null){
				if(nppBean.checkma(ma)){
					nppBean.setError("* M?? kh??ch h??ng ???? t???n t???i!");
					nppBean.setMessage("M?? Kh??ch h??ng ???? t???n t???i!");
					error = true;
				}
			}
			if(ma==""){
				nppBean.setError("* Vui l??ng nh???p m?? Kh??ch h??ng!");
				nppBean.setMessage("Vui l??ng nh???p m?? Kh??ch h??ng!");
				error = true;
			}
			
			/*if(TonAnToan == null|| TonAnToan==""){				
				nppBean.setError("* Nh???p tr?????ng t???n an to??n!");
				nppBean.setMessage("Nh???p tr?????ng t???n an to??n!");
				error = true;				
			}*/
			
			/*if(MuaHangTu == null|| MuaHangTu==""){				
				nppBean.setError("* Nh???p tr?????ng ch???n mua h??ng t???!");
				nppBean.setMessage("Nh???p tr?????ng ch???n mua h??ng t???!");
				error = true;				
			}*/
			if(khottid==null ||khottid==""){
				nppBean.setError("* Ch???n tr?????ng kho h??ng b??n!");
				nppBean.setMessage("Ch???n tr?????ng kho h??ng b??n!");
				error = true;
			}
		
			
			if (ndd.trim().length() == 0)
			{
				nppBean.setError("* Vui l??ng nh???p t??n ng?????i ?????i di???n");
				nppBean.setMessage("Vui l??ng nh???p t??n ng?????i ?????i di???n");
				error = true;
			}
			/*if(loainpp=="")
			{
				nppBean.setError("* Vui l??ng ch???n lo???i nh?? ph??n ph???i!");
				nppBean.setMessage("Vui l??ng ch???n lo???i nh?? ph??n ph???i!");
				error = true;
			}*/
			
			
			if(!error) {
					if (id == null){
					System.out.println("C?? v??o ???? nh???");
						if (!(nppBean.CreateNpp(request)))
						{
							System.out.println("Tao bi loi!");
							session.setAttribute("nppBean", nppBean);
							nppBean.setUserId(userId);
							String nextJSP = "/AHF/pages/Center/KhachHangMTNew.jsp";
							response.sendRedirect(nextJSP);
						} else
						{
							System.out.println("Tao dung!");
							IKhachhangMTList obj = new KhachhangMTList();
							obj.setUserId(userId);
							session.setAttribute("obj", obj);
							obj.init("");
							String nextJSP = "/AHF/pages/Center/KhachHangMT.jsp";
							response.sendRedirect(nextJSP);
						}

				} else	{
					System.out.println("Kh??ng v??o ah!");
					if (!(nppBean.UpdateNpp(request)))
					{
						session.setAttribute("nppBean", nppBean);
						String nextJSP = "/AHF/pages/Center/KhachHangMTUpdate.jsp";
						response.sendRedirect(nextJSP);
					} else
					{
						IKhachhangMTList obj = new KhachhangMTList();
						obj.setUserId(userId);
						session.setAttribute("obj", obj);
						obj.init("");
						String nextJSP = "/AHF/pages/Center/KhachHangMT.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			} else{
				
				
				nppBean.createRS();
				nppBean.setUserId(userId);
				session.setAttribute("nppBean", nppBean);
				String nextJSP;
				if (id == null)
				{
					nextJSP = "/AHF/pages/Center/KhachHangMTNew.jsp";
				} else
				{
					nextJSP = "/AHF/pages/Center/KhachHangMTUpdate.jsp";
				}
				response.sendRedirect(nextJSP);
			}
			
		} else {
			nppBean.createRS();
			nppBean.setUserId(userId);
			session.setAttribute("nppBean", nppBean);
			String nextJSP;
			if (id == null)
			{
				nextJSP = "/AHF/pages/Center/KhachHangMTNew.jsp";
			} else
			{
				nextJSP = "/AHF/pages/Center/KhachHangMTUpdate.jsp";
			}
			response.sendRedirect(nextJSP);

		}
	
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
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
	
	public static void main(String[] arg)
	{
		String pattern="0000000";
		DecimalFormat df2 = new DecimalFormat(pattern );
		System.out.println(df2.format(111));
	}
	
}
