package geso.dms.center.servlets.thongtinsanpham;

import geso.dms.center.beans.thongtinsanpham.IThongtinsanpham;
import geso.dms.center.beans.thongtinsanpham.IThongtinsanphamList;
import geso.dms.center.beans.thongtinsanpham.imp.Thongtinsanpham;
import geso.dms.center.beans.thongtinsanpham.imp.ThongtinsanphamList;
import geso.dms.center.db.sql.*;
import geso.dms.center.util.Utility;
import geso.dms.distributor.db.sql.dbutils;







import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.PivotFieldType;
import com.aspose.cells.PivotTable;
import com.aspose.cells.PivotTables;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

 public class ThongtinsanphamSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   PrintWriter out;
   IThongtinsanphamList obj;
private int items = 50;
private int splittings = 10;
   
   public ThongtinsanphamSvl() {
		super();
	}   
   
   private void settingPage(IThongtinsanphamList obj) {
		Utility util = new Utility();
		if(getServletContext().getInitParameter("items") != null){
	    	String i = getServletContext().getInitParameter("items").trim();
	    	if(util.isNumeric(i))
	    		items = Integer.parseInt(i);
	    }
	    
	    if(getServletContext().getInitParameter("splittings") != null){
	    	String i = getServletContext().getInitParameter("splittings").trim();
	    	if(util.isNumeric(i))
	    		splittings = Integer.parseInt(i);
	    }
	    
   	obj.setItems(items);
   	obj.setSplittings(splittings);
	}
	
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    
      
	    Utility util = new Utility();
	    out = response.getWriter();
	    obj = new ThongtinsanphamList();
   		
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String spId = util.getId(querystring);
	    
	    
	    //--- check user
	    String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		if (view == null) {
			view = "";
		}
		String param = "";
		if(view.trim().length() > 0) param ="&view="+view;
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "ThongtinsanphamSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
		 //--- check user 
	    
	    

	    if (action.equals("delete")){	   		  	    	
	    	Delete(spId);
	    	out.print(spId);
	    }
	   	
    	obj.setUserId(userId);
    	settingPage(obj);
		obj.init();
		
    	session.setAttribute("obj", obj);

		session.setAttribute("userId", userId);
    		
		response.sendRedirect("/AHF/pages/Center/ThongTinSanPham.jsp");
	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Utility util = new Utility();
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    OutputStream out = response.getOutputStream();
	    
		HttpSession session = request.getSession();
	    String userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	    System.out.println(userId);
	    
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	     
	  //--- check user
	    String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		if (view == null)
			view = "";
		//obj.setView(view);
		String param = "";
		if(view.trim().length() > 0) param ="&view="+view;
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			obj.DBClose();
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "ThongtinsanphamSvl", param, Utility.XEM ))
		{
			obj.DBClose();
			Utility.RedireactLogin(session, request, response);
		}
		 //--- check user
	    
	    
	    
	    if (action.equals("new"))
	    {
	    	// Empty Bean for distributor
	    	IThongtinsanpham spBean = (IThongtinsanpham) new Thongtinsanpham();
	    	spBean.setUserId(userId);
	    	spBean.CreateRS();
	    	// Save Data into session
	    	session.setAttribute("userId", userId);
	    	session.setAttribute("spBean", spBean);
    		
    		String nextJSP = "/AHF/pages/Center/ThongTinSanPhamNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    obj = new ThongtinsanphamList();
	    settingPage(obj);
	    obj.setUserId(userId);
	    if (action.equals("search"))
	    {
	    	obj.setQuery(getSearchQuery(request, obj));
			obj.setUserId(userId);
			obj.init();
			
	    	session.setAttribute("obj", obj);

    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/AHF/pages/Center/ThongTinSanPham.jsp");	    	
	    	
	    }
	    else 
		    if(action.equals("view") || action.equals("next") || action.equals("prev")){
		    	
		    	String search = getSearchQuery(request, obj);
		    	
		    	obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
		    	obj.setUserId(userId);
		    	obj.setQuery(search);
		    	obj.init();
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/AHF/pages/Center/ThongTinSanPham.jsp");
		    }
	    
	    if (action.equals("excel"))
	    {
	    	obj = new ThongtinsanphamList();
	    	obj.setUserId(userId);

	    	obj.setQuery(getSearchQuery(request, obj));
	    	
	    	try
		    {
		    	response.setContentType("application/vnd.ms-excel");
		        response.setHeader("Content-Disposition", "attachment; filename=DanhSachSanPham.xlsm");
		        //FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ThongTinSanPham.xlsm");
		      //  File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\ThongTinSanPham.xlsm");
			//	FileInputStream fstream = new FileInputStream(f) ;
				
				String strfstream = getServletContext().getInitParameter("path") + "\\ThongTinSanPham.xlsm";			
				FileInputStream fstream = new FileInputStream(strfstream);	
				
				
				Workbook workbook = new Workbook();
				workbook.open(fstream);
				workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

			     CreateStaticHeader(workbook, "Nguyen Duy Hai");
			     CreateStaticData(workbook, getSearchQuery2(request, "", ""));
			
			     //Saving the Excel file
			     workbook.save(out);
		    }
		    catch (Exception ex)
		    {
		        obj.setMsg("Không thể xuất excel !");
		        ex.printStackTrace();
		        obj.init();
				System.out.println("Không thể xuất excel !");
		    	session.setAttribute("obj", obj);

	    		session.setAttribute("userId", userId);
		    		
	    		response.sendRedirect("/AHF/pages/Center/ThongTinSanPham.jsp");	   
		    }
			 		
	    }
	    
	    if (action.equals("excelnew"))
	    {
	    	obj = new ThongtinsanphamList();
	    	
	    	obj.setQuery(getSearchQuery(request, obj));
	    	
	    	try
		    {
		    	response.setContentType("application/vnd.ms-excel");
		        response.setHeader("Content-Disposition", "attachment; filename=DanhSachSanPham.xlsm");
		        //FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ThongTinSanPham.xlsm");
		      //  File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\ThongTinSanPham.xlsm");
			//	FileInputStream fstream = new FileInputStream(f) ;
				
				String strfstream = getServletContext().getInitParameter("path") + "\\ThongTinSanPham.xlsm";			
				FileInputStream fstream = new FileInputStream(strfstream);	
				
				
				Workbook workbook = new Workbook();
				workbook.open(fstream);
				workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
				 
			     CreateStaticHeader(workbook, "Bùi Quang Trung");
			     
			     CreateStaticData(workbook, getSearchQuery2(request, "", ""));
			     System.out.println("cacac3");
			
			     //Saving the Excel file
			     workbook.save(out);
		    }
		    catch (Exception ex)
		    {
		        obj.setMsg("Không thể xuất excel !");
		        ex.printStackTrace();
		    }
	    	
			obj.setUserId(userId);
			obj.init();
			
	    	session.setAttribute("obj", obj);

    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/AHF/pages/Center/ThongTinSanPham.jsp");	    		
	    }
	    
	
	}   
		
	private String getSearchQuery(HttpServletRequest request, IThongtinsanphamList obj)
	{
		
		obj.getDataSearch().clear();
		
		String masp = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("masp"));
    	if (masp == null)
    		masp = "";
    	obj.setMasp(masp);
    	
    	String maddt = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("maddt"));
    	if (maddt == null)
    		maddt = "";
    	obj.setMasp(masp);

		String tensp = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tensp"));
    	if (tensp == null)
    		tensp = "";
    	obj.setTensp(tensp);
    	
    	String dvkdId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId"));
    	if (dvkdId == null)
    		dvkdId = "";    	
    	obj.setDvkdId(dvkdId);
    	
    	String nhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhId"));
    	if (nhId == null)
    		nhId = "";    	
    	obj.setNhId(nhId);
    	
