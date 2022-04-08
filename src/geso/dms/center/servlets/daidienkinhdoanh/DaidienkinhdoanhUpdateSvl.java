package geso.dms.center.servlets.daidienkinhdoanh;

import geso.dms.center.beans.daidienkinhdoanh.imp.*;
import geso.dms.center.beans.daidienkinhdoanh.*;
import geso.dms.center.util.Utility;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

public class DaidienkinhdoanhUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	PrintWriter out;

	public DaidienkinhdoanhUpdateSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		IDaidienkinhdoanh ddkdBean;
		this.out = response.getWriter();
		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		out.println(userId);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

		String id = util.getId(querystring);
		String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		if (view == null) {
			view = "";
		}

		ddkdBean = new Daidienkinhdoanh(id);
		ddkdBean.setView(view);
		System.out.println("VIEW GET: " + view);
		ddkdBean.setUserId(userId);
		session.setAttribute("ddkdBean", ddkdBean);
		String nextJSP = "/AHF/pages/Center/DaiDienKinhDoanhUpdate.jsp";
		if(querystring.indexOf("display") > 0)
			nextJSP = "/AHF/pages/Center/DaiDienKinhDoanhDisplay.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();

		
		IDaidienkinhdoanh ddkdBean;
		this.out = response.getWriter();
		Utility util = new Utility();
		String contentType = request.getContentType();

		String id="";
		String userId ="";
		String ddkdTen ="";
		String trangthai ="";
		String diachi ="";
		String dienthoai ="";
		String nppId ="";
		String ttppId = "";
		String cmnd = "";
		String ngaysinh="";
		String quequan ="";
		String marp = "";
		
		String tiencap ="";
		String tienthe ="";
		String nppasm ="";
		String tienmat ="";
		String maycap ="";
		String maythe ="";
		String maymat ="";
		String filethe = "";
		String filemat ="";
		String filecap ="";
		String chonmt ="";
		String removename="";
		String ngaybatdau ="";
		String ngayketthuc = "";
		String DDKDTIENNHIEM ="";
		String phantramchuyen ="";
		String nganhang = "";
		String chutaikhoan ="";
		String sotaikhoan ="";
		String[] gsbhIds;
		String thuviec ="";
		String action ="";
		String fileName="";
		String matkhau = "";
		String email = "";
		String imei = "";
		String view = "";
		String route_fk = "";
		String kbhId = "";
		int i=0;
		
		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
			String filePath = getServletContext().getInitParameter("pathAnhNhanVien") + "NVBH\\";
			MultipartRequest multi = new MultipartRequest(request,filePath, 20000000, "UTF-8");
			Enumeration<?> files = multi.getFileNames();
			
			ddkdBean = new Daidienkinhdoanh(id);
			String filenameu = "";
			while (files.hasMoreElements())
			{
				String name = (String) files.nextElement();
				filenameu = multi.getFilesystemName(name);
				System.out.println("name  " + filenameu);
			}
			i=1;
			
			action = multi.getParameter("action");
			fileName=filenameu;
			
			marp =  util.antiSQLInspection(multi.getParameter("ddkdMaERP"));
			view = util.antiSQLInspection(multi.getParameter("view"));
			route_fk = util.antiSQLInspection(multi.getParameter("route_fk"));
			id= util.antiSQLInspection(multi.getParameter("id"));
			userId = util.antiSQLInspection(multi.getParameter("userId"));
			ddkdTen = util.antiSQLInspection(multi.getParameter("ddkdTen"));
			trangthai = util.antiSQLInspection(multi.getParameter("TrangThai"));
			diachi = util.antiSQLInspection(multi.getParameter("DiaChi"));
			dienthoai = util.antiSQLInspection(multi.getParameter("DienThoai"));
			nppId = util.antiSQLInspection(multi.getParameter("nppId"));
			ttppId = util.antiSQLInspection(multi.getParameter("TTPP"));
			cmnd = util.antiSQLInspection(multi.getParameter("cmnd"));
			ngaysinh = util.antiSQLInspection(multi.getParameter("ngaysinh"));
			quequan = util.antiSQLInspection(multi.getParameter("quequan"));
			ngaybatdau = util.antiSQLInspection(multi.getParameter("ngaybatdau"));
			ngayketthuc = util.antiSQLInspection(multi.getParameter("ngayketthuc"));
			DDKDTIENNHIEM = util.antiSQLInspection(multi.getParameter("ddkdtiennhiem"));
			phantramchuyen = util.antiSQLInspection(multi.getParameter("phantramchuyen"));
			nganhang = util.antiSQLInspection(multi.getParameter("nganhang"));
			removename = util.antiSQLInspection(multi.getParameter("removename"));
			chutaikhoan = util.antiSQLInspection(multi.getParameter("chutaikhoan"));
			sotaikhoan = util.antiSQLInspection(multi.getParameter("sotaikhoan"));
			gsbhIds = multi.getParameterValues("gsbhList");
			thuviec = util.antiSQLInspection(multi.getParameter("thuviec"));
			action = multi.getParameter("action");
			matkhau = multi.getParameter("matkhau");
			email = multi.getParameter("email");
			maycap = util.antiSQLInspection(multi.getParameter("maycap"));
			maythe = util.antiSQLInspection(multi.getParameter("maythe"));
			chonmt = util.antiSQLInspection(multi.getParameter("ttdh"));
			maymat = util.antiSQLInspection(multi.getParameter("maymat"));
			tiencap = util.antiSQLInspection(multi.getParameter("tiencap"));
			tienthe = util.antiSQLInspection(multi.getParameter("tienthe"));
			tienmat = util.antiSQLInspection(multi.getParameter("tienmat"));
			nppasm = util.antiSQLInspection(multi.getParameter("nppasm"));
			imei = util.antiSQLInspection(multi.getParameter("imei"));
			kbhId = util.antiSQLInspection(multi.getParameter("kbhId"));
			
		} else
		{

			marp =  util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdMaERP")));
			view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
			route_fk = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("route_fk")));
			fileName=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("fileName")));
			id= util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
			ddkdTen = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdTen")));
			trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("TrangThai")));
			diachi = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("DiaChi")));
			dienthoai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("DienThoai")));
			nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
			ttppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("TTPP")));
			cmnd = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("cmnd")));
			ngaysinh = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaysinh")));
			quequan = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("quequan")));
			ngaybatdau = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaybatdau")));
			ngayketthuc = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngayketthuc")));
			DDKDTIENNHIEM = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdtiennhiem")));
			phantramchuyen = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("phantramchuyen")));
			nganhang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nganhang")));
			chutaikhoan = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chutaikhoan")));
			sotaikhoan = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sotaikhoan")));
			gsbhIds = request.getParameterValues("gsbhList");
			thuviec = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thuviec")));
			matkhau = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("matkhau")));
			action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
			email = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("email"));
			maycap = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("maycap")));
			maythe = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("maythe")));
			chonmt = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ttdh")));
			maymat = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("maymat")));
			filecap = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("filenamecap1")));
			filethe = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("filenamethe1")));
			filemat = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("filenamemat1")));
			tiencap = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tiencap")));
			tienthe = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tienthe")));
			tienmat = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tienmat")));
			nppasm = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppasm")));
			imei = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("imei")));
			kbhId = util.antiSQLInspection(request.getParameter("kbhId"));
		}
		
		if (id == null)
		{
			ddkdBean = new Daidienkinhdoanh("");
		} else
		{
			ddkdBean = new Daidienkinhdoanh(id);
		}
		System.out.println("__file cap " +filecap);
		if(filecap == null)
			filecap = "";
		if(filethe == null)
			filethe = "";
		if(filemat == null)
			filemat = "";
		ddkdBean.setHinhanh(fileName);
		ddkdBean.setFilecap(filecap);
		ddkdBean.setFilethe(filethe);
		ddkdBean.setFilemat(filemat);
		ddkdBean.setImei(imei);
		ddkdBean.setUserId(userId);
		if (view == null) {
			view = "";
		}
		ddkdBean.setMaERP(marp);
		ddkdBean.setView(view);
		System.out.println("VIEW POST: " + view);
		session.setAttribute("userId", userId);

		if (ddkdTen == null)
			ddkdTen = "";
		ddkdBean.setTen(ddkdTen);


		if (trangthai == null)
			trangthai = "0";
		else
			trangthai = "1";
		ddkdBean.setTrangthai(trangthai);

		if (diachi == null)
			diachi = "";
		ddkdBean.setDiachi(diachi);

		if (dienthoai == null)
			dienthoai = "";
		ddkdBean.setSodt(dienthoai);

		System.out.println("nppIdafkafaaa: "+nppId);
		if(kbhId == null)
			kbhId = "";
		ddkdBean.setKenhbanhang(kbhId);
		
		if (nppId == null)
			nppId = "";
		ddkdBean.setNppId(nppId);

		if (ttppId == null)
			ttppId = "";
		ddkdBean.setTtppId(ttppId);

		if (cmnd == null)
			cmnd = "";
		ddkdBean.setCmnd(cmnd);

		if (ngaysinh == null)
			ngaysinh = "";
		ddkdBean.setNgaysinh(ngaysinh);

		if (quequan == null)
			quequan = "";
		ddkdBean.setQuequan(quequan);

		if (ngaybatdau == null)
			ngaybatdau = "";
		ddkdBean.setNgaybatdau(ngaybatdau);

		if (ngayketthuc == null)
			ngayketthuc = "";
		ddkdBean.setNgayketthuc(ngayketthuc);

		if (DDKDTIENNHIEM == null)
			DDKDTIENNHIEM = "";
		ddkdBean.setDDKDTn(DDKDTIENNHIEM);

		if (phantramchuyen == null)
			phantramchuyen = "0";
		ddkdBean.setPhantramChuyen(phantramchuyen);

		if (nganhang == null)
			nganhang = "";
		ddkdBean.setNganHang(nganhang);

		if (chutaikhoan == null)
			chutaikhoan = "";
		ddkdBean.setChuTaiKhoan(chutaikhoan);

		if (maycap == null)
			maycap = "0";
		ddkdBean.setMaycap(maycap);
		
		if (maythe == null)
			maythe = "0";
		ddkdBean.setMaythe(maythe);
		
		if (chonmt == null)
			chonmt = "";
		ddkdBean.setChonmt(chonmt);
		
		if (maymat == null)
			maymat = "0";
		ddkdBean.setMaymat(maymat);
		
		if (nppasm == null)
			nppasm = "";
		ddkdBean.setNppasm(nppasm);
		
		if (tiencap == null)
			tiencap = "0";
		ddkdBean.setTiencap(tiencap);
		
		if (tienthe == null)
			tienthe = "0";
		System.out.println("dfds" + tienthe);
		ddkdBean.setTienthe(tienthe);
		
		if (tienmat == null)
			tienmat = "0";
		ddkdBean.setTienmat(tienmat);
		
		if (removename == null)
			removename = "";
		
		if (sotaikhoan == null)
			sotaikhoan = "";
		ddkdBean.setSoTaiKhoan(sotaikhoan);

		if (matkhau == null) matkhau = ""; else matkhau = matkhau.trim();

		if (email == null) email = ""; else email = email.trim();
		ddkdBean.setEmail(email);

		System.out.println("gsbh " + gsbhIds);
		ddkdBean.setGsbhIds(gsbhIds);

		String ngaysua = getDateTime();
		ddkdBean.setNgaysua(ngaysua);

		String nguoisua = userId;
		ddkdBean.setNguoisua(nguoisua);

		if (thuviec == null || thuviec.trim().length()==0)
			thuviec = "0";
		else
			thuviec = "1";

		if (route_fk == null) {
			route_fk = "";
		}
		ddkdBean.setRoute_fk(route_fk);

		ddkdBean.setThuviec(thuviec);
		boolean error = false;
		
		if ((nppId == null || nppId.length() <= 0))
		{
			ddkdBean.setMessage("Vui lòng chọn chi nhánh/nhà phân phối trực thuộc!");
			error = true;
		}

		if (ddkdTen.trim().length() == 0)
		{
			ddkdBean.setMessage("Vui lòng nhập tên đại diện kinh doanh");
			error = true;
		}

		String maddkd = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdMa"));
		if(maddkd == null)
			maddkd = "";
		ddkdBean.setSmartId(maddkd);

		if (diachi.trim().length() == 0)
		{
			ddkdBean.setMessage("Vui lòng nhập địa chỉ");
			error = true;
		}
		
		if (dienthoai.trim().length() == 0)
		{
			ddkdBean.setMessage("Vui lòng nhập số điện thoại");
			error = true;
		}
	
		if (action.equals("save") && id==null && matkhau.trim().length() == 0) { //Tạo mới -> cần nhập mật khẩu khởi tạo
			
		} else 
		{
			ddkdBean.setMatkhau(matkhau);
		}

		String nextJSP = "";
		if (!error)
		{
			if (action.equals("save"))
			{
				
				
				if (id == null||id.length()==0)
				{
					if (!(ddkdBean.CreateDdkd()))
					{
						ddkdBean.createRS();
						session.setAttribute("ddkdBean", ddkdBean);
						ddkdBean.setUserId(userId);
						nextJSP = "/AHF/pages/Center/DaiDienKinhDoanhNew.jsp";
						response.sendRedirect(nextJSP);
					} else
					{
						IDaidienkinhdoanhList obj = new DaidienkinhdoanhList();
						obj.setView(view);
						obj.setUserId(userId);
						obj.init("");
						session.setAttribute("obj", obj);
						nextJSP = "/AHF/pages/Center/DaiDienKinhDoanh.jsp";
						response.sendRedirect(nextJSP);
					}
				} else
				{
					if (!(ddkdBean.UpdateDdkd()))
					{
						ddkdBean.init();
						session.setAttribute("ddkdBean", ddkdBean);
						nextJSP = "/AHF/pages/Center/DaiDienKinhDoanhUpdate.jsp";
						response.sendRedirect(nextJSP);
					} else
					{
						IDaidienkinhdoanhList obj = new DaidienkinhdoanhList();
						obj.setView(view);
						obj.setUserId(userId);
						obj.init("");
						System.out.print("\nVao trang nay...\n");
						session.setAttribute("obj", obj);
						nextJSP = "/AHF/pages/Center/DaiDienKinhDoanh.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			} else
			{
				if (action.equals("downcap"))
				{
					
					
					if (filecap.trim().length() > 0)
					{
						try
						{
							/*FileInputStream fileToDownload = new FileInputStream(getServletContext().getInitParameter("path") + "\\images\\" + filecap);
							ServletOutputStream output = response.getOutputStream();
							response.setContentType("application/octet-stream");
							
							System.out.println(filecap);
							response.setHeader("Content-Disposition", "attachment; filename=\"" + filecap + "\"");
							response.setContentLength(fileToDownload.available());
							int c;
							while ((c = fileToDownload.read()) != -1)
							{
								output.write(c);
							}
							output.flush();
							output.close();
							fileToDownload.close();*/
							
							request.setCharacterEncoding("utf-8");
							
							response.setContentType("application/octet-stream");
							response.setHeader("Content-Disposition", "attachment; filename=\"" + filecap + "\"");

							OutputStream out = response.getOutputStream();
							FileInputStream fileToDownload = new FileInputStream(getServletContext().getInitParameter("path") + "\\images\\" + filecap);	
							int c;
							while ((c = fileToDownload.read()) != -1)
							{
								out.write(c);
							}
							out.flush();
							out.close();
							fileToDownload.close();
						} catch (Exception e)
						{
							System.out.println("___Loi DOWNLOAD file: " + e.getMessage());
						}
					} 
				}else if (action.equals("downthe"))
				{
					
					
					if (filethe.trim().length() > 0)
					{
						try
						{
							/*FileInputStream fileToDownload = new FileInputStream(getServletContext().getInitParameter("path") + "\\images\\" + filethe);
							ServletOutputStream output = response.getOutputStream();
							response.setContentType("application/octet-stream");
							
							System.out.println(filethe);
							response.setHeader("Content-Disposition", "attachment; filename=\"" + filethe + "\"");
							response.setContentLength(fileToDownload.available());
							int c;
							while ((c = fileToDownload.read()) != -1)
							{
								output.write(c);
							}
							output.flush();
							output.close();
							fileToDownload.close();*/
							
							request.setCharacterEncoding("utf-8");
							
							response.setContentType("application/octet-stream");
							response.setHeader("Content-Disposition", "attachment; filename=\"" + filethe + "\"");

							OutputStream out = response.getOutputStream();
							FileInputStream fileToDownload = new FileInputStream(getServletContext().getInitParameter("path") + "\\images\\" + filethe);	
							int c;
							while ((c = fileToDownload.read()) != -1)
							{
								out.write(c);
							}
							out.flush();
							out.close();
							fileToDownload.close();
							
							
						} catch (Exception e)
						{
							System.out.println("___Loi DOWNLOAD file: " + e.getMessage());
						}
					} 
				}else if (action.equals("downmat"))
				{
					
					if (filemat.trim().length() > 0)
					{
						
							/*FileInputStream fileToDownload = new FileInputStream(getServletContext().getInitParameter("path") + "\\images\\" + filemat);
							ServletOutputStream output = response.getOutputStream();
							response.setContentType("application/octet-stream");
							
							System.out.println(filemat);
							response.setHeader("Content-Disposition", "attachment; filename=\"" + filemat + "\"");
							response.setContentLength(fileToDownload.available());
							int c;
							while ((c = fileToDownload.read()) != -1)
							{
								output.write(c);
							}
							output.flush();
							output.close();
							fileToDownload.close();
							return;*/
						request.setCharacterEncoding("utf-8");
						
						response.setContentType("application/octet-stream");
						response.setHeader("Content-Disposition", "attachment; filename=\"" + filemat + "\"");

						OutputStream out = response.getOutputStream();
						FileInputStream fileToDownload = new FileInputStream(getServletContext().getInitParameter("path") + "\\images\\" + filemat);	
						int c;
						while ((c = fileToDownload.read()) != -1)
						{
							out.write(c);
						}
						out.flush();
						out.close();
						fileToDownload.close();
					
					} 
				}
				else if(action.equals("removecap"))
				{
					String s = "";
					PrintWriter out = response.getWriter();
					System.out.println("xoa file " + s);
					if (!this.deletefile(getServletContext().getInitParameter("path") + "\\images\\"  + removename))
					{
						out.write("Lỗi xóa file");
					} else
					{
						out.write("");
					}
					
					ddkdBean.setFilecap(s);
					ddkdBean.createRS();
					session.setAttribute("ddkdBean", ddkdBean);
					if (id == null)
					{
						nextJSP = "/AHF/pages/Center/DaiDienKinhDoanhNew.jsp";
					} else
					{
						nextJSP = "/AHF/pages/Center/DaiDienKinhDoanhUpdate.jsp";
					}
					response.sendRedirect(nextJSP);
				}
				else if(action.equals("removemat"))
				{
					String s = "";
					PrintWriter out = response.getWriter();
					
					if (!this.deletefile(getServletContext().getInitParameter("path") + "\\images\\"  + removename))
					{
						out.write("Lỗi xóa file");
					} else
					{
						out.write("");
					}
					
					ddkdBean.setFilemat(s);
					ddkdBean.createRS();
					session.setAttribute("ddkdBean", ddkdBean);
					if (id == null)
					{
						nextJSP = "/AHF/pages/Center/DaiDienKinhDoanhNew.jsp";
					} else
					{
						nextJSP = "/AHF/pages/Center/DaiDienKinhDoanhUpdate.jsp";
					}
					response.sendRedirect(nextJSP);
				}
				else if(action.equals("removethe"))
				{
					String s = "";
					PrintWriter out = response.getWriter();
					
					if (!this.deletefile(getServletContext().getInitParameter("path") + "\\images\\"  + removename))
					{
						out.write("Lỗi xóa file");
					} else
					{
						out.write("");
					}
					
					ddkdBean.setFilethe(s);
					ddkdBean.createRS();
					session.setAttribute("ddkdBean", ddkdBean);
					if (id == null)
					{
						nextJSP = "/AHF/pages/Center/DaiDienKinhDoanhNew.jsp";
					} else
					{
						nextJSP = "/AHF/pages/Center/DaiDienKinhDoanhUpdate.jsp";
					}
					response.sendRedirect(nextJSP);
				}
				else
					{
						ddkdBean.createRS();
						ddkdBean.setUserId(userId);
						session.setAttribute("ddkdBean", ddkdBean);
						if (id == null)
						{
							nextJSP = "/AHF/pages/Center/DaiDienKinhDoanhNew.jsp";
						} else
						{
							nextJSP = "/AHF/pages/Center/DaiDienKinhDoanhUpdate.jsp";
						}
						response.sendRedirect(nextJSP);
					}
			}
		} else
		{
			ddkdBean.createRS();
			ddkdBean.setUserId(userId);
			session.setAttribute("ddkdBean", ddkdBean);
			if (id == null)
			{
				nextJSP = "/AHF/pages/Center/DaiDienKinhDoanhNew.jsp";
			} else
			{
				nextJSP = "/AHF/pages/Center/DaiDienKinhDoanhUpdate.jsp";
			}
			response.sendRedirect(nextJSP);
		}

	}

	private boolean deletefile(String file)
	{
		System.out.println(file);
		File f1 = new File(file);
		boolean success = f1.delete();
		if (!success)
		{
			return false;
		} else
		{
			return true;
		}
	}
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	private String getDateTime2()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
		Date date = new Date();
		return dateFormat.format(date);
	}

	private String SaveFile(HttpServletRequest request)
	{
		try
		{
			HttpSession session = request.getSession();
			Utility util = new Utility();
			String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
			session.setAttribute("userId", userId);
			String filePath = getServletContext().getInitParameter("path") + "\\images\\";


			String contentType = request.getContentType();

			String fileName = getDateTime2().replaceAll(":", "-").replaceAll(" ", "_") + ".jpg";

			DataInputStream in = new DataInputStream(request.getInputStream());

			int formDataLength = request.getContentLength();
			byte dataBytes[] = new byte[formDataLength];
			int byteRead = 0;
			int totalBytesRead = 0;

			System.out.println("Do dai lay duoc: " + formDataLength);

			while (totalBytesRead < formDataLength)
			{
				byteRead = in.read(dataBytes, totalBytesRead, formDataLength);
				totalBytesRead += byteRead;
			}

			String file = new String(dataBytes);

			System.out.println("1.Chay toi day ");
			String saveFile = file.substring(file.indexOf("filename=\"") + 10);
			saveFile = saveFile.substring(0, saveFile.indexOf("\n"));
			saveFile = saveFile.substring(saveFile.lastIndexOf("\\") + 1, saveFile.indexOf("\""));
			int lastIndex = contentType.lastIndexOf("=");
			String boundary = contentType.substring(lastIndex + 1, contentType.length());
			int pos;
			// extracting the index of file
			pos = file.indexOf("filename=\"");
			pos = file.indexOf("\n", pos) + 1;
			pos = file.indexOf("\n", pos) + 1;
			pos = file.indexOf("\n", pos) + 1;

			int boundaryLocation = file.indexOf(boundary, pos) - 4;
			int startPos = ((file.substring(0, pos)).getBytes()).length;
			int endPos = ((file.substring(0, boundaryLocation)).getBytes()).length;
			if (file.length() > 0)
			{
				System.out.println("filePath + fileName : "+filePath + fileName);
				FileOutputStream fileOut = new FileOutputStream(filePath + fileName);
				System.out.println(startPos);
				System.out.println(endPos);
				fileOut.write(dataBytes, startPos,formDataLength);
				fileOut.flush();
				fileOut.close();
				return fileName;
			} else
			{
				return "";
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("__Exception: " + e.getMessage());
			return e.getMessage();
		}

	}
}
