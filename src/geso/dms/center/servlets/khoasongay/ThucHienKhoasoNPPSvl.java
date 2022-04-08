package geso.dms.center.servlets.khoasongay;

import geso.dms.center.beans.khoasongay.IKhoasotudong;
import geso.dms.center.beans.khoasongay.imp.Khoasotudong;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

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


public class ThucHienKhoasoNPPSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    public ThucHienKhoasoNPPSvl() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		IKhoasotudong obj;
		PrintWriter out; 
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out  = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    obj = new Khoasotudong();
	    obj.setUserId(userId);
	 
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Center/ThucHienKhoaSoNpp.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		IKhoasotudong obj = new Khoasotudong();
		PrintWriter out; 
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out  = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    
	    Utility util=new Utility();
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
		
		obj = new Khoasotudong();
	    
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    System.out.println ("action  : "+action);
	    
	   
	    
	    String NgayKs= util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngayks")));
	    obj.SetNgayKhoaSo(NgayKs);
	    
	    System.out.println ("Ngay KS : "+NgayKs);
		if(action.equals("save"))
		{ 
					dbutils db=new dbutils();
			
					int  i=1;
					while (i!=0 ){
					// thuc hien khoa so
					 String sql=    " SELECT Max (ngayks) as ngayksgannhat, NPP_FK  , " +
					 				"  convert(char(10) , DATEADD(day,1, cast(Max (ngayks) as datetime)) , 120)  as ngayks FROM KHOASONGAY  where NPP_FK    " +
					 				" IN (select PK_SEQ from nhaphanphoi where   trangthai=1 and PRIANDSECOND='0') "+
					 				" group by NPP_FK  HAVING Max(NGAYKS)<'"+NgayKs+"' ";
		
							System.out.println("SQL  : "+sql);
							i=0;
							try{
								ResultSet rs=db.get(sql);
								if(rs!=null){
									   while (rs.next()){ 
										  i++;
										  String ngayksgannhat =rs.getString("ngayksgannhat");
										  String ngayks =rs.getString("ngayks");
										  System.out.println("ngayksgannhat   : "+ngayksgannhat);
										  System.out.println("ngayks   : "+ngayks);
										  
											String msg=  this.KhoaSoNgayNPP(db,ngayksgannhat, ngayks,rs.getString("NPP_FK"),userId);
											System.out.println("Error   : "+msg);
											/*if(!msg.equals("")){
												break;
											}*/
									   }
								}
							
							}catch (Exception  er){
								System.out.println(er.toString());
								
							}
					
					}
					db.shutDown();
		}
		session.setAttribute("obj", obj);
		String nextJSP = "/AHF/pages/Center/ThucHienKhoaSoNpp.jsp";
		response.sendRedirect(nextJSP);
	}
	  
	public String KhoaSoNgayNPP(dbutils db, String ngayksgn,String ngayks,String nppId,String userId ) 
	{	
		try 
		{
			
			String MsgKhoaSo="";
			String spma = "";
			String query = "select pxk_sp.Ma "+  
				 " from "+   
				" ( "+  
				" 		select npp_fk, kbh_fk, kho_fk, MA, spId, sum(soluong) as soluong "+   
				" 		from(    "+
				" 			select  npp_fk,kbh_fk, kho_fk, MA, sum(SOLUONG) as soluong, b.PK_SEQ as spId from " +
				" PHIEUXUATKHO_SANPHAM a inner join SANPHAM b on a.SANPHAM_FK = b.PK_SEQ "+  
				" 			inner join  PHIEUXUATKHO pxk on pxk.pk_Seq=a.pxk_fk  "+
				" 			where pxk.TRANGTHAI = '1' and pxk.NGAYLAPPHIEU = '"+ngayks+"' and pxk.npp_fk ="+nppId +
				" 			group by kbh_fk, kho_fk, b.PK_SEQ, MA,npp_fk   "+
				" 			union all   "+
				" 			select  npp_fk,kbh_fk, kho_fk, MA, sum(SOLUONG) as soluong, b.PK_SEQ as spId from" +
				" PHIEUXUATKHO_SPKM  a inner join SANPHAM b on a.SANPHAM_FK = b.PK_SEQ  "+ 
				" 			 inner join  PHIEUXUATKHO pxk on pxk.pk_Seq=a.pxk_fk  "+
				" 			 where pxk.TRANGTHAI = '1' and pxk.NGAYLAPPHIEU = '"+ngayks+"' and pxk.npp_fk ="+nppId +
				" 			group by kbh_fk, kho_fk, b.PK_SEQ, MA ,npp_fk  "+
				" 		   ) a    "+
				" 		 group by kbh_fk, kho_fk, MA, spId ,npp_fk   "+
				" 		) pxk_sp     "+
				" 		inner join    "+
				" 		(    "+
				" 			select kho_fk, npp_fk, sanpham_fk, kbh_fk, soluong from nhapp_kho where npp_fk ="+nppId +
				" 		) kho_npp on pxk_sp.kho_fk = kho_npp.kho_fk and pxk_sp.kbh_fk = kho_npp.kbh_fk and  "+
				" 		 pxk_sp.spId = kho_npp.sanpham_fk and  pxk_sp.npp_fk = kho_npp.npp_fk "+
				" 		where (kho_npp.soluong) < 0";
			
			
			ResultSet spRs = db.get(query);
			if(spRs != null)
			{
				while(spRs.next())
				{
					spma += spRs.getString("MA") + ",";
				}
				spRs.close();
			}
			if(spma.length() > 0)
			{
				MsgKhoaSo  = "Các sản phẩm sau: " + spma + " không còn số lượng trong kho, bạn phải nhập hàng về mới có thể khóa sổ ngày.";
				return MsgKhoaSo;
			}
			

			
			query = userId + "&" + nppId + "&" + ngayksgn + "&" + ngayks;
			
			String param[] = query.split("&");
			
			int kq = db.execProceduce("Khoa_So_Ngay", param);
			if(kq == -1)
			{
				MsgKhoaSo = "Không thể khóa sổ ngày." ;
				return MsgKhoaSo;
			}
			
			
			
		} 
		catch(Exception e) 
		{
		
			return e.toString();
		}
		return "";
	}


	public String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
