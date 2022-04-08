package geso.dms.distributor.beans.doihang.imp;

import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.doihang.IErpKhachhangdoihang;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

public class ErpKhachhangdoihang implements IErpKhachhangdoihang
{

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
	
	String khoDoiId;
	ResultSet khoDoiRs;
	
	String nppId;
	ResultSet nppRs;
	
	String khId;
	ResultSet khRs;
	
	String nppTen;
	String sitecode;
	
	String dvkdId;
	ResultSet dvkdRs;
	
	String kbhId;
	ResultSet kbhRs;
	
	ResultSet lydoRs;
	String lydoId;
	
	ResultSet dvtRs;
	
	String schemeId;
	ResultSet schemeRs;
	
	ResultSet sanphamRs;
	ResultSet sanphamDOIRs;
	
	Hashtable<String, String> sp_soluong;
	Hashtable<String, String> sp_solo;
	Hashtable<String, String> sp_nsx;
	Hashtable<String, String> sp_ngaysanxuat;
	
	Hashtable<String, String> sp_id_duyet;
	Hashtable<String, String> sp_soluong_duyet;
	Hashtable<String, String> sp_solo_duyet;
	
	String tsdh;
	
	dbutils db;
	
	public ErpKhachhangdoihang()
	{
		this.id = "";
		this.ngayyeucau = getDateTime();
		this.ngaydenghi = "";
		this.ghichu = "";
		this.khoDoiId = "";
		this.khoNhanId = "";
		this.nppId = "";
		this.msg = "";
		this.loaidonhang = "0";
		this.trangthai = "0";
		this.chietkhau = "0";
		this.vat = "10";
		this.dvkdId = "";
		this.kbhId = "";
		this.lydoId = "";
		this.schemeId = "";
		this.tsdh = "6";
		this.khId = "";
		
		this.sp_soluong = new Hashtable<String, String>();
		this.sp_solo = new Hashtable<String, String>();
		this.sp_nsx = new Hashtable<String, String>();
		
		this.db = new dbutils();
	}
	
