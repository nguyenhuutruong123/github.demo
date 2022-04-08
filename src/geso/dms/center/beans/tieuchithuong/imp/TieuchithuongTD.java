package geso.dms.center.beans.tieuchithuong.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import geso.dms.center.beans.tieuchithuong.ITieuchithuongTD;
import geso.dms.center.db.sql.dbutils;

public class TieuchithuongTD implements ITieuchithuongTD 
{
	String userId;
	String id;
	String scheme;
	String thang;
	String nam;
	String diengiai;
	
	ResultSet sanphamRs;
	String spIds;
	ResultSet nppRs;
	String nppIds;
	
	ResultSet vungRs;
	String vungIds;
	ResultSet kvRs;
	String kvIds;
	
	String[] diengiaiMuc;
	String[] tumuc;
	String[] denmuc;
	String[] thuongSR;
	String[] thuongTDSR;
	String[] thuongSS;
	String[] thuongTDSS;
	String[] thuongASM;
	String[] thuongTDASM;
	
	String[] diengiaiMuc3;
	String[] tumuc3;
	String[] denmuc3;
	String[] thuongSR3;
	String[] thuongTDSR3;
	String[] thuongSS3;
	String[] thuongTDSS3;
	String[] thuongASM3;
	String[] thuongTDASM3;
	
	String mucvuot;
	String ckMucvuot;
	String dvMucvuot;
	
	
	String hinhthucTra;
	String[] maspTra;
	String[] tenspTra;
	String[] isspTra;
	String[] soluongspTra;
	String[] dongiaspTra;
	
	String doanhsotheoThung;
	String msg;

	String httt;
	String ptchietkhau;
	
	Hashtable<String, String> dieukien;
	Hashtable<String, String> quydoi;
	
	dbutils db;
	
	public TieuchithuongTD()
	{
		this.id = "";
		this.thang = "";
		this.nam = "";
		this.diengiai = "";
		this.msg = "";
		this.scheme = "";
		this.spIds = "";
		this.nppIds = "";
		
		this.mucvuot = "";
		this.ckMucvuot = "";
		this.dvMucvuot = "";
		
		this.vungIds = "";
		this.kvIds = "";
		this.hinhthucTra = "0";		
		
		this.doanhsotheoThung = "0";
		this.httt = "0";
		this.ptchietkhau = "0";
		
		this.dieukien = new Hashtable<String, String>();
		this.quydoi = new Hashtable<String, String>();
	
		this.db = new dbutils();
	}
	
	public TieuchithuongTD(String id)
	{
		this.id = id;
		this.thang = "";
		this.nam = "";
		this.diengiai = "";
		this.msg = "";
		this.scheme = "";
		this.spIds = "";
		this.nppIds = "";
		
		this.mucvuot = "";
		this.ckMucvuot = "";
		this.dvMucvuot = "";
		
		this.vungIds = "";
		this.kvIds = "";
		this.hinhthucTra = "0";	
		
		this.doanhsotheoThung = "0";
		this.httt = "0";
		this.ptchietkhau = "0";
		
		this.dieukien = new Hashtable<String, String>();
		this.quydoi = new Hashtable<String, String>();
		
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

	public String getThang() 
	{
		return this.thang;
	}
	
	public void setThang(String thang) 
	{
		this.thang = thang;
	}
	
	public String getNam() 
	{
		return this.nam;
	}
	
	public void setNam(String nam)
	{
		this.nam = nam;
	}

	public String getId() 
	{
		return this.id;
	}

	public void setId(String id) 
	{
		this.id = id;
	}

	public String getDiengiai()
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}

