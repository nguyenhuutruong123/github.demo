package geso.dms.distributor.beans.banggiabandoitac.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import geso.dms.center.db.sql.*;
import geso.dms.distributor.beans.banggiabandoitac.IBanggiabandoitac;

public class Banggiabandoitac implements IBanggiabandoitac
{
	private static final long serialVersionUID = -9217977546733610214L;
	String userId;
	String userTen;
	String id;
	String ten;

	String trangthai;

	String msg;
	dbutils db;

	ResultSet dvkdRs;
	String dvkdId;
	
	ResultSet kenhRs;
	String kenhId;
	
	ResultSet doitacRs;
	String doitacIds;
	
	ResultSet sanphamRs;
	String spIds;
	Hashtable<String, String> spChietkhau;
	Hashtable<String, String> spDG_SauCK;

	String tungay, denngay;
	
	String nppId;
	String nppTen;
	String sitecode;
	public String getNhomsp() {
		return nhomsp;
	}

	public void setNhomsp(String nhomsp) {
		this.nhomsp = nhomsp;
	}

	public ResultSet getRsnhomsp() {
		return rsnhomsp;
	}

	public void setRsnhomsp(ResultSet rsnhomsp) {
		this.rsnhomsp = rsnhomsp;
	}

	String nhomsp;
	ResultSet rsnhomsp;
	public Banggiabandoitac(String id)
	{
		this.db = new dbutils();
		this.id = id;
		this.ten = "";
		this.msg = "";
		this.dvkdId = "";
		this.kenhId = "";
		this.tungay="";
		this.denngay="";
		this.trangthai = "1";
		this.spIds = "";
		this.doitacIds = "";
		this.spChietkhau = new Hashtable<String, String>();
		this.spDG_SauCK = new Hashtable<String, String>();
	}
	
	public Banggiabandoitac()
	{	
		this.db = new dbutils();
		this.id = "";
		this.ten = "";
		this.msg = "";
		this.dvkdId = "";
		this.kenhId = "";
		this.tungay="";
		this.denngay="";
		this.trangthai = "1";
		this.spIds = "";
		this.doitacIds = "";
		this.nhomsp="";
		this.spChietkhau = new Hashtable<String, String>();
		this.spDG_SauCK = new Hashtable<String, String>();
	}

	public String getUserId() 
	{
		return this.userId;
	}
	
	public String getUserTen() 
	{
		return this.userTen;
	}
	
	public void setUserTen(String userTen) 
	{
		this.userTen = userTen;
	}

	public String getId() 
	{
		return this.id;
	}
	
	public void setId(String id) 
	{
		this.id = id;
	}

	public String getTen() 
	{
		return this.ten;
	}

	public void setTen(String ten) 
	{
		this.ten = ten;
	}
	
	public String getDvkdId() 
	{
		return this.dvkdId;
	}

	public void setDvkdId(String dvkdId) 
	{
		this.dvkdId = dvkdId;
	}
	
	public String getKenhId() 
	{
		return this.kenhId;
	}

