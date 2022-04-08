package geso.dms.center.beans.nhanhang.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.dms.center.beans.nhanhang.INhanhang;
import geso.dms.center.db.sql.*;


public class Nhanhang implements INhanhang
{
	private static final long serialVersionUID = -9217977546733610415L;
	String id;
	String nhanhang;
	String ngaytao;
	String ngaysua;
	String nguoitao;
	String nguoisua;
	String trangthai;
	String dvkdId;
	String diengiai;
	String dvkd;
	ResultSet dvkdlist;
	String msg;
	dbutils db;
	ResultSet nganhhanglist;
	String nganhhangId;
	String ma = "";
	public Nhanhang(String[] param)
	{
		this.id = param[0];
		this.nhanhang = param[1];	
		this.ngaytao = param[2];
		this.ngaysua = param[3];
		this.nguoitao = param[4];
		this.nguoisua = param[5];
		this.trangthai = param[6];	
		this.dvkd = param[7];
		this.dvkdId = param[8];
		if(param[9]==null)
			param[9]="";
		this.diengiai=param[9];
		this.nganhhangId=param[10];
		this.ma = param[11];
		this.msg = "";
		this.db = new dbutils();
		createDvkdList();
		createNganhHangList();
	}
	
	public Nhanhang()
	{
		this.id = "";
		this.nhanhang = "";	
		this.ngaytao = "";
		this.ngaysua = "";
		this.nguoitao = "";
		this.nguoisua = "";
		this.trangthai = "2";	
		this.dvkd = "";
		this.dvkdId = "";
		this.msg = "";
		this.diengiai="";
		this.nganhhangId = "";
		this.db = new dbutils();
		createDvkdList();
		createNganhHangList();
	}

	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getNhanhang()
	{
		return this.nhanhang;
	}

	public void setNhanhang(String nhanhang)
	{
		this.nhanhang = nhanhang;
	}

	public String getNgaytao()
	{
		return this.ngaytao;
		
	}

	public void setNgaytao(String ngaytao)
	{
		this.ngaytao = ngaytao;
	}
	
	public String getNgaysua()
	{
		return this.ngaysua;
		
	}

	public void setNgaysua(String ngaysua)
	{
		this.ngaysua = ngaysua;
	}
	
	public String getNguoitao()
	{
		return this.nguoitao;
	}
	
	public void setNguoitao(String nguoitao)
	{
		this.nguoitao = nguoitao;
	}

	public String getNguoisua()
	{
		return this.nguoisua;
	}

	public void setNguoisua(String nguoisua)
	{
		this.nguoisua = nguoisua;
	}
	
