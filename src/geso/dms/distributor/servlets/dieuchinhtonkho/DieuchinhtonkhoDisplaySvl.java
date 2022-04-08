package geso.dms.distributor.servlets.dieuchinhtonkho;

import geso.dms.center.db.sql.dbutils;
import geso.dms.distributor.beans.dieuchinhtonkho.IDieuchinhtonkho;
import geso.dms.distributor.beans.dieuchinhtonkho.IDieuchinhtonkhoList;
import geso.dms.distributor.beans.dieuchinhtonkho.imp.Dieuchinhtonkho;
import geso.dms.distributor.beans.dieuchinhtonkho.imp.DieuchinhtonkhoList;
import geso.dms.distributor.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import com.oreilly.servlet.MultipartRequest;

public class DieuchinhtonkhoDisplaySvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DieuchinhtonkhoDisplaySvl() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
//		PrintWriter out = response.getWriter();
		String nextJSP;
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	        
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String id = util.getId(querystring);  	
	    String action = util.getAction(querystring);

		IDieuchinhtonkho dctkBean = (IDieuchinhtonkho) new Dieuchinhtonkho();
		dctkBean.setUserId(userId);
		dctkBean.setId(id);
		dctkBean.setNppId(util.antiSQLInspection(request.getParameter("nppId")));
    	dctkBean.initDisplay();
    	session.setAttribute("dctkBean", dctkBean);
    	nextJSP = "/AHF/pages/Distributor/DieuChinhTonKhoDisplay.jsp";
    	response.sendRedirect(nextJSP);    
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		NumberFormat formatter = new DecimalFormat("#,###,###.###");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/AHF/index.jsp");
		} else
		{
			Utility util = new Utility();
			IDieuchinhtonkho dctkBean = (IDieuchinhtonkho) new Dieuchinhtonkho();
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			String id;
			String nppId;
			String khoId;
			String kbhId;
			String dvkdId;
			String lydodc;
			String ngaydc;
			String action = "";
			String sodong = "0";

			String[] spId = null;
			String[] spMa = null;
			String[] spTen = null;
			String[] spTon = null;
			String[] spTonMoi = null;
			String[] spDieuchinh = null;
			String[] spDongia = null;
			String[] spThanhtien = null;
			String[] spDonvi = null;
			MultipartRequest multi = null;

			String contentType = request.getContentType();
			if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
				multi = new MultipartRequest(request, "C:\\java-tomcat\\data\\", 20000000, "UTF-8");
			String removename = util.antiSQLInspection(request.getParameter("removename"));
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
			dctkBean.setFile(s);

			{
				action = request.getParameter("action");

				id = util.antiSQLInspection(request.getParameter("id"));
				if (id != null)
					dctkBean.setId(id);

				userId = util.antiSQLInspection(request.getParameter("userId"));
				if (userId == null)
				{
					response.sendRedirect("/DDT");
				} else
				{
					dctkBean.setUserId(userId);
				}
				nppId = util.antiSQLInspection(request.getParameter("nppId") == null ? "" : request.getParameter("nppId"));
				dctkBean.setNppId(nppId);

				khoId = util.antiSQLInspection(request.getParameter("khoId") == null ? "" : request.getParameter("khoId"));
				dctkBean.setKhoId(khoId);

				kbhId = util.antiSQLInspection(request.getParameter("kbhId") == null ? "" : request.getParameter("kbhId"));
				dctkBean.setKbhId(kbhId);

				dvkdId = util.antiSQLInspection(request.getParameter("dvkdId") == null ? "" : request.getParameter("dvkdId"));
				dctkBean.setDvkdId(dvkdId);

				ngaydc = util.antiSQLInspection(request.getParameter("ngaydc") == null ? "" : request.getParameter("ngaydc"));
				dctkBean.setNgaydc(ngaydc);

				lydodc = util.antiSQLInspection(request.getParameter("lydo") == null ? "" : request.getParameter("lydo"));
				dctkBean.setLydodc(lydodc);

				sodong = util.antiSQLInspection(request.getParameter("sodong") == null ? "" : request.getParameter("sodong"));
				if (sodong == null || sodong.trim().length() <= 0)
					sodong = "0";
				int sd = Integer.parseInt(sodong)+30;
				dctkBean.setSodong(sd+"");


				spId = request.getParameterValues("spId");
				spMa = request.getParameterValues("spMa");
				spTen = request.getParameterValues("spTen");
				spTon = request.getParameterValues("tonkho");
				spTonMoi = request.getParameterValues("tonkhomoi");
				spDieuchinh = request.getParameterValues("dieuchinh");
				spDongia = request.getParameterValues("dongia");
				spThanhtien = request.getParameterValues("thanhtien");
				spDonvi = request.getParameterValues("donvi");

				int n = 0;
				if (spMa != null)
				{
					int size = spMa.length;
					for (int i = 0; i < size; i++)
					{
						if (spTonMoi[i].length() > 0)
						{
							spTonMoi[i] = spTonMoi[i].replace(",", "");
							spDongia[i] = spDongia[i].replace(",", "");
							spTon[i] = spTon[i].replace(",", "");
							spThanhtien[i] = spThanhtien[i].replace(",", "");
							n++;
						}
					}
					dctkBean.setSpId(spId);
					dctkBean.setMasp(spMa);
					dctkBean.setTenSp(spTen);
					dctkBean.setSoluongle(spTonMoi);
					dctkBean.setTkht(spTon);
					dctkBean.setDonvi(spDonvi);
					dctkBean.setGiamua(spDongia);
					dctkBean.setTtien(spThanhtien);
					dctkBean.setDc(spDieuchinh);
					dctkBean.setSize(n + "");
				}

			}

			session.setAttribute("khoId", khoId);
			session.setAttribute("nppId", nppId);
			session.setAttribute("kbhId", kbhId);


				if (action.equals("download"))
				{
					System.out.println("___Vao DOWNLOAD FILE....");
					String fileName = request.getParameter("tenfile");
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
						dctkBean.setUserId(userId);
						dctkBean.setId(id);
						

						String nextJSP;
						dctkBean.initUpdate("2");
						nextJSP = "/AHF/pages/Distributor/DieuChinhTonKhoDisplay.jsp";
						
						session.setAttribute("dctkBean", dctkBean);
						response.sendRedirect(nextJSP);
					}
				} 
				{
					String nextJSP;
					if (id == null)
					{
						dctkBean.initNew();
						nextJSP = "/AHF/pages/Distributor/DieuChinhTonKhoNew.jsp";
					} else
					{
						dctkBean.initUpdate("2");
						nextJSP = "/AHF/pages/Distributor/DieuChinhTonKhoUpdate.jsp";
					}
					session.setAttribute("dctkBean", dctkBean);
					response.sendRedirect(nextJSP);
				}
		}
	
	}

}
