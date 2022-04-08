package geso.dms.center.servlets.duyetdonhangnpp;

import geso.dms.center.beans.duyettradonhang.IDuyettradonhang;
import geso.dms.center.beans.duyettradonhang.IDuyettradonhangList;
import geso.dms.center.beans.duyettradonhang.imp.Duyettradonhang;
import geso.dms.center.beans.duyettradonhang.imp.DuyettradonhangList;
import geso.dms.distributor.beans.donhangtrave.ISanpham;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DuyettradonhangUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
	
	
    public DuyettradonhangUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		IDuyettradonhang dtdhBean;
		List<ISanpham> spList;
		PrintWriter out;
		
		HttpSession session = request.getSession();
		
		out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	        
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String id = util.getId(querystring);

	    dtdhBean = new Duyettradonhang(id);
        dtdhBean.setUserId(userId); //phai co UserId truoc khi Init
        dtdhBean.init();
     
        session.setAttribute("dtdhBean", dtdhBean);
        String nextJSP = "/AHF/pages/Center/DuyetDonHangTraUpdate.jsp";
        
        if(request.getQueryString().indexOf("display") >= 0 ) 
        	nextJSP = "/AHF/pages/Center/DuyetDonHangTraDisplay.jsp";
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		IDuyettradonhang dtdhBean;
		List<ISanpham> spList;
		PrintWriter out;
		
		HttpSession session = request.getSession();
		
		Utility util = new Utility();
		String querystring = request.getQueryString();
	    
	    String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
	    if(id == null)
	    	id = "";
	    dtdhBean = new Duyettradonhang(id);
	    
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    dtdhBean.setUserId(userId);
	    
	    String dhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("madonhang")));
	    if(dhId == null)
	    	dhId = "";
	    dtdhBean.setDhId(dhId);
	    
	    String ngaytra = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
	    if(ngaytra == null || ngaytra == "")
	    	ngaytra = this.getDateTime();
	    dtdhBean.setNgaygiaodich(ngaytra);
	    
	    String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppTen")));
	    if(nppId == null)
	    	nppId = "";
	    dtdhBean.setNppId(nppId);
	    
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		
		if(action.equals("save"))
		{
			if(id == "")
			{
				if (!(dtdhBean.createDtdhtrave(nppId, userId)))
				{							
					dtdhBean.createRS();
					
			    	session.setAttribute("dtdhBean", dtdhBean);
		    		String nextJSP = "/AHF/pages/Center/DuyetDonHangTraNew.jsp";
		    		response.sendRedirect(nextJSP);
				}
				else
				{
					IDuyettradonhangList obj = new DuyettradonhangList();
				    obj.setUserId(userId);
				    obj.init("");
				    
					session.setAttribute("obj", obj);
							
					String nextJSP = "/AHF/pages/Center/DuyetDonHangTra.jsp";
					response.sendRedirect(nextJSP);    									
				}
			}
			else //xac nhan duyet tra don hang
			{
				if (!(dtdhBean.duyetSptrave(id, userId)))
				{							
			        dtdhBean.init();
			        
			        session.setAttribute("dtdhBean", dtdhBean);
			        String nextJSP = "/AHF/pages/Center/DuyetDonHangTraUpdate.jsp";
			        
			        response.sendRedirect(nextJSP);
				}
				else
				{
					IDuyettradonhangList obj = new DuyettradonhangList();
					obj.setUserId(userId);
					obj.init("");
					    
					session.setAttribute("obj", obj);
								
					String nextJSP = "/AHF/pages/Center/DuyetDonHangTra.jsp";
					response.sendRedirect(nextJSP);	    			    									
				}
			}
		}
		else
		{
			if(action.equals("checkDtdh"))
			{
				dbutils db = new dbutils();
				
				//check xem donhang nay da tra chua
				if(checkDonhangdatra(dhId, db))
				{
					dtdhBean.createRS();
					dtdhBean.setMessage("Đơn hàng này bạn đã trả rồi.");
					dtdhBean.setCheckDhtv(false);
					
			    	session.setAttribute("dtdhBean", dtdhBean);
		    		String nextJSP = "/AHF/pages/Center/DuyetDonHangTraNew.jsp";
		    		response.sendRedirect(nextJSP);
		    		return;
				}
				
				//chi nhung donhang da chpt moi dc phep tra ve, cac truong hop khac su dung chuc nang xoa donhang duoi Npp
				String sql = "select pk_seq, ngaynhap, npp_fk from donhang where pk_seq = '" + dhId + "' and trangthai = '1'";
				ResultSet rs = db.get(sql);
				if(rs != null)
				{
					try 
					{
						String pk_seq = "";
						String npp_fk = "";
						if(rs.next())
						{
							pk_seq = rs.getString("pk_seq");
							npp_fk = rs.getString("npp_fk");
							rs.close();
							

							String msg = checkNgaykhoaso(pk_seq, npp_fk, db);
							if(msg.length() > 0)
							{
								dtdhBean.createRS();
								dtdhBean.setMessage(msg);
								dtdhBean.setCheckDhtv(false);
								
						    	session.setAttribute("dtdhBean", dtdhBean);
					    		String nextJSP = "/AHF/pages/Center/DuyetDonHangTraNew.jsp";
					    		response.sendRedirect(nextJSP);
					    		return;
							}

							String msg2 = checkNgaytradh(pk_seq, npp_fk, db);
							if(msg2.length() > 0)
							{
								dtdhBean.createRS();
								dtdhBean.setMessage(msg2);
								dtdhBean.setCheckDhtv(false);
								
						    	session.setAttribute("dtdhBean", dtdhBean);
					    		String nextJSP = "/AHF/pages/Center/DuyetDonHangTraNew.jsp";
					    		response.sendRedirect(nextJSP);
					    		return;
							}
							
							String ctkm = checkKhuyenMai(pk_seq, db);
							if(ctkm.length() > 0)
							{
								dtdhBean.createRS();
								dtdhBean.setMessage("Chương trình khuyến mại: " + ctkm + " đã hết hiệu lực, bạn không thể trả đơn hàng này.");
								dtdhBean.setCheckDhtv(false);
								
						    	session.setAttribute("dtdhBean", dtdhBean);
					    		String nextJSP = "/AHF/pages/Center/DuyetDonHangTraNew.jsp";
					    		response.sendRedirect(nextJSP);
					    		return;
							}
						}

						//hien thi List sanpham cua don hang
						if(pk_seq.length() > 0)
						{
							dtdhBean.createRS();
							dtdhBean.setCheckDhtv(true);
							dtdhBean.setNppId(npp_fk);
							
							session.setAttribute("dtdhBean", dtdhBean);
				    		String nextJSP = "/AHF/pages/Center/DuyetDonHangTraNew.jsp";
				    		response.sendRedirect(nextJSP);
						}
						else
						{
							dtdhBean.createRS();
							dtdhBean.setMessage("Mã đơn hàng bạn nhập không chính xác hoặc đơn hàng chưa chốt, vui lòng kiểm tra lại");
							dtdhBean.setCheckDhtv(false);
							
					    	session.setAttribute("dtdhBean", dtdhBean);
				    		String nextJSP = "/AHF/pages/Center/DuyetDonHangTraNew.jsp";
				    		response.sendRedirect(nextJSP);
						}
					} 
					catch(Exception e) {}
				}
			}
		}
	}
	
	private boolean checkDonhangdatra(String dhId, dbutils db) 
	{
		//don hang phai sau ngay khoa so gan nhat
		String query = "select pk_seq from donhangtrave where donhang_fk = '" + dhId + "'";
		//System.out.println("Truy van: " + query + "\n");
		
		ResultSet rs = db.get(query);
		
		String msg = "";
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					msg = rs.getString("pk_seq");
					rs.close();
				}
			} 
			catch(Exception e) {}
		}
		if(msg.length() > 0)
			return true; //da tra roi
		return false;

	}

	String ngaykhoaso = "";
	private String checkNgaykhoaso(String dhId, String nppId, dbutils db) 
	{
		//don hang phai sau ngay khoa so gan nhat
		
		String query = "select max(ngayks) as nks from khoasongay where npp_fk = '" + nppId + "'";
		ResultSet rsKs = db.get(query);
		
		if(rsKs != null)
		{
			try 
			{
				if(rsKs.next())
				{
					ngaykhoaso = rsKs.getString("nks");
					rsKs.close();
				}
			} 
			catch(Exception e) {}
		}
		
		String[] ngaythang = ngaykhoaso.split("-");
		int ngay = Integer.parseInt(ngaythang[2]) + 1;
		
		String ngayTang1 = ngaythang[0] + "-" + ngaythang[1] + "-" + Integer.toString(ngay);
		
		query = "select pk_seq, ngaynhap from donhang where pk_seq = '" + dhId + "' and ngaynhap > '" + ngayTang1 + "'";
		System.out.println("Query la: " + query + "\n");
		
		ResultSet rs = db.get(query);
		
		String msg = "";
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					msg = rs.getString("ngaynhap");
				}
				rs.close();
			} 
			catch(Exception e) {}
		}
		
		if(msg.length() > 0)
			msg = "Bạn chỉ được phép trả những đơn hàng sau ngày khóa sổ gần nhất";
		return msg;
	}
	
	private String checkNgaytradh(String dhId, String nppId, dbutils db) 
	{
		//don hang phai trong thang hien tai
		String query = "select ngaynhap from donhang where pk_seq = '" + dhId + "'";
				
		ResultSet rs = db.get(query);
		
		String msg = "";
		String ngaynhap = "";
		
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					ngaynhap = rs.getString("ngaynhap");
					
					//System.out.println("Ngay nhap: " + ngaynhap + " --- Ngay hien tai: " + this.getDateTime() + "\n");
					
					String[] nn = ngaynhap.trim().split("-");
					//String[] date = this.getDateTime().trim().split("-");
					String[] date = this.getDateTime().trim().split("-");
					if(ngaykhoaso.length() > 0)
						date = this.ngaykhoaso.trim().split("-");
					
					
				}
				rs.close();
			} 
			catch(Exception e) {}
		}
		
		return msg;
	}

	private String checkKhuyenMai(String dhId, dbutils db) 
	{
		

		//neu co 1 ctkm da het hieu luc, thi khong cho phep tra don hang
		String query = "select b.pk_seq, b.scheme, b.diengiai from donhang_ctkm_trakm a inner join ctkhuyenmai  b on a.ctkmId = b.pk_seq " +
		 		" inner join donhang dh on dh.pk_seq=a.donhangid " +
		 		"where a.donhangId = '" + dhId + "' and b.denngay <= (select max(ngayks) from khoasongay where npp_fk=dh.npp_fk)";
		System.out.println(query);
		ResultSet rs = db.get(query);
		
		String ctkmId = "";
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					ctkmId += rs.getString("pk_seq") + " - " + rs.getString("diengiai") + ", ";
				}
				rs.close();
			} 
			catch(Exception e) {}
		}
		
		if(ctkmId.length() > 0)
			ctkmId = ctkmId.substring(0, ctkmId.length() - 2);
		return ctkmId;
	}

	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
}
