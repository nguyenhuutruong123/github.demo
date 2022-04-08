package geso.dms.distributor.servlets.xuatkho;

import geso.dms.center.beans.doctien.DocTien;
import geso.dms.distributor.beans.xuatkho.IXuatKho;
import geso.dms.distributor.beans.xuatkho.IXuatKhoList;
import geso.dms.distributor.beans.xuatkho.ISanPham;
import geso.dms.distributor.beans.xuatkho.imp.XuatKho;
import geso.dms.distributor.beans.xuatkho.imp.SanPham;
import geso.dms.distributor.beans.xuatkho.imp.XuatKhoList;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@WebServlet("/XuatKhoUpdateSvl")
public class XuatKhoUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;

	public XuatKhoUpdateSvl()
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
			response.sendRedirect("/TheGioiNuocHoa/index.jsp");
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
			IXuatKho dhBean = new XuatKho(id);
			dhBean.setUserId(userId); // phai co UserId truoc khi Init
			dhBean.init();

			String nextJSP;

			if (request.getQueryString().indexOf("display") >= 0)
			{
				nextJSP = "/TheGioiNuocHoa/pages/Distributor/XuatKhoDisplay.jsp";
			} else
			{
				nextJSP = "/TheGioiNuocHoa/pages/Distributor/XuatKhoUpdate.jsp";
			}

			session.setAttribute("xkBean", dhBean);
			session.setAttribute("nppId", dhBean.getNppId());
			session.setAttribute("nhappid", dhBean.getNppId());
			session.setAttribute("kenhid", dhBean.getKbhId());
			session.setAttribute("dvkdid", dhBean.getDvkdId());
			session.setAttribute("khoId", dhBean.getKhoNppId());
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
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TheGioiNuocHoa/index.jsp");
		} else
		{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			session.setMaxInactiveInterval(1200);
			IXuatKho dnhBean = (IXuatKho) new XuatKho();
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

			String ngaynhap = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaynhap")));
			dnhBean.setNgaynhap(ngaynhap);

			String nccId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nccId")));
			dnhBean.setNccId(nccId);

			String dvkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));
			dnhBean.setDvkdId(dvkdId);

			String kbhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhId")));
			dnhBean.setKbhId(kbhId);

			String khoNppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khoNppId")));
			dnhBean.setKhoNppId(khoNppId);

			String khoTtId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khoTtId")));
			dnhBean.setKhoTtId(khoTtId);
			
			String tienBVAT = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tienBVAT")));
			dnhBean.setTienBVAT(tienBVAT);
			
			String tienAVAT = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tienAVAT")));
			dnhBean.setTienAVAT(tienAVAT);
			
			String vat = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vat")));
			dnhBean.setVat(vat);

			session.setAttribute("nhappid", nppId);
			session.setAttribute("kenhid", kbhId);
			session.setAttribute("dvkdid", dvkdId);
			session.setAttribute("khoId", khoNppId);

			String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
			dnhBean.createRs();
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
					if (spMa[m] != "" && dvdlId[m] != "" && soluongChuan[m] != "")
					{
						if (Float.parseFloat(soluong[m].replace(",", "")) > 0)
						{
							sanpham = new SanPham(spId[m], spMa[m], spTen[m], dvdlId[m], soluong[m].replace(",", ""), soluongChuan[m].replace(",", ""), dongia[m].replace(",", ""));
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
			if (action.equals("save"))
			{
				if (id == null)
				{
					if (!(dnhBean.save()))
					{
						session.setAttribute("xkBean", dnhBean);
						String nextJSP = "/TheGioiNuocHoa/pages/Distributor/XuatKhoNew.jsp";
						response.sendRedirect(nextJSP);
					} else
					{
						IXuatKhoList obj = new XuatKhoList();
						obj.setUserId(userId);
						obj.createDdhlist("");
						session.setAttribute("obj", obj);
						String nextJSP = "/TheGioiNuocHoa/pages/Distributor/XuatKho.jsp";
						response.sendRedirect(nextJSP);

					}
				} else
				{
					if (!(dnhBean.edit()))
					{
						session.setAttribute("xkBean", dnhBean);
						String nextJSP = "/TheGioiNuocHoa/pages/Distributor/XuatKhoUpdate.jsp";
						response.sendRedirect(nextJSP);
					} else
					{
						IXuatKhoList obj = new XuatKhoList();
						obj.setUserId(userId);
						obj.createDdhlist("");
						session.setAttribute("obj", obj);
						String nextJSP = "/TheGioiNuocHoa/pages/Distributor/XuatKho.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			} 
			else if(action.equals("print"))
			{
				response.setContentType("application/pdf");				
				//hien thi hop thoai dialog save file xuong may Client
				response.setHeader("Content-Disposition"," inline; filename=\"XuatKhoVeCongTy.pdf\" ");					
				Document document = new Document();
				ServletOutputStream outstream = response.getOutputStream();
				
				System.out.println("Ngay nhap: " + dnhBean.getNgaynhap()); //*Ngay nhap				
				System.out.println("Cua hang/KH VP: " + dnhBean.getNppTen()); //*Cua hang/ KH VP
				
				String nccTen = "";				
				ResultSet rs = (ResultSet) dnhBean.getNccRs();
				if(rs!=null)
				{
					try {
						while(rs.next())
						{
							if (rs.getString("nccId").equals(dnhBean.getNccId()))
							{
								nccTen = rs.getString("nccTen");
								break;
							}
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
				}
				System.out.println("Nha cung cap: " + nccTen);//*Nha cung cap
				
				String dvkdTen = "";
				rs = (ResultSet) dnhBean.getDvkdRs();
				if(rs!=null)
				{
					try {
						while(rs.next())
						{
							if (rs.getString("dvkdId").equals(dnhBean.getDvkdId()))
							{
								dvkdTen = rs.getString("dvkd");
								break;
							}
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
				}
				System.out.println("Don vi kinh doanh: " + dvkdTen);//*Don vi kinh doanh
				
				String kbhTen = "";
				rs = (ResultSet) dnhBean.getKbhRs();
				if(rs!=null)
				{
					try {
						while(rs.next())
						{
							if (dnhBean.getKbhId().equals(rs.getString("kbhId")))
							{
								kbhTen = rs.getString("kbh");
								break;
							}
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
				}
				System.out.println("Kenh ban hang: " + kbhTen);//*Kenh ban hang
				
				String khochuyen = "";
				Hashtable<String, String> khoNppList = dnhBean.getKhoNppList();
				if (khoNppList != null)
				{
					Enumeration<String> keys = khoNppList.keys();
					while (keys.hasMoreElements())
					{
						String key = keys.nextElement();
						if (key.toString().equals(dnhBean.getKhoNppId() ))
						{
							khochuyen = khoNppList.get(key);
							break;
						}
					}
				}
				System.out.println("Kho chuyen: " + khochuyen);//*Kho chuyen
				
				String khonhan = "";
				Hashtable<String, String> khoTTList = dnhBean.getKhoTtList();
				if (khoTTList != null)
				{
					Enumeration<String> keys = khoTTList.keys();
					while (keys.hasMoreElements())
					{
						String key = keys.nextElement();
						if (key.toString().equals(dnhBean.getKhoTtId()))
						{
							khonhan = khoTTList.get(key);
						}
					}
				}
				System.out.println("Kho nhan: " + khonhan);//*Kho nhan				
				System.out.println("Ghi chu: " + dnhBean.getGhichu());			
				
				System.out.println("Tong so tien (chua VAT): " + dnhBean.getTienBVAT());			
				System.out.println("VAT (10%): " + dnhBean.getVat());				
				System.out.println("Tong so tien (co VAT): " + dnhBean.getTienAVAT());		
				
				String bangchu = "";
				String temp1 = dnhBean.getTienAVAT().replace(",", "");
				long tien = Long.parseLong(temp1);			
				bangchu = DocTien.docTien(tien) + " Việt Nam";				
				bangchu = bangchu.replace("triệu", "triệu,");
				bangchu = bangchu.replace("tỉ", "tỉ,");				
				bangchu = toBeCapped(bangchu);
				System.out.println("Bang chu: " + bangchu);
				
				
				this.CreateSanPhamXuatKhoVeCTY(document, outstream, dnhBean,
						nccTen, dvkdTen, kbhTen, khochuyen, khonhan, bangchu);
				
				if(rs!=null)
				{
					try {
						rs.close();
					} catch (SQLException e) {
					}
				}
				return;
			}
			else
			{
				if (id == null)
				{
					session.setAttribute("xkBean", dnhBean);
					String nextJSP = "/TheGioiNuocHoa/pages/Distributor/XuatKhoNew.jsp";
					response.sendRedirect(nextJSP);
				} else
				{
					session.setAttribute("xkBean", dnhBean);
					String nextJSP = "/TheGioiNuocHoa/pages/Distributor/XuatKhoUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
			}
		}
	}

	
	public String toBeCapped(String s)
	{	//In tat ca ki tu dau tien thanh chu in hoa.
		String toBeCapped = s;
		String[] tokens = toBeCapped.split("\\s");
		toBeCapped = "";

		for(int i = 0; i < tokens.length; i++){
			char capLetter = Character.toUpperCase(tokens[i].charAt(0));
			toBeCapped +=  " " + capLetter + tokens[i].substring(1, tokens[i].length());
		}
		return toBeCapped;
	}
	
	private void CreateSanPhamXuatKhoVeCTY(Document document, ServletOutputStream outstream, 
			IXuatKho dh, String nccTen, String dvkdTen, String khbTen, String khochuyen, String khonhan, String bangchu) throws IOException
	{
		try {
			PdfWriter.getInstance(document, outstream);
			document.open();// OPEN &...
			
			// chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font fonttb = new Font(bf, 12, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			Font font3 = new Font(bf, 8, Font.UNDERLINE);
			Font fontnomar = new Font(bf, 10, Font.NORMAL);
			Font fontnomar2 = new Font(bf, 10, Font.UNDERLINE);
			Font font8normal = new Font(bf, 8, Font.NORMAL);
			
			//----header
			PdfPTable tableheader = new PdfPTable(2);
			tableheader.setWidthPercentage(100);// chieu dai cua bang
			tableheader.setSpacingAfter(14);
			tableheader.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withheder =	{ 6.0f, 3.0f };
			tableheader.setWidths(withheder);
			
			//Column 1
			// logo
			String logo = getServletContext().getInitParameter("path") + "\\images\\LogoTGNH.gif";
			Image hinhanh = Image.getInstance(logo);
			hinhanh.scaleAbsolute(100, 40);
			PdfPCell cell1 = new PdfPCell();
			cell1.addElement(hinhanh);			
			// address
			cell1.addElement(new Paragraph("", fontnomar));
			cell1.addElement(new Paragraph("Lầu 8, 161 Võ Văn Tần, Phường 6, Quận 3, Tp.HCM", fontnomar));
			cell1.addElement(new Paragraph("Tel : (08) 6290 5560 - Fax(08) 6290 5104", fontnomar));
			cell1.addElement(new Paragraph("Tax Code : 0310776071", fontnomar));
			cell1.setBorder(0);
			tableheader.addCell(cell1);
			
			//Column 2
			PdfPCell cell2 = new PdfPCell();
			cell2.addElement(new Paragraph("Ngày nhập: " + dh.getNgaynhap(), font8normal));
			cell2.setBorder(0);
			tableheader.addCell(cell2);
			
			document.add(tableheader);
			//----end header
			
			//----title
			Paragraph tieude = new Paragraph("XUẤT KHO VỀ CÔNG TY", font);
			tieude.setAlignment(Element.ALIGN_CENTER);
			document.add(tieude);			
			//----end title
			
			
			//----header2
			PdfPTable tableheader2 = new PdfPTable(2);
			tableheader.setWidthPercentage(100);
			tableheader.setSpacingAfter(14);
			tableheader.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableheader2.setSpacingBefore(16);
			float[] withheder2 =	{ 13.0f, 0.0f };
			tableheader2.setWidths(withheder2);			
			//----header2 Column 1
			cell1 = new PdfPCell();
			cell1.addElement(new Paragraph("THÔNG TIN NHÀ PHÂN PHỐI ", fontnomar2));
			cell1.addElement(new Paragraph("Cửa hàng/ KH VP: "+ dh.getNppTen(), fontnomar));
			cell1.addElement(new Paragraph("Nhà cung cấp: "+ nccTen, fontnomar));
			cell1.addElement(new Paragraph("Đơn vị kinh doanh: "+ dvkdTen, fontnomar));
			cell1.addElement(new Paragraph("Kênh bán hàng: "+ khbTen, fontnomar));
			cell1.addElement(new Paragraph("Kho chuyển: "+ khochuyen, fontnomar));
			cell1.addElement(new Paragraph("Kho nhận: "+ khonhan, fontnomar));
			cell1.addElement(new Paragraph("           ", fontnomar));
			cell1.addElement(new Paragraph("THÔNG TIN ĐƠN HÀNG ", fontnomar2));
			cell1.addElement(new Paragraph("Tổng số tiền (chưa VAT): "+ dh.getTienBVAT(), fontnomar));
			cell1.addElement(new Paragraph("VAT (10%): " + dh.getVat(), fontnomar));
			cell1.addElement(new Paragraph("Tổng số tiền (có VAT): "+ dh.getTienAVAT(), fontnomar));
			cell1.addElement(new Paragraph("Viết bằng chữ: " + bangchu, fontnomar));
			cell1.addElement(new Paragraph("Ghi chú: " + dh.getGhichu(), fontnomar));
			cell1.addElement(new Paragraph("           ", fontnomar));
			cell1.addElement(new Paragraph("           ", fontnomar));
			cell1.setBorder(0);
			tableheader2.addCell(cell1);
			
			//----header2 Column 2
			cell2 = new PdfPCell();
			cell2.setBorder(0);
			tableheader2.addCell(cell2);
			document.add(tableheader2);
			
			//----end header2
			
			//----danh sach san pham
			PdfPTable tableSP = new PdfPTable(7);
			tableSP.setWidthPercentage(100);// chieu rong cua bang
			tableSP.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withs =
				{ 10.0f, 20.0f, 5.0f, 5.0f, 8.0f, 10.0f, 10.0f };
			tableSP.setWidths(withs);
			String[] th = new String[]
				{ "Mã sản phẩm","Tên sản phẩm","Tồn kho","Số lượng", "ĐVT","Đơn giá (lẻ)", "Thành tiền" };
			PdfPCell[] cell = new PdfPCell[7];
			for (int i = 0; i < 7; i++)
			{
				cell[i] = new PdfPCell(new Paragraph(th[i], font2));
				cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				tableSP.addCell(cell[i]);
			}
			
			NumberFormat formatter = new DecimalFormat("#,###,###");
			double totalSoLuong=0;
			double totalThanhTien=0;
			int m = 0;
			List<ISanPham> spList = dh.getSpList();
			if(spList!=null)
			{
				ISanPham sanpham = null;
				int size = spList.size();
				System.out.println("Mã sản phẩm - Tên sản phẩm - Tồn kho - Số lượng - ĐVT - Đơn giá (lẻ) - Thành tiền");
				while (m < size){
					sanpham = spList.get(m); 
					totalSoLuong+= Double.parseDouble(sanpham.getSoluong());
					totalThanhTien+= Double.parseDouble(sanpham.getThanhtien());
					
					String spMa = sanpham.getMa();
					String spTen = sanpham.getTen();
					String tonkho = sanpham.getTonkho();
					String soluongchuan = sanpham.getSoluongchuan();
					String dvdl = "";
					Hashtable<String, String> dvdlList = dh.getDvdlList();
					if (dvdlList != null)
					{
						Enumeration<String> keys = dvdlList.keys();
						while (keys.hasMoreElements())
						{
							String key = keys.nextElement();
							if (key.toString().equals(sanpham.getDvdlId()))
							{
								dvdl = dvdlList.get(key);
							}
						}
					}
					String dongia = formatter.format(Double.parseDouble (sanpham.getDongia().replace(",", "")) );
					String thanhtien = formatter.format(Double.parseDouble( sanpham.getThanhtien().replace(",", "")));					
					
					System.out.println(spMa + " - " + spTen + " - " 
							+ tonkho + " - " + soluongchuan + " - "
							+ dvdl + " - " + dongia + " - " + thanhtien + "");
					
					//In 
					th = new String[] {spMa,spTen,
							tonkho,soluongchuan,
							dvdl,dongia,thanhtien};
					for(int j=0; j<7; j++)
					{
						cell[j] = new PdfPCell(new Paragraph(th[j], font8normal));
						cell[j].setHorizontalAlignment(Element.ALIGN_CENTER);
						cell[j].setVerticalAlignment(Element.ALIGN_MIDDLE);
						if(j==1)
						{
							cell[j].setHorizontalAlignment(Element.ALIGN_LEFT);
							cell[j].setVerticalAlignment(Element.ALIGN_MIDDLE);
						}
						tableSP.addCell(cell[j]);
					}
					
					m++;
				}
				
				
			}
			System.out.println("Tong luong: " + formatter.format(totalSoLuong));
			System.out.println("Tong tien: " + formatter.format(totalThanhTien));
			
			cell1 = new PdfPCell(new Paragraph("Tổng lượng",font2));
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setColspan(3);
			tableSP.addCell(cell1);
			
			cell1 = new PdfPCell(new Paragraph(formatter.format(totalSoLuong), font8normal));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tableSP.addCell(cell1);
			
			cell1 = new PdfPCell(new Paragraph("Tổng tiền", font2));
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setColspan(2);
			tableSP.addCell(cell1);
			
			cell1 = new PdfPCell(new Paragraph(formatter.format(totalThanhTien), font8normal));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tableSP.addCell(cell1);
			
			
			/*					
			
			cell1 = new PdfPCell();
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.addElement(new Paragraph("Tổng lượng", font2));
			cell1.setColspan(2);
			tableSP.addCell(cell1);
			//		Ghi cach tren van hop le, nhung khong xet duoc setHorizontalAlignment va setVerticalAlignment
			//		Ly do, xem: http://sourceforge.net/p/itext/mailman/message/17394715/
			
					You are mixing 'text mode' and 'composite mode'.
					In 'text mode', the cell follows the settings of the cell
					For instance: cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
					causes the content to be right aligned.
					In 'composite mode', the cell follows the settings of
					the content. For instance: the alignment of the cell will
					be ignored if you use addElement().
			
			cell1 = new PdfPCell(new Paragraph("Tổng lượng",font2));
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setColspan(2);
			tableSP.addCell(cell1);
			
			cell1 = new PdfPCell(new Paragraph(tongLuong_s, font8normal));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tableSP.addCell(cell1);*/
			
			document.add(tableSP);
			//----het danh sach san pham
			
			document.close();// ...$ CLOSE
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
