package geso.dms.center.servlets.dangkytrungbay;

import geso.dms.center.beans.dangkytrungbay.IDangkytrungbayTT;
import geso.dms.center.beans.dangkytrungbay.IDangkytrungbayTTList;
import geso.dms.center.beans.dangkytrungbay.imp.DangkytrungbayTT;
import geso.dms.center.beans.dangkytrungbay.imp.DangkytrungbayTTList;
import geso.dms.center.util.Utility;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.oreilly.servlet.MultipartRequest;

public class DangkytrungbayTTUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	    
    public DangkytrungbayTTUpdateSvl() {
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
		}else{
		    
		    PrintWriter out; 
			
		    IDangkytrungbayTT obj;
			
			out = response.getWriter();
			Utility util = new Utility();
			
	    	String querystring = request.getQueryString();
		    userId = util.getUserId(querystring);
		    
		    out.println(userId);
		    
		    if (userId.length()==0)
		    	userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
		    String action = util.getAction(querystring);
		    String id = util.getId(querystring);  	
		    obj = new DangkytrungbayTT();
		    obj.setUserId(userId);
		    obj.setId(id);    
		    obj.init();
		    
		    session.setAttribute("obj", obj);  	
	    	if(action.equals("update"))
	    		response.sendRedirect("/AHF/pages/Center/DangKyTrungBayTTNew.jsp");
	    	else if(action.equals("dislay")){
	    		response.sendRedirect("/AHF/pages/Center/DangKyTrungbayTTDisplay.jsp");
	    	}else if(action.equals("chot")){
	    		 obj.Chot();
	    		 IDangkytrungbayTTList  obj1  = new DangkytrungbayTTList();
	    	     obj1.setUserId(userId);
	    	     obj1.init();
	             session.setAttribute("obj",obj1);
	             String nextJSP = "/AHF/pages/Center/DangKyTrungBayTT.jsp";
	             response.sendRedirect(nextJSP);
	    	}else if(action.equals("unchot")){
	    		// obj.UnChot();
	    		 IDangkytrungbayTTList  obj1  = new DangkytrungbayTTList();
	    	     obj1.setUserId(userId);
	    	     obj1.init();
	             session.setAttribute("obj",obj1);
	             String nextJSP = "/AHF/pages/Center/DangKyTrungBayTT.jsp";
	             response.sendRedirect(nextJSP);
	            
	    	}else{
	    		if(obj.Delete()){
	    			IDangkytrungbayTTList   obj1  = new DangkytrungbayTTList();
		    	    obj1.setUserId(userId);
		    	    obj1.init();
		            session.setAttribute("obj",obj1);
		            String nextJSP = "/AHF/pages/Center/DangKyTrungBayTT.jsp";
		            response.sendRedirect(nextJSP);
	    		}
	    	}
	    }
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility util = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!util.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
			PrintWriter out; 
			
			IDangkytrungbayTT obj;
			
			request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    //out = response.getWriter();
		    String contentType = request.getContentType();
		    
		    
		    obj = new DangkytrungbayTT();
		    
		    userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
		    obj.setUserId(userId);
		    
		    String cttbId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("cttbId"));
		    obj.setCttbId(cttbId);
		    
		    String nppIds[] = request.getParameterValues("nppIds");
		    obj.setNppIds(nppIds);
		    
		    String nppIdSelecteds[] = request.getParameterValues("nppIdSelecteds");
		    obj.setNppIdSelecteds(nppIdSelecteds);
		    
		    String id = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id"));
		    if(id == null)
		    	id = "";
		    obj.setId(id);
		    
		    String nvbhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nvbhId"));
		    if(nvbhId == null)
		    	nvbhId = "";
		    obj.setNvbhIds(nvbhId);
		    
		    String vungs[] = request.getParameterValues("vung");
		    obj.setVungIds(vungs);
		    
		    String khuvucs[] = request.getParameterValues("khuvuc");
		    obj.setKhuvucIds(khuvucs);
		    
		    Hashtable<String, Integer> suatdk = new Hashtable<String, Integer>();
		    Hashtable<String, Integer> suatduyet = new Hashtable<String, Integer>();
		    
		    String khachhang[] = request.getParameterValues("khIds");
		    if(khachhang != null)
		    {
		    	String kh = "";
		    	for(int i = 0; i < khachhang.length; i++ )
		    	{
		    		kh += khachhang[i] + ",";
		    		String dk = request.getParameter("suatdk_" + khachhang[i]);
		    		String duyet = request.getParameter("suatduyet_" + khachhang[i]);
		    		
		    		suatdk.put(khachhang[i], Integer.parseInt(dk.trim()));
		    		suatduyet.put(khachhang[i], Integer.parseInt(duyet.trim()));
		    	}
		    	if(kh.trim().length() > 0)
		    	{
		    		kh = kh.substring(0, kh.length() - 1);
		    		obj.setKhId(kh);
		    	}
		    }
		    System.out.println(" kh list = "+ obj.getKhId());
		    obj.setSoSuatDk(suatdk);
		    obj.setSoSuatDuyet(suatduyet);
		    
		    if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) 
			{
		    	//MultipartRequest multi = new MultipartRequest(request, "C:\\java-tomcat\\data\\", 20000000, "UTF-8");
				MultipartRequest multi = new MultipartRequest(request, getServletContext().getRealPath("\\upload\\"), 20000000, "UTF-8"); 
			    userId = util.antiSQLInspection(multi.getParameter("userId"));
			 	obj.setUserId(userId);
				obj.setCttbId(util.antiSQLInspection(multi.getParameter("cttbId"))==null? "":util.antiSQLInspection(multi.getParameter("cttbId")));	   	 
				obj.setId(util.antiSQLInspection(multi.getParameter("id"))==null? "":util.antiSQLInspection(multi.getParameter("id")));	 
				obj.setNvbhIds(util.antiSQLInspection(multi.getParameter("nvbhId"))==null? "":util.antiSQLInspection(multi.getParameter("nvbhId")));
				
			  	Enumeration files = multi.getFileNames(); 
			  	String filenameu  ="";
			  	while (files.hasMoreElements())
	            {
	                 String name = (String)files.nextElement();
	                 filenameu = multi.getFilesystemName(name); 
	                 System.out.println("name :   "+name);;
	            }
			  	boolean err = false;
			  //String filename = "C:\\java-tomcat\\data\\" + filenameu;
				String filename=    getServletContext().getRealPath("\\upload\\")+ "\\"+ filenameu;
				if(filenameu == null)
					obj.setMessage("Không có file nào được upload");
				if (filenameu != null && filename.length() > 0)
				{
					//doc file excel
					File file = new File(filename);
					System.out.println("filename  "+file);
					Workbook workbook;
					int indexRow=4;
			
					try 
					{						
						System.out.println(file);
						workbook = Workbook.getWorkbook(file);
						
						Sheet[] sheet1 = workbook.getSheets();
						Sheet sheet=sheet1[0];					 
						Cell[] cells ;
						String khachhang_fks = "";
						Hashtable<String, Integer> sosuatdk = new Hashtable<String, Integer>();
						Hashtable<String, Integer> sosuatduyet = new Hashtable<String, Integer>();
						int sokh = 0;
						String khtrung = "";
						//System.out.println("getRows = " +sheet.getRows());
						for(int i= indexRow; i < sheet.getRows();i++)
						{	
							cells = sheet.getRow(i);
							//System.out.println("cell length: " +cells.length);
							if (cells.length >= 3){
								String stt = cells[0].getContents().toString();
								String KHACHHANG_FK = cells[1].getContents().toString();
								String _suatdk = cells[4].getContents().toString();
								String _suatduyet = cells[5].getContents().toString();
								khachhang_fks += "" + KHACHHANG_FK.trim() + ",";
								if(_suatdk.trim().length() == 0)
									_suatdk = "0";
								if(_suatduyet.trim().length() == 0)
									_suatduyet = "0";
								if(sosuatdk.containsKey(KHACHHANG_FK.trim())){
									khtrung += KHACHHANG_FK + ",\n";
								}
								else
									sokh ++;
								sosuatdk.put(KHACHHANG_FK.trim(), Integer.parseInt(_suatdk));
								sosuatduyet.put(KHACHHANG_FK.trim(), Integer.parseInt(_suatduyet));
								
								//System.out.println("__ " + KHACHHANG_FK + ":" + muc_dk);
							}							
						}
						if(sokh == 0)
							obj.setMessage("Không có khách hàng nào được chọn!");
						
						if(khachhang_fks.length() > 0)
							khachhang_fks = khachhang_fks.substring(0, khachhang_fks.length() - 1);

						obj.getId_Khachhang(khachhang_fks, sosuatdk, sosuatduyet);
						if(khtrung.length() > 0)
							obj.setMessage(obj.getMessage() + "\nSố khách hàng trùng:\n" + khtrung);
						
					}catch(Exception er){
						er.printStackTrace();
						System.out.println("Exception. "+er.getMessage());
						obj.setMessage("Lỗi trong quá trình upload: " + er.getMessage());
					}
				}
		    	obj.createRs();
		    	session.setAttribute("obj", obj);  	 		
		    	response.sendRedirect("/AHF/pages/Center/DangKyTrungBayTTNew.jsp");
		    	return;
			}
		    
		    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		    //out.println(action); 
		    if(action.equals("submit"))
		    {
		    	obj.createRs();
		    	session.setAttribute("obj", obj);  	 		
		    	response.sendRedirect("/AHF/pages/Center/DangKyTrungBayTTNew.jsp");	
		    }
		    else if(action.equals("excel"))
			{
		    	obj.init();
				userTen = (String) session.getAttribute("userTen");
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=DangKyTrungbay.xlsm");
				XuatFileExcel(response, obj );
			}
		    else if(action.equals("save"))
		    {
		    	if(obj.getId().length()>0)
		    	{
		    		if(obj.edit()){
			    		IDangkytrungbayTTList objl  = new DangkytrungbayTTList();
			    		objl.setUserId(userId);
			    		objl.init();
			    		session.setAttribute("obj",objl);
			    		String nextJSP = "/AHF/pages/Center/DangKyTrungBayTT.jsp";
			    		response.sendRedirect(nextJSP);
			    	}else{
			    		//obj.init();
				    	obj.createRs();
				    	session.setAttribute("obj", obj);  	 		
			    		response.sendRedirect("/AHF/pages/Center/DangKyTrungBayTTNew.jsp");	
			    	}	
		    	}else{
		    		if(obj.save()){
		    			IDangkytrungbayTTList objl  = new DangkytrungbayTTList();
			    		objl.setUserId(userId);
			    		objl.init();
			    		session.setAttribute("obj",objl);
			    		String nextJSP = "/AHF/pages/Center/DangKyTrungBayTT.jsp";
			    		response.sendRedirect(nextJSP);
			    	}else{
			    		//obj.init();
				    	obj.createRs();
				    	session.setAttribute("obj", obj);  	 		
			    		response.sendRedirect("/AHF/pages/Center/DangKyTrungBayTTNew.jsp");	
			    	}
		    	}
		    	
		    }
		}
	}
	private void XuatFileExcel(HttpServletResponse response, IDangkytrungbayTT obj) {
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=DangKyTrungbay.xls");
		try {
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());
			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
			cellFont.setColour(Colour.BLACK);

			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

			cellFormat.setBackground(jxl.format.Colour.GRAY_25);
			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			WritableSheet sheet = w.createSheet("DangKyTB", 0);
			int cot = 0;
			sheet.addCell(new Label(0, 0, "Danh sách đăng ký trưng bày"));
			sheet.addCell(new Label(cot++, 3, "SỐ TT", cellFormat));
			sheet.addCell(new Label(cot++, 3, "MÃ KH", cellFormat));
			sheet.addCell(new Label(cot++, 3, "TÊN KH", cellFormat));
			sheet.addCell(new Label(cot++, 3, "NHÀ PHÂN PHỐI", cellFormat));
			sheet.addCell(new Label(cot++, 3, "SỐ XUẤT ĐĂNG KÝ", cellFormat));
			sheet.addCell(new Label(cot++, 3, "SỐ XUẤT DUYỆT ĐĂNG KÝ", cellFormat));
			sheet.addCell(new Label(cot++, 3, "SỐ XUẤT DUYỆT TRẢ", cellFormat));
			sheet.addCell(new Label(cot++, 3, "MÃ NVBH", cellFormat));
			sheet.addCell(new Label(cot++, 3, "TÊN NVBH", cellFormat));
			
			int j = 4;
			// set style to cell data
			WritableCellFormat cellFormat2 = new WritableCellFormat();

			cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			Label label;
			Number number;
			ResultSet rs = obj.getKhList();
			int i = 0;
			if(rs != null){
				while(rs.next()){
					cot = 0;
					i++;
					label = new Label(cot++, j,i+"" , cellFormat2);
					sheet.addCell(label);
					
					label = new Label(cot++, j, rs.getString("MAFAST"), cellFormat2);
					sheet.addCell(label);
					
					label = new Label(cot++, j, rs.getString("TEN"), cellFormat2);
					sheet.addCell(label);
					
					label = new Label(cot++, j, rs.getString("nppTen"), cellFormat2);
					sheet.addCell(label);
					
					number = new Number(cot++, j, rs.getDouble("DANGKY"), cellFormat2);
					sheet.addCell(number);
					
					number = new Number(cot++, j, rs.getDouble("SUATDUYETDK"), cellFormat2);
					sheet.addCell(number);
					
					label = new Label(cot++, j, "", cellFormat2);
					sheet.addCell(label);
					
					label = new Label(cot++, j,  rs.getString("DDKDMA"), cellFormat2);
					sheet.addCell(label);
					label = new Label(cot++, j,  rs.getString("DDKDTEN"), cellFormat2);
					sheet.addCell(label);
				
					j++;
				}
				rs.close();
			}
			
			w.write();
			w.close();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
}
