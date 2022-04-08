package geso.dms.center.beans.kenhbanhang.imp;

import geso.dms.center.beans.kenhbanhang.IKenhbanhang;
import geso.dms.center.db.sql.*;
import geso.dms.center.util.Utility;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Kenhbanhang implements IKenhbanhang
{
	private static final long serialVersionUID = -9217977546733610214L;
	String userId;
	String id;
	String kenhbanhang;
	String diengiai;
	String trangthai;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String msg;
	String ma = "";
	

	dbutils db;
	
	public Kenhbanhang(String[] param)
	{
		this.db = new dbutils();
		this.id = param[0];
		this.kenhbanhang = param[1];
		this.diengiai = param[2];
		this.trangthai = param[3];
		this.ngaytao = param[4];
		this.nguoitao = param[5];
		this.ngaysua = param[6];
		this.nguoisua = param[7];
		this.ma = param[8];
		this.msg = "";
		this.db = new dbutils();
	}
	
	public Kenhbanhang(String id)
	{
		this.db = new dbutils();
		this.id = id;
		this.kenhbanhang = "";
		this.diengiai = "";
		this.trangthai = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.msg = "";
		this.db = new dbutils();
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
	
	public String getKenhbanhang() 
	{
		return this.kenhbanhang;
	}

	public void setKenhbanhang(String kenhbanhang) 
	{
		this.kenhbanhang = kenhbanhang;
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

	public boolean CreateKbh()
	{
		try{
			this.db.getConnection().setAutoCommit(false);
		this.ngaytao = getDateTime();
		this.nguoitao = this.userId;
		
		List<Object> data = new ArrayList<Object>();
		//String command = "insert into kenhbanhang(ten, diengiai, trangthai, ngaytao, ngaysua, nguoitao, nguoisua) values(N'" + this.kenhbanhang + "',N'" + this.diengiai + "'," + this.trangthai + ",'" + this.ngaytao + "','" + this.ngaytao + "','" + this.userId + "','" + this.userId + "')"; 
		String command = "insert into kenhbanhang(ten, diengiai, trangthai, ngaytao, ngaysua, nguoitao, nguoisua) "
		+ " select ?,?,?,?,?,?,? "; 
		data.clear();
		data.add(this.kenhbanhang);
		data.add(this.diengiai);data.add(this.trangthai);data.add(this.ngaytao);
		data.add(this.ngaytao);data.add( this.userId );data.add( this.userId );
		
		/*if (!db.update(command)){
			this.msg = "Khong the tao moi kenh ban hang: " + command;		
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			return false;
		}*/
		if( this.db.updateQueryByPreparedStatement(command, data)!=1 )
		{
			this.msg="Lỗi tạo mới KBH(1)";
			this.db.getConnection().rollback();
			return false;
		}	
		/*
		 * thuc hien cap nhat lai smartid
		 * 
		 */
		
		this.id= db.getPk_seq();
		data.clear();
		command="update kenhbanhang set smartid=? where pk_seq=?";
		data.add(this.id);
		data.add(this.id);
		if( this.db.updateQueryByPreparedStatement(command, data)!=1 )
		{
			this.msg="Lỗi cập nhật KBH(1)";
			this.db.getConnection().rollback();
			return false;
		}	
		/*String query_getid = "select IDENT_CURRENT('kenhbanhang') as id";
		ResultSet rs_getpk_seq = this.db.get(query_getid);
		rs_getpk_seq.next();
		this.id = rs_getpk_seq.getString("id");
		
		String sql_update_smartid="update kenhbanhang set smartid='"+this.id+"' where pk_seq=" + this.id;
		try{
		if(!db.update(sql_update_smartid)){
			 geso.dms.center.util.Utility.rollback_throw_exception(db);
			  System.out.println("Khong Cap Nhat Lai Duoc SmartId Cho Bang Kenh Ban Hang,Vui Long Lien He Voi Admin De Sua Loi Nay");
			return false;
		}
		}catch(Exception er){
			
		}*/
		this.db.getConnection().commit();
		this.db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			this.msg="Khong The Them Kenh Ban Hang Nay , Loi Dong Lenh Sau :" + er.toString();
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			return false;
		}
		return true;
	}

	public boolean UpdateKbh() 
	{
		this.ngaysua = getDateTime();
		this.nguoisua = this.userId;
		List<Object> data = new ArrayList<Object>();
		
		int[] quyen = Utility.Getquyen("KenhbanhangSvl", "",this.userId);
		if(quyen[Utility.SUA]!=1)
		{
			this.msg = "User không được phân quyền sửa!";
			return false;
		}
		
		if(!Utility.KiemTra_PK_SEQ_HopLe(this.id,"kenhbanhang", db))
		{
			this.msg =" Mã không hợp lệ!";
			return false;
		}
		
	/*	String command ="update kenhbanhang set ten = N'" + this.kenhbanhang + "', diengiai = N'" + this.diengiai + "', trangthai='" + this.trangthai + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.userId + "',FLEX_VALUE = '" + this.ma + "' where pk_seq = '" + this.id + "'";*/
		String command ="update kenhbanhang set  trangthai=?, ngaysua = ?, nguoisua = ? where pk_seq = ?";
		data.clear();
		data.add(this.trangthai);
		data.add(this.ngaysua);
		data.add(this.userId);
		data.add(this.id);
		
		if( this.db.updateQueryByPreparedStatement(command, data)!=1 )
		{
			this.msg="Lỗi cập nhật KBH(1)";
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			return false;
		}
		/*if (!this.db.update(command)){
			this.msg = "Khong the cap nhat: " + command;
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			return false;
		}*/

		return true; 
	}

	private void init()
	{	
		String query = "select a.pk_seq as id,isnull(a.FLEX_VALUE,'') as ma, a.ten as kbhTen, a.diengiai, a.trangthai, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua from kenhbanhang a, nhanvien b, nhanvien c ";
		query = query + " where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.pk_seq= '"+ this.id + "'"; 
        ResultSet rs =  this.db.get(query);
        try{
            rs.next();        	
        	this.id = rs.getString("id");
        	this.kenhbanhang = rs.getString("kbhTen");
        	this.diengiai = rs.getString("diengiai");
        	this.trangthai = rs.getString("trangthai");
        	this.ngaytao = rs.getString("ngaytao");
        	this.nguoitao = rs.getString("nguoitao");
        	this.ngaysua = rs.getString("ngaysua");
        	this.nguoisua = rs.getString("nguoisua");
        	this.ma = rs.getString("ma");
        	  	
       	}catch(Exception e){}
	}
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}	

	public void DBClose(){
		if (this.db != null) 
			this.db.shutDown();
	}


	public String getma() {
		// TODO Auto-generated method stub
		return this.ma;
	}


	public void setma(String ma) {
		// TODO Auto-generated method stub
		this.ma = ma;
	}
}
