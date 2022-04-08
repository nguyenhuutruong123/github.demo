package geso.dms.center.servlets.duyettratrungbay;

import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.NumberFormat;
import java.text.DecimalFormat;

public class DuyetAnhTrungBayAjaxSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public DuyetAnhTrungBayAjaxSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		this.doPost(request, response);
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		
		PrintWriter out = response.getWriter();
		System.out.println("DuyetAnhTrungBayAjaxSvl vo");
		HttpSession session = request.getSession();
		Utility util = new Utility();
		String khId = util.antiSQLInspection(request.getParameter("khId"));
		String duyetId = util.antiSQLInspection(request.getParameter("duyetId"));
		String loai =util.antiSQLInspection(request.getParameter("loai")); 
		
		
		System.out.println("ddkdId = "+ util.antiSQLInspection(request.getParameter("ddkdId"))  );
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		NumberFormat formatter = new DecimalFormat("#,###,###");
		
		dbutils db = new dbutils();
		String query =  "";
		
		
		try 
		{
			if(khId != null && duyetId != null && loai != null)
			{
				db.getConnection().setAutoCommit(false);
				
				query = "UPDATE DENGHITRATRUNGBAY  SET trangthai = 0 WHERE trangthai = 0 and PK_SEQ = " + duyetId;

				if(db.updateReturnInt(query)!=1){

				
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					db.shutDown();
					out.write("Vui lòng kiểm tra trạng thái duyệt trả");
					return ;

				}
				query = " update DENGHITRATB_KHACHHANG set duyetAnh = "+loai+",thoidiemDuyetAnh =  dbo.GetLocalDate(DEFAULT) , nguoiDuyetAnh ="+session.getAttribute("userId") +"  where duyetAnh = 0 and khachhang_fk = "+khId+" and  denghitratb_fk = " + duyetId;
			System.out.println(query);
				if(db.updateReturnInt(query)!=1){

					
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					db.shutDown();
					out.write("Lỗi duyệt ảnh KH");
					return ;

				}
				query = " insert DENGHITRATB_KHACHHANG_DUyetAnh(duyetanh,denghitratb_fk,khachhang_fk,nguoitao) select  "+loai+","+duyetId+","+khId+","+session.getAttribute("userId") +"   ";
				if(db.updateReturnInt(query)!=1){					
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					db.shutDown();
					out.write("Lỗi duyệt ảnh KH");
					return ;

				}
				
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
				db.shutDown();
				return;
				
			}
			else
			{
				db.shutDown();
				out.write("Lỗi dữ liệu!");
				return;
			}
					
		} 
		catch(Exception e)
		{ 
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			db.shutDown();
			out.write("Lỗi:" + e.getMessage());
			return;
		}
		

	}

}
