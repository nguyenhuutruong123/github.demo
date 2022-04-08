package geso.dms.distributor.servlets.khachhang;

import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AjaxPhuongXa")
public class AjaxPhuongXa extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public AjaxPhuongXa()
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Utility util = new Utility();
		

		request.setCharacterEncoding("UTF-8");
	  response.setCharacterEncoding("UTF-8");
	   response.setContentType("text/html; charset=UTF-8");
	    
		PrintWriter out = response.getWriter();
		
		String query = (String)request.getQueryString();
	
		String pxId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("pxId")==null?"":request.getParameter("pxId")));
		String tpId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tpId")==null?"":request.getParameter("tpId")));
		String qhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("qhId")==null?"":request.getParameter("qhId")));
		String action = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")==null?"":request.getParameter("action")));
		
		String msg="";
		if(action.equals("save"))
		{
			String pxTen = new String(query.substring(query.indexOf("pxTen=") + 6, query.indexOf("&id=")).getBytes("UTF-8"), "UTF-8");
			pxTen = URLDecoder.decode(pxTen, "UTF-8").replace("+", " ");
			 msg = this.createPhuongXa(qhId,tpId,pxTen);	
		}else if(action.equals("delete"))
		{
			 msg = this.DetelePhuongXa(pxId);
		}		
		out.write(msg);
	}
	
	private String DetelePhuongXa(String pxId)
  {
		dbutils db = new dbutils();
		String msg = "";
		try
    {
	    db.getConnection().setAutoCommit(false);
	    String  query="select count(*) as SoDong from KhachHang where PhuongXa_fk='"+pxId+"' ";
	    int soDong=0;
	    ResultSet rs=db.get(query);
	    while(rs.next())
	    {
	    	soDong=rs.getInt("SoDong");
	    }
	    if(soDong>0)
	    {
	    	msg="Phường xã đã phát sinh dữ liệu. !";
	    }
	    
	    query="delete from phuongxa where pk_Seq='"+pxId+"' ";
			if(!db.update(query))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				msg="Lỗi tạo mới"+query;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
    } catch (SQLException e)
    {
	    e.printStackTrace();
	    return "Exception";
    }
		return msg;
  }

	private String createPhuongXa(String qhId,String ttId,String pxTen )
  {
		dbutils db = new dbutils();
		String msg = "";
		try
    {
	    db.getConnection().setAutoCommit(false);
	    String  query="select count(*) as SoDong from PhuongXa where ten=N'"+pxTen+"' and QuanHuyen_fk='"+qhId+"' and TinhThanh_fk='"+ttId+"' ";
	    int soDong=0;
	    ResultSet rs=db.get(query);
	    while(rs.next())
	    {
	    	soDong=rs.getInt("SoDong");
	    }
	    if(soDong>0)
	    {
	    	msg="Phường xã đã tạo rồi !";
	    }
	    
	    query="insert into PhuongXa(Ten,QuanHuyen_fk,TinhThanh_fk) select N'"+pxTen+"','"+qhId+"','"+ttId+"'";
			if(!db.update(query))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				msg="Lỗi tạo mới"+query;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
    } catch (SQLException e)
    {
	    e.printStackTrace();
	    return "Exception";
    }
		return msg;
  }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
	
		
	}
	
}
