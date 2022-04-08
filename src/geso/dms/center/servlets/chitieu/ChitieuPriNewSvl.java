package geso.dms.center.servlets.chitieu;

import geso.dms.center.beans.chitieu.IChiTieu;
import geso.dms.center.beans.chitieu.imp.ChiTieu;
import geso.dms.center.util.Utility;
import geso.dms.distributor.db.sql.dbutils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.oreilly.servlet.MultipartRequest;

@WebServlet("/ChitieuPriSvl")
public class ChitieuPriNewSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ChitieuPriNewSvl()
	{
		super();

	}

	private String gettenuser(String userId_)
	{
		dbutils db = new dbutils();
		String sql_getnam = "select ten from nhanvien where pk_seq=" + userId_;
		ResultSet rs_tenuser = db.get(sql_getnam);
		String ten = "";
		if (rs_tenuser != null)
		{
			try
			{
				while (rs_tenuser.next())
				{
					ten = rs_tenuser.getString("ten");
				}
				rs_tenuser.close();
			} catch (Exception er)
			{

			}

		}
		db.shutDown();
		return ten;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();
		String querystring = request.getQueryString();
		Utility util = new Utility();
		String userId = util.getUserId(querystring);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		String id = util.getId(querystring);
		String loaict = "";
		loaict = (String) session.getAttribute("loaichitieu");
		if (loaict == null)
		{
			loaict = "";
			try
			{
				// lay gia tri loai ct
				String tmp;

				if (querystring != null)
				{
					if (querystring.contains("&"))
					{
						tmp = querystring.split("&")[2];
					} else
					{
						tmp = querystring;
					}
					loaict = tmp.split("=")[1];
				}
			} catch (Exception er)
			{

			}
		}
		IChiTieu obj = new ChiTieu(id, "0");

		obj.setUserId(userId);
		session.setAttribute("loaichitieu", loaict);
		obj.CreateRs();

		Utility Ult = new Utility();

		session.setAttribute("userId", userId);
		String tenuser = gettenuser(userId);
		session.setAttribute("userTen", tenuser);

		session.setAttribute("obj", obj);
		String action = util.getAction(querystring);
		session.setAttribute("check", "0");

		if (action.equals("update"))
		{

			String nextJSP = "/AHF/pages/Center/ChitieuPriUpdate.jsp";// default
			response.sendRedirect(nextJSP);
		} else if (action.equals("display"))
		{
			String nextJSP = "/AHF/pages/Center/ChitieuPriDisplay.jsp";// default
			response.sendRedirect(nextJSP);
		} else if (action.equals("excel"))
		{
			XuatFileExcel(response, id);
		}
	}

	private void XuatFileExcel(HttpServletResponse response, String id) throws IOException
	{
		// TODO Auto-generated method stub
		OutputStream out1 = null;
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=ChiTieuPri.xls");
			WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
			WritableSheet sheet = w.createSheet("Demo", 0);
			dbutils db = new dbutils();
			String sql = "select thang,nam,dvkd.diengiai,kbh.ten from chitieu ct inner join donvikinhdoanh dvkd on dvkd.pk_seq=dvkd_fk " + " "+
					" inner join kenhbanhang kbh on kbh.pk_seq=kenh_fk " + " where ct.pk_seq=" + id;

			System.out.println("1.Khoi tao upload : " + sql);

			ResultSet rs = db.get(sql);
			if (rs.next())
			{

				sheet.addCell(new Label(0, 0, "CHI TIEU THANG " + rs.getString("thang") + "-" + rs.getString("nam")));
				sheet.addCell(new Label(0, 2, "DVKD:" + rs.getString("diengiai")));
				sheet.addCell(new Label(0, 3, "KENH:" + rs.getString("ten")));

				sheet.addCell(new Label(0, 5, "MA NPP"));
				sheet.addCell(new Label(1, 5, "TEN NPP"));

				sql = "select distinct nhomsp_fk from CHITIEU_NHAPP_NHOMSP where chitieu_fk=" + id;

				rs = db.get(sql);
				String chuoingoac = "";
				String[] chuoi = new String[20];
				int i = 0;
				while (rs.next())
				{

					//
					// sheet.addCell(new Label(6+i, 4,
					// rs.getString("tennhom")));

					sheet.addCell(new Label(2 + i, 5, rs.getString("nhomsp_fk")));
					chuoi[i] = rs.getString("nhomsp_fk");

					if (chuoingoac.equals(""))
					{
						chuoingoac = "[" + rs.getString("nhomsp_fk") + "]";

					} else
					{
						chuoingoac = chuoingoac + ",[" + rs.getString("nhomsp_fk") + "]";

					}
					i++;

				}
				rs.close();
				System.out.println("i "+i);
				sql = "select * from " + "( " + "select ctnhom.nhomsp_fk,ctnhom.chitieu,npp.pk_seq as nppid ,npp.ten as nppten " + "from chitieu ct " + "inner join CHITIEU_NHAPP_NHOMSP ctnhom on ctnhom.chitieu_fk=ct.pk_seq "
						+ "inner join nhaphanphoi npp on npp.pk_seq=ctnhom.npp_fk " + "where ct.pk_seq= " + id + ") p " + "pivot(sum(chitieu) for nhomsp_fk in (" + chuoingoac + ")) as chitieu ";

				System.out.println("Chi tieu primary : " + sql);

				ResultSet rs1 = db.get(sql);
				Label label;
				Number number;
				int j = 6;
				if (rs1 != null)
					while (rs1.next())
					{

						label = new Label(0, j, rs1.getString("nppid"));
						sheet.addCell(label);

						label = new Label(1, j, rs1.getString("nppten"));
						sheet.addCell(label);

						for (int k = 0; k < i; k++)
						{
							number = new Number(2 + k, j, rs1.getDouble(chuoi[k]));
							sheet.addCell(number);
						}
						j++;
					}

				w.write();
				w.close();
			}
		} catch (Exception e)
		{
			System.out.println("Error Cac Ban : " + e.toString());
			e.printStackTrace();
		} finally
		{
			if (out1 != null)
				out1.close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		dbutils db = new dbutils();
		IChiTieu chitieu = new ChiTieu();

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String contentType = request.getContentType();

		Utility util = new Utility();

		System.out.println("contentType " + contentType);
		System.out.println("contentType.indexOf(multipart/form-data;charset=utf-8)    " + contentType);
		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
			//MultipartRequest multi = new MultipartRequest(request, "C:\\java-tomcat\\data\\", 20000000, "UTF-8");
			MultipartRequest multi = new MultipartRequest(request, getServletContext().getRealPath("\\upload\\"), 20000000, "UTF-8"); 

			String userId = util.antiSQLInspection(multi.getParameter("userId"));
			chitieu.setUserId(userId);
			Enumeration files = multi.getFileNames();
			String filenameu = "";
			while (files.hasMoreElements())
			{
				String name = (String) files.nextElement();
				filenameu = multi.getFilesystemName(name);
				System.out.println("name  " + name);
				;
			}

			// ///////////////////////////////////
			String thang =util.antiSQLInspection(multi.getParameter("thang"));
			chitieu.setThang(thang);

			String nam =util.antiSQLInspection(multi.getParameter("nam"));
			chitieu.setNam(nam);


			String quy =util.antiSQLInspection(multi.getParameter("quy"));
			chitieu.setQuy(quy);
			
			String id = util.antiSQLInspection(multi.getParameter("id"));
			try
			{

				chitieu.setID(Double.parseDouble(id));

			} catch (Exception err)
			{
			}
			String dvkdid = util.antiSQLInspection(multi.getParameter("dvkdid"));
			chitieu.setDVKDID(dvkdid);
			String kbhid = util.antiSQLInspection(multi.getParameter("kbhid"));
			chitieu.setKenhId(kbhid);
			String songaylamviec = util.antiSQLInspection(multi.getParameter("songaylamviec"));
			chitieu.setSoNgayLamViec(songaylamviec);
			String ngayketthuc = util.antiSQLInspection(multi.getParameter("ngayketthuc"));
			chitieu.setNgayKetThuc(ngayketthuc);

			String diengiai = util.antiSQLInspection(multi.getParameter("diengiai"));
			chitieu.setDienGiai(diengiai);

			String loaichitieu = util.antiSQLInspection(multi.getParameter("loaichitieu"));
			chitieu.setLoaiChiTieu(loaichitieu);

			chitieu.setKhuVucID("");
			chitieu.setNguoiSua(userId);
			chitieu.setNguoiTao(userId);
			chitieu.setNgayTao(this.getDateTime());
			chitieu.setNgaySua(this.getDateTime());

			//String filename = "C:\\java-tomcat\\data\\" + filenameu;
			String filename=    getServletContext().getRealPath("\\upload\\")+ "\\"+ filenameu;
			if (filename.length() > 0)
			{
				// doc file excel
				File file = new File(filename);
				System.out.println("filename  " + file);
				Workbook workbook;
				ResultSet rs = null;

				int indexRow = 5;
				int j = 2;
				try
				{

					System.out.println(file);
					workbook = Workbook.getWorkbook(file);
					Sheet sheet = workbook.getSheet(0);
					Cell[] cells = sheet.getRow(indexRow);

					String nhomspid = "";
					int dodai = 2;
					while (dodai < cells.length)
					{
						if (cells[dodai].getContents().trim().length() > 0)
						{
							dodai = dodai + 1;

						} else
						{

							break;
						}
					}

					System.out.println("Do Dai :" + dodai);

					while (j < dodai)
					{
						if (j == 2)
						{
							nhomspid = "0";
						} else
						{
							nhomspid = nhomspid + ";" + cells[j].getContents();
						}
						j++;
						System.out.println("nhomSpID : " + nhomspid);
					}
					chitieu.setChuoiNhomSp(nhomspid);
					String chuoi = "";
					for (j = 1; j < dodai - 1; j++)
					{
						chuoi = chuoi + "," + "manhom" + j;
					}
					System.out.println(chuoi);
					chitieu.setKhuVucID(chuoi);

					System.out.println("So Dong : " + sheet.getRows());
					for (int i = indexRow + 1; i < sheet.getRows(); i++)
					{
						cells = sheet.getRow(i);
						if (cells.length > 0)
						{

							if (cells[0].getContents().toString().length() > 0)
							{
								String values = "";
								for (j = 0; j < dodai; j++)
								{
									if (j != 1)
									{
										if (j == 0)
										{
											values = "'" + cells[j].getContents() + "'";
										} else
										{
											String sotmp = "0";
											try
											{
												//sotmp = Float.parseFloat(cells[j].getContents().toString().replace(",", ""));
												sotmp = cells[j].getContents().toString().replace(",", "");

											} catch (Exception er)
											{

											}
											values = values + ",'" + sotmp + "'";
										}
									}
								}
								String sql = " insert into chitieutmp (ma" + chuoi + ")values(" + values + ")";
								System.out.println("sql : " + sql);
								db.update(sql);
							}
						}
					}
					// sau khi doc vao bang tam,se thuc hien lay du lieu ra
					if (id.equals("0"))
					{
						if (chitieu.SaveChiTieu())
						{
							// Thanh cong
							//session.setAttribute("loaichitieu", "0");
							chitieu.setRsPri("");
							chitieu.setView("TT");
							session.setAttribute("obj", chitieu);
							String nextJSP = "/AHF/pages/Center/ChitieuPri.jsp";
							response.sendRedirect(nextJSP);
							System.out.println(chitieu.getMessage());
						} else
						{
							System.out.println("Khong Thanh Cong");
							String nextJSP = "/AHF/pages/Center/ChitieuPriNew.jsp";// default
							// Xoa het chi tieu cu di
							chitieu.setUserId(userId);
							chitieu.CreateRs();
							session.setAttribute("obj", chitieu);
							response.sendRedirect(nextJSP);
							System.out.println(chitieu.getMessage());
						}
					} else
					{
						if (chitieu.EditChiTieu())
						{
							chitieu.setRsPri("");
							chitieu.setView("TT");
							session.setAttribute("obj", chitieu);
							String nextJSP = "/AHF/pages/Center/ChitieuPri.jsp";
							response.sendRedirect(nextJSP);
							System.out.println(chitieu.getMessage());

						} else
						{
							String nextJSP = "/AHF/pages/Center/ChitieuPriUpdate.jsp";// default
							chitieu.setUserId(userId);
							chitieu.CreateRs();
							session.setAttribute("obj", chitieu);
							response.sendRedirect(nextJSP);
						}
					}
					String sql = "delete chitieutmp";
					db.update(sql);
				} catch (Exception er)
				{
					chitieu.setMessage("Thong bao loi : " + er.toString());
					System.out.println(er.toString());
				}
			}
		} else
		{
			
			System.out.println("[CapNhat]");
			
			String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
			chitieu.setUserId(userId);

			String thang =util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thang")));
			chitieu.setThang(thang);

			String nam =util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nam")));
			chitieu.setNam(nam);


			String quy =util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("quy")==null?"":request.getParameter("quy")));
			chitieu.setQuy(quy);
			
			
			String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
			try
			{

				chitieu.setID(Double.parseDouble(id));

			} catch (Exception err)
			{
			}
			String dvkdid = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdid")));
			chitieu.setDVKDID(dvkdid);
			String kbhid = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhid")));
			chitieu.setKenhId(kbhid);
			String songaylamviec = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("songaylamviec")));
			chitieu.setSoNgayLamViec(songaylamviec);
			String ngayketthuc = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngayketthuc")));
			chitieu.setNgayKetThuc(ngayketthuc);

			String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
			chitieu.setDienGiai(diengiai);

			String loaichitieu = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaichitieu")));
			chitieu.setLoaiChiTieu(loaichitieu);

			chitieu.setKhuVucID("");
			chitieu.setNguoiSua(userId);
			chitieu.setNguoiTao(userId);
			chitieu.setNgayTao(this.getDateTime());
			chitieu.setNgaySua(this.getDateTime());

			String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
			if (action.equals("save"))
			{
				if (chitieu.EditChiTieu(request))
				{
					chitieu = new  ChiTieu();
					chitieu.setUserId(userId);
					chitieu.setView("TT");
					chitieu.setRsPri("");
					session.setAttribute("obj", chitieu);
					String nextJSP = "/AHF/pages/Center/ChitieuPri.jsp";
					response.sendRedirect(nextJSP);
				} else
				{
					String msg=chitieu.getMessage();
					chitieu = new  ChiTieu(id,"0");
					chitieu.setMessage(msg);
					String nextJSP = "/AHF/pages/Center/ChitieuPriUpdate.jsp";
					session.setAttribute("obj", chitieu);
					response.sendRedirect(nextJSP);
				}
			} else
			{
				session.setAttribute("userId", userId);
				session.setAttribute("check", "0");
				session.setAttribute("obj", chitieu);
				String nextJSP = "/AHF/pages/Center/ChiTieuPriDisplay.jsp";
				response.sendRedirect(nextJSP);
			}
		}
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		return dateFormat.format(date);
	}
}
