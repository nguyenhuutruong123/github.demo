package geso.dms.distributor.servlets.phieuxuatkho;

import geso.dms.distributor.beans.donhang.ISanpham;
import geso.dms.distributor.beans.donhang.imp.Sanpham;
import geso.dms.distributor.beans.phieuxuatkho.IPhieuxuatkho;
import geso.dms.distributor.beans.phieuxuatkho.IPhieuxuatkhoList;
import geso.dms.distributor.beans.phieuxuatkho.imp.Phieuxuatkho;
import geso.dms.distributor.beans.phieuxuatkho.imp.PhieuxuatkhoList;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PhieuxuatkhoSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
    public PhieuxuatkhoSvl() 
    {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IPhieuxuatkhoList obj;
		PrintWriter out = response.getWriter();; 
		String userId;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();

	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    //out.println(userId);
	    
	    if (userId.length() == 0 )
	    	userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String pxkId = util.getId(querystring);

	    String msg = "";
	    if(action.equals("chotphieu"))
	    {
    		String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
    		String ngaylap = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaylap"));
    		
			msg = this.Chotphieuxuat(pxkId, nppId, ngaylap);
			if(msg.length() > 0)
			{
				out.println("Khong the chot phieu xuat...\n");
				System.out.println("Error Mesege: " + msg + "\n");
			}

	    }
	    else
	    {
	    	if(action.equals("delete"))
	    	{
	    		DeletePxk(pxkId);
	    	}
	    }
	   	    
	    obj = new PhieuxuatkhoList();
	    obj.setRequestObj(request);
	    obj.setUserId(userId);
	    
	    obj.init("");
	    obj.setMsg(msg);
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Distributor/PhieuXuatKho.jsp";
		response.sendRedirect(nextJSP);
	}

	private String DeletePxk(String pxkId) 
	{
		dbutils db = new dbutils();
		//khi moi tao phieuxuatkho chua lam gi het, nen co the xoa thang
		//String query = "update phieuxuatkho set trangthai = '2' where pxk_fk = '" + pxkId + "'";
		//db.update(query);
		try{
			db.getConnection().setAutoCommit(false);
			
			String query = " update phieuxuatkho set trangthai = 0 where pk_seq = '"+pxkId+"' and  trangthai = 0";
			
			if(db.updateReturnInt(query)!=1 ){
				db.getConnection().rollback();
				return "Không thể xóa pxk, lỗi trạng thái";
				
			}
			query="delete phieuxuatkho_tienkm where pxk_fk = '" + pxkId + "'";
			if(!db.update(query)){
				db.getConnection().rollback();
				return query;
			}
					
		  //System.out.println("delete phieuxuatkho_tienkm where pxk_fk = '" + pxkId + "'");
			query="delete phieuxuatkho_sanpham where pxk_fk = '" + pxkId + "'";
			System.out.println(query);
			if(! db.update(query)){
				db.getConnection().rollback();
				return query;
			}
		 //System.out.println("delete phieuxuatkho_sanpham where pxk_fk = '" + pxkId + "'");
			query="delete phieuxuatkho_spkm where pxk_fk = '" + pxkId + "'";
			System.out.println(query);
			if(! db.update(query)){
				db.getConnection().rollback();
				return query;
			}
			query="delete PHIEUXUATKHO_DONHANG where pxk_fk='"+pxkId+"'";
			System.out.println(query);
			if(! db.update(query)){
				db.getConnection().rollback();
				return query;
			}
		
			query="delete phieuxuatkho where pk_seq = '" + pxkId + "' and trangthai=0 ";
			System.out.println(query);
			if(db.updateReturnInt(query)!=1)
			{
				db.getConnection().rollback();
				return query;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
		}catch(Exception er){
			System.out.println(er);
		}
		
		return "";
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IPhieuxuatkhoList obj = new PhieuxuatkhoList();
		PrintWriter out = response.getWriter();; 
		String userId;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
		HttpSession session = request.getSession();
	    userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    out.println(action); 
	    obj.setRequestObj(request);
	    if (action.equals("Tao moi"))
	    {
	    	// Empty Bean for distributor
	    	IPhieuxuatkho pxkBean = (IPhieuxuatkho) new Phieuxuatkho("");
	    	pxkBean.setUserId(userId);
	    	pxkBean.createRS();
	    	
	    	// Save Data into session
	    	session.setAttribute("pxkBean", pxkBean);
    		
    		String nextJSP = "/AHF/pages/Distributor/PhieuXuatKhoNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    else if(action.equals("clear"))
	    {
	    	obj.setUserId(userId);
	    	obj.init("");
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
    		response.sendRedirect("/AHF/pages/Distributor/PhieuXuatKho.jsp");	 
	    }
	    
	    else if(action.equals("view") || action.equals("next") || action.equals("prev")){
	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
	    	obj.setUserId(userId);
	    	obj.init(search);
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	session.setAttribute("obj", obj);
	    	response.sendRedirect("/AHF/pages/Distributor/PhieuXuatKho.jsp");	
	    }
	    
	    else
	    {
	    	obj = new PhieuxuatkhoList();
	    	obj.setRequestObj(request);

	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
    		session.setAttribute("abc", search);
		
    		response.sendRedirect("/AHF/pages/Distributor/PhieuXuatKho.jsp");	    		    	
	    }
	    
	}
	
	private String getSearchQuery(HttpServletRequest request, IPhieuxuatkhoList obj) 
	{	
		String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
    	if ( nppId == null)
    		nppId = "";
    	obj.setNppId(nppId);
    	
		String nvgnId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nvgnTen"));
    	if ( nvgnId == null)
    		nvgnId = "";
    	obj.setNvgnId(nvgnId);
    	
    	String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));
    	if ( trangthai == null)
    		trangthai = "";
    	obj.setTrangthai(trangthai);
    	
    	String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
    	if (tungay == null)
    		tungay = "";    	
    	obj.setTungay(tungay);
    	
    	String donhangid = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("donhangid"));
    	if (donhangid == null)
    		donhangid = "";    	
    	obj.setDonhangId(donhangid);
    	   	
    	String query = "";
    	query = "select ROW_NUMBER() OVER(ORDER BY pxk.ngaylapphieu DESC) AS 'stt', pxk.pk_seq as pxkId, nvgn.pk_seq as nvgnId, nvgn.ten as nvgnTen, pxk.trangthai, pxk.ngaylapphieu, pxk.ngaytao, pxk.ngaysua, nv1.ten as nguoitao, nv2.ten as nguoisua ";
    	query += "from phieuxuatkho pxk inner join nhanviengiaonhan nvgn on pxk.nvgn_fk = nvgn.pk_seq inner join nhanvien nv1 on pxk.nguoitao = nv1.pk_seq inner join nhanvien nv2 on pxk.nguoisua = nv2.pk_seq	where pxk.npp_fk = '" + nppId + "'";
    		
    	if (nvgnId.length() > 0)
    	{
			query = query + " and nvgn.pk_seq='" + nvgnId + "'";			
    	}
    	
    	if (trangthai.length() > 0)
    	{
			query = query + " and pxk.trangthai='" + trangthai + "'";			
    	}
    	
    	if (tungay.length() > 0)
    	{
    		query = query + " and pxk.ngaytao >= '" + tungay + "'"; 
    	}
    	
    	if (donhangid.length() > 0)
    	{
    		query = query + " and pxk.pk_seq in (select pxk_fk from phieuxuatkho_donhang where donhang_fk = '"+donhangid+"' )"; 
    	}
    	
    	System.out.print("\nQuery la: " + query + "\n");
    	
    	return query;
	}
	
	private String Chotphieuxuat(String pxkId, String nppId, String ngaylap) 
	{	
		dbutils db = new dbutils();
		
		String msg = "";
		String ngaytiep = "";
		int songay = 0;
		
		//check ton kho
		
		
		String query = "select pxk_sp.spMa " + 
				"from " +
				"(" +
				"select khoId, kbhId, spId, spMa, sum(soluong) as soluong " +
				"from( " +
				"select c.kho_fk as khoId, c.kbh_fk as kbhId, b.pk_seq as spId, b.ma as spMa, sum(a.soluong) as soluong " +
				"from donhang_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq inner join donhang c on a.donhang_fk = c.pk_seq " +
				"where c.trangthai != 2 and a.donhang_fk in (select donhang_fk from phieuxuatkho_donhang where pxk_fk = " + pxkId + ") " + 
				"group by c.kho_fk, c.kbh_fk, b.pk_seq, b.ma " +
				"union all " +
				"select b.kho_fk as khoId, e.kbh_fk as kbhId, d.pk_seq as spId, a.spMa, sum(a.soluong) as soluong " +
				"from donhang_ctkm_trakm a inner join ctkhuyenmai b on a.ctkmid = b.pk_seq " +
				"	inner join sanpham d on a.spMa = d.ma inner join donhang e on a.donhangId = e.pk_seq " +
				"where e.trangthai != 2 and a.spMa is not null and a.donhangId in (select donhang_fk from phieuxuatkho_donhang where pxk_fk = " + pxkId + ") " +
				"group by b.kho_fk, e.kbh_fk, a.ctkmId, a.spMa, d.pk_seq " +
				" ) a " +
				" group by khoId, kbhId, spId, spMa " +
				") pxk_sp " +
				"left join " +
				"( " +
				"	select kho_fk, npp_fk, sanpham_fk, kbh_fk, soluong from nhapp_kho where npp_fk = '" + nppId + "' " +
				") kho_npp on pxk_sp.khoId = kho_npp.kho_fk and pxk_sp.kbhId = kho_npp.kbh_fk and pxk_sp.spId = kho_npp.sanpham_fk " +
				"where (isnull(kho_npp.soluong,0) - pxk_sp.soluong) < 0";
		
		
		System.out.println("Query check chot phieu xuat kho: " + query + "\n");
		ResultSet sosp = db.get(query);
		String spMa = "";
		if(sosp != null)
		{
			try 
			{
				while(sosp.next())
				{
					spMa += sosp.getString("spMa") + ",";
				}
				sosp.close();
			} 
			catch(Exception e1) {}
		}
		
		if(spMa.length() > 0)
		{
			msg = "Các mã sản phẩm sau: " + spMa + " không đủ số lượng trong kho \nVui lòng kiểm tra lại số lượng trước khi chốt phiếu xuất kho";
			return msg;
		}
		
		query = "select max(ngayks) as ngayks, cast(DATEADD(day, 1, max(ngayks)) as nvarchar(11)) as ngaytiep, DATEDIFF(day, max(ngayks), '" + ngaylap + "') AS songay" +
		" from khoasongay where npp_fk ='" + nppId + "'";
		ResultSet ngaykhoaso = db.get(query);
		try 
		{
			if(ngaykhoaso.next())
			{
				ngaytiep = ngaykhoaso.getString("ngaytiep");
				songay = ngaykhoaso.getInt("songay");
			}
			ngaykhoaso.close();
		} 
		catch(Exception e2) {}
	
		
		if(songay <= 0)
		{
			msg = "Bạn hãy khóa sổ tháng trước khi chốt phiếu xuất kho này.";
			return msg;
		}
		
		query="select DonHang_fk,pxk_Fk from phieuxuatkho_donhang where  pxk_Fk="+pxkId+" and  donhang_Fk in (select donhang_Fk from  phieuxuatkho_donhang where pxk_fk!="+pxkId+" )";
		ResultSet rs = db.get(query);
		try 
		{
			while(rs.next())
			{
				msg+="Đơn hàng số "+rs.getString("donhang_fk")+" đã tồn tại trong phiếu xuất kho "+rs.getString("pxk_fk") +" vui lòng bỏ chọn \n";
				
			}
			rs.close();
			if(msg.length()>0)
			{
				return msg;
			}
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		query = "select donhang_fk from phieuxuatkho_donhang where pxk_fk = "+pxkId+"";
		ResultSet rsq = db.get(query);
		try 
		{
			if(!rsq.next())
			{
				msg+="Phiếu xuất kho "+pxkId +" không có đơn hàng nào, vui lòng kiểm tra lại \n";
			}
			rsq.close();
			if(msg.length()>0)
			{
				return msg;
			}
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		
		query = "select a.donhang_fk from phieuxuatkho_donhang a inner join donhang b on a.donhang_fk = b.pk_seq where a.pxk_fk = "+pxkId+" and b.trangthai <> 0";
		ResultSet rstt = db.get(query);
		try 
		{
			if(rstt.next())
			{
				msg+="Phiếu xuất kho "+pxkId +" có đơn hàng trạng thái khác chưa chốt, vui lòng kiểm tra lại \n";
			}
			rstt.close();
			if(msg.length()>0)
			{
				return msg;
			}
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}

		query="select a.DONHANG_FK,a.PXK_FK,b.NGAYNHAP from phieuxuatkho_donhang a inner join DONHANG b on a.DONHANG_FK = b.PK_SEQ"
				+ " where b.TRANGTHAI <> 1 and a.pxk_Fk="+pxkId+" ";
		 rs = db.get(query);
		try 
		{
			while(rs.next())
			{
				// Check NXT tồn đúng mới cho chốt đơn.
				query = "select * from [dbo].[ufn_Check_XNT_KhiChotDH]("+nppId+",'"+rs.getString("NGAYNHAP")+"',"+rs.getString("donhang_fk")+")";
				System.out.println("QRXNT: "+query);
				ResultSet rsXNT  = db.get(query);
				if(rsXNT != null)
				{
					String loi = "";
					while(rsXNT.next())
					{
						loi+= "\n Đơn Hàng "+rs.getString("donhang_fk")+". Sản phẩm  "+rsXNT.getString("tensp")+". Không đủ XNT trong tháng ("+rs.getString("NGAYNHAP").substring(0,7)+"). Vui lòng điều chỉnh số lượng !";
					}
					if(loi.length() > 0)
					{
						msg = loi;
						rsXNT.close();
						return msg; 
					}
					
				}
			}
			rs.close();
			if(msg.length()>0)
			{
				return msg;
			}
		} catch (SQLException e) 
		{
			e.printStackTrace();
		
		}
		
		
		query = pxkId + "&" + nppId + "&" + ngaylap;
		String param[] = query.split("&");
		try {
			db.getConnection().setAutoCommit(false);
			query = " update phieuxuatkho set trangthai = 0 where pk_seq = '"+pxkId+"' and  trangthai = 0";
			
			if(db.updateReturnInt(query)!=1 ){
				db.getConnection().rollback();
				return "Không thể chốt pxk, lỗi trạng thái";
			}
			int kq = db.execProceduce("Chot_Phieu_Xuat_Kho", param);
			query="insert into logfile (pxk_fk,nguoisua,LOAI) values('"+pxkId+"','"+nppId+"','1')";
			db.update(query);
		
		
			if(kq == -1)
			{
				db.getConnection().rollback();
				return "Không thể chốt pxk, lỗi trạng thái";
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	private List<ISanpham> createSanpham(String pxkId, dbutils db)
	{
		List<ISanpham> sanphamList = new ArrayList<ISanpham>();
		
		String query = "select c.kho_fk as khoId, c.kbh_fk as kbhId, b.pk_seq as spId, b.ma as spMa, b.ten as spTen, sum(a.soluong) as soluong from donhang_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq inner join donhang c on a.donhang_fk = c.pk_seq ";
		query += "where c.trangthai != 2 and a.donhang_fk in (select donhang_fk from phieuxuatkho_donhang where pxk_fk = '" + pxkId + "') group by c.kho_fk, c.kbh_fk, b.pk_seq, b.ma, b.ten";
		System.out.println("Query San pham XK la: " + query);
		ResultSet sanphamRS = db.get(query);
		if(sanphamRS != null)
		{
			String[] param = new String[8];
			ISanpham sp = null;	
			try 
			{
				while(sanphamRS.next())
				{
					param[0] = sanphamRS.getString("spId");
					param[1] = sanphamRS.getString("spma");
					param[2] = sanphamRS.getString("spten");
					param[3] = sanphamRS.getString("soluong");
					
					//luu kho
					param[4] = "";
					if(sanphamRS.getString("khoId") != null)
						param[4] = sanphamRS.getString("khoId");
					
					//luu kenh ban hang
					param[5] = "";
					if(sanphamRS.getString("kbhId") != null)
						param[5] = sanphamRS.getString("kbhId");
					
					param[6] = "";
					param[7] = "";
					
					sp = new Sanpham(param);
					sanphamList.add(sp);
				}
				sanphamRS.close();
			} 
			catch(Exception e) {}
		}			

		return sanphamList;
	}
	
	private List<ISanpham> createSpKmList(String pxkId, dbutils db)
	{
		List<ISanpham> sanphamKMList = new ArrayList<ISanpham>();
		
		String query = "select b.kho_fk as khoId, e.kbh_fk as kbhId, a.ctkmId, a.spMa, d.ten as spten, d.pk_seq as spId, sum(a.soluong) as soluong from donhang_ctkm_trakm a inner join ctkhuyenmai b on a.ctkmid = b.pk_seq inner join sanpham d on a.spMa = d.ma inner join donhang e on a.donhangId = e.pk_seq "; 
		query += "where e.trangthai != 2 and a.spMa is not null and a.donhangId in (select donhang_fk from phieuxuatkho_donhang where pxk_fk = '" + pxkId + "') group by b.kho_fk, e.kbh_fk, a.ctkmId, a.spMa, d.ten, d.pk_seq";
		ResultSet spKhuyenmaiRS = db.get(query);

		if(spKhuyenmaiRS != null)
		{
			try 
			{
				while(spKhuyenmaiRS.next())
				{
					String[] param = new String[8];
					ISanpham sp = null;	
													
					param[0] = spKhuyenmaiRS.getString("spId");
					param[1] = spKhuyenmaiRS.getString("spma");
					param[2] = spKhuyenmaiRS.getString("spTen");
					param[3] = spKhuyenmaiRS.getString("soluong");
					
					//luu kho
					param[4] = "";
					if(spKhuyenmaiRS.getString("khoId") != null)
						param[4] = spKhuyenmaiRS.getString("khoId");
					
					//luu kenh ban hang
					param[5] = "";
					if(spKhuyenmaiRS.getString("kbhId") != null)
						param[5] = spKhuyenmaiRS.getString("kbhId");
					
					//luu ctkm
					param[6] = "";
					if(spKhuyenmaiRS.getString("ctkmId") != null)
						param[6] = spKhuyenmaiRS.getString("ctkmId");
					
					param[7] = "";
					
					sp = new Sanpham(param);
					sanphamKMList.add(sp);
				}
				spKhuyenmaiRS.close();
			}
			catch(Exception e) {}
		}

		return sanphamKMList;
	}

	private List<ISanpham> createTienKmList(String pxkId, dbutils db)
	{
		List<ISanpham> sanphamKMList = new ArrayList<ISanpham>();
		
		String query = "select ctkmID, sum(tonggiatri) as tonggiatri from donhang_ctkm_trakm a inner join donhang b on a.donhangId = b.pk_seq "; 
		query += "where b.trangthai != '2' and a.spMa is null and a.donhangId in (select donhang_fk from phieuxuatkho_donhang where pxk_fk = '" + pxkId + "') group by ctkmID";
		ResultSet spKhuyenmaiRS = db.get(query);

		if(spKhuyenmaiRS != null)
		{
			try 
			{
				while(spKhuyenmaiRS.next())
				{
					String[] param = new String[8];
					ISanpham sp = null;	
													
					param[0] = "";
					param[1] = "";
					param[2] = "";
					param[3] = "";
					param[4] = "";
					param[5] = "";
					
					//luu scheme
					param[6] = "";
					if(spKhuyenmaiRS.getString("ctkmId") != null)
						param[6] = spKhuyenmaiRS.getString("ctkmId");
					
					//luu tong gia tri
					param[7] = "";
					if(spKhuyenmaiRS.getString("tonggiatri") != null)
						param[7] = spKhuyenmaiRS.getString("tonggiatri");

					sp = new Sanpham(param);
					sanphamKMList.add(sp);
				}
				spKhuyenmaiRS.close();
			}
			catch(Exception e) {}
		}
		return sanphamKMList;	
	}
	
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}