	public String getTrangthai()
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}

	public String getDvkdId()
	{
		return this.dvkdId;
	}

	public void setDvkdId(String dvkdId)
	{
		this.dvkdId = dvkdId;
	}
	public String getNganhHangId()
	{
		return this.nganhhangId;
	}
	
	public void setNganhHangId(String nganhhangId)
	{
		this.nganhhangId =  nganhhangId;
	}
	
	public String getDvkd()
	{
		return this.dvkd;
	}

	public void setDvkd(String dvkd)
	{
		this.dvkd = dvkd;
	}

	public String getMessage()
	{
		return this.msg;
	}
	
	public void setMessage(String msg)
	{
		this.msg = msg;
	}

	public void setDvkdList(ResultSet dvkdlist)
	{
		this.dvkdlist = dvkdlist;
	}

	public ResultSet getDvkdList()
	{ 		
		return this.dvkdlist;
		
	}
	
	public ResultSet getNganhHangList()
	{
		return this.nganhhanglist;
	}
	
	public boolean saveNewNhanhang(){
		try{
			if(this.dvkdId.equals(""))
			{
				//this.dvkdId = "NULL";
				return false;
			}
			if(this.nganhhangId.equals(""))
			{
				//this.nganhhangId = "NULL";
				return false;
			}
		// Insert data set into table "Nhanhang"
			this.db.getConnection().setAutoCommit(false);
						
		 	String query ="insert into nhanhang(ten,trangthai,ngaytao,ngaysua,nguoitao,nguoisua,dvkd_fk,nganhhang_fk) "+ 
		 	" values(?,?,?,?,?,?,?,?)";
		
		 	Object[] data;
			data= new Object[]   { this.nhanhang, this.trangthai , this.ngaytao , this.ngaysua , this.nguoitao , this.nguoisua , this.dvkdId ,this.nganhhangId };
			db.viewQuery(query, data);
		 	
			if( this.db.updateQueryByPreparedStatement(query, data)!=1 ) {
		 		geso.dms.center.util.Utility.rollback_throw_exception(db);
		 		//this.msg = "Lỗi "+query;
		 		this.msg = "1. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý";
		 		return false;			
		 	}
		 	/*
		 	 /*
			 * thuc hien cap nhat lai smartid
			 * 
			 */
			/*String query_getid = "select IDENT_CURRENT('nhanhang') as id";
			ResultSet rs_getpk_seq = this.db.get(query_getid);
			rs_getpk_seq.next();
			this.id = rs_getpk_seq.getString("id");*/
			
			
			this.id = db.getPk_seq();
			System.out.println(" id new: "+ this.id);
			
				String sql_update_smartid="update nhanhang set smartid='"+this.id+"' where pk_seq=" + this.id;
				try{
					if(!db.update(sql_update_smartid)){
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						this.msg="Không thể cập nhật SmartID";
						return false;
					}
				}catch(Exception er){
				
				}
			
		 	
		 	this.db.getConnection().commit();
		 	this.db.getConnection().setAutoCommit(true);
		
		}catch(Exception er){
			this.msg="Không thể lưu, vui lòng liên hệ Admin";
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			
		}
		return true;
	}
	
	public boolean UpdateNhanhang(){ 
		if(this.dvkdId.equals(""))
		{
			//this.dvkdId="NULL";
			return false;
		}
		if(this.nganhhangId.equals(""))
		{
			//this.nganhhangId="NULL";
			return false;
		}
		// Update table "Nhanhang"
	/*	String query = "update nhanhang set ten = N'" + this.nhanhang +"',ngaysua = '" + this.ngaysua + "',smartid = '" + this.ma + "',  nguoisua = '" + this.nguoisua + "', trangthai = '" + this.trangthai + "' , dvkd_fk = " + this.dvkdId + "" + " , nganhhang_fk = " + this.nganhhangId + ""
				+ " where pk_seq = '" + this.id + "'" ;*/
		String query = "update nhanhang set ngaysua = ?,  nguoisua = ?, trangthai = ? , dvkd_fk = ? , nganhhang_fk = ?"
				+ " where pk_seq = ?" ;

		Object[] data;
		data= new Object[]   {  this.ngaysua ,  this.nguoisua , this.trangthai , this.dvkdId ,this.nganhhangId , this.id };
		db.viewQuery(query, data);
	 	
		if( this.db.updateQueryByPreparedStatement(query, data)!=1 ) {
			this.msg = "1. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý";
			//this.msg = "Không thể cập nhật "+query;
			return false; 
		}
		return true;
	}

	private void createDvkdList(){//chi cho load don vi kinh doanh nao co checked=1
		//this.dvkdlist =  db.get("select distinct pk_seq, donvikinhdoanh from donvikinhdoanh order by donvikinhdoanh");
		this.dvkdlist =  db.get("select distinct a.pk_seq, a.donvikinhdoanh from donvikinhdoanh a,nhacungcap_dvkd b where a.pk_seq = b.DVKD_fk and b.checked ='1' and a.trangthai ='1' order by a.donvikinhdoanh");
	}
	
	private void createNganhHangList(){//chi cho load nganh hang active
		String query = " select a.PK_SEQ,a.TEN from NGANHHANG a where TRANGTHAI=1 "
				+ " and a.DVKD_FK in ( "
				+ " select distinct a.pk_seq from donvikinhdoanh a,nhacungcap_dvkd b "
				+ " where a.pk_seq = b.DVKD_fk and b.checked ='1' and a.trangthai ='1' ) ";
		System.out.println("Tao nghanh hang:"+ query);
		this.nganhhanglist = db.getScrol(query);

	}
	
	public boolean AllowtoChangeDvkd(){
		ResultSet rs = db.get("select count(pk_seq) as num from sanpham where nhanhang_fk='" + this.id + "' and dvkd_fk = '" + this.dvkdId + "'");
		try{
			rs.next();
			if (rs.getString("num").equals("0"))
				return true;
			else 
				return false;
		}catch(Exception e){
			return false;
		}		
	
	}
	public void DBClose(){
		try{
			if(this.dvkdlist != null) this.dvkdlist.close();
			this.db.shutDown();
		}catch(Exception e){}
	}


	public void setDiengiai(String diengiai) {
		this.diengiai=diengiai;
		
	}

	public String getDiengiai() 
	{
		return this.diengiai;
	}


	public String getma() {
		
		return this.ma;
	}


	public void setma(String ma) {
		
		this.ma = ma;
	}
		
}


