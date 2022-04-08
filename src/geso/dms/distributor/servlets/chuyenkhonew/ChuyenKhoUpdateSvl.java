package geso.dms.distributor.servlets.chuyenkhonew;

import geso.dms.distributor.beans.chuyenkhonew.IChuyenKho;
import geso.dms.distributor.beans.chuyenkhonew.IChuyenKhoList;
import geso.dms.distributor.beans.chuyenkhonew.ISanPham;
import geso.dms.distributor.beans.chuyenkhonew.imp.ChuyenKho;
import geso.dms.distributor.beans.chuyenkhonew.imp.SanPham;
import geso.dms.distributor.beans.chuyenkhonew.imp.ChuyenKhoList;
import geso.dms.distributor.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

@WebServlet("/ChuyenKhoUpdateSvl")
public class ChuyenKhoUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;

	public ChuyenKhoUpdateSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/AHF/index.jsp");
		} else
		{
			session.setMaxInactiveInterval(30000);

			this.out = response.getWriter();
			Utility util = new Utility();

			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);

			out.println(userId);

			if (userId.length() == 0)
				userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

			String id = util.getId(querystring);
			IChuyenKho dhBean = new ChuyenKho(id);
			dhBean.setUserId(userId); // phai co UserId truoc khi Init
			
			String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));			
			dhBean.init(nppId);
			
			String view = util.antiSQLInspection( geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")) );
			dhBean.setView(view);

			String nextJSP;

			if (request.getQueryString().indexOf("display") >= 0)
			{
				dhBean.setDhId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("display")));
				System.out.println("===+++========" + dhBean.getNppTen());
				nextJSP = "/AHF/pages/Distributor/ChuyenKhoDisplay.jsp";
			} else
			{
				nextJSP = "/AHF/pages/Distributor/ChuyenKhoUpdate.jsp";
			}

			session.setAttribute("ckBean", dhBean);
			session.setAttribute("nppId", dhBean.getNppId());
			session.setAttribute("nhappid", dhBean.getNppId());
			session.setAttribute("kenhid", dhBean.getKbhId());
			session.setAttribute("dvkdid", dhBean.getDvkdId());
			session.setAttribute("khoId", dhBean.getKhochuyenId());
			session.setAttribute("ngaydc", dhBean.getNgayDc());
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/AHF/index.jsp");
		} else
		{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			session.setMaxInactiveInterval(1200);
			IChuyenKho dnhBean = (IChuyenKho) new ChuyenKho();
			Utility util = new Utility();

			String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
			if (id != null)
			{
				dnhBean.setId(id);
			}

			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
			dnhBean.setUserId(userId);
			String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
			if (nppId == null)
				nppId = "";
			dnhBean.setNppId(nppId);
			MultipartRequest multi = null;

			String contentType = request.getContentType();
			if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
				multi = new MultipartRequest(request, "C:\\java-tomcat\\data\\", 20000000, "UTF-8");
			String removename = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("removename")));
			if (removename == null)
				removename = "";
			String[] filenames = request.getParameterValues("filedk");
			String s = "";
			if(filenames != null)
			{
				for(int i=0; i< filenames.length; i++)
					s += filenames[i] + ",";
				if(s.length() > 0)
					s = s.substring(0, s.length() - 1);

			}
			System.out.println("s =" + s + ";");
			dnhBean.setFile(s);
			String action = "";
			action = request.getParameter("action");
			if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
			{

				id = util.antiSQLInspection(multi.getParameter("id"));
				if (id != null)
					dnhBean.setId(id);
				action = multi.getParameter("action");

				userId = util.antiSQLInspection(multi.getParameter("userId"));
				if (userId == null)
				{
					response.sendRedirect("/DDT");
				} else
				{
					dnhBean.setUserId(userId);
				}
				String ngaynhap = util.antiSQLInspection(multi.getParameter("ngaynhap"));
				dnhBean.setNgaynhap(ngaynhap);

				String nccId = util.antiSQLInspection(multi.getParameter("nccId"));
				dnhBean.setNccId(nccId);

				String dvkdId = util.antiSQLInspection(multi.getParameter("dvkdId"));
				dnhBean.setDvkdId(dvkdId);

				String kbhId = util.antiSQLInspection(multi.getParameter("kbhId"));
				dnhBean.setKbhId(kbhId);

				String khochuyenId = util.antiSQLInspection(multi.getParameter("khochuyenId"));
				dnhBean.setKhochuyenId(khochuyenId);
				
				String khonhanId = util.antiSQLInspection(multi.getParameter("khonhanId"));
				dnhBean.setKhonhanId(khonhanId);			
				
				String ghiChu = util.antiSQLInspection(multi.getParameter("ghichu"));
				dnhBean.setGhichu(ghiChu);
				String thuevat = util.antiSQLInspection(multi.getParameter("thuevat"));
				dnhBean.setThuevat(thuevat); 
				String ngayDc = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaydc")));
				dnhBean.setNgayDc(ngayDc);

				filenames = multi.getParameterValues("filedk");
				s = "";
				if(filenames != null)
				{
					for(int i=0; i< filenames.length; i++)
						s += filenames[i] + ",";
					if(s.length() > 0)
						s = s.substring(0, s.length() - 1);

				}
				System.out.println("File cu: "+s);
				Enumeration files = multi.getFileNames();
				String filenameu = "";
				String filedk = "";
				while (files.hasMoreElements())
				{
					String name = (String) files.nextElement();
					filenameu = multi.getFilesystemName(name);
					if(name != null)
						if(name.equals("fileup"))
							filedk = multi.getFilesystemName(name);

				}

				if(filedk != null && filedk.length() > 0){
					if(s.length() > 0)
					{
						dnhBean.setFile(s + "," + filedk);
						s= s + "," + filedk;
					}
					else
					{
						dnhBean.setFile(filedk);
						System.out.println("___S:" + s + "," + filedk);
						s= s + "," + filedk;
					}
				}
				
				dnhBean.createRs("");
				
				String[] spId = multi.getParameterValues("spId");
				String[] spMa = multi.getParameterValues("spMa");
				String[] spTen = multi.getParameterValues("spTen");
				String[] soluongChuan = multi.getParameterValues("soluongchuan");
				String[] soluong = multi.getParameterValues("soluong");
				String[] dvdlId = multi.getParameterValues("dvdlId");
				String[] dongia = multi.getParameterValues("dongia");
				String[] quycach = multi.getParameterValues("quycach");
				String[] tonkho = multi.getParameterValues("tonkho");
				String[] thanhtien = multi.getParameterValues("thanhtien");
				List<ISanPham> spList = new ArrayList<ISanPham>();
				
				if (spMa != null)
				{
					ISanPham sanpham = null;
					int m = 0;
					while (m < spMa.length)
					{
						System.out.println("SOLuong: "+soluong[m]);
						if (spMa[m] != "" && dvdlId[m] != "" && soluong[m] != "" && soluong[m].length() > 0)
						{
							if (Float.parseFloat(soluong[m].replace(",", "")) > 0)
							{
								sanpham = new SanPham(spId[m], spMa[m], spTen[m], dvdlId[m], soluongChuan[m].replace(",", ""), soluongChuan[m].replace(",", ""), dongia[m].replace(",", ""));
								sanpham.setTonkho(tonkho[m].replace(",", ""));
								sanpham.setQuycach(quycach[m].replace(",", ""));
								sanpham.setThanhtien(thanhtien[m].replace(",", ""));
								spList.add(sanpham);
							}
						}
						m++;
					}
					dnhBean.setSpList(spList);
				}
				session.setAttribute("nhappid", nppId);
				session.setAttribute("kenhid", kbhId);
				session.setAttribute("dvkdid", dvkdId);
				session.setAttribute("khoId", khochuyenId);
				
			}
			else
			{
			String ngaynhap = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaynhap")));
			dnhBean.setNgaynhap(ngaynhap);

			String nccId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nccId")));
			dnhBean.setNccId(nccId);

			String dvkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));
			dnhBean.setDvkdId(dvkdId);

			String kbhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhId")));
			dnhBean.setKbhId(kbhId);

			String khochuyenId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khochuyenId")));
			dnhBean.setKhochuyenId(khochuyenId);
			
			String khonhanId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khonhanId")));
			dnhBean.setKhonhanId(khonhanId);			
			
			String ghiChu = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ghichu")));
			dnhBean.setGhichu(ghiChu);
			
			String ngayDc = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaydc")));
			dnhBean.setNgayDc(ngayDc);
			String thuevat = util.antiSQLInspection(request.getParameter("thuevat"));
			dnhBean.setThuevat(thuevat); 
			session.setAttribute("nhappid", nppId);
			session.setAttribute("kenhid", kbhId);
			session.setAttribute("dvkdid", dvkdId);
			session.setAttribute("khoId", khochuyenId);
			session.setAttribute("ngaydc", ngayDc);
			 action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
			
			dnhBean.createRs("");
			
			String[] spId = request.getParameterValues("spId");
			String[] spMa = request.getParameterValues("spMa");
			String[] spTen = request.getParameterValues("spTen");
			String[] soluongChuan = request.getParameterValues("soluongchuan");
			String[] soluong = request.getParameterValues("soluong");
			String[] dvdlId = request.getParameterValues("dvdlId");
			String[] dongia = request.getParameterValues("dongia");
			String[] quycach = request.getParameterValues("quycach");
			String[] tonkho = request.getParameterValues("tonkho");
			String[] thanhtien = request.getParameterValues("thanhtien");
			List<ISanPham> spList = new ArrayList<ISanPham>();
			
			if (spMa != null)
			{
				ISanPham sanpham = null;
				int m = 0;
				while (m < spMa.length)
				{
					if (spMa[m] != "" && 
							dvdlId[m] != "" &&
							soluong[m] != "" && soluong[m].length() > 0)
					{
						if (Float.parseFloat(soluong[m].replace(",", "")) > 0)
						{
							sanpham = new SanPham(spId[m], spMa[m], spTen[m], dvdlId[m], soluongChuan[m].replace(",", ""), soluongChuan[m].replace(",", ""), dongia[m].replace(",", ""));
							sanpham.setTonkho(tonkho[m].replace(",", ""));
							sanpham.setQuycach(quycach[m].replace(",", ""));
							sanpham.setThanhtien(thanhtien[m].replace(",", ""));
							spList.add(sanpham);
						}
					}
					m++;
				}
				dnhBean.setSpList(spList);
			}
			}
			if (action.equals("save"))
			{
				if (id == null)
				{					
					if (!(dnhBean.save()))
					{
						session.setAttribute("ckBean", dnhBean);
						String nextJSP = "/AHF/pages/Distributor/ChuyenKhoNew.jsp";
						response.sendRedirect(nextJSP);
					} else
					{
						IChuyenKhoList obj = new ChuyenKhoList();
						obj.setUserId(userId);
						obj.createDdhlist("");
						session.setAttribute("obj", obj);
						String nextJSP = "/AHF/pages/Distributor/ChuyenKho.jsp";
						response.sendRedirect(nextJSP);
					}
				} else
				{
					if (!(dnhBean.edit()))
					{
						session.setAttribute("ckBean", dnhBean);
						String nextJSP = "/AHF/pages/Distributor/ChuyenKhoUpdate.jsp";
						response.sendRedirect(nextJSP);
					} else
					{
						IChuyenKhoList obj = new ChuyenKhoList();
						obj.setUserId(userId);
						obj.createDdhlist("");
						session.setAttribute("obj", obj);
						String nextJSP = "/AHF/pages/Distributor/ChuyenKho.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else if(action.equals("remove")){
				s = "";
				for(int i=0; i< filenames.length; i++)
					if(!filenames[i].equals(removename))
						s += filenames[i] + ",";
				if(s.length() > 0)
					s = s.substring(0, s.length() - 1);

				PrintWriter out = response.getWriter();

				if (!this.deletefile("C:\\java-tomcat\\data\\" + removename))
				{
					out.write("Lỗi xóa file");
				} else
				{
					out.write("");
				}

				
				System.out.println("Filexoa: "+s);
				String nextJSP;
				if (id == null)
				{
					session.setAttribute("ckBean", dnhBean);
					nextJSP = "/AHF/pages/Distributor/ChuyenKhoNew.jsp";
				} else
				{
					dnhBean.init(nppId);
					dnhBean.setFile(s);
					session.setAttribute("ckBean", dnhBean);
					nextJSP = "/AHF/pages/Distributor/ChuyenKhoUpdate.jsp";
				}
				session.setAttribute("dnhBean", dnhBean);
				response.sendRedirect(nextJSP);
			}else
				if (action.equals("download"))
				{
					System.out.println("___Vao DOWNLOAD FILE....");
					String fileName = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tenfile"));
					if (fileName == null)
						fileName = "";

					if (fileName.trim().length() > 0)
					{
						try
						{
							FileInputStream fileToDownload = new FileInputStream("C:\\java-tomcat\\data\\" + fileName);
							ServletOutputStream output = response.getOutputStream();
							response.setContentType("application/octet-stream");

							System.out.println(fileName);
							response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
							response.setContentLength(fileToDownload.available());
							int c;
							while ((c = fileToDownload.read()) != -1)
							{
								output.write(c);
							}
							output.flush();
							output.close();
							fileToDownload.close();
						} catch (Exception e)
						{
							System.out.println("___Loi DOWNLOAD file: " + e.getMessage());
						}
					} else
					{
						dnhBean.setUserId(userId);
						dnhBean.setId(id);
						

						String nextJSP;
						if (id == null)
						{
							
							nextJSP = "/AHF/pages/Distributor/ChuyenKhoNew.jsp";
						} else
						{
							dnhBean.init(nppId);
							nextJSP = "/AHF/pages/Distributor/ChuyenKhoUpdate.jsp";
						}
						session.setAttribute("dnhBean", dnhBean);
						response.sendRedirect(nextJSP);
					}
				} 
				else
			{
				if (id == null)
				{
					session.setAttribute("ckBean", dnhBean);
					String nextJSP = "/AHF/pages/Distributor/ChuyenKhoNew.jsp";
					response.sendRedirect(nextJSP);
				} else
				{
					dnhBean.init(nppId);
					System.out.println("Filename: "+s);
					dnhBean.setFile(s);
					session.setAttribute("ckBean", dnhBean);
					String nextJSP = "/AHF/pages/Distributor/ChuyenKhoUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
			}
		}
	}
}
