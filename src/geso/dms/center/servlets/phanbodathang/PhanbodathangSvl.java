package geso.dms.center.servlets.phanbodathang;

import geso.dms.center.beans.phanbodathang.IPhanbodathang;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.beans.phanbodathang.imp.Phanbodathang;
import geso.dms.center.util.Utility;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class PhanbodathangSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PhanbodathangSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    Utility util = new Utility();	    
	    HttpSession session = request.getSession();
	    IPhanbodathang pbdhBean = new Phanbodathang();
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
//	    System.out.println(userId);
	    
	    pbdhBean.init();	    
		session.setAttribute("pbdh", pbdhBean);

		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Center/PhanBoDatHang.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    Utility util = new Utility();
	    HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();

		String contentType = request.getContentType();
		//here we are checking the content type is not equal to Null and as well as the passed data from mulitpart/form-data is greater than or equal to 0
		
		String userId = (String)session.getAttribute("userId");
		IPhanbodathang pbdhBean = new Phanbodathang();
		
		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
			String spId = (String)session.getAttribute("spId");		
			if(spId == null) spId = ""; 
			
			String tungay = (String)session.getAttribute("tungay");
			if(tungay == null) tungay = "";
			
			String denngay = (String)session.getAttribute("denngay");
			if(denngay == null) denngay = "";
				
			String filename = SaveExcel(request,pbdhBean);
			if (filename.length() > 0 & pbdhBean.getTungay().length() > 0 & pbdhBean.getDenngay().length() > 0 & pbdhBean.getSpId().length() > 0)
			{
				String msg = readExcel(filename, spId,pbdhBean.getTungay() ,pbdhBean.getDenngay(), userId);

				if (msg.length() > 0) pbdhBean.setMsg(msg);
				
				pbdhBean.setSpId(spId);
				pbdhBean.setTungay(tungay);
				pbdhBean.setDenngay(denngay);
				
				pbdhBean.init();

				session.setAttribute("pbdh", pbdhBean);
				session.setAttribute("userId", userId);
				String nextJSP = "/AHF/pages/Center/PhanBoDatHang.jsp";
				response.sendRedirect(nextJSP);
			}
			else
			{
				pbdhBean.setSpId(spId);
				pbdhBean.setTungay(tungay);
				pbdhBean.setDenngay(denngay);
				pbdhBean.setMsg("Vui long dien du thong tin tu ngay, den ngay, ma san pham va chon tap tin de upload");
				pbdhBean.init();	    
				
			    session.setAttribute("pbdh", pbdhBean);
				
				session.setAttribute("userId", userId);
				String nextJSP = "/AHF/pages/Center/PhanBoDatHang.jsp";
				response.sendRedirect(nextJSP);				
			}
		}		
		else
		{
			
			String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
			if(tungay == null) tungay = "";
			pbdhBean.setTungay(tungay);
			
			String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
			if(denngay == null) denngay = "";
			pbdhBean.setDenngay(denngay);

			String spId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("spId")));
			if(spId == null) spId = "";
			pbdhBean.setSpId(spId);
			
			session.setAttribute("spId", spId);

			String kbhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhId")));
			
			pbdhBean.setKenhId(kbhId);

			String vungId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")));
			
			if(vungId == null) vungId = "";
			
			pbdhBean.setVungId(vungId);
		
			String kvId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kvId")));
		
			if(kvId == null) kvId = "";
			
			pbdhBean.setKvId(kvId);
			
			session.setAttribute("kvId", kvId);
		
			pbdhBean.init();
			
			
			session.setAttribute("pbdh", pbdhBean);
				
			session.setAttribute("userId", userId);
			
			session.setAttribute("spId", spId);
			
			session.setAttribute("tungay", tungay);
			
			session.setAttribute("denngay", denngay);
			
			String nextJSP = "/AHF/pages/Center/PhanBoDatHang.jsp";
			response.sendRedirect(nextJSP);
		}
	}

	

	private String SaveExcel(HttpServletRequest request,IPhanbodathang pbdhBean)throws ServletException, IOException
	{
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
		
		//for saving the file name
		
		String saveFile = file.substring(file.indexOf("filename=\"") + 10);
		
		saveFile = saveFile.substring(0, saveFile.indexOf("\n"));
		saveFile = saveFile.substring(saveFile.lastIndexOf("\\") + 1,saveFile.indexOf("\""));
		
	
		//spId
	
		
		
		String tungay = file.substring(file.indexOf("tungay"));//indexOf la lenh lay den vi tri
		tungay = tungay.substring(tungay.indexOf("\n")).trim();//vi tri xuong dong
		//System.out.println("tu ngay  : "+tungay);
		tungay =tungay.substring(0,10).trim();
		
		pbdhBean.setTungay(tungay);
		
		String denngay = file.substring(file.indexOf("denngay"));//indexOf la lenh lay den vi tri
		denngay = denngay.substring(denngay.indexOf("\n")).trim();//vi tri xuong dong
		//System.out.println("Den ngay : "+denngay);
		denngay =denngay.substring(0,10).trim();
		
		pbdhBean.setDenngay(denngay);
		
		String sanpham = file.substring(file.indexOf("spId"));//indexOf la lenh lay den vi tri
		sanpham = sanpham.substring(sanpham.indexOf("\n")).trim();//vi tri xuong dong
		sanpham = sanpham.substring(0,sanpham.indexOf("-")).trim();

		
		pbdhBean.setSpId(sanpham);
		
		System.out.println("san pham : "+sanpham);
		
		int lastIndex = contentType.lastIndexOf("=");
		String boundary = contentType.substring(lastIndex + 1,contentType.length());
		
//		System.out.println("boundary = " + boundary);
		
		int pos;
		
		//extracting the index of file 
		pos = file.indexOf("filename=\"");
		pos = file.indexOf("\n", pos) + 1;
		pos = file.indexOf("\n", pos) + 1;
		pos = file.indexOf("\n", pos) + 1;
		
		int boundaryLocation = file.indexOf(boundary, pos) - 4;	
		
//		System.out.println(file);
		
		if(saveFile.length()>0){
		// creating a new file with the same name and writing the content in new file
			FileOutputStream fileOut = new FileOutputStream("C:\\" + getDateTime() + "-" + saveFile);
			fileOut.write(dataBytes, pos, (boundaryLocation - pos));
			fileOut.flush();
			fileOut.close();
			return ("C:\\" + getDateTime() + "-" + saveFile);
		}else{
			return "";
		}
	}

	private String readExcel(String filename, String spId, String tungay, String denngay, String userId) throws ServletException, IOException
	{
		File inputWorkbook = new File(filename);
		Workbook w;

		Hashtable dadat = this.getDaDatHang(spId, tungay, denngay);
		
		dbutils db = new dbutils();
		
		boolean flag = false; //bien dung kiem tra xem trong file Upload co ma NPP tuong ung ko
		try 
		{
			db.getConnection().setAutoCommit(false);
			w = Workbook.getWorkbook(inputWorkbook);
			ResultSet rs = db.get("SELECT SP.PK_SEQ " + 
								  "FROM SANPHAM SP " +
								  "WHERE SP.MA='"+ spId + "' " +
								  "AND SP.PK_SEQ IN (SELECT SANPHAM_FK FROM BGMUANPP_SANPHAM WHERE TRANGTHAI = '1' AND GIAMUANPP > 0)") ;
			if(rs.next()){
				String pk_seq = rs.getString("PK_SEQ");
				rs.close();
				// Get the first sheet
				Sheet sheet = w.getSheet(0);
			
				//dat bien dau tien la true,de khi co nha pp khong dc khai bao trong ctkm thi ko dc cho vao
				flag = true;
				for (int i = 0; i < sheet.getRows(); i++) 
				{			
					Cell cell0 = sheet.getCell(0, i);
					Cell cell1 = sheet.getCell(1, i);
//					Cell cell2 = sheet.getCell(2, i);
					Cell cell3 = sheet.getCell(3, i);
					Cell cell4 = sheet.getCell(4, i);
					
					if( (cell0.getContents().length()== 0 || cell0.getContents().toUpperCase().contains("AREA")) & !cell0.getContents().toUpperCase().contains("Total") & !cell1.getContents().contains("Ma NPP") & !cell0.getContents().contains("Grand")){
						
						String nppId = cell1.getContents();
						System.out.println("nhappp = " + nppId);

						String kbhId = "100000";
						if(cell4.getContents().equals("MT")){
							kbhId = "100002";
						}
						
						String soluong = cell3.getContents();
						System.out.println("souong = " + soluong);
						float d  = 0;
						
						if(dadat.containsKey(nppId)){
							d = Float.parseFloat((String)dadat.get(nppId));
						}					
						
						System.out.println("d = " + d);
						System.out.println("soluong = " + soluong);
						
						if(soluong.length() > 0 & nppId.length() > 0){
							if((d - Integer.parseInt(soluong)) <= 0 & tungay.length() > 0 & denngay.length() > 0){
								String sql = 	"INSERT INTO PHANBODATHANG (KBH_FK, NPP_FK, SANPHAM_FK, SOLUONG, TUNGAY, DENNGAY, NGUOITAO, " +
												"NGAYTAO) VALUES('" + kbhId + "', '" + nppId + "', '" + pk_seq + "','" + soluong + "'," +
												"'" + tungay + "','" + denngay + "','" + userId + "','" + this.getDateTime() + "')";
								System.out.println(sql);
								if(!db.update(sql)){
									sql = "UPDATE PHANBODATHANG SET SOLUONG ='" + soluong + "', tungay ='" + tungay + "', denngay = '"+ denngay + "', " +
											"ngaytao ='" + this.getDateTime() + "', nguoitao = '" + userId + "' where kbh_fk='"+ kbhId +"' and npp_fk = '" + nppId + "' and sanpham_fk = '" + pk_seq + "'";
									System.out.println(sql);
									db.update(sql);
								}
							}else{
								System.out.println("Loi Nay nek : ");
								geso.dms.center.util.Utility.rollback_throw_exception(db);
								return "I .Upload khong thanh cong. Vui long kiem tra lai gia ban va san pham phai duoc chon ban";
							}
						}
					}
				}
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
				
				db.shutDown();
			}else{
				return "II. Upload khong thanh cong. Vui long kiem tra lai gia ban va san pham phai duoc chon ban";
			}
		}
		catch (Exception e) {
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			System.out.println("Loi Xay Ra trong Qua ttrinh cap nhat :"+e.toString());
			return "Loi Trong Qua Trinh Doc File,Vui Long Thu Lai.Loi :"+ e.toString();	
		}
		return "";
	}

	private Hashtable<String, String> getNpp()
	{
		Hashtable<String, String> ht = new Hashtable<String, String>();
		dbutils db = new dbutils();
	
			//chi duoc lay nhung nhapp trong ctkm_npp tuong ung -- khong lay het
			ResultSet rs = db.get("select pk_seq, ma from nhaphanphoi where trangthai='1' and sitecode = convsitecode");// and pk_seq in (select npp_fk from ctkm_npp where ctkm_fk = '" + this.schemeId + "')");

			if(rs != null){
				try{
					while(rs.next()){
						if(rs.getString("ma") != null)
						{
							ht.put(rs.getString("pk_seq"), rs.getString("pk_seq"));
						}
					}
				rs.close();
				}catch(Exception e)
				{
					
				}
			}else{
				
			}
			db.shutDown();
		
		
	return ht;
		
	}

	private Hashtable<String, String> getDaDatHang(String spId, String tungay, String denngay){
		Hashtable<String, String> ht = new Hashtable<String, String>();
			dbutils db = new dbutils();
			if(db.getConnection() != null){
				//lay nha phan phoi da va dang dat hang, trang thai don hang la 1
				String query = 	"SELECT DDH.NPP_FK AS NPPID, ISNULL(SUM(DDH_SP.SOLUONG)/QC.SOLUONG1,0) AS SOLUONG " +
							   	"FROM DONDATHANG DDH " +
								"INNER JOIN DONDATHANG_SP DDH_SP ON DDH_SP.DONDATHANG_FK = DDH.PK_SEQ " +
								"INNER JOIN QUYCACH QC ON QC.SANPHAM_FK = DDH_SP.SANPHAM_FK " +
								"WHERE DDH.NGAYDAT >='"+ tungay + "' AND DDH.NGAYDAT <= '"+ denngay +  "'" +
								"AND DDH_SP.SANPHAM_FK = '"+ spId +"' " +
								"GROUP BY DDH.NPP_FK, QC.SOLUONG1 ";
				System.out.println("Dat Hang :"+query);
				ResultSet rs = db.get(query);
				if(rs != null){
					try{
						while(rs.next()){
							if(rs.getString("nppId") != null){
								ht.put(rs.getString("nppId"), rs.getString("soluong"));
							}
						}
						rs.close();
					
					}catch(Exception e){geso.dms.center.util.Utility.rollback_throw_exception(db);}
				}else{
					System.out.print("Error in getUsedPromotion/rs==null");
				}
				db.shutDown();
			}else{
				System.out.print("Error in getUsedPromotion/rs==null");
			}
			
		return ht;
		
	}
	
	public String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

}
