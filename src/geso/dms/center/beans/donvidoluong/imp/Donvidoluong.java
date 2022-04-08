package geso.dms.center.beans.donvidoluong.imp;

import java.sql.ResultSet;

import geso.dms.center.beans.donvidoluong.IDonvidoluong;
import geso.dms.center.db.sql.*;


public class Donvidoluong implements IDonvidoluong
{
	private static final long serialVersionUID = -9217977546733610415L;
	String id;
	String donvi;
	String diengiai;
	String ngaytao;
	String ngaysua;
	String nguoitao;
	String nguoisua;
	String trangthai;
	String msg;
	dbutils db;
	
	public Donvidoluong(String[] param)
	{
		this.id = param[0];
		this.donvi = param[1];	
		this.diengiai = param[2];
		this.ngaytao = param[3];
		this.ngaysua = param[4];
		this.nguoitao = param[5];
		this.nguoisua = param[6];
		this.trangthai = param[7];	
		this.msg = "";
		this.db = new dbutils();
	}
	
	public Donvidoluong()
	{
		this.id = "";
		this.donvi = "";
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

	public String getDonvi()
	{
		return this.donvi;
	}

	public void setDonvi(String donvi)
	{
		this.donvi = donvi;
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
		String query =  "select donvi, diengiai, trangthai from donvidoluong where pk_seq = '"+ this.id +"'";
        ResultSet rs =  this.db.get(query);
        try{
        	rs.next();
        	this.donvi = rs.getString("donvi");
        	this.diengiai = rs.getString("diengiai");
        	this.trangthai = rs.getString("trangthai");
        	
        }catch(Exception e){}
        
	}
	public boolean saveNewDvdl(){
		try{
			this.db.getConnection().setAutoCommit(false);
		    // Insert data set into table "Donvidoluong"
		    String query ="insert into donvidoluong(donvi,diengiai,trangthai,ngaytao,ngaysua,nguoitao,nguoisua) values(?,?,?,?,?,?,?)";
		   

		    Object[] data;
			data= new Object[]   {  this.donvi , this.diengiai , this.trangthai, this.ngaytao , this.ngaysua, this.nguoitao , this.nguoisua };
			db.viewQuery(query, data);
		 	
			if( this.db.updateQueryByPreparedStatement(query, data)!=1 ) {
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			//this.msg = "Khong the luu vao table 'donvidoluong'";
			this.msg = "1. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý";
			return false;			
		    }
		    
		    
		    
		    //Upp date Lai Smart ID
				/*query = "select IDENT_CURRENT('donvidoluong') as id";
				ResultSet rs = this.db.get(query);
	 			rs.next();
	 			this.id = rs.getString("id");*/
			
			this.id = db.getPk_seq();
			System.out.println(" id new: "+ this.id);
	 			
	 			
 			String sql_update_smartid="update donvidoluong set smartid='"+this.id+"' where pk_seq=" + this.id;
 			try{
 			if(!db.update(sql_update_smartid)){
 				 //geso.dms.center.util.Utility.rollback_throw_exception(db);
			    System.out.println("Khong The Thuc Hien Update SmartId vao bang  Don Vi Do Luong  Nay,Vui Long Lien He Voi Admin De Sua Loi Nay");
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

	
	public boolean UpdateDvdl(){
		// Update table "Donvidoluong"
		String query = "update donvidoluong set donvi =?,  diengiai = ?, ngaysua = ?,  nguoisua = ?, trangthai = ? where pk_seq = ?" ;
		Object[] data;
		data= new Object[]   {  this.donvi , this.diengiai , this.ngaysua , this.nguoisua,this.trangthai,this.id };
		db.viewQuery(query, data);
	 	
		if( this.db.updateQueryByPreparedStatement(query, data)!=1 ) {
		geso.dms.center.util.Utility.rollback_throw_exception(this.db);
		//this.msg = "Khong the luu vao table 'donvidoluong'";
		this.msg = "1. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý";
		return false;			
	    }
		/*if(!this.db.update(query)){
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			this.msg = "Khong the cap nhat table 'Donvidoluong'";
			return false; 
		}*/
		return true;
	}
		
	public void DBClose(){
		this.db.shutDown();

	}
	
}


