package geso.dms.center.servlets.kehoachnhanvien;

import geso.dms.center.beans.kehoachnhanvien.*;
import geso.dms.center.beans.kehoachnhanvien.imp.*;
import geso.dms.center.util.Utility;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;


public class KeHoachNhanVienSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
	
    public KeHoachNhanVienSvl()
    {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IKeHoachNhanVienList obj;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    
	    obj = new KeHoachNhanVienList();
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	    obj.setUserId(userId);
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    String param="";
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
			return;
		}
		if( Utility.CheckRuleUser( session,  request, response, "KeHoachNhanVienSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
			return;
		}
	    String khnvId = util.getId(querystring);

	    if (action.equals("delete")) {
	    	obj.delete(khnvId);
	    } else if(action.equals("duyet")) {
	    	obj.duyet(khnvId);
	    } else if(action.equals("moduyet")) {
	    	obj.moduyet(khnvId);
	    }
	    obj.setThang(getDateTime().substring(5, 7));
	    obj.setNam(getDateTime().substring(0, 4));
	    //obj.init("");
	    String search = obj.getSearchQuery();
	    obj.init(search);
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Center/KeHoachNhanVien.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IKeHoachNhanVienList obj;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out = response.getWriter();
	    obj = new KeHoachNhanVienList();
		HttpSession session = request.getSession();
	    String userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	    obj.setUserId(userId);
	    
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    
	    String contentType = request.getContentType(); 
		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
			Upload(request, response);
			return ;
		} 
		
	    
	    
	    if (action.equals("new")) {
	    	obj.closeDB();
	    	// Empty Bean for distributor
	    	IKeHoachNhanVien khnvBean = (IKeHoachNhanVien) new KeHoachNhanVien(userId, "");
	    	khnvBean.init();
	    	khnvBean.createRs();
	    	// Save Data into session
	    	session.setAttribute("khnvBean", khnvBean);
    		
    		String nextJSP = "/AHF/pages/Center/KeHoachNhanVienNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else if (action.equals("search"))
	    {
	    	String loai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Loai"));
	    	if (loai == null)
	    		loai = "";
	    	obj.setLoai(loai);
	    	String kbhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhId"));
	    	if (kbhId == null)
	    		kbhId = "";
	    	obj.setKbhId(kbhId); 
	    	String tennv = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tennv"));
	    	if (tennv == null)
	    		tennv = "";
	    	obj.setTenNhanVien(tennv); 
	    	String thang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Thang"));
	    	if (thang == null)
	    		thang = "";    	
	    	obj.setThang(thang);
	    	
	    	String nam = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Nam"));
	    	if (nam == null)
	    		nam = "";
	    	obj.setNam(nam);
	    	
	    	String vungId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")==null?"": request.getParameter("vungId"));
		    obj.setVungId(vungId);
		    
		    String khuvucId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")==null?"": request.getParameter("khuvucId"));
		    obj.setKhuvucId(khuvucId);
	    	
	    	obj.setUserId(userId);
	    	String search = obj.getSearchQuery();
	    		
	    	obj.init(search);
			
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/AHF/pages/Center/KeHoachNhanVien.jsp");	    	
	    }
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
      
        return dateFormat.format(date);
	}
	
	private String getDateTime(String pattern) 
	{
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
      
        return dateFormat.format(date);
	}

	public void Upload(HttpServletRequest request, HttpServletResponse response) throws IOException
	{ 
		Utility util = new Utility();
		HttpSession session = request.getSession();
		MultipartRequest multi = new MultipartRequest(request, "C:\\java-tomcat\\data\\", 20000000, "UTF-8");
		IKeHoachNhanVienList obj = new KeHoachNhanVienList(); 
		String userId = (String) session.getAttribute("userId"); 
		 obj.setUserId(userId); 
		
	    Enumeration files = multi.getFileNames(); 
	  	String filenameu  ="";
	  	while (files.hasMoreElements())
        {
             String name = (String)files.nextElement();
             filenameu = multi.getFilesystemName(name); 
             System.out.println("name :   "+name);
        }
	  	boolean err = false;
	  	String filename = "C:\\java-tomcat\\data\\" + filenameu;
		//String filename=    getServletContext().getRealPath("\\upload\\")+ "\\"+ filenameu;
		if(filenameu == null)
			obj.setMsg("Không có file nào được upload");
		if (filenameu != null && filename.length() > 0)
		{
			//doc file excel
			File file = new File(filename);
			System.out.println("filename  "+file);
			Workbook workbook;
			int indexRow=1;
	
			try 
			{						
				System.out.println(file);
				WorkbookSettings ws = new WorkbookSettings(); 
				ws.setEncoding("Cp1252");
				workbook = Workbook.getWorkbook(file,ws);
				
				Sheet[] sheet1 = workbook.getSheets();
				Sheet sheet=sheet1[0];					 
				Cell[] cells ; 
				int sokh = 0; 
				String data = "";
				for(int i= indexRow; i < sheet.getRows();i++)
				{	
					cells = sheet.getRow(i);   
					String dangnhap = cells[0].getContents().toString();
					if(dangnhap.trim().length()>0)
					{ 
						String ten = cells[1].getContents().toString().trim().length()>0?cells[1].getContents().toString().trim():"null";
						String ngay = cells[2].getContents().toString().trim().length()>0?cells[2].getContents().toString().trim():"null";
						String thang = cells[3].getContents().toString().trim().length()>0?cells[3].getContents().toString().trim():"null";
						String nam = cells[4].getContents().toString().trim().length()>0?cells[4].getContents().toString().trim():"null";
						String tacvu = cells[5].getContents().toString().trim().length()>0?cells[5].getContents().toString().trim():"null";
						String manpp = cells[6].getContents().toString().trim().length()>0?cells[6].getContents().toString().trim():"null";
						String tinhthanh_fk = cells[7].getContents().toString().trim().length()>0?cells[7].getContents().toString().trim():"null";
						String quanhuyen_fk = cells[9].getContents().toString().trim().length()>0?cells[9].getContents().toString().trim():"null";
						String maddkd = cells[11].getContents().toString().trim().length()>0?cells[11].getContents().toString().trim():"null";
						String ngayid = cells[13].getContents().toString().trim().length()>0?cells[13].getContents().toString().trim():"null";
						String ghichu = cells[14].getContents().toString().trim().length()>0?cells[14].getContents().toString().trim():"null";
						data += "\n select '"+dangnhap+"' as dangnhap,N'"+ten+"' as ten, '"+ngay+"' as ngay, '"+thang+"' as thang, '"+nam+"' as nam , N'"+
								tacvu+"' as tacvu, '"+manpp+"' as manpp, '"+tinhthanh_fk+"' as tinhthanh_fk, '"+quanhuyen_fk+"' as quanhuyen_fk, '"+
								maddkd+"' as maddkd, '"+ngayid+"' as ngayid, N'"+ghichu+"' as ghichu \n union all ";			
						sokh++;
					}else{
						break;
					}
				}
				if(data.trim().length()>0)
				{
					data=data.substring(0, data.length()-10);
				}
				System.out.println("_____data: "+data);
				if(sokh == 0)
				{
					obj.setMsg("Không có khách hàng nào được chọn!"); 
					obj.init(""); 
			    	session.setAttribute("obj", obj);  	
		    		session.setAttribute("userId", userId);
			    		
		    		response.sendRedirect("/AHF/pages/Center/KeHoachNhanVien.jsp");	    
					return;
				}
				if(obj.Upload(data))
				{ 
					obj.init("");
					
			    	session.setAttribute("obj", obj);  	
		    		session.setAttribute("userId", userId);
			    		
		    		response.sendRedirect("/AHF/pages/Center/KeHoachNhanVien.jsp");	    
					return;
				}
				
				
			}catch(Exception er){
				er.printStackTrace();
				System.out.println("Exception. "+er.getMessage());
				obj.setMsg("Lỗi trong quá trình upload: " + er.getMessage());
				return;
			}
			
		}
		obj.init("");
		
    	session.setAttribute("obj", obj);  	
		session.setAttribute("userId", userId);
    		
		response.sendRedirect("/AHF/pages/Center/KeHoachNhanVien.jsp");	    
	
	}
}
