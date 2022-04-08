package geso.dms.center.servlets.bandott;

import geso.dms.center.util.Utility;
import geso.dms.center.beans.bandott.IBandott;
import geso.dms.center.beans.bandott.imp.Bandott;
import geso.dms.center.db.sql.dbutils;
import geso.dms.distributor.beans.khachhang.IKhachhangList;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class BandoTTSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
       
    public BandoTTSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    
	    HttpSession session = request.getSession();	
	    
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

	    IBandott obj = new Bandott();
	    obj.setUserId(userId);
	    
	    String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
		if(view == null) view = ""; else view = view.trim();
		obj.setView(view);
		
		String isTT = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("isTT"));
		if(isTT == null) isTT = ""; else isTT = isTT.trim();
		obj.setIsTT(isTT);
		
		System.out.println("View: "+view+"--isTT: "+isTT);
		if(view.equals("khachhangTB"))
		{
			System.out.println("khach hang trung bay");
			obj.initTB();
			session.setAttribute("obj", obj);
			
			String nextJSP = "/AHF/pages/Center/KhachHangTrungBay.jsp";
			response.sendRedirect(nextJSP);
		}
		else if(view.equals("khachhangToado")) {
			System.out.println("---------------------[Bandott.initToado] ------------------------------------ sql = " );
			obj.initToaDo();
			session.setAttribute("obj", obj);
		
			String nextJSP = "/AHF/pages/Center/KhachHangToaDo_1.jsp";
			response.sendRedirect(nextJSP);
		}
		else if(view.equals("lotrinhOnline"))
		{
			obj.initLoTrinhOnline();
			session.setAttribute("obj", obj);
			System.out.println("Ban do TT");
			String nextJSP = "/AHF/pages/Center/BanDoLoTrinhOnline.jsp";
			response.sendRedirect(nextJSP);
		}
		else if(view.equals("lotrinhOnlineNPP"))
		{
			obj.initLoTrinhOnlineNPP();
			session.setAttribute("obj", obj);
			System.out.println("Ban do TT");
			String nextJSP = "/AHF/pages/Distributor/BanDoLoTrinhOnline.jsp";
			response.sendRedirect(nextJSP);
		}
		else
		{
			obj.init();
			session.setAttribute("obj", obj);
			System.out.println("Ban do TT");
			String nextJSP = "/AHF/pages/Center/BanDoTT.jsp";
			response.sendRedirect(nextJSP);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();	

		Utility util = new Utility();

		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));     
		IBandott obj = new Bandott();
		obj.setUserId(userId);

		String mafast = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("maFAST")));
		if (mafast== null)
			mafast = "";
		obj.setMafast(mafast);

		String isTT = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("isTT")));
		if (isTT== null)
			isTT = "";
		obj.setIsTT(isTT);

		String vungId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vung")));
		if (vungId == null)
			vungId = "";
		obj.setVungId(vungId);

		String dvkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));
		if (dvkdId == null)
			dvkdId = "";
		obj.setDvkdId(dvkdId);
		System.out.println("DVKDID: "+dvkdId+"--isTT: "+isTT);

		String ma_tenkh = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ma_tenkh")));
		if (ma_tenkh == null)
			ma_tenkh = "";
		obj.setMa_tenkh(ma_tenkh);
		
		String kvId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvuc")));
		if (kvId == null)
			kvId = "";
		obj.setKvId(kvId);

		String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("npp")));
		if (nppId == null)
			nppId = "";
		obj.setNppId(nppId);

		String cttbId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("cttbIds")));
		if (cttbId == null)
			cttbId = "";
		obj.setCttbId(cttbId);

		String ngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngay")));
		if (ngay == null)
			ngay = getDateTime();
		obj.setDate(ngay);

		String ddkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkd")));
		if (ddkdId == null)
			ddkdId = "";
		obj.setDdkdId(ddkdId);

		String nvName = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nvName")));
		if (nvName == null)
			nvName = "";
		obj.setNvId(nvName);

		String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
		if(view == null) view = ""; else view = view.trim();
		obj.setView(view);
		String tbhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tbh")));
		if (tbhId == null)
			tbhId = "";
		obj.setTbhId(tbhId);

		String address = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("address")));
		if (address == null)
			address = "";
		obj.setAddress(address);

		String trungtam = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trungtam")));
		if (trungtam == null)
			trungtam = "";
		obj.setTrungtam(trungtam);

		String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
		if (denngay == null)
			denngay = "";
		obj.setDenngay(denngay);

		String khId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khId")));
		if (khId == null) khId = ""; else khId = khId.trim();

		String khachhangsi = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khachhangsi")));
		if (khachhangsi == null)
			khachhangsi = "";
		obj.setKhachHangSi(khachhangsi);


		String ttId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ttId")==null?"":request.getParameter("ttId")));
		obj.setTtId(ttId);

		String action = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")==null?"":request.getParameter("action")));
		obj.setAction(action);
		String [] chon=request.getParameterValues("chon");


		System.out.println("_________"+action);

		System.out.println("-----VIEW: " + view);
		if(view.equals("khachhangTB"))
		{
			System.out.println("1.Action: " + action);

			if(action.equals("pdf"))
			{
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition"," inline; filename=KhachHangTrungBay.pdf");

				Document document = new Document();
				ServletOutputStream outstream = response.getOutputStream();

				obj.initTB();
				this.ExportTrungBay(document, outstream, obj);
			}

			else
			{
				if(action.equals("excel"))
				{
					response.setContentType("application/pdf");
					response.setHeader("Content-Disposition"," inline; filename=KhachHangTrungBay.pdf");

					Document document = new Document();
					ServletOutputStream outstream = response.getOutputStream();

					obj.initTB();
					this.ExportTrungBay_To_Excel(document, outstream, obj);
					out.close();
				}
				else
				{
					obj.initTB();
					session.setAttribute("obj", obj);

					String nextJSP = "/AHF/pages/Center/KhachHangTrungBay.jsp";
					response.sendRedirect(nextJSP);
				}
			}
		}
		else if(view.equals("khachhangToado")) {

			if(action.equals("xoaanh"))
			{
				if(khId.trim().length() > 0) {
					if(obj.xoaanhkh(khId)) {
						obj.setMsg("Xóa ảnh khách hàng thành công!");
					} else {
						obj.setMsg("Không thể xóa ảnh khách hàng này!");
					}
				}

				obj.initToaDo();
				session.setAttribute("obj", obj);

				String nextJSP = "/AHF/pages/Center/KhachHangToaDo_1.jsp";
				response.sendRedirect(nextJSP);
				return;
			}
			else if (action.equals("xoaanhnhieu"))
			{
				if(chon!=null)
				{
					for(int i=0;i<chon.length;i++)
					{
						if(obj.xoaanhkh(chon[i])) {
							obj.setMsg("Xóa ảnh khách hàng thành công!");
						} else {
							obj.setMsg("Không thể xóa tọa độ khách hàng này!");
						}
					}
				}

				obj.initToaDo();
				session.setAttribute("obj", obj);

				String nextJSP = "/AHF/pages/Center/KhachHangToaDo_1.jsp";
				response.sendRedirect(nextJSP);
			}
			else if (action.equals("xtdnhieukh"))
			{
				if(chon!=null)
				{
					for(int i=0;i<chon.length;i++)
					{
						if(obj.xoaToaDoKh(chon[i])) {
							obj.setMsg("xóa tọa độ khách hàng thành công!");
						} else {
							obj.setMsg("Không thể xóa tọa độ khách hàng này!");
						}
					}
				}

				obj.initToaDo();
				session.setAttribute("obj", obj);

				String nextJSP = "/AHF/pages/Center/KhachHangToaDo_1.jsp";
				response.sendRedirect(nextJSP);

			}
			else{
				if(khId != null && khId.trim().length() > 0) {
					if(obj.xoaToaDoKh(khId)) {
						obj.setMsg("Xóa tọa độ khách hàng thành công!");
					} else {
						obj.setMsg("Không thể xóa tọa độ khách hàng này!");
					}
				}
				String search = getSearchQuery(request, obj);
				obj.initToaDo();
				session.setAttribute("obj", obj);

				String nextJSP = "/AHF/pages/Center/KhachHangToaDo_1.jsp";
				response.sendRedirect(nextJSP);
				return;
			}



		}		
		else
			if(view.equals("lotrinhOnline"))
			{
				obj.initLoTrinhOnline();
				session.setAttribute("obj", obj);
				System.out.println("Ban do TT");
				String nextJSP = "/AHF/pages/Center/BanDoLoTrinhOnline.jsp";
				response.sendRedirect(nextJSP);
			}
			else if(view.equals("lotrinhOnlineNPP"))
			{
				obj.initLoTrinhOnlineNPP();
				session.setAttribute("obj", obj);
				System.out.println("Ban do TT");
				String nextJSP = "/AHF/pages/Distributor/BanDoLoTrinhOnline.jsp";
				response.sendRedirect(nextJSP);
			}
			else
			{
				obj.init();
				session.setAttribute("obj", obj);

				String nextJSP = "/AHF/pages/Center/BanDoTT.jsp";
				response.sendRedirect(nextJSP);
			}

	}
	private String getSearchQuery(HttpServletRequest request, IBandott obj)
	{
		Utility util = new Utility();
		
		String ma_tenkh = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ma_tenkh")));
		if ( ma_tenkh == null)
			ma_tenkh = "";
    	obj.setMa_tenkh(ma_tenkh);
		String query = "select pk_seq, ten  from khachhang where trangthai = 1 ";
		
		if(ma_tenkh != null && ma_tenkh.length() > 0){
			query = query + "\n and h.pk_seq ='" + ma_tenkh + "'";		
		}
		
		return query;
	}
	private void ExportTrungBay(Document document, ServletOutputStream outstream, IBandott obj)
	{
		try 
		{
			PdfWriter.getInstance(document, outstream);
			document.open();
			
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
		
			Paragraph cttb = new Paragraph("KHÁCH HÀNG TRƯNG BÀY", font);
			cttb.setSpacingAfter(10);
			cttb.setAlignment(Element.ALIGN_CENTER);
			document.add(cttb);
			
			if(obj.getCttbId().trim().length() > 0)
			{
				dbutils db = new dbutils();
				ResultSet rsScheme = db.get("select scheme + ' - ' + diengiai as cttbTen from cttrungbay where pk_seq = '" + obj.getCttbId() + "' ");
				
				String schemeName = "";
				if(rsScheme != null)
				{
					if(rsScheme.next())
					{
						schemeName = rsScheme.getString("cttbTen");
					}
					rsScheme.close();
				}
				
				Paragraph scheme = new Paragraph("Chương trình trưng bày: " + schemeName, new Font(bf, 8, Font.ITALIC));
				scheme.setSpacingAfter(10);
				scheme.setAlignment(Element.ALIGN_LEFT);
				document.add(scheme);
				
				String thang = getMonth();
			    String nam = getYear();
				
				String query = "select  g.pk_seq as vungId, g.ten as vungTen, f.pk_seq as kvId, f.ten as kvTen, e.pk_seq as nppId, e.ten as nppTen, d.ten as khTen, c.scheme,   " +
									"a.dangky, isnull(a.ghichu, '') as ghichu, " +
									"isnull( ( select ImageFilePath from DKTRUNGBAY_KHACHHANG_CHITIET where loai = '0' and KHACHHANG_FK = d.PK_SEQ and DKTRUNGBAY_FK = a.DKTRUNGBAY_FK and thang = '"+thang+"' and nam = '"+nam+"' ) , '') as hinhBenTrai, " +
				    				"isnull( ( select ImageFilePath from DKTRUNGBAY_KHACHHANG_CHITIET where loai = '2' and KHACHHANG_FK = d.PK_SEQ and DKTRUNGBAY_FK = a.DKTRUNGBAY_FK and thang = '"+thang+"' and nam = '"+nam+"' ) , '') as hinhBenPhai, " +
				    				"isnull( ( select ImageFilePath from DKTRUNGBAY_KHACHHANG_CHITIET where loai = '1' and KHACHHANG_FK = d.PK_SEQ and DKTRUNGBAY_FK = a.DKTRUNGBAY_FK and thang = '"+thang+"' and nam = '"+nam+"' ) , '') as hinhGiua,   " +
									"-1 as type   " +
								"from DKTrungBay_KhachHang a inner join DangKyTrungBay b on a.dktrungbay_fk = b.pk_seq  " +
									"inner join CtTrungBay c on b.cttrungbay_fk = c.pk_seq  " +
									"inner join KhachHang d on a.khachhang_fk = d.pk_seq   " +
									"inner join NhaPhanPhoi e on d.npp_fk = e.pk_seq   " +
									"inner join KhuVuc f on e.khuvuc_fk = f.pk_seq   " +
									"inner join Vung g on f.vung_fk = g.pk_seq   " +
								"where c.pk_seq = '" + obj.getCttbId() + "' and b.trangthai != 2 " +
						"union all  " +
								"select c.pk_seq as vungId, c.ten as vungTen, c.pk_seq as kvId, b.ten as kvTen, a.pk_seq as nppId, a.ten as nppten, '' as khTen,    " +
									"'' as scheme, '-1' as dangky, '' as ghichu, '' as hinhBenTrai, '' as hinhBenPhai, '' as hinhGiua, -2 as type  " +
								"from NhaPhanPhoi a inner join KhuVuc b on a.khuvuc_fk = b.pk_seq  " +
									"inner join Vung c on b.vung_fk = c.pk_seq   " +
								"where a.pk_seq in ( select npp_fk from dangkytrungbay where cttrungbay_fk = '" + obj.getCttbId() + "' and trangthai != 2 )  " +
						"union all  " +
								"select c.pk_seq as vungId, c.ten as vungTen, b.pk_seq as kvId, b.ten as kvTen, a.pk_seq as nppId, a.ten as nppten, '' as khTen,   " +
									"'' as scheme, '-1' as dangky, '' as ghichu, '' as hinhBenTrai, '' as hinhBenPhai, '' as hinhGiua, -3 as type   " +
								"from NhaPhanPhoi a inner join KhuVuc b on a.khuvuc_fk = b.pk_seq   " +
									"inner join Vung c on b.vung_fk = c.pk_seq   " +
								"where a.pk_seq in ( select npp_fk from dangkytrungbay where cttrungbay_fk = '" + obj.getCttbId() + "' and trangthai != 2 )  " +
						"union all  " +
								"select c.pk_seq as vungId, c.ten as vungTen, b.pk_seq as kvId, b.ten as kvTen, a.pk_seq as nppId, a.ten as nppten, '' as khTen,    " +
									"'' as scheme, '-1' as dangky, '' as ghichu, '' as hinhBenTrai, '' as hinhBenPhai, '' as hinhGiua, -4 as type    " +
								"from NhaPhanPhoi a inner join KhuVuc b on a.khuvuc_fk = b.pk_seq  " +
									"inner join Vung c on b.vung_fk = c.pk_seq   " +
								"where a.pk_seq in ( select npp_fk from dangkytrungbay where cttrungbay_fk = '" + obj.getCttbId() + "' and trangthai != 2 )  " +
						"order by vungTen asc, kvTen asc, nppTen asc, type asc";
				
				ResultSet rs = db.get(query);
				if(rs != null)
				{
					PdfPTable table = new PdfPTable(9);
					table.setWidthPercentage(100);
					table.setHorizontalAlignment(Element.ALIGN_LEFT);
					//float[] widths = {10.0f, 10.0f, 15.0f, 15.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f };
					float[] widths = {10.0f, 10.0f, 15.0f, 15.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f };
			        table.setWidths(widths);
			        
					String[] th = new String[]{"Vùng", "Khu vực", "Nhà phân phối", "Khách hàng", "Đăng ký", "Ghi chú", "Hình Trái", "Chính Diện", "Hình Phải"};
					PdfPCell[] c = new PdfPCell[9];
					for(int i = 0; i < 9 ; i++)
					{
						c[i] = new PdfPCell(new Paragraph(th[i], new Font(bf, 8, Font.BOLD)));
						c[i].setHorizontalAlignment(Element.ALIGN_CENTER);
						c[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
						c[i].setPadding(5);
						c[i].setBackgroundColor(BaseColor.LIGHT_GRAY);
						table.addCell(c[i]);			
					}
					
					
					while(rs.next())
					{
						String vungTen = "";
						String kvTen = "";
						String nppTen = "";
						String khTen = "";
						String dangky = "";
						String ghichu = "";
						String filehinhtrai = "";
						String filehinhgiua = "";
						String filehinhphai = "";
						
						if(rs.getString("type").equals("-4"))
						{
							vungTen = rs.getString("vungTen");
						}
						else
						{
							if(rs.getString("type").equals("-3"))
							{
								kvTen = rs.getString("kvTen");
							}
							else
							{
								if(rs.getString("type").equals("-2"))
								{
									nppTen = rs.getString("nppTen");
								}
								else
								{
									khTen = rs.getString("khTen");
									dangky = rs.getString("dangky");
									ghichu = rs.getString("ghichu");
									filehinhtrai = rs.getString("hinhBenTrai");
									filehinhgiua = rs.getString("hinhBenPhai");
									filehinhphai = rs.getString("hinhGiua");
									
								}
							}		
						}
						
						if(vungTen.trim().length() > 0)
						{
							PdfPCell cell = new PdfPCell(new Paragraph(vungTen, new Font(bf, 8, Font.NORMAL)));
							cell.setColspan(9);
							table.addCell(cell);
						}
						else
						{
							if(kvTen.trim().length() > 0)
							{
								PdfPCell cell = new PdfPCell(new Paragraph(vungTen, new Font(bf, 8, Font.NORMAL)));
								table.addCell(cell);
								
								cell = new PdfPCell(new Paragraph(kvTen, new Font(bf, 8, Font.NORMAL)));
								cell.setColspan(8);
								table.addCell(cell);
							}
							else
							{
								if(nppTen.trim().length() > 0)
								{
									PdfPCell cell = new PdfPCell(new Paragraph("", new Font(bf, 8, Font.NORMAL)));
									cell.setColspan(2);
									table.addCell(cell);
									
									cell = new PdfPCell(new Paragraph(nppTen, new Font(bf, 8, Font.NORMAL)));
									cell.setColspan(7);
									table.addCell(cell);
								}
								else
								{
									PdfPCell cell = new PdfPCell(new Paragraph(vungTen, new Font(bf, 8, Font.NORMAL)));
									cell.setColspan(3);
									table.addCell(cell);
									
									cell = new PdfPCell(new Paragraph(khTen, new Font(bf, 8, Font.NORMAL)));
									table.addCell(cell);
									
									cell = new PdfPCell(new Paragraph(dangky, new Font(bf, 8, Font.NORMAL)));
									table.addCell(cell);
									
									cell = new PdfPCell(new Paragraph(ghichu, new Font(bf, 8, Font.NORMAL)));
									table.addCell(cell);
									
									if(filehinhtrai.trim().length() <= 0)
									{
										cell = new PdfPCell(new Paragraph("", new Font(bf, 8, Font.NORMAL)));
										table.addCell(cell);
									}
									else
									{
										//String filePath = "D:\\project\\dms\\dms\\WebContent\\pages\\upload\\" + filehinh;
										
										String filePath = "C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\dms\\pages\\upload\\" + filehinhtrai;
										System.out.println("___File hinh: " + filePath);
										
										try
										{
											Image pic = Image.getInstance(filePath);
											
											if(pic != null)
											{
												pic.setAlignment(Element.ALIGN_CENTER);
												table.addCell(pic);
											}
											else
											{
												cell = new PdfPCell(new Paragraph(" ", new Font(bf, 8, Font.NORMAL)));
												table.addCell(cell);
											}
										}
										catch (Exception e) 
										{
											cell = new PdfPCell(new Paragraph(" ", new Font(bf, 8, Font.NORMAL)));
											table.addCell(cell);
										}
									}
									
									if(filehinhgiua.trim().length() <= 0)
									{
										cell = new PdfPCell(new Paragraph("", new Font(bf, 8, Font.NORMAL)));
										table.addCell(cell);
									}
									else
									{
										//String filePath = "D:\\project\\dms\\dms\\WebContent\\pages\\upload\\" + filehinh;
										
										String filePath = "C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\dms\\pages\\upload\\" + filehinhgiua;
										System.out.println("___File hinh: " + filePath);
										
										try
										{
											Image pic = Image.getInstance(filePath);
											
											if(pic != null)
											{
												pic.setAlignment(Element.ALIGN_CENTER);
												table.addCell(pic);
											}
											else
											{
												cell = new PdfPCell(new Paragraph(" ", new Font(bf, 8, Font.NORMAL)));
												table.addCell(cell);
											}
										}
										catch (Exception e) 
										{
											cell = new PdfPCell(new Paragraph(" ", new Font(bf, 8, Font.NORMAL)));
											table.addCell(cell);
										}
									}
									
									if(filehinhphai.trim().length() <= 0)
									{
										cell = new PdfPCell(new Paragraph("", new Font(bf, 8, Font.NORMAL)));
										table.addCell(cell);
									}
									else
									{
										//String filePath = "D:\\project\\dms\\dms\\WebContent\\pages\\upload\\" + filehinh;
										
										String filePath = "C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\dms\\pages\\upload\\" + filehinhphai;
										System.out.println("___File hinh: " + filePath);
										
										try
										{
											Image pic = Image.getInstance(filePath);
											
											if(pic != null)
											{
												pic.setAlignment(Element.ALIGN_CENTER);
												table.addCell(pic);
											}
											else
											{
												cell = new PdfPCell(new Paragraph(" ", new Font(bf, 8, Font.NORMAL)));
												table.addCell(cell);
											}
										}
										catch (Exception e) 
										{
											cell = new PdfPCell(new Paragraph(" ", new Font(bf, 8, Font.NORMAL)));
											table.addCell(cell);
										}
									}
								}
							}
						}
						
					}
					rs.close();
					
					document.add(table);
				}
				
			}
			
			document.close();
		} 
		catch (Exception e) 
		{
			System.out.println("__Exception: " + e.getMessage());
		}
		
	}


	private void ExportTrungBay_To_Excel(Document document, ServletOutputStream outstream, IBandott obj)
	{
		try 
		{
			PdfWriter.getInstance(document, outstream);
			document.open();
			
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
		
			Paragraph cttb = new Paragraph("KHÁCH HÀNG TRƯNG BÀY", font);
			cttb.setSpacingAfter(10);
			cttb.setAlignment(Element.ALIGN_CENTER);
			document.add(cttb);
			
			if(obj.getCttbId().trim().length() > 0)
			{
				dbutils db = new dbutils();
				ResultSet rsScheme = db.get("select scheme + ' - ' + diengiai as cttbTen from cttrungbay where pk_seq = '" + obj.getCttbId() + "' ");
				
				String schemeName = "";
				if(rsScheme != null)
				{
					if(rsScheme.next())
					{
						schemeName = rsScheme.getString("cttbTen");
					}
					rsScheme.close();
				}
				
				Paragraph scheme = new Paragraph("Chương trình trưng bày: " + schemeName, new Font(bf, 8, Font.ITALIC));
				scheme.setSpacingAfter(10);
				scheme.setAlignment(Element.ALIGN_LEFT);
				document.add(scheme);
				

				String thang = getMonth();
			    String nam = getYear();
				
				String query = "select  g.pk_seq as vungId, g.ten as vungTen, f.pk_seq as kvId, f.ten as kvTen, e.pk_seq as nppId, e.ten as nppTen, d.ten as khTen, c.scheme,   " +
									"a.dangky, isnull(a.ghichu, '') as ghichu, " +
									"isnull( ( select ImageFilePath from DKTRUNGBAY_KHACHHANG_CHITIET where loai = '0' and KHACHHANG_FK = d.PK_SEQ and DKTRUNGBAY_FK = a.DKTRUNGBAY_FK and thang = '"+thang+"' and nam = '"+nam+"' ) , '') as hinhBenTrai, " +
				    				"isnull( ( select ImageFilePath from DKTRUNGBAY_KHACHHANG_CHITIET where loai = '2' and KHACHHANG_FK = d.PK_SEQ and DKTRUNGBAY_FK = a.DKTRUNGBAY_FK and thang = '"+thang+"' and nam = '"+nam+"' ) , '') as hinhBenPhai, " +
				    				"isnull( ( select ImageFilePath from DKTRUNGBAY_KHACHHANG_CHITIET where loai = '1' and KHACHHANG_FK = d.PK_SEQ and DKTRUNGBAY_FK = a.DKTRUNGBAY_FK and thang = '"+thang+"' and nam = '"+nam+"' ) , '') as hinhGiua,   " +
									"-1 as type   " +
								"from DKTrungBay_KhachHang a inner join DangKyTrungBay b on a.dktrungbay_fk = b.pk_seq  " +
									"inner join CtTrungBay c on b.cttrungbay_fk = c.pk_seq  " +
									"inner join KhachHang d on a.khachhang_fk = d.pk_seq   " +
									"inner join NhaPhanPhoi e on d.npp_fk = e.pk_seq   " +
									"inner join KhuVuc f on e.khuvuc_fk = f.pk_seq   " +
									"inner join Vung g on f.vung_fk = g.pk_seq   " +
								"where c.pk_seq = '" + obj.getCttbId() + "' and b.trangthai != 2 " +
						"union all  " +
								"select c.pk_seq as vungId, c.ten as vungTen, c.pk_seq as kvId, b.ten as kvTen, a.pk_seq as nppId, a.ten as nppten, '' as khTen,    " +
									"'' as scheme, '-1' as dangky, '' as ghichu, '' as hinhBenTrai, '' as hinhBenPhai, '' as hinhGiua, -2 as type  " +
								"from NhaPhanPhoi a inner join KhuVuc b on a.khuvuc_fk = b.pk_seq  " +
									"inner join Vung c on b.vung_fk = c.pk_seq   " +
								"where a.pk_seq in ( select npp_fk from dangkytrungbay where cttrungbay_fk = '" + obj.getCttbId() + "' and trangthai != 2 )  " +
						"union all  " +
								"select c.pk_seq as vungId, c.ten as vungTen, b.pk_seq as kvId, b.ten as kvTen, a.pk_seq as nppId, a.ten as nppten, '' as khTen,   " +
									"'' as scheme, '-1' as dangky, '' as ghichu, '' as hinhBenTrai, '' as hinhBenPhai, '' as hinhGiua, -3 as type   " +
								"from NhaPhanPhoi a inner join KhuVuc b on a.khuvuc_fk = b.pk_seq   " +
									"inner join Vung c on b.vung_fk = c.pk_seq   " +
								"where a.pk_seq in ( select npp_fk from dangkytrungbay where cttrungbay_fk = '" + obj.getCttbId() + "' and trangthai != 2 )  " +
						"union all  " +
								"select c.pk_seq as vungId, c.ten as vungTen, b.pk_seq as kvId, b.ten as kvTen, a.pk_seq as nppId, a.ten as nppten, '' as khTen,    " +
									"'' as scheme, '-1' as dangky, '' as ghichu, '' as hinhBenTrai, '' as hinhBenPhai, '' as hinhGiua, -4 as type    " +
								"from NhaPhanPhoi a inner join KhuVuc b on a.khuvuc_fk = b.pk_seq  " +
									"inner join Vung c on b.vung_fk = c.pk_seq   " +
								"where a.pk_seq in ( select npp_fk from dangkytrungbay where cttrungbay_fk = '" + obj.getCttbId() + "' and trangthai != 2 )  " +
						"order by vungTen asc, kvTen asc, nppTen asc, type asc";
				
				ResultSet rs = db.get(query);
				if(rs != null)
				{
					PdfPTable table = new PdfPTable(9);
					table.setWidthPercentage(100);
					table.setHorizontalAlignment(Element.ALIGN_LEFT);
					float[] widths = {15.0f, 15.0f, 25.0f, 25.0f, 12.0f, 25.0f, 15.0f};
			        table.setWidths(widths);
			        
					String[] th = new String[]{"Vùng", "Khu vực", "Nhà phân phối", "Khách hàng", "Đăng ký", "Ghi chú", "Hình Trái", "Chính Diện", "Hình Phải"};
					PdfPCell[] c = new PdfPCell[9];
					for(int i = 0; i < 9 ; i++)
					{
						c[i] = new PdfPCell(new Paragraph(th[i], new Font(bf, 8, Font.BOLD)));
						c[i].setHorizontalAlignment(Element.ALIGN_CENTER);
						c[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
						c[i].setPadding(5);
						c[i].setBackgroundColor(BaseColor.LIGHT_GRAY);
						table.addCell(c[i]);			
					}
					
					
					while(rs.next())
					{
						String vungTen = "";
						String kvTen = "";
						String nppTen = "";
						String khTen = "";
						String dangky = "";
						String ghichu = "";
						String filehinhphai = "";
						String filehinhtrai = "";
						String filehinhgiua = "";
						
						if(rs.getString("type").equals("-4"))
						{
							vungTen = rs.getString("vungTen");
						}
						else
						{
							if(rs.getString("type").equals("-3"))
							{
								kvTen = rs.getString("kvTen");
							}
							else
							{
								if(rs.getString("type").equals("-2"))
								{
									nppTen = rs.getString("nppTen");
								}
								else
								{
									khTen = rs.getString("khTen");
									dangky = rs.getString("dangky");
									ghichu = rs.getString("ghichu");
									filehinhtrai = rs.getString("hinhBenTrai");
									filehinhphai = rs.getString("hinhBenPhai");
									filehinhgiua = rs.getString("hinhGiua");
								}
							}		
						}
						
						if(vungTen.trim().length() > 0)
						{
							PdfPCell cell = new PdfPCell(new Paragraph(vungTen, new Font(bf, 8, Font.NORMAL)));
							cell.setColspan(9);
							table.addCell(cell);
						}
						else
						{
							if(kvTen.trim().length() > 0)
							{
								PdfPCell cell = new PdfPCell(new Paragraph(vungTen, new Font(bf, 8, Font.NORMAL)));
								table.addCell(cell);
								
								cell = new PdfPCell(new Paragraph(kvTen, new Font(bf, 8, Font.NORMAL)));
								cell.setColspan(8);
								table.addCell(cell);
							}
							else
							{
								if(nppTen.trim().length() > 0)
								{
									PdfPCell cell = new PdfPCell(new Paragraph("", new Font(bf, 8, Font.NORMAL)));
									cell.setColspan(2);
									table.addCell(cell);
									
									cell = new PdfPCell(new Paragraph(nppTen, new Font(bf, 8, Font.NORMAL)));
									cell.setColspan(7);
									table.addCell(cell);
								}
								else
								{
									PdfPCell cell = new PdfPCell(new Paragraph(vungTen, new Font(bf, 8, Font.NORMAL)));
									cell.setColspan(3);
									table.addCell(cell);
									
									cell = new PdfPCell(new Paragraph(khTen, new Font(bf, 8, Font.NORMAL)));
									table.addCell(cell);
									
									cell = new PdfPCell(new Paragraph(dangky, new Font(bf, 8, Font.NORMAL)));
									table.addCell(cell);
									
									cell = new PdfPCell(new Paragraph(ghichu, new Font(bf, 8, Font.NORMAL)));
									table.addCell(cell);
									
									if(filehinhtrai.trim().length() <= 0)
									{
										cell = new PdfPCell(new Paragraph("", new Font(bf, 8, Font.NORMAL)));
										table.addCell(cell);
									}
									else
									{
										//String filePath = "D:\\project\\dms\\dms\\WebContent\\pages\\upload\\" + filehinh;
										
										String filePath = "C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\dms\\pages\\upload\\" + filehinhtrai;
										System.out.println("___File hinh: " + filePath);
										
										try
										{
											Image pic = Image.getInstance(filePath);
											
											if(pic != null)
											{
												pic.setAlignment(Element.ALIGN_CENTER);
												table.addCell(pic);
											}
											else
											{
												cell = new PdfPCell(new Paragraph(" ", new Font(bf, 8, Font.NORMAL)));
												table.addCell(cell);
											}
										}
										catch (Exception e) 
										{
											cell = new PdfPCell(new Paragraph(" ", new Font(bf, 8, Font.NORMAL)));
											table.addCell(cell);
										}
									}
									
									if(filehinhgiua.trim().length() <= 0)
									{
										cell = new PdfPCell(new Paragraph("", new Font(bf, 8, Font.NORMAL)));
										table.addCell(cell);
									}
									else
									{
										//String filePath = "D:\\project\\dms\\dms\\WebContent\\pages\\upload\\" + filehinh;
										
										String filePath = "C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\dms\\pages\\upload\\" + filehinhgiua;
										System.out.println("___File hinh: " + filePath);
										
										try
										{
											Image pic = Image.getInstance(filePath);
											
											if(pic != null)
											{
												pic.setAlignment(Element.ALIGN_CENTER);
												table.addCell(pic);
											}
											else
											{
												cell = new PdfPCell(new Paragraph(" ", new Font(bf, 8, Font.NORMAL)));
												table.addCell(cell);
											}
										}
										catch (Exception e) 
										{
											cell = new PdfPCell(new Paragraph(" ", new Font(bf, 8, Font.NORMAL)));
											table.addCell(cell);
										}
									}
									
									if(filehinhphai.trim().length() <= 0)
									{
										cell = new PdfPCell(new Paragraph("", new Font(bf, 8, Font.NORMAL)));
										table.addCell(cell);
									}
									else
									{
										//String filePath = "D:\\project\\dms\\dms\\WebContent\\pages\\upload\\" + filehinh;
										
										String filePath = "C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\dms\\pages\\upload\\" + filehinhphai;
										System.out.println("___File hinh: " + filePath);
										
										try
										{
											Image pic = Image.getInstance(filePath);
											
											if(pic != null)
											{
												pic.setAlignment(Element.ALIGN_CENTER);
												table.addCell(pic);
											}
											else
											{
												cell = new PdfPCell(new Paragraph(" ", new Font(bf, 8, Font.NORMAL)));
												table.addCell(cell);
											}
										}
										catch (Exception e) 
										{
											cell = new PdfPCell(new Paragraph(" ", new Font(bf, 8, Font.NORMAL)));
											table.addCell(cell);
										}
									}
								}
							}
						}
						
					}
					rs.close();
					
					document.add(table);
				}
				
			}
			
			document.close();
		} 
		catch (Exception e) 
		{
			System.out.println("__Exception: " + e.getMessage());
		}
		
	}

	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	
	private String getMonth() {
		DateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        return dateFormat.format(date);
	}
	
	private String getYear() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return dateFormat.format(date);
	}

}
