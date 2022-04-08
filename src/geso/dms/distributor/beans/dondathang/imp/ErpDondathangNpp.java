package geso.dms.distributor.beans.dondathang.imp;

import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.OracleConnUtils;
import geso.dms.distributor.beans.dondathang.IErpDondathangNpp;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

public class ErpDondathangNpp implements IErpDondathangNpp
{

	String userId;
	String id;
	String trangthai = "0";
	String ngayyeucau;
	String ngaydenghi;
	String ghichu;

	String msg;
	

	String loaidonhang;  //0 đơn đặt hàng, 1 đơn hàng khuyến mại ứng hàng, 3 đơn hàng khuyến mại tích lũy, 4 đơn hàng trưng bày, 5 đơn hàng khác
	String chietkhau;
	String vat;

	String khoNhanId;
	ResultSet khoNhanRs;

	String nppId;
	ResultSet nppRs;

	String nppTen;
	String sitecode;

	String dvkdId;
	ResultSet dvkdRs;

	String kbhId;
	ResultSet kbhRs;

	ResultSet dvtRs;

	String schemeId;
	ResultSet schemeRs;

	Hashtable<String, String> scheme_soluong;
	
	boolean aplaikm;

	String[] spId;
	String[] spMa;
	String[] spTen;
	String[] spDonvi;
	String[] spTonkho;
	String[] spSoluong;
	String[] spSoluongNppDat;
	String[] spGianhap;
	String[] spChietkhau;
	String[] spSCheme;

	String[] dhCk_diengiai;
	String[] dhCk_giatri;
	String[] dhCk_loai;

	ResultSet congnoRs;
	
	String congno;

	String tiengiam;
	
	dbutils db;

	public ErpDondathangNpp()
	{
		this.id = "";
		this.ngayyeucau = getDateTime();
		this.ngaydenghi = getDateTime_CongMot();
		this.ghichu = "";
		this.khoNhanId = "";
		this.nppId = "";
		this.msg = "";
		this.loaidonhang = "0";
		this.trangthai = "0";
		this.chietkhau = "0";
		this.vat = "10";
		this.tiengiam = "0";
		this.dvkdId = "";
		this.kbhId = "";
		this.schemeId = "";

		this.scheme_soluong = new Hashtable<String, String>();

		this.dhCk_diengiai = new String[]{"Hoa hồng", "Lương NVBH", "Thưởng đạt chỉ tiêu", "Khác"};
		this.dhCk_giatri = new String[]{"6", "0", "0", "0"};
		this.dhCk_loai = new String[]{"1", "0", "0", "0"};

		this.db = new dbutils();
		
		this.aplaikm = false;
		
		this.congno = "";
	}

	public ErpDondathangNpp(String id)
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
		this.tiengiam = "0";
		this.dvkdId = "";
		this.kbhId = "";
		this.schemeId = "";

		this.scheme_soluong = new Hashtable<String, String>();

		this.dhCk_diengiai = new String[]{"Hoa hồng", "Lương NVBH", "Thưởng đạt chỉ tiêu", "Khác"};
		this.dhCk_giatri = new String[]{"6", "0", "0", "0"};
		this.dhCk_loai = new String[]{"1", "0", "0", "0"};

		this.db = new dbutils();
		
		this.aplaikm = false;
		
		this.congno = "";
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

	public boolean createNK() 
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

		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm đặt hàng";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spMa.length; i++)
			{
				if( spMa[i].trim().length() > 0 )
				{
					if(spSoluong[i].trim().length() <= 0)
					{
						this.msg = "Bạn phải nhập số lượng của sản phẩm ( " + spTen[i] + " )";
						return false;
					}

					if(spGianhap[i].trim().length() <= 0)
					{
						this.msg = "Bạn phải nhập đơn giá của sản phẩm ( " + spTen[i] + " )";
						return false;
					}

					if(spDonvi[i].trim().length() <= 0)
					{
						this.msg = "Bạn phải nhập đơn vị  của sản phẩm ( " + spTen[i] + " )";
						return false;
					}

					coSP = true;
				}
			}

			if(!coSP)
			{
				this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm";
				return false;
			}

