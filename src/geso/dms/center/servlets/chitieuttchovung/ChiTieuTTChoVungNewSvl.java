package geso.dms.center.servlets.chitieuttchovung;

import geso.dms.center.beans.chitieu.IChiTieu;
import geso.dms.center.beans.chitieu.imp.ChiTieu;
import geso.dms.center.beans.chitieu.imp.ChiTieuNPP;
import geso.dms.center.beans.chitieuttchovung.imp.ChiTieuTTChoVung;
import geso.dms.center.beans.chitieuttchovung.imp.ChiTieuTTKhuVuc;
import geso.dms.center.beans.khuvuc.imp.KhuvucList;
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
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;


public class ChiTieuTTChoVungNewSvl extends HttpServlet {
	//Note: khi ban khai bao bienkieu private thi trong nhung phuong thuc private se khong truy cap vao duoc cac bien nay
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChiTieuTTChoVungNewSvl() {
        super();
        // TODO Auto-generated constructor stub
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int thangnew=0;
		int namnew=0;
		String ngaykethucnew="";
		String diengiainew="";
		String usernamenew="";
		String useridnew="";
		String songaylamviecnew="";
		String loaictnew="";
		String dvkdnew="";
		String KenhIDNew="";
		String pk_seq="";
		String tenformnew="";
		
		Utility util=new Utility();
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String querystring = request.getQueryString();
		//lay gia tri loai ct 
		 String tmp;
		 String loaict="";
			if (querystring != null){
		    	if (querystring.contains("&")){
		    		tmp = querystring.split("&")[2];
		    	}else{
		    		tmp = querystring;
		    	}
		    	loaict = tmp.split("=")[1];
			}
		session.setAttribute("loaichitieu",loaict);
		String userId = util.getUserId(querystring); 
		//System.out.println("Ten user:  "+ userId);
		if (userId.length()==0)
		userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		String id=util.getId(querystring);
		ChiTieuTTChoVung obj = new ChiTieuTTChoVung(id,loaict);
		int thang=obj.getThang();
		int nam=obj.getNam();
		String dvkd=obj.getDVKDID();
		String kenhid_=obj.getKenhID();
		session.setAttribute("userId", userId);
		String tenuser=gettenuser(userId);
		session.setAttribute("userTen",tenuser);
		dbutils db=new dbutils();
	
		//
		String sql_getvung="select pk_seq, ten from vung order by ten";
		ResultSet rsvung=	db.get(sql_getvung);
		session.setAttribute("rsvung", rsvung);
		session.setAttribute("obj",obj);
		String action = util.getAction(querystring);
		session.setAttribute("check", "0");
		  //truyen qua mot resultset danh sach cac donvikinhdoanh
	    String sql_getdvkd="select pk_seq,donvikinhdoanh from donvikinhdoanh where trangthai=1";
		ResultSet rs_dvkd= db.get(sql_getdvkd);
		session.setAttribute("rs_dvkd",rs_dvkd);
		if(action.equals("update")){
		  	
			String nextJSP = "/AHF/pages/Center/ChiTieuTTChoVungUpdate.jsp";//default
			response.sendRedirect(nextJSP);
		}
		else if(action.equals("display")){
			
			String sql_getchitieunpp="";
			//Phan nay dung de lay ra danh sach cac nha phan phoi da duoc phan bo chi tieu trong thang
			if(loaict.equals("0")){
				/*
			 sql_getchitieunpp="select a.nhapp_fk,a.chitieu,p.ten,p.khuvuc_fk,kv.chitieu as ctkhuvuc from Chitieu_nhapp a inner join chitieu b " + 
				" on a.chitieu_fk =b.pk_seq "+
				" inner join  nhaphanphoi p on p.pk_seq=a.nhapp_fk "+
				" LEFT JOIN (select  CTKV.CHITIEU,CTTT.THANG,CTTT.NAM,CTTT.DVKD_FK,CTTT.KENH_FK ,KHUVUC_FK from "+
				" CHITIEUTT_KHUVUC CTKV inner join CHITIEU_TRUNGTAM CTTT ON CTTT.PK_SEQ=CTKV.CHITIEU_TRUNGTAM_FK WHERE  "+
				" CTTT.THANG="+thang+" AND CTTT.NAM="+nam+" AND CTTT.DVKD_FK="+dvkd+" AND CTTT.KENH_FK ="+kenhid_+"  AND CTTT.TRANGTHAI='1') AS kv "+
				" ON KV.KHUVUC_FK=p.KHUVUC_fk AND b.thang=kv.thang and b.nam=kv.nam and b.kenh_fk=kv.kenh_fk and b.dvkd_fk=kv.dvkd_fk "+
				" where b.thang="+thang+" and b.nam="+nam+"  and b.dvkd_fk="+dvkd+" and b.trangthai='1' and b.kenh_fk="+kenhid_+" 	";
			*/
			 sql_getchitieunpp="select a.nhapp_fk,a.chitieu,p.ten,p.khuvuc_fk from Chitieu_nhapp a inner join chitieu b on a.chitieu_fk =b.pk_seq inner join " +
									 " nhaphanphoi p on p.pk_seq=a.nhapp_fk where thang="+thang+" and nam="+nam + "  and dvkd_fk="+ dvkd+" and b.trangthai!='2' and kenh_fk="+kenhid_;
			}else{
				 sql_getchitieunpp="select a.nhapp_fk,a.chitieu,a.sodonhang,a.sku,p.ten,p.khuvuc_fk from Chitieu_nhapp_sec a inner join chitieu_sec b on a.chitieu_sec_fk =b.pk_seq inner join " +
				 " nhaphanphoi p on p.pk_seq=a.nhapp_fk where thang="+thang+" and nam="+nam + "  and dvkd_fk="+ dvkd +" and b.trangthai!='2' and kenh_fk="+ kenhid_;

			}
			System.out.println("sql_getchitieunpp :" +sql_getchitieunpp);
			ChiTieu chitieu=new ChiTieu();
			List<ChiTieuNPP> listnpp=new ArrayList<ChiTieuNPP>();
			ResultSet rs=db.get(sql_getchitieunpp);
			if(rs!=null){
				try{
				while (rs.next()){
					ChiTieuNPP npp=new ChiTieuNPP();
					npp.setNhaPPId(rs.getString("nhapp_fk"));
					npp.setTenNhaPP(rs.getString("ten"));
					//double tongtien=rs.getDouble("ctkhuvuc");
					//double phantram=0;
					//if(tongtien>0){
					// phantram= rs.getDouble("chitieu")/ tongtien *100;
					//}
					//npp.setPhantram(phantram);
					npp.setSoTien(rs.getDouble("chitieu"));
					npp.setId(rs.getDouble("khuvuc_fk"));//cho nay khong phai sai dau nhe ^^,
					/* 
					 * muon tam field id de luu field khuvuc,de qua ben chitieuchovungDisplay  
					 * in danh sach cac nhapp theo khuvucid
					 */
					try{
						npp.setSoDonHang(rs.getString("sodonhang"));
						npp.setSoSku(rs.getString("sku"));
					}catch(Exception er){
						
					}
					listnpp.add(npp);
				}
				}catch(Exception er){
					
				}
			}
			chitieu.setListChiTieuNPP(listnpp);
			session.setAttribute("chitieunpp",chitieu);
			String nextJSP = "/AHF/pages/Center/ChiTieuTTChoVungDisplay.jsp";//default
			response.sendRedirect(nextJSP);
		}
		
	}