	public void setKenhId(String kenhId) 
	{
		this.kenhId = kenhId;
	}
	
	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getTrangthai() 
	{
		return this.trangthai;
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
		String query = "select ten, dvkd_fk as dvkdId, kenh_fk as kenhId, trangthai, tungay, isnull(denngay, '') as denngay " +
				       "from BANGGIABANDOITAC where pk_seq ='" + this.id + "' ";
		ResultSet rs = this.db.get(query);
		try
		{		
			rs.next();
			this.ten = rs.getString("ten");
			this.dvkdId = rs.getString("dvkdId");
			this.kenhId = rs.getString("kenhId");
			this.trangthai = rs.getString("trangthai");
			this.tungay = rs.getString("tungay");
			this.denngay = rs.getString("denngay");
				
			rs.close();
			
			
			//INIT DOI TAC
			query = "select NPP_FK from BANGGIABANDOITAC_DOITAC where BANGGIABANDOITAC_FK = '" + this.id + "' ";
			ResultSet doitacRS = db.get(query);
			String khIds = "";
			if(doitacRS != null)
			{
				while(doitacRS.next())
				{
					khIds += doitacRS.getString("NPP_FK") + ",";
				}
				doitacRS.close();
			}
			
			if(khIds.trim().length() > 0)
			{
				khIds = khIds.substring(0, khIds.length() - 1);
				this.doitacIds = khIds;
			}
			
			//INIT CHIET KHAU
			query = "select SANPHAM_FK, CHIETKHAU, ISNULL(DONGIA, 0) as DONGIA from BANGGIABANDOITAC_SANPHAM where BGBANDOITAC_FK = '" + this.id + "' ";
			ResultSet spRS = db.get(query);
			if(spRS != null)
			{
				while(spRS.next())
				{
					this.spChietkhau.put(spRS.getString("SANPHAM_FK"), spRS.getString("CHIETKHAU"));
					this.spDG_SauCK.put(spRS.getString("SANPHAM_FK"), spRS.getString("DONGIA"));
				}
				spRS.close();
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		createRS();
		this.rsnhomsp=db.get("select pk_seq , ten from nhomsanpham");
	}

	
	public void closeDB()
	{
		
	}

	public void setDenngay(String denngay) 
	{
		this.denngay = denngay;
	}
	
	public String getTungay() 
	{
		return tungay;
	}

	public void setTungay(String tungay) 
	{
		this.tungay = tungay;
	}

	public String getDenngay() 
	{
		return denngay;
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

	
	public void setUserId(String userId) {
		
		this.userId = userId;
	}

	
	public ResultSet getDvkdRs() {
		
		return this.dvkdRs;
	}

	
	public void setDvkdRs(ResultSet dvkdRs) {
		
		this.dvkdRs = dvkdRs;
	}

	
	public ResultSet getKenhRs() {
		
		return this.kenhRs;
	}

	
	public void setKenhRs(ResultSet kenhRs) {
		
		this.kenhRs = kenhRs;
	}

	
	public ResultSet getSanPhamList() {
		
		return this.sanphamRs;
	}

	
	public void setSanPhamList(ResultSet sanphamlist) {
		
		this.sanphamRs = sanphamlist;
	}

	
	public boolean CreateBg() 
	{
		if(this.ten.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập tên bảng giá";
			return false;
		}
		
		if(this.dvkdId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn đơn vị kinh doanh";
			return false;
		}
		
		if(this.kenhId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn kênh bán hàng";
			return false;
		}
		
		if(this.doitacIds.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn đối tác áp dụng";
			return false;
		}
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "insert BANGGIABANDOITAC(ten, dvkd_fk, kenh_fk, trangthai, tungay, npp_fk, ngaytao, ngaysua, nguoitao, nguoisua) " +
						   "values(N'" + this.ten + "', '" + this.dvkdId + "', '" + this.kenhId + "', '" + this.trangthai + "', '" + this.tungay + "', '" + this.nppId + "', '" + getDateTime() + "', '" + getDateTime() + "', '" + this.userId + "', '" + this.userId + "')";
			if(!db.update(query))
			{
				this.msg = "Lỗi khi tạo bảng giá: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			String bgId = "";
			query = "select ident_current('BANGGIABANDOITAC') as bgId ";
			ResultSet rs = db.get(query);
			rs.next();
			bgId = rs.getString("bgId");
			rs.close();
			
			query = "insert BANGGIABANDOITAC_DOITAC(BANGGIABANDOITAC_FK, NPP_FK) " +
					"select '" + bgId + "', pk_seq from NHAPHANPHOI where pk_seq in ( " + this.doitacIds + " )";
			if(!db.update(query))
			{
				this.msg = "Lỗi khi tạo bảng giá: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "insert BANGGIABANDOITAC_SANPHAM(BGBANDOITAC_FK, SANPHAM_FK, CHIETKHAU, DONGIA) " +
					"select '" + bgId + "', pk_seq, 0, 0 from SANPHAM ";
			if(!db.update(query))
			{
				this.msg = "Lỗi khi tạo bảng giá: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if(this.spChietkhau.size() > 0)
			{
				Enumeration<String> keys = this.spChietkhau.keys();
				while(keys.hasMoreElements())
				{
					String key = keys.nextElement();
					String ck = this.spChietkhau.get(key);
					if(ck.trim().length() <= 0)
						ck = "0";
					
					String dg = this.spDG_SauCK.get(key);
					if(dg.trim().length() <= 0)
						dg = "0";
					
					query = "update BANGGIABANDOITAC_SANPHAM set CHIETKHAU = '" + ck + "', DONGIA = '" + dg + "' " +
							"where BGBANDOITAC_FK = '" + bgId + "' and sanpham_fk = '" + key + "' ";
					if(!db.update(query))
					{
						this.msg = "Lỗi khi tạo bảng giá: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			db.getConnection().commit();
		} 
		catch (Exception e) 
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			e.printStackTrace();
			this.msg = "Lỗi khi tạo mới bảng giá: " + e.getMessage();
			return false;
		}
		
		return true;
		
	}

	
	public boolean UpdateBg() 
	{
		if(this.ten.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập tên bảng giá";
			return false;
		}
		
		if(this.dvkdId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn đơn vị kinh doanh";
			return false;
		}
		
		if(this.kenhId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn kênh bán hàng";
			return false;
		}
		
		if(this.doitacIds.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn đối tác áp dụng";
			return false;
		}
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "update BANGGIABANDOITAC set ten = N'" + this.ten + "', dvkd_fk = '" + this.dvkdId + "', kenh_fk = '" + this.kenhId + "', trangthai = '" + this.trangthai + "', " +
							"	tungay = '" + this.tungay + "', npp_fk = '" + this.nppId + "', ngaysua = '" + this.getDateTime() + "', nguoisua = '" + this.userId + "' " +
						   "where pk_seq = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Lỗi khi cập nhật bảng giá: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete BANGGIABANDOITAC_DOITAC where BANGGIABANDOITAC_FK = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Lỗi khi cập nhật bảng giá: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete BANGGIABANDOITAC_SANPHAM where BGBANDOITAC_FK = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Lỗi khi cập nhật bảng giá: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "insert BANGGIABANDOITAC_DOITAC(BANGGIABANDOITAC_FK, NPP_FK) " +
					"select '" + this.id + "', pk_seq from NHAPHANPHOI where pk_seq in ( " + this.doitacIds + " )";
			if(!db.update(query))
			{
				this.msg = "Lỗi khi cập nhật bảng giá: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "insert BANGGIABANDOITAC_SANPHAM(BGBANDOITAC_FK, SANPHAM_FK, CHIETKHAU, DONGIA) " +
					"select '" + this.id + "', pk_seq, 0, 0 from SANPHAM ";
			if(!db.update(query))
			{
				this.msg = "Lỗi khi cập nhậ bảng giá: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if(this.spChietkhau.size() > 0)
			{
				Enumeration<String> keys = this.spChietkhau.keys();
				while(keys.hasMoreElements())
				{
					String key = keys.nextElement();
					String ck = this.spChietkhau.get(key);
					if(ck.trim().length() <= 0)
						ck = "0";
					
					String dg = this.spDG_SauCK.get(key);
					if(dg.trim().length() <= 0)
						dg = "0";
					
					query = "update BANGGIABANDOITAC_SANPHAM set CHIETKHAU = '" + ck + "', DONGIA = '" + dg + "' " +
							"where BGBANDOITAC_FK = '" + this.id + "' and sanpham_fk = '" + key + "' ";
					if(!db.update(query))
					{
						this.msg = "Lỗi khi tạo bảng giá: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			db.getConnection().commit();
		} 
		catch (Exception e) 
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			e.printStackTrace();
			this.msg = "Lỗi khi tạo mới bảng giá: " + e.getMessage();
			return false;
		}
		
		return true;
	}

	
	public void createRS() 
	{
		
		
		this.getNppInfo();
		
		if(this.kenhId == null || this.kenhId.trim().length() <=0)
			this.kenhId ="100025";
		
		String query =  " select pk_seq, ma as maFAST, TEN, DIACHI, DIENTHOAI from NHAPHANPHOI " +
						" where pk_seq != '" + this.nppId + "' and trangthai = 1 ";
		if(this.id != null && this.id.trim().length() > 0)
			query +=  " union select pk_seq,ma as maFAST, TEN, DIACHI, DIENTHOAI from NHAPHANPHOI " +
					  " where pk_seq in ( select NPP_FK from BANGGIABANDOITAC_DOITAC where BANGGIABANDOITAC_FK  = "+this.id+"   ) ";
		query += " order by TEN asc ";		
		this.doitacRs = db.get(query);
	
		query = "select a.pk_seq, a.ma, a.ten, b.donvi, " +
				"	isnull(( select top(1) bgsp.giabanleNPP from BGBANLENPP_SANPHAM bgsp inner join BANGGIABANLENPP bg on bg.kbh_fk = "+this.kenhId+" and bgsp.BGBANLENPP_FK=bg.pk_seq where sanpham_fk = a.pk_seq and BGBANLENPP_FK in ( select pk_seq from BANGGIABANLENPP where npp_fk = '" + this.nppId + "'  ) order by bg.ngaybatdau DESC ),0) as gblc,  " +
				"	isnull(( select top(1) giabanleNPP from  BGBANLENPP_SANPHAM bgsp inner join BANGGIABANLENPP bg on bg.kbh_fk = "+this.kenhId+" and bgsp.BGBANLENPP_FK=bg.pk_seq where sanpham_fk = a.pk_seq and BGBANLENPP_FK in ( select pk_seq from BANGGIABANLENPP where npp_fk = '" + this.nppId + "'  ) order by bg.ngaybatdau DESC ),0) as giasauCK   " +
				"\n from SANPHAM a inner join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq " +
				"order by a.ten asc";
		
		
		System.out.println("sql la "+query);
		this.sanphamRs = db.get(query);
		
		this.dvkdRs = db.get("select pk_seq, diengiai from DONVIKINHDOANH");
		this.kenhRs = db.get("select pk_seq, diengiai from KENHBANHANG");
		this.rsnhomsp=db.get("select pk_seq ,ten from nhomsanpham");
		
	}

	
	public ResultSet getDoiTacList() {
		
		return this.doitacRs;
	}

	
	public void setDoiTacList(ResultSet doitaclist) {
		
		this.doitacRs = doitaclist;
	}

	
	public String getDoitacId() {
		
		return this.doitacIds;
	}

	
	public void setDoitacId(String doitacId) {
		
		this.doitacIds = doitacId;
	}

	
	public String getSanphamId() {
		
		return this.spIds;
	}

	
	public void setSanphamId(String sanphamId) {
		
		this.spIds = sanphamId;
	}

	
	public Hashtable<String, String> getSanphamCK() {

		return this.spChietkhau;
	}


	public void setSanphamCK(Hashtable<String, String> spCK) {

		this.spChietkhau = spCK;
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public String getNppId() 
	{
		return this.nppId;
	}

	public void setNppId(String nppId) 
	{
		this.nppId = nppId;
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

	public Hashtable<String, String> getSanphamDG_SauCK() 
	{
		return this.spDG_SauCK;
	}

	public void setSanphamDG_SauCK(Hashtable<String, String> spDG_SauCK) 
	{
		this.spDG_SauCK = spDG_SauCK;
	}
	
	
	
}