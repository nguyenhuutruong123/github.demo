package geso.dms.distributor.servlets.doihang;

import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.doihang.IErpKhachhangdoihang;
import geso.dms.distributor.beans.doihang.IErpKhachhangdoihangList;
import geso.dms.distributor.beans.doihang.imp.ErpKhachhangdoihang;
import geso.dms.distributor.beans.doihang.imp.ErpKhachhangdoihangList;
import geso.dms.distributor.beans.donhangnhapp.IDonhangnpp;
import geso.dms.distributor.beans.donhangnhapp.ISanPhamDhNpp;
import geso.dms.distributor.beans.donhangnhapp.imp.SanPhamDhNpp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ErpKhachhangdoihangUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	PrintWriter out;
	public ErpKhachhangdoihangUpdateSvl() 
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
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}
		else
		{
			session.setMaxInactiveInterval(30000);

			Utility util = new Utility();

			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);

			if (userId.length()==0)
				userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"))); 

			String id = util.getId(querystring);  	
			IErpKhachhangdoihang lsxBean = new ErpKhachhangdoihang(id);
			lsxBean.setUserId(userId); 

			String nextJSP = "";
			String printpdf=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("print")));
			if(printpdf==null)
				printpdf="";
			if(!printpdf.equals("")){

				Document document = new Document();
				ServletOutputStream outstream = response.getOutputStream();
				lsxBean.initDuyet();
				this.CreateDondathangHPC(document, outstream, lsxBean);
				document.close();
				return;
			}	

			if(querystring.contains("display"))
			{
				lsxBean.initDuyet();
				nextJSP = "/AHF/pages/Distributor/ErpKhachHangDoiHangDisplay.jsp";
			}
			else if(querystring.contains("chot"))
			{
				lsxBean.initDuyet();
				nextJSP = "/AHF/pages/Distributor/ErpKhachHangDoiHangDuyet.jsp";
			}
			else
			{
				System.out.println("*Dang update");
				//lsxBean.init();
				lsxBean.initCapNhat();
				nextJSP = "/AHF/pages/Distributor/ErpKhachHangDoiHangUpdate.jsp";
			}

			session.setAttribute("lsxBean", lsxBean);
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
		}
		else
		{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			session.setMaxInactiveInterval(30000);

			this.out = response.getWriter();
			IErpKhachhangdoihang lsxBean;

			Utility util = new Utility();	
			String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
			if(id == null)
			{  	
				lsxBean = new ErpKhachhangdoihang("");
			}
			else
			{
				lsxBean = new ErpKhachhangdoihang(id);
			}

			lsxBean.setUserId(userId);

			String ngayyeucau = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaychuyen")));
			if(ngayyeucau == null || ngayyeucau.trim().length() <= 0)
				ngayyeucau = getDateTime();
			lsxBean.setNgayyeucau(ngayyeucau);

			String ngaydenghi = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaydenghi")));
			if(ngaydenghi == null || ngaydenghi.trim().length() <= 0)
				ngaydenghi = "";
			lsxBean.setNgaydenghi(ngaydenghi);

			String ghichu = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ghichu")));
			if(ghichu == null)
				ghichu = "";
			lsxBean.setGhichu(ghichu);

			String khonhapId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khonhapId")));
			if (khonhapId == null)
				khonhapId = "";				
			lsxBean.setKhoNhapId(khonhapId);

			String khodoiId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khodoiId")));
			if (khodoiId == null)
				khodoiId = "";				
			lsxBean.setKhoDoiId(khodoiId);

			String dvkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));
			if (dvkdId == null)
				dvkdId = "";				
			lsxBean.setDvkdId(dvkdId);

			String kbhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhId")));
			if (kbhId == null)
				kbhId = "";				
			lsxBean.setKbhId(kbhId);

			String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
			if (nppId == null)
				nppId = "";	
			lsxBean.setNppId(nppId);

			String khId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khId")));
			if (khId == null)
				khId = "";				
			lsxBean.setKhId(khId);

			String chietkhau = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ptChietkhau")));
			if (chietkhau == null)
				chietkhau = "";				
			lsxBean.setChietkhau(chietkhau);

			String vat = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ptVat")));
			if (vat == null)
				vat = "";				
			lsxBean.setVat(vat);

			String loaidonhang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaidonhang")));
			if (loaidonhang == null)
				loaidonhang = "";				
			lsxBean.setLoaidonhang(loaidonhang); 

			String lydoId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("lydoId"));
			if(lydoId == null)
				lydoId = "";
			lsxBean.setLydoId(lydoId);

			String[] schemeIds = request.getParameterValues("schemeIds");
			if (schemeIds != null)
			{
				String _scheme = "";
				for(int i = 0; i < schemeIds.length; i++)
				{
					_scheme += schemeIds[i] + ",";
				}

				if(_scheme.trim().length() > 0)
				{
					_scheme = _scheme.substring(0, _scheme.length() - 1);
					lsxBean.setSchemeId(_scheme);
				}
			}

			String[] spId = request.getParameterValues("spID");
			String[] soluong = request.getParameterValues("spSOLUONG");
			String[] solo = request.getParameterValues("spSOLO");
			String[] ngaysanxuat = request.getParameterValues("spNGAYSANXUAT");		


			if(spId != null)
			{
				Hashtable<String, String> sp_soluong = new Hashtable<String, String>();
				Hashtable<String, String> sp_solo = new Hashtable<String, String>();
				Hashtable<String, String> sp_nsx = new Hashtable<String, String>();

				for(int i = 0; i < spId.length; i++ )
				{
					if(soluong[i].trim().length() > 0)
					{
						sp_soluong.put(spId[i], soluong[i].replaceAll(",", "") );
					}
				}
				lsxBean.setSanpham_soluong(sp_soluong);
				lsxBean.setSanpham_solo(sp_solo);
				lsxBean.setSanpham_NSX(sp_nsx);

			}

			String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
			if(action.equals("save"))
			{	
				if(id == null)
				{
					boolean kq = lsxBean.createNK(request); //tao 1 don doi hang
					if(!kq)
					{
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
						String nextJSP = "/AHF/pages/Distributor/ErpKhachHangDoiHangNew.jsp";
						response.sendRedirect(nextJSP);
					}
					else
					{
						IErpKhachhangdoihangList obj = new ErpKhachhangdoihangList();
						obj.setLoaidonhang(loaidonhang);

						obj.setUserId(userId);
						obj.init("");  
						session.setAttribute("obj", obj);  	
						session.setAttribute("userId", userId);

						String nextJSP = "/AHF/pages/Distributor/ErpKhachHangDoiHang.jsp";
						response.sendRedirect(nextJSP);
					}
				}
				else
				{
					boolean kq = lsxBean.updateNK(request);

					if(!kq)
					{
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
						String nextJSP = "/AHF/pages/Distributor/ErpKhachHangDoiHangUpdate.jsp";
						response.sendRedirect(nextJSP);
					}
					else
					{
						IErpKhachhangdoihangList obj = new ErpKhachhangdoihangList();
						obj.setLoaidonhang(loaidonhang);

						obj.setUserId(userId);
						obj.init("");
						session.setAttribute("obj", obj);							
						String nextJSP = "/AHF/pages/Distributor/ErpKhachHangDoiHang.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else if(action.equals("duyet"))
			{
				System.out.println("Tu ErpKhachHangDoiHangDuyet.jsp -> ErpKhachangdoihangUpdateSvl -> action:duyet");
				boolean kq = lsxBean.duyetNK(request);

				if(!kq)
				{
					lsxBean.initDuyet();
					session.setAttribute("lsxBean", lsxBean);
					String nextJSP = "/AHF/pages/Distributor/ErpKhachHangDoiHangDuyet.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IErpKhachhangdoihangList obj = new ErpKhachhangdoihangList();
					obj.setLoaidonhang(loaidonhang);

					obj.setUserId(userId);
					obj.init("");
					session.setAttribute("obj", obj);							
					String nextJSP = "/AHF/pages/Distributor/ErpKhachHangDoiHang.jsp";
					response.sendRedirect(nextJSP);
				}
			}
			else
			{
				lsxBean.createRs();

				session.setAttribute("lsxBean", lsxBean);

				String nextJSP = "";

				nextJSP = "/AHF/pages/Distributor/ErpKhachHangDoiHangNew.jsp";
				if(id != null)
				{
					nextJSP = "/AHF/pages/Distributor/ErpKhachHangDoiHangUpdate.jsp";
				}

				response.sendRedirect(nextJSP);

			}
		}
	}

	private void CreateDondathangHPC(Document document, ServletOutputStream outstream, IErpKhachhangdoihang lsxBean) throws IOException
	{
		try{			
			NumberFormat formatter = new DecimalFormat("#,###,###.###");
			PdfWriter.getInstance(document, outstream);
			document.open();
			//chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			BaseFont bf = BaseFont.createFont("C:\\WINDOWS\\Fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 12, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.NORMAL);
			Font font3 = new Font(bf, 8, Font.NORMAL);

			Paragraph ddh = new Paragraph("Đơn Đổi Hàng", font);
			ddh.setSpacingAfter(15);
			ddh.setAlignment(Element.ALIGN_CENTER);
			document.add(ddh);
			Paragraph ddh1 = new Paragraph("( theo chính sách đổi trả mà công ty ban hành )", font2);
			ddh1.setSpacingAfter(15);
			ddh1.setAlignment(Element.ALIGN_CENTER);

			document.add(ddh);

			PdfPTable tableHead = new PdfPTable(2);
			tableHead.setWidthPercentage(90);
			tableHead.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableHead.setSpacingAfter(2);
			float[] with = {24.0f, 90.0f}; //set chieu rong cac columns
			tableHead.setWidths(with);

			PdfPCell cell1 = new PdfPCell(new Paragraph("Tên NPP:", font3));
			PdfPCell cell2 = new PdfPCell(new Paragraph(lsxBean.getNppTen(), font2));
			cell1.setBorder(0);
			cell2.setBorder(0);
			tableHead.addCell(cell1);
			tableHead.addCell(cell2);

			PdfPCell cell5 = new PdfPCell(new Paragraph("Tên khách hàng:", font3));
			PdfPCell cell6 = new PdfPCell(new Paragraph(lsxBean.gettenkh(), font2));
			cell5.setBorder(0);
			cell6.setBorder(0);
			tableHead.addCell(cell5);
			tableHead.addCell(cell6);

			PdfPCell cell9 = new PdfPCell(new Paragraph("Tên NVBH:", font3));
			PdfPCell cell10 = new PdfPCell(new Paragraph("", font2));
			cell9.setBorder(0);
			cell10.setBorder(0);
			tableHead.addCell(cell9);
			tableHead.addCell(cell10);
			PdfPCell cell11a = new PdfPCell(new Paragraph("Ngày Đổi Hàng :", font3));
			PdfPCell cell12a = new PdfPCell(new Paragraph(lsxBean.getNgaydenghi(), font2));
			cell11a.setBorder(0);
			cell12a.setBorder(0);

			tableHead.addCell(cell11a);
			tableHead.addCell(cell12a);

			PdfPCell cell13a = new PdfPCell(new Paragraph("Lý do :", font3));
			PdfPCell cell14a = new PdfPCell(new Paragraph(lsxBean.getLydoId().equals("1")?"Đổi Hàng":"Trả Hàng", font2));
			cell13a.setBorder(0);
			cell14a.setBorder(0);

			tableHead.addCell(cell11a);
			tableHead.addCell(cell12a);


			tableHead.setSpacingAfter(20.0f);
			document.add(tableHead);


			PdfPTable table = new PdfPTable(7);
			table.setWidthPercentage(100);
			float[] withs = {16.0f, 42.0f, 11.0f,  12.0f, 14.0f, 12.0f,22.0f};
			table.setWidths(withs);


			String[] th = new String[]{"Mã SP", "Tên SP", "Số lượng trả", "Số lượng Đổi", "Đơn vị", "Đơn giá","T.Tiền"};
			PdfPCell[] cell = new PdfPCell[7];
			for(int i=0; i < 7 ; i++)
			{
				cell[i] = new PdfPCell(new Paragraph(th[i], font2));
				cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[i].setPadding(5);
				/*if(i <= 3 )
					cell[i].setRowspan(2);		
				if( i == 5 )
				{
					cell[i].setColspan(5);
				}*/
				cell[i].setBackgroundColor(BaseColor.WHITE);

				table.addCell(cell[i]);

			}
			ResultSet spRs = lsxBean.getSanphamRs();
			Hashtable<String, String> sp_soluong = lsxBean.getSanpham_soluong();
			//int size = splist.size();
			int m = 0;
			double thuevat=0;
			try {
				while (spRs.next()){
					/*sanpham = splist.get(m);
					System.out.println(sanpham.getMaSanPham());*/
					cell[0] = new PdfPCell(new Paragraph(spRs.getString("MA"), font2));		
					cell[0].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell[0].setPadding(4);
					table.addCell(cell[0]);	

					cell[1] = new PdfPCell(new Paragraph(spRs.getString("TEN"), font2));		
					cell[1].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell[1].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell[1].setPadding(4);
					table.addCell(cell[1]);	

					cell[2] = new PdfPCell(new Paragraph(spRs.getDouble("SOLUONG")+"", font2));		
					cell[2].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell[2].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell[2].setPadding(4);
					table.addCell(cell[2]);	

					cell[3] = new PdfPCell(new Paragraph(sp_soluong.get(spRs.getString("PK_SEQ")), font2));		
					cell[3].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell[3].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell[3].setPadding(4);
					table.addCell(cell[3]);	

					cell[4] = new PdfPCell(new Paragraph(spRs.getString("DONVI"), font2));		
					cell[4].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell[4].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell[4].setPadding(4);
					table.addCell(cell[4]);	



					cell[5] = new PdfPCell(new Paragraph(/*formatter.format(sanpham.getGiaMua())*/"", font2));		
					cell[5].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell[5].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell[5].setPadding(4);
					table.addCell(cell[5]);

					cell[6] = new PdfPCell(new Paragraph(/*formatter.format(sanpham.getGiaMua()* sanpham.getSoLuongNhan())*/"", font2));		
					cell[6].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell[6].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell[6].setPadding(4);
					table.addCell(cell[6]);

					m++;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			document.add(table);

			PdfPTable tablefoot = new PdfPTable(2);
			tablefoot.setWidthPercentage(90);
			tablefoot.setHorizontalAlignment(Element.ALIGN_LEFT);
			tablefoot.setSpacingBefore(10);

			float[] withf = {350.0f,60.0f}; //set chieu rong cac columns
			tablefoot.setWidths(withf);



			PdfPCell cellf1 = new PdfPCell(new Paragraph("Tổng Tiền:", font3));
			PdfPCell cellf2 = new PdfPCell(new Paragraph("", font2));
			cellf1.setBorder(0);
			cellf2.setBorder(0);
			cellf1.setPaddingLeft(330);

			tablefoot.addCell(cellf1);
			tablefoot.addCell(cellf2);
			//	double tienchuathue=(lsxBean.getTongGiaTri());
			//double tiencothue=tienchuathue+tienchuathue/100*Double.parseDouble(lsxBean.getVAT());

			PdfPCell cellf3 = new PdfPCell(new Paragraph("Thuế VAT (%):", font3));
			PdfPCell cellf4 = new PdfPCell(new Paragraph("", font2));
			cellf3.setBorder(0);
			cellf4.setBorder(0);
			cellf3.setPaddingLeft(330);


			tablefoot.addCell(cellf3);
			tablefoot.addCell(cellf4);

			PdfPCell cellf5 = new PdfPCell(new Paragraph("Tổng Giá Trị:", font3));
			PdfPCell cellf6 = new PdfPCell(new Paragraph("", font2));
			cellf5.setBorder(0);
			cellf6.setBorder(0);
			cellf5.setPaddingLeft(330);


			tablefoot.addCell(cellf5);
			tablefoot.addCell(cellf6);

			document.add(tablefoot);
			document.close();


		}
		catch(DocumentException e)
		{
			e.printStackTrace();
		}
		lsxBean.DBclose();
	}
	private String getDateTime() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}

}
