package geso.dms.center.beans.dangkykhuyenmaitichluy.imp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import com.lowagie.tools.concat_pdf;

import geso.dms.center.beans.dangkykhuyenmaitichluy.IDangkykhuyenmaitichluyTT;
import geso.dms.center.beans.tieuchithuong.ITieuchithuongTL;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

public class DangkykhuyenmaitichluyTT implements IDangkykhuyenmaitichluyTT, Serializable
{
	
	
	private static final long serialVersionUID = 1L;
	
	String ngaydangky= "";
	String diengiai = "";
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
	ResultSet khDangkyRs;
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
	
	public DangkykhuyenmaitichluyTT()
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
	
	public ResultSet getKhDangkyRs() {
		return khDangkyRs;
	}
	public void setKhDangkyRs() {
		
		if(this.Id.trim().length() > 0)
		{
			String query =  this.setQueryKhachHangDangKy();
			System.out.println("query = "+ query);
			this.khDangkyRs = db.get(query);
		}
	}
	
	public void init() 
	{
		if( this.Id.length() > 0 )
		{
			String sqlgetinfo = "select * from DANGKYKM_TICHLUY where PK_SEQ = " + this.Id;
			
			ResultSet rs = db.get(sqlgetinfo);
			if(rs != null)
			{
				try
				{
					
					while(rs.next())
					{
						this.diengiai = rs.getString("DienGiai");
						this.ngaydangky  = rs.getString("NgayDangKy");
						
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
									"inner join DANGKYKM_TICHLUY c on c.PK_SEQ = b.DKKMTICHLUY_FK \n" +
									"inner join NHAPHANPHOI p on p.pk_seq = a.npp_fk " +
									"where dkkmtichluy_fk = '" + this.Id + "' --and " +
									"--(DKKMTICHLUY_NPP_FK is null or DKKMTICHLUY_NPP_FK in (select pk_seq from DANGKYKM_TICHLUY_NPP z where z.TIEUCHITL_FK = c.tieuchitl_fk and z.TRANGTHAI ='1'))";
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

	
	public boolean Upload(String values, List<Object> dataUpload)
	{
		String query = "";
		if(!this.edit())
			return false;
		try
		{
			Object[] data;
			
			
			db.getConnection().setAutoCommit(false);
			
			
			query = " select isnull(max(isnull(muc,0))  + 1,-1) muc from tieuchithuongtl_tieuchi where thuongtl_fk =" + this.Id;
			ResultSet rs=  db.get(query);
			rs.next();
	
			
			int muc = rs.getInt("muc");
			
			
			
			String error = "";
			query  = " select smartId from ("+values+") data where  smartId not in (select smartId from  khachhang where smartId is not null) ";
			System.out.println(query);

			rs = db.get(query);
			while(rs.next())
			{
				if(error.trim().length() > 0)
					error += ", "  + rs.getString("smartId");
				else
					error += rs.getString("smartId");
				
			}
			if(error.trim().length() > 0)
			{
				this.Msg = " Các id KH sau không tồn tại : "+ error;
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return false;
			}
			
		//	db.viewQuery(query, this.dataUpload);
			
			query  = " select smartId from ("+values+") data" +
					 " group by smartId " +
					 " having count(*) > 1 ";
			rs = db.get(query);
			while(rs.next())
			{
				if(error.trim().length() > 0)
					error += ", "  + rs.getString("smartId");
				else
					error += rs.getString("smartId");
				
			}
			if(error.trim().length() > 0)
			{
				this.Msg = " Các id KH sau  bị lặp dòng : "+ error;
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return false;
			}
			
			query  = " select smartId, muc from ("+values+") data where data.muc < 0 and data.muc >" +muc;
			rs = db.get(query);
			while(rs.next())
			{
				if(error.trim().length() > 0)
					error += ", "  + rs.getString("smartId") + "  , mức "+( rs.getInt("muc") + 1  )+"  ";
				else
					error += rs.getString("smartId") + "  , mức "+( rs.getInt("muc") + 1  )+"  ";
				
			}
			if(error.trim().length() > 0)
			{
				this.Msg = " Các id KH đăng ký mức không hợp lệ : "+ error;
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return false;
			}
			
			
			query = " delete DANGKYKM_TICHLUY_khachhang where DKKMTICHLUY_FK =  "+ this.Id;
			if(!db.update(query))
			{
				this.Msg = " Loi Upload (2) ";
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return false;
			}
			query  = " insert DANGKYKM_TICHLUY_khachhang(nguoitao,DKKMTICHLUY_FK,khachhang_fk,soxuat,dangky,dahuy,mucdangky_fk,thuongtl_fk) " +
					 " select "+this.userId+",dk.pk_seq, kh.pk_seq , 1,1,0, a.muc,dk.thuongtl_fk  from ("+values+") a " +
					 "  inner join DANGKYKM_TICHLUY  dk on dk.pk_seq =    "+ this.Id + "" +
					 " inner join khachhang kh on kh.smartId =  a.smartId ";
			if( db.updateReturnInt(query) < 0 )
			{
				this.Msg = " Loi Upload (3) ";
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return false;
			}
			query =  "\n select npp.ten npp, a.muc + 1 ,  a.soluong ngansach,  isnull(dadung.tongsuat,0) dadung " + 
					 "\n from tieuchithuongtl_npp a " + 
					 "\n inner join NHAPHANPHOI npp on a.npp_fk = npp.pk_seq " + 
					 "\n inner join DANGKYKM_TICHLUY dk on dk.thuongtl_fk =  a.thuongtl_fk " + 
					 "\n outer apply " + 
					 "\n ( " + 
					 "\n 	select sum( soxuat) tongsuat " + 
					 "\n 	from DANGKYKM_TICHLUY_khachhang dkkh " + 
					 "\n 	inner join khachhang kh on kh.pk_seq = dkkh.KHACHHANG_FK and kh.npp_fk = a.npp_fk " + 
					 "\n 	where  dkkh.thuongtl_fk  = a.thuongtl_fk and dkkh.mucdangky_fk = a.muc " + 
					 "\n ) dadung " + 
					 "\n where dk.PK_SEQ = "+this.Id+" and a.soluong < isnull(dadung.tongsuat,0) " ;
			rs = db.get(query);
			while(rs.next())
			{
				if(error.trim().length() > 0)
					error += ", "  + rs.getString("npp") + "  , mức "+( rs.getInt("muc") + 1  )+" , ngân sách "+ rs.getString("ngansach") +" , đã dùng "+rs.getString("dadung")+"  ";
				else
					error += rs.getString("npp") + "  , mức "+( rs.getInt("muc") + 1  )+" , ngân sách "+ rs.getString("ngansach") +" , đã dùng "+rs.getString("dadung")+"  ";
				
			}
			if(error.trim().length() > 0)
			{
				this.Msg = " Các NPP sau bị vượt ngân sách : "+ error;
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return false;
			}
			
			this.Msg ="Upload thành công";
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		}
		catch(Exception er)
		{
			er.printStackTrace();
			this.Msg="Khong The Thuc Hien Insert ,Vui Long Thu Lai. Loi "+ er.toString();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		
	}
	
	public boolean save() 
	{
		try
		{
			Object[] data;
			
			
			db.getConnection().setAutoCommit(false);
			String sql_checkexit="select pk_seq,diengiai from DANGKYKM_TICHLUY where tieuchiTL_fk = "+this.ctkmId +" and trangthai = 0  ";
			//System.out.println(sql_checkexit);
			ResultSet rs_check=db.get(sql_checkexit);
			if(rs_check!=null)
			{
				if(rs_check.next()){
					this.Msg = "Vui lòng chốt đăng ký "+rs_check.getString("diengiai")+" trước khi tạo mới đăng ký mới !";
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
				    return false;
				}
			}
			String query= 	"insert into DANGKYKM_TICHLUY (ngaydangky,diengiai,thuongtl_fk,tieuchiTL_fk, trangthai, ngaytao, nguoitao, ngaysua, nguoisua) " +
							"select ?,?,?,?, '0',?, ?,?,? where not exists ( select 1 from DANGKYKM_TICHLUY where tieuchiTL_fk = ? and trangthai = 0  ) ";
			data= new Object[]{this.ngaydangky,this.diengiai,this.ctkmId ,this.ctkmId ,this.getDateTime(),this.userId,this.getDateTime(),this.userId, this.ctkmId};
			
			if(db.update_v2(query, data)!= 1){					
				this.Msg="Lỗi đăng ký (1) ";
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return false;				    
			}
					
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		}
		catch(Exception er)
		{
			this.Msg="Khong The Thuc Hien Insert ,Vui Long Thu Lai. Loi "+ er.toString();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		
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
		try
		{
			Object[] data;
			
			
			db.getConnection().setAutoCommit(false);
			String sql_checkexit="select pk_seq,diengiai from DANGKYKM_TICHLUY where tieuchiTL_fk = "+this.ctkmId +" and trangthai = 0 and pk_seq != "+this.Id+"  ";
			ResultSet rs_check=db.get(sql_checkexit);
			if(rs_check!=null)
			{
				if(rs_check.next()){
					this.Msg = "Vui lòng chốt đăng ký "+rs_check.getString("diengiai")+" trước khi tạo mới đăng ký mới !";
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
				    return false;
				}
			}
			String query= " update DANGKYKM_TICHLUY set ngaydangky = ?, diengiai= ?, ngaysua = ?, nguoisua = ?  " +
						  " where trangthai = 0 and pk_seq = " + this.Id;	
					data= new Object[]{this.ngaydangky,this.diengiai,this.getDateTime(),this.userId};
			
			if(db.update_v2(query, data)!= 1){					
				this.Msg="Lỗi đăng ký (1) ";
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return false;				    
			}
					
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		}
		catch(Exception er)
		{
			this.Msg="Khong The Thuc Hien Insert ,Vui Long Thu Lai. Loi "+ er.toString();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		
		
	
	}

	public boolean Chot() 
	{

		try{
			db.getConnection().setAutoCommit(false);
			
			
			
			String insert_="update DANGKYKM_TICHLUY set trangthai=1, ngaysua = '"+this.getDateTime()+"', nguoisua="+this.userId+
					" where  trangthai = 0 and PK_SEQ = "+ this.Id;		
			if(db.updateReturnInt(insert_)!=1){
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
			
			
			
			
			String query = "\n select duyet.DienGiai  " +
			"\n from DUYETTRAKHUYENMAI_Dangky a  " +
			"\n inner join DUYETTRAKHUYENMAI duyet on duyet.PK_SEQ = a.duyetkm_fk and duyet.TRANGTHAI in (1) " +
			"\n where a.dangky_fk = "+ this.Id;	
		ResultSet rs = db.get(query);
		if(rs.next())
		{
			String dg = rs.getString("DienGiai");
			Utility.rollback_throw_exception(db);
			this.Msg= "Chương trình ["+dg+"]  đã chốt duyệt trả nên không thể mở chốt.";
			return false;
		}
		
		query="update DANGKYKM_TICHLUY set trangthai=0 ,ngaysua='"+this.getDateTime()+"',nguoisua="+this.userId+
				" where trangthai = 1 and PK_SEQ = "+ this.Id;		
		if(db.updateReturnInt(query)<=0){
			    this.Msg="Khong The Thuc Hien Huy  ,Vui Long Thu Lai. Loi "+ query;
				geso.dms.center.util.Utility.rollback_throw_exception(db);
			    return false;
		}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			this.Msg = "Mở chốt thành công !";
			return true;
		}catch(Exception er){
			this.Msg="Khong The Thuc Hien Insert ,Vui Long Thu Lai. Loi "+ er.toString();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		
	}

	public boolean Delete() {

		try{
			db.getConnection().setAutoCommit(false);
			
			
			String query = "\n select duyet.DienGiai  " +
				"\n from DUYETTRAKHUYENMAI_Dangky a  " +
				"\n inner join DUYETTRAKHUYENMAI duyet on duyet.PK_SEQ = a.duyetkm_fk and duyet.TRANGTHAI in (0,1) " +
				"\n where a.dangky_fk = "+ this.Id;	
			ResultSet rs = db.get(query);
			if(rs.next())
			{
				String dg = rs.getString("DienGiai");
				Utility.rollback_throw_exception(db);
				this.Msg= "Đăng ký  đã phát sinh duyệt trả  ["+dg+"]  ";
				return false;
			}
			
			query="update DANGKYKM_TICHLUY set trangthai=2 ,ngaysua='"+this.getDateTime()+"',nguoisua="+this.userId+
					" where trangthai = 0 and PK_SEQ = "+ this.Id;		
			if(!db.update(query)){
				    this.Msg="Khong The Thuc Hien Huy  ,Vui Long Thu Lai. Loi "+ query;
					geso.dms.center.util.Utility.rollback_throw_exception(db);
				    return false;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			this.Msg = "Xóa thành công";
			return true;
		}catch(Exception er){
			this.Msg="Khong The Thuc Hien Huy ,Vui Long Thu Lai. Loi "+ er.toString();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		
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
	
	public void createRs() 
	{
		setKhDangkyRs();
		String ngay = getDateTime();
		String query = "select pk_seq, scheme  " +
				"from TIEUCHITHUONGTL where '" + ngay + "' >= THANG and '" + ngay + "' <= nam and trangthai = '1' AND PK_SEQ NOT IN (select TIEUCHITL_FK from DANGKYKM_TICHLUY where TRANGTHAI != 2 )";
					   //"and PK_SEQ in ( select thuongtl_fk from TIEUCHITHUONGTL_NPP where npp_fk = '" + this.nppId + "' ) " +
					   //"and pk_seq not in ( select TIEUCHITL_FK from DANGKYKM_TICHLUY where npp_fk = '" + this.nppId + "' and trangthai != 2 ) ";
		
		if(this.Id.trim().length() > 0)
		{
			query += " union " +
					"select pk_seq, scheme from TIEUCHITHUONGTL where pk_seq  IN (select TIEUCHITL_FK from DANGKYKM_TICHLUY where pk_seq = '" + this.Id + "') ";
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
			if(this.ctkmId.length() == 0){
				this.Msg = "Vui lòng chọn chương trình tích lũy.";
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
				this.Msg = "Không có khách hàng nào được upload";
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
			
			String query= "select t.SMARTID from KHACHHANG k right join #KHACHHANG t on t.SMARTID = k.SMARTID where k.PK_SEQ IS NULL";
			ResultSet rs = this.db.get(query);
			String _msg = "";
			while(rs.next()){
				_msg += rs.getString("SMARTID") + "\n";
			}
			if(_msg.length() > 0){
				this.Msg = "Khách hàng không tồn tại trong hệ thống: \n" + _msg;
				//return;
			}
			rs.close();
			
			query = "select '' as tentinhthanh,''as tendaidien, k.PK_SEQ, k.SMARTID, k.TEN,k.DIACHI, " +
					" p.TEN AS nppTen, p.PK_SEQ as nppPK_SEQ, t.MUCDK " +
					"from KHACHHANG k LEFT join NHAPHANPHOI p on k.NPP_FK = p.PK_sEQ " +
					"inner join #KHACHHANG t on t.SMARTID = k.SMARTID " +
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
			db.update("IF OBJECT_ID('tempdb.dbo.#KHACHHANG') IS NOT NULL DROP TABLE #KHACHHANG");
			if(sokh > 0)
				this.Msg = "Upload thành công! Có " + sokh + " khách hàng đăng ký chương trình tích lũy này.\n" + this.Msg;
			else
				this.Msg = "Không có khách hàng nào được chọn.";
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
	
	public String getNgaydangky() {
		if( this.ngaydangky == null || this.ngaydangky.trim().length() <=0)
			this.ngaydangky = getDateTime();
		return ngaydangky;
	}
	public void setNgaydangky(String ngaydangky) {
		this.ngaydangky = ngaydangky;
	}
	public String getDiengiai() {
		return diengiai;
	}
	public dbutils getDb() {
		return db;
	}
	public void setDiengiai(String diengiai) {
		this.diengiai = diengiai;
	}
	
	
	public String setQueryKhachHangDangKy ()
	{
		
		String query =	 	 "\n select kh.smartId [MÃ KH(Dữ liệu upload)], npp.ten [NPP] , kh.ten [TÊN] " + 
							 "\n 	, isnull( 'NVBH:' + ddkd.ten,'NV:'+ nv.TEN) [Người tạo], dk.MUCDANGKY_FK +1 [Mức(Dữ liệu upload)]--, isnull(dk.isPDA,0) [Từ PDA] " + 
							 "\n from DANGKYKM_TICHLUY_khachhang  dk " + 
							 "\n inner join khachhang kh on dk.khachhang_fk = kh.pk_seq " + 
							 "\n inner join nhaphanphoi npp on npp.pk_seq = kh.npp_fk " + 
							 "\n left join DaiDienkinhDoanh ddkd on ddkd.pk_seq = dk.ddkd_fk " + 
							 "\n left join nhanvien nv on nv.pk_seq = dk.NguoiTao " + 
							 "\n where dk.DKKMTICHLUY_FK = " + this.getId() ;
		
		System.out.println("query BC="+ query);
		return query;
	}
}