//    	String clId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("clId"));
//    	if (clId == null)
//    		clId = "";    	
//    	obj.setClId(clId);
    	
    	String nganhhangId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngid"));
    	if (nganhhangId == null)
    		nganhhangId = "";    	
    	obj.setNganhhangId(nganhhangId);
    	
    	String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));   	
    	if (trangthai == null)
    		trangthai = "";    	
	
    	if (trangthai.equals("2"))
    		trangthai = "";
    	
    	obj.setTrangthai(trangthai);
    	Utility util = new Utility();
//	    String query = "select a.pk_seq, a.ma, a.ten, b.donvi, g.donvikinhdoanh as dvkd, g.pk_seq as dvkdId, c.pk_seq as nhId, c.ten as nhanhang, d.pk_seq as clId, d.ten as chungloai, f.giabanlechuan as giablc, a.trangthai from sanpham a, donvidoluong b, nhanhang c, chungloai d, banggiabanlechuan e, banggiablc_sanpham f, donvikinhdoanh g  where a.dvdl_fk=b.pk_seq and a.nhanhang_fk = c.pk_seq and a.chungloai_fk = d.pk_seq and e.pk_seq = f.bgblc_fk and a.pk_seq = f.sanpham_fk and c.dvkd_fk = g.pk_seq ";
    	//String query = "select a.pk_seq, a.ma, a.ten, b.donvi, g.donvikinhdoanh as dvkd, g.pk_seq as dvkdId, c.pk_seq as nhId, c.ten as nhanhang, d.pk_seq as clId, d.ten as chungloai, a.trangthai, i.giabanlechuan as giablc 
    	//from sanpham a, donvidoluong b, nhanhang c, chungloai d,  donvikinhdoanh g, banggiabanlechuan h, banggiablc_sanpham i  where a.dvdl_fk=b.pk_seq and a.nhanhang_fk = c.pk_seq and a.chungloai_fk = d.pk_seq and c.dvkd_fk = g.pk_seq and g.pk_seq = h.dvkd_fk and h.pk_seq= i.bgblc_fk and a.pk_seq = i.sanpham_fk "; 
    	/*String query = "\n select ROW_NUMBER() OVER(ORDER BY a.ma DESC) AS 'stt', a.pk_seq, a.ma,a.ma_ddt, " +
    			"\n a.ten, a.trongluong, a.thetich, b.donvikinhdoanh  as dvkd,b.pk_seq as dvkdId,isnull(c.ten,'') as chungloai, " +
    			"\n e.pk_seq as nhId, d.donvi,isnull(e.ten,'') as nhanhang,d.pk_seq as clId,a.trangthai ";//,f.giabanlechuan as giablc
		query += "\n from sanpham a inner join donvikinhdoanh b on a.dvkd_fk = b.pk_seq " +
				"\n left join chungloai c on a.chungloai_fk = c.pk_seq " +
				"\n left join donvidoluong d on a.dvdl_fk = d.pk_seq " +
				"\n left join nhanhang e on a.nhanhang_fk = e.pk_seq ";*/
		//query = query  + " left join banggiablc_sanpham f on a.pk_seq = f.sanpham_fk left join banggiabanlechuan g on f.bgblc_fk = g.pk_seq left join quycach qc on a.pk_seq = qc.sanpham_fk where a.pk_seq > 0 ";
		/*query = query  + " left join quycach qc on a.pk_seq = qc.sanpham_fk */
    	
    	String query=  "\n select ROW_NUMBER() OVER(ORDER BY a.ma DESC) AS 'stt', a.pk_seq, a.ma, a.ma_ddt," +
        		"\n a.ten,b.donvikinhdoanh as dvkd,b.pk_seq as dvkdId,isnull(c.ten,'') as NGANHHANG, e.pk_seq as nhId," +
        		"\n d.donvi,isnull(e.ten,'') as nhanhang,d.pk_seq as clId,a.trangthai";//, isnull(f.giabanlechuan, '0') as giablc ";
		query += "\n from sanpham a left join donvikinhdoanh b on a.dvkd_fk = b.pk_seq " +
				"\n left join NGANHHANG c on a.NGANHHANG_fk = c.pk_seq " +
				"\n left join donvidoluong d on a.dvdl_fk = d.pk_seq " +
				"\n left join nhanhang e on a.nhanhang_fk = e.pk_seq ";
		 query = query +" where a.pk_seq > 0 ";
			query+= "\n and a.pk_seq in "+util.quyen_sanpham(obj.getUserId());        
    	
			
		if (masp.length()>0){
			query = query + "\n and upper(a.ma) like upper(?)";	
			
			obj.getDataSearch().add( "%" + masp + "%"  );
    	}
    	
    	if (maddt.length()>0){
			query = query + "\n and upper(a.ma_ddt) like upper(?)";
			obj.getDataSearch().add( "%" + maddt + "%"  );
    	}

	    if (tensp.length()>0){
	    	 
			query = query + "\n and upper(dbo.ftBoDau(a.ten)) like upper(?)";
			obj.getDataSearch().add( "%" + util.replaceAEIOU(tensp.trim()).trim() + "%"  );
    	}
    	
    	if (dvkdId.length()>0){
			query = query + "\n and b.pk_seq = ? ";	
			obj.getDataSearch().add(dvkdId );
    	}

    	if (nhId.length()>0){
			query = query + "\n and e.pk_seq = ?"; 
			obj.getDataSearch().add( nhId );
    	}
    	
