package geso.dms.center.servlets.thongbao;

import geso.dms.center.beans.thongbao.IThongbao;
import geso.dms.center.beans.thongbao.IThongbaoList;
import geso.dms.center.beans.thongbao.imp.Thongbao;
import geso.dms.center.beans.thongbao.imp.ThongbaoList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

public class ThongbaoUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public ThongbaoUpdateSvl()
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
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/AHF/index.jsp");
		} else
		{
			session.setMaxInactiveInterval(30000);
			
			Utility util = new Utility();
			
			// String querystring = request.getQueryString();
			// userId = util.getUserId(querystring);
			
			if (userId.length() <= 0)
				userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
			
			String id = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id"));
			IThongbao tbBean;
			String task = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("task"));
			
			String viewMode = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("viewMode"));
			if (viewMode == null)
				viewMode = "1";
			
			String loaivanban = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaivanban"));
			if (loaivanban == null)
				loaivanban = "";
			
			if (task.equals("view"))
			{
				tbBean = new Thongbao(id);
				tbBean.setViewMode("0");
				tbBean.setUserId(userId);
				
				tbBean.initDisplay();
				tbBean.initThaoLuanTT();
				
				session.setAttribute("tbBean", tbBean);
				String nextJSP = "/AHF/pages/Center/ThongBaoDisplay.jsp";
				response.sendRedirect(nextJSP);
			} else if (task.equals("capnhatnv"))
			{
				dbutils db = new dbutils();
				// cap nhat lai trang thai thong bao
				String query = "update thongbao_nhanvien set trangthai='1' where thongbao_fk='" + id + "' and nhanvien_fk = '" + userId + "'";
				System.out.println("Câu lênh cập nhât NV: " + query);
				
				if (!db.update(query))
				{
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					db.shutDown();
				}
				db.shutDown();
				
				tbBean = new Thongbao(id);
				tbBean.setViewMode("0");
				tbBean.setUserId(userId);
				
				tbBean.initDisplay();
				tbBean.initThaoLuan();
				
				session.setAttribute("tbBean", tbBean);
				String nextJSP = "/AHF/pages/Center/ThongBaoNvDisplay.jsp";
				response.sendRedirect(nextJSP);
			} 
			else if (task.equals("capnhat"))
			{
				tbBean = new Thongbao(id);
				tbBean.setViewMode("0");
				tbBean.init();
				session.setAttribute("tbBean", tbBean);
				String nextJSP = "/AHF/pages/Center/ThongBaoUpdate.jsp";
				if (viewMode.equals("0"))
					nextJSP = "/AHF/pages/Center/ThongBaoUpdate_NPP.jsp";
				response.sendRedirect(nextJSP);
				
			} 
			else if (task.equals("chot"))
			{
				String pk = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id"));
				IThongbaoList obj = new ThongbaoList();
				obj.setViewMode(viewMode);
				dbutils db = new dbutils();
				try
				{
					db.getConnection().setAutoCommit(false);
					
					String query = "update thongbao set trangthai='1', tinhtrang = '1' where pk_seq= '" + pk + "'";
					if (!db.update(query))
					{
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						obj.setMsg("Không thể chốt, lỗi: " + query);
						
					}
					query = "update thongbao_nhanvien set trangthai='0' where thongbao_fk= '" + pk + "'";
					if (!db.update(query))
					{
						obj.setMsg("Không thể chốt, lỗi: " + query);
					}
					db.getConnection().commit();
					db.getConnection().setAutoCommit(true);
				} catch (Exception e)
				{
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					obj.setMsg("Không thể chốt, lỗi: " + e.getMessage());
				}
				db.shutDown();
				
				obj = new ThongbaoList(userId);
				obj.setLoaithongbao(loaivanban);
				obj.setViewMode(viewMode);
				obj.setUserId(userId);
				obj.init("");
				session.setAttribute("obj", obj);
				String nextJSP = "/AHF/pages/Center/ThongBao.jsp";
				response.sendRedirect(nextJSP);
				
			}
			if (task.equals("copy"))
			{
				tbBean = new Thongbao(id);
				tbBean.init();
		    	tbBean.createRs();
		    	tbBean.setId("");
		    	tbBean.setLoaithongbao(loaivanban);
		    	tbBean.setViewMode(viewMode);
		    	
		    	session.setAttribute("tbBean", tbBean);		
				String nextJSP = "/AHF/pages/Center/ThongBaoNew.jsp";
				if(viewMode.equals("0"))
					nextJSP = "/AHF/pages/Center/ThongBaoNew_NPP.jsp";
				
				response.sendRedirect(nextJSP);
			}
			
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
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		String contentType = request.getContentType();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		Utility util = new Utility();
		
		MultipartRequest multi;
		multi = new MultipartRequest(request, "C:\\java-tomcat\\dinhkem\\", 20000000, "UTF-8");
		
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/AHF/index.jsp");
		} else
		{
			IThongbao tbBean = new Thongbao();
			
			String tieude = multi.getParameter("tieude");
			String noidung = multi.getParameter("noidung");
			
			String ngaybatdau = multi.getParameter("ngaybatdau");
			String ngayketthuc = multi.getParameter("ngayketthuc");
			
			String hethieuluc = multi.getParameter("hethieuluc");
			if (hethieuluc == null)
				hethieuluc = "0";
			tbBean.setHethieuluc(hethieuluc);
			
			String chinhanh = multi.getParameter("chinhanh");
			if (chinhanh == null)
				chinhanh = "";
			tbBean.setChiNhanh(chinhanh);
			
			String doitac = multi.getParameter("doitac");
			if (doitac == null)
				doitac = "";
			tbBean.setDoiTac(doitac);
			
			System.out.println(chinhanh + "  " + doitac);
			
			String loaiNv = multi.getParameter("loaiNv")==null?"":multi.getParameter("loaiNv");
			tbBean.setLoaiNv(loaiNv);
			
			String ttId = multi.getParameter("ttId")==null?"":multi.getParameter("ttId");
			tbBean.setTtId(ttId);
			
		
			String loaithongbao = multi.getParameter("loaithongbao");
			if (loaithongbao == null)
				loaithongbao = "0";
			tbBean.setLoaithongbao(loaithongbao);
			
			String viewMode = multi.getParameter("viewMode");
			if (viewMode == null)
				viewMode = "1";
			tbBean.setViewMode(viewMode);
			
			String[] nvIds = multi.getParameterValues("nvIds");
			String nvId = "";
			if (nvIds != null)
			{
				
				for (int i = 0; i < nvIds.length; i++)
					nvId += nvIds[i] + ",";
				
				if (nvId.trim().length() > 0)
				{
					nvId = nvId.substring(0, nvId.length() - 1);
					tbBean.setNhanvienIds(nvId);
				}
				System.out.println("Lấy Đăng Nhập là; " + nvId);
			}
			
			String[] nppids = multi.getParameterValues("nppids");
			String nppid = "";
			if (nppids != null)
			{
				
				for (int i = 0; i < nppids.length; i++)
					nppid += nppids[i] + ",";
				
				if (nppid.trim().length() > 0)
				{
					nppid = nppid.substring(0, nppid.length() - 1);
					tbBean.setnppIds(nppid);
				}
				System.out.println("npp " + nvId);
			}
			
			String[] ddkdIds = multi.getParameterValues("ddkdIds");
			String ddkdId = "";
			if (ddkdIds != null)
			{
				
				for (int i = 0; i < ddkdIds.length; i++)
					ddkdId += ddkdIds[i] + ",";
				
				if (ddkdId.trim().length() > 0)
				{
					ddkdId = ddkdId.substring(0, ddkdId.length() - 1);
					tbBean.setDdkdId(ddkdId);
				}
				
				System.out.println("___Nhân viên bán hàng: " + ddkdId);
			}
			String nhomnv = multi.getParameter("nhomnv");
			if (nhomnv == null)
				nhomnv = "";
			tbBean.setNhomNvId(nhomnv);
			if(nhomnv.length() > 0)
			{
				dbutils db = new dbutils();
				ResultSet nv = db.get("select manhanvien, loai from nhomnhanvien where pk_seq = '"+nhomnv+"'");
				try 
				{
					nv.next();
					if(nv.getString("loai").equals("4"))
					{
						if(ddkdId.length() > 0)
						{
							ddkdId += ","+nv.getString("manhanvien");
							tbBean.setDdkdId(ddkdId);
						}
						else
						{
							ddkdId = nv.getString("manhanvien");
							tbBean.setDdkdId(ddkdId);
						}
					}
					else
					{
						if(nvId.length() > 0)
						{
							nvId += ","+nv.getString("manhanvien");
							tbBean.setNhanvienIds(nvId);
						}
						else
						{
							nvId = nv.getString("manhanvien");
							tbBean.setNhanvienIds(nvId);
						}
					}
				} catch (SQLException e) 
				{
					
					e.printStackTrace();
				}
				
			}
			/*
			 * if(tbBean.getNhanvienIds().trim().length() <= 0)
			 * tbBean.setNhanvienIds(tbBean.getDdkdId()); else
			 * if(tbBean.getNhanvienIds().trim().length() > 0 &&
			 * tbBean.getDdkdId().trim().length() > 0)
			 * tbBean.setNhanvienIds(tbBean.getNhanvienIds()+","+tbBean.getDdkdId());
			 */
			
			String[] vbhdIds = multi.getParameterValues("vbhdIds");
			if (vbhdIds != null)
			{
				String vbhdId = "";
				for (int i = 0; i < vbhdIds.length; i++)
					vbhdId += vbhdIds[i] + ",";
				
				if (vbhdId.trim().length() > 0)
				{
					vbhdId = vbhdId.substring(0, vbhdId.length() - 1);
					tbBean.setVbhdId(vbhdId);
				}
			}
			
			String[] vbccIds = multi.getParameterValues("vbccIds");
			if (vbccIds != null)
			{
				String vbccId = "";
				for (int i = 0; i < vbccIds.length; i++)
					vbccId += vbccIds[i] + ",";
				
				if (vbccId.trim().length() > 0)
				{
					vbccId = vbccId.substring(0, vbccId.length() - 1);
					tbBean.setVbccId(vbccId);
				}
			}
			
			String[] vbttIds = multi.getParameterValues("vbttIds");
			if (vbttIds != null)
			{
				String vbttId = "";
				for (int i = 0; i < vbttIds.length; i++)
					vbttId += vbttIds[i] + ",";
				
				if (vbttId.trim().length() > 0)
				{
					vbttId = vbttId.substring(0, vbttId.length() - 1);
					tbBean.setVbttId(vbttId);
				}
			}
			
			String[] vbsdbsIds = multi.getParameterValues("vbsdbsIds");
			if (vbsdbsIds != null)
			{
				String vbsdbsId = "";
				for (int i = 0; i < vbsdbsIds.length; i++)
					vbsdbsId += vbsdbsIds[i] + ",";
				
				if (vbsdbsId.trim().length() > 0)
				{
					vbsdbsId = vbsdbsId.substring(0, vbsdbsId.length() - 1);
					tbBean.setVbsdbsId(vbsdbsId);
				}
				
				System.out.println("---Van ban sua doi bo sung: " + vbsdbsId);
			}
			
			tbBean.setNoidung(noidung);
			tbBean.setTieude(tieude);
			tbBean.setNgaybatdau(ngaybatdau);
			tbBean.setNgayketthuc(ngayketthuc);
			tbBean.setUserId(userId);
			
			String id = util.antiSQLInspection(multi.getParameter("id"));
			tbBean.setId(id);
			
			String kvId = util.antiSQLInspection(multi.getParameter("kvId"));
			if (kvId == null)
				kvId = "";
			tbBean.setKhuvuc(kvId);
			
			String vungId = util.antiSQLInspection(multi.getParameter("vungId"));
			if (vungId == null)
				vungId = "";
			tbBean.setVung(vungId);
			
			String removename = util.antiSQLInspection(multi.getParameter("removename"));
			if (removename == null)
				removename = "";
			
			String[] filenames = multi.getParameterValues("filename");
			String s = "";
			for(int i=0; i< filenames.length; i++)
				s += filenames[i] + ",";
			if(s.length() > 0)
				s = s.substring(0, s.length() - 1);
			System.out.println("s =" + s + ";");
			tbBean.setFile(s);
			
			String action = util.antiSQLInspection(multi.getParameter("action"));
			
			if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
			{
				Enumeration<?> files = multi.getFileNames();
				String filename = "";
				while (files.hasMoreElements())
				{
					String name = (String) files.nextElement();
					filename = multi.getFilesystemName(name);
					System.out.println("FILE NAME......" + filename+";");
				}
				if(filename != null && filename.length() > 0){
					if(s.length() > 0)
						tbBean.setFile(s + "," + filename);
					else
						tbBean.setFile(filename);
					System.out.println("___S:" + s + "," + filename);
				}
			
			}
			
			System.out.println("___Action: " + action);
			if (action.equals("save"))
			{
				if (id == null)
				{
					if (!tbBean.createTb())
					{
						System.out.println("Không được create");
						tbBean.createRs();
						session.setAttribute("tbBean", tbBean);
						String nextJSP = "/AHF/pages/Center/ThongBaoNew.jsp";
						if (viewMode.equals("0"))
							nextJSP = "/AHF/pages/Center/ThongBaoNew_NPP.jsp";
						response.sendRedirect(nextJSP);
					} else
					{
						IThongbaoList obj = new ThongbaoList();
						obj.setViewMode(viewMode);
						obj.setUserId(userId);
						obj.setLoaithongbao(loaithongbao);
						obj.init("");
						session.setAttribute("obj", obj);
						String nextJSP = "/AHF/pages/Center/ThongBao.jsp";
						response.sendRedirect(nextJSP);
					}
				} else
				{
					if (!tbBean.updateTb())
					{
						System.out.println("Không được update");
						tbBean.createRs();
						session.setAttribute("tbBean", tbBean);
						String nextJSP = "/AHF/pages/Center/ThongBaoUpdate.jsp";
						if (viewMode.equals("0"))
							nextJSP = "/AHF/pages/Center/ThongBaoUpdate_NPP.jsp";
						response.sendRedirect(nextJSP);
					} else
					{
						IThongbaoList obj = new ThongbaoList();
						obj.setViewMode(viewMode);
						obj.setUserId(userId);
						System.out.println("----LOAI THONG BAO: " + loaithongbao);
						obj.setLoaithongbao(loaithongbao);
						obj.init("");
						session.setAttribute("obj", obj);
						String nextJSP = "/AHF/pages/Center/ThongBao.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			} else
			{
				if (action.equals("guitraloi"))
				{
					String noidungtraloi = multi.getParameter("noidungtraloi");
					if (noidungtraloi == null)
						noidungtraloi = "";
					tbBean.setNoidungtraloi(noidungtraloi);
					System.out.println("-----Noi dung TB: " + noidungtraloi);
					
					String trungtamTL = multi.getParameter("trungtamTL");
					if (trungtamTL == null)
						trungtamTL = "";
					
					String TT_TraLoi = multi.getParameter("TT_TraLoi");
					if (TT_TraLoi == null)
						TT_TraLoi = "";
					
					if (trungtamTL.equals("1"))
					{
						String nguoinhanTLId = multi.getParameter("nguoinhanTLId");
						if (nguoinhanTLId == null)
							nguoinhanTLId = "";
						tbBean.setNguoinhanTLId(nguoinhanTLId);
						
						if (TT_TraLoi.equals("1"))
							tbBean.guitraloiTbTT();
						
						tbBean.setId(id);
						tbBean.setViewMode("0");
						tbBean.setUserId(userId);
						
						tbBean.initDisplay();
						tbBean.initThaoLuanTT();
						
						session.setAttribute("tbBean", tbBean);
						String nextJSP = "/AHF/pages/Center/ThongBaoDisplay.jsp";
						response.sendRedirect(nextJSP);
						
					} else
					{
						tbBean.guitraloiTb();
						
						tbBean.setUserId(userId);
						tbBean.setId(id);
						tbBean.setViewMode("0");
						tbBean.initDisplay();
						tbBean.initThaoLuan();
						
						session.setAttribute("tbBean", tbBean);
						String nextJSP = "/AHF/pages/Center/ThongBaoNvDisplay.jsp";
						response.sendRedirect(nextJSP);
					}
				} else
				{
					if (action.equals("download"))
					{
						System.out.println("___Vao DOWNLOAD FILE....");
						String fileName = multi.getParameter("tenfile");
						if (fileName == null)
							fileName = "";
						
						if (fileName.trim().length() > 0)
						{
							try
							{
								FileInputStream fileToDownload = new FileInputStream("C:\\java-tomcat\\dinhkem\\" + fileName);
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
							tbBean.setUserId(userId);
							tbBean.setId(id);
							tbBean.setViewMode(viewMode);
							tbBean.initDisplay();
							tbBean.initThaoLuan();
							
							session.setAttribute("tbBean", tbBean);
							String nextJSP = "/AHF/pages/Center/ThongBaoNvDisplay.jsp";
							response.sendRedirect(nextJSP);
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
						
						if (!this.deletefile("C:\\java-tomcat\\dinhkem\\" + removename))
						{
							out.write("Lỗi xóa file");
						} else
						{
							out.write("");
						}
						
						tbBean.setFile(s);
						String nextJSP = "";
						tbBean.createRs();
						session.setAttribute("tbBean", tbBean);
						if (id == null)
						{
							nextJSP = "/AHF/pages/Center/ThongBaoNew.jsp";
							if (viewMode.equals("0"))
								nextJSP = "/AHF/pages/Center/ThongBaoNew_NPP.jsp";
						} 
						else
						{
							nextJSP = "/AHF/pages/Center/ThongBaoUpdate.jsp";
							if (viewMode.equals("0"))
								nextJSP = "/AHF/pages/Center/ThongBaoUpdate_NPP.jsp";
						}
						
						response.sendRedirect(nextJSP);
					}
					else
					{
						System.out.println("Vào lọc nhân viên");
						tbBean.createRs();
						session.setAttribute("tbBean", tbBean);
						String nextJSP = "";
						
						if (id == null)
						{
							nextJSP = "/AHF/pages/Center/ThongBaoNew.jsp";
							if (viewMode.equals("0"))
								nextJSP = "/AHF/pages/Center/ThongBaoNew_NPP.jsp";
						} else
						{
							nextJSP = "/AHF/pages/Center/ThongBaoUpdate.jsp";
							if (viewMode.equals("0"))
								nextJSP = "/AHF/pages/Center/ThongBaoUpdate_NPP.jsp";
						}
						
						response.sendRedirect(nextJSP);
					}
				}
			}
		}
	}

	
}
