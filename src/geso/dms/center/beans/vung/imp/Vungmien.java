package geso.dms.center.beans.vung.imp;

import geso.dms.center.beans.vung.IVungmien;
import geso.dms.center.db.sql.*;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Vungmien implements IVungmien
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
	String bm;
	ResultSet bms;
	String ma = "";
	
	dbutils db;
	
	public Vungmien(String[] param)
	{
		this.db = new dbutils();
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
		creatBm();
	}
	
	public Vungmien(String id)
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
		this.bm ="";
		this.msg = "";
		this.ma = "";
		if(id.length() > 0)
		
			this.init();
		creatBm();
		
	}
	public void creatBm()
	{
		String sql = "select * from bm";
       	bms = db.get(sql);
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
	
	public String getVungmien() 
	{
		return this.vungmien;
	}

	public void setVungmien(String vungmien) 
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
			List<Object> data = new ArrayList<Object>();
		this.ngaytao = getDateTime();
		this.nguoitao = this.userId;
		String command="";
		if(this.bm==null || this.bm.equals("")){
			/* command = "insert into vung(ten, diengiai, trangthai, ngaytao, ngaysua, nguoitao, nguoisua,ma) "
			 		+ "values(N'" + this.vungmien + "',N'" + this.diengiai + "'," + this.trangthai + ",'" + this.ngaytao + "',"
			 				+ "'" + this.ngaytao + "','" + this.userId + "','" + this.userId + "','" + this.ma + "')"; 
			 				*/
			command = "insert into vung(ten, diengiai, trangthai, ngaytao, ngaysua, nguoitao, nguoisua,ma) "
					+ " select ?,?,?,?,?,?,?,? ";
			data.clear();
			data.add(this.vungmien);
			data.add(this.diengiai);
			data.add(this.trangthai);
			data.add(this.ngaytao);
			data.add(this.ngaytao);
			data.add(this.userId);
			data.add(this.userId);
			data.add(this.ma);
			
		}else{
					/* command = "insert into vung(ten, diengiai, trangthai, ngaytao, ngaysua, nguoitao, nguoisua,bm_fk,ma) "
					 		+ "values(N'" + this.vungmien + "',N'" + this.diengiai + "'," + this.trangthai + ",'" + this.ngaytao + "',"
					 		+ "'" + this.ngaytao + "','" + this.userId + "','" + this.userId + "','"+ this.bm +"','" + this.ma + "')"; */
			command = "insert into vung(ten, diengiai, trangthai, ngaytao, ngaysua, nguoitao, nguoisua,bm_fk,ma) "
					+ " select ?,?,?,?,?,?,?,?,? ";
					 	data.clear();
						data.add(this.vungmien);
						data.add(this.diengiai);
						data.add(this.trangthai);
						data.add(this.ngaytao);
						data.add(this.ngaytao);
						data.add(this.userId);
						data.add(this.userId);
						data.add(this.bm);
						data.add(this.ma);
		}
		
		/*if (!this.db.update(command)){
			this.msg = command;		
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			return false;
		}*/
		
		if( this.db.updateQueryByPreparedStatement(command, data)!=1 )
		{
			this.msg="1. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý";
			this.db.getConnection().rollback();
			return false;
		}	
		
		this.db.getConnection().commit();
		this.db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			this.msg="khong the cap nhat vung nay,loi tai dong lenh sau :" + er.toString();
			return false;
		}
		return true;
	}

	public boolean UpdateVm() 
	{
		this.ngaysua = getDateTime();
		this.nguoisua = this.userId;
		 Object[] data;
		String command;
		/*if(this.bm==null || this.bm.equals("")){
			 command ="update vung set ten = N'" + this.vungmien + "', diengiai = N'" + this.diengiai + "', trangthai='" + this.trangthai + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.userId + "', ma = '"+this.ma+"'  where pk_seq = '" + this.id + "'";
		}else{
		 command ="update vung set ten = N'" + this.vungmien + "', diengiai = N'" + this.diengiai + "', trangthai='" + this.trangthai + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.userId + "' ,bm_fk = '"+ this.bm +"', ma = '"+this.ma+"'  where pk_seq = '" + this.id + "'";
		}*/
		
		if(this.bm==null || this.bm.equals("")){
			// command ="update vung set trangthai='" + this.trangthai + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.userId + "', ma = '"+this.ma+"'  where pk_seq = '" + this.id + "'";
			 command ="update vung set trangthai=?, ngaysua =?, nguoisua = ?, ma = ?, ten = ?, diengiai = ?  where pk_seq = ?";
			
			 data= new Object[]   {this.trangthai, this.ngaysua , this.userId, this.ma,this.vungmien, this.diengiai,this.id };
			
		}else{
		 //command ="update vung set  trangthai='" + this.trangthai + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.userId + "' ,bm_fk = '"+ this.bm +"'  where pk_seq = '" + this.id + "'";
			command ="update vung set  trangthai=?, ngaysua =?, nguoisua = ? ,bm_fk = ? ,ma = ?, ten = ?, diengiai = ?  where pk_seq = ?";
		 data= new Object[]   {this.trangthai, this.ngaysua , this.userId , this.bm ,this.ma,this.vungmien, this.diengiai, this.id };
		}
		db.viewQuery(command, data);
		//if (!this.db.update(command)){
		if( this.db.updateQueryByPreparedStatement(command, data)!=1 ) {
		//	this.msg = "update vung set ten = N'" + this.vungmien + "', diengiai = N'" + this.diengiai + "', trangthai='" + this.trangthai + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.userId + "' where pk_seq = '" + this.id + "'";
			this.msg = "2. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý";
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			return false;
		}

		return true; 
	}

	private void init()
	{	
		
		String query = "select a.pk_seq as id, a.ten, a.diengiai, a.trangthai, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua,a.bm_fk,a.ma"; 
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
        	this.bm = rs.getString("bm_fk");
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

	public void setBm(String bm) {
		
		this.bm = bm;
	}

	public String getBm() {
		
		return this.bm;
	}

	
	public void setBms(ResultSet bms) {
		
		this.bms = bms;
	}

	public ResultSet getBms() {
		return this.bms;
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

