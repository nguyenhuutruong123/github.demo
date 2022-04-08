package geso.dms.center.beans.dondathang.imp;


import geso.dms.center.beans.dondathang.IErpDuyetddh;
import geso.dms.center.db.sql.dbutils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

public class ErpDuyetddh implements IErpDuyetddh
{
	private static final long serialVersionUID = 1L;
	String userId;
	String id;
	
	String ngayyeucau;
	String ngaydenghi;
	String ghichu;

	String msg;
	String trangthai;
	
	String loaidonhang;  //0 đơn đặt hàng, 1 đơn hàng khuyến mại ứng hàng, 3 đơn hàng khuyến mại tích lũy, 4 đơn hàng trưng bày, 5 đơn hàng khác
	String chietkhau;
	String vat;
	
	String khoNhanId;
	ResultSet khoNhanRs;
	
	String nppId;
	ResultSet nppRs;
	
	String dvkdId;
	ResultSet dvkdRs;
	
	String kbhId;
	ResultSet kbhRs;
	
	ResultSet dvtRs;
	
	String schemeId;
	ResultSet schemeRs;
	
	Hashtable<String, String> scheme_soluong;

	String[] spId;
	String[] spMa;
	String[] spTen;
	String[] spDonvi;
	String[] spSoluong;
	String[] spGianhap;
	String[] spChietkhau;
	String[] spSCheme;
	String[] spTrongluong;
	String[] spThetich;
	
	String[] dhCk_diengiai;
	String[] dhCk_giatri;
	String[] dhCk_loai;
	
	ResultSet congnoRs;
	
	dbutils db;
	
	public ErpDuyetddh()
	{
		this.id = "";
		this.ngayyeucau = getDateTime();
		this.ngaydenghi = "";
		this.ghichu = "";
		this.khoNhanId = "";
		this.nppId = "";
		this.msg = "";
		this.loaidonhang = "0";
		this.trangthai = "0";
		this.chietkhau = "0";
		this.vat = "10";
		this.dvkdId = "";
		this.kbhId = "";
		this.schemeId = "";
		
		this.scheme_soluong = new Hashtable<String, String>();
		
		this.db = new dbutils();
	}
	
