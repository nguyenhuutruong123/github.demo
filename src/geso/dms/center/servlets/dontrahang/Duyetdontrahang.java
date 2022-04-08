package geso.dms.center.servlets.dontrahang;

import geso.dms.center.beans.dontrahang.IDontrahang;
import geso.dms.center.beans.dontrahang.IDontrahangList;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import geso.dms.center.beans.dontrahang.imp.Dontrahang;
import geso.dms.center.beans.dontrahang.imp.DontrahangList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;





import java.io.PrintWriter;
import java.sql.ResultSet;


import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

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


public class Duyetdontrahang extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 
  
    public Duyetdontrahang() {
        super();

    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	/*	request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	*/   // PrintWriter out = response.getWriter();
	    	    
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		Utility util = (Utility) session.getAttribute("util");
		if(!util.check(userId, userTen, sum)){
			response.sendRedirect("/index.jsp");
		}else{
			session.setMaxInactiveInterval(1200);	    	    
			String querystring = request.getQueryString();
			String action = util.getAction(querystring);
			
			if (action == null){
				action = "";
			}
	    
			String id = util.getId(querystring);
			if (id == null){
				id = "";
			}
			
	    	 String printpdf=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("print")));
	  		if(printpdf==null)
	  			printpdf="";
			 if(!printpdf.equals("")){
		 			
				 IDontrahang dthBean = (IDontrahang) new Dontrahang();
				    dthBean.setUserId(userId);				    
				   	dthBean.setId(id);
				    dthBean.initUpdate();
					Document document = new Document();
				ServletOutputStream outstream = response.getOutputStream();
				try {
					this.CreateDondathangHPC(document, outstream, dthBean);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				document.close();
				return;
			}	
			
			if (action.equals("display")){				   
			    IDontrahang dthBean = (IDontrahang) new Dontrahang();
			    dthBean.setUserId(userId);				    
			   	dthBean.setId(id);
			    dthBean.initUpdate();
				    
			    String nextJSP = "/AHF/pages/Center/DonTraHangDisplay.jsp";
				    	    
				session.setAttribute("dthBean", dthBean);			
				
				response.sendRedirect(nextJSP);
			}else if(action.equals("delete")){
				IDontrahangList    	obj = new DontrahangList();
				
				obj.setUserId(userId);
				this.DeleteDth(id,obj);
				obj.createDthlist("");
				session.setAttribute("obj", obj);
				
				String nextJSP = "/AHF/pages/Center/DonTraHang.jsp";
				response.sendRedirect(nextJSP);
				
				
				
			}else {						

				IDontrahangList    	obj = new DontrahangList();
				obj.setUserId(userId);
				obj.createDthlist("");
				session.setAttribute("obj", obj);
				
				String nextJSP = "/AHF/pages/Center/DonTraHang.jsp";
				response.sendRedirect(nextJSP);
				
			}
		}

	}

	private void CreateDondathangHPC(Document document, ServletOutputStream outstream, IDontrahang ddhBean) throws IOException, SQLException
	{
		try{		
			
			ResultSet rs=ddhBean.getthongtinnpp(ddhBean.getNppId());
			while (rs.next()){
			 NumberFormat formatter = new DecimalFormat("#,###,###.###");
		    PdfWriter.getInstance(document, outstream);
			document.open();
			//chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			BaseFont bf = BaseFont.createFont("C:\\WINDOWS\\Fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 12, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.NORMAL);
			Font font3 = new Font(bf, 8, Font.NORMAL);
			 
			Paragraph ddh = new Paragraph("Đề Nghị Trả Hàng Về NCC", font);
			ddh.setSpacingAfter(15);
			ddh.setAlignment(Element.ALIGN_CENTER);
			document.add(ddh);
			
			PdfPTable tableHead = new PdfPTable(2);
			tableHead.setWidthPercentage(90);
			tableHead.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableHead.setSpacingAfter(2);
			float[] with = {24.0f, 90.0f}; //set chieu rong cac columns
			tableHead.setWidths(with);
			
			PdfPCell cell1 = new PdfPCell(new Paragraph("Tên NPP: ", font3));
			PdfPCell cell2 = new PdfPCell(new Paragraph(ddhBean.getNppTen(), font2));
			cell1.setBorder(0);
			cell2.setBorder(0);
			tableHead.addCell(cell1);
			tableHead.addCell(cell2);
		
			PdfPCell cell5 = new PdfPCell(new Paragraph("Fax Đường Biên Hòa:", font3));
			PdfPCell cell6 = new PdfPCell(new Paragraph("", font2));
			cell5.setBorder(0);
			cell6.setBorder(0);
				tableHead.addCell(cell5);
			tableHead.addCell(cell6);
			
			PdfPCell cell9 = new PdfPCell(new Paragraph("Tỉnh/ TP:", font3));
			PdfPCell cell10 = new PdfPCell(new Paragraph(rs.getString("tinh"), font2));
			cell9.setBorder(0);
			cell10.setBorder(0);
			tableHead.addCell(cell9);
			tableHead.addCell(cell10);
			PdfPCell cell11a = new PdfPCell(new Paragraph("Tel/Fax NPP:", font3));
			PdfPCell cell12a = new PdfPCell(new Paragraph(rs.getString("dienthoai")+"/"+rs.getString("fax"), font2));
			cell11a.setBorder(0);
			cell12a.setBorder(0);
			
			tableHead.addCell(cell11a);
			tableHead.addCell(cell12a);
			
			PdfPCell cell13a = new PdfPCell(new Paragraph("Ngày Trả Hàng:", font3));
			PdfPCell cell14a = new PdfPCell(new Paragraph(ddhBean.getNgayth(), font2));
			cell13a.setBorder(0);
			cell14a.setBorder(0);
			
			tableHead.addCell(cell13a);
			tableHead.addCell(cell14a);
		
			tableHead.setSpacingAfter(20.0f);
			document.add(tableHead);
			
			
			PdfPTable table = new PdfPTable(7);
			table.setWidthPercentage(100);
			float[] withs = {12.0f, 19.0f, 10.0f,  10.0f, 10.0f, 10.0f,10.0f};
	        table.setWidths(withs);
	        
	        
			String[] th = new String[]{"Mã SP", "Tên SP", "Tồn kho", "Trả hàng", "Đơn vị", "Đơn giá","T.Tiền"};
			PdfPCell[] cell = new PdfPCell[7];
			for(int i=0; i < 7 ; i++)
			{
				cell[i] = new PdfPCell(new Paragraph(th[i], font2));
				cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[i].setPadding(5);
				cell[i].setBackgroundColor(BaseColor.WHITE);
							
				table.addCell(cell[i]);
				
			}
			String[] spId = ddhBean.getSpId() ; 
			String[] masp = ddhBean.getMasp() ; 
			 String[] tensp = ddhBean.getTensp(); 
			 String[] ton = ddhBean.getTon(); 
			 String[] sl = ddhBean.getSl(); 
			 String[] tienbVAT = ddhBean.getTienbVAT(); 
			 String[] dg = ddhBean.getDongia(); 
			 String[] dv = ddhBean.getDonvi() ; 
			 int i=0;
			while(spId[i]!=null)
			 {
				System.out.println(spId[i]+"______________");
				 cell[0] = new PdfPCell(new Paragraph(masp[i], font2));		
					cell[0].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell[0].setPadding(4);
					table.addCell(cell[0]);	
					
					cell[1] = new PdfPCell(new Paragraph(tensp[i], font2));		
					cell[1].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell[1].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell[1].setPadding(4);
					table.addCell(cell[1]);	
					
					cell[2] = new PdfPCell(new Paragraph(ton[i], font2));		
					cell[2].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell[2].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell[2].setPadding(4);
					table.addCell(cell[2]);	
					
					cell[3] = new PdfPCell(new Paragraph(sl[i]+"", font2));		
					cell[3].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell[3].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell[3].setPadding(4);
					table.addCell(cell[3]);	
					
					cell[4] = new PdfPCell(new Paragraph(dv[i]+"", font2));		
					cell[4].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell[4].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell[4].setPadding(4);
					table.addCell(cell[4]);	
					
			
					
					cell[5] = new PdfPCell(new Paragraph(formatter.format(Double.parseDouble(dg[i])), font2));		
					cell[5].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell[5].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell[5].setPadding(4);
					table.addCell(cell[5]);
					
					cell[6] = new PdfPCell(new Paragraph(formatter.format(Double.parseDouble(tienbVAT[i])), font2));		
					cell[6].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell[6].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell[6].setPadding(4);
					table.addCell(cell[6]);
					i++;
			 }
			
			document.add(table);
			
			PdfPTable tablefoot = new PdfPTable(2);
			tablefoot.setWidthPercentage(90);
			tablefoot.setHorizontalAlignment(Element.ALIGN_LEFT);
			tablefoot.setSpacingBefore(10);
			
			float[] withf = {350.0f,60.0f}; //set chieu rong cac columns
			tablefoot.setWidths(withf);
			
		
			
			PdfPCell cellf1 = new PdfPCell(new Paragraph("Tổng Tiền:", font3));
			PdfPCell cellf2 = new PdfPCell(new Paragraph(formatter.format(Double.parseDouble(ddhBean.getTongtienbVAT())), font2));
			cellf1.setBorder(0);
			cellf2.setBorder(0);
			cellf1.setPaddingLeft(330);

			tablefoot.addCell(cellf1);
			tablefoot.addCell(cellf2);
		
			PdfPCell cellf3 = new PdfPCell(new Paragraph("Thuế VAT(VND):", font3));
			PdfPCell cellf4 = new PdfPCell(new Paragraph(formatter.format(Double.parseDouble(ddhBean.getVat())), font2));
			cellf3.setBorder(0);
			cellf4.setBorder(0);
			cellf3.setPaddingLeft(330);

		
			tablefoot.addCell(cellf3);
			tablefoot.addCell(cellf4);
			
			PdfPCell cellf5 = new PdfPCell(new Paragraph("Tổng Giá Trị:", font3));
			PdfPCell cellf6 = new PdfPCell(new Paragraph(formatter.format(Double.parseDouble(ddhBean.getTongtienaVAT())), font2));
			cellf5.setBorder(0);
			cellf6.setBorder(0);
			cellf5.setPaddingLeft(330);

		
			tablefoot.addCell(cellf5);
			tablefoot.addCell(cellf6);
			
			document.add(tablefoot);
			document.close();
		
		  
		}
		}
		catch(DocumentException e)
		{
			e.printStackTrace();
		}
		ddhBean.DBclose();
		
	}
	
	private boolean DeleteDth(String id,IDontrahangList obj) {
		// TODO Auto-generated method stub
		dbutils db = new dbutils();
		ResultSet rs = db.get("select count(pk_seq) as num from dontrahang where pk_seq='" + id + "' and trangthai = '1'");
		try{
			db.getConnection().setAutoCommit(false);
			rs.next();
			if(!rs.getString("num").equals("0")){
				rs.close();
				String sql="select sanpham_fk,soluong,npp_fk,kbh_fk,kho_fk from dontrahang_sp dth_sp inner join dontrahang dth "+  
							" on dth.pk_Seq=dth_sp.dontrahang_fk where dth.pk_seq='"+id+"'";
				rs=db.get(sql);
				if(rs!= null){
					while(rs.next()){
						sql="update nhapp_kho set booked = booked - " + rs.getString("soluong") + ", available = available + " + rs.getString("soluong") + " where npp_fk = '" + rs.getString("npp_fk") + "' and sanpham_fk = '" + rs.getString("sanpham_fk") + "' and kbh_fk = '" + rs.getString("kbh_fk") + "' and kho_fk='"+rs.getString("kho_fk") + "'";
						if(!db.update(sql)){
							geso.dms.center.util.Utility.rollback_throw_exception(db);
							obj.setMesage("Khong The Thu Hien Huy Don Tra Hang,Vui Long Kiem Tra Lai.Loi :"+ sql);
							return false;
						}
						
					}
				}
				rs.close();
				
				// db.update("delete from dontrahang_sp where dontrahang_fk='" + id + "'");
				
				//db.update("delete from dontrahang where pk_seq = " + id + "");
				//set trang thai don hang =3 la da xoa
				sql="update dontrahang set trangthai='3',nguoisua='"+obj.getUserId()+"' where pk_seq="+id;
				if(!db.update(sql)){
					obj.setMesage("Khong The Thu Hien Huy Don Tra Hang,Vui Long Kiem Tra Lai.Loi :"+ sql);
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					return false;
				}
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
				
				db.shutDown();
			}
		}catch(Exception e){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			obj.setMesage("Khong The Thu Hien Huy Don Tra Hang,Vui Long Kiem Tra Lai.Loi :"+ e.toString());
			return false;
		}
		return false;
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    IDontrahangList obj;     	    
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		Utility util = (Utility) session.getAttribute("util");
		if(!util.check(userId, userTen, sum)){
			response.sendRedirect("/index.jsp");
		}else{
			session.setMaxInactiveInterval(1200);	    	    
			
			String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
			
			if (action.equals("search")){				   		
				obj = new DontrahangList();
				obj.setUserId(userId);
				String search = getSearchQuery(request,obj);
				obj.createDthlist(search);
				session.setAttribute("obj", obj);
		
				String nextJSP = "/AHF/pages/Center/DonTraHang.jsp";
				response.sendRedirect(nextJSP);
			}else if (action.equals("clean"))
			{
				obj = new DontrahangList();
				obj.setUserId(userId);
				obj.createDthlist("");
				session.setAttribute("obj", obj);
				
				String nextJSP = "/AHF/pages/Center/DonTraHang.jsp";
				response.sendRedirect(nextJSP);
			}
			else{
				if (action.equals("duyet")){	
					String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));					
					IDontrahang dthBean = (IDontrahang) new Dontrahang();
				    dthBean.setUserId(userId);				    
				   	dthBean.setId(id);
				   	dthBean.DuyetDthnpp();
				}

				obj = new DontrahangList();
				obj.setUserId(userId);
				obj.createDthlist("");
				session.setAttribute("obj", obj);
				
				String nextJSP = "/AHF/pages/Center/DonTraHang.jsp";
				response.sendRedirect(nextJSP);
			}
		}

	}
	private String getSearchQuery(HttpServletRequest request, IDontrahangList obj){
//	    PrintWriter out = response.getWriter();
		
		Utility util = new Utility();
    	String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
    	if (tungay == null)
    		tungay = "";    	
    	obj.setTungay(tungay);

    	String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
    	if (denngay == null)
    		denngay = "";    	
    	obj.setDenngay(denngay);
    	   	
    	String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));   	
    	if (trangthai == null)
    		trangthai = "";    	
	   	
    	obj.setTrangthai(trangthai);
    		    
	    String query = "select distinct a.ngaytra, a.pk_seq as chungtu,e.donvikinhdoanh, a.sotienavat, b.ten as nguoitao, c.ten as nguoisua, a.trangthai from dontrahang a, nhanvien b, nhanvien c, dontrahang_sp d, donvikinhdoanh e where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.pk_seq = d.dontrahang_fk and a.dvkd_fk = e.pk_seq  "; 
    	
    	if (tungay.length()>0){
			query = query + " and a.ngaytra >= '" + tungay+ "'";
    		
    	}

    	if (denngay.length()>0){
			query = query + " and a.ngaytra <= '" + denngay+ "'";
    		
    	}

    	if(trangthai.length() > 0){
    		query = query + " and a.trangthai = '" + trangthai + "'";
    		
    	}
    	
    	
    	System.out.println("chuoi svl:"+query);
    	return query;
	}	

}
