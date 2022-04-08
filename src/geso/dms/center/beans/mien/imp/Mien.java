package geso.dms.center.beans.mien.imp;

import geso.dms.center.beans.mien.IMien;
import geso.dms.center.db.sql.*;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Mien implements IMien
{
	private static final long serialVersionUID = -9217977546733610214L;
	String userId;
	String id;
	String vungmien;
	String diengiai;
	String trangthai;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String msg;
	String ma = "";
	
	dbutils db;
	
	public Mien(String[] param)
	{
		this.id = param[0];
		this.vungmien = param[1];
		this.diengiai = param[2];
		this.trangthai = param[3];
		this.ngaytao = param[4];
		this.nguoitao = param[5];
		this.ngaysua = param[6];
		this.nguoisua = param[7];
		this.ma = param[8];
		this.msg = "";

	}
	
	public Mien(String id)
	{
		this.db = new dbutils();
		this.id = id;
		this.vungmien = "";
		this.diengiai = "";
		this.trangthai = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.msg = "";
		this.ma = "";
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
	
	public String getMien() 
	{
		return this.vungmien;
	}

	public void setMien(String vungmien) 
	{
		this.vungmien = vungmien;
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

	public boolean CreateVm()
	{
		try{
			this.db.getConnection().setAutoCommit(false);
			
		this.ngaytao = getDateTime();
		this.nguoitao = this.userId;
		String command="";
		 command = "insert into Mien(ten, diengiai, trangthai, ngaytao, ngaysua, nguoitao, nguoisua,ma) values(N'" + this.vungmien + "',N'" + this.diengiai + "'," + this.trangthai + ",'" + this.ngaytao + "','" + this.ngaytao + "','" + this.userId + "','" + this.userId + "','" + this.ma + "')";
		
		if (!this.db.update(command)){
			this.msg = command;		
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			return false;
		}
		this.db.getConnection().commit();
		this.db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			this.msg="khong the cap nhat Mien nay,loi tai dong lenh sau :" + er.toString();
			return false;
		}
		return true;
	}

	public boolean UpdateVm() 
	{
		this.ngaysua = getDateTime();
		this.nguoisua = this.userId;
		String command;
		
		command ="update vung set trangthai='" + this.trangthai + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.userId + "', ma = '"+this.ma+"'  where pk_seq = '" + this.id + "'";
		
		if (!this.db.update(command)){
			this.msg ="Không thể cập nhật tiền"; 			
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			return false;
		}

		return true; 
	}

	private void init()
	{	
		
		String query = "select a.pk_seq as id, a.ten, a.diengiai, a.trangthai, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua,a.ma"; 
		query = query + " from vung a, nhanvien b, nhanvien c where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq";
		query = query + " and a.pk_seq='"+ this.id + "'";
        ResultSet rs =  this.db.get(query);
        try{
            rs.next();        	
        	this.id = rs.getString("id");
        	this.vungmien = rs.getString("ten");
        	this.diengiai = rs.getString("diengiai");
        	this.trangthai = rs.getString("trangthai");
        	this.ngaytao = rs.getString("ngaytao");
        	this.nguoitao = rs.getString("nguoitao");
        	this.ngaysua = rs.getString("ngaysua");
        	this.nguoisua = rs.getString("nguoisua");
        	this.ma = rs.getString("ma")==null ? "":rs.getString("ma");
       	}catch(Exception e){}
       	
       
       	
	}
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}	
	
	public void closeDB(){
		if (this.db != null)
			this.db.shutDown();
	}


	
	

	@Override
	public String getMa() {
		// TODO Auto-generated method stub
		return this.ma;
	}

	@Override
	public void setMa(String Ma) {
		// TODO Auto-generated method stub
		this.ma = Ma;
	}
	
	
}

