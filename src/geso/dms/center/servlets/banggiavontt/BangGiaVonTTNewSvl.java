package geso.dms.center.servlets.banggiavontt;

import geso.dms.center.beans.banggiavontt.IBangGiaVonTT;
import geso.dms.center.beans.banggiavontt.IBangGiaVonTT_SanPham;
import geso.dms.center.beans.banggiavontt.imp.BangGiaVonTT;
import geso.dms.center.beans.banggiavontt.imp.BangGiaVonTT_SanPham;
import geso.dms.center.beans.phieunhapkhott.imp.PhieuNhapKhoTT;
import geso.dms.center.beans.phieunhapkhott.imp.PhieuNhapKhoTT_SanPham;
import geso.dms.center.util.Utility;
import geso.dms.distributor.db.sql.dbutils;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * Servlet implementation class BangGiaVonTTNewSvl
 */

public class BangGiaVonTTNewSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BangGiaVonTTNewSvl() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private String gettenuser(String userId_){
		dbutils db=new dbutils();
		String sql_getnam="select ten from nhanvien where pk_seq="+ userId_;
		ResultSet rs_tenuser=db.get(sql_getnam);
		if(rs_tenuser!= null){
			try{
			  while(rs_tenuser.next()){
				  return rs_tenuser.getString("ten");
			  }
			}catch(Exception er){
				return "";
			}
		}
		return "";
	}
    /*
	 * Save file Excel vao o dia
	 */

	public String SaveExcel(HttpServletRequest request)throws ServletException, IOException
	{
		Utility util=new Utility();
	       String idnew="";
	   	String useridnew="";
	   	String actionnew="";
	   	String usernamenew="";
	   	String dvkdidnew="";
	   	String checknew;
	   	String formnamenew="";
	   	String tennew="";
		
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
		String file = new String(dataBytes);
		//System.out.println("FILE :"+ file);
		//for saving the file name
		//Lay thong tin id kho
		String id_ = file.substring(file.indexOf("id"));
		id_ =id_.substring(id_.indexOf("\n"));//vi tri xuong dong
		id_ =id_.substring(0,id_.indexOf("--")).trim();
		idnew=id_;
		//Lay thong tin ten bang gia
		String bgten = file.substring(file.indexOf("bgTen"));
		bgten =bgten.substring(bgten.indexOf("\n"));//vi tri xuong dong
		bgten =bgten.substring(0,bgten.indexOf("--")).trim();
		tennew=bgten;
		
		//Lay thong tin khott
		String dvkdId_ = file.substring(file.indexOf("dvkdId"));
		dvkdId_ =dvkdId_.substring(dvkdId_.indexOf("\n"));//vi tri xuong dong
		dvkdId_ =dvkdId_.substring(0,dvkdId_.indexOf("--")).trim();
		dvkdidnew=dvkdId_;
		//lay thong tin ve userid
		String userId_ = file.substring(file.indexOf("userId"));
		userId_ =userId_.substring(userId_.indexOf("\n"));//vi tri xuong dong
		userId_ =userId_.substring(0,userId_.indexOf("-")).trim();
		useridnew=userId_;
		
		//lay thong tin ve action
		String action_ = file.substring(file.indexOf("action"));
		action_ =action_.substring(action_.indexOf("\n"));//vi tri xuong dong
		action_ =action_.substring(0,action_.indexOf("-")).trim();
		actionnew=action_;
		 
		//lay thong tin ve ten form
		String tenform_ = file.substring(file.indexOf("tenform"));
		tenform_ =tenform_.substring(tenform_.indexOf("\n"));//vi tri xuong dong
		tenform_ =tenform_.substring(0,tenform_.indexOf("-")).trim();
		formnamenew=tenform_;
		//lay thong tin username
		String userTen_ = file.substring(file.indexOf("userTen"));
		userTen_ =userTen_.substring(userTen_.indexOf("\n"));//vi tri xuong dong
		userTen_ =userTen_.substring(0,userTen_.indexOf("-")).trim();
		usernamenew=userTen_;
		
		//lay thong tin check (check la 1 gia tri luu dang cho nhap tay hay la import excel)
		String check_ = file.substring(file.indexOf("check"));
		check_ =check_.substring(check_.indexOf("\n"));//vi tri xuong dong
		check_ =check_.substring(0,check_.indexOf("-")).trim();
		checknew=check_;
		
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
			//kiem tra thu muc ExcelFilePhieuNhapKho co ton tai hay chua
			File checkfile =  new File("C:\\ExcelFileBangGiavonTT");

			boolean isExists = checkfile.exists();

			if(!isExists){
			   boolean bien=	 (new File("C:\\ExcelFileBangGiavonTT")).mkdir(); 
			}
		// creating a new file with the same name and writing the content in new file
			FileOutputStream fileOut = new FileOutputStream("C:\\ExcelFileBangGiavonTT\\" + getDateTime() + "-" + saveFile);
			fileOut.write(dataBytes, startPos, (endPos - startPos));
			fileOut.flush();
			fileOut.close();
			return ("C:\\ExcelFileBangGiavonTT\\" + getDateTime() + "-" + saveFile);
		}else{
			return "";
		}
		
	}
	/**
	 * Ham tra ve chuoi thoi gian theo dinh dang dd-MMM-yyyy
	 * @return
	 */
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
        
        
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Utility util=new Utility();
	       String idnew="";
	   	String useridnew="";
	   	String actionnew="";
	   	String usernamenew="";
	   	String dvkdidnew="";
	   	String checknew;
	   	String formnamenew="";
	   	String tennew="";
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");		    		 
	    HttpSession session = request.getSession();
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    String action=util.getAction(querystring);
	    session.setAttribute("userId", userId);
	    //
	    String id=util.getId(querystring);
	    session.setAttribute("userTen", gettenuser(userId));   
	    session.setAttribute("dvkdId","");
        session.setAttribute("check","0");
	    String sql_dvkd="select pk_seq,donvikinhdoanh as ten from donvikinhdoanh where trangthai!='0'";
     	dbutils db=new dbutils();
     	ResultSet rs_dvkd=db.get(sql_dvkd);
     	session.setAttribute("rs_dvkd", rs_dvkd); 
     	if(action.equals("update")){
     		IBangGiaVonTT obj = new BangGiaVonTT(id);	 
     		session.setAttribute("obj", obj);		
        	// userId is saved into session
        	String nextJSP = "/AHF/pages/Center/BangGiaVonTTUpdate.jsp";
        	response.sendRedirect(nextJSP);
     	}else if(action.equals("display")){
     		IBangGiaVonTT obj = new BangGiaVonTT(id);	 
     		session.setAttribute("obj", obj);		
        	// userId is saved into session
        	
        	String nextJSP = "/AHF/pages/Center/BangGiaVonTTDisplay.jsp";
        	response.sendRedirect(nextJSP);
     	}
     	    
	    
    	//Data object is saved into session
    	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//KHai bao mot bien loi, neu nhan dc loi thi bien loi tang len 1
		
		Utility util=new Utility();
	       String idnew="";
	   	String useridnew="";
	   	String actionnew="";
	   	String usernamenew="";
	   	String dvkdidnew="";
	   	String checknew;
	   	String formnamenew="";
	   	String tennew="";
		
		int loi=0;
		//khai bao ket noi
		  dbutils db=new dbutils();
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    String contentType = request.getContentType();   
		HttpSession session = request.getSession();
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		
		String userTen=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userTen")));
		
			String madh=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));//truong truong hop don hang sua thi moi co id,nguoc lai th� khong co
	
		String action=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));//action =new : che do them, nguoc lai la che do sua;
		if(action==null){
			action="";
		}
		//System.out.println("Action trong truong hop nay la  : " + action);
		IBangGiaVonTT ctbean=new BangGiaVonTT();
	
		ctbean.setId(madh);
		//this.out = response.getWriter();
		ctbean.setNguoisua(userId);
		String ngaysua = getDateTime();
		ctbean.setNgaysua(ngaysua);
    	String ngaytao=getDateTime();
    	ctbean.setNguoitao(userId);
    	//System.out.println("user trong truong hop nay la  : " + userId);
    	ctbean.setNgaytao(ngaytao);
    	List<IBangGiaVonTT_SanPham>  spList = new ArrayList<IBangGiaVonTT_SanPham>();
    	if(action.equals("save")||action.equals("update")){
    		
    	String[] masp = request.getParameterValues("masp");
    	String[] tensp =request.getParameterValues("tensp");
		String[] idsp = request.getParameterValues("idsp");
		String[] dvt = request.getParameterValues("dvt");
		String[] giamoi = request.getParameterValues("giamoi");
		String[] giavon=request.getParameterValues("giavon");
		String[] valuechonban = request.getParameterValues("valuechonban");
		
		  if(masp != null){					
			int m = 0;
			while(m < masp.length){
				IBangGiaVonTT_SanPham sp = new BangGiaVonTT_SanPham();
				if(masp[m] != ""){
					if(masp[m] != ""){ //chi them nhung san pham co so luong > 0
					   sp.setSanPhamID(idsp[m]);
					   sp.setMaSanPham(masp[m]);
					   sp.setTenSanPham(tensp[m]);
					   sp.setDonViTinh(dvt[m]);
					   sp.setChonBan(valuechonban[m]);
					   try{
						   sp.setGiaMoi(Double.parseDouble(giamoi[m]));
						   
					   }catch(Exception er){
						   sp.setGiaMoi(0);
					   }
					   try{
						   sp.setGiaVon(Double.parseDouble(giavon[m]));
					   }catch(Exception er){
						   
					   }
					    spList.add(sp);
					}
				}
				m++;
			}	
		  }
		 
    	}
		 String check=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("check")));
		  session.setAttribute("check",check);
		  String dvkdId=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));
		  ctbean.setDvkdId(dvkdId);
		  String bgTen=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("bgTen")));
		  ctbean.setTen(bgTen);
		  
		  String	nextJSP;
		  String tenform=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tenform")));			  		
		 // System.out.println("CONTENT TYPE : "+ contentType);
		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) {//truong hop co file dinh kem
						String filename = SaveExcel(request);
				//System.out.println("FILENAME  :"+ filename);
				spList.clear();//xoa het danh sach cu	
				if (filename.length() > 0){
					File inputWorkbook = new File(filename);
					Workbook w;
					Hashtable<String,Double> hatable=new Hashtable<String, Double>();
					try {
						w = Workbook.getWorkbook(inputWorkbook);
						Sheet sheet = w.getSheet(0);
						
						for (int i = 0; i < sheet.getRows(); i++) {			
							Cell cell0 = sheet.getCell(0, i);
							Cell cell1 = sheet.getCell(1, i);
							Cell cell2 = sheet.getCell(2, i);
							int k=0;
							
							if(cell1.getContents()!=""  &&  cell0.getContents()!=""){//khhong phai nhung don chua du lieu   
								try{ 
								hatable.put(cell0.getContents(), Double.parseDouble(cell2.getContents()));
								}catch(Exception er){
									System.out.println("BangGiaVonTTNewSvl :" + er.toString());
								}
							}
						}
						
						String sqlgetsp="select a.pk_seq,a.ten,d.donvi,ma from sanpham a inner join donvidoluong d on a.dvdl_fk=d.pk_seq where dvkd_fk="+dvkdidnew;
						ResultSet rs_getsanpham=db.get(sqlgetsp);
						if(rs_getsanpham!=null){
							while(rs_getsanpham.next()){
								 IBangGiaVonTT_SanPham sp = new BangGiaVonTT_SanPham();
								   sp.setDonViTinh(rs_getsanpham.getString("donvi"));
								   sp.setMaSanPham(rs_getsanpham.getString("ma"));
								   sp.setTenSanPham(rs_getsanpham.getString("ten"));
								   sp.setSanPhamID(rs_getsanpham.getString("pk_seq"));
								   sp.setChonBan("1");
								   if(hatable.containsKey(sp.getMaSanPham().trim())){
									   sp.setGiaMoi(hatable.get(sp.getMaSanPham()));
								   }else{
									   sp.setGiaMoi(0);
								   }
								   spList.add(sp);
								   ctbean.setListBangGia_SanPham(spList);
							}
							
						}
						ctbean.setTen(tennew);
						ctbean.setId(idnew);
						ctbean.setDvkdId(dvkdidnew);
						///ctbean.setTen(this.)
					}catch(Exception er){
					ctbean.setMessage("File dua vao bi loi, vui long kiem tra lai!");
					spList.clear();
					}
				}
				
			}
		  if(userId==null){
			  session.setAttribute("userId",useridnew);
		  }else{
			  session.setAttribute("userId",userId);  
		  }
		  if(userTen==null){
			  session.setAttribute("userTen", usernamenew);
		  }else{
			  session.setAttribute("userTen", userTen);
		  }
		  if(check==null){
			  session.setAttribute("check", "1"); 
		  }else{
			  session.setAttribute("check", check);
		  }
		  if(tenform==null){
			  tenform=formnamenew;
		  }
		  if(tenform.equals("newform")){
			  nextJSP= "/AHF/pages/Center/BangGiaVonTTNew.jsp";
		  }else{
			  nextJSP= "/AHF/pages/Center/BangGiaVonTTUpdate.jsp";
		  }		  
		  String sql_getdvkd="select pk_seq,donvikinhdoanh as ten from donvikinhdoanh where trangthai!='0'";
          ResultSet rs_dvkd=db.get(sql_getdvkd);
          session.setAttribute("rs_dvkd", rs_dvkd); 
      	ctbean.setListBangGia_SanPham(spList);//add cac chi tiet san pham  vao don hang
		  	if(action.equals("loadhang")){
		  		
		  	   String sql_getsp="select a.pk_seq,a.ten,d.donvi,ma from sanpham a inner join donvidoluong d on a.dvdl_fk=d.pk_seq where dvkd_fk='"+dvkdId+"'";
			   
		  	   ResultSet rs_getsp= db.get(sql_getsp);
			   if(rs_getsp!=null){
				   try{
					   while(rs_getsp.next()){
						   IBangGiaVonTT_SanPham sp = new BangGiaVonTT_SanPham();
						   sp.setDonViTinh(rs_getsp.getString("donvi"));
						   sp.setMaSanPham(rs_getsp.getString("ma"));
						   sp.setTenSanPham(rs_getsp.getString("ten"));
						   sp.setSanPhamID(rs_getsp.getString("pk_seq"));
						   sp.setGiaMoi(0);
						   sp.setChonBan("1");
						   spList.add(sp);
					   }
				   }catch(Exception er){
					   System.out.println("Error 291 BangGiaVonTTNewSvl : " +er.toString());
				   }
			   }
		  	}
		  	if(action.equals("save")){ // mode them moi
			  if(ctbean.SaveBangGiaVonTT()){
		          //sau khi luu xong thi thuc hien quay lai form PhieuNhapKhoTT.jsp
				  ctbean = new  BangGiaVonTT();	   
				  ctbean.setListBangGia("");
				  //Data object is saved into session   

				  session.setAttribute("dvkdid","");
		
				  nextJSP= "/AHF/pages/Center/BangGiaVonTT.jsp";
			  }
			 }else if(action.equals("update")){//truong hop update
				 if(ctbean.EditBangGiaVonTT()){		        
						  ctbean = new BangGiaVonTT();	   
						  ctbean.setListBangGia("");
						session.setAttribute("dvkdid","");
					  nextJSP= "/AHF/pages/Center/BangGiaVonTT.jsp";
					  }
			 }
		  //	System.out.println(ctbean.getMessage());
		  
		    session.setAttribute("obj", ctbean);
		   response.sendRedirect(nextJSP);
	}

}
