package geso.dms.center.servlets.phieunhapkhott;

import geso.dms.center.beans.chitieu.imp.ChiTieu;
import geso.dms.center.beans.chitieu.imp.ChiTieuNPP;
import geso.dms.center.beans.chitieuttchovung.imp.ChiTieuTTChoVung;
import geso.dms.center.beans.chitieuttchovung.imp.ChiTieuTTKhuVuc;
import geso.dms.center.beans.phieunhapkhott.imp.PhieuNhapKhoTT;
import geso.dms.center.beans.phieunhapkhott.imp.PhieuNhapKhoTT_SanPham;
import geso.dms.center.util.Utility;
import geso.dms.distributor.db.sql.dbutils;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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


public class PhieuNhapKhoTTNewSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PhieuNhapKhoTTNewSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String ngaynhapkhonew="";
		String idnew="";
		String useridnew="";
		String actionnew="";
		String usernamenew="";
		String khottnew="";
		String checknew;
		String formnamenew="";
		Utility util=new Utility();
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring); 
	    
		if (userId.length()==0)
		userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		String id=util.getId(querystring);
		PhieuNhapKhoTT obj = new PhieuNhapKhoTT(id);
		session.setAttribute("userId", userId);
		String tenuser=gettenuser(userId);		
		session.setAttribute("userTen",tenuser);
		dbutils db=new dbutils();
		String sql_getkhott="select pk_seq, ten from khott  where trangthai!='2' order by ten";
		
		ResultSet rs_khott=	db.get(sql_getkhott);
		session.setAttribute("rs_khott", rs_khott);
		session.setAttribute("obj",obj);
		String action = util.getAction(querystring);
		session.setAttribute("check","0");
		if(action.equals("update")){
		  	
			String nextJSP = "/AHF/pages/Center/PhieuNhapKhoTTUpDate.jsp";//default
			response.sendRedirect(nextJSP);
		}
		else if(action.equals("display")){
			String nextJSP = "/AHF/pages/Center/PhieuNhapKhoTTDisplay.jsp";//default
			response.sendRedirect(nextJSP);
		}
		else if(action.equals("chot")){
			String nextJSP = "/AHF/pages/Center/PhieuNhapKhoTTChot.jsp";//default
			response.sendRedirect(nextJSP);
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
	/*
	 * Save file Excel vao o dia
	 */

	public String SaveExcel(HttpServletRequest request)throws ServletException, IOException
	{
		
		String ngaynhapkhonew="";
		String idnew="";
		String useridnew="";
		String actionnew="";
		String usernamenew="";
		String khottnew="";
		String checknew;
		String formnamenew="";
		Utility util=new Utility();
		
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

		//Lay thong tin ngay ket thuc
		String ngaynhapkho_ = file.substring(file.indexOf("ngaynhapkho"));
		ngaynhapkho_ =ngaynhapkho_.substring(ngaynhapkho_.indexOf("\n"));//vi tri xuong dong
		ngaynhapkho_ =ngaynhapkho_.substring(0,ngaynhapkho_.indexOf("--")).trim();
		ngaynhapkhonew=ngaynhapkho_;
		//Lay thong tin id kho
		String id_ = file.substring(file.indexOf("id"));
		id_ =id_.substring(id_.indexOf("\n"));//vi tri xuong dong
		id_ =id_.substring(0,id_.indexOf("--")).trim();
		idnew=id_;
		//Lay thong tin khott
		String khott_ = file.substring(file.indexOf("khott"));
		khott_ =khott_.substring(khott_.indexOf("\n"));//vi tri xuong dong
		khott_ =khott_.substring(0,khott_.indexOf("--")).trim();
		khottnew=khott_;
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
		 
		//lay thong tin ve action
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
			File checkfile =  new File("C:\\ExcelFilePhieuNhapKhoTT");

			boolean isExists = checkfile.exists();

			if(!isExists){
			   boolean bien=	 (new File("C:\\ExcelFilePhieuNhapKhoTT")).mkdir(); 
			}
		// creating a new file with the same name and writing the content in new file
			FileOutputStream fileOut = new FileOutputStream("C:\\ExcelFilePhieuNhapKhoTT\\" + getDateTime() + "-" + saveFile);
			fileOut.write(dataBytes, startPos, (endPos - startPos));
			fileOut.flush();
			fileOut.close();
			return ("C:\\ExcelFilePhieuNhapKhoTT\\" + getDateTime() + "-" + saveFile);
		}else{
			return "";
		}
		
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
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//KHai bao mot bien loi, neu nhan dc loi thi bien loi tang len 1
		int loi=0;
		//khai bao ket noi
		
		
		String ngaynhapkhonew="";
		String idnew="";
		String useridnew="";
		String actionnew="";
		String usernamenew="";
		String khottnew="";
		String checknew;
		String formnamenew="";
		Utility util=new Utility();
		
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
		PhieuNhapKhoTT ctbean=new PhieuNhapKhoTT ();
	
		ctbean.setId(madh);
		//out = response.getWriter();
		ctbean.setNguoisua(userId);
		String ngaysua = getDateTime();
		ctbean.setNgaysua(ngaysua);
    	String ngaytao=getDateTime();
    	ctbean.setNguoitao(userId);
    	ctbean.setNgaytao(ngaytao);
		List<PhieuNhapKhoTT_SanPham>  spList = new ArrayList<PhieuNhapKhoTT_SanPham>();
		if(!action.equals("chot")){
    	String[] masp = request.getParameterValues("masp");
    	String[] tensp =request.getParameterValues("tensp");
		String[] soluong = request.getParameterValues("soluong");
		  if(masp != null){					
			int m = 0;
			while(m < masp.length){
				PhieuNhapKhoTT_SanPham sp = new PhieuNhapKhoTT_SanPham();
				if(masp[m] != ""){
					if(masp[m] != ""){ //chi them nhung san pham co so luong > 0
					   sp.setSanPhamId(masp[m]);
					   sp.setTenSanPham(tensp[m]);
					
					   try{
						   sp.setSoLuong(Integer.parseInt(soluong[m]));

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
		 //lay thong tin ve ngaynhapkho
		  String ngaynhapkho=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaynhapkho")));
		  ctbean.setNgayNhap(ngaynhapkho);
		  
		  String	nextJSP;
		  String tenform=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tenform"));	
			String khott=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khott")));
			ctbean.setKhoId(khott);
				  //truyen qua mot resultset danh sach cac kho trung tam
		    String sql_getkhott="select pk_seq,ten from khott where trangthai!='2'";
			ResultSet rs_khott= db.get(sql_getkhott);
			session.setAttribute("rs_khott",rs_khott);	
			//trong truong hop neu cap nhat file thi update se khong lay duoc
		  
		
		 // System.out.println("CONTENT TYPE : "+ contentType);
		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) {//truong hop co file dinh kem
						String filename = SaveExcel(request);
				//System.out.println("FILENAME  :"+ filename);
				spList.clear();//xoa het danh sach cu	
				if (filename.length() > 0){
					File inputWorkbook = new File(filename);
					Workbook w;
					try {
						w = Workbook.getWorkbook(inputWorkbook);
						Sheet sheet = w.getSheet(0);
					
						for (int i = 0; i < sheet.getRows(); i++) {			
							Cell cell0 = sheet.getCell(0, i);
							Cell cell1 = sheet.getCell(1, i);
							Cell cell2 = sheet.getCell(2, i);
							Cell cell3 = sheet.getCell(3, i);
							if(cell1.getContents()!=""  &&  cell1.getContents()!="" && !cell0.getContents().trim().equals("STT")){//khhong phai nhung don chua du lieu
							
								   PhieuNhapKhoTT_SanPham sp = new PhieuNhapKhoTT_SanPham();
								   sp.setSanPhamId(cell1.getContents());
								   
								   sp.setTenSanPham(cell2.getContents());
								   int soluong_=0;
								   try{
									   soluong_=Integer.parseInt(cell3.getContents());
								   }catch(Exception er){}
								    sp.setSoLuong(soluong_);
									spList.add(sp);
							}
						}
						ctbean.setId(idnew);
						ctbean.setKhoId(khottnew);
						ctbean.setNgayNhap(ngaynhapkhonew);
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
			  nextJSP= "/AHF/pages/Center/PhieuNhapKhoTTNew.jsp";
		  }else{
			  nextJSP= "/AHF/pages/Center/PhieuNhapKhoTTUpDate.jsp";
		  }
		  
		  	ctbean.setListSanPham(spList);//add cac chi tiet san pham  vao don hang
		  	if(action.equals("new")){ // mode them moi
			  if(ctbean.SavePhieuNhapKhoTT()){
		          String sql_getdvkd="select pk_seq,donvikinhdoanh as ten from donvikinhdoanh where trangthai!='2'";
		          ResultSet rs_dvkd=db.get(sql_getdvkd);
		          session.setAttribute("rs_dvkd", rs_dvkd); 
		          String sql_getsanpham="select pk_seq,ma,ten from sanpham where trangthai!='2'";
		          ResultSet rs_sanpham=db.get(sql_getsanpham);
		          session.setAttribute("rs_sanpham", rs_sanpham);
		          //sau khi luu xong thi thuc hien quay lai form PhieuNhapKhoTT.jsp
				  ctbean = new PhieuNhapKhoTT();	   
				  ctbean.setListNhapKho("");
				  //Data object is saved into session   
				  session.setAttribute("tungay", "");
				  session.setAttribute("denngay", "");
				  session.setAttribute("dvkdid","");
				  session.setAttribute("sanphamid","");
				  nextJSP= "/AHF/pages/Center/PhieuNhapKhoTT.jsp";
			  }
			 }else if(action.equals("update")){//truong hop update\
				 if(ctbean.EditPhieuNhapKhoTT()){
			          String sql_getdvkd="select pk_seq,donvikinhdoanh as ten from donvikinhdoanh where trangthai!='2'";
			          ResultSet rs_dvkd=db.get(sql_getdvkd);
			          session.setAttribute("rs_dvkd", rs_dvkd); 
			          String sql_getsanpham="select pk_seq,ma,ten from sanpham where trangthai!='2'";
			          ResultSet rs_sanpham=db.get(sql_getsanpham);
			          session.setAttribute("rs_sanpham", rs_sanpham);
					  //sau khi luu xong thi thuc hien quay lai form PhieuNhapKhoTT.jsp
						  ctbean = new PhieuNhapKhoTT();	   
						  ctbean.setListNhapKho("");
				    	//Data object is saved into session   
						session.setAttribute("tungay", "");
						session.setAttribute("denngay", "");
						session.setAttribute("dvkdid","");
						session.setAttribute("sanphamid","");
					  nextJSP= "/AHF/pages/Center/PhieuNhapKhoTT.jsp";
					  }
			 }else if(action.equals("chot")){
				 if(ctbean.ChotPhieuNhapKhoTT()){
					 ctbean.setId(madh);
					 ctbean.setListSanPhamById();
			          String sql_getdvkd="select pk_seq,donvikinhdoanh as ten from donvikinhdoanh where trangthai!='2'";
			          ResultSet rs_dvkd=db.get(sql_getdvkd);
			          session.setAttribute("rs_dvkd", rs_dvkd); 
			          String sql_getsanpham="select pk_seq,ma,ten from sanpham where trangthai!='2'";
			          ResultSet rs_sanpham=db.get(sql_getsanpham);
			          session.setAttribute("rs_sanpham", rs_sanpham);
					  //sau khi luu xong thi thuc hien quay lai form PhieuNhapKhoTT.jsp
						  ctbean = new PhieuNhapKhoTT();	   
						  ctbean.setListNhapKho("");
				    	//Data object is saved into session   
						session.setAttribute("tungay", "");
						session.setAttribute("denngay", "");
						session.setAttribute("dvkdid","");
						session.setAttribute("sanphamid","");
					  nextJSP= "/AHF/pages/Center/PhieuNhapKhoTT.jsp";
					  }
				 else{//truong hop khong chot duocC
					 String msg=ctbean.getMessage();
					 ctbean = new PhieuNhapKhoTT(madh);
					 ctbean.setMessage(msg);
					 nextJSP= "/AHF/pages/Center/PhieuNhapKhoTTChot.jsp";
				 }
			 }
		  //	System.out.println(ctbean.getMessage());
		 session.setAttribute("obj", ctbean);
		  response.sendRedirect(nextJSP);
	}
	}

