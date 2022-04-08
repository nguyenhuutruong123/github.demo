package geso.dms.center.servlets.login;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import geso.dms.center.db.sql.*;
import geso.dms.center.servlets.count.SessionCounter;
import geso.dms.center.util.*;
import geso.dms.distributor.util.FixData;
 public class LoginSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet  {
   static final long serialVersionUID = 1L;
   public LoginSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String userId = (String)request.getSession(false).getAttribute("userId");  
    	String userTen = (String)request.getSession(false).getAttribute("userTen");
    	String sum = (String)request.getSession(false).getAttribute("sum");
    	String site = (String)request.getSession(false).getAttribute("site");
    	Utility util = (Utility)request.getSession(false).getAttribute("util");
    	if(!util.check(userId, userTen, sum)){
    		response.sendRedirect("/AHF/index.jsp");
    	}else{
    		response.sendRedirect("/AHF/ChangePassword.jsp");
    	}
	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		String userId = ""; 
		String userTen = ""; 
			
		Utility util = new Utility();
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    dbutils db = new dbutils();
		String name = util.ValidateParam(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("username"))));
		String pass = util.ValidateParam(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("password"))));
		String site="";
		 String chonchucdanh = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chonchucdanh")));
		    if(chonchucdanh == null)
		    	chonchucdanh = "";
		try{
		String qr =	"select top(1) phanloai from nhanvien where dangnhap='"+name+"'";
		ResultSet pl = db.get(qr);
		
		pl.next();
		
		site =  pl.getString("phanloai");//util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("site")));
		System.out.println("site: " + site + "; "+qr);

		
		}catch(Exception  ex){
			ex.printStackTrace();
			
		}
		String doituongId = "";
	    String login = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("login")));
	    if (login.equals("1")){
	    	System.out.println("vào kkkk000000000000");
	    	String result = createSession(request, name, pass, site); 
	    	System.out.println("KQ: "+result+chonchucdanh);
			if(chonchucdanh.equals("1"))
	    	{
	    		String npp_duocchon_id = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("congtyId"));
	    		session.setAttribute("npp_duocchon_id", npp_duocchon_id);
	    		session.setMaxInactiveInterval(240000);
	    		
	    		if(!npp_duocchon_id.equals("1")) //ko phai NPP TONG CONG TY
	    		{
	    			userId = (String)request.getSession(false).getAttribute("userId");  
	    			// Cập nhật site code của nhân viên bán hàng !
	    			String	query  = " update nhanvien set CONVSITECODE = (select top(1) SITECODE from NHAPHANPHOI where pk_seq = '"+npp_duocchon_id+"' ) where pk_seq = '"+userId+"' ";
	    			System.out.println("Update: "+query);
	    			db.update(query);
	    			
	    			// Lấy ngày khóa sổ NPP
					query = "select isnull(max(ngayks), 'Chưa có ngày khóa sổ') as ngay from khoasongay where npp_fk = "+npp_duocchon_id+" ";
								
						System.out.println("1.Khoi tao thang: " + query);
					ResultSet	 rs = db.get(query);
						
						
						if(rs != null)
						{
							try {
								while(rs.next())
								{
									session.setAttribute("ngaykhoasonpp", rs.getString("ngay"));	
								}
							} catch (SQLException e) {
							
								e.printStackTrace();
							}
							try {
								rs.close();
							} catch (SQLException e) {
							
								e.printStackTrace();
							}
						}	
						
						//  Sửa lại user ten cho riêng giám sát bán hàng
						query =  "   select nv.dangnhap,npp.LoaiNPP,npp.ten as nppten, npp.khosap, npp.pk_seq,npp.sitecode,gs.ten as tengs,npp.tructhuoc_fk  "+ 
							      "   from nhanvien nv inner join GIAMSATBANHANG gs on nv.GSBH_FK=gs.PK_SEQ  "+
							      "    inner join NHAPHANPHOI npp on npp.SITECODE=nv.CONVSITECODE  "+
							      "   where nv.pk_seq='"+userId+"' and nv.trangthai='1'  and nv.PHANLOAI=2 and gs.TRANGTHAI=1   "+
							      "   and npp.TRANGTHAI=1 ";
						rs = db.get(query);
						String ten = "";
						if(rs != null)
						{
							try {
							
								while(rs.next())
								{
									ten = rs.getString("tengs") +" - "+rs.getString("nppten");
								}
							} catch (SQLException e) {
							
								e.printStackTrace();
							}
							try {
								rs.close();
							} catch (SQLException e) {
							
								e.printStackTrace();
							}
						}	
						userTen  = ""+ten;
					
						session.setAttribute("userTen", userTen);
						session.setAttribute("sum", util.calSum(userId, userTen));
						SessionCounter.activeSessions+=1;	    			
		    			response.sendRedirect("/AHF/Distributor/mainpage.jsp");
		    			return;
					
	    		}
			
			
	    	}
	    	if(result.equals("4"))
	    	{
	    		userId = (String)request.getSession(false).getAttribute("userId");  
	    		System.out.println("Userid: "+userId);
	    		session.setAttribute("userId", userId);
	    		response.sendRedirect("/AHF/ChonCongTy.jsp");
	    		return;
	    	}
	    	if (result.equals("2"))
	    	{			
	    		if(site.equals("1")){
	    		
	    			System.out.println("SessionCounter.activeSessions "+SessionCounter.activeSessions);
	    			SessionCounter.activeSessions+=1;
	    			System.out.println("dang nhap va cong Session "+SessionCounter.activeSessions);
	    			
	    			response.sendRedirect("/AHF/Distributor/mainpage.jsp");	
	    		}else{
	    			
	    			System.out.println("SessionCounter.activeSessions "+SessionCounter.activeSessions);
	    			SessionCounter.activeSessions+=1;
	    			response.sendRedirect("/AHF/Center/mainpage.jsp");
	    		}
	    	}else{
	    		if(result.equals("1")){
	    			session.setAttribute("msg", "Chon mat khau dai tren 5 ky tu va dung de doan");
	    			response.sendRedirect("/AHF/ChangePassword.jsp");				
	    		}else{
	    			session.setAttribute("msg", "Tai khoan khong hop le hoac tai khoan da duoc dang nhap");
	    			response.sendRedirect("/AHF/index.jsp");
	    		}
	    	}
	    }else{
	    	userId = (String)request.getSession(false).getAttribute("userId");  
	    	userTen = (String)request.getSession(false).getAttribute("userTen");
	    	String sum = (String)request.getSession(false).getAttribute("sum");
	    	site = (String)request.getSession(false).getAttribute("site");
	    	util = (Utility)request.getSession(false).getAttribute("util");
	    	if(!util.check(userId, userTen, sum))
	    	{
	    		response.sendRedirect("/AHF/index.jsp");
	    	
	    	}else{ 
	    		
	    		String oldpass = util.ValidateParam(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("oldpass"))));
	    		String newpass = util.ValidateParam(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("newpass1"))));
	    		String query = "SELECT pwdcompare ('" + oldpass + "', (select matkhau from nhanvien where pk_seq='" + userId + "')) as num";
	    		
