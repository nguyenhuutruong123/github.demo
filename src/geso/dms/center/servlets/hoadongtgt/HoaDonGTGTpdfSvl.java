package geso.dms.center.servlets.hoadongtgt;

import geso.dms.center.beans.doctien.doctienrachu;
import geso.dms.center.beans.hoadon.IHoaDon;
import geso.dms.center.beans.hoadon.imp.HoaDon;
import geso.dms.center.beans.hoadongtgt.IHoaDonGTGT;
import geso.dms.center.beans.hoadongtgt.IHoaDonGTGT_SP;
import geso.dms.center.beans.hoadongtgt.imp.HoaDonGTGT;
import geso.dms.center.beans.nhaphanphoi.INhaphanphoiList;
import geso.dms.center.beans.nhaphanphoi.imp.NhaphanphoiList;
import geso.dms.center.beans.phieuxuatkhott.IPhieuXuatKhoTT;
import geso.dms.center.beans.phieuxuatkhott.IPhieuXuatKhoTT_SP;
import geso.dms.center.beans.phieuxuatkhott.imp.PhieuXuatKhoTT;
import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.khachhang.imp.Khachhang;
import geso.dms.distributor.db.sql.dbutils;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.text.TabableView;

import org.apache.poi.xssf.eventusermodel.examples.FromHowTo;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfImage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class HoaDonGTGTpdfSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HoaDonGTGTpdfSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		IHoaDonGTGT pxkBean;
		dbutils db;
		
		HttpSession session = request.getSession();
		pxkBean = (IHoaDonGTGT)session.getAttribute("hoadon");
		
		
		 db = new dbutils();
		List<IHoaDonGTGT_SP> sanphamList = pxkBean.getListSanPham();
	
				
		response.setContentType("application/pdf");
		//response.setHeader("Content-Disposition"," inline; filename=Phieuxuatkho.pdf");
		
		//hien thi hop thoai dialog save file xuong may Client
		//response.setHeader("Content-Disposition"," attachment; filename=\"Phieuxuatkho.pdf\" ");
		
		Document document = new Document();
		ServletOutputStream outstream = response.getOutputStream();
		this.CreatePxk(document, outstream, sanphamList);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		IHoaDonGTGT pxkBean;
		dbutils db;
		
		Utility util = new Utility();
		
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		
		String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
		pxkBean = new HoaDonGTGT(id);
		System.out.println("ID :"+id);
		
		
		db = new dbutils();
		
		List<IHoaDonGTGT_SP> sanphamList = pxkBean.getListSanPham();		
				
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition"," inline; filename=Phieuxuatkho.pdf");
		
		//hien thi hop thoai dialog save file xuong may Client
	    //response.setHeader("Content-Disposition"," attachment; filename=\"Phieuxuatkho.pdf\" ");
		
		Document document = new Document();
		ServletOutputStream outstream = response.getOutputStream();
		this.CreatePxk(document, outstream, sanphamList);
		
	}

	private void CreatePxk(Document document, ServletOutputStream outstream, List<IHoaDonGTGT_SP> spList) throws IOException
	{
		
		IHoaDonGTGT pxkBean = new HoaDonGTGT();
		dbutils db;
		
		try{		
			String iddondathang=pxkBean.getIdDonDatHang();
			IHoaDon ddh=new HoaDon(iddondathang);
			INhaphanphoiList npp=ddh.getInfoNhaPhoiPhoi();
			
			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			PdfWriter.getInstance(document, outstream);
			document.open();
		//lay doi tuong khach hang
			
			//chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 15, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			Font font3 = new Font(bf, 8, Font.UNDERLINE);
			Font fontnomar=new Font(bf,10,Font.NORMAL);
			Font font8normal=new Font(bf,8,Font.NORMAL);
			//font2.setColor(BaseColor.GREEN);
			 
			PdfPTable tableheader=new PdfPTable(3);
			tableheader.setWidthPercentage(100);//chieu dai cua bảng 
			tableheader.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withsheader = {20.0f,35.0f,20.0f};
			tableheader.setWidths(withsheader);
			
			Paragraph pxk = new Paragraph("HÓA ĐƠN GTGT", font);
			pxk.setSpacingAfter(10);
			pxk.setAlignment(Element.ALIGN_CENTER);
		
			Paragraph HD_E = new Paragraph("VAT INVOICE", font);
			HD_E.setSpacingAfter(10);
			HD_E.setAlignment(Element.ALIGN_CENTER);
			
			
			Paragraph lien2 = new Paragraph("Liên 2: Giao khách hàng/Copy 2:Custumer", font2);
			//pxk = new Paragraph("Ngày nhận hàng : " +pxkBean.getNgaylap(), font2);
			lien2.setSpacingAfter(15);
			lien2.setAlignment(Element.ALIGN_CENTER);
			//Cho Nay Chua Tim Duoc Duong Dan Dong,Can Phai Cai Thien---KHOAND
			Image hinhanh=Image.getInstance("C:/Tomcat 5.5/webapps/AHF/pages/images/logo.png");	
			//Image hinhanh =Image.getInstance("D:/AHF/SGP/WebContent/pages/images/logo.bmp");
			
			PdfPCell cellslogo = new PdfPCell(hinhanh);
			cellslogo.setPadding(10);
			cellslogo.setBorderWidthBottom(0);
			cellslogo.setBorderWidthRight(0);
			tableheader.addCell(cellslogo);
			PdfPCell celltieude=new PdfPCell();
			celltieude.addElement(pxk);
			celltieude.addElement(HD_E);
			celltieude.addElement(lien2);
			celltieude.setBorderWidthBottom(0);
			celltieude.setBorderWidthRight(0);
			celltieude.setBorderWidthLeft(0);
			
			tableheader.addCell(celltieude);
			PdfPCell cellinfo=new PdfPCell();
			
			cellinfo.addElement(new Paragraph("CÔNG TY TNHH",fontnomar));
			cellinfo.addElement(new Paragraph("Co, Ltd",fontnomar));
			cellinfo.addElement(new Paragraph("Số 3,Đường Số 4",fontnomar));
			cellinfo.addElement(new Paragraph("Quận 3,Tp.Hồ Chí Minh",fontnomar));
			cellinfo.addElement(new Paragraph("Tell: 080808080",fontnomar));
			cellinfo.addElement(new Paragraph("Fax : 080808080",fontnomar));
			cellinfo.addElement(new Paragraph("Mẫu số:AT308483",fontnomar));
			cellinfo.addElement(new Paragraph("Ký hiệu :294/48",fontnomar));
			cellinfo.addElement(new Paragraph("Số hóa đơn/Invoice No:" + pxkBean.getSoHoaDon(),fontnomar));
			cellinfo.addElement(new Paragraph("Ngày/Date :"+pxkBean.getNgaygiaodich(),fontnomar));
			cellinfo.setRowspan(2);
			//cellinfo.setBorderWidthBottom(0);
			//cellinfo.setBorderWidthRight(0);
			cellinfo.setBorderWidthLeft(0);
			tableheader.addCell(cellinfo);
			
			PdfPCell cellmst=new PdfPCell();
			cellmst.setColspan(2);
			
			
			pxk = new Paragraph("MST:VAT code: ", font2);
			pxk.setSpacingAfter(10);
			pxk.setAlignment(Element.ALIGN_LEFT);
			cellmst.addElement(pxk);
			
			pxk = new Paragraph("Số tài khoản/Account No: 9709709 ABC BAND ", font2);
			pxk.setSpacingAfter(10);
			pxk.setAlignment(Element.ALIGN_LEFT);
			cellmst.addElement(pxk);
			
			pxk = new Paragraph("Building No: " + pxkBean.getId(), font2);
			pxk.setSpacingAfter(10);
			pxk.setAlignment(Element.ALIGN_LEFT);
			cellmst.addElement(pxk);
			cellmst.setBorderWidthTop(0);
			cellmst.setBorderWidthRight(0);
			//cellmst.setBorderWidthLeft(0);
			//cellmst.setBorder(1);
			tableheader.addCell(cellmst);
			document.add(tableheader);
			PdfPTable table_header=new PdfPTable(2);
			float[] with3={4.0f,2.0f};
			table_header.setWidthPercentage(100);
			table_header.setWidths(with3);
			PdfPCell cell111= new PdfPCell();
			cell111.addElement(new Paragraph("Tên khách hàng/Customer's Name :" + npp.getTen(),fontnomar));
			cell111.addElement(new Paragraph("MST KH/Cust Vat Code :" + npp.getMaSoThue() ,fontnomar));
			cell111.addElement(new Paragraph("Địa chỉ/Address :" +npp.getDiaChi(),fontnomar ));
			cell111.addElement(new Paragraph("Hình thức thanh toán /Payment Term :" ,fontnomar));
			table_header.addCell(cell111);
			PdfPCell cell22= new PdfPCell();
			cell22.addElement(new Paragraph("Hoạch toán :",fontnomar));
			cell22.addElement(new Paragraph("Nợ :",fontnomar ));
			cell22.addElement(new Paragraph("Có :" ,fontnomar));
			table_header.addCell(cell22);
	        document.add(table_header);
	       
			//Table Content
			PdfPTable table = new PdfPTable(8);
			table.setWidthPercentage(100);//chieu dai cua bảng 
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withs = {4.0f,13.0f, 22.0f, 7.0f,7.0f,7.0f,10.0f,10.0f};
	        table.setWidths(withs);//set các độ rộng cho bảng
	        
	        
			String[] th = new String[]{"STT", "Mã số", "Tên,Nhãn hiện,quy cách phẩm chất vật tư", "DVT","SL","Đơn giá","Chiết khấu","Thành tiền"};
			PdfPCell[] cell = new PdfPCell[8];
			for(int i=0; i < 8 ; i++)
			{
				cell[i] = new PdfPCell(new Paragraph(th[i], font2));
				cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[i].setBackgroundColor(BaseColor.LIGHT_GRAY);		
				table.addCell(cell[i]);			
			}
			Font font4 = new Font(bf, 9);
			PdfPCell cells_detail = new PdfPCell();
			
			double totalle=0;
			for(int i = 0; i < spList.size(); i++)
			{
				IHoaDonGTGT_SP sanpham = (IHoaDonGTGT_SP)spList.get(i);
				String[] arr = new String[]{Integer.toString(i+1), sanpham.getMaSanPham(), sanpham.getTenSanPham(), sanpham.getDonViTinh(),Integer.toString(sanpham.getSoLuong()) ,formatter.format(sanpham.getDonGia()),formatter.format(sanpham.getChietKhau()),formatter.format(sanpham.getThanhTien())};
				totalle= totalle+sanpham.getSoLuong()* sanpham.getDonGia();
				for(int j=0; j < 8; j++)
				{
					cells_detail = new PdfPCell(new Paragraph(arr[j], font4));
					if(j==2){
						cells_detail.setHorizontalAlignment(Element.ALIGN_LEFT);
					}else if(j==7){
						cells_detail.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}else{
						cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
					}
					
					cells_detail.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells_detail.setBorderWidthBottom(0);
					cells_detail.setBorderWidthTop(0);
					table.addCell(cells_detail);
					
				}
			}	
			PdfPCell cellghichukm=new PdfPCell(new Paragraph("Tiền hưởng khuyễn mãi ("+pxkBean.getGhiChuCHuongKm()+"):",font4));
			cellghichukm.setColspan(7);
			cellghichukm.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellghichukm.setBorderWidthBottom(0);
			//cellghichukm.setBorderWidthTop(0);
			table.addCell(cellghichukm);
			
			PdfPCell celltienhuongkm=new PdfPCell(new Paragraph("(" +formatter.format(ddh.getSoTienTraKM())+")",fontnomar));
			celltienhuongkm.setHorizontalAlignment(Element.ALIGN_RIGHT);
			celltienhuongkm.setBorderWidthBottom(0);
			//celltienhuongkm.setBorderWidthTop(0);
			table.addCell(celltienhuongkm);
			
			PdfPCell cellghichutb=new PdfPCell(new Paragraph("Tiền hưởng trưng bày ("+pxkBean.getGhiChuCHuongTB()+") :",font4));
			cellghichutb.setColspan(7);
			cellghichutb.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellghichutb.setBorderWidthBottom(0);
			cellghichutb.setBorderWidthTop(0);
			table.addCell(cellghichutb);
			
			
			PdfPCell celltienhuongtb=new PdfPCell(new Paragraph("(" +formatter.format(ddh.getSoTienTraTB())+")",fontnomar));
			celltienhuongtb.setHorizontalAlignment(Element.ALIGN_RIGHT);
			celltienhuongtb.setBorderWidthBottom(0);
			celltienhuongtb.setBorderWidthTop(0);
			table.addCell(celltienhuongtb);
			
			
			PdfPCell cellghichuck=new PdfPCell(new Paragraph("Tiền hưởng chiết khấu  :",font4));
			cellghichuck.setColspan(7);
			cellghichuck.setBorderWidthTop(0);
			cellghichuck.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cellghichuck);
			
			double tienchietkhau=totalle/100* ddh.getChietkhau();
			
			PdfPCell celltienhuongck=new PdfPCell(new Paragraph("("+formatter.format(tienchietkhau)+")",fontnomar));
			celltienhuongck.setHorizontalAlignment(Element.ALIGN_RIGHT);
			celltienhuongck.setBorderWidthTop(0);
			table.addCell(celltienhuongck);
			document.add(table);
			
			//Table phan tinh tổng tiền
			PdfPTable tabletongtien=new PdfPTable(3);
			float[] size={35.0f,35.0f,10.0f};
			tabletongtien.setWidths(size);
			tabletongtien.setWidthPercentage(100);
			PdfPCell celltongtien=new PdfPCell();
			celltongtien.addElement(new Paragraph(""));
			celltongtien.addElement(new Paragraph("Thuế suất GTGT(VAT Rate) : "+ ddh.getVAT(),fontnomar));
			celltongtien.addElement(new Paragraph(""));

			
			tabletongtien.addCell(celltongtien);
			
			PdfPCell cellcenter=new PdfPCell();
			cellcenter.addElement( new Paragraph("Tổng cộng /Total :",fontnomar));
			cellcenter.addElement(new Paragraph("Tiền thuế GTGT /VAT :",fontnomar));
			cellcenter.addElement(new Paragraph("Tổng cộng tiền thanh toán",fontnomar));
			cellcenter.addElement(new Paragraph("(Total of paymet)",fontnomar));
			tabletongtien.addCell(cellcenter);
			PdfPCell celltien=new PdfPCell();
			
			Paragraph tongtien= new Paragraph(""+ formatter.format(totalle),fontnomar);
			tongtien.setAlignment(Element.ALIGN_RIGHT);
			celltien.addElement(tongtien);
			
			double tiensauvat= totalle /100 * ddh.getVAT();
			Paragraph tien_sau_vat=new Paragraph("" + formatter.format(tiensauvat),fontnomar);
			tien_sau_vat.setAlignment(Element.ALIGN_RIGHT);
			celltien.addElement(tien_sau_vat);
			
			
			double tienphaitra=totalle+tiensauvat-tienchietkhau-ddh.getSoTienTraKM()-ddh.getSoTienTraTB();
			Paragraph tien_phai_tra=new Paragraph("" +formatter.format(tienphaitra),fontnomar);
			tien_phai_tra.setAlignment(Element.ALIGN_RIGHT);
			celltien.addElement(tien_phai_tra);
			celltien.addElement(new Paragraph(""));
			
			tabletongtien.addCell(celltien);
			PdfPCell celldoctien=new PdfPCell();
			celldoctien.setColspan(3);
			
			doctienrachu doctien=new doctienrachu();
			celldoctien.addElement(new Paragraph("Số tiền bằng chữ:" + doctien.tranlate(Long.toString((long)tienphaitra)),fontnomar));
			tabletongtien.addCell(celldoctien);
			document.add(tabletongtien);
			//Table Footer			
			PdfPTable tableFooter = new PdfPTable(4);
			tableFooter.setWidthPercentage(100);
			tableFooter.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			//tableFooter.setSpacingBefore(15);
			tableFooter.setWidths(new float[]{25.0f, 25.0f, 25.0f,25.0f});
			
			PdfPCell cell11 = new PdfPCell();
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			Paragraph km=new Paragraph("Khách hàng", font2);
			km.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(km);
			km=new Paragraph("Customer's Full Name & Signature", font8normal);
			km.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(km);
			km=new Paragraph("Ký,ghi rõ họ tên", font8normal);
			km.setAlignment(Element.ALIGN_CENTER);
			
			cell11.addElement(km);
			cell11.setPaddingBottom(60);
			PdfPCell cell12 = new PdfPCell();
			cell12.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			Paragraph thukho=new Paragraph("Thủ kho", font2);
			thukho.setAlignment(Element.ALIGN_CENTER);
			cell12.addElement(thukho);
			thukho=new Paragraph("F.G WareHouse Sup", font8normal);
			thukho.setAlignment(Element.ALIGN_CENTER);
			cell12.addElement(thukho);
			thukho=new Paragraph("Ký, ghi rõ họ tên", font8normal);
			thukho.setAlignment(Element.ALIGN_CENTER);
			cell12.addElement(thukho);
			cell12.setPaddingBottom(60);
		
			PdfPCell cell14 = new PdfPCell();
			cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
			Paragraph  nguoiban=new Paragraph("Người bán hàng", font2);
			nguoiban.setAlignment(Element.ALIGN_CENTER);
			cell14.addElement(nguoiban);
			nguoiban=new Paragraph("Saler", font8normal);
			nguoiban.setAlignment(Element.ALIGN_CENTER);
			cell14.addElement(nguoiban);
			nguoiban=new Paragraph("Ký,ghi rõ họ tên",font8normal);
			nguoiban.setAlignment(Element.ALIGN_CENTER);
			cell14.addElement(nguoiban);
			
			cell14.setPaddingBottom(60);
			PdfPCell cell15 = new PdfPCell();
			Paragraph thutruong=new Paragraph("Thủ trưởng đơn vị", font2);
			thutruong.setAlignment(Element.ALIGN_CENTER);
			
			cell15.addElement(thutruong);
			thutruong=new Paragraph("Authorized Signature", font8normal);
			thutruong.setAlignment(Element.ALIGN_CENTER);
			
			cell15.addElement(thutruong);
			thutruong=new Paragraph("Ký, ghi rõ họ tên", font8normal);
			thutruong.setAlignment(Element.ALIGN_CENTER);
			cell15.addElement(thutruong);
		
			cell15.setPaddingBottom(60);
			tableFooter.addCell(cell11);
			tableFooter.addCell(cell12);
			tableFooter.addCell(cell14);
			tableFooter.addCell(cell15);
			document.add(tableFooter);
			
			document.close(); 
		}
		catch(DocumentException e)
		{
			e.printStackTrace();
		}
	}

}
