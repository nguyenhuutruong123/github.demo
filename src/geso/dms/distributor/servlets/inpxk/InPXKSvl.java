package geso.dms.distributor.servlets.inpxk;

import geso.dms.center.beans.doctien.doctienrachu;
import geso.dms.distributor.beans.donhang.IDonhang;
import geso.dms.distributor.beans.donhang.ISanpham;
import geso.dms.distributor.beans.donhang.imp.Donhang;
import geso.dms.distributor.beans.donhang.imp.Sanpham;
import geso.dms.distributor.beans.inpxk.IInPXK;
import geso.dms.distributor.beans.inpxk.IInPXKList;
import geso.dms.distributor.beans.inpxk.imp.InPXK;
import geso.dms.distributor.beans.inpxk.imp.InPXKList;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Z.DB;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class InPXKSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private static final float CONVERT = 0;
	
    public InPXKSvl() 
    {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IInPXKList obj;
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
    		

	    }
	    else
	    {
	    	if(action.equals("delete"))
	    	{
	    	
	    	}
	    }
	   	    
	    obj = new InPXKList();
	    obj.setRequestObj(request);
	    obj.setUserId(userId);
	    
	    obj.init("");
	    obj.setMsg(msg);
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Distributor/InPXK.jsp";
		response.sendRedirect(nextJSP);
	}



	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IInPXKList obj = new InPXKList();
		//PrintWriter out = response.getWriter();; 
		String userId;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    
	    
		HttpSession session = request.getSession();
		
	    userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	    String loi = "";
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    IInPXK pxkBean = new InPXK();
//		
	    
	    
	    String tab = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tab"));
	    obj.setTab(tab);
	    
	    obj.setRequestObj(request);
	    if (action.equals("Tao moi"))
	    {
	    	// Empty Bean for distributor
	    	pxkBean = (IInPXK) new InPXK("");
	    	pxkBean.setUserId(userId);
	    	pxkBean.createRS();
	    	
	    	// Save Data into session
	    	session.setAttribute("pxkBean", pxkBean);
    		
    		String nextJSP = "/AHF/pages/Distributor/PhieuXuatKhoNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    else if (action.equals("InALL"))
			{	
				String dhId[] = request.getParameterValues("InpxkIds");
				String arrdh = "" ;
				if(dhId != null && userId != null)
				{
					System.out.println("__+_+_+_+_____" + dhId);
					for(int i = 0; i < dhId.length ; i++ )
					{
						
						arrdh += ""+dhId[i]+",";
					}
					arrdh= arrdh.substring(0, arrdh.length() -1);
					response.setContentType("application/pdf");
					response.setHeader("Content-Disposition"," inline; filename=PhieuXuatKhoTong.pdf");
					float CONVERT = 28.346457f;
					float PAGE_LEFT = 1.0f*CONVERT, PAGE_RIGHT = 1.5f*CONVERT, PAGE_TOP = 0.5f*CONVERT, PAGE_BOTTOM = 0.0f*CONVERT; //cm
				    Document document = new Document(PageSize.A4, PAGE_LEFT, PAGE_RIGHT, PAGE_TOP, PAGE_BOTTOM);

					dbutils db = new dbutils();
					ServletOutputStream outstream = response.getOutputStream();
					try {
						PdfWriter.getInstance(document, outstream);
					} catch (DocumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					document.open();
					
					
						//pxkBean = new InPXK(dhId);
						pxkBean.setUserId(userId); //phai co UserId truoc khi Init
						pxkBean.init2();	
						
						List<ISanpham> sanphamList = this.createPxk_SpList(arrdh, db);
						List<ISanpham> sanphamKMList = this.createPxk_SpkmList(arrdh, db);
						//List<ISanpham> tienKMList = this.createPxk_TienkmList(dhId, db);
						

						CreatePxkle(document, arrdh, db, userId);

						
						document.newPage();
						sanphamList.clear();
						sanphamKMList.clear();
						//tienKMList.clear();

						System.out.println("action in _+_+_" +action);
					
					pxkBean.DBclose();
					db.shutDown();
					document.close(); 
				}
				else
					loi ="Không thể in phiếu xuất kho tổng !";

				String search = getSearchQuery(request,obj);
				obj.setMsg(loi);
				/*		obj.setUserId(userId);
				obj.init(search);    	  	
				session.setAttribute("userId", userId);
				session.setAttribute("obj", obj);
				response.sendRedirect("/AHF/pages/Distributor/DonHang.jsp");	    */
			}
	    else if (action.equals("InPXK"))
		{	
	    	dbutils db = new dbutils();
	    	String pxkId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("pxkId"));
	    	
			String query = " select * from PXKIN_DH where pxkin_fk = "+pxkId+" ";
			ResultSet rs = db.get(query);
			
			String arrdh = "" ;
			
				if(rs != null)
				{
					try {
						while(rs.next())
						{
							arrdh += ""+rs.getString("donhang_fk")+",";
						}
						//arrdh= arrdh.substring(0, arrdh.length() -1);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				 query = " select ngaytao from PXKIN where pk_seq = "+pxkId+" ";
				 rs = db.get(query);
				
				String ngaytao = "" ;
				
					if(rs != null)
					{
						try {
							while(rs.next())
							{
								ngaytao = rs.getString("ngaytao");
							}
							//arrdh= arrdh.substring(0, arrdh.length() -1);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				arrdh= arrdh.substring(0, arrdh.length() -1);
				System.out.println("danh sach don hang " + arrdh);
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition"," inline; filename=PhieuXuatKhoTong.pdf");
				//Document document = new Document(PageSize.A5.rotate(),10, 20, 10, 20);
				float CONVERT = 28.346457f;
				float PAGE_LEFT = 1.0f*CONVERT, PAGE_RIGHT = 1.5f*CONVERT, PAGE_TOP = 0.5f*CONVERT, PAGE_BOTTOM = 0.0f*CONVERT; //cm
			    Document document = new Document(PageSize.A4, PAGE_LEFT, PAGE_RIGHT, PAGE_TOP, PAGE_BOTTOM);
				ServletOutputStream outstream = response.getOutputStream();
				try {
					PdfWriter.getInstance(document, outstream);
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				document.open();
				
				
					//pxkBean = new InPXK(dhId);
					pxkBean.setUserId(userId); //phai co UserId truoc khi Init
					pxkBean.init2();	
					
					List<ISanpham> sanphamList = this.createPxk_SpList(arrdh, db);
					List<ISanpham> sanphamKMList = this.createPxk_SpkmList(arrdh, db);
					//List<ISanpham> tienKMList = this.createPxk_TienkmList(dhId, db);
					

					CreatePxk(document, arrdh, db, userId, ngaytao);

					
					document.newPage();
					sanphamList.clear();
					sanphamKMList.clear();
					//tienKMList.clear();

					System.out.println("action in _+_+_" +action);
				
				pxkBean.DBclose();
				db.shutDown();
				document.close(); 
			

			String search = getSearchQuery(request,obj);
			obj.setMsg(loi);
			/*		obj.setUserId(userId);
			obj.init(search);    	  	
			session.setAttribute("userId", userId);
			session.setAttribute("obj", obj);
			response.sendRedirect("/AHF/pages/Distributor/DonHang.jsp");	    */
		}
	    else if (action.equals("XoaPXK"))
		{	
	    	dbutils db = new dbutils();
	    	String pxkId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("pxkId"));
	    	try{
			String query = " delete from PXKIN_DH where pxkin_fk = "+pxkId+" ";
			System.out.println("_+_+_" +query);
			db.update(query);
			query = "delete from PXKIN where pk_seq = "+pxkId+" ";
			db.update(query);
	    	}catch(Exception e){e.getStackTrace();}
			
			

			String search = getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
    		session.setAttribute("abc", search);
		
    		response.sendRedirect("/AHF/pages/Distributor/InPXK.jsp");	    
		}
	    else if(action.equals("clear"))
	    {
	    	obj.setUserId(userId);
	    	obj.init("");
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
    		response.sendRedirect("/AHF/pages/Distributor/InPXK.jsp");	 
	    }
	    else if(action.equals("LuuPXK"))
	    {
	    	
	    	dbutils db = new dbutils();
	    	try {
				db.getConnection().setAutoCommit(false);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	String dhId[] = request.getParameterValues("InpxkIds");
	    	if(dhId != null && userId != null)
			{
	    		String sql = 	" insert into PXKIN (nguoitao, ngaytao)" +
	    						" values ('"+userId+"','"+getDateTime()+"')";
	    		System.out.println("lưu đh +_+ " + sql);
	    	
	    		if(!db.update(sql)  )
	    		{
	    			System.out.println("Lỗi không thêm được"+sql);
	    			try {
	    				db.getConnection().rollback();
	    			} catch (SQLException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			}
	    			return;
	    		}	    		
	    		String query = " select scope_identity() as Id from PXKIN ";
	    		System.out.println("lấy ra id +_+ " + query );
	    		ResultSet rs = db.get(query);
	    		if(rs != null)
					try {
						rs.next();
						
							for(int i = 0; i < dhId.length; i++)
							{
									sql = " update donhang set trangthaiin = 1 " +
									" where pk_seq = '"+dhId[i]+"' ";
									System.out.println("lưu đh +_+ " + sql);
									if(!db.update(sql)  )
									{
										System.out.println("Lỗi không thêm được "+sql);
										try {
											db.getConnection().rollback();
										} catch (SQLException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										return;
									}
									sql = " insert into PXKIN_DH (pxkin_fk, donhang_fk)" +
										 	" values ('"+rs.getString("id")+"','"+dhId[i]+"' )";
									System.out.println("lưu đh +_+ " + sql);
									if(!db.update(sql)  )
									{
										System.out.println("Lỗi không thêm được "+sql);
										try {
											db.getConnection().rollback();
										} catch (SQLException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										return;
									}

							}
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
	    	try {
				db.getConnection().commit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				db.getConnection().setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	 
			/*String search = getSearchQuery(request, obj);
	    	
	    	obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
	    	obj.setUserId(userId);
	    	obj.init(search);
			session.setAttribute("obj", obj);
			obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
			String nextJSP = "/AHF/pages/Distributor/InPXK.jsp";
			response.sendRedirect(nextJSP);
			obj.setUserId(userId);
	    	obj.init1(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
    		session.setAttribute("abc", search);
		
    		response.sendRedirect("/AHF/pages/Distributor/InPXK.jsp");	   */
			String search = getSearchQueryxk(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init1(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
    		session.setAttribute("abc", search);
		
    		response.sendRedirect("/AHF/pages/Distributor/InPXK.jsp");	  
	    }
	    else if(action.equals("view") || action.equals("next") || action.equals("prev")){
	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
	    	obj.setUserId(userId);
	    	obj.init(search);
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	session.setAttribute("obj", obj);
	    	response.sendRedirect("/AHF/pages/Distributor/InPXK.jsp");	
	    }else if(action.equals("searchxk"))
	    {
	    	obj = new InPXKList();
	    	obj.setRequestObj(request);
	    	obj.setTab(tab);

	    	String search = getSearchQueryxk(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init1(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
    		session.setAttribute("abc", search);
		
    		response.sendRedirect("/AHF/pages/Distributor/InPXK.jsp");	    		    	
	    }
	    
	    else
	    {
	    	obj = new InPXKList();
	    	obj.setRequestObj(request);
	    	obj.setTab(tab);
	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
    		session.setAttribute("abc", search);
		
    		response.sendRedirect("/AHF/pages/Distributor/InPXK.jsp");	    		    	
	    }
	    
	}
	
	private String getSearchQuery(HttpServletRequest request, IInPXKList obj) 
	{	
		String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
    	if ( nppId == null)
    		nppId = "";
    	obj.setNppId(nppId);
    	
		String nvgnId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nvgnTen"));
    	if ( nvgnId == null)
    		nvgnId = "";
    	obj.setNvgnId(nvgnId);
    	
    	String nvbhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nvbhTen"));
    	if ( nvbhId == null)
    		nvbhId = "";
    	obj.setNvbhId(nvbhId);
    	
    	String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));
    	if ( trangthai == null)
    		trangthai = "";
    	obj.setTrangthai(trangthai);
    	
    	String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
    	if (tungay == null)
    		tungay = "";    	
    	obj.setTungay(tungay);
    	
    	String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"));
    	if (denngay == null)
    		denngay = "";    	
    	obj.setDenngay(denngay);
    	
    	String donhangid = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("donhangid"));
    	if (donhangid == null)
    		donhangid = "";    	
    	obj.setDonhangId(donhangid);
    	   	
    	String query = "";
    	query = "select ROW_NUMBER() OVER(ORDER BY b.ngaytao DESC) AS 'stt',c.ten as nvbhTen, c.pk_seq as nvbhId " +
    			",b.pk_seq as dhId, b.ngaytao as ngaylapphieu, b.ngaytao as ngaydonhang,  d.pk_seq as maKH, d.ten as tenKH, b.trangthaiin ";
    	query += "from donhang b" +
    			" inner join khachhang d on b.khachhang_fk = d.pk_seq " +
    			" inner join daidienkinhdoanh c on c.pk_seq = b.ddkd_fk " +
    			"	where b.trangthai != 2 and b.npp_fk = '" + nppId + "'" +
    			" and not exists (select 1 from PXKIN_DH where donhang_fk = b.pk_seq)";
    	if(nvbhId.length() >0)
    	{
    		query = query + " and c.pk_seq = '"+nvbhId+"'";
    	}
    
    	
    	if (trangthai.length() > 0)
    	{
			query = query + " and b.trangthai='" + trangthai + "'";			
    	}
    	
    	if (tungay.length() > 0)
    	{
    		query = query + " and b.ngaytao >= '" + tungay + "'"; 
    	}
    	if (denngay.length() > 0)
    	{
    		query = query + " and b.ngaytao <= '" + denngay + "'"; 
    	}
    	
    	if (donhangid.length() > 0)
    	{
    		query = query + " and pxk.pk_seq in (select pxk_fk from phieuxuatkho_donhang where donhang_fk = '"+donhangid+"' )"; 
    	}
    	
    	System.out.print("\nQuery la: " + query + "\n");
    	
    	return query;
	}
	
	private String getSearchQueryxk(HttpServletRequest request, IInPXKList obj) 
	{	
		String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
    	if ( nppId == null)
    		nppId = "";
    	obj.setNppId(nppId);
    	
    	String nvbhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nvbhTen"));
    	if ( nvbhId == null)
    		nvbhId = "";
    	obj.setNvbhId(nvbhId);
    	
    	String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));
    	if ( trangthai == null)
    		trangthai = "";
    	obj.setTrangthai(trangthai);
    	
    	String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
    	if (tungay == null)
    		tungay = "";    	
    	obj.setTungay(tungay);
    	
    	String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"));
    	if (denngay == null)
    		denngay = "";    	
    	obj.setDenngay(denngay);
    	

    	   	
    	String query = "";
    	query = "select ROW_NUMBER() OVER(ORDER BY a.ngaytao DESC) AS 'stt',a.pk_seq,b.ten as nguoitao, a.ngaytao as ngaytaoi, a.ngaytao ";
    	query += " from PXKIN a inner join nhanvien b on a.nguoitao = b.pk_seq " +
				" inner join NHAPHANPHOI c on c.SITECODE = b.CONVSITECODE where c.pk_seq = "+obj.getNppId()+" " +
						"\n and a.pk_seq in (select pxkin_fk from pxkin_dh pxkdh inner join donhang dh on pxkdh.donhang_fk = dh.pk_seq where pxkdh.pxkin_fk = a.pk_seq " ;
    	if (trangthai.length() > 0)
    	{
			query = query + " and dh.trangthai='" + trangthai + "'";			
    	}	
    	    	 	
    	if(nvbhId.length() >0)
    	{
    		query = query + " and dh.ddkd_fk = '"+nvbhId+"'";
    	}
    	if (tungay.length() > 0)
    	{
    		query = query + " and dh.ngaynhap >= '" + tungay + "'"; 
    	}
    	if (denngay.length() > 0)
    	{
    		query = query + " and dh.ngaynhap <= '" + denngay + "'"; 
    	}
    	query = query + "  ) ";
    	
    	System.out.print("\nQuery la: " + query + "\n");
    	
    	
    	return query;
	}
	
	
	private List<ISanpham> createPxk_SpList(String arrdh, dbutils db)
	{
		List<ISanpham> pxk_splist = new ArrayList<ISanpham>();
		String query = "select a.sanpham_fk as spId, isnull(b.barcode,'') as barcode, b.ma as spMa,sum(a.soluong) as soluong, b.ten as spTen, c.ten as khoTen, d.ten as kbhTen from donhang_sanpham a inner join donhang dh on dh.pk_seq = a.donhang_fk inner join sanpham b on a.sanpham_fk = b.pk_seq ";
		query += "\n left join kho c on a.kho_fk = c.pk_seq left join kenhbanhang d on dh.kbh_fk = d.pk_seq where a.donhang_fk in (" + arrdh + ")"
				+ "\n group by a.sanpham_fk ,b.barcode, b.ma , b.ten , c.ten, d.ten";
		
		System.out.print("SPLIST_NPXK: " + query + "\n");
		
		ResultSet rsPxk_sp = db.get(query);
		if(rsPxk_sp != null)
		{
			String[] param = new String[11];
			ISanpham sp = null;	
			try 
			{
				while(rsPxk_sp.next())
				{
					param[0] = rsPxk_sp.getString("spId");
					param[1] = rsPxk_sp.getString("spMa");
					param[2] = rsPxk_sp.getString("spTen");
					param[3] = rsPxk_sp.getString("soluong");
					
					param[4] = "";
					if(rsPxk_sp.getString("khoTen") != null)
						param[4] = rsPxk_sp.getString("khoTen");
					
					param[5] = "";
					if(rsPxk_sp.getString("kbhTen") != null)
						param[5] = rsPxk_sp.getString("kbhTen");
					
					param[6] = "";
					param[7] = "";
					param[8]=rsPxk_sp.getString("barcode");
					
					sp = new Sanpham(param);
					pxk_splist.add(sp);
				}
				rsPxk_sp.close();
			} 
			catch(Exception e) {
				e.printStackTrace();
			}
			finally{try {
				if(rsPxk_sp != null)
					rsPxk_sp.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}}
		}
		return pxk_splist;
	}
	
	private List<ISanpham> createPxk_SpkmList(String arrdh, dbutils db)
	{
		List<ISanpham> pxk_spkmlist = new ArrayList<ISanpham>();
		String query = "select c.ten as khoTen, d.ten as kbhTen, e.scheme + '-' + e.diengiai as ctkmTen, b.pk_seq as spId, isnull(b.barcode,'') as barcode," +
				" b.ma as spMa, b.ten as spTen, sum(a.soluong) as soluong from DONHANG_CTKM_TRAKM a " +
				" inner join ctkhuyenmai e on e.pk_seq = a.ctkmid " +
				"inner join donhang dh on dh.pk_seq = a.donhangid " +
				" inner join sanpham b on a.spMa = b.ma inner join kho c on e.kho_fk = c.pk_seq ";
		query += "inner join kenhbanhang d on dh.kbh_fk = d.pk_seq  " +
				" where dh.pk_seq in (" + arrdh + ") group by c.ten, d.ten, " +
						" e.scheme + '-' + e.diengiai, b.pk_seq, b.ma, b.ten ,b.barcode ";
		System.out.println("Get San Pham Khuyen Mai : "+query);
		ResultSet rsPxk_spkm = db.get(query);
		if(rsPxk_spkm != null)
		{	
			try
			{
				while(rsPxk_spkm.next())
				{
					String[] param = new String[11];
					ISanpham sp = null;
					
					param[0] = rsPxk_spkm.getString("spId");					
					param[1] = rsPxk_spkm.getString("spMa");		
					param[2] = rsPxk_spkm.getString("spTen");
					param[3] = rsPxk_spkm.getString("soluong");
					
					//luu kho
					param[4] = "";
					if(rsPxk_spkm.getString("khoTen") != null)
						param[4] = rsPxk_spkm.getString("khoTen");
					
					//luu kenh ban hang
					param[5] = "";
					if(rsPxk_spkm.getString("kbhTen") != null)
						param[5] = rsPxk_spkm.getString("kbhTen");
					System.out.println("Kenh ban hang : "+param[5]);	
					//luu ten ctkm
					param[6] = "";
					if(rsPxk_spkm.getString("ctkmTen") != null)
						param[6] = rsPxk_spkm.getString("ctkmTen");
					
					param[7] = "";
					param[8]=rsPxk_spkm.getString("barcode");
					
					sp = new Sanpham(param);
					pxk_spkmlist.add(sp);
				}
				rsPxk_spkm.close();
			} 
			catch(Exception e) {}
			finally{try {
				if( rsPxk_spkm != null)
					rsPxk_spkm.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}}
		}
		return pxk_spkmlist;
	}
	

	private void CreatePxk(Document document, String arrdh, dbutils db, String userId, String ngaytao) throws IOException
	{
		try
		{			
			IInPXK pxkBean = new InPXK();
			pxkBean.setUserId(userId); //phai co UserId truoc khi Init
			pxkBean.init2();	
			
			List<ISanpham> sanphamList = this.createPxk_SpList(arrdh, db);
			List<ISanpham> sanphamKMList = this.createPxk_SpkmList(arrdh, db);
			//List<ISanpham> tienKMList = this.createPxk_TienkmList(arr, db);
			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			//PdfWriter.getInstance(document, outstream);
			document.open();

			//chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 9, Font.BOLD);
			Font font3 = new Font(bf, 9, Font.UNDERLINE);
			//font2.setColor(BaseColor.GREEN);
			 
			Paragraph pxk = new Paragraph("In ngày: " + getDateTime(), new Font(bf, 6, Font.BOLDITALIC));
			pxk.setSpacingAfter(2);
			pxk.setSpacingBefore(-25);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			pxk = new Paragraph("Phiếu xuất kho tổng", font);
			pxk.setSpacingAfter(3);
			pxk.setSpacingBefore(-15);
			pxk.setAlignment(Element.ALIGN_CENTER);
			document.add(pxk);
			
			pxk = new Paragraph("Ngày lập phiếu: " + ngaytao , font2);
			pxk.setSpacingAfter(5);
			pxk.setAlignment(Element.ALIGN_CENTER);
			document.add(pxk);
			
			PdfPTable tableHead = new PdfPTable(2);
			tableHead.setWidthPercentage(100);
			tableHead.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableHead.setSpacingAfter(5);
			float[] with = {10.0f, 60.0f}; //set chieu rong cac columns
			tableHead.setWidths(with);
			
			PdfPCell cell1 = new PdfPCell(new Paragraph("NV giao nhận: ", font3));
			PdfPCell cell2 = new PdfPCell(new Paragraph(pxkBean.getNvgnTen(), font2));
			cell1.setBorder(0);
			cell2.setBorder(0);
			tableHead.addCell(cell1);
			tableHead.addCell(cell2);
	
			PdfPCell cell5 = new PdfPCell(new Paragraph("Nhà phân phối: ", font3));
			PdfPCell cell6 = new PdfPCell(new Paragraph(pxkBean.getNppTen(), font2));
			cell5.setBorder(0);
			cell6.setBorder(0);
			tableHead.addCell(cell5);
			tableHead.addCell(cell6);
			
			document.add(tableHead);
			
			//Table Content
			PdfPTable root = new PdfPTable(2);
			root.setKeepTogether(false);
			root.setSplitLate(false);
			root.setWidthPercentage(100);
			root.setHorizontalAlignment(Element.ALIGN_LEFT);
			root.getDefaultCell().setBorder(0);
			float[] cr = {95.0f, 100.0f};
			root.setWidths(cr);
			//phan nay de in ma vach kenh MT
			 boolean bien=false;
			 int dong=6;  
			 if(sanphamList.size() >0){
			  Sanpham sanpham1 = (Sanpham)sanphamList.get(0);
		    	System.out.println("Kenh  : "+sanpham1.getDongia());
		    	if(sanpham1.getDongia().equals("Modern Trade")){
		    		bien=true;
		    		dong=7;
		    	}
			 }
		    	
			
			PdfPTable table = new PdfPTable(dong);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			
			if(bien){
				float[]  withs1 = {6.0f, 22.0f,22.0f, 30.0f, 8.0f,8.0f, 11.0f};
				  table.setWidths(withs1);
			}else{
				float[] withs = {7.0f, 22.0f, 40.0f, 13.0f, 13.0f, 13.0f};
				 table.setWidths(withs);
			}
			
	      
	        
	        Font font4 = new Font(bf, 7);
			PdfPCell cells = new PdfPCell();
			
			PdfPCell tieude = new PdfPCell(new Paragraph("Hàng bán", font2));
			tieude.setColspan(dong);
			tieude.setBorder(0);
			tieude.setPadding(4);
			table.addCell(tieude);
			String[] th = new String[]{"STT", "Mã sản phẩm", "Tên sản phẩm", "Số lượng thùng/cây", "Số lượng lẻ", "Kho"};
        	if(bien){
        		th = new String[]{"STT", "Mã sản phẩm","Mã vạch", "Tên sản phẩm", "Số lượng thùng/cây", "Số lượng lẻ", "Kho"};
        		
        	}
	        //if(spList.size() > 0)
	        //{
				
				System.out.println(dong);
				PdfPCell[] cell = new PdfPCell[dong];
				for(int i= 0; i < dong ; i++)
				{
					cell[i] = new PdfPCell(new Paragraph(th[i], font4));
					cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell[i].setPadding(2);
					//cell[i].setBackgroundColor(BaseColor.CYAN);
					table.addCell(cell[i]);			
				}
				
				int soluongt = 0;
				int soluongle = 0;
				for(int i = 0; i < sanphamList.size(); i++)
				{
					Sanpham sanpham = (Sanpham)sanphamList.get(i);
					System.out.println("Don Gia ma La Kenh :"+sanpham.getDongia());
					
					////////////////////////////////////-------------------------------------------------- lấy số lượng thùng
					int slthung = 0;
					db = new dbutils();
					String query = "select case when  ( sp.DVDL_FK = dvdl.PK_SEQ) \n"+
						"				then (select top(1) floor(("+sanpham.getSoluong()+"*soluong2)/SOLUONG1) from QUYCACH qc where (DVDL2_FK =100018 or DVDL2_FK = 1200532) and  qc.SANPHAM_FK = sp.PK_SEQ   order by DVDL2_FK) \n"+
						"	else \n"+
						"	 0 end as soluongthung" +
						" from sanpham sp" +
						" inner join donvidoluong dvdl on sp.dvdl_fk = dvdl.pk_seq " +
						" where sp.ma = "+sanpham.getMasanpham()+" ";
					System.out.println("so luong thung _+_++++++++++ " +query);
					ResultSet rs = db.get(query);
					if (rs != null) 
					{
						try 
						{	while (rs.next())
							{
								slthung = rs.getInt("soluongthung");
							}
						if (rs != null) 
						{
							rs.close();
						}
						} 
						catch (Exception e) 
						{
							e.printStackTrace();
						}
					} 
					else {		
						return;
					}
					
					sanpham.setSoluongt(slthung);
					////////////////////////////////////--------------------------------------------------
					int slle = 0;
					db = new dbutils();
					query = "select case when  ( sp.DVDL_FK = dvdl.PK_SEQ) \n"+
						"				then (select top(1) (Convert(int,"+sanpham.getSoluong()+")*Convert(int,soluong2))%Convert(int,SOLUONG1) from QUYCACH qc where (DVDL2_FK =100018 or DVDL2_FK = 1200532) and  qc.SANPHAM_FK = sp.PK_SEQ   order by DVDL2_FK) \n"+
						"	else \n"+
						"	 "+sanpham.getSoluong()+" end as soluongthung" +
						" from sanpham sp" +
						" inner join donvidoluong dvdl on sp.dvdl_fk = dvdl.pk_seq " +
						" where sp.ma = "+sanpham.getMasanpham()+" ";
					System.out.println("so luong le _+_++++++++++ " +query);
					rs = db.get(query);
					if (rs != null) 
					{
						try 
						{	while (rs.next())
							{
								slle = rs.getInt("soluongthung");
							}
						if (rs != null) 
						{
							rs.close();
						}
						} 
						catch (Exception e) 
						{
							e.printStackTrace();
						}
					} 
					else {			
						return;
					}
					
					sanpham.setSoluongle(slle);
					
					
					
					
					String[] arr = new String[]{Integer.toString(i+1), sanpham.getMasanpham(), sanpham.getTensanpham(), Integer.toString(sanpham.getSoluongt()), Integer.toString(sanpham.getSoluongle()), sanpham.getDonvitinh()};
					if(bien){
						arr = new String[]{Integer.toString(i+1), sanpham.getMasanpham(),sanpham.getBarcode(), sanpham.getTensanpham(), Integer.toString(sanpham.getSoluongt()), Integer.toString(sanpham.getSoluongle()), sanpham.getDonvitinh()};

					}
					//System.out.println(sanpham.getMasanpham() + "\n");
					
					for(int j = 0; j < dong; j++)
					{
						cells = new PdfPCell(new Paragraph(arr[j], font4));
						if( j==2 )
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						else
							cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					
						cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cells.setPadding(2);
						table.addCell(cells);
					}
					soluongt += sanpham.getSoluongt();
					soluongle += sanpham.getSoluongle();
				}	
				cells = new PdfPCell(new Paragraph("Tổng cộng", font4));
				cells.setColspan(dong-3);
				cells.setHorizontalAlignment(Element.ALIGN_CENTER);
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(5);
				table.addCell(cells);
				
				cells = new PdfPCell(new Paragraph(Integer.toString(soluongt), font4));
				cells.setHorizontalAlignment(Element.ALIGN_CENTER);
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(5);
				table.addCell(cells);
				
				cells = new PdfPCell(new Paragraph(Integer.toString(soluongle), font4));
				cells.setHorizontalAlignment(Element.ALIGN_CENTER);
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(5);
				table.addCell(cells);
				
				cells = new PdfPCell();
				//cells.setColspan(2);
				cells.setPadding(2);
				table.addCell(cells);
				
				cells = new PdfPCell(new Paragraph(" ", font4));
				cells.setColspan(dong);
				cells.setBorder(0);
				cells.setPadding(1);
				table.addCell(cells);
				//document.add(table);
	        //}
	        
	        root.addCell(table);
	        
	       
			PdfPTable table2 = new PdfPTable(5);
			table2.setWidthPercentage(100);
			table2.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] widths = {13.0f, 20.0f, 20.0f, 16.0f, 19.0f}; //, 14.0f
	        table2.setWidths(widths);

	        tieude = new PdfPCell(new Paragraph("Danh sách khách hàng", font2));
			tieude.setColspan(5);
			tieude.setBorder(0);
			tieude.setPadding(4);
			table2.addCell(tieude);
			
			String[] th2 = new String[]{"Mã", "Tên khách hàng", "Tên NVBH", "Hóa đơn", "Số tiền"}; //, "Nợ cũ"
			 int dongkh=5;
			
		  
		   
			PdfPCell[] c = new PdfPCell[dongkh];
			
			for(int i = 0; i < dongkh ; i++)
			{
				c[i] = new PdfPCell(new Paragraph(th2[i], font4));
				c[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				c[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				c[i].setPadding(2);
				//cell[i].setBackgroundColor(BaseColor.CYAN);
				table2.addCell(c[i]);			
			}
			try
			{
				String query = " select a.pk_seq as dhId, c.ten as NVBH , a.ngaynhap, isnull(a.tonggiatri, '0') as tonggiatri,b.smartid, b.pk_seq as khId, b.ten as khTen, b.diachi as dc " +
								" from donhang a inner join khachhang b on a.khachhang_fk = b.pk_seq" +
							   " inner join daidienkinhdoanh c on a.ddkd_fk = c.Pk_seq " +
							   " where a.pk_seq in  ("+arrdh+")";
				System.out.println("in khach hang________+++" +query );
				ResultSet rs = db.get(query);
				//Hashtable<String, Long> credit = pxkBean.getCredits();
				System.out.println("+_+_+_+_+_+_ " + rs);
				if (rs != null)
				{
					
						int i = 1;
						float total = 0;
						//long nocu=0;
						while(rs.next())
						{								
							c[0] = new PdfPCell(new Paragraph(rs.getString("smartid"), font4));
							c[0].setHorizontalAlignment(Element.ALIGN_CENTER);
							c[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
							c[0].setPadding(2);
							//	cell[i].setBackgroundColor(BaseColor.CYAN);
							table2.addCell(c[0]);
							
							c[1] = new PdfPCell(new Paragraph(rs.getString("khTen")+ " - " + rs.getString("dc"), font4));
							c[1].setHorizontalAlignment(Element.ALIGN_LEFT);
							c[1].setVerticalAlignment(Element.ALIGN_MIDDLE);
							c[1].setPadding(2);
							//	cell[i].setBackgroundColor(BaseColor.CYAN);
							table2.addCell(c[1]);
							
							c[2] = new PdfPCell(new Paragraph(rs.getString("NVBH"), font4));
							c[2].setHorizontalAlignment(Element.ALIGN_LEFT);
							c[2].setVerticalAlignment(Element.ALIGN_MIDDLE);
							c[2].setPadding(2);
							//	cell[i].setBackgroundColor(BaseColor.CYAN);
							table2.addCell(c[2]);
	
							c[3] = new PdfPCell(new Paragraph(rs.getString("dhId"), font4));
							c[3].setHorizontalAlignment(Element.ALIGN_CENTER);
							c[3].setVerticalAlignment(Element.ALIGN_MIDDLE);
							c[3].setPadding(2);
							//	cell[i].setBackgroundColor(BaseColor.CYAN);
							table2.addCell(c[3]);
							
							float tonggtri = 0;
							if(rs.getString("tonggiatri") != null)
							{
								tonggtri = Float.parseFloat(rs.getString("tonggiatri"));
							}
							total += tonggtri;
							
							c[4] = new PdfPCell(new Paragraph(formatter.format(tonggtri).toString(), font4));
							c[4].setHorizontalAlignment(Element.ALIGN_CENTER);
							c[4].setVerticalAlignment(Element.ALIGN_MIDDLE);
							c[4].setPadding(2);
							//	cell[i].setBackgroundColor(BaseColor.CYAN);
							table2.addCell(c[4]);
							
							/*if(rs.getString("khId") != null)
							{
								if(credit.containsKey(rs.getString("khId")))
								{
									nocu = nocu + (Long)credit.get(rs.getString("khId")).longValue();
									c[4] = new PdfPCell(new Paragraph(formatter.format((Long)credit.get(rs.getString("khId")).longValue()), font4));
								}
								else{
									c[4] = new PdfPCell(new Paragraph("", font4));
								}
							}
							c[4].setHorizontalAlignment(Element.ALIGN_CENTER);
							c[4].setVerticalAlignment(Element.ALIGN_MIDDLE);
							c[4].setPadding(2);
							//	cell[i].setBackgroundColor(BaseColor.CYAN);
							table2.addCell(c[4]);*/
							
							i++;
						}
						c[0] = new PdfPCell(new Paragraph("Tổng cộng", font4));
						c[0].setHorizontalAlignment(Element.ALIGN_CENTER);
						c[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
						c[0].setPadding(5);
						//	cell[i].setBackgroundColor(BaseColor.CYAN);
						c[0].setColspan(3);
						table2.addCell(c[0]);
						
						c[1] = new PdfPCell(new Paragraph(Integer.toString(i-1), font4));
						c[1].setHorizontalAlignment(Element.ALIGN_CENTER);
						c[1].setVerticalAlignment(Element.ALIGN_MIDDLE);
						c[1].setPadding(5);
						//	cell[i].setBackgroundColor(BaseColor.CYAN);
						
						table2.addCell(c[1]);
	
						c[2] = new PdfPCell(new Paragraph(formatter.format(Math.round(total)).toString(), font4));
						c[2].setHorizontalAlignment(Element.ALIGN_CENTER);
						c[2].setVerticalAlignment(Element.ALIGN_MIDDLE);
						c[2].setPadding(2);
						//	cell[i].setBackgroundColor(BaseColor.CYAN);
						
						table2.addCell(c[2]);
	
						/*c[3] = new PdfPCell(new Paragraph(formatter.format(nocu).toString(), font4));
						c[3].setHorizontalAlignment(Element.ALIGN_CENTER);
						c[3].setVerticalAlignment(Element.ALIGN_MIDDLE);
						c[3].setPadding(2);
						//	cell[i].setBackgroundColor(BaseColor.CYAN);
						table2.addCell(c[3]);*/
						
						cells = new PdfPCell(new Paragraph(" ", font4));
						cells.setColspan(4);
						cells.setPadding(1);
						cells.setBorder(0);
						table2.addCell(cells);
					
				}
				}
			catch(Exception e){
				e.getStackTrace();
			}
			root.addCell(table2);

			document.add(root);
			
			if(sanphamKMList.size() > 0)
			{
			int	dongkm=6;
			if(bien){
				dongkm=7;
			}
				PdfPTable tableKM = new PdfPTable(dongkm);
				tableKM.setWidthPercentage(100);
				tableKM.setHorizontalAlignment(Element.ALIGN_LEFT);
				if(bien){
					float[] withsKM = {7.0f, 65.0f, 20.0f,20.0f, 30.0f, 18.0f, 15.0f};
				    tableKM.setWidths(withsKM);
				}else{
					float[] withsKM = {7.0f, 65.0f, 20.0f, 30.0f, 18.0f, 15.0f};
				    tableKM.setWidths(withsKM);
				}
			    tieude = new PdfPCell(new Paragraph("Hàng khuyến mãi", font2));
				tieude.setColspan(dongkm);
				tieude.setBorder(0);
				tieude.setPadding(4);
				tableKM.addCell(tieude);
				String[] thKM = new String[]{"STT", "Chương trình", "Mã sản phẩm", "Tên sản phẩm", "Số lượng / Số tiền", "Kho"};
				
				if(bien){
					 thKM = new String[]{"STT", "Chương trình", "Mã sản phẩm","Mã vạch", "Tên sản phẩm", "Số lượng / Số tiền", "Kho"};
				}
				PdfPCell[] cellKM = new PdfPCell[dongkm];
				for(int i = 0; i < dongkm ; i++)
				{
					cellKM[i] = new PdfPCell(new Paragraph(thKM[i], font4));
					cellKM[i].setHorizontalAlignment(Element.ALIGN_CENTER);
					cellKM[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cellKM[i].setPadding(2);
					tableKM.addCell(cellKM[i]);			
				}
				
				int m = 0;
				while(m < sanphamKMList.size())
				{
					Sanpham sanpham = (Sanpham)sanphamKMList.get(m);
					String masanpham=sanpham.getMasanpham();
					
					String[] arr = new String[]{Integer.toString(m+1), sanpham.getCTKM(), masanpham, sanpham.getTensanpham(), sanpham.getSoluong(), sanpham.getDonvitinh(), sanpham.getDongia()};
					if(bien){
						 arr = new String[]{Integer.toString(m+1), sanpham.getCTKM(), masanpham,sanpham.getBarcode(), sanpham.getTensanpham(), sanpham.getSoluong(), sanpham.getDonvitinh(), sanpham.getDongia()};
					}
					cells = new PdfPCell();
					
					for(int j = 0; j < dongkm; j++)
					{
						cells = new PdfPCell(new Paragraph(arr[j], font4));
						if(j==3 || j == 1)
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						else
							cells.setHorizontalAlignment(Element.ALIGN_CENTER);
						cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cells.setPadding(2);
						tableKM.addCell(cells);
					}
					m++;
				}		
				
				//int n = 0;
				/*while( n < tienKMList.size())
				{
					Sanpham sanpham = (Sanpham)tienKMList.get(n);
					String[] arr = new String[]{Integer.toString(m), sanpham.getCTKM(), "", "", sanpham.getChietkhau(), "", ""};
					if(bien){
						arr = new String[]{Integer.toString(m), sanpham.getCTKM(), "","", "", sanpham.getChietkhau(), "", ""};

					}
					cells = new PdfPCell();
					
					for(int j = 0; j < dongkm; j++)
					{
						cells = new PdfPCell(new Paragraph(arr[j], font4));
						if(j==3)
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						else
							cells.setHorizontalAlignment(Element.ALIGN_CENTER);
						cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cells.setPadding(2);
						tableKM.addCell(cells);
					}
					n++;
					m++;
				}*/		
					
				document.add(tableKM);
			}
			
			//Table Footer			
			PdfPTable tableFooter = new PdfPTable(4);
			tableFooter.setWidthPercentage(90);
			tableFooter.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableFooter.setSpacingBefore(15);
			tableFooter.setWidths(new float[]{20.0f, 30.0f, 30.0f, 25.0f});
			
			PdfPCell cell11 = new PdfPCell(new Paragraph("Thủ kho", font2));
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell12 = new PdfPCell(new Paragraph("Nhân viên giao nhận", font2));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell13 = new PdfPCell(new Paragraph("Kế toán hàng hóa", font2));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell14 = new PdfPCell(new Paragraph("Kế toán công nợ", font2));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell11.setBorder(0);
			cell12.setBorder(0);
			cell13.setBorder(0);
			cell14.setBorder(0);
			tableFooter.addCell(cell11);
			tableFooter.addCell(cell12);
			tableFooter.addCell(cell13);
			tableFooter.addCell(cell14);
			document.add(tableFooter);
			
			Paragraph pxk0=new Paragraph("Địa chỉ : ",new Font(bf,8));
			pxk0.setSpacingBefore(90);
			document.add(pxk0);
			 db = new dbutils();
			
			String query = "select b.pk_seq as dhId, b.ngaynhap, isnull(a.tonggiatri, '0') as tonggiatri,c.smartid, " +
		    		" c.pk_seq as khId, c.ten as khTen, c.diachi as dc ,isnull( c.phuong,'N/A') as phuong  ,isnull(qh.ten,'') as quanhuyen " + 
	                " from phieuxuatkho_donhang a inner join donhang b on a.donhang_fk = b.pk_seq  " +
	        		" inner join khachhang c on b.khachhang_fk = c.pk_seq " +
	        		" left join quanhuyen qh on qh.pk_seq=c.quanhuyen_fk " +
	        		"  where b.pk_seq in (" +arrdh+ ") "; 
		    System.out.println("Cau Lenh Query : "+query);
			ResultSet rs = db.get(query);
			 int j=0;
			try {
				while (rs.next())
				{

					Paragraph pxk1 = new Paragraph(rs.getString("khTen")+ " - " + rs.getString("dc")+" -- "+ "P." +rs.getString("phuong")+"---Quận."+ rs.getString("quanhuyen") , new Font(bf, 6));
					if(j==0)
						pxk1.setSpacingBefore(5);	
					else 
						pxk1.setSpacingBefore(5);	
					document.add(pxk1);
					j++;
				}
				rs.close();
			} catch (Exception e) {
			
			}

			
			
			/*document.newPage();
			//tao danh sach don hang
		
			String[] dhId = pxkBean.getDhIds();
			//System.out.println("So phan tu trong don hang la: " + dhId.length);
			if(dhId != null)
			{
			for(int i=0; i < dhId.length; i++)
			{
				createDonHangPdf(document, dhId[i], db, pxkBean.getUserId());
			}
			}
			db.shutDown();
			document.close(); */
		}
		catch(DocumentException e)
		{
			e.printStackTrace();
			System.out.println("Error Here  : "+e.toString());
		}
	}
	
	private void CreatePxkle(Document document, String arrdh, dbutils db, String userId) throws IOException
	{
		try
		{			
			IInPXK pxkBean = new InPXK();
			pxkBean.setUserId(userId); //phai co UserId truoc khi Init
			pxkBean.init2();	
			
			List<ISanpham> sanphamList = this.createPxk_SpList(arrdh, db);
			List<ISanpham> sanphamKMList = this.createPxk_SpkmList(arrdh, db);
			//List<ISanpham> tienKMList = this.createPxk_TienkmList(arr, db);
			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			//PdfWriter.getInstance(document, outstream);
			document.open();

			//chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 9, Font.BOLD);
			Font font3 = new Font(bf, 9, Font.UNDERLINE);
			//font2.setColor(BaseColor.GREEN);
			 
			Paragraph pxk = new Paragraph("In ngày: " + getDateTime(), new Font(bf, 6, Font.BOLDITALIC));
			pxk.setSpacingAfter(2);
			pxk.setSpacingBefore(-25);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			pxk = new Paragraph("Phiếu xuất kho tổng", font);
			pxk.setSpacingAfter(3);
			pxk.setSpacingBefore(-15);
			pxk.setAlignment(Element.ALIGN_CENTER);
			document.add(pxk);
			
			pxk = new Paragraph("Ngày lập phiếu: " + getDateTime() , font2);
			pxk.setSpacingAfter(5);
			pxk.setAlignment(Element.ALIGN_CENTER);
			document.add(pxk);
			
			PdfPTable tableHead = new PdfPTable(2);
			tableHead.setWidthPercentage(100);
			tableHead.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableHead.setSpacingAfter(5);
			float[] with = {10.0f, 60.0f}; //set chieu rong cac columns
			tableHead.setWidths(with);
			
			PdfPCell cell1 = new PdfPCell(new Paragraph("NV giao nhận: ", font3));
			PdfPCell cell2 = new PdfPCell(new Paragraph(pxkBean.getNvgnTen(), font2));
			cell1.setBorder(0);
			cell2.setBorder(0);
			tableHead.addCell(cell1);
			tableHead.addCell(cell2);
	
			PdfPCell cell5 = new PdfPCell(new Paragraph("Nhà phân phối: ", font3));
			PdfPCell cell6 = new PdfPCell(new Paragraph(pxkBean.getNppTen(), font2));
			cell5.setBorder(0);
			cell6.setBorder(0);
			tableHead.addCell(cell5);
			tableHead.addCell(cell6);
			
			document.add(tableHead);
			
			//Table Content
			PdfPTable root = new PdfPTable(2);
			root.setKeepTogether(false);
			root.setSplitLate(false);
			root.setWidthPercentage(100);
			root.setHorizontalAlignment(Element.ALIGN_LEFT);
			root.getDefaultCell().setBorder(0);
			float[] cr = {95.0f, 100.0f};
			root.setWidths(cr);
			//phan nay de in ma vach kenh MT
			 boolean bien=false;
			 int dong=6;  
			 if(sanphamList.size() >0){
			  Sanpham sanpham1 = (Sanpham)sanphamList.get(0);
		    	System.out.println("Kenh  : "+sanpham1.getDongia());
		    	if(sanpham1.getDongia().equals("Modern Trade")){
		    		bien=true;
		    		dong=7;
		    	}
			 }
		    	
			
			PdfPTable table = new PdfPTable(dong);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			
			if(bien){
				float[]  withs1 = {6.0f, 22.0f,22.0f, 30.0f, 8.0f,8.0f, 11.0f};
				  table.setWidths(withs1);
			}else{
				float[] withs = {7.0f, 22.0f, 40.0f, 13.0f, 13.0f, 13.0f};
				 table.setWidths(withs);
			}
			
	      
	        
	        Font font4 = new Font(bf, 7);
			PdfPCell cells = new PdfPCell();
			
			PdfPCell tieude = new PdfPCell(new Paragraph("Hàng bán", font2));
			tieude.setColspan(dong);
			tieude.setBorder(0);
			tieude.setPadding(4);
			table.addCell(tieude);
			String[] th = new String[]{"STT", "Mã sản phẩm", "Tên sản phẩm", "Số lượng thùng/cây", "Số lượng lẻ", "Kho"};
        	if(bien){
        		th = new String[]{"STT", "Mã sản phẩm","Mã vạch", "Tên sản phẩm", "Số lượng thùng/cây", "Số lượng lẻ", "Kho"};
        		
        	}
	        //if(spList.size() > 0)
	        //{
				
				System.out.println(dong);
				PdfPCell[] cell = new PdfPCell[dong];
				for(int i= 0; i < dong ; i++)
				{
					cell[i] = new PdfPCell(new Paragraph(th[i], font4));
					cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell[i].setPadding(2);
					//cell[i].setBackgroundColor(BaseColor.CYAN);
					table.addCell(cell[i]);			
				}
				
				int soluongt = 0;
				int soluongle = 0;
				for(int i = 0; i < sanphamList.size(); i++)
				{
					Sanpham sanpham = (Sanpham)sanphamList.get(i);
					System.out.println("Don Gia ma La Kenh :"+sanpham.getDongia());
					
					////////////////////////////////////-------------------------------------------------- lấy số lượng thùng
					int slthung = 0;
					db = new dbutils();
					String query = "select case when  ( sp.DVDL_FK = dvdl.PK_SEQ) \n"+
						"				then (select top(1) floor(("+sanpham.getSoluong()+"*soluong2)/SOLUONG1) from QUYCACH qc where (DVDL2_FK =100018 or DVDL2_FK = 1200532) and  qc.SANPHAM_FK = sp.PK_SEQ   order by DVDL2_FK) \n"+
						"	else \n"+
						"	 0 end as soluongthung" +
						" from sanpham sp" +
						" inner join donvidoluong dvdl on sp.dvdl_fk = dvdl.pk_seq " +
						" where sp.ma = "+sanpham.getMasanpham()+" ";
					System.out.println("so luong thung _+_++++++++++ " +query);
					ResultSet rs = db.get(query);
					if (rs != null) 
					{
						try 
						{	while (rs.next())
							{
								slthung = rs.getInt("soluongthung");
							}
						if (rs != null) 
						{
							rs.close();
						}
						} 
						catch (Exception e) 
						{
							e.printStackTrace();
						}
					} 
					else {		
						return;
					}
					
					sanpham.setSoluongt(slthung);
					////////////////////////////////////--------------------------------------------------
					int slle = 0;
					db = new dbutils();
					query = "select case when  ( sp.DVDL_FK = dvdl.PK_SEQ) \n"+
						"				then (select top(1) (Convert(int,"+sanpham.getSoluong()+")*Convert(int,soluong2))%Convert(int,SOLUONG1) from QUYCACH qc where (DVDL2_FK =100018 or DVDL2_FK = 1200532) and  qc.SANPHAM_FK = sp.PK_SEQ   order by DVDL2_FK) \n"+
						"	else \n"+
						"	 "+sanpham.getSoluong()+" end as soluongthung" +
						" from sanpham sp" +
						" inner join donvidoluong dvdl on sp.dvdl_fk = dvdl.pk_seq " +
						" where sp.ma = "+sanpham.getMasanpham()+" ";
					System.out.println("so luong le _+_++++++++++ " +query);
					rs = db.get(query);
					if (rs != null) 
					{
						try 
						{	while (rs.next())
							{
								slle = rs.getInt("soluongthung");
							}
						if (rs != null) 
						{
							rs.close();
						}
						} 
						catch (Exception e) 
						{
							e.printStackTrace();
						}
					} 
					else {			
						return;
					}
					
					sanpham.setSoluongle(slle);
					
					
					
					
					String[] arr = new String[]{Integer.toString(i+1), sanpham.getMasanpham(), sanpham.getTensanpham(), Integer.toString(sanpham.getSoluongt()), Integer.toString(sanpham.getSoluongle()), sanpham.getDonvitinh()};
					if(bien){
						arr = new String[]{Integer.toString(i+1), sanpham.getMasanpham(),sanpham.getBarcode(), sanpham.getTensanpham(), Integer.toString(sanpham.getSoluongt()), Integer.toString(sanpham.getSoluongle()), sanpham.getDonvitinh()};

					}
					
					for(int j = 0; j < dong; j++)
					{
						cells = new PdfPCell(new Paragraph(arr[j], font4));
						if( j==2 )
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						else
							cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					
						cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cells.setPadding(2);
						table.addCell(cells);
					}
					soluongt += sanpham.getSoluongt();
					soluongle += sanpham.getSoluongle();
				}	
				cells = new PdfPCell(new Paragraph("Tổng cộng", font4));
				cells.setColspan(dong-3);
				cells.setHorizontalAlignment(Element.ALIGN_CENTER);
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(5);
				table.addCell(cells);
				
				cells = new PdfPCell(new Paragraph(Integer.toString(soluongt), font4));
				cells.setHorizontalAlignment(Element.ALIGN_CENTER);
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(5);
				table.addCell(cells);
				
				cells = new PdfPCell(new Paragraph(Integer.toString(soluongle), font4));
				cells.setHorizontalAlignment(Element.ALIGN_CENTER);
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(5);
				table.addCell(cells);
				
				cells = new PdfPCell();
				//cells.setColspan(2);
				cells.setPadding(2);
				table.addCell(cells);
				
				cells = new PdfPCell(new Paragraph(" ", font4));
				cells.setColspan(dong);
				cells.setBorder(0);
				cells.setPadding(1);
				table.addCell(cells);
				//document.add(table);
	        //}
	        
	        root.addCell(table);
	        
	       
			PdfPTable table2 = new PdfPTable(5);
			table2.setWidthPercentage(100);
			table2.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] widths = {13.0f, 20.0f, 20.0f, 16.0f, 19.0f}; //, 14.0f
	        table2.setWidths(widths);

	        tieude = new PdfPCell(new Paragraph("Danh sách khách hàng", font2));
			tieude.setColspan(5);
			tieude.setBorder(0);
			tieude.setPadding(4);
			table2.addCell(tieude);
			
			String[] th2 = new String[]{"Mã", "Tên khách hàng", "Tên NVBH", "Hóa đơn", "Số tiền"}; //, "Nợ cũ"
			 int dongkh=5;
			
		  
		   
			PdfPCell[] c = new PdfPCell[dongkh];
			
			for(int i = 0; i < dongkh ; i++)
			{
				c[i] = new PdfPCell(new Paragraph(th2[i], font4));
				c[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				c[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				c[i].setPadding(2);
				table2.addCell(c[i]);			
			}
			try
			{
				String query = " select a.pk_seq as dhId, c.ten as NVBH , a.ngaynhap, isnull(a.tonggiatri, '0') as tonggiatri,b.smartid, b.pk_seq as khId, b.ten as khTen, b.diachi as dc " +
								" from donhang a inner join khachhang b on a.khachhang_fk = b.pk_seq" +
							   " inner join daidienkinhdoanh c on a.ddkd_fk = c.Pk_seq " +
							   " where a.pk_seq in  ("+arrdh+")";
				System.out.println("in khach hang________+++" +query );
				ResultSet rs = db.get(query);
				System.out.println("+_+_+_+_+_+_ " + rs);
				if (rs != null)
				{
					
						int i = 1;
						float total = 0;
						while(rs.next())
						{								
							c[0] = new PdfPCell(new Paragraph(rs.getString("smartid"), font4));
							c[0].setHorizontalAlignment(Element.ALIGN_CENTER);
							c[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
							c[0].setPadding(2);
							table2.addCell(c[0]);
							
							c[1] = new PdfPCell(new Paragraph(rs.getString("khTen")+ " - " + rs.getString("dc"), font4));
							c[1].setHorizontalAlignment(Element.ALIGN_LEFT);
							c[1].setVerticalAlignment(Element.ALIGN_MIDDLE);
							c[1].setPadding(2);
							table2.addCell(c[1]);
							
							c[2] = new PdfPCell(new Paragraph(rs.getString("NVBH"), font4));
							c[2].setHorizontalAlignment(Element.ALIGN_LEFT);
							c[2].setVerticalAlignment(Element.ALIGN_MIDDLE);
							c[2].setPadding(2);
							table2.addCell(c[2]);
	
							c[3] = new PdfPCell(new Paragraph(rs.getString("dhId"), font4));
							c[3].setHorizontalAlignment(Element.ALIGN_CENTER);
							c[3].setVerticalAlignment(Element.ALIGN_MIDDLE);
							c[3].setPadding(2);
							table2.addCell(c[3]);
							
							float tonggtri = 0;
							if(rs.getString("tonggiatri") != null)
							{
								tonggtri = Float.parseFloat(rs.getString("tonggiatri"));
							}
							total += tonggtri;
							
							c[4] = new PdfPCell(new Paragraph(formatter.format(tonggtri).toString(), font4));
							c[4].setHorizontalAlignment(Element.ALIGN_CENTER);
							c[4].setVerticalAlignment(Element.ALIGN_MIDDLE);
							c[4].setPadding(2);
							table2.addCell(c[4]);
							
							
							i++;
						}
						c[0] = new PdfPCell(new Paragraph("Tổng cộng", font4));
						c[0].setHorizontalAlignment(Element.ALIGN_CENTER);
						c[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
						c[0].setPadding(5);
						c[0].setColspan(3);
						table2.addCell(c[0]);
						
						c[1] = new PdfPCell(new Paragraph(Integer.toString(i-1), font4));
						c[1].setHorizontalAlignment(Element.ALIGN_CENTER);
						c[1].setVerticalAlignment(Element.ALIGN_MIDDLE);
						c[1].setPadding(5);
						table2.addCell(c[1]);
	
						c[2] = new PdfPCell(new Paragraph(formatter.format(Math.round(total)).toString(), font4));
						c[2].setHorizontalAlignment(Element.ALIGN_CENTER);
						c[2].setVerticalAlignment(Element.ALIGN_MIDDLE);
						c[2].setPadding(2);
						
						table2.addCell(c[2]);
	
						
						cells = new PdfPCell(new Paragraph(" ", font4));
						cells.setColspan(4);
						cells.setPadding(1);
						cells.setBorder(0);
						table2.addCell(cells);
					
				}
				}
			catch(Exception e){
				e.getStackTrace();
			}
			root.addCell(table2);

			document.add(root);
			
			if(sanphamKMList.size() > 0)
			{
			int	dongkm=6;
			if(bien){
				dongkm=7;
			}
				PdfPTable tableKM = new PdfPTable(dongkm);
				tableKM.setWidthPercentage(100);
				tableKM.setHorizontalAlignment(Element.ALIGN_LEFT);
				if(bien){
					float[] withsKM = {7.0f, 65.0f, 20.0f,20.0f, 30.0f, 18.0f, 15.0f};
				    tableKM.setWidths(withsKM);
				}else{
					float[] withsKM = {7.0f, 65.0f, 20.0f, 30.0f, 18.0f, 15.0f};
				    tableKM.setWidths(withsKM);
				}
			    tieude = new PdfPCell(new Paragraph("Hàng khuyến mãi", font2));
				tieude.setColspan(dongkm);
				tieude.setBorder(0);
				tieude.setPadding(4);
				tableKM.addCell(tieude);
				String[] thKM = new String[]{"STT", "Chương trình", "Mã sản phẩm", "Tên sản phẩm", "Số lượng / Số tiền", "Kho"};
				
				if(bien){
					 thKM = new String[]{"STT", "Chương trình", "Mã sản phẩm","Mã vạch", "Tên sản phẩm", "Số lượng / Số tiền", "Kho"};
				}
				PdfPCell[] cellKM = new PdfPCell[dongkm];
				for(int i = 0; i < dongkm ; i++)
				{
					cellKM[i] = new PdfPCell(new Paragraph(thKM[i], font4));
					cellKM[i].setHorizontalAlignment(Element.ALIGN_CENTER);
					cellKM[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cellKM[i].setPadding(2);
					tableKM.addCell(cellKM[i]);			
				}
				
				int m = 0;
				while(m < sanphamKMList.size())
				{
					Sanpham sanpham = (Sanpham)sanphamKMList.get(m);
					String masanpham=sanpham.getMasanpham();
					
					String[] arr = new String[]{Integer.toString(m+1), sanpham.getCTKM(), masanpham, sanpham.getTensanpham(), sanpham.getSoluong(), sanpham.getDonvitinh(), sanpham.getDongia()};
					if(bien){
						 arr = new String[]{Integer.toString(m+1), sanpham.getCTKM(), masanpham,sanpham.getBarcode(), sanpham.getTensanpham(), sanpham.getSoluong(), sanpham.getDonvitinh(), sanpham.getDongia()};
					}
					cells = new PdfPCell();
					
					for(int j = 0; j < dongkm; j++)
					{
						cells = new PdfPCell(new Paragraph(arr[j], font4));
						if(j==3 || j == 1)
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						else
							cells.setHorizontalAlignment(Element.ALIGN_CENTER);
						cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cells.setPadding(2);
						tableKM.addCell(cells);
					}
					m++;
				}		
				
				document.add(tableKM);
			}
			
			//Table Footer			
			PdfPTable tableFooter = new PdfPTable(4);
			tableFooter.setWidthPercentage(90);
			tableFooter.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableFooter.setSpacingBefore(15);
			tableFooter.setWidths(new float[]{20.0f, 30.0f, 30.0f, 25.0f});
			
			PdfPCell cell11 = new PdfPCell(new Paragraph("Thủ kho", font2));
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell12 = new PdfPCell(new Paragraph("Nhân viên giao nhận", font2));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell13 = new PdfPCell(new Paragraph("Kế toán hàng hóa", font2));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell14 = new PdfPCell(new Paragraph("Kế toán công nợ", font2));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell11.setBorder(0);
			cell12.setBorder(0);
			cell13.setBorder(0);
			cell14.setBorder(0);
			tableFooter.addCell(cell11);
			tableFooter.addCell(cell12);
			tableFooter.addCell(cell13);
			tableFooter.addCell(cell14);
			document.add(tableFooter);
			
			Paragraph pxk0=new Paragraph("Địa chỉ : ",new Font(bf,8));
			pxk0.setSpacingBefore(90);
			document.add(pxk0);
			 db = new dbutils();
			
			String query = "select b.pk_seq as dhId, b.ngaynhap, isnull(a.tonggiatri, '0') as tonggiatri,c.smartid, " +
		    		" c.pk_seq as khId, c.ten as khTen, c.diachi as dc ,isnull( c.phuong,'N/A') as phuong  ,isnull(qh.ten,'') as quanhuyen " + 
	                " from phieuxuatkho_donhang a inner join donhang b on a.donhang_fk = b.pk_seq  " +
	        		" inner join khachhang c on b.khachhang_fk = c.pk_seq " +
	        		" left join quanhuyen qh on qh.pk_seq=c.quanhuyen_fk " +
	        		"  where b.pk_seq in (" +arrdh+ ") "; 
		    System.out.println("Cau Lenh Query : "+query);
			ResultSet rs = db.get(query);
			 int j=0;
			try {
				while (rs.next())
				{

					Paragraph pxk1 = new Paragraph(rs.getString("khTen")+ " - " + rs.getString("dc")+" -- "+ "P." +rs.getString("phuong")+"---Quận."+ rs.getString("quanhuyen") , new Font(bf, 6));
					if(j==0)
						pxk1.setSpacingBefore(5);	
					else 
						pxk1.setSpacingBefore(5);	
					document.add(pxk1);
					j++;
				}
				rs.close();
			} catch (Exception e) {
			
			}
		}
		catch(DocumentException e)
		{
			e.printStackTrace();
			System.out.println("Error Here  : "+e.toString());
		}
	}
	
	
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}
