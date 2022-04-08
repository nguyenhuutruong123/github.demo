package geso.dms.distributor.servlets.donhangchoxuly;

import geso.dms.distributor.beans.donhangchoxuly.IDonHangChoXuLy;
import geso.dms.distributor.beans.donhangchoxuly.IDonHangChoXuLyList;
import geso.dms.distributor.beans.donhangchoxuly.imp.DonHangChoXuLy;
import geso.dms.distributor.beans.donhangchoxuly.imp.DonHangChoXuLyList;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/DonHangChoXuLySvl")
public class DonHangChoXuLySvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DonHangChoXuLySvl() {
		super();

	}

	PrintWriter out;

	private int items = 50; 

	private int splittings = 20;



	private void settingPage(IDonHangChoXuLyList obj) {
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

		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			this.out  = response.getWriter();


			session.setMaxInactiveInterval(30000);

			Utility util = new Utility();
			out = response.getWriter();

			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);
			//out.println(userId);
			System.out.println();
			Date date = new Date();
			System.out.println("DonHangChoXuLySvl user :" + userId + "  ,sessionId: " + session.getId() );

			if (userId.length()==0)
				userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
			String nppId;
			if(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")) != null)
				nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));

			//Lay Nha PP Theo Dang Nhap Moi
			nppId=util.getIdNhapp(userId);

			String action = util.getAction(querystring);

			String dhId = util.getId(querystring);

			IDonHangChoXuLyList obj;   
			obj = new DonHangChoXuLyList();
			obj.setUserId(userId);
			settingPage(obj);

			String msg="";

			if (action.equals("delete"))
			{	
				if(querystring.contains("nppId"))
				{
					nppId = querystring.trim().substring(querystring.lastIndexOf("&") + 7, querystring.length());
					if(!Delete2(dhId, userId, nppId))
					{
						out.print("Khong the xoa don hang nay...");
						return;
					}
				}
				else
				{
					if(!Delete(dhId,nppId))
					{
						out.print("Khong the xoa don hang nay :" + dhId);
						System.out.println("Khong the xoa don hang nay...");
					}
					else
					{
						out.print("Da xoa don hang nay :" + dhId);
						System.out.println("Da xoa don hang nay...");
					}
				}
			}
			else if(action.equals("convert"))
			{
				nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
				msg = this.ConvertPO(dhId,nppId,userId);
				obj.setMsg(msg);
			}
			obj.init("");
			System.out.println("[convert]"+msg);
			session.setAttribute("obj", obj);
			session.setAttribute("khId", "");
			String nextJSP = "/AHF/pages/Distributor/DonHangChoXuLy.jsp";
			response.sendRedirect(nextJSP);
		}
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

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			this.out = response.getWriter();


			session.setMaxInactiveInterval(30000);

			userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));

			System.out.println();
			System.out.println("DonHangChoXuLySvl user :" + userId + "  ,sessionId: " + session.getId()) ;
			String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
			if (action == null){
				action = "";
			}

			if (action.equals("new"))
			{
				// Empty Bean for distributor
				IDonHangChoXuLy dhBean = (IDonHangChoXuLy) new DonHangChoXuLy("");
				dhBean.setUserId(userId);
				dhBean.createRS();

				// Save Data into session
				session.setAttribute("dhBean", dhBean);//truyen vao session mot doi tuong DonHangChoXuLy co gia tri rong khi khoi tao de ben form don hang nhan dc
				session.setAttribute("khId", "");
				session.setAttribute("ddkdId", "");
				session.setAttribute("nppId", dhBean.getNppId());

				String nextJSP = "/AHF/pages/Distributor/DonHangChoXuLyNew.jsp";
				response.sendRedirect(nextJSP);
			}
			IDonHangChoXuLyList obj;
			obj = new DonHangChoXuLyList();
			settingPage(obj);

			if(action.equals("search"))
			{

				String search = getSearchQuery(request,obj);
				System.out.println("cau lenh chay:"+ search);
				obj.setUserId(userId);
				obj.init(search);
				// System.out.println("tu ngay"+ obj.getTungay());				    	  	
				session.setAttribute("userId", userId);
				session.setAttribute("obj", obj);

				response.sendRedirect("/AHF/pages/Distributor/DonHangChoXuLy.jsp");	    		    	
			}
			else 
				if(action.equals("view") || action.equals("next") || action.equals("prev")){

					String search = getSearchQuery(request, obj);

					obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
					obj.setUserId(userId);

					obj.init(search);
					obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
					session.setAttribute("obj", obj);
					response.sendRedirect("/AHF/pages/Distributor/DonHangChoXuLy.jsp");
				}


		}
	}

	private String ConvertPO(String pxkId, String nppId,String userId) 
	{
		dbutils db = new dbutils();
		String msg = "";


		// 24/8 : sửa lại cho chuyển những sp còn đủ tôn >> đơn hàng . Khi nào tất cả sp trong đơn hàng xử lý ko đủ tồn mới báo
		//        chỉ chuyển hàng bán , còn KM >> vào đơn hàng áp lại
		
			String query =  "   select count(*) as SoDong  "+
							"	from  "+
							"	(  "+
							"		select sanpham_fk,SoLuong,Kho_FK  "+
							"		from donhangchoxuly_sanpham where donhangchoxuly_fk='"+pxkId+"' AND conLai>0 "+
							"	)a ";
			System.out.println("[donhangchoxuly_sanpham]"+query);
			ResultSet	rs =db.get(query);
			int  SoDong=0;			
			try
			{
				while(rs.next())
				{
					SoDong= rs.getInt("SoDong");
				}
			} 
			catch(Exception e1) { return "Loi: " + e1.getMessage(); }
			
			if(SoDong<=0)
			{
				msg = "Đơn hàng đã chuyển đủ số lượng";
				return msg;
			}
		
		
		//check ton kho (HANG SU DUNG TRONG KHO NPP)
			query = "select pxk_sp.spMa " + 
				"from " +
				"(" +
				"select khoId, kbhId, spId, spMa, sum(soluong) as soluong " +
				"from( " +
				"select c.kho_fk as khoId, c.kbh_fk as kbhId, b.pk_seq as spId, b.ma as spMa, sum(a.ConLai) as soluong " +
				"from DonHangChoXuLy_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq inner join DonHangChoXuLy c on a.DonHangChoXuLy_FK = c.pk_seq " +
				"where c.trangthai != 2 and a.DonHangChoXuLy_FK='"+pxkId+"' and a.ConLai>0 " + 
				"group by c.kho_fk, c.kbh_fk, b.pk_seq, b.ma " +
				/*"union all " +
				"select b.kho_fk as khoId, e.kbh_fk as kbhId, d.pk_seq as spId, a.spMa, sum(a.ConLai) as soluong " +
				"from DonHangChoXuLy_ctkm_trakm a inner join ctkhuyenmai b on a.ctkmid = b.pk_seq " +
				"	inner join sanpham d on a.spMa = d.ma inner join DonHangChoXuLy e on a.DHCXLID = e.pk_seq " +
				"where e.trangthai != 2 and a.spMa is not null and a.DHCXLID ='"+pxkId+"'   and a.ConLai>0 " +
				"group by b.kho_fk, e.kbh_fk, a.ctkmId, a.spMa, d.pk_seq " +*/
				" ) a " +
				" group by khoId, kbhId, spId, spMa " +
				") " +
				"pxk_sp left join " +
				"( " +
				"	select kho_fk, npp_fk, sanpham_fk, kbh_fk, AVAILABLE " +
				"	from nhapp_kho where npp_fk = '" + nppId + "' " +
				") " +
				"kho_npp on pxk_sp.khoId = kho_npp.kho_fk and pxk_sp.kbhId = kho_npp.kbh_fk and pxk_sp.spId = kho_npp.sanpham_fk " +
				"where (isnull(kho_npp.AVAILABLE,0) - pxk_sp.soluong) < 0";


		System.out.println("Query check chot phieu xuat kho: " + query + "\n");
		ResultSet sosp = db.get(query);
		String spMa = "";
		int demsp = 0;
		/*if(sosp != null)*/
		{
			try 
			{
				while(sosp.next())
				{
					spMa += sosp.getString("spMa") + ", ";
					demsp ++;
				}
				sosp.close();
			} 
			catch(Exception e1) { return "Loi: " + e1.getMessage(); }
		}

		
		/*if(spMa.length() > 0)
		{
			msg = "Các mã sản phẩm sau: " + spMa + " không đủ số lượng trong kho NHÀ PHÂN PHỐI \nVui lòng kiểm tra lại số lượng trước khi chốt phiếu xuất kho";
			return msg;
		}*/
		
		if(SoDong == demsp)
		{
			msg = "Tất cả sản phẩm còn lại trong đơn không đủ số lượng trong kho NHÀ PHÂN PHỐI \nVui lòng kiểm tra lại số lượng trước khi chốt phiếu xuất kho";
			return msg;
		}
		
		try
		{

			db.update("SET TRANSACTION ISOLATION LEVEL SNAPSHOT;");
			db.update("SET LOCK_TIMEOUT 60000;");
			db.getConnection().setAutoCommit(false);
			query =
					"	insert into DonHang(ngaynhap, trangthai, ngaytao, ngaysua, nguoitao, nguoisua, vat, tonggiatri, ddkd_fk, gsbh_fk, khachhang_fk, npp_fk, kho_fk, kbh_fk, tinhtrang,BM,ASM,IsChiNhanh, diachigiao,FromDonHang_FK) "+
							"  select ngaynhap, 0 as trangthai , '"+getDateTime()+"',  '"+getDateTime()+"', nguoitao, nguoisua, vat, tonggiatri, ddkd_fk, gsbh_fk, khachhang_fk, npp_fk, kho_fk, kbh_fk, tinhtrang,BM,ASM,IsChiNhanh, diachigiao,DonHang_FK " +
							"  From DonHangChoXuLy where pk_Seq='"+pxkId+"' " ;

			if(db.updateReturnInt(query) <= 0)
			{
				db.getConnection().rollback(); 
				msg = "Lỗi khi cập nhật tồn kho: " + query;
				return msg; 
			}

			String dhId="";
			ResultSet rsDDH = db.get("select scope_identity() as ID ");
			if(rsDDH.next())
			{
				dhId = rsDDH.getString("ID");
			}
			rsDDH.close();

			query=
					"	insert into DonHang_SanPham(DonHang_fk,SanPham_FK,Kho_FK,GiaMua,ChietKhau,DonGiaGoc,BangGia_FK,ptCKTT,ptCKDLN,ptCKDH,SoLuong)  "+
							"select '"+dhId+"',SanPham_FK,Kho_FK,GiaMua,ChietKhau,DonGiaGoc,BangGia_FK,ptCKTT,ptCKDLN,ptCKDH,ConLai " +
							" from DonHangChoXuLy_SanPham  where DonHangChoXuLy_fk='"+pxkId+"' and ConLai>0 ";		
			if(!db.update(query))
			{
				db.getConnection().rollback(); 
				msg = "Lỗi khi cập nhật tồn kho: " + query;
				return msg; 
			}
			
			/*query=
					"	INSERT INTO DonHang_CTKM_TRAKM(	DonHangID,CTKMID,TRAKMID,SOXUAT,TONGGIATRI,SPMA,SOLUONG,CHIETKHAU,LOAI) "+
							"select "+dhId+",CTKMID,TRAKMID,SOXUAT,TONGGIATRI,SPMA,ConLai,CHIETKHAU,LOAI " +
							" from DonHangChoXuLy_CTKM_TRAKM  where DHCXLID='"+pxkId+"' and ConLai>0 ";
			if(!db.update(query))
			{
				db.getConnection().rollback(); 
				msg = "Lỗi khi cập nhật tồn kho: " + query;
				return msg; 
			}*/

			query = "update DonHang set trangthai = '0' where pk_seq = '" + dhId + "' ";
			if( db.updateReturnInt(query) != 1 )
			{
				db.getConnection().rollback();
				msg = "1.Khong the cap nhat table 'Don Hang'(ĐH đã chốt hoặc xảy ra lỗi) , " + query;
				return msg; 
			}
			query = "update kho set kho.booked = kho.booked + sp.SOLUONG, kho.available = kho.available - sp.SOLUONG " + 
					"from DONHANG dh inner join DONHANG_SANPHAM sp on dh.PK_SEQ = sp.DONHANG_FK   " + 
					"inner join nhapp_kho kho on sp.SANPHAM_FK = kho.SANPHAM_FK and sp.KHO_FK = kho.KHO_FK and kho.NPP_FK = dh.NPP_FK and kho.KBH_FK = dh.KBH_FK " + 
					"where dh.PK_SEQ = " + dhId +" and dh.trangthai=0 " ;
			if(db.updateReturnInt(query) <= 0)
			{
				db.getConnection().rollback(); 
				msg = "Lỗi khi cập nhật tồn kho: " + query;
				return msg; 
			}

			/*query =
					"	UPDATE  NHAPP_KHO   "+
							"	SET  NHAPP_KHO.BOOKED=NHAPP_KHO.BOOKED +DATA.TONGSOLUONG,  "+
							"	NHAPP_KHO.AVAILABLE=NHAPP_KHO.AVAILABLE - DATA.TONGSOLUONG  "+	
							"	 FROM (  "+
							"			select B.NPP_FK,B.KBH_FK,CTKM.KHO_FK,C.PK_SEQ AS SANPHAM_FK,SUM(A.SOLUONG) AS TONGSOLUONG  "+
							"			FROM DONHANG_CTKM_TRAKM A   "+
							"			INNER JOIN DONHANG B ON A.DONHANGID = B.PK_SEQ    "+
							"			INNER JOIN SANPHAM C ON A.SPMA = C.MA INNER JOIN  "+ 
							"			CTKHUYENMAI CTKM ON CTKM.PK_SEQ = A.CTKMID   "+
							"			WHERE A.SOLUONG IS NOT NULL AND DONHANGID ='"+dhId+"' AND B.TRANGTHAI='0'  "+
							"			GROUP BY B.NPP_FK,B.KBH_FK,CTKM.KHO_FK,C.PK_SEQ  "+  
							"	)  AS DATA   "+
							"	INNER JOIN NHAPP_KHO ON NHAPP_KHO.SANPHAM_FK=DATA.SANPHAM_FK  "+  
							"	AND NHAPP_KHO.KHO_FK=DATA.KHO_FK AND  "+
							"	NHAPP_KHO.NPP_FK= DATA.NPP_FK AND NHAPP_KHO.KBH_FK=DATA.KBH_FK  ";

			if(db.updateReturnInt(query) <= 0)
			{
				db.getConnection().rollback(); 
				msg = "Lỗi khi cập nhật tồn kho: " + query;
				return msg; 
			}*/
			
			
			query = "update DonHangChoXuLy set trangthai = '1' where pk_seq = '" + pxkId + "' and TrangThai=0 ";
			if( db.updateReturnInt(query) != 1 )
			{
				db.getConnection().rollback();
				msg = "1.Khong the cap nhat table 'Don Hang'(ĐH đã chốt hoặc xảy ra lỗi) , " + query;
				return msg; 
			}


			query = 
					"	update DONHANG set TONGGIATRI= "+
							"			isnull(( "+
							"				select SUM(b.soluong*b.giamua)  as TongGiaTri "+
							"				from DONHANG a inner join DONHANG_SANPHAM b on b.DONHANG_FK=a.PK_SEQ "+
							"				where  a.PK_SEQ=DONHANG.PK_SEQ "+
							"				group by b.DONHANG_FK "+
							"			),0)- "+
							"			isnull "+
							"				(( "+
							"					select SUM(TONGGIATRI) from DONHANG_CTKM_TRAKM km  "+
							"					where km.DONHANGID=DONHANG.PK_SEQ and SPMA is null "+
							"				),0)  "+
							"		from DONHANG  "+
							"		where pk_Seq='"+ dhId+"' ";
			if( db.updateReturnInt(query) != 1 )
			{
				db.getConnection().rollback();
				msg = "1.Khong the cap nhat table 'Don Hang'(ĐH đã chốt hoặc xảy ra lỗi) , " + query;
				return msg; 
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return "Exception: " + e.getMessage();
		}
		finally
		{
			db.shutDown();	
		}
		return "Chuyển thành đơn hàng thành công ";
	}

	private String getSearchQuery(HttpServletRequest request,IDonHangChoXuLyList obj) 
	{
		String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
		if ( nppId == null)
			nppId = "";
		obj.setNppId(nppId);

		String ddkdId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdTen"));
		if ( ddkdId == null)
			ddkdId = "";
		obj.setDdkdId(ddkdId);

		String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));
		if (trangthai == null)
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

		String soDonHangChoXuLy = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sodonhang"));
		if (soDonHangChoXuLy == null)
			soDonHangChoXuLy = "";    	
		obj.setSohoadon(soDonHangChoXuLy);
		
		String id = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id"));
		if (id == null)
			id = "";    	
		obj.setId(id);
		
		

		String khachhang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khachhang"));
		if (khachhang == null)
			khachhang = "";    	
		obj.setKhachhang(khachhang);

		String tumuc = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tumuc"));
		if (tumuc == null)
			tumuc = "";    	
		obj.setTumuc(tumuc);

		String denmuc = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denmuc"));
		if (denmuc == null)
			denmuc = "";    	
		obj.setDenmuc(denmuc);

		String	query = " select a.DonHang_FK,a.pk_seq as dhId, a.ngaynhap, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua, d.ten as khTen, d.pk_seq as khId, e.pk_seq as ddkdId, \n"+
			        	"        e.ten as ddkdTen, f.ten as nppTen, a.tonggiatri, a.IsPDA,a.ddkdtao_fk, a.ddkdTao, \n"+
			        	"        (select count(*) from donhangchoxuly_sanpham where donhangchoxuly_fk = a.pk_seq AND conLai > 0) isChuyen \n"+
		                " from DonHangChoXuLy a left join nhanvien b on a.nguoitao = b.pk_seq \n"+
		                "      left join nhanvien c on a.nguoisua = c.pk_seq \n"+
		                "	   inner join khachhang d on a.khachhang_fk = d.pk_seq \n"+
		                "	   inner join daidienkinhdoanh e on a.ddkd_fk = e.pk_seq \n"+
		                "	   inner join nhaphanphoi f on a.npp_fk = f.pk_seq \n"+
	                    " where a.npp_fk = '" + nppId + "'  ";
		
		if (id.length() > 0)
		{
			query = query + " and cast( a.pk_seq as varchar(20))  like  '%" + id + "%'";		
		}    	

		if (ddkdId.length() > 0)
		{
			query = query + " and e.pk_seq = '" + ddkdId + "'";		
		}    	
		if (trangthai.length() > 0)
		{
			query = query + " and a.trangthai ='" + trangthai + "'";			
		}    	
		if (tungay.length() > 0)
		{
			query = query + " and a.ngaynhap >= '" + tungay + "'";			
		}    	
		if (denngay.length() > 0)
		{
			query = query + " and a.ngaynhap <= '" + denngay + "'";	
		}
		if (soDonHangChoXuLy.length() > 0)
		{
			query = query + " and a.pk_seq like '%" + soDonHangChoXuLy + "%'";	
		}
		if (khachhang.length() > 0)
		{
			Utility util = new Utility();
			query = query + " and (d.smartid like N'%"+ khachhang +"%' or dbo.ftBoDau(d.pk_seq) like (N'%" + util.replaceAEIOU(khachhang) + "%') or upper(dbo.ftBoDau(d.ten)) like upper(N'%" + util.replaceAEIOU(khachhang) + "%') )";	
		}
		if(tumuc.length() > 0)
		{
			query = query + " and a.tonggiatri >= '" + tumuc + "'";	
		}
		if(denmuc.length() > 0)
		{
			query = query + " and a.tonggiatri <= '" + denmuc + "'";	
		}

		//System.out.print("\nQuery cua ban: " + query);
		return query;
	}

	private boolean Delete(String id,String nppId)
	{		
		dbutils db = new dbutils(); 

		String query = id + "&" + nppId;
		String param[] = query.split("&");

		int kq = db.execProceduce("DeleteDonHangChoXuLy", param);
		if(kq == -1)
			return false;
		return true;
	}

	//ham delete co phat sinh don hang thu hoi
	private boolean Delete2(String dhId, String userId, String nppId) 
	{
		//neu da xuat kho ma xoa, thi phai tao ra phieu thu hoi
		try
		{
			IDonHangChoXuLy DonHangChoXuLy = new DonHangChoXuLy(dhId);
			DonHangChoXuLy.setUserId(userId);
			DonHangChoXuLy.setNppId(nppId);

			//DonHangChoXuLy.DeleteDonHangChoXuLyDxk();
			return true;

		}
		catch (Exception e) 
		{
			return false;	
		}
	}
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}


}