//	    		String query = "select count(pk_seq) as num from nhanvien where matkhau='" + oldpass + "' and pk_seq='" + userId + "'";
	    		ResultSet rs = db.get(query);
	    		try{
	    			rs.next();
	    			if (rs.getString("num").equals("0")){
	    				session.setAttribute("msg", "Mat khau khong hop le");
		    			response.sendRedirect("/AHF/ChangePassword.jsp");
	    			}else{
	    				if(newpass.length() > 5 & !newpass.contains("12345")){
	    					query = "update nhanvien set matkhau= pwdencrypt('" + newpass + "') , sessionId = '" + this.getdate() + "'  where pk_seq='" + userId + "'";
	    					System.out.println("_____+________"+query);
	    					db.update(query);
	    					
	    					if(site.equals("1"))
	    					{
	    						
	    						response.sendRedirect("/AHF/Distributor/mainpage.jsp");	
	    					}else
	    					{
	    						
	    						response.sendRedirect("/AHF/Center/mainpage.jsp");
	    					}
	    				}else{
	    					if (newpass.length() <= 5){
	    						session.setAttribute("msg", "Mat khau phai dai tren 5 ky tu");
	    						response.sendRedirect("/AHF/ChangePassword.jsp");	    					
	    					}else{
	    					if(newpass.contains("12345")){
	    						session.setAttribute("msg", "Mat khau qua de doan");
	    						response.sendRedirect("/AHF/ChangePassword.jsp");	    					   						
	    					}
	    					}
	    				}
	    			}
	    			rs.close();
	    			db.shutDown();
	    		}catch(Exception e){}

	    	}
	    }		
	}
			
    private String getTime()
    {
        return new Date(System.currentTimeMillis()).toString();
    }
	
    private String createSession(HttpServletRequest request, String name, String pass, String site)
    {
		Utility util = new Utility();
		dbutils db = new dbutils();
		String userId = "";
		String userTen = "";
		int trangthai=0;
		String query;
		String result;
		
		/*int i = name.indexOf("or");
		int j = pass.indexOf("or");
		
		if ((i >= 0 ) || (j >= 0))
		{
			System.out.println("i >= 0 ) || (j >= 0  ");
			return "0";
		}*/
		
		Object[] data;
		
		
		/*
		query = "SELECT pwdcompare (?, (select matkhau from nhanvien where dangnhap=?)) as num";	
		data= new Object[]   { pass,name}; 	
		ResultSet rs = db.get_v2(query, data);
		*/
		query = " select 1 as num ";
		ResultSet rs = db.get(query);
		
		try{
			if(rs.next()){
				if(rs.getString("num").equals("1")){
					rs.close();
					
					query = "select dangnhap,pk_seq, ten,trangthai from nhanvien where dangnhap=? and phanloai=?";
					if(site.equals("2"))
					{
						query+=" and trangthai='1'";
					}
					data= new Object[]   { name,site}; 	
					rs = db.get_v2(query, data);	
					if(rs.next())
					{			
						name = rs.getString("dangnhap");
						userId = rs.getString("pk_seq"); 
						userTen = rs.getString("ten");
						trangthai=rs.getInt("trangthai");
						HttpSession session = request.getSession();
						session.setAttribute("active" ,trangthai);
						System.out.println("UserId 1:" + userId);
						
						query = "insert into DangNhap_NhanVien(nhanvien_fk,ngay,PhanLoai,loai,gsbh_fk,asm_fk,bm_fk) " +
								" select pk_seq, convert(varchar(10), dbo.getlocaldate(default),120),phanloai,loai,gsbh_fk,asm_fk,bm_fk from NHANVIEN where pk_seq =  "+ userId;
						db.updateReturnInt(query);

						rs.close();				
					}else{
						userId =  "";
						userTen = "";
						System.out.println("UserId 1:" + userId);
					}
				}
			}
		
		rs.close();
		
		query = "SELECT COUNT(PK_SEQ) AS NUM FROM NHANVIEN WHERE DANGNHAP='" + name + "' " + 
				"and sessionId <=(SELECT CONVERT(VARCHAR(10),DATEADD(day,-60,dbo.GetLocalDate(DEFAULT)),120))";
		
		rs = db.get(query);
		
		rs.next();
		if ((userId.length()>0) ){
			// Kiem tra password co bi het han su dung khong?
			if (1==2){
				result = "";
			
			}else{
				rs.close();
				//Kiem tra password co bang voi username khong?
				query = "SELECT pwdcompare ('" + name + "', (select matkhau from nhanvien where dangnhap='" + name + "')) as num";
				rs = db.get(query);
				rs.next();
				
				if(rs.getString("num").equals("1")) 
					result = "1";
				else
					result = "2";
				rs.close();
			}
		}else{
			result = "0";
		}
		 query = " select pk_seq, ten, isnull(loai, '') as loai, GSBH_FK,phanloai,(select top 1 loaimenu from danhmucquyen where pk_seq in (select dmq_fk from PHANQUYEN where nhanvien_fk = nhanvien.pk_seq ))loaimenu " + 
				" from nhanvien where dangnhap='" + name + "' and phanloai='" + site + "' and trangthai='1'";	
		System.out.println(":::: LAY PHAN LOAI NV: " + query);
			rs = db.get(query);
		String loai = "";
		String doituongId = "";
		String pl = "";
		try {
			if(rs.next())
			{			
			//	userId = rs.getString("pk_seq"); 
				userTen = rs.getString("ten");
				loai = rs.getString("loai");
				String loaimn = rs.getString("loaimenu");
				pl =  rs.getString("phanloai");
				 if( loai.equals("3") && pl.equals("2") && loaimn.equals("1")) //GSBH
					doituongId = rs.getString("GSBH_FK");
				rs.close();				
			}
		} catch (SQLException e1) {
		
			e1.printStackTrace();
		}
		if(doituongId != null)
			if(doituongId.length() > 0)
			{
				query ="update NHANVIEN set ISLOGIN='1' where PK_SEQ='"+userId+"'";
				db.update(query);
				
				
				HttpSession session = request.getSession(true);
				session.setAttribute("userId", userId);
				session.setAttribute("userTen", userTen);
				session.setAttribute("sum", util.calSum(userId, userTen));
				session.setAttribute("util", util);
				session.setAttribute("site", site);			
				session.setMaxInactiveInterval(10000);
				result = "4";
			}
		
		HttpSession session = request.getSession(true);

		if(result.equals("1") || result.equals("2"))
		{
			session.setAttribute("userId", userId);	
			////Up date lai islog =1
			
			query ="update NHANVIEN set ISLOGIN='1' where PK_SEQ='"+userId+"'";
			db.update(query);
			///
			session.setAttribute("userTen", userTen);
			session.setAttribute("sum", util.calSum(userId, userTen));
			session.setAttribute("util", util);
			session.setAttribute("site", site);			
			session.setMaxInactiveInterval(10000);
			
			if(site.equals("1"))
			{
				query = "select isnull(max(ngayks), 'Chưa có ngày khóa sổ') as ngay from khoasongay where npp_fk in ( " +
						"select npp.pk_seq from nhanvien nv inner join nhaphanphoi npp on nv.convsitecode = sitecode " +
						"where nv.pk_seq='" + userId + "' )";
				ResultSet ngayks = db.get(query);
				if(ngayks.next())
				{
					session.setAttribute("ngaykhoasonpp", ngayks.getString("ngay"));
				}
				ngayks.close();
			}
		}
		if(rs != null)
			rs.close();
		
		// fix booked
		geso.dms.distributor.db.sql.dbutils dbd = new geso.dms.distributor.db.sql.dbutils();
		String nppId = util.getIdNhapp(userId);
		FixData fixed = new FixData();
		if(nppId != null)
		fixed.ProcessBOOKED(nppId, "", dbd);
		
		}catch(Exception e){
			return "0";
		}
		db.shutDown();
		System.out.println("userid trong ham: "+userId);
		return result;
    }
    
	private String getdate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}