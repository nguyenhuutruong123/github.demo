package geso.dms.center.servlets.phieuxuatkhott;

import geso.dms.center.beans.phieuxuatkhott.IPhieuXuatKhoTT;
import geso.dms.center.beans.phieuxuatkhott.IPhieuXuatKhoTT_SP;
import geso.dms.center.beans.phieuxuatkhott.imp.PhieuXuatKhoTT;
import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.donhang.ISanpham;
import geso.dms.distributor.beans.donhang.imp.Sanpham;
import geso.dms.distributor.beans.phieuxuatkho.IPhieuxuatkho;
import geso.dms.distributor.beans.phieuxuatkho.imp.Phieuxuatkho;
import geso.dms.distributor.db.sql.dbutils;

import java.io.IOException;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class PhieuXuatKhoTTPdfSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PhieuXuatKhoTTPdfSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		IPhieuXuatKhoTT pxkBean; 
	      dbutils db;
		
		HttpSession session = request.getSession();
		pxkBean = (IPhieuXuatKhoTT)session.getAttribute("pxkBean");
		
		 db = new dbutils();
		List<IPhieuXuatKhoTT_SP> sanphamList = pxkBean.getListSanPham();
	
				
		response.setContentType("application/pdf");
		//response.setHeader("Content-Disposition"," inline; filename=Phieuxuatkho.pdf");
		
		//hien thi hop thoai dialog save file xuong may Client
		response.setHeader("Content-Disposition"," attachment; filename=\"Phieuxuatkho.pdf\" ");
		
		Document document = new Document();
		ServletOutputStream outstream = response.getOutputStream();
		this.CreatePxk(document, outstream, sanphamList);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		
		Utility util = new Utility();
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		
	IPhieuXuatKhoTT pxkBean; 
    dbutils db;
	
		String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
		pxkBean = new PhieuXuatKhoTT(id);
		System.out.println("ID :"+id);
		pxkBean.setListSanPham();
		
		db = new dbutils();
		
		List<IPhieuXuatKhoTT_SP> sanphamList = pxkBean.getListSanPham();		
				
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition"," inline; filename=Phieuxuatkho.pdf");
		
		//hien thi hop thoai dialog save file xuong may Client
	    //response.setHeader("Content-Disposition"," attachment; filename=\"Phieuxuatkho.pdf\" ");
		
		Document document = new Document();
		ServletOutputStream outstream = response.getOutputStream();
		this.CreatePxk(document, outstream, sanphamList);
	}

	private void CreatePxk(Document document, ServletOutputStream outstream, List<IPhieuXuatKhoTT_SP> spList) throws IOException
	{
		
		IPhieuXuatKhoTT pxkBean = new PhieuXuatKhoTT(); 
	      dbutils db;
		
		try{			
			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			PdfWriter.getInstance(document, outstream);
			document.open();
		
			//chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 15, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			Font font3 = new Font(bf, 8, Font.UNDERLINE);
			//font2.setColor(BaseColor.GREEN);
			 
			Paragraph pxk = new Paragraph("Phiếu Xuất Kho", font);
			pxk.setSpacingAfter(10);
			pxk.setAlignment(Element.ALIGN_CENTER);
			document.add(pxk);
			
			pxk = new Paragraph("( Ngày : " + pxkBean.getNgaylap().substring(8, 10)+ " Tháng : " + pxkBean.getNgaylap().substring(5,7 ) + " Năm :" +pxkBean.getNgaylap().substring(0,4 )+" )" , font2);
			//pxk = new Paragraph("Ngày nhận hàng : " +pxkBean.getNgaylap(), font2);
			pxk.setSpacingAfter(15);
			pxk.setAlignment(Element.ALIGN_CENTER);
			document.add(pxk);
			
			//set Ho ten nguoi nhan hang
			pxk = new Paragraph("Họ tên người nhận hàng : " +pxkBean.getHoTenNguoiNhan(), font2);
			pxk.setSpacingAfter(10);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);

			//set dia chi giao nhan
			pxk = new Paragraph("Địa chỉ nhận hàng : " +pxkBean.getDiaChiNguoiNhan(), font2);
			pxk.setSpacingAfter(10);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);


			//set Ly do xuat
			pxk = new Paragraph("Lý do xuất hàng : " +pxkBean.getLyDoXuat(), font2);
			pxk.setSpacingAfter(10);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);

			//set Xuất tại kho 
			pxk = new Paragraph("Xuất tại kho  : "+pxkBean.getTenKho(), font2);
			pxk.setSpacingAfter(10);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);

			//set Xuất tại kho 
			pxk = new Paragraph("Ghi chú : "+pxkBean.getGhiChu(), font2);
			pxk.setSpacingAfter(10);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			//Table Content
			PdfPTable table = new PdfPTable(8);
			table.setWidthPercentage(100);//chieu dai cua bảng 
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withs = {4.0f,30.0f, 10.0f, 7.0f,7.0f,7.0f,7.0f,7.0f};
	        table.setWidths(withs);//set các độ rộng cho bảng
	        
			String[] th = new String[]{"STT", "Tên,Nhãn hiện,quy cách phẩm chất vật tư", "Mã số", "DVT","Quy cách","Số lượng","Thùng","Lẻ"};
			PdfPCell[] cell = new PdfPCell[8];
			for(int i=0; i < 8 ; i++)
			{
				cell[i] = new PdfPCell(new Paragraph(th[i], font2));
				cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[i].setBackgroundColor(BaseColor.LIGHT_GRAY);		
				table.addCell(cell[i]);			
			}
			Font font4 = new Font(bf, 8);
			PdfPCell cells = new PdfPCell();
			int totalsoluong=0;
			int totalthung=0;
			int totalle=0;
			for(int i = 0; i < spList.size(); i++)
			{
				IPhieuXuatKhoTT_SP sanpham = (IPhieuXuatKhoTT_SP)spList.get(i);
				String[] arr = new String[]{Integer.toString(i+1), sanpham.getTenSanPham(), sanpham.getMaSanPham(), sanpham.getDonViTinh(),Integer.toString(sanpham.getQuyCach()) ,Integer.toString(sanpham.getSoLuong()),Integer.toString( sanpham.getSoLuongQuyDoi()),Integer.toString(sanpham.getLe())};
				totalsoluong=totalsoluong+sanpham.getSoLuong();
				totalthung=totalthung+ sanpham.getSoLuongQuyDoi();
				totalle= totalle+sanpham.getLe();
				for(int j=0; j < 8; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], font4));
					if(j==2)
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					else
						cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					
					table.addCell(cells);
				}
			}		
			PdfPCell celltotal=new PdfPCell(new Paragraph("Tổng cộng :", font4));
			celltotal.setColspan(5);
			celltotal.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(celltotal);
			PdfPCell celltotalsl=new PdfPCell(new Paragraph(Integer.toString(totalsoluong), font4));
			
			celltotalsl.setHorizontalAlignment(Element.ALIGN_CENTER);
			celltotalsl.setVerticalAlignment(Element.ALIGN_CENTER);
			table.addCell(celltotalsl);
			//set cot tong thùng
				PdfPCell celltotalthung=new PdfPCell(new Paragraph(Integer.toString(totalthung), font4));
				celltotalthung.setHorizontalAlignment(Element.ALIGN_CENTER);
				celltotalthung.setVerticalAlignment(Element.ALIGN_CENTER);
			table.addCell(celltotalthung);
			
			//set cot tong le
			PdfPCell celltotalle=new PdfPCell(new Paragraph(Integer.toString(totalle), font4));
			celltotalle.setHorizontalAlignment(Element.ALIGN_CENTER);
			celltotalle.setVerticalAlignment(Element.ALIGN_CENTER);
			table.addCell(celltotalle);
			
		
			document.add(table);
			//Table Footer			
			PdfPTable tableFooter = new PdfPTable(4);
			tableFooter.setWidthPercentage(90);
			tableFooter.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableFooter.setSpacingBefore(15);
			tableFooter.setWidths(new float[]{20.0f, 30.0f, 30.0f, 25.0f});
			
			PdfPCell cell11 = new PdfPCell(new Paragraph("Người lập phiếu", font2));
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell12 = new PdfPCell(new Paragraph("Tài xế", font2));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell13 = new PdfPCell(new Paragraph("Thủ kho", font2));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell14 = new PdfPCell(new Paragraph("Người nhận", font2));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell15 = new PdfPCell(new Paragraph("Người nhận", font2));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			cell11.setBorder(0);
			cell12.setBorder(0);
			cell13.setBorder(0);
			cell14.setBorder(0);
			cell15.setBorder(0);
			tableFooter.addCell(cell11);
			tableFooter.addCell(cell12);
			tableFooter.addCell(cell13);
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