			//CHECK TRUNG MA 
			for(int i = 0; i < spMa.length - 1; i++)
			{
				for(int j = i + 1; j < spMa.length; j++)
				{
					if(spMa[i].trim().length() > 0 && spMa[j].trim().length() > 0 )
					{
						if( spMa[i].trim().equals(spMa[j].trim()) )
						{
							this.msg = "Sản phẩm ( " + spTen[i] + " )  đã bị trùng.";
							return false;
						}
					}
				}
			}	
		}

		try
		{
			db.getConnection().setAutoCommit(false);

			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
			String chietkhau = this.chietkhau.trim().length() <= 0 ? "0" : this.chietkhau.trim();
			String vat = this.vat.trim().length() <= 0 ? "0" : this.vat.trim();

			String query =  " insert ERP_Dondathang(ChietKhau,ngaydonhang, ngaydenghi, loaidonhang, ghichu, trangthai, dvkd_fk, kbh_fk, npp_fk, kho_fk, vat, ngaytao, nguoitao, ngaysua, nguoisua, NPP_DACHOT, tiengiam) " +
							" select  isnull(npp.chietkhau,0),?, ?, ?, ?, '0', ?, ?, ?, ?, ?, ?, ?, ?, ?, '0', ?" +
							" from nhaphanphoi npp where pk_seq = ?";

			System.out.println("1.Insert DDH: " + query);
			if(db.updateQueryByPreparedStatement(query, new Object[]{this.ngayyeucau, this.ngaydenghi
					, this.loaidonhang, this.ghichu, dvkdId, kbhId, nppId, khonhan_fk, vat, getDateTime()
					, this.userId, getDateTime(), this.userId, this.tiengiam, this.nppId}) < 1)
			{
				this.msg = "Không thể tạo mới đơn hàng ";
				db.getConnection().rollback();
				return false;
			}

			
			
			this.id = db.getPk_seq();

			System.out.println("DDH ID: " + this.id);

			if(this.loaidonhang.equals("0"))  //DON DAT HANG CHI LUU NHUNG SP KO CO SCHEME
			{
				for(int i = 0; i < spMa.length; i++)
				{
					if(spMa[i].trim().length() > 0 && spSCheme[i].trim().length() <= 0 )
					{
						//CHECK SP NAY DA CO KHAI QUY CACH CHUA?
						query =    "select sp.ten, dv.donvi, case when sp.dvdl_fk != dv.pk_seq   " +
								"	then ISNULL( ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = sp.PK_SEQ and DVDL1_FK = sp.DVDL_FK and DVDL2_FK = dv.pk_seq ), -1 ) else 1 end as quycach   "  +
								"from SANPHAM sp, DONVIDOLUONG dv " +
								"where sp.MA = ? and dv.donvi = ?   ";

						System.out.println("-----CHECK QUY CACH: " + query );
						ResultSet rs = db.getByPreparedStatement(query, new Object[]{spMa[i].trim(), spDonvi[i].trim()});
						if(rs.next())
						{
							if(rs.getDouble("quycach") <= 0)
							{
								this.msg = "Sản phẩm ( " + rs.getString("ten") + " ) với đơn vị đặt ( " + rs.getString("donvi") + " ) chưa thiết lập quy cách trong DLN ";
								rs.close();
								db.getConnection().rollback();
								return false;
							}
						}
						rs.close();

						query = "insert ERP_Dondathang_SANPHAM( isNppDat,Dondathang_fk, SANPHAM_FK, tonkho, soluong, soluongttduyet, dongia, thueVAT, dvdl_fk,DonGiaGoc,BANGGIAMUANPP_FK,SoTT, chietkhau ) " +
								"select 1,?, pk_seq, ?, ?, ?, ?, ?, ( select pk_Seq from DONVIDOLUONG where donvi = ? ),?,?,? " +
								", ? from SANPHAM where MA = ? ";

						System.out.println("1.Insert NK - SP: " + query);
						if(db.updateQueryByPreparedStatement(query, new Object[]{this.id, spTonkho[i].replace(",", "")
								, spSoluong[i].replaceAll(",", ""), spSoluong[i].replaceAll(",", "")
								, spGianhap[i].replaceAll(",", ""), spVAT[i].replaceAll(",", ""), spDonvi[i].trim()
								,spGiagoc[i].replaceAll(",", ""),spBgId[i],i,spChietkhau[i].replaceAll(",", ""), spMa[i].trim()}) < 1)
						{
							this.msg = "Khong the tao moi san pham cho don hang";
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}
			else
			{
				for(int i = 0; i < spMa.length; i++)
				{
					if(spMa[i].trim().length() > 0)
					{
						//CHECK SP NAY DA CO KHAI QUY CACH CHUA?
						query =    "select sp.ten, dv.donvi, case when sp.dvdl_fk != dv.pk_seq   " +
								"	then ISNULL( ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = sp.PK_SEQ and DVDL1_FK = sp.DVDL_FK and DVDL2_FK = dv.pk_seq ), -1 ) else 1 end as quycach   "  +
								"from SANPHAM sp, DONVIDOLUONG dv " +
								"where sp.MA = ? and dv.donvi = ?";

						System.out.println("-----CHECK QUY CACH: " + query );
						ResultSet rs = db.getByPreparedStatement(query, new Object[]{spMa[i].trim(), spDonvi[i].trim()});
						if(rs.next())
						{
							if(rs.getDouble("quycach") <= 0)
							{
								this.msg = "Sản phẩm ( " + rs.getString("ten") + " ) với đơn vị đặt ( " + rs.getString("donvi") + " ) chưa thiết lập quy cách trong DLN ";
								rs.close();
								db.getConnection().rollback();
								return false;
							}
						}
						rs.close();

						query = "insert ERP_Dondathang_SANPHAM( isNppDat,Dondathang_fk, SANPHAM_FK, tonkho, soluong, soluongttduyet, dongia, thueVAT, dvdl_fk,DonGiaGoc,BANGGIAMUANPP_FK,SoTT, chietkhau ) " +
								"select 1,?, pk_seq, ?, ?, ?, ?, ?, ( select pk_Seq from DONVIDOLUONG where donvi = ? ),?,?,? " +
								", ? from SANPHAM where MA = ? ";

						System.out.println("2.Insert NK - SP: " + query);
						if(db.updateQueryByPreparedStatement(query, new Object[]{this.id, spTonkho[i].replace(",", "")
								,spSoluong[i].replaceAll(",", ""), spSoluong[i].replaceAll(",", "")
								, spGianhap[i].replaceAll(",", ""), spVAT[i].replaceAll(",", ""),spDonvi[i].trim()
								,spGiagoc[i].replaceAll(",", ""),spBgId[i],i,spChietkhau[i].replaceAll(",", "")
								,spMa[i].trim()}) < 1)
						{
							this.msg = "Khong the tao moi san pham cho don hang";
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}

			query=
					" INSERT INTO ERP_DONDATHANG_SANPHAM_LOG(DONDATHANG_FK,SANPHAM_FK, TONKHO,SOLUONG, SOLUONGTTDUYET,DONGIA,DVDL_FK,CHIETKHAU,SOTT,THUEVAT,DonGiaGoc,BANGGIAMUANPP_FK) "+ 
							" SELECT DONDATHANG_FK,SANPHAM_FK, TONKHO,SOLUONG, SOLUONGTTDUYET,DONGIA,DVDL_FK,CHIETKHAU,SOTT,THUEVAT,DonGiaGoc,BANGGIAMUANPP_FK  "+
							" FROM ERP_DONDATHANG_SANPHAM" +
							" where DONDATHANG_FK=?";
			if(db.updateQueryByPreparedStatement(query, new Object[]{this.id}) < 1)
			{
				this.msg = "Khong the tao moi san pham cho don hang ";
				db.getConnection().rollback();
				return false;
			}
			
			String msgUpdate = geso.dms.distributor.util.Utility.Update_GiaTri_ERP_DonDatHang(this.id, db,true);			
			if(msgUpdate.trim().length() > 0)
			{
				this.msg = msgUpdate;
				db.getConnection().rollback();
				return false;
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
	
	/*public boolean createNK() 
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

		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm đặt hàng";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spMa.length; i++)
			{
				if( spMa[i].trim().length() > 0 )
				{
					if(spSoluong[i].trim().length() <= 0)
					{
						this.msg = "Bạn phải nhập số lượng của sản phẩm ( " + spTen[i] + " )";
						return false;
					}

					if(spGianhap[i].trim().length() <= 0)
					{
						this.msg = "Bạn phải nhập đơn giá của sản phẩm ( " + spTen[i] + " )";
						return false;
					}

					if(spDonvi[i].trim().length() <= 0)
					{
						this.msg = "Bạn phải nhập đơn vị  của sản phẩm ( " + spTen[i] + " )";
						return false;
					}

					coSP = true;
				}
			}

			if(!coSP)
			{
				this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm";
				return false;
			}

			//CHECK TRUNG MA 
			for(int i = 0; i < spMa.length - 1; i++)
			{
				for(int j = i + 1; j < spMa.length; j++)
				{
					if(spMa[i].trim().length() > 0 && spMa[j].trim().length() > 0 )
					{
						if( spMa[i].trim().equals(spMa[j].trim()) )
						{
							this.msg = "Sản phẩm ( " + spTen[i] + " )  đã bị trùng.";
							return false;
						}
					}
				}
			}	
		}

		try
		{
			db.getConnection().setAutoCommit(false);

			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
			String chietkhau = this.chietkhau.trim().length() <= 0 ? "0" : this.chietkhau.trim();
			String vat = this.vat.trim().length() <= 0 ? "0" : this.vat.trim();

			String query =  " insert ERP_Dondathang(ChietKhau,ngaydonhang, ngaydenghi, loaidonhang, ghichu, trangthai, dvkd_fk, kbh_fk, npp_fk, kho_fk, vat, ngaytao, nguoitao, ngaysua, nguoisua, NPP_DACHOT, tiengiam) " +
							" select  isnull(npp.chietkhau,0),'" + this.ngayyeucau + "', '" + this.ngaydenghi + "', '" + this.loaidonhang + "', N'" + this.ghichu + "', '0', '" + dvkdId + "', '" + kbhId + "', '" + nppId + "', " + khonhan_fk + ", '" + vat + "', '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "', '0', '"+this.tiengiam+"' " +
							" from nhaphanphoi npp where pk_seq = "+ this.nppId;

			System.out.println("1.Insert DDH: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_Dondathang " + query;
				db.getConnection().rollback();
				return false;
			}

			//LAY ID
			ResultSet rsDDH = db.get("select scope_identity() as ID ");
			if(rsDDH.next())
			{
				this.id = rsDDH.getString("ID");
			}
			rsDDH.close();

			System.out.println("DDH ID: " + this.id);

			if(this.loaidonhang.equals("0"))  //DON DAT HANG CHI LUU NHUNG SP KO CO SCHEME
			{
				for(int i = 0; i < spMa.length; i++)
				{
					if(spMa[i].trim().length() > 0 && spSCheme[i].trim().length() <= 0 )
					{
						//CHECK SP NAY DA CO KHAI QUY CACH CHUA?
						query =    "select sp.ten, dv.donvi, case when sp.dvdl_fk != dv.pk_seq   " +
								"	then ISNULL( ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = sp.PK_SEQ and DVDL1_FK = sp.DVDL_FK and DVDL2_FK = dv.pk_seq ), -1 ) else 1 end as quycach   "  +
								"from SANPHAM sp, DONVIDOLUONG dv " +
								"where sp.MA = '" + spMa[i].trim() + "' and dv.donvi = N'" + spDonvi[i].trim() + "'   ";

						System.out.println("-----CHECK QUY CACH: " + query );
						ResultSet rs = db.get(query);
						if(rs.next())
						{
							if(rs.getDouble("quycach") <= 0)
							{
								this.msg = "Sản phẩm ( " + rs.getString("ten") + " ) với đơn vị đặt ( " + rs.getString("donvi") + " ) chưa thiết lập quy cách trong DLN ";
								rs.close();
								db.getConnection().rollback();
								return false;
							}
						}
						rs.close();

						query = "insert ERP_Dondathang_SANPHAM( isNppDat,Dondathang_fk, SANPHAM_FK, tonkho, soluong, soluongttduyet, dongia, thueVAT, dvdl_fk,DonGiaGoc,BANGGIAMUANPP_FK,SoTT, chietkhau ) " +
								"select 1,'" + this.id + "', pk_seq, '"+spTonkho[i].replace(",", "")+"', '" + spSoluong[i].replaceAll(",", "") + "', '" + spSoluong[i].replaceAll(",", "") + "', '" + spGianhap[i].replaceAll(",", "") + "', '" + spVAT[i].replaceAll(",", "") + "', ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ),'"+spGiagoc[i].replaceAll(",", "")+"',"+spBgId[i]+",'"+i+"' " +
								", '" + spChietkhau[i].replaceAll(",", "") + "' from SANPHAM where MA = '" + spMa[i].trim() + "' ";

						System.out.println("1.Insert NK - SP: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_Dondathang_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}
			else
			{
				for(int i = 0; i < spMa.length; i++)
				{
					if(spMa[i].trim().length() > 0)
					{
						//CHECK SP NAY DA CO KHAI QUY CACH CHUA?
						query =    "select sp.ten, dv.donvi, case when sp.dvdl_fk != dv.pk_seq   " +
								"	then ISNULL( ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = sp.PK_SEQ and DVDL1_FK = sp.DVDL_FK and DVDL2_FK = dv.pk_seq ), -1 ) else 1 end as quycach   "  +
								"from SANPHAM sp, DONVIDOLUONG dv " +
								"where sp.MA = '" + spMa[i].trim() + "' and dv.donvi = N'" + spDonvi[i].trim() + "'   ";

						System.out.println("-----CHECK QUY CACH: " + query );
						ResultSet rs = db.get(query);
						if(rs.next())
						{
							if(rs.getDouble("quycach") <= 0)
							{
								this.msg = "Sản phẩm ( " + rs.getString("ten") + " ) với đơn vị đặt ( " + rs.getString("donvi") + " ) chưa thiết lập quy cách trong DLN ";
								rs.close();
								db.getConnection().rollback();
								return false;
							}
						}
						rs.close();

						query = "insert ERP_Dondathang_SANPHAM( isNppDat,Dondathang_fk, SANPHAM_FK, tonkho, soluong, soluongttduyet, dongia, thueVAT, dvdl_fk,DonGiaGoc,BANGGIAMUANPP_FK,SoTT, chietkhau ) " +
								"select 1,'" + this.id + "', pk_seq, '"+spTonkho[i].replace(",", "")+"', '" + spSoluong[i].replaceAll(",", "") + "', '" + spSoluong[i].replaceAll(",", "") + "', '" + spGianhap[i].replaceAll(",", "") + "', '" + spVAT[i].replaceAll(",", "") + "', ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ),'"+spGiagoc[i].replaceAll(",", "")+"',"+spBgId[i]+",'"+i+"' " +
								", '" + spChietkhau[i].replaceAll(",", "") + "' from SANPHAM where MA = '" + spMa[i].trim() + "' ";

						System.out.println("2.Insert NK - SP: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_Dondathang_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}

			query=
					" INSERT INTO ERP_DONDATHANG_SANPHAM_LOG(DONDATHANG_FK,SANPHAM_FK, TONKHO,SOLUONG, SOLUONGTTDUYET,DONGIA,DVDL_FK,CHIETKHAU,SOTT,THUEVAT,DonGiaGoc,BANGGIAMUANPP_FK) "+ 
							" SELECT DONDATHANG_FK,SANPHAM_FK, TONKHO,SOLUONG, SOLUONGTTDUYET,DONGIA,DVDL_FK,CHIETKHAU,SOTT,THUEVAT,DonGiaGoc,BANGGIAMUANPP_FK  "+
							" FROM ERP_DONDATHANG_SANPHAM" +
							" where DONDATHANG_FK='"+this.id+"'   ";
			if(!db.update(query))
			{
				this.msg = "Khong the tao moi ERP_Dondathang_SANPHAM: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			String msgUpdate = geso.dms.distributor.util.Utility.Update_GiaTri_ERP_DonDatHang(this.id, db,true);			
			if(msgUpdate.trim().length() > 0)
			{
				this.msg = msgUpdate;
				db.getConnection().rollback();
				return false;
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
	}*/

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


		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm đặt hàng";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spMa.length; i++)
			{
				if( spMa[i].trim().length() > 0 )
				{
					if(spSoluong[i].trim().length() <= 0 && spSCheme[i].trim().length() <= 0 )
					{
						this.msg = "Bạn phải nhập số lượng của sản phẩm ( " + spTen[i] + " )";
						return false;
					}

					if(spGianhap[i].trim().length() <= 0 && spSCheme[i].trim().length() <= 0 )
					{
						this.msg = "Bạn phải nhập đơn giá của sản phẩm ( " + spTen[i] + " )";
						return false;
					}

					if(!this.loaidonhang.equals("4"))
					{
						if(spDonvi[i].trim().length() <= 0 && spSCheme[i].trim().length() <= 0 )
						{
							this.msg = "Bạn phải nhập đơn vị  của sản phẩm ( " + spTen[i] + " )";
							return false;
						}
					}

					coSP = true;
				}
			}
			if(!coSP)
			{
				this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm";
				return false;
			}
		}




		//CHECK TRUNG MA 
		for(int i = 0; i < spMa.length - 1; i++)
		{
			for(int j = i + 1; j < spMa.length; j++)
			{
				if(spMa[i].trim().length() > 0 && spMa[j].trim().length() > 0  )
				{
					if( spMa[i].trim().equals(spMa[j].trim()) && spSCheme[j].trim().length() <= 0 && spSCheme[i].trim().length() <= 0 )
					{
						this.msg = "Sản phẩm ( " + spTen[i] + " )  đã bị trùng.";
						return false;
					}
				}
			}
		}

		try
		{
			db.getConnection().setAutoCommit(false);

			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
			String chietkhau = this.chietkhau.trim().length() <= 0 ? "0" : this.chietkhau.trim();
			String vat = this.vat.trim().length() <= 0 ? "0" : this.vat.trim();

			String query =	" Update ERP_Dondathang set ChietKhau = isnull((select chietkhau from nhaphanphoi where pk_seq =? ),0) " +
					"\n ,ngaydonhang = ?, ngaydenghi = ?, ghichu = ?, tiengiam = ?," +
					"\n 	dvkd_fk = ?, kbh_fk = ?, npp_fk = ?, kho_fk = ?, ngaysua = ?, nguoisua = ?, vat = ? " + 
					"\n where pk_seq = ? ";
			db.viewQuery(query, new Object[]{this.nppId, this.ngayyeucau, this.ngaydenghi
					, this.ghichu, this.tiengiam, this.dvkdId, this.kbhId, this.nppId, khonhan_fk, getDateTime()
					, this.userId, vat, this.id});
			if(db.updateQueryByPreparedStatement(query, new Object[]{this.nppId, this.ngayyeucau, this.ngaydenghi
					, this.ghichu, this.tiengiam, this.dvkdId, this.kbhId, this.nppId, khonhan_fk, getDateTime()
					, this.userId, vat, this.id}) < 1)
			{
				this.msg = "Không thể cập nhật đặt hàng";
				db.getConnection().rollback();
				return false;
			}

			query = "delete ERP_Dondathang_SANPHAM where Dondathang_fk = ?";
			if(db.updateQueryByPreparedStatement(query, new Object[]{this.id}) < 1)
			{
				this.msg = "Không thể xóa sản phẩm cho đơn đặt hàng";
				db.getConnection().rollback();
				return false;
			}

			query = "delete ERP_DONDATHANG_CHIETKHAU where Dondathang_fk = ?";
			db.viewQuery(query, new Object[]{this.id});
			if(db.updateQueryByPreparedStatement(query, new Object[]{this.id}) == -1)
			{
				this.msg = "Không thể cập nhật chiết khấu cho đơn đặt hàng";
				db.getConnection().rollback();
				return false;
			}

			if(this.loaidonhang.equals("0"))  //DON DAT HANG CHI LUU NHUNG SP KO CO SCHEME
			{
				for(int i = 0; i < spMa.length; i++)
				{
					if(spMa[i].trim().length() > 0 && spSCheme[i].trim().length() <= 0 )
					{
						//CHECK SP NAY DA CO KHAI QUY CACH CHUA?
						query =    "select sp.ten, dv.donvi, case when sp.dvdl_fk != dv.pk_seq   " +
								"	then ISNULL( ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = sp.PK_SEQ and DVDL1_FK = sp.DVDL_FK and DVDL2_FK = dv.pk_seq ), -1 ) else 1 end as quycach   "  +
								"from SANPHAM sp, DONVIDOLUONG dv " +
								"where sp.MA = ? and dv.donvi = ?   ";

						//System.out.println("-----CHECK QUY CACH: " + query );
						ResultSet rs = db.getByPreparedStatement(query, new Object[]{spMa[i].trim(), spDonvi[i].trim()});
						if(rs.next())
						{
							if(rs.getDouble("quycach") <= 0)
							{
								this.msg = "Sản phẩm ( " + rs.getString("ten") + " ) với đơn vị đặt ( " + rs.getString("donvi") + " ) chưa thiết lập quy cách trong DLN ";
								rs.close();
								db.getConnection().rollback();
								return false;
							}
						}
						rs.close();
						
						double soluong = Double.parseDouble(spSoluong[i].replaceAll(",", ""));
						double soluongtt = 0;
						if(spSoluongtt != null && spSoluongtt.length > 0 && !spSoluongtt[i].equals("")){
							soluongtt = Double.parseDouble(spSoluongtt[i].replaceAll(",", ""));
						}
						String ssoluongtt =  spSoluongtt[i].replaceAll(",", "");
						if(soluong <= soluongtt || soluongtt == 0)
							ssoluongtt = spSoluong[i].replaceAll(",", "");

						query = "insert ERP_Dondathang_SANPHAM( isNppDat,Dondathang_fk, SANPHAM_FK, tonkho, soluong, soluongttduyet, dongia, thueVAT, dvdl_fk,DonGiaGoc,BANGGIAMUANPP_FK,SoTT,GhiChu,NgayGiaoHang, chietkhau ) " +
								"select 1,?, pk_seq, ?, ?, ?, ?, ?, ( select pk_Seq from DONVIDOLUONG where donvi = ? ),?,?,?, " +
								" ?,?, ?  "+
								"from SANPHAM where MA = ? ";

						//System.out.println("1.Insert NK - SP: " + query);
						if(db.updateQueryByPreparedStatement(query, new Object[]{this.id,spTonkho[i].replaceAll(",", "")
								,spSoluong[i].replaceAll(",", ""),ssoluongtt,spGianhap[i].replaceAll(",", "")
								,spVAT[i].replaceAll(",", ""),spDonvi[i].trim(),spGiagoc[i].replaceAll(",", "")
								,spBgId[i],i,spGhichu[i],spNgaygiaohang[i],spChietkhau[i].replaceAll(",", "")
								,spMa[i].trim()}) < 1)
						{
							this.msg = "Khong the tao moi sản phẩm cho đơn đặt hàng";
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}
			else
			{
				for(int i = 0; i < spMa.length; i++)
				{
					if(spMa[i].trim().length() > 0)
					{
						//CHECK SP NAY DA CO KHAI QUY CACH CHUA?
						query =    "select sp.ten, dv.donvi, case when sp.dvdl_fk != dv.pk_seq   " +
								"	then ISNULL( ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = sp.PK_SEQ and DVDL1_FK = sp.DVDL_FK and DVDL2_FK = dv.pk_seq ), -1 ) else 1 end as quycach   "  +
								"from SANPHAM sp, DONVIDOLUONG dv " +
								"where sp.MA = ? and dv.donvi = ?";

						//System.out.println("-----CHECK QUY CACH: " + query );
						ResultSet rs = db.getByPreparedStatement(query, new Object[]{spMa[i].trim(),spDonvi[i].trim()});
						if(rs.next())
						{
							if(rs.getDouble("quycach") <= 0)
							{
								this.msg = "Sản phẩm ( " + rs.getString("ten") + " ) với đơn vị đặt ( " + rs.getString("donvi") + " ) chưa thiết lập quy cách trong DLN ";
								rs.close();
								db.getConnection().rollback();
								return false;
							}
						}
						rs.close();

						double soluong = Double.parseDouble(spSoluong[i].replaceAll(",", ""));
						double soluongtt = 0;
						if(spSoluongtt != null && spSoluongtt.length > 0 && !spSoluongtt[i].equals("")){
							soluongtt = Double.parseDouble(spSoluongtt[i].replaceAll(",", ""));
						}
						//double soluongtt = Double.parseDouble(spSoluongtt[i].replaceAll(",", ""));
						String ssoluongtt =  spSoluongtt[i].replaceAll(",", "");
						if(soluong <= soluongtt || soluongtt == 0)
							ssoluongtt = spSoluong[i].replaceAll(",", "");
						
						query = "insert ERP_Dondathang_SANPHAM(isNppDat, Dondathang_fk, SANPHAM_FK, tonkho, soluong, soluongttduyet, dongia, thueVAT, dvdl_fk,DonGiaGoc,BANGGIAMUANPP_FK,SoTT, chietkhau ) " +
								"select 1,?, pk_seq, ?, ?, ?, ?, ?, ( select pk_Seq from DONVIDOLUONG where donvi = ? ),?,?,? " +
								", ? from SANPHAM where MA = ? ";

						//System.out.println("1.Insert NK - SP: " + query);
						if(db.updateQueryByPreparedStatement(query, new Object[]{this.id, spTonkho[i].replaceAll(",", "")
								,spSoluong[i].replaceAll(",", ""),ssoluongtt,spGianhap[i].replaceAll(",", "")
								,spVAT[i].replaceAll(",", ""),spDonvi[i].trim(),spGiagoc[i].replaceAll(",", "")
								,spBgId[i],i,spChietkhau[i].replaceAll(",", ""),spMa[i].trim()}) < 1)
						{
							this.msg = "Khong the tao moi sản phẩm cho đơn đặt hàng";
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}
			
			String msgUpdate = geso.dms.distributor.util.Utility.Update_GiaTri_ERP_DonDatHang(this.id, db,true);			
			if(msgUpdate.trim().length() > 0)
			{
				this.msg = msgUpdate;
				db.getConnection().rollback();
				return false;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.msg = "Exception: " + e.getMessage();
			e.printStackTrace();
			return false;
		}

		return true;
	}

	double ptChietKhau = 0;

	public void createRs() 
	{
		this.getNppInfo();
		String loainpp = "", kho = "khott";
		String query = "select loainpp, khosap from NHAPHANPHOI where PK_SEQ=?";
		ResultSet rs = db.getByPreparedStatement(query, new Object[]{this.nppId});
		if(rs != null)
		{
			try
			{
				rs.next();
				loainpp = rs.getString("loainpp");
				if(this.khoNhanId.length() <= 0)
					this.khoNhanId = rs.getString("khosap");
				if(loainpp.trim().equals("6"))
					kho = "kho";
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		query = "select PK_SEQ, TEN + '-' + DIENGIAI AS TEN from "+kho+" where trangthai = '1' and pk_seq = 100000  ";
		this.khoNhanRs = db.get(query);
		this.dvtRs = db.getScrol("select PK_SEQ, DONVI from DONVIDOLUONG where trangthai = '1' ");

		this.dvkdRs = db.get("select PK_SEQ, DONVIKINHDOANH as TEN from DONVIKINHDOANH where TRANGTHAI = '1' ");
		query = "select PK_SEQ, DIENGIAI as TEN from KENHBANHANG where TRANGTHAI = '1' and PK_SEQ in ( select KBH_FK from NHAPP_KBH where NPP_FK = ? ) ";
		this.kbhRs = db.getByPreparedStatement(query, new Object[]{this.nppId});

		query = "select PK_SEQ, MA + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1' and pk_seq = ?";
		this.nppRs = db.getByPreparedStatement(query, new Object[]{this.nppId});

		if(this.nppId.trim().length() > 0)
		{
			query = "select isnull( a.chietkhau,0) chietkhau ,a.PK_SEQ as nppId, d.DVKD_FK, b.KBH_FK  " +
					"From NhaPhanPhoi a left join nhapp_kbh b on b.NPP_FK = a.PK_SEQ left join NHAPP_NHACC_DONVIKD c on c.NPP_FK = b.NPP_FK  " +
					"	left join NHACUNGCAP_DVKD d on d.PK_SEQ = c.NCC_DVKD_FK   " +
					"where a.pk_Seq = ?";
			System.out.println("lay kbh, dvkd: "+query);
			ResultSet rsInfo = db.getByPreparedStatement(query, new Object[]{this.nppId});
			if(rsInfo != null)
			{
				try 
				{
					if(rsInfo.next())
					{
						if(!this.trangthai.equals("1") && !this.trangthai.equals("3"))
							ptChietKhau = rsInfo.getDouble("chietkhau");
						
						if(this.dvkdId.trim().length() <= 0)
							this.dvkdId = rsInfo.getString("DVKD_FK");
						if(this.kbhId.trim().length() <= 0 )
							this.kbhId = rsInfo.getString("KBH_FK");
					}
					rsInfo.close();
				} 
				catch (Exception e) {}
			}

		
			
			

		}

		if(this.kbhId != null)
		{
			if( this.kbhId.trim().equals("100052") && this.vat.equals("0") )
				this.vat = "10";
			else
			{
				if(this.kbhId.equals("100025"))
					this.vat = "0";
			}
		}

	}

	NumberFormat formater = new DecimalFormat("##,###,###");
	NumberFormat formater2 = new DecimalFormat("##,###,###.####");

	private void initSANPHAM() 
	{
		


		if(this.id.length()>0)
		{
			OracleConnUtils sync = new OracleConnUtils();
			String query = "select loainpp, nppc1 from nhaphanphoi where pk_seq = ?";
			String loaiNPP = "", nppc1 = "";
			ResultSet rs = db.getByPreparedStatement(query, new Object[]{nppId});
			if(rs != null)
			{
				try
				{
					rs.next();
					loaiNPP = rs.getString("loainpp");
					nppc1 = rs.getString("nppc1");
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			
			query = "select a.soluongNppdat,b.MA, b.TEN, DV.donvi, a.soluong, a.dongia, a.thueVAT,a.ChietKhau,a.DonGiaGoc,isnull(BANGGIAMUANPP_FK,0) as BANGGIAMUANPP_FK, "+
					" 	a.soluongttduyet, a.ghichu,a.NgayGiaoHang, a.tonkho    " +
					" from ERP_Dondathang_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ    " +
					" 		INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK       " +
					" where a.Dondathang_FK = ?" +
					"  order by a.sott ";

			System.out.println("--INIT SP: " + query);
			ResultSet spRs = db.getByPreparedStatement(query, new Object[]{this.id});


			if(spRs != null)
			{
				try 
				{
					String spMA = "";
					String spTEN = "";
					String spDONVI = "";
					String spTONKHO = "";
					String spSOLUONG = "";
					String spSOLUONGTT = "";
					String spGIANHAP = "";
					String spVAT = "";
					String spSCHEME = "";
					String spCHIETKHAU = "";
					String spDONGIAGOC = "";
					String spBGID = "";
					String spGHICHU = "";
					String spNGAYGIAOHANG = "";
					String spTRAKMID = "";
					String spSoluongNppDatStr = "";
					String spma = "", donvi = "", tonkho = "", kho = "", dvkd = "";
					
					while(spRs.next())
					{
						spMA += spRs.getString("MA") + "__";
						spTEN += spRs.getString("TEN") + "__";
						
						if(loaiNPP.equals("1"))
						{
							query = "select ten from khott where pk_seq = ?";
							rs = db.getByPreparedStatement(query, new Object[]{this.khoNhanId});
							if(rs != null)
							{
								rs.next();
								kho = rs.getString("ten");
								rs.close();
							}
							query = "select donvikinhdoanh from donvikinhdoanh where pk_seq = ?";
							rs = db.getByPreparedStatement(query, new Object[]{dvkdId});
							if(rs != null)
							{
								rs.next();
								dvkd = rs.getString("donvikinhdoanh");
								rs.close();
							}
							query = "select ON_HAND as tonkho from apps.V_TONKHO where ORGANIZATION_CODE = '"+kho+"' and ITEM_CODE = '"+spRs.getString("MA")+"' and PRODUCT_GROUP = upper('"+dvkd+"')";
							rs = sync.get(query);
						}
						else
						{
							query = "select available as tonkho from nhapp_kho where kho_fk = ? \n"+
									"and sanpham_fk = (select pk_seq from sanpham where ma = '"+spRs.getString("MA")+"') and npp_fk = "+nppc1;
							rs = db.getByPreparedStatement(query, new Object[]{this.khoNhanId});
						}
						System.out.println("[tonkho] "+query);
						if(rs != null)
						{
							if(rs.next())
							{								
								tonkho = rs.getString("tonkho");
							}
							else
							{
								tonkho = "0";
							}
						}
						else
							tonkho = "0";
						
						spTONKHO += tonkho + "__";
						spDONVI += spRs.getString("DONVI") + "__";
						spSOLUONG += formater.format(spRs.getDouble("SOLUONG")) + "__";
						spGIANHAP += formater2.format(spRs.getDouble("DONGIA")) + "__";
						spVAT += spRs.getDouble("thueVAT") + "__";
						spSCHEME += " __";
						spCHIETKHAU += formater2.format(spRs.getDouble("ChietKhau")) + "__";
						spDONGIAGOC += formater.format(spRs.getDouble("DonGiaGoc")) + "__";
						spBGID += spRs.getString("BANGGIAMUANPP_FK") + "__";
						spSOLUONGTT += formater.format(spRs.getDouble("SOLUONGTTDUYET")) + "__";
						spGHICHU += spRs.getString("GHICHU")==null?" __":spRs.getString("GHICHU") + " __";

						spNGAYGIAOHANG += (spRs.getString("NGAYGIAOHANG")==null||spRs.getString("NGAYGIAOHANG").trim().length()<=0)?" __":spRs.getString("NGAYGIAOHANG") + " __";
						spTRAKMID+=" __";
						
						spSoluongNppDatStr += spRs.getString("soluongNppdat") +"__"; 
						
					}
					spRs.close();

					//INIT SP KHUYEN MAI
					query = "select  a.TRAKMID,isnull(b.MA, ' ') as MA, isnull(b.TEN, ' ') as TEN, isnull(c.DONVI, ' ') as donvi, d.SCHEME, isnull(a.soluong, 0) as soluong, isnull(a.soluongTT, a.soluong) as soluongTT , a.tonggiatri,0 as ChietKhau " +
							"from ERP_DONDATHANG_CTKM_TRAKM a left join SANPHAM b on a.SPMA = b.MA  " +
							"	left join DONVIDOLUONG c on b.DVDL_FK = c.PK_SEQ  " +
							"	inner join CTKHUYENMAI d on a.ctkmID = d.PK_SEQ " +
							"where a.dondathangID = ?" ;
					System.out.println("--INIT SPKM: " + query);

					spRs = db.getByPreparedStatement(query, new Object[]{this.id});
					while(spRs.next())
					{
						spMA += spRs.getString("MA") + "__";
						spTEN += spRs.getString("TEN") + "__";
						spDONVI += spRs.getString("DONVI") + "__";
						spTONKHO += " __";
						spSOLUONG += formater.format(spRs.getDouble("SOLUONG")) + "__";
						spGIANHAP += formater.format(spRs.getDouble("tonggiatri")) + "__";
						spVAT += "0__";
						spSCHEME += spRs.getString("SCHEME") + "__";
						spCHIETKHAU += formater2.format(spRs.getDouble("ChietKhau")) + "__";
						spDONGIAGOC += " __";
						spBGID += " __";
						spSOLUONGTT += formater.format(spRs.getDouble("SOLUONGTT")) + "__";
						spGHICHU += " __";
						spNGAYGIAOHANG += " __";
						spTRAKMID += spRs.getString("TRAKMID") + "__";
						spSoluongNppDatStr += " __"; 
					}
					spRs.close();

					query=					
							"	SELECT DONDATHANG_FK,DIENGIAI as SCHEME ,0 as SoLuong ,LOAI,ChietKhau as tonggiatri,0 as ChietKhau,N'CKĐH' AS MA,SCHEME AS TEN,'CK' AS donvi  "+ 
									"	 FROM ERP_DONDATHANG_CHIETKHAU A   "+
									"	 WHERE DONDATHANG_FK=? AND LOAI=1 ";					 
					System.out.println("--INIT ERP_DONDATHANG_CHIETKHAU: " + query);
					spRs = db.getByPreparedStatement(query, new Object[]{this.id});
					while(spRs.next())
					{
						spMA += spRs.getString("MA") + "__";
						spTEN += spRs.getString("TEN") + "__";
						spDONVI += spRs.getString("DONVI") + "__";
						spTONKHO += " __";
						spSOLUONG += formater.format(spRs.getDouble("SOLUONG")) + "__";
						spGIANHAP += formater.format(spRs.getDouble("tonggiatri")) + "__";
						spVAT += "0__";
						spSCHEME += spRs.getString("SCHEME") + "__";
						spCHIETKHAU += formater2.format(spRs.getDouble("ChietKhau")) + "__";
						spDONGIAGOC += " __";
						spBGID += " __";
						spSOLUONGTT += " __";
						spGHICHU += " __";
						spNGAYGIAOHANG += " __";
						spTRAKMID+=" __";
						spSoluongNppDatStr += " __"; 
					}
					spRs.close();
					
					if(spMA.trim().length() > 0)
					{
						spMA = spMA.substring(0, spMA.length() - 2);
						this.spMa = spMA.split("__");

						spTEN = spTEN.substring(0, spTEN.length() - 2);
						this.spTen = spTEN.split("__");

						System.out.println("donvi: "+spDONVI);
						spDONVI = spDONVI.substring(0, spDONVI.length() - 2);
						this.spDonvi = spDONVI.split("__");
						
						spTONKHO = spTONKHO.substring(0, spTONKHO.length() - 2);
						System.out.println(" spTONKHO = "+ spTONKHO);
						this.spTonkho = spTONKHO.split("__");

						spSOLUONG = spSOLUONG.substring(0, spSOLUONG.length() - 2);
						this.spSoluong = spSOLUONG.split("__");

						spGIANHAP = spGIANHAP.substring(0, spGIANHAP.length() - 2);
						this.spGianhap = spGIANHAP.split("__");

						spVAT = spVAT.substring(0, spVAT.length() - 2);
						this.spVAT = spVAT.split("__");

						spSCHEME = spSCHEME.substring(0, spSCHEME.length() - 2);
						this.spSCheme = spSCHEME.split("__");

						System.out.println("spchietkhau "+spCHIETKHAU);
						spCHIETKHAU = spCHIETKHAU.substring(0, spCHIETKHAU.length() - 2);
						this.spChietkhau = spCHIETKHAU.split("__");

						spDONGIAGOC = spDONGIAGOC.substring(0, spDONGIAGOC.length() - 2);
						this.spGiagoc = spDONGIAGOC.split("__");

						spBGID = spBGID.substring(0, spBGID.length() - 2);
						this.spBgId = spBGID.split("__");

						spSOLUONGTT = spSOLUONGTT.substring(0, spSOLUONGTT.length() - 2);
						this.spSoluongtt = spSOLUONGTT.split("__");

						spGHICHU = spGHICHU.substring(0, spGHICHU.length() - 2);
						this.spGhichu = spGHICHU.split("__");

						spNGAYGIAOHANG = spNGAYGIAOHANG.substring(0, spNGAYGIAOHANG.length() - 2);
						this.spNgaygiaohang = spNGAYGIAOHANG.split("__");

						spTRAKMID = spTRAKMID.substring(0, spTRAKMID.length() - 2);
						this.spTrakmId = spTRAKMID.split("__");
						
						spSoluongNppDatStr = spSoluongNppDatStr.substring(0, spSoluongNppDatStr.length() - 2);
						this.spSoluongNppDat = spSoluongNppDatStr.split("__");


					}
					
					/*System.out.println("scheme "+spSCHEME+", length "+this.spSCheme[1].trim().length());
					if(this.spSCheme[1].trim().length() > 0)
					{
						this.aplaikm = true;
					}*/

					String dhCk_DIENGIAI="";
					String dhCk_GIATRI="";
					String dhCk_LOAI="";
					query=					
							"	SELECT DIENGIAI,GIATRI,LOAI,ChietKhau,DOANHSO FROM ERP_DONDATHANG_CHIETKHAU "+ 
									"	 WHERE DONDATHANG_FK=? AND LOAI=0 ";					 
					spRs = db.getByPreparedStatement(query, new Object[]{this.id});
					while(spRs.next())
					{
						dhCk_DIENGIAI += spRs.getString("DIENGIAI") + "__";
						dhCk_GIATRI += formater.format(spRs.getDouble("GIATRI")) + "__";
						dhCk_LOAI += spRs.getString("LOAI") + "__";							
					}
					spRs.close();

					if(dhCk_DIENGIAI.trim().length() > 0)
					{
						dhCk_DIENGIAI = dhCk_DIENGIAI.substring(0, dhCk_DIENGIAI.length() - 2);
						this.dhCk_diengiai = dhCk_DIENGIAI.split("__");

						dhCk_GIATRI = dhCk_GIATRI.substring(0, dhCk_GIATRI.length() - 2);
						this.dhCk_giatri = dhCk_GIATRI.split("__");

						dhCk_LOAI = dhCk_LOAI.substring(0, dhCk_LOAI.length() - 2);
						this.dhCk_loai = dhCk_LOAI.split("__");
					}
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
					System.out.println("115.Exception: " + e.getMessage());
				}
			}
		}
	}

	public void init() 
	{
		String query = "select isnull(ChietKhau,0) ChietKhau,trangthai,ngaydonhang, ngaydenghi, ISNULL(ghichu, '') as ghichu, dvkd_fk, kbh_fk, npp_fk, kho_fk, chietkhau, vat, loaidonhang, isnull(sotienthu, 0) as sotienthu, isnull(tiengiam, 0) as tiengiam " +
				"from ERP_Dondathang where pk_seq = ?";
		System.out.println("____INIT NHAP KHO: " + query);
		ResultSet rs = db.getByPreparedStatement(query, new Object[]{this.id});
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.ptChietKhau = rs.getDouble("ChietKhau");
					this.trangthai =   rs.getString("trangthai");
					this.ngayyeucau = rs.getString("ngaydonhang");
					this.ngaydenghi = rs.getString("ngaydenghi");
					this.ghichu = rs.getString("ghichu");
					this.dvkdId = rs.getString("dvkd_fk");
					this.kbhId = rs.getString("kbh_fk");
					this.nppId = rs.getString("npp_fk");
					this.khoNhanId = rs.getString("kho_fk");
					this.loaidonhang = rs.getString("loaidonhang");
					this.chietkhau = rs.getString("sotienthu");
					this.vat = rs.getString("vat");
					this.tiengiam = rs.getString("tiengiam")==null?"0":rs.getString("tiengiam");
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("---LOI INIT: " + e.getMessage());
			}
		}

		this.createRs();

		this.initSANPHAM();

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

	String[] spVAT;
	public String[] getSpVat() 
	{
		return this.spVAT;
	}
	public void setSpVat(String[] spVat) 
	{
		this.spVAT = spVat;
	}

	String[] spSoluongtt;
	public String[] getSoluongtt() 
	{
		return this.spSoluongtt;
	}
	public void setSoluongtt(String[] spSoluongtt) 
	{
		this.spSoluongtt = spSoluongtt;
	}

	String[] spGhichu;
	public String[] getSpGhichu() 
	{
		return this.spGhichu;
	}
	public void setSpGhichu(String[] spGhichu) 
	{
		this.spGhichu = spGhichu;
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


	private String getDateTime_CongMot() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		String[] arr = dateFormat.format(date).split("-");	

		Calendar cal = Calendar.getInstance();    
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(arr[2]));
		cal.set(Calendar.MONTH, Integer.parseInt(arr[1]) - 1);
		cal.set(Calendar.YEAR, Integer.parseInt(arr[0]) );

		cal.add(Calendar.DAY_OF_MONTH, 1);

		String nam = Integer.toString(cal.get(Calendar.YEAR));
		String thang = Integer.toString( cal.get(Calendar.MONTH) + 1);
		if(thang.trim().length() < 2)
			thang = "0" + thang;

		String ngay = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
		if(ngay.trim().length() < 2)
			ngay = "0" + ngay;

		return nam + "-" + thang + "-" + ngay;	
	}

	String[] spGiagoc,spBgId;

	public String[] getSpBgId()
	{
		return spBgId;
	}

	public void setSpBgId(String[] spBgId)
	{
		this.spBgId = spBgId;
	}

	public String[] getSpGiagoc()
	{
		return spGiagoc;
	}

	public void setSpGiagoc(String[] spGiagoc)
	{
		this.spGiagoc = spGiagoc;
	}

	String[] spNgaygiaohang;

	public String[] getSpNgaygiaohang()
	{
		return spNgaygiaohang;
	}

	public void setSpNgaygiaohang(String[] spNgaygiaohang)
	{
		this.spNgaygiaohang = spNgaygiaohang;
	}
	String[] spTrakmId;
	public String[] getSpTrakmId() {
		return spTrakmId;
	}

	public void setSpTrakmId(String[] spTrakmId) {
		this.spTrakmId = spTrakmId;
	}

	public boolean isAplaikhuyenmai() 
	{
		return this.aplaikm;
	}

	public void setAplaikhuyenmai(boolean aplaikm) 
	{
		this.aplaikm = aplaikm;
	}

	@Override
	public String getCongno() {
		return this.congno;
	}

	@Override
	public void setCongno(String congno) {
		this.congno = congno;
	}

	@Override
	public String[] getSpTonkho() {
		return spTonkho;
	}

	@Override
	public void setSpTonkho(String[] spTonkho) {
		this.spTonkho = spTonkho;
	}

	@Override
	public String getTiengiam() {
		return this.tiengiam;
	}

	@Override
	public void setTiengiam(String tiengiam) {
		this.tiengiam = tiengiam;
	}
	public double getPtChietKhau() {
		return ptChietKhau;
	}
	public void setPtChietKhau(double ptChietKhau) {
		this.ptChietKhau = ptChietKhau;
	}
	
	public String[] getSpSoluongNppDat() {
		return spSoluongNppDat;
	}
	
}