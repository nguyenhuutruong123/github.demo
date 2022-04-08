package geso.dms.center.servlets.thietlapnamtaichinh;

import geso.dms.center.beans.thietlapnamtaichinh.IThietLapNamTaiChinh;
import geso.dms.center.beans.thietlapnamtaichinh.imp.ThietLapNamTaiChinh;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class thietlapnamtaichinhSvl  extends HttpServlet  
{
	private static final long serialVersionUID = 1L;
	public thietlapnamtaichinhSvl() 
	{
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		IThietLapNamTaiChinh obj = null;
		PrintWriter out; 
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		out  = response.getWriter();

		HttpSession session = request.getSession();	    

		Utility util = new Utility();
		out = response.getWriter();

		String querystring = request.getQueryString();
		System.out.println("Truy van TLNTC "+querystring);
		String userId = util.getUserId(querystring);
		out.println(userId);

		if (userId.length()==0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

		String action=util.getAction(querystring);
		String id=util.getId(querystring);
		String ngayxoa = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("delete"));
		if(ngayxoa != null)
			ngayxoa = ngayxoa.substring(0, 4);
		if(action.equals("delete"))
		{
			if(!this.Xoa(ngayxoa))
				obj.setMsg("Lỗi không xóa được");
		}

		obj = new ThietLapNamTaiChinh();


		obj.setUserId(userId);

		session.setAttribute("obj", obj);

		String nextJSP = "/AHF/pages/Center/ThietLapNamTaiChinh.jsp";
		response.sendRedirect(nextJSP);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{

		IThietLapNamTaiChinh obj = new ThietLapNamTaiChinh();
		PrintWriter out; 
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		out  = response.getWriter();

		HttpSession session = request.getSession();	    
		Utility util=new Utility();
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));


		obj = new ThietLapNamTaiChinh();

		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		System.out.println ("action  : "+action);

		String NgayTL= util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaytl")));
		String DienGiai= util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
		obj.SetNgayThietLap(NgayTL);
		String activeTab = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("activeTab")));
		if (activeTab == null)
			activeTab = "";
		obj.setActiveTab(activeTab);
		Calendar cal = Calendar.getInstance();
		String ngaytao =  getDateTime();
		if(action.equals("save"))
		{ 
			ResultSet kq = null;
			dbutils db=new dbutils();
			String sqlkt =  null;
			String nam = NgayTL.substring(0,4);
			SimpleDateFormat fomat = new SimpleDateFormat("yyyy-MM-dd");
			sqlkt = "select NgayTl from TLNTC order by Ngaytl";

			kq = db.get(sqlkt);
			boolean exitst = false;
			try
			{


				try {
					Date ktngtl = fomat.parse(NgayTL);
					while (kq.next())
					{
						Date ngaydathietlap = fomat.parse(kq.getString("NgayTl"));
						Calendar ckt = Calendar.getInstance();
						ckt.setTime(ngaydathietlap);
						ckt.add(Calendar.YEAR,1);
						Date ngayss = fomat.parse(fomat.format(ckt.getTime()));
						if(ktngtl.compareTo(ngayss) < 0)
						{
							exitst = true;
							break;

						}

					}

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("kt "+exitst);   
			if(exitst == false)
			{
				String ngaybdk,ngayktk,ky,tenk,namk,thangk;
				Date nbdk = null,nktk,nbdq = null,nktq;
				int k,q;
				Calendar c = Calendar.getInstance();
				
				// thêm năm tài chính
				String sql="insert into TLNTC (TLNTC_PK,DienGiai,NgayTL,NgayTao,NguoiTao,NgaySua,NguoiSua) values ('"+nam+"',N'"+DienGiai+"','"+NgayTL+"','"+ngaytao+"',"+userId+",'"+ngaytao+"',"+userId+")";
				if(!db.update(sql))
				{
					System.out.println("Insert "+sql);
					String loi = "Lỗi không thể thêm !";
					obj.setMsg(loi);

				}
				else
				{
					System.out.println("Cau lenh "+sql);
					k = 1;
					q = 1;
					try 
					{
						db.getConnection().setAutoCommit(false);
						nbdk = fomat.parse(NgayTL);
						nbdq = fomat.parse(NgayTL);
					
					int ki = 1;
					String ngaybatdau = "";
					while(k <= 365)
						{	
							ngaybdk = fomat.format(nbdk);
							c.setTime(nbdk);
							
							if(ki == 1)
							{
								
								// tăng ngày lên 1
								c.add(Calendar.DAY_OF_YEAR, 1);
								nbdk = fomat.parse(fomat.format(c.getTime()));
								System.out.println("IN k "+k+" thu"+c.get(c.DAY_OF_WEEK)+" ngay"+c.DAY_OF_YEAR);
							}	
							else
							{
								
								c.add(Calendar.DAY_OF_YEAR, 26);
								ngayktk = fomat.format(c.getTime());
								if (ki == 13)
								{
									c.setTime(fomat.parse(NgayTL));
									c.add(Calendar.YEAR, 1);
									c.add(Calendar.DAY_OF_YEAR, -1);
									nktk = fomat.parse(fomat.format(c.getTime()));
	
									ngayktk = fomat.format(nktk);
								}
								thangk = ngayktk.substring(5,7);	
								if(thangk.charAt(0) == '0')
									thangk = thangk.substring(1);
								namk = ngayktk.substring(0, 4);
								tenk = ""+ki;
								String sql1 ="insert into Ky (TLNTC_FK,TenKy,Thang,Nam,TuNgay,DenNgay) values ('"+nam+"',N'"+tenk+"','"+thangk+"','"+namk+"','"+ngaybatdau+"','"+ngayktk+"')";
								
								if (!db.update(sql1))
								{
									System.out.println("LOI KI 2-13");
									obj.setMsg("Loi insert: "+sql1);
									geso.dms.center.util.Utility.rollback_throw_exception(db);
									
									break;
	
								}
								c.add(Calendar.DAY_OF_YEAR,2);
								nbdk = fomat.parse(fomat.format(c.getTime()));
								ngaybatdau = fomat.format(nbdk);
								ki++;
							}
						
							if(k > 21 && c.get(c.DAY_OF_WEEK) ==  7 && ki == 1)
							{
								System.out.println("thu "+c.get(c.DAY_OF_WEEK));
								
								ngaybatdau = NgayTL;
								System.out.println("Ngaybatdauki 1 "+ngaybatdau);
								ngayktk = fomat.format(c.getTime());
								thangk = ngayktk.substring(5,7);	
								if(thangk.charAt(0) == '0')
									thangk = thangk.substring(1);
								namk = ngayktk.substring(0, 4);
								tenk = ""+ki;
								String sql1 ="insert into Ky (TLNTC_FK,TenKy,Thang,Nam,TuNgay,DenNgay) values ('"+nam+"',N'"+tenk+"','"+thangk+"','"+namk+"','"+ngaybatdau+"','"+ngayktk+"')";
							
								if (!db.update(sql1))
								{
	
									obj.setMsg("Lỗi insert: "+sql1);
									System.out.println("Lỗi "+sql1);
									geso.dms.center.util.Utility.rollback_throw_exception(db);
									break;
	
								}
								c.add(Calendar.DAY_OF_YEAR, 2);
								nbdk = fomat.parse(fomat.format(c.getTime()));
								ngaybatdau = fomat.format(nbdk);
								ki++;
							}
							if(ki == 14) break;
							
							
							k++;
	
						}
					 // thiết lập quý 1
					 sql = "insert into quy  ( [TLNTC_FK],[Ten] ,[Quy],[Nam],[TuNgay],[DenNgay] )"
					 		+ " select [TLNTC_FK],'1' as ten,'1' as quy,[Nam],[TuNgay],[DenNgay] from ky "
					 		+ " where TLNTC_FK = '"+nam+"' and tenky = '1'  ";
					 if(!db.update(sql))
					 {
						 System.out.println("Lỗi không thêm được "+sql);
						 geso.dms.center.util.Utility.rollback_throw_exception(db);
						 
					 }
					 sql = "update quy set denngay = (select denngay from ky where tenky = '3') where quy = '1' ";
					 if(!db.update(sql))
					 {
						 System.out.println("Lỗi update được "+sql);
						 geso.dms.center.util.Utility.rollback_throw_exception(db);
						 
					 }
					 
					 // thiết lập quý 2
					 sql = "insert into quy  ( [TLNTC_FK],[Ten] ,[Quy],[Nam],[TuNgay],[DenNgay] )"
						 		+ "  select [TLNTC_FK],'2' as ten,'2' as quy,[Nam],[TuNgay],[DenNgay] from ky "
						 		+ " where TLNTC_FK = '"+nam+"' and tenky = '4' ";
						 if(!db.update(sql))
						 {
							 System.out.println("Lỗi không thêm được "+sql);
							 
						 }
						 sql = "update quy set denngay = (select denngay from ky where tenky = '6') where quy = '2' ";
						 if(!db.update(sql))
						 {
							 System.out.println("Lỗi update được "+sql);
							 
						 }
						 
						 // thiết lập quý 3
						 sql = "insert into quy  ( [TLNTC_FK],[Ten] ,[Quy],[Nam],[TuNgay],[DenNgay] )"
							 		+ "  select [TLNTC_FK],'3' as ten ,'3' as quy ,[Nam],[TuNgay],[DenNgay] from ky "
							 		+ " where TLNTC_FK = '"+nam+"' and tenky = '7'  ";
							 if(!db.update(sql))
							 {
								 System.out.println("Lỗi không thêm được "+sql);
								 geso.dms.center.util.Utility.rollback_throw_exception(db);
								 
							 }
							 sql = "update quy set denngay = (select denngay from ky where tenky = '9') where quy = '3' ";
							 if(!db.update(sql))
							 {
								 System.out.println("Lỗi update được "+sql);
								 geso.dms.center.util.Utility.rollback_throw_exception(db);
								 
							 } 
							 
							 // thiết lập quý 4
							 sql = "insert into quy  ( [TLNTC_FK],[Ten] ,[Quy],[Nam],[TuNgay],[DenNgay] )"
								 		+ "  select [TLNTC_FK],'4' as ten,'4' as quy,[Nam],[TuNgay],[DenNgay] from ky "
								 		+ " where TLNTC_FK = '"+nam+"' and tenky = '10' ";
								 if(!db.update(sql))
								 {
									 System.out.println("Lỗi không thêm được "+sql);
									 
								 }
								 sql = "update quy set denngay = (select denngay from ky where tenky = '13') where quy = '4' ";
								 if(!db.update(sql))
								 {
									 System.out.println("Lỗi update được "+sql);
									 geso.dms.center.util.Utility.rollback_throw_exception(db);
									 
								 } 
					db.getConnection().commit();
					db.getConnection().setAutoCommit(true);
					} catch (ParseException e)
					{
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
			else
			{
				String loi = "Năm tài chính đã thiết lập !";
				obj.setMsg(loi);
			}
			try {
				kq.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			db.shutDown();
		}
		
		session.setAttribute("obj", obj);
		obj.init();
		String nextJSP = "/AHF/pages/Center/ThietLapNamTaiChinh.jsp";
		response.sendRedirect(nextJSP);
	}
	public String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	private boolean Xoa(String id) {
		// TODO Auto-generated method stub
		dbutils db=new dbutils();

		try
		{
			db.getConnection().setAutoCommit(false);
			String sql1 = "Delete Quy where TLNTC_FK = "+"'"+id+"'";
			String sql2 = "Delete Ky where TLNTC_FK = "+"'"+id+"'";
			String sql3="Delete TLNTC where TLNTC_PK = "+"'"+id+"'";
			System.out.println("SQL1 "+sql1 );
			if( !db.update(sql1))
			{
				System.out.println("Khong xóa đc");
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}


			if( !db.update(sql2))
			{
				System.out.println("Khong xóa đc");
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;

			}
			if( !db.update(sql3))
			{
				System.out.println("Khong xóa đc");
				geso.dms.center.util.Utility.rollback_throw_exception(db);

				return false;

			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);

		}catch(Exception er)
		{
			System.out.println("Loi___________________");
			return false;
		}




		db.shutDown();
		return true;

	}
}
