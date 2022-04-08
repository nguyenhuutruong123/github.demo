package geso.dms.center.servlets.thongbao;

import geso.dms.center.beans.thongbao.IThongbao;
import geso.dms.center.beans.thongbao.IThongbaoList;
import geso.dms.center.beans.thongbao.imp.Thongbao;
import geso.dms.center.beans.thongbao.imp.ThongbaoList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ThongbaoSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ThongbaoSvl() {
        super();
    }
    private boolean deletefile(String file)
	{
		System.out.println(file);
		  File f1 = new File(file);
		  boolean success = f1.delete();
		  if (!success)
		  {
			return false;
		  }
		  else
		  {
			 return true;
		   }
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");	    	    
	    HttpSession session = request.getSession();
	    String userId = (String) session.getAttribute("userId");
	    
	    IThongbaoList obj =  new ThongbaoList();
	    
	    String task = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("task"));
	    if(task == null)
	    	task = "";
	    obj.setTask(task);
	    System.out.println("TASK: "+task);
	    
	    String viewMode = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("viewMode"));
	    if(viewMode == null)
	    	viewMode = "1";
	    obj.setViewMode(viewMode);
	    
	    String loaivanban = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaivanban"));
	    if(loaivanban == null)
	    	loaivanban = "";
	    obj.setLoaithongbao(loaivanban);
	    
	    if(task.trim().length() > 0)
	    {
	    	if(task.equals("xoa"))
	    	{
	    		String pk = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id"));	    		
	    		dbutils db = new dbutils();
	    		try 
	    		{
					db.getConnection().setAutoCommit(false);
					String query = "select filename from thongbao where pk_seq='"+pk+"'";
					System.out.println("cau select: "+query);
					ResultSet rs = db.get(query);
					String filename="";
					try 
					{
						rs.next();
						filename = rs.getString("filename");
					}
					catch (Exception e) 
					{
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						obj.setMsg("Không thể xóa, lỗi: không thể lấy tên file");
					}
					if(!filename.equals("0"))
					{
						if(!this.deletefile("C:\\java-tomcat\\dinhkem\\"+filename))
						{
							geso.dms.center.util.Utility.rollback_throw_exception(db);
							obj.setMsg("Không thể xóa, lỗi: không thể lấy delete file");
						}
					}
					query = "delete thongbao_nhanvien where thongbao_fk= '" + pk+ "'";
		    		if(!db.update(query))
		    		{
		    			geso.dms.center.util.Utility.rollback_throw_exception(db);
		    			obj.setMsg("Không thể xóa, lỗi: "+ query);
		    		}
		    		query = "delete THONGBAO_VBHD where thongbao_fk= '" + pk+ "'";
		    		if(!db.update(query))
		    		{
		    			geso.dms.center.util.Utility.rollback_throw_exception(db);
		    			obj.setMsg("Không thể xóa, lỗi: "+ query);
		    		}
		    		query = "delete THONGBAO_VBCC where thongbao_fk= '" + pk+ "'";
		    		if(!db.update(query))
		    		{
		    			geso.dms.center.util.Utility.rollback_throw_exception(db);
		    			obj.setMsg("Không thể xóa, lỗi: "+ query);
		    		}
		    		query = "delete THONGBAO_VBTT where thongbao_fk= '" + pk+ "'";
		    		if(!db.update(query))
		    		{
		    			geso.dms.center.util.Utility.rollback_throw_exception(db);
		    			obj.setMsg("Không thể xóa, lỗi: "+ query);
		    		}
		    		query = "delete THONGBAO_VBSDBS where thongbao_fk= '" + pk+ "'";
		    		if(!db.update(query))
		    		{
		    			geso.dms.center.util.Utility.rollback_throw_exception(db);
		    			obj.setMsg("Không thể xóa, lỗi: "+ query);
		    		}
		    		query = "delete thongbao where pk_seq = '" + pk+ "'";
		    		if(!db.update(query))
		    		{
		    			geso.dms.center.util.Utility.rollback_throw_exception(db);
		    			obj.setMsg("Không thể xóa, lỗi: "+ query);
		    		}
		    		
		    		db.getConnection().commit();
					db.getConnection().setAutoCommit(true);
	    		} 
	    		catch (Exception e)
	    		{
	    			geso.dms.center.util.Utility.rollback_throw_exception(db);
	    			obj.setMsg("Không thể xóa, lỗi: "+ e.getMessage());
				}
	    	}
	    	else if(task.equals("1"))
	    	{
	    		obj =  new ThongbaoList(userId);
	    		obj.setUserId(userId);
	    		obj.setViewMode(viewMode);
	    		obj.setTask(task);
	    		//obj.setLoaithongbao(loaivanban);
	    		System.out.println("Task la:"+1);
	    		obj.initNhanvien("");
	    		
	    		session.setAttribute("obj", obj);
	    		String nextJSP = "/AHF/pages/Center/ThongBaoNhanVien.jsp";
	    		response.sendRedirect(nextJSP);
	    		return;
	    	}
	    	else if(task.equals("2"))
	    	{
	    		obj =  new ThongbaoList(userId);
	    		obj.setUserId(userId);
	    		obj.setViewMode(viewMode);
	    		obj.setLoaithongbao(loaivanban);
	    		
	    		obj.init("");
	    		
	    		session.setAttribute("obj", obj);
	    		String nextJSP = "/AHF/pages/Center/ThongBao.jsp";
	    		response.sendRedirect(nextJSP);
	    		return;
	    	}
	    }
	    
	    obj =  new ThongbaoList();	    
	    obj.setUserId(userId);
	    System.out.println("___Loai van ban: " + loaivanban);
	    obj.setViewMode(viewMode);
	    System.out.println("___VIEW MODE: " + viewMode);
	    obj.setLoaithongbao(loaivanban);
	    
	    obj.init("");
		session.setAttribute("obj", obj);
		String nextJSP = "/AHF/pages/Center/ThongBao.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");	    	    
	    HttpSession session = request.getSession();	
	    String userId = (String) session.getAttribute("userId");
	    
	    IThongbaoList obj = new ThongbaoList();	  
	    
	    
	    String maso = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("maso"));
	    String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));
	    String ngaybatdau = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaybatdau"));
	    String ngayketthuc = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngayketthuc"));
	    String tieude = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tieude"));
	    String noidung = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("noidung"));
	    String task = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("task"));
	    String viewMode = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("viewMode"));
		if(viewMode == null)
			viewMode = "0";
		obj.setViewMode(viewMode);
		
		String loaivanban = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaivanban"));
	    if(loaivanban == null)
	    	loaivanban = "";
	    obj.setLoaithongbao(loaivanban);
	    
	    if(ngaybatdau == null)
	    	ngaybatdau = "";
	    obj.setNgaybatdau(ngaybatdau);
	    
	    System.out.println("Tieu de la:"+ tieude);
	    
	    if(tieude == null)
	    	tieude = "";
	    obj.setTieude(tieude);
	    
	    if(noidung == null)
	    	noidung = "";
	    obj.setNoidung(noidung);
	    
	    if(ngayketthuc == null)
	    	ngayketthuc = "";
	    obj.setNgayketthuc(ngayketthuc);
	    if(trangthai == null)
	    	trangthai = "";
	    if(maso == null)
	    	maso = "";
	    obj.setId(maso);
	    if(task==null){
	    	task="";
	    }
	    obj.setTask(task);
	    Utility util = new Utility();
	    
	    
	    /*select  ct.pk_seq,ct.trangthai, ct.tinhtrang, ct.noidung,ct.tieude,ct.ngaybatdau,ct.filename, case when len(ct.ngayketthuc) <= 0 then N'Vô th?i h?n' else ct.ngayketthuc end as ngayketthuc,ct.ngaytao,ct.nguoitao,ct.ngaysua,ct.nguoisua ,NV.TEN as TENNV,NV.PK_SEQ as MANV,NV2.TEN as TENNVS,NV2.PK_SEQ as MANVS, ct.nguoitao as nguoitaoTB from thongbao ct inner join NHANVIEN nv on ct.nguoitao = nv.PK_SEQ inner join NHANVIEN nv2 on ct.NGUOISUA = nv2.PK_SEQ where 1=1  and ct.loaithongbao = '5'*/
	    
	    
	    
	    
	    
	    
	    
	   String query ="select a.ngaybatdau,a.ngayketthuc,a.pk_seq,a.ngaytao,a.filename,a.tieude,a.noidung,nhanvien_fk,nv.TEN as TENNV,";
	   query+= " a.ngaysua,nvs.TEN as TENNVS,a.trangthai, a.tinhtrang,a.nguoitao as nguoitaotb, nv.ten as nguoitao ";
	   query+= "from thongbao a inner join thongbao_nhanvien b on a.pk_seq = b.thongbao_fk ";
	   query+=	"inner join NHANVIEN nv on a.nguoitao = nv.PK_SEQ "; 
	   query+=	"inner join NHANVIEN nvs on a.nguoisua = nvs.PK_SEQ where isnull(a.hienthi,1) = 1 ";
	   // String query = "select   ct.pk_seq,ct.trangthai, ct.tinhtrang,ct.noidung,ct.tieude, ct.filename, ct.ngaybatdau, case when len(ct.ngayketthuc) <= 0 then N'Vô thời hạn' else ct.ngayketthuc end as ngayketthuc,ct.ngaytao,ct.nguoitao,ct.ngaysua,ct.nguoisua ,NV.TEN as TENNV,NV.PK_SEQ as MANV,NV2.TEN as TENNVS,NV2.PK_SEQ as MANVS, ct.nguoitao as nguoitaoTB from thongbao ct inner join NHANVIEN nv on ct.nguoitao = nv.PK_SEQ inner join NHANVIEN nv2 on ct.NGUOISUA = nv2.PK_SEQ where 1 = 1 ";
	    	query += " and nhanvien_fk = '" + userId + "' ";
	    	
	    	  //query= "select a.ngaybatdau,a.ngayketthuc,a.pk_seq,a.filename,a.tieude,a.noidung,b.trangthai, a.tinhtrang, a.nguoitao as nguoitaoTB from thongbao a inner join thongbao_nhanvien b on a.pk_seq = b.thongbao_fk where nhanvien_fk='"+userId+"' and 1=1 ";
	    	    if(maso.length() > 0)
	    	    	query += " and a.pk_seq  =  '" + maso + "'";
	    	    if(trangthai.length() > 0)
	    	    	query += " and a.trangthai= '" + trangthai + "'";
	    	    if(ngaybatdau.length() > 0)
	    	    	query += " and a.ngaybatdau >= '" + ngaybatdau + "'";
	    	    if(ngayketthuc.length() > 0)
	    	    	query += " and a.ngayketthuc <= '" + ngayketthuc + "'";
	    	    if(tieude.length() > 0)
	    	    	query += " and a.tieude like N'%" + tieude + "%'";
	    	    if(noidung.length() > 0)
	    	    	query += " and a.noidung like N'%" + noidung + "%'";
	    	    if(loaivanban.trim().length() > 0)
	    	    	query += " and a.loaithongbao = '" +loaivanban + "' ";
				 else
					 query += " and a.loaithongbao != '5' "; //chi lay Van ban, khong lay thong bao
	    	    
