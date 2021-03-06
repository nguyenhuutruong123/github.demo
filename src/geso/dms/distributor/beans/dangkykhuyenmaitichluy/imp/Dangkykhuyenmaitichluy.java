package geso.dms.distributor.beans.dangkykhuyenmaitichluy.imp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.dangkykhuyenmaitichluy.IDangkykhuyenmaitichluy;
import geso.dms.distributor.db.sql.dbutils;

public class Dangkykhuyenmaitichluy implements IDangkykhuyenmaitichluy, Serializable
{
	private static final long serialVersionUID = 1L;
	 String nppId;
	String Id;
	String userId;
    String nppIds;
    String tungay;
    String denngay;
    
    ResultSet ctkmRs;
    String ctkmId;
    
    ResultSet nvbhRS;
	String nvbhIds;
	
    ResultSet khRs;
	String khId;
	
	ResultSet vungRs;
	String vungIds;
	
	ResultSet khuvucRs;
	String khuvucIds;
	
	ResultSet nppRs;
	
	String nppIdSelecteds;

	String Msg;
	Utility utl;
	dbutils db;

	private Hashtable<String, Integer> kh_mucDK;

	private Hashtable<String, Integer> kh_stt;
	public Hashtable<String, String> getKh_tinhthanh() {
		return kh_tinhthanh;
	}
	public void setKh_tinhthanh(Hashtable<String, String> kh_tinhthanh) {
		this.kh_tinhthanh = kh_tinhthanh;
	}
	public Hashtable<String, String> getKh_ddkd() {
		return kh_ddkd;
	}
	public void setKh_ddkd(Hashtable<String, String> kh_ddkd) {
		this.kh_ddkd = kh_ddkd;
	}
	private Hashtable<String, String> kh_tinhthanh;
	private Hashtable<String, String> kh_ddkd;

	private ResultSet khExportRs;

	private String action;
	
	public Dangkykhuyenmaitichluy()
	{
		this.userId="";
		this.nppIds="";
		this.tungay="";
		this.denngay="";
		this.ctkmId ="";
		this.nvbhIds = "";
		this.khId = "";
		this.Msg ="";
		this.Id = "";
		this.db = new dbutils();
		utl = new Utility();
		this.vungIds = "";
		this.khuvucIds = "";
		this.nppIdSelecteds = "";
		this.action = "";
		this.kh_mucDK = new Hashtable<String, Integer>();
		this.kh_stt = new Hashtable<String, Integer>();
		this.kh_tinhthanh = new Hashtable<String, String>();
		this.kh_ddkd = new Hashtable<String, String>();
	}
	public String getUserId() {
		
		return this.userId;
	}
	
	public void setUserId(String userId) {
		
		this.userId = userId;
	}

	
	public String getNppIds() {
		
		return this.nppIds;
	}

	
    public void setNppIds(String[] nppIds) {
    	String tmp = "";
		if(nppIds != null)
			for(int i=0;i<nppIds.length; i++)
				if(nppIds[i].trim().length() > 0)
					tmp += nppIds[i].trim() + ",";
		if(tmp.length() > 0)
			tmp = tmp.substring(0, tmp.length() - 1);
		this.nppIds = tmp;	
	}

	
	public void setTungay(String tungay) {
		
		this.tungay = tungay;
	}

	
	public String getTungay() {
		
		return this.tungay;
	}

	
	public void setDenngay(String denngay) {
		
		this.denngay = denngay;
	}

	
	public String getDenngay() {
		
		return this.denngay;
	}
	
