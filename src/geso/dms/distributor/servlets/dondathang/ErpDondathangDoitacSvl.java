package geso.dms.distributor.servlets.dondathang;

import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.dondathang.IErpDondathangDoitac;
import geso.dms.distributor.beans.dondathang.IErpDondathangDoitacList;
import geso.dms.distributor.beans.dondathang.imp.ErpDondathangDoitac;
import geso.dms.distributor.beans.dondathang.imp.ErpDondathangDoitacList;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpDondathangDoitacSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	public ErpDondathangDoitacSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpDondathangDoitacList obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();	    

		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		if (userId.length()==0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

		String action = util.getAction(querystring);

		String locgiaQUYDOI = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("locgiaQUYDOI"));
		if(locgiaQUYDOI == null)
			locgiaQUYDOI = "0";


		System.out.println("ACTION LA: " + action );
		if(locgiaQUYDOI.equals("1"))
		{
			String spMa = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("spMa"));
			if(spMa == null)
				spMa = "";

			String dvt = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvt"));
			if(dvt == null)
				dvt = "";

			String ngaydh = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaydh"));
			if(ngaydh == null)
				ngaydh = "";

			String dvkdId = "";
			if(session.getAttribute("dvkdId") != null )
				dvkdId = (String) session.getAttribute("dvkdId");

			String kbhId = "";
			if(session.getAttribute("kbhId") != null )
				kbhId = (String) session.getAttribute("kbhId");

			String nppId = "";
			if(session.getAttribute("nppId") != null )
				nppId = (String) session.getAttribute("nppId");

			String doitacId = "";
			if(session.getAttribute("doitacId") != null )
				doitacId = (String) session.getAttribute("doitacId");

			String query = (String)request.getQueryString();
			spMa = new String(query.substring(query.indexOf("&spMa=") + 6, query.indexOf("&dvt=")).getBytes("UTF-8"), "UTF-8");
			spMa = URLDecoder.decode(spMa, "UTF-8").replace("+", " ");

			dvt = new String(query.substring(query.indexOf("&dvt=") + 5, query.indexOf("&locgiaQUYDOI")).getBytes("UTF-8"), "UTF-8");
			dvt = URLDecoder.decode(dvt, "UTF-8").replace("+", " ");

			ngaydh = new String(query.substring(query.indexOf("&locgiaQUYDOI=") + 9, query.indexOf("&ngaydh")).getBytes("UTF-8"), "UTF-8");
			ngaydh = URLDecoder.decode(ngaydh, "UTF-8").replace("+", " ");


			//System.out.println(" -- MA SP: " + spMa + " -- DVT: " + dvt );
			//spMa = URLDecoder.decode(spMa, "UTF-8").replace("+", " ");
			//dvt = URLDecoder.decode(dvt, "UTF-8").replace("+", " ");

			if(spMa.trim().length() > 0 && dvt.trim().length() > 0 )
			{
				dbutils db = new dbutils();


				String command = "select a.DVDL_FK as dvCHUAN, ( select PK_SEQ from DONVIDOLUONG where DONVI = N'" + dvt + "' ) as dvNEW,      " +
				"	isnull( ( select SOLUONG1 / SOLUONG2 from QUYCACH where SANPHAM_FK = a.pk_seq and DVDL1_FK = a.DVDL_FK and DVDL2_FK = ( select PK_SEQ from DONVIDOLUONG where DONVI = N'" + dvt + "' ) ), 0 ) as quydoi,  	  " +
				"	dbo.[GiaDoitacSanpham](100001,"+kbhId+","+nppId+",a.pk_seq,0,'"+ ngaydh +"') as giamua   " +
				"from SANPHAM a where a.MA = '" + spMa + "'  ";

				System.out.println("Lay don gia san pham: " + command);
				String kq  = "0";
				String quycach = "0";
				ResultSet rs = db.get(command);
				try
				{
					if(rs.next())
					{
						String dvCHUAN = rs.getString("dvCHUAN");
						String dvNEW = rs.getString("dvNEW");
						double quydoi = rs.getDouble("quydoi");
						double dongia = rs.getDouble("giamua");
						quycach = Double.toString(quydoi);
						//System.out.println("DON VI NEW: " + dvNEW);
						if(dvCHUAN.equals(dvNEW))
							kq = Double.toString(dongia);
						else
							kq = Double.toString(dongia * quydoi );


					}
					rs.close();
				} 
				catch (Exception e)
				{
					kq = "0_0";
				}

				db.shutDown();
				String res = kq + "_" + quycach;
				System.out.println("GIA: " + res);
				out.write(res);
			}
			else
			{
				out.write("0_0");
			}

			return;
		}
		else
		{
			String lsxId = util.getId(querystring);
			obj = new ErpDondathangDoitacList();

			String loaidonhang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaidonhang"));
			if(loaidonhang == null)
				loaidonhang = "0";
			obj.setLoaidonhang(loaidonhang);
			System.out.println("---LOAI DON HANG: " + loaidonhang);


			obj.setUserId(userId);

			if (action.equals("delete") )
			{	
				IErpDondathangDoitac lsxBean=  new ErpDondathangDoitac(lsxId);
				String msg = lsxBean.DeleteChuyenKho(lsxId);
				obj.setMsg(msg);
				lsxBean.DBclose();
			}
			else if(action.equals("chot"))
			{
				IErpDondathangDoitac lsxBean=  new ErpDondathangDoitac(lsxId);
				lsxBean.setUserId(userId);
				String msg = lsxBean.Duyet() ;
				obj.setMsg(msg);
				lsxBean.DBclose();
			}
			else if(action.equals("unchot"))
			{
				String msg = this.UnChot(lsxId, userId);
				obj.setMsg(msg);
			}

			obj.setUserId(userId);
			obj.init("");

			session.setAttribute("obj", obj);

			String nextJSP = "/AHF/pages/Distributor/ErpDonDatHangDoiTac.jsp";
			response.sendRedirect(nextJSP);
		}
	}

	private String UnChot(String lsxId, String userId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);

			String query = "update ERP_DondathangNPP set trangthai = '0', NPP_DACHOT = '0' where pk_seq = '" + lsxId + "'";
			if(!db.update(query))
			{
				msg = "Khong the chot: " + query;
				db.getConnection().rollback();
				return msg;
			}


			query = "select dungchungkenh from NHAPHANPHOI where PK_SEQ =  (SELECT NPP_FK FROM ERP_DONDATHANGNPP WHERE PK_sEQ="+lsxId+" )";
			ResultSet rs = db.get(query);
			boolean dungchungkenh=false;
			if(rs.next())
			{
				if(rs.getString("dungchungkenh").equals("1")){
					dungchungkenh=true;
				}
			}
			rs.close();

			Utility uilt_kho=new Utility();
			//GIAM BOOK, TANG AVAI
			query=	"	select khoxuat_fk, npp_fk, "+(dungchungkenh?"100025":" kbh_fk")+ " as kbh_fk, sanpham_fk, sum(soluong) as soluong  " +
			"	from " +
			"	( " +
			"		select c.kho_fk as khoxuat_fk, c.npp_fk,   kbh_fk, a.sanpham_fk,       " +
			"				case when a.dvdl_fk IS null then a.soluong       " +
			"					 when a.dvdl_fk = b.DVDL_FK then a.soluong      " +
			"					 else  a.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and dvdl1_fk = b.dvdl_fk )  end as soluong    " +
			"		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ   " +
			"				inner join ERP_DONDATHANGNPP c on a.dondathang_fk = c.pk_seq     " +
			"		where a.dondathang_fk in (  " + lsxId + "  ) and a.soluong > 0 " +
			"	) " +
			"	DATHANG  " +
			"	group by khoxuat_fk, npp_fk, "+(dungchungkenh?"":" kbh_fk,")+ "  sanpham_fk " ;

			ResultSet rskho=db.get(query);
			while(rskho.next()){
				String   _khoxuat_fk, _npp_fk, _kbh_fk, _sanpham_fk ;
				_khoxuat_fk=rskho.getString("khoxuat_fk");
				_npp_fk=rskho.getString("npp_fk");
				_kbh_fk=rskho.getString("kbh_fk");
				_sanpham_fk=rskho.getString("sanpham_fk"); 
				double soluongct_ =rskho.getDouble("soluong");
				String msg1=uilt_kho.Update_NPP_Kho_Sp(this.getDateTime(), "erpdondathangDoitacSvl 340",
						db, _khoxuat_fk, _sanpham_fk, _npp_fk, _kbh_fk, 0, soluongct_*(-1), soluongct_, 0);
				if(msg1.length() >0){
					db.getConnection().rollback();
					return msg1;
				}

			}
			rskho.close();

			// xoa bang chi tiet


			query=	" SELECT KHOXUAT_FK, NPP_FK,   "+(dungchungkenh?"100025":" kbh_fk")+ " as  KBH_FK, SANPHAM_FK, SUM(SOLUONG) AS SOLUONG   ,SOLO, NGAYHETHAN, NGAYNHAPKHO "+      
			" FROM "+
			" ( "+
			" SELECT C.KHO_FK AS KHOXUAT_FK, C.NPP_FK,   KBH_FK, A.SANPHAM_FK, A.SOLO,A.NGAYHETHAN,A.NGAYNHAPKHO, "+     
			" CASE WHEN A.DVDL_FK IS NULL THEN A.SOLUONG       "+
			" WHEN A.DVDL_FK = B.DVDL_FK THEN A.SOLUONG      "+
			" ELSE  A.SOLUONG * ( SELECT SOLUONG1 / SOLUONG2 FROM QUYCACH "+ 
			" WHERE SANPHAM_FK = A.SANPHAM_FK AND DVDL2_FK = A.DVDL_FK AND DVDL1_FK = B.DVDL_FK )  END AS SOLUONG "+   
			" FROM ERP_DONDATHANGNPP_SANPHAM_CHITIET A INNER JOIN SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ   "+
			" INNER JOIN ERP_DONDATHANGNPP C ON A.DONDATHANG_FK = C.PK_SEQ     "+
			" WHERE A.DONDATHANG_FK IN (  "+lsxId+" ) AND A.SOLUONG > 0 "+
			" ) "+
			" DATHANG "+ 
			" GROUP BY KHOXUAT_FK, NPP_FK, "+(dungchungkenh?"":" kbh_fk,")+ " SANPHAM_FK ,SOLO, NGAYHETHAN, NGAYNHAPKHO    ";

			rskho=db.get(query);
			while(rskho.next()){
				String   _khoxuat_fk, _npp_fk, _kbh_fk, _sanpham_fk,_solo,_ngayhethan,_ngaynhapkho ;
				_khoxuat_fk=rskho.getString("khoxuat_fk");
				_npp_fk=rskho.getString("npp_fk");
				_kbh_fk=rskho.getString("kbh_fk");
				_sanpham_fk=rskho.getString("sanpham_fk"); 
				_solo= rskho.getString("SOLO");
				_ngayhethan= rskho.getString("ngayhethan");
				_ngaynhapkho= rskho.getString("ngaynhapkho");

				double soluongct_ =rskho.getDouble("SOLUONG");

				/*String msg1=uilt_kho.Update_NPP_Kho_Sp_Chitiet("","Cập nhật đơn hàng đối tác :erpdondathangDoitacSvl 372" 
						, db, _khoxuat_fk, _sanpham_fk, _npp_fk, _kbh_fk, _solo, _ngayhethan, _ngaynhapkho, 0, (-1)*soluongct_, soluongct_, soluongct_, 0);
				if(msg1.length() >0){
					db.getConnection().rollback();
					return msg1;
				}*/


			}
			rskho.close();

			query = "delete ERP_DONDATHANGNPP_SANPHAM_CHITIET where dondathang_fk = '" + lsxId + "' ";
			if(db.updateReturnInt(query)<=0)
			{

				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return "Lỗi khi unchot: " + query;
			}


			db.getConnection().commit();
			db.shutDown();
		}
		catch (Exception e) 
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			db.shutDown();
			return "Exception: " + e.getMessage();
		}

		return "";
	}

	

	public static String getDateTime1() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss.SS");
		Date date = new Date();
		return dateFormat.format(date);	
	}

	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		if (action == null)
		{
			action = "";
		}

		String loaidonhang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaidonhang"));
		if(loaidonhang == null)
			loaidonhang = "0";


		IErpDondathangDoitacList obj = new ErpDondathangDoitacList();
		obj.setLoaidonhang(loaidonhang);



		Utility util = new Utility();

		HttpSession session = request.getSession();
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"))); 

		obj.setUserId(userId);


		if(action.equals("Tao moi"))
		{
			IErpDondathangDoitac lsxBean = new ErpDondathangDoitac();
			lsxBean.setLoaidonhang(loaidonhang);
			lsxBean.setUserId(userId);

			lsxBean.createRs();
			session.setAttribute("dvkdId", lsxBean.getDvkdId());
			session.setAttribute("kbhId", lsxBean.getKbhId());
			session.setAttribute("nppId", lsxBean.getNppId());
			session.setAttribute("doitacId", "");
			session.setAttribute("ngaydh", lsxBean.getNgayyeucau());

			session.setAttribute("lsxBean", lsxBean);

			String nextJSP = "/AHF/pages/Distributor/ErpDonDatHangDoiTacNew.jsp";
			response.sendRedirect(nextJSP);
		}
		else
		{
			if(action.equals("view") || action.equals("next") || action.equals("prev"))
			{
				String search = getSearchQuery(request, obj);
				obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
				obj.setUserId(userId);
				obj.init(search);
				obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
				session.setAttribute("obj", obj);

				String nextJSP = "/AHF/pages/Distributor/ErpDonDatHangDoiTac.jsp";
				response.sendRedirect(nextJSP);
			}
			else
			{
				String search = getSearchQuery(request, obj);
				obj.setUserId(userId);
				obj.init(search);

				session.setAttribute("obj", obj);  	
				session.setAttribute("userId", userId);

				String nextJSP = "/AHF/pages/Distributor/ErpDonDatHangDoiTac.jsp";
				response.sendRedirect(nextJSP);
			}
		}
	}

	private String getSearchQuery(HttpServletRequest request, IErpDondathangDoitacList obj)
	{
		Utility util = new Utility();
		String query = "select isnull(a.isdhkhac,0)isdhkhac ,isnull(a.ngaygiochot,a.ngaydonhang) as ngaygiochot,a.PK_SEQ, a.trangthai, a.ngaydonhang, c.ten as nppTEN, b.ten as khoTEN, NV.TEN as nguoitao, ISNULL(cast(a.from_dondathang as nvarchar),'')as maddh, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, a.NPP_DACHOT,   " +
		"case a.KBH_FK when 100052 then N'ETC' when 100025 then N'OTC' end as KenhBanHang,isnull(a.iskm,0) as iskm "+
		"from ERP_DondathangNPP a inner join KHO b on a.kho_fk = b.pk_seq inner join NHAPHANPHOI c on a.NPP_DAT_FK = c.pk_seq  " +
		"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
		"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.pk_seq > 0 and a.kho_fk in "+util.quyen_kho(obj.getUserId());

		String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);

		String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"));
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);

		String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);

		String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
		if(nppId == null)
			nppId = "";
		obj.setNppTen(nppId);

		String sodonhang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sodonhang"));
		if(sodonhang == null)
			sodonhang = "";
		obj.setSodonhang(sodonhang);

		String khId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khId"));
		if(khId == null)
			khId = "";
		obj.setKhTen(khId);

		String madhdt = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("madhdt"));
		if(madhdt == null)
			madhdt = "";
		obj.setMaddh(madhdt);


		String iskm = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("iskm")==null?"0":request.getParameter("iskm")));
		obj.setIsKm(iskm);

		if(iskm.length() > 0)
			query += " and isnull(a.isdhkhac,0) = '" + iskm + "' ";


		if(tungay.length() > 0)
			query += " and a.ngaydonhang >= '" + tungay + "'";

		if(denngay.length() > 0)
			query += " and a.ngaydonhang <= '" + denngay + "'";

		if(trangthai.length() > 0)
			query += " and a.TrangThai = '" + trangthai + "'";
		else
			query += " and a.TrangThai != 3 ";
		if(nppId.length() > 0){
			query += " and a.NPP_FK= '" + nppId + "'";
		}

		if(sodonhang.length() > 0){
			query += " and cast( a.PK_SEQ as varchar(10) ) like '%" + sodonhang + "%'";
		}

		if(khId.length() > 0){
			query += " and a.npp_dat_FK = '" + khId + "'";
		}
		if(madhdt.length() > 0){
			query += " and a.from_dondathang = '" + madhdt + "'";
		}
		System.out.print(query);
		return query;
	}

	public String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}


}
