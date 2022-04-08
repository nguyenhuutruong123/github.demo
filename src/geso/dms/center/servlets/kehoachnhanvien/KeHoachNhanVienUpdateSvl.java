package geso.dms.center.servlets.kehoachnhanvien;

import geso.dms.center.beans.doctien.doctienrachu;
import geso.dms.center.beans.kehoachnhanvien.imp.*;
import geso.dms.center.beans.kehoachnhanvien.*;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

 








import org.apache.poi.ss.usermodel.CellStyle;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.HorizontalAlignmentType;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class KeHoachNhanVienUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private PrintWriter out;  

    public KeHoachNhanVienUpdateSvl() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String id = util.getId(querystring);	

	    IKeHoachNhanVien khnvBean = new KeHoachNhanVien(userId, id);
	    
	    String nextJSP = "";
	    if(request.getQueryString().indexOf("update") >= 0 && khnvBean.getTrangthai().equals("0") ) 
	    {
	    	//Update
	    	khnvBean.init();
		    khnvBean.createRs();
	    	nextJSP = "/AHF/pages/Center/KeHoachNhanVienUpdate.jsp";
	    } 
	    else
	    {
	    	//Display
	    	khnvBean.init(true);
	    	nextJSP = "/AHF/pages/Center/KeHoachNhanVienDisplay.jsp";
	    }
	    
        session.setAttribute("khnvBean", khnvBean);
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		boolean error = false;
		
		IKeHoachNhanVien khnvBean;
		//this.out = response.getWriter();
		
		Utility util = new Utility();
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		
	    String id =  util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
	    if(id == null){  	
	    	khnvBean = new KeHoachNhanVien(userId, "");
	    } else {
	    	khnvBean = new KeHoachNhanVien(userId, id);
	    }
	    
    	String tennhanvien = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tennhanvien")));
		if (tennhanvien == null) tennhanvien = ""; else tennhanvien = tennhanvien.trim();
    	khnvBean.setTenNhanVien(tennhanvien);
    	
    	String thang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thang")));
		if (thang == null) thang = ""; else thang = thang.trim();
		khnvBean.setThang(thang);

		String nam = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nam")));
		if (nam == null) nam = ""; else nam = nam.trim();
		khnvBean.setNam(nam);
		
		//Lấy số ngày trong tháng
		long _nam = 0;
		try { _nam = Math.round(Double.parseDouble(nam)); } catch(Exception e) { }
		
		long _thang = 0;
		try { _thang = Math.round(Double.parseDouble(thang)); } catch(Exception e) { }
		
		Calendar mycal = new GregorianCalendar((int)_nam, (int)_thang-1, 1);
		int _songay = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);		
		
		String ngaysua = getDateTime();
    	khnvBean.setNgaysua(ngaysua);
		
		String nguoisua = userId;
		khnvBean.setNguoisua(nguoisua);
		
		String[] ghichunpps, ghichutts, ghichutbhs;
		IKeHoachNhanVienNgay[] ngays = new IKeHoachNhanVienNgay[_songay];
		IKeHoachNhanVienNgay khNgay;
		IKeHoachNhanVienChiTiet chitiet;
		String[] npps, tinhs, quanhuyens, npptbhs, ddkds, tbhs;
		List<String> nppErrorList = new ArrayList<String>();
		List<String> tinhErrorList = new ArrayList<String>();
		List<String> quanErrorList = new ArrayList<String>();
		for(int i = 0; i < _songay; i++) 
		{
			khNgay = new KeHoachNhanVienNgay("");
			khNgay.setNgay(String.valueOf(i+1));
		
			ngays[i] = khNgay;
			
			//Insert chi tiết			
			npps = request.getParameterValues("ngay"+khNgay.getNgay() + "_npp"); if(npps == null) npps = new String[0];
			ghichunpps = request.getParameterValues("ngay"+khNgay.getNgay() + "_nppghichu"); if(ghichunpps == null) ghichunpps = new String[0];
			tinhs = request.getParameterValues("ngay"+khNgay.getNgay() + "_tinhthanh"); if(tinhs == null) tinhs = new String[0];
			quanhuyens = request.getParameterValues("ngay"+khNgay.getNgay() + "_quanhuyen"); if(quanhuyens == null) quanhuyens = new String[0];
			ghichutts = request.getParameterValues("ngay"+khNgay.getNgay() + "_ttghichu"); if(ghichutts == null) ghichutts = new String[0];
			
			npptbhs = request.getParameterValues("ngay"+khNgay.getNgay() + "__npp"); if(npptbhs == null) npptbhs = new String[0];
			ddkds = request.getParameterValues("ngay"+khNgay.getNgay() + "_ddkd"); if(ddkds == null) ddkds = new String[0];
			tbhs = request.getParameterValues("ngay"+khNgay.getNgay() + "_tbh"); if(tbhs == null) tbhs = new String[0];
			ghichutbhs = request.getParameterValues("ngay"+khNgay.getNgay() + "_tbhghichu"); if(ghichutbhs == null) ghichutbhs = new String[0];
			
			List<IKeHoachNhanVienChiTiet> nppList = khNgay.getNppList();
			for(int j = 0; j < npps.length; j++) {
				System.out.println("Ghi chuu = "+ ghichunpps[j] );
				if(npps[j] == null) npps[j] = "";
				if(ghichunpps[j] == null) ghichunpps[j] = "";
				chitiet = new KeHoachNhanVienChiTiet("");
				chitiet.setNppId(npps[j]);
				chitiet.setGhiChu(ghichunpps[j]);
				nppList.add(chitiet);
				
				if(npps[j].trim().length() <= 0) {
					if(nppErrorList.indexOf(khNgay.getNgay()) < 0) {
						nppErrorList.add(khNgay.getNgay());
					}
				}
			}
			
			List<IKeHoachNhanVienChiTiet> thitruongList = khNgay.getThiTruongList();
			for(int j = 0; j < tinhs.length; j++) {
				if(tinhs[j] == null) tinhs[j] = "";
				if(quanhuyens[j] == null) quanhuyens[j] = "";
				if(ghichutts[j] == null) ghichutts[j] = "";
				chitiet = new KeHoachNhanVienChiTiet("");
				chitiet.setTinhId(tinhs[j]);
				chitiet.setQuanHuyenId(quanhuyens[j]);
				chitiet.setGhiChu(ghichutts[j]);
				thitruongList.add(chitiet);

				if(tinhs[j].trim().length() <= 0) {
					if(tinhErrorList.indexOf(khNgay.getNgay()) < 0) {
						tinhErrorList.add(khNgay.getNgay());
					}
				}
				if(quanhuyens[j].trim().length() <= 0) {
					if(quanErrorList.indexOf(khNgay.getNgay()) < 0) {
						quanErrorList.add(khNgay.getNgay());
					}
				}
			}
			
			List<IKeHoachNhanVienChiTiet> tbhList = khNgay.getTBHList();
			for(int j = 0; j < npptbhs.length; j++) {
				if(npptbhs[j] == null) npptbhs[j] = "";
				if(ddkds[j] == null) ddkds[j] = "";
				if(tbhs[j] == null) tbhs[j] = "";
				if(ghichutbhs[j] == null) ghichutbhs[j] = "";
				System.out.println("ghichutbhs chuu = "+ ghichutbhs[j] );
				chitiet = new KeHoachNhanVienChiTiet("");
				chitiet.setNppTBHId(npptbhs[j]);
				chitiet.setDDKDId(ddkds[j]);
				chitiet.setTBHId(tbhs[j]);
				chitiet.setGhiChuTBH(ghichutbhs[j]);
				tbhList.add(chitiet);

			}
			
		}
		khnvBean.setNgayList(ngays);

		if (thang.trim().length()== 0) {
			khnvBean.setMessage("Vui lòng chọn tháng lập bảng kế hoạch!");
			error = true;
		} 
		else if (nam.trim().length()== 0) {
			khnvBean.setMessage("Vui lòng chọn năm lập bảng kế hoạch!");
			error = true;
		} else if (nppErrorList.size() > 0) {
			String ngayErrorStr = nppErrorList.get(0);
			for(int i = 1; i < nppErrorList.size(); i++) {
				ngayErrorStr += ", " + nppErrorList.get(i);
			}
			
			khnvBean.setMessage("Vui lòng chọn nhà phân phối ở kế hoạch ngày " + ngayErrorStr);
			error = true;
		} else if (tinhErrorList.size() > 0) {
			String ngayErrorStr = tinhErrorList.get(0);
			for(int i = 1; i < tinhErrorList.size(); i++) {
				ngayErrorStr += ", " + tinhErrorList.get(i);
			}
			
			khnvBean.setMessage("Vui lòng chọn tỉnh thành ở kế hoạch ngày " + ngayErrorStr);
			error = true;
		} else if (quanErrorList.size() > 0) {
			String ngayErrorStr = quanErrorList.get(0);
			for(int i = 1; i < quanErrorList.size(); i++) {
				ngayErrorStr += ", " + quanErrorList.get(i);
			}
			
			khnvBean.setMessage("Vui lòng chọn quận huyện ở kế hoạch ngày " + ngayErrorStr);
			error = true;
		}
 		
 		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if(!error || action.equals("excel") || action.equals("inpdf")) 
	    {
	    	if(action.equals("save"))
	    	{
	    		if ( id == null || id.trim().length() == 0) {
	    			if (!(khnvBean.create())) {
	    				khnvBean.createRs();
	    				session.setAttribute("khnvBean", khnvBean);
	    				String nextJSP = "/AHF/pages/Center/KeHoachNhanVienNew.jsp";
	    				response.sendRedirect(nextJSP);
	    			} else {
	    				khnvBean.closeDB();
	    				IKeHoachNhanVienList obj = new KeHoachNhanVienList();
	    				obj.setUserId(userId);
	    			    obj.init("");
	    				session.setAttribute("obj", obj);
						
	    				String nextJSP = "/AHF/pages/Center/KeHoachNhanVien.jsp";
	    				response.sendRedirect(nextJSP);			    			    									
	    			}
				
	    		} else {
	    			if (!(khnvBean.update())) {
	    				khnvBean.createRs();
	    				session.setAttribute("khnvBean", khnvBean);
	    				String nextJSP = "/AHF/pages/Center/KeHoachNhanVienUpdate.jsp";
	    				response.sendRedirect(nextJSP);
	    			}
	    			else {
	    				khnvBean.closeDB();
	    				IKeHoachNhanVienList obj = new KeHoachNhanVienList();
	    				obj.setUserId(userId);
	    			    obj.init("");
	    				session.setAttribute("obj", obj);
						
	    				String nextJSP = "/AHF/pages/Center/KeHoachNhanVien.jsp";
	    				response.sendRedirect(nextJSP);			    			    									
	    			}
	    		}
	    	}else if (action.equals("inpdf")){

	    		response.setContentType("application/pdf");
				response.setHeader("Content-Disposition"," inline; filename=HoaDonGTGT.pdf");
				
				Document document = new Document();
				ServletOutputStream outstream = response.getOutputStream();
				this.CreatePxk(document, outstream,  khnvBean.getId(),userId,khnvBean);
				khnvBean.createRs();
				session.setAttribute("khnvBean", khnvBean);
				String nextJSP = "/AHF/pages/Center/KeHoachNhanVienDisplay.jsp";
				response.sendRedirect(nextJSP);
 	    		
	    	} else if (action.equals("excel")){

		       OutputStream out = response.getOutputStream();
		    	response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=KeHoachNhanVien.xlsm");
				FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\KeHoachNhanVien.xlsm");
				Workbook workbook = new Workbook();
				workbook.open(fstream);
				workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
				CreateStaticData(workbook, khnvBean.getId(),userId,khnvBean);
				workbook.save(out);
				fstream.close();
				khnvBean.createRs();
				session.setAttribute("khnvBean", khnvBean);
				String nextJSP = "/AHF/pages/Center/KeHoachNhanVienDisplay.jsp";
				response.sendRedirect(nextJSP);
 	    		
	    	}else {
				khnvBean.createRs();
	    		session.setAttribute("khnvBean", khnvBean);
			
	    		String nextJSP;
	    		if (id == null){			
	    			nextJSP = "/AHF/pages/Center/KeHoachNhanVienNew.jsp";
	    		}else{
	    			nextJSP = "/AHF/pages/Center/KeHoachNhanVienUpdate.jsp";   						
	    		}
	    		response.sendRedirect(nextJSP);
			
	    	}
	    } 
	    else 
	    {
			khnvBean.createRs();
    		session.setAttribute("khnvBean", khnvBean);
		
    		String nextJSP;
    		if (id == null){			
    			nextJSP = "/AHF/pages/Center/KeHoachNhanVienNew.jsp";
    		}else{
    			nextJSP = "/AHF/pages/Center/KeHoachNhanVienUpdate.jsp";   						
    		}
    		response.sendRedirect(nextJSP);
	    	
	    }
	}
	private void CreatePxk(Document document, ServletOutputStream outstream,String  Id,String userId,IKeHoachNhanVien obj) throws IOException
	{
		try
		{		
			dbutils db =  new dbutils();
			
   			       String sql="select (select thang from kehoachnv where pk_seq="+Id+") as thang,(select nam from kehoachnv where pk_seq="+Id+") as nam,ten from nhanvien where pk_seq="+userId+"";
			       ResultSet rs=db.get(sql);
			       String ten="";
			       String thang="";
			       String nam="";
			       try {
					rs.next();
					ten=rs.getString("ten");
					thang=rs.getString("thang");
					nam=rs.getString("nam");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			   	 
			 
	 
			
			//lay doi tuong khach hang
			//chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			PdfWriter.getInstance(document, outstream);
			document.open();
			document.left(1.0f);
			//font2.setColor(BaseColor.GREEN);
			//KHAI BAO 1 BANG CO BAO NHIEU COT
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			com.itextpdf.text.Font font = new com.itextpdf.text.Font(bf, 15, com.itextpdf.text.Font.BOLD);
			com.itextpdf.text.Font fontnomar = new com.itextpdf.text.Font(bf,10,com.itextpdf.text.Font.NORMAL);
			PdfPTable tableheader = new PdfPTable(2);
			tableheader.setWidthPercentage(100);//chieu dai cua báº£ng 
			tableheader.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withsheader = {18.0f, 82.0f};//SET DO RONG CAC COT
			tableheader.setWidths(withsheader); 

			Image hinhanh = Image.getInstance( getServletContext().getInitParameter("path")+"/images/logosgp.png");
			
			//hinhanh.scalePercent(36);
			hinhanh.scalePercent(26);
			hinhanh.setAlignment(Element.ALIGN_CENTER);
			//hinhanh.setAlignment(Element.ALIGN_MIDDLE);

			PdfPCell cellslogo = new PdfPCell(hinhanh);

			cellslogo.setPadding(2);
			cellslogo.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			cellslogo.setVerticalAlignment(Element.ALIGN_MIDDLE);

		 

			tableheader.addCell(cellslogo);
			Paragraph pxk = new Paragraph("KẾ HOẠCH NHÂN VIÊN", font);
			pxk.setSpacingAfter(1);
			pxk.setAlignment(Element.ALIGN_CENTER);
			PdfPCell celltieude = new PdfPCell();
			celltieude.addElement(pxk);
			tableheader.addCell(celltieude);

			//Add bang vao document
			document.add(tableheader);		
			PdfPTable table_info = new PdfPTable(1);
			float[] with3 = {50.0f};//SET DO RONG CAC COT
			table_info.setWidthPercentage(100);
			table_info.setWidths(with3);
			PdfPCell cell111= new PdfPCell();

			cell111.addElement(new Paragraph("Nhân viên: "+obj.getTenNhanVien(),fontnomar));
			cell111.addElement(new Paragraph("Tháng : " + thang+" Năm: "+nam,fontnomar));
			cell111.addElement(new Paragraph("Ngày tạo: " + getDateTime(),fontnomar));
			cell111.addElement(new Paragraph("Người tạo: " + ten,fontnomar));

			cell111.setPaddingBottom(10);
			table_info.addCell(cell111);
			document.add(table_info);
			
			 
			
 			PdfPTable table = new PdfPTable(8);
			 
 				//Table Content
				table = new PdfPTable(4);//Chu y nhe 6 cot o day, thi xuong duoi nho set withs la 6 cot
				table.setWidthPercentage(100);//chieu dai cua báº£ng 
				table.setHorizontalAlignment(Element.ALIGN_LEFT);
 				float[] withs = {5.0f, 15.0f, 15.0f, 25.0f};
				table.setWidths(withs);
 				String[] th = new String[]{"Ngày", "Đi Nhà phân phối ", "Đi thị trường", "Đi tuyến bán hàng"};
				PdfPCell[] cell1 = new PdfPCell[8];
				for (int i = 0; i < 4 ; i++)
				{
					cell1[i] = new PdfPCell(new Paragraph(th[i],fontnomar));
					cell1[i].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell1[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
 					table.addCell(cell1[i]);			
				}
				float size = 8.5f;
				com.itextpdf.text.Font font4 = new com.itextpdf.text.Font(bf, size);
 				PdfPCell cells_detail = new PdfPCell();
			
                 obj.init(true);
			 
  				 
					for(int i=0;i<obj.getNgayList().length;i++){
			        	 String VTNPPDL="";
				    	 String VTTTDL="";
				    	 String VTWWDL="";
			        	IKeHoachNhanVienNgay khNgay = obj.getNgayList()[i];
			        	IKeHoachNhanVienChiTiet chitiet;
						List<IKeHoachNhanVienChiTiet> nppList = khNgay.getNppList();
						for(int j = 0; j < nppList.size(); j++) {
							chitiet = nppList.get(j); 
							VTNPPDL+="NPP:"+chitiet.getNppId()+"\n";
						boolean	daviengtham = chitiet.getLat() != null && chitiet.getLat().trim().length() > 0 && chitiet.getLon() != null && chitiet.getLon().trim().length() > 0;
		 					if(daviengtham){
		 						VTNPPDL+="(Đã viếng thăm)"+chitiet.getThoidiem()+"\n";
							}
							if(chitiet.getGhiChu() != null && chitiet.getGhiChu().trim().length() > 0){
								VTNPPDL+="Ghi chú: "+chitiet.getGhiChu()+"\n";
								
							}
							if(chitiet.getGhiChu2() != null && chitiet.getGhiChu2().trim().length() > 0){
								VTNPPDL+="Ghi chú thực hiện: "+chitiet.getGhiChu2()+"\n";
								
							}
							 
		 				}			
  						nppList = khNgay.getThiTruongList();
						for(int j = 0; j < nppList.size(); j++) {
							chitiet = nppList.get(j);
							boolean	daviengtham = chitiet.getLat() != null && chitiet.getLat().trim().length() > 0 && chitiet.getLon() != null && chitiet.getLon().trim().length() > 0;
							VTTTDL+="Thị trường:"+chitiet.getTinhId()+"-"+chitiet.getQuanHuyenId()+"\n" ;
							if(chitiet.getGhiChu() != null && chitiet.getGhiChu().trim().length() > 0){
								VTTTDL+="Ghi chú: "+chitiet.getGhiChu()+"\n" ;
								
							}
							if(daviengtham && chitiet.getDiaChi() != null && chitiet.getDiaChi().trim().length() > 0){
								VTTTDL+="Đã viếng thăm tại địa chỉ: "+chitiet.getDiaChi()+"\n" ;

							}
							if(chitiet.getGhiChu2() != null && chitiet.getGhiChu2().trim().length() > 0){
								VTTTDL+="Ghi chú khi thực hiện: "+chitiet.getGhiChu2()+"\n" ;
								
							}
						}
						 
 						nppList = khNgay.getTBHList();
						for(int j = 0; j < nppList.size(); j++) {
							chitiet = nppList.get(j);
							VTWWDL+="Tuyến bán hàng:"+chitiet.getNppTBHId()+"-"+chitiet.getDDKDId()+"-"+chitiet.getTBHId() +"\n"+chitiet.getGhiChuTBH()+chitiet.getTyle()+"\n";
						}
						 
 						String[] arr = new String[] { i+1+"", VTNPPDL,VTTTDL, VTWWDL};
						for (int j = 0; j < 4; j++)
						{
							cells_detail = new PdfPCell(new Paragraph(arr[j],font4));
							 
						 if(j==0){
								cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
								 
								cells_detail.setVerticalAlignment(Element.ALIGN_CENTER); 
						 }else{
								cells_detail.setHorizontalAlignment(Element.ALIGN_LEFT);
								 
								cells_detail.setVerticalAlignment(Element.ALIGN_LEFT); 
						 }
						
							table.addCell(cells_detail);
						}
 					}
						
			     
				   

			document.add(table);

 			document.close(); 
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	private void CreateStaticData(Workbook workbook,String  Id,String userId,IKeHoachNhanVien obj) 
	{

		// TODO Auto-generated method stub
		
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	    for(int k=9;k<40;k++){
	    	 cells.setRowHeight(100, k);
	    }
 	   
	    
	    Cell cell ;  
	  Utility util=new Utility();
	    dbutils db = new dbutils();	 
       String sql="select (select thang from kehoachnv where pk_seq="+Id+") as thang,(select nam from kehoachnv where pk_seq="+Id+") as nam,ten from nhanvien where pk_seq="+userId+"";
       ResultSet rs=db.get(sql);
       String ten="";
       String thang="";
       String nam="";
       try {
		rs.next();
		ten=rs.getString("ten");
		thang=rs.getString("thang");
		nam=rs.getString("nam");
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    		   cell = cells.getCell("B3");		
	         	cell.setValue(obj.getTenNhanVien());
	         	cell = cells.getCell("A4");		
	         	cell.setValue("Tháng: "+thang);
	         	cell = cells.getCell("B4");		
	         	cell.setValue("Năm: "+nam);
	         	cell = cells.getCell("B5");		
	         	cell.setValue(getDateTime());
	         	cell = cells.getCell("B6");		
	         	cell.setValue(ten);
 		
	    try
	     {
	    	obj.init(true);
	    	
	    	 int k=10;
	        for(int i=0;i<obj.getNgayList().length;i++){
	        	 String VTNPPDL="";
		    	 String VTTTDL="";
		    	 String VTWWDL="";
	        	IKeHoachNhanVienNgay khNgay = obj.getNgayList()[i];
	        	IKeHoachNhanVienChiTiet chitiet;
				List<IKeHoachNhanVienChiTiet> nppList = khNgay.getNppList();
				for(int j = 0; j < nppList.size(); j++) {
					chitiet = nppList.get(j); 
					VTNPPDL+="NPP:"+chitiet.getNppId()+"\n";
				boolean	daviengtham = chitiet.getLat() != null && chitiet.getLat().trim().length() > 0 && chitiet.getLon() != null && chitiet.getLon().trim().length() > 0;
 					if(daviengtham){
 						VTNPPDL+="(Đã viếng thăm)"+chitiet.getThoidiem()+"\n";
					}
					if(chitiet.getGhiChu() != null && chitiet.getGhiChu().trim().length() > 0){
						VTNPPDL+="Ghi chú: "+chitiet.getGhiChu()+"\n";
						
					}
					if(chitiet.getGhiChu2() != null && chitiet.getGhiChu2().trim().length() > 0){
						VTNPPDL+="Ghi chú thực hiện: "+chitiet.getGhiChu2()+"\n";
						
					}
					 
 				}			
				cell = cells.getCell("B"+k);
    				cell.setValue(VTNPPDL);  	
				nppList = khNgay.getThiTruongList();
				for(int j = 0; j < nppList.size(); j++) {
					chitiet = nppList.get(j);
					boolean	daviengtham = chitiet.getLat() != null && chitiet.getLat().trim().length() > 0 && chitiet.getLon() != null && chitiet.getLon().trim().length() > 0;
					VTTTDL+="Thị trường:"+chitiet.getTinhId()+"-"+chitiet.getQuanHuyenId()+"\n" ;
					if(chitiet.getGhiChu() != null && chitiet.getGhiChu().trim().length() > 0){
						VTTTDL+="Ghi chú: "+chitiet.getGhiChu()+"\n" ;
						
					}
					if(daviengtham && chitiet.getDiaChi() != null && chitiet.getDiaChi().trim().length() > 0){
						VTTTDL+="Đã viếng thăm tại địa chỉ: "+chitiet.getDiaChi()+"\n" ;

					}
					if(chitiet.getGhiChu2() != null && chitiet.getGhiChu2().trim().length() > 0){
						VTTTDL+="Ghi chú khi thực hiện: "+chitiet.getGhiChu2()+"\n" ;
						
					}
				}
				 
				cell = cells.getCell("C"+k);
				cell.setValue(VTTTDL); 
				nppList = khNgay.getTBHList();
				for(int j = 0; j < nppList.size(); j++) {
					chitiet = nppList.get(j);
					VTWWDL+="Tuyến bán hàng:"+chitiet.getNppTBHId()+"-"+chitiet.getDDKDId()+"-"+chitiet.getTBHId() +"\n"+chitiet.getGhiChuTBH()+chitiet.getTyle()+"\n";
				}
				 
				cell = cells.getCell("D"+k);
				cell.setValue(VTWWDL); 
				k++;
	        }
			
		 
	    			
	     }catch(Exception ex){}  finally{
		    	if(db!=null){
		    		db.shutDown();
		    	}
		    }
	    
	   
 	
		
	}
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

}