	public void init() 
	{
		if( this.Id.length() > 0 )
		{
			String sqlgetinfo = "select * from DANGKYKM_TICHLUY_NPP where PK_SEQ = " + this.Id;
			
			ResultSet rs = db.get(sqlgetinfo);
			if(rs != null)
			{
				try
				{
					while(rs.next())
					{
						this.ctkmId = rs.getString("tieuchiTL_fk");
						this.nppIds += rs.getString("npp_fk") + ",";
					}
					rs.close();
					if(this.nppIds.length() > 0)
						this.nppIds = this.nppIds.substring(0, this.nppIds.length() - 1);
					this.nppIdSelecteds = this.nppIds;
					//
					String query = "select '' as tentinhthanh, '' as tendaidien, a.PK_SEQ, a.SMARTID, a.TEN, a.DIACHI, b.MUCDANGKY_FK, \n" +
									" p.ten as nppTen, p.pk_seq as nppPK_SEQ  \n" +
									"from  KHACHHANG a inner join DANGKYKM_TICHLUY_KHACHHANG b on a.pk_seq = b.khachhang_fk  \n" +
									"inner join NHAPHANPHOI p on p.pk_seq = a.npp_fk " +
									"where DKKMTICHLUY_NPP_FK = '" + this.Id + "' ";
					System.out.println(query);
					this.khRs = db.getScrol(query);
					String khId = "";
					while(this.khRs.next())
					{
						khId += this.khRs.getString("PK_SEQ") + ",";
						int muc = this.khRs.getInt("MUCDANGKY_FK");
						this.kh_mucDK.put(this.khRs.getString("PK_SEQ"), muc);
						//this.kh_stt.put(rsKh.getString("khachhang_fk"), rsKh.getInt("STT"));
					}
					this.khRs.beforeFirst();
					
					if(khId.trim().length() > 0)
					{
						khId = khId.substring(0, khId.length() - 1);
						this.khId = khId;
					}
				}
				catch(Exception er){
					er.printStackTrace();
					System.out.println("Error :"+er.toString());
				}
			}
		}
		this.createRs();
	}
	
	public void setMessage(String Msg) {
		
		this.Msg = Msg;
	}
	
	public String getMessage() {
		
		return this.Msg;
	}

	public void setctkmId(String ctkmId) {
	
		this.ctkmId = ctkmId;
	}
	
	public String getctkmId() {
		
		return this.ctkmId;
	}

	
	
