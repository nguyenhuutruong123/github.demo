package geso.dms.center.beans.hinhthuckinhdoanh.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.dms.center.beans.hinhthuckinhdoanh.IHinhthuckinhdoanh;
import geso.dms.center.db.sql.*;

public class Hinhthuckinhdoanh implements IHinhthuckinhdoanh{

	private static final long serialVersionUID = -9217977546733610415L;
	String id;
	String ma;
	String diengiai;
	String ngaytao;
	String ngaysua;
	String nguoitao;
	String nguoisua;
	String trangthai;
	String msg;
	dbutils db;
	
	public Hinhthuckinhdoanh(String[] param)
	{
		this.id = param[0];
		this.ma = param[1];	
		this.diengiai = param[2];
		this.ngaytao = param[3];
		this.ngaysua = param[4];
		this.nguoitao = param[5];
		this.nguoisua = param[6];
		this.trangthai = param[7];	
		this.msg = "";
		this.db = new dbutils();
	}
	
	public Hinhthuckinhdoanh()
	{
		this.id = "";
		this.ma = "";
		this.diengiai = "";
		this.ngaytao = "";
		this.ngaysua = "";
		this.nguoitao = "";
		this.nguoisua = "";
		this.trangthai = "";	
		this.msg = "";
		this.db = new dbutils();		
	}

	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getMa()
	{
		return this.ma;
	}

	public void setMa(String ma)
	{
		this.ma = ma;
	}

	public String getDiengiai()
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai)
	{
		this.diengiai = diengiai;
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

	public String getMessage()
	{
		return this.msg;
	}

	public void setMessage(String msg)
	{
		this.msg = msg;
	}
	
	public void init(){
		String query =  "select ma, diengiai, trangthai from hinhthuckinhdoanh where pk_seq = '"+ this.id +"'";
        ResultSet rs =  this.db.get(query);
        try{
        	rs.next();
        	this.ma = rs.getString("ma");
        	this.diengiai = rs.getString("diengiai");
        	this.trangthai = rs.getString("trangthai");
        	
        }catch(Exception e){}
        
	}
	public boolean saveNewHtkd(){
		try{
			this.db.getConnection().setAutoCommit(false);
		    // Insert data set into table "Donvidoluong"
		    String query ="insert into hinhthuckinhdoanh(ma,diengiai,trangthai,ngaytao,ngaysua,nguoitao,nguoisua) values(N'" + this.ma + "',N'" + this.diengiai + "','" + this.trangthai + "','" + this.ngaytao + "','" + this.ngaysua + "','" + this.nguoitao + "','" + this.nguoisua + "')";
		    System.out.println(query);
		    if (!this.db.update(query)){
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			this.msg = "Khong the luu vao table 'hinhthuckinhdoanh'";
			return false;			
		    }
		    //Upp date Lai Smart ID
				query = "select IDENT_CURRENT('hinhthuckinhdoanh') as id";
				ResultSet rs = this.db.get(query);
	 			rs.next();
	 			this.id = rs.getString("id");
	 			String sql_update_smartid="update hinhthuckinhdoanh set smartid='"+this.id+"' where pk_seq=" + this.id;
	 			try{
	 			if(!db.update(sql_update_smartid)){
	 				 //geso.dms.center.util.Utility.rollback_throw_exception(db);
				    System.out.println("Khong The Thuc Hien Update SmartId vao bang  Hinh thuc kinh doanh nay, Vui Long Lien He Voi Admin De Sua Loi Nay");
				     //return false;
	 			}
	 			}catch(Exception er){
	 				
	 			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			return false;
		}
		return true;
	}

	
	public boolean UpdateHtkd(){
		// Update table "Donvidoluong"
		try {
			this.db.getConnection().setAutoCommit(false);
			String query = "update hinhthuckinhdoanh set ma =N'" + this.ma + "',  diengiai = N'" + this.diengiai + "', ngaysua = '" + this.ngaysua + "',  nguoisua = '" + this.nguoisua + "', trangthai = '" + this.trangthai + "' where pk_seq = '" + this.id + "'" ;

			if(!this.db.update(query)){
				geso.dms.center.util.Utility.rollback_throw_exception(this.db);
				this.msg = "Khong the cap nhat table 'hinhthuckinhdoanh'";
				return false; 
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} catch (SQLException e) {
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			return false;
		}
		return true;
	}
		
	public void DBClose(){
		this.db.shutDown();
	}

}
