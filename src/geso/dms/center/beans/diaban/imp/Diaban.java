package geso.dms.center.beans.diaban.imp;

import geso.dms.center.beans.diaban.IDiaban;
import geso.dms.center.db.sql.*;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Diaban implements IDiaban
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId;
	String id;
	String ten;
	String diengiai;
	String trangthai;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String msg;
	
	String khuvucId = "";
	String khuvucTen = "";
	ResultSet khuvucRs ;
	
	dbutils db;
	
	
	
	public Diaban(String[] param)
	{
		this.id = param[0];
		this.ten = param[1];
		this.trangthai = param[2];
		this.ngaytao = param[3];
		this.nguoitao = param[4];
		this.ngaysua = param[5];
		this.nguoisua = param[6];
		this.diengiai = param[7];
		this.khuvucId = param[8];
		this.khuvucTen = param[9];
		this.msg = "";
		this.db = new dbutils();
	}
	
	public Diaban(String id)
	{
		this.id = id;
		this.ten = "";
		this.diengiai = "";
		this.trangthai = "2";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.khuvucId ="";
		this.msg = "";
		this.db = new dbutils();
		if(id.trim().length() > 0)
			this.init();
		khuvucRs =  db.get("select pk_seq , ten from khuvuc where trangthai = 1");
	}
	
	public String getKhuvucTen() {
		return khuvucTen;
	}
	public void setKhuvucTen(String khuvucTen) {
		this.khuvucTen = khuvucTen;
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

	public String getTen() 
	{
		return this.ten;
	}

	public void setTen(String ten) 
	{
		this.ten = ten;
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
		if (this.trangthai.equals("1")){
			return "Hoat dong";
		}
		return "Ngung hoat dong";
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
	
	public String getMessage() 
	{
		return this.msg;
	}
	
	public void setMessage(String msg) 
	{
		this.msg = msg;
	}

	
	public boolean CreateDiaBan() 
	{
		try{
			this.db.getConnection().setAutoCommit(false);
		this.ngaytao = getDateTime();
		this.nguoitao = this.userId;
		String command="";

		command = "insert into DIABAN(ten, ngaytao, ngaysua, nguoitao, nguoisua, khuvuc_fk, trangthai, diengiai)";
		command = command + " values(N'" + this.ten + "','" + this.ngaytao + "','" + this.ngaytao + "','" + this.nguoitao + "','" + this.nguoitao + "','" + this.khuvucId + "','1', N'" + this.diengiai  +"')";
		System.out.println("sql la "+command);
		if (!db.update(command)){
			this.msg = command;
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		

		
		this.db.getConnection().commit();
		this.db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			this.msg="Khong the cap nhat lai bang nay,xay ra loi trong dong lenh sau "+ er.toString();
			return false;
		}
		return true;
	}

	public boolean UpdateDiaBan() 
	{
		this.ngaysua = getDateTime();
		this.nguoisua = this.userId;
		String command="";

		command ="update DIABAN set ten = N'" + this.ten + "', khuvuc_fk='"+ this.khuvucId + "', diengiai=N'"+ this.diengiai + "', trangthai='" + this.trangthai + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.userId +  "' where pk_seq = '" + this.id + "'";
		
		if (!db.update(command)){
			this.msg = command;			
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}

		return true; 
	}
	
	private void init()
	{	
		String query = " select a.pk_seq as id, a.ten as dbTen, a.trangthai as trangthai, a.khuvuc_fk , c.ten as nguoitao, a.ngaytao as ngaytao, d.ten as nguoisua, a.ngaysua as ngaysua, a.diengiai";
		query = query + " from diaban a, nhanvien c, nhanvien d";
		query = query + " where  a.nguoitao = c.pk_seq and a.nguoisua = d.pk_seq and a.pk_seq = '" + this.id + "'";
        ResultSet rs =  this.db.get(query);
        try{
            rs.next();        	
        	this.id = rs.getString("id");
        	this.ten = rs.getString("dbTen");
        	this.trangthai = rs.getString("trangthai");
        	this.ngaytao = rs.getString("ngaytao");
        	this.nguoitao = rs.getString("nguoitao");
        	this.ngaysua = rs.getString("ngaysua");
        	this.nguoisua = rs.getString("nguoisua");
        	this.diengiai = rs.getString("diengiai");
        	this.khuvucId = rs.getString("khuvuc_fk");
        	     	
       	}catch(Exception e){
       		
       		e.printStackTrace();
       	}

       	
	}
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public String getKhuvucId() {
		return khuvucId;
	}
	public void setKhuvucId(String khuvucId) {
		this.khuvucId = khuvucId;
	}
	public ResultSet getKhuvucRs() {
		return khuvucRs;
	}
	
	public void closeDB(){
		try {
			if(khuvucRs!=null) {
				khuvucRs.close();
			}

		} catch(Exception e) {}
		if (this.db != null)
			this.db.shutDown();
	}


	
	
}
