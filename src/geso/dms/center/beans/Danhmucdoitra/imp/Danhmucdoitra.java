package geso.dms.center.beans.Danhmucdoitra.imp;

import geso.dms.center.beans.Danhmucdoitra.IDanhmucdoitra;
import geso.dms.center.beans.loaicuahang.ILoaicuahang;
import geso.dms.center.db.sql.*;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Danhmucdoitra implements IDanhmucdoitra
{
	private static final long serialVersionUID = -9217977546733610214L;
	String userId;
	String id;
	String loaicuahang;
	String diengiai;
	String trangthai;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String msg;
	
	dbutils db;
	
	public Danhmucdoitra(String[] param)
	{
		this.db = new dbutils();
		this.id = param[0];
		this.loaicuahang = param[1];
		this.diengiai = param[2];
		this.ngaytao = param[3];
		this.nguoitao = param[4];
		this.ngaysua = param[5];
		this.nguoisua = param[6];
		this.msg = "";
	}
	
	public Danhmucdoitra(String id)
	{
		this.db = new dbutils();
		this.id = id;
		this.loaicuahang = "";
		this.diengiai = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.msg = "";
		if(id.length() > 0)
			this.init();
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

	public void setId(String id) 
	{
		this.id = id;
	}
	
	public String getLoaicuahang() 
	{
		return this.loaicuahang;
	}

	public void setLoaicuahang(String loaicuahang) 
	{
		this.loaicuahang = loaicuahang;
	}
	
	public String getDiengiai() 
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getNgaytao()
	{
		return this.ngaytao;
	}

	public void setNgaytao(String ngaytao) 
	{
		this.ngaytao = ngaytao;
	}

	public String getNguoitao() 
	{
		return this.nguoitao;
	}

	public void setNguoitao(String nguoitao) 
	{
		this.nguoitao = nguoitao;
	}

	public String getNgaysua() 
	{
		return this.ngaysua;
	}

	public void setNgaysua(String ngaysua) 
	{
		this.ngaysua = ngaysua;
	}

	public String getNguoisua() 
	{
		return this.nguoisua;
	}

	public void setNguoisua(String nguoisua) 
	{
		this.nguoisua = nguoisua;
	}

	public String getMessage() 
	{
		return this.msg;
	}

	public void setMessage(String msg) 
	{
		this.msg = msg;
	}

	public boolean CreateLch()
	{
		try{
			this.ngaytao = getDateTime();
			this.nguoitao = this.userId;
			this.db.getConnection().setAutoCommit(false);
			String command = "insert into DOITRAHANG ( lydo,loai,nguoitao,ngaytao,nguoisua,ngaysua) "+
			" values(N'" + this.diengiai + "','" + this.trangthai + "','" + this.nguoitao + "','" + this.ngaytao + "','" + this.userId + "','" + this.ngaysua + "')"; 
		
			if (!db.update(command)){
				 command = "insert into DOITRAHANG ( lydo,loai,nguoitao,ngaytao,nguoisua,ngaysua) "+
						" values(N'" + this.diengiai + "','" + this.trangthai + "','" + this.nguoitao + "','" + this.ngaytao + "','" + this.userId + "','" + this.ngaysua + "')"; 
				geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			return true;
		}catch(Exception er){
				
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}
		}
		
	

	public boolean UpdateLch() 
	{
		
		this.ngaysua = getDateTime();
		this.nguoisua = this.userId;
		
		String command ="update doitrahang set loai = N'" + this.trangthai + "', lydo = N'" + this.diengiai + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.userId + "' where pk_seq = '" + this.id + "'";
		
		if (!db.update(command)){
			this.msg = "update doitrahang set loai = '" + this.trangthai + "', lydo = N'" + this.diengiai + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.userId + "' where pk_seq = '" + this.id + "'";			
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}

		return true; 
	}

	private void init()
	{	
		//String query = "select a.pk_seq as id, a.loai, a.diengiai, a.trangthai, a.ngaytao, b.ten as nguoitao, a.ngaysua, b.ten as nguoisua"; 
		//query = query + " from loaicuahang a inner join nhanvien b on a.nguoitao = b.pk_seq and a.nguoisua = b.pk_seq";
		String query = "select a.pk_seq as id, a.loai, a.lydo as diengiai, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua"; 
		query = query + " from doitrahang a, nhanvien b, nhanvien c where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq";
		query = query + " and a.pk_seq='"+ this.id + "'";
		//String query = "select * from loaicuahang where pk_seq ='"+ this.id +"'"; 
		System.out.println("loai cua hang:" + query);
        ResultSet rs =  db.get(query);
        try{
           while(rs.next())
           {
        	this.id = rs.getString("id");
        	this.loaicuahang = rs.getString("loai");
        	this.diengiai = rs.getString("diengiai");
        	System.out.println("loai hang"+ this.loaicuahang +" dien giai " + this.diengiai);
        	//this.trangthai = rs.getString("trangthai");
        //	this.ngaytao = rs.getString("ngaytao");
        //	this.nguoitao = rs.getString("nguoitao");
        	//this.ngaysua = rs.getString("ngaysua");
        	//this.nguoisua = rs.getString("nguoisua");
           }  	
       	}catch(Exception e){}
	}
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}	
		
	public void closeDB(){
		if(this.db != null)
			this.db.shutDown();
	}
}
