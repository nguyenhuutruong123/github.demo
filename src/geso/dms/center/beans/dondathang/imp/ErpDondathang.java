package geso.dms.center.beans.dondathang.imp;

import geso.dms.center.beans.dondathang.IErpDondathang;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.OracleConnUtils;
import geso.dms.center.util.Utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

public class ErpDondathang implements IErpDondathang
{
	String userId;
	String id;
	String isTT = "";
	String ngayyeucau;
	String ngaydenghi;
	String ghichu;

	String msg;
	String trangthai = "0";

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
	String[] spGianhapck;
	String[] spChietkhau;
	String[] spSChemeIds;
	String[] spSCheme;
	String[] spSChemeDiengiai;
	String[] spTrongluong;
	String[] spThetich;
	String[] spTrakmId;
	String[] spTonkho;
	String[] isNppDat;

	String[] dhCk_diengiai;
	String[] dhCk_giatri;
	String[] dhCk_loai;

	ResultSet congnoRs;
	ResultSet schemespecialRs;

	String tiengiam;
	String pokhuyenmai;

	dbutils db;
	NumberFormat formater = new DecimalFormat("##,###,###");
	NumberFormat formater2 = new DecimalFormat("##,###,###.####");

	public ErpDondathang()
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
		this.tiengiam = "0";
		this.vat = "0";
		this.dvkdId = "";
		this.kbhId = "";
		this.schemeId = "";

		/*this.dhCk_diengiai = new String[]{"Chiết khấu thanh toán"};
		this.dhCk_giatri = new String[]{"0"};
		this.dhCk_loai = new String[]{"1"};*/

