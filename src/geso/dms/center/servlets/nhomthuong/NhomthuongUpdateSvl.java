package geso.dms.center.servlets.nhomthuong;

import geso.dms.center.beans.nhomthuong.INhomthuong;
import geso.dms.center.beans.nhomthuong.INhomthuongList;
import geso.dms.center.beans.nhomthuong.imp.Nhomthuong;
import geso.dms.center.beans.nhomthuong.imp.NhomthuongList;
import geso.dms.center.util.Utility;
import geso.dms.center.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class NhomthuongUpdateSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   private PrintWriter out;
	public NhomthuongUpdateSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		this.out = response.getWriter();
		dbutils db = new dbutils();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);
	    
	    
	    
	    
	      	
		String query = 	"select isnull(a.tungay,'') as tungay,isnull(a.denngay,'') as denngay,a.pk_seq, a.ten, a.diengiai, a.trangthai, a.type, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua, isnull(NHOMSPTT,'') as NHOMSPTT " +
						"from NHOMSANPHAMCHITIEU a, nhanvien b, nhanvien c where (a.type='4' or a.type='6') and a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ and a.pk_seq='" + id + "'";
		
        ResultSet rs =  db.get(query);
        INhomthuong nkmBean = null;
        try
        {
        	rs.next();
        	String[] param = new String[12];
        	param[0]= id;
        	param[1]= rs.getString("ten");
        	param[2]= rs.getString("diengiai");        	
        	param[3]= rs.getString("trangthai");
        	param[4]= rs.getString("ngaytao");
        	param[5]= rs.getString("ngaysua");
        	param[6]= rs.getString("nguoitao");
        	param[7]= rs.getString("nguoisua");
        	param[8]= rs.getString("type");
        	param[9]= rs.getString("tungay");
        	param[10]= rs.getString("denngay");
        	param[11]= rs.getString("NHOMSPTT");
        	
    	    nkmBean = new Nhomthuong(param);
    	    
    	    nkmBean.UpdateRS();
       		if(rs!=null){
       			rs.close();
       		}
       		db.shutDown();
       	}
        catch(Exception e){
	    	out.println(e.toString());
	    }
        
        if(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("delete")) != null)
        {
        	System.out.println("*delete.");
        	db = new dbutils();
			//db.getConnection().setAutoCommit(false);
			String ma = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("delete"));
			query = "delete from NHOMSANPHAMCHITIEU_SANPHAM where NSP_FK='" + ma + "' ";
			if(!db.update(query))
			{
				System.out.println("NhomthuongUpdateSvl: Không xóa được sản phẩm của nhóm sản phẩm chỉ tiêu");
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return;
			}
			
			query = "delete from NHOMSANPHAMCHITIEU where PK_SEQ='" + ma + "' ";
			if(!db.update(query))
			{
				System.out.println("NhomthuongUpdateSvl: Không xóa được nhóm sản phẩm chỉ tiêu");
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return;
			}
			
			//load lai trang
			query = "select  a.tungay,a.denngay,a.pk_seq, a.ten, a.type, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from NHOMSANPHAMCHITIEU a, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ and a.type = '4' ";
			db = new dbutils();
			ResultSet Dsnkm = db.get(query);
			INhomthuongList obj = new NhomthuongList();
			obj.setDsnkm(Dsnkm);
			session.setAttribute("obj", obj);

			session.setAttribute("userId", userId);
			

