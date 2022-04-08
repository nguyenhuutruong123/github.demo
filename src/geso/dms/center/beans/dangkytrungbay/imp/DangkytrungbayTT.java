package geso.dms.center.beans.dangkytrungbay.imp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;


import geso.dms.center.beans.dangkytrungbay.IDangkytrungbayTT;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

public class DangkytrungbayTT implements IDangkytrungbayTT, Serializable
{
	private static final long serialVersionUID = 1L;
	
	String Id;
	String userId;
    String nppIds;
    String tungay;
    String denngay;
    
    ResultSet ctkmRs;
    String cttbId;
    
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

	private Hashtable<String, Integer> suatmua;
	private Hashtable<String, Integer> suatdk;

	private Hashtable<String, Integer> suatduyet;
	
	public DangkytrungbayTT()
	{
		this.userId="";
		this.nppIds="";
		this.tungay="";
		this.denngay="";
		this.cttbId ="";
		this.nvbhIds = "";
		this.khId = "";
		this.Msg ="";
		this.Id = "";
		this.db = new dbutils();
		utl = new Utility();
		this.vungIds = "";
		this.khuvucIds = "";
		this.nppIdSelecteds = "";
		this.suatmua = new Hashtable<String, Integer>();
		this.suatdk = new Hashtable<String, Integer>();
		this.suatduyet = new Hashtable<String, Integer>();
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
			String sqlgetinfo = "select * from DANGKYTRUNGBAY a where PK_SEQ = " + this.Id + "";
			
			
			ResultSet rs = db.get(sqlgetinfo);
			if(rs != null)
			{
				try
				{
					while(rs.next())
					{
						this.cttbId = rs.getString("CTTRUNGBAY_FK");
					}
					rs.close();
				}
				catch(Exception er){
					System.out.println("Error :"+er.toString());
				}
			}
			String sql ="\n select ddkd.DDKDTEN, ddkd.DDKDMA,a.khachhang_fk, k.PK_SEQ, k.SMARTID as MAFAST, k.TEN, isnull(SOXUAT,0) as SOXUAT, isnull(DANGKY,0) as DANGKY, isnull(SUATDUYETDK, 0) as SUATDUYETDK, " +
						"\n p.ten as nppTen " +
						"\n from DKTRUNGBAY_KHACHHANG a " +
						"\n inner join KHACHHANG k on k.PK_SEQ = a.KHACHHANG_FK " +
						"\n inner join NHAPHANPHOI p on p.PK_SEQ = k.NPP_FK" +
						"\n outer apply" +
						"\n (" +
						"\n		select top 1 ddkd.ten DDKDTEN ,ddkd.smartId DDKDMA" +
						"\n 	from daidienkinhdoanh ddkd" +
						"\n		inner join tuyenbanhang tbh on tbh.ddkd_fk = ddkd.pk_seq" +
						"\n 	inner join khachhang_tuyenbh x on x.tbh_fk = tbh.pk_seq " +
						"\n		where x.khachhang_fk  = k.pk_seq and tbh.npp_fk = k.npp_fk " +
						"\n )ddkd " +
						"\n where a.DKTRUNGBAY_FK = '" + this.Id + "' ";
			

			System.out.println(sql);
			this.khRs = db.getScrol(sql);
			String khId = "";
			try {
				while(this.khRs.next())
				{
					khId += this.khRs.getString("khachhang_fk") + ",";
					//this.nppIdSelecteds += this.khRs.getString("NPP_FK") + ",";
					int SOXUAT = this.khRs.getInt("SOXUAT");
					int DANGKY = this.khRs.getInt("DANGKY");
					int DUYET = this.khRs.getInt("SUATDUYETDK");
					suatmua.put(this.khRs.getString("khachhang_fk"), SOXUAT);
					suatdk.put(this.khRs.getString("khachhang_fk"), DANGKY);
					if(this.suatduyet.get(this.khRs.getString("khachhang_fk")) == null)
						this.suatduyet.put(this.khRs.getString("khachhang_fk"), DUYET);
				}
				this.khRs.beforeFirst();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(khId.trim().length() > 0)
			{
				khId = khId.substring(0, khId.length() - 1);
				this.khId = khId;
			}
			if(this.nppIdSelecteds.trim().length() > 0)
			{
				nppIdSelecteds = nppIdSelecteds.substring(0, nppIdSelecteds.length() - 1);
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
	
		this.cttbId = ctkmId;
	}
	
	public String getCttbId() {
		
		return this.cttbId;
	}

	
	
	public boolean save() 
	{
		try
		{
			db.getConnection().setAutoCommit(false);
			String sql_checkexit="select pk_seq from dangkytrungbay where CTTRUNGBAY_FK = "+this.cttbId +" and trangthai = 0 ";
			ResultSet rs_check=db.get(sql_checkexit);
			if(rs_check.next()){
				this.Msg = "Vui lòng chốt đăng ký cũ trước khi tạo mới.";
				geso.dms.center.util.Utility.rollback_throw_exception(db);
			    return false;
			}

			if(this.khId.length() == 0)
			{
				this.Msg = "Vui lòng chọn khách hàng";
				geso.dms.center.util.Utility.rollback_throw_exception(db);
			    return false;
			}

			String maxId =  " isnull( (select max( isnull(stt,0)) from DANGKYTRUNGBAY where trangthai != 2 and CTTRUNGBAY_FK = "+this.cttbId+" ),0)";
			
			
			String insert_=     "\n insert into dangkytrungbay(cttrungbay_fk, trangthai, ngaydangky, nguoitao, ngaysua, nguoisua,stt) " +
								"\n values( "+this.cttbId+", '0', '"+this.getDateTime()+"', "+this.userId+", '"+this.getDateTime()+"', "+this.userId+"" +
								"\n			, 1 + "+maxId+"  	 )";
		//	"\n	select ?,0,?,?,?,?";

			/*data.clear();
			data.add(this.cttbId);
			data.add(this.getDateTime());
			data.add(this.userId);
			data.add(this.getDateTime());
			data.add(this.userId);*/
			
			
			//if( this.db.updateQueryByPreparedStatement(insert_, data)!=1 )
			if( this.db.updateReturnInt(insert_)!=1 )
			{
				this.Msg="Khong The Thuc Hien Insert ,Vui Long Thu Lai.'";
				this.db.getConnection().rollback();
				return false;
			}	
			
	
				
			String query = "insert dktrungbay_khachhang(dktrungbay_fk, khachhang_fk) " +
							"select scope_identity(), pk_seq from KhachHang where pk_seq in (" + this.khId + ") " +
							" and pk_seq not in (select khachhang_fk from dktrungbay_khachhang where dktrungbay_fk in (select pk_seq from dangkytrungbay where cttrungbay_fk = "+this.cttbId+" and TRANGTHAI != 2))";	
			if(!db.update(query))
			{
			    this.Msg="Khong The Thuc Hien Update ,Vui Long Thu Lai. Loi "+ query;
				geso.dms.center.util.Utility.rollback_throw_exception(db);
			    return false;
			}
			
			query = "";
			Enumeration<String> keyList = suatmua.keys();
			while(keyList.hasMoreElements())
			{
				String key = keyList.nextElement();
				if(suatmua.get(key) != null){
					query += " update dktrungbay_khachhang set soxuat = "+this.suatmua.get(key)+", dangky = "+this.suatdk.get(key)+" " +
							" where dktrungbay_fk = scope_identity() and KHACHHANG_FK = " + key + " \n";
				
					
					
				}
			}
			if(query.trim().length() > 0 )
			if( this.db.updateReturnInt(query) < 0 )
			{
				this.Msg="Khong The Thuc Hien Insert ,Vui Long Thu Lai.'";
				this.db.getConnection().rollback();
				return false;
			}

			
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
			
			if(this.khId.length() == 0)
			{
				this.Msg = "Vui lòng chọn khách hàng";
				db.getConnection().rollback();
			    return false;
			}
			
		
			String query= "Update dangkytrungbay set ngaysua = '"+this.getDateTime()+"', Nguoisua = "+this.userId+" where trangthai = 0 and pk_seq = " + this.Id;	
			if(db.updateReturnInt(query)<=0)
			{					
			    this.Msg="Khong The Thuc Hien Update ,Vui Long Thu Lai. Loi "+ query;
				geso.dms.center.util.Utility.rollback_throw_exception(db);
			    return false;				    
			}
			
			/*query = " delete dktrungbay_khachhang where dktrungbay_fk =  "+   this.Id;
			if(!db.update(query))
			{					
			    this.Msg="Khong The Thuc Hien Update ,Vui Long Thu Lai. Loi "+ query;
				geso.dms.center.util.Utility.rollback_throw_exception(db);
			    return false;				    
			}*/
			
			if(this.khId == null || this.khId.trim().length() <=4)
			{
				 this.Msg="Vui lòng chọn khách hàng";
					geso.dms.center.util.Utility.rollback_throw_exception(db);
				    return false;		
			}
			
			
			
			query = " select smartId from KhachHang where pk_seq in (" + this.khId + ") " +
								" and pk_seq  in (select khachhang_fk from dktrungbay_khachhang where dktrungbay_fk in (select pk_seq from dangkytrungbay where pk_seq != "+ this.Id +" and cttrungbay_fk = "+this.cttbId+" and TRANGTHAI != 2))";
			ResultSet rs = db.get(query);
			String data = "";
			while (rs.next())
			{
				data += data.trim().length() > 0 ?  ", " + rs.getString("smartId") :  rs.getString("smartId");
			}
			if(data.trim().length() > 0)
			{
				this.Msg="Các khách hàng sau đã phát sinh đăng ký : "+ data;
				geso.dms.center.util.Utility.rollback_throw_exception(db);
			    return false;
			}
				
			query = "insert dktrungbay_khachhang(dktrungbay_fk, khachhang_fk) " +
							"select "+this.Id+", pk_seq from KhachHang where pk_seq in (" + this.khId + ") " +
							" and pk_seq not in (select khachhang_fk from dktrungbay_khachhang where dktrungbay_fk in (select pk_seq from dangkytrungbay where cttrungbay_fk = "+this.cttbId+" and TRANGTHAI  = 0))";
			System.out.println("query =  " + query);
			if(db.updateReturnInt(query)< 0)
			{
			    this.Msg="Khong The Thuc Hien Update ,Vui Long Thu Lai. Loi ";
				geso.dms.center.util.Utility.rollback_throw_exception(db);
			    return false;
			}
			
			
			
			Enumeration<String> keyList = suatduyet.keys();
			while(keyList.hasMoreElements())
			{
				String key = keyList.nextElement();
				if(suatduyet.get(key) != null){
					query  += " update dktrungbay_khachhang set DANGKY ="+this.suatdk.get(key).toString()+", SUATDUYETDK = " + this.suatduyet.get(key).toString() +
							 " where dktrungbay_fk = '"+this.Id+"' and KHACHHANG_FK = " + key + " \n";
					
				}
			}
			if(query.trim().length() > 0)
				if(db.updateReturnInt(query)< 0)
				{
					this.Msg="Khong The Thuc Hien Insert ,Vui Long Thu Lai.'";
					this.db.getConnection().rollback();
					return false;
				}
			
			query = "  delete  dktrungbay_khachhang where  dktrungbay_fk = '"+this.Id+"'  and KHACHHANG_FK not in  (" + this.khId + ") ";
			if(db.updateReturnInt(query)< 0)
			{
				this.Msg="Khong The Thuc Hien Insert ,Vui Long Thu Lai.'";
				this.db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		}
		catch(Exception er)
		{	er.printStackTrace();
			this.Msg="Khong The Thuc Hien Insert ,Vui Long Thu Lai. Loi "+ er.toString();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		
	}

	public boolean Chot() 
	{

		try{
			db.getConnection().setAutoCommit(false);
			
		
			String insert_= "\n update DANGKYTRUNGBAY set trangthai='1', ngaysua='"+this.getDateTime()+"', nguoisua="+this.userId +
						//	"\n  	,stt = 1+ "+ maxId +
							"\n where trangthai = 0 and PK_SEQ = "+ this.Id;		
			if(db.updateReturnInt(insert_)!=1){
				System.out.println("query =" + insert_);
				    this.Msg="Vui lòng kiểm tra lại trạng thái  trưng bày";
					geso.dms.center.util.Utility.rollback_throw_exception(db);
				    return false;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			er.printStackTrace();
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
			String insert_="update DANGKYTRUNGBAY set trangthai = '0', ngaysua = '"+this.getDateTime()+"', nguoisua="+this.userId +
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
			String insert_="update DANGKYTRUNGBAY set trangthai=2, ngaysua = '"+this.getDateTime()+"', nguoisua = "+this.userId+
					" where pk_seq = "+ this.Id;		
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
	
	public void createRs() 
	{
		String ngay = getDateTime();
		String query = "select pk_seq, scheme  " +
				"from CTTRUNGBAY where '" + ngay + "' >= NgayDangKy and '" + ngay + "' <= NgayKetThucDK AND PK_SEQ NOT IN (select CTTRUNGBAY_FK from DANGKYTRUNGBAY where TRANGTHAI in (0) )";
					   //"and PK_SEQ in ( select thuongtl_fk from TIEUCHITHUONGTL_NPP where npp_fk = '" + this.nppId + "' ) " +
					   //"and pk_seq not in ( select TIEUCHITL_FK from DANGKYKM_TICHLUY where npp_fk = '" + this.nppId + "' and trangthai != 2 ) ";
		
		if(this.Id.trim().length() > 0)
		{
			query += " union " +
					"select pk_seq, scheme from CTTRUNGBAY where pk_seq = ( select distinct CTTRUNGBAY_FK from DANGKYTRUNGBAY where PK_SEQ = '" + this.Id + "' ) ";
			
		}
		
		//System.out.println("__LAY SCHEME: " + query);
		this.ctkmRs = db.get(query);
		
		/*this.vungRs = this.db.get("select * from VUNG where TRANGTHAI = 1");
		query = "select * from Khuvuc where trangthai = 1 ";
		if(vungIds.length() > 0)
			query += " and vung_fk in (" + this.vungIds + ")";
		this.khuvucRs = this.db.get(query);*/
		
		if(this.cttbId.trim().length() > 0)
		{
			query = "select NGAYTRUNGBAY, NGAYKETTHUCTB from CTTRUNGBAY where pk_seq = '" + this.cttbId + "' ";
			
			ResultSet rsTL = db.get(query);
			try 
			{
				if(rsTL.next())
				{
					this.tungay = rsTL.getString("NGAYTRUNGBAY");
					this.denngay = rsTL.getString("NGAYKETTHUCTB");
				}
				rsTL.close();
				
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
	
	public void setCttbId(String cttbId) {
		
		this.cttbId = cttbId;
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
		return this.nppRs;
	}
	@Override
	public ResultSet getVungRs() {
		return this.vungRs;
	}
	@Override
	public String getVungIds() {
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
		return this.khuvucRs;
	}
	@Override
	public String getKhuvucIds() {
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
	public void getId_Khachhang(String maKh, Hashtable<String, Integer> sosuatdk, Hashtable<String, Integer> sosuatduyet) {
		if(this.cttbId.length() == 0){
			this.Msg = "Vui lòng chọn chương trình trưng bày.";
			return;
		}
		try{
			String khIds = "";
			String query = "" +
					"IF OBJECT_ID('tempdb.dbo.#DATA') IS NOT NULL DROP TABLE #DATA " +
					"CREATE TABLE #DATA " +
					"( " +
					"  	SMARTID varchar(50), SUATDK INT, SUATDUYETDK INT " +
					") ";
			String[] arrkhId = maKh.split(",");
			
			for(int i=0; i<arrkhId.length; i++)
				query += "\n INSERT #DATA(SMARTID, SUATDK, SUATDUYETDK) VALUES('"+arrkhId[i]+"', "+sosuatdk.get(arrkhId[i])+", "+sosuatduyet.get(arrkhId[i])+")";
			//System.out.println(query);
			this.db.update(query);
			String sqlcheck = "select d.SMARTID from KHACHHANG k right join #DATA d on d.SMARTID = k.SMARTID where k.SMARTID is null";
			ResultSet rscheck = this.db.get(sqlcheck);
			String khcheck = "";
			while(rscheck.next())
				khcheck += rscheck.getString("SMARTID") + ", ";
			
			String _id = "0";
			if(this.Id.trim().length() > 0 )
				_id = this.Id;
			
			query = "select ddkd.DDKDTEN, ddkd.DDKDMA ,k.PK_SEQ, d.SMARTID, d.SUATDK, d.SUATDUYETDK, k.SMARTID as MAFAST, k.TEN, p.ten as nppTen " +
					"from KHACHHANG k inner join #DATA d on d.SMARTID = k.SMARTID "+
					"inner join NHAPHANPHOI p on p.PK_SEQ = k.NPP_FK " +
					"left join DKTRUNGBAY_KHACHHANG dk on dk.KHACHHANG_FK = k.PK_SEQ and dk.DKTRUNGBAY_FK = "+_id +
					"\n outer apply" +
					"\n (" +
					"\n		select top 1 ddkd.ten DDKDTEN ,ddkd.smartId DDKDMA" +
					"\n 	from daidienkinhdoanh ddkd" +
					"\n		inner join tuyenbanhang tbh on tbh.ddkd_fk = ddkd.pk_seq" +
					"\n 	inner join khachhang_tuyenbh x on x.tbh_fk = tbh.pk_seq " +
					"\n		where x.khachhang_fk  = k.pk_seq and tbh.npp_fk = k.npp_fk " +
					"\n )ddkd" +
					"\n where 1=1  ";
			System.out.println("NPP " + query);
			this.khRs = db.getScrol(query);
			int sokh = 0;
			this.suatdk = new Hashtable<String, Integer>();
		
			while(this.khRs.next()){
				khIds += this.khRs.getString("PK_SEQ") + ",";
				this.suatdk.put(this.khRs.getString("PK_SEQ"), this.khRs.getInt("SUATDK"));
				this.suatduyet.put(this.khRs.getString("PK_SEQ"), this.khRs.getInt("SUATDUYETDK"));
				sokh++;
			}
			this.khRs.beforeFirst();
			
			if(khIds.length() > 0)
				khIds = khIds.substring(0, khIds.length() - 1);
			if(sokh > 0)
				this.Msg = "Có " + sokh + " khách hàng upload thành công.";
			else
				this.Msg = "Không có khách hàng nào được chọn.";
			if(khcheck.length() > 0)
				this.Msg += "\nKhách hàng không có trong hệ thống: " + khcheck;
			
			this.khId = khIds;
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	@Override
	public Hashtable<String, Integer> getSoSuatmua() {
		return this.suatmua;
	}
	@Override
	public void setSoSuatmua(Hashtable<String, Integer> value) {
		this.suatmua = value;
	}

	@Override
	public Hashtable<String, Integer> getSoSuatDk() {
		return this.suatdk;
	}
	@Override
	public void setSoSuatDk(Hashtable<String, Integer> value) {
		this.suatdk = value;
	}
	
	@Override
	public Hashtable<String, Integer> getSoSuatDuyet() {
		return this.suatduyet;
	}
	@Override
	public void setSoSuatDuyet(Hashtable<String, Integer> value) {
		this.suatduyet = value;
	}
	
}