//	    if(maso.length() > 0)
//	    	query += " and cast(ct.pk_seq as nvarchar(20)) like '%" + maso + "%'";
//	    if(ngaybatdau.length() > 0)
//	    	query += " and ct.ngaybatdau >= '" + ngaybatdau + "'";
//	    if(ngayketthuc.length() > 0)
//	    	query += " and ct.ngayketthuc <= '" + ngayketthuc + "'";
//	    if(tieude.length() > 0)
//	    	query += " and upper(dbo.ftBoDau(ct.tieude)) like upper(N'%" + util.replaceAEIOU(tieude) + "%')";
//	    if(noidung.length() > 0)
//	    	query += " and upper(dbo.ftBoDau(ct.noidung)) like upper(N'%" + util.replaceAEIOU(noidung) + "%')";
//	    if(trangthai.length() > 0)
//	    	query += " and ct.trangthai = "+"'"+trangthai+"'";
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    System.out.println(" serch query: "+query);
	    //System.out.println(" action"+action);
	    System.out.println("Action nhap lai la gi: "+action);
	    if(action.equals("search"))
	    {
	    	
	    	//obj = new ThongbaoList();
	    	obj.setViewMode(viewMode);
	    	obj.setLoaithongbao(loaivanban);
	    	//System.out.println("Query trong cung: "+query);
	    	obj.init(query);
			obj.setUserId(userId);				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
    		String nextJSP = "/AHF/pages/Center/ThongBao.jsp";
    		response.sendRedirect(nextJSP);
	    	
		 /*   obj.initNhanvien(query);
			session.setAttribute("obj", obj);
			System.out.println("Truy van 1"+query);
			System.out.println("Truy van 1"+query);
			String nextJSP = "/AHF/pages/Center/ThongBaoNhanVien.jsp";
			response.sendRedirect(nextJSP);*/
	    }
	    else if (action.equals("nhanvien"))
	    {
    	    if(trangthai == null)
    	    	trangthai = "";
    	    obj.setTrangthai(trangthai);
    	    query= "select a.pk_seq,a.tieude,a.filename,a.noidung,b.trangthai,a.ngaybatdau,a.ngayketthuc,a.ngaytao,c.Ten as nguoitao,a.nguoitao as nguoitaotb, a.ngaysua,a.nguoisua, a.loaithongbao " +
 			 	   "from thongbao a inner join thongbao_nhanvien b on a.pk_seq = b.thongbao_fk inner join NHANVIEN c on a.nguoitao = c.PK_SEQ " +
 			 	   "where isnull(a.hienthi,1) = 1 and nhanvien_fk='"+userId+"' and 1=1 ";
    	    if(maso.length() > 0)
    	    	query += " and a.pk_seq like N'%" + maso + "%'";
    	    if(trangthai.length() > 0)
    	    	query += " and b.trangthai= '" + trangthai + "'";
    	    if(ngaybatdau.length() > 0)
    	    	query += " and a.ngaybatdau >= '" + ngaybatdau + "'";
    	    if(ngayketthuc.length() > 0)
    	    	query += " and a.ngayketthuc <= '" + ngayketthuc + "'";
    	    if(tieude.length() > 0)
    	    	query += " and a.tieude like N'%" + tieude + "%'";
    	    if(noidung.length() > 0)
    	    	query += " and a.noidung like N'%" + noidung + "%'";
    	    
    		/*obj =  new ThongbaoList(userId);
    		obj.setUserId(userId);
    		obj.setViewMode(viewMode);
    		obj.setTask(task);
    		//obj.setLoaithongbao(loaivanban);
    		System.out.println("Task la:"+1);
    		obj.initNhanvien("");
    		
    		session.setAttribute("obj", obj);
    		String nextJSP = "/AHF/pages/Center/ThongBaoNhanVien.jsp";
    		response.sendRedirect(nextJSP);*/
    	    
    	    
    	    System.out.println("task post la:" + task);
    	    
    	    obj.setUserId(userId);
    		obj.setViewMode(viewMode);
    		obj.setTask(task);
    		//obj.setLoaithongbao(loaivanban);
    	    
    	    obj.initNhanvien(query);
			session.setAttribute("obj", obj);	
			System.out.println("truy van "+ query);
			String nextJSP = "/AHF/pages/Center/ThongBaoNhanVien.jsp";
			response.sendRedirect(nextJSP);
			
	    } 
	    else if(action.equals("new"))
	    {
	    	IThongbao tbBean = new Thongbao();	
	    	tbBean.setUserId(userId);
	    	tbBean.createRs();
	    	tbBean.setLoaithongbao(loaivanban);
	    	obj.setViewMode(viewMode);
	    	
	    	session.setAttribute("tbBean", tbBean);		
	    	
	    	System.out.println("__Vao tao moi: --  ViewMode la: " + viewMode + "  -- Trong Bean: " + obj.getViewMode());
			String nextJSP = "/AHF/pages/Center/ThongBaoNew.jsp";
			if(viewMode.equals("0"))
				nextJSP = "/AHF/pages/Center/ThongBaoNew_NPP.jsp";
			
			response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new ThongbaoList();
	    		obj.setViewMode(viewMode);
	    
		    	System.out.println("nxtApprSplitting "+Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
		    	obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
		    	obj.setUserId(userId);
		    	obj.init(query);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	String nextJSP = "/AHF/pages/Center/ThongBao.jsp";
		    	response.sendRedirect(nextJSP);
		    }
	    	else if(action.equals("viewnv") || action.equals("nextnv") || action.equals("prevnv"))
	    	{
	    		obj = new ThongbaoList();
	    		obj.setViewMode(viewMode);
	    		
	    	    if(trangthai == null)
	    	    	trangthai = "";
	    	    obj.setTrangthai(trangthai);
	    	    tieude = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tieude"));
	    	    if(ngaybatdau == null)
	    	    	ngaybatdau = "";
	    	    if(tieude == null)
	    	    	tieude = "";
	    	    obj.setTieude(tieude);
	    	    query= "select a.ngaybatdau,a.ngayketthuc,a.pk_seq,a.tieude, a.tinhtrang, a.noidung,b.trangthai, a.nguoitao as nguoitaoTB from thongbao a inner join thongbao_nhanvien b on a.pk_seq = b.thongbao_fk where isnull(a.hienthi,1) = 1 and  nhanvien_fk='"+userId+"' and 1=1 ";
	    	    if(maso.length() > 0)
	    	    	query += " and a.pk_seq = '" + maso + "'";
	    	    if(trangthai.length() > 0)
	    	    	query += " and b.trangthai= '" + trangthai + "'";
	    	    if(ngaybatdau.length() > 0)
	    	    	query += " and a.ngaybatdau >= '" + ngaybatdau + "'";
	    	    if(ngayketthuc.length() > 0)
	    	    	query += " and a.ngayketthuc <= '" + ngayketthuc + "'";
	    	    if(tieude.length() > 0)
	    	    	query += " and a.tieude like N'%" + tieude + "%'";
	    	    if(noidung.length() > 0)
	    	    	query += " and a.noidung like N'%" + noidung + "%'";
		    	System.out.println("nxtApprSplitting sxl"+Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
		    	obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
		    	obj.setUserId(userId);
		    	obj.init(query);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	String nextJSP = "/AHF/pages/Center/ThongBaoNhanVien.jsp";
		    	response.sendRedirect(nextJSP);
	    	}
	    	else
	    	{
	    		
		    	obj = new ThongbaoList();
		    	obj.setViewMode(viewMode);
		    	obj.setLoaithongbao(loaivanban);
		    	System.out.println("Query trong cung: "+query);
		    	obj.init(query);
				obj.setUserId(userId);				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
	    		String nextJSP = "/AHF/pages/Center/ThongBao.jsp";
	    		response.sendRedirect(nextJSP);
	    	}
	    }
	}

}