		this.scheme_soluong = new Hashtable<String, String>();
		this.congNo="";
		this.pokhuyenmai = "0";
		this.db = new dbutils();
	}

	public ErpDondathang(String id)
	{
		this.id = id;
		this.ngayyeucau = getDateTime();
		this.ngaydenghi = getDateTime_CongMot();
		this.ghichu = "";
		this.khoNhanId = "";
		this.nppId = "";
		this.msg = "";
		this.loaidonhang = "0";
		this.trangthai = "0";
		this.chietkhau = "0";
		this.vat = "0";
		this.tiengiam = "0";
		this.dvkdId = "";
		this.kbhId = "";
		this.schemeId = "";

		/*this.dhCk_diengiai = new String[]{"Chiết khấu thanh toán"};
		this.dhCk_giatri = new String[]{"0"};
		this.dhCk_loai = new String[]{"1"};*/

		this.congNo="";
		this.pokhuyenmai = "0";
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
		if(this.nppId.trim().length() > 0)
		{
			String sqlnpp ="select * from BANGGIAMUANPP_NPP where npp_fk = "+nppId+" ";

			ResultSet rsnpp = db.get(sqlnpp);
			if(rsnpp != null)
			{
				try {
					while(!rsnpp.next())
					{
						msg = "Vui lòng thiết lập bảng giá cho NPP ";
						return false;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
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
					if(spSoluongtt[i].trim().length() <= 0)
					{
						this.msg = "Bạn phải nhập số lượng của sản phẩm ( " + spTen[i] + " )";
						return false;
					}

					if(spGianhap[i].trim().length() <= 0)
					{
						this.msg = "Bạn phải nhập đơn giá của sản phẩm ( " + spTen[i] + " )";
						return false;
					}

					if(!this.loaidonhang.equals("4"))
					{
						if(spDonvi[i].trim().length() <= 0)
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

	/*		String query = 
				" insert ERP_Dondathang(isTT,ngaydonhang, ngaydenghi, loaidonhang, ghichu, trangthai, dvkd_fk, kbh_fk, npp_fk, kho_fk, chietkhau, "+
				" vat, ngaytao, nguoitao, ngaysua, nguoisua,CongNo,NguonGoc_TaoDH, tiengiam, pokhuyenmai) " +
				" values(1,'" + this.ngayyeucau + "', '" + this.ngaydenghi + "', '" + this.loaidonhang + "', N'" + this.ghichu + "', '0', '" + dvkdId + "', "+
				" '" + kbhId + "', '" + nppId + "', " + khonhan_fk + ", '" + chietkhau + "', '" + vat + "', '" + getDateTime() + "', '" + this.userId + "', "+
				" '" + getDateTime() + "', '" + this.userId + "', "+(this.congNo.length()<=0?"0":this.congNo)+", N'Center', '"+this.tiengiam+"', "+this.pokhuyenmai+")";

			System.out.println("1.Insert DDH: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_Dondathang " + query;
				db.getConnection().rollback();
				return false;
			}

			//LAY ID
			ResultSet rsDDH = db.get("select Scope_identity() as ID ");
			if(rsDDH.next())
			{
				this.id = rsDDH.getString("ID");
			}
			rsDDH.close();*/
			
			String query = "INSERT ERP_DONDATHANG(isTT,ngaydonhang, ngaydenghi, loaidonhang, ghichu, trangthai, dvkd_fk, kbh_fk, npp_fk, kho_fk, chietkhau,  vat, ngaytao, nguoitao, ngaysua, nguoisua,CongNo,NguonGoc_TaoDH, tiengiam, pokhuyenmai) select ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";

			Object[] data = {"1",this.ngayyeucau,this.ngaydenghi,this.loaidonhang,this.ghichu,"0",dvkdId,kbhId,nppId,khonhan_fk,chietkhau,vat,getDateTime(),this.userId,getDateTime(),this.userId,(this.congNo.length()<=0?0:this.congNo),"Center",this.tiengiam,this.pokhuyenmai};

			dbutils.viewQuery(query,data);
			if (this.db.updateQueryByPreparedStatement(query,data) != 1) {
				this.msg = "1. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý";
				this.db.getConnection().rollback();
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
						String ck = "0";
						if(spChietkhau[i].trim().length() > 0)
							ck = spChietkhau[i].trim().replaceAll(",", "");

				/*		query = "insert ERP_Dondathang_SANPHAM( Dondathang_fk, SANPHAM_FK, tonkho, soluong, soluongttduyet, dongia, chietkhau, dvdl_fk,thueVAT,DonGiaGoc,BANGGIAMUANPP_FK,SoTT, ghichu,NgayGiaoHang ) " +
						"select '" + this.id + "', pk_seq, '" + spTonkho[i].replaceAll(",", "") + "', '" + spSoluongtt[i].replaceAll(",", "") + "', '" + spSoluongtt[i].replaceAll(",", "") + "', '" + spGianhap[i].replaceAll(",", "") + "', '" + ck + "', ISNULL( ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ), DVDL_FK ),'"+spVAT[i].replaceAll(",", "")+"','"+spGiagoc[i].replaceAll(",", "")+"','"+spBgId[i].replaceAll(",", "")+"','"+i+"', " +
						"N'"+spGhichu[i].trim()+"','"+spNgaygiaohang[i]+"' from SANPHAM where MA = '" + spMa[i].trim() + "' ";

						System.out.println("1.Insert NK - SP: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_Dondathang_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						}*/
						
						query = "insert ERP_Dondathang_SANPHAM( Dondathang_fk, SANPHAM_FK, tonkho, soluong, soluongttduyet, dongia, chietkhau, dvdl_fk,thueVAT,DonGiaGoc,BANGGIAMUANPP_FK,SoTT, ghichu,NgayGiaoHang ) " +
								"select ?, pk_seq, ?, ?, ?, ?, ?, ISNULL( ( select pk_Seq from DONVIDOLUONG where donvi = ? ), DVDL_FK ),?,?,?,?, " +
								"?,? from SANPHAM where MA = ? ";
						
						Object[] data1 = {this.id,spTonkho[i].replaceAll(",", ""),spSoluongtt[i].replaceAll(",", ""),spSoluongtt[i].replaceAll(",", ""),
								spGianhap[i].replaceAll(",", ""),ck,spDonvi[i].trim(),spVAT[i].replaceAll(",", ""),
								spGiagoc[i].replaceAll(",", ""),spBgId[i].replaceAll(",", ""),i,spGhichu[i].trim(),spNgaygiaohang[i],spMa[i].trim()};
						
						dbutils.viewQuery(query,data1);
						if (this.db.updateQueryByPreparedStatement(query,data1) != 1) {
							this.msg = "1. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý";
							this.db.getConnection().rollback();
							return false;
						}
					}
					else if(spMa[i].trim().length() > 0 && pokhuyenmai.trim().equals("1"))
					{
						String ck = "0";
						if(spChietkhau[i].trim().length() > 0)
							ck = spChietkhau[i].trim().replaceAll(",", "");

			/*			query = "insert ERP_Dondathang_SANPHAM( Dondathang_fk, SANPHAM_FK, tonkho, soluong, soluongttduyet, dongia, chietkhau, dvdl_fk,thueVAT,DonGiaGoc,BANGGIAMUANPP_FK,SoTT, ghichu,NgayGiaoHang, ctkm_fk ) " +
						"select '" + this.id + "', pk_seq, '" + spTonkho[i].replaceAll(",", "") + "', '" + spSoluongtt[i].replaceAll(",", "") + "', '" + spSoluongtt[i].replaceAll(",", "") + "', '" + spGianhap[i].replaceAll(",", "") + "', '" + ck + "', ISNULL( ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ), DVDL_FK ),'"+spVAT[i].replaceAll(",", "")+"','"+spGiagoc[i].replaceAll(",", "")+"','"+spBgId[i].replaceAll(",", "")+"','"+i+"', " +
						"N'"+spGhichu[i].trim()+"','"+spNgaygiaohang[i]+"', '"+spSCheme[i].trim()+"' from SANPHAM where MA = '" + spMa[i].trim() + "' ";

						System.out.println("1.Insert NK - SP: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_Dondathang_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						}*/
						
						query = "insert ERP_Dondathang_SANPHAM( Dondathang_fk, SANPHAM_FK, tonkho, soluong, soluongttduyet, dongia, chietkhau, dvdl_fk,thueVAT,DonGiaGoc,BANGGIAMUANPP_FK,SoTT, ghichu,NgayGiaoHang, ctkm_fk ) " +
								"select ?, pk_seq, ?, ?, ?, ?, ?, ISNULL( ( select pk_Seq from DONVIDOLUONG where donvi = ? ), DVDL_FK ),?,?,?,?, " +
								"?,?, ? from SANPHAM where MA = ? ";
						
						Object[] data1 = {this.id,spTonkho[i].replaceAll(",", ""),spSoluongtt[i].replaceAll(",", ""),spSoluongtt[i].replaceAll(",", ""),
								spGianhap[i].replaceAll(",", ""),ck,spDonvi[i].trim(),spVAT[i].replaceAll(",", ""),spGiagoc[i].replaceAll(",", ""),
								spBgId[i].replaceAll(",", ""),i,spGhichu[i].trim(),spNgaygiaohang[i],spSCheme[i].trim(),spMa[i].trim()};
						
						dbutils.viewQuery(query,data1);
						if (this.db.updateQueryByPreparedStatement(query,data1) != 1) {
							this.msg = "1. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý";
							this.db.getConnection().rollback();
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
			/*			query = "insert ERP_Dondathang_SANPHAM( Dondathang_fk, SANPHAM_FK, tonkho, soluong, soluongttduyet, dongia, chietkhau, dvdl_fk ) " +
						"select '" + this.id + "', pk_seq, '" + spTonkho[i].replaceAll(",", "") + "', '" + spSoluongtt[i].replaceAll(",", "") + "', '" + spSoluongtt[i].replaceAll(",", "") + "', '" + spGianhap[i].replaceAll(",", "") + "', '0', ISNULL( ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ), DVDL_FK ) " +
						"from SANPHAM where MA = '" + spMa[i].trim() + "' ";

						System.out.println("1.Insert NK - SP: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_Dondathang_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						}*/
						
						query = "insert ERP_Dondathang_SANPHAM( Dondathang_fk, SANPHAM_FK, tonkho, soluong, soluongttduyet, dongia, chietkhau, dvdl_fk ) " +
								"select ?, pk_seq, ?, ?, ?, ?, '0', ISNULL( ( select pk_Seq from DONVIDOLUONG where donvi = ? ), DVDL_FK ) " +
								"from SANPHAM where MA = ? ";
						
						Object[] data2 = {this.id,spTonkho[i].replaceAll(",", ""),spSoluongtt[i].replaceAll(",", ""),spSoluongtt[i].replaceAll(",", ""),
								spGianhap[i].replaceAll(",", ""),spDonvi[i].trim(),spMa[i].trim()};
						
						dbutils.viewQuery(query,data2);
						if (this.db.updateQueryByPreparedStatement(query,data2) != 1) {
							this.msg = "1. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý";
							this.db.getConnection().rollback();
							return false;
						}
					}
				}
			}

			//INSERT CHIET KHAU BO SUNG
			if(this.dhCk_diengiai != null)
			{
				for(int i = 0; i < this.dhCk_diengiai.length; i++)
				{
					if(this.dhCk_giatri[i].trim().length() > 0)
					{
				/*		query = "insert ERP_DONDATHANG_CHIETKHAU(DONDATHANG_FK, DIENGIAI, GIATRI, LOAI,CHIETKHAU) " +
						"values( '" + this.id + "', N'" + this.dhCk_diengiai[i].trim() + "', '" + this.dhCk_giatri[i].replaceAll(",", "") + "', '" + this.dhCk_loai[i] + "','" + this.Dhck_chietkhau[i] + "' ) ";

						System.out.println("1.Insert DH - CK: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_DONDATHANG_CHIETKHAU: " + query;
							db.getConnection().rollback();
							return false;
						}*/
						
						query = "insert ERP_DONDATHANG_CHIETKHAU(DONDATHANG_FK, DIENGIAI, GIATRI, LOAI,CHIETKHAU) " +
								"values( ?, ?,"
										+ " ?, ?,? ) ";
						
						Object[] data2 = {this.id,this.dhCk_diengiai[i].trim(),this.dhCk_giatri[i].replaceAll(",", ""),this.dhCk_loai[i],this.Dhck_chietkhau[i]};
						
						dbutils.viewQuery(query,data2);
						if (this.db.updateQueryByPreparedStatement(query,data2) != 1) {
							this.msg = "1. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý";
							this.db.getConnection().rollback();
							return false;
						}
					}
				}
			}

			
			String kq = geso.dms.distributor.util.Utility.Update_GiaTri_ERP_DonDatHang(this.id, db,false);
			if(kq.trim().length() > 0)
			{
				this.msg =kq;
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			e.printStackTrace();
			this.msg = "Exception: " + e.getMessage();
			return false;
		}

		return true;
	}

	public boolean updateNK(String checkKM) 
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

		if(this.nppId.trim().length() > 0)
		{
			String sqlnpp ="select * from BANGGIAMUANPP_NPP where npp_fk = "+nppId+" ";

			ResultSet rsnpp = db.get(sqlnpp);
			if(rsnpp != null)
			{
				try {
					while(!rsnpp.next())
					{
						msg = "Vui lòng thiết lập bảng giá cho NPP ";
						return false;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
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


			//NEU LA CAP NHAT, MA THAY DOI SP HOAC SO LUONG THI PHAI AP LAI KM
			if(checkKM.equals("1"))
			{
				boolean coKM = false;
				String sql = "select count(*) as soDONG from ERP_DONDATHANG_CTKM_TRAKM where DONDATHANGID = '" + this.id + "' ";
				ResultSet rs = db.get(sql);
				if(rs != null)
				{
					try 
					{
						if(rs.next())
						{
							if(rs.getInt("soDONG") > 0 )
								coKM = true;
						}
						rs.close();
					}
					catch (Exception e) {}
				}

				if(coKM)
				{
					String table="";
					for(int i = 0; i < spMa.length; i++)
					{
						if( spMa[i].trim().length() > 0 && spSoluongtt[i].trim().length() > 0 && spDonvi[i].trim().length() > 0 && spSCheme[i].trim().length() <= 0 )
						{
							float SoLuong = Float.parseFloat(spSoluongtt[i].replaceAll(",", "") );
							if(i==0)
								table ="select '"+spMa[i]+"' as spma, '"+SoLuong +"' as SoLuong ";
							else 
							{
								table +="union all select '"+spMa[i]+"' as spma, '"+SoLuong +"' as SoLuong ";
							}
						}
					}					
					String query=
						"select isnull(a.spMa,b.spMa) as spMa,a.SoLuong as SoLuong_Truoc,b.SoLuong as SoLuong_Sau  \n"+  
						"	from   \n"+
						"	(  \n"+
						"		select a.sanpham_fk as spId,sum(a.soluongTTDUYET) as SoLuong, b.ma as spMa  \n"+ 
						"		from ERP_DONDATHANG_SANPHAM a inner join sanpham b on a.sanpham_fk = b.pk_seq  \n"+ 
						"		where a.DONDATHANG_FK= '"+id+"'  \n"+
						"		group by a.SANPHAM_FK,b.MA \n"+
						"	)as a full outer join \n"+ 
						"	( \n"+
						"		select temp.spMa,SUM(cast(temp.SoLuong as float) ) as SoLuong \n"+
						"		from   \n"+
						"		(  \n"+
						"			"+table+"  \n"+ 
						"		)as temp \n"+
						"		group by temp.spMa   \n"+
						"	)as b on a.spMa=b.spMa \n"+
						"	where isnull(a.SoLuong,0)!=isnull(b.SoLuong,0) ";

					System.out.println("[coKm]"+query);

					if(table.length()<=0)
					{
						this.msg = "Khi thay đổi thông tin sản phẩm, số lượng, đơn vị trong đơn hàng, bạn phải bấm áp lại khuyến mại.";
						return false;
					}else 
					{
						rs =db.get(query);
						try
						{
							if(rs.next())
							{
								this.msg = "Khi thay đổi thông tin sản phẩm, số lượng, đơn vị trong đơn hàng, bạn phải bấm áp lại khuyến mại.";
								return false;
							}

						} catch (SQLException e)
						{
							e.printStackTrace();
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
		/*		String query =	
					" Update ERP_Dondathang set  ngaydonhang = '" + this.ngayyeucau + "', "+
					" ngaydenghi = '" + this.ngaydenghi + "', ghichu = N'" + this.ghichu + "', tiengiam = '"+this.tiengiam+"', dvkd_fk = '" + this.dvkdId + "', "+
					" kbh_fk = '" + this.kbhId + "', npp_fk = '" + this.nppId + "', kho_fk = " + khonhan_fk + ", ngaysua = '" + getDateTime() + "', "+
					" nguoisua = '" + this.userId + "', chietkhau = '" + chietkhau + "', vat = '" + vat + "' " + 
					" where pk_seq = '" + this.id + "' ";

				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật ERP_Dondathang " + query;
					db.getConnection().rollback();
					return false;
				}*/
				
				String query =	
						" Update ERP_Dondathang set  ngaydonhang = ?, "+
						" ngaydenghi = ?, ghichu = ?, tiengiam = ?, dvkd_fk = ?, "+
						" kbh_fk = ?, npp_fk = ?, kho_fk = ?, ngaysua = ?, "+
						" nguoisua = ?, chietkhau = ?, vat = ? " + 
						" where pk_seq = ? ";
				
				Object[] data = {this.ngayyeucau,this.ngaydenghi,this.ghichu,this.tiengiam,this.dvkdId,this.kbhId,this.nppId,khonhan_fk,
						getDateTime(),this.userId,chietkhau,vat,this.id};
				dbutils.viewQuery(query,data);
				if (this.db.updateQueryByPreparedStatement(query,data) != 1) {
					this.msg = "1. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý";
					this.db.getConnection().rollback();
					return false;
				}

				query = "delete ERP_Dondathang_SANPHAM where isNppDat = 0 and Dondathang_fk = '" + this.id + "'";
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật ERP_Dondathang_SANPHAM " + query;
					db.getConnection().rollback();
					return false;
				}

				query = "delete ERP_DONDATHANG_CHIETKHAU where CHIETKHAUDATHANG_FK IS NULL AND Dondathang_fk = '" + this.id + "' ";
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật ERP_DONDATHANG_CHIETKHAU " + query;
					db.getConnection().rollback();
					return false;
				}

				if(this.loaidonhang.equals("0"))  //DON DAT HANG CHI LUU NHUNG SP KO CO SCHEME
				{
					for(int i = 0; i < spMa.length; i++)
					{			
						if(spMa[i].trim().length() > 0 && spSCheme[i].trim().length() <= 0 )
						{
							query = "select SUM(soluong) as soluong from nhaphang a inner join nhaphang_sp b on a.PK_SEQ = b.NHAPHANG_FK "+ 
							"where a.DONDATHANG_FK = "+this.id+" and SANPHAM_FK = '"+spMa[i].trim()+"' and a.trangthai <> 2 and isnull(b.scheme, '') = '' ";
							ResultSet rscheck = db.get(query);
							if(rscheck != null)
							{
								if(rscheck.next())
								{
									double soluong = rscheck.getDouble("soluong");
									double soluongtt = Double.parseDouble(spSoluongtt[i].replaceAll(",", ""));
									if(soluongtt < soluong)
									{
										this.msg = "Sản phẩm "+spMa[i].trim()+" đã nhận "+soluong+" bạn không thể điều chỉnh nhỏ hơn số lượng nhận";
										db.getConnection().rollback();
										return false;
									}
								}rscheck.close();
							}
							String ck = "0";
							if(spChietkhau[i].trim().length() > 0)
								ck = spChietkhau[i].trim().replaceAll(",", "");

							Object[] data1;
							
							if(isNppDat[i].equals("1"))
							{
							/*	query = "\n update ERP_Dondathang_SANPHAM set    soluong =" + spSoluongtt[i].replaceAll(",", "") + " , soluongttduyet = " + spSoluongtt[i].replaceAll(",", "") + "" +
								"\n , ghichu = N'"+spGhichu[i].trim()+"'  ,NgayGiaoHang = '"+spNgaygiaohang[i]+"' " +
								"\n where  Dondathang_fk = "+this.id+"  and SANPHAM_FK = (select pk_seq from sanpham where ma ='"+spMa[i].trim()+"' )  " ;	*/
								
								query = "\n update ERP_Dondathang_SANPHAM set    soluong =? , soluongttduyet = ?" +
										"\n , ghichu = ?  ,NgayGiaoHang = ? " +
										"\n where  Dondathang_fk = ?  and SANPHAM_FK = (select pk_seq from sanpham where ma =? )  " ;	

								data1 = new Object[] {spSoluongtt[i].replaceAll(",", ""),spSoluongtt[i].replaceAll(",", ""),spGhichu[i].trim(),spNgaygiaohang[i],
									this.id,spMa[i].trim()};
							}
							else
							{					
						/*		query = "insert ERP_Dondathang_SANPHAM(soLuongNPPdat ,Dondathang_fk, SANPHAM_FK, tonkho, soluong, soluongttduyet, dongia, chietkhau, dvdl_fk,thueVAT,DonGiaGoc,BANGGIAMUANPP_FK,SoTT, ghichu,NgayGiaoHang ) " +
								"select 0,'" + this.id + "', pk_seq, '" + spTonkho[i].replaceAll(",", "") + "', '" + spSoluongtt[i].replaceAll(",", "") + "', '" + spSoluongtt[i].replaceAll(",", "") + "', '" + spGianhap[i].replaceAll(",", "") + "', '" + ck + "', ISNULL( ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ), DVDL_FK ),'"+spVAT[i].replaceAll(",", "")+"','"+spGiagoc[i].replaceAll(",", "")+"','"+spBgId[i].replaceAll(",", "")+"','"+i+"', " +
								"N'"+spGhichu[i].trim()+"','"+spNgaygiaohang[i]+"' from SANPHAM where MA = '" + spMa[i].trim() + "' ";*/
								
								query = "insert ERP_Dondathang_SANPHAM(soLuongNPPdat ,Dondathang_fk, SANPHAM_FK, tonkho, soluong, soluongttduyet, dongia, chietkhau, dvdl_fk,thueVAT,DonGiaGoc,BANGGIAMUANPP_FK,SoTT, ghichu,NgayGiaoHang ) " +
										"select 0, ?, pk_seq, ?, ?, ?, ?, ?, ISNULL( ( select pk_Seq from DONVIDOLUONG where donvi = ? ), DVDL_FK ),?,?,?,?, " +
										"?,? from SANPHAM where MA = ? ";
								
								data1 = new Object[] {this.id,spTonkho[i].replaceAll(",", ""),spSoluongtt[i].replaceAll(",", ""),spSoluongtt[i].replaceAll(",", ""),
										spGianhap[i].replaceAll(",", ""),ck,spDonvi[i].trim(),spVAT[i].replaceAll(",", ""),
										spGiagoc[i].replaceAll(",", ""),spBgId[i].replaceAll(",", ""),i,spGhichu[i].trim(),spNgaygiaohang[i],spMa[i].trim()};
							}
						/*	if(db.updateReturnInt(query)<=0)
							{
								this.msg = "Khong the tao moi ERP_Dondathang_SANPHAM: " + query;
								db.getConnection().rollback();
								return false;
							}*/
							
							dbutils.viewQuery(query,data1);
							if (this.db.updateQueryByPreparedStatement(query,data1) != 1) {
								this.msg = "1. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý";
								this.db.getConnection().rollback();
								return false;
							}
							
						}/*else
						{
							if(spMa[i].trim().length() > 0 && spSCheme[i].trim().length() > 0 && spSoluongtt[i].trim().length()>0 )
							{
								query= " update ERP_DONDATHANG_CTKM_TRAKM  set SoLuongTT='"+spSoluongtt[i]+"'" +
										"	where DONDATHANGID='"+this.id+"' and spMa='"+spMa[i]+"'  and ctkmId= ( select pk_Seq from ctKhuyenMai where scheme =N'"+spSCheme[i]+"')" +
										"  and trakmId='"+spTrakmId[i]+"' ";
								if(db.updateReturnInt(query)!=1)
								{
									this.msg = "Khong the tao moi ERP_DONDATHANG_CTKM_TRAKM: " + query;
									db.getConnection().rollback();
									return false;
								}
							}
						}*/
						else if(spMa[i].trim().length() > 0 && pokhuyenmai.trim().equals("1"))
						{
							query = "select SUM(soluong) as soluong from nhaphang a inner join nhaphang_sp b on a.PK_SEQ = b.NHAPHANG_FK "+ 
							"where a.DONDATHANG_FK = "+this.id+" and SANPHAM_FK = '"+spMa[i].trim()+"' and a.trangthai <> 2 and isnull(b.scheme, '') = '' ";
							ResultSet rscheck = db.get(query);
							if(rscheck != null)
							{
								if(rscheck.next())
								{
									double soluong = rscheck.getDouble("soluong");
									double soluongtt = Double.parseDouble(spSoluongtt[i].replaceAll(",", ""));
									if(soluongtt < soluong)
									{
										this.msg = "Sản phẩm "+spMa[i].trim()+" đã nhận "+soluong+" bạn không thể điều chỉnh nhỏ hơn số lượng nhận";
										db.getConnection().rollback();
										return false;
									}
								}rscheck.close();
							}
							String ck = "0";
							if(spChietkhau[i].trim().length() > 0)
								ck = spChietkhau[i].trim().replaceAll(",", "");

						/*	query = "insert ERP_Dondathang_SANPHAM( Dondathang_fk, SANPHAM_FK, tonkho, soluong, soluongttduyet, dongia, chietkhau, dvdl_fk,thueVAT,DonGiaGoc,BANGGIAMUANPP_FK,SoTT, ghichu,NgayGiaoHang, ctkm_fk ) " +
							"select '" + this.id + "', pk_seq, '" + spTonkho[i].replaceAll(",", "") + "', '" + spSoluongtt[i].replaceAll(",", "") + "', '" + spSoluongtt[i].replaceAll(",", "") + "', '" + spGianhap[i].replaceAll(",", "") + "', '" + ck + "', ISNULL( ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ), DVDL_FK ),'"+spVAT[i].replaceAll(",", "")+"','"+spGiagoc[i].replaceAll(",", "")+"','"+spBgId[i].replaceAll(",", "")+"','"+i+"', " +
							"N'"+spGhichu[i].trim()+"','"+spNgaygiaohang[i]+"', '"+spSCheme[i].trim()+"' from SANPHAM where MA = '" + spMa[i].trim() + "' ";

							System.out.println("1.Insert NK - SP: " + query);
							if(!db.update(query))
							{
								this.msg = "Khong the tao moi ERP_Dondathang_SANPHAM: " + query;
								db.getConnection().rollback();
								return false;
							}*/
							
							query = "insert ERP_Dondathang_SANPHAM( Dondathang_fk, SANPHAM_FK, tonkho, soluong, soluongttduyet, dongia, chietkhau, dvdl_fk,thueVAT,DonGiaGoc,BANGGIAMUANPP_FK,SoTT, ghichu,NgayGiaoHang, ctkm_fk ) " +
									"select ?, pk_seq, ?, ?,?, ?, ?, ISNULL( ( select pk_Seq from DONVIDOLUONG where donvi = ? ), DVDL_FK ),?,?,?,?, " +
									"?,?, ? from SANPHAM where MA = ? ";
							
							Object[] data1 = {this.id,spTonkho[i].replaceAll(",", ""),spSoluongtt[i].replaceAll(",", ""),spSoluongtt[i].replaceAll(",", ""),
									spGianhap[i].replaceAll(",", ""),ck,spDonvi[i].trim(),spVAT[i].replaceAll(",", ""),spGiagoc[i].replaceAll(",", ""),
									spBgId[i].replaceAll(",", ""),i,spGhichu[i].trim(),spNgaygiaohang[i],spSCheme[i].trim(),spMa[i].trim()};
							
							dbutils.viewQuery(query,data1);
							if (this.db.updateQueryByPreparedStatement(query,data1) != 1) {
								this.msg = "1. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý";
								this.db.getConnection().rollback();
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
							query = "select SUM(soluong) as soluong from nhaphang a inner join nhaphang_sp b on a.PK_SEQ = b.NHAPHANG_FK "+ 
							"where a.DONDATHANG_FK = "+this.id+" and SANPHAM_FK = '"+spMa[i].trim()+"' and a.trangthai <> 2 and isnull(b.scheme, '') = '' ";
							ResultSet rscheck = db.get(query);
							if(rscheck != null)
							{
								if(rscheck.next())
								{
									double soluong = rscheck.getDouble("soluong");
									double soluongtt = Double.parseDouble(spSoluongtt[i].replaceAll(",", ""));
									if(soluongtt < soluong)
									{
										this.msg = "Sản phẩm "+spMa[i].trim()+" đã nhận "+soluong+" bạn không thể điều chỉnh nhỏ hơn số lượng nhận";
										db.getConnection().rollback();
										return false;
									}
								}rscheck.close();
							}

							String ck = "0";
							if(spChietkhau[i].trim().length() > 0)
								ck = spChietkhau[i].trim().replaceAll(",", "");
					/*		query = "insert ERP_Dondathang_SANPHAM( Dondathang_fk, SANPHAM_FK, tonkho, soluong, soluongttduyet, dongia, chietkhau, dvdl_fk,thueVAT ) " +
							"select '" + this.id + "', pk_seq, '" + spTonkho[i].replaceAll(",", "") + "', '" + spSoluong[i].replaceAll(",", "") + "', '" + spSoluongtt[i].replaceAll(",", "") + "', '" + spGianhap[i].replaceAll(",", "") + "', '" + ck + "', ISNULL( ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ), DVDL_FK ),'"+spVAT[i].replaceAll(",", "")+"' " +
							"from SANPHAM where MA = '" + spMa[i].trim() + "' ";

							System.out.println("1.Insert NK - SP: " + query);
							if(!db.update(query))
							{
								this.msg = "Khong the tao moi ERP_Dondathang_SANPHAM: " + query;
								db.getConnection().rollback();
								return false;
							}*/
							
							String temp23 = spTonkho[i].replaceAll(",","");
							String temp34 = spSoluong[i].replaceAll(",","");
							String temp45 = spSoluongtt[i].replaceAll(",","");
							String temp56 = spGianhap[i].replaceAll(",","");

							query = "INSERT ERP_DONDATHANG_SANPHAM( Dondathang_fk, SANPHAM_FK, tonkho, soluong, soluongttduyet, dongia, chietkhau, dvdl_fk,thueVAT ) "
									+ " select ?,pk_seq,?,?,?,?,?,?,DVDL_FK) " +
									"from SANPHAM where MA = ? ";

							Object[] data1 = {this.id,temp23,temp34,temp45,temp56,ck,spDonvi[i].trim(),spMa[i].trim()};
							dbutils.viewQuery(query,data1);
							if (this.db.updateQueryByPreparedStatement(query,data1) != 1) {
								this.msg = "Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý!";
								this.db.getConnection().rollback();
								return false;
							}
						}
					}
				}


				//INSERT CHIET KHAU BO SUNG
				if(this.dhCk_diengiai != null)
				{
					for(int i = 0; i < this.dhCk_diengiai.length; i++)
					{
						if(this.dhCk_giatri[i].trim().length() > 0 && !this.dhCk_giatri[i].trim().equals("0"))
						{
					/*		query = "insert ERP_DONDATHANG_CHIETKHAU(DONDATHANG_FK, DIENGIAI, GIATRI, LOAI,CHIETKHAU) " +
							"values( '" + this.id + "', N'" + this.dhCk_diengiai[i].trim() + "', '" + this.dhCk_giatri[i].replaceAll(",", "") + "', '" + this.dhCk_loai[i] + "','" + this.Dhck_chietkhau[i] + "' ) ";
							System.out.println("1.Insert DH - CK: " + query);
							if(!db.update(query))
							{
								this.msg = "Khong the tao moi ERP_DONDATHANG_CHIETKHAU: " + query;
								db.getConnection().rollback();
								return false;
							}*/
							
							String temp23 = this.dhCk_giatri[i].replaceAll(",","");

							query = "INSERT ERP_DONDATHANG_CHIETKHAU(DONDATHANG_FK, DIENGIAI, GIATRI, LOAI,CHIETKHAU) select ?,?,?,?,?";

							Object[] data1 = {this.id,this.dhCk_diengiai[i].trim(),temp23,this.dhCk_loai[i],this.Dhck_chietkhau[i]};
							dbutils.viewQuery(query,data1);
							if (this.db.updateQueryByPreparedStatement(query,data1) != 1) {
								this.msg = "Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý!";
								this.db.getConnection().rollback();
								return false;
							}
						}
					}
				}

				String kq = geso.dms.distributor.util.Utility.Update_GiaTri_ERP_DonDatHang(this.id, db,false);
				if(kq.trim().length() > 0)
				{
					this.msg =kq;
					db.getConnection().rollback();
					return false;
				}
				
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
			} 
			catch (Exception e) 
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				e.printStackTrace();
				this.msg = "Exception: " + e.getMessage();
				return false;
			}

			return true;
		}
	}

	public void createSP()
	{
		String query = "";
		if(this.loaidonhang.equals("2"))
		{
			if(this.schemeId.trim().length() > 0 && this.nppId.trim().length() > 0 )
			{
				query =  " select TICHLUY.ctkmId, ctkm.scheme, ctkm.diengiai, TICHLUY.soXUAT, SPTRA.sanpham_fk, SPTRA.ma, SPTRA.ten, SPTRA.donvi, SPTRA.dongia,  "+  
				" 	round( TICHLUY.soXUAT - 0.5, 0) as tongluong, ISNULL(DATRA.SOLUONG, 0) as datra, ISNULL(TRA.SOLUONG, 0) as slgtra, "+  
				" 	round( TICHLUY.soXUAT - 0.5, 0) - ISNULL(DATRA.SOLUONG, 0) as conLAI "+  
				" from   "+  
				" (   "+  
				" 	select a.ctkmId, SUM(a.soXUAT) as soXUAT "+  
				" 	from DONHANG_CTKM_TRAKM a left join DONHANG b on b.PK_SEQ = a.donhangID  "+  
				" 	where a.ctkmId in ( " + this.schemeId + " ) and b.npp_fk = '" + this.nppId + "'  "+  
				" 	group by a.ctkmId  "+  
				" )   "+  
				" TICHLUY inner join   "+  
				" ( "+  
				"  select d.pk_seq as ctkmId, sanpham_fk, b.ma, b.ten, c.diengiai as donvi, dongia  "+  
				"  from TIEUCHITHUONGTL_SPTRA a inner join SANPHAM b on a.sanpham_fk = b.pk_seq "+  
				" 			inner join DONVIDOLUONG c on b.dvdl_fk = c.pk_seq "+  
				" 			inner join CTKHUYENMAI d on a.thuongtl_fk = d.thuongtl_fk "+  
				"  where d.pk_seq in ( " + this.schemeId + " ) " +
				" ) "+  
				" SPTRA on TICHLUY.ctkmId = SPTRA.ctkmId left join "+  
				" (   "+  
				" 	select b.ctkm_fk, SUM(b.soluong) as soluong    "+  
				" 	from ERP_DONDATHANG a inner join ERP_DONDATHANG_SANPHAM b on a.PK_SEQ = b.dondathang_fk   "+  
				" 	where a.LoaiDonHang = '2' and a.TRANGTHAI != 3 and b.ctkm_fk in ( " + this.schemeId + "  ) and a.NPP_FK = '" + this.nppId + "' and a.pk_seq != '" + ( this.id.trim().length() > 0 ? this.id : "-1" ) + "'  "+  
				" 	group by b.ctkm_fk   "+  
				" )   "+  
				" DATRA on TICHLUY.ctkmId = DATRA.ctkm_fk left join "+
				" (   "+  
				" 	select b.ctkm_fk, SUM(b.soluong) as soluong    "+  
				" 	from ERP_DONDATHANG a inner join ERP_DONDATHANG_SANPHAM b on a.PK_SEQ = b.dondathang_fk   "+  
				" 	where b.ctkm_fk in ( " + this.schemeId + "  ) and a.NPP_FK = '" + this.nppId + "' and a.pk_seq = '" + ( this.id.trim().length() > 0 ? this.id : "-1" ) + "'  "+  
				" 	group by b.ctkm_fk   "+  
				" )" +
				" TRA on TICHLUY.ctkmId = TRA.ctkm_fk   "+  
				" inner join CTKHUYENMAI ctkm on TICHLUY.ctkmId = ctkm.pk_seq "+  
				" where round( TICHLUY.soXUAT - 0.5, 0) - ISNULL(DATRA.SOLUONG, 0) >= 1  ";

				System.out.println("---INIT TICH LUY: " + query);
				ResultSet rs = db.get(query);
				if(rs != null)
				{
					try 
					{
						String _schemeID = "";
						String _scheme = "";
						String _diengiai = "";
						String _sanphamID = "";
						String _sanpham = "";
						String _donvi = "";
						String _tongluong = "";
						String _datra = "";
						String _conlai = "";
						String _soluong = "";

						NumberFormat format = new DecimalFormat("#,###,###");
						while(rs.next())
						{
							_schemeID += rs.getString("ctkmId") + "__";
							_scheme += rs.getString("SCHEME") + "__";
							_diengiai += rs.getString("DIENGIAI") + "__";
							_sanphamID += rs.getString("SANPHAM_FK") + "__";
							_sanpham += rs.getString("TEN") + "__";
							_donvi += rs.getString("DONVI") + "__";
							_tongluong += format.format(rs.getDouble("tongluong")) + "__";
							_datra += format.format(rs.getDouble("datra")) + "__";
							_conlai += format.format(rs.getDouble("conlai")) + "__";
							_soluong += format.format(rs.getDouble("slgtra")) + "__";
						}
						rs.close();

						if(_scheme.trim().length() > 0)
						{
							_schemeID = _schemeID.substring(0, _schemeID.length() -2 );
							this.spSChemeIds = _schemeID.split("__");

							_scheme = _scheme.substring(0, _scheme.length() -2 );
							this.spSCheme = _scheme.split("__");

							_diengiai = _diengiai.substring(0, _diengiai.length() -2 );
							this.spSChemeDiengiai = _diengiai.split("__");

							_sanphamID = _sanphamID.substring(0, _sanphamID.length() -2 );
							this.spId = _sanphamID.split("__");

							_sanpham = _sanpham.substring(0, _sanpham.length() -2 );
							this.spTen = _sanpham.split("__");

							_donvi = _donvi.substring(0, _donvi.length() -2 );
							this.spDonvi = _donvi.split("__");

							_tongluong = _tongluong.substring(0, _tongluong.length() -2 );
							this.spTrongluong = _tongluong.split("__");

							_datra = _datra.substring(0, _datra.length() -2 );
							this.spThetich = _datra.split("__");

							_soluong = _soluong.substring(0, _soluong.length() -2 );
							this.spSoluong = _soluong.split("__");

						}
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}
				}
			}
		}
		else if(loaidonhang.equals("1"))
		{
			/*query = "select PK_SEQ, SCHEME from CTKHUYENMAI where  KHO_FK = '100000' order by DENNGAY desc ";
			this.schemeRs = db.get(query);*/
			if(this.nppId.trim().length() > 0 )
			{
				query = 
					" select UNGHANG.PK_SEQ, UNGHANG.SCHEME " +			
					" from " +
					" ( " +
					" 	select c.PK_SEQ, c.SCHEME, c.denngay, SUM(a.SOLUONG) as tongLUONG  " +
					" 	from DONHANG_CTKM_TRAKM a inner join SANPHAM b on a.SPMA = b.MA " +
					" 	inner join CTKHUYENMAI c on a.CTKMID = c.PK_SEQ " +
					" 	where c.kho_fk = '100000' and SPMA is not null " +
					"	and a.DONHANGID in ( select pk_seq from DONHANG where trangthai = '1' and NPP_FK = '" + this.nppId + "' ) " +
					" 	group by c.PK_SEQ, c.SCHEME, c.DENNGAY " +
					" ) UNGHANG " +
					" left join " +
					" ( " +
					" 	select b.ctkm_fk, SUM(b.soluong) as soluong  " +
					" 	from ERP_DONDATHANG a inner join ERP_DONDATHANG_SANPHAM b on a.PK_SEQ = b.dondathang_fk " +
					" 	where a.LoaiDonHang = '1' and a.TRANGTHAI != 3 " +
					"	and a.NPP_FK = '" + this.nppId + "'  " + ( ( this.id.trim().length() > 0 ) ? " AND a.pk_seq != '" + this.id + "' " : "" ) +
					" 	group by b.ctkm_fk " +
					" ) DUYET on UNGHANG.PK_SEQ = DUYET.ctkm_fk " +
					" where UNGHANG.tongLUONG - ISNULL(DUYET.SOLUONG, 0) > 0 " +
					" order by unghang.DENNGAY desc ";
				System.out.println("Scheme : "+query);
				this.schemeRs = db.get(query);
			}

			if(this.schemeId.trim().length() > 0 && this.nppId.trim().length() > 0 )
			{
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
				query = " select UNGHANG.PK_SEQ, UNGHANG.SCHEME, UNGHANG.DIENGIAI,  UNGHANG.tongLUONG - ISNULL(DUYET.SOLUONG, 0) as tongLUONG " +
				" from " +
				" ( " +
				"  select d.PK_SEQ, d.SCHEME, d.DIENGIAI,   " +
				"  	sum(b.xuatduyet) * ( select SUM(isnull(TONGLUONG, 0)) from TRATRUNGBAY where PK_SEQ in ( select TRATRUNGBAY_FK from CTTB_TRATB where CTTRUNGBAY_FK = d.PK_SEQ ) ) as tongluong  " +
				"  from DENGHITRATRUNGBAY a inner join DENGHITRATB_KHACHHANG b on a.PK_SEQ = b.DENGHITRATB_FK  " +
				"  		inner join DANGKYTRUNGBAY c on a.CTTRUNGBAY_FK  = c.CTTRUNGBAY_FK  " +
				"  		inner join CTTRUNGBAY d on c.CTTRUNGBAY_FK = d.PK_SEQ  " +
				"  where c.TRANGTHAI = '1' and a.NPP_FK = '" + this.nppId + "' and b.XUATDUYET != 0 and d.PK_SEQ in ( " + this.schemeId + " ) " +
				"  group by d.PK_SEQ, d.SCHEME, d.DIENGIAI    " +
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

				System.out.println("---DON HANG TRUNG BAY: " + query);
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
		else
		{
			initSANPHAM();
		}
	}

	public void createScheme()
	{
		String query = "";
		if(this.loaidonhang.equals("2"))
		{
			query = "select PK_SEQ, SCHEME from CTKHUYENMAI where  loaict = '3' order by DENNGAY desc";
			this.schemeRs = db.get(query);

		}
		else if(this.loaidonhang.equals("1"))
		{
			query = "select PK_SEQ, SCHEME from CTKHUYENMAI where  KHO_FK = '100000' order by DENNGAY desc ";			
			this.schemeRs = db.get(query);	
		}
		else if(this.loaidonhang.equals("3"))
		{
			query = "select PK_SEQ, SCHEME from CTTRUNGBAY order by pk_seq desc ";
			this.schemeRs = db.get(query);					
		}		
		System.out.println("scheme : "+query);
	}

	public void createRs() 
	{			
		Utility util =new Utility(); 

		String loainpp = "", kho = "khott";
		String query = "select loainpp, khosap from NHAPHANPHOI where PK_SEQ='"+this.nppId+"'";
		ResultSet rs = db.get(query);
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
		this.khoNhanRs = db.get("select PK_SEQ, TEN + '-' + DIENGIAI AS TEN from "+kho+" where trangthai = '1'"  );

		this.dvtRs = db.getScrol("select PK_SEQ, DONVI from DONVIDOLUONG where trangthai = '1' ");		
		this.dvkdRs = db.get("select PK_SEQ, DONVIKINHDOANH as TEN from DONVIKINHDOANH where TRANGTHAI = '1'");
		this.kbhRs = db.get("select PK_SEQ, DIENGIAI as TEN from KENHBANHANG where TRANGTHAI = '1' and pk_Seq in "+util.quyen_kenh(userId));

		query = "select PK_SEQ, MA + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1'  AND PK_sEQ IN "+util.quyen_npp(userId)+" ";
		if(this.kbhId.equals("100021"))
			query = "select PK_SEQ, MA + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1' ";
		if(this.dvkdId.trim().length() > 0)
			query += " and pk_seq in ( select NPP_FK from NHAPP_NHACC_DONVIKD where NCC_DVKD_FK in ( select PK_SEQ from NHACUNGCAP_DVKD where DVKD_FK = '" + this.dvkdId + "' ) ) ";
		if(this.kbhId.trim().length() > 0)
			query += " and pk_seq in ( select NPP_FK from NHAPP_KBH where KBH_FK = '" + this.kbhId + "' ) ";

		this.nppRs = db.get(query);

		if(this.nppId.trim().length() > 0)
		{
			query = "select isnull( a.chietkhau,0) chietkhau ,a.PK_SEQ as nppId, d.DVKD_FK, b.KBH_FK  " +
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
						if(!this.trangthai.equals("1") && !this.trangthai.equals("3"))
							this.chietkhau = rsInfo.getString("chietkhau");

						if(this.dvkdId.trim().length() <= 0)
							this.dvkdId = rsInfo.getString("DVKD_FK");
						if(this.kbhId.trim().length() <= 0 )
							this.kbhId = rsInfo.getString("KBH_FK");
					}
					rsInfo.close();
				} 
				catch (Exception e) {e.printStackTrace();}
			}
			//INIT CONG NO
			try
			{
				this.congNo  = "0";
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		this.createScheme();		
		this.schemespecialRs = db.getScrol("select PK_SEQ, SCHEME from CTKHUYENMAI where  loaict = '4' and isduyet = '1' order by DENNGAY desc");
	}

	private void initSANPHAM() 
	{
		if(this.id.length()>0)
		{
			String query =  "select a.isNppDat,b.MA, b.TEN, DV.donvi, a.tonkho, a.soLuongNPPdat soluong, a.dongia, a.thueVAT,a.ChietKhau,a.DonGiaGoc,"+
			"isnull(BANGGIAMUANPP_FK,0) as BANGGIAMUANPP_FK, a.soluongttduyet, a.ghichu,a.NgayGiaoHang, a.ctkm_fk    " +
			" from ERP_Dondathang_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ    " +
			" 		INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK       " +
			"where a.Dondathang_FK = '" + this.id + "' " +
			"  order by a.sott ";

			System.out.println("--INIT SP: " + query);
			ResultSet spRs = db.get(query);


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
					String sPisNppDat="";

					while(spRs.next())
					{
						spMA += spRs.getString("MA") + "__";
						spTEN += spRs.getString("TEN") + "__";
						spDONVI += spRs.getString("DONVI") + "__";
						spTONKHO += spRs.getString("TONKHO") + "__";
						spSOLUONG += formater.format(spRs.getDouble("SOLUONG")) + "__";
						spGIANHAP += formater2.format(spRs.getDouble("DONGIA")) + "__";
						spVAT += spRs.getDouble("thueVAT") + "__";
						spSCHEME += spRs.getString("ctkm_fk")==null?" __":spRs.getString("ctkm_fk")+"__";
						spCHIETKHAU += formater2.format(spRs.getDouble("ChietKhau")) + "__";
						spDONGIAGOC += formater.format(spRs.getDouble("DonGiaGoc")) + "__";
						spBGID += spRs.getString("BANGGIAMUANPP_FK") + "__";
						spSOLUONGTT += formater.format(spRs.getDouble("SOLUONGTTDUYET")) + "__";
						spGHICHU += spRs.getString("GHICHU")==null?" __":spRs.getString("GHICHU") + " __";

						spNGAYGIAOHANG += (spRs.getString("NGAYGIAOHANG")==null||spRs.getString("NGAYGIAOHANG").trim().length()<=0)?" __":spRs.getString("NGAYGIAOHANG") + " __";
						spTRAKMID+=" __";
						sPisNppDat += spRs.getString("isNppDat") +  "__";
					}
					spRs.close();

					//INIT SP KHUYEN MAI
					query = "select  a.TRAKMID,isnull(b.MA, ' ') as MA, isnull(b.TEN, ' ') as TEN, isnull(c.DONVI, ' ') as donvi, d.SCHEME, isnull(a.soluong, 0) as soluong, isnull(a.soluongTT, a.soluong) as soluongTT , a.tonggiatri,0 as ChietKhau " +
					"from ERP_DONDATHANG_CTKM_TRAKM a left join SANPHAM b on a.SPMA = b.MA  " +
					"	left join DONVIDOLUONG c on b.DVDL_FK = c.PK_SEQ  " +
					"	inner join CTKHUYENMAI d on a.ctkmID = d.PK_SEQ " +
					"where a.dondathangID = '" + this.id + "' " ;
					System.out.println("--INIT SPKM: " + query);

					spRs = db.get(query);
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

						sPisNppDat +=   "0__";
					}
					spRs.close();

					query=					
						"	SELECT DONDATHANG_FK,DIENGIAI as SCHEME ,0 as SoLuong ,LOAI,ChietKhau as tonggiatri,0 as ChietKhau,N'CKĐH' AS MA,SCHEME AS TEN,'CK' AS donvi  "+ 
						"	 FROM ERP_DONDATHANG_CHIETKHAU A   "+
						"	 WHERE DONDATHANG_FK='"+this.id+"' AND LOAI=1 ";					 
					System.out.println("--INIT ERP_DONDATHANG_CHIETKHAU: " + query);
					spRs = db.get(query);
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
						sPisNppDat +=   "0__";
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

						spTONKHO = spTONKHO.substring(0, spTONKHO.length() - 2);
						this.spTonkho = spTONKHO.split("__");

						spSOLUONG = spSOLUONG.substring(0, spSOLUONG.length() - 2);
						this.spSoluong = spSOLUONG.split("__");

						spGIANHAP = spGIANHAP.substring(0, spGIANHAP.length() - 2);
						this.spGianhap = spGIANHAP.split("__");

						spVAT = spVAT.substring(0, spVAT.length() - 2);
						this.spVAT = spVAT.split("__");

						spSCHEME = spSCHEME.substring(0, spSCHEME.length() - 2);
						this.spSCheme = spSCHEME.split("__");

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


						System.out.println("sPisNppDat = "+ sPisNppDat);
						sPisNppDat = sPisNppDat.substring(0, sPisNppDat.length() - 2);
						System.out.println("sPisNppDat2 = "+ sPisNppDat);
						this.isNppDat =sPisNppDat.split("__");

					}

					String dhCk_DIENGIAI="";
					String dhCk_GIATRI="";
					String dhCk_LOAI="";
					query=					
						"	SELECT DIENGIAI,GIATRI,LOAI,ChietKhau,DOANHSO FROM ERP_DONDATHANG_CHIETKHAU "+ 
						"	 WHERE DONDATHANG_FK='"+this.id+"' AND LOAI=1 ";					 
					spRs = db.get(query);
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
		String query = 
			"select isTT,isnull(ChietKhau,0) ChietKhau,trangthai,ngaydonhang, ngaydenghi, ISNULL(ghichu, '') as ghichu, dvkd_fk, kbh_fk, npp_fk, kho_fk,  "+
			"vat, loaidonhang, CongNo, tiengiam, pokhuyenmai " +
			"from ERP_Dondathang where pk_seq = '" + this.id + "'";
		System.out.println("____INIT NHAP KHO: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.isTT = rs.getString("isTT");
					this.trangthai  = rs.getString("trangthai");
					this.ngayyeucau = rs.getString("ngaydonhang");
					this.ngaydenghi = rs.getString("ngaydenghi");
					this.ghichu = rs.getString("ghichu");
					this.dvkdId = rs.getString("dvkd_fk");
					this.kbhId = rs.getString("kbh_fk");
					this.nppId = rs.getString("npp_fk");
					this.khoNhanId = rs.getString("kho_fk");
					this.loaidonhang = rs.getString("loaidonhang");
					this.chietkhau = rs.getString("chietkhau");

					this.vat = rs.getString("vat");
					this.congNo = formater.format(rs.getDouble("congNo"));
					this.tiengiam = formater.format(rs.getDouble("tiengiam"));
					this.pokhuyenmai = rs.getString("pokhuyenmai")==null?"0":rs.getString("pokhuyenmai");
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("---LOI INIT: " + e.getMessage());
			}
		}

		if(this.loaidonhang.equals("2"))
			this.initSCHEME();
		else if(this.loaidonhang.equals("1"))
			this.initSCHEME_UngHang();
		else if(this.loaidonhang.equals("3"))
			this.initSCHEME_TrungBay();
		else
			this.initSANPHAM();
		this.createRs(); 
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
				e.printStackTrace();
				System.out.println("115.Exception: " + e.getMessage());
			}
		}
	}

	private void initSCHEME_UngHang()
	{
		String query =  "select b.PK_SEQ, b.SCHEME, b.DIENGIAI, a.tongluong " +
		"from ERP_DONDATHANG_KMUNGHANG a inner join CTKHUYENMAI b on a.ctkm_fk = b.PK_SEQ " +
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
				e.printStackTrace();
				System.out.println("115.Exception: " + e.getMessage());
			}
		}
	}

	private void initSCHEME()
	{
		/*String query =  "select b.PK_SEQ, b.SCHEME, b.DIENGIAI, a.tongluong " +
						"from ERP_DONDATHANG_KMTICHLUY a inner join TIEUCHITHUONGTL b on a.ctkm_fk = b.PK_SEQ " +
						"where a.dondathang_fk = '" + this.id + "' ";*/

		String query =  "select b.PK_SEQ, b.SCHEME, b.DIENGIAI, a.tongluong " +
		"from ERP_DONDATHANG_KMTICHLUY a inner join CTKHUYENMAI b on a.ctkm_fk = b.PK_SEQ " +
		"where a.dondathang_fk = '" + this.id + "' ";
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				String schemeID = "";
				while(rs.next())
				{
					schemeID += rs.getString("PK_SEQ") + ",";
				}
				rs.close();

				if(schemeID.trim().length() > 0)
				{
					schemeID = schemeID.substring(0, schemeID.length() - 1);
					this.schemeId = schemeID;
				}
			} 
			catch (Exception e) {}
		}

		createSP();

		/*ResultSet spRs = db.get(query);

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
		}*/
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

	public String[] getSpGianhapCK() {

		return this.spGianhapck;
	}


	public void setSpGianhapCK(String[] spGianhapck) {

		this.spGianhapck = spGianhapck;
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

		System.out.println("---VAT LA: " + this.vat);
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


	public boolean createKMTichLuy() 
	{
		if(this.ngayyeucau.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày đơn hàng";
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
			this.msg = "Vui lòng chọn nhà phân phối";
			return false;
		}

		if( this.khoNhanId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kho hàng";
			return false;
		}

		if( this.schemeId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn SCHEME";
			return false;
		}

		if(spSChemeIds == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách SCHEME trả cho NPP";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spId.length; i++)
			{
				if( spId[i].trim().length() > 0 )
				{
					//CHECK TOTAL KHONG DUOC VUOT QUA TONG LUONG CO THE NHAN

					coSP = true;
				}
			}

			if(!coSP)
			{
				this.msg = "Vui lòng kiểm tra lại danh sách SCHEME trả cho NPP";
				return false;
			}

		}

		try
		{
			db.getConnection().setAutoCommit(false);

			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
			String chietkhau = this.chietkhau.trim().length() <= 0 ? "0" : this.chietkhau.trim();
			String vat = this.vat.trim().length() <= 0 ? "0" : this.vat.trim();

	/*		String query = " insert ERP_Dondathang(ngaydonhang, ngaydenghi, loaidonhang, ghichu, trangthai, dvkd_fk, kbh_fk, npp_fk, kho_fk, chietkhau, vat, ngaytao, nguoitao, ngaysua, nguoisua) " +
			" values('" + this.ngayyeucau + "', '" + this.ngaydenghi + "', '" + this.loaidonhang + "', N'" + this.ghichu + "', '0', '" + dvkdId + "', '" + kbhId + "', '" + nppId + "', " + khonhan_fk + ", '" + chietkhau + "', '" + vat + "', '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "' )";

			System.out.println("1.Insert DDH: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_Dondathang " + query;
				db.getConnection().rollback();
				return false;
			}*/
			
			String query = "INSERT INTO ERP_DONDATHANG(ngaydonhang, ngaydenghi, loaidonhang, ghichu, trangthai, dvkd_fk, kbh_fk, npp_fk, kho_fk, chietkhau, vat, ngaytao, nguoitao, ngaysua, nguoisua) select ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";

			Object[] data = {this.ngayyeucau,this.ngaydenghi,this.loaidonhang,this.ghichu,'0',dvkdId,kbhId,nppId,khonhan_fk,chietkhau,vat,getDateTime(),this.userId,getDateTime(),this.userId};

			dbutils.viewQuery(query,data);
			if (this.db.updateQueryByPreparedStatement(query,data) != 1) {
				this.msg = "Không thể tạo mới ERP_Dondathang!";
				db.getConnection().rollback();
				return false;
			}
			String tempID = db.getPk_seq();

			for(int i = 0; i < spSChemeIds.length; i++)
			{
				if(spSChemeIds[i].trim().length() > 0 && spSoluong[i].trim().length() > 0 )
				{
					double conLAI = Double.parseDouble(spTrongluong[i].replaceAll(",", "")) - Double.parseDouble(spThetich[i].replaceAll(",", ""));
					if(conLAI < Double.parseDouble(spSoluong[i]))
					{
						this.msg = "Số lượng xuất không được vượt quá số lượng còn lại có thể xuất";
						db.getConnection().rollback();
						return false;
					}

		/*			query = "insert ERP_DONDATHANG_KMTICHLUY( Dondathang_fk, CTKM_FK, tongluong ) " +
					"select IDENT_CURRENT('ERP_Dondathang'), '" + spSChemeIds[i] + "', '" + spTrongluong[i].replaceAll(",", "") + "' ";

					System.out.println("1.1.Insert NK - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_DONDATHANG_KMTICHLUY: " + query;
						db.getConnection().rollback();
						return false;
					}*/
					
					String tempTrongLuong = spTrongluong[i].replaceAll(",", "");
					query = "INSERT INTO ERP_DONDATHANG_KMTICHLUY( Dondathang_fk, CTKM_FK, tongluong ) select ?,?,?";

					Object[] data1 = {tempID,spSChemeIds[i],tempTrongLuong};

					dbutils.viewQuery(query,data1);
					if (this.db.updateQueryByPreparedStatement(query,data1) != 1) {
						this.msg = "1. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý";
						db.getConnection().rollback();
						return false;
					}

				/*	query = "insert ERP_Dondathang_SANPHAM( Dondathang_fk, SANPHAM_FK, soluong, dongia, dvdl_fk, ctkm_fk ) " +
					"select IDENT_CURRENT('ERP_Dondathang'), pk_seq, '" + spSoluong[i] + "', '0', dvdl_fk, '" + spSChemeIds[i] + "' " +
					"from SANPHAM where PK_SEQ = '" + this.spId[i]  + "' ";

					System.out.println("1.2.Insert NK - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_Dondathang_SANPHAM: " + query;
						db.getConnection().rollback();
						return false;
					}*/
					
					query = "insert ERP_Dondathang_SANPHAM( Dondathang_fk, SANPHAM_FK, soluong, dongia, dvdl_fk, ctkm_fk ) " +
							"select ? , pk_seq, ? , '0', dvdl_fk, ? " +
							"from SANPHAM where PK_SEQ = ? ";
					
					Object[] data2 = {tempID,spSoluong[i],spSChemeIds[i],this.spId[i]};

					dbutils.viewQuery(query,data2);
					if (this.db.updateQueryByPreparedStatement(query,data2) != 1) {
						this.msg = "1. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý";
						db.getConnection().rollback();
						return false;
					}

					/*if(this.scheme_soluong != null)
					{
						Enumeration<String> keys = this.scheme_soluong.keys();
						while(keys.hasMoreElements())
						{
							String key = keys.nextElement();
							if(key.startsWith(spId[i]))
							{
								String[] _spTRA = key.split("__");
								String _soluongTRA = "0"; 
								if(this.scheme_soluong.get(key) != null)
									_soluongTRA = this.scheme_soluong.get(key);

								query = "insert ERP_Dondathang_SANPHAM( Dondathang_fk, SANPHAM_FK, soluong, dongia, dvdl_fk, ctkm_fk ) " +
										"select IDENT_CURRENT('ERP_Dondathang'), pk_seq, '" + _soluongTRA + "', '0', dvdl_fk, '" + _spTRA[0] + "' " +
										"from SANPHAM where MA = '" + _spTRA[1] + "' ";

								System.out.println("1.2.Insert NK - SP: " + query);
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi ERP_Dondathang_SANPHAM: " + query;
									db.getConnection().rollback();
									return false;
								}
							}
						}
					}*/
				}
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			e.printStackTrace();
			this.msg = "Exception: " + e.getMessage();
			return false;
		}

		return true;
	}


	public boolean updateKMTichLuy() 
	{

		if(this.ngayyeucau.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày đơn hàng";
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
			this.msg = "Vui lòng chọn nhà phân phối";
			return false;
		}

		if( this.khoNhanId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kho hàng";
			return false;
		}

		if(spSChemeIds == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách SCHEME trả cho NPP";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spId.length; i++)
			{
				if( spId[i].trim().length() > 0 )
				{
					//CHECK TOTAL KHONG DUOC VUOT QUA TONG LUONG CO THE NHAN

					coSP = true;
				}
			}

			if(!coSP)
			{
				this.msg = "Vui lòng kiểm tra lại danh sách SCHEME trả cho NPP";
				return false;
			}

		}

		try
		{
			db.getConnection().setAutoCommit(false);

			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
			String chietkhau = this.chietkhau.trim().length() <= 0 ? "0" : this.chietkhau.trim();
			String vat = this.vat.trim().length() <= 0 ? "0" : this.vat.trim();

	/*		String query =	" Update ERP_Dondathang set ngaydonhang = '" + this.ngayyeucau + "', ngaydenghi = '" + this.ngaydenghi + "', ghichu = N'" + this.ghichu + "', " +
			" 	dvkd_fk = '" + this.dvkdId + "', kbh_fk = '" + this.kbhId + "', npp_fk = '" + this.nppId + "', kho_fk = " + khonhan_fk + ", ngaysua = '" + getDateTime() + "', nguoisua = '" + this.userId + "', chietkhau = '" + chietkhau + "', vat = '" + vat + "' " + 
			" where pk_seq = '" + this.id + "' ";

			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_Dondathang " + query;
				db.getConnection().rollback();
				return false;
			}*/
			
			String query =	" Update ERP_Dondathang set ngaydonhang = ?, ngaydenghi = ?, ghichu = ?, " +
					" 	dvkd_fk = ?, kbh_fk = ?, npp_fk = ?, kho_fk = ?, ngaysua = ?, nguoisua = ?, chietkhau = ?, vat = ? " + 
					" where pk_seq = ? ";
			
			Object[] data = {this.ngayyeucau,this.ngaydenghi,this.ghichu,this.dvkdId,this.kbhId,this.nppId,khonhan_fk,getDateTime(),this.userId,chietkhau,vat,this.id};
			dbutils.viewQuery(query,data);
			if (this.db.updateQueryByPreparedStatement(query,data) != 1) {
				this.msg = "Không thể cập nhật: Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý";
				this.db.getConnection().rollback();
				return false;
			}

			query = "delete ERP_Dondathang_SANPHAM where Dondathang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_Dondathang_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}

			query = "delete ERP_DONDATHANG_KMTICHLUY where Dondathang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_DONDATHANG_KMTICHLUY " + query;
				db.getConnection().rollback();
				return false;
			}

			for(int i = 0; i < spSChemeIds.length; i++)
			{
				if(spSChemeIds[i].trim().length() > 0 && spSoluong[i].trim().length() > 0 )
				{
					double conLAI = Double.parseDouble(spTrongluong[i].replaceAll(",", "")) - Double.parseDouble(spThetich[i].replaceAll(",", ""));
					if(conLAI < Double.parseDouble(spSoluong[i]))
					{
						this.msg = "Số lượng xuất không được vượt quá số lượng còn lại có thể xuất";
						db.getConnection().rollback();
						return false;
					}

		/*			query = "insert ERP_DONDATHANG_KMTICHLUY( Dondathang_fk, CTKM_FK, tongluong ) " +
					"select '" + this.id + "', '" + spSChemeIds[i] + "', '" + spTrongluong[i].replaceAll(",", "") + "' ";

					System.out.println("1.1.Insert NK - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_DONDATHANG_KMTICHLUY: " + query;
						db.getConnection().rollback();
						return false;
					}*/
					
					query = "insert ERP_DONDATHANG_KMTICHLUY( Dondathang_fk, CTKM_FK, tongluong ) " +
							"select ?, ?, ? ";
					
					Object[] data1 = {this.id,spSChemeIds[i],spTrongluong[i].replaceAll(",", "")};
					dbutils.viewQuery(query,data1);
					if (this.db.updateQueryByPreparedStatement(query,data1) != 1) {
						this.msg = "1. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý";
						this.db.getConnection().rollback();
						return false;
					}

			/*		query = "insert ERP_Dondathang_SANPHAM( Dondathang_fk, SANPHAM_FK, soluong, dongia, dvdl_fk, ctkm_fk ) " +
					"select '" + this.id + "', pk_seq, '" + spSoluong[i] + "', '0', dvdl_fk, '" + spSChemeIds[i] + "' " +
					"from SANPHAM where PK_SEQ = '" + this.spId[i]  + "' ";

					System.out.println("1.2.Insert NK - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_Dondathang_SANPHAM: " + query;
						db.getConnection().rollback();
						return false;
					}*/
					
					query = "insert ERP_Dondathang_SANPHAM( Dondathang_fk, SANPHAM_FK, soluong, dongia, dvdl_fk, ctkm_fk ) " +
							"select ?, pk_seq, ?, '0', dvdl_fk, ? " +
							"from SANPHAM where PK_SEQ = ? ";
					
					Object[] data2 = {this.id,spSoluong[i],spSChemeIds[i],this.spId[i]};
					dbutils.viewQuery(query,data2);
					if (this.db.updateQueryByPreparedStatement(query,data2) != 1) {
						this.msg = "1. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý";
						this.db.getConnection().rollback();
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
			e.printStackTrace();
			this.msg = "Exception: " + e.getMessage();
			e.printStackTrace();
			return false;
		}

		return true;
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


	public boolean createKMUngHang() 
	{
		if(this.ngayyeucau.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày đơn hàng";
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
			this.msg = "Vui lòng chọn nhà phân phối";
			return false;
		}

		if( this.khoNhanId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kho hàng";
			return false;
		}

		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách SCHEME trả cho NPP";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spMa.length; i++)
			{
				if( spMa[i].trim().length() > 0 )
				{
					//CHECK TOTAL KHONG DUOC VUOT QUA TONG LUONG CO THE NHAN

					coSP = true;
				}
			}

			if(!coSP)
			{
				this.msg = "Vui lòng kiểm tra lại danh sách SCHEME trả cho NPP";
				return false;
			}

		}

		try
		{
			db.getConnection().setAutoCommit(false);

			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
			String chietkhau = this.chietkhau.trim().length() <= 0 ? "0" : this.chietkhau.trim();
			String vat = this.vat.trim().length() <= 0 ? "0" : this.vat.trim();

	/*		String query = " insert ERP_Dondathang(ngaydonhang, ngaydenghi, loaidonhang, ghichu, trangthai, dvkd_fk, kbh_fk, npp_fk, kho_fk, chietkhau, vat, ngaytao, nguoitao, ngaysua, nguoisua) " +
			" values('" + this.ngayyeucau + "', '" + this.ngaydenghi + "', '" + this.loaidonhang + "', N'" + this.ghichu + "', '0', '" + dvkdId + "', '" + kbhId + "', '" + nppId + "', " + khonhan_fk + ", '" + chietkhau + "', '" + vat + "', '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "' )";

			System.out.println("1.Insert DDH: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_Dondathang " + query;
				db.getConnection().rollback();
				return false;
			}*/
			
			String query = "INSERT ERP_DONDATHANG(ngaydonhang, ngaydenghi, loaidonhang, ghichu, trangthai, dvkd_fk, kbh_fk, npp_fk, kho_fk, chietkhau, vat, ngaytao, nguoitao, ngaysua, nguoisua) select ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";

			Object[] data = {this.ngayyeucau,this.ngaydenghi,this.loaidonhang,this.ghichu,"0",dvkdId,kbhId,nppId,khonhan_fk,chietkhau,vat,getDateTime(),this.userId,getDateTime(),this.userId};

			dbutils.viewQuery(query,data);
			if (this.db.updateQueryByPreparedStatement(query,data) != 1) {
				this.msg = "1. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý";
				this.db.getConnection().rollback();
				return false;
			}
			String tempID = db.getPk_seq();

			for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0)
				{
			/*		query = "insert ERP_DONDATHANG_KMUNGHANG( Dondathang_fk, CTKM_FK, tongluong ) " +
					"select IDENT_CURRENT('ERP_Dondathang'), pk_seq, '" + spSoluong[i].replaceAll(",", "") + "' " + 
					"from CTKHUYENMAI where SCHEME = '" + spMa[i].trim() + "' ";

					System.out.println("1.1.Insert NK - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_DONDATHANG_KMUNGHANG: " + query;
						db.getConnection().rollback();
						return false;
					}*/
					
					query = "insert ERP_DONDATHANG_KMUNGHANG( Dondathang_fk, CTKM_FK, tongluong ) " +
							"select ? , pk_seq, ? " + 
							"from CTKHUYENMAI where SCHEME = ? ";
					
					Object[] data1 = {tempID,spSoluong[i].replaceAll(",", ""),spMa[i].trim()};
					dbutils.viewQuery(query,data1);
					if (this.db.updateQueryByPreparedStatement(query,data) != 1) {
						this.msg = "1. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý";
						db.getConnection().rollback();
						return false;
					}

					if(this.scheme_soluong != null)
					{
						Enumeration<String> keys = this.scheme_soluong.keys();
						while(keys.hasMoreElements())
						{
							String key = keys.nextElement();
							if(key.startsWith(spId[i]+"__"))
							{
								String[] _spTRA = key.split("__");
								String _soluongTRA = "0"; 
								if(this.scheme_soluong.get(key) != null)
									_soluongTRA = this.scheme_soluong.get(key);

						/*		query = "insert ERP_Dondathang_SANPHAM( Dondathang_fk, SANPHAM_FK, soluong, dongia, dvdl_fk, ctkm_fk ) " +
								"select IDENT_CURRENT('ERP_Dondathang'), pk_seq, '" + _soluongTRA + "', '0', dvdl_fk, '" + _spTRA[0] + "' " +
								"from SANPHAM where MA = '" + _spTRA[1] + "' ";

								System.out.println("1.2.Insert NK - SP: " + query);
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi ERP_Dondathang_SANPHAM: " + query;
									db.getConnection().rollback();
									return false;
								}*/
								
								query = "insert ERP_Dondathang_SANPHAM( Dondathang_fk, SANPHAM_FK, soluong, dongia, dvdl_fk, ctkm_fk ) " +
										"select ? , pk_seq, ?, '0', dvdl_fk, ? " +
										"from SANPHAM where MA = ? ";

								Object[] data2 = {tempID,_soluongTRA,_spTRA[0],_spTRA[1]};

								dbutils.viewQuery(query,data2);
								if (this.db.updateQueryByPreparedStatement(query,data2) != 1) {
									this.msg = "1. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý";
									this.db.getConnection().rollback();
									return false;
								}
							}
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
			e.printStackTrace();
			this.msg = "Exception: " + e.getMessage();
			return false;
		}

		return true;
	}


	public boolean updateKMUngHang() 
	{
		if(this.ngayyeucau.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày đơn hàng";
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
			this.msg = "Vui lòng chọn nhà phân phối";
			return false;
		}

		if( this.khoNhanId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kho hàng";
			return false;
		}

		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách SCHEME trả cho NPP";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spMa.length; i++)
			{
				if( spMa[i].trim().length() > 0 )
				{
					//CHECK TOTAL KHONG DUOC VUOT QUA TONG LUONG CO THE NHAN

					coSP = true;
				}
			}

			if(!coSP)
			{
				this.msg = "Vui lòng kiểm tra lại danh sách SCHEME trả cho NPP";
				return false;
			}

		}

		try
		{
			db.getConnection().setAutoCommit(false);

			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
			String chietkhau = this.chietkhau.trim().length() <= 0 ? "0" : this.chietkhau.trim();
			String vat = this.vat.trim().length() <= 0 ? "0" : this.vat.trim();

			String query =	" Update ERP_Dondathang set ngaydonhang = '" + this.ngayyeucau + "', ngaydenghi = '" + this.ngaydenghi + "', ghichu = N'" + this.ghichu + "', " +
			" 	dvkd_fk = '" + this.dvkdId + "', kbh_fk = '" + this.kbhId + "', npp_fk = '" + this.nppId + "', kho_fk = " + khonhan_fk + ", ngaysua = '" + getDateTime() + "', nguoisua = '" + this.userId + "', chietkhau = '" + chietkhau + "', vat = '" + vat + "' " + 
			" where pk_seq = '" + this.id + "' ";

			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_Dondathang " + query;
				db.getConnection().rollback();
				return false;
			}

			query = "delete ERP_Dondathang_SANPHAM where Dondathang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_Dondathang_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}

			query = "delete ERP_DONDATHANG_KMUNGHANG where Dondathang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_DONDATHANG_KMUNGHANG " + query;
				db.getConnection().rollback();
				return false;
			}

			for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0)
				{
					query = "insert ERP_DONDATHANG_KMUNGHANG( Dondathang_fk, CTKM_FK, tongluong ) " +
					"select '" + this.id + "', pk_seq, '" + spSoluong[i].replaceAll(",", "") + "' " + 
					"from CTKHUYENMAI where SCHEME = '" + spMa[i].trim() + "' ";

					System.out.println("1.1.Insert NK - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_DONDATHANG_KMTICHLUY: " + query;
						db.getConnection().rollback();
						return false;
					}

					if(this.scheme_soluong != null)
					{
						Enumeration<String> keys = this.scheme_soluong.keys();
						while(keys.hasMoreElements())
						{
							String key = keys.nextElement();
							System.out.println("----KEY LA: " + key);
							if(key.startsWith(spId[i]+"__"))
							{
								String[] _spTRA = key.split("__");
								String _soluongTRA = "0"; 
								if(this.scheme_soluong.get(key) != null)
									_soluongTRA = this.scheme_soluong.get(key);

								query = "insert ERP_Dondathang_SANPHAM( Dondathang_fk, SANPHAM_FK, soluong, dongia, dvdl_fk, ctkm_fk ) " +
								"select '" + this.id + "', pk_seq, '" + _soluongTRA + "', '0', dvdl_fk, '" + _spTRA[0] + "' " +
								"from SANPHAM where MA = '" + _spTRA[1] + "' ";

								System.out.println("1.2.Insert NK - SP: " + query);
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi ERP_Dondathang_SANPHAM: " + query;
									db.getConnection().rollback();
									return false;
								}
							}
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
			e.printStackTrace();
			this.msg = "Exception: " + e.getMessage();
			return false;
		}

		return true;

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

	public boolean createTrungBay() 
	{
		if(this.ngayyeucau.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày đơn hàng";
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
			this.msg = "Vui lòng chọn nhà phân phối";
			return false;
		}

		if( this.khoNhanId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kho hàng";
			return false;
		}

		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách SCHEME trả cho NPP";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spMa.length; i++)
			{
				if( spMa[i].trim().length() > 0 )
				{
					//CHECK TOTAL KHONG DUOC VUOT QUA TONG LUONG CO THE NHAN

					coSP = true;
				}
			}

			if(!coSP)
			{
				this.msg = "Vui lòng kiểm tra lại danh sách SCHEME trả cho NPP";
				return false;
			}

		}

		try
		{
			db.getConnection().setAutoCommit(false);

			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
			String chietkhau = this.chietkhau.trim().length() <= 0 ? "0" : this.chietkhau.trim();
			String vat = this.vat.trim().length() <= 0 ? "0" : this.vat.trim();

	/*		String query = " insert ERP_Dondathang(ngaydonhang, ngaydenghi, loaidonhang, ghichu, trangthai, dvkd_fk, kbh_fk, npp_fk, kho_fk, chietkhau, vat, ngaytao, nguoitao, ngaysua, nguoisua) " +
			" values('" + this.ngayyeucau + "', '" + this.ngaydenghi + "', '" + this.loaidonhang + "', N'" + this.ghichu + "', '0', '" + dvkdId + "', '" + kbhId + "', '" + nppId + "', " + khonhan_fk + ", '" + chietkhau + "', '" + vat + "', '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "' )";

			System.out.println("1.Insert DDH: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_Dondathang " + query;
				db.getConnection().rollback();
				return false;
			}*/
			
			String query = "INSERT ERP_DONDATHANG(ngaydonhang, ngaydenghi, loaidonhang, ghichu, trangthai, dvkd_fk, kbh_fk, npp_fk, kho_fk, chietkhau, vat, ngaytao, nguoitao, ngaysua, nguoisua) select ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";

			Object[] data = {this.ngayyeucau,this.ngaydenghi,this.loaidonhang,this.ghichu,"0",dvkdId,kbhId,nppId,khonhan_fk,chietkhau,vat,getDateTime(),this.userId,getDateTime(),this.userId};

			dbutils.viewQuery(query,data);
			if (this.db.updateQueryByPreparedStatement(query,data) != 1) {
				this.msg = "1. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý";
				this.db.getConnection().rollback();
				return false;
			}
			String tempID = db.getPk_seq();

			for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0)
				{
			/*		query = "insert ERP_DONDATHANG_TRUNGBAY( Dondathang_fk, CTTB_FK, tongluong ) " +
					"select IDENT_CURRENT('ERP_Dondathang'), pk_seq, '" + spSoluong[i].replaceAll(",", "") + "' " + 
					"from CTTRUNGBAY where SCHEME = '" + spMa[i].trim() + "' ";

					System.out.println("1.1.Insert NK - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_DONDATHANG_TRUNGBAY: " + query;
						db.getConnection().rollback();
						return false;
					}*/
					
					query = "insert ERP_DONDATHANG_TRUNGBAY( Dondathang_fk, CTTB_FK, tongluong ) " +
							"select ?, pk_seq, ? " + 
							"from CTTRUNGBAY where SCHEME = ? ";
					
					Object[] data1 = {tempID,spSoluong[i].replaceAll(",", ""),spMa[i].trim()};
					dbutils.viewQuery(query,data1);
					if (this.db.updateQueryByPreparedStatement(query,data1) != 1) {
						this.msg = "1. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý";
						this.db.getConnection().rollback();
						return false;
					}

					if(this.scheme_soluong != null)
					{
						Enumeration<String> keys = this.scheme_soluong.keys();
						while(keys.hasMoreElements())
						{
							String key = keys.nextElement();
							if(key.startsWith(spId[i]+"__"))
							{
								String[] _spTRA = key.split("__");
								String _soluongTRA = "0"; 
								if(this.scheme_soluong.get(key) != null)
									_soluongTRA = this.scheme_soluong.get(key);

						/*		query = "insert ERP_Dondathang_SANPHAM( Dondathang_fk, SANPHAM_FK, soluong, dongia, dvdl_fk, cttb_fk ) " +
								"select IDENT_CURRENT('ERP_Dondathang'), pk_seq, '" + _soluongTRA + "', '0', dvdl_fk, '" + _spTRA[0] + "' " +
								"from SANPHAM where MA = '" + _spTRA[1] + "' ";

								System.out.println("1.2.Insert NK - SP: " + query);
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi ERP_Dondathang_SANPHAM: " + query;
									db.getConnection().rollback();
									return false;
								}*/
								
								query = "insert ERP_Dondathang_SANPHAM( Dondathang_fk, SANPHAM_FK, soluong, dongia, dvdl_fk, cttb_fk ) " +
										"select ?, pk_seq, ?, '0', dvdl_fk, ? " +
										"from SANPHAM where MA = ? ";
								
								Object[] data2 = {tempID,_soluongTRA,_spTRA[0],_spTRA[1]};
								dbutils.viewQuery(query,data2);
								if (this.db.updateQueryByPreparedStatement(query,data2) != 1) {
									this.msg = "1. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý";
									this.db.getConnection().rollback();
									return false;
								}
							}
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
			e.printStackTrace();
			this.msg = "Exception: " + e.getMessage();
			return false;
		}

		return true;
	}


	public boolean updateTrungBay() 
	{

		if(this.ngayyeucau.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày đơn hàng";
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
			this.msg = "Vui lòng chọn nhà phân phối";
			return false;
		}

		if( this.khoNhanId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kho hàng";
			return false;
		}

		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách SCHEME trả cho NPP";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spMa.length; i++)
			{
				if( spMa[i].trim().length() > 0 )
				{
					//CHECK TOTAL KHONG DUOC VUOT QUA TONG LUONG CO THE NHAN

					coSP = true;
				}
			}

			if(!coSP)
			{
				this.msg = "Vui lòng kiểm tra lại danh sách SCHEME trả cho NPP";
				return false;
			}

		}

		try
		{
			db.getConnection().setAutoCommit(false);

			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
			String chietkhau = this.chietkhau.trim().length() <= 0 ? "0" : this.chietkhau.trim();
			String vat = this.vat.trim().length() <= 0 ? "0" : this.vat.trim();

	/*		String query =	" Update ERP_Dondathang set ngaydonhang = '" + this.ngayyeucau + "', ngaydenghi = '" + this.ngaydenghi + "', ghichu = N'" + this.ghichu + "', " +
			" 	dvkd_fk = '" + this.dvkdId + "', kbh_fk = '" + this.kbhId + "', npp_fk = '" + this.nppId + "', kho_fk = " + khonhan_fk + ", ngaysua = '" + getDateTime() + "', nguoisua = '" + this.userId + "', chietkhau = '" + chietkhau + "', vat = '" + vat + "' " + 
			" where pk_seq = '" + this.id + "' ";

			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_Dondathang " + query;
				db.getConnection().rollback();
				return false;
			}*/
			
			String query =	" Update ERP_Dondathang set ngaydonhang = ?, ngaydenghi = ?, ghichu = ?, " +
					" 	dvkd_fk = ?, kbh_fk = ?, npp_fk = ?, kho_fk = ?, ngaysua = ?, nguoisua = ?, chietkhau = ?, vat = ? " + 
					" where pk_seq = ? ";
			
			Object[] data = {this.ngayyeucau,this.ngaydenghi,this.ghichu,this.dvkdId,this.kbhId,this.nppId,khonhan_fk,getDateTime(),
					this.userId,chietkhau,vat,this.id};
			
			dbutils.viewQuery(query,data);
			if (this.db.updateQueryByPreparedStatement(query,data) != 1) {
				this.msg = "1. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý";
				this.db.getConnection().rollback();
				return false;
			}

			query = "delete ERP_Dondathang_SANPHAM where Dondathang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_Dondathang_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}

			query = "delete ERP_DONDATHANG_TRUNGBAY where Dondathang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_DONDATHANG_TRUNGBAY " + query;
				db.getConnection().rollback();
				return false;
			}

			for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0)
				{
			/*		query = "insert ERP_DONDATHANG_TRUNGBAY( Dondathang_fk, CTTB_FK, tongluong ) " +
					"select '" + this.id + "', pk_seq, '" + spSoluong[i].replaceAll(",", "") + "' " + 
					"from CTTRUNGBAY where SCHEME = '" + spMa[i].trim() + "' ";

					System.out.println("1.1.Insert NK - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_DONDATHANG_TRUNGBAY: " + query;
						db.getConnection().rollback();
						return false;
					}*/
					
					query = "insert ERP_DONDATHANG_TRUNGBAY( Dondathang_fk, CTTB_FK, tongluong ) " +
							"select ?, pk_seq, ? " + 
							"from CTTRUNGBAY where SCHEME = ? ";
					
					Object[] data1 = {this.id,spSoluong[i].replaceAll(",", ""),spMa[i].trim()};
					
					dbutils.viewQuery(query,data1);
					if (this.db.updateQueryByPreparedStatement(query,data1) != 1) {
						this.msg = "1. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý";
						this.db.getConnection().rollback();
						return false;
					}

					if(this.scheme_soluong != null)
					{
						Enumeration<String> keys = this.scheme_soluong.keys();
						while(keys.hasMoreElements())
						{
							String key = keys.nextElement();
							System.out.println("----KEY LA: " + key);
							if(key.startsWith(spId[i]+"__"))
							{
								String[] _spTRA = key.split("__");
								String _soluongTRA = "0"; 
								if(this.scheme_soluong.get(key) != null)
									_soluongTRA = this.scheme_soluong.get(key);

					/*			query = "insert ERP_Dondathang_SANPHAM( Dondathang_fk, SANPHAM_FK, soluong, dongia, dvdl_fk, cttb_fk ) " +
								"select '" + this.id + "', pk_seq, '" + _soluongTRA + "', '0', dvdl_fk, '" + _spTRA[0] + "' " +
								"from SANPHAM where MA = '" + _spTRA[1] + "' ";

								System.out.println("1.2.Insert NK - SP: " + query);
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi ERP_Dondathang_SANPHAM: " + query;
									db.getConnection().rollback();
									return false;
								}*/
								
								query = "insert ERP_Dondathang_SANPHAM( Dondathang_fk, SANPHAM_FK, soluong, dongia, dvdl_fk, cttb_fk ) " +
										"select ?, pk_seq, ?, '0', dvdl_fk, ? " +
										"from SANPHAM where MA = ? ";
								
								Object[] data2 = {this.id,_soluongTRA,_spTRA[0], _spTRA[1]};
								dbutils.viewQuery(query,data2);
								if (this.db.updateQueryByPreparedStatement(query,data2) != 1) {
									this.msg = "1. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý";
									this.db.getConnection().rollback();
									return false;
								}
							}
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
			e.printStackTrace();
			this.msg = "Exception: " + e.getMessage();
			return false;
		}

		return true;
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

	String[] spQuyDoi;


	public String[] getSpQuyDoi()
	{
		return spQuyDoi;
	}


	public void setSpQuyDoi(String[] spQuyDoi)
	{
		this.spQuyDoi =spQuyDoi;
	}


	public String[] getSpSchemeIds() {

		return this.spSChemeIds;
	}


	public void setSpSchemeIds(String[] spSchemeIds) {

		this.spSChemeIds = spSchemeIds;
	}


	public String[] getSpSchemeDiengiai() {

		return this.spSChemeDiengiai;
	}


	public void setSpSchemeDiengiai(String[] spScheme) {

		this.spSChemeDiengiai = spScheme;
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

	@Override
	public boolean ApChietKhau()
	{
		try
		{
			db.getConnection().setAutoCommit(false);
			String query="DELETE FROM ERP_DONDATHANG_CHIETKHAU WHERE ChietKhauDatHang_fk IS NOT NULL AND DONDATHANG_FK='"+this.id+"'";
			if(!db.update(query))
			{
				this.msg="Lỗi phát sinh khi áp CK đặt hàng"+query;
				db.getConnection().rollback();
				return false;
			}

			query=
				"	select COUNT(*) as SoDong,b.ChietKhauDatHang_FK,  "+
				"	( "+
				"		select COUNT(*) from ChietKhauDatHang_SP aa "+
				"		where aa.ChietKhauDatHang_FK=b.ChietKhauDatHang_FK "+
				"	)as ckSanPham "+
				"	from ChietKhauDatHang a inner join ChietKhauDatHang_NPP b on b.ChietKhauDatHang_FK=a.pk_Seq "+
				"	where TuNgay <= '"+this.ngaydenghi+"' AND DenNgay>='"+ngaydenghi+"' and b.NPP_FK='"+this.nppId+"' and a.TrangThai=1 "+
				"	group by b.ChietKhauDatHang_FK ";
			System.out.println("[ChietKhauDatHang]"+query);
			ResultSet rs =db.get(query);
			try
			{
				while(rs.next())
				{
					String ckId=rs.getString("ChietKhauDatHang_FK");
					int ckSanPham=rs.getInt("ckSanPham");

					query=
						" select ds.*,ck.TuMuc,ck.ToiMuc,ck.chietkhau as ptChietKhau    "+
						" from "+
						" ( "+
						" 	select a.NPP_FK,SUM(b.SoLuong*b.dongia)  as DoanhSo "+
						" 	from ERP_DONDATHANG a inner join ERP_DONDATHANG_SANPHAM b on b.dondathang_fk=a.PK_SEQ "+
						" 	where a.NPP_FK='"+nppId+"' and a.pk_Seq='"+this.id+"' ";
					if(ckSanPham>0)
					{
						query+=" and b.sanpham_fk in ( select sp_fk FROM ChietKhauDatHang_SP where ChietKhauDatHang_FK='"+ckId+"')";
					}
					query+=
						" 	group by a.NPP_FK "+
						" ) as ds inner join  "+
						" ( "+
						" 	select c.ChietKhauDatHang_fk,TuMuc,CHIETKHAU,ToiMuc,b.NPP_FK "+
						" 	from ChietKhauDatHang a inner join ChietKhauDatHang_NPP b on b.ChietKhauDatHang_FK=a.pk_Seq "+
						" 		inner join ChietKhauDatHang_TieuChi c on c.ChietKhauDatHang_fk=a.pk_Seq "+
						" 	where TuNgay <= '"+this.ngaydenghi+"' AND DenNgay>='"+this.ngaydenghi+"'  "+
						" 	and b.NPP_FK='"+this.nppId+"' and c.ChietKhauDatHang_fk='"+ckId+"' "+
						" )as ck  on ck.NPP_FK=ds.NPP_FK and ds.DoanhSo>=ck.TuMuc and ds.DoanhSo<=ck.ToiMuc ";
					System.out.println("[LayCK]"+query);
					ResultSet rsCK=db.get(query);
					while(rsCK.next())
					{
						double ptChietKhau = rsCK.getDouble("ptChietKhau");
						double DoanhSo =rsCK.getDouble("DoanhSo");
						double TienCK=DoanhSo*ptChietKhau/100;
						query=
							" insert into ERP_DONDATHANG_CHIETKHAU(DONDATHANG_FK,DIENGIAI,GIATRI,LOAI,ChietKhau,ChietKhauDatHang_fk,DOANHSO,SCHEME) " +
							"  select '"+this.id+"',N'Chiết khấu đặt hàng' ,'"+ptChietKhau+"',1,'"+TienCK+"','"+ckId+"','"+DoanhSo+"',(select SCHEME FROM CHIETKHAUDATHANG WHERE PK_SEQ='"+ckId+"') ";
						System.out.println("[ERP_DONDATHANG_CHIETKHAU]"+query);
						if(!db.update(query))
						{
							this.msg="Lỗi phát sinh khi áp CK đặt hàng"+query;
							db.getConnection().rollback();
							return false;
						}
					}
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (SQLException e1)
		{
			e1.printStackTrace();
		}
		return true;
	}

	String congNo;

	public String getCongNo()
	{
		return congNo;
	}

	public void setCongNo(String congNo)
	{
		this.congNo = congNo;
	}

	String[] Dhck_chietkhau;

	public String[] getDhck_chietkhau()
	{
		return Dhck_chietkhau;
	}

	public void setDhck_chietkhau(String[] dhck_chietkhau)
	{
		Dhck_chietkhau = dhck_chietkhau;
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

	String[] spSoluongtt,spGhichu;

	public String[] getSoluongtt()
	{
		return spSoluongtt;
	}

	public void setSoluongtt(String[] spSoluongtt)
	{
		this.spSoluongtt = spSoluongtt;
	}

	public String[] getSpGhichu()
	{
		return spGhichu;
	}

	public void setSpGhichu(String[] spGhichu)
	{
		this.spGhichu = spGhichu;
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

	public String[] getSpTrakmId() {
		return spTrakmId;
	}

	public void setSpTrakmId(String[] spTrakmId) {
		this.spTrakmId = spTrakmId;
	}

	@Override
	public String[] getSpTonkho() {
		return this.spTonkho;
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

	@Override
	public String getPOKhuyenMai() {
		return this.pokhuyenmai;
	}

	@Override
	public void setPOKhuyenMai(String pokhuyenmai) {
		this.pokhuyenmai = pokhuyenmai;
	}

	@Override
	public ResultSet getSchemeSpecialRs() {
		return this.schemespecialRs;
	}

	@Override
	public void setSchemeSpecialRs(ResultSet schemespecialRs) {
		this.schemespecialRs = schemespecialRs;
	}
	public void setIsNppDat(String[] isNppDat) {
		this.isNppDat = isNppDat;
	}
	public String[] getIsNppDat() {
		return isNppDat;
	}
	public String getIsTT() {
		return isTT;
	}
	public void setIsTT(String isTT) {
		this.isTT = isTT;
	}
}