/*				db.getConnection().commit();
			db.getConnection().setAutoCommit(true);*/
			
			response.sendRedirect("/AHF/pages/Center/NhomThuong.jsp");
			Dsnkm = null;
			return;

        	
        }
        
        session.setAttribute("nkmBean", nkmBean);
   		session.setAttribute("userId", userId);
    	String nextJSP = "/AHF/pages/Center/NhomThuongUpdate.jsp";
    	
    	if(querystring.indexOf("display") >= 0)
    		nextJSP = "/AHF/pages/Center/NhomThuongDisplay.jsp";
    	else if(querystring.indexOf("copy") >= 0)
    	{
    		nextJSP = "/AHF/pages/Center/NhomThuongNew.jsp";
    	}
   		response.sendRedirect(nextJSP);
       
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
	    cell = cells.getCell("A3");
	    cell.setValue("Ten Nhom");
	    

	    cell = cells.getCell("A5");
	    cell.setValue("Ma SP");
	    cell = cells.getCell("B5");
	    cell.setValue("Ten SP");

	    
	    worksheet.setName("Danh sách  sản phẩm");
	}
	
	private void CreateStaticData(Workbook workbook,String nspid) 
	{
		String query="select nsp.ten, nsp.diengiai,sp.ma,sp.ten as tensp from NHOMSANPHAMCHITIEU_sanpham a inner join NHOMSANPHAMCHITIEU nsp on nsp.pk_Seq=nsp_fk "+ 
					 " inner join sanpham sp on sp.pk_seq=a.sp_fk "+
					 " where nsp.pk_seq="+ nspid;
		
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    
		dbutils db = new dbutils();
		ResultSet rs = db.get(query);
		System.out.println("Get san pham :"+query);
		int i = 6;
		if(rs != null)
		{
			try 
			{
				cells.setColumnWidth(0, 25.0f);
				cells.setColumnWidth(1, 15.0f);
				cells.setColumnWidth(2, 15.0f);
				
				
				
				Cell cell = null;
				while(rs.next())
				{
					
					cell = cells.getCell("B3" );
					cell.setValue(rs.getString("diengiai"));
					cell = cells.getCell("A" + Integer.toString(i));
					cell.setValue(rs.getString("ma"));
					cell = cells.getCell("B" + Integer.toString(i));
					cell.setValue(rs.getString("tensp"));

					i++;
				}
				rs.close();
				db.shutDown();
			}
			catch(Exception e){
				e.printStackTrace();
			System.out.println("Loi Nek :"+e.toString());	
			}
		}
	
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    HttpSession session = request.getSession();
	    java.io.OutputStream out = response.getOutputStream();
	    
	    INhomthuong nkmBean = new Nhomthuong();
	    
	    Utility Ult = new Utility();
		String userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
    	String ten = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ten"));
    	nkmBean.setTen(ten);
    	
    	
    	
    	
    	
    	String id = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nkmId"));
    	nkmBean.setId(id);

    	
    	String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
    	nkmBean.setTungay(tungay);
    	String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"));
    	nkmBean.setDenngay(denngay);
    	
    	String diengiai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai"));
    	nkmBean.setDiengiai(diengiai);

    	String dvkdId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId"));
    	if (!(dvkdId == null))
    		nkmBean.setDvkdId(dvkdId);
    
    	String kenhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId"));
    	if(kenhId == null)
    		kenhId ="";
    	nkmBean.setkenhId(kenhId);
    	
    	String nhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhId"));
    	if (!(nhId == null))
    		nkmBean.setNhId(nhId);

    	String clId= geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("clId"));
    	if (!(clId == null))
    		nkmBean.setClId(clId);
    	
    	String nsptt= geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nsptt"));
    	if (nsptt != null)
    		nkmBean.setNspTT("1");
    	else
    		nkmBean.setNspTT("0");

    	String type= geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("type"));
    	if (type == null) type = "4"; else type = type.trim();
    	nkmBean.setType(type);
    	
    	String ngaytao = getDateTime();
		nkmBean.setNgaytao(ngaytao);
		
		String ngaysua = ngaytao;
		nkmBean.setNgaysua(ngaysua);
		
		String nguoitao = userId;
		nkmBean.setNguoitao(userId);
		
		String nguoisua = nguoitao;
    	nkmBean.setNguoisua(nguoisua);
    	
    	String trangthai;
    	if(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"))!= null)
			trangthai = "1";
		else
			trangthai = "0";
		nkmBean.setTrangthai(trangthai);
			
		boolean error = false;
		if (ten.trim().length()> 0)
			nkmBean.setTen(ten);
		else{
			nkmBean.setMessage("Vui lòng nhập vào nhóm thưởng");
			error = true;
		}
		
		String[] sanpham = request.getParameterValues("sanpham");
		nkmBean.setSanpham(sanpham);
		
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		session.setAttribute("userId", geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		
		if(action.equals("xuatexel"))
		{
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
		    	nkmBean.setMessage("Khong Xuat Ra Excel Duoc");
		    }
	    	
			session.setAttribute("nkmBean", nkmBean);
			session.setAttribute("userId", userId);
			String nextJSP = "/AHF/pages/Center/NhomThuongUpdate.jsp";
			response.sendRedirect(nextJSP);
		}
		else 	if (action.equals("filter") || error){		
			nkmBean.UpdateRS();
    		session.setAttribute("nkmBean", nkmBean);
    		session.setAttribute("userId", userId);
    		if(id.length()>0){
    			response.sendRedirect("/AHF/pages/Center/NhomThuongUpdate.jsp");
    		}else{
    			response.sendRedirect("/AHF/pages/Center/NhomThuongNew.jsp");
    		}
    		
		}else{

			session.setAttribute("userId", nguoitao);
			if(id.length()>0){
				if (!nkmBean.updateNkm()){
					nkmBean.UpdateRS();				
					session.setAttribute("nkmBean", nkmBean);
					session.setAttribute("userId", userId);
					String nextJSP = "/AHF/pages/Center/NhomThuongUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else{					
					String query = "select  a.tungay,a.denngay,a.pk_seq, a.ten, a.type, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from NHOMSANPHAMCHITIEU a, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ and a.type = '4' ";
					dbutils db = new dbutils();
					ResultSet Dsnkm = db.get(query);
					INhomthuongList obj = new NhomthuongList();
					obj.setDsnkm(Dsnkm);
					session.setAttribute("obj", obj);

					session.setAttribute("userId", userId);

					response.sendRedirect("/AHF/pages/Center/NhomThuong.jsp");
					Dsnkm = null;
				}
			}else{
				if (!nkmBean.saveNewNkm()){			
					session.setAttribute("nkmBean", nkmBean);
		    		session.setAttribute("userId", userId);
					String nextJSP = "/AHF/pages/Center/NhomThuongNew.jsp";
		    		response.sendRedirect(nextJSP);
				}
				else{
					String query = "select  a.tungay,a.denngay,a.pk_seq, a.ten, a.type, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from NHOMSANPHAMCHITIEU a, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ and a.type = '4' ";
					dbutils db = new dbutils();
					ResultSet Dsnkm = db.get(query);
					INhomthuongList obj = new NhomthuongList();
					obj.setDsnkm(Dsnkm);
					session.setAttribute("obj", obj);

					session.setAttribute("userId", userId);

					response.sendRedirect("/AHF/pages/Center/NhomThuong.jsp");
					Dsnkm = null;
				}
				
			}
		}
		
	}   	  	    
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	/*private void  getNkmBeanList(List<INhomthuong> nkmlist){	
    	dbutils db = new dbutils();
	   	String query = "select a.pk_seq, a.ten, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from NHOMSANPHAMCHITIEU a, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ and a.type='1' order by pk_seq";
	   	ResultSet rs = db.get(query);
	   	try{	
	   		String[] param = new String[11];
    		INhomthuong nkmBean;
    		while (rs.next()){	    			
				param[0]= rs.getString("pk_seq");
				param[1]= rs.getString("ten");	
				param[2]= rs.getString("diengiai");
				param[3]= rs.getString("trangthai");
				param[4]= rs.getString("ngaytao");
				param[5]= rs.getString("ngaysua");
				param[6]= rs.getString("nguoitao");
				param[7]= rs.getString("nguoisua");			
				nkmBean = new Nhomthuong(param);					
				nkmlist.add(nkmBean);
    		}    		
	   	}catch(Exception e){}
	}
	*/

}
