package geso.dms.distributor.beans.dondathang.imp;

import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.dondathang.IErpDenghidathangNpp;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;


public class ErpDenghidathangNpp implements IErpDenghidathangNpp
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
	
	
	String nppId;
	
	
	String nppTen;
	String sitecode;
	
	String dvkdId;
	
	
	String kbhId;
	ResultSet khoNhanRs;
	ResultSet kbhRs;
	ResultSet nppRs;
	ResultSet dvtRs;
	ResultSet dvkdRs;
	String schemeId;
	ResultSet schemeRs;
	ResultSet congnoRs;
	ResultSet sanphamRs;
	Hashtable<String, String> sp_soluong;

	
	
	String tsdh;
	
	dbutils db;
	
	public ErpDenghidathangNpp()
	{
		this.id = "";
		this.ngayyeucau = getDateTime();
		this.ngaydenghi = getDateTime();
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
		this.tsdh = "6";
		
		this.sp_soluong = new Hashtable<String, String>();
		
		this.db = new dbutils();
	}
	
	public ErpDenghidathangNpp(String id)
	{
		this.id = id;
		this.ngayyeucau = getDateTime();
		this.ngaydenghi = getDateTime();
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
		this.tsdh = "1";

		this.sp_soluong = new Hashtable<String, String>();
		
		this.db = new dbutils();
	}

	public dbutils getDb() {
		return db;
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

	
	/*public boolean createNK() 
	{
		if(this.ngaydenghi.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày đề nghị";
			return false;
		}

		if( this.dvkdId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đơn vị kinh doanh";
			return false;
		}
		
		if( this.kbhId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kênh bán hàng";
			return false;
		}
		
		if( this.nppId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn nhà phân phối đặt hàng";
			return false;
		}
		
		if(this.sp_soluong == null)
		{
			this.msg = "Vui lòng nhập sản phẩm đề nghị";
			return false;
		}
		else
		{
			if(this.sp_soluong.size() <= 0)
			{
				this.msg = "Vui lòng nhập sản phẩm đề nghị";
				return false;
			}
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
			String chietkhau = this.chietkhau.trim().length() <= 0 ? "0" : this.chietkhau.trim();
			String vat = this.vat.trim().length() <= 0 ? "0" : this.vat.trim();
			
			String query = " insert ERP_DeNghiDatHang(ngaydenghi, TanSuatDatHang, ghichu, trangthai, dvkd_fk, kbh_fk, npp_fk, kho_fk, chietkhau, vat, ngaytao, nguoitao, ngaysua, nguoisua) " +
						   " values('" + this.ngaydenghi + "', '" + this.tsdh + "', N'" + this.ghichu + "', '0', '" + dvkdId + "', '" + kbhId + "', '" + nppId + "', " + khonhan_fk + ", '" + chietkhau + "', '" + vat + "', '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "' )";
			
			System.out.println("1.Insert DDH: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_DeNghiDatHang " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//LAY ID
			ResultSet rsDDH = db.get("select IDENT_CURRENT('ERP_DeNghiDatHang') as ID ");
			if(rsDDH.next())
			{
				this.id = rsDDH.getString("ID");
			}
			rsDDH.close();
			
			
			query = "insert ERP_DENGHIDATHANG_SANPHAM(denghidathang_fk, sanpham_fk, dvdl_fk, quydoi, tbban, tontoithieu, tonkho, denghi, dongia) " +
					" select '" + this.id + "', a.PK_SEQ, '100018', isnull(BGIA.quydoi, 1) as quydoi, ISNULL(TBBAN.tbBAN, 0) as tbBAN, isnull(a.tontoithieu, 0) as tontoithieu,   " +
					" 	( select SUM(AVAILABLE) from NHAPP_KHO where NPP_FK = '" + this.nppId + "' and SANPHAM_FK = a.PK_SEQ ) as tonKHO, 0 as denghi, " +
					" 	BGIA.giamua * BGIA.quydoi as donGIA " +
					" from SANPHAM a inner join DONVIDOLUONG b on a.DVDL_FK = b.PK_SEQ " +
					" left join " +
					" ( " +
					" 	select b.SANPHAM_FK, SUM(b.soluong) / ( DATEDIFF(dd, DATEADD(MM, -1, dbo.GetLocalDate(DEFAULT)), dbo.GetLocalDate(DEFAULT) ) ) as tbBAN " +
					" 	from DONHANG a inner join DONHANG_SANPHAM b on a.PK_SEQ = b.DONHANG_FK " +
					" 	where a.TRANGTHAI = '1' and a.NPP_FK = '" + this.nppId + "' and a.NGAYNHAP >= CONVERT(varchar(10), DATEADD(MM, -1, dbo.GetLocalDate(DEFAULT)), 120) " +
					" 			and a.NGAYNHAP <= CONVERT(varchar(10), dbo.GetLocalDate(DEFAULT), 120) " +
					" 	group by b.SANPHAM_FK " +
					" ) " +
					" TBBAN on a.PK_SEQ = TBBAN.SANPHAM_FK left join " +
					" ( " +
					" 	select a.PK_SEQ, a.DVDL_FK as dvCHUAN,   " +
					" 		  isnull( ( select SOLUONG1 / SOLUONG2 from QUYCACH where SANPHAM_FK = a.pk_seq and DVDL1_FK = a.DVDL_FK ), 0 ) as quydoi,  " +
					"   		  isnull( ( select GIAMUA_SAUCK from BGMUANPP_SANPHAM  " +
					"  					where SANPHAM_FK = a.pk_seq  " +
					"  						and BGMUANPP_FK in ( select top(1) PK_SEQ from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = '" + this.nppId + "' and bg.DVKD_FK = '" + this.dvkdId + "' and bg.KENH_FK = '" + this.kbhId + "' order by bg.TUNGAY desc ) ), 0) as giamua   " +
					" 	from SANPHAM a  " +
					" ) " +
					" BGIA on a.PK_SEQ = BGIA.PK_SEQ ";
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_DENGHIDATHANG_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if(this.sp_soluong.size() > 0)
			{
				Enumeration<String> keys = this.sp_soluong.keys();
				while(keys.hasMoreElements())
				{
					String key = keys.nextElement();
					
					query = "Update ERP_DENGHIDATHANG_SANPHAM set denghi = '" + this.sp_soluong.get(key).replaceAll(",", "") + "' " +
							"where denghidathang_fk = '" + this.id + "' and sanpham_fk = ( select pk_seq from SANPHAM where ma = '" + key + "' ) ";
					if(!db.update(query))
					{
						this.msg = "Không thể tạo mới ERP_DENGHIDATHANG_SANPHAM " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.msg = "Exception: " + e.getMessage();
			return false;
		}
		
		return true;
	}*/
	
	public boolean createNK() 
	{
		if(this.ngaydenghi.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày đề nghị";
			return false;
		}

		if( this.dvkdId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đơn vị kinh doanh";
			return false;
		}
		
		if( this.kbhId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kênh bán hàng";
			return false;
		}
		
		if( this.nppId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn nhà phân phối đặt hàng";
			return false;
		}
		
		if(this.sp_soluong == null)
		{
			this.msg = "Vui lòng nhập sản phẩm đề nghị";
			return false;
		}
		else
		{
			if(this.sp_soluong.size() <= 0)
			{
				this.msg = "Vui lòng nhập sản phẩm đề nghị";
				return false;
			}
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
			String chietkhau = this.chietkhau.trim().length() <= 0 ? "0" : this.chietkhau.trim();
			String vat = this.vat.trim().length() <= 0 ? "0" : this.vat.trim();
			
			
			List<Object> data = new ArrayList<Object>();
			
			String query = " insert ERP_DeNghiDatHang(ngaydenghi, TanSuatDatHang, ghichu, trangthai, dvkd_fk, kbh_fk, npp_fk, kho_fk, chietkhau, vat, ngaytao, nguoitao, ngaysua, nguoisua) " +
						   " values(?, ?, ?, '0', ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
			
			data.clear();
			data.add(this.ngaydenghi);
			data.add(this.tsdh);
			data.add(this.ghichu);
			data.add(this.dvkdId);
			data.add(this.kbhId);
			data.add(this.nppId);
			data.add(khonhan_fk);
			data.add(chietkhau);
			data.add(vat);
			data.add(getDateTime());
			data.add(this.userId);
			data.add(getDateTime());
			data.add(this.userId);
			
			
			//System.out.println("1.Insert DDH: " + query);
			dbutils.viewQuery(query, data);
			if(this.db.updateQueryByPreparedStatement(query, data) < 1)
			{
				this.msg = "Không thể tạo mới de nghi dat hang ";
				db.getConnection().rollback();
				return false;
			}
			
			//LAY ID
			/*ResultSet rsDDH = db.get("select IDENT_CURRENT('ERP_DeNghiDatHang') as ID ");
			if(rsDDH.next())
			{
				this.id = rsDDH.getString("ID");
			}
			rsDDH.close();*/
			
			this.id = db.getPk_seq();
			
			
			
			query = "insert ERP_DENGHIDATHANG_SANPHAM(denghidathang_fk, sanpham_fk, dvdl_fk, quydoi, tbban, tontoithieu, tonkho, denghi, dongia) " +
					" select ?, a.PK_SEQ, '100018', isnull(BGIA.quydoi, 1) as quydoi, ISNULL(TBBAN.tbBAN, 0) as tbBAN, isnull(a.tontoithieu, 0) as tontoithieu,   " +
					" 	( select SUM(AVAILABLE) from NHAPP_KHO where NPP_FK = ? and SANPHAM_FK = a.PK_SEQ ) as tonKHO, 0 as denghi, " +
					" 	BGIA.giamua * BGIA.quydoi as donGIA " +
					" from SANPHAM a inner join DONVIDOLUONG b on a.DVDL_FK = b.PK_SEQ " +
					" left join " +
					" ( " +
					" 	select b.SANPHAM_FK, SUM(b.soluong) / ( DATEDIFF(dd, DATEADD(MM, -1, dbo.GetLocalDate(DEFAULT)), dbo.GetLocalDate(DEFAULT) ) ) as tbBAN " +
					" 	from DONHANG a inner join DONHANG_SANPHAM b on a.PK_SEQ = b.DONHANG_FK " +
					" 	where a.TRANGTHAI = '1' and a.NPP_FK = ? and a.NGAYNHAP >= CONVERT(varchar(10), DATEADD(MM, -1, dbo.GetLocalDate(DEFAULT)), 120) " +
					" 			and a.NGAYNHAP <= CONVERT(varchar(10), dbo.GetLocalDate(DEFAULT), 120) " +
					" 	group by b.SANPHAM_FK " +
					" ) " +
					" TBBAN on a.PK_SEQ = TBBAN.SANPHAM_FK left join " +
					" ( " +
					" 	select a.PK_SEQ, a.DVDL_FK as dvCHUAN,   " +
					//" 		  isnull( ( select SOLUONG1 / SOLUONG2 from QUYCACH where SANPHAM_FK = a.pk_seq and DVDL1_FK = a.DVDL_FK ), 0 ) as quydoi,  " +
					" 		  1 as quydoi,  " + // không có đơn vị quy đổi nên cho mặc định
					"   		  isnull( ( select GIAMUA_SAUCK from BGMUANPP_SANPHAM  " +
					"  					where SANPHAM_FK = a.pk_seq  " +
					"  						and BGMUANPP_FK in ( select top(1) PK_SEQ from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = ? and bg.DVKD_FK = ? and bg.KENH_FK = ? order by bg.TUNGAY desc ) ), 0) as giamua   " +
					" 	from SANPHAM a  " +
					" ) " +
					" BGIA on a.PK_SEQ = BGIA.PK_SEQ ";
			
			data.clear();
			data.add(this.id);
			data.add(this.nppId);data.add(this.nppId);data.add(this.nppId); 
			data.add(this.dvkdId); data.add(this.kbhId);
			
			dbutils.viewQuery(query, data);
			if(this.db.updateQueryByPreparedStatement(query, data) < 1)
			{
				this.msg = "Không thể tạo mới san pham ";
				db.getConnection().rollback();
				return false;
			}
			
			/*if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_DENGHIDATHANG_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}*/
			
			if(this.sp_soluong.size() > 0)
			{
				Enumeration<String> keys = this.sp_soluong.keys();
				while(keys.hasMoreElements())
				{
					String key = keys.nextElement();
					data.clear();
					
					query = "Update ERP_DENGHIDATHANG_SANPHAM set denghi = ? " +
							"where denghidathang_fk = ? and sanpham_fk = ( select pk_seq from SANPHAM where ma = ? ) ";
					
					data.add(this.sp_soluong.get(key).replaceAll(",", ""));
					data.add(this.id);
					data.add(key);
					
					if(this.db.updateQueryByPreparedStatement(query, data) < 1)
					{
						this.msg = "Không thể cập nhật san pham ";
						db.getConnection().rollback();
						return false;
					}
					
					/*if(!db.update(query))
					{
						this.msg = "Không thể tạo mới ERP_DENGHIDATHANG_SANPHAM " + query;
						db.getConnection().rollback();
						return false;
					}*/
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.msg = "Exception: " + e.getMessage();
			return false;
		}
		
		return true;
	}
	
	public boolean updateNK() 
	{
		if(this.ngayyeucau.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày nhập kho";
			return false;
		}
		
		if(this.ngaydenghi.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày đề nghị giao hàng";
			return false;
		}

		if( this.dvkdId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đơn vị kinh doanh";
			return false;
		}
		
		if( this.kbhId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kênh bán hàng";
			return false;
		}
		
		if( this.nppId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn nhà phân phối đặt hàng";
			return false;
		}
		
		if( this.khoNhanId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kho đặt hàng";
			return false;
		}
		
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			List<Object> data = new ArrayList<Object>();
			
			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
			String chietkhau = this.chietkhau.trim().length() <= 0 ? "0" : this.chietkhau.trim();
			String vat = this.vat.trim().length() <= 0 ? "0" : this.vat.trim();
			
			String query =	" Update ERP_DENGHIDATHANG set ngaydenghi = ?, ghichu = ?, " +
							" 	dvkd_fk = ?, kbh_fk = ?, npp_fk = ?, kho_fk = ?, ngaysua = ?, nguoisua = ?, chietkhau = ?, vat = ?, tansuatdathang = ? " + 
							" where pk_seq = ? ";
			
			data.clear();
			data.add(this.ngaydenghi); data.add(this.ghichu); data.add(this.dvkdId); data.add(this.kbhId);
			data.add(this.nppId); data.add(khonhan_fk); data.add(getDateTime()); data.add(this.userId);
			data.add(chietkhau); data.add(vat); data.add(tsdh); data.add(this.id);
		
			if(this.db.updateQueryByPreparedStatement(query, data) < 1)
			{
				this.msg = "Không thể cập nhật de nghi dat hang ";
				db.getConnection().rollback();
				return false;
			}
			
			/*if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_DENGHIDATHANG " + query;
				db.getConnection().rollback();
				return false;
			}*/
						
			query = "delete ERP_DENGHIDATHANG_SANPHAM where denghidathang_fk = ?";
			
			data.clear();
			data.add(this.id);
			
			if(this.db.updateQueryByPreparedStatement(query, data) < 1)
			{
				this.msg = "Không thể cập nhật san pham ";
				db.getConnection().rollback();
				return false;
			}
			
			/*if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_DENGHIDATHANG_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}*/
			
			
			query = "insert ERP_DENGHIDATHANG_SANPHAM(denghidathang_fk, sanpham_fk, dvdl_fk, quydoi, tbban, tontoithieu, tonkho, denghi, dongia) " +
					" select ?, a.PK_SEQ, '100018', isnull(BGIA.quydoi, 1) as quydoi, ISNULL(TBBAN.tbBAN, 0) as tbBAN, isnull(a.tontoithieu, 0) as tontoithieu,   " +
					" 	( select SUM(AVAILABLE) from NHAPP_KHO where NPP_FK = ? and SANPHAM_FK = a.PK_SEQ ) as tonKHO, 0 as denghi, " +
					" 	BGIA.giamua * BGIA.quydoi as donGIA " +
					" from SANPHAM a inner join DONVIDOLUONG b on a.DVDL_FK = b.PK_SEQ " +
					" left join " +
					" ( " +
					" 	select b.SANPHAM_FK, SUM(b.soluong) / ( DATEDIFF(dd, DATEADD(MM, -1, dbo.GetLocalDate(DEFAULT)), dbo.GetLocalDate(DEFAULT) ) ) as tbBAN " +
					" 	from DONHANG a inner join DONHANG_SANPHAM b on a.PK_SEQ = b.DONHANG_FK " +
					" 	where a.TRANGTHAI = '1' and a.NPP_FK = ? and a.NGAYNHAP >= CONVERT(varchar(10), DATEADD(MM, -1, dbo.GetLocalDate(DEFAULT)), 120) " +
					" 			and a.NGAYNHAP <= CONVERT(varchar(10), dbo.GetLocalDate(DEFAULT), 120) " +
					" 	group by b.SANPHAM_FK " +
					" ) " +
					" TBBAN on a.PK_SEQ = TBBAN.SANPHAM_FK left join " +
					" ( " +
					" 	select a.PK_SEQ, a.DVDL_FK as dvCHUAN,   " +
					//" 		  isnull( ( select SOLUONG1 / SOLUONG2 from QUYCACH where SANPHAM_FK = a.pk_seq and DVDL1_FK = a.DVDL_FK ), 0 ) as quydoi,  " +
					"   		  1 as quydoi ,isnull( ( select GIAMUA_SAUCK from BGMUANPP_SANPHAM  " +
					"  					where SANPHAM_FK = a.pk_seq  " +
					"  						and BGMUANPP_FK in ( select top(1) PK_SEQ from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = ? and bg.DVKD_FK = ? and bg.KENH_FK = ? order by bg.TUNGAY desc ) ), 0) as giamua   " +
					" 	from SANPHAM a  " +
					" ) " +
					" BGIA on a.PK_SEQ = BGIA.PK_SEQ ";
			
			data.clear();
			data.add(this.id); data.add(this.nppId); data.add(this.nppId);
			data.add(this.nppId); data.add(this.dvkdId); data.add(this.kbhId);
			
			if(this.db.updateQueryByPreparedStatement(query, data) < 1)
			{
				this.msg = "Không thể tạo mới san pham ";
				db.getConnection().rollback();
				return false;
			}
			
			/*if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_DENGHIDATHANG_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}*/
			
			if(this.sp_soluong.size() > 0)
			{
				Enumeration<String> keys = this.sp_soluong.keys();
				while(keys.hasMoreElements())
				{
					String key = keys.nextElement();
					
					query = "Update ERP_DENGHIDATHANG_SANPHAM set denghi = ? " +
							"where denghidathang_fk = ? and sanpham_fk = ( select pk_seq from SANPHAM where ma = ? ) ";
					
					data.clear();
					data.add(this.sp_soluong.get(key).replaceAll(",", ""));
					data.add(this.id);
					data.add(key);
					
					if(this.db.updateQueryByPreparedStatement(query, data) < 1)
					{
						this.msg = "Không thể tạo mới san pham ";
						db.getConnection().rollback();
						return false;
					}
					
					/*if(!db.update(query))
					{
						this.msg = "Không thể tạo mới ERP_DENGHIDATHANG_SANPHAM " + query;
						db.getConnection().rollback();
						return false;
					}*/
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.msg = "Exception: " + e.getMessage();
			return false;
		}
		
		return true;
	}

	/*public boolean updateNK() 
	{
		if(this.ngayyeucau.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày nhập kho";
			return false;
		}
		
		if(this.ngaydenghi.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày đề nghị giao hàng";
			return false;
		}

		if( this.dvkdId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đơn vị kinh doanh";
			return false;
		}
		
		if( this.kbhId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kênh bán hàng";
			return false;
		}
		
		if( this.nppId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn nhà phân phối đặt hàng";
			return false;
		}
		
		if( this.khoNhanId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kho đặt hàng";
			return false;
		}
		
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
			String chietkhau = this.chietkhau.trim().length() <= 0 ? "0" : this.chietkhau.trim();
			String vat = this.vat.trim().length() <= 0 ? "0" : this.vat.trim();
			
			String query =	" Update ERP_DENGHIDATHANG set ngaydenghi = '" + this.ngaydenghi + "', ghichu = N'" + this.ghichu + "', " +
							" 	dvkd_fk = '" + this.dvkdId + "', kbh_fk = '" + this.kbhId + "', npp_fk = '" + this.nppId + "', kho_fk = " + khonhan_fk + ", ngaysua = '" + getDateTime() + "', nguoisua = '" + this.userId + "', chietkhau = '" + chietkhau + "', vat = '" + vat + "', tansuatdathang = '" + tsdh + "' " + 
							" where pk_seq = '" + this.id + "' ";
		
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_DENGHIDATHANG " + query;
				db.getConnection().rollback();
				return false;
			}
						
			query = "delete ERP_DENGHIDATHANG_SANPHAM where denghidathang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_DENGHIDATHANG_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "insert ERP_DENGHIDATHANG_SANPHAM(denghidathang_fk, sanpham_fk, dvdl_fk, quydoi, tbban, tontoithieu, tonkho, denghi, dongia) " +
					" select '" + this.id + "', a.PK_SEQ, '100018', isnull(BGIA.quydoi, 1) as quydoi, ISNULL(TBBAN.tbBAN, 0) as tbBAN, isnull(a.tontoithieu, 0) as tontoithieu,   " +
					" 	( select SUM(AVAILABLE) from NHAPP_KHO where NPP_FK = '" + this.nppId + "' and SANPHAM_FK = a.PK_SEQ ) as tonKHO, 0 as denghi, " +
					" 	BGIA.giamua * BGIA.quydoi as donGIA " +
					" from SANPHAM a inner join DONVIDOLUONG b on a.DVDL_FK = b.PK_SEQ " +
					" left join " +
					" ( " +
					" 	select b.SANPHAM_FK, SUM(b.soluong) / ( DATEDIFF(dd, DATEADD(MM, -1, dbo.GetLocalDate(DEFAULT)), dbo.GetLocalDate(DEFAULT) ) ) as tbBAN " +
					" 	from DONHANG a inner join DONHANG_SANPHAM b on a.PK_SEQ = b.DONHANG_FK " +
					" 	where a.TRANGTHAI = '1' and a.NPP_FK = '" + this.nppId + "' and a.NGAYNHAP >= CONVERT(varchar(10), DATEADD(MM, -1, dbo.GetLocalDate(DEFAULT)), 120) " +
					" 			and a.NGAYNHAP <= CONVERT(varchar(10), dbo.GetLocalDate(DEFAULT), 120) " +
					" 	group by b.SANPHAM_FK " +
					" ) " +
					" TBBAN on a.PK_SEQ = TBBAN.SANPHAM_FK left join " +
					" ( " +
					" 	select a.PK_SEQ, a.DVDL_FK as dvCHUAN,   " +
					" 		  isnull( ( select SOLUONG1 / SOLUONG2 from QUYCACH where SANPHAM_FK = a.pk_seq and DVDL1_FK = a.DVDL_FK ), 0 ) as quydoi,  " +
					"   		  isnull( ( select GIAMUA_SAUCK from BGMUANPP_SANPHAM  " +
					"  					where SANPHAM_FK = a.pk_seq  " +
					"  						and BGMUANPP_FK in ( select top(1) PK_SEQ from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = '" + this.nppId + "' and bg.DVKD_FK = '" + this.dvkdId + "' and bg.KENH_FK = '" + this.kbhId + "' order by bg.TUNGAY desc ) ), 0) as giamua   " +
					" 	from SANPHAM a  " +
					" ) " +
					" BGIA on a.PK_SEQ = BGIA.PK_SEQ ";
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_DENGHIDATHANG_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if(this.sp_soluong.size() > 0)
			{
				Enumeration<String> keys = this.sp_soluong.keys();
				while(keys.hasMoreElements())
				{
					String key = keys.nextElement();
					
					query = "Update ERP_DENGHIDATHANG_SANPHAM set denghi = '" + this.sp_soluong.get(key).replaceAll(",", "") + "' " +
							"where denghidathang_fk = '" + this.id + "' and sanpham_fk = ( select pk_seq from SANPHAM where ma = '" + key + "' ) ";
					if(!db.update(query))
					{
						this.msg = "Không thể tạo mới ERP_DENGHIDATHANG_SANPHAM " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.msg = "Exception: " + e.getMessage();
			return false;
		}
		
		return true;
	}*/
	
	public void createRs() 
	{
		this.getNppInfo();
		
		String loainpp = "", kho = "khott";
		String query = "select loainpp from NHAPHANPHOI where PK_SEQ=?";
		ResultSet rs = db.getByPreparedStatement(query, new Object[]{this.nppId});
		if(rs != null)
		{
			try
			{
				rs.next();
				loainpp = rs.getString("loainpp");
				if(loainpp.trim().equals("6"))
					kho = "kho";
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		query = "select PK_SEQ, TEN from "+ kho +" where trangthai = '1'  ";
		this.khoNhanRs = db.get(query);
		
		this.dvtRs = db.getScrol("select PK_SEQ, DONVI from DONVIDOLUONG where trangthai = '1' ");
		
		this.dvkdRs = db.get("select PK_SEQ, DONVIKINHDOANH as TEN from DONVIKINHDOANH where TRANGTHAI = '1' ");
		query = "select PK_SEQ, DIENGIAI as TEN from KENHBANHANG where TRANGTHAI = '1' and PK_SEQ in ( select KBH_FK from NHAPP_KBH where NPP_FK = ? ) ";
		this.kbhRs = db.getByPreparedStatement(query, new Object[]{this.nppId});
		
		query = "select PK_SEQ, MA + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1' and pk_seq = ? ";
		this.nppRs = db.getByPreparedStatement(query, new Object[] {this.nppId});
		
		if(this.nppId.trim().length() > 0)
		{
			query = "select a.PK_SEQ as nppId, d.DVKD_FK, b.KBH_FK  " +
				    "From NhaPhanPhoi a left join nhapp_kbh b on b.NPP_FK = a.PK_SEQ left join NHAPP_NHACC_DONVIKD c on c.NPP_FK = b.NPP_FK  " +
				    "	left join NHACUNGCAP_DVKD d on d.PK_SEQ = c.NCC_DVKD_FK   " +
				    "where a.pk_Seq = ? ";
			ResultSet rsInfo = db.getByPreparedStatement(query, new Object[]{this.nppId});
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
			
			/*if(this.kbhId==null){
				this.kbhId = "null";
			}else { this.kbhId = "'"  + this.kbhId + "'";}
			if(this.dvkdId==null){
				this.dvkdId = "null";
			}else { this.dvkdId = "'" + this.dvkdId + "'";}*/
			
			//INIT SAN PHAM
			query = " select a.PK_SEQ, a.MA, a.TEN, isnull(a.tontoithieu, 0) as tontoithieu, isnull(BGIA.quydoi, 1) as quydoi, b.DONVI,  " +
					" 	( select SUM(AVAILABLE) from NHAPP_KHO where NPP_FK = ? and SANPHAM_FK = a.PK_SEQ ) as tonKHO, " +
					" 	ISNULL(TBBAN.tbBAN, 0) as tbBAN, BGIA.giamua * BGIA.quydoi as donGIA, TBBAN.PTTANGTRUONG " +
					" from SANPHAM a inner join DONVIDOLUONG b on a.DVDL_FK = b.PK_SEQ " +
					" left join " +
					" ( " +
					" 	select b.SANPHAM_FK, SUM(b.soluong) / ( DATEDIFF(dd, DATEADD(MM, -3, dbo.GetLocalDate(DEFAULT)), dbo.GetLocalDate(DEFAULT) ) ) as tbBAN, c.PTTANGTRUONG " +
					" 	from DONHANG a inner join DONHANG_SANPHAM b on a.PK_SEQ = b.DONHANG_FK "+
					"	inner join NHAPHANPHOI c on a.NPP_FK = c.PK_SEQ " +
					" 	where a.TRANGTHAI = '1' and a.NPP_FK = ? and a.NGAYNHAP >= CONVERT(varchar(10), DATEADD(MM, -1, dbo.GetLocalDate(DEFAULT)), 120) " +
					" 			and a.NGAYNHAP <= CONVERT(varchar(10), dbo.GetLocalDate(DEFAULT), 120) " +
					" 	group by b.SANPHAM_FK, c.PTTANGTRUONG " +
					" ) " +
					" TBBAN on a.PK_SEQ = TBBAN.SANPHAM_FK left join " +
					" ( " +
					" 	select a.PK_SEQ, a.DVDL_FK as dvCHUAN,   " +
					//" 		isnull( ( select SOLUONG1 / SOLUONG2 from QUYCACH where SANPHAM_FK = a.pk_seq and DVDL1_FK = a.DVDL_FK ), 0 ) as quydoi,  " +
					" 		1 as quydoi,  " +
					"   	isnull( ( select GIAMUANPP from BGMUANPP_SANPHAM  " +
					"  	where SANPHAM_FK = a.pk_seq and BGMUANPP_FK in ( "+
					"		select top(1) PK_SEQ from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK "+
					"		where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = ? and bg.DVKD_FK = ?"+
					"		and bg.KENH_FK = ? order by bg.TUNGAY desc ) ), 0) as giamua   " +
					" 	from SANPHAM a  " +
					" ) " +
					" BGIA on a.PK_SEQ = BGIA.PK_SEQ ";
			
			dbutils.viewQuery(query, new Object[]{this.nppId,this.nppId,this.nppId,this.dvkdId,this.kbhId});
			this.sanphamRs = db.getByPreparedStatement(query, new Object[]{this.nppId,this.nppId,this.nppId,this.dvkdId,this.kbhId});
			
			
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
					"  				where a.dondathang_fk in (    select PK_SEQ from ERP_DONDATHANG where TRANGTHAI = '2' and NPP_FK = ?  and LoaiDonHang = '0'  )   " +
					"  			union ALL  " +
					"  				select b.PK_SEQ,  a.soluong   " +
					"  				from ERP_DONDATHANG_CTKM_TRAKM a inner join SANPHAM b on a.SPMA = b.MA   " +
					"  						inner join CTKHUYENMAI c on a.CTKMID = c.PK_SEQ   " +
					"  				where a.DONDATHANGID in (    select PK_SEQ from ERP_DONDATHANG where TRANGTHAI = '2' and NPP_FK = ? and LoaiDonHang = '0'   )    " +
					"  		)    " +
					"  		dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ    " +
					"  		group by sp.PK_SEQ   " +
					" 	 )    " +
					" 	 ddh left join     " +
					" 	 (    " +
					"  		select b.sanpham_fk, SUM( b.soluongXUAT ) as soluongDAXUAT    " +
					"  		from ERP_YCXUATKHO a inner join ERP_YCXUATKHO_SANPHAM b on a.PK_SEQ = b.ycxk_fk   " +
					"  		where a.TRANGTHAI in (2)    " +
					"  			and a.PK_SEQ in ( select ycxk_fk from  ERP_YCXUATKHO_DDH where ddh_fk in (  select PK_SEQ from ERP_DONDATHANG where TRANGTHAI = '2' and NPP_FK = ? and LoaiDonHang = '0'  ) )   " +
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
					" 		where a.dondathang_fk in (    select PK_SEQ from ERP_DONDATHANG where TRANGTHAI = '2' and NPP_FK = ? and LoaiDonHang = '1'   )  " +
					" 		group by  a.sanpham_fk " +
					" 	 )    " +
					" 	 ddh left join     " +
					" 	 (    " +
					"  		select b.sanpham_fk, SUM( b.soluongXUAT ) as soluongDAXUAT    " +
					"  		from ERP_YCXUATKHO a inner join ERP_YCXUATKHO_SANPHAM b on a.PK_SEQ = b.ycxk_fk   " +
					"  		where a.TRANGTHAI in (2)    " +
					"  			and a.PK_SEQ in ( select ycxk_fk from  ERP_YCXUATKHO_DDH where ddh_fk in (  select PK_SEQ from ERP_DONDATHANG where TRANGTHAI = '2' and NPP_FK = ? and LoaiDonHang = '1'  ) )   " +
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
					" 		where a.dondathang_fk in (    select PK_SEQ from ERP_DONDATHANG where TRANGTHAI = '2' and NPP_FK = ? and LoaiDonHang = '2'   )  " +
					" 		group by  a.sanpham_fk " +
					" 	 )    " +
					" 	 ddh left join     " +
					" 	 (    " +
					"  		select b.sanpham_fk, SUM( b.soluongXUAT ) as soluongDAXUAT    " +
					"  		from ERP_YCXUATKHO a inner join ERP_YCXUATKHO_SANPHAM b on a.PK_SEQ = b.ycxk_fk   " +
					"  		where a.TRANGTHAI in (2)    " +
					"  			and a.PK_SEQ in ( select ycxk_fk from  ERP_YCXUATKHO_DDH where ddh_fk in (  select PK_SEQ from ERP_DONDATHANG where TRANGTHAI = '2' and NPP_FK = ? and LoaiDonHang = '2'  ) )   " +
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
					" 		where a.dondathang_fk in (    select PK_SEQ from ERP_DONDATHANG where TRANGTHAI = '2' and NPP_FK = ? and LoaiDonHang = '3'   )  " +
					" 		group by  a.sanpham_fk " +
					" 	 )    " +
					" 	 ddh left join     " +
					" 	 (    " +
					"  		select b.sanpham_fk, SUM( b.soluongXUAT ) as soluongDAXUAT    " +
					"  		from ERP_YCXUATKHO a inner join ERP_YCXUATKHO_SANPHAM b on a.PK_SEQ = b.ycxk_fk   " +
					"  		where a.TRANGTHAI in (2)    " +
					"  			and a.PK_SEQ in ( select ycxk_fk from  ERP_YCXUATKHO_DDH where ddh_fk in (  select PK_SEQ from ERP_DONDATHANG where TRANGTHAI = '2' and NPP_FK = ? and LoaiDonHang = '3'  ) )   " +
					"  		group by b.sanpham_fk, b.LOAI, b.SCHEME   " +
					" 	 )   " +
					" 	 daxuat on ddh.sanpham_fk = daxuat.sanpham_fk " +
					" ) " +
					" TB on a.PK_SEQ = TB.sanpham_fk ";
			
			System.out.println("CONG NO: " + query);
			this.congnoRs = db.getByPreparedStatement(query, new Object[]{this.nppId,this.nppId,this.nppId,this.nppId,this.nppId,this.nppId,this.nppId,this.nppId,this.nppId});
			
		}
		
	}

	/*public void createRs() 
	{
		this.getNppInfo();
		
		String loainpp = "", kho = "khott";
		String query = "select loainpp from NHAPHANPHOI where PK_SEQ='"+this.nppId+"'";
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try
			{
				rs.next();
				loainpp = rs.getString("loainpp");
				if(loainpp.trim().equals("6"))
					kho = "kho";
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		query = "select PK_SEQ, TEN from "+kho+" where trangthai = '1'  ";
		this.khoNhanRs = db.get(query);
		
		this.dvtRs = db.getScrol("select PK_SEQ, DONVI from DONVIDOLUONG where trangthai = '1' ");
		
		this.dvkdRs = db.get("select PK_SEQ, DONVIKINHDOANH as TEN from DONVIKINHDOANH where TRANGTHAI = '1' ");
		this.kbhRs = db.get("select PK_SEQ, DIENGIAI as TEN from KENHBANHANG where TRANGTHAI = '1' and PK_SEQ in ( select KBH_FK from NHAPP_KBH where NPP_FK = '" + this.nppId + "' ) ");
		
		query = "select PK_SEQ, MA + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1' and pk_seq = '" + this.nppId + "' ";
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
			
			if(this.kbhId==null){
				this.kbhId = "null";
			}else { this.kbhId = "'"  + this.kbhId + "'";}
			if(this.dvkdId==null){
				this.dvkdId = "null";
			}else { this.dvkdId = "'" + this.dvkdId + "'";}
			
			//INIT SAN PHAM
			query = " select a.PK_SEQ, a.MA, a.TEN, isnull(a.tontoithieu, 0) as tontoithieu, isnull(BGIA.quydoi, 1) as quydoi, b.DONVI,  " +
					" 	( select SUM(AVAILABLE) from NHAPP_KHO where NPP_FK = '" + this.nppId + "' and SANPHAM_FK = a.PK_SEQ ) as tonKHO, " +
					" 	ISNULL(TBBAN.tbBAN, 0) as tbBAN, BGIA.giamua * BGIA.quydoi as donGIA, TBBAN.PTTANGTRUONG " +
					" from SANPHAM a inner join DONVIDOLUONG b on a.DVDL_FK = b.PK_SEQ " +
					" left join " +
					" ( " +
					" 	select b.SANPHAM_FK, SUM(b.soluong) / ( DATEDIFF(dd, DATEADD(MM, -3, dbo.GetLocalDate(DEFAULT)), dbo.GetLocalDate(DEFAULT) ) ) as tbBAN, c.PTTANGTRUONG " +
					" 	from DONHANG a inner join DONHANG_SANPHAM b on a.PK_SEQ = b.DONHANG_FK "+
					"	inner join NHAPHANPHOI c on a.NPP_FK = c.PK_SEQ " +
					" 	where a.TRANGTHAI = '1' and a.NPP_FK = '" + this.nppId + "' and a.NGAYNHAP >= CONVERT(varchar(10), DATEADD(MM, -1, dbo.GetLocalDate(DEFAULT)), 120) " +
					" 			and a.NGAYNHAP <= CONVERT(varchar(10), dbo.GetLocalDate(DEFAULT), 120) " +
					" 	group by b.SANPHAM_FK, c.PTTANGTRUONG " +
					" ) " +
					" TBBAN on a.PK_SEQ = TBBAN.SANPHAM_FK left join " +
					" ( " +
					" 	select a.PK_SEQ, a.DVDL_FK as dvCHUAN,   " +
					//" 		isnull( ( select SOLUONG1 / SOLUONG2 from QUYCACH where SANPHAM_FK = a.pk_seq and DVDL1_FK = a.DVDL_FK ), 0 ) as quydoi,  " +
					" 		1 as quydoi,  " +
					"   	isnull( ( select GIAMUANPP from BGMUANPP_SANPHAM  " +
					"  	where SANPHAM_FK = a.pk_seq and BGMUANPP_FK in ( "+
					"		select top(1) PK_SEQ from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK "+
					"		where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = '" + this.nppId + "' and bg.DVKD_FK = "+this.dvkdId+
					"		and bg.KENH_FK = "+this.kbhId+" order by bg.TUNGAY desc ) ), 0) as giamua   " +
					" 	from SANPHAM a  " +
					" ) " +
					" BGIA on a.PK_SEQ = BGIA.PK_SEQ ";
			
			System.out.println("--INIT SAN PHAM: " + query);
			this.sanphamRs = db.get(query);
			
			
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
		
	}*/

	private void initSANPHAM() 
	{
		String query =  "select b.MA, b.TEN, DV.donvi, a.soluong, a.dongia    " +
						" from ERP_Dondathang_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ    " +
						" 		INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK       " +
						"where a.Dondathang_FK = '" + this.id + "' ";

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
				String spSCHEME = "";
				
				while(spRs.next())
				{
					spMA += spRs.getString("MA") + "__";
					spTEN += spRs.getString("TEN") + "__";
					spDONVI += spRs.getString("DONVI") + "__";
					spSOLUONG += formater.format(spRs.getDouble("SOLUONG")) + "__";
					spGIANHAP += formater.format(spRs.getDouble("DONGIA")) + "__";
					spSCHEME += "__";
				}
				spRs.close();
				
				//INIT SP KHUYEN MAI
				query = "select isnull(b.MA, ' ') as MA, isnull(b.TEN, ' ') as TEN, isnull(c.DONVI, ' ') as donvi, d.SCHEME, isnull(a.soluong, 0) as soluong, a.tonggiatri " +
						"from ERP_DONDATHANG_CTKM_TRAKM a left join SANPHAM b on a.SPMA = b.MA  " +
						"	left join DONVIDOLUONG c on b.DVDL_FK = c.PK_SEQ  " +
						"	inner join CTKHUYENMAI d on a.ctkmID = d.PK_SEQ " +
						"where a.dondathangID = '" + this.id + "' ";
		
				spRs = db.get(query);
				while(spRs.next())
				{
					spMA += spRs.getString("MA") + "__";
					spTEN += spRs.getString("TEN") + "__";
					spDONVI += spRs.getString("DONVI") + "__";
					spSOLUONG += formater.format(spRs.getDouble("SOLUONG")) + "__";
					spGIANHAP += formater.format(spRs.getDouble("tonggiatri")) + "__";
					spSCHEME += spRs.getString("SCHEME") + "__";
				}
				spRs.close();

			} 
			catch (Exception e) 
			{
				System.out.println("115.Exception: " + e.getMessage());
			}
		}
	}

	public void init() 
	{
		String query = "select ngaydenghi, ISNULL(ghichu, '') as ghichu, dvkd_fk, kbh_fk, npp_fk, kho_fk, chietkhau, vat, tansuatdathang " +
						"from ERP_Denghidathang where pk_seq = '" + this.id + "'";
		System.out.println("____INIT DNDH: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.ngaydenghi = rs.getString("ngaydenghi");
					this.ghichu = rs.getString("ghichu");
					this.dvkdId = rs.getString("dvkd_fk");
					this.kbhId = rs.getString("kbh_fk");
					this.nppId = rs.getString("npp_fk");
					this.khoNhanId = rs.getString("kho_fk");
					this.tsdh = rs.getString("tansuatdathang");
					this.chietkhau = rs.getString("chietkhau");
					this.vat = rs.getString("vat");
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("---LOI INIT: " + e.getMessage());
			}
			
			//INIT SO LUONG DAT
			query = "select b.MA, a.denghi  " +
					"from ERP_DeNghiDatHang_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ  " +
					"where a.denghidathang_fk = '" + this.id + "' and denghi != 0";
			System.out.println("--INIT SP: " + query);
			ResultSet rsSP = db.get(query);
			if(rsSP != null)
			{
				try 
				{
					Hashtable<String, String> sp_soluong = new Hashtable<String, String>();
					while(rsSP.next())
					{
						sp_soluong.put(rsSP.getString("MA"), rsSP.getString("denghi"));
					}
					rsSP.close();
					
					this.sp_soluong = sp_soluong;
				} 
				catch (Exception e) {}
			}
			
		}
		
		this.createRs();
		
		
		//this.initSANPHAM();
		
	}

	public void DBclose() {
		
		try{
			
			
			if(khoNhanRs!=null){
				khoNhanRs.close();
			}
			if(kbhRs != null){
				kbhRs.close();
			}
			if(nppRs != null){
				nppRs.close();
			}
			if(dvtRs != null){
				dvtRs.close();
			}
			if(dvkdRs != null){
				dvkdRs.close();
			}
			if(schemeRs != null){
				schemeRs.close();
			}
			if(congnoRs != null){
				congnoRs.close();
			}
			if(sanphamRs != null){
				sanphamRs.close();
			}
			
			if(this.db != null){
				this.db.shutDown();
			}
			
			
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

	public ResultSet getCongnoRs() {

		return this.congnoRs;
	}


	public void setCongnoRs(ResultSet congnoRs) {
		
		this.congnoRs = congnoRs;
	}
	
	public String getNppTen() 
	{
		return this.nppTen;
	}

	public void setNppTen(String nppTen) 
	{
		this.nppTen = nppTen;
	}
	
	public String getSitecode() 
	{
		return this.sitecode;
	}

	public void setSitecode(String sitecode) 
	{
		this.sitecode = sitecode;
	}
	
	private void getNppInfo()
	{		
		//Phien ban moi
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		//this.dangnhap = util.getDangNhap();
		this.sitecode=util.getSitecode();
	}

	
	public ResultSet getSanphamRs() {
		
		return this.sanphamRs;
	}

	
	public void setSanphamRs(ResultSet spRs) {
		
		this.sanphamRs = spRs;
	}

	
	public void setSanpham_soluong(Hashtable<String, String> sp_soluong) {
		
		this.sp_soluong = sp_soluong;
	}

	
	public Hashtable<String, String> getSanpham_soluong() {
		
		return this.sp_soluong;
	}

	
	public String getTansuatdathang() {

		return this.tsdh;
	}


	public void setTansauatdathang(String tsdh) {
		
		this.tsdh = tsdh;
	}
}
