package geso.dms.center.servlets.chitieu;

import geso.dms.center.beans.chitieu.IChuLuc;
import geso.dms.center.beans.chitieu.IChuLucList;
import geso.dms.center.beans.chitieu.imp.ChuLuc;
import geso.dms.center.beans.chitieu.imp.ChuLucList;
import geso.dms.center.util.Utility;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Sheet;
import jxl.Workbook;

import com.oreilly.servlet.MultipartRequest;

@WebServlet("/ChuLucUpdateSvl")
public class ChuLucUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	public ChuLucUpdateSvl()
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		IChuLuc tctskuBean;

		this.out = response.getWriter();
		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		out.println(userId);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

		String id = util.getId(querystring);

		tctskuBean = new ChuLuc(id);
		tctskuBean.setId(id);
		tctskuBean.setUserId(userId);

		tctskuBean.init();
		session.setAttribute("tctskuBean", tctskuBean);

		String nextJSP = "/AHF/pages/Center/ChuLucUpdate.jsp";
		if (querystring.indexOf("display") >= 0)
			nextJSP = "/AHF/pages/Center/ChuLucDisplay.jsp";

		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IChuLuc tctskuBean;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		this.out = response.getWriter();
		Utility util = new Utility();

		String contentType = request.getContentType();
		String id=null; 
		String userId =null;
		String diengiai =null;
		String scheme =null;
		String thang =null;
		String nam =null;
		String quy =null;
		String apdung =null;
		String loaichitieu =null;
		String view =null;
		String tructhuocId =null;
		String vungId =null;
		String kvId =null;
		String action=null;
		String doituong=null;
		
		String thuongSR=null;
		String thuongSS=null;
		String thuongASM=null;
		String thuongRSM=null;
		String sonhomdat=null;
		
		

		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
			//MultipartRequest multi = new MultipartRequest(request, "C:\\java-tomcat\\data\\", 20000000, "UTF-8");
			MultipartRequest multi = new MultipartRequest(request, getServletContext().getRealPath("\\upload\\"), 20000000, "UTF-8"); 
			id = util.antiSQLInspection(multi.getParameter("id"));
			if (id == null)
			{
				tctskuBean = new ChuLuc("");
			} else
			{
				tctskuBean = new ChuLuc(id);
			}

			userId = util.antiSQLInspection(multi.getParameter("userId"));
			tctskuBean.setUserId(userId);

			diengiai = util.antiSQLInspection(multi.getParameter("diengiai"));
			if (diengiai == null)
				diengiai = "";
			tctskuBean.setDiengiai(diengiai);

			scheme = util.antiSQLInspection(multi.getParameter("scheme"));
			if (scheme == null)
				scheme = "";
			tctskuBean.setScheme(scheme);

			thang = util.antiSQLInspection(multi.getParameter("thang"));
			if (thang == null)
				thang = "";
			tctskuBean.setThang(thang);

			nam = util.antiSQLInspection(multi.getParameter("nam"));
			if (nam == null)
				nam = "";
			tctskuBean.setNam(nam);

			quy = util.antiSQLInspection(multi.getParameter("quy") == null ? "" : multi.getParameter("quy"));
			tctskuBean.setQuy(quy);

			apdung = util.antiSQLInspection(multi.getParameter("apdung") == null ? "" : multi.getParameter("apdung"));
			tctskuBean.setApdung(apdung);

			loaichitieu = util.antiSQLInspection(multi.getParameter("loaichitieu") == null ? "0" : multi.getParameter("loaichitieu"));
			tctskuBean.setLoaichitieu(loaichitieu);

			view = util.antiSQLInspection(multi.getParameter("view") == null ? "" : multi.getParameter("view"));
			tctskuBean.setView(view);

			tructhuocId = util.antiSQLInspection(multi.getParameter("tructhuocId") == null ? "" : multi.getParameter("tructhuocId"));
			tctskuBean.setTructhuocId(tructhuocId);
			
			doituong = util.antiSQLInspection(multi.getParameter("doituong") == null ? "" : multi.getParameter("doituong"));
			tctskuBean.setDoituong(doituong);
			
			
			sonhomdat = util.antiSQLInspection(multi.getParameter("sonhomdat") == null ? "" : multi.getParameter("sonhomdat"));
			tctskuBean.setSonhomdat(sonhomdat);

			thuongSR = util.antiSQLInspection(multi.getParameter("thuongSR") == null ? "" : multi.getParameter("thuongSR"));
			tctskuBean.setThuongSR(thuongSR);
			
			thuongSS = util.antiSQLInspection(multi.getParameter("thuongSS") == null ? "" : multi.getParameter("thuongSS"));
			tctskuBean.setThuongSS(thuongSS);

			thuongASM = util.antiSQLInspection(multi.getParameter("thuongASM") == null ? "" : multi.getParameter("thuongASM"));
			tctskuBean.setThuongASM(thuongASM);
			
			thuongRSM = util.antiSQLInspection(multi.getParameter("thuongRSM") == null ? "" : multi.getParameter("thuongRSM"));
			tctskuBean.setThuongRSM(thuongRSM);


			

			action = util.antiSQLInspection(multi.getParameter("action") == null ? "" : multi.getParameter("action"));

			Enumeration files = multi.getFileNames();
			String filenameu = "";
			while (files.hasMoreElements())
			{
				String name = (String) files.nextElement();
				filenameu = multi.getFilesystemName(name);
				System.out.println("name  " + name);
			}

			//String filename = "C:\\java-tomcat\\data\\" + filenameu;
			String filename=    getServletContext().getRealPath("\\upload\\")+ "\\"+ filenameu;
			if (filename.length() > 0)
			{
				File file = new File(filename);
				Workbook workbook;
				int indexRow = 5;
				try
				{					
					workbook = Workbook.getWorkbook(file);
					Sheet sheet = workbook.getSheet(0);
					int sodong = sheet.getRows();
					int socot = sheet.getColumns();
					String sql="";
					String sql_NHOM="";
					int index=0;
					for (int row=6; row < sodong ; row++)
					{
						index++;
						String nvId =getValue(sheet, 0,row);
						String nvTen =getValue(sheet, 1,row);
						String nvLoai =getValue(sheet, 2,row);
						String ChiTieu =getValue(sheet, 3,row);
						String SoDonHang =getValue(sheet, 4,row);
						String GiaTriDonHang =getValue(sheet, 5,row);
						String SoKhachHangMoi =getValue(sheet, 6,row);
						String SoKhachHangMuaHang =getValue(sheet, 7,row);
						if(index!=1)
						{
							sql+=" union all ";							
						}
						sql +=
								"\n  select '"+nvId+"' as nvId,'"+nvLoai+"' as nvLoai, N'"+nvTen+"' as nvTen,  '"+ChiTieu+"' as ChiTieu,'"+SoDonHang+"' as SoDonHang,'"+GiaTriDonHang+"' as GiaTriTBDonHang,'"+SoKhachHangMoi +"' as SoKhachHangMoi ," +
										"\n  '"+SoKhachHangMuaHang+"' as SoKhachHangMuaHang  ";

						for(int i=8;i<socot;i++)
						{
							String nhomId = getValue(sheet, i,indexRow);
							String nhomValue =getValue(sheet, i,row);
							if(nhomId.length()>0 && nhomValue.length()>0)
							{
								if(index!=1)
								{
									sql_NHOM +=" union all ";
								}

								sql_NHOM +="\n select '"+nvId+"' as nvId,'"+nvLoai+"' as nvLoai,'"+nhomId+"' as nhomId,"+nhomValue+" as ChiTieu ";
								System.out.println("[i]"+i+"[index]"+index);
								if(i==8&&index==1)
								{
									sql_NHOM +=" union all ";
								}
							}
						}
						tctskuBean.setSql(sql);
						tctskuBean.setSqlNhom(sql_NHOM);
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		else
		{
			id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
			if (id == null)
			{
				tctskuBean = new ChuLuc("");
			} else
			{
				tctskuBean = new ChuLuc(id);
			}

			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
			tctskuBean.setUserId(userId);

			diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
			if (diengiai == null)
				diengiai = "";
			tctskuBean.setDiengiai(diengiai);

			scheme = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("scheme")));
			if (scheme == null)
				scheme = "";
			tctskuBean.setScheme(scheme);

			thang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thang")));
			if (thang == null)
				thang = "";
			tctskuBean.setThang(thang);

			nam = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nam")));
			if (nam == null)
				nam = "";
			tctskuBean.setNam(nam);

			quy = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("quy") == null ? "" : request.getParameter("quy")));
			tctskuBean.setQuy(quy);

			apdung = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("apdung") == null ? "" : request.getParameter("apdung")));
			tctskuBean.setApdung(apdung);

			loaichitieu = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaichitieu") == null ? "0" : request.getParameter("loaichitieu")));
			tctskuBean.setLoaichitieu(loaichitieu);

			view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view") == null ? "" : request.getParameter("view")));
			tctskuBean.setView(view);

			tructhuocId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tructhuocId") == null ? "" : request.getParameter("tructhuocId")));
			tctskuBean.setTructhuocId(tructhuocId);

			loaichitieu = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaichitieu") == null ? "0" : request.getParameter("loaichitieu")));
			tctskuBean.setLoaichitieu(loaichitieu);
			
			doituong = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("doituong") == null ? "0" : request.getParameter("doituong")));
			tctskuBean.setDoituong(doituong);
			
			sonhomdat = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sonhomdat") == null ? "" : request.getParameter("sonhomdat").replaceAll(",", "")));
			tctskuBean.setSonhomdat(sonhomdat);

			thuongSR = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thuongSR") == null ? "" : request.getParameter("thuongSR").replaceAll(",", "")));
			tctskuBean.setThuongSR(thuongSR);
			
			thuongSS = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thuongSS") == null ? "" : request.getParameter("thuongSS").replaceAll(",", "")));
			tctskuBean.setThuongSS(thuongSS);

			thuongASM = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thuongASM") == null ? "" : request.getParameter("thuongASM").replaceAll(",", "")));
			tctskuBean.setThuongASM(thuongASM);
			
			thuongRSM = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thuongRSM") == null ? "" : request.getParameter("thuongRSM").replaceAll(",", "")));
			
			tctskuBean.setThuongRSM(thuongRSM);
			
			vungId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"));
			if (vungId == null)
				vungId = "";
			tctskuBean.setVungIds(vungId);

			kvId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId"));
			if (kvId == null)
				kvId = "";
			tctskuBean.setKhuvucIds(kvId);

			
			String[] nhomId = request.getParameterValues("nhomId");
			String[] nhomTrongso = request.getParameterValues("nhomTrongso");
			String[] chonban = request.getParameterValues("nhomchon");
			String[] nhomTen =request.getParameterValues("nhomTen");
			
			
			if (nhomId != null)
			{
				Hashtable<String, String> spChonban = new Hashtable<String, String>();
				for (int i = 0; i < nhomId.length; i++)
				{
					if(nhomId[i]!=null)
					{
						spChonban.put(nhomId[i],nhomTrongso[i].replaceAll(",", "")); 
					}
				}
				tctskuBean.setNhomTrongso(spChonban);
			}
			
			if (chonban != null)
			{
				Hashtable<String, String> spChonban = new Hashtable<String, String>();
				for (int i = 0; i < chonban.length; i++)
				{
					if(chonban[i]!=null)
					{
						spChonban.put(chonban[i],nhomTen[i] );
					}
				}
				tctskuBean.setNhomchon(spChonban);
			}
			
			String[] nvId =request.getParameterValues("nvId");
			if(nvId!=null)
			{
				Hashtable<String, String> spChonban = new Hashtable<String, String>();
				for (int i = 0; i < nvId.length; i++)
				{
					if(nvId[i]!=null)
					{
						for(int j=0;(chonban!=null && j<chonban.length);j++)
						{
							String[] ctNhom =request.getParameterValues("ctNhom_"+chonban[j]);
							if(ctNhom[i]!=null && ctNhom[i].length()>0)
							{
								spChonban.put( nvId[i]+ "__"+ chonban[j],ctNhom[i].replaceAll(",", "")); 
							}
						}
					}
				}
				tctskuBean.setTdv_chitieu(spChonban);
			}
			action = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action") == null ? "" : request.getParameter("action")));
		}

		System.out.println("Action la: " + action);

		if (action.equals("save"))
		{
			if (id == null)
			{
				if (!tctskuBean.save())
				{
					tctskuBean.setUserId(userId);
					tctskuBean.createRs();
					session.setAttribute("userId", userId);
					session.setAttribute("tctskuBean", tctskuBean);
					String nextJSP = "/AHF/pages/Center/ChuLucNew.jsp";
					response.sendRedirect(nextJSP);
				} else
				{
					IChuLucList obj = new ChuLucList();
					obj.setUserId(userId);
					obj.setView(view);
					obj.init("");
					session.setAttribute("obj", obj);
					String nextJSP = "/AHF/pages/Center/ChuLuc.jsp";
					response.sendRedirect(nextJSP);
				}
			} else
			{
				if (!(tctskuBean.edit()))
				{
					tctskuBean.setUserId(userId);
					tctskuBean.createRs();
					session.setAttribute("userId", userId);
					session.setAttribute("tctskuBean", tctskuBean);

					String nextJSP = "/AHF/pages/Center/ChuLucUpdate.jsp";
					response.sendRedirect(nextJSP);
				} else
				{
					IChuLucList obj = new ChuLucList();
					obj.setUserId(userId);
					obj.setView(view);
					obj.init("");
					session.setAttribute("obj", obj);
					String nextJSP = "/AHF/pages/Center/ChuLuc.jsp";
					response.sendRedirect(nextJSP);
				}
			}
		} else
		{
			tctskuBean.createRs();
			session.setAttribute("userId", userId);
			session.setAttribute("tctskuBean", tctskuBean);

			String nextJSP;
			if (id == null)
			{
				nextJSP = "/AHF/pages/Center/ChuLucNew.jsp";
			} else
			{
				nextJSP = "/AHF/pages/Center/ChuLucUpdate.jsp";
			}
			response.sendRedirect(nextJSP);
		}
	}


	String getValue(Sheet sheet,int col,int row)
	{
		return sheet.getCell(col,row).getContents().trim().replaceAll(",", "");
	}


	public String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

}
