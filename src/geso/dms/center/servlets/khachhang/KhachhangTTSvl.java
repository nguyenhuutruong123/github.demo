package geso.dms.center.servlets.khachhang;

import geso.dms.center.beans.khachhang.*;
import geso.dms.center.beans.khachhang.imp.*;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class KhachhangTTSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private int items = 50;
	private int splittings = 20;
	
	
    public KhachhangTTSvl() 
    {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");
		
		String nppId = (String) session.getAttribute("nppId");
		
		System.out.println("userId : "+userId+" & userTen : "+userTen+" & nppId : "+nppId);
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
		
		IKhachhangList obj;
		PrintWriter out; 
		
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out  = response.getWriter();

	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    //if ()
	    obj = new KhachhangList();
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String khId = util.getId(querystring);

	    if (action.equals("delete")){	   		  	    	
	    	Delete(khId);
	    	out.print(khId);
	    }
	    else
	    	if (action.equals("deletehinh")){	   		  	    	
	    		if(!DeleteHinhDD(khId,userId))
	    		{
	    			obj.setMsg("Lỗi ! Xóa hình đại diện không thành công !");
	    		}
	    		else
	    			obj.setMsg("Xóa hình đại diện thành công !");
	    	
	    }
	   	String isMt =    util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("isMT")));
	   	System.out.println("isMT: "+isMt);
	    obj.setIsMT(isMt==null?"":isMt);
	    
	    settingPage(obj);
	    	
	    obj.setUserId(userId);
	    obj.init("");
	
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Center/KhachHang.jsp";
		response.sendRedirect(nextJSP);
		}
	}

	private void settingPage(IKhachhangList obj) {
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
		
			OutputStream out = response.getOutputStream();	
			
		IKhachhangList obj = new KhachhangList();
		//PrintWriter out; 
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    //out = response.getWriter();
	    Utility util = new Utility();
	    userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		String isMt =    util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("isMT")));
	    obj.setIsMT(isMt==null?"":isMt);
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    
	    
	    //out.println(action); 
	          
	    if (action.equals("new")){	  
	    	
	    	// Empty Bean for Center
	    	IKhachhang khBean = (IKhachhang) new Khachhang("");	 
	    	khBean.setIsMT(isMt);
	    	khBean.setUserId(userId);
	    	khBean.createRS();
	    	// Save Data into session
	    	session.setAttribute("khBean", khBean);
    		String nextJSP = "/AHF/pages/Center/KhachHangNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    
	    settingPage(obj);
	    
	    if (action.equals("search"))
	    {	    
	    	//search = search + " and a.npp_fk='" + userId + "' order by a.ten";
	    	
	    	//obj = new KhachhangList(search);
	    	obj.setUserId(userId);
	    	String search = getSearchQuery(request, obj);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/AHF/pages/Center/KhachHang.jsp");	    		    	
	    }
	    else if (action.equals("excel"))
		{
	    	String search = getSearchQuery(request, obj);

			try
			{
				response.setContentType("application/vnd.ms-excel");
				//response.setHeader("Content-Disposition", "attachment; filename=DanhsachKhachhang_"+getDateTime()+".xls");*/
				//response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=danhsachkhachhang.xls");
				Workbook workbook = new Workbook();

				CreateStaticHeader(workbook, "Nguyen Duy Hai");
				CreateStaticData(workbook, getQueryExcel(request, (IKhachhangList) obj));

				//Saving the Excel file
				workbook.save(out);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}

			obj.setUserId(userId);


			session.setAttribute("obj", obj);

			session.setAttribute("userId", userId);
			return;

			//response.sendRedirect("/HTP/pages/Distributor/KhachHang.jsp");	    		
		}
	    else 

	    if(action.equals("view") || action.equals("next") || action.equals("prev")){
	    	
	    	
	    	System.out.println("PAGE: "+geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting")));
	    	obj.setUserId(userId);
	    	obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
	    	//String search = getSearchQuery(request, obj);
	    	obj.init("");
	    
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	session.setAttribute("obj", obj);
	    	response.sendRedirect("/AHF/pages/Center/KhachHang.jsp");
	    }
	    
	    
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IKhachhangList obj)
	{		
		Utility util = new Utility();
		String ten = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khTen")));
    	if ( ten == null)
    		ten = "";
    	obj.setTen(ten);    	       
    	
    	String hchId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("hchTen")));
    	if (hchId == null)
    		hchId = "";    	
    	obj.setHchId(hchId);
    	
    	String kbhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhTen")));
    	if (kbhId == null)
    		kbhId = "";    	
    	obj.setKbhId(kbhId);    	
    	
    	String vtchId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vtchTen")));
    	if (vtchId == null)
    		vtchId = "";    	
    	obj.setVtId(vtchId);
    	
    	String lchId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("lchTen")));
    	if (lchId == null)
    		lchId = "";    	
    	obj.setLchId(lchId);
    	
    	String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
		if (tungay == null)
			tungay = "";
		obj.setTungay(tungay);

		String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
		if (denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
    	
    	
		String vungId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")));
		if (vungId == null)
			vungId = "";
		obj.setvungId(vungId);
    	
    	
    	String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("NhaPhanPhoi")));
    	if ( nppId == null)
    		nppId = "";
    	obj.setNppId(nppId);
    	
    	String ttId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tpId")));
    	if ( ttId == null)
    		ttId = "";
    	obj.setTpId(ttId);
    	
    	String qhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("qhId")));
    	if ( qhId == null)
    		qhId = "";
    	obj.setQhId(qhId);
    	
    	String nvbhid = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nvbhid")));
    	if ( nvbhid == null)
    		nvbhid = "";
    	obj.setNvbhId(nvbhid);
    	
       	String isMt =    util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("isMT")));
	  
	    obj.setIsMT(isMt==null?"":isMt);
    	
		String query = "select ROW_NUMBER() OVER(ORDER BY a.pk_seq DESC) AS 'stt', a.pk_seq as khId,a.smartid, a.ten as khTen, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua, d.chietkhau, d.pk_seq as mckId,isnull(a.ANHCUAHANG,'') as ANHCUAHANG," 
			+ "\n e.diengiai as kbhTen, e.pk_seq as kbhId, f.hang as hchTen, f.pk_seq as hchId, g.loai as lchTen, g.pk_seq as lchId, h.ten as nppTen, h.pk_seq as nppId,"
			+ "\n k.sotienno as ghcnTen, k.pk_seq as ghcnId, l.ten as bgstTen, l.pk_seq as bgstId, m.vitri as vtchTen, m.pk_seq as vtchId, a.dienthoai, a.diachi, n.ten as tinhthanh, o.ten as quanhuyen,isnull(ismt,'') as ismt, isnull((select top(1) ten from DAIDIENKINHDOANH where pk_seq = a.DDKD_FK),'') as ddkdtao,isnull((select top(1) ten from DAIDIENKINHDOANH where pk_seq = a.DDKDTAO_FK),'') as ddkdsua"
			+ "\n ,isnull(isPDASua,'') as  isPDASua  "
			+ "\n from khachhang a inner join nhanvien b on a.nguoitao = b.pk_seq inner join nhanvien c on a.nguoisua = c.pk_seq left join mucchietkhau d on a.chietkhau_fk = d.pk_seq"
			+ "\n left join kenhbanhang e on a.kbh_fk = e.pk_seq left join hangcuahang f on a.hch_fk = f.pk_seq left join loaicuahang g on a.lch_fk = g.pk_seq"
			+ "\n left join nhaphanphoi h on a.npp_fk = h.pk_seq "
			+ "\n left join gioihancongno k on a.ghcn_fk = k.pk_seq left join banggiasieuthi l on a.bgst_fk = l.pk_seq left join vitricuahang m on a.vtch_fk = m.pk_seq "
			+ "\n left join tinhthanh n on a.tinhthanh_fk = n.pk_seq " 
			+ "\n left join quanhuyen o on a.quanhuyen_fk = o.pk_seq "
			+ "\n left join khuvuc_quanhuyen kv on kv.quanhuyen_fk = o.pk_seq "
			+ "\n left join khuvuc v on v.pk_seq = kv.khuvuc_fk "
			+ "\n where 1=1 "; //where a.npp_fk='"+ nppId +"'

    	
    	if (ten.length()>0)
    	{ 
    		
			//query = query + " and ( upper(a.pk_seq) like upper(N'%" + util.replaceAEIOU(ten) + "%') or upper(a.ten) like upper(N'%" + util.replaceAEIOU(ten) + "%')) ";	
			query = query + " and ( upper(a.SmartId) like upper(N'%" + util.replaceAEIOU(ten) + "%') or upper((a.timkiem)) like upper(N'%" + util.replaceAEIOU(ten) + "%')) ";			
    	}
    	
    	if (kbhId.length()>0){
			query = query + " and a.kbh_fk ='" + kbhId + "'";			
    	}
    	
    	if (nppId.length()>0){
			query = query + " and a.npp_fk ='" + nppId + "'";			
    	}
    	
    	if (hchId.length()>0){
			query = query + " and a.hch_fk='" + hchId + "'";			
    	}
    	
    	if (vtchId.length()>0){
			query = query + " and a.vtch_fk='" + vtchId + "'";			
    	}
    	
    	if (lchId.length()>0){
			query = query + " and a.lch_fk='" + lchId + "'";			
    	}
    	
    	if (ttId.length()>0){
			query = query + " and a.tinhthanh_fk='" + ttId + "'";			
    	}
    	
    	if (qhId.length()>0){
			query = query + " and a.quanhuyen_fk='" + qhId + "'";			
    	}
    	
    	if (tungay.length() > 0) {
			query = query + " and a.ngaytao >= '" + tungay + "'";
		}
		if (denngay.length() > 0) {
			query = query + " and a.ngaytao <= '" + denngay + "'";
		}
		
		if (vungId.length() > 0) {
			query = query + " and v.vung_fk <= '" + vungId + "'";
		}
    	
    	
    	if (nvbhid.length()>0){
			query = query + " and exists (select 1 from KHACHHANG_TUYENBH khtbh inner join TUYENBANHANG tbh on khtbh.TBH_FK = tbh.PK_SEQ where khtbh.KHACHHANG_FK = a.PK_SEQ and tbh.DDKD_FK = '"+nvbhid+"'  )";			
    	}
    	if(isMt.length() > 0)
    		query = query + " and a.ismt = '"+isMt+"' ";
    	else
    		query += " and a.npp_fk in "+ util.quyen_npp(obj.getUserId()) ;    	
    	System.out.println("Query lay khach hang: " + query + "\n");

    	
    	return query;
	}	
	
	private void Delete(String id)
	{
		dbutils db = new dbutils();
		//Lay ID nhapp tu UserId
		/*String sql = "select a.pk_seq from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + userId + "'";
		ResultSet rs = db.get(sql);
		System.out.println("1.sql : "+sql);
		String nppId = "";
		try {
			rs.next();
			nppId = rs.getString("pk_seq");
			System.out.println("2.sql : "+nppId);
			rs.close();
		} 
		catch(Exception e) {}*/
    		
		//Xoa Cac Bang Con Truoc 
		try{
			String sql = "delete from nhomkhachhang_khachhang where kh_fk='" + id + "'";			
		db.update(sql);
		System.out.println("delete 1 : "+sql);
			sql = "delete from khachhang_tuyenBH where khachhang_fk='" + id + "'";
		db.update(sql);
		System.out.println("delete 2 : "+sql);
		
		sql = "delete from nvgn_kh where khachhang_fk='" + id + "'";
		db.update(sql);
		System.out.println("delete 3 : "+sql);
		//Xoa Bang Cha
			sql = "delete from khachhang where pk_seq = '" + id + "'";
		db.update(sql);
		System.out.println("delete 4 : "+sql);
		db.shutDown();}catch (Exception e) {System.out.println("Loi "+e.toString());		
		}
		
	}
	
	private boolean DeleteHinhDD(String id,String userId)
	{
		dbutils db = new dbutils();
		try{
			
		String sql = "update khachhang set ANHCUAHANG = null,nguoisua = "+userId+"  where pk_seq ='" + id + "'";			
		if(!db.update(sql))
		{
			System.out.println("Loi xoa hinh DD: "+sql);
			return false;
		}
	
		db.shutDown();
		}catch (Exception e) 
		{	
			
			e.printStackTrace();
			System.out.println("Loi "+e.toString());
			return false;
		}
		return true;
	}
	
	private String getQueryExcel(HttpServletRequest request, IKhachhangList obj)
	{
		Utility util = new Utility();
		String ten = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khTen")));
    	if ( ten == null)
    		ten = "";
    	obj.setTen(ten);    	       
    	
    	String hchId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("hchTen")));
    	if (hchId == null)
    		hchId = "";    	
    	obj.setHchId(hchId);
    	
    	String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
		if (tungay == null)
			tungay = "";
		obj.setTungay(tungay);

		String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
		if (denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
    	
    	
		String vungId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")));
		if (vungId == null)
			vungId = "";
		obj.setvungId(vungId);
    	
    	String kbhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhTen")));
    	if (kbhId == null)
    		kbhId = "";    	
    	obj.setKbhId(kbhId);    	
    	
    	String vtchId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vtchTen")));
    	if (vtchId == null)
    		vtchId = "";    	
    	obj.setVtId(vtchId);
    	
    	String lchId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("lchTen")));
    	if (lchId == null)
    		lchId = "";    	
    	obj.setLchId(lchId);
    	
    	String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("NhaPhanPhoi")));
    	if ( nppId == null)
    		nppId = "";
    	obj.setNppId(nppId);
    	
    	String ttId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tpId")));
    	if ( ttId == null)
    		ttId = "";
    	obj.setTpId(ttId);
    	
    	String qhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("qhId")));
    	if ( qhId == null)
    		qhId = "";
    	obj.setQhId(qhId);
    	String isMt =    util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("isMT")));
  	  
	    obj.setIsMT(isMt==null?"":isMt);
    	
		String query = "select distinct ROW_NUMBER() OVER(ORDER BY a.pk_seq DESC) AS 'STT', a.Smartid as N'Mã khách hàng', a.ten as N'Khách hàng tên', "+ 
				"\n N'Trạng thái' = case a.TRANGTHAI "+ 
				"\n when '0' then N'Ngưng hoạt động' "+
				"\n when '1' then N'Hoạt động' end, "+
				"\n d.DIENGIAI as N'Mức chiết khấu', isnull(d.chietkhau,0) as N'Chiết khấu', e.diengiai as N'Kênh bán hàng', f.hang as N'Hạng cửa hàng', "+ 
				"\n g.loai as N'Loại cửa hàng', h.ma as N'Mã nhà phân phối', h.ten as N'Nhà phân phối',(select top 1 ten from giamsatbanhang where pk_seq in (select gsbh_fk from NHAPP_GIAMSATBH where npp_fk = h.pk_seq)) as N'Giám sát BH' , "+
				"\n isnull(k.DIENGIAI,0) as N'Giới hạn công nợ', isnull(k.sotienno,0) as N'Số tiền nợ', "+
				"\n m.vitri as N'Vị trí cửa hàng', a.dienthoai as N'Điện thoại', a.diachi as N'Địa chỉ', n.ten as N'Tỉnh thành', o.ten as N'Quận huyện' "+
				",\n  isnull(DDKD.TEN,'NA') as 'NVBH',"
				+ "\n a.ngaytao as N'Ngày tạo', a.Created_Date as N'Giờ tạo',"+
				"\n N'Trạng thái NVBH' = case DDKD.TRANGTHAI "+ 
				"\n when '0' then N'Ngưng hoạt động' "+
				"\n when '1' then N'Hoạt động' end, "+
				"\n case when a.trangthai = 0 then isnull((select top 1 ngaysua from khachhang_hdlog where khachhang_fk = a.pk_seq and trangthai = 0 order by ngaysua desc),a.ngaysua) else '' end N'Ngày ngưng HĐ', isnull(dh.doanhso,0) as N'Doanh Số KH',"+
				"\n(select top 1 TEN from VUNG  where PK_SEQ in (select vung_fk from KHUVUC where PK_SEQ in (select KHUVUC_FK from KHUVUC_QUANHUYEN where QUANHUYEN_FK=o.PK_SEQ )) ) as N'Vùng/Miền'"+
				"\n from khachhang a inner join nhanvien b on a.nguoitao = b.pk_seq inner join nhanvien c on a.nguoisua = c.pk_seq "+
				"\n LEFT join KHACHHANG_TUYENBH kht on kht.KHACHHANG_FK =a.PK_SEQ "+
				"\n LEFT join TUYENBANHANG tbh on tbh.PK_SEQ = kht.TBH_FK "+
				"\n LEFT JOIN DAIDIENKINHDOANH DDKD ON tbh.DDKD_FK=ddkd.PK_SEQ "+
				"\n left join mucchietkhau d on a.chietkhau_fk = d.pk_seq "+
				"\n left join kenhbanhang e on a.kbh_fk = e.pk_seq "+
				"\n left join hangcuahang f on a.hch_fk = f.pk_seq "+
				"\n left join loaicuahang g on a.lch_fk = g.pk_seq "+
				"\n left join nhaphanphoi h on a.npp_fk = h.pk_seq "+
				/*"\n left join NHAPP_GIAMSATBH gs on gs.npp_fk = h.pk_seq "+
				"\n left join (select top 1 ten from giamsatbanhang) gsbh on gsbh.pk_seq = gs.gsbh_fk "+*/
				"\n left join gioihancongno k on a.ghcn_fk = k.pk_seq "+ 
				"\n left join vitricuahang m on a.vtch_fk = m.pk_seq  "+
				"\n left join tinhthanh n on a.tinhthanh_fk = n.pk_seq "+
				"\n	left join ( "+
				"\n							select khachhang_fk, round(sum(dh_sp.soluong*dh_sp.giamua),0) as doanhso from donhang dh "+
				"\n							inner join donhang_sanpham dh_sp on dh_sp.donhang_fk = dh.pk_seq "+
				"\n							where dh.trangthai = 1 "+
				"\n							and dh.ngaynhap >=  CONVERT(varchar(10),DATEADD(month, -2, dbo.GetLocalDate(DEFAULT)),120) " +
				"\n								and dh.ngaynhap <=  CONVERT(varchar(10), dbo.GetLocalDate(DEFAULT),120)  and trangthai = 1 "+
				"\n							group by khachhang_fk "+
				"\n				 ) dh on dh.khachhang_fk = a.pk_seq "+
				"\n left join quanhuyen o on a.quanhuyen_fk = o.pk_seq "
				+ "\n left join khuvuc_quanhuyen kv on kv.quanhuyen_fk = o.pk_seq "
				+ "\n left join khuvuc v on v.pk_seq = kv.khuvuc_fk "
				+ "\n where 1=1" ; //where a.npp_fk='"+ nppId +"'

    	
    	if (ten.length()>0)
    	{ 
			//query = query + " and ( upper(a.pk_seq) like upper(N'%" + util.replaceAEIOU(ten) + "%') or upper(a.ten) like upper(N'%" + util.replaceAEIOU(ten) + "%')) ";	
    		query = query + " and ( upper(a.SmartId) like upper(N'%" + util.replaceAEIOU(ten) + "%') or upper((a.timkiem)) like upper(N'%" + util.replaceAEIOU(ten) + "%')) ";			
    	}
    	
    	if (kbhId.length()>0){
			query = query + " and a.kbh_fk ='" + kbhId + "'";			
    	}
    	
    	if (nppId.length()>0){
			query = query + " and a.npp_fk ='" + nppId + "'";			
    	}
    	
    	if (hchId.length()>0){
			query = query + " and a.hch_fk='" + hchId + "'";			
    	}
    	
    	if (tungay.length() > 0) {
			query = query + " and a.ngaytao >= '" + tungay + "'";
		}
		if (denngay.length() > 0) {
			query = query + " and a.ngaytao <= '" + denngay + "'";
		}
		
		if (vungId.length() > 0) {
			query = query + " and v.vung_fk <= '" + vungId + "'";
		}
    	
    	if (vtchId.length()>0){
			query = query + " and a.vtch_fk='" + vtchId + "'";			
    	}
    	
    	if (lchId.length()>0){
			query = query + " and a.lch_fk='" + lchId + "'";			
    	}
    	
    	if (ttId.length()>0){
			query = query + " and a.tinhthanh_fk='" + ttId + "'";			
    	}
    	
    	if (qhId.length()>0){
			query = query + " and a.quanhuyen_fk='" + qhId + "'";			
    	}
    	if(isMt.length() > 0)
    		query = query + " and a.ismt = '"+isMt+"' ";
    	else
    		query += " and a.npp_fk in "+ util.quyen_npp(obj.getUserId()) ;  
    	query += " group by a.Smartid ,  a.ten ,  a.TRANGTHAI  , d.DIENGIAI ,d.chietkhau, e.diengiai, f.hang , "+
    			" g.loai , h.ma, h.ten,h.pk_seq ,k.DIENGIAI, k.sotienno, a.PK_SEQ,a.NGAYSUA,dh.doanhso,o.PK_SEQ, "+
    			" m.vitri , a.dienthoai , a.diachi , n.ten, o.ten  , DDKD.TEN, a.ngaytao , a.Created_Date , DDKD.TRANGTHAI ";
    	query+="order by STT";

		System.out.println("Query excel: " + query + "\n");

		return query;

	}
	
	private void CreateStaticHeader(Workbook workbook, String UserName) 
	{
		/*Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Cells cells = worksheet.getCells();
		Style style;

		Cell cell = cells.getCell("A1"); 
		cells.merge(0,0,0,11);
		cell.setValue("DANH SÁCH KHÁCH HÀNG");	
		style = cell.getStyle();
		Font font2 = new Font();	
		font2.setName("Calibri");
		font2.setColor(Color.NAVY);
		font2.setSize(18);
		font2.setBold(true);
		style.setFont(font2);
		style.setHAlignment(TextAlignmentType.CENTER);					
		cell.setStyle(style);

		font2 = new Font();	
		font2.setName("Calibri");
		font2.setBold(true);
		font2.setSize(11);

		cell = cells.getCell("A3");
		cell.setValue("Ngày tạo : " + this.getDateTime());
		style = cell.getStyle();
		style.setFont(font2);
		cell.setStyle(style);
		
		worksheet.setName(" DSKH ");*/
	}

	private void CreateStaticData(Workbook workbook, String query) 
	{
		try
		{
			FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\danhsachkhachhang.xls");
			//File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\danhsachkhachhang.xls");
			//FileInputStream fstream = new FileInputStream(f) ;
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			Cells cells = worksheet.getCells();
			Style style;

			Cell cell = cells.getCell("A1"); 
			cells.merge(0,0,0,11);
			cell.setValue("DANH SÁCH KHÁCH HÀNG");	
			style = cell.getStyle();
			Font font2 = new Font();	
			font2.setName("Calibri");
			font2.setColor(Color.NAVY);
			font2.setSize(18);
			font2.setBold(true);
			style.setFont(font2);
			style.setHAlignment(TextAlignmentType.CENTER);					
			cell.setStyle(style);

			font2 = new Font();	
			font2.setName("Calibri");
			font2.setBold(true);
			font2.setSize(11);

			cell = cells.getCell("A3");
			cell.setValue("Ngày tạo : " + this.getDateTime());
			style = cell.getStyle();
			style.setFont(font2);
			cell.setStyle(style);
			
			worksheet.setName(" DSKH ");
	
			 cell = cells.getCell("A3");
	
			 style = cell.getStyle();
			style.setNumber(41);
			dbutils db = new dbutils();
			ResultSet rs = db.get(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();
	   
			int countRow = 6;
			for( int i =1 ; i <=socottrongSql ; i ++  )
			{
				cell = cells.getCell(countRow,i-1 );
				Style s = cell.getStyle();
				s.setTextWrapped(true);
				s.setHAlignment(TextAlignmentType.CENTER);
				s.setVAlignment(TextAlignmentType.JUSTIFY);
				cell.setStyle(s);
				ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
				
				ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, rsmd.getColumnName(i));
		
			}
			countRow ++;
	   
			NumberFormat formatter = new DecimalFormat("#,###,###");
			while(rs.next())
			{
				for(int i = 1; i <= socottrongSql; i ++)
				{
					cell = cells.getCell(countRow,i-1 );
					if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i)==Types.NUMERIC || rsmd.getColumnType(i)==Types.INTEGER )
					{
						
						cell.setStyle(style);
						ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9,  rs.getString(i));
						 ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
					}
					else
					{
						ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, rs.getString(i));
						// ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					}
				}
				++countRow;
			}
	   
			if(rs!=null)rs.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
	}

	private void setCellBorderStyle(Cell cell, short align) {
		Style style = cell.getStyle();
		style.setHAlignment(align);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		cell.setStyle(style);
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

}
