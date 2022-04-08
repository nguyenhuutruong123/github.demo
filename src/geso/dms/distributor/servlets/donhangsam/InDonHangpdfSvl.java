package geso.dms.distributor.servlets.donhang;

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
import geso.dms.distributor.beans.donhang.IDonhang;
import geso.dms.distributor.beans.donhang.ISanpham;
import geso.dms.distributor.beans.donhang.imp.Donhang;
import geso.dms.distributor.beans.khachhang.imp.Khachhang;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Enumeration;
import java.util.Hashtable;
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


public class InDonHangpdfSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InDonHangpdfSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
		IDonhang dhBean;
		dbutils db;
		
		Utility util=new Utility();
		String querystring=request.getQueryString();
		String dhid=util.getId(querystring);
		userId=util.getUserId(querystring);
		
		 dhBean=new Donhang(dhid); 
		 dhBean.setUserId(userId); //phai co UserId truoc khi Init
		 dhBean.init();	
		 dhBean.CreateSpDisplay();
		 
	
		 
		 dhBean.setKhId(dhBean.getKhId());
			 List<ISanpham> splist = (List<ISanpham>)dhBean.getSpList();
			 List<ISanpham> spkmlist = (List<ISanpham>)dhBean.getScheme_SpList();
			  Hashtable<String, Float> scheme_tongtien = dhBean.getScheme_Tongtien(); 
			  Hashtable<String, Float> scheme_chietkhau = dhBean.getScheme_Chietkhau(); 
		 db = new dbutils();
		
		response.setContentType("application/pdf");
		//response.setHeader("Content-Disposition"," inline; filename=Phieuxuatkho.pdf");
		
		//hien thi hop thoai dialog save file xuong may Client
		//response.setHeader("Content-Disposition"," attachment; filename=\"Phieuxuatkho.pdf\" ");
		
		Document document = new Document();
		ServletOutputStream outstream = response.getOutputStream();
		this.CreatePxk(document, outstream, splist,spkmlist,scheme_tongtien,scheme_chietkhau, dhBean, db);
		}
	}

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
		IDonhang dhBean;
		dbutils db;
		
		userId = cutil.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		String id=cutil.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
		
		 dhBean=new Donhang(id);
		 dhBean.setUserId(userId); //phai co UserId truoc khi Init
	     dhBean.init();
	     dhBean.setKhId(dhBean.getKhId());
		 System.out.println("So don hang :"+id);
		 dhBean.CreateSpDisplay();
		
		 List<ISanpham> splist = (List<ISanpham>)dhBean.getSpList();
		 List<ISanpham> spkmlist = (List<ISanpham>)dhBean.getScheme_SpList();
		  Hashtable<String, Float> scheme_tongtien = dhBean.getScheme_Tongtien(); //khuyen mai tien
		  Hashtable<String, Float> scheme_chietkhau = dhBean.getScheme_Chietkhau(); //khuyen mai chiet khau
		db = new dbutils();

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition"," inline; filename=HoaDonGTGT.pdf");
		
		//hien thi hop thoai dialog save file xuong may Client
	    //response.setHeader("Content-Disposition"," attachment; filename=\"Phieuxuatkho.pdf\" ");
		
		Document document = new Document();
		ServletOutputStream outstream = response.getOutputStream();
		this.CreatePxk(document, outstream, splist,spkmlist,scheme_tongtien,scheme_chietkhau, dhBean, db);
		}
	}

	private void CreatePxk(Document document, ServletOutputStream outstream, List<ISanpham> spList, List<ISanpham> spkmlist,Hashtable<String, Float> scheme_tongtien,Hashtable<String, Float> scheme_chietkhau, IDonhang dhBean, dbutils db) throws IOException
	{
		try{		
			
			
			
			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			NumberFormat formatter1 = new DecimalFormat("#,###,###.##"); 
			
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
			 //KHAI BAO 1 BANG CO BAO NHIEU COT
			
			PdfPTable tableheader=new PdfPTable(3);
			tableheader.setWidthPercentage(100);//chieu dai cua báº£ng 
			tableheader.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withsheader = {10.0f,50.0f,10.0f};//SET DO RONG CAC COT
			tableheader.setWidths(withsheader); 
			
			//khoang trang goc trai
			Paragraph space = new Paragraph("", font);
			space.setSpacingAfter(10);
			space.setAlignment(Element.ALIGN_CENTER);
			PdfPCell cellright=new PdfPCell();
			cellright.addElement(space);
			cellright.setBorderWidthRight(0);
			
			tableheader.addCell(cellright);
			
			
			Paragraph pxk = new Paragraph("HÃ“A Ä�Æ N", font);
			pxk.setSpacingAfter(10);
			pxk.setAlignment(Element.ALIGN_CENTER);
		
			
			
			Paragraph HD_E = new Paragraph("GIÃ� TRá»Š GIA TÄ‚NG", font);
			HD_E.setSpacingAfter(10);
			HD_E.setAlignment(Element.ALIGN_CENTER);
			
			
			Paragraph lien2 = new Paragraph("LiÃªn 2: Giao khÃ¡ch hÃ ng/Copy 2:Custumer", font2);
			//pxk = new Paragraph("NgÃ y nháº­n hÃ ng : " +pxkBean.getNgaylap(), font2);
			lien2.setSpacingAfter(15);
			lien2.setAlignment(Element.ALIGN_CENTER);
			//System.out.println("Ngay thang :"+dhBean.getNgaygiaodich());
			String ngaythang= "NgÃ y " + dhBean.getNgaygiaodich().substring(8, 10)+" ThÃ¡ng  " +dhBean.getNgaygiaodich().substring(5, 7) +" NÄƒm " +dhBean.getNgaygiaodich().substring(0, 4) ;
			
			Paragraph ngay= new Paragraph(ngaythang, font2);
			ngay.setSpacingAfter(10);
			ngay.setAlignment(Element.ALIGN_CENTER);

			PdfPCell celltieude=new PdfPCell();
			celltieude.addElement(pxk);
			celltieude.addElement(HD_E);
			celltieude.addElement(lien2);
			celltieude.addElement(ngay);
			celltieude.setBorderWidthLeft(0);
			
			tableheader.addCell(celltieude);
			
			PdfPCell cellinfo=new PdfPCell();
			
			cellinfo.addElement(new Paragraph("Máº«u sá»‘:",fontnomar));
		
			//cellinfo.setBorderWidthBottom(0);
			//cellinfo.setBorderWidthRight(0);
			//cellinfo.setBorderWidthLeft(0);
			tableheader.addCell(cellinfo);
			
			
			//Add bang vao document
			document.add(tableheader);
			
			
			
			PdfPTable table_info=new PdfPTable(1);
			float[] with3 = {50.0f};//SET DO RONG CAC COT
			table_info.setWidthPercentage(100);
			table_info.setWidths(with3);
			PdfPCell cell111= new PdfPCell();
			/*
			 * Get info distributor
			 * 
			 */
			String tennpp="";
			String diachi="";
			String dienthoai="";
			String masothue="";
			
			String sql_getinfodis="select ten,isnull(diachi,'Chua xac dinh') as diachi,isnull( masothue,'Chua xac dinh') as masothue ,isnull( dienthoai,'Chua xac dinh') as dienthoai from nhaphanphoi where pk_seq='"+dhBean.getNppId()+"'";
			ResultSet rs=db.get(sql_getinfodis);
			try{
				if(rs.next()){
				tennpp=rs.getString("ten");
				diachi=rs.getString("diachi");
				dienthoai=rs.getString("dienthoai");
				masothue=rs.getString("masothue");
				}
			}catch(Exception er){
				
			}
			
			String tenkh="";
			String diachikh="";
			String dienthoaikh="";
			String masothuekh="";
			String sql_getinfokh="select ten,isnull(dienthoai,'Chua xac dinh') as dienthoai,isnull(diachi,'Chua xac dinh') as diachi,isnull(masothue,'Chua xac dinh') as masothue from khachhang where pk_seq=" +dhBean.getKhId();
			System.out.println(sql_getinfokh);
			ResultSet rs_kh=db.get(sql_getinfokh);
			try{
				if(rs_kh.next()){
			 tenkh= rs_kh.getString("ten");
			 diachikh= rs_kh.getString("diachi");
			 dienthoaikh= rs_kh.getString("dienthoai");
			 masothuekh= rs_kh.getString("masothue");
				}
			}catch(Exception er ){
				System.out.println("Loi Khach Hang :" + er.toString());
			}
			cell111.addElement(new Paragraph("Ä�Æ¡n vá»‹ bÃ¡n hÃ ng :"+ tennpp ,fontnomar));
			cell111.addElement(new Paragraph("Ä�á»‹a chá»‰ :" +diachi  ,fontnomar));
			cell111.addElement(new Paragraph("MÃ£ sá»‘ thuáº¿ :" +masothue,fontnomar ));
			cell111.addElement(new Paragraph("Ä�iá»‡n thoáº¡i:"+dienthoai ,fontnomar));
			cell111.addElement(new Paragraph("Há»� tÃªn ngÆ°á»�i mua hÃ ng :"+ tenkh ,fontnomar));
			cell111.addElement(new Paragraph("TÃªn Ä‘Æ¡n vá»‹:" ,fontnomar));
			cell111.addElement(new Paragraph("Ä�á»‹a chá»‰:"+diachikh  ,fontnomar));
			cell111.addElement(new Paragraph("MÃ£ sá»‘ thuáº¿ :" +masothuekh,fontnomar));
			cell111.addElement(new Paragraph("HÃ¬nh thá»©c thanh toÃ¡n  :......................" + " TÃ i khoáº£n :......................"  ,fontnomar));
			cell111.setPaddingBottom(10);
			table_info.addCell(cell111);
			
			
			document.add(table_info);
			//Table Content
			PdfPTable table = new PdfPTable(8);//Chu y nhe 6 cot o day, thi xuong duoi nho set withs la 6 cot
			table.setWidthPercentage(100);//chieu dai cua báº£ng 
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withs = {4.0f,14.0f,30.0f, 10.0f, 10.0f, 10.0f,10.0f,10.0f}; 			
	        table.setWidths(withs);//set cÃ¡c Ä‘á»™ rá»™ng cho báº£ng
			String[] th = new String[]{"STT","MÃ£ hÃ ng", "TÃªn hÃ ng hÃ³a ,dá»‹ch vá»¥ ", "Ä�Æ¡n vá»‹ tÃ­nh","Sá»‘ lÆ°á»£ng","Ä�Æ¡n giÃ¡","Chiáº¿t kháº¥u","ThÃ nh tiá»�n"};
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
				ISanpham sanpham = (ISanpham)spList.get(i);
				double dongia=Double.parseDouble(sanpham.getDongia());
				double chietkhau=Double.parseDouble(sanpham.getSoluong())* Double.parseDouble(sanpham.getDongia()) /100 * Double.parseDouble(sanpham.getChietkhau());
				String[] arr = new String[]{Integer.toString(i+1),sanpham.getMasanpham(), sanpham.getTensanpham(), sanpham.getDonvitinh() ,sanpham.getSoluong() ,formatter1.format(dongia),formatter.format(chietkhau),formatter.format(Double.parseDouble(sanpham.getSoluong())* Double.parseDouble(sanpham.getDongia())-chietkhau )};
				
				totalle= totalle+Double.parseDouble(sanpham.getSoluong())* Double.parseDouble(sanpham.getDongia()) -chietkhau;
				for(int j=0; j < 8; j++)
				{
					cells_detail = new PdfPCell(new Paragraph(arr[j], font4));
					if(j==2){
						cells_detail.setHorizontalAlignment(Element.ALIGN_LEFT);
					}else if(j==5){
						cells_detail.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}else{
						cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
					}
					
					cells_detail.setVerticalAlignment(Element.ALIGN_MIDDLE);
					//cells_detail.setBorderWidthBottom(0);
					//cells_detail.setBorderWidthTop(0);
					table.addCell(cells_detail);
					
				}
			}	
			
			
			//hang khuyen mai
			for(int i = 0; i < spkmlist.size(); i++)
			{
				ISanpham sanpham = (ISanpham)spkmlist.get(i);
				double dongia=Double.parseDouble(sanpham.getDongia());
				double chietkhau=Double.parseDouble(sanpham.getSoluong())* Double.parseDouble(sanpham.getDongia()) /100 * Double.parseDouble(sanpham.getChietkhau());
				String[] arr = new String[]{"*",sanpham.getMasanpham(), sanpham.getTensanpham(), sanpham.getDonvitinh() ,sanpham.getSoluong() ,formatter1.format(dongia),formatter.format(chietkhau),formatter.format(Double.parseDouble(sanpham.getSoluong())* Double.parseDouble(sanpham.getDongia())-chietkhau )};
				for(int j=0; j < 8; j++)
				{
					cells_detail = new PdfPCell(new Paragraph(arr[j], font4));
					if(j==2){
						cells_detail.setHorizontalAlignment(Element.ALIGN_LEFT);
					}else if(j==5){
						cells_detail.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}else{
						cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
					}
					
					cells_detail.setVerticalAlignment(Element.ALIGN_MIDDLE);
					//cells_detail.setBorderWidthBottom(0);
					//cells_detail.setBorderWidthTop(0);
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
					Double  tongtien=(double)scheme_tongtien.get(key);
					String[] arr = new String[]{"*",key, "", "" ,"" ,"","","-"+ formatter.format(tongtien) };
					for(int j=0; j < 8; j++)
					{
						cells_detail = new PdfPCell(new Paragraph(arr[j], font4));
						if(j==2){
							cells_detail.setHorizontalAlignment(Element.ALIGN_LEFT);
						}else if(j==5){
							cells_detail.setHorizontalAlignment(Element.ALIGN_RIGHT);
						}else{
							cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
						}
						
						cells_detail.setVerticalAlignment(Element.ALIGN_MIDDLE);
						//cells_detail.setBorderWidthBottom(0);
						//cells_detail.setBorderWidthTop(0);
						table.addCell(cells_detail);
						
					}
				}
				
			}
			
			// Xuat ra khuyen mai tra chiet khau
			if(scheme_chietkhau.size() > 0)
			{
				Enumeration<String> keys = scheme_chietkhau.keys();
				while(keys.hasMoreElements())
				{
					String key = keys.nextElement();
					tongtienkhuyenmai=tongtienkhuyenmai+scheme_chietkhau.get(key);
					double tienchietkhau= (double)scheme_chietkhau.get(key);
					String[] arr = new String[]{"*",key, "", "" ,"" ,"","-"+formatter.format(tienchietkhau),"" };
					for(int j=0; j < 8; j++)
					{
						cells_detail = new PdfPCell(new Paragraph(arr[j], font4));
						if(j==2){
							cells_detail.setHorizontalAlignment(Element.ALIGN_LEFT);
						}else if(j==5){
							cells_detail.setHorizontalAlignment(Element.ALIGN_RIGHT);
						}else{
							cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
						}
						
						cells_detail.setVerticalAlignment(Element.ALIGN_MIDDLE);
						//cells_detail.setBorderWidthBottom(0);
						//cells_detail.setBorderWidthTop(0);
						table.addCell(cells_detail);
						
					}
				}
				
			}
			PdfPCell cell11 = new PdfPCell();
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			Paragraph km=new Paragraph("Cá»™ng tiá»�n hÃ ng", font2);
			km.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(km);
			cell11.setColspan(7);
			table.addCell(cell11);
			
			PdfPCell cell12 = new PdfPCell();
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			Paragraph tongtien =new Paragraph( formatter.format(totalle), font2);
			tongtien.setAlignment(Element.ALIGN_CENTER);
			cell12.addElement(tongtien);
			table.addCell(cell12);
			//thue gtgt
			 cell11 = new PdfPCell();
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			 km=new Paragraph("Thuáº¿ suáº¥t GTGT:10%", font2);
			km.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(km);
			cell11.setColspan(7);
			table.addCell(cell11);
			//
			double tongtienthue=totalle/100 *10 ;
			cell12 = new PdfPCell();
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			tongtien =new Paragraph(formatter.format(tongtienthue) , font2);
			tongtien.setAlignment(Element.ALIGN_CENTER);
			cell12.addElement(tongtien);
			table.addCell(cell12);
			
			// tong tien khuyen mai
			 cell11 = new PdfPCell();
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			 km=new Paragraph("Tá»•ng sá»‘ tiá»�n chiáº¿t kháº¥u khuyáº¿n máº¡i", font2);
			km.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(km);
			cell11.setColspan(7);
			table.addCell(cell11);
			//
			
			cell12 = new PdfPCell();
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			tongtien =new Paragraph(formatter.format(tongtienkhuyenmai) , font2);
			tongtien.setAlignment(Element.ALIGN_CENTER);
			cell12.addElement(tongtien);
			table.addCell(cell12);
			//
			 cell11 = new PdfPCell();
			 cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			km=new Paragraph("Tá»•ng tiá»�n hÃ ng thanh toÃ¡n :", font2);
			km.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(km);
			cell11.setColspan(7);
			table.addCell(cell11);
			//
			double tongtiensauthue=totalle/100 *10 +totalle;
			cell12 = new PdfPCell();
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			tongtien =new Paragraph(formatter.format(tongtiensauthue-tongtienkhuyenmai) , font2);
			tongtien.setAlignment(Element.ALIGN_CENTER);
			cell12.addElement(tongtien);
			table.addCell(cell12);
			
			//doc tien bang chu
			doctienrachu doctien=new doctienrachu();
			 cell11 = new PdfPCell();
			 cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			 Long tongtienst= Math.round(tongtiensauthue-tongtienkhuyenmai);
			km=new Paragraph("Sá»‘ tiá»�n báº±ng chá»¯  :" + doctien.tranlate(tongtienst+""),  font2);
			km.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(km);
			cell11.setColspan(8);
			table.addCell(cell11);
			
			document.add(table);
			
			
			PdfPTable tablefooter=new PdfPTable(3);
			tablefooter.setWidthPercentage(100);//chieu dai cua báº£ng 
			tablefooter.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withfooterder = {35.0f,50.0f,35.0f};//SET DO RONG CAC COT
			tablefooter.setWidths(withfooterder); 
			
			//nguoimua hang 
			Paragraph para = new Paragraph("NgÆ°á»�i mua hÃ ng", fontnomar);
			
			para.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(para);
			
			cell11.setBorderWidthRight(0);
			tablefooter.addCell(para);
			//o giua
			 para = new Paragraph("", fontnomar);
			para.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(para);
			cell11.setBorderWidthLeft(0);
			cell11.setBorderWidthRight(0);
			tablefooter.addCell(para);
			//nguoi nhan hang
			 para = new Paragraph("NgÆ°á»�i nháº­n hÃ ng", fontnomar);
			para.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(para);
			cell11.setBorderWidthLeft(0);
			tablefooter.addCell(para);
			
			document.add(tablefooter);
			
			
			document.close(); 
		}
		catch(DocumentException e)
		{
			e.printStackTrace();
		}
	}

}