	public ErpKhachhangdoihang(String id)
	{
		this.id = id;
		this.ngayyeucau = getDateTime();
		this.ngaydenghi = "";
		this.ghichu = "";
		this.khoDoiId = "";
		this.khoNhanId = "";
		this.nppId = "";
		this.msg = "";
		this.loaidonhang = "0";
		this.trangthai = "0";
		this.chietkhau = "0";
		this.vat = "10";
		this.dvkdId = "";
		this.kbhId = "";
		this.lydoId = "";
		this.schemeId = "";
		this.tsdh = "1";
		this.khId = "";

		this.sp_soluong = new Hashtable<String, String>();
		this.sp_solo = new Hashtable<String, String>();
		this.sp_nsx = new Hashtable<String, String>();
		
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

	
	public boolean createNK(HttpServletRequest request) //tao 1 don doi hang
	{
		if(this.lydoId.trim().length() <=0)
		{
			this.msg = "Vui lòng chọn lý do, ban nhap ly do la: '" + this.lydoId +"' ";
			return false;
		}

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
			this.msg = "Vui lòng chọn nhà phân phối đổi hàng";
			return false;
		}
		if( this.khId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn khách hàng đổi hàng";
			return false;
		}
		if(this.sp_soluong == null)
		{
			this.msg = "1.Vui lòng nhập số lượng sản phẩm đổi";
			return false;
		}
		else
		{
			if(this.sp_soluong.size() <= 0)
			{
				this.msg = "2.Vui lòng nhập số lượng sản phẩm đổi";
				return false;
			}
		}
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
			String vat = this.vat.trim().length() <= 0 ? "0" : this.vat.trim();
			
			String query = " insert ERP_KHACHHANGDOIHANG(ngaydenghi, ghichu, trangthai, dvkd_fk, kbh_fk, npp_fk, khachhang_fk, kho_fk, khoDOI_FK, chietkhau, vat, ngaytao, nguoitao, ngaysua, nguoisua, DOITRAHANG_FK) " +
						   " values('" + this.ngaydenghi + "', N'" + this.ghichu + "', '0', '" + dvkdId + "', '" + kbhId + "', '" + nppId + "', '" + this.khId + "', " + khonhan_fk + ", '" + this.khoDoiId + "', '0', '" + vat + "', '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "', '" + this.lydoId + "' )";
			
			System.out.println("1.Insert DDH: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_KHACHHANGDOIHANG " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//LAY ID
			ResultSet rsDDH = db.get("select IDENT_CURRENT('ERP_KHACHHANGDOIHANG') as ID ");
			if(rsDDH.next())
			{
				this.id = rsDDH.getString("ID");
			}
			rsDDH.close();
			
			String[] spId = request.getParameterValues("spID");		
			String[] spSOLUONG = request.getParameterValues("spSOLUONG");
			if( spId != null )
			{
				for(int i = 0; i < spId.length; i++ )
				{
					if( spSOLUONG[i].trim().length() > 0)
					{						
						query = "insert ERP_KHACHHANGDOIHANG_SANPHAM(khachhangdoihang_fk, sanpham_fk, dvdl_fk, soluong) " +
								"select '" + this.id + "', '" + spId[i] + "', DVDL_FK, '" + spSOLUONG[i].replaceAll(",", "") + "' " +
								"from SANPHAM " +
								"where pk_seq = '" + spId[i] + "' ";
						
						System.out.println("--INSERT SP: " + query);
						if(!db.update(query))
						{
							this.msg = "Không thể tạo mới ERP_KHACHHANGDOIHANG_SANPHAM " + query;
							db.getConnection().rollback();
							return false;
						}
						
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
	}

	public boolean updateNK(HttpServletRequest request) 
	{
		
		if(this.lydoId.trim().length() <=0)
		{
			this.msg = "Vui lòng chọn lý do, ban nhap ly do la: '" + this.lydoId +"' ";
			return false;
		}
		
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
			this.msg = "Vui lòng chọn nhà phân phối đổi hàng";
			return false;
		}
		
		if( this.khId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn khách hàng đổi hàng";
			return false;
		}
		
		if(this.sp_soluong == null)
		{
			this.msg = "1.Vui lòng nhập sản phẩm đổi";
			return false;
		}
		else
		{
			if(this.sp_soluong.size() <= 0)
			{
				this.msg = "2.Vui lòng nhập sản phẩm đổi";
				return false;
			}
		}
		
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String vat = this.vat.trim().length() <= 0 ? "0" : this.vat.trim();
			String query = " update ERP_KHACHHANGDOIHANG set ngaydenghi = N'" + this.ngaydenghi + "', ghichu = N'" + this.ghichu + "', dvkd_fk = '" + this.dvkdId + "', kbh_fk = '" + this.kbhId + "', khachhang_fk = '" + this.khId + "', kho_fk = '" + this.khoNhanId + "', " +
						   "	vat = '" + vat + "', ngaysua = '" + this.getDateTime() + "', nguoisua = '" + this.userId + "', DOITRAHANG_FK='" + this.lydoId + "' " +
						   " where pk_seq = '" + this.id + "' ";
			System.out.println("1.update DDH: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_KHACHHANGDOIHANG " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = " delete ERP_KHACHHANGDOIHANG_SANPHAM where khachhangdoihang_fk = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_KHACHHANGDOIHANG_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			String[] spId = request.getParameterValues("spID");
			String[] spSOLO = request.getParameterValues("spSOLO");
			String[] spNGAYSANXUAT = request.getParameterValues("spNGAYSANXUAT");
			String[] spSOLUONG = request.getParameterValues("spSOLUONG");
			
			if( spId != null )
			{
				for(int i = 0; i < spId.length; i++ )
				{
					if(  spSOLUONG[i].trim().length() > 0  )
					{
						query = "insert ERP_KHACHHANGDOIHANG_SANPHAM(khachhangdoihang_fk, sanpham_fk, dvdl_fk, soluong) " +
								"select '" + this.id + "', '" + spId[i] + "', DVDL_FK, '" + spSOLUONG[i].replaceAll(",", "") + "' " +
								"from SANPHAM " +
								"where pk_seq = '" + spId[i] + "' ";
						
						System.out.println("--INSERT SP: " + query);
						if(!db.update(query))
						{
							this.msg = "Không thể tạo mới ERP_KHACHHANGDOIHANG_SANPHAM " + query;
							db.getConnection().rollback();
							return false;
						}
						
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
	}


	public void createRs() 
	{
		this.getNppInfo();
		this.khoNhanRs = db.get("select PK_SEQ, TEN from KHO where trangthai = '1'  ");
		this.khoDoiRs = db.get("select PK_SEQ, TEN from KHO where trangthai = '1' ");
		this.dvtRs = db.getScrol("select PK_SEQ, DONVI from DONVIDOLUONG where trangthai = '1' ");
		this.dvkdRs = db.get("select PK_SEQ, DONVIKINHDOANH as TEN from DONVIKINHDOANH where TRANGTHAI = '1' ");
		this.kbhRs = db.get("select PK_SEQ, DIENGIAI as TEN from KENHBANHANG where TRANGTHAI = '1' and PK_SEQ in ( select KBH_FK from NHAPP_KBH where NPP_FK = '" + this.nppId + "' ) ");
		this.lydoRs =  db.get("select * from DOITRAHANG where Loai=0"); //loai=0 la doi hang, loai=1 la tra hang.
		this.sanphamDOIRs = db.getScrol("select pk_seq, ten from SANPHAM where trangthai = '1' ");
		String query = "select PK_SEQ, SMARTID + ', ' + TEN as TEN from KHACHHANG where npp_fk = '" + this.nppId + "' ";
		this.khRs = db.get(query);
		if(this.nppId.trim().length() > 0)
		{
			query = "select a.PK_SEQ as nppId, d.DVKD_FK, b.KBH_FK  " +
				    "From NhaPhanPhoi a left join nhapp_kbh b on b.NPP_FK = a.PK_SEQ left join NHAPP_NHACC_DONVIKD c on c.NPP_FK = b.NPP_FK  " +
				    "	left join NHACUNGCAP_DVKD d on d.PK_SEQ = c.NCC_DVKD_FK   " +
				    "where a.pk_Seq = '" + this.nppId + "' ";
			ResultSet rsInfo = db.get(query);
			/*if(rsInfo != null)*/
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
			
			//INIT SAN PHAM
			query = " select a.PK_SEQ, a.MA, a.TEN, b.DONVI, BGIA.giamua as donGIA   " +
					" from SANPHAM a inner join DONVIDOLUONG b on a.DVDL_FK = b.PK_SEQ   " +
					" 	 left join   " +
					" 	 (  	 " +
					" 		 select a.PK_SEQ, a.DVDL_FK as dvCHUAN,  isnull( (  select GIAMUA_SAUCK  " +
					" 															from BGMUANPP_SANPHAM    					  " +
					" 															where SANPHAM_FK = a.pk_seq    and BGMUANPP_FK in ( select top(1) PK_SEQ from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK  where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = '" + this.nppId + "' and bg.DVKD_FK = '" + this.dvkdId + "' and bg.KENH_FK = '" + this.kbhId + "'  order by bg.TUNGAY desc ) ), 0) as giamua    	 " +
					" 		 from SANPHAM a   " +
					" 	 )   " +
					" 	 BGIA on a.PK_SEQ = BGIA.PK_SEQ  ";
			System.out.println("--INIT SAN PHAM: " + query);
			this.sanphamRs = db.get(query);
			
		}
		
	}
	
	public void createRsCapNhat()
	{
		this.getNppInfo();
		
		this.khoNhanRs = db.get("select PK_SEQ, TEN from KHO where trangthai = '1' and loaikho = '0' ");
		this.khoDoiRs = db.get("select PK_SEQ, TEN from KHO where trangthai = '1' and loaikho = '1' ");
		this.dvtRs = db.getScrol("select PK_SEQ, DONVI from DONVIDOLUONG where trangthai = '1' ");
		this.lydoRs =  db.get("select * from DOITRAHANG where Loai=0"); //loai=0 la doi hang, loai=1 la tra hang.
		this.dvkdRs = db.get("select PK_SEQ, DONVIKINHDOANH as TEN from DONVIKINHDOANH where TRANGTHAI = '1' ");
		this.kbhRs = db.get("select PK_SEQ, DIENGIAI as TEN from KENHBANHANG where TRANGTHAI = '1' and PK_SEQ in ( select KBH_FK from NHAPP_KBH where NPP_FK = '" + this.nppId + "' ) ");
		
		String query = "select PK_SEQ, SMARTID + ', ' + TEN as TEN from KHACHHANG where npp_fk = '" + this.nppId + "' ";
		this.khRs = db.get(query);
	
		
		//INIT SAN PHAM
		query = " select a.PK_SEQ, a.MA, a.TEN, b.DONVI, 0 as donGIA, c.solo, c.ngaysanxuat, c.soluong     " +
				" from SANPHAM a inner join DONVIDOLUONG b on a.DVDL_FK = b.PK_SEQ  " +
				"		inner join ERP_KHACHHANGDOIHANG_SANPHAM c on a.PK_SEQ = c.sanpham_fk " +
				" where khachhangdoihang_fk = '" + this.id + "' ";
				
		System.out.println("--INIT SAN PHAM: " + query);
		this.sanphamRs = db.get(query);
		

		this.sanphamDOIRs = db.getScrol("select pk_seq, ten from SANPHAM where trangthai = '1' ");
				
	
	}
	
	public void createRsDuyet() 
	{
		this.getNppInfo();
		
		this.khoNhanRs = db.get("select PK_SEQ, TEN from KHO where trangthai = '1' and loaikho = '0' ");
		this.khoDoiRs = db.get("select PK_SEQ, TEN from KHO where trangthai = '1' and loaikho = '1' ");
		this.dvtRs = db.getScrol("select PK_SEQ, DONVI from DONVIDOLUONG where trangthai = '1' ");
		this.lydoRs =  db.get("select * from DOITRAHANG where Loai=0"); //loai=0 la doi hang, loai=1 la tra hang.
		this.dvkdRs = db.get("select PK_SEQ, DONVIKINHDOANH as TEN from DONVIKINHDOANH where TRANGTHAI = '1' ");
		this.kbhRs = db.get("select PK_SEQ, DIENGIAI as TEN from KENHBANHANG where TRANGTHAI = '1' and PK_SEQ in ( select KBH_FK from NHAPP_KBH where NPP_FK = '" + this.nppId + "' ) ");
		
		String query = "select PK_SEQ, SMARTID + ', ' + TEN as TEN from KHACHHANG where npp_fk = '" + this.nppId + "' ";
		this.khRs = db.get(query);
	
		
		//INIT SAN PHAM
		query = " select a.PK_SEQ, a.MA, a.TEN, b.DONVI, 0 as donGIA, c.solo, c.ngaysanxuat, c.soluong     " +
				" from SANPHAM a inner join DONVIDOLUONG b on a.DVDL_FK = b.PK_SEQ  " +
				"		inner join ERP_KHACHHANGDOIHANG_SANPHAM c on a.PK_SEQ = c.sanpham_fk " +
				" where khachhangdoihang_fk = '" + this.id + "' ";
				
		System.out.println("--INIT SAN PHAM: " + query);
		this.sanphamRs = db.get(query);
		

		this.sanphamDOIRs = db.getScrol("select pk_seq, ma +'-' + ten as Ten from SANPHAM where trangthai = '1' ");
				
	}

	public void init() 
	{
		String query = "select ngaydenghi, ISNULL(ghichu, '') as ghichu, dvkd_fk, kbh_fk, npp_fk, khachhang_fk, kho_fk, khoDOI_FK, chietkhau, vat " +
						"from ERP_KHACHHANGDOIHANG where pk_seq = '" + this.id + "'";
		System.out.println("____INIT DNDH: " + query);
		ResultSet rs = db.get(query);
		/*if(rs != null)*/
		{
			try 
			{
				if(rs.next())
				{
					this.ngaydenghi = rs.getString("ngaydenghi");
					this.ghichu = rs.getString("ghichu");
					this.dvkdId = rs.getString("dvkd_fk");
					this.kbhId = rs.getString("kbh_fk");
					this.khId = rs.getString("khachhang_fk");
					this.khoNhanId = rs.getString("kho_fk");
					this.khoDoiId = rs.getString("khoDOI_FK");
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
			query = "select b.pk_seq, a.soluong, a.ngaysanxuat, a.solo  " +
					"from ERP_KHACHHANGDOIHANG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ   " +
					"	inner join ERP_KHACHHANGDOIHANG c on a.khachhangdoihang_fk = c.pk_seq  " +
					"where a.khachhangdoihang_fk = '" + this.id + "' and soluong != 0 ";
			System.out.println("--INIT SP: " + query);
			ResultSet rsSP = db.get(query);
			/*if(rsSP != null)*/
			{
				try 
				{
					Hashtable<String, String> sp_soluong = new Hashtable<String, String>();
					Hashtable<String, String> sp_solo = new Hashtable<String, String>();
					Hashtable<String, String> sp_nsx = new Hashtable<String, String>();
					
					while(rsSP.next())
					{
						sp_soluong.put(rsSP.getString("pk_seq"), rsSP.getString("soluong"));
						sp_solo.put(rsSP.getString("pk_seq"), rsSP.getString("solo"));
						sp_nsx.put(rsSP.getString("pk_seq"), rsSP.getString("ngaysanxuat"));
					}
					rsSP.close();
					
					this.sp_soluong = sp_soluong;
					this.sp_solo = sp_solo;
					this.sp_nsx = sp_nsx;
				} 
				catch (Exception e) {e.printStackTrace();}
			}
			
		}
		
		this.createRs();
		
	}
	
	public void initCapNhat()
	{
		String query = "select ngaydenghi, ISNULL(ghichu, '') as ghichu, dvkd_fk, kbh_fk, npp_fk, khachhang_fk, kho_fk, khoDOI_FK, chietkhau, vat, isnull(DOITRAHANG_FK,0) as lydoId " +
						" from ERP_KHACHHANGDOIHANG where pk_seq = '" + this.id + "' ";
		System.out.println("____INIT DNDH: " + query);
		ResultSet rs = db.get(query);
		/*if(rs != null)*/
		{
			try 
			{
				if(rs.next())
				{
					this.ngaydenghi = rs.getString("ngaydenghi");
					this.ghichu = rs.getString("ghichu");
					this.dvkdId = rs.getString("dvkd_fk");
					this.kbhId = rs.getString("kbh_fk");
					this.khId = rs.getString("khachhang_fk");
					this.khoNhanId = rs.getString("kho_fk");
					this.khoDoiId = rs.getString("khoDOI_FK");
					this.chietkhau = rs.getString("chietkhau");
					this.vat = rs.getString("vat");
					this.lydoId = rs.getString("lydoId");
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("---LOI INIT: " + e.getMessage());
			}			
			query = " select b.pk_seq, isnull(a.soluong, 0) as soluong, isnull(a.SANPHAM_DUYET_FK, 0) as SANPHAM_DUYET_FK, a.solo,a.ngaysanxuat "
					+ " from ERP_KHACHHANGDOIHANG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ "
					+ " inner join ERP_KHACHHANGDOIHANG c on a.khachhangdoihang_fk = c.pk_seq "
					+ " where a.khachhangdoihang_fk = '" + this.id + "' ";
			
			System.out.println("--INIT SP: " + query);
			ResultSet rsSP = db.get(query);
			/*if(rsSP != null)*/
			{
				try 
				{
					Hashtable<String, String> sp_soluong = new Hashtable<String, String>();
					Hashtable<String, String> sp_solo = new Hashtable<String, String>();
					Hashtable<String, String> sp_nsx = new Hashtable<String, String>();
					Hashtable<String, String> sp_ngaysanxuat = new Hashtable<String, String>();
					while(rsSP.next())
					{
						sp_soluong.put(rsSP.getString("pk_seq"), rsSP.getString("soluong"));
						sp_nsx.put(rsSP.getString("pk_seq"), rsSP.getString("SANPHAM_DUYET_FK"));
					}
					rsSP.close();
					
					this.sp_soluong = sp_soluong;
					this.sp_solo = sp_solo;
					this.sp_nsx = sp_nsx;
					this.sp_ngaysanxuat = sp_ngaysanxuat;
				} 
				catch (Exception e) {e.printStackTrace();}
			}
			
		}
		
		this.createRsCapNhat();

	
	}
	
	public String gettenkh()
	{
		String query="select * from khachhang where pk_seq="+this.khId;
		ResultSet rs=db.get(query);
		try {
			if(rs.next()){
				return rs.getString("ten");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	public void initDuyet() 
	{
		String query = "select ngaydenghi, ISNULL(ghichu, '') as ghichu, dvkd_fk, kbh_fk, npp_fk, khachhang_fk, kho_fk, khoDOI_FK, chietkhau, vat, isnull(DOITRAHANG_FK,0) as lydoId  " +
						"from ERP_KHACHHANGDOIHANG where pk_seq = '" + this.id + "'";
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
					this.khId = rs.getString("khachhang_fk");
					this.khoNhanId = rs.getString("kho_fk");
					this.khoDoiId = rs.getString("khoDOI_FK");
					this.chietkhau = rs.getString("chietkhau");
					this.vat = rs.getString("vat");
					this.lydoId = rs.getString("lydoId");
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("---LOI INIT: " + e.getMessage());
			}
			
			//INIT SO LUONG DAT
			query = "select b.pk_seq, isnull(a.duyet, 0) as duyet, isnull(a.SANPHAM_DUYET_FK, 0) as SANPHAM_DUYET_FK, isnull(a.soloDUYET, 0) as soloDUYET   " +
					"from ERP_KHACHHANGDOIHANG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ   " +
					"	inner join ERP_KHACHHANGDOIHANG c on a.khachhangdoihang_fk = c.pk_seq  " +
					"where a.khachhangdoihang_fk = '" + this.id + "' and SANPHAM_DUYET_FK is not null ";
			System.out.println("--INIT SP: " + query);
			ResultSet rsSP = db.get(query);
			if(rsSP != null)
			{
				try 
				{
					Hashtable<String, String> sp_soluong = new Hashtable<String, String>();
					Hashtable<String, String> sp_solo = new Hashtable<String, String>();
					Hashtable<String, String> sp_nsx = new Hashtable<String, String>();
					
					while(rsSP.next())
					{
						sp_soluong.put(rsSP.getString("pk_seq"), rsSP.getString("duyet"));
						sp_solo.put(rsSP.getString("pk_seq"), rsSP.getString("soloDUYET"));
						sp_nsx.put(rsSP.getString("pk_seq"), rsSP.getString("SANPHAM_DUYET_FK"));
						
					}
					rsSP.close();
					
					this.sp_soluong = sp_soluong;
					this.sp_solo = sp_solo;
					this.sp_nsx = sp_nsx;
				} 
				catch (Exception e) {}
			}
			
		}
		
		this.createRsDuyet();

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

	
	public void setSanpham_solo(Hashtable<String, String> sp_solo) {
		
		this.sp_solo = sp_solo;
	}


	public Hashtable<String, String> getSanpham_solo() {

		return this.sp_solo;
	}


	public ResultSet getSoloRs(String spId, String khoId)
	{
		String query = "select SOLO, AVAILABLE from NHAPP_KHO_CHITIET " +
					   "where NPP_FK = '" + this.nppId + "' and SANPHAM_FK = '" + spId + "' and KHO_FK = '" + khoId + "' " +
					   "order by NGAYHETHAN asc";
		
		/*System.out.println("LAY SO LO: " + query);*/
		return db.get(query);
	}

	
	public String getKhId() {
		
		return this.khId;
	}

	
	public void setKhId(String khId) {
		
		this.khId = khId;
	}

	
	public ResultSet getKhRs() {
		
		return this.khRs;
	}

	
	public void setKhRs(ResultSet khRs) {
		
		this.khRs = khRs;
	}

	
	public void setSanpham_NSX(Hashtable<String, String> sp_nsx) {
		
		this.sp_nsx = sp_nsx;
	}


	public Hashtable<String, String> getSanpham_NSX() {

		return this.sp_nsx;
	}

	
	public ResultSet getSanphamDoiRs() {

		return this.sanphamDOIRs;
	}


	public void setSanphamDoiRs(ResultSet spDoiRs) {

		this.sanphamDOIRs = spDoiRs;
	}


	public boolean duyetNK(HttpServletRequest request) 
	{
		if(this.ngaydenghi.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày đề nghị";
			return false;
		}

		if(this.sp_soluong == null)
		{
			this.msg = "1.Vui lòng nhập số lượng duyệt của sản phẩm đổi";
			return false;
		}
		else
		{
			if(this.sp_soluong.size() <= 0)
			{
				this.msg = "2.Vui lòng nhập số lượng của sản phẩm đổi";
				return false;
			}
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);			
			Utility util = new Utility();
			nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
			String NgayKs_CongMot="";
			NgayKs_CongMot = util.getNgayKs_CongMot(nppId, db);
			if(NgayKs_CongMot.length()<=0)
			{
				this.msg = "Không thể xác định ngày KS của npp " ;
				db.getConnection().rollback();
				return false;
			}
		
			String query = " update ERP_KHACHHANGDOIHANG set NgayDeNghi='"+NgayKs_CongMot+"' , trangthai = '1', ngaysua = '" + this.getDateTime() + "', nguoisua = '" + this.userId + "' " +
						   " where pk_seq = '" + this.id + "' and TrangThai=0 ";
			System.out.println("1.update DDH: " + query);
			if(db.updateReturnInt(query)!=1)
			{
				this.msg = "Đề nghị đã được duyệt !";
				db.getConnection().rollback();
				return false;
			}
			

			String[] spId = request.getParameterValues("spID");
			String[] spDUYET = request.getParameterValues("spSOLUONGDUYET");
			String[] spIDDUYET = request.getParameterValues("spIDDUYET");
			if( spId != null )
			{
				for(int i = 0; i < spId.length; i++ )
				{
					if(spDUYET[i].trim().length() > 0 && spIDDUYET[i].trim().length() > 0)
					{
						query = "update ERP_KHACHHANGDOIHANG_SANPHAM set duyet = '" + spDUYET[i].trim().replaceAll(",", "") + "', SANPHAM_DUYET_FK = '" + spIDDUYET[i].trim()+ "' " +
								"where khachhangdoihang_fk = '" + this.id + "' and sanpham_fk = '" + spId[i] + "' ";
						System.out.println("1. update DDH - SANPHAM: " + query);
						
						if(db.updateReturnInt(query)!=1)
						{
							this.msg = "Không thể duyệt ERP_KHACHHANGDOIHANG_SANPHAM " + query;
							db.getConnection().rollback();
							return false;
						}
					}
					if(spDUYET[i].trim().length() <= 0 || spIDDUYET[i].trim().length() <= 0)
					{
						this.msg = "Vui lòng nhập đầy đủ thông tin sản phẩm duyệt";
						db.getConnection().rollback();
						return false;
					}
				}
			}
			query = " select b.ten as spTEN, c.kbh_fk, c.kho_fk, c.npp_fk, a.soluong, a.duyet, a.ngaysanxuat, convert(varchar(10), DATEADD(dd,convert(int, isnull(HANSUDUNG, 0)), a.ngaysanxuat), 120) as NGAYHETHAN, " +
					" 			 a.sanpham_fk, a.SANPHAM_DUYET_FK, a.solo, a.soloDUYET, " +
					" 		( select AVAILABLE from NHAPP_KHO where NPP_FK = c.NPP_FK and KBH_FK = c.kbh_fk and SANPHAM_FK = a.SANPHAM_DUYET_FK and KHO_FK = '" + this.khoDoiId + "' )  as avai,  " +
					" 		( select COUNT(*) from NHAPP_KHO where NPP_FK = c.NPP_FK and KBH_FK = c.kbh_fk and SANPHAM_FK = a.SANPHAM_FK and KHO_FK = '" + this.khoNhanId + "' )  as exitKHOLOI   " +
					" from ERP_KHACHHANGDOIHANG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ   	 " +
					" 	inner join ERP_KHACHHANGDOIHANG c on a.khachhangdoihang_fk = c.pk_seq   " +
					" where a.khachhangdoihang_fk = '" + this.id + "' and SANPHAM_DUYET_FK is not null  " ;
			
			System.out.println("---CHOT: " + query);
			ResultSet rs = db.get(query);
			{
				while(rs.next())
				{
					String spTEN = rs.getString("spTEN");
					String kbh_fk = rs.getString("kbh_fk");
					String kho_fk = this.khoDoiId;
					String kho_loi_fk = this.khoNhanId;
					String npp_fk = rs.getString("npp_fk");
					double soluong = rs.getDouble("soluong");
					double duyet = rs.getDouble("duyet");
					String sanpham_fk = rs.getString("sanpham_fk");
					String sanpham_duyet_fk = rs.getString("SANPHAM_DUYET_FK");
					int exitKHOLOI = rs.getInt("exitKHOLOI");
					double avai = rs.getDouble("avai");
					
					if(duyet > avai)
					{
						this.msg = "Tồn kho của sản phẩm ( " + spTEN + " ) còn tối đa ( " + avai + " ) không đủ để đổi hàng với số lượng duyệt ( " + duyet + " ) ";
						db.getConnection().rollback();
						return false;
					}
					
					//GIAM KHO CHINH
					query = "Update NHAPP_KHO set soluong = soluong - '" + duyet + "', AVAILABLE = AVAILABLE - '" + duyet + "' " +
							"where kho_fk = '" + kho_fk + "' and NPP_FK = '" + npp_fk + "' and KBH_FK = '" + kbh_fk + "' and SANPHAM_FK = '" + sanpham_duyet_fk + "' ";
					System.out.println("GIAM KHO CHINH: " + query);
					if(db.updateReturnInt(query)!=1)
					{
						this.msg = "1.Lỗi khi cập nhật tồn kho " + query;
						db.getConnection().rollback();
						return false;
					}
				
					//KHO LOI TONG
					if(exitKHOLOI > 0)
					{
						query = "Update NHAPP_KHO set soluong = soluong + '" + soluong + "', AVAILABLE = AVAILABLE + '" + soluong + "' " +
								"where kho_fk = '" + kho_loi_fk + "' and NPP_FK = '" + npp_fk + "' and KBH_FK = '" + kbh_fk + "' and SANPHAM_FK = '" + sanpham_fk + "' ";
					}
					else
					{
						query = "insert NHAPP_KHO(KHO_FK, NPP_FK, SANPHAM_FK, SOLUONG, BOOKED, AVAILABLE, GIAMUA, KBH_FK) " +
								"values('" + kho_loi_fk + "', '" + npp_fk + "', '" + sanpham_fk + "', '" + soluong + "', '0', '" + soluong + "', '0', '" + kbh_fk + "')";
					}
					System.out.println("KHO LOI TONG: " +  query);
					if(db.updateReturnInt(query)!=1)
					{
						this.msg = "3.Lỗi khi cập nhật tồn kho ";
						db.getConnection().rollback();
						return false;
					}
				}
				rs.close();
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.msg = "Exception: " + e.getMessage();
			return false;
		}
		
		return true;
	}

	
	public String getKhoDoiId() {
		
		return this.khoDoiId;
	}

	
	public void setKhoDoiId(String khodoitt) {
		
		this.khoDoiId = khodoitt;
	}

	
	public ResultSet getKhoDoiRs() {
		
		return this.khoDoiRs;
	}

	
	public void setKhoDoiRs(ResultSet khodoiRs) {
		
		this.khoDoiRs = khodoiRs;
	}

	@Override
	public ResultSet getLydoRs() {
		return this.lydoRs;
	}

	@Override
	public void setLydoRs(ResultSet lydoRs) {
		this.lydoRs = lydoRs;
		
	}

	@Override
	public String getLydoId() {
		return this.lydoId;
	}

	@Override
	public void setLydoId(String lydoId) {
		this.lydoId = lydoId;
	}

	@Override
	public void setSanpham_ngaysanxuat(Hashtable<String, String> sp_ngaysanxuat) {
		this.sp_ngaysanxuat = sp_ngaysanxuat;
	}

	@Override
	public Hashtable<String, String> getSanpham_ngaysanxuat() {
		// TODO Auto-generated method stub
		return this.sp_ngaysanxuat;
	}

	@Override
	public void setSanpham_id_duyet(Hashtable<String, String> Sanpham_id_duyet) {
		this.sp_id_duyet = Sanpham_id_duyet;
	}

	@Override
	public Hashtable<String, String> getSanpham_id_duyet() {
		return this.sp_id_duyet;
	}

	@Override
	public void setSanpham_soluong_duyet(
			Hashtable<String, String> Sanpham_soluong_duyet) {
		this.sp_soluong_duyet = Sanpham_soluong_duyet;
		
	}

	@Override
	public Hashtable<String, String> getSanpham_soluong_duyet() {
		return this.sp_soluong_duyet;
	}

	@Override
	public void setSanpham_solo_duyet(
			Hashtable<String, String> Sanpham_solo_duyet) {
		this.sp_solo_duyet = Sanpham_solo_duyet;
	}

	@Override
	public Hashtable<String, String> getSanpham_solo_duyet() {
		return this.sp_solo_duyet;
	}

	
	
}