	public boolean save() 
	{
		try
		{
			this.getNppInfo();
			db.getConnection().setAutoCommit(false);
			String sql_checkexit="select pk_seq from DANGKYKM_TICHLUY_NPP where tieuchiTL_fk = "+this.ctkmId +" and trangthai <> '2' AND NPP_FK ='"+ nppId +"' " ;
			//System.out.println(sql_checkexit);
			ResultSet rs_check=db.get(sql_checkexit);
			if(rs_check!=null){
				if(rs_check.next()){
					this.Msg = "Da Dang Ky Khuyen Mai Tich Luy, Vui Long Chon CT Khac.";
					geso.dms.center.util.Utility.rollback_throw_exception(db);
				    return false;
				}
			}
			String dktt_fk = "";
			String rf ="select pk_seq from DANGKYKM_TICHLUY where tieuchiTL_fk = "+this.ctkmId +" and trangthai <> '2'";
			System.out.println(" sd " + rf );
			ResultSet rf_check = db.get(rf);
			if(rf_check!=null){
				if(rf_check.next()){
					dktt_fk = rf_check.getString("pk_seq");
				}
				else
				{
					String insert = "insert into DANGKYKM_TICHLUY (tieuchiTL_fk, trangthai, ngaytao, nguoitao, ngaysua, nguoisua) " +
					"values("+this.ctkmId+", '0', '"+this.getDateTime()+"', 100368, '"+this.getDateTime()+"', 100368 )";
					if(!db.update(insert)){					
						this.Msg="Khong The Thuc Hien Insert ,Vui Long Thu Lai. Loi "+ insert;
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						return false;				    
					}
					String quer = "select  IDENT_CURRENT('DANGKYKM_TICHLUY') as Id";
					ResultSet ef_check = db.get(quer);
					ef_check.next();
					dktt_fk = ef_check.getString("Id");
				}
			}
			this.getNppInfo();
			String insert_= "insert into DANGKYKM_TICHLUY_NPP (tieuchiTL_fk, NPP_FK, trangthai, ngaytao, nguoitao, ngaysua, nguoisua) " +
						"values("+this.ctkmId+", '"+ nppId +"' , '0', '"+this.getDateTime()+"', "+this.userId+", '"+this.getDateTime()+"', "+this.userId+" )";
			if(!db.update(insert_)){					
				this.Msg="Khong The Thuc Hien Insert ,Vui Long Thu Lai. Loi "+ insert_;
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;				    
			}
			
			String query = "insert DANGKYKM_TICHLUY_KHACHHANG(DKKMTICHLUY_FK, KHACHHANG_FK, DKKMTICHLUY_NPP_FK) " +
					"select '"+ dktt_fk +"' , pk_seq, IDENT_CURRENT('DANGKYKM_TICHLUY_NPP') from KhachHang where pk_seq in (" + this.khId + ") " +
					" and pk_seq not in (select khachhang_fk from DANGKYKM_TICHLUY_KHACHHANG where DKKMTICHLUY_FK in (select pk_seq from DANGKYKM_TICHLUY where tieuchiTL_fk = "+this.ctkmId+" and TRANGTHAI != 2))";
			if(!db.update(query))
			{
				this.Msg="Khong The Thuc Hien Update ,Vui Long Thu Lai. Loi "+ query;
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}
			
			query = "";
			Enumeration<String> keyList = kh_mucDK.keys();
			while(keyList.hasMoreElements())
			{
				String key = keyList.nextElement();
				if(kh_mucDK.get(key) != null){
					query += "update DANGKYKM_TICHLUY_KHACHHANG set MUCDANGKY_FK = "+kh_mucDK.get(key)+
							" where DKKMTICHLUY_FK in (select PK_SEQ FROM DANGKYKM_TICHLUY WHERE TIEUCHITL_FK = "+this.ctkmId+") and KHACHHANG_FK = " + key + " \n";
				}
			}
			if(query.length() > 0)
				if(!db.update(query))
				{
					this.Msg="Khong The Thuc Hien Update ,Vui Long Thu Lai. Loi "+ query;
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					//db.getConnection().rollback();
					
					return false;
				}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch(Exception er)
		{
			er.printStackTrace();
			this.Msg="Khong The Thuc Hien Insert ,Vui Long Thu Lai. Loi "+ er.toString();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			//db.getConnection().rollback();
			return false;
		}
		return true;
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public void DBclose() 
	{	
		try {
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if(db!=null)
		db.shutDown();
	}
	public void setId(String Id) {
		
		this.Id = Id;
	}
	
	public String getId() {
		
		return this.Id;
	}

	public boolean edit() 
	{
		try{
			db.getConnection().setAutoCommit(false);
			/*if(this.nppIds.length() == 0)
			{
				this.Msg = "Vui l??ng ch???n chi nh??nh/ nh?? ph??n ph???i";
				geso.dms.center.util.Utility.rollback_throw_exception(db);
			    return false;
			}*/
			if(this.khId.length() == 0)
			{
				this.Msg = "Vui l??ng ch???n kh??ch h??ng";
				db.getConnection().rollback();
			    return false;
			}
			
			String query = "delete DANGKYKM_TICHLUY_KHACHHANG where DKKMTICHLUY_NPP_FK ='" + this.Id + "'";
			if(!db.update(query))
			{
			    this.Msg="Khong The Thuc Hien Update ,Vui Long Thu Lai. Loi "+ query;
				geso.dms.center.util.Utility.rollback_throw_exception(db);
			    return false;
			}
			String dktt_fk = "";
			String rf ="select pk_seq from DANGKYKM_TICHLUY where tieuchiTL_fk = "+this.ctkmId +" and trangthai <> '2'";
			System.out.println(" sd " + rf );
			ResultSet rf_check = db.get(rf);
			if(rf_check!=null){
				if(rf_check.next()){
					dktt_fk = rf_check.getString("pk_seq");
				}
				else
				{
					String insert = "insert into DANGKYKM_TICHLUY (tieuchiTL_fk, trangthai, ngaytao, nguoitao, ngaysua, nguoisua) " +
					"values("+this.ctkmId+", '0', '"+this.getDateTime()+"', 100368, '"+this.getDateTime()+"', 100368 )";
					if(!db.update(insert)){					
						this.Msg="Khong The Thuc Hien Insert ,Vui Long Thu Lai. Loi "+ insert;
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						return false;				    
					}
					String quer = "select  IDENT_CURRENT('DANGKYKM_TICHLUY') as Id";
					ResultSet ef_check = db.get(quer);
					ef_check.next();
					dktt_fk = ef_check.getString("Id");
				}
			}
			String query1 = "insert DANGKYKM_TICHLUY_KHACHHANG(DKKMTICHLUY_FK, KHACHHANG_FK, DKKMTICHLUY_NPP_FK) " +
			"select '"+ dktt_fk +"' , pk_seq, IDENT_CURRENT('DANGKYKM_TICHLUY_NPP') from KhachHang where pk_seq in (" + this.khId + ") " +
			" and pk_seq not in (select khachhang_fk from DANGKYKM_TICHLUY_KHACHHANG where DKKMTICHLUY_FK in (select pk_seq from DANGKYKM_TICHLUY where tieuchiTL_fk = "+this.ctkmId+" and TRANGTHAI != 2))";
			if(!db.update(query1))
			{
				this.Msg="Khong The Thuc Hien Update ,Vui Long Thu Lai. Loi "+ query1;
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}
	
			
			
			
			query = "";
			Enumeration<String> keyList = kh_mucDK.keys();
			while(keyList.hasMoreElements())
			{
				
				String key = keyList.nextElement();
				if(kh_mucDK.get(key) != null){
					query += "update DANGKYKM_TICHLUY_KHACHHANG set MUCDANGKY_FK = "+kh_mucDK.get(key)+" " +
					"where DKKMTICHLUY_FK = "+this.Id+" and KHACHHANG_FK = " + key + " \n";
					System.out.println("oala "+query);
				}
			}
			if(query.length() > 0)
				if(!db.update(query))
				{
				    this.Msg="Khong The Thuc Hien Update ,Vui Long Thu Lai. Loi "+ query;
					geso.dms.center.util.Utility.rollback_throw_exception(db);
				    return false;
				}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch(Exception er)
		{
			this.Msg="Khong The Thuc Hien Insert ,Vui Long Thu Lai. Loi "+ er.toString();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		return true;
	}

	public boolean Chot() 
	{

		try{
			db.getConnection().setAutoCommit(false);
			String insert_="update DANGKYKM_TICHLUY_NPP set trangthai=1, ngaysua = '"+this.getDateTime()+"', nguoisua="+this.userId+
					" where PK_SEQ = "+ this.Id;		
			if(!db.update(insert_)){
				    this.Msg="Khong The Thuc Hien Update ,Vui Long Thu Lai. Loi "+ insert_;
					geso.dms.center.util.Utility.rollback_throw_exception(db);
				    return false;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			this.Msg="Khong The Thuc Hien Insert ,Vui Long Thu Lai. Loi "+ er.toString();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		return true;
	}
	
	public boolean UnChot() 
	{

		try{
			db.getConnection().setAutoCommit(false);
			String insert_="update DANGKYKM_TICHLUY_NPP set trangthai=0, ngaysua = '"+this.getDateTime()+"', nguoisua="+this.userId+
					" where PK_SEQ = "+ this.Id;		
			if(!db.update(insert_)){
				    this.Msg="Khong The Thuc Hien Update ,Vui Long Thu Lai. Loi "+ insert_;
					geso.dms.center.util.Utility.rollback_throw_exception(db);
				    return false;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			this.Msg="Khong The Thuc Hien Insert ,Vui Long Thu Lai. Loi "+ er.toString();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		return true;
	}

	public boolean Delete() {

		try{
			db.getConnection().setAutoCommit(false);
			String insert_="update DANGKYKM_TICHLUY set trangthai=2 ,ngaysua='"+this.getDateTime()+"',nguoisua="+this.userId+
					" where PK_SEQ = "+ this.Id;		
			if(!db.update(insert_)){
				    this.Msg="Khong The Thuc Hien Huy  ,Vui Long Thu Lai. Loi "+ insert_;
					geso.dms.center.util.Utility.rollback_throw_exception(db);
				    return false;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			this.Msg="Khong The Thuc Hien Huy ,Vui Long Thu Lai. Loi "+ er.toString();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		return true;
	}
	
	public void setctkmIds(ResultSet ctkmIds) {
		
		this.ctkmRs = ctkmIds;
	}
	
	public ResultSet getctkmIds() {
		
		return this.ctkmRs;
	}
	
	public String getNvbhIds() {
		
		return this.nvbhIds;
	}
	
	public void setNvbhIds(String nvbdIds) {
		
		this.nvbhIds = nvbdIds;
	}
	
	public String getKhId() {
		
		return this.khId;
	}
	
	public void setKhId(String khId) {
		
		this.khId = khId;
	}
	private void getNppInfo()
	{	
		geso.dms.distributor.util.Utility util = new geso.dms.distributor.util.Utility();
		this.nppId = util.getIdNhapp(this.userId);
	}
	public void createRs() 
	{
		this.getNppInfo();
		String ngay = getDateTime();
		String query = "select pk_seq, scheme  " +
				"from TIEUCHITHUONGTL where '" + ngay + "' >= THANG and '" + ngay + "' <= nam and trangthai = '1' AND PK_SEQ NOT IN (select TIEUCHITL_FK from DANGKYKM_TICHLUY_NPP where TRANGTHAI != 2 and NPP_FK = '"+ nppId +"')";
					   //"and PK_SEQ in ( select thuongtl_fk from TIEUCHITHUONGTL_NPP where npp_fk = '" + this.nppId + "' ) " +
					   //"and pk_seq not in ( select TIEUCHITL_FK from DANGKYKM_TICHLUY where npp_fk = '" + this.nppId + "' and trangthai != 2 ) ";
		
		if(this.Id.trim().length() > 0)
		{
			query += " union " +
					"select pk_seq, scheme from TIEUCHITHUONGTL where pk_seq  IN (select TIEUCHITL_FK from DANGKYKM_TICHLUY_NPP where pk_seq = '" + this.Id + "') ";
		}
		
		System.out.println("__LAY SCHEME: " + query);
		this.ctkmRs = db.get(query);
		
		/*this.vungRs = this.db.get("select * from VUNG where TRANGTHAI = 1");
		query = "select * from Khuvuc where trangthai = 1 ";
		if(vungIds.length() > 0)
			query += " and vung_fk in (" + this.vungIds + ")";
		this.khuvucRs = this.db.get(query);*/
		
		if(this.ctkmId.trim().length() > 0)
		{
			query = "select thang, nam from TIEUCHITHUONGTL where pk_seq = '" + this.ctkmId + "' ";
			
			ResultSet rsTL = db.get(query);
			try 
			{
				if(rsTL.next())
				{
					this.tungay = rsTL.getString("thang");
					this.denngay = rsTL.getString("nam");
				}
				rsTL.close();
				/*query = "select p.pk_seq, p.MaFAST, p.TEN, kv.PK_SEQ as KHUVUC, v.PK_SEQ as VUNG from NHAPHANPHOI p inner join KHUVUC kv on kv.PK_SEQ = p.KHUVUC_FK " +
						"inner join VUNG v on v.PK_SEQ = kv.VUNG_FK " +
						"WHERE p.TRANGTHAI = 1 AND p.PK_SEQ IN " + utl.quyen_npp(userId)+
						" and p.PK_SEQ in (select NPP_FK from TIEUCHITHUONGTL_NPP where thuongtl_fk = '" + this.ctkmId + "')";

				this.nppRs = this.db.get(query);
				query = "select PK_SEQ, SMARTID, TEN, isnull(MANHANVIEN, '') as maNV from DAIDIENKINHDOANH where NPP_FK in " + utl.quyen_npp(this.userId) + " and TRANGTHAI = '1'";
				this.nvbhRS = db.get(query);
				
				query = "select * from (" +
						"select PK_SEQ, SMARTID, TEN, DIACHI, isnull(d.STT, 999999999) AS STT, " +
						" STUFF " +
						" (  " +
						"	(	select ' , ' + p.ten " +
						"		from NHAPHANPHOI p " +
						"		where p.PK_SEQ in (select NPP_FK from KHACHHANG_NPP where KHACHHANG_FK = KHACHHANG.PK_SEQ) " +
						"		ORDER BY ' , '  +p.ten " +
						"		FOR XML PATH('') " +
						"	 ), 1, 2, '' " +
						" ) as nppTen, " +
						" STUFF " +
						" (  " +
						"	(	select ',' + cast(p.PK_SEQ as varchar(20))" +
						"		from NHAPHANPHOI p" +
						"		where p.PK_SEQ in (select NPP_FK from KHACHHANG_NPP where KHACHHANG_FK = KHACHHANG.PK_SEQ)" +
						"		ORDER BY ','  +p.TEN" +
						"		FOR XML PATH('')" +
						"	 ), 1, 2, ''" +
						" ) as nppPK_SEQ " +
						"from KHACHHANG ";
				
				String union = "";
				if(this.khId.length() > 0){
					String[] ids = this.khId.split(",");
					for(int i=0; i< ids.length; i++){
						if(i != ids.length-1)
							union += "select " + this.kh_stt.get(ids[i]) + " as STT, '" + ids[i] + "' as ID UNION ALL \n";
						else
							union += "select " + this.kh_stt.get(ids[i]) + " as STT, '" + ids[i] + "' as ID ";
					}
				}
				if(union.length() > 0){
					query += "left join (" + union + ") as d on PK_SEQ = d.ID ";
				}
				else
					query += "left join (select 0 as ID, 0 as STT) as d on PK_SEQ = d.ID ";
				query += "where TRANGTHAI = '1' and PK_SEQ in (select KHACHHANG_FK from KHACHHANG_NPP where NPP_FK in (select NPP_FK from TIEUCHITHUONGTL_NPP where thuongtl_fk = '" + this.ctkmId + "'))";
				if(this.nvbhIds.trim().length() > 0)
				{
					query += " and pk_seq in ( select KHACHHANG_FK from KHACHHANG_TUYENBH where TBH_FK in ( select PK_SEQ from TUYENBANHANG where DDKD_FK = '" + this.nvbhIds + "' ) ) ";
				}
				
				query += ") as d order by STT, SMARTID asc ";
				System.out.println(query);
				this.khRs = db.get(query);*/
			} 
			catch (Exception e) {e.printStackTrace();}
		}		
	}
	
	public void setCtkmRs(ResultSet ctkmIds) {
		
		this.ctkmRs = ctkmIds;
	}
	
	public ResultSet getCtkmRs() {
		
		return this.ctkmRs;
	}
	
	public void setCtkmId(String ctkmId) {
		
		this.ctkmId = ctkmId;
	}
	
	public ResultSet getNvBanhang() {
		
		return this.nvbhRS;
	}
	
	public void setNvBanhang(ResultSet nvbanhang) {
		
		this.nvbhRS = nvbanhang;
	}
	
	public void setKhList(ResultSet KhList) {
		
		this.khRs = KhList;
	}
	
	public ResultSet getKhList() {
		
		return this.khRs;
	}
	@Override
	public ResultSet getNppRs() {
		// TODO Auto-generated method stub
		return this.nppRs;
	}
	@Override
	public ResultSet getVungRs() {
		// TODO Auto-generated method stub
		return this.vungRs;
	}
	@Override
	public String getVungIds() {
		// TODO Auto-generated method stub
		return this.vungIds;
	}
	@Override
	public void setVungIds(String[] vungIds) {
		String tmp = "";
		if(vungIds != null)
			for(int i=0;i<vungIds.length; i++)
				if(vungIds[i].trim().length() > 0)
					tmp += vungIds[i].trim() + ",";
		if(tmp.length() > 0)
			tmp = tmp.substring(0, tmp.length() - 1);
		this.vungIds = tmp;
	}
	@Override
	public ResultSet getKhuvucRs() {
		// TODO Auto-generated method stub
		return this.khuvucRs;
	}
	@Override
	public String getKhuvucIds() {
		// TODO Auto-generated method stub
		return this.khuvucIds;
	}
	@Override
	public void setKhuvucIds(String[] kvIds) {
		String tmp = "";
		if(kvIds != null)
			for(int i=0;i<kvIds.length; i++)
				if(kvIds[i].trim().length() > 0)
					tmp += kvIds[i].trim() + ",";
		if(tmp.length() > 0)
			tmp = tmp.substring(0, tmp.length() - 1);
		this.khuvucIds = tmp;
	}
	@Override
	public void setNppIdSelecteds(String[] nppIdSelecteds) {
		String tmp = "";
		if(nppIdSelecteds != null)
			for(int i=0;i<nppIdSelecteds.length; i++)
				if(nppIdSelecteds[i].trim().length() > 0)
					tmp += nppIdSelecteds[i].trim() + ",";
		if(tmp.length() > 0)
			tmp = tmp.substring(0, tmp.length() - 1);
		this.nppIdSelecteds = tmp;
	}
	@Override
	public String getNppIdSelecteds() {
		return this.nppIdSelecteds;
	}
	@Override
	
	public void getId_Khachhang(String maKh, Hashtable<String, Integer> kh_muc, Hashtable<String, Integer> kh_st) {
		try{
			this.getNppInfo();
			if(this.ctkmId.length() == 0){
				this.Msg = "Vui l??ng ch???n ch????ng tr??nh t??ch l??y.";
				return;
			}
			String queryInsert = "";
			Enumeration<String> keyList = kh_muc.keys();
			int i = 0;
			while(keyList.hasMoreElements())
			{
				String key = keyList.nextElement();
				queryInsert += "INSERT INTO #KHACHHANG(STT, SMARTID, MUCDK) VALUES("+kh_st.get(key)+", '"+key+"', '"+kh_muc.get(key)+"')\n";
				i++;
			}
			if(queryInsert.length() == 0){
				this.Msg = "Kh??ng c?? kh??ch h??ng n??o ???????c upload";
				return;
			}
			String command = // test
				 " IF OBJECT_ID('tempdb.dbo.#KHACHHANG') IS NOT NULL DROP TABLE #KHACHHANG  " + 
				 " CREATE TABLE #KHACHHANG    " + 
				 " (   " + 
				 "  	STT INT, SMARTID varchar(500), MUCDK INT " +
				 " )  " + queryInsert;
			
			System.out.println(command);
			this.db.update(command);
			
			String query= "select t.SMARTID from KHACHHANG k right join #KHACHHANG t on t.SMARTID = k.SMARTID and k.npp_fk='"+ nppId +"' where k.PK_SEQ IS NULL ";
			ResultSet rs = this.db.get(query);
			String _msg = "";
			while(rs.next()){
				_msg += rs.getString("SMARTID") + "\n";
			}
			if(_msg.length() > 0){
				this.Msg = "Kh??ch h??ng kh??ng t???n t???i trong h??? th???ng: \n" + _msg;
				//return;
			}
			rs.close();
			
			query = "select '' as tentinhthanh,''as tendaidien, k.PK_SEQ, k.SMARTID, k.TEN,k.DIACHI, " +
					" p.TEN AS nppTen, p.PK_SEQ as nppPK_SEQ, t.MUCDK " +
					"from KHACHHANG k inner join NHAPHANPHOI p on k.NPP_FK = p.PK_sEQ " +
					"inner join #KHACHHANG t on t.SMARTID = k.SMARTID" +
					" where k.npp_fk = '"+ nppId +"' " +
					"order by t.STT";
			System.out.println("NPP " + query);
			this.khRs = this.db.getScrol(query);
			int sokh = 0;
			this.kh_mucDK = new Hashtable<String, Integer>();
		
			while(this.khRs.next()){
				String SMARTID = this.khRs.getString("SMARTID");
				this.kh_mucDK.put(this.khRs.getString("PK_SEQ"), this.khRs.getInt("MUCDK"));
				//this.kh_ddkd.put(this.khRs.getString("PK_SEQ"), kh_ddkd.get(mafast));
				//this.kh_stt.put(this.khRs.getString("PK_SEQ"), kh_stt.get(mafast));
				//this.kh_tinhthanh.put(this.khRs.getString("PK_SEQ"), kh_tinhthanh.get(mafast));
				sokh++;
			}
			this.khRs.beforeFirst();
			if(sokh > 0)
				this.Msg = "Upload th??nh c??ng! C?? " + sokh + " kh??ch h??ng ????ng k?? ch????ng tr??nh t??ch l??y n??y.\n" + this.Msg;
			else
				this.Msg = "Kh??ng c?? kh??ch h??ng n??o ???????c ch???n.";
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	@Override
	public Hashtable<String, Integer> getMucDangky() {
		return this.kh_mucDK;
	}
	@Override
	public void setMucDangky(Hashtable<String, Integer> value) {
		this.kh_mucDK = value;
	}
	@Override
	public Hashtable<String, Integer> getSTT() {
		return this.kh_stt;
	}
	@Override
	public void setSTT(Hashtable<String, Integer> value) {
		this.kh_stt = value;
	}
	@Override
	public ResultSet getKhExportRs() {
		return this.khExportRs;
	}
	@Override
	public void setAction(String string) {
		this.action = string;
	}
	@Override
	public String getAction() {
		return this.action;
	}
	
}
