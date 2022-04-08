package geso.dms.distributor.servlets.dangkytrungbay;


import geso.dms.distributor.beans.dangkytrungbay.IDangkytrungbay;
import geso.dms.distributor.beans.dangkykhuyenmaitichluy.IDangkykhuyenmaitichluyList;
import geso.dms.distributor.beans.dangkykhuyenmaitichluy.imp.Dangkykhuyenmaitichluy;
import geso.dms.distributor.beans.dangkykhuyenmaitichluy.imp.DangkykhuyenmaitichluyList;
import geso.dms.distributor.beans.dangkytrungbay.IDangkytrungbay;
import geso.dms.distributor.beans.dangkytrungbay.IDangkytrungbayList;
import geso.dms.distributor.beans.dangkytrungbay.imp.Dangkytrungbay;
import geso.dms.distributor.beans.dangkytrungbay.imp.DangkytrungbayList;
import geso.dms.distributor.util.Utility;



import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.oreilly.servlet.MultipartRequest;


public class DangkytrungbayUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	    
    public DangkytrungbayUpdateSvl() {
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
			
			IDangkytrungbay obj;
			
			out = response.getWriter();
			Utility util = new Utility();
			
	    	String querystring = request.getQueryString();
		    userId = util.getUserId(querystring);
		    
		    out.println(userId);
		    
		    if (userId.length()==0)
		    	userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
		    String action = util.getAction(querystring);
		    String id = util.getId(querystring);  	
		    obj = new Dangkytrungbay();
		    obj.setUserId(userId);
		    obj.setId(id);    
		    obj.init();
		    
		    session.setAttribute("obj", obj);  	
	    	if(action.equals("update"))
	    		response.sendRedirect("/AHF/pages/Distributor/DangKyTrungBayNew.jsp");
	    	
	    	else if(action.equals("dislay")){
	    		response.sendRedirect("/AHF/pages/Distributor/DangKyTrungBayDisplay.jsp");
	    	}else if(action.equals("chot")){
	    		 obj.Chot();
	    		 DangkytrungbayList  obj1  = new DangkytrungbayList();
	    	     obj1.setUserId(userId);
	    	     obj1.init("");
	             session.setAttribute("obj",obj1);
	             String nextJSP = "/AHF/pages/Distributor/DangKyTrungBay.jsp";
	             response.sendRedirect(nextJSP);
	    	}else if(action.equals("unchot")){
	    		 obj.UnChot();
	    		 DangkytrungbayList  obj1  = new DangkytrungbayList();
	    	     obj1.setUserId(userId);
	    	     obj1.init("");
	             session.setAttribute("obj",obj1);
	             String nextJSP = "/AHF/pages/Distributor/DangKyTrungbay.jsp";
	             response.sendRedirect(nextJSP);
	            
	    	}else{
	    		if(obj.Delete()){
	    			DangkytrungbayList   obj1  = new DangkytrungbayList();
		    	    obj1.setUserId(userId);
		    	    obj1.init("");
		            session.setAttribute("obj",obj1);
		            String nextJSP = "/AHF/pages/Distributor/DangKyTrungBay.jsp";
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
			
			IDangkytrungbay obj;
			
			request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    //out = response.getWriter();
		    String contentType = request.getContentType();
		    
		    
		    obj = new Dangkytrungbay();
		    
		    userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
		    obj.setUserId(userId);
		    
		    String ctkmId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ctkmId"));
		    obj.setCtkmId(ctkmId);
		    
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
		    
		    Hashtable<String, Integer> kh_stt = new Hashtable<String, Integer>();
		    Hashtable<String, Integer> kh_mucdk = new Hashtable<String, Integer>();
		    
		    String khachhang[] = request.getParameterValues("khIds");
		    if(khachhang != null)
		    {
		    	String kh = "";
		    	for(int i = 0; i < khachhang.length; i++ )
		    	{
		    		kh += khachhang[i] + ",";
		    		String stt = request.getParameter("stt_" + khachhang[i]);
		    		kh_stt.put(khachhang[i], Integer.parseInt(stt));
		    		System.out.println(i + "-" + khachhang[i]);
		    	}
		    	if(kh.trim().length() > 0)
		    	{
		    		kh = kh.substring(0, kh.length() - 1);
		    		obj.setKhId(kh);
		    	}
		    	
		    }
		    System.out.println(" kh list = "+ obj.getKhId());
		    obj.setSTT(kh_stt);
		
		    
		    if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) 
			{
		    	//MultipartRequest multi = new MultipartRequest(request, "C:\\java-tomcat\\data\\", 20000000, "UTF-8");
				MultipartRequest multi = new MultipartRequest(request, getServletContext().getRealPath("\\upload\\"), 20000000, "UTF-8"); 
			    userId = util.antiSQLInspection(multi.getParameter("userId"));
			 	obj.setUserId(userId);
				obj.setCtkmId(util.antiSQLInspection(multi.getParameter("ctkmId"))==null? "":util.antiSQLInspection(multi.getParameter("ctkmId")));	   	 
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
						Hashtable<String, Integer> kh_st= new Hashtable<String, Integer>();
						Hashtable<String, Integer> kh_muc = new Hashtable<String, Integer>();
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

								khachhang_fks += "'" + KHACHHANG_FK.trim() + "',";
								System.out.println("fdsfsf" + KHACHHANG_FK);
								if(kh_muc.containsKey(KHACHHANG_FK.trim())){
									khtrung += KHACHHANG_FK + ",\n";
								}
								kh_st.put(KHACHHANG_FK.trim(), i - 3);
							
								
								sokh ++;
								//System.out.println("__ " + KHACHHANG_FK + ":" + muc_dk);
							}							
						}
						if(sokh == 0)
							obj.setMessage("Không có khách hàng nào được chọn!");
						
						if(khachhang_fks.length() > 0)
							khachhang_fks = khachhang_fks.substring(0, khachhang_fks.length() - 1);

						obj.getId_Khachhang(khachhang_fks, kh_st);
						obj.setMessage(obj.getMessage() + "\nSố khách hàng trùng:\n" + khtrung);
						//obj.setAction("upload");
						
					}catch(Exception er){
						er.printStackTrace();
						System.out.println("Exception. "+er.getMessage());
						obj.setMessage("Lỗi trong quá trình upload: " + er.getMessage());
					}
				}
		    	obj.createRS();
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/AHF/pages/Distributor/DangKyTrungBayNew.jsp");
		    	return;
			}
		    
		    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		    //out.println(action); 
		    if(action.equals("submit"))
		    {
		    	obj.createRS();
		    	session.setAttribute("obj", obj);  	 		
		    	response.sendRedirect("/AHF/pages/Distributor/DangKyTrungBayNew.jsp");	
		    }
		    else if(action.equals("upload")){
		    	//Export(request, response);
		    }
		    else
		    {
		    	if(obj.getId().length()>0)
		    	{
		    		if(obj.edit()){
			    		IDangkytrungbayList objl  = new DangkytrungbayList();
			    		objl.setUserId(userId);
			    		objl.init("");
			    		session.setAttribute("obj",objl);
			    		String nextJSP = "/AHF/pages/Distributor/DangKyTrungBay.jsp";
			    		response.sendRedirect(nextJSP);
			    	}else{
			    		//obj.init();
				    	obj.createRS();
				    	session.setAttribute("obj", obj);  	 		
			    		response.sendRedirect("/AHF/pages/Distributor/DangKyTrungBayUpdate.jsp");	
			    	}	
		    	}else{
		    		if(obj.save()){
			    		IDangkytrungbayList objl  = new DangkytrungbayList();
			    		objl.setUserId(userId);
			    		objl.init("");
			    		session.setAttribute("obj",objl);
			    		String nextJSP = "/AHF/pages/Distributor/DangKyTrungBay.jsp";
			    		response.sendRedirect(nextJSP);
			    	}else{
			    		//obj.init();
				    	obj.createRS();
				    	session.setAttribute("obj", obj);  	 		
			    		response.sendRedirect("/AHF/pages/Distributor/DangKyTrungBayNew.jsp");	
			    	}
		    	}
		    	
		    }
		    
	
		}
	}

	private void Export(HttpServletRequest request, HttpServletResponse response) {
		OutputStream out = null;
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=Export.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());
			WritableSheet sheet = w.createSheet("Sheet1", 0);
			
			WritableCellFormat cellFormat = new WritableCellFormat();

			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			sheet.addCell(new Label(0, 3, "STT", cellFormat));
			sheet.addCell(new Label(1, 3, "MÃ KH", cellFormat));
			sheet.addCell(new Label(2, 3, "MỨC ĐK", cellFormat));
			int stt = 4;
			String khachhang[] = request.getParameterValues("smartid");
		    if(khachhang != null)
		    {
		    	String kh = "";
		    	for(int i = 0; i < khachhang.length; i++ )
		    	{
		    		kh += khachhang[i] + ",";
		    		String stt2 = request.getParameter("stt2_" + khachhang[i]);
		    		String mkd2 = request.getParameter("mdk2_" + khachhang[i]);
		    		sheet.addCell(new Label(0, stt, stt2, cellFormat));
					sheet.addCell(new Label(1, stt, khachhang[i], cellFormat));
					sheet.addCell(new Label(2, stt, mkd2, cellFormat));
					stt++;
		    	}
		    }
			w.write();
			w.close();
		} catch (Exception e)
		{
			System.out.println("Error Cac Ban : " + e.getMessage());
			e.printStackTrace();
		} finally
		{
			if (out != null)
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

}
