 package geso.dms.center.servlets.dieuchinhtonkho;

import geso.dms.center.beans.dieuchinhtonkho.IDieuchinhtonkhoList;
import geso.dms.center.beans.dieuchinhtonkho.imp.DieuchinhtonkhoList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.distributor.util.Utility;
import geso.dms.center.beans.dieuchinhtonkho.IDieuchinhtonkho;
import geso.dms.center.beans.dieuchinhtonkho.imp.Dieuchinhtonkho;
import geso.dms.center.beans.thongtinsanpham.imp.ThongtinsanphamList;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class DuyetdctkSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public DuyetdctkSvl() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		OutputStream out = response.getOutputStream();
		IDieuchinhtonkhoList obj;
		String userId = "";	    
		HttpSession session = request.getSession();	    

		Utility util = new Utility();


		String querystring = request.getQueryString();
		String action = util.getAction(querystring);
		//	    out.println(action);

		String dctkId = util.getId(querystring);

		if (userId.length()==0)
			userId = request.getParameter("userId");
		obj = new DieuchinhtonkhoList();
		System.out.println("action la :"+action);
		
		String msg="";
		
		if(action.equals("approve")){
			msg = 	Duyet(dctkId, userId);
		
		}
		else if(action.equals("delete")){
			msg= Delete(dctkId, userId);
		}

		obj.setMsg(msg);
		
		
		userId = util.getUserId(querystring);

		
		obj.setUserId(userId);
		obj.init0();
		obj.createDctklist("");
		session.setAttribute("obj", obj);

		String nextJSP = "/AHF/pages/Center/DuyetDieuChinhTonKho.jsp";
		response.sendRedirect(nextJSP);
	} 

	private String Delete(String dctkId, String userId)
	{
		
		geso.dms.center.util.Utility util = new geso.dms.center.util.Utility();
		
		
		dbutils db = new dbutils();
		String msg="";
		String query = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			// CAP NHAT LAI BOOK DOI VOI NHUNG DCTK AM
			 
			query = "update dieuchinhtonkho set trangthai = '2' where pk_seq = '"+ dctkId +"' and TrangThai=0 and isnull(nppDaChot,0 )  = '1' ";
			if(db.updateReturnInt(query)!=1)
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				msg="Điều chỉnh tồn kho đã xóa rồi !";
				return msg;
			}
			
			
			query=	"	SELECT dcsp.ngaynhapkho ,dc.ngaydc,DC.KHO_FK, DC.KBH_FK, DC.NPP_FK, SANPHAM_FK, (-1)*DCSP.DIEUCHINH AS DIEUCHINH FROM DIEUCHINHTONKHO DC " +
					"	INNER JOIN DIEUCHINHTONKHO_SP DCSP ON DCSP.DIEUCHINHTONKHO_FK = DC.PK_SEQ" +
					" WHERE DC.PK_SEQ = '"+ dctkId +"' AND DCSP.DIEUCHINH < 0 " ;
					
		    ResultSet ckRs = db.get(query);
		    while(ckRs.next())
		    {
		    	String kho_fk=ckRs.getString("kho_fk");
				String nppId=ckRs.getString("npp_fk");	
				String kbh_fk=ckRs.getString("kbh_fk");
				String sanpham_fk=ckRs.getString("sanpham_fk");
				String ngaydc  =ckRs.getString("ngaydc");
				String ngaynhapkho  =ckRs.getString("ngaynhapkho");
				
				Double dieuchinh = ckRs.getDouble("dieuchinh");


				msg=util.Update_NPP_Kho(ngaydc,dctkId, "XÓA DIEUCHINHTONKHO trên HO ", db, kho_fk, sanpham_fk, nppId, kbh_fk,ngaynhapkho, 0.0,  (-1)*dieuchinh, dieuchinh,0, 0.0);
				if(msg.length()>0)
				{
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return msg;
				}			
		    }
		    
		    
		
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);

		}catch(Exception e)
		{
			msg="Exception xảy ra khi cập nhật ";
			e.printStackTrace();
			try 
			{
				db.getConnection().rollback();
			} catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
		}
		finally
		{
			db.shutDown();
		}
		return msg ;
	}

	private void CreateStaticHeader(Workbook workbook) 
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Cells cells = worksheet.getCells();

		Cell cell = cells.getCell("A1"); 
		cell.setValue("Danh sách sản phẩm");

		cell = cells.getCell("A3");

		//cell = cells.getCell("A4");
		//cell.setValue("User:  " + UserName);

		//tieu de
		cell = cells.getCell("A8");
		cell.setValue("So PHieu");


		cell = cells.getCell("B8");
		cell.setValue("Đơn vị kinh doanh");
		cell = cells.getCell("C8");
		cell.setValue("Kho");
		cell = cells.getCell("D8");
		cell.setValue("Kenh");
		cell = cells.getCell("E8");
		cell.setValue("SITECODE");
		cell = cells.getCell("F8");
		cell.setValue("Nha Phan  PHoi");
		cell = cells.getCell("G8");
		cell.setValue("Ma SP");
		cell = cells.getCell("H8");
		cell.setValue("TEN SP");
		cell = cells.getCell("I8");
		cell.setValue("Dieu CHinh");
		cell = cells.getCell("J8");
		cell.setValue("DON VI");
		cell = cells.getCell("K8");
		cell.setValue("Gia MUa");
		cell = cells.getCell("L8");
		cell.setValue("THanh Tien");
		cell = cells.getCell("M8");
		cell.setValue("Ton Hien Tai");
		cell = cells.getCell("N8");
		cell.setValue("TOn MOi");

		worksheet.setName("Danh sách  sản phẩm");
	}

	private void CreateStaticData(Workbook workbook,String dctkid) 
	{
		String query="select dctk.pk_seq,kbh.diengiai as kbh,dvkd.diengiai as dvkd,kho.pk_Seq,kho.ten as kho,npp.sitecode,npp.ten as nppten "+ 
				" ,sp.ma,sp.ten spten,dctk_sp.* "+
				"  from dieuchinhtonkho dctk "+
				" inner join dieuchinhtonkho_sp dctk_sp on dctk.pk_seq=dctk_sp.dieuchinhtonkho_fk "+
				" inner join sanpham sp on sp.pk_seq=sanpham_fk "+
				" inner join kho  on kho.pk_seq=dctk.kho_fk "+
				" inner join donvikinhdoanh dvkd on  dctk.dvkd_fk=dvkd.pk_seq "+
				" inner join kenhbanhang kbh on kbh.pk_Seq=dctk.kbh_fk "+
				" inner join nhaphanphoi npp on npp.pk_seq=dctk.npp_fk "+
				" where dctk.pk_seq="+dctkid;
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();

		dbutils db = new dbutils();
		ResultSet rs = db.get(query);
		System.out.println("Get san pham :"+query);
		int i = 9;
		if(rs != null)
		{
			try 
			{
				cells.setColumnWidth(0, 25.0f);
				cells.setColumnWidth(1, 15.0f);
				cells.setColumnWidth(2, 15.0f);
				cells.setColumnWidth(3, 30.0f);
				cells.setColumnWidth(4, 45.0f);
				cells.setColumnWidth(5, 25.0f);
				cells.setColumnWidth(6, 15.0f);
				cells.setColumnWidth(7, 15.0f);
				cells.setColumnWidth(8, 15.0f);
				cells.setColumnWidth(2, 15.0f);
				cells.setColumnWidth(3, 30.0f);
				cells.setColumnWidth(4, 45.0f);
				cells.setColumnWidth(5, 25.0f);
				cells.setColumnWidth(6, 15.0f);
				cells.setColumnWidth(7, 15.0f);
				cells.setColumnWidth(8, 15.0f);
				for(int j = 0; j < 9; j++)
					cells.setRowHeight(j, 13.0f);

				Cell cell = null;
				while(rs.next())
				{





					cell = cells.getCell("A" + Integer.toString(i));
					cell.setValue(rs.getString("pk_seq"));
					cell = cells.getCell("B" + Integer.toString(i));
					cell.setValue(rs.getString("dvkd"));
					cell = cells.getCell("C" + Integer.toString(i));
					cell.setValue(rs.getString("kho"));
					cell = cells.getCell("D" + Integer.toString(i));
					cell.setValue(rs.getString("kbh"));
					cell = cells.getCell("E" + Integer.toString(i));
					cell.setValue(rs.getString("sitecode"));
					cell = cells.getCell("F" + Integer.toString(i));
					cell.setValue(rs.getString("nppten"));
					cell = cells.getCell("G" + Integer.toString(i));
					cell.setValue(rs.getString("ma"));
					cell = cells.getCell("H" + Integer.toString(i));
					cell.setValue(rs.getString("spten"));
					cell = cells.getCell("I" + Integer.toString(i));
					cell.setValue(rs.getFloat("dieuchinh"));
					cell = cells.getCell("J" + Integer.toString(i));
					cell.setValue(rs.getString("donvi"));
					cell = cells.getCell("K" + Integer.toString(i));
					cell.setValue(rs.getFloat("giamua"));

					cell = cells.getCell("L" + Integer.toString(i));
					cell.setValue(rs.getFloat("thanhtien"));

					cell = cells.getCell("M" + Integer.toString(i));
					cell.setValue(rs.getFloat("tonhientai"));

					cell = cells.getCell("N" + Integer.toString(i));
					cell.setValue(rs.getFloat("tonmoi"));

					i++;
				}
				rs.close();
			}
			catch(Exception e){
				e.printStackTrace();
				System.out.println("Loi Nek :"+e.toString());	
			}
		}

	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		OutputStream out = response.getOutputStream();
		IDieuchinhtonkhoList obj;

		String userId = request.getParameter("userId");
		obj = (IDieuchinhtonkhoList) new DieuchinhtonkhoList();
		if(userId == null){
			response.sendRedirect("/Best");
		}else{
			obj.setUserId(userId);
		}

		String 	action = request.getParameter("action");


		String	id = request.getParameter("id");
		Utility util = new Utility();
		if (action.equals("Xoa"))
		{	   
			String msg = "";
			String IdXoa = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("IdXoa")));
			if (IdXoa == null)
				IdXoa = "";
			System.out.println("IdXoa = "+ IdXoa);
			try{
				 msg= Delete(IdXoa,userId);
				}catch (Exception e) {
					System.out.println("ee:"+ e.getMessage());
					e.printStackTrace();
				}
			obj.setMsg(msg);
			
			 
		}

		if(action.equals("approve"))
		{
			String loi	=	Duyet(id, userId);
			if(!loi.trim().equals("")){
				IDieuchinhtonkho dctkBean = (IDieuchinhtonkho) new Dieuchinhtonkho();
				dctkBean.setUserId(userId);
				dctkBean.setId(id);
				dctkBean.setMessage(loi);
				dctkBean.initDisplay();
				dctkBean.setMessage(loi);
				session.setAttribute("dctkBean", dctkBean);
				String nextJSP = "/AHF/pages/Center/DieuChinhTonKhoDisplay.jsp";
				response.sendRedirect(nextJSP);    	
			}else{
				obj = new DieuchinhtonkhoList();
				obj.setUserId(userId);
				obj.init0();
				obj.createDctklist("");
				session.setAttribute("obj", obj);
		

				String nextJSP = "/AHF/pages/Center/DuyetDieuChinhTonKho.jsp";
				response.sendRedirect(nextJSP);
			}
		}
		else if(action.equals("excel")){

			IDieuchinhtonkho dctkBean = (IDieuchinhtonkho) new Dieuchinhtonkho();

			try
			{

				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition", "attachment; filename=DanhSachSanPham.xls");

				Workbook workbook = new Workbook();

				CreateStaticHeader(workbook);
				CreateStaticData(workbook,id);

				//Saving the Excel file
				workbook.save(out);
			}
			catch (Exception ex)
			{
				dctkBean.setMessage("Khong Xuat Ra Excel Duoc");
			}


			dctkBean.setUserId(userId);
			dctkBean.setId(id);

			dctkBean.initDisplay();
			session.setAttribute("dctkBean", dctkBean);
			String nextJSP = "/AHF/pages/Center/DieuChinhTonKhoDisplay.jsp";
			response.sendRedirect(nextJSP);   
			return;
		}else 
			if (action.equals("download"))
			{
				System.out.println("___Vao DOWNLOAD FILE....");
				String fileName = request.getParameter("tenfile");
				if (fileName == null)
					fileName = "";

				if (fileName.trim().length() > 0)
				{
					try
					{
						FileInputStream fileToDownload = new FileInputStream("C:\\java-tomcat\\data\\" + fileName);
						
						ServletOutputStream output = response.getOutputStream();
						response.setContentType("application/octet-stream");

						System.out.println(fileName);
						response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
						response.setContentLength(fileToDownload.available());
						int c;
						while ((c = fileToDownload.read()) != -1)
						{
							output.write(c);
						}
						output.flush();
						output.close();
						fileToDownload.close();
					} catch (Exception e)
					{
						System.out.println("___Loi DOWNLOAD file: " + e.getMessage());
					}
				}
			}
		else{

			
			String dvkdId =  util.antiSQLInspection(request.getParameter("dvkdId"));   
			if(dvkdId == null){  	
				dvkdId = "";
			}
			obj.setDvkdId(dvkdId);	        

			String kbhId = util.antiSQLInspection(request.getParameter("kbhId"));
			if (kbhId == null){
				kbhId = "";
			}
			obj.setKbhId(kbhId);

			String khoId = util.antiSQLInspection(request.getParameter("khoId"));
			if (khoId == null){
				khoId = "";
			}

			obj.setKhoId(khoId);

			String tungay = util.antiSQLInspection(request.getParameter("tungay"));
			if (tungay == null){
				tungay = "";
			}
			obj.setTungay(tungay);
			String sochungtu = util.antiSQLInspection(request.getParameter("sochungtu"));
			if (sochungtu == null){
				sochungtu = "";
			}
			obj.setSochungtu(sochungtu);
			String denngay = util.antiSQLInspection(request.getParameter("denngay"));
			if (denngay == null){
				denngay = "";
			}
			obj.setDenngay(denngay);

			String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
			if (trangthai == null){
				trangthai = "";
			}
			obj.setTrangthai(trangthai);
			
			String nhaPhanPhoi = util.antiSQLInspection(request.getParameter("nppId"));
			if(nhaPhanPhoi == null)
			{
				nhaPhanPhoi = "";
			}
			obj.setNppId(nhaPhanPhoi);

			obj.init0();
			
			obj.createDctklist(obj.createQueryString());
			session.setAttribute("obj", obj);    					

			String nextJSP = "/AHF/pages/Center/DuyetDieuChinhTonKho.jsp";
			response.sendRedirect(nextJSP);
		}
	}

	private boolean CheckNgayDuyet(String nppId, String ngayduyet)
	{
		Utility util = new Utility();
		String ngayksgn = util.ngaykhoaso(nppId);

		//System.out.print("\nNgay khoa so gan nhat la: " + this.ngaychungtu + "\n");

		if(ngayksgn.equals(""))
			return false;

		String[] ngayks = ngayksgn.split("-");
		String[] ngayct = ngayduyet.split("-");

		//System.out.print("\nNgay chung tu la: " + this.ngaychungtu + "\n");

		Calendar c1 = Calendar.getInstance(); //new GregorianCalendar();
		Calendar c2 = Calendar.getInstance(); //new GregorianCalendar();

		//NGAY KHOA SO

		c1.set(Integer.parseInt(ngayks[0]), Integer.parseInt(ngayks[1]) - 1, Integer.parseInt(ngayks[2]));

		//NGAY thuc hien tra hang
		c2.set(Integer.parseInt(ngayct[0]), Integer.parseInt(ngayct[1]) - 1, Integer.parseInt(ngayct[2]));

		c1.add(Calendar.DATE, 1);//ngay tra don hang bang ngay khoa so them 1 ngay
		//phep tinh ngay nhan hang - ngay khoa so

		long songay = ( c1.getTime().getTime() - c2.getTime().getTime()) / (24 * 3600 * 1000);

		if(songay < 0) //ngay chung tu khong duoc nho hon hoac bang ngay khoa so gan nhat 
		{
			//this.msg = "Ngay Tra Don Hang Phai Lon Hon Ngay Khoa So Gan Nhat.";
			return false;
		}
		return true;
	}
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	private String  Duyet(String dctkId, String userId)
	{		
		dbutils db = new dbutils();
		geso.dms.center.util.Utility util = new geso.dms.center.util.Utility();
		String ngayks ="";
		
		String npp_fk = "",ngaydc =  "";
		ResultSet rs = db.get("select npp_fk,ngaydc from dieuchinhtonkho where pk_seq = '"+dctkId+"'  and trangthai = 0");
		if(rs != null)
		{
			try {
				if(rs.next())
				{
					npp_fk = rs.getString("npp_fk")==null?"":rs.getString("npp_fk");
					ngaydc = rs.getString("ngaydc")==null?"":rs.getString("ngaydc");
				}
				rs.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
		
		}else
		{
			return "Vui lòng reload lại đơn hàng, trạng thái đơn hàng không hợp lệ!"; 
		}
		
		if(npp_fk.length() <= 0 || ngaydc.length() <= 0)
		{
			return "Lỗi không lấy được nhà phân phối hoặc ngày điều chỉnh !"; 
		}
		
		String query = "select MAX(NGAYKS) as ngayks from KHOASONGAY where NPP_FK = '"+npp_fk+"'";
		 rs = db.get(query);

		try {
			if(rs.next())
			{
				 ngayks = rs.getString("ngayks")==null?"":rs.getString("ngayks");
					rs.close();
			}
		} catch (SQLException e1) {
		
			e1.printStackTrace();
		}
		if(ngayks.length() <= 0)
		{
			return  "Nhà phân phối chưa khóa sổ hoặc ngày điều chỉnh phải lớn hơn ngày khóa sổ";
		
		}
		if(ngayks.compareTo(ngaydc) >= 0)
		{
			return  "Không thể chốt ĐCTK khi chưa khóa sổ nhà phân phối hoặc ngày điều chỉnh nhỏ hơn ngày khóa sổ !";
		}
		 
		 
		try
		{
			db.getConnection().setAutoCommit(false);
			
		
			query=	  	"	    update b set NGAYNHAPKHO = a.NGAYDC " +
			"		from DIEUCHINHTONKHO a inner join DIEUCHINHTONKHO_SP b on a.PK_SEQ = b.DIEUCHINHTONKHO_FK " +
			" 		where b.DIEUCHINH > 0 and b.DIEUCHINHTONKHO_FK =  " + dctkId;

			if(db.updateReturnInt(query) < 0)
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);		
				return "Khong The Duyet Dieu Chinh Ton Kho,Xay Ra Loi Cap Nhat Sau :"+ query;
			
			}
			
			query=	"	SELECT dcsp.ngaynhapkho ,dc.ngaydc,DC.KHO_FK, DC.KBH_FK, DC.NPP_FK, SANPHAM_FK,   DCSP.DIEUCHINH AS DIEUCHINH FROM DIEUCHINHTONKHO DC " +
					"	INNER JOIN DIEUCHINHTONKHO_SP DCSP ON DCSP.DIEUCHINHTONKHO_FK = DC.PK_SEQ" +
					" WHERE DC.PK_SEQ = '"+ dctkId +"' AND DCSP.DIEUCHINH != 0 and dc.trangthai = 0 " ;
					System.out.println("__+_"+query);
		    ResultSet ckRs = db.get(query);
		    while(ckRs.next())
		    {
		    	String kho_fk=ckRs.getString("kho_fk");
				String nppId=ckRs.getString("npp_fk");	
				String kbh_fk=ckRs.getString("kbh_fk");
				String sanpham_fk=ckRs.getString("sanpham_fk");
				 
				String ngaynhapkho  =ckRs.getString("ngaynhapkho");
				
				Double dieuchinh = ckRs.getDouble("dieuchinh");
				double soluong= 0;
				double booked=0;
				double availabe=0;
				if(dieuchinh<0){
					  soluong= dieuchinh;
					  booked= dieuchinh;
					  availabe=0;
				}else{
					 soluong= dieuchinh;
					  booked=0;
					  availabe=dieuchinh;
					    ngaynhapkho  =ckRs.getString("ngaydc");
					    
				}
				String  msg=util.Update_NPP_Kho(ngaydc,dctkId, "DUYỆT  DIEUCHINHTONKHO", db, kho_fk, sanpham_fk, nppId, kbh_fk,ngaynhapkho, soluong,  booked,availabe,0, 0.0);
				if(msg.length()>0)
				{
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return msg;
				}			
		    } 
		    
		    
		    
			query = "update dieuchinhtonkho set trangthai='1', nguoiduyet = '" +userId+ "' where pk_seq = '" + dctkId + "' and TrangThai=0  and isnull(nppDaChot,0 )  = '1'  ";

			if(db.updateReturnInt(query)!=1)
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);		
				return "Không thể chốt phiếu,vui lòng reload lại phiếu, Lỗi dòng lệnh : "+query;


			}
			query = "select count(*) as sl from DIEUCHINHTONKHO_SP where ngaynhapkho is null and DIEUCHINHTONKHO_FK ="+dctkId;
			ResultSet rsCheckNgayNhapKho = db.get(query);
			if(rsCheckNgayNhapKho != null)
			{
				if(rsCheckNgayNhapKho.next())
				{
					int sl = rsCheckNgayNhapKho.getInt("sl");
					if(sl>0)
					{
						db.getConnection().rollback();	
						return "Điều chỉnh tồn kho không có ngày nhập kho. Vui lòng liên hệ Admin ";
					}
				}
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}catch(Exception e)
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);		
			return "Khong The Duyet Dieu Chinh Ton Kho,Xay Ra Loi Cap Nhat Sau :"+ e.toString();
		}
		finally
		{
			db.shutDown();
		}
		return "";
	}
}