//    	if (clId.length()>0){
//			query = query + " and c.pk_seq = ?";  
//			obj.getDataSearch().add( clId  );
//    	}
    	
    	if (nganhhangId.length()>0){
			query = query + " and c.pk_seq = ?";  
			obj.getDataSearch().add( nganhhangId  );
    	}

    	if(trangthai.length() > 0){
    		query = query + "\n and a.trangthai = ?";  
    		obj.getDataSearch().add(trangthai );
    	}
    
    	/*
    	query = query + "union select a.pk_seq, a.ma, a.ten, '' as donvi, '' as dvkd, null as dvkdId, null as nhId, null as nhanhang, null as clId, null as chungloai, a.trangthai, '0' as giablc from sanpham a  where a.pk_seq not in (select a.pk_seq from sanpham a, donvidoluong b, nhanhang c, chungloai d,  donvikinhdoanh g, banggiabanlechuan h, banggiablc_sanpham i  where a.dvdl_fk=b.pk_seq and a.nhanhang_fk = c.pk_seq and a.chungloai_fk = d.pk_seq and c.dvkd_fk = g.pk_seq and g.pk_seq = h.dvkd_fk and h.pk_seq= i.bgblc_fk and a.pk_seq = i.sanpham_fk) ";
    	if (masp.length()>0){
			query = query + " and upper(a.ma) like upper('%" + masp + "%')";
			
    	}

	    if (tensp.length()>0){
			query = query + " and upper(a.ten) like upper('%" + tensp + "%')";
			
    	}
    	
    	if(trangthai.length() > 0){
    		query = query + " and a.trangthai = '" + trangthai + "'";
    		
    	}
    	
    	query = query + " order by trangthai, ma";
    	 */
    	
    	System.out.println("Chuoi Tim Kiem: "+query);
    	return query;
	}	
	
	boolean kiemtra(String sql,String id)
	{
		dbutils db =new dbutils();
		String query = "select count(*) as num from " + sql + " where sanpham_fk ='"+ id +"'";
	
    	ResultSet rs = db.get(query);
		try 
		{	
			//kiem tra ben san pham
			while(rs.next())
			{ 
				if(rs.getString("num").equals("0"))
					return false;
			}
		} 
		catch (SQLException e) {}
		return true;
	}
	private void Delete(String id){
		dbutils db = new dbutils();
		/*ResultSet rs1 = db.get("select count(sanpham_fk) as num from donhang_sanpham where sanpham_fk='"+ id + "'");
		try{
			rs1.next();			
			if (rs1.getString("num").equals("0")){		
				db.update("delete from nhomsanpham_sanpham where sp_fk='" + id + "'");
				db.update("delete from quycach where sanpham_fk='" + id + "'");
				db.update("delete from banggiablc_sanpham where sanpham_fk='" + id + "'");
				db.update("delete from bgbanlenpp_sanpham where sanpham_fk='" + id + "'");
				db.update("delete from bgmuanpp_sanpham where sanpham_fk='" + id + "'");
				db.update("delete from banggiast_sanpham where sanpham_fk='" + id + "'");
				db.update("delete from nhapp_kho where sanpham_fk='" + id + "'");
				db.update("delete from sanpham where pk_seq='" + id + "'");
				db.shutDown();
			}
		}catch(java.sql.SQLException e){}
		*/
		if(kiemtra("nhaphang_sp",id)||kiemtra("donhangtrave_sanpham",id) || kiemtra("dieukienkm_sanpham",id) || kiemtra("phieuxuatkho_spkm",id) || kiemtra("bosanpham_sanpham",id) || kiemtra("trakhuyenmai_sanpham",id)||kiemtra("donhangthuhoi_sanpham",id)||kiemtra("phieuxuatkho_sanpham",id) || kiemtra("denghidathang_sp",id) ||kiemtra("phieuthuhoi_sanpham",id) || kiemtra("donhang_sanpham",id) || kiemtra("dieuchinhtonkho_sp",id) || kiemtra("dontrahang_sp",id) || kiemtra("bosanpham_sanpham",id) ||kiemtra("phieuxuatkho_spkm",id) )
		{
         obj.setMsg("Sản phẩm này đã tồn tại trong các đơn hàng rồi, nên không thể xóa được"); 			
		}
		else
		{	
			String sql = "delete from nhomsanpham_sanpham where sp_fk='" + id + "'";
			if(!db.update(sql))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				obj.setMsg("Sản phẫm đã tồn tại trong nhóm sản phẩm rồi, nên không thể xóa được");
				return;
			}
			
			sql = "delete from quycach where sanpham_fk='" + id + "'";
			if(!db.update(sql))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				obj.setMsg("Sản phẫm đã tồn tại trong quy cách rồi, nên không thể xóa được");
				return;
			}
			
			sql = "DELETE FROM BANGGIABLC_SANPHAM WHERE SANPHAM_FK='" + id + "'";
			if(!db.update(sql))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				obj.setMsg("Sản phẫm đã tồn tại trong bảng giá bán lẻ rồi, nên không thể xóa được");
				return;
			}
			
			
			sql = "delete from bgbanlenpp_sanpham where sanpham_fk='" + id + "'";
			if(!db.update(sql))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				obj.setMsg("Sản phẫm đã tồn tại trong bảng giá bán lẻ rồi, nên không thể xóa được");
				return;
			}
			
			sql = "delete from bgmuanpp_sanpham where sanpham_fk='" + id + "'";
			if(!db.update(sql))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				obj.setMsg("Sản phẫm đã tồn tại trong bảng giá mua rồi, nên không thể xóa được");
				return;
			}
			
			sql = "delete from banggiast_sanpham where sanpham_fk='" + id + "'";
			if(!db.update(sql))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				obj.setMsg("Sản phẫm đã tồn tại trong bảng giá siêu thị rồi, nên không thể xóa được");
				return;
			}
			
			sql = "delete from nhapp_kho where sanpham_fk='" + id + "'";
			if(!db.update(sql))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				obj.setMsg("Sản phẫm đã tồn tại trong kho nhà phân phối rồi, nên không thể xóa được");
				return;
			}
			
			sql = "delete from nhanvien_sanpham where sanpham_fk = '" + id + "'";
			if(!db.update(sql))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				obj.setMsg("Sản phẫm đã được bán bởi nhân viên rồi, nên không thể xóa được");
				return;
			}
			
			sql = "delete from sanpham where pk_seq='" + id + "'";
			if(!db.update(sql))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				obj.setMsg("Sản phẫm đã tồn tại trong kho rồi, nên không thể xóa được");
				return;
			}
			
			db.shutDown();
		}		
	}
	
	private void CreateStaticHeader(Workbook workbook, String UserName) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	   	    
	    Cell cell = cells.getCell("A1"); 
	    cell.setValue("Danh sách sản phẩm");
	   
	    cell = cells.getCell("A3");
	    cell.setValue("Ngày tạo: " + this.getDateTime());
	    //cell = cells.getCell("A4");
	    //cell.setValue("User:  " + UserName);
	    
	    //tieu de
	    cell = cells.getCell("AA1");
	    cell.setValue("DonViKinhDoanh");
	    

	    cell = cells.getCell("AB1");
	    cell.setValue("NhanHang");
	    cell = cells.getCell("AC1");
	    cell.setValue("ChungLoai");
	    cell = cells.getCell("AD1");
	    cell.setValue("MaSanPham");
	    cell = cells.getCell("AE1");
	    cell.setValue("TenSanPham");
	    cell = cells.getCell("AF1");
	    cell.setValue("DonViDoLuong");
	    cell = cells.getCell("AG1");
	    cell.setValue("GiaBanLeChuan");
	  //Them trong luong +The tich
	    cell = cells.getCell("AH1");
	    cell.setValue("QuyCach");
	    
	    cell = cells.getCell("AI1");
	    cell.setValue("TinhTrang");
	    
	    cell = cells.getCell("AJ1");
	    cell.setValue("Vung");
	    
	    cell = cells.getCell("AK1");
	    cell.setValue("KhuVuc");
	    
	    cell = cells.getCell("AL1");
	    cell.setValue("ngayhieulucbgblc");
	    
	    worksheet.setName("sheet1");
	}
	
	private void CreateStaticData(Workbook workbook, String query) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    
		dbutils db = new dbutils();
		ResultSet rs = db.get(query);
		System.out.println("Get san pham :"+query);
		int i = 2;
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
				cells.setColumnWidth(9, 15.0f);
				cells.setColumnWidth(10, 15.0f);
				for(int j = 0; j < 11; j++)
					cells.setRowHeight(j, 13.0f);
				
				Cell cell = null;
				while(rs.next())
				{
					String dvkd = "";
					if(rs.getString("dvkd")!= null)
						dvkd = rs.getString("dvkd");
					
					String nhanhang = "";
					if(rs.getString("nhanhang") != null)
						nhanhang = rs.getString("nhanhang");
					
					String chungloai = "";
					if(rs.getString("chungloai") != null)
						chungloai = rs.getString("chungloai");
						
					String masp = "";
					if(rs.getString("ma") != null)
						masp = rs.getString("ma");
					
					String tensp = "";
					if(rs.getString("ten") != null)
						tensp = rs.getString("ten");
					
					String dvdl = "";
					if(rs.getString("donvi") != null)
						dvdl = rs.getString("donvi");
					
					String gblc = "";
					if(rs.getString("giablc") != null)
						gblc = rs.getString("giablc");
					
					String quycach = "";
					if(rs.getString("quycach") != null)
						quycach = rs.getString("quycach");
					String trangthai="Hoạt động";
					if(rs.getString("trangthai").equals("0")){
						trangthai="Không hoạt động";
					}
					
					NumberFormat formatTheTich = new DecimalFormat("#,###,##0.00000"); 
					NumberFormat formatKhoiLuong = new DecimalFormat("#,###,##0.000"); 
					//Them trong luong+the tich
					String trongluong="";
					if(rs.getString("trongluong")!=null)
						trongluong=formatKhoiLuong.format((rs.getDouble("trongluong")));
						
					String thetich="";
					if(rs.getString("thetich")!=null)
						thetich=formatTheTich.format((rs.getDouble("thetich")));
					
					String vung="";
					if(rs.getString("vung")!=null)
						vung = rs.getString("vung");
					
					String khuvuc="";
					if(rs.getString("khuvuc")!=null)
						khuvuc = rs.getString("khuvuc");
					
					String ngayhieulucbgblc="";
					if(rs.getString("ngayhieulucbgblc")!=null)
						ngayhieulucbgblc = rs.getString("ngayhieulucbgblc");
					
					cell = cells.getCell("AA" + Integer.toString(i));
					cell.setValue(dvkd);
					cell = cells.getCell("AB" + Integer.toString(i));
					cell.setValue(nhanhang);
					cell = cells.getCell("AC" + Integer.toString(i));
					cell.setValue(chungloai);
					cell = cells.getCell("AD" + Integer.toString(i));
					cell.setValue(masp);
					cell = cells.getCell("AE" + Integer.toString(i));
					cell.setValue(tensp);
					cell = cells.getCell("AF" + Integer.toString(i));
					cell.setValue(dvdl);
					cell = cells.getCell("AG" + Integer.toString(i));
					cell.setValue(gblc);
					//Trong luong
					cell = cells.getCell("AH" + Integer.toString(i));
					cell.setValue(quycach);
					//The tich
					cell = cells.getCell("AI" + Integer.toString(i));
					cell.setValue(trangthai);
					
					cell = cells.getCell("AJ" + Integer.toString(i));
					cell.setValue(vung);
					//The tich
					cell = cells.getCell("AK" + Integer.toString(i));
					cell.setValue(khuvuc);
					
					cell = cells.getCell("AL" + Integer.toString(i));
					cell.setValue(ngayhieulucbgblc);
				
					i++;
				}
				rs.close();
			}
			catch (SQLException e){ e.printStackTrace(); }
		}
		/*
		//create pivot
		PivotTables pivotTables = worksheet.getPivotTables();

	    //Adding a PivotTable to the worksheet
		String pos = Integer.toString(i-1);		
	    int index = pivotTables.add("=A8:H" + pos,"A8","DanhSachSanPham");

	    //Accessing the instance of the newly added PivotTable
	    PivotTable pivotTable = pivotTables.get(index);

	    //Unshowing grand totals for rows.
	    pivotTable.setRowGrand(false);

	    //Draging the first field to the row area.
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 0);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 1);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 2);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 3);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 4);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 5);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 6);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 7);
	    */
		db.shutDown();
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private String getSearchQuery2(HttpServletRequest request, String pages, String soDong)
	{
		String masp = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("masp"));
    	if (masp == null)
    		masp = "";
    	obj.setMasp(masp);

		String tensp = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tensp"));
    	if (tensp == null)
    		tensp = "";
    	obj.setTensp(tensp);
    	
    	String dvkdId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId"));
    	if (dvkdId == null)
    		dvkdId = "";    	
    	obj.setDvkdId(dvkdId);
    	
    	String nhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhId"));
    	if (nhId == null)
    		nhId = "";    	
    	obj.setNhId(nhId);
    	
    	String clId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("clId"));
    	if (clId == null)
    		clId = "";    	
    	obj.setClId(clId);
    	
    	String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));   	
    	if (trangthai == null)
    		trangthai = "";    	
	
    	if (trangthai.equals("2"))
    		trangthai = "";
    	
    	obj.setTrangthai(trangthai);

    	String query ="\n select a.pk_seq,a.trongluong,a.thetich, a.ma, a.ten, b.donvikinhdoanh  as dvkd,b.pk_seq as dvkdId,c.ten as chungloai, e.pk_seq as nhId, d.donvi,e.ten as nhanhang,d.pk_seq as clId,a.trangthai,f.giabanlechuan as giablc, [dbo].[GetSanPham_QuyCach](a.pk_seq) as quycach, isnull(v.ten,'') as vung,isnull(kv.ten,'') as khuvuc, g.tungay as ngayhieulucbgblc  ";
		query = query + "\n from sanpham a left join donvikinhdoanh b on a.dvkd_fk = b.pk_seq left join chungloai c on a.chungloai_fk = c.pk_seq left join donvidoluong d on a.dvdl_fk = d.pk_seq left join nhanhang e on a.nhanhang_fk = e.pk_seq ";
		query = query  + "\n left join banggiablc_sanpham f on a.pk_seq = f.sanpham_fk left join banggiabanlechuan g on f.bgblc_fk = g.pk_seq  left join BANGGIABANLECHUAN_VUNG bgv on bgv.BANGGIABANLECHUAN_FK = f.BGBLC_FK left join BANGGIABANLECHUAN_KHUVUC bgkv on bgkv.BANGGIABANLECHUAN_FK = f.BGBLC_FK "
				+ "\n left join vung v on v.pk_seq = bgv.Vung_fk left join khuvuc kv on kv.pk_seq = bgkv.khuvuc_fk "
				+ "\n where 1 = 1 ";
		    
    	if (masp.length()>0){
			query = query + " and upper(a.ma) like upper('%" + masp + "%')";	
    	}

	    if (tensp.length()>0){
	    	   Utility util = new Utility();
			query = query + " and upper(a.ten) like upper(N'%"+ util.replaceAEIOU(tensp) + "%')";	
    	}
    	
    	if (dvkdId.length()>0){
			query = query + " and b.pk_seq = '" + dvkdId + "'";	
    	}

    	if (nhId.length()>0){
			query = query + " and e.pk_seq = '" + nhId + "'";   		
    	}
    	
    	if (clId.length()>0){
			query = query + " and c.pk_seq = '" + clId + "'";    		
    	}

    	if(trangthai.length() > 0){
    		query = query + " and a.trangthai = '" + trangthai + "'";   		
    	}
    	System.out.println("Excel: "+query);
    	return query;
    	
	}	
	private String FormatNumber(double number, int round)
	{
		//System.out.println("Truoc kho Format: " + number);
		String format = "#,###,###";
		if(round >= 1)
			format += ".";
		
		for(int i = 0; i < round; i++)
			format += "0";
		
		//System.out.println("Chuoi Format: " + format);
		
		DecimalFormat df = new DecimalFormat(format);
		String result = df.format(number);
		
		if(number > 999)
		{
			//result = result.replaceAll(".", "+");
			result = result.replaceAll(",", ".");
			if(round > 0)
				result = result.substring(0, result.length() - (round + 1)) + "," + result.substring(result.length() - round);
			//result = result.replaceFirst("-", ",");
		}
		else
			result = result.replaceAll(",", ".");
			
		//System.out.println("ket qua: " + result);
		return result;
	}
}