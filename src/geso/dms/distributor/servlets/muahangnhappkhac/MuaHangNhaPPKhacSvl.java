/**
 * Author : KHOAND 
 * class name : MuaHangNhaPPKhacSvl
 * Date : 2011-10-20
 */

package geso.dms.distributor.servlets.muahangnhappkhac;

import geso.dms.distributor.beans.dondathang.IDondathang;
import geso.dms.distributor.beans.donhangnhapp.IDonhangnpp;
import geso.dms.distributor.beans.donhangnhapp.ISanPhamDhNpp;
import geso.dms.distributor.beans.donhangnhapp.imp.DonHangNPP;
import geso.dms.distributor.beans.donhangnhapp.imp.SanPhamDhNpp;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
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
import com.oreilly.servlet.MultipartRequest;


public class MuaHangNhaPPKhacSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MuaHangNhaPPKhacSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
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
		
		IDonhangnpp obj;
		PrintWriter out; 
			
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");		
		response.setContentType("application/pdf");
	    
	    
	    Utility util = new Utility();
	 //   out = response.getWriter();
	    
	    String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = util.getAction(querystring);
	    String dhId = util.getId(querystring);
	    
	
		//obj.setUserId(userId);
		 //lay thong tin nha phan phoi
	    
	   
	    obj=new DonHangNPP(dhId);
		 obj.createRs_BenNhanHang();
	
	    
	      obj.set_NppId_Mua(util.getIdNhapp(userId));
	      obj.setTenNPPMua(util.getTenNhaPP());
	      
	    session.setAttribute("userId",userId);
	    String printpdf=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("print")));
		if(printpdf==null)
			printpdf="";
		 String dis=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dis")));
			if(dis==null)
				dis="";
			
			 if(!dis.equals("")){
					
				 byte[] buffer=null;
			        
				   
				    	String filename=obj.displayfile(dhId, userId, 1);
				    	if(!filename.equals(""))
				    	{
				    	 try {
						    String pdfFilename = "C:\\upload\\"+filename;    // I use id here to determine pdf
						    File f = new File(pdfFilename);    
		
						    BufferedInputStream is = new BufferedInputStream(new FileInputStream(f));            
						    ByteArrayOutputStream bos = new ByteArrayOutputStream((int)f.length());
		
						    int ch;
						    long actual=0;
						    while((ch=is.read())!=-1){
						        bos.write(ch);    
						        actual++;
						    }
						    bos.flush();
						    bos.close();
						    buffer=bos.toByteArray();    
		
						    response.getOutputStream().write(buffer, 0, buffer.length);
						            
						    }
						    catch (Exception e) {
						    e.printStackTrace();
						    } 
				    	 return;
				    }else
				    {
				    	session.setAttribute("obj", obj);	
						String	nextJSP = "/AHF/pages/Distributor/NhanHangMuaNhaPPDisplay.jsp";
						response.sendRedirect(nextJSP);
						return;
				    }
				
			}	
			
			
		 if(!printpdf.equals("")){
			
			 	Document document = new Document();
				ServletOutputStream outstream = response.getOutputStream();
				this.CreateDondathangHPC(document, outstream, obj);
				document.close();
				return;
			}	
		if(action.equals("display"))
		{
			
			session.setAttribute("obj", obj);	
			String	nextJSP = "/AHF/pages/Distributor/NhanHangMuaNhaPPDisplay.jsp";
			response.sendRedirect(nextJSP);
		}
		else if(action.equals("update")){

	
			    
			session.setAttribute("obj", obj);	
	        String	nextJSP = "/AHF/pages/Distributor/NhanMuaHangNhaPPUpdate.jsp";
	        response.sendRedirect(nextJSP);
		}
		else
		{

				session.setAttribute("obj", obj);
				obj.setUserId(userId);
				obj.setListNhaPPMua("");
			    String	nextJSP = "/AHF/pages/Distributor/NhanHangMuaNhaPP.jsp";
			    response.sendRedirect(nextJSP);
		}
		
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//xu ly chot don hang
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
			String contentType = request.getContentType();
			System.out.println("contentype"+contentType);
			if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
			{
				MultipartRequest multi = new MultipartRequest(request, "C:\\upload\\", 20000000, "UTF-8");
				Enumeration files = multi.getFileNames();
				String filenameu = "";
				while (files.hasMoreElements())
				{
					String name = (String) files.nextElement();
					filenameu = multi.getFilesystemName(name);
					System.out.println("name  " + filenameu + "ori" + multi.getOriginalFileName(name));
					
				}
				byte[] buffer=null;
		        
			    try {

			    String pdfFilename = "C:\\upload\\"+filenameu;    // I use id here to determine pdf
			    File f = new File(pdfFilename);    

			    BufferedInputStream is = new BufferedInputStream(new FileInputStream(f));            
			    ByteArrayOutputStream bos = new ByteArrayOutputStream((int)f.length());

			    int ch;
			    long actual=0;
			    while((ch=is.read())!=-1){
			        bos.write(ch);    
			        actual++;
			    }
			    bos.flush();
			    bos.close();
			    buffer=bos.toByteArray();    
			    response.getOutputStream().write(buffer, 0, buffer.length);
			            
			    }
			    catch (Exception e) {
			    e.printStackTrace();
			    }    
			    geso.dms.center.util.Utility util = (geso.dms.center.util.Utility) session.getAttribute("util");
			    userId = util.antiSQLInspection(multi.getParameter("userId"));
				String madh=util.antiSQLInspection(multi.getParameter("id"));//truong truong hop don hang sua thi moi co id,nguoc lai th� khong co
				String action=multi.getParameter("action1");//action =new : che do them, nguoc lai la che do sua;
				IDonhangnpp obj=new DonHangNPP();
				obj.setUserId(userId);//sau khi set userid thi chuong trinh se set mancc,va ten ncc cho doi tuong dhbean(IDonhangnpp)
				obj.setId(madh);
				int flag=obj.luufile(madh, filenameu,userId, 1);
				if(flag==1 || flag==2)
				{
					obj=new DonHangNPP(madh);
					 obj.createRs_BenNhanHang();
				      obj.set_NppId_Mua(util.getIdNhapp(userId));
				      obj.setTenNPPMua(util.getTenNhaPP());
				  	session.setAttribute("obj", obj);	
					String	nextJSP = "/AHF/pages/Distributor/NhanHangMuaNhaPPDisplay.jsp";
					response.sendRedirect(nextJSP);
				}
				return;
			}
			
			
			
		//dbutils db = new dbutils();//tao doi tuong ket noi csdl
		int loi=0; ///loi tang len khi chuong trinh co loi
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    Utility util = new Utility();
	    
	    
	    userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		String madh=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));//truong truong hop don hang sua thi moi co id,nguoc lai th� khong co
		String action=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action1"));//action =new : che do them, nguoc lai la che do sua;
		IDonhangnpp dhbean=new DonHangNPP();
		dhbean.setUserId(userId);//sau khi set userid thi chuong trinh se set mancc,va ten ncc cho doi tuong dhbean(IDonhangnpp)
		dhbean.setId(madh);
		
		
	
		
				
		
			  //System.out.println("ten dang nhap moi la ;" +userId );  
			  
			
		
		  if(action.equals("chot"))
		  {
			//khai bao nha phan phoi mua hang,va ban hang
				String nppId_mua = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId_mua")));
				String nppId_ban = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId_ban")));
				if (nppId_mua == null){
					nppId_mua = "";	
				}
				
				dhbean.set_NppId_Mua(nppId_mua);
				dhbean.setNppId_Ban(nppId_ban);
				String VAT = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("VAT")));
		    	if (VAT == null)
		    		VAT = "";    	
		    	dhbean.setVAT(VAT);
				
				dhbean.setNguoisua(userId);
				String ngaysua = getDateTime();
		    	dhbean.setNgaysua(ngaysua);
		    	String ngaytao=getDateTime();
		    	dhbean.setNgaytao(ngaytao);
		    	
		    	String kbhid=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhbh")));
		    	String khoid=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khobh")));
		    	dhbean.setIdKho_Nhan(khoid);//mac dinh la kenh kho hang.
		    	dhbean.setIdKenh_Nhan(kbhid);// Can phai tim hieu them de set kenh 
		    	String ngaygd = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));//tungay la ten cua o textbox ben form donhangnppnew
		    	if (ngaygd == null || ngaygd == "")
					ngaygd = this.getDateTime();			
		    	dhbean.setNgayGiaoDich(ngaygd);
		    	String nhappmua=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhappmua")));
		    	dhbean.setTenNPPMua(nhappmua);
		    	String[] masp = request.getParameterValues("masp");
				String[] tensp = request.getParameterValues("tensp");
				String[] soluong = request.getParameterValues("soluong");
				String[] soluongnhan=request.getParameterValues("soluongnhan");
				String[] donvitinh = request.getParameterValues("donvitinh");
				String[] dongia = request.getParameterValues("dongia");
				String[] thanhtien = request.getParameterValues("thanhtien");
			    List<ISanPhamDhNpp>	spList = new ArrayList<ISanPhamDhNpp>();
				  if(masp != null){		
					int m = 0;
					while(m < masp.length){
						ISanPhamDhNpp sanpham = new SanPhamDhNpp();
						if(masp[m] != ""){
							if(soluong[m] != ""){ //chi them nhung san pham co so luong > 0
								sanpham.setMaSanPham(masp[m]);
								//System.out.println("ma san pham"+ masp[m]);
								
								sanpham.setTenSanPham(tensp[m]);
								try
								{
								sanpham.setSoLuong(Integer.parseInt(soluong[m]));
								}catch(Exception er){
									loi=loi+1;
									dhbean.setthongbao("SO LUONG KHONG HOP LE TREN DONG SAN PHAM : " + masp[m]);
								}
								sanpham.setDVT(donvitinh[m]);
								try
								{
								sanpham.setSoLuongNhan(Integer.parseInt(soluongnhan[m]));
								}catch(Exception er){
									loi=loi+1;
									dhbean.setthongbao("SO LUONG NHAN KHONG HOP LE TREN DONG SAN PHAM : " + masp[m]);
								}
								//sanpham.setDonHangNPP(DongHangNpp)
								sanpham.setGiaMua(Double.parseDouble(dongia[m]));
								sanpham.setThanhTien(Double.parseDouble(thanhtien[m]));
								spList.add(sanpham);
							}
						}
						m++;
					}	
				  }
				  
				  dhbean.setListSanPhamNew(spList);//add cac chi tiet san pham  vao don hang
				  session.setAttribute("obj", dhbean);
				  session.setAttribute("userId", userId);	 
					  dhbean.setTrangthai("1");//1 la chot don hang
					  if(loi>1)
						 {
							 session.setAttribute("type", "0");
							 String nextJSP = "/AHF/pages/Distributor/NhanHangMuaNhaPP.jsp";
							 response.sendRedirect(nextJSP);
						 }
			  if(dhbean.ChotDonHangNPP_Mua())
			  {
				  dhbean.setListNhaPPMua("");
				  String nextJSP = "/AHF/pages/Distributor/NhanHangMuaNhaPP.jsp";
				  response.sendRedirect(nextJSP);
			  }
			  else
			  {
				  dhbean.createRs_BenNhanHang();
				  String nextJSP = "/AHF/pages/Distributor/NhanMuaHangNhaPPUpdate.jsp";
					 response.sendRedirect(nextJSP);
			  }
			  
		  }
		 else if(action.equals("timkiem")){
			 
			 	
				String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));
				dhbean.setTrangthai(trangthai);
				
				String npptimkiem = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhappmuatk")));
				dhbean.setNppId_Ban(npptimkiem);
				String tungay=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
				dhbean.setTuNgay(tungay);
				
				String denngay=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
				dhbean.setDenNgay(denngay);
					
				
				dhbean.set_NppId_Mua(util.getIdNhapp(userId));
			 
				String sql1=" SELECT     A.PK_SEQ, M.TEN, T.TEN AS NGUOITAO, S.TEN AS NGUOISUA, A.NGAYNHAP, A.TRANGTHAI, A.NGAYTAO, A.NGAYSUA, "+
				" A.VAT, A.TONGGIATRI,  A.DATHANHTOAN, A.NPP_FK_MUA, A.NPP_FK_BAN, A.KHO_FK, A.KBH_FK "+
		        " FROM         dbo.DONHANG_NPP AS A INNER JOIN  dbo.NHAPHANPHOI AS M ON A.NPP_FK_BAN = M.PK_SEQ INNER JOIN "+
		                            "  dbo.NHANVIEN AS T ON T.PK_SEQ = A.NGUOITAO INNER JOIN "+
		                             "  dbo.NHANVIEN AS S ON S.PK_SEQ = A.NGUOISUA  "+
		       " WHERE   (A.NPP_FK_MUA = " + util.getIdNhapp(userId)+")";
				//Them cac dieu kien tim kiem
				   if(trangthai!="")
				    {
				    	sql1=sql1+ " and a.trangthai = "+ trangthai;
				    }
				   
					session.setAttribute("tungay", "");
					session.setAttribute("denngay","");//Truyen qua mac dinh 2 gia tri trong
				    if(tungay!="") {
				    	sql1=sql1+ " and A.NGAYNHAP >= '"+tungay+"'"; 
				    	session.setAttribute("tungay", tungay);//truyen qua tu ngay co gia tri
				    }
				    if(denngay!=""){
				     	sql1=sql1+ " and A.NGAYNHAP <= '"+denngay+"'"; 
				     
						session.setAttribute("denngay", denngay);//truyen qua den ngay co gia tri
				    }
				    	
				
					if(npptimkiem!="" && npptimkiem!=null){
						sql1=sql1+ " and A.NPP_FK_BAN=" + npptimkiem;
					}
					
					
					System.out.println("Khong vao day :");
					dhbean.setListNhaPPMua(sql1);
					dhbean.createRs_BenNhanHang();
				session.setAttribute("obj",dhbean );
				 String nextJSP = "/AHF/pages/Distributor/NhanHangMuaNhaPP.jsp";
				 response.sendRedirect(nextJSP);
		 }
		 
		}
		
	}

	private void CreateDondathangHPC(Document document, ServletOutputStream outstream, IDonhangnpp ddhBean) throws IOException
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
			 
			Paragraph ddh = new Paragraph("Mua Hàng Từ NPP Khác", font);
			ddh.setSpacingAfter(15);
			ddh.setAlignment(Element.ALIGN_CENTER);
			document.add(ddh);
			
			PdfPTable tableHead = new PdfPTable(2);
			tableHead.setWidthPercentage(90);
			tableHead.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableHead.setSpacingAfter(2);
			float[] with = {24.0f, 90.0f}; //set chieu rong cac columns
			tableHead.setWidths(with);
			
			PdfPCell cell1 = new PdfPCell(new Paragraph("Số đơn hàng:", font3));
			PdfPCell cell2 = new PdfPCell(new Paragraph(ddhBean.getId(), font2));
			cell1.setBorder(0);
			cell2.setBorder(0);
			tableHead.addCell(cell1);
			tableHead.addCell(cell2);
		
			PdfPCell cell5 = new PdfPCell(new Paragraph("Nhà phân phối bán hàng:", font3));
			PdfPCell cell6 = new PdfPCell(new Paragraph(ddhBean.getTenNPPBan(), font2));
			cell5.setBorder(0);
			cell6.setBorder(0);
				tableHead.addCell(cell5);
			tableHead.addCell(cell6);
			
			PdfPCell cell9 = new PdfPCell(new Paragraph("Nhà phân phối mua hàng:", font3));
			PdfPCell cell10 = new PdfPCell(new Paragraph(ddhBean.getTenNPPMua(), font2));
			cell9.setBorder(0);
			cell10.setBorder(0);
			tableHead.addCell(cell9);
			tableHead.addCell(cell10);
			PdfPCell cell11a = new PdfPCell(new Paragraph("Ngày đề nghị giao hàng:", font3));
			PdfPCell cell12a = new PdfPCell(new Paragraph(ddhBean.getNgayGiaoDich(), font2));
			cell11a.setBorder(0);
			cell12a.setBorder(0);
			
			tableHead.addCell(cell11a);
			tableHead.addCell(cell12a);
		
			tableHead.setSpacingAfter(20.0f);
			document.add(tableHead);
			
			
			PdfPTable table = new PdfPTable(7);
			table.setWidthPercentage(100);
			float[] withs = {16.0f, 42.0f, 11.0f,  12.0f, 14.0f, 12.0f,22.0f};
	        table.setWidths(withs);
	        
	        
			String[] th = new String[]{"Mã SP", "Tên SP", "Số lượng", "Số lượng nhận", "Đơn vị", "Đơn giá","T.Tiền"};
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
			
			List<ISanPhamDhNpp> splist = (List<ISanPhamDhNpp>)ddhBean.getSanPhamList();
			ISanPhamDhNpp sanpham =new SanPhamDhNpp();
			int size = splist.size();
			int m = 0;
			double thuevat=0;
			while (m < size){
				sanpham = splist.get(m);
				System.out.println(sanpham.getMaSanPham());
				cell[0] = new PdfPCell(new Paragraph(sanpham.getMaSanPham(), font2));		
				cell[0].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[0].setPadding(4);
				table.addCell(cell[0]);	
				
				cell[1] = new PdfPCell(new Paragraph(sanpham.getTenSanPham(), font2));		
				cell[1].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[1].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[1].setPadding(4);
				table.addCell(cell[1]);	
				
				cell[2] = new PdfPCell(new Paragraph(sanpham.getSoLuong()+"", font2));		
				cell[2].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[2].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[2].setPadding(4);
				table.addCell(cell[2]);	
				
				cell[3] = new PdfPCell(new Paragraph(sanpham.getSoLuongNhan()+"", font2));		
				cell[3].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[3].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[3].setPadding(4);
				table.addCell(cell[3]);	
				
				cell[4] = new PdfPCell(new Paragraph(sanpham.getDVT()+"", font2));		
				cell[4].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[4].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[4].setPadding(4);
				table.addCell(cell[4]);	
				
		
				
				cell[5] = new PdfPCell(new Paragraph(formatter.format(sanpham.getGiaMua()), font2));		
				cell[5].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[5].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[5].setPadding(4);
				table.addCell(cell[5]);
				
				cell[6] = new PdfPCell(new Paragraph(formatter.format(sanpham.getGiaMua()* sanpham.getSoLuongNhan()), font2));		
				cell[6].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[6].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[6].setPadding(4);
				table.addCell(cell[6]);
				
				m++;
			}
			document.add(table);
			
			PdfPTable tablefoot = new PdfPTable(2);
			tablefoot.setWidthPercentage(90);
			tablefoot.setHorizontalAlignment(Element.ALIGN_LEFT);
			tablefoot.setSpacingBefore(10);
			
			float[] withf = {350.0f,60.0f}; //set chieu rong cac columns
			tablefoot.setWidths(withf);
			
		
			
			PdfPCell cellf1 = new PdfPCell(new Paragraph("Tổng Tiền:", font3));
			PdfPCell cellf2 = new PdfPCell(new Paragraph(formatter.format(ddhBean.getTongGiaTri()), font2));
			cellf1.setBorder(0);
			cellf2.setBorder(0);
			cellf1.setPaddingLeft(330);

			tablefoot.addCell(cellf1);
			tablefoot.addCell(cellf2);
			double tienchuathue=(ddhBean.getTongGiaTri());
			double tiencothue=tienchuathue+tienchuathue/100*Double.parseDouble(ddhBean.getVAT());
		
			PdfPCell cellf3 = new PdfPCell(new Paragraph("Thuế VAT ("+ddhBean.getVAT()+"%):", font3));
			PdfPCell cellf4 = new PdfPCell(new Paragraph(formatter.format(tiencothue-ddhBean.getTongGiaTri()), font2));
			cellf3.setBorder(0);
			cellf4.setBorder(0);
			cellf3.setPaddingLeft(330);

		
			tablefoot.addCell(cellf3);
			tablefoot.addCell(cellf4);
			
			PdfPCell cellf5 = new PdfPCell(new Paragraph("Tổng Giá Trị:", font3));
			PdfPCell cellf6 = new PdfPCell(new Paragraph(formatter.format(tiencothue), font2));
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
		ddhBean.DBclose();
	}

	
}
