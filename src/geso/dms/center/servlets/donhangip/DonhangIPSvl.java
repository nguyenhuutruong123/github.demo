package geso.dms.center.servlets.donhangip;

import geso.dms.center.beans.doctien.doctienrachu;
import geso.dms.center.beans.donhangip.IDonhangIP;
import geso.dms.center.beans.donhangip.IDonhangListIP;
import geso.dms.center.beans.donhangip.ISanphamIP;
import geso.dms.center.beans.donhangip.imp.DonhangIP;
import geso.dms.center.beans.donhangip.imp.DonhangListIP;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.FixData;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.buf.UEncoder;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class DonhangIPSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet
{
	private static final long serialVersionUID = 1L;

	PrintWriter out;

	private int items = 50; 

	private int splittings = 20;

	public DonhangIPSvl() 
	{
		super();
	}

	private void settingPage(IDonhangListIP obj) {
		Utility util = new Utility();
		if(getServletContext().getInitParameter("items") != null){
			String i = getServletContext().getInitParameter("items").trim();
			if(util.isNumeric(i))
				items = Integer.parseInt(i);
		}

		if(getServletContext().getInitParameter("splittings") != null){
			String i = getServletContext().getInitParameter("splittings").trim();
			if(util.isNumeric(i))
				splittings = Integer.parseInt(i);
		}

		obj.setItems(items);
		obj.setSplittings(splittings);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{

		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			out  = response.getWriter();


			session.setMaxInactiveInterval(30000);

			Utility util = new Utility();
			out = response.getWriter();

			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);
			//out.println(userId);
			System.out.println();
			Date date = new Date();
			System.out.println("DonhangSvl user :" + userId + "  ,sessionId: " + session.getId() );

			if (userId.length()==0)
				userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
			String nppId;
			if(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")) != null)
				nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));

			//Lay Nha PP Theo Dang Nhap Moi
			nppId=util.getIdNhapp(userId);

			String action = util.getAction(querystring);

			String dhId = util.getId(querystring);

			if (action.equals("delete"))
			{	
				{
					if(!Delete(dhId))
					{
						out.print("Khong the xoa don hang nay :" + dhId);
						System.out.println("Khong the xoa don hang nay...");
					}
					else
					{
						out.print("Da xoa don hang nay :" + dhId);
						System.out.println("Da xoa don hang nay...");
					}
				}
			}


			IDonhangListIP obj;   
			obj = new DonhangListIP();
			obj.setUserId(userId);
			settingPage(obj);
			obj.init("");

			session.setAttribute("obj", obj);
			session.setAttribute("khId", "");

			String nextJSP = "/AHF/pages/Center/DonHangIP.jsp";
			response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			//	out = response.getWriter();


			session.setMaxInactiveInterval(30000);

			userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
			String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));


			String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
			if (action == null){
				action = "";
			}
			String loi = "";
			IDonhangListIP obj;
			obj = new DonhangListIP();
			settingPage(obj);
			if (action.equals("new"))
			{
				// Empty Bean for distributor
				IDonhangIP dhBean = (IDonhangIP) new DonhangIP("");
				dhBean.setUserId(userId);
				dhBean.createRS();

				// Save Data into session
				session.setAttribute("dhBean", dhBean);//truyen vao session mot doi tuong donhang co gia tri rong khi khoi tao de ben form don hang nhan dc
				session.setAttribute("khId", "");
				session.setAttribute("ddkdId", "");
				session.setAttribute("nppId", dhBean.getNppId());

				String nextJSP = "/AHF/pages/Center/DonHangIPNew.jsp";
				response.sendRedirect(nextJSP);
			} else
				/*	if (action.equals("XK"))
				{	
					String dhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dhId"));
					System.out.println("NPP: "+nppId+" DHID: "+dhId);
					if(nppId != null && dhId != null)
						loi = CreatePxk(dhId,userId,nppId);
					String search = getSearchQuery(request,obj);

					obj.setMsg(loi);
					obj.setUserId(userId);
					obj.init(search);    	  	
					session.setAttribute("userId", userId);
					session.setAttribute("obj", obj);
					response.sendRedirect("/AHF/pages/Center/DonHangIP.jsp");	    
				}else*/
				if (action.equals("chotALL"))
				{	
					String dhId[] = request.getParameterValues("donhangIds");
					if(dhId != null && nppId != null)
					{
						geso.dms.center.db.sql.dbutils db = new geso.dms.center.db.sql.dbutils();
						for(int i = 0; i < dhId.length; i++)
						{
							
							String sql = "update donhangip set trangthai = 1 where pk_seq = "+dhId[i];
							db.update(sql);
						}
						db.shutDown();
					}
					else
						loi ="Không thể chốt đơn hang !";

					String search = getSearchQuery(request,obj);
					obj.setMsg(loi);
					obj.setUserId(userId);
					obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
					obj.setUserId(userId);

					obj.init(search);
					session.setAttribute("obj", obj);
					response.sendRedirect("/AHF/pages/Center/DonHangIP.jsp");	    
				}
				else 
					/*if (action.equals("duyetALL"))
					{	
						loi = "";
						String dhId[] = request.getParameterValues("duyetdonhangIds");
						if(dhId != null && nppId != null)
						{
							for(int i = 0; i < dhId.length; i++)
							{
								try {
									loi += Duyetdonhang(dhId[i]);
								} catch (SQLException e) {
								
									e.printStackTrace();
								}
								if(loi.length() > 0)
								{
									break;
								}
							}
						}
						else
							loi ="Không thể duyệt đơn hang !";

						String search = getSearchQuery(request,obj);
						obj.setMsg(loi);
						obj.setUserId(userId);
						obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
						obj.setUserId(userId);

						obj.init(search);
						session.setAttribute("obj", obj);
						response.sendRedirect("/AHF/pages/Center/DonHangIP.jsp");	    
					}else*/
						if (action.equals("InALL"))
						{	
							String dhId[] = request.getParameterValues("IndonhangIds");
							if(dhId != null && nppId != null)
							{

								response.setContentType("application/pdf");
								response.setHeader("Content-Disposition"," inline; filename=HoaDonGTGT.pdf");
								Document document = new Document(PageSize.A5.rotate(),10, 20, 10, 20);

								dbutils db = new dbutils();
								ServletOutputStream outstream = response.getOutputStream();
								IDonhangIP dhBean = null;
								try {
									PdfWriter.getInstance(document, outstream);
								} catch (DocumentException e) {
									
									e.printStackTrace();
								}
								document.open();
								for(int i = 0; i < dhId.length; i++)
								{



									dhBean=new DonhangIP(dhId[i]); 
									dhBean.setUserId(userId); //phai co UserId truoc khi Init
									dhBean.init();	
									dhBean.setKhId(dhBean.getKhId());
									List<ISanphamIP> splist = (List<ISanphamIP>)dhBean.getSpList();
									List<ISanphamIP> spkmlist = (List<ISanphamIP>)dhBean.getScheme_SpList();
									Hashtable<String, Float> scheme_tongtien = dhBean.getScheme_Tongtien(); 
									Hashtable<String, Float> scheme_chietkhau = dhBean.getScheme_Chietkhau(); 

									createDonHangPdf(document, dhId[i], db, userId);


									document.newPage();
									splist.clear();
									spkmlist.clear();
									scheme_tongtien.clear();
									scheme_chietkhau.clear();


								}
								dhBean.DBclose();
								db.shutDown();
								document.close(); 
							}
							else
								loi ="Không thể in đơn hang !";

							String search = getSearchQuery(request,obj);
							obj.setMsg(loi);
							/*		obj.setUserId(userId);
							obj.init(search);    	  	
							session.setAttribute("userId", userId);
							session.setAttribute("obj", obj);
							response.sendRedirect("/AHF/pages/Center/DonHangIP.jsp");	    */
						}else
							if(action.equals("search"))
							{

								String search = getSearchQuery(request,obj);
								System.out.println("cau lenh chay:"+ search);
								obj.setUserId(userId);
								obj.init(search);
								// System.out.println("tu ngay"+ obj.getTungay());				    	  	
								session.setAttribute("userId", userId);
								session.setAttribute("obj", obj);

								response.sendRedirect("/AHF/pages/Center/DonHangIP.jsp");	    		    	
							}
							else 
								if(action.equals("view") || action.equals("next") || action.equals("prev")){

									String search = getSearchQuery(request, obj);

									obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
									obj.setUserId(userId);

									obj.init(search);
									obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
									session.setAttribute("obj", obj);
									response.sendRedirect("/AHF/pages/Center/DonHangIP.jsp");
								}


		}
	}
	
	String Duyetdonhang(String DonhangId) throws SQLException
	{
		// Duyệt đơn hàng, duyệt xong rồi mới đc phép chốt, chưa tính lượng hàng cần sản xuất ..
		geso.dms.center.db.sql.dbutils db = new geso.dms.center.db.sql.dbutils();
		try {
			db.getConnection().setAutoCommit(false);
			
			String query = " update donhangip set trangthai = 3 where pk_seq  = '"+DonhangId+"' ";
			
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Lỗi không duyệt được đơn hàng "+DonhangId;
			}
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (SQLException e) {
			db.getConnection().rollback();
			
			e.printStackTrace();
		}
		db.shutDown();
		return "";
		
	}
	
	private String getSearchQuery(HttpServletRequest request,IDonhangListIP obj) 
	{
		Utility util = new Utility();
		String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
		if ( nppId == null)
			nppId = "";
		obj.setNppId(nppId);

		String ddkdId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdTen"));
		if ( ddkdId == null)
			ddkdId = "";
		obj.setDdkdId(ddkdId);

		String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));
		if (trangthai == null)
			trangthai = "";    	
		obj.setTrangthai(trangthai);

		String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
		if (tungay == null)
			tungay = "";    	
		obj.setTungay(tungay);

		String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"));
		if (denngay == null)
			denngay = "";    	
		obj.setDenngay(denngay);

		String sodonhang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sodonhang"));
		if (sodonhang == null)
			sodonhang = "";    	
		obj.setSohoadon(sodonhang);

		String khachhang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khachhang"));
		if (khachhang == null)
			khachhang = "";    	
		obj.setKhachhang(khachhang);

		String tumuc = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tumuc"));
		if (tumuc == null)
			tumuc = "";    	
		obj.setTumuc(tumuc);

		String denmuc = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denmuc"));
		if (denmuc == null)
			denmuc = "";    	
		obj.setDenmuc(denmuc);

		String query = "select a.pk_seq as dhId, a.ngaynhap, a.trangthai, a.ngaytao, a.ngaysua, isnull(b.ten,e.ten) as nguoitao, isnull(c.ten,f.ten) as nguoisua, d.ten as khTen, d.pk_seq as khId, e.pk_seq as ddkdId, e.ten as ddkdTen,  a.tonggiatri, a.IsPDA,a.ddkdtao_fk, a.ddkdTao,0 as qtbh ";
		query = query + " from donhangip a left join nhanvien b on a.nguoitao = b.pk_seq "
					+ " left join nhanvien c on a.nguoisua = c.pk_seq inner join khachhang d on a.khachhang_fk = d.pk_seq "
					+ " left join daidienkinhdoanh e on a.nguoitao = e.pk_seq"
					+ " left join daidienkinhdoanh f on a.nguoisua = f.pk_seq ";
		query = query + " where 1 = 1";

		if (ddkdId.length() > 0)
		{
			query = query + " and e.pk_seq = '" + ddkdId + "'";		
		}    	
		if (trangthai.length() > 0)
		{
			query = query + " and a.trangthai ='" + trangthai + "'";			
		}    	
		if (tungay.length() > 0)
		{
			query = query + " and a.ngaynhap >= '" + tungay + "'";			
		}    	
		if (denngay.length() > 0)
		{
			query = query + " and a.ngaynhap <= '" + denngay + "'";	
		}
		if (sodonhang.length() > 0)
		{
			query = query + " and a.pk_seq like '%" + util.replaceAEIOU(sodonhang)  + "%'";	
		}
		if (khachhang.length() > 0)
		{

			query = query + " and (d.smartid like N'%"+ khachhang +"%' or dbo.ftBoDau(d.pk_seq) like (N'%" + util.replaceAEIOU(khachhang) + "%') or upper(dbo.ftBoDau(d.ten)) like upper(N'%" + util.replaceAEIOU(khachhang) + "%') )";	
		}
		if(tumuc.length() > 0)
		{
			query = query + " and a.tonggiatri >= '" + util.replaceAEIOU(tumuc) + "'";	
		}
		if(denmuc.length() > 0)
		{
			query = query + " and a.tonggiatri <= '" + util.replaceAEIOU(denmuc) + "'";	
		}

		System.out.print("\nQuery cua ban: " + query);
		return query;
	}

	private boolean Delete(String id)
	{		
		// XÓa đơn hàng IP, chưa có trả hàng lại kho khi xóa
		dbutils db = new dbutils(); 

		String query = "update donhangIP set trangthai = 2 where pk_seq = '"+id+"' and trangthai <> 1 ";
		db.update(query);
		db.shutDown();
		return true;
	}
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}
	
	
	
	
	private void createDonHangPdf(Document document, String id, dbutils db, String userId) 
	{
		try 
		{
			IDonhangIP dhBean;
			dhBean = new DonhangIP(id);
			dhBean.setUserId(userId); //phai co UserId truoc khi Init
			dhBean.init();
			dhBean.setKhId(dhBean.getKhId());

			//dhBean.CreateSpDisplay();

			List<ISanphamIP> spList = (List<ISanphamIP>)dhBean.getSpList();
			List<ISanphamIP> spkmlist = (List<ISanphamIP>)dhBean.getScheme_SpList();
			Hashtable<String, Float> scheme_tongtien = dhBean.getScheme_Tongtien(); 
			Hashtable<String, Float> scheme_chietkhau = dhBean.getScheme_Chietkhau();

			System.out.println("----So san pham: " + spList.size());

			String tennpp = "";
			String diachi = "";
			String dienthoai = "";
			String masothue = "";

			String sql_getinfodis = "select ten,isnull(diachi,'') as diachi, isnull( masothue, '') as masothue," +
					"isnull( dienthoai,'') as dienthoai from nhaphanphoi where pk_seq='" + dhBean.getNppId() + "'";

			ResultSet rs=db.get(sql_getinfodis);
			try
			{
				if(rs.next()){
					tennpp=rs.getString("ten");
					diachi=rs.getString("diachi");
					dienthoai=rs.getString("dienthoai");
					masothue=rs.getString("masothue");
				}
				rs.close();
			}
			catch(Exception er){}

			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			NumberFormat formatter1 = new DecimalFormat("#,###,###"); 

			//chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 15, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			Font fontnomar=new Font(bf,10,Font.NORMAL);
			//font2.setColor(BaseColor.GREEN);
			//KHAI BAO 1 BANG CO BAO NHIEU COT

			PdfPTable tableheader=new PdfPTable(2);
			tableheader.setWidthPercentage(100);//chieu dai cua báº£ng 
			tableheader.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withsheader = {35.0f, 65.0f};//SET DO RONG CAC COT
			tableheader.setWidths(withsheader); 

			Image hinhanh=Image.getInstance( getServletContext().getInitParameter("path")+"/images/logosgp.png");	
			hinhanh.setAlignment(Element.ALIGN_CENTER);
			PdfPCell cellslogo = new PdfPCell(hinhanh);


			cellslogo.setPaddingTop(1);
			cellslogo.setFixedHeight(78.4f);
			cellslogo.setHorizontalAlignment(Element.ALIGN_CENTER);

			tableheader.addCell(cellslogo);

			Paragraph pxk = new Paragraph("PHIẾU GIAO HÀNG", font);
			pxk.setSpacingAfter(5);
			pxk.setAlignment(Element.ALIGN_CENTER);
			PdfPCell celltieude = new PdfPCell();
			celltieude.addElement(pxk);

			Paragraph dvbh = new Paragraph("Nhà phân phối: " + tennpp, fontnomar);
			dvbh.setSpacingAfter(2);
			dvbh.setAlignment(Element.ALIGN_LEFT);
			celltieude.addElement(dvbh);

			dvbh = new Paragraph("Điện thoại: " + dienthoai, fontnomar);
			dvbh.setSpacingAfter(2);
			dvbh.setAlignment(Element.ALIGN_LEFT);
			celltieude.addElement(dvbh);

			dvbh = new Paragraph("Địa chỉ: " + diachi, fontnomar);
			dvbh.setSpacingAfter(4);
			dvbh.setAlignment(Element.ALIGN_LEFT);
			celltieude.addElement(dvbh);
			String ngay = dhBean.getNgaygiaodich().substring(8,10)+ "-" +dhBean.getNgaygiaodich().substring(5,7)+"-"+dhBean.getNgaygiaodich().substring(0,4);
			celltieude.addElement(new Paragraph("Số chứng từ    : " + dhBean.getId()+" - Ngày chứng từ: "+ngay,fontnomar));
			tableheader.addCell(celltieude);

			PdfPCell cellinfo = new PdfPCell();

			cellinfo.addElement(new Paragraph("Số chứng từ    : " + dhBean.getId(),fontnomar));

			/*String ngay = dhBean.getNgaygiaodich().substring(8,10)+ "-" +dhBean.getNgaygiaodich().substring(5,7)+"-"+dhBean.getNgaygiaodich().substring(0,4);
			cellinfo.addElement(new Paragraph("Ngày chứng từ: " + ngay,fontnomar));*/
			tableheader.addCell(cellinfo);

			//Add bang vao document
			document.add(tableheader);						

			PdfPTable table_info=new PdfPTable(1);
			float[] with3 = {50.0f};//SET DO RONG CAC COT
			table_info.setWidthPercentage(100);
			table_info.setWidths(with3);
			PdfPCell cell111= new PdfPCell();

			String tenkh="";
			String diachikh="";
			String dienthoaikh="";
			String masothuekh="";
			String quanhuyen="";
			String tinhthanh="";
			String phuongxa="";
			String sql_getinfokh =	"select khachhang.ten,isnull(khachhang.dienthoai,'') as dienthoai,isnull(khachhang.diachi,'') as diachi,isnull(q.TEN,'')as quan,isnull(t.TEN,'')as tinhthanh,isnull(khachhang.phuong,'')as phuongxa,isnull(khachhang.masothue,'') as masothue " +
					"from khachhang left join QUANHUYEN q ON q.pk_seq=khachhang.quanhuyen_fk left join TINHTHANH t on t.pk_seq=khachhang.tinhthanh_fk where khachhang.pk_seq=" +dhBean.getKhId();
			System.out.println(sql_getinfokh);
			ResultSet rs_kh=db.get(sql_getinfokh);
			try{
				if(rs_kh.next()){
					tenkh= rs_kh.getString("ten");
					diachikh= rs_kh.getString("diachi");
					dienthoaikh= rs_kh.getString("dienthoai");
					masothuekh= rs_kh.getString("masothue");
					quanhuyen=rs_kh.getString("quan");
					tinhthanh=rs_kh.getString("tinhthanh");
					phuongxa=rs_kh.getString("phuongxa");
				}
				rs_kh.close();
			}catch(Exception er )
			{
				er.printStackTrace();
				System.out.println("Loi Khach Hang: " + er.toString());
			}
			//lay dai dien kinh doanh
			String ddkdten="";
			String dienthoaiDDKD = "";
			String sql_getddkd	=	"select pk_seq, ten, isnull(dienthoai, '') as dienthoai " +
					"from daidienkinhdoanh where pk_seq="+dhBean.getDdkdId();
			ResultSet rs_getddkd = db.get(sql_getddkd);
			System.out.println(sql_getddkd);
			try
			{
				if(rs_getddkd.next())
				{
					ddkdten = rs_getddkd.getString("ten");
					dienthoaiDDKD = rs_getddkd.getString("dienthoai");
				}
				rs_getddkd.close();
			}
			catch(Exception er ){
				System.out.println("Loi DDkD : " + er.toString());
			}


			String ddkd = "Đại diện kinh doanh: " + ddkdten;
			if(dienthoaiDDKD.trim().equals("NA"))
				dienthoaiDDKD="";
			if(dienthoaiDDKD.trim().equals("N/A"))
				dienthoaiDDKD="";
			if(dienthoaiDDKD.length() > 0)
				ddkd += " (ĐT:" + dienthoaiDDKD + ")";

			cell111.addElement(new Paragraph(ddkd, fontnomar));

			String nocu = "";
			if(dhBean.getNoCu() == 0)
				nocu = "";
			else
				nocu = formatter1.format(dhBean.getNoCu());
			String dd_khachhang = "Mã khách hàng: " + dhBean.getSmartId() + "     Tên khách hàng : " + tenkh ;
			if(dienthoaikh.length() > 0)
				dd_khachhang += " (" + dienthoaikh + ")";

			cell111.addElement(new Paragraph(dd_khachhang ,fontnomar));
			if(phuongxa.length()!=0)
				phuongxa=", phường "+phuongxa;
			if(quanhuyen.length()!=0)
				quanhuyen=", quận " +quanhuyen;
			if(tinhthanh.length()!=0)
				tinhthanh=", Tỉnh/Tp "+tinhthanh;
			cell111.addElement(new Paragraph("Địa chỉ: " + diachikh + phuongxa +quanhuyen  +tinhthanh ,fontnomar));
			cell111.setPaddingBottom(10);
			table_info.addCell(cell111);


			document.add(table_info);
			//Table Content
			PdfPTable table = new PdfPTable(10);//Chu y nhe 6 cot o day, thi xuong duoi nho set withs la 6 cot
			table.setWidthPercentage(100);//chieu dai cua báº£ng 
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withs = {5.0f,14.0f,30.0f,10.0f, 8.0f, 8.0f, 10.0f,5.0f,0.0f,12.0f}; 			
			table.setWidths(withs);
			String[] th = new String[]{"STT","Mã hàng", "Tên hàng","Kho","Số lượng","ĐVT","Đơn giá","%CK","","Thành tiền"};
			PdfPCell[] cell = new PdfPCell[10];
			for(int i=0; i < 10 ; i++)
			{
				cell[i] = new PdfPCell(new Paragraph(th[i], font2));
				cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[i].setBackgroundColor(BaseColor.LIGHT_GRAY);		
				table.addCell(cell[i]);			
			}
			float size= 8.5f;
			Font font4 = new Font(bf,size );
			PdfPCell cells_detail = new PdfPCell();
			double totalle=0;

			String query=
					"	select d.ten as khoTen,c.donvi as spDonVi, b.ma as spMa,b.ten as spTen,a.SoLuong,a.GiaMua,a.DonGiaGoc, isnull(ptCKDH,0) as ckDH  "+
							"	,isnull(ptCKTT,0) as ckTT,isnull(ptCKDLN,0) as ckDLN,isnull(ptCKTT,0)+isnull(ptCKDH,0)+isnull(ptCKDLN,0) as TongCK, (select isnull(chietkhau,0) from donhang where pk_seq = a.donhang_fk )as chietkhau ,(select isnull(Sotiengiam,0) from donhang where pk_seq = a.donhang_fk )as sotiengiam  "+
							" from DONHANG_SANPHAMSPIP a inner join SanPham b on b.pk_Seq=a.sanpham_fk  "+
							"   inner join DonViDoLuong c on c.pk_Seq=b.DVDL_FK  "+
							"  INNER JOIN KhoTT d on d.pk_Seq=a.Kho_FK  " +
							" where a.donhang_fk='"+dhBean.getId()+"'  ";

			rs = db.get(query);
			int i=0;
			double totalTienCK=0;
			double sotiengiam=0;
			double ckdh = 0;
			try
			{
				while(rs.next())
				{
					i++;
					String spMa= rs.getString("spMa");
					String spTen= rs.getString("spTen");
					String spDonVi= rs.getString("spDonVi");
					String khoTen= rs.getString("khoTen");
					double SoLuong =rs.getDouble("SoLuong");
					double GiaSauCK =rs.getDouble("GiaMua");
					double DonGiaGoc =rs.getDouble("DonGiaGoc");
					double totalCK =rs.getDouble("TongCK");
					double thanhTien = SoLuong*( DonGiaGoc) ;
					double tienSauCK = SoLuong*( DonGiaGoc *(1-totalCK/100)) ;
					double tienCK = thanhTien -tienSauCK;
					totalTienCK+=tienCK;
					ckdh =rs.getDouble("chietkhau");
					sotiengiam =rs.getDouble("sotiengiam");

					totalle+= thanhTien;
					String[] arr = new String[]{Integer.toString(i), spMa, spTen, khoTen,formatter.format(SoLuong ), spDonVi ,formatter1.format(DonGiaGoc), formatter1.format( totalCK )  ,"", formatter1.format( thanhTien ) };
					for(int j=0; j < 10; j++)
					{
						cells_detail = new PdfPCell(new Paragraph(arr[j], font4));
						if(j==2)
						{
							cells_detail.setHorizontalAlignment(Element.ALIGN_LEFT);
						}else
						{
							cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
						}
						cells_detail.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cells_detail);
					}
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}

			if(spkmlist.size()>0)
			{
				PdfPCell cellkhuyenmai = new PdfPCell();
				cellkhuyenmai.setHorizontalAlignment(Element.ALIGN_CENTER);
				Paragraph km1=new Paragraph("Hàng khuyến mại", font2);
				km1.setAlignment(Element.ALIGN_LEFT);
				cellkhuyenmai.addElement(km1);
				cellkhuyenmai.setColspan(10);
				table.addCell(cellkhuyenmai);
			}
			//hang khuyen mai
			int demtt=0;
			for(i = 0; i < spkmlist.size(); i++)
			{
				ISanphamIP sanpham = (ISanphamIP)spkmlist.get(i);
				double dongia=Double.parseDouble(sanpham.getDongia());

				double chietkhau=Double.parseDouble(sanpham.getSoluong())* Double.parseDouble(sanpham.getDongia()) /100 * Double.parseDouble(sanpham.getChietkhau());
				String sql_getkho="select kho_fk,kho.ten  from ctkhuyenmai inner join kho on  kho.pk_Seq=kho_fk  where scheme ='"+ sanpham.getCTKM()+"'";
				rs = db.get(sql_getkho);
				String TenKho="Khuyến mại";
				try
				{
					if(rs.next())
					{
						TenKho=rs.getString("ten");
					}
					rs.close();
				}catch(Exception er)
				{
					System.out.println("Loi tai day :"+er.toString());
				}
				demtt=demtt+1;
				String[] arr = new String[]{""+demtt,sanpham.getMasanpham(), sanpham.getTensanpham(),TenKho ,sanpham.getSoluong(),sanpham.getDonvitinh(),formatter1.format(dongia),formatter1.format(Double.parseDouble(sanpham.getChietkhau())),formatter.format(chietkhau),formatter.format(Double.parseDouble(sanpham.getSoluong())* Double.parseDouble(sanpham.getDongia())-chietkhau )};

				for(int j=0; j < 10; j++)
				{
					cells_detail = new PdfPCell(new Paragraph(arr[j], font4));
					if(j==2){
						cells_detail.setHorizontalAlignment(Element.ALIGN_LEFT);
					}else{
						cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
					}

					cells_detail.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cells_detail);	
				}
			}	
			//Khai  bao 1 bien luu tien khuyen mai
			float tongtienkhuyenmai=0;
			// Xuat ra khuyen mai tra tien
			if(scheme_tongtien.size() > 0)
			{
				Enumeration<String> keys = scheme_tongtien.keys();
				while(keys.hasMoreElements())
				{
					String key = keys.nextElement();
					tongtienkhuyenmai=tongtienkhuyenmai+scheme_tongtien.get(key);
					String tongtien= Float.toString(scheme_tongtien.get(key));
					String sql_tenschem="select diengiai from ctkhuyenmai where scheme='"+key+"'";
					rs	=	db.get(sql_tenschem);
					String Tenschem="";
					try{
						if(rs.next()){					
							Tenschem=rs.getString("diengiai");						
						}
						rs.close();
					}catch(Exception er){
						System.out.println("Loi tai day :"+er.toString());
					}
					demtt=demtt+1;
					String[] arr = new String[]{""+demtt,key,Tenschem, "", "" ,"" ,"","","","-"+tongtien };
					for(int j=0; j < 10; j++)
					{
						cells_detail = new PdfPCell(new Paragraph(arr[j], font4));
						if(j==2){
							cells_detail.setHorizontalAlignment(Element.ALIGN_LEFT);
						}else{
							cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
						}

						cells_detail.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cells_detail);

					}
				}

			}

			if(scheme_chietkhau.size() > 0)
			{

				Enumeration<String> keys = scheme_chietkhau.keys();
				while(keys.hasMoreElements())
				{
					String key = keys.nextElement();
					String sql_tenschem="select diengiai from ctkhuyenmai where scheme='"+key+"'";
					rs	=	db.get(sql_tenschem);
					String Tenschem="";
					try{
						if(rs.next()){						
							Tenschem=rs.getString("diengiai");					
						}
						rs.close();
					}catch(Exception er){
						System.out.println("Loi tai day :"+er.toString());
					}

					tongtienkhuyenmai=tongtienkhuyenmai+scheme_chietkhau.get(key);
					String tienchietkhau= Float.toString(scheme_chietkhau.get(key));
					demtt=demtt+1;
					String[] arr = new String[]{""+demtt,key,Tenschem, "", "" ,"" ,"","","-"+tienchietkhau,"" };
					for(int j=0; j < 10; j++)
					{
						cells_detail = new PdfPCell(new Paragraph(arr[j], font4));
						if(j==2){
							cells_detail.setHorizontalAlignment(Element.ALIGN_LEFT);
						}else{
							cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
						}
						cells_detail.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cells_detail);

					}
				}

			}

			PdfPCell cell11 = new PdfPCell();
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			Paragraph km=new Paragraph("Cộng tiền hàng ", font2);
			km.setAlignment(Element.ALIGN_RIGHT);
			cell11.addElement(km);
			cell11.setColspan(9);
			table.addCell(cell11);

			PdfPCell cell12 = new PdfPCell();
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			Paragraph tongtien =new Paragraph( formatter1.format(totalle), font2);
			tongtien.setAlignment(Element.ALIGN_RIGHT);
			cell12.addElement(tongtien);
			table.addCell(cell12);

			// Cộng thêm chiết khấu tổng đơn hàng
			totalTienCK += (totalle*(ckdh/100.0));
			if(totalTienCK>0)
			{

				cell11 = new PdfPCell();
				cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
				km=new Paragraph("Tiền chiết khấu ", font2);
				km.setAlignment(Element.ALIGN_RIGHT);
				cell11.addElement(km);
				cell11.setColspan(9);
				table.addCell(cell11);

				cell12 = new PdfPCell();
				cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
				tongtien =new Paragraph( formatter1.format(totalTienCK), font2);
				tongtien.setAlignment(Element.ALIGN_RIGHT);
				cell12.addElement(tongtien);
				table.addCell(cell12);


			}

			if(sotiengiam>0)
			{

				cell11 = new PdfPCell();
				cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
				km=new Paragraph("Số tiền giảm ", font2);
				km.setAlignment(Element.ALIGN_RIGHT);
				cell11.addElement(km);
				cell11.setColspan(9);
				table.addCell(cell11);

				cell12 = new PdfPCell();
				cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
				tongtien =new Paragraph( formatter1.format(sotiengiam), font2);
				tongtien.setAlignment(Element.ALIGN_RIGHT);
				cell12.addElement(tongtien);
				table.addCell(cell12);


			}


			if(tongtienkhuyenmai>0)
			{
				cell11 = new PdfPCell();
				cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
				km=new Paragraph("Tổng số tiền chiết khấu khuyến mại ", font2);
				km.setAlignment(Element.ALIGN_RIGHT);
				cell11.addElement(km);
				cell11.setColspan(9);
				table.addCell(cell11);

				cell12 = new PdfPCell();
				cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
				tongtien =new Paragraph(formatter1.format(tongtienkhuyenmai) , font2);
				tongtien.setAlignment(Element.ALIGN_RIGHT);
				cell12.addElement(tongtien);
				table.addCell(cell12);

			}

			//
			cell11 = new PdfPCell();
			cell11.setHorizontalAlignment(Element.ALIGN_RIGHT);
			km=new Paragraph("Tổng tiền thanh toán(đã làm tròn) ", font2);
			km.setAlignment(Element.ALIGN_RIGHT);
			cell11.addElement(km);
			cell11.setColspan(9);
			table.addCell(cell11);

			//
			//double tongtiensauthue = totalle / 100 * 10 + totalle;
			double tongtiensauthue = totalle;
			cell12 = new PdfPCell();
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			tongtien =new Paragraph(formatter.format(tongtiensauthue - tongtienkhuyenmai-totalTienCK) , font2);
			tongtien.setAlignment(Element.ALIGN_RIGHT);
			cell12.addElement(tongtien);
			table.addCell(cell12);

			//doc tien bang chu
			doctienrachu doctien=new doctienrachu();
			cell11 = new PdfPCell();
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			Long tongtienst= Math.round(tongtiensauthue - tongtienkhuyenmai-totalTienCK);
			System.out.println("[tongtiensauthue]"+tongtiensauthue+"[tongtienkhuyenmai]"+tongtienkhuyenmai);
			km=new Paragraph("Số tiền bằng chữ : " + doctien.tranlate(tongtienst+""),  font2);
			km.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(km);
			cell11.setColspan(10);
			table.addCell(cell11);

			document.add(table);


			PdfPTable tablefooter = new PdfPTable(5);
			tablefooter.setWidthPercentage(100);//chieu dai cua báº£ng 
			tablefooter.setHorizontalAlignment(Element.ALIGN_CENTER);
			float[] withfooterder = {30.0f, 30.0f, 30.0f, 30.0f, 30.0f};//SET DO RONG CAC COT
			tablefooter.setWidths(withfooterder); 

			//nguoimua hang 
			Paragraph para = new Paragraph("Khách hàng", fontnomar);

			para.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(para);
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell11.setBorderWidthRight(0);
			tablefooter.addCell(para);

			//nvgn
			para = new Paragraph("Giao nhận", fontnomar);
			para.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(para);
			cell11.setBorderWidthLeft(0);
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell11.setBorderWidthRight(0);
			tablefooter.addCell(para);

			//o giua
			para = new Paragraph("Thủ kho", fontnomar);
			para.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(para);
			cell11.setBorderWidthLeft(0);
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell11.setBorderWidthRight(0);
			tablefooter.addCell(para);

			//Káº¿ toÃ¡n
			para = new Paragraph("Kế toán", fontnomar);
			para.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(para);
			cell11.setBorderWidthLeft(0);
			cell11.setBorderWidthRight(0);
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablefooter.addCell(para);

			//nguoi nhan hang
			para = new Paragraph("Người lập phiếu", fontnomar);
			para.add("\n");
			para.add("\n");
			para.add("\n");
			para.add("\n");
			para.add("\n");
			para.add("\n");
			para.add("\n");

			para.setAlignment(Element.ALIGN_CENTER);
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell11.addElement(para);
			cell11.setBorderWidthLeft(0);
			cell11.setBorder(0);

			tablefooter.addCell(para);

			document.add(tablefooter);

			document.newPage();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("115.Exception: " + e.getMessage());
		}

	}


	


}
