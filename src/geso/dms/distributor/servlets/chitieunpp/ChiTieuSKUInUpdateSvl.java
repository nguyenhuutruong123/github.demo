package geso.dms.distributor.servlets.chitieunpp;

import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.chitieunpp.IChitieuSKUInList;
import geso.dms.distributor.beans.chitieunpp.IChitieuSKUInTT;
import geso.dms.distributor.beans.chitieunpp.IChitieusku;
import geso.dms.distributor.beans.chitieunpp.imp.ChitieuSKUInList;
import geso.dms.distributor.beans.chitieunpp.imp.ChitieuSKUInTT;
import geso.dms.distributor.beans.chitieunpp.imp.ChitieuSku;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

public class ChiTieuSKUInUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private PrintWriter out;
    
    public ChiTieuSKUInUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IChitieuSKUInTT ctskuBean;
		HttpSession session = request.getSession();
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String action = util.getAction(querystring);
	    String id = util.getId(querystring); 
	   	 System.out.print(id);  
	    ctskuBean = new ChitieuSKUInTT(id);
	    ctskuBean.initnpp();
        session.setAttribute("obj", ctskuBean);
        String nextJSP="";
        System.out.print("action "+ action); 
        if(action.equals("xem"))
         nextJSP = "/AHF/pages/Distributor/ChiTieuSKUInDisplay.jsp";
        else  nextJSP = "/AHF/pages/Distributor/ChiTieuSKUInUpdate.jsp";
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    String contentType = request.getContentType();
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		IChitieuSKUInTT ctskuBean;
		Utility util = new Utility();
	    String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));	
	    if(id == null)
	    {  	
	    	ctskuBean = new ChitieuSKUInTT();
	    }
	    else 
	    {	
	    	ctskuBean = new ChitieuSKUInTT(id);
	    }
		String userId = (String) session.getAttribute("userId"); ;
		ctskuBean.setUserId(userId);	       
		ctskuBean.setNppIds(util.getIdNhapp(userId));
		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) 
		{	
			String filename=SaveExcel(request,ctskuBean);
			if (filename.length() > 0)
			{
				if(!readExcel(filename,userId,ctskuBean))
				{
					System.out.println("ctskuBean.getId() "+ctskuBean.getId());
					String nextJSP ="";
					if(ctskuBean.getId().length() !=0 )
					{
						nextJSP =  "/AHF/pages/Distributor/ChiTieuSKUInUpdate.jsp";
					}
					else nextJSP =  "/AHF/pages/Distributor/ChiTieuSKUInNew.jsp";
					ctskuBean.setMsg("Đọc file không thành công (file không đúng định dạng vui lòng xem lại)");
					ctskuBean.setUserId(userId);
					ctskuBean.createRs();
	    			session.setAttribute("obj", ctskuBean);	
	    			response.sendRedirect(nextJSP);
	    			return;
				}
				else
				{
					String nextJSP ="";
					System.out.println("ctskuBean.getId() "+ctskuBean.getId());
					if(ctskuBean.getId().length() !=0 )
					{
						nextJSP =  "/AHF/pages/Distributor/ChiTieuSKUInUpdate.jsp";
					}
					else nextJSP =  "/AHF/pages/Distributor/ChiTieuSKUInNew.jsp";
					int a = ctskuBean.getSpList().size();
					if(a==0)
						ctskuBean.setMsg("Không có dữ liệu");
					else ctskuBean.setMsg("Đọc file thành công");
	    			ctskuBean.setUserId(userId);
	    			session.setAttribute("obj", ctskuBean);	
	    			ctskuBean.createRs();
	    			response.sendRedirect(nextJSP);
	    			return;
				}
			}
			else
			{
				String nextJSP =  "/AHF/pages/Distributor/ChiTieuSKUInNew.jsp";
				ctskuBean.setMsg("Upload không thành công");
				ctskuBean.setUserId(userId);
    			session.setAttribute("obj", ctskuBean);				
    			response.sendRedirect(nextJSP);
			}
		}
		else
		 {
			String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
			if (diengiai == null)
				diengiai = "";
			ctskuBean.setDiengiai(diengiai);
			
			String thang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thang")));
			if (thang == null)
				thang = "";
			ctskuBean.setThang(thang);
			
			String nam = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("namxx")));
			if (nam == null)
				nam = "";
			ctskuBean.setNam(nam);
			
			String kenhbanhang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhbanhang")));
			if (kenhbanhang == null)
				kenhbanhang = "";
			ctskuBean.setKbhId(kenhbanhang);
			
			String donvikinhdoanh = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("donvikinhdoanh")));
			if (donvikinhdoanh == null)
				donvikinhdoanh = "";
			ctskuBean.setDvkdId(donvikinhdoanh);
			
			String[] masp=request.getParameterValues("masp");
			String[] tensp=request.getParameterValues("tensp");
			String[] soluong=request.getParameterValues("soluong");
			List<IChitieusku> ctList = new ArrayList<IChitieusku>();
			for(int i=0;i<masp.length;i++)
			{
				IChitieusku sp = null;
				sp = new ChitieuSku();
				
				sp.setMasanpham(masp[i]);
				sp.setTensanpham(tensp[i]);
				sp.setSoluong(soluong[i]);
				ctList.add(sp);
			}
			ctskuBean.setSpList(ctList);
		 }
		
		
 		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
 		if(action.equals("save"))
 		{
    		if (id.length()==0 )
    		{
    			if (!ctskuBean.createChiTieuSkuin())
    			{
    				ctskuBean.setUserId(userId);
    				session.setAttribute("obj", ctskuBean);  				
    				String nextJSP = "/AHF/pages/Distributor/ChiTieuSKUInNew.jsp";
    				response.sendRedirect(nextJSP);
    			}
    			else
    			{
    				System.out.println("da cap nay duoc roi");
    				IChitieuSKUInList obj = new ChitieuSKUInList();
    				obj.setUserId(userId);
    				obj.init("");
    				session.setAttribute("obj", obj);
    			    String nextJSP = "/AHF/pages/Distributor/ChiTieuSKUIn.jsp";
    				response.sendRedirect(nextJSP);	
    			}		
    		}
    		else
    		{
    			if (!(ctskuBean.updateChiTieuSkuin()))
    			{			
    				ctskuBean.setUserId(userId);
    				session.setAttribute("obj", ctskuBean);   				
    				String nextJSP = "/AHF/pages/Distributor/ChiTieuSKUInUpdate.jsp";
    				response.sendRedirect(nextJSP);
    			}
    			else
    			{
    				IChitieuSKUInList obj = new ChitieuSKUInList();
    				obj.setUserId(userId);
    				obj.init("");
    				session.setAttribute("obj", obj);
    			    String nextJSP = "/AHF/pages/Distributor/ChiTieuSKUIn.jsp";
    				response.sendRedirect(nextJSP);		
    			}
    		}
	    }
	}
	public String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	private boolean readExcel(String filename,String userId,IChitieuSKUInTT nhaphang) throws ServletException, IOException
	{

		File file = new File(filename);
		Workbook workbook;
		ResultSet rs = null;
		dbutils db = new dbutils();
		int indexRow=0;
		int m=0;
		int j=0;
		try 
		{
			db.getConnection().setAutoCommit(false);
			System.out.println(file);
			workbook = Workbook.getWorkbook(file);
			Sheet sheet = workbook.getSheet(0);	
			boolean flat=false;
			List<IChitieusku> ctList = new ArrayList<IChitieusku>();
			for(int i= indexRow + 5; i < sheet.getRows();++i)
			{
				Cell[] cells = sheet.getRow(i);
				if(cells[j].getContents().equals("Total"))
				{					
					flat = true;
				}
				if(flat)
					break;
				
				IChitieusku sp = null;
				sp = new ChitieuSku();
				
				sp.setMasanpham(cells[j].getContents());
				sp.setTensanpham(cells[j+1].getContents());
				sp.setSoluong(cells[j+2].getContents());
				ctList.add(sp);	
				j=0;
				
			}
			nhaphang.setSpList(ctList);
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);	
			return true;
		}
		catch (Exception ex) 
		{	
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
	}
	private String SaveExcel(HttpServletRequest request,IChitieuSKUInTT nhaphang)throws ServletException, IOException
	{
		
		/*String fullPath = "";
	   int maxFileSize = 5000 * 1024;
	   int maxMemSize = 5000 * 1024;*/	
	   //
	   String contentType = request.getContentType();
	   DataInputStream in = new DataInputStream(request.getInputStream());
		//we are taking the length of Content type data
		int formDataLength = request.getContentLength();
		byte dataBytes[] = new byte[formDataLength];
		int byteRead = 0;
		int totalBytesRead = 0;
		//this loop converting the uploaded file into byte code
		while (totalBytesRead < formDataLength) {
			byteRead = in.read(dataBytes, totalBytesRead, formDataLength);
			totalBytesRead += byteRead;
		}
		String file = new String(dataBytes,"UTF-8");
		String diengiai = file.substring(file.indexOf("diengiai"));//indexOf la lenh lay den vi tri
		diengiai = diengiai.substring(diengiai.indexOf("\n"));//vi tri xuong dong
		diengiai =diengiai.substring(0, diengiai.indexOf("-")).trim();
		 nhaphang.setDiengiai(diengiai);
		 System.out.println("diengiai :"+ diengiai);
		//
			String id = file.substring(file.indexOf("id"));//indexOf la lenh lay den vi tri
			id = id.substring(id.indexOf("\n"));//vi tri xuong dong
			id = id.substring(0, id.indexOf("-")).trim();
			if(id.length()!=0)
			 nhaphang.setId(id);
			 System.out.println("id :"+ id);
		 //
	   // String nppId =  geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Nppid"));
		String thang = file.substring(file.indexOf("thang"));//indexOf la lenh lay den vi tri
		thang = thang.substring(thang.indexOf("\n"));//vi tri xuong dong
		thang =thang.substring(0, thang.indexOf("-")).trim();
	    nhaphang.setThang(thang);
	    System.out.println("thang :"+ nhaphang.getThang());
	    String nam = file.substring(file.indexOf("namxx"));//indexOf la lenh lay den vi tri
	    nam = nam.substring(nam.indexOf("\n"));//vi tri xuong dong
	    nam =nam.substring(0, nam.indexOf("-")).trim();
	    
	    nhaphang.setNam(nam);
	    System.out.println("namxx :"+ nhaphang.getNam());
	   //
	    // String nppId =  geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Nppid"));
		String kenhbanhang = file.substring(file.indexOf("kenhbanhang"));//indexOf la lenh lay den vi tri
		kenhbanhang = kenhbanhang.substring(kenhbanhang.indexOf("\n"));//vi tri xuong dong
		kenhbanhang =kenhbanhang.substring(0, kenhbanhang.indexOf("-")).trim();
	    nhaphang.setKbhId(kenhbanhang);
	    System.out.println("kenhbanhang :"+ nhaphang.getKbhId());
	    String donvikinhdoanh = file.substring(file.indexOf("donvikinhdoanh"));//indexOf la lenh lay den vi tri
	    donvikinhdoanh = donvikinhdoanh.substring(donvikinhdoanh.indexOf("\n"));//vi tri xuong dong
	    donvikinhdoanh =donvikinhdoanh.substring(0, donvikinhdoanh.indexOf("-")).trim();
	    
	    nhaphang.setDvkdId(donvikinhdoanh);
	    System.out.println("donvikinhdoanh :"+ nhaphang.getDvkdId());
	   //
	    file = new String(dataBytes);
	    String saveFile = file.substring(file.indexOf("filename=\"") + 10);
		saveFile = saveFile.substring(0, saveFile.indexOf("\n"));
		saveFile = saveFile.substring(saveFile.lastIndexOf("\\") + 1,saveFile.indexOf("\""));
		int lastIndex = contentType.lastIndexOf("=");
		String boundary = contentType.substring(lastIndex + 1,contentType.length());
		int pos;
		//extracting the index of file 
		pos = file.indexOf("filename=\"");
		pos = file.indexOf("\n", pos) + 1;
		pos = file.indexOf("\n", pos) + 1;
		pos = file.indexOf("\n", pos) + 1;
		int boundaryLocation = file.indexOf(boundary, pos) - 4;
		int startPos = ((file.substring(0, pos)).getBytes()).length;
		int endPos = ((file.substring(0, boundaryLocation)).getBytes()).length;
		if(saveFile.length()>0){
		// creating a new file with the same name and writing the content in new file
			FileOutputStream fileOut = new FileOutputStream("C:\\java-tomcat\\data\\" + getDateTime() + "-" + saveFile);
			fileOut.write(dataBytes, startPos, (endPos - startPos));
			fileOut.flush();
			fileOut.close();
			return ("C:\\java-tomcat\\data\\" + getDateTime() + "-" + saveFile);
		}else{
			return "";
		}
	}
}