	/*
	 * Save file Excel vao o dia
	 */
	public String SaveExcel(HttpServletRequest request)throws ServletException, IOException
	{
		
		 int thangnew=0;
		 int namnew=0;
		 String ngaykethucnew="";
		  String diengiainew="";
		  String usernamenew="";
		 String useridnew="";
		 String songaylamviecnew="";
		 String loaictnew="";
		 String dvkdnew="";
		 String KenhIDNew="";
		 String pk_seq="";
		 String tenformnew="";
		
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
		String thang_ = file.substring(file.indexOf("thang"));//indexOf la lenh lay den vi tri
		thang_ = thang_.substring(thang_.indexOf("\n"));//vi tri xuong dong
		thang_ =thang_.substring(0, thang_.indexOf("-")).trim();
		//System.out.println(" THANG NEK "+thang_);
		try{
			thangnew=Integer.parseInt(thang_);
		}catch(Exception er){
			System.out.println("Bi Loi Khi Parse thang"+ er.toString());
		}
		
		String nam_ = file.substring(file.indexOf("=\"nam"));
		//indexOf la lenh lay den vi tri,phai lay them dau = va dau " vi co nhieu chu nam trong file, nen no se ko nhan ra vi tri nam
		nam_ = nam_.substring(nam_.indexOf("\n"));//vi tri xuong dong
		nam_ =nam_.substring(0,nam_.indexOf("-")).trim();
		//System.out.println(" NAM NEK "+nam_);
		try{
		namnew=Integer.parseInt(nam_);
		}catch(Exception er){
			System.out.println("Bi Loi Khi Parse Nam"+ er.toString());
		}
		//Lay thong tin dien giai
		String diengiai_ = file.substring(file.indexOf("diengiai"));
		diengiai_ = diengiai_.substring(diengiai_.indexOf("\n"));//vi tri xuong dong
		diengiai_ = diengiai_.substring(0,diengiai_.indexOf("-")).trim();
		diengiainew=diengiai_;
		//Lay thong tin ve kenh
		String kenhid_ = file.substring(file.indexOf("kenhid"));
		kenhid_ =kenhid_.substring(kenhid_.indexOf("\n"));//vi tri xuong dong
		kenhid_ =kenhid_.substring(0,kenhid_.indexOf("-")).trim();
		KenhIDNew=kenhid_;
		
		//Lay thong tin ve ten form
		String tenformnew_ = file.substring(file.indexOf("tenform"));
		tenformnew_ =tenformnew_.substring(tenformnew_.indexOf("\n"));//vi tri xuong dong
		tenformnew_ =tenformnew_.substring(0,tenformnew_.indexOf("-")).trim();
		tenformnew =tenformnew_;
		//Lay thong tin ve chi tieu 
		
		String pk_seq_ = file.substring(file.indexOf("id"));
		pk_seq_ =pk_seq_.substring(pk_seq_.indexOf("\n"));//vi tri xuong dong
		pk_seq_ =pk_seq_.substring(0,pk_seq_.indexOf("-")).trim();
		pk_seq=pk_seq_;
		//Lay thong tin ngay ket thuc
		try{
		String ngayketthuc_ = file.substring(file.indexOf("ngayketthuc"));
		ngayketthuc_ =ngayketthuc_.substring(ngayketthuc_.indexOf("\n"));//vi tri xuong dong
		ngayketthuc_ =ngayketthuc_.substring(0,ngayketthuc_.indexOf("--")).trim();
		ngaykethucnew=ngayketthuc_;
		}catch(Exception er){
			
		}
		//Lay thong tin so ngay lam viec
		String songaylamviec_ = file.substring(file.indexOf("songaylamviec"));
		songaylamviec_ =songaylamviec_.substring(songaylamviec_.indexOf("\n"));//vi tri xuong dong
		songaylamviec_ =songaylamviec_.substring(0,songaylamviec_.indexOf("--")).trim();
		songaylamviecnew=songaylamviec_;
		//lay thong tin ve userid
		String userId_ = file.substring(file.indexOf("userId"));
		userId_ =userId_.substring(userId_.indexOf("\n"));//vi tri xuong dong
		userId_ =userId_.substring(0,userId_.indexOf("-")).trim();
		useridnew=userId_;
		//lay thong tin ve userid
		String dvkdnew_ = file.substring(file.indexOf("selectdvkd"));
		dvkdnew_ =dvkdnew_.substring(dvkdnew_.indexOf("\n"));//vi tri xuong dong
		dvkdnew_ =dvkdnew_.substring(0,dvkdnew_.indexOf("-")).trim();
		dvkdnew=dvkdnew_;
		//lay thong tin ve loaict
		String loaict_ = file.substring(file.indexOf("loaict"));
		loaict_ =loaict_.substring(loaict_.indexOf("\n"));//vi tri xuong dong
		loaict_ =loaict_.substring(0,loaict_.indexOf("-")).trim();
		loaictnew=loaict_;
		//lay thong tin username
		String userTen_ = file.substring(file.indexOf("userTen"));
		userTen_ =userTen_.substring(userTen_.indexOf("\n"));//vi tri xuong dong
		userTen_ =userTen_.substring(0,userTen_.indexOf("-")).trim();
		usernamenew=userTen_;
		
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
			FileOutputStream fileOut = new FileOutputStream("C:\\" + getDateTime() + "-" + saveFile);
			fileOut.write(dataBytes, startPos, (endPos - startPos));
			fileOut.flush();
			fileOut.close();
			return ("C:\\" + getDateTime() + "-" + saveFile);
		}else{
			return "";
		}
		
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int thangnew=0;
		 int namnew=0;
		 String ngaykethucnew="";
		  String diengiainew="";
		  String usernamenew="";
		 String useridnew="";
		 String songaylamviecnew="";
		 String loaictnew="";
		 String dvkdnew="";
		 String KenhIDNew="";
		 String pk_seq="";
		 String tenformnew="";
		 Utility util = new Utility();
		
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		//KHai bao mot bien loi, neu nhan dc loi thi bien loi tang len 1
		int loi=0;
		//khai bao ket noi
		  dbutils db=new dbutils();
		  ChiTieuTTChoVung ctbean=new ChiTieuTTChoVung ();
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    String contentType = request.getContentType();   
		HttpSession session = request.getSession();
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		ctbean.setUserId(userId);
		String userTen=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userTen")));
		String loaichitieu=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaict")));
		String songaylamviec=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("songaylamviec")));
		String madh=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));//truong truong hop don hang sua thi moi co id,nguoc lai thï¿½ khong co
		String kenhid=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhid")));
		String dvkdid=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("selectdvkd")));
		ctbean.setDvkdID(dvkdid);
		int thang=0;
		  try {
			  thang=Integer.parseInt(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thang"))));
		  }catch(Exception ex){
			  
		  }
		  ctbean.setThang(thang);
		  int nam=0;
		  try{
			  nam=Integer.parseInt(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nam"))));
		  }catch(Exception ex ){ 
		  }
		  ctbean.setNam(nam);
		String VungID=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("VungID")));
		String action=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));//action =new : che do them, nguoc lai la che do sua;
		if(action==null){
			action="";
		}
		//System.out.println("Action trong truong hop nay la  : " + action);
		
		try{
		ctbean.setID(Double.parseDouble(madh));
		}catch(Exception er){
		}
		//this.out = response.getWriter();
		ctbean.setKenhID(kenhid);
		ctbean.setVungID(VungID);
		ctbean.setNguoiSua(userId);
		String ngaysua = getDateTime();
		ctbean.setNgaySua(ngaysua);
    	String ngaytao=getDateTime();
    	ctbean.setNguoiTao(userId);
    	ctbean.setNgayTao(ngaytao);
    	ctbean.setSoNgayLamViec(songaylamviec);
		List<ChiTieuTTKhuVuc>  spList = new ArrayList<ChiTieuTTKhuVuc>();
    	
    	String[] mavung = request.getParameterValues("mavung");
    	String[] tenvung =request.getParameterValues("tenvung");
		String[] chitieu = request.getParameterValues("chitieu");
		String[] sodonhang=request.getParameterValues("sodonhang");
		String[] trungbinhthang=request.getParameterValues("trungbinhthang");
		String[] sosku=request.getParameterValues("sosku");

		  if(mavung != null){					
			int m = 0;
			while(m < mavung.length){
				ChiTieuTTKhuVuc khuvuc = new ChiTieuTTKhuVuc();
				if(mavung[m] != ""){
					if(mavung[m] != ""){ //chi them nhung san pham co so luong > 0
					   khuvuc.setKhuVucId(mavung[m]);
					   khuvuc.setTenKhuVucId(tenvung[m]);
					   double ctvung=0;
					   try{
						   ctvung=Double.parseDouble(chitieu[m]);
					   }catch(Exception er){
						
					   }
					   double tbthang=0;
					   try{
						   tbthang=Double.parseDouble(trungbinhthang[m]);
						   
					   }catch(Exception er){
						  
					   }
					   khuvuc.setTrungBinhThang(tbthang);
					   //Lấy trung bình tháng khi tháng năm ,kenh thay đổi
					 //lay nhung khu vuc dang hoat dong
						Calendar c2 = Calendar.getInstance();
						  c2.clear();
						 
				        c2.set(nam,thang-1,1);//Lay ngay thang la thang nam dat chi tieu
				        //lay nguoc lai cach 3 thang
				        c2.add(Calendar.MONTH, -3);//get datetime before 3 month
				        
				        DateFormat formatdate=new SimpleDateFormat("yyyy-MM-dd");
				        String ngaythang=formatdate.format(c2.getTime());
				        c2.clear();
				       
				        c2.set(nam,thang-1,1);
				        String toingay=formatdate.format(c2.getTime());
						/*
						 String sql ="SELECT K.PK_SEQ,K.TEN,ISNULL((SELECT SUM(TONGGIATRI) FROM DONHANG A " +
						 		"INNER JOIN  NHAPHANPHOI P ON A.NPP_FK=P.PK_SEQ WHERE P.KHUVUC_FK=K.PK_SEQ and a.trangthai='1'   and a.kbh_fk="+kenhid+"  AND (a.ngaynhap<'"+toingay+"') and  (A.NGAYNHAP >= '"+ngaythang+"')),0) AS TONGCHITIEU "+ 
								        " FROM KHUVUC K WHERE TRANGTHAI ='1' and k.pk_seq="+ khuvuc.getKhuVucId();
						*/
				        if(action.equals("0")){
						        String sql_getkhuvuc=" select sum(soluong* giamua ) as tongchitieu  from donhang_sanpham dh_sp "+
						        " inner join donhang dh on dh.pk_seq=dh_sp.donhang_fk and ngaynhap between '"+ngaythang+"' and '"+toingay+"'"+
						        " inner join sanpham sp on sp.pk_Seq=dh_sp.sanpham_fk "+
						        " inner join nhaphanphoi npp on npp.pk_Seq=dh.npp_fk "+
						        " where dh.kbh_fk="+kenhid+" and sp.dvkd_fk="+dvkdid+" and dh.trangthai=1 and npp.khuvuc_fk="+khuvuc.getKhuVucId();
								// System.out.println("Lenh Sql khi submit de lay trung binh thang moi :"+sql_getkhuvuc);
								ResultSet rs_getkhuvuc=db.get(sql_getkhuvuc);
								if(rs_getkhuvuc!=null){
									try{
									if(rs_getkhuvuc.next()){
										double sochitieu=rs_getkhuvuc.getDouble("tongchitieu");
					        			//Tnh Ra Duoc Phan Tram So Tien Ban Duoc Theo Khu Vuc, Sau Do Sẽ lấy tổng số tiên muốn đặt chỉ tiêu, sẽ chia theo tỉ lệ phần trăm mà nhà phân phối đặt được
					        			khuvuc.setTrungBinhThang(sochitieu/3);
									}
									}catch(Exception er){
										
									}
								}
				        }
					   khuvuc.setChiTieu(ctvung);//set chi tieu
					    try
					    {
						khuvuc.setSoDonHang((sodonhang[m]!="")?  sodonhang[m]:"0");//set so don hang,neu rong thi set =0
						
					    }catch(Exception er){
					    	khuvuc.setSoDonHang("0");
					    }
					    try{
					    	khuvuc.setSoSKU((sosku[m]!="")? sosku[m]:"0");// set  so sku
					    }catch(Exception er){
					    	khuvuc.setSoSKU("0");
					    }
					    spList.add(khuvuc);
						
					}
				}
				m++;
			}	
		  }
    	
		  ctbean.setListKhuVuc(spList);//add cac chi tiet san pham  vao don hang
		  
		  String ngaykethuc=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngayketthuc")));
		  ctbean.setNgayKetThuc(ngaykethuc);
		  double tongchitieu= 0;
		  try{
			 tongchitieu= Double.parseDouble(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tongchitieu"))));
		  }catch(Exception ex){
			  
		  }
		  if(tongchitieu<=0)
		  {
			 loi=loi+1;
		  }
		  //String thoigiandatchitieu=String.valueOf(nam)+"-" +String.valueOf(thang)+"-01";
		  //DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		  ctbean.setChitieu(tongchitieu);
		  String diengiai=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
		  String check=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chon")));
		  ctbean.setDienGiai(diengiai);
		  String	nextJSP;
		  String tenform=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tenform")));
		  if(tenform==null){
			  tenform="";
		  }
			
			  //truyen qua mot resultset danh sach cac donvikinhdoanh
		    String sql_getdvkd="select pk_seq,donvikinhdoanh from donvikinhdoanh where trangthai=1";
			ResultSet rs_dvkd= db.get(sql_getdvkd);
			session.setAttribute("rs_dvkd",rs_dvkd);	
			//Khai bao 1 bang hashtable de luu tru cac khu vuc duoc insert tu file excel va duoc load tu dong tu csdl
			Hashtable<String, ChiTieuTTKhuVuc> htable=new Hashtable<String, ChiTieuTTKhuVuc>();
			System.out.println("Action :"+ action);
		  if(tenform.equals("update")){
			  nextJSP= "/AHF/pages/Center/ChiTieuTTChoVungUpdate.jsp";
		  }else{
			  nextJSP= "/AHF/pages/Center/ChiTieuTTChoVungNew.jsp";
		  }
		
		  if(action.equals("new")){ // mode them moi
			  if(loaichitieu.equals("0")){
				  if(ctbean.SaveChiTieu()){//truong hop nay them duoc,quay tro  lai trang main
					session.setAttribute("nam",0);
					session.setAttribute("thang",0);
					session.setAttribute("tumuc","");
					session.setAttribute("toimuc","");	
					ctbean.setListChiTieu("",loaichitieu);
					session.setAttribute("obj", ctbean);
				    nextJSP= "/AHF/pages/Center/ChiTieuTTChoVung.jsp"; 	 
			 }
			  }else{
				  if(ctbean.SaveChiTieu_Sec()){//truong hop nay them duoc,quay tro  lai trang main
						session.setAttribute("nam",0);
						session.setAttribute("thang",0);
						session.setAttribute("tumuc","");
						session.setAttribute("toimuc","");	
						ctbean.setListChiTieu("",loaichitieu);
						session.setAttribute("obj", ctbean);
					    nextJSP= "/AHF/pages/Center/ChiTieuTTChoVung.jsp"; 	 
				 }
			  }
		 }else if(action.equals("update")){//truong hop update
			 if(loaichitieu.equals("0")){
			 if(ctbean.EditChiTieu()){//truong hop nay them duoc,quay tro  lai trang main
					String sql_getvung="select pk_seq, ten from vung order by ten";
					ResultSet rsvung=	db.get(sql_getvung);
					session.setAttribute("rsvung", rsvung);
					session.setAttribute("nam",0);
					session.setAttribute("thang",0);
					session.setAttribute("tumuc","");
					session.setAttribute("toimuc","");	
					ctbean.setListChiTieu("",loaichitieu);
					session.setAttribute("obj", ctbean);
				    nextJSP= "/AHF/pages/Center/ChiTieuTTChoVung.jsp"; 	 
			 }
			 }else{
				 if(ctbean.EditChiTieuSec()){//truong hop nay them duoc,quay tro  lai trang main
						String sql_getvung="select pk_seq, ten from vung order by ten";
						ResultSet rsvung=	db.get(sql_getvung);
						session.setAttribute("rsvung", rsvung);
						session.setAttribute("nam",0);
						session.setAttribute("thang",0);
						session.setAttribute("tumuc","");
						session.setAttribute("toimuc","");	
						ctbean.setListChiTieu("",loaichitieu);
						session.setAttribute("obj", ctbean);
					    nextJSP= "/AHF/pages/Center/ChiTieuTTChoVung.jsp"; 	 
				 }
			 }
		 }else if(action.equals("capnhat")){// option auto update chitieu for regions
			 //remove het danh sach list cu
			 spList.clear();
		        Calendar c2 = Calendar.getInstance();
		        if(thang<3){
		        	
		        }
		        c2.set(nam, thang-1, 1);//Lay ngay thang la thang nam dat chi tieu,thang trong ham nay bat dau tinh tu 0 toi 11 vi vay phai tru di 1
		       
		       //get datetime before 3 month
		        c2.add(Calendar.MONTH,-3);
		        
		        DateFormat formatdate=new SimpleDateFormat("yyyy-MM-dd");
		        String ngaythang=formatdate.format(c2.getTime());
		        c2.set(nam,thang-1,1);
		        String toingay=formatdate.format(c2.getTime());
		        
		        double sumchitieu=0;
		        /*truoc tien ta se lay sum so tien cua cac don hang theo dieu kien cu the
		        
		        String sql_sum="SELECT     SUM(dbo.DONHANG.TONGGIATRI) AS tongchitieu  FROM  dbo.DONHANG INNER JOIN  "+
                " dbo.NHAPHANPHOI ON dbo.DONHANG.NPP_FK = dbo.NHAPHANPHOI.PK_SEQ INNER JOIN  dbo.KHUVUC ON dbo.NHAPHANPHOI.KHUVUC_FK = dbo.KHUVUC.PK_SEQ "+
                " WHERE     (dbo.DONHANG.TRANGTHAI = '1') and kbh_fk="+kenhid+" AND (DONHANG.NGAYNHAP<'"+toingay+"') and (dbo.DONHANG.NGAYNHAP >= '"+ngaythang+"') ";
		       */
		        String sql_sum=" select sum(soluong* giamua ) as tongchitieu  from donhang_sanpham dh_sp "+
		        " inner join donhang dh on dh.pk_seq=dh_sp.donhang_fk and ngaynhap between '"+ngaythang+"' and '"+toingay+"'"+
		        " inner join sanpham sp on sp.pk_Seq=dh_sp.sanpham_fk "+
		        " inner join nhaphanphoi npp on npp.pk_Seq=dh.npp_fk "+
		        " where dh.kbh_fk="+kenhid+" and sp.dvkd_fk="+dvkdid+" and dh.trangthai=1 ";

		        // System.out.println("sql_sum"+sql_sum);
		        ResultSet rs_sum=db.get(sql_sum);
		        if(rs_sum!=null){
		        	try{
		        		while(rs_sum.next()){
		        			sumchitieu=rs_sum.getDouble("tongchitieu");//tong so tien doanh so trong 3 thang cua tat ca cac khu vuc
		        		}
		        	}catch(Exception er){
		        		
		        	}
		        }
		      
		        /*
		         * Lenh moi nay su dung tot hon,lay duoc ca sku va so don hang
		         */
		        // CAU LENH SQL LAY RA TONG SO CHI TIEU,SODONHANG,SO SAN PHAM BAN DUOC CUA 1 NHA PHAN PHOI CHO KHACH HANG ,theo 1 kenh xac dinh.
		        
		        //chu y lay nhug don hang da chot trang thai =1 ,dieu kien lay la trong 3 thang gan nhat
		      /*
		        String sql_sumbyregion="SELECT K.PK_SEQ,K.TEN,ISNULL((SELECT SUM(TONGGIATRI) FROM DONHANG A INNER JOIN  NHAPHANPHOI P ON A.NPP_FK=P.PK_SEQ WHERE P.KHUVUC_FK=K.PK_SEQ and a.trangthai='1' and a.kbh_fk="+kenhid+" AND (A.NGAYNHAP<'"+toingay+"') AND  (A.NGAYNHAP >= '"+ngaythang+"') ),0) AS TONGCHITIEU, "+ 
		        "(  SELECT COUNT(A.PK_SEQ) FROM DONHANG A INNER JOIN  NHAPHANPHOI P ON A.NPP_FK=P.PK_SEQ WHERE P.KHUVUC_FK=K.PK_SEQ and a.trangthai='1' and a.kbh_fk="+kenhid+" AND (A.NGAYNHAP<'"+toingay+"') AND A.NGAYNHAP>= '"+ngaythang+"' ) AS TONGSODONHANG, "+
		        "(  SELECT COUNT(A.SANPHAM_FK)  FROM DONHANG_SANPHAM A INNER JOIN DONHANG B ON A.DONHANG_FK=B.PK_SEQ INNER JOIN NHAPHANPHOI C ON B.NPP_FK=C.PK_SEQ WHERE C.KHUVUC_FK=K.PK_SEQ and b.trangthai='1' and b.kbh_fk="+kenhid+" AND (B.NGAYNHAP<'"+toingay+"')  AND B.NGAYNHAP> '"+ngaythang+"') AS SOSKU " +
		        " FROM KHUVUC K WHERE TRANGTHAI ='1' order by K.PK_SEQ  ";
		       */
		        String sql_sumbyregion="select pk_seq,ten,  isnull((  select sum(soluong* giamua )  from donhang_sanpham dh_sp "+ 
			 	 " inner join donhang dh on dh.pk_seq=dh_sp.donhang_fk and ngaynhap between '"+ngaythang+"' and '"+toingay+"'"+
			 	 " inner join sanpham sp on sp.pk_Seq=dh_sp.sanpham_fk  inner join nhaphanphoi npp on npp.pk_Seq=dh.npp_fk "+ 
				" where dh.kbh_fk="+kenhid+" and sp.dvkd_fk="+dvkdid+" and dh.trangthai=1 and npp.khuvuc_fk=kv.pk_Seq),0) as tongchitieu from khuvuc kv "+ 
			 	" where trangthai=1 order by kv.pk_Seq";
		        System.out.println("sql_sumbyregion "+sql_sumbyregion);
		        ResultSet rs_sumbyregion=db.get(sql_sumbyregion);
		        if(rs_sumbyregion!=null){
		        	try{
		        		while(rs_sumbyregion.next()){
		        			ChiTieuTTKhuVuc khuvuc = new ChiTieuTTKhuVuc();
		        			double sochitieu=rs_sumbyregion.getDouble("tongchitieu");
		        			//Tnh Ra Duoc Phan Tram So Tien Ban Duoc Theo Khu Vuc, Sau Do Sẽ lấy tổng số tiên muốn đặt chỉ tiêu, sẽ chia theo tỉ lệ phần trăm mà nhà phân phối đặt được
		        			
		        			double 	phantram=sochitieu/sumchitieu* 100; //Tính duoc phan tram
		        			
		        			khuvuc.setTrungBinhThang(sochitieu/3);
		        			
		        			double chitieuchotungkv= tongchitieu/100* phantram; 
		        			//System.out.println("Khu vuc :"+ rs_sumbyregion.getString("TEN"));
		        			//System.out.println("Chi Tieu  :"+ chitieuchotungkv);
		        			//System.out.println("Chi Tieu  :"+ sochitieu/3);
		        			khuvuc.setKhuVucId(rs_sumbyregion.getString("PK_SEQ"));
		        			
		        			khuvuc.setChiTieu(chitieuchotungkv);
		        			
		        			khuvuc.setTenKhuVucId(rs_sumbyregion.getString("TEN"));
		        			//lay tong so don hang cua khu vuc, chia cho 90 ngay,thi se ra so don hang trong 1 ngay
		        			
		        			/*double tongsodonhang= rs_sumbyregion.getDouble("TONGSODONHANG")/90;
		        			
		        			khuvuc.setSoDonHang(Double.toString(Math.round(tongsodonhang)));
		        			
		        			// De biet duoc so trung binh sku tren moi don hang thi thuc hien lay tong so sku chia cho tong so don hang
		        			if(rs_sumbyregion.getDouble("TONGSODONHANG")>0){
		        			double tongsosku=rs_sumbyregion.getDouble("SOSKU")/rs_sumbyregion.getDouble("TONGSODONHANG");
		        			khuvuc.setSoSKU(Double.toString(Math.round(tongsosku)));
		        			}else{
		        				khuvuc.setSoSKU("0");
		        			}
		        			*/
		        			spList.add(khuvuc);
		        		}
		        	}catch(Exception er){
		        		System.out.println("Error ChiTieuTTChoVungNewSvl :"+er.toString());
		        	}
		        }
		        //
		 }
		 // System.out.println("CONTENT TYPE : "+ contentType);
		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) {//truong hop co file dinh kem
						String filename = SaveExcel(request);
				//System.out.println("FILENAME  :"+ filename);
				 tongchitieu=0;	
				 String mavungsai="";
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
							Cell cell3 =sheet.getCell(3, i);
							Cell cell4 =sheet.getCell(4, i);
							if(cell0.getContents()!=""  &&  cell1.getContents()!="" && !cell0.getContents().trim().equals("Ma Kvuc")){//khhong phai nhung don chua du lieu
								//kiem tra ma khu vuc co ton tai khong?
								String sql_checkvung="select pk_seq from khuvuc where pk_seq="+ cell0.getContents();
								ResultSet rs_check=db.get(sql_checkvung);
								if(rs_check!=null){
								 if(!rs_check.next())
								 {
									//neu khong ton tai thi se thong bao cho nguoi dung biet la file khong hop le.
									if(mavungsai.equals("")){
									 mavungsai =cell0.getContents();
									}else{
										mavungsai = mavungsai+ "," +cell0.getContents();
									}
								 	}
								}
								   ChiTieuTTKhuVuc khuvuc = new ChiTieuTTKhuVuc();
								   khuvuc.setKhuVucId(cell0.getContents());
								   khuvuc.setTenKhuVucId(cell1.getContents());
								   double chitieu_=0;
								   try{
								    chitieu_=Double.parseDouble(cell2.getContents());
								   }catch(Exception er){
									   throw new Exception("Du Lieu["+cell2.getContents()+"] Dua Vao Khong Hop Le,Vui Long Kiem Tra Lai.");
								   }
								    khuvuc.setChiTieu(chitieu_);
								    khuvuc.setSoDonHang(cell3.getContents());
								    khuvuc.setSoSKU(cell4.getContents());
									//spList.add(khuvuc);
								    // add doi tuong khu vuc nay vao bang bam
				        			htable.put(khuvuc.getKhuVucId(),khuvuc);
				        			System.out.println("+fdafas+"+khuvuc.getKhuVucId());
				        			
							}
							//if(cell0.getContents().trim().equals("Grand Total")){//lay ra tong so
							//	tongchitieu=Double.parseDouble(cell2.getContents());
							//	ctbean.setChitieu(tongchitieu);
							//}
						     if(!mavungsai.equals("")){
							  ctbean.setMessage("Ma Vung Khong Hop Le :" + mavungsai); 
					     	 }
						}
					}catch(Exception er){
						ctbean.setMessage("Chu Y : "+ er.toString());
						spList.clear();
					}
				}
			
				//lay nhung khu vuc dang hoat dong
				Calendar c2 = Calendar.getInstance();
				  c2.clear();
		        c2.set(namnew, thangnew-1, 1);//Lay ngay thang la thang nam dat chi tieu
		        //lay nguoc lai cach 3 thang
		       
		        DateFormat formatdate=new SimpleDateFormat("yyyy-MM-dd");
		        String ngaythang=formatdate.format(c2.getTime());
		        
		        c2.add(Calendar.MONTH,-3);
		        
		        String toingay=formatdate.format(c2.getTime());
				/*
				 String sql_getkhuvuc="SELECT K.PK_SEQ,K.TEN,ISNULL((SELECT SUM(TONGGIATRI) FROM DONHANG A " +
				 		" INNER JOIN  NHAPHANPHOI P ON A.NPP_FK=P.PK_SEQ WHERE P.KHUVUC_FK=K.PK_SEQ and a.trangthai='1' " +
				 		" and a.kbh_fk="+KenhIDNew+" and (A.NGAYNHAP<='"+ngaythang+"') " +
				 				" AND  (A.NGAYNHAP >= '"+toingay+"')),0) AS TONGCHITIEU "+ 
						        " FROM KHUVUC K WHERE TRANGTHAI ='1' order by K.PK_SEQ ";
						 */
				  String sql_getkhuvuc="select pk_seq,ten,  isnull((  select sum(soluong* giamua )  from donhang_sanpham dh_sp "+ 
				 	 " inner join donhang dh on dh.pk_seq=dh_sp.donhang_fk and ngaynhap between '"+toingay+"' and '"+ngaythang+"'"+
				 	 " inner join sanpham sp on sp.pk_Seq=dh_sp.sanpham_fk  inner join nhaphanphoi npp on npp.pk_Seq=dh.npp_fk "+ 
					" where dh.kbh_fk="+KenhIDNew+" and sp.dvkd_fk="+dvkdnew+" and dh.trangthai=1 and npp.khuvuc_fk=kv.pk_Seq),0) as tongchitieu from khuvuc kv "+ 
				 	" where trangthai=1 order by kv.pk_Seq";

				// System.out.println("Lenh Sql :"+sql_getkhuvuc);
				ResultSet rs_getkhuvuc=db.get(sql_getkhuvuc);
				if(rs_getkhuvuc!=null){
					try{
					while(rs_getkhuvuc.next()){
						ChiTieuTTKhuVuc khuvuc = new ChiTieuTTKhuVuc();
		    			System.out.println("Khu vuc can them vao la :" +rs_getkhuvuc.getString("pk_seq"));
		    			khuvuc.setKhuVucId(rs_getkhuvuc.getString("pk_seq"));
		    			
		    			khuvuc.setTenKhuVucId(rs_getkhuvuc.getString("TEN"));
		    			
		    			double sochitieu=rs_getkhuvuc.getDouble("tongchitieu");
	        			//Tnh Ra Duoc Phan Tram So Tien Ban Duoc Theo Khu Vuc, Sau Do Sẽ lấy tổng số tiên muốn đặt chỉ tiêu, sẽ chia theo tỉ lệ phần trăm mà nhà phân phối đặt được
	        			khuvuc.setTrungBinhThang(sochitieu/3);
	        			
		    			khuvuc.setChiTieu(0);
		    			
		    			khuvuc.setSoSKU("0");
		    			
		    			khuvuc.setSoDonHang("0");
		    			
		    			if(htable.containsKey(khuvuc.getKhuVucId())){
		    				
		    				htable.get(khuvuc.getKhuVucId()).setTrungBinhThang(sochitieu/3);
		    				tongchitieu=tongchitieu + htable.get(khuvuc.getKhuVucId()).getChiTieu();
		    				spList.add(htable.get(khuvuc.getKhuVucId()));
		    				
		    			}else{
		    				spList.add(khuvuc);
		    			}
		    			
					}
					}catch(Exception er){
						System.out.println("Error Here ChiTieuTTChoVung : "+ er.toString());
					}
				}
				ctbean.setChitieu(tongchitieu);
				ctbean.setThang(thangnew);
				ctbean.setNam(namnew);
				ctbean.setDienGiai(diengiainew);
				ctbean.setNgayKetThuc(ngaykethucnew);
				ctbean.setSoNgayLamViec(songaylamviecnew);
				ctbean.setDvkdID(dvkdnew);
				ctbean.setKenhID(KenhIDNew);
				try{
				ctbean.setID(Double.parseDouble(pk_seq));
				}catch(Exception er){
					ctbean.setMessage("Vui Long Quay Lai Trang Chu De Thuc Hien Lai,Xay Ra Loi Trong Qua Trinh Thuc Hien");
				}
				 if(tenformnew.equals("update")){
					  nextJSP= "/AHF/pages/Center/ChiTieuTTChoVungUpdate.jsp";
				  }else{
					  nextJSP= "/AHF/pages/Center/ChiTieuTTChoVungNew.jsp";
				  }
			}
		
		//Kiem tra neu SpList ma rong thi dang trong truong hop la upload va import file excel
		
		  String sql="select pk_seq,ten from vung order by ten";
	    	
	     ResultSet rsvung=	db.get(sql);
	     session.setAttribute("rsvung",rsvung);
		  
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
		  if(loaichitieu==null)
		  {
			  session.setAttribute("loaichitieu",loaictnew);
		  }else{
		  session.setAttribute("loaichitieu", loaichitieu);
		  }
		  session.setAttribute("obj", ctbean);
		  response.sendRedirect(nextJSP);
	}

}
