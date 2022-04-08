package geso.dms.distributor.beans.nhaphang.imp;

import geso.dms.center.db.sql.db_Sync;
import geso.dms.center.util.OracleConnUtils;
import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.nhaphang.INhaphang;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.FixData;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Nhaphang implements INhaphang, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;
	
	String khoNhanId;
	ResultSet khoNhanRs;
	String loaidonhang;
	ResultSet nppRs;
	String sitecode;
	String thuevat;

	public String getThuevat() {
		return thuevat;
	}

	public void setThuevat(String thuevat) {
		this.thuevat = thuevat;
	}

	String loai;
	String ghichuTT;
	public String getGhichuTT() {
		return ghichuTT;
	}

	public void setGhichuTT(String ghichuTT) {
		this.ghichuTT = ghichuTT;
	}

	String dvkdId;
	ResultSet dvkdRs;

	String kbhId;
	ResultSet kbhRs;
	
	String userId;
	String nppId;
	String nppTen;
	String id;

	String ngayyeucau;
	String ngaynhan;
	String sochungtu;
	String ghichu;
	String ghichutaomoi;


	String msg;
	String trangthai;

	String ddhId;
	ResultSet ddhRs;
	String kbhTen = "";

	String khonhanId;
	ResultSet khonhanRs;
	ResultSet chungtuRs;

	String[] spId;
	String[] spMa;
	String[] spTen;
	String[] spDonvi;
	String[] spSolo;
	String[] spTonkho;
	String[] spXuat;
	String[] spSoluong;
	String[] spDongia;
	String[] spLoai;
	String[] spSCheme;
	String[] spvat;
	String[] spchietkhau;
	String[] bvat;
	String[] vat;
	String[] avat;
	
	String[] spSoluongNppDat;
	String[] spGianhap;
	String[] spChietkhau;
	
	dbutils db;

	public Nhaphang()
	{
		this.db = new dbutils();
		this.userId = "";
		this.nppId = "";
		this.nppTen = "";
		this.id = "";
		this.ghichuTT = "";
		this.msg ="";
		this.ghichutaomoi ="";
		this.thuevat ="8";
		this.loaidonhang = "0";
		this.ngayyeucau = "";
		this.ngaynhan = "";
		this.loai = "0";
		this.createRs();
	}

	public String getGhichutaomoi() {
		return ghichutaomoi;
	}

	public void setGhichutaomoi(String ghichutaomoi) {
		this.ghichutaomoi = ghichutaomoi;
	}
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId = util.getIdNhapp(this.userId);
		this.nppTen = util.getTenNhaPP();
	}

	public String getNppId()
	{
		return this.nppId;
	}

	public void setNppId(String id)
	{
		this.nppId = id;
	}

	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}


	public String getNgaynhap() {

		return this.ngaynhan;
	}


	public void setNgaynhap(String ngaynhap) {

		this.ngaynhan = ngaynhap;
	}


	public String getDondathangId() {

		return this.ddhId;
	}


	public void setDondathangId(String kbhId) {

		this.ddhId = kbhId;
	}

	public ResultSet getDondathangRs() {

		return this.ddhRs;
	}

	public void setDondathangRs(ResultSet ddhRs) {

		this.ddhRs = ddhRs;
	}

	public String getNgayyeucau() 
	{
		return this.ngayyeucau;
	}

	public void setNgayyeucau(String ngayyeucau) 
	{
		this.ngayyeucau = ngayyeucau;
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

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public String getMessage() 
	{
		return this.msg;
	}

	public void setMessage(String msg) 
	{
		this.msg = msg;
	}

	public void init()
	{
		String query = 
						" select  isnull(nh.vat, 10) thuevat,isnull(nh.ghichuimport, 0) ghichuTT,isnull(nh.loai, 0) loai, nh.ngaychungtu as ngayyeucau, isnull(nh.ghichu,'') as ghichu" +
						"	, nh.npp_fk,   nh.ngaynhan, nh.trangthai, isnull(nh.kho_fk, 100000) as kho_fk ,nh.CHUNGTU " +
						"	, kbh.ten KBH " +
						" from NhapHang nh " +
						" inner join kenhbanhang kbh on kbh.pk_seq = nh.kbh_fk " +
						" where nh.pk_seq = ?";
		System.out.println("____INIT NHAP KHO: " + query);
		ResultSet rs = db.getByPreparedStatement(query, new Object[]{this.id});
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					
					/*if(rs.getString("trangthai").equals("0")){
						this.msg = "Vui lòng chọn ngày nhận hàng khớp với ngày nhận hàng thực tế. Cảm ơn!!!";
						}*/
					this.loai = rs.getString("loai");
					this.thuevat = rs.getString("thuevat");
					this.ngayyeucau = rs.getString("ngayyeucau");
					this.ghichu = rs.getString("ghichu");
					this.ghichuTT = rs.getString("ghichuTT");

					/*if(rs.getString("trangthai").equals("1")){
					this.ngaynhan = rs.getString("ngaynhan");
					}*/
					this.ngaynhan = rs.getString("ngaynhan");
					
					if(rs.getString("trangthai").equals("1") || rs.getString("loai").equals("1"))
					{
						this.sohoadon = rs.getString("CHUNGTU");
					}
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("---LOI INIT: " + e.getMessage());
			}
		}

		this.createRs();
	}
	
	/*public void init()
	{
		String query = 
				"select ngaychungtu as ngayyeucau, isnull(ghichu,'') as ghichu, npp_fk,   ngaynhan, trangthai, isnull(kho_fk, 100000) as kho_fk ,CHUNGTU " +
						"from NhapHang where pk_seq = '" + this.id + "'";
		System.out.println("____INIT NHAP KHO: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					
					if(rs.getString("trangthai").equals("0")){
						this.msg = "Vui lòng chọn ngày nhận hàng khớp với ngày nhận hàng thực tế. Cảm ơn!!!";
						}
					this.ngayyeucau = rs.getString("ngayyeucau");
					this.ghichu = rs.getString("ghichu");
					if(rs.getString("trangthai").equals("1")){
					this.ngaynhan = rs.getString("ngaynhan");
					}
					if(rs.getString("trangthai").equals("1"))
					{
					this.sohoadon = rs.getString("CHUNGTU");
					}
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("---LOI INIT: " + e.getMessage());
			}
		}

		this.createRs();
	}*/

	String kenhbanhang()
	{
		String st ="";
		String sql ="select distinct kbh_fk from nhaphang where pk_seq ='"+ this.id +"'";
		ResultSet rs = db.get(sql);
		try {
			rs.next();
			st = rs.getString("kbh_fk");

		} catch(Exception e) {
			e.printStackTrace();
		}
		finally{try {
			if(rs != null)
				rs.close();
		} catch (Exception e2) {
			// TODO: handle exception
		}}
		return st;
	}
	public boolean UpdateNhaphangNpp() 
	{ 	getNppInfo();

	return true;
	}

	public boolean create() 
	{
		geso.dms.center.util.Utility util = new geso.dms.center.util.Utility();
		//getNppInfo();
		if(this.ngaynhan.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn ngày nhận hàng";
			return false;
		}
		/*if(this.sohoadon.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập số chứng từ";
			return false;
		}*/

		if(this.spId == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm nhập kho";
			return false;
		}

		try
		{
			
			
			
			
			if(this.ngayyeucau.trim().compareTo(this.ngaynhan.trim()) > 0)
			{
				this.msg = "ngày xuất kho không được sau ngày nhận hàng.";
				return false;
			}

			String loainpp = "", nppc1 = "";
			String query = "select loainpp, nppc1 from nhaphanphoi where pk_seq = ?";
			ResultSet rs = db.getByPreparedStatement(query, new Object[]{this.nppId});
			if(rs.next())
			{
				loainpp = rs.getString("loainpp")==null ? "''": rs.getString("loainpp");
				nppc1 = rs.getString("nppc1")==null ? "NULL": rs.getString("nppc1");
			}
			
			db.getConnection().setAutoCommit(false);
			String npp_fk = "", kbh_fk = "";
			
			
			String msgKS  = geso.dms.center.util.Utility.Check_Huy_NghiepVu_KhoaSo_Tao_Moi_NV(this.nppId, this.ngaynhan, db);
			if( msgKS.length() > 0)
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				this.msg = msgKS;
				return false;
			}
			
			query = "insert into nhaphang(ghichu,NGAYNHAN, NGUOITAO, NGUOISUA, NGAYTAO, NGAYSUA, TRANGTHAI, NPP_FK, NCC_FK, SOTIENBVAT, VAT, SOTIENAVAT, "+
					"DVKD_FK, KBH_FK, NGAYCHUNGTU, KHO_FK, CHUNGTU, HDTAICHINH, DONDATHANG_FK, Created_Date, LOAI)"+
					"select N'"+ this.ghichu +"','"+ this.ngaynhan +"', "+userId+", "+userId+", '"+getDateTime()+"', '"+getDateTime()+"', 0,'"+ this.nppId +"' NPP_FK, "+nppc1+", 0 BVAT, 8 Vat, 0 AVAT, "+
					"'"+ this.dvkdId +"' DVKD_FK,'"+ this.kbhId +"' KBH_FK, '"+ this.ngayyeucau +"', '"+ this.khoNhanId +"' as Khodat, '' CHUNGTU, '' HDTAICHINH, NULL DONDATHANG_FK, dbo.GetLocalDate(DEFAULT), '1' ";
			System.out.println("query : "+ query);
			if(!this.db.update(query))
			{
				this.msg = "Có lỗi trong khi tạo mới nhập hàng.";
				this.db.getConnection().rollback();
				return false;
			}
			String id = "";
			rs = db.get("SELECT SCOPE_IDENTITY() AS nhaphangId");
			rs.next();
			id = rs.getString("nhaphangId");
			
			query = " select KBH_FK,NPP_FK from nhaphang where PK_SEQ = ? ";
			rs = db.getByPreparedStatement(query, new Object[]{ id });
			if(rs.next())
			{
				npp_fk = rs.getString("npp_fk")==null ? "": rs.getString("npp_fk");
				kbh_fk = rs.getString("kbh_fk")==null ? "": rs.getString("kbh_fk");
			}
			
			System.out.println("spidlength : "+ spId.length);
			
			for(int i = 0; i < spId.length; i++)
			{
				if(spId[i].length() > 0)
				{
					System.out.println(" spid: "+ spId[i] +" - sl : "+ Double.parseDouble(spSoluong[i].replaceAll(",", "")) + " - "+ Double.parseDouble(spDongia[i].replaceAll(",", "")) +" - "+ Double.parseDouble(spVAT[i]) );
					double soluong = Double.parseDouble(spSoluong[i].replaceAll(",", ""));
					double dongia = Double.parseDouble(spDongia[i].replaceAll(",", ""));
					double vat = Double.parseDouble(spVAT[i]); //.replaceAll(",", ""));
					double avat = ( soluong * dongia ) * ( 1 + (Math.round(vat/100)) );
					String scheme = "";
				   //	System.out.println("id : "+ id + " - spId[i] : "+ spId[i] + " - soluong : "+ soluong + " - dongia : "+ dongia + " - vat : "+ vat + " spDonvi[i] : "+ spDonvi[i]+" - kho : "+ this.khoNhanId);
					
					query = " insert into nhaphang_sp (nhaphang_fk, sanpham_fk, soluong, soluongnhan, donvi, gianet, tienbvat, vat, tienavat, scheme, kho_fk) "+
							" select '"+ id +"', ( select pk_seq from sanpham where ma = '"+ spId[i] +"' ), "+ soluong +", "+ soluong +", N'"+ spDonvi[i] +"', "+ dongia +", "+ ( soluong * dongia ) +", "+ vat +", "+ avat +",'"+ scheme +"', '"+ this.khoNhanId +"'"; 
					System.out.println("[nhaphang_sp]"+ query);
					if( !this.db.update(query) )
					{
						msg = "Không thể tạo nhận hàng chi tiết: " + query;
						this.db.getConnection().rollback();
						return false;
					}
				}
			
				
				/*this.msg=util.Update_NPP_Kho_Sp(this.ngaynhan,this.id, "Tạo mới nhập hàng" + this.id, db, spKhoId[i], spId[i], npp_fk, kbh_fk, soluong, 0.0, soluong, 0.0);
				if(this.msg.length()>0)
				{
					//this.msg = "Bạn không thể 35345lần nữa " + query;
					db.getConnection().rollback();
					return false;
				}
				String ngaynhapkho= "";
				
				String msg1=util.Update_NPP_Kho_Sp_Chitiet(this.ngaynhan, "Tạo mới nhập hàng" + this.id, db, spKhoId[i], spId[i], npp_fk, kbh_fk, this.ngaynhan,soluong, 0, soluong, 0, 0);
				if (msg1.length()> 0) 
				{
					//this.msg = "Bạn324hàng lần nữa " + query;
					db.getConnection().rollback();
					return false;
				}
				*/
				/*if(loainpp.equals("6"))
				{
					this.msg=util.Update_NPP_Kho_Sp(this.ngaynhan,this.id, "Nhận hàng_438", db, spKhoId[i], spId[i], nppc1, kbh_fk, (-1)*soluong, (-1)*soluong, 0.0, 0.0);

					if(this.msg.length()>0)
					{
						//this.msg = "Bạ45345 nữa " + query;
						db.getConnection().rollback();
						return false;
					}	
					msg1=util.Update_NPP_Kho_Sp_Chitiet(this.ngaynhan, "uNhận hàng_: "+this.id ,  db, spKhoId[i], spId[i], npp_fk, kbh_fk, this.ngaynhan,(-1)*soluong, (-1)*soluong, 0, 0, 0);
					if (msg1.length()> 0) 
					{
						//this.msg = "Bạn kh1312414 " + query;
						db.getConnection().rollback();
						return false;
					}
				}*/
			}
			
			query = " UPDATE NH SET NH.SOTIENBVAT = CT.TIENBVAT, NH.VAT = CT.VAT, NH.SOTIENAVAT = CT.TIENAVAT, NH.CHUNGTU = 'NH"+ id +"' " +
					" FROM NHAPHANG NH " +
					" INNER JOIN ( SELECT NHAPHANG_FK, ISNULL(SUM(TIENBVAT), 0) TIENBVAT, ISNULL(VAT, 0) VAT, ISNULL(SUM(TIENAVAT), 0) TIENAVAT " +
					" FROM NHAPHANG_SP WHERE NHAPHANG_FK = '"+ id +"' GROUP BY NHAPHANG_FK, VAT ) AS CT " +
					" ON CT.NHAPHANG_FK = NH.PK_SEQ " +
					" WHERE NH.PK_SEQ = '"+ id +"' "; 
			if( this.db.updateReturnInt(query)!= 1 )
			{
				msg = "Không thể tạo mới nhập hàng.";
				this.db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			try { db.getConnection().rollback(); } catch (SQLException e1) { e1.printStackTrace(); }
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean chot() 
	{
		geso.dms.center.util.Utility util = new geso.dms.center.util.Utility();
		getNppInfo();
		if(this.ngaynhan.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn ngày nhận hàng";
			return false;
		}

		if(this.sohoadon.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập số chứng từ";
			return false;
		}

		if(spId == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm nhập kho";
			return false;
		}

		try
		{
			//CHECK SO CHUNG TU
			String ycxk_fk = "", so = "";
			String ngayct ="";
			String query =
						"	select  ChungTu as SoChungTu, ngaychungtu, so_number  "+
						"	from NhapHang  " +
						" 	where pk_Seq=?   ";

			System.out.println("[SoCT]"+query);

			ResultSet rs = db.getByPreparedStatement(query, new Object[]{this.id});

			if(rs.next())
			{
				ycxk_fk = rs.getString("SoChungTu")==null ? "": rs.getString("SoChungTu");
				so = rs.getString("so_number")==null ? "": rs.getString("so_number");
				ngayct = rs.getString("ngaychungtu")==null ? "": rs.getString("ngaychungtu");
			}

			if(rs != null)
			{				
				rs.close();	
			}

			if(ycxk_fk.length()<=0 && !this.loai.equals("1"))
			{
				this.msg = "Số chứng từ chưa phát sinh!Vui lòng liên hệ TT!";
				return false;
			}

			System.out.println("Số hd "+this.sohoadon);
			System.out.println("Số yc "+ycxk_fk);
			
			
			

			String loainpp = "", nppc1 = "";
			query = "select loainpp, nppc1 from nhaphanphoi where pk_seq = ?";
			rs = db.getByPreparedStatement(query, new Object[]{this.nppId});
			if(rs.next())
			{
				loainpp = rs.getString("loainpp")==null ? "": rs.getString("loainpp");
				nppc1 = rs.getString("nppc1")==null ? "": rs.getString("nppc1");
			}
			
			db.getConnection().setAutoCommit(false);		
			
			String sql1 = "update nhaphang set trangthai = 0 where trangthai=0 and pk_seq="+this.id+" ";
			if(db.updateReturnInt(sql1)<=0) {
				this.msg = "10. Lỗi hệ thống. Vui lòng đăng nhập và thao tác lại!";
				db.getConnection().rollback();
				return false;
			}
			
			String msgKS  = geso.dms.center.util.Utility.Check_Huy_NghiepVu_KhoaSo_Tao_Moi_NV(this.nppId, this.ngaynhan, db);
			if( msgKS.length() > 0)
			{
				this.msg = msgKS;
				db.getConnection().rollback();
				return false;
			}
			
			
			
			String npp_fk = "", kbh_fk = "";
			query = " select KBH_FK,NPP_FK from nhaphang where PK_SEQ = ? ";
			rs = db.getByPreparedStatement(query, new Object[]{this.id});
			if(rs.next())
			{
				npp_fk = rs.getString("npp_fk")==null ? "": rs.getString("npp_fk");
				kbh_fk = rs.getString("kbh_fk")==null ? "": rs.getString("kbh_fk");
			}
			
			for(int i = 0; i < spId.length; i++)
			{
				System.out.println("spid : "+ spId.length);
				if(spId[i].length() > 0)
				{
					
					String sql = "select pk_seq from sanpham where ma = '"+ spId[i] +"' ";
					rs = db.get(sql);
					rs.next();
					String spid = rs.getString("pk_seq");
					
					double soluong = Double.parseDouble(spSoluong[i].replaceAll(",", ""));
					System.out.println("soluong : "+ soluong );
					System.out.println("ngaynhan : "+ ngaynhan );
					System.out.println("id : "+ this.id );
					System.out.println("spKhoId[i] : "+ spKhoId[i] );
					System.out.println("spId[i] : "+ spId[i] );
					System.out.println("npp_fk : "+ npp_fk );
					System.out.println("kbh_fk"+ kbh_fk );
					System.out.println("soluong"+ soluong );
					
					//+" - "+this.ngaynhan+" - "+ this.id +" - "+ spKhoId[i] +" - "+ spId[i] +" - " + spId[i] +" - "+ npp_fk+" - "+ kbh_fk + " - "+ soluong  )  ;
					this.msg=util.Update_NPP_Kho_Sp(this.ngaynhan,this.id, "Nhận hàng_429", db, spKhoId[i],spid, npp_fk, kbh_fk, soluong, 0.0, soluong, 0.0);

					if(this.msg.length()>0)
					{
						//this.msg = "Bạn không thể 35345lần nữa " + query;
						db.getConnection().rollback();
						return false;
					}
					String ngaynhapkho= "";
					
					String msg1=util.Update_NPP_Kho_Sp_Chitiet(this.ngaynhan, "uNhận hàng_: "+this.id ,  db, spKhoId[i], spid, npp_fk, kbh_fk, this.ngaynhan,soluong, 0, soluong, 0, 0);
					if (msg1.length()> 0) 
					{
						//this.msg = "Bạn324hàng lần nữa " + query;
						db.getConnection().rollback();
						return false;
					}
					
					if(loainpp.equals("6"))
					{
						this.msg=util.Update_NPP_Kho_Sp(this.ngaynhan,this.id, "Nhận hàng_438", db, spKhoId[i], spId[i], nppc1, kbh_fk, (-1)*soluong, (-1)*soluong, 0.0, 0.0);

						if(this.msg.length()>0)
						{
							//this.msg = "Bạ45345 nữa " + query;
							db.getConnection().rollback();
							return false;
						}	
						msg1=util.Update_NPP_Kho_Sp_Chitiet(this.ngaynhan, "uNhận hàng_: "+this.id ,  db, spKhoId[i], spId[i], npp_fk, kbh_fk, this.ngaynhan,(-1)*soluong, (-1)*soluong, 0, 0, 0);
						if (msg1.length()> 0) 
						{
							//this.msg = "Bạn kh1312414 " + query;
							db.getConnection().rollback();
							return false;
						}
					}
				}
				
			}
			//Utility util =new Utility();
			String nppId=util.getIdNhapp(userId);
			query = 
					" Update NhapHang set vat=8,trangthai = '1', ghichu = N'"+ this.ghichu +"', ngaysua = ?, nguoisua = ?, " +
					" thoigianchot = dbo.GetLocalDate(DEFAULT), nguoichot = ?, ngaynhan = ? "+
					" where pk_seq = ? and trangthai = '0' ";
 
			//System.out.println("1.Update NhapHang: " + query);
			geso.dms.center.db.sql.dbutils.viewQuery(query, new Object[]{this.getDateTime(), this.userId,this.userId,this.ngaynhan,this.id} );
			if (db.updateQueryByPreparedStatement(query, new Object[]{this.getDateTime(), this.userId,this.userId,this.ngaynhan,this.id}) < 1)
			{
				this.msg = "Bạn không thể nhận hàng lần nữa ";
				db.getConnection().rollback();
				return false;
			}
			
			query= "UPDATE NHAPHANG_SP SET NGAYNHAPKHO=?, SOLUONGNHAN = SOLUONG  WHERE NHAPHANG_FK=?";
			
			if (db.updateQueryByPreparedStatement(query, new Object[]{this.ngaynhan, this.id}) < 1 )
			{
				this.msg = "Bạn không thể nhận hàng lần nữa ";
				db.getConnection().rollback();
				return false;
			}
			
			query="INSERT INTO LOGNHAP (NHAPHANG_FK,USERID,NPP_FK,SOCHUNGTU,soso) VALUES(?,?,?,?,?)";
			System.out.println("insert lognhap "+query);
			if(this.db.updateQueryByPreparedStatement(query, new Object[]{this.id, this.userId, this.nppId,ycxk_fk,so}) < 1){

				this.msg="Khong the Cap Nhat Nhap Hang ,Vui Long Thu Lai : ";
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			//this.msg = "Exception: " + e.getMessage();
			e.printStackTrace();
			return false;
		}
		System.out.println("toiday");
		return true;
	}

	/*public boolean chot() 
	{
		geso.dms.center.util.Utility util = new geso.dms.center.util.Utility();
		getNppInfo();
		if(this.ngaynhan.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn ngày nhận hàng";
			return false;
		}

		if(this.sohoadon.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập số chứng từ";
			return false;
		}

		if(spId == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm nhập kho";
			return false;
		}

		try
		{
			//CHECK SO CHUNG TU
			String ycxk_fk = "", so = "";
			String ngayct ="";
			String query =
						"	select  ChungTu as SoChungTu, ngaychungtu, so_number  "+
						"	from NhapHang  " +
						" 	where pk_Seq='"+this.id+"'   ";

			System.out.println("[SoCT]"+query);

			ResultSet rs = db.get(query);

			if(rs.next())
			{
				ycxk_fk = rs.getString("SoChungTu")==null ? "": rs.getString("SoChungTu");
				so = rs.getString("so_number")==null ? "": rs.getString("so_number");
				ngayct = rs.getString("ngaychungtu")==null ? "": rs.getString("ngaychungtu");
			}

			if(rs != null)
			{				
				rs.close();	
			}

			if(ycxk_fk.length()<=0)
			{
				this.msg = "Số chứng từ chưa phát sinh!Vui lòng liên hệ TT!";
				return false;
			}

			System.out.println("Số hd "+this.sohoadon);
			System.out.println("Số yc "+ycxk_fk);
			
			if(!this.sohoadon.trim().equals(ycxk_fk.trim()) )
			{
				this.msg = "Số chứng từ không hợp lệ";
				return false;
			}
			String ngayks = "";
			// check ngay ks
			query = "select MAX(NGAYKS) as ngayks from KHOASONGAY where NPP_FK = '"+this.nppId+"'";
			rs = db.get(query);

			if(rs.next())
			{
				ngayks  = rs.getString("ngayks")==null?"":rs.getString("ngayks");
			
			}
			
			if(ngayks.length() <= 0)
			{
				this.msg = "Nhà phân phối chưa khóa sổ hoặc ngày điều chỉnh phải lớn hơn ngày khóa sổ";
			
				return false;
			}
			if(ngayks.compareTo(this.ngaynhan.substring(0,10)) >= 0)
			{
				//this.msg = "Không thể nhận hàng trước ngày khóa sổ hoặc không có ngày khóa sổ!";
				this.msg = "Vui lòng chọn ngày nhận hàng khớp với ngày nhận hàng thực tế. Cảm ơn!!!";
				
				return false;
			}
			
			if(ngayct.substring(0,10).compareTo(this.ngaynhan.substring(0,10)) > 0)
			{
				//this.msg = "Không thể nhận hàng trước ngày khóa sổ hoặc không có ngày khóa sổ!";
				this.msg = "Vui lòng chọn ngày nhận hàng khớp với ngày nhận hàng thực tế. Cảm ơn!!!";
				
				return false;
			}
			

			String loainpp = "", nppc1 = "";
			query = "select loainpp, nppc1 from nhaphanphoi where pk_seq = "+this.nppId;
			rs = db.get(query);
			if(rs.next())
			{
				loainpp = rs.getString("loainpp")==null ? "": rs.getString("loainpp");
				nppc1 = rs.getString("nppc1")==null ? "": rs.getString("nppc1");
			}
			
			db.getConnection().setAutoCommit(false);		
			
			
			String npp_fk = "", kbh_fk = "";
			query = " select KBH_FK,NPP_FK from nhaphang where PK_SEQ = '" + this.id + "' ";
			rs = db.get(query);
			if(rs.next())
			{
				npp_fk = rs.getString("npp_fk")==null ? "": rs.getString("npp_fk");
				kbh_fk = rs.getString("kbh_fk")==null ? "": rs.getString("kbh_fk");
			}
			
			for(int i = 0; i < spId.length; i++)
			{
 
				double soluong = Double.parseDouble(spSoluong[i].replaceAll(",", ""));
				this.msg=util.Update_NPP_Kho_Sp(this.ngaynhan,this.id, "Nhận hàng_429", db, spKhoId[i], spId[i], npp_fk, kbh_fk, soluong, 0.0, soluong, 0.0);

				if(this.msg.length()>0)
				{
					this.msg = "Bạn không thể 35345lần nữa " + query;
					db.getConnection().rollback();
					return false;
				}
				String ngaynhapkho= "";
				
				String msg1=util.Update_NPP_Kho_Sp_Chitiet(this.ngaynhan, "uNhận hàng_: "+this.id ,  db, spKhoId[i], spId[i], npp_fk, kbh_fk, this.ngaynhan,soluong, 0, soluong, 0, 0);
				if (msg1.length()> 0) 
				{
					this.msg = "Bạn324hàng lần nữa " + query;
					db.getConnection().rollback();
					return false;
				}
				
				if(loainpp.equals("6"))
				{
					this.msg=util.Update_NPP_Kho_Sp(this.ngaynhan,this.id, "Nhận hàng_438", db, spKhoId[i], spId[i], nppc1, kbh_fk, (-1)*soluong, (-1)*soluong, 0.0, 0.0);

					if(this.msg.length()>0)
					{
						this.msg = "Bạ45345 nữa " + query;
						db.getConnection().rollback();
						return false;
					}	
					msg1=util.Update_NPP_Kho_Sp_Chitiet(this.ngaynhan, "uNhận hàng_: "+this.id ,  db, spKhoId[i], spId[i], npp_fk, kbh_fk, this.ngaynhan,(-1)*soluong, (-1)*soluong, 0, 0, 0);
					if (msg1.length()> 0) 
					{
						this.msg = "Bạn kh1312414 " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			
			}
			//Utility util =new Utility();
			String nppId=util.getIdNhapp(userId);
			
			
			query = 
					" Update NhapHang set trangthai = '1', ngaysua = '" + this.getDateTime() + "', nguoisua = '" + this.userId + "', " +
					" thoigianchot = dbo.GetLocalDate(DEFAULT), nguoichot = '" + this.userId + "', ngaynhan = '"+this.ngaynhan+"' "+
					" where pk_seq = '" + this.id+ "' and trangthai=0 ";

			System.out.println("1.Update NhapHang: " + query);
			if (db.updateReturnInt(query) != 1)
			{
				this.msg = "Bạn không thể nhận hàng lần nữa " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query= "UPDATE NHAPHANG_SP SET NGAYNHAPKHO='"+this.ngaynhan+"' WHERE NHAPHANG_FK="+ this.id;
			
			if (db.updateReturnInt(query) <=0 )
			{
				this.msg = "Bạn không thể nhận hàng lần nữa " + query;
				db.getConnection().rollback();
				return false;
			}

	
			
			
			
			
			query="INSERT INTO LOGNHAP (NHAPHANG_FK,USERID,NPP_FK,SOCHUNGTU,soso) VALUES('"+this.id+"','"+this.userId+"','"+this.nppId+"','"+ycxk_fk+"','"+so+"')";
			System.out.println("insert lognhap "+query);
			if(!this.db.update(query)){

				this.msg="Khong the Cap Nhat Nhap Hang ,Vui Long Thu Lai : "+ query;
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}

			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			//geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.msg = "Exception: " + e.getMessage();
			e.printStackTrace();
			return false;
		}
		System.out.println("toiday");
		return true;
	}*/

	private void getNppInfo()
	{
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		System.out.println("________+_"+this.userId);
		this.nppId=util.getIdNhapp(this.userId,this.db);
		this.nppTen=util.getTenNhaPP();
	}

	public boolean isInteger( String input )  
	{  
		try  
		{  
			Integer.parseInt( input );  
			return true;  
		}  
		catch(Exception e)  
		{  
			return false;  
		}  
	}  

	public String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}


	public void DBclose(){
		try 
		{

			if(this.db != null){
				this.db.shutDown();
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}


	public void createRs()
	{
		this.getNppInfo();
		String	query = "";
		
		String loainpp = "", kho = "khott";
		query = "select loainpp, khosap from NHAPHANPHOI where PK_SEQ=?";
		ResultSet rs = db.getByPreparedStatement(query, new Object[]{this.nppId});
		if(rs != null)
		{
			try
			{
				rs.next();
				loainpp = rs.getString("loainpp");
				/*if(this.khoNhanId == null || this.khoNhanId.length() <= 0)
					this.khoNhanId = rs.getString("khosap");*/
				if(loainpp.trim().equals("6"))
					kho = "kho";
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		query = "select PK_SEQ, TEN + '-' + DIENGIAI AS TEN from KHO where trangthai = '1' and pk_seq = 100000  ";
		this.khoNhanRs = db.get(query);

		
		this.dvkdRs = db.get("select PK_SEQ, DONVIKINHDOANH as TEN from DONVIKINHDOANH where TRANGTHAI = '1' ");
		query = "select PK_SEQ, DIENGIAI as TEN from KENHBANHANG where TRANGTHAI = '1' "; 
		this.kbhRs = db.get(query);

		query = "\n select PK_SEQ, MA + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1' and pk_seq = ?";
		this.nppRs = db.getByPreparedStatement(query, new Object[]{this.nppId});
		
		Utility util = new Utility();
		if(this.id.trim().length() > 0 )
		{
			query = "\n select b.PK_SEQ, b.MA, b.TEN, a.DONVI, GiaNet as DONGIA, isnull(a.SOLO, 'NA') as SOLO, a.SOLUONG,";
			query += "\n ISNULL(SOLUONGNHAN, a.SOLUONG) as soluongNHAN, tienbvat, vat, tienavat, ";
			query += "\n isnull(a.SCHEME, '') as SCHEME, a.kho_fk as khoId,a.chietkhau,10 as thuevat ";
			query += "\n from nhaphang_sp a inner join SANPHAM b on a.SANPHAM_FK = b.pk_seq 		";
			query += "\n inner join DONVIDOLUONG c on b.DVDL_FK = c.PK_SEQ  where a.NHAPHANG_FK = ?   ";

			Object[] data;
			data = new Object[] {this.id};
			System.out.println("true or false: " + data==null);
			dbutils.viewQuery(query, data);
			ResultSet spRs = db.getByPreparedStatement(query, data);
			NumberFormat formater = new DecimalFormat("##,###,###");

			if(spRs != null)
			{
				try 
				{
					String spID = "";
					String spMA = "";
					String spTEN = "";
					String spDONVI = "";
					String spDONGIA = "";
					String spVAT = "";
					String spchietkhau = "";
					String spSOLO = "";
					String spXUAT = "";
					String spSOLUONG = "";
					String spLOAI = "";
					String spSCHEME = "";
					String spNGAYHETHAN = "";
					String spKHOID = "";
					String BVAT = "";
					String VAT = "";
					String AVAT = "";

					while(spRs.next())
					{
						spID += spRs.getString("PK_SEQ") + "__";
						spMA += spRs.getString("MA") + "__";
						spTEN += spRs.getString("TEN") + "__";
						spDONVI += spRs.getString("DONVI") + "__";
						spDONGIA += spRs.getDouble("DONGIA") + "__";
						spXUAT += formater.format(spRs.getDouble("SOLUONG")) + "__";
						spSOLUONG += formater.format(spRs.getDouble("soluongNHAN")) + "__";
						spchietkhau += formater.format(spRs.getDouble("chietkhau")) + "__";
						spVAT += formater.format(spRs.getDouble("thuevat")) + "__";

						BVAT += formater.format(spRs.getDouble("tienbvat")) + "__";
						VAT += formater.format(spRs.getDouble("vat")) + "__";
						AVAT += formater.format(spRs.getDouble("tienavat")) + "__";

						if(spRs.getString("SOLO").trim().length() > 0)
							spSOLO += spRs.getString("SOLO") + "__";
						else 
							spSOLO += " __";

						if(spRs.getString("SCHEME").trim().length() > 0)
							spSCHEME += spRs.getString("SCHEME") + "__";
						else
							spSCHEME += " __";

						if(spRs.getString("khoId").trim().length() > 0)
							spKHOID += spRs.getString("khoId") + "__";
						else
							spKHOID += " __";

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

						spDONVI = spDONVI.substring(0, spDONVI.length() - 2);
						this.spDonvi = spDONVI.split("__");

						spDONGIA = spDONGIA.substring(0, spDONGIA.length() - 2);
						this.spDongia = spDONGIA.split("__");

						spXUAT = spXUAT.substring(0, spXUAT.length() - 2);
						this.spXuat = spXUAT.split("__");
						
						spSOLUONG = spSOLUONG.substring(0, spSOLUONG.length() - 2);
						this.spSoluong = spSOLUONG.split("__");

						spSCHEME = spSCHEME.substring(0, spSCHEME.length() - 2);
						this.spSCheme = spSCHEME.split("__");

						spVAT = spVAT.substring(0, spVAT.length() - 2);
						this.spvat = spVAT.split("__");
						spchietkhau = spchietkhau.substring(0, spchietkhau.length() - 2);
						this.spchietkhau = spchietkhau.split("__");

						spKHOID = spKHOID.substring(0, spKHOID.length() - 2);
						System.out.println("spKhoid : "+ spKHOID);
						this.spKhoId = spKHOID.split("__");
						
						BVAT = BVAT.substring(0, BVAT.length() - 2);
						this.bvat = BVAT.split("__");
						
						VAT = VAT.substring(0, VAT.length() - 2);
						this.vat = VAT.split("__");

						AVAT = AVAT.substring(0, AVAT.length() - 2);
						this.avat = AVAT.split("__");
					}

				} 
				catch (Exception e) {e.printStackTrace(); System.out.println("EXCEPTION SP: " + e.getMessage() ); }

			}

		}
		List<Object> data = new ArrayList<Object>();
		this.khonhanRs = db.get("select pk_seq, ten from KHO order by pk_seq asc");
		
		/*query = "select CHUNGTU, trangthai from nhaphang where TRANGTHAI = 0 and npp_fk = ?";
		data.add(this.nppId);
		if(this.id.length() > 0)
		{
			query += " union select CHUNGTU from nhaphang where pk_seq = ?";
			data.add(this.nppId);
		}
			
		this.chungtuRs = db.getByPreparedStatement(query, data);*/
		
		this.getNppInfo();

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
	
	public String[] getSpXuat() {

		return this.spXuat;
	}

	public void setSpXuat(String[] spXuat) {

		this.spXuat = spXuat;
	}

	public String[] getSpTonKho() {

		return this.spTonkho;
	}

	public void setSpTonKho(String[] spTonkho) {

		this.spTonkho = spTonkho; 
	}
	public String[] getSpchietkhau()
	{
		return spchietkhau;
	}

	public void setSpchietkhau(String[] spchietkhau)
	{
		this.spchietkhau = spchietkhau;
	}

	public String[] getSpvat()
	{
		return spvat;
	}

	public void setSpvat(String[] spvat)
	{
		this.spvat = spvat;
	}

	public String[] getSpDongia() {

		return this.spDongia;
	}


	public void setSpDongia(String[] spDongia) {

		this.spDongia = spDongia;
	}

	public String[] getSpScheme() {

		return this.spSCheme;
	}


	public void setSpScheme(String[] spScheme) {

		this.spSCheme = spScheme;
	}

	String[] spKhoId;
	@Override
	public String[] getSpKhoId() 
	{
		return spKhoId;
	}
	@Override
	public void setSpKhoId(String[] spKhoId) 
	{
		this.spKhoId =spKhoId;
	}

	String sohoadon="";

	public String getSohoadon() {
		return sohoadon;
	}
	public void setSohoadon(String sohoadon) {
		this.sohoadon = sohoadon;
	}
	@Override
	public boolean update() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public String[] getBvat() {
		return this.bvat;
	}
	@Override
	public void setBvat(String[] bvat) {
		this.bvat = bvat;
	}
	@Override
	public String[] getVat() {
		return this.vat;
	}
	@Override
	public void setVat(String[] vat) {
		this.vat = vat;
	}
	@Override
	public String[] getAvat() {
		return this.avat;
	}
	@Override
	public void setAvat(String[] avat) {
		this.avat = avat;
	}
	@Override
	public ResultSet getChungtuRs() {
		return this.chungtuRs;
	}
	@Override
	public void setChungtuRs(ResultSet ctRs) {
		this.chungtuRs = ctRs;
	}
	
	/*-------------------------------------------------*/
	
	public String getKhoNhapId()
	{ return this.khoNhanId; }

	public void setKhoNhapId(String khonhaptt) 
	{ this.khoNhanId = khonhaptt; }

	public ResultSet getKhoNhapRs() 
	{ return this.khoNhanRs; }

	public void setKhoNHapRs(ResultSet khonhapRs) 
	{ this.khoNhanRs = khonhapRs; }
	
	public String getDvkdId() 
	{ return this.dvkdId; }

	public void setDvkdId(String dvkdId) 
	{ this.dvkdId = dvkdId; }

	public ResultSet getDvkdRs() 
	{ return this.dvkdRs; }

	public void setDvkdRs(ResultSet dvkdRs) 
	{ this.dvkdRs = dvkdRs; }

	public String getKbhId() 
	{ return this.kbhId; }

	public void setKbhId(String kbhId) 
	{ this.kbhId = kbhId; }

	public ResultSet getKbhRs() 
	{ return this.kbhRs; }

	public void setKbhRs(ResultSet kbhRs) 
	{ this.kbhRs = kbhRs; }
	@Override
	public ResultSet getNppRs() {
		// TODO Auto-generated method stub
		return this.nppRs;
	}
	@Override
	public void setNppRs(ResultSet nppRs) {
		// TODO Auto-generated method stub
		this.nppRs = nppRs;
	}
	
	public String[] getSpTonkho() {
		return spTonkho;
	}

	@Override
	public void setSpTonkho(String[] spTonkho) {
		this.spTonkho = spTonkho;
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
	
	public String[] getSpChietkhau() {

		return this.spChietkhau;
	}

	public void setSpChietkhau(String[] spChietkhau) {

		this.spChietkhau = spChietkhau;
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
	
	public String getLoaidonhang() {

		return this.loaidonhang;
	}

	public void setLoaidonhang(String loaidonhang) {

		this.loaidonhang = loaidonhang;
	}
	
	public String getNppTen() 
	{
		return this.nppTen;
	}

	public void setNppTen(String nppTen) 
	{
		this.nppTen = nppTen;
	}
	@Override
	public String getLoai() {
		// TODO Auto-generated method stub
		return this.loai;
	}
	@Override
	public void setLoai(String loai) {
		// TODO Auto-generated method stub
		this.loai = loai;	
	}
	public String getKbhTen() {
		return kbhTen;
	}
	public void setKbhTen(String kbhTen) {
		this.kbhTen = kbhTen;
	}
}
