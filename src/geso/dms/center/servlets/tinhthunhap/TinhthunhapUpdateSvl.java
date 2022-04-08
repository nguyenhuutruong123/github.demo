package geso.dms.center.servlets.tinhthunhap;

import geso.dms.center.beans.tinhthunhap.*;
import geso.dms.center.beans.tinhthunhap.imp.*;
import geso.dms.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

public class TinhthunhapUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;

	public TinhthunhapUpdateSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		ITinhthunhap ttnBean;

		this.out = response.getWriter();
		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		out.println(userId);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

		String id = util.getId(querystring);

		ttnBean = new Tinhthunhap(id);
		ttnBean.setId(id);
		ttnBean.setUserId(userId);

		ttnBean.init();
		String nextJSP = "/AHF/pages/Center/TinhThuNhapUpdate.jsp";
		if (querystring.indexOf("copy") >= 0)
		{
			ttnBean.setId("");
			ttnBean.setThang("");
			ttnBean.setNam("");
			ttnBean.setDiengiai("");
			nextJSP = "/AHF/pages/Center/TinhThuNhapNew.jsp";
		}
		if (querystring.indexOf("display") >= 0)
			nextJSP = "/AHF/pages/Center/TinhThuNhapDisplay.jsp";
		session.setAttribute("ttnBean", ttnBean);
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		ITinhthunhap ttnBean;

		Utility util = new Utility();
		String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
		if (id == null)
		{
			ttnBean = new Tinhthunhap("");
		} else
		{
			ttnBean = new Tinhthunhap(id);
		}

		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		ttnBean.setUserId(userId);

		String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
		if (diengiai == null)
			diengiai = "";
		ttnBean.setDiengiai(diengiai);

		String thang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thang")));
		if (thang == null)
			thang = "";
		ttnBean.setThang(thang);

		String nam = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nam")));
		if (nam == null)
			nam = "";
		ttnBean.setNam(nam);

		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));

		System.out.println("__Action la: " + action);

		List<ITinhthunhapDetail> tcDetailList = new ArrayList<ITinhthunhapDetail>();

		String[] dvkdIds = request.getParameterValues("dvkdIds");
		String[] kbhIds = request.getParameterValues("kbhIds");
		String[] chucvu = request.getParameterValues("chucvu");
		String[] luongcb = request.getParameterValues("luongcb");
		String[] ptluongtg = request.getParameterValues("ptluongtg");
		String[] ptluonghq = request.getParameterValues("ptluonghq");
		String[] baohiemtu = request.getParameterValues("baohiemtu");
		String[] baohiemden = request.getParameterValues("baohiemden");
		String[] baohiem = request.getParameterValues("baohiem");
		String[] congdoan = request.getParameterValues("congdoan");
		String[] tdnc = request.getParameterValues("tdnc");
		String[] kvTen = request.getParameterValues("khuvucTen");

		if (dvkdIds != null)
		{
			ITinhthunhapDetail detail = null;
			for (int i = 0; i < dvkdIds.length; i++)
			{
				System.out.println("Dvkd: " + dvkdIds[i] + " --- Kbh: " + kbhIds[i] + " --- Chuc vu: " + chucvu[i] +
					" --- Luong co ban: " + luongcb[i] + " --- Bao hiem den: " + baohiemden[i]);
				String[] kvIds = request.getParameterValues("khuvuc" + Integer.toString(i));
				String[] NppIds_Checked = request.getParameterValues("NppIdCheck"+ Integer.toString(i));
				String kvId = "";
				String NppId = "";
				if (kvIds != null)
				{
					for (int j = 0; j < kvIds.length; j++)
						kvId += kvIds[j] + ",";

					if (kvId.trim().length() > 0)
						kvId = kvId.substring(0, kvId.length() - 1);
				}
				if (NppIds_Checked != null)
				{
					for (int j = 0; j < NppIds_Checked.length; j++)
					{
						NppId += NppIds_Checked[j] + ",";
					}
					if (NppId.trim().length() > 0)
					{
						NppId = NppId.substring(0, NppId.length() - 1);
					}
				} 
				System.out.println("__Detail: " + i + " --- KV Id: " + kvId + "___NppId_Selected____" + NppId);

				if (kvIds != null && kvId.trim().length() > 0)
				{
					detail = new TinhthunhapDetail();
					detail.setDvkdId(dvkdIds[i]);
					detail.setKbhId(kbhIds[i]);
					detail.setKvId(kvId);
					detail.setNppId(NppId);
					detail.setNppSelected(getNppId_Selected(detail, tcDetailList));
					System.out.println("___Npp đã chọn là ___" + detail.getNppSelected());
					detail.setChucvu(chucvu[i]);

					luongcb[i] = luongcb[i].replaceAll(",", "");
					detail.setLuongCB(luongcb[i]);

					ptluongtg[i] = ptluongtg[i].replaceAll(",", "");
					detail.setPhantramluongTG(ptluongtg[i]);

					ptluonghq[i] = ptluonghq[i].replaceAll(",", "");
					detail.setPhantramluongHQ(ptluonghq[i]);

					detail.setBaohiemtu(baohiemtu[i]);

					baohiemden[i] = baohiemden[i].replaceAll(",", "");
					detail.setBaohiemDen(baohiemden[i]);

					baohiem[i] = baohiem[i].replaceAll(",", "");
					detail.setBaohiem(baohiem[i]);

					congdoan[i] = congdoan[i].replaceAll(",", "");
					detail.setCongdoan(congdoan[i]);

					tdnc[i] = tdnc[i].replaceAll(",", "");
					detail.setThucdatngaycong(tdnc[i]);
					detail.setKvTenSelected(kvTen[i]);
					/************************************************* Nha Phan Phoi *****************************************/
					String[] NppIds = request.getParameterValues("nppId" + Integer.toString(i));
					String[] NppMa = request.getParameterValues("NppMa" + Integer.toString(i));
					String[] NppTen = request.getParameterValues("nppTen" + Integer.toString(i));
					List<INhaPhanPhoi> nppList = new ArrayList<INhaPhanPhoi>();
					if (NppIds != null)
					{
						System.out.println("Tao Npp ");
						for (int ii = 0; ii < NppIds.length; ii++)
						{
							INhaPhanPhoi e = null;
							if (NppIds[ii].trim().length() > 0)
							{
								e = new NhaPhanPhoi();
								e.setId(NppIds[ii]);
								e.setTen(NppTen[ii]);
								nppList.add(e);
							}
						}
					}
					detail.setNhanPhanPhoiList(nppList);
					/******************************************************************************************/

					String[] maDetail = request.getParameterValues("maDetail" + Integer.toString(i));
					String[] noidung = request.getParameterValues("noidung" + Integer.toString(i));
					String[] trongso = request.getParameterValues("trongso" + Integer.toString(i));
					String[] mucbaohiem = request.getParameterValues("mucbaohiem" + Integer.toString(i));
					String[] thuongvuotmuc = request.getParameterValues("thuongvuotmuc" + Integer.toString(i));

					detail.setMaDetail(maDetail);
					detail.setNoidung(noidung);
					detail.setTrongso(trongso);
					detail.setMucbaohiem(mucbaohiem);
					detail.setThuongSRvuotmuc(thuongvuotmuc);

					// Thuong vuot muc
					String[] tvmNhomthuong = request.getParameterValues("tvmNhomthuong" + Integer.toString(i));
					String[] tvmTumuc = request.getParameterValues("tvmTumuc" + Integer.toString(i));
					String[] tvmDenmuc = request.getParameterValues("tvmDenmuc" + Integer.toString(i));
					String[] tvmThuong = request.getParameterValues("tvmThuong" + Integer.toString(i));

					List<IThuongvuotmuc> tvmList = new ArrayList<IThuongvuotmuc>();
					for (int ii = 0; ii < tvmNhomthuong.length; ii++)
					{
						if (tvmTumuc[ii].trim().length() > 0 && tvmDenmuc[ii].trim().length() > 0 &&
							tvmThuong[ii].trim().length() > 0)
						{
							IThuongvuotmuc tvm = new Thuongvuotmuc();

							tvm.setNhomthuong(tvmNhomthuong[ii].trim());
							tvm.setTumuc(tvmTumuc[ii].trim().replaceAll(",", ""));
							tvm.setDenmuc(tvmDenmuc[ii].trim().replaceAll(",", ""));
							tvm.setThuong(tvmThuong[ii].trim().replaceAll(",", ""));
							tvmList.add(tvm);
						}
					}
					detail.setThuongvuotmucList(tvmList);

					List<INhanvien> nvDetailList = new ArrayList<INhanvien>();

					String[] nppId = request.getParameterValues("npp" + Integer.toString(i) + "Id");
					String[] nvId = request.getParameterValues("nv" + Integer.toString(i) + "Id");
					String[] nvTen = request.getParameterValues("nv" + Integer.toString(i) + "Ten");
					String[] nvLuongcb = request.getParameterValues("nv" + Integer.toString(i) + "Luongcb");

					if (nvId != null)
					{
						for (int ii = 0; ii < nvId.length; ii++)
						{
							INhanvien nv = null;
							if (nvLuongcb[ii].trim().length() > 0)
							{
								nv = new Nhanvien();

								nv.setNppTen(nppId[ii]);
								nv.setId(nvId[ii]);
								nv.setTen(nvTen[ii]);
								nv.setLuongCB(nvLuongcb[ii].replace(",", ""));
								nvDetailList.add(nv);
							}
						}
					}

					if (action.equals("save"))
					{
						if (nvDetailList.size() > 0)
						{
							detail.setNhanvienList(nvDetailList);
						} else
						{
							if (chucvu[i].trim().length() > 0)
							{
								detail.InitNhanVien();
								detail.InitNhaPhanPhoi();
							}
						}
					} else
					{
						if (chucvu[i].trim().length() > 0)
						{
							detail.InitNhanVien();
							detail.InitNhaPhanPhoi();
						}
					}

					tcDetailList.add(detail);
				}
			}
			// System.out.println("+++So san pham: " + tcDetailList.size());
			ttnBean.setTieuchiDetail(tcDetailList);
		}

		if (action.equals("save"))
		{
			if (id == null)
			{
				if (!ttnBean.createKhl())
				{
					System.out.println("....Khong the luu...");
					ttnBean.setUserId(userId);
					ttnBean.createRs();

					session.setAttribute("userId", userId);
					session.setAttribute("ttnBean", ttnBean);

					String nextJSP = "/AHF/pages/Center/TinhThuNhapNew.jsp";
					response.sendRedirect(nextJSP);
				} else
				{
					ITinhthunhapList obj = new TinhthunhapList();
					obj.setUserId(userId);
					obj.init("");

					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);

					response.sendRedirect("/AHF/pages/Center/TinhThuNhap.jsp");
				}
			} else
			{
				if (!(ttnBean.updateKhl()))
				{
					ttnBean.setUserId(userId);
					ttnBean.createRs();

					session.setAttribute("userId", userId);
					session.setAttribute("ttnBean", ttnBean);

					String nextJSP = "/AHF/pages/Center/TinhThuNhapUpdate.jsp";
					response.sendRedirect(nextJSP);
				} else
				{
					ITinhthunhapList obj = new TinhthunhapList();
					obj.setUserId(userId);
					obj.init("");

					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);

					response.sendRedirect("/AHF/pages/Center/TinhThuNhap.jsp");
				}
			}
		} else
		{
			ttnBean.createRs();
			session.setAttribute("userId", userId);

			session.setAttribute("ttnBean", ttnBean);
			String nextJSP;
			if (id == null)
			{
				nextJSP = "/AHF/pages/Center/TinhThuNhapNew.jsp";
			} else
			{
				nextJSP = "/AHF/pages/Center/TinhThuNhapUpdate.jsp";
			}
			response.sendRedirect(nextJSP);
		}
	}

	private String getNppId_Selected(ITinhthunhapDetail detail, List<ITinhthunhapDetail> tcDetailList)
	{
		String NppIds = "";

		for (int i = 0; i < tcDetailList.size(); i++)
		{
			ITinhthunhapDetail ttnDetai = tcDetailList.get(i);
			NppIds += ttnDetai.GetNppId();
		}
		return NppIds;
	}

}
