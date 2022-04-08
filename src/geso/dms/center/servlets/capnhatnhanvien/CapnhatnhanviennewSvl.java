package geso.dms.center.servlets.capnhatnhanvien;

import geso.dms.center.beans.capnhatnhanvien.ICapnhatnhanvien;
import geso.dms.center.beans.capnhatnhanvien.ICapnhatnhanvienList;
import geso.dms.center.beans.capnhatnhanvien.imp.Capnhatnhanvien;
import geso.dms.center.beans.capnhatnhanvien.imp.CapnhatnhanvienList;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CapnhatnhanviennewSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public CapnhatnhanviennewSvl()
	{
		super();
	}

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
		String userId = util.getUserId(querystring);

		String id = util.getId(querystring);
		System.out.println("UserId nhanvien " + id);
		String action = util.getAction(querystring);
		ICapnhatnhanvien obj = new Capnhatnhanvien(id);
		obj.setuserId(userId);

		obj.CreateQuyen(null);
		obj.CreateKenh(null);
		obj.CreateNpp(null);
		obj.CreateSanpham(null);
		obj.CreateKho(null);
		obj.CreateNhomskus(null);
		obj.CreateRS();
		obj.init();

		
		
		session.setAttribute("obj", obj);
		String nextJsp = "";
		if(action.equals("update"))
		{
			nextJsp = "/AHF/pages/Center/CapNhatNhanVienUpdate.jsp";
		}else{
			nextJsp = "/AHF/pages/Center/CapNhatNhanVienDisplay.jsp";
		}
		response.sendRedirect(nextJsp);
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		ICapnhatnhanvien obj = new Capnhatnhanvien();
		HttpSession session = request.getSession();
		Utility util = new Utility();
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		if (userId == null)
			userId = "";
		obj.setuserId(userId);

		String Id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Id")));
		if (Id == null)
			Id = "";
		obj.setId(Id);

		String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
		if (nppId == null)
			nppId = "";
		obj.setnppId(nppId);

		String Ten = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ten")));
		if (Ten == null)
			Ten = "";
		obj.setTen(Ten);

		String Ngaysinh = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaysinh")));
		if (Ngaysinh == null)
			Ngaysinh = "";
		obj.setngaysinh(Ngaysinh);

		System.out.println("ngay sinh" + Ngaysinh);

		String Dienthoai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dienthoai")));
		if (Dienthoai == null)
			Dienthoai = "";
		obj.setdienthoai(Dienthoai);

		String Trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));
		if (Trangthai == null)
			Trangthai = "0";
		obj.settrangthai(Trangthai);

		String Phanloai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("phanloai")));
		if (Phanloai == null)
			Phanloai = "";
		obj.setphanloai(Phanloai);

		String Email = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("email")));
		if (Email == null)
			Email = "";
		obj.setemail(Email);
		System.out.println("Email" + Email);
		String Tendangnhap = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tendangnhap")));
		if (Tendangnhap == null)
			Tendangnhap = "";
		obj.settendangnhap(Tendangnhap);

		
		boolean isValidated = false;
		String mk = request.getParameter("matkhau")== null ? "": request.getParameter("matkhau").trim()  ;		
		String matkhau = geso.dms.center.util.Utility.antiSQLInspection(mk);
		if(mk.equals(matkhau))
			isValidated = true;
		else
			obj.setmsg("Mật khẩu tồn tại ký tự @  ");
		
		obj.setmatkhau(matkhau);
		
		
		

		String vungId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")));
		if (vungId == null)
			vungId = "";
		obj.setvungId(vungId);

		String khuvucId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")));
		if (khuvucId == null)
			khuvucId = "";
		obj.setkhuvucId(khuvucId);
		
		String nhanmaildms = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhanmaildms")));
		if (nhanmaildms == null)
			nhanmaildms = "0";
		obj.setNhanmaildms(nhanmaildms);
		
		String[] VungId = request.getParameterValues("vung");
		String vungIds="";
		if(VungId!=null)
		{
			for(int i=0;i<VungId.length;i++)
			{
				vungIds+=VungId[i]+",";	
			}
			if(vungIds.length()>0)
			{
				vungIds=vungIds.substring(0,vungIds.length()-1);
			}
		}
		obj.setVungId(vungIds);

		String[] KhuvucId = request.getParameterValues("khuvuc");
		String khuvucIds="";
		if(KhuvucId!=null)
		{
			for(int i=0;i<KhuvucId.length;i++)
			{
				khuvucIds+=KhuvucId[i]+",";	
			}
			if(khuvucIds.length()>0)
			{
				khuvucIds=khuvucIds.substring(0,khuvucIds.length()-1);
			}
		}
		obj.setKhuvucId(khuvucIds);

		String nhanhangId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhanhangId")));
		if (nhanhangId == null)
			nhanhangId = "";
		obj.setnhanhangId(nhanhangId);
		
		String sohoadontu = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sohoadontu")));
		if (sohoadontu == null)
			sohoadontu = "";
		obj.setSohoadontu(sohoadontu);
		
		String sohoadonden = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sohoadonden")));
		if (sohoadonden == null)
			sohoadonden = "";
		obj.setSohoadonden(sohoadonden);

		String chungloaiId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chungloaiId")));
		if (chungloaiId == null)
			chungloaiId = "";
		obj.setchungloaiId(chungloaiId);

		String loai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loai")));
		if (loai == null) loai = "";
		obj.setLoai(loai);

		String loaiId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaiId")));
		if (loaiId == null) loaiId = "";
		obj.setLoaiId(loaiId);
		
		String dmquyenId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dmquyenId")));
		if (dmquyenId == null) dmquyenId = "";
		obj.setdmquyenId(dmquyenId);
		
		String[] kenhId = request.getParameterValues("kenhId");
		String kenhIds="";
		if(kenhId!=null)
		{
			for(int i=0;i<kenhId.length;i++)
			{
				kenhIds+=kenhId[i]+",";	
			}
			if(kenhIds.length()>0)
			{
				kenhIds=kenhIds.substring(0,kenhIds.length()-1);
			}
		}
		obj.setKenhId(kenhIds);

		String quyen[] = request.getParameterValues("quyen");
		obj.CreateQuyen(quyen);

		String chon = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chon")));
		if (chon == null)
			chon = "1";
		
		
		String activeTab = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("activeTab")));
		if (activeTab == null)
			activeTab = "";
		obj.setActiveTab(activeTab);
		
		System.out.println("chon :" + chon);
		obj.setchon(chon);

		String kenh[] = request.getParameterValues("kenh");
		// System.out.println("so chuoi:" +kenh.length);
		obj.CreateKenh(kenh);

		String npp[] = request.getParameterValues("npp");
		obj.CreateNpp(npp);
		String nppIds="";
		if(npp!=null)
		{
			for(int i=0;i<npp.length;i++)
			{
				nppIds+=npp[i]+",";	
			}
			if(nppIds.length()>0)
			{
				nppIds=nppIds.substring(0,nppIds.length()-1);
			}
		}
		obj.setNppIds(nppIds);
		
		
		String ttIds="";
		
		String[] ttId = request.getParameterValues("ttId");
		if (ttId != null)
		{
			int size = ttId.length;
			for (int i = 0; i < size; i++)
			{
				ttIds += ttId[i] + ",";
			}
			if (ttIds.length() > 0)
			{
				ttIds = ttIds.substring(0, ttIds.length() - 1);
			}
		}
	
		obj.setTtId(ttIds);
		
		String qhIds="";
		String[] qhId = request.getParameterValues("qhId");
		if (qhId != null)
		{
			int size = qhId.length;
			for (int i = 0; i < size; i++)
			{
				qhIds += qhId[i] + ",";
			}
			if (qhIds.length() > 0)
			{
				qhIds = qhIds.substring(0, qhIds.length() - 1);
			}
		}
		obj.setTtId(ttIds);
		

		String sanpham[] = request.getParameterValues("sanpham");

		obj.CreateSanpham(sanpham);
		
		String kho[] = request.getParameterValues("kho");
		
		obj.CreateKho(kho);
		
		String nhomskus[] = request.getParameterValues("nhomskus");
		
		obj.CreateNhomskus(nhomskus);
		
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		if( isValidated && action.equals("save"))
		{
			if (!obj.save())
			{
				obj.CreateRS();
				obj.CreateKenh(kenh);
				obj.CreateQuyen(quyen);
				obj.CreateSanpham(sanpham);
				obj.CreateKho(kho);
				obj.CreateNhomskus(nhomskus);
				obj.CreateNpp(npp);
				session.setAttribute("obj", obj);
				response.sendRedirect("/AHF/pages/Center/CapNhatNhanVienUpdate.jsp");
			} 
			else
			{
				ICapnhatnhanvienList obj1 = new CapnhatnhanvienList();
				obj1.setuserId(userId);
				obj1.init("");
				session.setAttribute("obj", obj1);
				response.sendRedirect("/AHF/pages/Center/CapNhatNhanVien.jsp");

			}
		} 
		else
		{
			obj.CreateRS();
			// obj.init();
			obj.CreateKenh(kenh);
			obj.CreateQuyen(quyen);
			obj.CreateSanpham(sanpham);
			obj.CreateKho(kho);
			obj.CreateNhomskus(nhomskus);
			obj.CreateNpp(npp);
			session.setAttribute("obj", obj);
			response.sendRedirect("/AHF/pages/Center/CapNhatNhanVienUpdate.jsp");
		}

	}

}