	public ErpDuyetddh(String id)
	{
		this.id = id;
		this.ngayyeucau = getDateTime();
		this.ngaydenghi = "";
		this.ghichu = "";
		this.khoNhanId = "";
		this.nppId = "";
		this.msg = "";
		this.loaidonhang = "0";
		this.trangthai = "0";
		this.chietkhau = "0";
		this.vat = "10";
		this.dvkdId = "";
		this.kbhId = "";
		this.schemeId = "";

		this.scheme_soluong = new Hashtable<String, String>();
		
		this.db = new dbutils();
	}

	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}

	public String getId() 
	{
		return this.id;
	}

	public void setId(String Id) 
	{
		this.id = Id;
	}

	public String getNgayyeucau() 
	{
		return this.ngayyeucau;
	}

	public void setNgayyeucau(String ngayyeucau) 
	{
		this.ngayyeucau = ngayyeucau;
	}

	public String getKhoNhapId()
	{
		return this.khoNhanId;
	}

	public void setKhoNhapId(String khonhaptt) 
	{
		this.khoNhanId = khonhaptt;
	}

	public ResultSet getKhoNhapRs() 
	{
		return this.khoNhanRs;
	}

	public void setKhoNHapRs(ResultSet khonhapRs) 
	{
		this.khoNhanRs = khonhapRs;
	}
	
	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public void createRs() 
	{
		this.khoNhanRs = db.get("select PK_SEQ, TEN from ERP_KHOTT where trangthai = '1' ");
		this.dvtRs = db.getScrol("select PK_SEQ, DONVI from DONVIDOLUONG where trangthai = '1' ");
		
		this.dvkdRs = db.get("select PK_SEQ, DONVIKINHDOANH as TEN from DONVIKINHDOANH where TRANGTHAI = '1'");
		this.kbhRs = db.get("select PK_SEQ, DIENGIAI as TEN from KENHBANHANG where TRANGTHAI = '1'");
		
		String query = "select PK_SEQ, MA + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1' ";
		if(this.dvkdId.trim().length() > 0)
			query += " and pk_seq in ( select NPP_FK from NHAPP_NHACC_DONVIKD where NCC_DVKD_FK in ( select PK_SEQ from NHACUNGCAP_DVKD where DVKD_FK = '" + this.dvkdId + "' ) ) ";
		if(this.kbhId.trim().length() > 0)
			query += " and pk_seq in ( select NPP_FK from NHAPP_KBH where KBH_FK = '" + this.kbhId + "' ) ";
		
		this.nppRs = db.get(query);
		
		if(this.nppId.trim().length() > 0)
		{
			query = "select a.PK_SEQ as nppId, d.DVKD_FK, b.KBH_FK  " +
				    "From NhaPhanPhoi a left join nhapp_kbh b on b.NPP_FK = a.PK_SEQ left join NHAPP_NHACC_DONVIKD c on c.NPP_FK = b.NPP_FK  " +
				    "	left join NHACUNGCAP_DVKD d on d.PK_SEQ = c.NCC_DVKD_FK   " +
				    "where a.pk_Seq = '" + this.nppId + "' ";
			ResultSet rsInfo = db.get(query);
			if(rsInfo != null)
			{
				try 
				{
					if(rsInfo.next())
					{
						if(this.dvkdId.trim().length() <= 0)
							this.dvkdId = rsInfo.getString("DVKD_FK");
						if(this.kbhId.trim().length() <= 0 )
							this.kbhId = rsInfo.getString("KBH_FK");
					}
					rsInfo.close();
				} 
				catch (Exception e) {}
			}
			
			//INIT CONG NO
			query = " select a.MA, a.TEN, b.DONVI, ISNULL(HANGMUA.noHANGMUA, 0) as noHANGMUA, ISNULL(KM.noKHUYENMAI, 0) as noKHUYENMAI, " +
					" 			ISNULL(TL.noTICHLUY, 0) as noTICHLUY, ISNULL(TB.noTRUNGBAY, 0) as noTRUNGBAY, 0 as noDOIHANG " +
					" from SANPHAM a inner join DONVIDOLUONG b on a.DVDL_FK = b.PK_SEQ left join " +
					" (	 " +
					" 	 select ddh.PK_SEQ as sanpham_fk, ddh.soluongDAT - ISNULL( daxuat.soluongDAXUAT, 0) as noHANGMUA " +
					" 	 from    " +
					" 	 (    " +
					"  		select sp.PK_SEQ, SUM(dathang.soluong) as soluongDAT     " +
					"  		from    " +
					"  		(    " +
					"  				select a.sanpham_fk,   " +
					"  						case when a.dvdl_fk IS null then a.soluong     " +
					"  							 when a.dvdl_fk = b.DVDL_FK then a.soluong    " +
					"  							 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )     " +
					"  											 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )	 end as soluong  " +
					"  				from ERP_DONDATHANG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ    " +
					"  				where a.dondathang_fk in (    select PK_SEQ from ERP_DONDATHANG where TRANGTHAI = '2' and NPP_FK = '" + this.nppId + "'  and LoaiDonHang = '0'  )   " +
					"  			union ALL  " +
					"  				select b.PK_SEQ,  a.soluong   " +
					"  				from ERP_DONDATHANG_CTKM_TRAKM a inner join SANPHAM b on a.SPMA = b.MA   " +
					"  						inner join CTKHUYENMAI c on a.CTKMID = c.PK_SEQ   " +
					"  				where a.DONDATHANGID in (    select PK_SEQ from ERP_DONDATHANG where TRANGTHAI = '2' and NPP_FK = '" + this.nppId + "' and LoaiDonHang = '0'   )    " +
					"  		)    " +
					"  		dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ    " +
					"  		group by sp.PK_SEQ   " +
					" 	 )    " +
					" 	 ddh left join     " +
					" 	 (    " +
					"  		select b.sanpham_fk, SUM( b.soluongXUAT ) as soluongDAXUAT    " +
					"  		from ERP_YCXUATKHO a inner join ERP_YCXUATKHO_SANPHAM b on a.PK_SEQ = b.ycxk_fk   " +
					"  		where a.TRANGTHAI in (2)    " +
					"  			and a.PK_SEQ in ( select ycxk_fk from  ERP_YCXUATKHO_DDH where ddh_fk in (  select PK_SEQ from ERP_DONDATHANG where TRANGTHAI = '2' and NPP_FK = '" + this.nppId + "' and LoaiDonHang = '0'  ) )   " +
					"  		group by b.sanpham_fk, b.LOAI, b.SCHEME   " +
					" 	 )   " +
					" 	 daxuat on ddh.PK_SEQ = daxuat.sanpham_fk	 " +
					" ) " +
					" HANGMUA on a.PK_SEQ = HANGMUA.sanpham_fk left join  " +
					" ( " +
					" 	 select ddh.sanpham_fk, ddh.soluongDAT - ISNULL( daxuat.soluongDAXUAT, 0) as noKHUYENMAI " +
					" 	 from    " +
					" 	 (    " +
					" 		select a.sanpham_fk, sum(a.soluong) as soluongDAT  " +
					" 		from ERP_DONDATHANG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ    " +
					" 		where a.dondathang_fk in (    select PK_SEQ from ERP_DONDATHANG where TRANGTHAI = '2' and NPP_FK = '" + this.nppId + "' and LoaiDonHang = '1'   )  " +
					" 		group by  a.sanpham_fk " +
					" 	 )    " +
					" 	 ddh left join     " +
					" 	 (    " +
					"  		select b.sanpham_fk, SUM( b.soluongXUAT ) as soluongDAXUAT    " +
					"  		from ERP_YCXUATKHO a inner join ERP_YCXUATKHO_SANPHAM b on a.PK_SEQ = b.ycxk_fk   " +
					"  		where a.TRANGTHAI in (2)    " +
					"  			and a.PK_SEQ in ( select ycxk_fk from  ERP_YCXUATKHO_DDH where ddh_fk in (  select PK_SEQ from ERP_DONDATHANG where TRANGTHAI = '2' and NPP_FK = '" + this.nppId + "' and LoaiDonHang = '1'  ) )   " +
					"  		group by b.sanpham_fk, b.LOAI, b.SCHEME   " +
					" 	 )   " +
					" 	 daxuat on ddh.sanpham_fk = daxuat.sanpham_fk	 " +
					" ) " +
					" KM on a.PK_SEQ = KM.sanpham_fk left join  " +
					" ( " +
					" 	 select ddh.sanpham_fk, ddh.soluongDAT - ISNULL( daxuat.soluongDAXUAT, 0) as noTICHLUY " +
					" 	 from    " +
					" 	 (    " +
					" 		select a.sanpham_fk, sum(a.soluong) as soluongDAT  " +
					" 		from ERP_DONDATHANG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ    " +
					" 		where a.dondathang_fk in (    select PK_SEQ from ERP_DONDATHANG where TRANGTHAI = '2' and NPP_FK = '" + this.nppId + "' and LoaiDonHang = '2'   )  " +
					" 		group by  a.sanpham_fk " +
					" 	 )    " +
					" 	 ddh left join     " +
					" 	 (    " +
					"  		select b.sanpham_fk, SUM( b.soluongXUAT ) as soluongDAXUAT    " +
					"  		from ERP_YCXUATKHO a inner join ERP_YCXUATKHO_SANPHAM b on a.PK_SEQ = b.ycxk_fk   " +
					"  		where a.TRANGTHAI in (2)    " +
					"  			and a.PK_SEQ in ( select ycxk_fk from  ERP_YCXUATKHO_DDH where ddh_fk in (  select PK_SEQ from ERP_DONDATHANG where TRANGTHAI = '2' and NPP_FK = '" + this.nppId + "' and LoaiDonHang = '2'  ) )   " +
					"  		group by b.sanpham_fk, b.LOAI, b.SCHEME   " +
					" 	 )   " +
					" 	 daxuat on ddh.sanpham_fk = daxuat.sanpham_fk	 " +
					" ) " +
					" TL on a.PK_SEQ = TL.sanpham_fk left join  " +
					" ( " +
					" 	select ddh.sanpham_fk, ddh.soluongDAT - ISNULL( daxuat.soluongDAXUAT, 0) as noTRUNGBAY " +
					" 	 from    " +
					" 	 (    " +
					" 		select a.sanpham_fk, sum(a.soluong) as soluongDAT  " +
					" 		from ERP_DONDATHANG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ    " +
					" 		where a.dondathang_fk in (    select PK_SEQ from ERP_DONDATHANG where TRANGTHAI = '2' and NPP_FK = '" + this.nppId + "' and LoaiDonHang = '3'   )  " +
					" 		group by  a.sanpham_fk " +
					" 	 )    " +
					" 	 ddh left join     " +
					" 	 (    " +
					"  		select b.sanpham_fk, SUM( b.soluongXUAT ) as soluongDAXUAT    " +
					"  		from ERP_YCXUATKHO a inner join ERP_YCXUATKHO_SANPHAM b on a.PK_SEQ = b.ycxk_fk   " +
					"  		where a.TRANGTHAI in (2)    " +
					"  			and a.PK_SEQ in ( select ycxk_fk from  ERP_YCXUATKHO_DDH where ddh_fk in (  select PK_SEQ from ERP_DONDATHANG where TRANGTHAI = '2' and NPP_FK = '" + this.nppId + "' and LoaiDonHang = '3'  ) )   " +
					"  		group by b.sanpham_fk, b.LOAI, b.SCHEME   " +
					" 	 )   " +
					" 	 daxuat on ddh.sanpham_fk = daxuat.sanpham_fk " +
					" ) " +
					" TB on a.PK_SEQ = TB.sanpham_fk ";
			
			System.out.println("CONG NO: " + query);
			this.congnoRs = db.get(query);
			
		}
		
		if(this.loaidonhang.equals("2"))
		{
			query = "select b.PK_SEQ, b.SCHEME + ', ' + b.DIENGIAI as SCHEME  " +
					"from DUYETTRAKHUYENMAI a inner join TIEUCHITHUONGTL b on a.CTKM_FK = b.PK_SEQ order by b.pk_seq desc ";
			this.schemeRs = db.get(query);
			
			if(this.schemeId.trim().length() > 0 && this.nppId.trim().length() > 0 )
			{
				query = "select a.PK_SEQ, a.SCHEME, a.DIENGIAI, SUM(b.thuong) as tongLUONG  " +
						"from TIEUCHITHUONGTL a inner join DUYETTRAKHUYENMAI_KHACHHANG b on a.PK_SEQ = b.duyetkm_fk " +
						"where a.PK_SEQ in ( " + this.schemeId + " ) and b.nppId = '" + this.nppId + "' and donvi = '2' " +
						"group by a.PK_SEQ, a.SCHEME, a.DIENGIAI";
				
				ResultSet rs = db.get(query);
				if(rs != null)
				{
					try 
					{
						String _schemeID = "";
						String _scheme = "";
						String _diengiai = "";
						String _tongluong = "";
						
						NumberFormat format = new DecimalFormat("#,###,###");
						while(rs.next())
						{
							_schemeID += rs.getString("PK_SEQ") + "__";
							_scheme += rs.getString("SCHEME") + "__";
							_diengiai += rs.getString("DIENGIAI") + "__";
							_tongluong += format.format(rs.getDouble("tongLUONG")) + "__";
						}
						rs.close();
						
						if(_scheme.trim().length() > 0)
						{
							_schemeID = _schemeID.substring(0, _schemeID.length() -2 );
							this.spId = _schemeID.split("__");
							
							_scheme = _scheme.substring(0, _scheme.length() -2 );
							this.spMa = _scheme.split("__");
							
							_diengiai = _diengiai.substring(0, _diengiai.length() -2 );
							this.spTen = _diengiai.split("__");
							
							_tongluong = _tongluong.substring(0, _tongluong.length() -2 );
							this.spSoluong = _tongluong.split("__");
							
						}
					} 
					catch (Exception e) {}
				}
			}
		}
		else if(loaidonhang.equals("1"))
		{
			query = "select PK_SEQ, SCHEME from CTKHUYENMAI where  KHO_FK = '100001' order by DENNGAY desc ";
			System.out.println("Scheme : "+query);
			this.schemeRs = db.get(query);
			
			if(this.schemeId.trim().length() > 0 && this.nppId.trim().length() > 0 )
			{
				/*query = "select c.PK_SEQ, c.SCHEME, c.DIENGIAI, SUM(a.SOLUONG) as tongLUONG  " +
						"from DONHANG_CTKM_TRAKM a inner join SANPHAM b on a.SPMA = b.MA " +
						"	inner join CTKHUYENMAI c on a.CTKMID = c.PK_SEQ " +
						"where a.CTKMID in (" + this.schemeId + ") and SPMA is not null " +
								"and a.DONHANGID in ( select pk_seq from DONHANG where trangthai = '1' and NPP_FK = '" + this.nppId + "' ) " +
						"group by c.PK_SEQ, c.SCHEME, c.DIENGIAI";*/
				
				query = " select UNGHANG.PK_SEQ, UNGHANG.SCHEME, UNGHANG.DIENGIAI,  " +
						" 	UNGHANG.tongLUONG - ISNULL(DUYET.SOLUONG, 0) as tongLUONG " +
						" from " +
						" ( " +
						" 	select c.PK_SEQ, c.SCHEME, c.DIENGIAI, SUM(a.SOLUONG) as tongLUONG  " +
						" 	from DONHANG_CTKM_TRAKM a inner join SANPHAM b on a.SPMA = b.MA " +
						" 			inner join CTKHUYENMAI c on a.CTKMID = c.PK_SEQ " +
						" 	where a.CTKMID in ( " + this.schemeId + " ) and SPMA is not null " +
						"					and a.DONHANGID in ( select pk_seq from DONHANG where trangthai = '1' and NPP_FK = '" + this.nppId + "' ) " +
						" 	group by c.PK_SEQ, c.SCHEME, c.DIENGIAI " +
						" ) " +
						" UNGHANG left join " +
						" ( " +
						" 	select b.ctkm_fk, SUM(b.soluong) as soluong  " +
						" 	from ERP_DONDATHANG a inner join ERP_DONDATHANG_SANPHAM b on a.PK_SEQ = b.dondathang_fk " +
						" 	where a.LoaiDonHang = '1' and a.TRANGTHAI != 3 and b.ctkm_fk in ( " + this.schemeId + " ) and a.NPP_FK = '" + this.nppId + "'  " + ( ( this.id.trim().length() > 0 ) ? " AND a.pk_seq != '" + this.id + "' " : "" ) +
						" 	group by b.ctkm_fk " +
						" ) " +
						" DUYET on UNGHANG.PK_SEQ = DUYET.ctkm_fk " +
						" where UNGHANG.tongLUONG - ISNULL(DUYET.SOLUONG, 0) > 0 ";
				
				System.out.println("INIT SP: " + query);
				ResultSet rs = db.get(query);
				if(rs != null)
				{
					try 
					{
						String _schemeID = "";
						String _scheme = "";
						String _diengiai = "";
						String _tongluong = "";
						
						NumberFormat format = new DecimalFormat("#,###,###");
						while(rs.next())
						{
							System.out.println("--TOI DAY: " + rs.getString("PK_SEQ") );
							_schemeID += rs.getString("PK_SEQ") + "__";
							_scheme += rs.getString("SCHEME") + "__";
							_diengiai += rs.getString("DIENGIAI") + "__";
							_tongluong += format.format(rs.getDouble("tongLUONG")) + "__";
						}
						rs.close();
						
						if(_scheme.trim().length() > 0)
						{
							_schemeID = _schemeID.substring(0, _schemeID.length() -2 );
							this.spId = _schemeID.split("__");
							
							_scheme = _scheme.substring(0, _scheme.length() -2 );
							this.spMa = _scheme.split("__");
							
							_diengiai = _diengiai.substring(0, _diengiai.length() -2 );
							this.spTen = _diengiai.split("__");
							
							_tongluong = _tongluong.substring(0, _tongluong.length() -2 );
							this.spSoluong = _tongluong.split("__");
							
						}
						
						System.out.println("---SCHEME: " + _scheme);
					} 
					catch (Exception e) { e.printStackTrace(); }
					
					
				}
			}
		}
		else if(this.loaidonhang.equals("3"))
		{
			query = "select PK_SEQ, SCHEME from CTTRUNGBAY order by pk_seq desc ";
			this.schemeRs = db.get(query);
			
			if(this.schemeId.trim().length() > 0 && this.nppId.trim().length() > 0 )
			{
				/*query = " select d.PK_SEQ, d.SCHEME, d.DIENGIAI,  " +
						" 	b.xuatduyet * ( select SUM(isnull(TONGLUONG, 0)) from TRATRUNGBAY where PK_SEQ in ( select TRATRUNGBAY_FK from CTTB_TRATB where CTTRUNGBAY_FK = d.PK_SEQ ) ) as tongluong " +
						" from DENGHITRATRUNGBAY a inner join DENGHITRATB_KHACHHANG b on a.PK_SEQ = b.DENGHITRATB_FK " +
						" 		inner join DANGKYTRUNGBAY c on a.CTTRUNGBAY_FK  = c.CTTRUNGBAY_FK " +
						" 		inner join CTTRUNGBAY d on c.CTTRUNGBAY_FK = d.PK_SEQ " +
						" where c.TRANGTHAI = '1' and a.NPP_FK = '" + this.nppId + "' and b.XUATDUYET != 0 and d.PK_SEQ in ( 2, 3 ) ";*/
				
				query = " select UNGHANG.PK_SEQ, UNGHANG.SCHEME, UNGHANG.DIENGIAI,  UNGHANG.tongLUONG - ISNULL(DUYET.SOLUONG, 0) as tongLUONG " +
						" from " +
						" ( " +
						"  select d.PK_SEQ, d.SCHEME, d.DIENGIAI,   " +
						"  	b.xuatduyet * ( select SUM(isnull(TONGLUONG, 0)) from TRATRUNGBAY where PK_SEQ in ( select TRATRUNGBAY_FK from CTTB_TRATB where CTTRUNGBAY_FK = d.PK_SEQ ) ) as tongluong  " +
						"  from DENGHITRATRUNGBAY a inner join DENGHITRATB_KHACHHANG b on a.PK_SEQ = b.DENGHITRATB_FK  " +
						"  		inner join DANGKYTRUNGBAY c on a.CTTRUNGBAY_FK  = c.CTTRUNGBAY_FK  " +
						"  		inner join CTTRUNGBAY d on c.CTTRUNGBAY_FK = d.PK_SEQ  " +
						"  where c.TRANGTHAI = '1' and a.NPP_FK = '" + this.nppId + "' and b.XUATDUYET != 0 and d.PK_SEQ in ( " + this.schemeId + " )  " +
						" ) " +
						" UNGHANG left join  " +
						" ( " +
						" 	select b.cttb_fk, SUM(b.soluong) as soluong   " +
						" 	from ERP_DONDATHANG a inner join ERP_DONDATHANG_SANPHAM b on a.PK_SEQ = b.dondathang_fk " +
						" 	where a.LoaiDonHang = '3' and a.TRANGTHAI != 3 and b.cttb_fk in ( " + this.schemeId + " ) and a.NPP_FK = '" + this.nppId + "' " + ( ( this.id.trim().length() > 0 ) ? " AND a.pk_seq != '" + this.id + "' " : "" ) +
						" 	group by b.cttb_fk  " +
						" ) " +
						" DUYET on UNGHANG.PK_SEQ = DUYET.cttb_fk " +
						" where UNGHANG.tongLUONG - ISNULL(DUYET.SOLUONG, 0) > 0 ";
				
				ResultSet rs = db.get(query);
				if(rs != null)
				{
					try 
					{
						String _schemeID = "";
						String _scheme = "";
						String _diengiai = "";
						String _tongluong = "";
						
						NumberFormat format = new DecimalFormat("#,###,###");
						while(rs.next())
						{
							_schemeID += rs.getString("PK_SEQ") + "__";
							_scheme += rs.getString("SCHEME") + "__";
							_diengiai += rs.getString("DIENGIAI") + "__";
							_tongluong += format.format(rs.getDouble("tongLUONG")) + "__";
						}
						rs.close();
						
						if(_scheme.trim().length() > 0)
						{
							_schemeID = _schemeID.substring(0, _schemeID.length() -2 );
							this.spId = _schemeID.split("__");
							
							_scheme = _scheme.substring(0, _scheme.length() -2 );
							this.spMa = _scheme.split("__");
							
							_diengiai = _diengiai.substring(0, _diengiai.length() -2 );
							this.spTen = _diengiai.split("__");
							
							_tongluong = _tongluong.substring(0, _tongluong.length() -2 );
							this.spSoluong = _tongluong.split("__");
							
						}
					} 
					catch (Exception e) {}
				}
			}
		}
		
	}

	private void initSANPHAM() 
	{
		String query =  "select b.MA, b.TEN, DV.donvi, a.soluong, a.dongia, isnull(a.chietkhau, 0) as chietkhau, ISNULL(b.trongluong, 0) as trongluong, ISNULL(b.thetich, 0) as thetich    " +
						" from ERP_Dondathang_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ    " +
						" 		INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK       " +
						"where a.Dondathang_FK = '" + this.id + "' ";
		
		System.out.println("---INIT SP: " + query);
		ResultSet spRs = db.get(query);
		
		NumberFormat formater = new DecimalFormat("##,###,###");
		if(spRs != null)
		{
			try 
			{
				String spMA = "";
				String spTEN = "";
				String spDONVI = "";
				String spSOLUONG = "";
				String spGIANHAP = "";
				String spCHIETKHAU = "";
				String spSCHEME = "";
				
				String spTRONGLUONG = "";
				String spTHETICH = "";
				
				while(spRs.next())
				{
					spMA += spRs.getString("MA") + "__";
					spTEN += spRs.getString("TEN") + "__";
					spDONVI += spRs.getString("DONVI") + "__";
					spSOLUONG += formater.format(spRs.getDouble("SOLUONG")) + "__";
					spGIANHAP += formater.format(spRs.getDouble("DONGIA")) + "__";
					spCHIETKHAU += formater.format(spRs.getDouble("chietkhau")) + "__";
					spSCHEME += " __";
					
					spTRONGLUONG += spRs.getString("trongluong") + "__";
					spTHETICH += spRs.getString("thetich") + "__";
				}
				spRs.close();
				
				//INIT SP KHUYEN MAI
				query = "select isnull(b.MA, ' ') as MA, isnull(b.TEN, ' ') as TEN, isnull(c.DONVI, ' ') as donvi, d.SCHEME, isnull(a.soluong, 0) as soluong, a.tonggiatri, " +
						"		ISNULL(b.trongluong, 0) as trongluong, ISNULL(b.thetich, 0) as thetich " +
						"from ERP_DONDATHANG_CTKM_TRAKM a left join SANPHAM b on a.SPMA = b.MA  " +
						"	left join DONVIDOLUONG c on b.DVDL_FK = c.PK_SEQ  " +
						"	inner join CTKHUYENMAI d on a.ctkmID = d.PK_SEQ " +
						"where a.dondathangID = '" + this.id + "' ";
				
				System.out.println("___INIT KM: " + query);
				spRs = db.get(query);
				while(spRs.next())
				{
					spMA += spRs.getString("MA") + "__";
					spTEN += spRs.getString("TEN") + "__";
					spDONVI += spRs.getString("DONVI") + "__";
					spSOLUONG += formater.format(spRs.getDouble("SOLUONG")) + "__";
					spGIANHAP += formater.format(spRs.getDouble("tonggiatri")) + "__";
					spCHIETKHAU += "0__";
					spSCHEME += spRs.getString("SCHEME") + "__";
					
					spTRONGLUONG += spRs.getString("trongluong") + "__";
					spTHETICH += spRs.getString("thetich") + "__";
				}
				spRs.close();
				
				if(spMA.trim().length() > 0)
				{
					spMA = spMA.substring(0, spMA.length() - 2);
					this.spMa = spMA.split("__");
					
					spTEN = spTEN.substring(0, spTEN.length() - 2);
					this.spTen = spTEN.split("__");
					
					spDONVI = spDONVI.substring(0, spDONVI.length() - 2);
					this.spDonvi = spDONVI.split("__");
					
					spSOLUONG = spSOLUONG.substring(0, spSOLUONG.length() - 2);
					this.spSoluong = spSOLUONG.split("__");
					
					spGIANHAP = spGIANHAP.substring(0, spGIANHAP.length() - 2);
					this.spGianhap = spGIANHAP.split("__");
					
					spCHIETKHAU = spCHIETKHAU.substring(0, spCHIETKHAU.length() - 2);
					this.spChietkhau = spCHIETKHAU.split("__");
					
					spSCHEME = spSCHEME.substring(0, spSCHEME.length() - 2);
					this.spSCheme = spSCHEME.split("__");
					
					spTRONGLUONG = spTRONGLUONG.substring(0, spTRONGLUONG.length() - 2);
					this.spTrongluong = spTRONGLUONG.split("__");
					
					spTHETICH = spTHETICH.substring(0, spTHETICH.length() - 2);
					this.spThetich = spTHETICH.split("__");
					
				}
				
				//INIT CHIET KHAU
				if(this.loaidonhang.equals("0"))
				{
					query = "select DIENGIAI, GIATRI, LOAI from ERP_DONDATHANG_CHIETKHAU where DONDATHANG_FK = '" + this.id + "' ORDER BY LOAI DESC";
					ResultSet rsCK = db.get(query);
					if(rsCK != null)
					{
						String dkCK_diengiai = "";
						String dkCK_giatri = "";
						String dkCK_loai = "";
						
						while(rsCK.next())
						{
							dkCK_diengiai += rsCK.getString("DIENGIAI") + "__";
							dkCK_giatri += formater.format(rsCK.getDouble("GIATRI")) + "__";
							dkCK_loai += rsCK.getString("LOAI") + "__";
						}
						rsCK.close();
						
						if(dkCK_diengiai.trim().length() > 0)
						{
							dkCK_diengiai = dkCK_diengiai.substring(0, dkCK_diengiai.length() - 2);
							this.dhCk_diengiai = dkCK_diengiai.split("__");
							
							dkCK_giatri = dkCK_giatri.substring(0, dkCK_giatri.length() - 2);
							this.dhCk_giatri = dkCK_giatri.split("__");
							
							dkCK_loai = dkCK_loai.substring(0, dkCK_loai.length() - 2);
							this.dhCk_loai = dkCK_loai.split("__");
						}
						
					}
				}
				
			} 
			catch (Exception e) 
			{
				System.out.println("115.Exception: " + e.getMessage());
			}
		}
		
	}

	public void init() 
	{
		String query = "select ngaydonhang, ngaydenghi, ISNULL(ghichu, '') as ghichu, dvkd_fk, kbh_fk, npp_fk, kho_fk, isnull(CongNo, 0) as SOTIENTHU, isnull(chietkhau, 0) as chietkhau, vat, loaidonhang " +
						"from ERP_Dondathang where pk_seq = '" + this.id + "'";
		System.out.println("____INIT NHAP KHO: " + query);
		ResultSet rs = db.get(query);
		
		NumberFormat format = new DecimalFormat("#,###,###");
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.ngayyeucau = rs.getString("ngaydonhang");
					this.ngaydenghi = rs.getString("ngaydenghi");
					this.ghichu = rs.getString("ghichu");
					this.dvkdId = rs.getString("dvkd_fk");
					this.kbhId = rs.getString("kbh_fk");
					this.nppId = rs.getString("npp_fk");
					this.khoNhanId = rs.getString("kho_fk");
					this.loaidonhang = rs.getString("loaidonhang");
					this.chietkhau = format.format(rs.getDouble("SOTIENTHU"));
					this.vat = rs.getString("vat");
					//System.out.println("VAT: " + this.vat);
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("---LOI INIT: " + e.getMessage());
			}
		}
		
		this.createRs();
		
		if(this.loaidonhang.equals("2"))
			this.initSCHEME();
		else if(this.loaidonhang.equals("1"))
			this.initSCHEME_UngHang();
		else if(this.loaidonhang.equals("3"))
			this.initSCHEME_TrungBay();
		else
			this.initSANPHAM();
		
	}

	private void initSCHEME_TrungBay()
	{
		String query =  "select b.PK_SEQ, b.SCHEME, b.DIENGIAI, a.tongluong " +
						"from ERP_DONDATHANG_TRUNGBAY a inner join CTTRUNGBAY b on a.cttb_fk = b.PK_SEQ " +
						"where a.dondathang_fk = '" + this.id + "' ";
		
		ResultSet spRs = db.get(query);
		
		NumberFormat formater = new DecimalFormat("##,###,###");
		this.scheme_soluong = new Hashtable<String, String>();
		
		if(spRs != null)
		{
			try 
			{
				String spID = "";
				String spMA = "";
				String spTEN = "";
				String spSOLUONG = "";
				
				while(spRs.next())
				{
					spID += spRs.getString("PK_SEQ") + "__";
					spMA += spRs.getString("SCHEME") + "__";
					spTEN += spRs.getString("DIENGIAI") + "__";
					spSOLUONG += formater.format(spRs.getDouble("tongluong")) + "__";
					
					if(!this.schemeId.trim().contains(spRs.getString("PK_SEQ")))
						this.schemeId += spRs.getString("PK_SEQ") + "__";
					
					//INIT SP
					query = "select ( select mA from SANPHAM where PK_SEQ = a.sanpham_fk ) as spMA, soluong, cttb_fk  " +
							"from ERP_DONDATHANG_SANPHAM a where DONDATHANG_FK = '" + this.id + "' and cttb_fk = '" + spRs.getString("PK_SEQ") + "'";
					ResultSet rsSP = db.get(query);
					if(rsSP != null)
					{
						while(rsSP.next())
						{
							String _maSPTRA = rsSP.getString("spMA");
							this.scheme_soluong.put(spRs.getString("PK_SEQ") + "__" + _maSPTRA, rsSP.getString("soluong"));
						}
						rsSP.close();
					}
					
				}
				spRs.close();
			
				if(spMA.trim().length() > 0)
				{
					spID = spID.substring(0, spID.length() - 2);
					this.spId = spID.split("__");
					
					spMA = spMA.substring(0, spMA.length() - 2);
					this.spMa = spMA.split("__");
					
					spTEN = spTEN.substring(0, spTEN.length() - 2);
					this.spTen = spTEN.split("__");

					spSOLUONG = spSOLUONG.substring(0, spSOLUONG.length() - 2);
					this.spSoluong = spSOLUONG.split("__");
					
					this.schemeId = this.schemeId.substring(0, this.schemeId.length() - 2);
				}
				
				
			} 
			catch (Exception e) 
			{
				System.out.println("115.Exception: " + e.getMessage());
			}
		}
	}

	private void initSCHEME_UngHang()
	{
		String query =  "select b.PK_SEQ, b.SCHEME, b.DIENGIAI, a.tongluong " +
						"from ERP_DONDATHANG_KMUNGHANG a inner join CTKHUYENMAI b on a.ctkm_fk = b.PK_SEQ " +
						"where a.dondathang_fk = '" + this.id + "' ";
		System.out.println("scheme dh : "+query);
		ResultSet spRs = db.get(query);
		
		NumberFormat formater = new DecimalFormat("##,###,###");
		this.scheme_soluong = new Hashtable<String, String>();
		
		if(spRs != null)
		{
			try 
			{
				String spID = "";
				String spMA = "";
				String spTEN = "";
				String spSOLUONG = "";
				
				while(spRs.next())
				{
					spID += spRs.getString("PK_SEQ") + "__";
					spMA += spRs.getString("SCHEME") + "__";
					spTEN += spRs.getString("DIENGIAI") + "__";
					spSOLUONG += formater.format(spRs.getDouble("tongluong")) + "__";
					
					if(!this.schemeId.trim().contains(spRs.getString("PK_SEQ")))
						this.schemeId += spRs.getString("PK_SEQ") + "__";
					
					//INIT SP
					query = "select ( select mA from SANPHAM where PK_SEQ = a.sanpham_fk ) as spMA, soluong, ctkm_fk  " +
							"from ERP_DONDATHANG_SANPHAM a where DONDATHANG_FK = '" + this.id + "' and ctkm_fk = '" + spRs.getString("PK_SEQ") + "'";
					ResultSet rsSP = db.get(query);
					if(rsSP != null)
					{
						while(rsSP.next())
						{
							String _maSPTRA = rsSP.getString("spMA");
							this.scheme_soluong.put(spRs.getString("PK_SEQ") + "__" + _maSPTRA, rsSP.getString("soluong"));
						}
						rsSP.close();
					}
					
				}
				spRs.close();
			
				if(spMA.trim().length() > 0)
				{
					spID = spID.substring(0, spID.length() - 2);
					this.spId = spID.split("__");
					
					spMA = spMA.substring(0, spMA.length() - 2);
					this.spMa = spMA.split("__");
					
					spTEN = spTEN.substring(0, spTEN.length() - 2);
					this.spTen = spTEN.split("__");

					spSOLUONG = spSOLUONG.substring(0, spSOLUONG.length() - 2);
					this.spSoluong = spSOLUONG.split("__");
					
					this.schemeId = this.schemeId.substring(0, this.schemeId.length() - 2);
				}
				
				
			} 
			catch (Exception e) 
			{
				System.out.println("115.Exception: " + e.getMessage());
			}
		}
	}

	private void initSCHEME()
	{
		String query =  "select b.PK_SEQ, b.SCHEME, b.DIENGIAI, a.tongluong " +
						"from ERP_DONDATHANG_KMTICHLUY a inner join TIEUCHITHUONGTL b on a.ctkm_fk = b.PK_SEQ " +
						"where a.dondathang_fk = '" + this.id + "' ";
		
		ResultSet spRs = db.get(query);
		
		NumberFormat formater = new DecimalFormat("##,###,###");
		this.scheme_soluong = new Hashtable<String, String>();
		
		if(spRs != null)
		{
			try 
			{
				String spID = "";
				String spMA = "";
				String spTEN = "";
				String spSOLUONG = "";
				
				while(spRs.next())
				{
					spID += spRs.getString("PK_SEQ") + "__";
					spMA += spRs.getString("SCHEME") + "__";
					spTEN += spRs.getString("DIENGIAI") + "__";
					spSOLUONG += formater.format(spRs.getDouble("tongluong")) + "__";
					
					if(!this.schemeId.trim().contains(spRs.getString("PK_SEQ")))
						this.schemeId += spRs.getString("PK_SEQ") + "__";
					
					//INIT SP
					query = "select ( select mA from SANPHAM where PK_SEQ = a.sanpham_fk ) as spMA, soluong, ctkm_fk  " +
							"from ERP_DONDATHANG_SANPHAM a where DONDATHANG_FK = '" + this.id + "' and ctkm_fk = '" + spRs.getString("PK_SEQ") + "'";
					ResultSet rsSP = db.get(query);
					if(rsSP != null)
					{
						while(rsSP.next())
						{
							String _maSPTRA = rsSP.getString("spMA");
							this.scheme_soluong.put(spRs.getString("PK_SEQ") + "__" + _maSPTRA, rsSP.getString("soluong"));
						}
						rsSP.close();
					}
					
				}
				spRs.close();
			
				if(spMA.trim().length() > 0)
				{
					spID = spID.substring(0, spID.length() - 2);
					this.spId = spID.split("__");
					
					spMA = spMA.substring(0, spMA.length() - 2);
					this.spMa = spMA.split("__");
					
					spTEN = spTEN.substring(0, spTEN.length() - 2);
					this.spTen = spTEN.split("__");

					spSOLUONG = spSOLUONG.substring(0, spSOLUONG.length() - 2);
					this.spSoluong = spSOLUONG.split("__");
					
					this.schemeId = this.schemeId.substring(0, this.schemeId.length() - 2);
				}
				
				
			} 
			catch (Exception e) 
			{
				System.out.println("115.Exception: " + e.getMessage());
			}
		}
	}

	public void DBclose() {
		
		try{
			
			if(khoNhanRs!=null){
				khoNhanRs.close();
			}
			
			this.db.shutDown();
			
		}catch(Exception er){
			
		}
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getGhichu() {
		
		return this.ghichu;
	}

	public void setGhichu(String ghichu) {
		
		this.ghichu = ghichu;
	}

	
	public String[] getSpId() {
		
		return this.spId;
	}

	
	public void setSpId(String[] spId) {
		
		this.spId = spId;
	}

	
	public String[] getSpMa() {
		
		return this.spMa;
	}

	
	public void setSpMa(String[] spMa) {
		
		this.spMa = spMa;
	}

	
	public String[] getSpTen() {
		
		return this.spTen;
	}

	
	public void setSpTen(String[] spTen) {
		
		this.spTen = spTen;
	}

	
	public String[] getSpDonvi() {
		
		return this.spDonvi;
	}

	
	public void setSpDonvi(String[] spDonvi) {
		
		this.spDonvi = spDonvi;
	}

	
	public String[] getSpSoluong() {
		
		return this.spSoluong;
	}

	
	public void setSpSoluong(String[] spSoluong) {
		
		this.spSoluong = spSoluong;
	}

	
	public String[] getSpGianhap() {
		
		return this.spGianhap;
	}

	
	public void setSpGianhap(String[] spGianhap) {
		
		this.spGianhap = spGianhap;
	}

	public String getNppId() {
		
		return this.nppId;
	}

	
	public void setNppId(String nppId) {
		
		this.nppId = nppId;
	}

	
	public ResultSet getNppRs() {
		
		return this.nppRs;
	}

	
	public void setNppRs(ResultSet nppRs) {
		
		this.nppRs = nppRs;
	}

	
	public String getLoaidonhang() {
		
		return this.loaidonhang;
	}

	
	public void setLoaidonhang(String loaidonhang) {
		
		this.loaidonhang = loaidonhang;
	}

	
	public String getChietkhau() {
		
		return this.chietkhau;
	}

	
	public void setChietkhau(String chietkhau) {
		
		this.chietkhau = chietkhau;
	}

	
	public String getVat() {
		
		return this.vat;
	}

	
	public void setVat(String vat) {
		
		this.vat = vat;
	}

	
	public String getDvkdId() {
		
		return this.dvkdId;
	}

	
	public void setDvkdId(String dvkdId) {
		
		this.dvkdId = dvkdId;
	}

	
	public ResultSet getDvkdRs() {
		
		return this.dvkdRs;
	}

	
	public void setDvkdRs(ResultSet dvkdRs) {
		
		this.dvkdRs = dvkdRs;
	}

	
	public String getKbhId() {
		
		return this.kbhId;
	}

	
	public void setKbhId(String kbhId) {
		
		this.kbhId = kbhId;
	}

	
	public ResultSet getKbhRs() {
		
		return this.kbhRs;
	}

	
	public void setKbhRs(ResultSet kbhRs) {
		
		this.kbhRs = kbhRs;
	}

	public String getNgaydenghi() {
		
		return this.ngaydenghi;
	}

	public void setNgaydenghi(String ngaydenghi) {
		
		this.ngaydenghi = ngaydenghi;
	}

	public ResultSet getDvtRs() {

		return this.dvtRs;
	}


	public void setDvtRs(ResultSet dvtRs) {
		
		this.dvtRs = dvtRs;
	}

	
	public String getSchemeId() {
		
		return this.schemeId;
	}

	
	public void setSchemeId(String kbhId) {
		
		this.schemeId = kbhId;
	}

	
	public ResultSet getSchemeRs() {
		
		return this.schemeRs;
	}

	
	public void setSchemeRs(ResultSet schemeRs) {
		
		this.schemeRs = schemeRs;
	}

	public ResultSet getSpTheoScheme(String scheme, String tongluong) 
	{
		String query =  "select c.MA, c.TEN, d.donvi, '' as soluong " +
						"from TIEUCHITHUONGTL_SPTRA a inner join TIEUCHITHUONGTL b on a.thuongtl_fk = b.PK_SEQ " +
						"	inner join SANPHAM c on a.sanpham_fk = c.PK_SEQ " +
						"	inner join DONVIDOLUONG d on c.DVDL_FK = d.PK_SEQ " +
						"where b.PK_SEQ = N'" + scheme+ "'";
		return db.get(query);
	}

	
	public Hashtable<String, String> getScheme_Soluong() {
	
		return this.scheme_soluong;
	}


	public void setScheme_Soluong(Hashtable<String, String> scheme_soluong) {
		
		this.scheme_soluong = scheme_soluong;
	}

	public ResultSet getSpTheoScheme_UngHang(String scheme, String tongluong)
	{
		/*String query = " select b.MA, b.TEN, d.donvi, sum(a.SOLUONG) as soluong   " +
					   "from DONHANG_CTKM_TRAKM a inner join SANPHAM b on a.SPMA = b.MA 	inner join CTKHUYENMAI c on a.CTKMID = c.PK_SEQ " +
					   "	inner join DONVIDOLUONG d on b.DVDL_FK = d.PK_SEQ " +
					   "where a.CTKMID in (" + scheme + ") and SPMA is not null and a.DONHANGID in ( select pk_seq from DONHANG where trangthai = '1' and NPP_FK = '" + this.nppId + "' )   " +
					   "group by b.MA, b.TEN, d.donvi ";*/
		
		String query =  " select UNGHANG.PK_SEQ, UNGHANG.MA, UNGHANG.TEN, UNGHANG.DONVI, " +
						" 	UNGHANG.soluong - ISNULL(DUYET.SOLUONG, 0) as soluong " +
						" from " +
						" ( " +
						" 	select b.PK_SEQ, b.MA, b.TEN, d.donvi, sum(a.SOLUONG) as soluong   " +
						" 	from DONHANG_CTKM_TRAKM a inner join SANPHAM b on a.SPMA = b.MA 	inner join CTKHUYENMAI c on a.CTKMID = c.PK_SEQ " +
						" 			inner join DONVIDOLUONG d on b.DVDL_FK = d.PK_SEQ  " +
						" 	where a.CTKMID = '" + scheme + "' and SPMA is not null and a.DONHANGID in ( select pk_seq from DONHANG where trangthai = '1' and NPP_FK = '" + this.nppId + "' )  " +
						" 	group by b.PK_SEQ, b.MA, b.TEN, d.donvi " +
						" ) " +
						" UNGHANG left join " +
						" ( " +
						" 	select b.sanpham_fk, SUM(b.soluong) as soluong  " +
						" 	from ERP_DONDATHANG a inner join ERP_DONDATHANG_SANPHAM b on a.PK_SEQ = b.dondathang_fk " +
						" 	where a.LoaiDonHang = '1' and a.TRANGTHAI in (0, 1) and b.ctkm_fk = '" + scheme + "' and a.NPP_FK = '" + this.nppId + "'  " + ( this.id.trim().length() > 0 ? " and a.PK_SEQ != '" + this.id + "' " : "" ) +
						" 	group by b.sanpham_fk " +
						" ) " +
						" DUYET on UNGHANG.PK_SEQ = DUYET.sanpham_fk ";
		
		return db.get(query);
	}

	public ResultSet getSpTheoScheme_TrungBay(String scheme, String tongluong) 
	{
		String query =  "select distinct c.MA, c.TEN, d.donvi, '' as soluong  " +
						"from TRATRUNGBAY_SANPHAM a inner join TRATRUNGBAY b on a.TRATRUNGBAY_FK = b.PK_SEQ  " +
						"	inner join SANPHAM c on a.sanpham_fk = c.PK_SEQ  " +
						"	inner join DONVIDOLUONG d on c.DVDL_FK = d.PK_SEQ   " +
						"where b.PK_SEQ in ( select TRATRUNGBAY_FK from CTTB_TRATB where CTTRUNGBAY_FK = '" + scheme + "' )";
		
		return db.get(query);

	}

	public String[] getSpScheme() {

		return this.spSCheme;
	}


	public void setSpScheme(String[] spScheme) {
		
		this.spSCheme = spScheme;
	}

	public ResultSet getCongnoRs() {

		return this.congnoRs;
	}


	public void setCongnoRs(ResultSet congnoRs) {
		
		this.congnoRs = congnoRs;
	}

	public String[] getSpChietkhau() {
		
		return this.spChietkhau;
	}

	
	public void setSpChietkhau(String[] spChietkhau) {
		
		this.spChietkhau = spChietkhau;
	}

	
	public String[] getDhck_diengiai() {
		
		return this.dhCk_diengiai;
	}

	
	public void setDhck_Diengiai(String[] obj) {
		
		this.dhCk_diengiai = obj;
	}

	
	public String[] getDhck_giatri() {
		
		return this.dhCk_giatri;
	}

	
	public void setDhck_giatri(String[] obj) {
		
		this.dhCk_giatri = obj;
	}

	
	public String[] getDhck_loai() {
		
		return this.dhCk_loai;
	}

	
	public void setDhck_loai(String[] obj) {
		
		this.dhCk_loai = obj;
	}

	
	public String[] getSpTrongluong() {
		
		return this.spTrongluong;
	}

	
	public void setSpTrongluong(String[] spTrongluong) {
		
		this.spTrongluong = spTrongluong;
	}

	
	public String[] getSpThetich() {
		
		return this.spThetich;
	}

	
	public void setSpThetich(String[] spThetich) {
		
		this.spThetich = spThetich;
	}

	public boolean saveDDH(HttpServletRequest request)
	{
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String sotienthu = "0";
			if(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sotienthu")) != null)
				sotienthu = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sotienthu").replaceAll(",", ""));
			this.chietkhau = sotienthu;
			
			String query = "UPDATE ERP_DONDATHANG set SOTIENTHU = '" + sotienthu + "' where pk_seq = '" + this.id + "' ";
			System.out.println("----DUYET: " + query);
			if(!db.update(query))
			{
				this.msg = "Lỗi khi cập nhật ERP_DONDATHANG " + query;
				db.getConnection().rollback();
				return false;
			}

			db.getConnection().commit();
			db.shutDown();
		} 
		catch (Exception e) 
		{
			System.out.println("--LOI DUYET: " + e.getMessage());
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			db.shutDown();
		}
		
		return true;
	}
		
	
	public boolean duyetDDH(HttpServletRequest request)
	{
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			// KIEM TRA MO NHIEU TAB DUYET DON HANG
			String sql1 = "SELECT COUNT(*) AS COUNT FROM ERP_YCXUATKHO_DDH WHERE ddh_fk = '" + this.id + "'";
			ResultSet rs = db.get(sql1);
			rs.next();
			Integer count = rs.getInt("COUNT");
			if(count > 0)
			{
				this.msg = "Đã có yêu cầu xuất kho cho đơn hàng này !";
				db.getConnection().rollback();
				return false;
			}
			else
			{					
				String sotienthu = "0";
				if(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sotienthu")) != null)
					sotienthu = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sotienthu").replaceAll(",", ""));
				this.chietkhau = sotienthu;
				
				if(this.loaidonhang.equals("0"))
				{
					//CHECK XEM CO PHAI DON DOI HANG HAY KHONG?
					String sql = "select NOTE from ERP_DONDATHANG where pk_seq = '" + this.id + "'";
					ResultSet rsCHECK = db.get(sql);
					String note = "";
					if(rsCHECK.next())
					{
						note = rsCHECK.getString("NOTE");
					}
					rsCHECK.close();
					
					if(Double.parseDouble(sotienthu.trim()) <= 0 && !note.contains("Convert từ duyệt đổi hàng") )
					{
						this.msg = "Bạn phải nhập số tiền thu của NPP ";
						db.getConnection().rollback();
						return false;
					}
					
					//CHECK TON KHO NEU DU THI CHO DUYET
					
					
				}
				
				String query = "UPDATE ERP_DONDATHANG set SOTIENTHU = '" + sotienthu + "', trangthai = '2' where pk_seq = '" + this.id + "' ";
				System.out.println("----DUYET: " + query);
				if(!db.update(query))
				{
					this.msg = "Lỗi khi cập nhật ERP_DONDATHANG " + query;
					db.getConnection().rollback();
					return false;
				}
				
				//CHECK TON KHO
				query = "select sp.PK_SEQ, sp.TEN, SUM(dathang.soluong) as soluongXUAT, ISNULL( ( select AVAILABLE from ERP_KHOTT_SANPHAM where khott_fk = '" + this.khoNhanId + "' and sanpham_fk = sp.PK_SEQ ), 0) as tonkho " +
						"from    " +
						"(    " +
						"		select a.sanpham_fk, b.DVDL_FK as dvCHUAN,    " +
						"				case when a.dvdl_fk IS null then a.soluong     " +
						"					 when a.dvdl_fk = b.DVDL_FK then a.soluong    " +
						"					 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )     " +
						"									 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )	 end as soluong, 0 as loai, ' ' as scheme   " +
						"		from ERP_DONDATHANG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ    " +
						"		where a.dondathang_fk in ( '" + this.id + "' )   " +
						"	union ALL  " +
						"		select b.PK_SEQ, b.DVDL_FK as dvCHUAN, a.soluong, 1 as loai, c.SCHEME   " +
						"		from ERP_DONDATHANG_CTKM_TRAKM a inner join SANPHAM b on a.SPMA = b.MA   " +
						"				inner join CTKHUYENMAI c on a.CTKMID = c.PK_SEQ   " +
						"		where a.DONDATHANGID in ( '" + this.id + "' )    " +
						")    " +
						"dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ  " +
						"group by sp.PK_SEQ, sp.TEN " +
						"having  SUM(dathang.soluong) > ISNULL( ( select AVAILABLE from ERP_KHOTT_SANPHAM where khott_fk = '" + this.khoNhanId + "' and sanpham_fk = sp.PK_SEQ ), 0) " ;
				
				System.out.println("--CHECK TON KHO: " + query);
				
				ResultSet rsCHECK = db.get(query);
				if(rsCHECK != null)
				{
					while(rsCHECK.next())
					{
						this.msg = "Sản phẩm ( " + rsCHECK.getString("TEN") + " ) với số lượng yêu cầu ( " + rsCHECK.getString("soluongXUAT") + " ) không đủ tồn kho ( " + rsCHECK.getString("tonkho") + " ). Vui lòng kiểm tra lại.";
						db.getConnection().rollback();
						rsCHECK.close();
						return false;
					}
					rsCHECK.close();
				}
				
				//Tu dong tao YCXK
				query = " insert ERP_YCXUATKHO(NgayYeuCau, ghichu, trangthai, npp_fk, kho_fk, ngaytao, nguoitao, ngaysua, nguoisua) " +
						" values('" + this.ngayyeucau + "', N'" + this.ghichu + "', '0', '" + this.nppId + "', " + this.khoNhanId + ", '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "' )";
				
				System.out.println("1.Insert YCXUATKHO: " + query);
				if(!db.update(query))
				{
					this.msg = "Không thể tạo mới ERP_YCXUATKHO " + query;
					db.getConnection().rollback();
					return false;
				}
							
				query = "SELECT SCOPE_IDENTITY() AS ID";
				ResultSet rsYCXK = this.db.get(query);
				rsYCXK.next();
				String ycxkId = rsYCXK.getString("ID");
				
				query = "Insert ERP_YCXUATKHO_DDH(ycxk_fk, ddh_fk) " +
						"select '"+ ycxkId +"', pk_seq from ERP_DONDATHANG where pk_seq in ( " + this.id + " )  ";
				if(!db.update(query))
				{
					this.msg = "Không thể tạo mới ERP_YCXUATKHO_DDH " + query;
					db.getConnection().rollback();
					return false;
				}
				
				query = "insert ERP_YCXUATKHO_SANPHAM( ycxk_fk, sanpham_fk, soluongDAT, tonkho, daxuat, soluongXUAT, LOAI, SCHEME ) " +
						"select '"+ ycxkId +"', sp.PK_SEQ, SUM(dathang.soluong) as soluongDAT, ISNULL( ( select AVAILABLE from ERP_KHOTT_SANPHAM where khott_fk = '" + this.id + "' and sanpham_fk = sp.PK_SEQ ), 0)  as tonkho, 0, SUM(dathang.soluong) as soluongXUAT, loai, scheme " +
						"from " +
						"( " +
						"		select a.sanpham_fk, b.DVDL_FK as dvCHUAN, " +
						"		case when a.dvdl_fk IS null then a.soluong " +
						"		when a.dvdl_fk = b.DVDL_FK then a.soluong " +
						"		else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk ) " +
						"		/ ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )	 end as soluong, 0 as loai, ' ' as scheme " +
						"		from ERP_DONDATHANG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ " +
						"		where a.dondathang_fk in ( '" + this.id + "' ) " +
						"		union ALL " +
						"		select b.PK_SEQ, b.DVDL_FK as dvCHUAN, a.soluong, 1 as loai, c.SCHEME " +
						"		from ERP_DONDATHANG_CTKM_TRAKM a inner join SANPHAM b on a.SPMA = b.MA " +
						"		inner join CTKHUYENMAI c on a.CTKMID = c.PK_SEQ " +
						"		where a.DONDATHANGID in ( '" + this.id + "' ) " +
						") dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ " +
						"group by sp.PK_SEQ, scheme, loai ";
				
				System.out.println("1.1.Insert YCXK - SP: " + query);
				if(!db.update(query))
				{
					this.msg = "Khong the tao moi ERP_YCXUATKHO_SANPHAM: " + query;
					db.getConnection().rollback();
					return false;
				}
				
				query = "select sp.PK_SEQ, sp.TEN, LOAI, SCHEME, SUM(dathang.soluong) as soluongXUAT " +
						"from  " +
						"( " +
						"	select a.sanpham_fk, b.DVDL_FK as dvCHUAN, " +
						"	case when a.dvdl_fk IS null then a.soluong " +
						"	when a.dvdl_fk = b.DVDL_FK then a.soluong " +
						"	else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk ) " +
						"	/ ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )	 end as soluong, 0 as loai, ' ' as scheme " +
						"	from ERP_DONDATHANG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ " +
						"	where a.dondathang_fk in ( '" + this.id + "' ) " +
						"	union ALL " +
						"	select b.PK_SEQ, b.DVDL_FK as dvCHUAN, a.soluong, 1 as loai, c.SCHEME " +
						"	from ERP_DONDATHANG_CTKM_TRAKM a inner join SANPHAM b on a.SPMA = b.MA " +
						"	inner join CTKHUYENMAI c on a.CTKMID = c.PK_SEQ " +
						"	where a.DONDATHANGID in ( '" + this.id + "' ) " +
						") dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ " +
						"group by sp.PK_SEQ, sp.TEN, LOAI, SCHEME ";
				System.out.println("--CHECK KHO CHI TIET: " + query);
				rsCHECK = db.get(query);
				if(rsCHECK != null)
				{
					while(rsCHECK.next())
					{
						String sanpham_fk = rsCHECK.getString("PK_SEQ");
						String LOAI = rsCHECK.getString("LOAI");
						String SCHEME = rsCHECK.getString("SCHEME");
						double soLUONG = rsCHECK.getDouble("soluongXUAT");
						
						query = "Update ERP_KHOTT_SANPHAM set booked = booked + '" + soLUONG + "', AVAILABLE = AVAILABLE - '" + soLUONG + "' " +
								"where KHOTT_FK = '" + this.khoNhanId + "' and SANPHAM_FK = '" + sanpham_fk + "'  ";
						if(!db.update(query))
						{
							this.msg = "Khong the cap nhat ERP_KHOTT_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						}
						
						//CAP NHAT KHO CHI TIET
						query = "select AVAILABLE, SOLO from ERP_KHOTT_SP_CHITIET " +
								"where KHOTT_FK = '" + this.khoNhanId + "'  and SANPHAM_FK = '" + sanpham_fk + "' and AVAILABLE > 0 order by ngayhethan asc ";
						System.out.println("1.1.CHECK KHOCT: " + query);
						ResultSet rsTK = db.get(query);
						double avai = 0;
						double totalXUAT = 0;
						while(rsTK.next())
						{
							double soluongCT = 0;
							String solo = rsTK.getString("SOLO");
							
							avai = rsTK.getDouble("AVAILABLE");
							totalXUAT += avai;
							
							if(totalXUAT <= soLUONG)
							{
								soluongCT = avai;
								
								query = "insert ERP_YCXUATKHO_SANPHAM_CHITIET( ycxk_fk, SANPHAM_FK, solo, soluong, loai, scheme ) " +
										"select '"+ ycxkId +"', '" + sanpham_fk + "', N'" + solo + "', '" + soluongCT + "', '" + LOAI + "', '" + SCHEME + "' ";
								
								//IDENT_CURRENT('ERP_YCXUATKHO')
								
								System.out.println("1.2.Insert YCXK - SP - CT: " + query);
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi ERP_YCXUATKHO_SANPHAM_CHITIET: " + query;
									db.getConnection().rollback();
									return false;
								}
								
								
								query = "Update ERP_KHOTT_SP_CHITIET set booked = booked + '" + soluongCT + "', AVAILABLE = AVAILABLE - '" + soluongCT + "' " +
										"where KHOTT_FK = '" + this.khoNhanId + "' and SOLO = '" + solo + "' and SANPHAM_FK = '" + sanpham_fk + "'  ";
								if(!db.update(query))
								{
									this.msg = "Khong the cap nhat ERP_KHOTT_SP_CHITIET: " + query;
									db.getConnection().rollback();
									return false;
								}
							}
							else
							{
								soluongCT = soLUONG - ( totalXUAT - avai );
								
								query = "insert ERP_YCXUATKHO_SANPHAM_CHITIET( ycxk_fk, SANPHAM_FK, solo, soluong, loai, scheme ) " +
										"select '"+ ycxkId +"', '" + sanpham_fk + "', N'" + solo + "', '" + soluongCT + "', '" + LOAI + "', '" + SCHEME + "' ";
								
								System.out.println("1.2.Insert YCXK - SP - CT: " + query);
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi ERP_YCXUATKHO_SANPHAM_CHITIET: " + query;
									db.getConnection().rollback();
									return false;
								}
								
								
								query = "Update ERP_KHOTT_SP_CHITIET set booked = booked + '" + soluongCT + "', AVAILABLE = AVAILABLE - '" + soluongCT + "' " +
										"where KHOTT_FK = '" + this.khoNhanId + "' and SOLO = '" + solo + "' and SANPHAM_FK = '" + sanpham_fk + "'  ";
								if(!db.update(query))
								{
									this.msg = "Khong the cap nhat ERP_KHOTT_SP_CHITIET: " + query;
									db.getConnection().rollback();
									return false;
								}
								
								break;
							}
						}
						rsTK.close();
	
					}
					rsCHECK.close();
				}
								
				if(!update_XUATKHO_OLD(ycxkId))
				{
					this.msg = "Khong the cap nhat ERP_KHOTT_SP_CHITIET: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			db.getConnection().commit();
			db.shutDown();
		} 
		catch (Exception e) 
		{
			System.out.println("--LOI DUYET: " + e.getMessage());
			e.printStackTrace();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		
		return true;
	}
	
	
	public boolean update_XUATKHO_OLD(String ddh) 
	{
		try
		{
			//db.getConnection().setAutoCommit(false);
			
			String query = " select pk_seq from ERP_YCXUATKHO where pk_seq not in ( select ycxk_fk from ERP_YCXUATKHO_DDH_SANPHAM ) and pk_seq = '"+ ddh +"' order by ngayyeucau asc ";
			System.out.println("--CHAY HOA DON: " + query);
			ResultSet rsYCXK = db.get(query);
			while(rsYCXK.next())
			{
				String ycxkID = rsYCXK.getString("pk_seq");
				query = "select sanpham_fk, soluongDAT, soluongXUAT, loai, SCHEME from ERP_YCXUATKHO_SANPHAM where ycxk_fk = '" + ycxkID + "'";
				ResultSet rsYC_SP = db.get(query);
				
				if(rsYC_SP != null)
				{
					while(rsYC_SP.next())
					{
						String spID = rsYC_SP.getString("sanpham_fk");
						double soluongXUATKHO = rsYC_SP.getDouble("soluongXUAT");
						double soluongDAT = rsYC_SP.getDouble("soluongDAT");
						String LOAI = rsYC_SP.getString("loai");
						String SCHEME = rsYC_SP.getString("SCHEME");
						
						System.out.println("---DANG CHAY: " + ycxkID );
						
						if( spID.trim().length() > 0 && soluongXUATKHO > 0 )
						{
							query = "select dathang.dondathang_fk, dathang.sanpham_fk, dathang.scheme, dathang.loai, dathang.soluongDAT - isnull( daxuat.soluongDAXUAT, 0 ) as soluong  " +
									"from " +
									"( " +
									"	select dathang.dondathang_fk, sp.PK_SEQ as sanpham_fk, scheme, loai, SUM(dathang.soluong) as soluongDAT " +
									"	from " +
									"	( " +
									"		select a.dondathang_fk, a.sanpham_fk, b.DVDL_FK as dvCHUAN,    " +
									"		case when a.dvdl_fk IS null then a.soluong     " +
									"		when a.dvdl_fk = b.DVDL_FK then a.soluong    " +
									"		else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )     " +
									"		/ ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk ) end as soluong, 0 as loai, '' as scheme " +
									"		from ERP_DONDATHANG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ    " +
									"		where a.dondathang_fk in ( select ddh_fk from ERP_YCXUATKHO_DDH where ycxk_fk = '" + ycxkID + "' ) " +
									"		union ALL  " +
									"		select a.dondathangId, b.PK_SEQ, b.DVDL_FK as dvCHUAN, a.soluong, 1 as loai, c.SCHEME " +
									"		from ERP_DONDATHANG_CTKM_TRAKM a inner join SANPHAM b on a.SPMA = b.MA " +
									"		inner join CTKHUYENMAI c on a.CTKMID = c.PK_SEQ " +
									"		where a.DONDATHANGID in ( select ddh_fk from ERP_YCXUATKHO_DDH where ycxk_fk = '" + ycxkID + "' ) " +
									"	) dathang " +
									"	inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ " +
									"	inner join DONVIDOLUONG dv on sp.DVDL_FK = dv.PK_SEQ " +
									"	group by dathang.dondathang_fk, sp.PK_SEQ, scheme, loai " +
									") dathang " +
									"left join " +
									"( " +
									"	select b.ddh_fk, b.sanpham_fk, b.LOAI, isnull(b.SCHEME, '') as SCHEME, SUM( b.soluongXUAT ) as soluongDAXUAT    " +
									"	from ERP_YCXUATKHO a inner join ERP_YCXUATKHO_DDH_SANPHAM b on a.PK_SEQ = b.ycxk_fk   " +
									"	where a.TRANGTHAI != 3 and b.DDH_FK in ( select ddh_fk from ERP_YCXUATKHO_DDH where ycxk_fk = '" + ycxkID + "'  ) " +
									"	and a.PK_SEQ in ( select ycxk_fk from  ERP_YCXUATKHO_DDH where ddh_fk in (  select ddh_fk from ERP_YCXUATKHO_DDH where ycxk_fk = '" + ycxkID + "' ) )   " +
									"	group by b.ddh_fk, b.sanpham_fk, b.LOAI, b.SCHEME  " +
									") daxuat on dathang.dondathang_fk = daxuat.ddh_fk and dathang.sanpham_fk = daxuat.sanpham_fk " +
									" and dathang.loai = daxuat.loai and dathang.scheme = daxuat.scheme " +
									"inner join ERP_DONDATHANG ddh on dathang.dondathang_fk = ddh.pk_seq " +
									"where dathang.loai = '" + LOAI + "' and dathang.scheme = N'" + SCHEME + "' and dathang.sanpham_fk = '"+ spID +"' " +
									"order by ngaydonhang desc, trangthai asc, pk_seq asc " ;
							
							ResultSet rsDEXUAT = db.get(query);
							if(rsDEXUAT != null )
							{
								double totalXUAT = soluongXUATKHO;
								while(rsDEXUAT.next())
								{
									String ddh_fk = rsDEXUAT.getString("dondathang_fk");
									double soluong = rsDEXUAT.getDouble("soluong");
									double soluongXUAT = 0;
									
									if(totalXUAT > soluong)
									{
										soluongXUAT = soluong;
										totalXUAT -= soluongXUAT;
									}
									else
									{
										if(totalXUAT > 0)
										{	
											soluongXUAT = totalXUAT;
											totalXUAT = 0;
										}
									}
									
									if(soluongXUAT > 0)
									{
										query = "insert ERP_YCXUATKHO_DDH_SANPHAM( ycxk_fk, sanpham_fk, ddh_fk, soluongXUAT_TONG, soluongXUAT, LOAI, SCHEME ) " +
												"select '" + ycxkID + "', '" + spID + "', '" + ddh_fk + "', '" + soluongDAT + "', '" + soluongXUAT + "', '" + LOAI + "', N'" + SCHEME + "' ";
										
										db.update(query);
										
										/*System.out.println("1.1.Insert ERP_YCXUATKHO_DDH_SANPHAM: " + query);
										if(!db.update(query))
										{
											this.msg = "Khong the tao moi ERP_YCXUATKHO_DDH_SANPHAM: " + query;
											db.getConnection().rollback();
											return false;
										}*/
									}
								}
								rsDEXUAT.close();
							}
						}
						
					}
					rsYC_SP.close();
				}
				
			}
			
			System.out.println("------CHAY OK ROI............");
			//db.getConnection().commit();
			//db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			//geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.msg = "Exception: " + e.getMessage();
			return false;
		}
		
		return true;

	}
	
	
}