	public String getMsg()
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	
	public boolean createTctSKU( ) 
	{
		try
		{
			if(this.thang.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn ngày bắt đầu";
				return false;
			}
			
			if(this.nam.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn ngày kết thúc";
				return false;
			}
			
			if(this.scheme.trim().length() <= 0)
			{
				this.msg = "Vui lòng scheme";
				return false;
			}
			
			//Check Scheme
			String query = "select count(*) as sodong from TieuchithuongTD where scheme = N'" + this.scheme + "'";
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				int count = 0;
				if(rs.next())
				{
					count = rs.getInt("sodong");
					if(count > 0)
					{
						this.msg = "Scheme: " + this.scheme + " đã tồn tại, vui lòng nhập scheme khác";
						rs.close();
						return false;
					}
				}
				rs.close();
			}
			
			//Check tieu chi
			if(this.spIds.trim().length() <= 0 )
			{
				this.msg = "Vui lòng kiểm tra lại thông tin nhóm / sản phẩm ";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			query = "insert TieuchithuongTD(scheme, thang, nam, diengiai, trangthai, ngaytao, nguoitao, ngaysua, nguoisua, DOANHSOTHEOTHUNG, HTTT, PT_TRATL) " +
					"values(N'" + this.scheme + "', '" + this.thang + "', '" + this.nam + "', N'" + this.diengiai + "', '0', " +
							"'" + this.getDateTime() + "', '" + this.userId + "', '" + this.getDateTime() + "', '" + this.userId + "', '" + this.doanhsotheoThung + "', '" + this.httt + "', '" + this.ptchietkhau + "')";
			
			System.out.println("1.Insert: " + query);
			if(!db.update(query))
			{
				this.msg = "Khong the tao moi TieuchithuongTD: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if(this.spIds.trim().length() > 0)
			{
				String[] _spIds = this.spIds.split(",");
				for(int i = 0; i < _spIds.length; i++)
				{
					String _dieukien = "NULL";
					String _dvDieukien = "NULL";
					if(this.dieukien.get(_spIds[i]) != null)
					{
						_dieukien = this.dieukien.get(_spIds[i]).split("__")[0].replaceAll(",", "");
						_dvDieukien = this.dieukien.get(_spIds[i]).split("__")[1];
					}
					
					String _quydoi = "NULL";
					String _dvQuydoi = "NULL";
					if(this.quydoi.get(_spIds[i]) != null)
					{
						_quydoi = this.quydoi.get(_spIds[i]).split("__")[0].replaceAll(",", "");
						_dvQuydoi = this.quydoi.get(_spIds[i]).split("__")[1];
					}
				
					if(!_dieukien.equals("NULL") || !_quydoi.equals("NULL"))
					{
						query = "Insert TieuchithuongTD_SANPHAM(thuongtd_fk, sanpham_fk, dieukien, donviDIEUKIEN, quydoi, donviQUYDOI) " +
								"select IDENT_CURRENT('TieuchithuongTD') as tctId, pk_seq, " + _dieukien + ", " + _dvDieukien + ", " + _quydoi + ", " + _dvQuydoi + " from SanPham where pk_seq in (" + _spIds[i] + ") ";
						
						System.out.println("3.Insert SP: " + query);
						if(!db.update(query))
						{
							this.msg = "3.Khong the tao moi TieuchithuongTD_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						}
					}
				}
				
			}
			
			if(this.nppIds.trim().length() > 0)
			{
				query = "Insert TieuchithuongTD_NPP(thuongtd_fk, npp_fk) " +
						"select IDENT_CURRENT('TieuchithuongTD') as tctId, pk_seq from NHAPHANPHOI where pk_seq in (" + this.nppIds + ") ";
				
				System.out.println("4.Insert: " + query);
				if(!db.update(query))
				{
					this.msg = "3.Khong the tao moi TieuchithuongTD_NPP: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			if(this.maspTra != null)
			{
				String spTra = "";
				for(int i = 0; i < this.maspTra.length; i++)
				{
					if(this.maspTra[i].trim().length() > 0)
					{
						String dg = this.dongiaspTra[i].trim().length() <= 0 ? "0" : this.dongiaspTra[i].trim();
						if(this.hinhthucTra.equals("0"))
						{
							spTra += " select pk_seq, null as soluong, '" + dg + "' as dongia from SANPHAM where ma = '" + this.maspTra[i] + "' ";
							spTra += " union ";
						}
						else
						{
							if(this.soluongspTra[i].trim().length() > 0)
							{
								spTra += " select pk_seq, null as soluong, '" + dg + "' as dongia from SANPHAM where ma = '" + this.maspTra[i] + "' ";
								spTra += " union ";
							}
						}
					}
				}
				
				if(spTra.trim().length() > 0)
				{	
					spTra = spTra.substring(0, spTra.length() - 6);
				
					query = "Insert TIEUCHITHUONGTD_SPTRA(thuongtd_fk, sanpham_fk, soluong, dongia) " +
							"select IDENT_CURRENT('TieuchithuongTD') as tctId, * from ( " + spTra + " ) sp where sp.pk_seq is not null ";
					
					System.out.println("5.Insert: " + query);
					if(!db.update(query))
					{
						this.msg = "5.Khong the tao moi TIEUCHITHUONGTD_SPTRA: " + query;
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
			try 
			{
				db.getConnection().rollback();
				System.out.println("__EXCEPTION UPDATE: " + e.getMessage());
			} 
			catch (SQLException e1) {}
		}
		
		return true;
	}

	public boolean updateTctSKU()
	{
		try
		{
			if(this.thang.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn ngày bắt đầu";
				return false;
			}
			
			if(this.nam.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn ngày kết thúc";
				return false;
			}
			
			if(this.scheme.trim().length() <= 0)
			{
				this.msg = "Vui lòng scheme";
				return false;
			}
			
			
			//Check Scheme
			String query = "select count(*) as sodong from TieuchithuongTD where scheme = N'" + this.scheme + "' and pk_seq != '" + this.id + "'";
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				int count = 0;
				if(rs.next())
				{
					count = rs.getInt("sodong");
					if(count > 0)
					{
						this.msg = "Scheme: " + this.scheme + " đã tồn tại, vui lòng nhập scheme khác";
						rs.close();
						return false;
					}
				}
				rs.close();
			}
			
			if(this.spIds.trim().length() <= 0 )
			{
				this.msg = "Vui lòng kiểm tra lại thông tin nhóm / sản phẩm ";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			query = "update TieuchithuongTD set scheme = N'" + this.scheme + "', thang = '" + this.thang + "', nam = '" + this.nam + "', diengiai = N'" + this.diengiai + "', " +
						"ngaysua = '" + this.getDateTime() + "', nguoisua = '" + this.userId + "', DOANHSOTHEOTHUNG = '" + this.doanhsotheoThung + "', HTTT = '" + this.httt + "', PT_TRATL = '" + this.ptchietkhau + "' " +
					"where pk_seq = '" + this.id + "'";
					
			System.out.println("1.Update: " + query);
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat TieuchithuongTD: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete TieuchithuongTD_SANPHAM where thuongtd_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat TieuchithuongTD_SANPHAM: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete TieuchithuongTD_NPP where thuongtd_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat TieuchithuongTD_NPP: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete TIEUCHITHUONGTD_SPTRA where thuongtd_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat TIEUCHITHUONGTD_SPTRA: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if(this.spIds.trim().length() > 0)
			{
				String[] _spIds = this.spIds.split(",");
				for(int i = 0; i < _spIds.length; i++)
				{
					String _dieukien = "NULL";
					String _dvDieukien = "NULL";
					if(this.dieukien.get(_spIds[i]) != null)
					{
						_dieukien = this.dieukien.get(_spIds[i]).split("__")[0].replaceAll(",", "");
						_dvDieukien = this.dieukien.get(_spIds[i]).split("__")[1];
					}
					
					String _quydoi = "NULL";
					String _dvQuydoi = "NULL";
					if(this.quydoi.get(_spIds[i]) != null)
					{
						_quydoi = this.quydoi.get(_spIds[i]).split("__")[0].replaceAll(",", "");
						_dvQuydoi = this.quydoi.get(_spIds[i]).split("__")[1];
					}
				
					if(!_dieukien.equals("NULL") || !_quydoi.equals("NULL"))
					{
						query = "Insert TieuchithuongTD_SANPHAM(thuongtd_fk, sanpham_fk, dieukien, donviDIEUKIEN, quydoi, donviQUYDOI) " +
								"select '" + this.id + "' as tctId, pk_seq, " + _dieukien + ", " + _dvDieukien + ", " + _quydoi + ", " + _dvQuydoi + " from SanPham where pk_seq in (" + _spIds[i] + ") ";
						
						System.out.println("3.Insert SP: " + query);
						if(!db.update(query))
						{
							this.msg = "3.Khong the tao moi TieuchithuongTD_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						}
					}
				}
				
			}
			
			if(this.nppIds.trim().length() > 0)
			{
				query = "Insert TieuchithuongTD_NPP(thuongtd_fk, npp_fk) " +
						"select '" + this.id + "' as tctId, pk_seq from NHAPHANPHOI where pk_seq in (" + this.nppIds + ") ";
				
				System.out.println("4.Insert: " + query);
				if(!db.update(query))
				{
					this.msg = "3.Khong the tao moi TieuchithuongTD_NPP: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			if(this.maspTra != null)
			{
				String spTra = "";
				for(int i = 0; i < this.maspTra.length; i++)
				{
					if(this.maspTra[i].trim().length() > 0)
					{
						String dg = this.dongiaspTra[i].trim().length() <= 0 ? "0" : this.dongiaspTra[i].trim();
						if(this.hinhthucTra.equals("0"))
						{
							spTra += " select pk_seq, null as soluong, '" + dg + "' as dongia from SANPHAM where ma = '" + this.maspTra[i] + "' ";
							spTra += " union ";
						}
						else
						{
							if(this.soluongspTra[i].trim().length() > 0)
							{
								spTra += " select pk_seq, null as soluong, '" + dg + "' as dongia from SANPHAM where ma = '" + this.maspTra[i] + "' ";
								spTra += " union ";
							}
						}
					}
				}
				
				if(spTra.trim().length() > 0)
				{	
					spTra = spTra.substring(0, spTra.length() - 6);
				
					query = "Insert TIEUCHITHUONGTD_SPTRA(thuongtd_fk, sanpham_fk, soluong, dongia) " +
							"select '" + this.id + "' as tctId, * from ( " + spTra + " ) sp where sp.pk_seq is not null ";
					
					System.out.println("5.Insert: " + query);
					if(!db.update(query))
					{
						this.msg = "5.Khong the tao moi TIEUCHITHUONGTD_SPTRA: " + query;
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
			try 
			{
				db.getConnection().rollback();
				System.out.println("__EXCEPTION UPDATE: " + e.getMessage());
			} catch (SQLException e1) {}
			
			System.out.println("115.Error: " + e.getMessage());
		}
		
		return true;
	}

	public void init() 
	{
		String query = "select scheme, thang, nam, diengiai, mucvuot, chietkhauMucVuot, donviMucVuot, hinhthuctra, DOANHSOTHEOTHUNG, HTTT, PT_TRATL  " +
					   "from TieuchithuongTD where pk_seq = '" + this.id + "'";
		
		System.out.println("__Khoi tao tieu chi thuong: " + query);
		
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				//NumberFormat format = new DecimalFormat("#,###,###");
				while(rs.next())
				{
					this.scheme = rs.getString("scheme");
					this.thang = rs.getString("thang");
					this.nam = rs.getString("nam");					
					this.diengiai= rs.getString("diengiai");
					
					/*this.mucvuot = rs.getString("mucvuot") != null ? format.format(rs.getDouble("mucvuot")) : "";
					this.ckMucvuot = rs.getString("chietkhauMucVuot") != null ? format.format(rs.getDouble("chietkhauMucVuot")) : "";
					this.dvMucvuot = rs.getString("donviMucVuot");
					this.hinhthucTra = rs.getString("hinhthuctra");*/
					
					this.doanhsotheoThung = rs.getString("DOANHSOTHEOTHUNG");
					this.httt = rs.getString("HTTT");
					this.ptchietkhau = rs.getString("PT_TRATL");
					
				}
				rs.close();
			} 
			catch (Exception e)
			{
				System.out.println("115.Error Meg: " + e.getMessage());
				e.printStackTrace();
			}
		}
		
		//Init San Pham Tra
		query = "select b.MA, b.TEN, isnull(a.soluong, 0) as soluong, isnull(a.dongia, 0) as dongia " +
				"from TIEUCHITHUONGTD_SPTRA a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ " +
				"where a.thuongtd_fk = '" + this.id + "'";
		
		System.out.println("__LAY SP TRA: " + query);
		rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				String spMa = "";
				String spTen = "";
				String spSoluong = "";
				String spDongia = "";
				
				while(rs.next())
				{
					spMa += rs.getString("MA") + ",,";
					spTen += rs.getString("TEN") + ",,";
					spDongia += rs.getString("dongia") + ",,";
					
					if(this.hinhthucTra.equals("1"))
						spSoluong += rs.getString("soluong") + ",,";
					else
						spSoluong += " " + ",,";
						
				}
				rs.close();
				
				System.out.println("---TEN SP: " + spTen + "  -- MA SP: " + spMa);
				if(spMa.trim().length() > 0)
				{
					spMa = spMa.substring(0, spMa.length() - 2);
					this.maspTra = spMa.split(",,");
					
					spTen = spTen.substring(0, spTen.length() - 2);
					this.tenspTra = spTen.split(",,");
				}
				
				if(spSoluong.trim().length() > 0)
				{
					spSoluong = spSoluong.substring(0, spSoluong.length() - 2);
					this.soluongspTra = spSoluong.split(",,");
				}
				
				if(spDongia.trim().length() > 0)
				{
					spDongia = spDongia.substring(0, spDongia.length() - 2);
					this.dongiaspTra = spDongia.split(",,");
				}
				
			} 
			catch (Exception e) { System.out.println("__EXCEPTION INIT: " + e.getMessage());  }		
		}
		
		this.createNdk();
		this.createRs();
	}
	
	private void createNdk() 
	{
		String query = "select sanpham_fk, dieukien, donviDIEUKIEN, quydoi, donviQUYDOI " + 
					   " from TieuchithuongTD_SANPHAM where thuongtd_fk = '" + this.id + "' ";
		System.out.println("___KHOI TAO SP: " + query);
		
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				String spId = "";
				Hashtable<String, String> htdieukien = new Hashtable<String, String>();
				Hashtable<String, String> htquydoi = new Hashtable<String, String>();
				
				while(rs.next())
				{
					spId += rs.getString("sanpham_fk") + ",";
					
					if(rs.getString("dieukien") != null)
					{
						htdieukien.put(rs.getString("sanpham_fk"), rs.getString("dieukien") + "__" + rs.getString("donviDIEUKIEN"));
					}
					
					if(rs.getString("quydoi") != null)
					{
						htquydoi.put(rs.getString("sanpham_fk"), rs.getString("quydoi") + "__" + rs.getString("donviQUYDOI"));
					}
				}
				rs.close();
				
				if(spId.trim().length() > 0)
				{
					this.spIds = spId.substring(0, spId.length() - 1);
					
					this.dieukien = htdieukien;
					this.quydoi = htquydoi;
				}
			} 
			catch (Exception e) 
			{
				System.out.println("33.Loi khoi tao: " + e.toString());
				e.printStackTrace();
			}
		}
		
		System.out.println("__SP ID: " + this.spIds);
		
		
		query = "select npp_fk from TieuchithuongTD_NPP where thuongtd_fk = '" + this.id + "' ";
		System.out.println("___KHOI TAO NPP: " + query);
		
		rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				String nppId = "";
				while(rs.next())
				{
					nppId += rs.getString("npp_fk") + ",";
				}
				rs.close();
				
				if(nppId.trim().length() > 0)
					this.nppIds = nppId.substring(0, nppId.length() - 1);
			} 
			catch (Exception e) 
			{
				System.out.println("33.Loi khoi tao: " + e.toString());
			}
		}
		
		System.out.println("__NPP ID: " + this.nppIds);
		
	}

	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public String getScheme()
	{
		return this.scheme;
	}

	public void setScheme(String scheme) 
	{
		this.scheme = scheme;
	}
	
	public String[] getDiengiaiMuc() {
		
		return this.diengiaiMuc;
	}

	
	public void setDiengiaiMuc(String[] diengiai) {
		
		this.diengiaiMuc = diengiai;
	}

	
	public String[] getTumuc() {
		
		return this.tumuc;
	}

	
	public void setTumuc(String[] tumuc) {
		
		this.tumuc = tumuc;
	}

	
	public String[] getDenmuc() {
		
		return this.denmuc;
	}

	
	public void setDenmuc(String[] denmuc) {
		
		this.denmuc = denmuc;
	}

	
	public String[] getThuongSR() {
		
		return this.thuongSR;
	}

	
	public void setThuongSR(String[] thuongSR) {
		
		this.thuongSR = thuongSR;
	}

	
	public String[] getThuongTDSR() {
		
		return this.thuongTDSR;
	}

	
	public void setThuongTDSR(String[] thuongTDSR) {
		
		this.thuongTDSR = thuongTDSR;
	}

	
	public String[] getThuongSS() {
		
		return this.thuongSS;
	}

	
	public void setThuongSS(String[] thuongSS) {
		
		this.thuongSS = thuongSS;
	}

	
	public String[] getThuongTDSS() {
		
		return this.thuongTDSS;
	}

	
	public void setThuongTDSS(String[] thuongTDSS) {
		
		this.thuongTDSS = thuongTDSS;
	}

	
	public String[] getThuongASM() {
		
		return this.thuongASM;
	}

	
	public void setThuongASM(String[] thuongASM) {
		
		this.thuongASM = thuongASM;
	}

	
	public String[] getThuongTDASM() {
		
		return this.thuongTDASM;
	}

	
	public void setThuongTDASM(String[] thuongTDASM) {
		
		this.thuongTDASM = thuongTDASM;
	}

	public void createRs() {
		
		String query = "";
		
		if(this.httt.equals("0"))
			query = "select PK_SEQ, MA, TEN, TRANGTHAI, NHANHANG_FK, CHUNGLOAI_FK  from SanPham where trangthai = '1' ";
		else
			query = "select PK_SEQ, DIENGIAI AS MA, TEN, TRANGTHAI, 1 as NHANHANG_FK, 1 as CHUNGLOAI_FK  from NhomSanPham where trangthai = '1' ";
			
		/*if(this.id.trim().length() > 0)
		{
			query += " union select PK_SEQ, MA, TEN, TRANGTHAI, NHANHANG_FK, CHUNGLOAI_FK from SanPham where pk_seq in ( select sanpham_fk from TieuchithuongTD_SANPHAM where thuongtl_fk = '" + this.id + "' ) ";
		}*/
		
		query += " order by TRANGTHAI desc, NHANHANG_FK asc, CHUNGLOAI_FK asc ";
		this.sanphamRs = db.getScrol(query);
		
		
		this.vungRs = db.get("select pk_seq, ten from VUNG where trangthai = '1'");
		
		query = "select pk_seq, ten from KHUVUC where trangthai = '1'";
		if(this.vungIds.trim().length() > 0)
			query += " and vung_fk in ( " + this.vungIds + " ) "; 
		this.kvRs = db.get(query);
		
		query = "select PK_SEQ, MA, TEN  from NhaPhanPhoi where trangthai = '1' and PRIANDSECOND = '0' ";
		if(this.kvIds.trim().length() > 0)
			query += " and khuvuc_fk in ( " +  this.kvIds + " ) ";
		if(this.vungIds.trim().length() > 0)
			query += " and khuvuc_fk in ( select pk_seq from KhuVuc where trangthai = '1' and vung_fk in ( " + this.vungIds + " ) ) ";
		
		if(this.id.trim().length() > 0)
		{
			query += " union select PK_SEQ, MA, TEN from NhaPhanPhoi where pk_seq in ( select npp_fk from TieuchithuongTD_NPP where thuongtd_fk = '" + this.id + "' ) ";
		}
		
		//
		if(this.nppIds.trim().length() > 0)
		{
			query += " union select PK_SEQ, MA, TEN from NhaPhanPhoi where pk_seq in ( " + this.nppIds + " ) ";
		}
		
		query += " order by PK_SEQ desc ";
		this.nppRs = db.getScrol(query);
		
	}
	
	public String[] getDiengiaiMuc3() {
		
		return this.diengiaiMuc3;
	}

	
	public void setDiengiaiMuc3(String[] diengiai) {
		
		this.diengiaiMuc3 = diengiai;
	}

	
	public String[] getTumuc3() {
		
		return this.tumuc3;
	}

	
	public void setTumuc3(String[] tumuc) {
		
		this.tumuc3 = tumuc;
	}

	
	public String[] getDenmuc3() {
		
		return this.denmuc3;
	}

	
	public void setDenmuc3(String[] denmuc) {
		
		this.denmuc3 = denmuc;
	}
	
	
	public String[] getThuongSR3() {
		
		return this.thuongSR3;
	}

	
	public void setThuongSR3(String[] thuongSR) {
		
		this.thuongSR3 = thuongSR;
	}

	
	public String[] getThuongTDSR3() {
		
		return this.thuongTDSR3;
	}

	
	public void setThuongTDSR3(String[] thuongTDSR) {
		
		this.thuongTDSR3 = thuongTDSR;
	}

	
	public String[] getThuongSS3() {
		
		return this.thuongSS3;
	}

	
	public void setThuongSS3(String[] thuongSS) {
		
		this.thuongSS3 = thuongSS;
	}

	
	public String[] getThuongTDSS3() {
		
		return this.thuongTDSS3;
	}

	
	public void setThuongTDSS3(String[] thuongTDSS) {
		
		this.thuongTDSS3 = thuongTDSS;
	}

	
	public String[] getThuongASM3() {
		
		return this.thuongASM3;
	}

	
	public void setThuongASM3(String[] thuongASM) {
		
		this.thuongASM3 = thuongASM;
	}

	
	public String[] getThuongTDASM3() {
		
		return this.thuongTDASM3;
	}

	
	public void setThuongTDASM3(String[] thuongTDASM) {
		
		this.thuongTDASM3 = thuongTDASM;
	}

	
	public void setSanphamRs(ResultSet spRs) {
		
		this.sanphamRs = spRs;
	}

	
	public ResultSet getSanphamRs() {
		
		return this.sanphamRs;
	}

	
	public String getSpIds() {
		
		return this.spIds;
	}

	
	public void setSpIds(String spIds) {
		
		this.spIds = spIds;
	}

	
	public void setNppRs(ResultSet nppRs) {
		
		this.nppRs = nppRs;
	}

	
	public ResultSet getNppRs() {
		
		return this.nppRs;
	}

	
	public String getNppIds() {
		
		return this.nppIds;
	}

	
	public void setNppIds(String nppIds) {
		
		this.nppIds = nppIds;
	}

	
	public String getMucvuot() {
		
		return this.mucvuot;
	}

	
	public void setMucvuot(String mucvuot) {
		
		this.mucvuot = mucvuot;
	}

	
	public String getChietkhauMucvuot() {
		
		return this.ckMucvuot;
	}

	
	public void setChietkhauMucvuot(String ckMv) {
		
		this.ckMucvuot = ckMv;
	}

	
	public String getDonviMucvuot() {
		
		return this.dvMucvuot;
	}

	
	public void setDonviMucvuot(String dvMv) {
		
		this.dvMucvuot = dvMv;
	}

	
	public void setVungRs(ResultSet vungRs) {
		
		this.vungRs = vungRs;
	}

	
	public ResultSet getVungRs() {
		
		return this.vungRs;
	}

	
	public String getVungIds() {
		
		return this.vungIds;
	}

	
	public void setVungIds(String vungIds) {
		
		this.vungIds = vungIds;
	}

	
	public void setKhuvucRs(ResultSet kvRs) {
		
		this.kvRs = kvRs;
	}

	
	public ResultSet getKhuvucRs() {
		
		return this.kvRs;
	}

	
	public String getKhuvucIds() {
		
		return this.kvIds;
	}

	
	public void setKhuvucIds(String kvIds) {
		
		this.kvIds = kvIds;
	}

	
	public String getHinhthuctra() {
		
		return this.hinhthucTra;
	}

	
	public void setHinhthuctra(String htTra) {
		
		this.hinhthucTra = htTra;
	}

	
	public String[] getMaspTra() {
		
		return this.maspTra;
	}

	
	public void setMaspTra(String[] maspTra) {
		
		this.maspTra = maspTra;
	}

	
	public String[] getTenspTra() {
		
		return this.tenspTra;
	}

	
	public void setTenspTra(String[] tenspTra) {
		
		this.tenspTra = tenspTra;
	}

	
	public String[] getSoluongspTra() {
		
		return this.soluongspTra;
	}

	
	public void setSoluongspTra(String[] soluongspTra) {
		
		this.soluongspTra = soluongspTra;
	}

	
	public String[] getIdspTra() {
		
		return this.isspTra;
	}

	
	public void setIdspTra(String[] idspTra) {
		
		this.isspTra = idspTra;
	}

	
	public String getDoanhsotheoThung() {
		
		return this.doanhsotheoThung;
	}

	
	public void setDoanhsotheoThung(String isThung) {
		
		this.doanhsotheoThung = isThung;
	}

	
	public String getHTTT() {
		
		return this.httt;
	}

	
	public void setHTTT(String httt) {
		
		this.httt = httt;
	}

	
	public String getPT_TRACK() {
		
		return this.ptchietkhau;
	}

	
	public void setPT_TRACK(String ptTRACK) {
		
		this.ptchietkhau = ptTRACK;
	}


	public String[] getDongiaspTra() {

		return this.dongiaspTra;
	}


	public void setDongiaspTra(String[] dongiaspTra) {
		
		this.dongiaspTra = dongiaspTra;
	}

	
	public Hashtable<String, String> getDieukien() {
		
		return this.dieukien;
	}

	
	public void setDieukien(Hashtable<String, String> dieukien) {
		
		this.dieukien = dieukien;
	}

	
	public Hashtable<String, String> getQuydoi() {
		
		return this.quydoi;
	}

	
	public void setQuydoi(Hashtable<String, String> quydoi) {
		
		this.quydoi = quydoi;
	}
	
	

	

}
