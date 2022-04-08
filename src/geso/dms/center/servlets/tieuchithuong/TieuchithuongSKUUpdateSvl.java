package geso.dms.center.servlets.tieuchithuong;

import geso.dms.center.beans.dieukienkhuyenmai.ISanpham;
import geso.dms.center.beans.dieukienkhuyenmai.imp.Sanpham;
import geso.dms.center.beans.tieuchithuong.INhomsp;
import geso.dms.center.beans.tieuchithuong.INhomspDetail;
import geso.dms.center.beans.tieuchithuong.ITieuchithuongSKU;
import geso.dms.center.beans.tieuchithuong.ITieuchithuongSKUList;
import geso.dms.center.beans.tieuchithuong.imp.Nhomsp;
import geso.dms.center.beans.tieuchithuong.imp.NhomspDetail;
import geso.dms.center.beans.tieuchithuong.imp.TieuChiThuongSKU;
import geso.dms.center.beans.tieuchithuong.imp.TieuchithuongSKUList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.oreilly.servlet.MultipartRequest;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
public class TieuchithuongSKUUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
	
    public TieuchithuongSKUUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		ITieuchithuongSKU tctskuBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    	    
	    String id = util.getId(querystring);
	   
	    tctskuBean = new TieuChiThuongSKU(id);
	    tctskuBean.setId(id);
	    tctskuBean.setUserId(userId);
	    
        tctskuBean.init();
        session.setAttribute("tctskuBean", tctskuBean);
        
        String nextJSP = "/AHF/pages/Center/TieuChiThuongSKUUpdate.jsp";
        if(querystring.indexOf("display") >= 0)
        	nextJSP = "/AHF/pages/Center/TieuChiThuongSKUDisplay.jsp";
        
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		dbutils db;
		ITieuchithuongSKU tctskuBean;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		 String contentType = request.getContentType();
		this.out = response.getWriter();
		Utility util = new Utility();
		
	 
	    	
	    


		System.out.println("contentType "+contentType);
		System.out.println("contentType.indexOf(multipart/form-data;charset=utf-8)    " + contentType);
		//
		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) 
			{	
		     	MultipartRequest multi = new MultipartRequest(request, "C:\\java-tomcat\\data\\", 20000000, "UTF-8"); 
		     	 String id = util.antiSQLInspection(multi.getParameter("id"));
		  	    if(id == null){  	
		  	    	tctskuBean = new TieuChiThuongSKU("");
		  	    }else{
		  	    	tctskuBean = new TieuChiThuongSKU(id);
		  	    }
		 		String userId = util.antiSQLInspection(multi.getParameter("userId"));
				tctskuBean.setUserId(userId);
			  	Enumeration files = multi.getFileNames(); 
			  	String filenameu  ="";
			  	while (files.hasMoreElements())
                {
                     String name = (String)files.nextElement();
                    filenameu = multi.getFilesystemName(name); 
                    System.out.println("name  "+name);;
                }
			  	
			  	/////////////////////////////////////
			  	
			  	String diengiai = util.antiSQLInspection(multi.getParameter("diengiai"));
				if (diengiai == null)
					diengiai = "";
				tctskuBean.setDiengiai(diengiai);
				
				String scheme = util.antiSQLInspection(multi.getParameter("scheme"));
				if (scheme == null)
					scheme = "";
				tctskuBean.setScheme(scheme);
				
				String thang = util.antiSQLInspection(multi.getParameter("thang"));
				if (thang == null)
					thang = "";
				tctskuBean.setThang(thang);
				
				String nam = util.antiSQLInspection(multi.getParameter("nam"));
				if (nam == null)
					nam = "";
				tctskuBean.setNam(nam);
						
				String phaidat =  util.antiSQLInspection(multi.getParameter("phaidat"));
				if (phaidat == null)
					phaidat = "";
				tctskuBean.setPhaidat(phaidat);
				
				String thuong =  util.antiSQLInspection(multi.getParameter("thuong"));
				if (thuong == null)
					thuong = "";
				tctskuBean.setThuong(thuong);
				
				String thuongtoida = util.antiSQLInspection(multi.getParameter("thuongtoida"));
				if(thuongtoida == null)
					thuongtoida = "";
				tctskuBean.setThuongtoida(thuongtoida);
				
				String thuongGS =  util.antiSQLInspection(multi.getParameter("thuongGS"));
				if (thuongGS == null)
					thuongGS = "";
				tctskuBean.setThuongGS(thuongGS);
				
				String thuongtoidaGS = util.antiSQLInspection(multi.getParameter("thuongtoidaGS"));
				if(thuongtoidaGS == null)
					thuongtoidaGS = "";
				tctskuBean.setThuongtoidaGS(thuongtoidaGS);
				
		    	String[] dkkmId = multi.getParameterValues("dkkmId");
				String[] dkkmDiengiai = multi.getParameterValues("dkkmDiengiai");
				String[] dkkmTongluong = multi.getParameterValues("dkkmTongluong");
				String[] dkkmTrongso = multi.getParameterValues("dkkmTrongso");
				System.out.println("xxxxxxxxxxxx dkkmId"+dkkmId.length);
				List<INhomsp> nsplist = new ArrayList<INhomsp>();	
				for(int i = 0; i < dkkmId.length; i++)
				{
					/*if(dkkmId[i].trim().length() > 0)
					{
						Nhomsp nsp = new Nhomsp();
						nsp.setId(dkkmId[i]);
						nsp.setDiengiai(dkkmDiengiai[i]);
						nsp.setTongluong(dkkmTongluong[i]);
						nsp.setTrongso(dkkmTrongso[i]);
						
						nsplist.add(nsp);
					}
					else
					{*/
						String diengiaiD = multi.getParameter("nhomsanpham" + Integer.toString(i) + ".diengiai");
						if(diengiaiD == null)
							diengiaiD = "";
						
						System.out.println("Dien giai D: " + diengiaiD);
						
						//if(diengiaiD.trim().length() > 0)
						//{
							Nhomsp nsp = new Nhomsp();
							nsp.setId(dkkmId[i]);
							nsp.setDiengiai(dkkmDiengiai[i]);
							nsp.setTongluong(dkkmTongluong[i]);
							nsp.setTrongso(dkkmTrongso[i]);
							
							INhomspDetail nspDetail = new NhomspDetail();
							nspDetail.setDiengiai(diengiaiD);
							
							String[] masanpham = multi.getParameterValues("nhomsanpham" + Integer.toString(i) + ".masanpham");
							String[] tensanpham = multi.getParameterValues("nhomsanpham" + Integer.toString(i) + ".tensanpham");
							String[] thuongSS = multi.getParameterValues("nhomsanpham" + Integer.toString(i) + ".thuongSS");
							String[] thuongTDSS = multi.getParameterValues("nhomsanpham" + Integer.toString(i) + ".thuongTDSS");
							String[] thuongSR = multi.getParameterValues("nhomsanpham" + Integer.toString(i) + ".thuongSR");
							String[] thuongTDSR = multi.getParameterValues("nhomsanpham" + Integer.toString(i) + ".thuongTDSR");
							String[] thuongASM = multi.getParameterValues("nhomsanpham" + Integer.toString(i) + ".thuongASM");
							String[] thuongTDASM = multi.getParameterValues("nhomsanpham" + Integer.toString(i) + ".thuongTDASM");
							
							List<ISanpham> spList = new ArrayList<ISanpham>();
							if(masanpham != null)
							{
								ISanpham spDetai = null;
								for(int j = 0; j < masanpham.length; j++)
								{
									if(masanpham[j].length() > 0)
									{
										spDetai = new Sanpham();
										spDetai.setMasanpham(masanpham[j]);
										spDetai.setTensanpham(tensanpham[j]);
										spDetai.setThuongSS(thuongSS[j]);
										spDetai.setThuongTDSS(thuongTDSS[j]);
										spDetai.setThuongSR(thuongSR[j]);
										spDetai.setThuongTDSR(thuongSS[j]);
										spDetai.setThuongASM(thuongASM[j]);
										spDetai.setThuongTDASM(thuongTDASM[j]);
										
										System.out.println("___San pham: " + masanpham[j] + " -- Ten SP: " + tensanpham[j]);
										
										spList.add(spDetai);
										nspDetail.setSpList(spList);
									}
								}
							}
							
							System.out.println("1.So san pham tao moi: " + nspDetail.getSpList().size());
							nsp.setSpDetail(nspDetail);
							
							nsplist.add(nsp);
				}
				tctskuBean.setNhomspList(nsplist);	
			  	///////////////////////////////////////
			  	System.out.println("filenameu  "+filenameu);;
			  	
				String filename=    "C:\\java-tomcat\\data\\"+ filenameu;
				if (filename.length() > 0)
				{
					if(!readExcel(filename,userId,tctskuBean))
					{
						String nextJSP ="";
						System.out.println("tctskuBean.getId() :"+tctskuBean.getId());
						if(tctskuBean.getId().length()!=0)
						{
							    nextJSP =  "/AHF/pages/Center/TieuChiThuongSKUUpdate.jsp";
						}
						else nextJSP =  "/AHF/pages/Center/TieuChiThuongSKUNew.jsp";
						tctskuBean.setMsg("Đọc file không thành công");
						tctskuBean.setUserId(userId);
		    			session.setAttribute("obj", tctskuBean);	
		    			response.sendRedirect(nextJSP);
					}
					else
					{
						String nextJSP ="";
						System.out.println("tctskuBean.getId() : thanh cong"+tctskuBean.getId() );
						if(tctskuBean.getId().length()!=0)
						{

							nextJSP =  "/AHF/pages/Center/TieuChiThuongSKUUpdate.jsp";
						}
						else nextJSP =  "/AHF/pages/Center/TieuChiThuongSKUNew.jsp";
						int a = tctskuBean.getdaidienkinhdoanhimport()[0].length();
						System.out.println("tctskuBean.getdaidienkinhdoanhimport().length "+tctskuBean.getdaidienkinhdoanhimport()[0].length());
						if(a==0)
							tctskuBean.setMsg("Không có dữ liệu");
						else tctskuBean.setMsg("Đọc file thành công");
						System.out.print(tctskuBean.getMsg()+"----"+a);
						tctskuBean.setUserId(userId);
						session.setAttribute("daidienkinhdoanhses", tctskuBean.getdaidienkinhdoanhimport());
						session.setAttribute("phaidatses", tctskuBean.getPhaidatimport());
		    			session.setAttribute("tctskuBean", tctskuBean);		    			
		    			response.sendRedirect(nextJSP);
		    			return;
					}
				}
				else
				{
					String nextJSP =  "/AHF/pages/Center/TieuChiThuongSKUNew.jsp";
					tctskuBean.setMsg("Upload không thành công");
					tctskuBean.setUserId(userId);
	    			session.setAttribute("obj", tctskuBean);				
	    			response.sendRedirect(nextJSP);
				}
				return;
			}
		   String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
		    if(id == null){  	
		    	tctskuBean = new TieuChiThuongSKU("");
		    }else{
		    	tctskuBean = new TieuChiThuongSKU(id);
		    }
			   String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
				tctskuBean.setUserId(userId);
			String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
			if (diengiai == null)
				diengiai = "";
			tctskuBean.setDiengiai(diengiai);
			
			String scheme = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("scheme")));
			if (scheme == null)
				scheme = "";
			tctskuBean.setScheme(scheme);
			
			String thang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thang")));
			if (thang == null)
				thang = "";
			tctskuBean.setThang(thang);
			
			String nam = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nam")));
			if (nam == null)
				nam = "";
			tctskuBean.setNam(nam);
					
			String phaidat =  util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("phaidat")));
			if (phaidat == null)
				phaidat = "";
			tctskuBean.setPhaidat(phaidat);
			
			String thuong =  util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thuong")));
			if (thuong == null)
				thuong = "";
			tctskuBean.setThuong(thuong);
			
			String thuongtoida = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thuongtoida")));
			if(thuongtoida == null)
				thuongtoida = "";
			tctskuBean.setThuongtoida(thuongtoida);
			
			String thuongGS =  util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thuongGS")));
			if (thuongGS == null)
				thuongGS = "";
			tctskuBean.setThuongGS(thuongGS);
			
			String thuongtoidaGS = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thuongtoidaGS")));
			if(thuongtoidaGS == null)
				thuongtoidaGS = "";
			tctskuBean.setThuongtoidaGS(thuongtoidaGS);
			
	    	String[] dkkmId = request.getParameterValues("dkkmId");
			String[] dkkmDiengiai = request.getParameterValues("dkkmDiengiai");
			String[] dkkmTongluong = request.getParameterValues("dkkmTongluong");
			String[] dkkmTrongso = request.getParameterValues("dkkmTrongso");
			
			List<INhomsp> nsplist = new ArrayList<INhomsp>();	
			for(int i = 0; i < dkkmId.length; i++)
			{
				/*if(dkkmId[i].trim().length() > 0)
				{
					Nhomsp nsp = new Nhomsp();
					nsp.setId(dkkmId[i]);
					nsp.setDiengiai(dkkmDiengiai[i]);
					nsp.setTongluong(dkkmTongluong[i]);
					nsp.setTrongso(dkkmTrongso[i]);
					
					nsplist.add(nsp);
				}
				else
				{*/
					String diengiaiD = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhomsanpham" + Integer.toString(i) + ".diengiai"));
					if(diengiaiD == null)
						diengiaiD = "";
					
					System.out.println("Dien giai D: " + diengiaiD);
					
					//if(diengiaiD.trim().length() > 0)
					//{
						Nhomsp nsp = new Nhomsp();
						nsp.setId(dkkmId[i]);
						nsp.setDiengiai(dkkmDiengiai[i]);
						nsp.setTongluong(dkkmTongluong[i]);
						nsp.setTrongso(dkkmTrongso[i]);
						
						INhomspDetail nspDetail = new NhomspDetail();
						nspDetail.setDiengiai(diengiaiD);
						
						String[] masanpham = request.getParameterValues("nhomsanpham" + Integer.toString(i) + ".masanpham");
						String[] tensanpham = request.getParameterValues("nhomsanpham" + Integer.toString(i) + ".tensanpham");
						String[] thuongSS = request.getParameterValues("nhomsanpham" + Integer.toString(i) + ".thuongSS");
						String[] thuongTDSS = request.getParameterValues("nhomsanpham" + Integer.toString(i) + ".thuongTDSS");
						String[] thuongSR = request.getParameterValues("nhomsanpham" + Integer.toString(i) + ".thuongSR");
						String[] thuongTDSR = request.getParameterValues("nhomsanpham" + Integer.toString(i) + ".thuongTDSR");
						String[] thuongASM = request.getParameterValues("nhomsanpham" + Integer.toString(i) + ".thuongASM");
						String[] thuongTDASM = request.getParameterValues("nhomsanpham" + Integer.toString(i) + ".thuongTDASM");
						
						List<ISanpham> spList = new ArrayList<ISanpham>();
						if(masanpham != null)
						{
							ISanpham spDetai = null;
							for(int j = 0; j < masanpham.length; j++)
							{
								if(masanpham[j].length() > 0)
								{
									spDetai = new Sanpham();
									spDetai.setMasanpham(masanpham[j]);
									spDetai.setTensanpham(tensanpham[j]);
									spDetai.setThuongSS(thuongSS[j]);
									spDetai.setThuongTDSS(thuongTDSS[j]);
									spDetai.setThuongSR(thuongSR[j]);
									spDetai.setThuongTDSR(thuongSS[j]);
									spDetai.setThuongASM(thuongASM[j]);
									spDetai.setThuongTDASM(thuongTDASM[j]);
									
									System.out.println("___San pham: " + masanpham[j] + " -- Ten SP: " + tensanpham[j]);
									
									spList.add(spDetai);
									nspDetail.setSpList(spList);
								}
							}
						}
						
						System.out.println("1.So san pham tao moi: " + nspDetail.getSpList().size());
						nsp.setSpDetail(nspDetail);
						
						nsplist.add(nsp);
					//}
				//}
			}
			
			System.out.println("2.So nhom la: " + nsplist.size());
			tctskuBean.setNhomspList(nsplist);	
 		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
 		System.out.println("Action la: " + action);
 		
 		if(action.equals("save"))
 		{    

 			String[] a=  (String[] )session.getAttribute("daidienkinhdoanhses");
 			String[]  b= (String[] )session.getAttribute("phaidatses");

			tctskuBean.setdaidienkinhdoanhimport(a);
			tctskuBean.setPhaidatimport(b);
    		if (id == null )
    		{
    			if (!tctskuBean.createTctSKU(dkkmId, dkkmTongluong, dkkmTrongso))
    			{

    		    	tctskuBean.setUserId(userId);
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("tctskuBean", tctskuBean);
    				
    				String nextJSP = "/AHF/pages/Center/TieuChiThuongSKUNew.jsp";
    				response.sendRedirect(nextJSP);
    			}
    			else
    			{
    				session.removeAttribute("daidienkinhdoanh");
    				session.removeAttribute("phaidat");
    				ITieuchithuongSKUList obj = new TieuchithuongSKUList();
				    obj.setUserId(userId);
				    
				    obj.init("");
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/AHF/pages/Center/TieuChiThuongSKU.jsp";
					response.sendRedirect(nextJSP);
    			}	
    		}
    		else
    		{
    			if (!(tctskuBean.updateTctSKU(dkkmId, dkkmTongluong, dkkmTrongso)))
    			{			
    		    	tctskuBean.setUserId(userId);
    		    	
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("tctskuBean", tctskuBean);
    				
    				String nextJSP = "/AHF/pages/Center/TieuChiThuongSKUUpdate.jsp";
    				response.sendRedirect(nextJSP);
    			}
				else
				{
					session.removeAttribute("daidienkinhdoanh");
    				session.removeAttribute("phaidat");
					ITieuchithuongSKUList obj = new TieuchithuongSKUList();
				    obj.setUserId(userId);
				    
				    obj.init("");
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/AHF/pages/Center/TieuChiThuongSKU.jsp";
					response.sendRedirect(nextJSP);
				}
    		}
	    }
		else
		{		
			session.setAttribute("userId", userId);
			session.setAttribute("tctskuBean", tctskuBean);
			
			String nextJSP;
			if (id == null){
				nextJSP = "/AHF/pages/Center/TieuChiThuongSKUNew.jsp";
			}
			else{
				nextJSP = "/AHF/pages/Center/TieuChiThuongSKUUpdate.jsp";   						
			}
			response.sendRedirect(nextJSP);
		}
	}
	
	private boolean readExcel(String filename,String userId,ITieuchithuongSKU nhaphang) throws ServletException, IOException
	{

		File file = new File(filename);
		System.out.println("filename  "+file);
		Workbook workbook;
		ResultSet rs = null;
		dbutils db = new dbutils();
		int indexRow=0;
		int j=0;
		try 
		{
			db.getConnection().setAutoCommit(false);
			System.out.println(file);
			workbook = Workbook.getWorkbook(file);
			Sheet sheet = workbook.getSheet(0);	
			boolean flat=false;
			String[] daidienkinhdoanhs = new String[sheet.getRows()]; 
			String[] phaidats = new String[sheet.getRows()];
			int sodong =0;
			for(int i= indexRow + 4; i < sheet.getRows();++i)
			{
				Cell[] cells = sheet.getRow(i);
				if(cells[j].getContents().equals("Total"))
				{					
					flat = true;
				}
				if(flat)
					break;
				
				daidienkinhdoanhs[sodong]=(cells[j+2].getContents());
				phaidats[sodong]=(cells[j+4].getContents());
				sodong++;
				j=0;
				
			}
			for(int k=0;k<daidienkinhdoanhs.length;k++)
				System.out.println(daidienkinhdoanhs[k]);
			ServletRequest session;
			for(int k=0;k<phaidats.length;k++)
				System.out.println(phaidats[k]);
			
			nhaphang.setdaidienkinhdoanhimport(daidienkinhdoanhs);
			nhaphang.setPhaidatimport(phaidats);
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);	
			return true;
		}
		catch (Exception ex) 
		{	
			System.out.println(ex);
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
	}
/*	private String SaveExcel(HttpServletRequest request,ITieuchithuongSKU nhaphang)throws ServletException, IOException
	{
		
		String fullPath = "";
	   int maxFileSize = 5000 * 1024;
	   int maxMemSize = 5000 * 1024;	
	   //
	   String contentType = request.getContentType();
	   System.out.println("endcode  " +request.getCharacterEncoding());
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
	//////////
		String dkkmTrongso = file.substring(file.indexOf("dkkmTrongso"));//indexOf la lenh lay den vi tri
		 System.out.println("dkkmTrongsoxxx---------d---------------------------- :"+ dkkmTrongso+"------------");
		 dkkmTrongso = dkkmTrongso.substring(dkkmTrongso.indexOf("\n"));//vi tri xuong dong
		 dkkmTrongso =dkkmTrongso.substring(0, dkkmTrongso.indexOf("-")).trim();
		 System.out.println("dkkmTrongso :"+ dkkmTrongso);
		
		
		////
		String diengiai = file.substring(file.indexOf("diengiai"));//indexOf la lenh lay den vi tri
		diengiai = diengiai.substring(diengiai.indexOf("\n"));//vi tri xuong dong
		diengiai =diengiai.substring(0, diengiai.indexOf("-")).trim();
		 nhaphang.setDiengiai(diengiai);
		 System.out.println("diengiai :"+ diengiai);
		//
		 	String kiemtra = file.substring(file.indexOf("kiemtra"));//indexOf la lenh lay den vi tri
			System.out.println("id la---------"+kiemtra);
			kiemtra = kiemtra.substring(kiemtra.indexOf("\n"));//vi tri xuong dong
			kiemtra = kiemtra.substring(0, kiemtra.indexOf("-")).trim();
			 System.out.println("id dddddddddddddddddddddddd:"+ kiemtra);

			    nhaphang.setId(kiemtra);

			
			
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
	 // String nppId =  geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Nppid"));
		String scheme = file.substring(file.indexOf("scheme"));//indexOf la lenh lay den vi tri
		scheme = scheme.substring(scheme.indexOf("\n"));//vi tri xuong dong
		scheme =scheme.substring(0, scheme.indexOf("-")).trim();
	    nhaphang.setScheme(scheme);
	    System.out.println("scheme :"+ nhaphang.getScheme());
	 // String nppId =  geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Nppid"));
		String phaidat = file.substring(file.indexOf("phaidat"));//indexOf la lenh lay den vi tri
		phaidat = phaidat.substring(phaidat.indexOf("\n"));//vi tri xuong dong
		phaidat =phaidat.substring(0, phaidat.indexOf("-")).trim();
	    nhaphang.setPhaidat(phaidat);
	    System.out.println("phaidat :"+ nhaphang.getPhaidat());
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
	}*/
	public String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

}
